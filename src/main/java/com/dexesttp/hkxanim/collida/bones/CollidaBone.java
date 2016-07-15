package com.dexesttp.hkxanim.collida.bones;

import java.util.OptionalInt;

import org.w3c.dom.Element;

import com.dexesttp.hkxanim.havok.components.HKXTransform;

public class CollidaBone {
	public static final int noParentIdentifier = 65535;
	
	protected final String name;
	protected final HKXTransform transform;
	protected OptionalInt parentID = OptionalInt.empty();
	
	public static CollidaBone fromCollidaNode(Element element) {
		String name = element.getAttribute("name");
		HKXTransform transform = CollidaBoneUtilities.createTransformFromNode(element);
		return new CollidaBone(name, transform);
	}
	
	public CollidaBone(String name, HKXTransform transform) {
		this.name = name;
		this.transform = transform;
	}
	
	public void setParentID(int id) {
		this.parentID = OptionalInt.of(id);
	}
	
	public int getParentID() {
		return this.parentID.orElse(noParentIdentifier);
	}
	
	public String getName() {
		return this.name;
	}
	
	public HKXTransform getTransform() {
		return this.transform;
	}
	
	public boolean isTranslationLocked() {
		return this.parentID.isPresent();
	}
}