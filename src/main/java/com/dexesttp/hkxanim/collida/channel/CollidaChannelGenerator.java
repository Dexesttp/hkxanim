package com.dexesttp.hkxanim.collida.channel;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Generate {@link CollidaChannel}.
 */
public class CollidaChannelGenerator implements Iterable<CollidaChannel> {
	private final Element root;
	
	/**
	 * Create a new {@link CollidaChannel} supplier from a parent {@link Element}.
	 * @param root
	 */
	public CollidaChannelGenerator(final Element root) {
		this.root = root;
	}

	@Override
	public Iterator<CollidaChannel> iterator() {
		NodeList nodeList = root.getElementsByTagName("channel");
		return new CollidaChannelIterator(nodeList);
	}
}
