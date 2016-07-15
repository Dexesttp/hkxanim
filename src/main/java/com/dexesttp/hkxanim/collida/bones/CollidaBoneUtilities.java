package com.dexesttp.hkxanim.collida.bones;

import java.util.Optional;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexesttp.hkxanim.havok.components.HKXTransform;
import com.dexesttp.hkxanim.processing.matrix.Matrix;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

public class CollidaBoneUtilities {
	public static HKXTransform createTransformFromNode(Element boneElement) {
		Optional<Vector3D> translate = Optional.empty();
		Optional<Quaternion> rotateFull = Optional.empty();
		Optional<Quaternion> rotateX = Optional.empty();
		Optional<Quaternion> rotateY = Optional.empty();
		Optional<Quaternion> rotateZ = Optional.empty();
		Optional<Vector3D> scale = Optional.empty();
		
		NodeList childList = boneElement.getChildNodes();
		for(int i = 0; i < childList.getLength(); i++) {
			Node currentNode = childList.item(i);
			if(currentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element currentElement = (Element) currentNode;
				String[] valuesList = Optional.of(currentElement.getTextContent()).orElse("").split(" ");
				switch(currentElement.getAttribute("sid")) {
					case "transform" : 
						Matrix x = new Matrix(new double[][]{
							{Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2]), Double.parseDouble(valuesList[3])},
							{Double.parseDouble(valuesList[4]), Double.parseDouble(valuesList[5]), Double.parseDouble(valuesList[6]), Double.parseDouble(valuesList[7])},
							{Double.parseDouble(valuesList[8]), Double.parseDouble(valuesList[9]), Double.parseDouble(valuesList[10]), Double.parseDouble(valuesList[11])},
							{Double.parseDouble(valuesList[12]), Double.parseDouble(valuesList[13]), Double.parseDouble(valuesList[14]), Double.parseDouble(valuesList[15])}
						});
						return new HKXTransform(x.getTranslation(), x.getQuaternion(), x.getScale());
					case "location" :
						translate = Optional.of(new Vector3D(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2])));
						break;
					case "rotationZ" :
						rotateZ = Optional.of(new Quaternion(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2]), Double.parseDouble(valuesList[3])));
						break;
					case "rotationY" :
						rotateY = Optional.of(new Quaternion(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2]), Double.parseDouble(valuesList[3])));
						break;
					case "rotationX" :
						rotateX = Optional.of(new Quaternion(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2]), Double.parseDouble(valuesList[3])));
						break;
					case "rotation" :
						rotateFull = Optional.of(new Quaternion(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2]), Double.parseDouble(valuesList[3])));
						break;
					case "scale" :
						scale = Optional.of(new Vector3D(Double.parseDouble(valuesList[0]), Double.parseDouble(valuesList[1]), Double.parseDouble(valuesList[2])));
						break;
					default:
						break;
				}
			}
		}
		
		if(!rotateFull.isPresent()) {
			Quaternion result = rotateZ.orElse(rotateY.orElse(rotateX.get()));
			if(rotateZ.isPresent() && rotateY.isPresent())
				result.combine(rotateY.get());
			if((rotateZ.isPresent() || rotateY.isPresent()) && rotateX.isPresent())
				result.combine(rotateX.get());
			rotateFull = Optional.of(result);
		}
		return new HKXTransform(translate.get(), rotateFull.get(), scale.get());
	}
}
