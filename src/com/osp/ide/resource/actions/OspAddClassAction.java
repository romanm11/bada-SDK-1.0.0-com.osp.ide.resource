package com.osp.ide.resource.actions;

import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;

import com.osp.ide.resource.editform.OspAddClassWidzard;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspAddClassAction extends SelectionAction implements
		FrameConst {
	public static final String ADD_ACTION_ID = "com.osp.ide.resource.editor.frame.OspAddClassListenerAction";

	AbstractGraphicalEditPart part;

	public OspAddClassAction(IWorkbenchPart part) {
		super(part);
		setLazyEnablementCalculation(true);
	}

	@Override
	protected void init() {
		super.init();
		setText("Add Class");
		setId(ADD_ACTION_ID);
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD_DISABLED));
		setEnabled(false);
	}

	@Override
	protected boolean calculateEnabled() {
		ISelection selectedObj = getSelection();
		if(selectedObj instanceof StructuredSelection) {
			Object obj = ((StructuredSelection)selectedObj).getFirstElement();
			if(obj instanceof AbstractGraphicalEditPart)
				part = (AbstractGraphicalEditPart) obj;
			else
				part = null;
		} else
			part = null;
		
		if(part != null && part instanceof FormFramePart)
			return true;
		else
			return false;
	}

	@Override
	public void run() {

		IWorkbenchWindow window = this.getWorkbenchPart().getSite().getWorkbenchWindow();
		ISelection selection = window.getSelectionService().getSelection();
		 
		IWorkbenchWizard wizard;
		wizard = new OspAddClassWidzard(ResourceExplorer.getResourceView()
				.getCurProject(),selection);
		
		if (selection instanceof IStructuredSelection) {
			wizard.init(window.getWorkbench(),
					(IStructuredSelection) selection);
		} else {
			wizard.init(window.getWorkbench(),
					StructuredSelection.EMPTY);
		}

		Shell parent = window.getShell();
		WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.setHelpAvailable(false);
		dialog.create();
		dialog.open();
		
	}
	
}
