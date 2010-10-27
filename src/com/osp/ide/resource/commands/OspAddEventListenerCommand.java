package com.osp.ide.resource.commands;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.osp.ide.resource.part.ButtonPart;
import com.osp.ide.resource.part.DrawCanvasPart;
import com.osp.ide.resource.part.FormFramePart;

public class OspAddEventListenerCommand extends Command {
	public static final String HEADER_FIRST = "public Application";

	AbstractGraphicalEditPart part = null;

	public OspAddEventListenerCommand() {
		super();
	}

	public OspAddEventListenerCommand(AbstractGraphicalEditPart part) {
		this.part = part;
	}

	@Override
	public boolean canExecute() {
		if (!(part instanceof DrawCanvasPart) &&
				!(part instanceof FormFramePart))
			return true;
		return false;
	}

	@Override
	public void execute() {
		if(part instanceof ButtonPart) {
			
		}
	}
}