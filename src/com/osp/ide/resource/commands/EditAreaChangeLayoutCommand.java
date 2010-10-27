package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.resinfo.Layout;

public class EditAreaChangeLayoutCommand extends OspAbstractLayoutCommand {

	public void setConstraint(Rectangle rect) {
		this.layout.mode = model.getMode();
		this.layout.x = rect.x;
		this.layout.y = rect.y;
		this.layout.width = rect.width;
		this.layout.height = rect.height;
	}

	public void setModel(Object model) {
		this.model = (EditArea) model;
		this.oldLayout = ((EditArea) model).getLayout();
		this.layout = new Layout(((EditArea) model).getLayout());
	}

	public void undo() {
		this.model.setLayout(this.oldLayout);
	}
}
