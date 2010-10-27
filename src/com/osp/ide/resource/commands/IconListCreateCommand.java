package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.resinfo.FrameConst;

public class IconListCreateCommand extends OspAbstractCreateCommand {

	public IconListCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setIconList(Object b) {
		if (b instanceof IconList)
			this.model = (IconList) b;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_ICONLIST, 0);
		super.execute();
	}
}