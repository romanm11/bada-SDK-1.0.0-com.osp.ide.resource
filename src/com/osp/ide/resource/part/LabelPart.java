package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.editpolicies.OspDirectEditPolicy;
import com.osp.ide.resource.figure.LabelFigure;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class LabelPart extends OspAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		Label label = (Label) getModel();
		LabelFigure figure = new LabelFigure(label.getParent().getScreen());
		this.figure = figure;
		figure.setHAlign(label.getHAlign());
		figure.setVAlign(label.getVAlign());
		File image = ((Label) getModel()).getBGBitmap();
		figure.setControlImage(image);
		figure.setBackgroundColor(OspResourceManager.FormatRGB(label.getBGColor()));
		((LabelFigure)figure).setOpacity(((Label) getModel()).getBGColorTransparency());

		if (label.getTextColor().equals(FrameConst.DEFAULT_COLOR))
			figure.setForegroundColor(ColorConstants.white);
		else
			figure.setForegroundColor(OspResourceManager.FormatRGB(label.getTextColor()));
		figure.setTextSize(label.getTextSize());
		figure.setTextStyle(label.getTextStyle());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new OspDirectEditPolicy());

	}

	protected void refreshVisuals() {
		LabelFigure figure = (LabelFigure) getFigure();
		Label model = (Label) getModel();

		OspUIString string = getString(model);
		
		figure.setOpacity(model.getBGColorTransparency());
		String text = model.getText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setText(text);

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	protected void propertyChange(String key) {
		LabelFigure figure = (LabelFigure) getFigure();
		Label model = (Label) getModel();

		if (key.equals(FrameNode.PROPERTY_HALIGN)) {
			figure.setHAlign(model.getHAlign());
		} else if (key.equals(FrameNode.PROPERTY_VALIGN)) {
			figure.setVAlign(model.getVAlign());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_STYLE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_HALIGN)) {
			propertyChange(FrameNode.PROPERTY_HALIGN);
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_VALIGN)) {
			propertyChange(FrameNode.PROPERTY_VALIGN);
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGBITMAPPATH)) {
			Label model = (Label) getModel();
			File image = model.getBGBitmap();
			((LabelFigure) getFigure()).setControlImage(image);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NBGCOLOR)) {
			getFigure().setBackgroundColor(
					OspResourceManager.FormatRGB(((Label) getModel()).getBGColor()));
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTCOLOR)) {
			getFigure().setForegroundColor(
					OspResourceManager.FormatRGB(((Label) getModel()).getTextColor()));
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTSIZE)) {
			((LabelFigure) getFigure()).setTextSize(((Label) getModel()).getTextSize());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTSTYLE)) {
			((LabelFigure) getFigure()).setTextStyle(((Label) getModel()).getTextStyle());
			refreshVisuals();
		}
		else if(evt.getPropertyName().equals(FrameNode.PROPERTY_BGOPACITY)) {
			refreshVisuals();
		}
	}
}
