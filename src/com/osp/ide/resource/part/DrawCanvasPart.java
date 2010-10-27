package com.osp.ide.resource.part;

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.osp.ide.resource.editpolicies.EditLayoutPolicy;
import com.osp.ide.resource.figure.DrawCanvasFigure;
import com.osp.ide.resource.model.DrawCanvas;
import com.osp.ide.resource.model.FrameNode;

public class DrawCanvasPart extends AbstractGraphicalEditPart {

	@Override
	protected IFigure createFigure() {
		IFigure figure = new DrawCanvasFigure(this);
		return figure;
	}

	@Override
	protected void createEditPolicies() {
        installEditPolicy(EditPolicy.LAYOUT_ROLE,
            new EditLayoutPolicy());
	}

	public List<FrameNode> getModelChildren() {
		return ((DrawCanvas) getModel()).getChildrenArray();

	}

}
