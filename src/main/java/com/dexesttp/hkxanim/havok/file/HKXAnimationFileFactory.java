package com.dexesttp.hkxanim.havok.file;

import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxanim.havok.utilities.HKXAnimationUtilities;
import com.dexesttp.hkxanim.havok.utilities.HKXSkeletonUtilities;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;

public class HKXAnimationFileFactory {
	private final HKXDescriptorFactory factory;

	public HKXAnimationFileFactory(HKXDescriptorFactory factory) {
		this.factory = factory;
	}
	
	public HKXFile createFile(HKXFile template, HKXAnimationContainer container) throws ClassFileReadException {
		
		// Fill the skeleton node with the skeleton pose
		HKXObject skeletonObject = template.getContentCollection()
				.stream()
				.filter(obj -> obj.getDescriptor().getName().equals("hkaSkeleton"))
				.findFirst()
				.orElseThrow(() -> new NullPointerException("The skeleton node couldn't be retrieved from the template file"));
		
		HKXSkeletonUtilities skeletonHelper = new HKXSkeletonUtilities(factory);
		skeletonHelper.fillSkeletonNode(skeletonObject, container);
		
		// Fill the animation node with the animation data
		HKXObject animationObject = template.getContentCollection()
				.stream()
				.filter(obj -> obj.getDescriptor().getName().equals("hkaInterleavedUncompressedAnimation"))
				.findFirst()
				.orElseThrow(() -> new NullPointerException("The animation node couldn't be retrieved from the template file"));

		HKXAnimationUtilities animationHelper = new HKXAnimationUtilities(factory);
		animationHelper.fillAnimationNode(animationObject, container);

		// Fill the animation binding node with the tracks data
		HKXObject animationBindingObject = template.getContentCollection()
				.stream()
				.filter(obj -> obj.getDescriptor().getName().equals("hkaAnimationBinding"))
				.findFirst()
				.orElseThrow(() -> new NullPointerException("The animation binding node couldn't be retrieved from the template file"));
		
		HKXArrayMember transformTracksToBoneIndices = (HKXArrayMember) animationBindingObject.getMembersList().get(3);
		for(int i = 0; i < container.getBones().size(); i++) {
			HKXDirectMember<Integer> boneIndice = new HKXDirectMember<>("", HKXType.TYPE_INT16);
			boneIndice.set(i);
			transformTracksToBoneIndices.add(boneIndice);
		}
		System.out.println("== Template filled ==");
		
		return template;
	}
}
