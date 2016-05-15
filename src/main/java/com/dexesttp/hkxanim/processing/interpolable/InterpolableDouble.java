package com.dexesttp.hkxanim.processing.interpolable;

import com.dexesttp.hkxanim.processing.interpolator.Interpolator;

public class InterpolableDouble implements Interpolable<InterpolableDouble> {
	protected final double value;
	
	public InterpolableDouble(double value) {
		this.value = value;
	}

	@Override
	public InterpolableDouble interpolate(Interpolator interpolator, double time, InterpolableDouble other) {
		return new InterpolableDouble(interpolator.compute(this.value, time, other.value));
	}
	
	public double get() {
		return this.value;
	}
}
