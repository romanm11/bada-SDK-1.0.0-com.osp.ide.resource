package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.resinfo.FrameConst;

public class SliderCreateCommand extends OspAbstractCreateCommand {

	public SliderCreateCommand() {
		super();
		frm = null;
		model = null;
	}

	public void setSlide(Object r) {
		if (r instanceof Slider)
			this.model = (Slider) r;
	}

	public void setLayout(Rectangle r) {
		setSuitableLayout(r);
	}

	@Override
	public void execute() {
		model.setBgStyle(FrameConst.cszBgStyle[1]);
		super.execute();
	}
}