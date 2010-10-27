package com.osp.ide.resource.part.tree;

import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.FrameNode;

public class EditAreaTreeEditPart extends OspAbstractTreeEditPart{

	protected List<FrameNode> getModelChildren() {
		return ((EditArea)getModel()).getChildrenArray();
	}
	
	@Override
	protected void createEditPolicies() {	
		image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/editarea_outline.png").createImage();

		installEditPolicy(EditPolicy.COMPONENT_ROLE,new OspDeletePolicy());
	}
	
	public void refreshVisuals(){
		EditArea model = (EditArea)getModel();
		setWidgetText(model.getName());
		setWidgetImage(image);
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}
}
