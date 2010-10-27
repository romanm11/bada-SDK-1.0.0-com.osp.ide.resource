package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.resinfo.Layout;

public class GroupedListChangeLayoutCommand extends OspAbstractLayoutCommand {

	public void setConstraint(Rectangle rect) {
		this.layout.mode = model.getMode();
		this.layout.x = rect.x;
		this.layout.y = rect.y;
		this.layout.width = rect.width;
		this.layout.height = rect.height;
	}

	public void setModel(Object model) {
		this.model = (GroupedList) model;
		this.oldLayout = ((GroupedList) model).getLayout();
		this.layout = new Layout(((GroupedList) model).getLayout());
	}

	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}
