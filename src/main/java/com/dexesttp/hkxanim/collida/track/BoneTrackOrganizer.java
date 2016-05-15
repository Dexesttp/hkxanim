package com.dexesttp.hkxanim.collida.track;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.channel.CollidaChannel;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.sampler.CollidaSamplerFactory;

public class BoneTrackOrganizer {
	private final Document document;
	CollidaSamplerFactory samplerFactory;
	private Map<String, BoneTrack> contents = new HashMap<>();
	private BoneTrackFactory boneTrackFactory = new BoneTrackFactory();
	private double maxTime = -1;

	public BoneTrackOrganizer(Document document) {
		this.document = document;
		this.samplerFactory = new CollidaSamplerFactory(document);
	}

	private BoneTrack getOrCreateBoneTrack(CollidaChannel channel) {
		if(!this.contents.containsKey(channel.getTargetBone()))
			this.contents.put(channel.getTargetBone(), boneTrackFactory .createBoneTrack(channel));
		return this.contents.get(channel.getTargetBone());
	}

	public Optional<BoneTrack> getBoneTrack(String boneName) {
		return Optional.ofNullable(this.contents.get(boneName));
	}
	
	public void addChannel(CollidaChannel channel) throws XPathExpressionException, SamplerNotFoundException, DOMException, UnhandledAccessibleArray {
		BoneTrack track = getOrCreateBoneTrack(channel);
		Element samplerElement = channel.getSampler(document);
		track.addSampler(samplerFactory, channel.getTargetTransform(), samplerElement);
	}
	
	private double createMaxTime() {
		double start = 0;
		for(BoneTrack track : contents.values()) {
			double maxTime = track.maxTime();
			start = start > maxTime ? start : maxTime;
		}
		return start;
	}

	public Double getMaxTime() {
		if(this.maxTime == -1)
			this.maxTime = createMaxTime();
		return maxTime;
	}

	public void displayInfos() {
		System.out.println("Loaded animation tracks...");
		System.out.println("Animation length (s) : " + getMaxTime());
		System.out.println("Detected bone tracks : " + contents.size());
	}
	
	private BoneTrack getOrNone(String name) {
		if(contents.containsKey(name))
			return contents.get(name);
		System.out.println("Not found : " + name);
		return new EmptyBoneTrack(name);
	}

	public Iterable<BoneTrack> getTracksFromList(List<String> boneList) {
		return () -> {
			return boneList
				.stream()
				.map((name) -> getOrNone(name))
				.iterator();
		};
	}

	/**
	 * Get all frame times for this BoneTrackOrganizer.
	 * @return
	 */
	public Iterable<Double> getFrameTimes() {
		int framesCount = (int) Math.nextUp(maxTime * 30) + 2;
		return () -> {
				return DoubleStream.generate(new DoubleSupplier() {
					private int i = 0;
					@Override
					public double getAsDouble() {
						return ((double) i++) / 30;
					}
				})
				.limit(framesCount)
				.iterator();
			};
	}
}
