package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.resinfo.FrameConst;

public class EditAreaCreateCommand extends OspAbstractCreateCommand {

	public EditAreaCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setEditArea(Object e) {
		if (e instanceof EditArea)
			this.model = (EditArea) e;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		((EditArea) model).setInputStyle(FrameConst.cszInputStyle[0]);
		super.execute();
	}
}
