package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Point;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspAbstractCreateCommand extends Command {
	protected FrameNode frm;
	protected FrameNode model;

	public void setFrame(Object d) {
		if (d instanceof Form)
			this.frm = (Form) d;
		else if(d instanceof PanelFrame)
			frm = (PanelFrame) d;
		else if(d instanceof Popup)
			frm = (Popup) d;
		else if(d instanceof Panel)
			frm = (Panel) d;
		else if(d instanceof OverlayPanel)
			frm = (OverlayPanel) d;
	}

	public FrameNode getFrame() {
		return this.frm;
	}

	@Override
	public boolean canExecute() {
		if (frm == null || model == null)
			return false;
		return true;
	}

	@Override
	public void execute() {
		Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[model.getType()], frm.getScreen());
		
		if(frm.getLayout().width < info.minSize.x || frm.getLayout().height < info.minSize.y) {
			if(frm instanceof PanelFrame || frm instanceof Panel || frm instanceof OverlayPanel)
				MessageDialog.openError(null, "Create " + FrameConst.cszTag[model.getType()], 
						"This Control cannot be inserted to the Panel because it is larger than the Panel.");
			else if(frm instanceof Popup)
				MessageDialog.openError(null, "Create " + FrameConst.cszTag[model.getType()], 
				"This Control cannot be inserted to the Popup because it is larger than the Popup.");

			return;
		}
		
		frm.addChild(model, true);
	}

	@Override
	public boolean canUndo() {
		if (frm == null || model == null)
			return false;
		return frm.contains(model);
	}

	@Override
	public void undo() {
		frm.removeChild(model);
	}
	
	protected void setOffsetLayout(Rectangle r) {
		if (model == null)
			return;

		String screen = "";
		if(frm != null) {
			screen = frm.getScreen();
		}
		
		ResourceExplorer view = ResourceExplorer.getResourceView();
		Tag_info taginfo = view.getImageInfo(
				FrameConst.cszTag[model.getType()], screen);
		int offset = 20;
		if(taginfo != null)
			offset = taginfo.dftlSize.x;
		
		Layout layout;
		if (frm.getModeIndex() == FrameConst.PORTRAIT) {
			layout = new Layout(FrameConst.PORTRAIT, r);
			model.setLayout(layout);
			layout = new Layout(FrameConst.LANDCAPE, r);
			layout.width = frm.getLayout(FrameConst.LANDCAPE).width - offset;
			model.setLayout(layout);
		} else if (frm.getModeIndex() == FrameConst.LANDCAPE) {
			layout = new Layout(FrameConst.LANDCAPE, r);
			model.setLayout(layout);
			layout = new Layout(FrameConst.PORTRAIT, r);
			layout.width = frm.getLayout(FrameConst.PORTRAIT).width - offset;
			model.setLayout(layout);
		}
	}
	
	protected void setNonResizeLayout(Rectangle r) {
		if (model == null)
			return;

		Layout layout;
		
        if(frm instanceof Form)
            model.minY = ((Form) frm).getMinY();
        else if(frm instanceof Popup)
            model.minY = ((Popup) frm).getMinY();
        else
            model.minY = 0;
        
        String message = FrameConst.cszTag[model.getType()] + "(" + model.getName() + 
        		") -   X:" + r.x + "  Y:"
                + (r.y - model.minY)
                + "  W:" + r.width + "  H:" + r.height;
        Activator.setStatusMessage(message);

        Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
            FrameConst.cszTag[model.getType()], frm.getScreen());
        int minX;
        if(info != null)
            minX = info.minSize.x;
        else
            minX = frm.getLayout().width;
        
		if (frm.getModeIndex() == FrameConst.PORTRAIT) {
			layout = new Layout(FrameConst.PORTRAIT, r);
			if (layout.y + layout.height > frm.getLayout(FrameConst.PORTRAIT).height)
				layout.y =  frm.getLayout(FrameConst.PORTRAIT).height - layout.height;
			model.setLayout(layout);
			layout = new Layout(FrameConst.LANDCAPE, r);
			if(model instanceof ColorPicker && info != null)
		        minX = Integer.parseInt(info.temp1);
			
			layout.width = minX;
			
			if (layout.y + layout.height > frm.getLayout(FrameConst.LANDCAPE).height)
				layout.y = frm.getLayout(FrameConst.LANDCAPE).height - layout.height;
			model.setLayout(layout);
		} else if (frm.getModeIndex() == FrameConst.LANDCAPE) {
			layout = new Layout(FrameConst.LANDCAPE, r);
            if(model instanceof ColorPicker && info != null)
                layout.width = Integer.parseInt(info.temp1);
			if (layout.y + layout.height > frm.getLayout(FrameConst.LANDCAPE).height)
				layout.y = frm.getLayout(FrameConst.LANDCAPE).height - layout.height;
			model.setLayout(layout);
			layout = new Layout(FrameConst.PORTRAIT, r);
            layout.width = minX;
            
			if (layout.y + layout.height > frm.getLayout(FrameConst.PORTRAIT).height)
				layout.y = frm.getLayout(FrameConst.PORTRAIT).height - layout.height;

			model.setLayout(layout);
		}
	}
	
	protected void setSuitableLayout(Rectangle r) {
		if (model == null)
			return;
		int lcdWidth = 0, lcdHeight = 0;
		Layout layout;
		lcdWidth = frm.getLayout().width;
		lcdHeight = frm.getLayout().height;
		if(frm instanceof Form)
			model.minY = ((Form) frm).getMinY();
        else if(frm instanceof Popup)
            model.minY = ((Popup) frm).getMinY();
		else
			model.minY = 0;

		String sScreen = "";

		org.eclipse.draw2d.geometry.Point minSize = new org.eclipse.draw2d.geometry.Point(
				0, 0);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		Tag_info taginfo = view.getImageInfo(
				FrameConst.cszTag[model.getType()], sScreen);
		if (taginfo != null)
			minSize = taginfo.minSize;

		if (minSize.x <= 0)
			minSize.x = FrameConst.defaultMinWidth[model.getType()];
		if (minSize.y <= 0)
			minSize.y = FrameConst.defaultMinHeight[model.getType()];

		if (r.height < minSize.y) {
			r.height = minSize.y;
			if (r.y + r.height > lcdHeight)
				r.y = lcdHeight - r.height;
		}
		if (r.width < minSize.x) {
			r.width = minSize.x;
			if (r.x + r.width > lcdWidth)
				r.x = lcdWidth - r.width;
		}

		if (r.x + r.width > lcdWidth) {
			r.width = lcdWidth - r.x;
			if (r.width < minSize.x) {
				r.width = minSize.x;
				r.x = lcdWidth - r.width;
			}
		}
		if (r.x <= 0) {
			r.width = r.x + r.width;
			r.x = 0;
		}
		if (r.y + r.height > lcdHeight) {
			r.height = lcdHeight - r.y;
			if (r.height < minSize.y) {
				r.height = minSize.y;
				r.y = lcdHeight - r.height;
			}
		}
		
        
        String message = FrameConst.cszTag[model.getType()] + "(" + model.getName() + 
        		") -   X:" + r.x + "  Y:"
                + (r.y - model.minY)
                + "  W:" + r.width + "  H:" + r.height;
        Activator.setStatusMessage(message);
		
		Point frameSize;
		Layout frameLayout;
        if (frm.getModeIndex() == FrameConst.PORTRAIT) {
            frameLayout = frm.getLayout(FrameConst.PORTRAIT);
            frameSize = new Point(frameLayout.width, frameLayout.height);
			layout = new Layout(FrameConst.PORTRAIT, r);
			model.setLayout(layout);
			r.x = (frameSize.y * r.x) / frameSize.x;
			r.y = (frameSize.x * r.y) / frameSize.y;
			if(r.y < model.minY)
			    r.y = model.minY;
			r.width = (frameSize.y * r.width) / frameSize.x;
			r.height = (frameSize.x * r.height) / frameSize.y;
			if(r.width < minSize.x)
				r.width = minSize.x;
			if(r.height < minSize.y)
				r.height = minSize.y;
			
            frameLayout = frm.getLayout(FrameConst.LANDCAPE);
			if(r.x + r.width > frameLayout.width)
				r.x -= r.x + r.width - frameLayout.width;
			if(r.y + r.height > frameLayout.height)
				r.y -= r.y + r.height - frameLayout.height;
			layout = new Layout(FrameConst.LANDCAPE, r);
			model.setLayout(layout);
		} else if (frm.getModeIndex() == FrameConst.LANDCAPE) {
            frameLayout = frm.getLayout(FrameConst.LANDCAPE);
            frameSize = new Point(frameLayout.width, frameLayout.height);
			layout = new Layout(FrameConst.LANDCAPE, r);
			model.setLayout(layout);
			r.x = (frameSize.y * r.x) / frameSize.x;
			r.y = (frameSize.x * r.y) / frameSize.y;
            if(r.y < model.minY)
                r.y = model.minY;
			r.width = (frameSize.y * r.width) / frameSize.x;
			r.height = (frameSize.x * r.height) / frameSize.y;
			if(r.width < minSize.x)
				r.width = minSize.x;
			if(r.height < minSize.y)
				r.height = minSize.y;
			
            frameLayout = frm.getLayout(FrameConst.PORTRAIT);
			if(r.x + r.width > frameLayout.width)
				r.x -= r.x + r.width - frameLayout.width;
			if(r.y + r.height > frameLayout.height)
				r.y -= r.y + r.height - frameLayout.height;
			layout = new Layout(FrameConst.PORTRAIT, r);
			model.setLayout(layout);
		}
	}
}
