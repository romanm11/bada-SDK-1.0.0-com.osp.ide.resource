package com.osp.ide.resource.actions;

import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.osp.ide.resource.commands.OspCopyNodeCommand;
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

public class OspCopyNodeAction extends SelectionAction {
	public OspCopyNodeAction(IWorkbenchPart part) {
		super(part);
		// force calculateEnabled() to be called in every context
		setLazyEnablementCalculation(false);
	}

	@Override
	protected void init() {
		super.init();
		ISharedImages sharedImages = PlatformUI.getWorkbench()
				.getSharedImages();
		setText("Copy");
		setId(ActionFactory.COPY.getId());
		setHoverImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
		setDisabledImageDescriptor(sharedImages
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY_DISABLED));
		setEnabled(false);
	}

	private Command createCopyCommand(List<Object> selectedObjects) {
		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return null;
		}

		OspCopyNodeCommand cmd = new OspCopyNodeCommand();
		Iterator<Object> it = selectedObjects.iterator();
		while (it.hasNext()) {
			Object curIt = it.next();
			if (!(curIt instanceof EditPart))
				continue;
			EditPart ep = (EditPart) curIt;
			FrameNode node = (FrameNode) ep.getModel();
			if (!cmd.isCopyableNode(node))
				continue;
			cmd.addElement(node);
		}
		return cmd;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> list = getSelectedObjects();
		if (list == null || list.isEmpty())
			return false;
		 Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			Object curIt = it.next();
			if (!(curIt instanceof EditPart))
				continue;
			EditPart ep = (EditPart) curIt;
			FrameNode node = (FrameNode) ep.getModel();
			if (!isCopyableNode(node))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Command cmd = createCopyCommand(getSelectedObjects());
		if (cmd != null && cmd.canExecute()) {
			cmd.execute();
		}
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
