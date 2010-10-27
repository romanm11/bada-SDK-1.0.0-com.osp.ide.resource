package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.resinfo.FrameConst;

public class TimePickerCreateCommand extends OspAbstractCreateCommand {

	public TimePickerCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setModel(Object b) {
		if (b instanceof EditTime)
			this.model = (EditTime) b;
	}

	public void setLayout(Rectangle r) {
		setNonResizeLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_TIMEPICKER, 0);
		super.execute();
	}
}