package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.OverlayPanel;

public class OverlayPanelCreateCommand extends OspAbstractCreateCommand {

	public OverlayPanelCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCustromControl(Object b) {
		if (b instanceof OverlayPanel)
			this.model = (OverlayPanel) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		((OverlayPanel) model).setBGColorTransparency(100);
		super.execute();
	}
}