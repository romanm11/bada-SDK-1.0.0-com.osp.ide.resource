package com.osp.ide.resource.commands;

import java.util.Iterator;

import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.Layout;

public class MoveGuideCommand extends Command {
	private int pDelta;
	private OspElementGuide guide;

	public MoveGuideCommand(OspElementGuide guide, int positionDelta) {
		super();
		this.guide = guide;
		pDelta = positionDelta;
	}

	public void execute() {
		guide.setPosition(guide.getPosition() + pDelta);
		Iterator<?> iter = guide.getParts().iterator();
		while (iter.hasNext()) {
			FrameNode element = (FrameNode) iter.next();
			Layout layout = element.getLayout();
			if (guide.isHorizontal()) {
				layout.y += pDelta;
			} else {
				layout.x += pDelta;
			}
			element.setLayout(layout);
		}
	}

	public void undo() {
		guide.setPosition(guide.getPosition() - pDelta);
		Iterator<?> iter = guide.getParts().iterator();
		while (iter.hasNext()) {
			FrameNode element = (FrameNode) iter.next();
			Layout layout = new Layout(element.getLayout());
			if (guide.isHorizontal()) {
				layout.y -= pDelta;
			} else {
				layout.x -= pDelta;
			}
			element.setLayout(layout);
		}
	}
}
