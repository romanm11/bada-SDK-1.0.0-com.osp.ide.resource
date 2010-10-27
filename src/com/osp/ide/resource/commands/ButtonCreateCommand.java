package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Button;

public class ButtonCreateCommand extends OspAbstractCreateCommand {

	public ButtonCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setButton(Object b) {
		if (b instanceof Button)
			model = (Button) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

}