package com.osp.ide.resource.commands;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.actions.OspElementRuler;
import com.osp.ide.resource.model.FrameNode;

public class DeleteGuideCommand extends Command {
	private OspElementRuler parent;
	private OspElementGuide guide;
	@SuppressWarnings("unchecked")
	private Map oldParts;

	public DeleteGuideCommand(OspElementGuide guide, OspElementRuler parent) {
		super();
		this.guide = guide;
		this.parent = parent;
	}

	public boolean canUndo() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public void execute() {
		oldParts = new HashMap(guide.getMap());
		Iterator iter = oldParts.keySet().iterator();
		while (iter.hasNext()) {
			guide.detachElement((FrameNode) iter.next());
		}
		parent.removeGuide(guide);
	}

	@SuppressWarnings("unchecked")
	public void undo() {
		parent.addGuide(guide);
		Iterator iter = oldParts.keySet().iterator();
		while (iter.hasNext()) {
			FrameNode element = (FrameNode) iter.next();
			guide.attachElement(element, ((Integer) oldParts.get(element))
					.intValue());
		}
	}
}
