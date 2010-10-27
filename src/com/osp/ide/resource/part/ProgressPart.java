package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.editpolicies.OspDirectEditPolicy;
import com.osp.ide.resource.figure.ProgressFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.resinfo.Layout;

public class ProgressPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		Progress model = (Progress) getModel();
		ProgressFigure figure = new ProgressFigure(model.getParent().getScreen());
		this.figure = figure;
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new OspDirectEditPolicy());

	}

	protected void refreshVisuals() {
		ProgressFigure figure = (ProgressFigure) getFigure();
		Progress model = (Progress) getModel();

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		
		super.propertyChange(evt);
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_STYLE)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_VALUE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_MIN)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_MAX)) {
			refreshVisuals();
		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_TEXT)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_STEP)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_FGCOLOR)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_BGCOLOR)) {
//			figure.setBackgroundColor(
//					OspTWUIFrame.FormatRGB(model.getBGColor()));
//			super.propertyChange(eve);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_LEFTTEXT)) {
//			figure.setLeftText(model.getLeftText());
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_CENTERTEXT)) {
//			//figure.setCenterText(model.getCenterText());
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_RIGHTTEXT)) {
//			figure.setRightText(model.getRightText());
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_LEFTTEXTCOLOR)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_CENTERTEXTCOLOR)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_RIGHTTEXTCOLOR)) {
//			super.propertyChange(evt);
//			refreshVisuals();
//		}
//		if (evt.getPropertyName().equals(TWFrameNode.PROPERTY_EDITORFGCOLOR)) {
//			getFigure().setForegroundColor(model.getEditorFgColor());
//			refreshVisuals();
//		}
	}
}
