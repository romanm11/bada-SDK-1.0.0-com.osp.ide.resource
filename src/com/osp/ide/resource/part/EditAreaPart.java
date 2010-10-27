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
import com.osp.ide.resource.figure.EditAreaFigure;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class EditAreaPart extends OspAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		EditArea model = (EditArea) getModel();
		EditAreaFigure figure = new EditAreaFigure(model.getParent().getScreen());
        Tag_info tag = figure.getTagInfo();
        if(tag != null)
            figure.setForegroundColor(OspResourceManager.FormatRGB(figure.getTagInfo().temp1));
        else
            figure.setForegroundColor(new Color(null, 255, 255, 255));
            
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
		EditAreaFigure figure = (EditAreaFigure) getFigure();
		EditArea model = (EditArea) getModel();
		
		OspUIString string = getString(model);

		Layout layout = model.getLayout();
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
		
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
		text = model.getText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		
		if (length < text.length())
			text = text.substring(0, length);
		
		figure.setText(text);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GUIDETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LIMITLENGTH)) {
			refreshVisuals();
		}
	}
}
