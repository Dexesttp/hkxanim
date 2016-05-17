package com.dexesttp.hkxanim.havok;

import java.util.ArrayList;
import java.util.List;

import com.dexesttp.hkxanim.collida.track.BoneTrack;
import com.dexesttp.hkxanim.collida.track.BoneTrackOrganizer;
import com.dexesttp.hkxanim.processing.quaternion.Quaternion;
import com.dexesttp.hkxanim.processing.vector.Vector3D;
import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;
import com.dexesttp.hkxpack.descriptor.enums.HKXType;

public class HKXAnimationFactory {
	private final HKXSkeleton skeleton;

	public HKXAnimationFactory(HKXSkeleton skeleton) {
		this.skeleton = skeleton;
	}

	public HKXFile fromTracks(BoneTrackOrganizer boneTracks) {
		// TODO everything
		return null;
	}

	@SuppressWarnings("unchecked")
	private void fillFile(HKXFile hkxFile, BoneTrackOrganizer boneTracks) {
		
		// Select the right output node
		HKXObject hkxAnimation = null;
		for(HKXObject hkxObject : hkxFile.getContentCollection()) {
			if(hkxObject.getDescriptor().getName().equals("hkaInterleavedUncompressedAnimation")) {
				hkxAnimation = hkxObject;
			}
		}
		
		// Set the frame times.
		((HKXDirectMember<Double>) hkxAnimation.getMembersList().get(2)).set(boneTracks.getMaxTime());
		
		// Fill the HKXFile with the relevant bone tracks.
		HKXArrayMember hkxTransformArray = (HKXArrayMember) hkxAnimation.getMembersList().get(7);
		List<String> boneList = getBoneList(hkxAnimation);
		for(Double frameTime : boneTracks.getFrameTimes()) {
			for(BoneTrack boneTrack : boneTracks.getTracksFromList(boneList)) {
				hkxTransformArray.add(createMember(
						boneTrack.getTranslationAt(frameTime),
						boneTrack.getRotationAt(frameTime),
						boneTrack.getScalingAt(frameTime)
					));
			}
		}
	}

	/**
	 * Create a new HKX QSTransform from a translation, rotation and scaling component
	 * @param translation the tranlsation {@link Vector3D}.
	 * @param rotation the rotation {@link Quaternion}.
	 * @param scale the scaling {@link Vector3D}.
	 * @return the new {@link HKXMember}
	 */
	private HKXMember createMember(Vector3D translation, Quaternion rotation, Vector3D scale) {
		HKXDirectMember<Double[]> transform = new HKXDirectMember<>("", HKXType.TYPE_QSTRANSFORM);
		transform.set(new Double[]{
				translation.getX(), translation.getY(), translation.getZ(), 0.0,
				rotation.getTheta(), rotation.getQX(), rotation.getQY(), rotation.getQZ(),
				scale.getX(), scale.getY(), scale.getZ(), 0.0,
			});
		return transform;
	}

	private List<String> getBoneList(HKXObject hkxAnimation) {
		List<HKXData> trackList = ((HKXArrayMember) hkxAnimation.getMembersList().get(6)).getContentsList();
		List<String> result = new ArrayList<>();
		for(HKXData tracksData : trackList) {
			String boneName = ((HKXStringMember) ((HKXObject) tracksData).getMembersList().get(0)).get();
			result.add(boneName);
		}
		return result;
	}

}
