package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.editpolicies.OspEditLayoutPolicy;
import com.osp.ide.resource.figure.PanelFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.resinfo.Layout;

public class PanelPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		Panel model = (Panel) getModel();
		PanelFigure figure = new PanelFigure(model.getParent().getScreen());
		figure.setBackColor(OspResourceManager.FormatRGB(model.getBGColor()));
		figure.setOpacity(model.getBGColorTransparency());
		figure.setGroupStyle(model.getGroupStyleIndex());
		this.figure = figure;
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new OspEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		PanelFigure figure = (PanelFigure) getFigure();
		Panel model = (Panel) getModel();

		figure.setOpacity(model.getBGColorTransparency());
		figure.setName(model.getName());
		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return ((Panel) getModel()).getChildrenArray();
	}

	public void propertyChange(PropertyChangeEvent evt) {
        PanelFigure figure = (PanelFigure) getFigure();
        Panel model = (Panel) getModel();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) {
			refreshChildren();
		}else if (evt.getPropertyName().equals(FrameNode.PROPERTY_CUSTOMCLASS)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_FRAMETITLE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
			figure.setBackColor(
							OspResourceManager.FormatRGB(((Panel) getModel())
									.getBGColor()));
			refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            figure.setGroupStyle(model.getGroupStyleIndex());
            refreshVisuals();
		}
		else if(evt.getPropertyName().equals(FrameNode.PROPERTY_BGOPACITY)) {
			refreshVisuals();
		}
	}

}
