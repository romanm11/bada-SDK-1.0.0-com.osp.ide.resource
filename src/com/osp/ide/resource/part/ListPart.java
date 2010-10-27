package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.ListFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class ListPart extends OspAbstractEditPart {
	ListFigure figure;

	@Override
	protected IFigure createFigure() {
		ListControl model = ((ListControl) getModel());
		ListFigure figure = new ListFigure(model.getParent().getScreen());
		this.figure = figure;
		int height = model.getLine1Height();
		if (height == 0)
			height = 80;
		figure.setLine1Height(height);
		height = model.getLine2Height();
		if (height == 0)
			height = 80;
		figure.setLine2Height(height);
		figure.setCol1Width(model.getColume1Width());
		figure.setCol2Width(model.getColume2Width());
		figure.setListItemFormat(ListControl.getListFormatIndex(model
				.getListItemFormat()));
		figure.setStyle(model.getStyle());
		
        if (model.getTextColor().equals(FrameConst.DEFAULT_COLOR)) {
            Tag_info tag = figure.getTagInfo();
            if(tag != null)
                figure.setTextColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp1));
            else
                figure.setTextColor(new Color(null, 255, 255, 255));
        } else
            figure.setTextColor(OspResourceManager.FormatRGB(model
                    .getTextColor()));
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	@Override
	public void setSelected(int value) {
		super.setSelected(value);

		refreshVisuals();
	}

	protected void refreshVisuals() {
		ListFigure figure = (ListFigure) getFigure();
		ListControl model = (ListControl) getModel();

		if (model == null)
			return;

		OspUIString string = getString(model);

		String text = model.getTextOfEmptyList();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if (tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setName(text);

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public java.util.List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		ListFigure figure = (ListFigure) this.figure;
		ListControl model = (ListControl) getModel();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_STYLE)) {
            figure.setStyle(model.getStyle());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
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
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_ITEMTEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LINE1HEIGHT)) {
			figure.setLine1Height(model.getLine1Height());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LINE2HEIGHT)) {
			figure.setLine2Height(model.getLine2Height());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_COLUME1WIDTH)) {
			figure.setCol1Width(model.getColume1Width());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_COLUME2WIDTH)) {
			figure.setCol2Width(model.getColume2Width());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LISTITEMFORMAT)) {
			figure.setListItemFormat(ListControl.getListFormatIndex(model
					.getListItemFormat()));
			refreshVisuals();
		}
	}
}
