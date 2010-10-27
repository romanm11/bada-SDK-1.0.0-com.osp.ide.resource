package com.osp.ide.resource.editform;

import java.util.Vector;

public class InterfaceList {
	String name = "";
	String base_class = "";
	Vector<String> Interface = new Vector<String>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBaseClass() {
		return base_class;
	}
	
	public void setBaseClass(String baseClass) {
		base_class = baseClass;
	}
	
	public Vector<String> getInterface() {
		return Interface;
	}
}


