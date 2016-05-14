package com.dexesttp.hkxanim.collida.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator.OfDouble;

import org.w3c.dom.Element;

import com.dexesttp.hkxanim.processing.matrix.Matrix;

/**
 * An array of {@link Matrix} elements. Matrices wouldn't allow em to have a link.
 */
public class MatrixAccessibleArray implements AccessibleArray {
	private transient List<Matrix> contents = new ArrayList<>();
	
	/**
	 * Create a new {@link MatrixAccessibleArray}.
	 * @param floatArrayElement the DOM {@link Element} with the values inside.
	 */
	public MatrixAccessibleArray(final Element floatArrayElement) {
		OfDouble contentsIterator = Arrays
				.stream(floatArrayElement
						.getTextContent()
						.split(" "))
				.mapToDouble((String element) -> { return Double.parseDouble(element);})
				.iterator();
		while(contentsIterator.hasNext()) {
			contents.add(new Matrix(new double[][]{
				{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
				{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
				{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
				{contentsIterator.next(), contentsIterator.next(), contentsIterator.next(), contentsIterator.next()},
			}));
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
