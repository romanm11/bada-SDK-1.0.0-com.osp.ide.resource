package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;

import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.editpolicies.OspDirectEditPolicy;
import com.osp.ide.resource.figure.EditFieldFigure;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class EditFieldPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		EditField model = (EditField) getModel();
		EditFieldFigure figure = new EditFieldFigure(model.getParent().getScreen());
		figure.setStyle(model.getStyle());
		figure.setShowTitle(model.getShowTitleText());
        figure.setGroupStyle(model.getGroupStyleIndex());
		this.figure = figure;
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new OspDirectEditPolicy());
	}

	protected void refreshVisuals() {
		EditFieldFigure figure = (EditFieldFigure) getFigure();
		EditField model = (EditField) getModel();

		OspUIString string = getString(model);

		Layout layout = model.getLayout();
		
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
        figure.setStyle(model.getStyle());
		
		String text = model.getGuideText();
		int length = model.getLimitLength();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		
//		if (length < text.length())
//			text = text.substring(0, length);
		
		figure.setGuideText(text);
		text = model.getTitleText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		
		figure.setShowTitle(model.getShowTitleText());
		figure.setTitleText(text);
		text = model.getText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		
		if (length < text.length())
			text = text.substring(0, length);
		
		if(model.getStyleIndex(FrameConst.STYLE_EDITFIELD) 
				== FrameConst.EDIT_FIELD_STYLE_PASSWORD ||
		   model.getStyleIndex(FrameConst.STYLE_EDITFIELD) 
				== FrameConst.EDIT_FIELD_STYLE_PASSWORD_SMALL) {
			length = 0;
			if(text != null)
				length = text.length();
			StringBuilder s;
			for(int i=0; i<length; i++) {
				if(i==0) {
					text = "*";
				} else {
					s = new StringBuilder(text);
					s.append('*');
					text = s.toString();
				}
			}
		}
		figure.setText(text);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		EditFieldFigure figure = (EditFieldFigure) getFigure();
		EditField model = (EditField) getModel();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_STYLE)) {
			model.setLayout(model.getLayout());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GUIDETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LIMITLENGTH)) {
			refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            figure.setGroupStyle(model.getGroupStyleIndex());
            refreshVisuals();
		}
	}
}
