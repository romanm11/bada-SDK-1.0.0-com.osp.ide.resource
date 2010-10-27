package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.SliderFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class SliderPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		Slider model = (Slider) getModel();
		SliderFigure figure = new SliderFigure(model.getParent().getScreen());
		figure.setLeftIconBitmap(model.getLeftIconBitmapPath());
		figure.setRightIconBitmap(model.getRightIconBitmapPath());
		figure.setBackgroundColor(OspResourceManager.FormatRGB(model.getBGColor()));
		figure.setBgStyle(model.getBgStyleIndex());
		figure.setShowTitle(model.getShowTitleText());
        if (model.getTitleTextColor().equals(FrameConst.DEFAULT_COLOR))
            figure.setTitleTextColor(new Color(null, 119, 186, 221));
        else
            figure.setTitleTextColor(OspResourceManager.FormatRGB(model
                    .getTitleTextColor()));
        figure.setGroupStyle(model.getGroupStyleIndex());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	protected void refreshVisuals() {
		SliderFigure figure = (SliderFigure) getFigure();
		Slider model = (Slider) getModel();

		OspUIString string = getString(model);

		String text = model.getTitleText();
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

	public void propertyChange(PropertyChangeEvent evt) {
		Slider model = (Slider) getModel();
		SliderFigure figure = (SliderFigure) getFigure();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_VALUE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_MIN)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_MAX)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_STEPSIZE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
			getFigure().setBackgroundColor(
					OspResourceManager.FormatRGB(model.getBGColor()));
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_LEFTICONBITMAPPATH)) {
			figure.setLeftIconBitmap(model.getLeftIconBitmapPath());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_RIGHTICONBITMAPPATH)) {
			figure.setRightIconBitmap(model.getRightIconBitmapPath());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			figure.setBgStyle(model.getBgStyleIndex());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            if (model.getTitleTextColor().equals(FrameConst.DEFAULT_COLOR))
                figure.setTitleTextColor(new Color(null, 119, 186, 221));
            else
                figure.setTitleTextColor(OspResourceManager.FormatRGB(model
                        .getTitleTextColor()));
            refreshVisuals();
        }
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            figure.setGroupStyle(model.getGroupStyleIndex());
            refreshVisuals();
		}
	}

}
