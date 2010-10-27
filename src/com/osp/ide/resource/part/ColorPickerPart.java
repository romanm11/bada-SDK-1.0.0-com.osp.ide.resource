package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.ColorPickerFigure;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.Layout;

public class ColorPickerPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		ColorPicker model = (ColorPicker) getModel();
		figure = new ColorPickerFigure(model.getParent().getScreen());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	protected void refreshVisuals() {
		ColorPickerFigure figure = (ColorPickerFigure) getFigure();
		ColorPicker model = (ColorPicker) getModel();

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
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_FRAMETITLE)) {
			super.propertyChange(evt);
			refreshVisuals();
		}
	}

}
