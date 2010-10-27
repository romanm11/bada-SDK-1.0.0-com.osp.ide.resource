package com.osp.ide.resource.part.tree;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.ListControl;

public class ListTreeEditPart extends OspAbstractTreeEditPart{
	
	protected java.util.List<FrameNode> getModelChildren() {
		return ((ListControl)getModel()).getChildrenArray();
	}
	
	@Override
	protected void createEditPolicies() {	
		image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/listctl_outline.png").createImage();
		
		installEditPolicy(EditPolicy.COMPONENT_ROLE,new OspDeletePolicy());
	}
	
	public void refreshVisuals(){
		ListControl model = (ListControl)getModel();
		setWidgetText(model.getName());
		setWidgetImage(image);
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}
}
