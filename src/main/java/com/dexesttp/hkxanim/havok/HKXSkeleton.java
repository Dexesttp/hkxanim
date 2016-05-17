package com.dexesttp.hkxanim.havok;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexesttp.hkxpack.data.HKXData;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.data.HKXObject;
import com.dexesttp.hkxpack.data.members.HKXArrayMember;
import com.dexesttp.hkxpack.data.members.HKXDirectMember;
import com.dexesttp.hkxpack.data.members.HKXStringMember;

public class HKXSkeleton {
	private Map<String, HKXTransform> boneMap;
	
	public static HKXSkeleton fromHKXFile(HKXFile file) throws NoSkeletonFoundException {
		return fromHKXFile(file, "Root");
	}
	
	@SuppressWarnings("unchecked")
	public static HKXSkeleton fromHKXFile(HKXFile skeletonFile, String prioritizedName) throws NoSkeletonFoundException {
		HKXSkeleton result = new HKXSkeleton();
		
		// Select the right skeleton node
		HKXObject hkxSkeleton = null;
		for(HKXObject hkxObject : skeletonFile.getContentCollection()) {
			if(hkxObject.getDescriptor().getName().equals("hkaSkeleton")) {
				// Prioritize the given name.
				// Second member (id 1) is the skeleton name.
				if(((HKXStringMember) hkxObject.getMembersList().get(1)).get().equals(prioritizedName ) || hkxSkeleton == null)
					hkxSkeleton = hkxObject;
			}
		}
		
		// Check if the skeleton was found.
		if(hkxSkeleton == null)
			throw new NoSkeletonFoundException();
		
		// Fill the Skeleton witht he retrieved data.
		// The bone names are the fourth (id=3) member.
		List<HKXData> boneNames = ((HKXArrayMember) hkxSkeleton.getMembersList().get(3)).getContentsList();
		// The bone positions are the fifth (id=4) member.
		List<HKXData>bonePoses = ((HKXArrayMember) hkxSkeleton.getMembersList().get(4)).getContentsList();
		
		// Casting fiesta.
		for(int i = 0; i < boneNames.size(); i++) {
			result.boneMap.put(
					((HKXStringMember) ((HKXObject) boneNames.get(i)).getMembersList().get(0)).get(),
					HKXTransform.fromHKX((HKXDirectMember<Double[]>) bonePoses.get(i))
				);
		}
		
		return result;
	}
	
	private HKXSkeleton() {
		boneMap = new HashMap<>();
	}

}
