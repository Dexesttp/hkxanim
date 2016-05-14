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

public class TestInterface {
	@SuppressWarnings("unused")
	public static void main(final String... args) {
		File blenderFileStart = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-Blender.dae");
		File blenderFileNew = new File("D:\\SANDBOX\\FO4\\animStudy\\TestAnimation01.dae");
		File blenderFileLeito = new File("D:\\SANDBOX\\FO4\\animStudy\\animation_v01_01.dae");
		File maxFile = new File("D:\\SANDBOX\\FO4\\animStudy\\FO4Animation-3dsmax.DAE");
		CollidaReader reader = new CollidaReader(blenderFileLeito);
		try {
			reader.read();
		} catch (SAXException | IOException | ParserConfigurationException | AnimationNotFoundException | SamplerNotFoundException | XPathExpressionException | DOMException | UnhandledAccessibleArray e) {
			// Gotta catch 'em all !
			e.printStackTrace();
		}
	}
}
