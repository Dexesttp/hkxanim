package com.dexesttp.hkxanim.processing.interpolable;

import com.dexesttp.hkxanim.processing.interpolator.Interpolator;

public interface Interpolable<T> {
	public T interpolate(Interpolator interpolator, double time, T other);
}
