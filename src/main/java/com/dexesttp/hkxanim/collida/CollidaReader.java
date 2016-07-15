package com.dexesttp.hkxanim.collida;

import java.io.File;
import java.io.IOException;
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

import com.dexesttp.hkxanim.collida.bones.CollidaBone;
import com.dexesttp.hkxanim.collida.bones.CollidaSkeletonHandler;
import com.dexesttp.hkxanim.collida.channel.CollidaChannel;
import com.dexesttp.hkxanim.collida.channel.CollidaChannelGenerator;
import com.dexesttp.hkxanim.collida.exceptions.AnimationNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SkeletonNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.collida.track.BoneTrackOrganizer;
import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxanim.havok.components.HKXAnimationFactory;
import com.dexesttp.hkxpack.data.HKXFile;

public class CollidaReader {
	private final File file;

	/**
	 * Create a new Collida reader.
	 * @param file the input file.
	 */
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
		for(int i = 0; i < rootList.getLength(); i++) {
			if(rootList.item(i).getNodeType() == Node.ELEMENT_NODE)
				return (Element) rootList.item(i);
		}
		throw new AnimationNotFoundException();
	}

	private Element getSkeletonRoot(final Document document) throws SkeletonNotFoundException {
		NodeList rootList = document.getElementsByTagName("visual_scene");
		Element sceneNode = null;
		for(int i = 0; i < rootList.getLength(); i++) {
			if(rootList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				sceneNode = (Element) rootList.item(i);
				break;
			}
		}
		if(sceneNode == null)
			throw new SkeletonNotFoundException();
		NodeList rootBoneList = sceneNode.getChildNodes();
		for(int i = 0; i < rootBoneList.getLength(); i++) {
			if(rootBoneList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = ((Element) rootBoneList.item(i)).getAttribute("name");
				if(nodeName.equals("Armature"))
					return (Element) rootBoneList.item(i);
			}
		}
		throw new SkeletonNotFoundException();
	}
	
	/**
	 * Read the contents of the Collida file into an HKXFile.
	 * @param skeleton the {@link HKXSkeleton} to base on.
	 * @return theanimation {@link HKXFile}.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws AnimationNotFoundException
	 * @throws SamplerNotFoundException 
	 * @throws XPathExpressionException 
	 * @throws UnhandledAccessibleArray 
	 * @throws DOMException 
	 * @throws SkeletonNotFoundException 
	 * @throws NoSkeletonFoundException 
	 */
	public HKXAnimationContainer fill()
			throws IOException, UnhandledAccessibleArray,
			AnimationNotFoundException, SkeletonNotFoundException, SamplerNotFoundException  {
		try {
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
			
			// Display intro message
			System.out.println("Creating bone list...");
			
			// Get the bone list
			Element rootBone = getSkeletonRoot(document);
			CollidaSkeletonHandler skeletonHandler = new CollidaSkeletonHandler();
			skeletonHandler.initialize(rootBone);
			List<CollidaBone> boneList = skeletonHandler.buildBoneList(boneTracks.getBoneNames());

			// Fill the HKX file.
			HKXAnimationContainer animationContainer = new HKXAnimationFactory().fromTracks(boneList, boneTracks);
			animationContainer.setCollidaBones(boneList);
			
			// Display end message
			System.out.println("HKX file contents created.");
			
			return animationContainer;
		} catch(DOMException | SAXException | ParserConfigurationException e) {
			throw new IOException("There was an error while parsing the XML file :", e);
		} catch (XPathExpressionException e) {
			throw new RuntimeException("There was an error in the XPath expression used to parse the Collida file : ", e);
		}
	}
}
