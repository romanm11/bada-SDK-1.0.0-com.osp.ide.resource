package com.osp.ide.resource.commands;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;

import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;

public class OspCopyNodeCommand extends Command {
	private ArrayList<FrameNode> list = new ArrayList<FrameNode>();

	public boolean addElement(FrameNode node) {
		FrameNode clone;
		try {
			clone = (FrameNode) node.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
		if (!list.contains(clone)) {
			return list.add(clone);
		}
		return false;
	}

	@Override
	public boolean canExecute() {
		if (list == null || list.isEmpty())
			return false;
		Iterator<FrameNode> it = list.iterator();
		while (it.hasNext()) {
			if (!isCopyableNode(it.next()))
				return false;
		}
		return true;
	}

	@Override
	public void execute() {
		if (canExecute())
			Clipboard.getDefault().setContents(list);
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	public boolean isCopyableNode(FrameNode node) {
		if (node instanceof Label || node instanceof Check
				|| node instanceof Slider || node instanceof EditField
				|| node instanceof EditArea || node instanceof Progress
				|| node instanceof Button || node instanceof ListControl
				|| node instanceof IconList || node instanceof CustomList
				|| node instanceof Flash
				|| node instanceof ColorPicker
				|| node instanceof EditDate
				|| node instanceof ExpandableList
				|| node instanceof GroupedList
				|| node instanceof OverlayPanel || node instanceof Panel
				|| node instanceof SlidableGroupedList
				|| node instanceof SlidableList
				|| node instanceof EditTime)

			return true;
		return false;
	}
}
