package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.editpolicies.OspEditLayoutPolicy;
import com.osp.ide.resource.figure.OverlayPanelFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.resinfo.Layout;

public class OverlayPanelPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		OverlayPanel model = (OverlayPanel) getModel();
		figure = new OverlayPanelFigure(model.getParent().getScreen());
		figure.setBackgroundColor(OspResourceManager.FormatRGB(((OverlayPanel) getModel())
				.getBGColor()));
//		((OverlayPanelFigure)figure).setOpacity(((OverlayPanel) getModel()).getBGColorTransparency());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new OspEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		OverlayPanelFigure figure = (OverlayPanelFigure) getFigure();
		OverlayPanel model = (OverlayPanel) getModel();

//		figure.setOpacity(model.getBGColorTransparency());
		figure.setName(model.getName());
		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return ((OverlayPanel) getModel()).getChildrenArray();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) {
			refreshChildren();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_CUSTOMCLASS)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_FRAMETITLE)) {
			refreshVisuals();
		} 
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
				getFigure()
						.setBackgroundColor(
								OspResourceManager.FormatRGB(((OverlayPanel) getModel())
										.getBGColor()));
				refreshVisuals();
		}
		else if(evt.getPropertyName().equals(FrameNode.PROPERTY_BGOPACITY)) {
			refreshVisuals();
		}
	}
}
