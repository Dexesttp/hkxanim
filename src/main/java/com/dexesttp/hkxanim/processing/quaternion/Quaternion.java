package com.dexesttp.hkxanim.processing.quaternion;

/**
 * Rotation component made of a Theta, QX, QY and QZ value.<br />
 * I think it defines like "rotate Theta arount the axis defined by QX/QY/QZ starting at 0, 0".
 */
public class Quaternion {
	private double theta;
	private double qx;
	private double qy;
	private double qz;

	/**
	 * Create a {@link Quaternion} from its intended values.
	 * @param theta the Theta parameter of the {@link Quaternion}
	 * @param qx the X parameter of the {@link Quaternion}
	 * @param qy the Y parameter of the {@link Quaternion}
	 * @param qz the Z parameter of the {@link Quaternion}
	 */
	public Quaternion(double theta, double qx, double qy, double qz) {
		this.theta = theta;
		this.qx = qx;
		this.qy = qy;
		this.qz = qz;
	}
	
	/**
	 * Create a new {@link Quaternion} from an Euler rotation.<br />
	 * The formula is in fact easy to remember : <br />
	 *  - Each part does ABC + ABC <br />
	 *  - c = cosA/B/C and s = sinA/B/C <br />
	 *  - two rectangles, bottom square striked top left to bottom right<br />
	 *  - Cos left, sin right.<br />
	 *  - Plus and minus every odd and even.<br />
	 *  <pre>
	 *		ccc - sss
	 *	Q=	scc - css
	 *		csc - scs
	 *		ccs - ssc
	 * </pre>
	 * @param angleX the X angle of the Euler rotation
	 * @param angleY the Y angle of the Euler rotation
	 * @param angleZ the Z angle of the Euler rotation
	 * @return the relevant {@link Quaternion}.
	 * @see <a href="https://en.wikipedia.org/wiki/Conversion_between_quaternions_and_Euler_angles">Conversion between Quaternions and Euler angles</a>
	 */
	public static Quaternion fromEuler(double angleX, double angleY, double angleZ) {
		double cosA = Math.cos(angleX / 2);
		double cosB = Math.cos(angleY / 2);
		double cosC = Math.cos(angleZ / 2);
		double sinA = Math.sin(angleX / 2);
		double sinB = Math.sin(angleY / 2);
		double sinC = Math.sin(angleZ / 2);
		return new Quaternion(
				cosA * cosB * cosC + sinA * sinB * sinC,
				sinA * cosB * cosC - cosA * sinB * sinC,
				cosA * sinB * cosC + sinA * cosB * sinC,
				cosA * cosB * sinC - sinA * sinB * cosC
				);
	}

	/**
	 * Get the Theta value of this {@link Quaternion}.
	 * @return the theta value of this {@link Quaternion}.
	 */
	public double getTheta() {
		return theta;
	}

	/**
	 * Get the QX value of this {@link Quaternion}.
	 * @return the qx value of this {@link Quaternion}.
	 */
	public double getQX() {
		return qx;
	}

	/**
	 * Get the QY value of this {@link Quaternion}.
	 * @return the qy value of this {@link Quaternion}.
	 */
	public double getQY() {
		return qy;
	}

	/**
	 * Get the QZ value of this {@link Quaternion}.
	 * @return the qz value of this {@link Quaternion}.
	 */
	public double getQZ() {
		return qz;
	}
}
