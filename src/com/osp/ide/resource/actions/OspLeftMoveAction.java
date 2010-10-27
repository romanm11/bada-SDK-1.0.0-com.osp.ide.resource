package com.osp.ide.resource.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import com.osp.ide.resource.commands.OspLeftMoveCommand;
import com.osp.ide.resource.model.FrameNode;

public class OspLeftMoveAction extends SelectionAction {
	public static final String ID = "com.osp.ide.resource.frame.LeftMoveAction";

	public OspLeftMoveAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(false);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("Left Move");
		setId(ID);
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	private Command createMoveCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}

		OspLeftMoveCommand cmd = new OspLeftMoveCommand();
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			Object curIt = it.next();
			if (!(curIt instanceof EditPart))
				continue;
			EditPart ep = (EditPart) curIt;
			FrameNode node = (FrameNode) ep.getModel();
			if (!cmd.isMoveableNode(node))
				continue;
			cmd.addElement(node);
		}
		return cmd;
	}

	@Override
	protected boolean calculateEnabled() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Command cmd = createMoveCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
		}
	}

}
