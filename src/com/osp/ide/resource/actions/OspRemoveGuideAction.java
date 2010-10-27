package com.osp.ide.resource.actions;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.internal.ui.rulers.RulerEditPart;
import org.eclipse.jface.action.Action;

@SuppressWarnings("restriction")
public class OspRemoveGuideAction extends Action {
	public static final String MESSAGE = "Remove All Guides";
	private EditPartViewer viewer;

	public OspRemoveGuideAction(EditPartViewer ruler) {
		super(MESSAGE);
		viewer = ruler;
		setToolTipText(MESSAGE);
	}
	
	public void run() {
		OspElementRulerProvider provider = (OspElementRulerProvider) ((RulerEditPart)viewer.getRootEditPart().getChildren()
				.get(0)).getRulerProvider();
		
		provider.removeAllGuides();
	}
}
