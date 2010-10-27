package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.ScrollPanel;

public class ScrollPanelCreateCommand extends OspAbstractCreateCommand {

	public ScrollPanelCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setScrollPanel(Object b) {
		if (b instanceof ScrollPanel)
			this.model = (ScrollPanel) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		super.execute();
		if(model != null && model.isScotia())
			((ScrollPanel)model).setBGColorTransparency(100);
	}
	
}