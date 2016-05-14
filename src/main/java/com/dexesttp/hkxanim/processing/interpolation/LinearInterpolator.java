package com.dexesttp.hkxanim.processing.interpolation;

/**
 * The most basic of interpolators
 */
public class LinearInterpolator implements Interpolator {
	/**
	 * {@inheritDoc}
	 */
	public double compute(double start, double evolution, double end) {
		return start * (1 - evolution) + end * evolution;
	}
}
