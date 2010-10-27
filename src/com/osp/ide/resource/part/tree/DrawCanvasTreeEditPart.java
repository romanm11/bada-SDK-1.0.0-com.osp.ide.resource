package com.osp.ide.resource.part.tree;

import java.beans.PropertyChangeEvent;
import java.util.List;

import com.osp.ide.resource.model.DrawCanvas;
import com.osp.ide.resource.model.FrameNode;

public class DrawCanvasTreeEditPart extends OspAbstractTreeEditPart {


	protected List<FrameNode> getModelChildren() {
		return ((DrawCanvas)getModel()).getChildrenArray();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) refreshChildren();
		if(evt.getPropertyName().equals(FrameNode.PROPERTY_REMOVE)) refreshChildren();
	}
}
