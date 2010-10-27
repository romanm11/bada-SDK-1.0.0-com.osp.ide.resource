package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.ExpandableListFigure;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class ExpandableListPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		ExpandableList cList = (ExpandableList) getModel();
		ExpandableListFigure figure = new ExpandableListFigure(cList.getParent().getScreen());
		this.figure = figure;
		
        if (cList.getTextColor().equals(FrameConst.DEFAULT_COLOR)) {
            Tag_info tag = figure.getTagInfo();
            if(tag != null)
                figure.setTextColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp1));
            else
                figure.setTextColor(new Color(null, 255, 255, 255));
        } else
            figure.setTextColor(OspResourceManager.FormatRGB(cList
                    .getTextColor()));
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	protected void refreshVisuals() {
		ExpandableListFigure figure = (ExpandableListFigure) getFigure();
		ExpandableList model = (ExpandableList) getModel();

		OspUIString string = getString(model);

		String text = model.getTextOfEmptyList();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setName(text);

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		ExpandableList model = (ExpandableList) getModel();
		ExpandableListFigure figure = (ExpandableListFigure) this.figure;
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_CUSTOMCLASS)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
			super.propertyChange(evt);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            if (model.getTextColor().equals(FrameConst.DEFAULT_COLOR)) {
                Tag_info tag = figure.getTagInfo();
                if(tag != null)
                    figure.setTextColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp1));
                else
                    figure.setTextColor(new Color(null, 255, 255, 255));
            } else
                figure.setTextColor(OspResourceManager.FormatRGB(model
                        .getTextColor()));
            refreshVisuals();
        }
	}

}
