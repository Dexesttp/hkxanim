package com.dexesttp.hkxanim.collida.sampler;

import com.dexesttp.hkxanim.collida.arrays.AccessibleArray;
import com.dexesttp.hkxanim.collida.arrays.FloatAccessibleArray;
import com.dexesttp.hkxanim.processing.interpolable.Interpolable;
import com.dexesttp.hkxanim.processing.interpolator.Interpolator;
import com.dexesttp.hkxanim.processing.interpolator.LinearInterpolator;

/**
 * Retrieve and handle a Collida sampler<br />
 */
public abstract class CollidaSampler<T extends Interpolable<T>> {
	protected final FloatAccessibleArray times;
	protected final AccessibleArray<T> values;
	protected final AccessibleArray<String> interpolations;
	protected final AccessibleArray<Double> inTangents;
	protected final AccessibleArray<Double> outTangents;

	public CollidaSampler(final FloatAccessibleArray times,
			final AccessibleArray<T> values,
			final AccessibleArray<String> interpolations,
			final AccessibleArray<Double> inTangents,
			final AccessibleArray<Double> outTangents) {
		this.times = times;
		this.values = values;
		this.interpolations = interpolations;
		this.inTangents = inTangents;
		this.outTangents = outTangents;
	}

	/**
	 * Get a default starting value for the element type, if there isn't one.
	 * @return the default starting value.
	 */
	protected abstract T defaultStart();

	/**
	 * Create the relevant interpolator for the given index.
	 * @return the relevant {@link Interpolator}.
	 */
	protected Interpolator createInterpolator(int index) {
		// TODO create the relevant interpolator instead of a linear one.
		return new LinearInterpolator();
	}

	public Double maxTime() {
		return times.getMax();
	}
	
	public T processForTime(final double time) {
		int index = 0;
		while(true) {
			if(times.get(index) >= time)
				break;
			index++;
			// If you got past the end of the array, return the last element.
			if(index >= times.size())
				return values.get(index-1);
		}
		// Retrieve the starting element.
		T start = index == 0
			? defaultStart()
			: values.get(index - 1);
		// Create the interpolator
		Interpolator interpolator = createInterpolator(index);
		// Compute the evolution percent of the interpolation
		double timeStart = index==0 ? 0 : times.get(index-1);
		double timeEnd = times.get(index);
		double evolution = (timeEnd - time) / (timeEnd - timeStart);
		// Return the interpolated result.
		return start.interpolate(interpolator, evolution, values.get(index));
	}
}
