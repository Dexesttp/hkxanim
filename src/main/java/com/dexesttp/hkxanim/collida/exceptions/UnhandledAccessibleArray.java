package com.dexesttp.hkxanim.collida.exceptions;

/**
 * Thrown when an unknown array type is given.
 */
public class UnhandledAccessibleArray extends Exception {
	/** Generated UUID */
	private static final long serialVersionUID = 5941551940093733219L;
	
	/**
	 * {@inheritDoc}
	 */
	public UnhandledAccessibleArray(final String type) {
		super(String.format("The following type : %s is unhandled for an animation array format.", type));
	}
}
