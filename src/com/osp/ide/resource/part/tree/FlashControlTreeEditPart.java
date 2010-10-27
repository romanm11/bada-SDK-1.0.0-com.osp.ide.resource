package com.osp.ide.resource.part.tree;

import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.FrameNode;

public class FlashControlTreeEditPart extends OspAbstractTreeEditPart {

	protected List<FrameNode> getModelChildren() {
		return ((Flash) getModel()).getChildrenArray();
	}

	@Override
	protected void createEditPolicies() {
		image = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/flashcontrol_outline.png")
				.createImage();

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	public void refreshVisuals() {
		Flash model = (Flash) getModel();
		setWidgetText(model.getName());
		setWidgetImage(image);
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}
}