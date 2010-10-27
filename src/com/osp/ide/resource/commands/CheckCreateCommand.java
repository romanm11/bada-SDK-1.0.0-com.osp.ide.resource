package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.resinfo.FrameConst;

public class CheckCreateCommand extends OspAbstractCreateCommand {

	public CheckCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCheck(Object c) {
		if (c instanceof Check)
			model = (Check) c;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}
	
	@Override
	public void execute() {
		((Check) model).setVAlign(FrameConst.cszVAlign[1]);
		model.setBgStyle(FrameConst.cszBgStyle[1]);
		super.execute();
	}
}