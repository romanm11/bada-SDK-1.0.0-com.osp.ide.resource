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
import com.osp.ide.resource.figure.ButtonFigure;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class ButtonPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		Button button = (Button) getModel();
		ButtonFigure figure = new ButtonFigure(button.getParent().getScreen());
		this.figure = figure;
		figure.setTextDirection(button.getTextDirection());
		figure.setHAlign(button.getHAlign());
		figure.setVAlign(button.getVAlign());
		if (button.getNormalFGColor().equals(FrameConst.DEFAULT_COLOR))
			figure.setForegroundColor(ColorConstants.black);
		else
			figure.setForegroundColor(OspResourceManager.FormatRGB(button
					.getNormalFGColor()));
//		figure.setBackgroundColor(button.getEditorBgColor());

		File image = button.getNormalBitmapPath();
		figure.setNormalBitmap(image);
		image = button.getNormalBGBitmapPath();
		figure.setNormalBGBitmap(image);

		figure.setPointX(button.getNormalBitmapX());
		figure.setPointY(button.getNormalBitmapY());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new OspDirectEditPolicy());
	}

	protected void refreshVisuals() {
		ButtonFigure figure = (ButtonFigure) getFigure();
		Button model = (Button) getModel();

		OspUIString string = getString(model);
		
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

	protected void propertyChange(String key) {
		ButtonFigure figure = (ButtonFigure) getFigure();
		Button model = (Button) getModel();

		if (key.equals(FrameNode.PROPERTY_HALIGN)) {
			figure.setHAlign(model.getHAlign());
		} else if (key.equals(FrameNode.PROPERTY_VALIGN)) {
			figure.setVAlign(model.getVAlign());
		} else if (key.equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			figure.setTextDirection(model.getTextDirection());
		}
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		Button button = (Button) getModel();
		ButtonFigure figure = (ButtonFigure) this.figure;
        super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			propertyChange(FrameNode.PROPERTY_TEXTDIRECTION);
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_HALIGN)) {
			propertyChange(FrameNode.PROPERTY_HALIGN);
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_VALIGN)) {
			propertyChange(FrameNode.PROPERTY_VALIGN);
		}
		// if (evt.getPropertyName().equals(FrameNode.PROPERTY_OUTLINE))
		// refreshVisuals();
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NBGCOLOR)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NTEXTCOLOR)) {
			if (button.getNormalFGColor().equals(FrameConst.DEFAULT_COLOR))
				figure.setForegroundColor(ColorConstants.black);
			else
				figure.setForegroundColor(OspResourceManager.FormatRGB(button
						.getNormalFGColor()));
			refreshVisuals();
		}
		// if (evt.getPropertyName().equals(FrameNode.PROPERTY_NOUTLINE))
		// refreshVisuals();
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_PBGCOLOR)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_PTEXTCOLOR)) {
			refreshVisuals();
		}
		// if (evt.getPropertyName().equals(FrameNode.PROPERTY_POUTLINE))
		// refreshVisuals();
		// if (evt.getPropertyName().equals(FrameNode.PROPERTY_DOUTLINE))
		// refreshVisuals();
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NORMALBITMAPPATH)) {
			figure.setNormalBitmap(button.getNormalBitmapPath());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NORMALBITMAPX)) {
			int pointX = button.getNormalBitmapX();
			figure.setPointX(pointX);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_NORMALBITMAPY)) {
			int pointY = button.getNormalBitmapY();
			figure.setPointY(pointY);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_NORMALBGBITMAPPATH)) {
			figure.setNormalBGBitmap(button.getNormalBGBitmapPath());
			refreshVisuals();
		}
	}
}
