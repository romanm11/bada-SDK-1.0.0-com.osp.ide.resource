package com.osp.ide.resource.actions;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.osp.ide.resource.commands.OspPasteNodeCommand;

public class OspPasteNodeAction extends SelectionAction
{
	private IWorkbenchPart editPart;
	private String screen;

	public OspPasteNodeAction(IWorkbenchPart part, String screen) {
		super(part);
		// force calculateEnabled() to be called in every context
		editPart = part;
		this.screen = screen;
		setLazyEnablementCalculation(false);
	}

	protected void init()
	{
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		setText("Paste");
		setId(ActionFactory.PASTE.getId());
		setHoverImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
		setDisabledImageDescriptor(sharedImages.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE_DISABLED));
		setEnabled(false);
	}
	
	private Command createPasteCommand() {
		return new OspPasteNodeCommand(editPart, screen, getSelection());
	}

	@Override
	protected boolean calculateEnabled() {
		Command command = createPasteCommand();
        return command != null && command.canExecute();
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return super.isEnabled();
	}

	@Override
	public void run() {
		Command command = createPasteCommand();
		if (command != null && command.canExecute())
			execute(command);
	}
}
