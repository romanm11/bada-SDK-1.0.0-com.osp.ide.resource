package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Panel;

public class PanelCreateCommand extends OspAbstractCreateCommand {

	public PanelCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCustromControl(Object b) {
		if (b instanceof Panel)
			this.model = (Panel) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		super.execute();
		if(model != null && model.isScotia())
			((Panel)model).setBGColorTransparency(100);
	}
	
}