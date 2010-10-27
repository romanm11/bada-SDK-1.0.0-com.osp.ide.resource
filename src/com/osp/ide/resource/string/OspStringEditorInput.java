package com.osp.ide.resource.string;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspStringEditorInput implements IEditorInput {

	public OspUIString strings;

	public OspStringEditorInput(OspUIString strings) {
		this.strings = strings;
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
		return ResourceExplorer.cszKindName[ResourceExplorer.KIND_STRING];
	}

	@Override
	public IPersistableElement getPersistable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return ResourceExplorer.cszKindName[ResourceExplorer.KIND_STRING];
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}

}
