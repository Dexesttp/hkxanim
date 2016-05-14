package com.dexesttp.hkxanim.collida.sampler;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;

import com.dexesttp.hkxanim.collida.arrays.AccessibleArrayFactory;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;

/**
 * Retrieve and handle a Collida sampler<br />
 */
public class CollidaSampler {
	private final String inputNodeName;
	private final String outputNodeName;
	private final String interpolationNodeName;
	private final String inCurveNodeName;
	private final String outCurveNodeName;

	public CollidaSampler(String inputNodeName, String outputNodeName, String interpolationNodeName, String inCurveNodeName, String outCurveNodeName) {
		this.inputNodeName = inputNodeName;
		this.outputNodeName = outputNodeName;
		this.interpolationNodeName = interpolationNodeName;
		this.inCurveNodeName = inCurveNodeName;
		this.outCurveNodeName = outCurveNodeName;
	}
	
	public void getData(Document document) throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		AccessibleArrayFactory aaFactory = new AccessibleArrayFactory(document);
		aaFactory.getByID(inputNodeName).display();
		aaFactory.getByID(outputNodeName).display();
		aaFactory.getByID(interpolationNodeName).display();
		aaFactory.getByID(inCurveNodeName).display();
		aaFactory.getByID(outCurveNodeName).display();
	}
}
