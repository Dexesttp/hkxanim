package com.dexesttp.hkxanim;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.dexesttp.hkxanim.collida.CollidaReader;
import com.dexesttp.hkxanim.collida.exceptions.AnimationNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
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
		File blenderFileStart = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-Blender.dae");
		File blenderFileNew = new File("D:\\SANDBOX\\FO4\\animStudy\\TestAnimation01.dae");
		File blenderFileLeito = new File("D:\\SANDBOX\\FO4\\animStudy\\animation_v01_01.dae");
		File maxFile = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-3dsmax.DAE");
		
		File outputFile = new File("test.hkx");
		CollidaReader collidaReader = new CollidaReader(blenderFileLeito);
		try {
			ByteBuffer hkxByteBuffer = openStreamToByteBuffer("/files/FullHumanBody.hkx");
			HKXEnumResolver enumResolver = new HKXEnumResolver();
			HKXDescriptorFactory descriptorFactory = new HKXDescriptorFactory(enumResolver);
			HKXReader hkxReader = new HKXReader(hkxByteBuffer, descriptorFactory, enumResolver);
			HKXFile file = hkxReader.read();
			collidaReader.fill(file);
			HKXWriter hkxWriter = new HKXWriter(outputFile, enumResolver);
			hkxWriter.write(file);
		} catch ( SAXException
				| IOException
				| ParserConfigurationException
				| AnimationNotFoundException
				| SamplerNotFoundException
				| XPathExpressionException
				| DOMException
				| UnhandledAccessibleArray
				| InvalidPositionException
				| UnsupportedVersionError e) {
			// Gotta catch 'em all !
			e.printStackTrace();
		}
	}

	private static ByteBuffer openStreamToByteBuffer(String resource) throws IOException {
		InputStream hkxFileStream = TestInterface.class.getResourceAsStream(resource);
		ByteBuffer buffer = ByteBuffer.allocate(1000000);
		byte[] data = new byte[1024];
		int readBytes;
		while((readBytes = hkxFileStream.read(data)) != -1) {
			buffer.put(data, 0, readBytes);
		}
		return buffer;
	}
}
