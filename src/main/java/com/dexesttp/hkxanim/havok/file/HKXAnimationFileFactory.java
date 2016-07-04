package com.dexesttp.hkxanim.havok.file;

import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.exceptions.ClassFileReadException;

public class HKXAnimationFileFactory {
	private final HKXDescriptorFactory factory;

	public HKXAnimationFileFactory(HKXDescriptorFactory factory) {
		this.factory = factory;
	}
	
	public HKXFile createFile(HKXFile template, HKXAnimationContainer container) throws ClassFileReadException {
		double totalTime = container.getTotalTime();
		int framesLength = container.getFrames().size();
		System.out.println("Time : " + totalTime);
		System.out.println("Frames : " + framesLength);
		// Define the skeleton bones to use
		int bonesLength = container.getBones().size();
		System.out.println("Bones : " + bonesLength);
		
		// Define the skeleton bone hierarchy
		// HELP IM NOT GOOD WITH COMPUTERS OH GEEZ
		
		// Fill the animation file with the skeleton pose
		// HELP² HELP²
		
		// Fill the animation file with the animation data
		// Oh. That I know.
		
		// Fill the other data from the animation file.
		
		return null;
	}
}
