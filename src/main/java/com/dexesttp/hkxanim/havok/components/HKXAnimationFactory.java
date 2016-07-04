package com.dexesttp.hkxanim.havok.components;

import com.dexesttp.hkxanim.collida.track.BoneTrackOrganizer;

public class HKXAnimationFactory {
	private final HKXSkeleton skeleton;

	public HKXAnimationFactory(HKXSkeleton skeleton) {
		this.skeleton = skeleton;
	}

	public HKXAnimationContainer fromTracks(BoneTrackOrganizer boneTracks) {
		HKXAnimationContainer container = new HKXAnimationContainer(boneTracks.getMaxTime());
		int boneCount = 0;
		// Trying bone first, frame next.
		for(String boneName : boneTracks.getBoneNames()) {
			if(skeleton.hasBone(boneName)) {
				container.addDefaultPose(boneName, skeleton.getBone(boneName));
				for(double time : boneTracks.getFrameTimes()) {
					container.addFrame(
							skeleton.getBone(boneName),
							new HKXTransform(
								boneTracks.getBoneTrack(boneName).get().getTranslationAt(time),
								boneTracks.getBoneTrack(boneName).get().getRotationAt(time),
								boneTracks.getBoneTrack(boneName).get().getScalingAt(time)
						));
				}
				boneCount++;
			} else {
				System.out.println("Couldn't export bone : " + boneName);
			}
		}
		System.out.println("Exported " + boneCount + " bones.");
		return container;
	}
}
