package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.resinfo.FrameConst;

public class DatePickerCreateCommand extends OspAbstractCreateCommand {

	public DatePickerCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCustromControl(Object b) {
		if (b instanceof EditDate)
			this.model = (EditDate) b;
	}

	public void setLayout(Rectangle r) {
		setNonResizeLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_DATEPICKER, 0);
		super.execute();
	}
}