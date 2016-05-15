package com.dexesttp.hkxanim.collida.arrays;

/**
 * When the data isn't there, create this element.
 */
public class EmptyAccessibleArray implements AccessibleArray<Double> {
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Double get(int index) {
		return new Double(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display() {
		System.out.println("No contents");
	}
}
