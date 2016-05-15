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
}
