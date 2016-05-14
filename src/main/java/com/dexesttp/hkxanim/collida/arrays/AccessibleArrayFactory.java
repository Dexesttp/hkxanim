package com.dexesttp.hkxanim.collida.arrays;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;

/**
 * Creates an Accessible Array from a source element.
 */
public class AccessibleArrayFactory {
	private final transient  Document document;
	private static final XPath xPath = XPathFactory.newInstance().newXPath();

	public AccessibleArrayFactory(final Document document) {
		this.document = document;
	}

	private String getAccessibleArrayType(final String arrayID) throws XPathExpressionException {
		return (String) xPath.evaluate("//source[@id='" + arrayID + "']/technique_common/accessor/param/@type", document, XPathConstants.STRING);
	}
	
	private Element getFloatArrayElement(final String arrayID) throws XPathExpressionException {
		return (Element) xPath.evaluate("//source[@id='" + arrayID + "']/float_array", document, XPathConstants.NODE);
	}

	private Element getNameArrayElement(final String arrayID) throws XPathExpressionException {
		return (Element) xPath.evaluate("//source[@id='" + arrayID + "']/Name_array", document, XPathConstants.NODE);
	}
	
	/**
	 * Creates a new {@link AccessibleArray} from the array's ID.
	 * @param arrayID the array's ID.
	 * @return a new {@link AccessibleArray} with the relevant data inside.
	 * @throws DOMException if there was a problem while reading the DOM.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen).
	 * @throws UnhandledAccessibleArray if the array given is of an unknown type.
	 */
	public AccessibleArray getByID(final String arrayID) throws DOMException, XPathExpressionException, UnhandledAccessibleArray {
		String type = getAccessibleArrayType(arrayID);
		switch(type) {
			case "":
				return new EmptyAccessibleArray();
			case "float":
			case "angle":
				return new FloatAccessibleArray(getFloatArrayElement(arrayID));
			case "name":
				return new NameAccessibleArray(getNameArrayElement(arrayID));
			case "float4x4":
				return new MatrixAccessibleArray(getFloatArrayElement(arrayID));
		}
		throw new UnhandledAccessibleArray(type);
	}
}
