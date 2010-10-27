package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspEditLayoutPolicy;
import com.osp.ide.resource.figure.FormFrameFigure;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.string.OspUIString;

public class FormFramePart extends OspAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		Form model = ((Form) getModel());
		FormFrameFigure figure = new FormFrameFigure(this);
		this.figure = figure;
		model.setFigure(figure);
		OspUIString string = model.editor.m_string;
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			model.refreshChildren();
		}

		String text = model.getSoftkey0Text();
		if (text.indexOf("::") >= 0)
			text = string.getText(text.replace("::", ""));
		figure.setSoftKeyText(0, text);

		text = model.getSoftkey1Text();
		if (text.indexOf("::") >= 0)
			text = string.getText(text.replace("::", ""));
		figure.setSoftKeyText(1, text);

		figure.setTitleIcon(model.getTitleIcon());
		figure.setHAlign(model.getHAlign());
		File image = model.getSoftKey0NIcon();
		figure.setSoftKey0Icon(image);
		image = model.getSoftKey1NIcon();
		figure.setSoftKey1Icon(image);

		figure.setOpacity(model.getBGColorTransparency());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new OspEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		FormFrameFigure figure = (FormFrameFigure) getFigure();
		Form model = (Form) getModel();
		OspUIString string = model.editor.m_string;
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			model.refreshChildren();
		}

		// System.out.println("Frame Figure refreshVisuals : "+model.getLayout());
		Layout layout = model.getLayout();

		String text = model.getSoftkey0Text();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setSoftKeyText(0, text);

		text = model.getSoftkey1Text();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setSoftKeyText(1, text);

		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);
		
        figure.setBackColor(OspResourceManager
                .FormatRGB(model.getBgColor()));
        
		figure.setTitle(model.isTitleStyle());
		figure.setTextTab(model.isTextTabStyle());
		figure.setIconTab(model.isIconTabStyle());
		figure.setIndicator(model.isIndicatorStyle());
//		figure.setSoftKey(model.isSoftKeyStyle());
		figure.setSoftKey0(model.isSoftKey0Style());
		figure.setSoftKey1(model.isSoftKey1Style());
		figure.setOptionKey(model.isOptionKeyStyle());
		
		figure.setLayout(rect);

		text = model.getTitle();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setTitleText(text);
		figure.setHAlign(model.getHAlign());

		EditPartViewer viewer = getViewer();
		double zoom = ((ScalableRootEditPart) viewer.getRootEditPart())
				.getZoomManager().getZoom();
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(3);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(zoom);
	}

	public List<FrameNode> getModelChildren() {
		return ((Form) getModel()).getChildrenArray();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Form model = ((Form) getModel());
		FormFrameFigure figure = ((FormFrameFigure) getFigure());
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) {
			refreshChildren();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_FRAMETITLE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLEICON)) {
			figure.setTitleIcon(model.getTitleIcon());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_HALIGN)) {
			figure.setHAlign(model.getHAlign());
            refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_HEADERHEIGHT)) {
			figure.setHeaderHeight(model.getHeaderHeight());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SOFTKEY0TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SOFTKEY1TEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SOFTKEY0NICON)) {
			figure.setSoftKey0Icon(model.getSoftKey0NIcon());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_SOFTKEY1NICON)) {
			figure.setSoftKey1Icon(model.getSoftKey1NIcon());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_INDICATORLAYOUTSTYLE)) {
			reAssignOffset(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLELAYOUTSTYLE)) {
			reAssignOffset(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TABLAYOUTSTYLE)) {
			reAssignOffset(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_SOFTKEY0LAYOUTSTYLE)) {
			reAssignOffsetMax(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_SOFTKEY1LAYOUTSTYLE)) {
			reAssignOffsetMax(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_OPTIONKEYLAYOUTSTYLE)) {
			reAssignOffsetMax(model);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(
				FrameNode.PROPERTY_BGOPACITY)) {
	        figure.setOpacity(model.getBGColorTransparency());
            refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
			Color bgColor = OspResourceManager.FormatRGB(((Form) getModel())
					.getBgColor());
			figure.setBackColor(bgColor);
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_GRID)) {
			Boolean val = (Boolean) getViewer().getProperty(
					SnapToGrid.PROPERTY_GRID_ENABLED);
			figure.paintGrid(val);
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_DIMENSION)) {
			Dimension val = (Dimension) getViewer().getProperty(
					SnapToGrid.PROPERTY_GRID_SPACING);
			figure.setDimension(val.height);
		}
	}

	@SuppressWarnings("unchecked")
	public void reAssignOffset(Form model) {
		for(int n=FrameConst.PORTRAIT; n<=FrameConst.LANDCAPE; n++) {
			int offset = model.getMinY(n);;
			int offsetMax = model.getLayout(n).height;
	//		if (model.isSoftKeyStyle()) {
	//			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
	//					TWFrameConst.cszTag[TWFrameConst.WINDOW_SOFTKEY]);
	//			offsetMax -= tag.dftlSize.y;
	//		}
			List<OspAbstractEditPart> parts = getChildren();
			for (int i = 0; i < parts.size(); i++) {
				FrameNode child = (FrameNode) parts.get(i).getModel();
				
				Layout layout = child.getLayout(n);
//				if(child instanceof TWDatePicker ||
//						child instanceof TWColorPicker ||
//						child instanceof TWTimePicker) {
//					child.setLayout(layout);
//					continue;
//				}
				
				if (layout.y < offset) {
					layout.y = offset;
					if (layout.y + layout.height > offsetMax)
						layout.height = offsetMax - layout.y;
					child.setLayout(layout);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void reAssignOffsetMax(Form model) {
		int offset = model.getMinY();;
		int offsetMax = model.getLayout().height;
//		if (model.isSoftKeyStyle()) {
//			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
//					TWFrameConst.cszTag[TWFrameConst.WINDOW_SOFTKEY]);
//			offsetMax -= tag.dftlSize.y;
//		}
		List<OspAbstractEditPart> parts = getChildren();
		for (int i = 0; i < parts.size(); i++) {
			FrameNode child = (FrameNode) parts.get(i).getModel();
			
//			if(child instanceof TWDatePicker ||
//					child instanceof TWColorPicker ||
//					child instanceof TWTimePicker)
//				continue;
			
			Layout layout = child.getLayout();
			if (layout.y + layout.height > offsetMax) {
				layout.y = offsetMax - layout.height;
				if (layout.y < offset) {
					layout.y = offset;
					layout.height = offsetMax - layout.y;
				}
				child.setLayout(layout);
			}
		}
	}
	
}
