package com.osp.ide.resource.editpolicies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;

import com.osp.ide.resource.commands.OspDirectCellEditorCommand;
import com.osp.ide.resource.figure.ButtonFigure;
import com.osp.ide.resource.figure.CheckFigure;
import com.osp.ide.resource.figure.EditAreaFigure;
import com.osp.ide.resource.figure.EditFieldFigure;
import com.osp.ide.resource.figure.LabelFigure;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Label;

/**
 * EditPolicy for the direct editing of Activity names.
 * 
 * @author Daniel Lee
 */
public class OspDirectEditPolicy extends DirectEditPolicy {

	/**
	 * @see DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest request) {
		Object model = getHost().getModel();
		OspDirectCellEditorCommand cmd = new OspDirectCellEditorCommand();
		cmd.setSource((FrameNode) model);
		if (model instanceof Button) {
			cmd.setOldName(((Button) model).getText());
		} else if (model instanceof Check) {
			cmd.setOldName(((Check) model).getText());
		} else if (model instanceof EditField) {
			cmd.setOldName(((EditField) model).getText());
		} else if (model instanceof EditArea) {
			cmd.setOldName(((EditArea) model).getText());
		} else if (model instanceof Label) {
			cmd.setOldName(((Label) model).getText());
		}
		cmd.setName((String) request.getCellEditor().getValue());
		return cmd;
	}

	/**
	 * @see DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request) {
		Object model = getHost().getModel();
		String value = (String) request.getCellEditor().getValue();
		if (model instanceof Button) {
			((ButtonFigure) getHostFigure()).setText(value);
		} else if (model instanceof Check) {
			((CheckFigure) getHostFigure()).setText(value);
		} else if (model instanceof EditField) {
			((EditFieldFigure) getHostFigure()).setText(value);
		} else if (model instanceof EditArea) {
			((EditAreaFigure) getHostFigure()).setText(value);
		} else if (model instanceof Label) {
			((LabelFigure) getHostFigure()).setText(value);
		}
		// hack to prevent async layout from placing the cell editor twice.
		// getHostFigure().getUpdateManager().performUpdate();
	}

}
