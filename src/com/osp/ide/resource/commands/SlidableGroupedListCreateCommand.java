package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.resinfo.FrameConst;

public class SlidableGroupedListCreateCommand extends OspAbstractCreateCommand {

	public SlidableGroupedListCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setListCtl(Object l) {
		if (l instanceof SlidableGroupedList)
			this.model = (SlidableGroupedList) l;
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
