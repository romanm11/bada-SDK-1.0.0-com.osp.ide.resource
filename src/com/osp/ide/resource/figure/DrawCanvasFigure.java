package com.osp.ide.resource.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.osp.ide.resource.part.DrawCanvasPart;

public class DrawCanvasFigure extends AbstactFigure {

	private XYLayout layout;
	private DrawCanvasPart part;

	// public static final int BORDER = 1;
	public DrawCanvasFigure(DrawCanvasPart part) {
		super("");
		this.part = part;
		layout = new XYLayout();
		setLayoutManager(layout);

		// setBorder(new LineBorder(BORDER));
	}

	@Override
	public void setSize(int w, int h) {
		super.setSize(w, h);
		Shell shell = Display.getCurrent().getActiveShell();
		MessageDialog.openError(shell, "Draw Canvas Figure", "SetSize : " + w
				+ ", " + h);
	}

	Rectangle oldRect = new Rectangle();

	@Override
	public void setBounds(Rectangle rect) {
		Object formFigure = getChildren().get(0);
		
		if(formFigure instanceof FormFrameFigure) {
			FormFrameFigure frame = (FormFrameFigure) formFigure;
			Rectangle rectD = frame.getLayout();
			if (!oldRect.equals(rect) && rectD.width != 0) {
				Rectangle newLayout = new Rectangle((rect.width - rectD.width) / 2,
						(rect.height - rectD.height) / 2, rectD.width, rectD.height);
				
				frame.setLayout(newLayout);
				oldRect = rect;
			}
		} else if(formFigure instanceof PanelFrameFigure) {
			PanelFrameFigure panel = (PanelFrameFigure) formFigure;
			Rectangle rectD = panel.getLayout();
			if (!oldRect.equals(rect) && rectD.width != 0) {
				Rectangle newLayout = new Rectangle((rect.width - rectD.width) / 2,
						(rect.height - rectD.height) / 2, rectD.width, rectD.height);
				
				panel.setLayout(newLayout);
				oldRect = rect;
			}
		} else if(formFigure instanceof PopupFrameFigure) {
			PopupFrameFigure popup = (PopupFrameFigure) formFigure;
			Rectangle rectD = popup.getLayout();
			if (!oldRect.equals(rect) && rectD.width != 0) {
				Rectangle newLayout = new Rectangle((rect.width - rectD.width) / 2,
						(rect.height - rectD.height) / 2, rectD.width, rectD.height);
				
				popup.setLayout(newLayout);
				oldRect = rect;
			}
		}
		super.setBounds(rect);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		
		if(part.getModelChildren().isEmpty())
			return;
		
		super.paintClientArea(graphics);
	}
}
