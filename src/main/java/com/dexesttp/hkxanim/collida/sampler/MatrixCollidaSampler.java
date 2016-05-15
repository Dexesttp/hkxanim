package com.dexesttp.hkxanim.collida.sampler;

import com.dexesttp.hkxanim.collida.arrays.AccessibleArray;
import com.dexesttp.hkxanim.collida.arrays.FloatAccessibleArray;
import com.dexesttp.hkxanim.processing.matrix.Matrix;

public class MatrixCollidaSampler extends CollidaSampler<Matrix> {
	public MatrixCollidaSampler(final FloatAccessibleArray times,
			final AccessibleArray<Matrix> values,
			final AccessibleArray<String> interpolations,
			final AccessibleArray<Double> inTangents,
			final AccessibleArray<Double> outTangents) {
		super(times, values, interpolations, inTangents, outTangents);
	}

	@Override
	protected Matrix defaultStart() {
		return new Matrix(new double[][]{
			{1, 0, 0, 0},
			{0, 1, 0, 0},
			{0, 0, 1, 0},
			{1, 1, 1, 0}
		});
	}

	public void display() {
		values.display();
	}
}
