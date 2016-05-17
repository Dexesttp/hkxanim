package com.dexesttp.hkxanim;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.dexesttp.hkxanim.collida.CollidaReader;
import com.dexesttp.hkxanim.collida.exceptions.AnimationNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.havok.HKXSkeleton;
import com.dexesttp.hkxanim.havok.NoSkeletonFoundException;
import com.dexesttp.hkxpack.data.HKXFile;
import com.dexesttp.hkxpack.descriptor.HKXDescriptorFactory;
import com.dexesttp.hkxpack.descriptor.HKXEnumResolver;
import com.dexesttp.hkxpack.hkx.exceptions.InvalidPositionException;
import com.dexesttp.hkxpack.hkx.exceptions.UnsupportedVersionError;
import com.dexesttp.hkxpack.hkxreader.HKXReader;
import com.dexesttp.hkxpack.hkxwriter.HKXWriter;

public class TestInterface {
	@SuppressWarnings("unused")
	public static void main(final String... args) {
		// Tested Collida files
		File blenderFileStart = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-Blender.dae");
		File blenderFileNew = new File("D:\\SANDBOX\\FO4\\animStudy\\TestAnimation01.dae");
		File blenderFileLeito = new File("D:\\SANDBOX\\FO4\\animStudy\\animation_v01_01.dae");
		File maxFile = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-3dsmax.DAE");

		// Necessary files
		File skeletonFile = new File("D:\\SANDBOX\\FO4\\animStudy\\skeleton.hkx");
		File outputFile = new File("test.hkx");
		
		// Collida reader
		CollidaReader collidaReader = new CollidaReader(blenderFileLeito);
		try {
			// Read skeleton file
			HKXEnumResolver enumResolver = new HKXEnumResolver();
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
			HKXReader hkxReader = new HKXReader(skeletonFile, descriptorFactory, enumResolver);
			HKXFile hkxSkeleton = hkxReader.read();
			// Load skeleton-specific data
			HKXSkeleton skeleton = HKXSkeleton.fromHKXFile(hkxSkeleton);
			// Create animation from Collida file + skeleton
			HKXFile collidaFile = collidaReader.fill(skeleton);
			// Write the Collida file back.
			HKXWriter hkxWriter = new HKXWriter(outputFile, enumResolver);
			hkxWriter.write(collidaFile);
		} catch ( SAXException
				| IOException
				| ParserConfigurationException
				| AnimationNotFoundException
				| SamplerNotFoundException
				| XPathExpressionException
				| DOMException
				| UnhandledAccessibleArray
				| InvalidPositionException
				| UnsupportedVersionError
				| NoSkeletonFoundException e) {
			// Gotta catch 'em all !
			e.printStackTrace();
		}
	}
}
