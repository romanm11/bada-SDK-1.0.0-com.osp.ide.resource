package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.ColorPicker;

public class ColorPickerCreateCommand extends OspAbstractCreateCommand {

	public ColorPickerCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCustromControl(Object b) {
		if (b instanceof ColorPicker)
			this.model = (ColorPicker) b;
	}

	public void setLayout(Rectangle r) {
		setNonResizeLayout(r);
	}
}