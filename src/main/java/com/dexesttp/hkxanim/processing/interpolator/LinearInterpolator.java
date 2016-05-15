package com.dexesttp.hkxanim.processing.interpolator;

/**
 * The most basic of interpolators
 */
public class LinearInterpolator implements Interpolator {
	/**
	 * {@inheritDoc}
	 */
	public double compute(final double start, final double evolution, final double end) {
		return start * (1 - evolution) + end * evolution;
	}
}
