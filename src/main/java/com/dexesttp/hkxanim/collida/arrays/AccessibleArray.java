package com.dexesttp.hkxanim.collida.arrays;

/**
 * The contents of a COLLIDA array.
 */
public interface AccessibleArray<T> {
	/**
	 * Get the data at a specific position.
	 * @throws IndexOutOfBoundsException if the requested index was illegal
	 */
	public T get(int index);
	
	/**
	 * Get the size of this {@link AccessibleArray}.
	 * @return the size.
	 */
	public int size();
	
	/**
	 * Temporary method to display extracted data.
	 */
	public void display();
}
