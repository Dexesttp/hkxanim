package com.dexesttp.hkxanim.collida.arrays;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

/**
 * An array of raw floats. They are handled as {@link Double}s.
 */
public class FloatAccessibleArray implements AccessibleArray {
	private transient List<Double> contents = new ArrayList<>();

	/**
	 * Creates a new {@link FloatAccessibleArray}
	 * @param floatArrayElement the {@link Element} that contains the values.
	 */
	public FloatAccessibleArray(final Element floatArrayElement) {
		String[] elementsAsStrings = floatArrayElement.getTextContent().split(" ");
		for(String element : elementsAsStrings) {
			contents.add(Double.parseDouble(element));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display() {
		System.out.println(contents);
	}
}
