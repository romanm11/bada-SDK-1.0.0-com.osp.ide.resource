package com.osp.ide.resource.part.tree;

import java.util.List;

import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.FrameNode;

public class DatePickerTreeEditPart extends OspAbstractTreeEditPart {

	protected List<FrameNode> getModelChildren() {
		return ((EditDate) getModel()).getChildrenArray();
	}

	@Override
	protected void createEditPolicies() {
		image = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/datepicker_outline.png").createImage();

		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	public void refreshVisuals() {
		EditDate model = (EditDate) getModel();
		setWidgetText(model.getName());
		setWidgetImage(image);
	}

	@Override
	public void deactivate() {
		super.deactivate();
	}
}