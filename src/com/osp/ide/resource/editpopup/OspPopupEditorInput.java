package com.osp.ide.resource.editpopup;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.string.OspUIString;

public class OspPopupEditorInput implements IEditorInput {
	public OspResourceManager popup;
	public String ID;
	public OspUIString string;
	public String screen;

	public OspPopupEditorInput(OspResourceManager popup, String screen, String ID, OspUIString ospUIString) {
		this.popup = popup;
		this.screen = screen;
		this.ID = ID;
		this.string = ospUIString;
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
