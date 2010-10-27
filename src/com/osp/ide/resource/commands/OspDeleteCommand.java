package com.osp.ide.resource.commands;

import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.model.FrameNode;

public class OspDeleteCommand extends Command {
	private FrameNode model;
	private FrameNode parentModel;

	public void execute() {
		this.parentModel.removeChild(model);
	}

	public void setModel(Object model) {
		this.model = (FrameNode) model;
	}

	public void setParentModel(Object model) {
		parentModel = (FrameNode) model;
	}

	public void undo() {
		this.parentModel.addChild(model, true);
	}
}
