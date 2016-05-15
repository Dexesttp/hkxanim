package com.dexesttp.hkxanim.collida.track;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.sampler.CollidaSamplerFactory;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

public class EmptyBoneTrack extends BoneTrack {

	public EmptyBoneTrack(String boneName) {
		super(boneName);
	}

	@Override
	public double maxTime() {
		return 0;
	}

	@Override
	public void addSampler(CollidaSamplerFactory samplerFactory, String transform, Element samplerElement)
			throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		// NO OP
		// TODO design a RuntimeException to maybe throw there ?
	}

	@Override
	public Vector3D getTranslationAt(Double frameTime) {
		return new Vector3D(0, 0, 0);
	}

	@Override
	public Quaternion getRotationAt(Double frameTime) {
		return new Quaternion(1, 0, 0, 0);
	}

	@Override
	public Vector3D getScalingAt(Double frameTime) {
		return new Vector3D(1, 1, 1);
	}

	@Override
	public void display() {
		System.out.println("Empty bone track");
	}

}
