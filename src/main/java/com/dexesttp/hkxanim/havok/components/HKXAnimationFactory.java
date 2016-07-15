package com.dexesttp.hkxanim.havok.components;

import java.util.List;

import com.dexesttp.hkxanim.collida.bones.CollidaBone;
import com.dexesttp.hkxanim.collida.track.BoneTrackOrganizer;

public class HKXAnimationFactory {
	public HKXAnimationContainer fromTracks(List<CollidaBone> boneList, BoneTrackOrganizer boneTracks) {
		HKXAnimationContainer container = new HKXAnimationContainer(boneTracks.getMaxTime());
		int boneCount = 0;
		// Trying bone first, frame next.
		for(String boneName : boneTracks.getBoneNames()) {
			if(boneList.stream().anyMatch(b -> b.getName().equals(boneName))) {
				CollidaBone collidaBone = boneList.stream()
						.filter(b -> b.getName().equals(boneName))
						.findFirst().orElseThrow(() -> new NullPointerException(String.format("The collida bone : %s couldn't be found in the list of available bones.", boneName)));
				container.addDefaultPose(boneName, collidaBone.getTransform());
				for(double time : boneTracks.getFrameTimes()) {
					container.addFrame(
							collidaBone.getTransform(),
							new HKXTransform(
								boneTracks.getBoneTrack(boneName).get().getTranslationAt(time),
								boneTracks.getBoneTrack(boneName).get().getRotationAt(time),
								boneTracks.getBoneTrack(boneName).get().getScalingAt(time))
						);
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
