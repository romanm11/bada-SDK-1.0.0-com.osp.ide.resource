package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.resinfo.FrameConst;

public class EditFieldCreateCommand extends OspAbstractCreateCommand {

	public EditFieldCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setEditField(Object e) {
		if (e instanceof EditField)
			this.model = (EditField) e;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_EDITFIELD, 0);
		((EditField) model).setInputStyle(FrameConst.cszInputStyle[0]);
		super.execute();
	}
}
