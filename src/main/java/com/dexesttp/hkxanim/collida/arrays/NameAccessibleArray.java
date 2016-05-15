package com.dexesttp.hkxanim.collida.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Element;

/**
 * Represents an array of {@link String}, as names.
 */
public class NameAccessibleArray implements AccessibleArray<String> {
	private transient List<String> contents = new ArrayList<>();
	
	/**
	 * Create a new {@link NameAccessibleArray}.
	 * @param nameArrayElement the {@link Element} to get the data from.
	 */
	public NameAccessibleArray(final Element nameArrayElement) {
		contents.addAll(Arrays.asList(nameArrayElement.getTextContent().split(" ")));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String get(int index) {
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
