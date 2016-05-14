package com.dexesttp.hkxanim.collida.exceptions;

/**
 * Thrown when an unknown array type is given.
 */
public class UnhandledAccessibleArray extends Exception {
	private static final long serialVersionUID = 5941551940093733219L;
	
	/**
	 * {@inheritDoc}
	 */
	public UnhandledAccessibleArray(final String type) {
		super(type);
	}
}
