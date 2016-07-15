package com.dexesttp.hkxanim.havok.utilities;

import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxanim.havok.components.HKXTransform;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.HKXDescriptor;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;

public class HKXAnimationUtilities {
	
	private HKXDescriptorFactory factory;

	public HKXAnimationUtilities(HKXDescriptorFactory factory) {
		this.factory = factory;
	}

	@SuppressWarnings("unchecked")
	public void fillAnimationNode(HKXObject node, HKXAnimationContainer container) throws ClassFileReadException {
		// Define the length of the animation
		double totalTime = container.getTotalTime();
		HKXMember duration = node.getMembersList().get(2);
		((HKXDirectMember<Double>) duration).set(totalTime);
		
		// Define the number of bone tracks to use
		int bonesLength = container.getBones().size();
		HKXMember numberOfTransformTracks = node.getMembersList().get(3);
		((HKXDirectMember<Integer>) numberOfTransformTracks).set(bonesLength);
		
		// Define the annotation tracks
		HKXMember annotationTracks = node.getMembersList().get(6);
		fillAnnotationTracks((HKXArrayMember) annotationTracks, container);

		// Define the transforms
		HKXMember transforms = node.getMembersList().get(7);
		fillTransforms((HKXArrayMember) transforms, container);
	}

	private void fillAnnotationTracks(HKXArrayMember annotationTracks, HKXAnimationContainer container) throws ClassFileReadException {
		HKXDescriptor annotationTrackDescriptor = factory.get("hkaAnnotationTrack");
		for(String boneName : container.getBones()) {
			HKXObject annotationTrack = new HKXObject("", annotationTrackDescriptor);
			HKXStringMember nameMember = new HKXStringMember("trackName", HKXType.TYPE_STRINGPTR);
			nameMember.set(boneName);
			annotationTrack.getMembersList().add(nameMember);
			
			HKXArrayMember arrayMember = new HKXArrayMember("annotations", HKXType.TYPE_STRINGPTR, HKXType.TYPE_STRUCT);
			annotationTrack.getMembersList().add(arrayMember);
		}
	}

	private void fillTransforms(HKXArrayMember transforms, HKXAnimationContainer container) {
		for(HKXTransform frame : container.getFrames()) {
			transforms.add(frame.toHKX());
		}
	}
}
