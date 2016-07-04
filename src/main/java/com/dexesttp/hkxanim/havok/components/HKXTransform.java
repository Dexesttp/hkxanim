package com.dexesttp.hkxanim.havok.components;

import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

/**
 * A HKXTransform it the T * R * S composition.<br />
 * Internally, it is represented as two {@link Vector3D} (translation + scale) and a {@link Quaternion}.
 */
public class HKXTransform {
	private Vector3D translation;
	private Quaternion rotation;
	private Vector3D scale;
	
	/**
	 * Create a {@link HKXTransform} from a {@link HKXDirectMember}.
	 * @param qsTransform the {@link HKXDirectMember} to extract data from.
	 * @return the filled {@link HKXTransform}.
	 */
	public static HKXTransform fromHKX(HKXDirectMember<Double[]> qsTransform) {
		return new HKXTransform(
				new Vector3D(qsTransform.get()[0], qsTransform.get()[1], qsTransform.get()[2]),
				new Quaternion(qsTransform.get()[4], qsTransform.get()[5], qsTransform.get()[6], qsTransform.get()[7]),
				new Vector3D(qsTransform.get()[0], qsTransform.get()[1], qsTransform.get()[2])
			);
	}
	
	/**
	 * Create a {@link HKXTransform} from a translation, rotation and scale.
	 * @param translation the translation component.
	 * @param rotation the rotation component.
	 * @param scale the scaling component.
	 */
	public HKXTransform(Vector3D translation, Quaternion rotation, Vector3D scale) {
		this.translation = translation;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	/**
	 * Combines with another transform, member by member.<br />
	 * I'm actually not sure if it is the proper thing to do, but I'll test that and see if it explodes somehow.
	 * @param other the other transform to combine with.
	 * @result the resulting {@link HKXTransform}.
	 */
	public HKXTransform combine(HKXTransform other) {
		return new HKXTransform(
				this.translation.combine(other.translation),
				this.rotation.combine(other.rotation),
				this.scale.combine(other.scale));
	}
	
	/**
	 * Retrieve the {@link HKXTransform} as a {@link HKXDirectMember}, with no name.
	 * @return the {@link HKXDirectMember}.
	 */
	public HKXDirectMember<Double[]> toHKX() {
		return toHKX("");
	}
	
	/**
	 * Retrieve the {@link HKXTransform} as a {@link HKXDirectMember}, with the given name.
	 * @param name the {@link HKXDirectMember} name.
	 * @return the {@link HKXDirectMember}.
	 */
	public HKXDirectMember<Double[]> toHKX(String name) {
		HKXDirectMember<Double[]> result = new HKXDirectMember<>(name, HKXType.TYPE_QSTRANSFORM);
		result.set(new Double[] {
				translation.getX(), translation.getY(), translation.getZ(), 0.,
				rotation.getTheta(), rotation.getQX(), rotation.getQY(), rotation.getQZ(),
				scale.getX(), scale.getY(), scale.getZ(), 0.
		});
		return result;
	}
	
}
