package com.osp.ide.resource.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.part.OspAbstractEditPart;

public class OspPasteNodeCommand extends Command {
	private HashMap<FrameNode, FrameNode> list = new HashMap<FrameNode, FrameNode>();
	private IWorkbenchPart editPart;
	private String screen;
	private ISelection selection;

	public OspPasteNodeCommand(IWorkbenchPart part, String screen, ISelection iSelection) {
		super();
		editPart = part;
		this.screen = screen;
		selection = iSelection;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean canExecute() {
		ArrayList<FrameNode> bList = (ArrayList<FrameNode>) Clipboard
				.getDefault().getContents();
		if (bList == null || bList.isEmpty())
			return false;

		Iterator<FrameNode> it = bList.iterator();
		while (it.hasNext()) {
			Object node = it.next();
			if (isPastableNode(node)) {
				list.put((FrameNode) node, null);
			}
		}
		return list.size() > 0 ? true : false;
	}

	@Override
	public void execute() {
		redo();
	}

	@Override
	public void redo() {
		StringBuilder s;
		Object[] keyList = list.keySet().toArray();
		for (int i = 0; i < keyList.length; i++) {
			FrameNode node = (FrameNode) keyList[i];
			try {
				FrameNode clone = (FrameNode) node.clone();
				list.remove(node);
				list.put(node, clone);
			} catch (CloneNotSupportedException e) {
				s = new StringBuilder("Frame PasteCommand ");
				s.append(node.getClass().getSimpleName());
				s.append(" ");
				s.append(e.getClass().getSimpleName());
				s.append(" : ");
				s.append(e.getMessage());
				Activator.setErrorMessage("OspPasteNodeCommand.execute()", s
						.toString(), e);
			}
		}

		FrameNode parent = getParent();
		if(parent == null)
			return;
		
		Iterator<FrameNode> it = list.values().iterator();
		while (it.hasNext()) {
			FrameNode node = it.next();
			if (isPastableNode(node)) {
				node.name = node.getItem().Id = node.getFrameModel().MakeID(node.getType(), "");
				parent.addChild(node, true);
			}
		}
	}

	private FrameNode getParent() {
		FrameNode parent = null;
		if(selection instanceof StructuredSelection) {
			Object element = ((StructuredSelection)selection).getFirstElement();
			if(element instanceof OspAbstractEditPart) {
				FrameNode model = (FrameNode) ((OspAbstractEditPart) element).getModel();
				
				if(model instanceof Panel ||
						model instanceof OverlayPanel)
					parent = model;
				else
					parent = model.getFrameModel();
			}
		}
		
		return parent;
	}

	@Override
	public boolean canUndo() {
		return !(list.isEmpty());
	}

	@Override
	public void undo() {
		Iterator<FrameNode> it = list.values().iterator();
		while (it.hasNext()) {
			FrameNode node = it.next();
			if (isPastableNode(node)) {
				undoNode(node);
			}
		}
	}

	private void undoNode(FrameNode node) {
		node.getParent().removeChild(node);
		node.setLayout(node.getLayout());
		for(int i=node.getChildrenArray().size() - 1; i >= 0; i--)
			undoNode(node.getChildrenArray().get(i));
	}

	public boolean isPastableNode(Object node) {
		if (node instanceof Label || node instanceof Check
				|| node instanceof Slider || node instanceof EditField
				|| node instanceof EditArea || node instanceof Progress
				|| node instanceof Button || node instanceof ListControl
				|| node instanceof IconList || node instanceof CustomList
				|| node instanceof Flash
				|| node instanceof EditDate || node instanceof ColorPicker
				|| node instanceof ExpandableList
				|| node instanceof ExpandableList
				|| node instanceof GroupedList || node instanceof OverlayPanel
				|| node instanceof Panel || node instanceof SlidableGroupedList
				|| node instanceof SlidableList || node instanceof EditTime) {
			FrameNode model = ((FrameNode) node).getFrameModel();
			if (model instanceof Form || model instanceof Popup) {
				if (getFrameId().equals(model.getName())
						&& (model.getScreen() != null
								&& !model.getScreen().isEmpty() && screen
								.equals(model.getScreen()))
						&& model.getPart().isActive())
					return true;
				else
					return false;
			} else if (model instanceof PanelFrame) {
				if (getFrameId().equals(model.getName()))
					return true;
				else
					return false;
			}
		}
		return false;
	}

	private String getFrameId() {
		if(editPart == null)
			return "";
		
		return editPart.getTitleToolTip();
	}
}
