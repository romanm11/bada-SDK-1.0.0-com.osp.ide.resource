package com.osp.ide.resource.editpolicies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.commands.OspAbstractLayoutCommand;
import com.osp.ide.resource.commands.PanelFrameChangeLayoutCommand;
import com.osp.ide.resource.commands.PopupFrameChangeLayoutCommand;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.part.PanelFramePart;
import com.osp.ide.resource.part.PopupFramePart;

public class EditLayoutPolicy extends XYLayoutEditPolicy{

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
        OspAbstractLayoutCommand command = null;
        String message = "";
        Rectangle rect = (Rectangle) constraint;

        if (child instanceof PanelFramePart) {
            command = new PanelFrameChangeLayoutCommand();
            PanelFrame model = (PanelFrame) child.getModel();
            message = model.getName();
        }else if (child instanceof PopupFramePart) {
            command = new PopupFrameChangeLayoutCommand();
            Popup model = (Popup) child.getModel();
            message = model.getName();
        }
        
        if(command == null)
            return null;
        
        if (message != null && !message.isEmpty())
            message += " -   X:" + rect.x + "  Y:" + rect.y
                    + "  W:" + (rect.width - 4) + "  H:" + (rect.height - 4);
        else
            message = "";

        Activator.setStatusMessage(message);
        
        command.setModel(child.getModel());
        command.setConstraint((Rectangle) constraint);

        return command;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
        CompoundCommand resize = new CompoundCommand();
        Command c;
        GraphicalEditPart child;
        List<?> children = request.getEditParts();

        for (int i = 0; i < children.size(); i++) {
            if(!(children.get(i) instanceof GraphicalEditPart))
                continue;
            child = (GraphicalEditPart) children.get(i);

            if (!request.getType().equals(REQ_RESIZE_CHILDREN))
                continue;
            if(child instanceof FormFramePart)
                continue;
            if (child instanceof PanelFramePart &&
                !(request.getResizeDirection() == PositionConstants.NORTH || 
                    request.getResizeDirection() == PositionConstants.SOUTH))
                continue;

            c = createChangeConstraintCommand(
                    request,
                    child,
                    translateToModelConstraint(getConstraintFor(request, child)));
            resize.add(c);
        }
        return resize.unwrap();
    }

}
