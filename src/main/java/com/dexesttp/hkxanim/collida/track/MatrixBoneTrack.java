package com.dexesttp.hkxanim.collida.track;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.sampler.CollidaSamplerFactory;
import com.dexesttp.hkxanim.collida.sampler.MatrixCollidaSampler;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

public class MatrixBoneTrack extends BoneTrack {
	protected transient MatrixCollidaSampler values;
	
	public MatrixBoneTrack(String boneName) {
		super(boneName);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSampler(CollidaSamplerFactory samplerFactory, String transform, Element samplerElement) throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		switch(transform) {
			case "transform":
				this.values = samplerFactory.createMatrixSampler(transform, samplerElement);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double maxTime() {
		return values.maxTime();
	}

	@Override
	public Vector3D getTranslationAt(Double frameTime) {
		return values.processForTime(frameTime).getTranslation();
	}

	@Override
	public Quaternion getRotationAt(Double frameTime) {
		return values.processForTime(frameTime).getQuaternion();
	}

	@Override
	public Vector3D getScalingAt(Double frameTime) {
		return values.processForTime(frameTime).getScale();
	}

	@Override
	public void display() {
		values.display();
	}
}
