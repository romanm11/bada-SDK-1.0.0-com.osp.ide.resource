package com.osp.ide.resource.resourceexplorer;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class DelegatePreview implements IViewActionDelegate {

	ResourceExplorer view;
	@Override
	public void init(IViewPart view) {
		// TODO Auto-generated method stub
		this.view = (ResourceExplorer) view;
	}

	@Override
	public void run(IAction action) {
		if (view.curProject == null || view.curProject.isEmpty()) {
			Shell shell = Display.getCurrent().getActiveShell();
			MessageDialog.openWarning(shell, ResourceExplorer.ACTION_SELECTLANGUAGE,
					"No project is selected. Please select a project and try again.");
			return;
		}

		Action previewAction = view.getPreviewAction();
		if(previewAction != null && previewAction.isEnabled())
		    previewAction.run();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
