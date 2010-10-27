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
import com.osp.ide.resource.figure.IconListFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class IconListPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		IconList model = (IconList) getModel();
		IconListFigure figure = new IconListFigure(model.getParent().getScreen());
		// figure.setBackgroundColor(((IconList)
		// getModel()).getEditorBgColor());
		// figure.setForegroundColor(((IconList)
		// getModel()).getEditorFgColor());
		figure.setItemHeight(model.getItemHeight());
		figure.setItemWidth(model.getItemWidth());
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

	protected void refreshVisuals() {
		IconListFigure figure = (IconListFigure) getFigure();
		IconList model = (IconList) getModel();

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
		IconListFigure figure = (IconListFigure) getFigure();
		IconList model = (IconList) getModel();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_STYLE)) {
			figure.setStyle(model.getStyle());
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LAYOUT))
			refreshVisuals();
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_ITEMHEIGHT)) {
			figure.setItemHeight(model.getItemHeight());
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_ITEMWIDTH)) {
			figure.setItemWidth(model.getItemWidth());
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
	}

}
