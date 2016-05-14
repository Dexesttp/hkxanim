package com.dexesttp.hkxanim.collida.exceptions;

/**
 * Thrown when a specific Sampler was not found.
 */
public class SamplerNotFoundException extends Exception {
	private static final long serialVersionUID = 1461634351270314292L;
	
	/**
	 * {@inheritDoc}
	 */
	public SamplerNotFoundException(final String source) {
		super("Sampler not found : " + source);
	}
}
