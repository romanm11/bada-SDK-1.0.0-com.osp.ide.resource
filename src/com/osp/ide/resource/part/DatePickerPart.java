package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.DatePickerFigure;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.Layout;

public class DatePickerPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		EditDate model = (EditDate) getModel();
		figure = new DatePickerFigure(model.getParent().getScreen());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	protected void refreshVisuals() {
		DatePickerFigure figure = (DatePickerFigure) getFigure();
		EditDate model = (EditDate) getModel();

		figure.setName(model.getName());
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
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_CUSTOMCLASS)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		}
	}

}
