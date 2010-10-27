package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.Layout;

public class OspAddCommand extends Command {

	private FrameNode parentModel;
	private FrameNode model;
	private Rectangle constraint;
	private Layout oldLayout;

	public OspAddCommand(EditPart child, Object constraint) {
		model = (FrameNode) child.getModel();
		if(constraint instanceof Rectangle)
			this.constraint = (Rectangle) constraint;
	}

	public void execute() {
		parentModel.addChild((FrameNode) model, true);
		
		if(constraint == null)
			return;
		
		oldLayout = model.getLayout();
		Layout layout = model.getLayout();
		layout.x = constraint.x;
		layout.y = constraint.y;
		layout.width = constraint.width;
		layout.height = constraint.height;
		model.setLayout(layout);
	}

	public void setParentModel(Object model) {
		parentModel = (FrameNode) model;
	}

	public void undo() {
		parentModel.removeChild((FrameNode) model);
		model.setLayout(oldLayout);
	}
}
