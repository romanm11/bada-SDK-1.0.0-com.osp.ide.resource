package com.osp.ide.resource.actions;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.internal.ui.rulers.RulerContextMenuProvider;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

@SuppressWarnings("restriction")
public class OspRulerContextMenuProvider extends RulerContextMenuProvider {

	public OspRulerContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		// TODO Auto-generated method stub
		super.buildContextMenu(menu);
		menu.appendToGroup(GEFActionConstants.GROUP_ADD, new OspRemoveGuideAction(getViewer()));
	}

}
