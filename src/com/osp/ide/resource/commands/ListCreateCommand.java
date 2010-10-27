package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.resinfo.FrameConst;

public class ListCreateCommand extends OspAbstractCreateCommand {

	public ListCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setListCtl(Object l) {
		if (l instanceof ListControl)
			this.model = (ListControl) l;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_LIST, 0);
		((ListControl) model).setListItemFormat(0);
		super.execute();
	}
}
