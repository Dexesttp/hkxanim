package com.dexesttp.hkxanim.processing.matrix;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PrimitiveIterator.OfDouble;

import com.dexesttp.hkxanim.processing.interpolable.Interpolable;
import com.dexesttp.hkxanim.processing.interpolator.Interpolator;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;

/**
 * A Matrix as given by the Blender Collida.
 */
public class Matrix implements Interpolable<Matrix> {
	private double[][] values;

	/**
	 * Create a new Matrix
	 * @param values the Matrix values, ordered by (line, column);
	 */
	public Matrix(double[][] values) {
		this.values = values.clone();
	}
	
	/**
	 * Creates a new Matrix by consuming the next 16 elements of the given iterator.
	 * @param contentsIterator the {@link OfDouble} iterator to consume from 
	 */
	public Matrix(Iterator<Double> contentsIterator) {
		this.values = new double[][]{
			{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
			{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
			{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
			{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
		};
	}

	/**
	 * Interpolate two matrices given an interpolator.
	 * @param interpolator the interpolator to use
	 * @param evolution the evolution at which to interpolate
	 * @param other the other {@link Matrix} to interpolate to.
	 * @return the interpolated {@link Matrix}.
	 */
	public Matrix interpolate(Interpolator interpolator, double evolution, Matrix other) {
		double res[][] = new double[4][4];
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				res[i][j] = interpolator.compute(this.values[i][j], evolution, other.values[i][j]);
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
	
	/**
	 * {@inheritDoc}
	 */
	public Vector3D getTranslation() {
		return new Vector3D(getXTranslation(), getYTranslation(), getZTranslation());
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
	
	/**
	 * {@inheritDoc}
	 */
	public Vector3D getScale() {
		return new Vector3D(getXScale(), getYScale(), getZScale());
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
		return - (values[0][2] / getXScale() -
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
	
	/**
	 * {@inheritDoc}
	 */
	public Quaternion getQuaternion() {
		return new Quaternion(getTheta(), getQX(), getQY(), getQZ());
	}
	
	public String toString() {
		return Arrays.deepToString(values);
	}
}
