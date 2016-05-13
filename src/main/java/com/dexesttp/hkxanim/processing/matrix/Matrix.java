package com.dexesttp.hkxanim.processing.matrix;

import com.dexesttp.hkxanim.processing.interpolation.Interpolator;

public class Matrix {
	private double[][] values;

	/**
	 * Create a new Matrix
	 * @param values the Matrix values, ordered by (line, column);
	 */
	public Matrix(double[][] values) {
		this.values = values.clone();
	}
	
	public Matrix interpolate(Interpolator interpolator, double time, Matrix other) {
		double res[][] = new double[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				res[i][j] = interpolator.compute(this.values[i][j], time, other.values[i][j]);
			}
		}
		return new Matrix(values);
	}

// Helper methods
	/**
	 * Retrieve the size of a 3D vector.
	 * @param x the X coordinate of the vector
	 * @param y the Y coordinate of the vector
	 * @param z the Z coordinate of the vector.
	 * @return the size of the <x y z> vector.
	 */
	private double get3DSize(final double x, final double y, final double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

// Translation
	/**
	 * Get the X translation component of this matrix.
	 * @return the X translation component of this matrix.
	 */
	public double getXTranslation() {
		return values[3][0];
	}

	/**
	 * Get the Y translation component of this matrix.
	 * @return the Y translation component of this matrix.
	 */
	public double getYTranslation() {
		return values[3][1];
	}

	/**
	 * Get the Z translation component of this matrix.
	 * @return the Z translation component of this matrix.
	 */
	public double getZTranslation() {
		return values[3][2];
	}

// Scale
	/**
	 * Get the X scale of this matrix.
	 * @return the X scale of this matrix.
	 */
	public double getXScale() {
		return get3DSize(
			values[0][0],
			values[0][1],
			values[0][2]);
	}

	/**
	 * Get the Y scale of this matrix.
	 * @return the Y scale of this matrix.
	 */
	public double getYScale() {
		return get3DSize(
			values[1][0],
			values[1][1],
			values[1][2]);
	}

	/**
	 * Get the Z scale of this matrix.
	 * @return the Z scale of this matrix.
	 */
	public double getZScale() {
		return get3DSize(
			values[2][0],
			values[2][1],
			values[2][2]);
	}

// Rotation
	/**
	 * Get the Theta component of the <T, qx, qy, qz> rotation quaternion.
	 * @return the Theta component of the Quaternion.
	 */
	public double getTheta() {
		return Math.sqrt(1 +
				values[0][0] / getXScale() + 
				values[1][1] / getYScale() + 
				values[2][2] / getZScale());
	}

	/**
	 * Get the QX component of the <T, qx, qy, qz> rotation quaternion.
	 * @return the QX component of the Quaternion.
	 */
	public double getQX() {
		return (values[2][1] / getZScale() - 
				values[1][2] / getYScale())
				/ getTheta();
	}

	/**
	 * Get the QY component of the <T, qx, qy, qz> rotation quaternion.
	 * @return the QY component of the Quaternion.
	 */
	public double getQY() {
		return (values[0][2] / getXScale() -
				values[2][0] / getZScale())
				/ getTheta();
	}

	/**
	 * Get the QZ component of the <T, qx, qy, qz> rotation quaternion.
	 * @return the QZ component of the Quaternion.
	 */
	public double getQZ() {
		return (values[1][0] / getYScale() - 
				values[0][1] / getXScale())
				/ getTheta();
	}
}
