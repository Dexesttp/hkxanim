package com.dexesttp.hkxanim.collida.channel;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexesttp.hkxanim.collida.exceptions.SamplerNotFoundException;

/**
 * Represents a Channel, a way to link a Sampler to a bone transform.
 */
public class CollidaChannel {
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	private final String source;
	private final String targetBone;
	private final String targetTransform;
	
	/**
	 * Create a new CollidaChannel element from a Channel element.
	 * @param element
	 */
	public CollidaChannel(final Element element) {
		this.source = element.getAttribute("source").substring(1);
		String target = element.getAttribute("target");
		this.targetBone = processBone(target.substring(0,  target.indexOf('/')));
		this.targetTransform = target.substring(target.indexOf('/') + 1);
	}

	/**
	 * Differences between a 3DsMax bone name (preprended with "node-") and a Blender bone name.
	 * @param boneString the given target bone string.
	 * @return the actual target bone name.
	 */
	private String processBone(final String boneString) {
		if(boneString.startsWith("node-"))
			return boneString.substring(5);
		return boneString;
	}
	
	public String getTargetBone() {
		return this.targetBone;
	}

	public String getTargetTransform() {
		return this.targetTransform;
	}
	
	/**
	 * Returns the sampler {@link Element} from the {@link Document}.
	 * @param document the document to extract the {@link Element} from.
	 * @return the sampler {@link Element}.
	 * @throws SamplerNotFoundException if the element wasn't found.
	 * @throws XPathExpressionException I don't know why, I guess if the XPath parsing fails ?
	 */
	public Element getSampler(final Document document) throws SamplerNotFoundException, XPathExpressionException {
		Node sampler = (Node) xpath.evaluate("//sampler[@id='" + this.source + "']", document, XPathConstants.NODE);
		if(sampler == null || sampler.getNodeType() != Node.ELEMENT_NODE)
			throw new SamplerNotFoundException(this.source);
		return (Element) sampler;
	}
}
