package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class OverlayPanelFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private float opacity = 100;

	public OverlayPanelFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		setBackgroundColor(ColorConstants.black);

		textLabel.setForegroundColor(ColorConstants.darkGray);
	}

	public void createImage() {
		if (rect == null)
			return;

		Dimension size = new Dimension(rect.width, rect.height);
		createImage(tag, size, getBackgroundColor(), opacity * 0.009f + 0.1f);
	}

	public void setName(String text) {
		textLabel.setText(text);
	}

	@Override
	public void setBackgroundColor(Color bg) {
		if (bg == null || !bg.equals(getBackgroundColor())) {
			if (bg == null)
				bg = ColorConstants.black;
			super.setBackgroundColor(bg);
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

	void setOpacity(float opacity) {
		if (opacity != this.opacity) {
			this.opacity = opacity;
			createImage();
			repaint();
		}
	}

}