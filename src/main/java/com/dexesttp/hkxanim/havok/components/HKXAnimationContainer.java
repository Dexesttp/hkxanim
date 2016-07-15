package com.dexesttp.hkxanim.havok.components;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxanim.collida.bones.CollidaBone;

public class HKXAnimationContainer {
	private final double totalTime;
	private final List<String> bones = new ArrayList<>();
	private final List<HKXTransform> poses = new ArrayList<>();
	private final List<HKXTransform> frames = new ArrayList<>();
	private List<CollidaBone> boneList = null;
	
	public HKXAnimationContainer(double totalTime) {
		this.totalTime = totalTime;
	}

	public void setCollidaBones(List<CollidaBone> boneList) {
		this.boneList = boneList;
	}

	public List<CollidaBone> getCollidaBones() {
		return this.boneList;
	}

	public void addDefaultPose(String boneName, HKXTransform bone) {
		this.bones.add(boneName);
		this.poses.add(bone);
	}
	
	public void addFrame(HKXTransform bone, HKXTransform hkxTransform) {
		this.frames.add(bone.combine(hkxTransform));
	}
	
	public double getTotalTime() {
		return totalTime;
	}
	
	public List<String> getBones() {
		return bones;
	}
	
	public List<HKXTransform> getFrames() {
		return frames;
	}
}
