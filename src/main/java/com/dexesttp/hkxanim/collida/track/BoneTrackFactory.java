package com.dexesttp.hkxanim.collida.track;

import com.dexesttp.hkxanim.collida.channel.CollidaChannel;

/**
 * Create a {@link BoneTrack} from the first channel to append to it.
 */
public class BoneTrackFactory {
	/**
	 * Create a bone track from the first channel to append to the bone track.
	 * @param channel the channel to be appended to the bone track.
	 * @return The empty bone track.
	 */
	public BoneTrack createBoneTrack(CollidaChannel channel) {
		switch(channel.getTargetTransform()) {
			case "transform":
				return new MatrixBoneTrack(channel.getTargetBone());
			default:
				return new EulerBoneTrack(channel.getTargetBone());
		}
	}
}
