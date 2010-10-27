package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.resinfo.FrameConst;

public class SlidableListCreateCommand extends OspAbstractCreateCommand {

	public SlidableListCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setListCtl(Object l) {
		if (l instanceof SlidableList)
			this.model = (SlidableList) l;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_CUSTOMLIST, 0);
		super.execute();
	}
}
