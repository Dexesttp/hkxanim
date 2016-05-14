package com.dexesttp.hkxanim.collida.channel;

import java.util.Iterator;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Iterator over all available CollidaChannels.
 */
class CollidaChannelIterator implements Iterator<CollidaChannel> {
	private final NodeList nodeList;
	private transient int index;

	public CollidaChannelIterator(final NodeList nodeList) {
		this.nodeList = nodeList;
		this.index = 0;
		prepareNext();
	}
	
	/**
	 * Preapre the next element, or set an invalid position of there's no next element.
	 */
	private void prepareNext() {
		while(index < nodeList.getLength()) {
			if(nodeList.item(index).getNodeType() == Node.ELEMENT_NODE)
				break;
			index++;
		}
	}

	@Override
	public boolean hasNext() {
		return index < nodeList.getLength();
	}
	
	@Override
	public CollidaChannel next() {
		Node next = nodeList.item(index);
		if(next == null)
			return null;
		index++;
		prepareNext();
		return new CollidaChannel((Element) next);
	}
}