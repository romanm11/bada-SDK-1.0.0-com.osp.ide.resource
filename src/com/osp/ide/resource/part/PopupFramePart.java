package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.dialogs.MessageDialog;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.editpolicies.OspEditLayoutPolicy;
import com.osp.ide.resource.figure.PopupFrameFigure;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspUIString;

public class PopupFramePart extends OspAbstractEditPart {
	@Override
	protected IFigure createFigure() {
		Popup model = ((Popup) getModel());
		PopupFrameFigure figure = new PopupFrameFigure(this);
		this.figure = figure;
		model.setFigure(figure);
		OspUIString string = model.editor.m_string;
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			model.refreshChildren();
		}

		String text = model.getTitleText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		figure.setTitleText(text);
		figure.setTitleIcon(model.getTitleIcon());
		figure.setBitmap(model.getBitmap());

		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new OspEditLayoutPolicy());
	}

	protected void refreshVisuals() {
		PopupFrameFigure figure = (PopupFrameFigure) getFigure();
		Popup model = (Popup) getModel();

		OspUIString string = model.editor.m_string;
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			model.refreshChildren();
		}

		String text = model.getTitleText();
		if (string != null && text != null && text.indexOf("::") >= 0) {
			String tableText = string.getText(text.replace("::", ""));
			if(tableText != null && !tableText.isEmpty())
				text = tableText;
		}
		if(text != null && !text.isEmpty())
            reAssignOffset(model);
		
		figure.setTitleText(text);
		// System.out.println("Frame Figure refreshVisuals : "+model.getLayout());
		Layout layout = model.getLayout();
        Rectangle oldRect = figure.getRect();
        if(oldRect != null && (oldRect.width != layout.width || oldRect.height != layout.height)) {
            if(resetChild(layout)) {
                Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
                        layout.height);
                figure.setLayout(rect);
            } else {
                layout.width = oldRect.width;
                layout.height = oldRect.height;
                model.getItem().SetLayout(layout);
            }
        } else {
            Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
                    layout.height);
            figure.setLayout(rect);
        }

		EditPartViewer viewer = getViewer();
		double zoom = ((ScalableRootEditPart) viewer.getRootEditPart())
				.getZoomManager().getZoom();
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(0.9);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(3);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(zoom);
	}

	public List<FrameNode> getModelChildren() {
		return ((Popup) getModel()).getChildrenArray();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Popup model = ((Popup) getModel());
		PopupFrameFigure figure = ((PopupFrameFigure) getFigure());
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) {
			refreshChildren();
			getViewer().select(((FrameNode)evt.getNewValue()).getPart());
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_LAYOUT)) {
            refreshVisuals();
        } else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLETEXT)) {
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_TITLEICON)) {
			figure.setTitleIcon(model.getTitleIcon());
			refreshVisuals();
		}
		else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BITMAP)) {
			figure.setBitmap(model.getBitmap());
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
        Popup popup = (Popup) getModel();
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
                popup.removeChild(child[i]);
            }
        }
        refreshChildren();
    }

    @SuppressWarnings("unchecked")
    public void reAssignOffset(Popup model) {
        for(int n=FrameConst.PORTRAIT; n<=FrameConst.LANDCAPE; n++) {
            int offset = model.getMinY();;
            int offsetMax = model.getLayout(n).height;
    //      if (model.isSoftKeyStyle()) {
    //          Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
    //                  TWFrameConst.cszTag[TWFrameConst.WINDOW_SOFTKEY]);
    //          offsetMax -= tag.dftlSize.y;
    //      }
            List<OspAbstractEditPart> parts = getChildren();
            for (int i = 0; i < parts.size(); i++) {
                FrameNode child = (FrameNode) parts.get(i).getModel();
                
                Layout layout = child.getLayout(n);
//              if(child instanceof TWDatePicker ||
//                      child instanceof TWColorPicker ||
//                      child instanceof TWTimePicker) {
//                  child.setLayout(layout);
//                  continue;
//              }
                
                if (layout.y < offset) {
                    layout.y = offset;
                    if (layout.y + layout.height > offsetMax)
                        layout.height = offsetMax - layout.y;
                    child.setLayout(layout);
                }
            }
        }
    }
    
}
