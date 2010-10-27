package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class ScrollPanelFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private float opacity = 100;
    private Color backColor = ColorConstants.black;

	public ScrollPanelFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);
		
		textLabel.setForegroundColor(ColorConstants.darkGray);
		if(isScotia()) {
			backColor = blue_gray;
			setOpaque(true);
		}
		setBackgroundColor(backColor);
	}

	public void createImage() {
		if (rect == null)
			return;

		Dimension size = new Dimension(rect.width, rect.height);
		if(isScotia()) {
		    createImage(tag, size, backColor, 1.0f);
		} else if(backColor == null)
	        createImage(tag, size, getBackgroundColor(), opacity * 0.007f + 0.3f);
		else
		    createImage(tag, size, backColor, opacity * 0.007f + 0.3f);
	}

	public void setName(String text) {
		if (text == null || !text.equals(textLabel.getText())) {
			textLabel.setText(text);
			createImage();
			repaint();
		}
	}

    public void setBackColor(Color bg) {
        if (bg == null || !bg.equals(backColor)) {
            backColor  = bg;
            createImage();
            repaint();
        }
    }

	public void setLayout(Rectangle rect) {
		if (this.rect == null || this.rect.height != rect.height
				|| this.rect.width != rect.width) {
			this.rect = rect;
			createImage();
			repaint();
		}

		if(getParent() != null)
			getParent().setConstraint(this, rect);
	}

	@Override
	public void addLayoutListener(LayoutListener listener) {
		super.addLayoutListener(listener);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {

		Point point = getLocation();

		if (bgImage != null && !bgImage.isDisposed())
			graphics.drawImage(bgImage, point.x, point.y);

		super.paintClientArea(graphics);
	}

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}
	
	public void setOpacity(float opacity) {
		if (opacity != this.opacity) {
			this.opacity = opacity;
			createImage();
			repaint();
		}
	}
}