package com.osp.ide.resource.part.tree;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.gef.tools.SelectEditPartTracker;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;

public abstract class OspAbstractTreeEditPart extends AbstractTreeEditPart
		implements PropertyChangeListener {
	protected Image image;

	public void activate() {
		super.activate();
		((FrameNode) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		((FrameNode) getModel()).removePropertyChangeListener(this);
		super.deactivate();
		if(image != null && !image.isDisposed()) {
			image.dispose();
			image = null;
		}
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		GraphicalEditorWithFlyoutPalette editor = null;
		if (getModel() instanceof FrameNode) {
			FrameNode frame = ((FrameNode)getModel()).getFrameModel();
			if(frame instanceof Form)
				editor = ((Form)frame).editor;
			else if(frame instanceof PanelFrame)
				editor = ((PanelFrame)frame).editor;
			else if(frame instanceof Popup)
				editor = ((Popup)frame).editor;
		}
		
		if (editor != null)
			editor.setFocus();
		
		return new SelectEditPartTracker(this);
	}

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(FrameNode.PROPERTY_ADD)) refreshChildren();
        if(evt.getPropertyName().equals(FrameNode.PROPERTY_REMOVE)) refreshChildren();

        refreshVisuals();
    }
    
	@Override
	public void performRequest(Request req) {
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				page.showView(IPageLayout.ID_PROP_SHEET);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
}
