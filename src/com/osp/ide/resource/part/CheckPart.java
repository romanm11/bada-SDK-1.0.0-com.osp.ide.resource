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
import com.osp.ide.resource.editpolicies.OspDirectEditPolicy;
import com.osp.ide.resource.figure.CheckFigure;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class CheckPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		Check check = (Check) getModel();
		CheckFigure figure = new CheckFigure(check.getParent().getScreen());
		this.figure = figure;
		figure.setTextDirection(check.getTextDirection());
		figure.setHAlign(check.getHAlign());
		figure.setVAlign(check.getVAlign());
		figure.setStyle(check.getStyleIndex(FrameConst.STYLE_CHECK));
		figure.setBgStyle(check.getBgStyleIndex());
		figure.setShowTitle(check.getShowTitleText());
        if (check.getTextColor().equals(FrameConst.DEFAULT_COLOR)) {
            Tag_info tag = figure.getTagInfo();
            if(tag != null)
                figure.setTextColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp2));
            else
                figure.setTextColor(new Color(null, 255, 255, 255));
        } else
            figure.setTextColor(OspResourceManager.FormatRGB(check
                    .getTextColor()));
        if (check.getTitleTextColor().equals(FrameConst.DEFAULT_COLOR))
            figure.setTitleTextColor(new Color(null, 119, 186, 221));
        else
            figure.setTitleTextColor(OspResourceManager.FormatRGB(check
                    .getTitleTextColor()));
        figure.setGroupStyle(check.getGroupStyleIndex());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new OspDirectEditPolicy());
	}

	protected void refreshVisuals() {
		CheckFigure figure = (CheckFigure) getFigure();
		Check model = (Check) getModel();
		
		OspUIString string = getString(model);

		String text = model.getText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}

		figure.setText(text);

		text = model.getTitleText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}

		figure.setShowTitle(model.getShowTitleText());
		figure.setTitleText(text);

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	protected void propertyChange(String key) {
		CheckFigure figure = (CheckFigure) getFigure();
		Check model = (Check) getModel();

		if (key.equals(FrameNode.PROPERTY_HALIGN)) {
			figure.setHAlign(model.getHAlign());
		} else if (key.equals(FrameNode.PROPERTY_VALIGN)) {
			figure.setVAlign(model.getVAlign());
		} else if (key.equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			figure.setTextDirection(model.getTextDirection());
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		Check model = (Check) getModel();
		CheckFigure figure = (CheckFigure) getFigure();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXT)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTCOLOR)) {
            if (model.getTextColor().equals(FrameConst.DEFAULT_COLOR))
                figure.setTextColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp2));
            else
                figure.setTextColor(OspResourceManager.FormatRGB(model
                        .getTextColor()));
            refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            if (model.getTitleTextColor().equals(FrameConst.DEFAULT_COLOR))
                figure.setTitleTextColor(new Color(null, 119, 186, 221));
            else
                figure.setTitleTextColor(OspResourceManager.FormatRGB(model
                        .getTitleTextColor()));
            refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			propertyChange(FrameNode.PROPERTY_TEXTDIRECTION);
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GROUPID)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_HALIGN)) {
			propertyChange(FrameNode.PROPERTY_HALIGN);
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_VALIGN)) {
			propertyChange(FrameNode.PROPERTY_VALIGN);
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_STYLE)) {
			figure.setStyle(model.getStyleIndex(FrameConst.STYLE_CHECK));
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			figure.setBgStyle(model.getBgStyleIndex());
			refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            figure.setGroupStyle(model.getGroupStyleIndex());
	        refreshVisuals();
		}
	}
}
