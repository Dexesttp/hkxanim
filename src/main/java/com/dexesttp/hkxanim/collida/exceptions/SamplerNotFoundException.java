package com.dexesttp.hkxanim.collida.exceptions;

/**
 * Thrown when a specific Sampler was not found.
 */
public class SamplerNotFoundException extends Exception {
	/** Generated UUID */
	private static final long serialVersionUID = 1461634351270314292L;
	
	/**
	 * {@inheritDoc}
	 */
	public SamplerNotFoundException(final String source) {
		super("The following animation sampler wasn't found in the Collida file : " + source);
	}
}
