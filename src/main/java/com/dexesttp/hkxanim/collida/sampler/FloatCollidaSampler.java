package com.dexesttp.hkxanim.collida.sampler;

import com.dexesttp.hkxanim.collida.arrays.AccessibleArray;
import com.dexesttp.hkxanim.collida.arrays.FloatAccessibleArray;
import com.dexesttp.hkxanim.processing.interpolable.InterpolableDouble;

public class FloatCollidaSampler extends CollidaSampler<InterpolableDouble> {

	public FloatCollidaSampler(final FloatAccessibleArray times,
			final AccessibleArray<InterpolableDouble> values,
			final AccessibleArray<String> interpolations,
			final AccessibleArray<Double> inTangents,
			final AccessibleArray<Double> outTangents) {
		super(times, values, interpolations, inTangents, outTangents);
	}

	@Override
	protected InterpolableDouble defaultStart() {
		// TODO Auto-generated method stub
		return new InterpolableDouble(0);
	}
}
