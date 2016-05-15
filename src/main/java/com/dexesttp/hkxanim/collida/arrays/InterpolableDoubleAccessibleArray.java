package com.dexesttp.hkxanim.collida.arrays;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.dexesttp.hkxanim.processing.interpolable.InterpolableDouble;

public class InterpolableDoubleAccessibleArray implements AccessibleArray<InterpolableDouble> {
	private transient List<InterpolableDouble> contents = new ArrayList<>();

	/**
	 * Creates a new {@link FloatAccessibleArray}
	 * @param floatArrayElement the {@link Element} that contains the values.
	 */
	public InterpolableDoubleAccessibleArray(final Element floatArrayElement) {
		String[] elementsAsStrings = floatArrayElement.getTextContent().split(" ");
		for(String element : elementsAsStrings) {
			contents.add(new InterpolableDouble(Double.parseDouble(element)));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InterpolableDouble get(int index) {
		return contents.get(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return contents.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display() {
		System.out.println(contents);
	}
}
