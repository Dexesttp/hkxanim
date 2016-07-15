package com.dexesttp.hkxanim.havok.utilities;

import com.dexesttp.hkxanim.collida.bones.CollidaBone;
import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;

public class HKXSkeletonUtilities {
	private HKXDescriptorFactory factory;

	public HKXSkeletonUtilities(HKXDescriptorFactory factory){
		this.factory = factory;
	}
	
	/**
	 * Fill the given node with the Skeleton node
	 * @param node the Skeleton {@link HKXObject} to fill
	 * @param container the {@link HKXAnimationContainer} with the relevant skeleton data inside
	 * @throws ClassFileReadException if there was an error creating descriptiors for the Skeleton data
	 */
	public void fillSkeletonNode(HKXObject node, HKXAnimationContainer container) throws ClassFileReadException {
		HKXMember parentIndices = node.getMembersList().get(2);
		HKXMember bones = node.getMembersList().get(3);
		HKXMember referencePose = node.getMembersList().get(4);
		fillIndices((HKXArrayMember) parentIndices, container);
		fillBones((HKXArrayMember) bones, container);
		fillPose((HKXArrayMember) referencePose, container);
	}

	private void fillIndices(HKXArrayMember parentIndices, HKXAnimationContainer container) {
		for(CollidaBone bone : container.getCollidaBones()) {
			HKXDirectMember<Integer> boneParentID = new HKXDirectMember<>("", HKXType.TYPE_INT16);
			boneParentID.set(bone.getParentID());
			parentIndices.add(boneParentID);
		}
	}

	private void fillBones(HKXArrayMember bones, HKXAnimationContainer container) throws ClassFileReadException {
		HKXDescriptor boneDescriptor = factory.get("hkaBone");
		for(CollidaBone bone : container.getCollidaBones()) {
			HKXObject object = new HKXObject("", boneDescriptor);
			HKXStringMember nameMember = new HKXStringMember("name", HKXType.TYPE_STRINGPTR);
			nameMember.set(bone.getName());
			object.getMembersList().add(nameMember);
			HKXDirectMember<Boolean> translationMember = new HKXDirectMember<>("lockTranslation", HKXType.TYPE_BOOL);
			translationMember.set(bone.isTranslationLocked());
			object.getMembersList().add(translationMember);
			bones.add(object);
		}
	}

	private void fillPose(HKXArrayMember referencePose, HKXAnimationContainer container) {
		for(CollidaBone bone : container.getCollidaBones()) {
			referencePose.add(bone.getTransform().toHKX());
		}
	}
	
}
