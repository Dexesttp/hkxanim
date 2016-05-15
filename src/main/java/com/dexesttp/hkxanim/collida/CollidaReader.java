package com.dexesttp.hkxanim.collida;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dexesttp.hkxanim.collida.channel.CollidaChannel;
import com.dexesttp.hkxanim.collida.channel.CollidaChannelGenerator;
import com.dexesttp.hkxanim.collida.exceptions.AnimationNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
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

public class CollidaReader {
	private final File file;

	public CollidaReader(final File file) {
		this.file = file;
	}

	private Document getDocument() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		return builder.parse(this.file);
	}

	private Element getAnimationRoot(final Document document) throws AnimationNotFoundException {
		NodeList rootList = document.getElementsByTagName("library_animations");
		for(int i = 0; i < rootList.getLength(); i++) {;
			if(rootList.item(i).getNodeType() == Node.ELEMENT_NODE)
				return (Element) rootList.item(i);
		}
		throw new AnimationNotFoundException();
	}
	
	/**
	 * Read the contents of the Collida file into an HKXFile.
	 * @param hkxFile the {@link HKXFile} to fill.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws AnimationNotFoundException
	 * @throws SamplerNotFoundException 
	 * @throws XPathExpressionException 
	 * @throws UnhandledAccessibleArray 
	 * @throws DOMException 
	 */
	public void fill(HKXFile hkxFile) throws SAXException, IOException, ParserConfigurationException, AnimationNotFoundException, SamplerNotFoundException, XPathExpressionException, DOMException, UnhandledAccessibleArray {
		Document document = getDocument();
		document.normalizeDocument();
		Element root = getAnimationRoot(document);
		// Display intro message
		System.out.println("Loading animation data...");
		// Get the bone tracks
		BoneTrackOrganizer boneTracks = new BoneTrackOrganizer(document);
		CollidaChannelGenerator channelGenerator = new CollidaChannelGenerator(root);
		for(CollidaChannel channel : channelGenerator) {
			boneTracks.addChannel(channel);
		}
		
		// Display outro message
		boneTracks.displayInfos();

		// Fill the HKX file.
		fillFile(hkxFile, boneTracks);
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

	private void displayInfo(int size) {
		System.out.println("Written animation file...");
		System.out.println("Bones written : " + size);
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
		
		displayInfo(boneList.size());
	}
}
