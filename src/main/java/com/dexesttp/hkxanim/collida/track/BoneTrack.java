package com.dexesttp.hkxanim.collida.track;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.sampler.CollidaSamplerFactory;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

public abstract class BoneTrack {
	protected final String boneName;
	public BoneTrack(String boneName) {
		this.boneName = boneName;
	}

	/**
	 * Retrieves the max time displayed on this bone track.
	 * @return the max time of this bone track.
	 */
	public abstract double maxTime();
	
	/**
	 * Add the given sampler, by DOM {@link Element}.
	 * @param samplerFactory the Sampler factory to use.
	 * @param transform the transform type of the sampler
	 * @param samplerElement the {@link Element} the sampler is in.
	 */
	public abstract void addSampler(final CollidaSamplerFactory samplerFactory, final String transform, final Element samplerElement)
			throws XPathExpressionException, DOMException, UnhandledAccessibleArray;

	public abstract Vector3D getTranslationAt(final Double frameTime);

	public abstract Quaternion getRotationAt(final Double frameTime);

	public abstract Vector3D getScalingAt(final Double frameTime);

	public abstract void display();
}
