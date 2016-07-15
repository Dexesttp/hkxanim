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
public class MatrixAccessibleArray implements AccessibleArray<Matrix> {
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
				.mapToDouble(Double::parseDouble)
				.iterator();
		while(contentsIterator.hasNext()) {
			contents.add(Matrix.fromLineFirstIterator(contentsIterator));
		}
	}
	
	public Matrix get(int i) {
		return contents.get(i);
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
		for(Matrix matrix : contents) {
			System.out.print(" // ");
			System.out.print(matrix.toString());
		}
		System.out.println(" // ");
	}
}
