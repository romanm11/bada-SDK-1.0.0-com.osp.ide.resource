package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.resinfo.FrameConst;

public class ProgressCreateCommand extends OspAbstractCreateCommand {

	public ProgressCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setProgress(Object p) {
		if (p instanceof Progress)
			this.model = (Progress) p;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_PROGRESS, 0);
		super.execute();
	}
}