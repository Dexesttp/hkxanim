package com.dexesttp.hkxanim;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.dexesttp.hkxanim.collida.CollidaReader;
import com.dexesttp.hkxanim.collida.exceptions.AnimationNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SkeletonNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.havok.components.HKXAnimationContainer;
import com.dexesttp.hkxanim.havok.file.HKXAnimationFileFactory;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;
import com.dexesttp.hkxpack.tagreader.TagXMLReader;
import com.dexesttp.hkxpack.tagreader.exceptions.InvalidTagXMLException;

public class TestInterface {
	protected static String blenderFileStart = "D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-Blender.dae";
	protected static String blenderFileNew = "D:\\SANDBOX\\FO4\\animStudy\\TestAnimation01.dae";
	protected static String blenderFileLeito = "D:\\SANDBOX\\FO4\\animStudy\\animation_v01_01.dae";
	protected static String maxFile = "D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-3dsmax.DAE";
	
	public static void main(final String... args) {
		String fileName = blenderFileLeito;
		if(args.length > 1) {
			fileName = args[0];
		}
		
		// Necessary files
		File inputFile = new File(fileName);
		File outputFile = new File("test.hkx");
		
		try {
			// skeleton file reader
			HKXEnumResolver enumResolver = new HKXEnumResolver();
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
			
			// Collida reader
			CollidaReader collidaReader = new CollidaReader(inputFile);
			// Create animation from Collida file + skeleton
			HKXAnimationContainer animationContainer = collidaReader.fill();
			
			// Read animation template
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			InputStream templateStream = TestInterface.class.getResourceAsStream("/files/animationTemplate.xml");
			Document templateDocument = builder.parse(templateStream);
			TagXMLReader xmlReader = new TagXMLReader(templateDocument, descriptorFactory);
			HKXFile animationTemplate = xmlReader.read();

			// Write animation container to template
			HKXAnimationFileFactory animationFileFactory = new HKXAnimationFileFactory(descriptorFactory);
			HKXFile animOutput =  animationFileFactory.createFile(animationTemplate, animationContainer);
			
			// Write the animation file as HKX file.
			HKXWriter hkxWriter = new HKXWriter(outputFile, enumResolver);
			hkxWriter.write(animOutput);
		} catch (IOException
				| AnimationNotFoundException
				| SkeletonNotFoundException
				| SamplerNotFoundException
				| UnhandledAccessibleArray
				| UnsupportedVersionError
				| InvalidTagXMLException e) {
			// Gotta catch 'em all !
			e.printStackTrace();
		} catch (SAXException
				| ParserConfigurationException e) {
			throw new RuntimeException("There was an error parsing the template output file", e);
		}
	}
}
