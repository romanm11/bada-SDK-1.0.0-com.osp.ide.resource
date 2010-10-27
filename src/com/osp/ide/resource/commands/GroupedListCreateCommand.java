package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.resinfo.FrameConst;

public class GroupedListCreateCommand extends OspAbstractCreateCommand {

	public GroupedListCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setCustromControl(Object b) {
		if (b instanceof GroupedList)
			this.model = (GroupedList) b;
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