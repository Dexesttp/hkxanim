package com.dexesttp.hkxanim.collida.arrays;

/**
 * When the data isn't there, create this element.
 */
public class EmptyAccessibleArray implements AccessibleArray {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display() {
		System.out.println("No contents");
	}
}
