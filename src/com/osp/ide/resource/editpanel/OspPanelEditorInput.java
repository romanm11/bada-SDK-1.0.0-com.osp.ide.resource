package com.osp.ide.resource.editpanel;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.string.OspUIString;

public class OspPanelEditorInput implements IEditorInput {
	public OspResourceManager frame;
	public String ID;
	public OspUIString string;
    public String screen;

	public OspPanelEditorInput(OspResourceManager frame, String screen, String ID, OspUIString string) {
		this.frame = frame;
		this.ID = ID;
		this.screen = screen;
		this.string = string;
	}

	@Override
	public boolean exists() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return ID;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

}
