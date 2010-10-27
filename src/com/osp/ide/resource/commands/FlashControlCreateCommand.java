package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.resinfo.FrameConst;

public class FlashControlCreateCommand extends OspAbstractCreateCommand {

	public FlashControlCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setFlashControl(Object b) {
		if (b instanceof Flash)
			this.model = (Flash) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_FLASHCONTROL, 1);
		super.execute();
	}
}