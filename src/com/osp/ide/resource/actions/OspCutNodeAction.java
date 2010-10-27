package com.osp.ide.resource.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.osp.ide.resource.commands.OspCutNodeCommand;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspCutNodeAction extends SelectionAction {
	public OspCutNodeAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(false);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("Cut");
		setId(ActionFactory.CUT.getId());
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
		setEnabled(false);
	}

	private Command createCutCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}

		OspCutNodeCommand cmd = new OspCutNodeCommand();
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			Object curIt = it.next();
			if (!(curIt instanceof EditPart))
				continue;
			EditPart ep = (EditPart) curIt;
			FrameNode node = (FrameNode) ep.getModel();
			if (!cmd.isCutableNode(node))
				continue;
			cmd.addElement(node);
		}

		return cmd;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

		super.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean calculateEnabled() {
		Command cmd = createCutCommand(getSelectedObjects());
		if (cmd == null)
			return false;
		return cmd.canExecute();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Command cmd = createCutCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
			
			IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
			
			ResourceExplorer view = (ResourceExplorer) page.findView(ResourceExplorer.ID);
			view.setFocus();
		}
	}

}
