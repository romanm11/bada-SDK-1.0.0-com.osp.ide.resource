package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editpolicies.OspEditLayoutPolicy;
import com.osp.ide.resource.figure.PanelFrameFigure;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspUIString;

public class PanelFramePart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
        PanelFrame model = (PanelFrame) getModel();
		PanelFrameFigure figure = new PanelFrameFigure(this);
		this.figure = figure;
		model.setFigure(figure);
        figure.setOpacity(model.getBGColorTransparency());
        if(!model.isScotia()) {
        	if(model.getFormColor().equals(FrameConst.DEFAULT_COLOR))
	            figure.setBackgroundColor(ColorConstants.black);
	        else
	            figure.setBackgroundColor(OspResourceManager.FormatRGB(model.getFormColor()));
        }
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new OspEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		PanelFrameFigure figure = (PanelFrameFigure) getFigure();
		PanelFrame model = (PanelFrame) getModel();
		OspUIString string = model.editor.m_string;
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			model.refreshChildren();
		}

		Layout layout = model.getLayout();
        Rectangle oldRect = figure.getRect();
        if(oldRect != null && oldRect.width != layout.width) {
            removeGetOutChild(layout);
        } else if(oldRect != null && oldRect.height != layout.height) {
            if(resetChild(layout)) {
                Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
                        layout.height);
                figure.setLayout(rect);
            } else {
                layout.width = oldRect.width;
                layout.height = oldRect.height;
                model.getItem().SetLayout(layout);
            }
        }
        
		Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
				layout.height);
		figure.setLayout(rect);

        if(!model.isScotia()) {
        	if(model.getFormColor().equals(FrameConst.DEFAULT_COLOR))
	            figure.setBackgroundColor(ColorConstants.black);
	        else
	            figure.setBackgroundColor(OspResourceManager.FormatRGB(model.getFormColor()));
        }
        figure.setBackColor(OspResourceManager.FormatRGB(model.getBGColor()));
        
		EditPartViewer viewer = getViewer();
		double zoom = ((ScalableRootEditPart) viewer.getRootEditPart())
				.getZoomManager().getZoom();
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(3);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(zoom);
	}

	public List<FrameNode> getModelChildren() {
		return ((PanelFrame) getModel()).getChildrenArray();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		PanelFrameFigure figure = (PanelFrameFigure) getFigure();
		PanelFrame model = (PanelFrame) getModel();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) {
			refreshChildren();
			getViewer().select(((FrameNode)evt.getNewValue()).getPart());
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_FRAMETITLE)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
            Color bgColor = OspResourceManager.FormatRGB(model.getBGColor());
            figure.setBackColor(bgColor);
			refreshVisuals();
		}
        else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGOPACITY)) {
            figure.setOpacity(model.getBGColorTransparency());
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

    /**
     * 
     */
    public boolean resetChild(Layout layout) {
        if(isOpenMessage(layout)) {
            if(MessageDialog.openQuestion(null, "Resize Popup", 
                "With the specified dimension, some child controls " + 
                "falls outside of Popup¡¯s bounds. " + 
                "Remove these controls and then change the size of Popup?")) {
                removeGetOutChild(layout);
                return true;
            } else
                return false;
        }
        return true;
    }
    
    /**
     * @param layout
     * @return
     */
    private boolean isOpenMessage(Layout layout) {
        List<FrameNode> modelChildren = getModelChildren();
        for(int i=0; i<modelChildren.size(); i++) {
            FrameNode child = modelChildren.get(i);
            
            Layout childLayout = child.getLayout();
            if(child instanceof ColorPicker ||
                child instanceof EditDate ||
                child instanceof EditTime) {
                Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
                    FrameConst.cszTag[child.getType()], child.getScreen());
                
                childLayout.width = info.minSize.x;
            }
            
            if(childLayout.x + childLayout.width > layout.width ||
                childLayout.y + childLayout.height > layout.height) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     */
    private void removeGetOutChild(Layout layout) {
        PanelFrame panel = (PanelFrame) getModel();
        List<FrameNode> modelChildren = getModelChildren();
        FrameNode[] child = modelChildren.toArray(new FrameNode[0]);
        for(int i=0; i<child.length; i++) {
            Layout childLayout = child[i].getLayout();
            if(child[i] instanceof ColorPicker ||
                child[i] instanceof EditDate ||
                child[i] instanceof EditTime) {
                Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
                    FrameConst.cszTag[child[i].getType()], child[i].getScreen());
                
                childLayout.width = info.minSize.x;
            }
            
            if(childLayout.x + childLayout.width > layout.width ||
                childLayout.y + childLayout.height > layout.height) {
                panel.removeChild(child[i]);
            }
            if(child[i] instanceof ColorPicker ||
                child[i] instanceof EditDate ||
                child[i] instanceof EditTime) {
                
                childLayout.width = layout.width;
                child[i].getItem().SetLayout(childLayout);
            }
        }
        refreshChildren();
    }

}
