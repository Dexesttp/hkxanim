package com.dexesttp.hkxanim.processing.vector;

public class Vector3D {
	protected final double x;
	protected final double y;
	protected final double z;

	public Vector3D(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	/**
	 * First grade vector cross product formula.<br />
	 * <a href="https://upload.wikimedia.org/math/9/3/3/9332a8bbcefd39abb4c01c9a07a087a7.png">Formula from Wikipedia</a>
	 * @param other the other vector to combine with
	 * @return the new combined vector.
	 */
	public Vector3D combine(Vector3D other) {
		return new Vector3D(
				this.y * other.z - this.z * other.y,
				this.z * other.x - this.x - other.z,
				this.x * other.y - this.y - other.x
			);
	}
}
