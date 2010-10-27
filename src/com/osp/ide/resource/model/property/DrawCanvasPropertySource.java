package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.ui.views.properties.IPropertyDescriptor;

import com.osp.ide.resource.model.DrawCanvas;

public class DrawCanvasPropertySource extends OspNodePropertySource {

	public DrawCanvasPropertySource(DrawCanvas node) {
		this.node = node;

	}

	protected void initDescriptor() {
		properties = new ArrayList<IPropertyDescriptor>();

		return;
	}

}
