package com.osp.ide.resource.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import com.osp.ide.resource.commands.OspDeleteCommand;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;

public class OspDeletePolicy extends ComponentEditPolicy{
	  
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object model = getHost().getModel();
		if(model instanceof Form ||
				model instanceof PanelFrame ||
				model instanceof Popup)
			return null;
		
		OspDeleteCommand command = new OspDeleteCommand();
		command.setModel(getHost().getModel());
		command.setParentModel(getHost().getParent().getModel());
		return command;
	}
}
