package com.dexesttp.hkxanim.collida.sampler;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.arrays.AccessibleArrayFactory;
import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;

/**
 * Retrieve a Collida sampler from its Element.
 * <br />
 * A Collida sampler is defined by a "sampler" tag with some elements inside :<br />
 * - an INPUT (the times)<br />
 * - an OUTPUT (the values for each time)<br />
 * - an INTERPOLATION method<br />
 * - an IN_TANGENT (optional) which is the first tangent for the interpolation method<br />
 * - an OUT_TANGENT (optional) which is the last tangent for the interpolation method<br />
 * These elements are available under "input" tags, with a "semantic" attribute having one
 * of the above values and a "source" attribute having the ID of the node describing the contents of the tag.
 */
public class CollidaSamplerFactory {
	private static XPath xPath = XPathFactory.newInstance().newXPath();
	private final AccessibleArrayFactory aaFactory;
	
	public CollidaSamplerFactory(Document document) {
		this.aaFactory = new AccessibleArrayFactory(document);
	}
	
	private String getNodeID(String property, Element root) throws XPathExpressionException {
		String samplerID = root.getAttribute("id");
		String extractedString = (String) xPath
				.evaluate("//sampler[@id='" + samplerID + "']/input[@semantic='" + property + "']/@source", root, XPathConstants.STRING);
		if(extractedString == null || extractedString.isEmpty())
			return "";
		return extractedString.substring(1);
	}
	
	public FloatCollidaSampler createFloatSampler(String type, Element element) throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		String inputNodeName = getNodeID("INPUT", element);
		String outputNodeName = getNodeID("OUTPUT", element);
		String interpolationNodeName = getNodeID("INTERPOLATION", element);
		String inTangentNodeName = getNodeID("IN_TANGENT", element);
		String outTangentNodeName = getNodeID("OUT_TANGENT", element);
		return new FloatCollidaSampler(
			aaFactory.getTimeArrayByID(inputNodeName),
			aaFactory.getValuesArrayByID(outputNodeName),
			aaFactory.getInterpolationsByID(interpolationNodeName),
			aaFactory.getTangentsByID(inTangentNodeName),
			aaFactory.getTangentsByID(outTangentNodeName));
	}
	
	public MatrixCollidaSampler createMatrixSampler(String type, Element element) throws XPathExpressionException, DOMException, UnhandledAccessibleArray {
		String inputNodeName = getNodeID("INPUT", element);
		String outputNodeName = getNodeID("OUTPUT", element);
		String interpolationNodeName = getNodeID("INTERPOLATION", element);
		String inTangentNodeName = getNodeID("IN_TANGENT", element);
		String outTangentNodeName = getNodeID("OUT_TANGENT", element);
		return new MatrixCollidaSampler(
			aaFactory.getTimeArrayByID(inputNodeName),
			aaFactory.getMatrixArrayByID(outputNodeName),
			aaFactory.getInterpolationsByID(interpolationNodeName),
			aaFactory.getTangentsByID(inTangentNodeName),
			aaFactory.getTangentsByID(outTangentNodeName));
	}
}
