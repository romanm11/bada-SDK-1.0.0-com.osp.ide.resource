package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.resinfo.Layout;

public class DatePickerChangeLayoutCommand extends
		OspAbstractLayoutCommand {

	public void setConstraint(Rectangle rect) {
		this.layout.mode = model.getMode();
		this.layout.x = rect.x;
		this.layout.y = rect.y;
	}

	public void setModel(Object model) {
		this.model = (EditDate) model;
		this.oldLayout = ((EditDate) model).getLayout();
		this.layout = new Layout(((EditDate) model).getLayout());
	}

	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}
