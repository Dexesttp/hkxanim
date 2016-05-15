package com.dexesttp.hkxanim.collida.arrays;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dexesttp.hkxanim.collida.exceptions.UnhandledAccessibleArray;
import com.dexesttp.hkxanim.processing.interpolable.InterpolableDouble;
import com.dexesttp.hkxanim.processing.matrix.Matrix;

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
	 * Creates a new general {@link AccessibleArray} from the array's ID.
	 * @param arrayID the array's ID.
	 * @return a new {@link AccessibleArray} with the relevant data inside.
	 * @throws DOMException if there was a problem while reading the DOM.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen).
	 * @throws UnhandledAccessibleArray if the array given is of an unknown type.
	 */
	public AccessibleArray<?> getByID(final String arrayID) throws DOMException, XPathExpressionException, UnhandledAccessibleArray {
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
	
	/**
	 * Requests specifically a Time array.
	 * @param arrayID the id of the Time array.
	 * @return the {@link FloatAccessibleArray} filled with the Time values.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen)
	 */
	public FloatAccessibleArray getTimeArrayByID(final String arrayID) throws XPathExpressionException {
		return new FloatAccessibleArray(getFloatArrayElement(arrayID));
	}

	/**
	 * Requests specifically a Tangents array. Can be empty, but will always be a Double array.
	 * @param arrayID the ID of the array.
	 * @return the relevant Tangents {@link AccessibleArray}.
	 * @throws XPathExpressionException
	 * @throws UnhandledAccessibleArray
	 */
	public AccessibleArray<Double> getTangentsByID(final String arrayID) throws XPathExpressionException, UnhandledAccessibleArray {
		String type = getAccessibleArrayType(arrayID);
		switch(type) {
			case "":
				return new EmptyAccessibleArray();
			case "float":
			case "angle":
				return new FloatAccessibleArray(getFloatArrayElement(arrayID));
		}
		throw new UnhandledAccessibleArray(type);
	}

	/**
	 * Requests specifically an {@link InterpolableDouble} array.
	 * @param arrayID the id of the array.
	 * @return the {@link InterpolableDoubleAccessibleArray} filled with the values.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen)
	 */
	public InterpolableDoubleAccessibleArray getValuesArrayByID(final String arrayID) throws XPathExpressionException, UnhandledAccessibleArray {
		String type = getAccessibleArrayType(arrayID);
		switch(type) {
			case "float":
			case "angle":
				return new InterpolableDoubleAccessibleArray(getFloatArrayElement(arrayID));
		}
		throw new UnhandledAccessibleArray(type);
	}

	/**
	 * Requests specifically a {@link Matrix} array.
	 * @param arrayID the id of the array.
	 * @return the {@link MatrixAccessibleArray} filled with the values.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen)
	 */
	public MatrixAccessibleArray getMatrixArrayByID(final String arrayID) throws XPathExpressionException, UnhandledAccessibleArray {
		String type = getAccessibleArrayType(arrayID);
		switch(type) {
			case "float4x4":
				return new MatrixAccessibleArray(getFloatArrayElement(arrayID));
		}
		throw new UnhandledAccessibleArray(type);
	}

	/**
	 * Requests specifically an Interpolations array.
	 * @param arrayID the id of the array.
	 * @return the {@link AccessibleArray<String>} filled with the values.
	 * @throws XPathExpressionException if the expression was wrong (shouldn't happen)
	 */
	public AccessibleArray<String> getInterpolationsByID(final String arrayID) throws XPathExpressionException {
		return new NameAccessibleArray(getNameArrayElement(arrayID));
	}
}
