package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.resinfo.Layout;

public class SlidableGroupedListChangeLayoutCommand extends OspAbstractLayoutCommand {
	private SlidableGroupedList model;
	private Layout layout;
	private Layout oldLayout;

	@Override
	public void setConstraint(Rectangle rect) {
		this.layout.mode = model.getMode();
		this.layout.x = rect.x;
		this.layout.y = rect.y;
		this.layout.width = rect.width;
		this.layout.height = rect.height;
	}

	@Override
	public void setModel(Object model) {
		this.model = (SlidableGroupedList) model;
		this.oldLayout = ((SlidableGroupedList) model).getLayout();
		this.layout = new Layout(((SlidableGroupedList) model).getLayout());
	}

	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}
