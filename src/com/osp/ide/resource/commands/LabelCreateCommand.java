package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.resinfo.FrameConst;

public class LabelCreateCommand extends OspAbstractCreateCommand {

	public LabelCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setLabel(Object l) {
		if (l instanceof Label)
			this.model = (Label) l;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setStyle(FrameConst.STYLE_LABEL, 0);
		((Label) model)
				.setTextStyle(FrameConst.cszStyle[FrameConst.STYLE_LABEL_TEXT][0]);

		if (((Label) model).getTextSize() == 0)
			((Label) model).setTextSize(34);
		super.execute();
		
		if(model != null && model.isScotia())
			((Label)model).setBGColorTransparency(100);
	}
}
