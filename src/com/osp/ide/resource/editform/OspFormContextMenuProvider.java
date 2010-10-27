package com.osp.ide.resource.editform;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

import com.osp.ide.resource.actions.OspAddClassAction;

public class OspFormContextMenuProvider extends ContextMenuProvider {

	private ActionRegistry actionRegistry;

	public OspFormContextMenuProvider(EditPartViewer viewer,
			ActionRegistry registry) {
		super(viewer);
		setActionRegistry(registry);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		IAction action;

		GEFActionConstants.addStandardActionGroups(menu);

		action = getActionRegistry().getAction(ActionFactory.UNDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = getActionRegistry().getAction(ActionFactory.REDO.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

		action = actionRegistry.getAction(ActionFactory.CUT.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
		
		action = actionRegistry.getAction(ActionFactory.COPY.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = actionRegistry.getAction(ActionFactory.PASTE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = getActionRegistry().getAction(ActionFactory.DELETE.getId());
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);

		action = getActionRegistry().getAction(
		OspAddClassAction.ADD_ACTION_ID);
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
	}

	private ActionRegistry getActionRegistry() {
		return actionRegistry;
	}

	private void setActionRegistry(ActionRegistry registry) {
		actionRegistry = registry;
	}
}