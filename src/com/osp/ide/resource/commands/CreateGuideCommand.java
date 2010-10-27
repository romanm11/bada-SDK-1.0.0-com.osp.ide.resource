package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.actions.OspElementRuler;
import com.osp.ide.resource.model.FrameNode;

public class CreateGuideCommand extends Command {
	private OspElementGuide guide;
	private OspElementRuler parent;
	private int position;
	private Rectangle frameRect;

	public CreateGuideCommand(FrameNode frame, Rectangle frameRect, OspElementRuler ruler,
			int position) {
		super();
		this.parent = ruler;
		this.position = position;
		this.frameRect = frameRect;
		if (guide == null)
			guide = new OspElementGuide(!parent.isHorizontal());
		guide.setFrameRect(frameRect);
		guide.setFrame(frame);
		guide.showToolTip(position);
	}

	public boolean canUndo() {
		return true;
	}

	public void execute() {
		guide.setFrameRect(frameRect);
		guide.setPosition(position);
		parent.addGuide(guide);
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return super.canExecute();
	}

	@Override
	public Command chain(Command command) {
		// TODO Auto-generated method stub
		return super.chain(command);
	}

	public void undo() {
		parent.removeGuide(guide);
	}

	public void closeToolTip() {
		if(guide != null)
			guide.closeToolTip();
	}
}
