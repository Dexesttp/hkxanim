package com.dexesttp.hkxanim.collida.bones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.soap.Node;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CollidaSkeletonHandler {
	protected Map<String, Element> boneMap = null;
	public CollidaSkeletonHandler() {
	}
	
	public void initialize(Element rootBone) {
		boneMap = new HashMap<>();
		NodeList nodes = rootBone.getElementsByTagName("node");
		for(int i = 0; i < nodes.getLength(); i++) {
			if(nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element bone = (Element) nodes.item(i);
				String boneName = bone.getAttribute("sid");
				boneMap.put(boneName, bone);
			}
		}
	}
	
	public List<CollidaBone> buildBoneList(Set<String> boneNames) {
		if(boneMap == null) {
			throw new RuntimeException("The buildBoneList function was called before the initialize function in CollidaSkeletonHandler.");
		}
		List<CollidaBone> result = new ArrayList<>();
		for(String boneName: boneNames) {
			if(result.stream().allMatch(b -> !b.getName().equals(boneName))) {
				if(boneMap.containsKey(boneName))
					includeParents(boneName, result);	
				else
					System.out.println(String.format("Couldn't find bone : %s in armature", boneName));
			}
		}
		return result;
	}
	
	protected void includeParents(String name, List<CollidaBone> result) {
		Element boneElement = boneMap.get(name);
		Element parentElement = (Element) boneElement.getParentNode();
		CollidaBone resultBone = CollidaBone.fromCollidaNode(boneElement);
		if(parentElement.getTagName() == "node" && !parentElement.getAttribute("name").equals("Armature")) {
			String parentName = parentElement.getAttribute("sid");
			if(result.stream().allMatch(b -> !b.getName().equals(parentName))) {
				includeParents(parentName, result);
			}
			resultBone.setParentID(
				result.indexOf(
					result.stream()
						.filter(b -> b.getName().equals(parentName))
						.findFirst().get()
				));
		}
		result.add(resultBone);
	}
}
