package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;

public abstract class OspAbstractLayoutCommand extends Command{
	protected FrameNode model;
	protected Layout layout;
	protected Layout oldLayout;
	
	public abstract void setConstraint(Rectangle rect);
	public abstract void setModel(Object model);
	
	public void execute() {
		if(model.getParent() instanceof Form)
			model.minY = ((Form) model.getParent()).getMinY();
        else if(model.getParent() instanceof Popup)
            model.minY = ((Popup) model.getParent()).getMinY();
        else
        	model.minY = 0;
		
		model.setLayout(layout);
		String message = FrameConst.cszTag[model.getType()] + "(" + model.getName() + 
		") -   X:" + layout.x + "  Y:"
		+ (layout.y - model.minY) + "  W:"
		+ layout.width + "  H:" + layout.height;
		Activator.setStatusMessage(message);
	}

}
