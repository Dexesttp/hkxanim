package com.dexesttp.hkxanim.collida;

import java.io.File;
import java.io.IOException;

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
import com.dexesttp.hkxanim.collida.track.BoneTrackOrganizer;
import com.dexesttp.hkxanim.havok.HKXAnimationFactory;
import com.dexesttp.hkxanim.havok.HKXSkeleton;
import com.dexesttp.hkxanim.havok.NoSkeletonFoundException;
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
		for(int i = 0; i < rootList.getLength(); i++) {;
			if(rootList.item(i).getNodeType() == Node.ELEMENT_NODE)
				return (Element) rootList.item(i);
		}
		throw new AnimationNotFoundException();
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
	 * @throws NoSkeletonFoundException 
	 */
	public HKXFile fill(HKXSkeleton skeleton) throws SAXException, IOException, ParserConfigurationException, AnimationNotFoundException, SamplerNotFoundException, XPathExpressionException, DOMException, UnhandledAccessibleArray, NoSkeletonFoundException {
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
		HKXFile animationFile = new HKXAnimationFactory(skeleton).fromTracks(boneTracks);
		
		// Display end message
		System.out.println("HKX file created. Exporting...");
		
		return animationFile;
	}
}
