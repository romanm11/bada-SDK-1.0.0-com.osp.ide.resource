package com.osp.ide.resource.figure;

import java.awt.image.BufferedImage;
import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.common.ImageUtil;

public class SoftKeyOptionFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private Image imageOption;

	public SoftKeyOptionFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		textLabel.setForegroundColor(ColorConstants.darkGray);
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void setLayout(Rectangle rect) {
		if (this.rect == null || this.rect.height != rect.height
				|| this.rect.width != rect.width) {
			this.rect = rect;
			createImage();
			repaint();
		}

		this.rect = rect;
		getParent().setConstraint(this, rect);
	}

	@Override
	public void setBackgroundColor(Color bg) {
		Color oldColor = getBackgroundColor();
		super.setBackgroundColor(bg);
		if (!oldColor.equals(bg)) {
			createImage();
			repaint();
		}
	}

	public void createImage() {
		if (rect == null)
			return;

		if (tag == null || tag.size() < 4)
			return;

		BufferedImage option;
		Dimension size = new Dimension(tag.get(4).getSize());

		option = makeImage(tag.get(4), size, null);
		if (imageOption != null)
			imageOption.dispose();
		imageOption = null;
		imageOption = new Image(Display.getDefault(), ImageUtil
				.convertToSWT(option));

	}

	@Override
	public void deleteImage() {
		super.deleteImage();
		if (imageOption != null) {
			imageOption.dispose();
			imageOption = null;
		}

	}

	@Override
	public void addLayoutListener(LayoutListener listener) {
		super.addLayoutListener(listener);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();

		graphics.drawImage(imageOption, point.x, point.y);

		super.paintClientArea(graphics);
	}

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}
}