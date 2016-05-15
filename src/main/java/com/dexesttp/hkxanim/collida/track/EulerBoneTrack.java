package com.dexesttp.hkxanim.collida.track;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.sampler.CollidaSamplerFactory;
import com.dexesttp.hkxanim.collida.sampler.FloatCollidaSampler;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

public class EulerBoneTrack extends BoneTrack {
	protected transient FloatCollidaSampler translateX;
	protected transient FloatCollidaSampler translateY;
	protected transient FloatCollidaSampler translateZ;
	protected transient FloatCollidaSampler rotateX;
	protected transient FloatCollidaSampler rotateY;
	protected transient FloatCollidaSampler rotateZ;
	protected transient FloatCollidaSampler scaleX;
	protected transient FloatCollidaSampler scaleY;
	protected transient FloatCollidaSampler scaleZ;

	public EulerBoneTrack(String boneName) {
		super(boneName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSampler(CollidaSamplerFactory samplerFactory, String transform, Element samplerElement) throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		switch(transform) {
			case "translation.X":
				translateX = samplerFactory.createFloatSampler(transform, samplerElement);
			case "translation.Y":
				translateY = samplerFactory.createFloatSampler(transform, samplerElement);
			case "translation.Z":
				translateZ = samplerFactory.createFloatSampler(transform, samplerElement);
			case "scale.X":
				scaleX = samplerFactory.createFloatSampler(transform, samplerElement);
			case "scale.Y":
				scaleY = samplerFactory.createFloatSampler(transform, samplerElement);
			case "scale.Z":
				scaleZ = samplerFactory.createFloatSampler(transform, samplerElement);
			case "rotationX.ANGLE":
				rotateX = samplerFactory.createFloatSampler(transform, samplerElement);
			case "rotationY.ANGLE":
				rotateY = samplerFactory.createFloatSampler(transform, samplerElement);
			case "rotationZ.ANGLE":
				rotateZ = samplerFactory.createFloatSampler(transform, samplerElement);
		}
	}

	/**
	 * {@inheritDoc}<br />
	 * <br />
	 * TIL that there's no Double... argument max. Thanks, Obama.
	 */
	@Override
	public double maxTime() {
		return Math.max(
			Math.max(
				Math.max(
					Math.max(rotateX.maxTime(),
						rotateY.maxTime()),
					Math.max(rotateZ.maxTime(),
						translateX.maxTime())),
				Math.max(
					Math.max(translateY.maxTime(),
						translateZ.maxTime()),
					Math.max(scaleX.maxTime(),
						scaleY.maxTime()))),
			scaleZ.maxTime());		
	}

	@Override
	public Vector3D getTranslationAt(Double frameTime) {
		return new Vector3D(
				translateX.processForTime(frameTime).get(),
				translateY.processForTime(frameTime).get(),
				translateZ.processForTime(frameTime).get());
	}

	@Override
	public Quaternion getRotationAt(Double frameTime) {
		return Quaternion.fromEuler(
				rotateX.processForTime(frameTime).get(),
				rotateY.processForTime(frameTime).get(),
				rotateZ.processForTime(frameTime).get());
	}

	@Override
	public Vector3D getScalingAt(Double frameTime) {
		return new Vector3D(
				scaleX.processForTime(frameTime).get(),
				scaleY.processForTime(frameTime).get(),
				scaleZ.processForTime(frameTime).get());
	}

	@Override
	public void display() {
		System.out.println("Euler bone track.");
	}
}
