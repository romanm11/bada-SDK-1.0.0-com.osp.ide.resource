package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;

public class FlashControlFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private LineBorder border = new LineBorder(2);

	public FlashControlFigure(String screen) {
		super(screen);
		
		border.setColor(ColorConstants.lightBlue);
		setBorder(border);
		setForegroundColor(ColorConstants.black);

		image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/twFlash.jpg").createImage();
	}

	public void createImage() {
		if (rect == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height), 5, 5, 5,
					5);
		} else {
			if (tag != null && tag.size() > 0) {
				setOpaque(false);
				String text = textLabel.getText();
				if (text == null || text.isEmpty())
					textLabel.setText("Flash");
				
				Dimension size = new Dimension(rect.width, rect.height);
				createImage(tag, size, null, SWT.CENTER, SWT.CENTER);
			} else {
				setOpaque(true);
			}
		}
	}

	public void setName(String text) {
		if (text == null || !text.equals(textLabel.getText())) {
			textLabel.setText(text);
			createImage();
			repaint();
		}
	}

	@Override
	public void setBackgroundColor(Color bg) {
		if (bg == null || !bg.equals(getBackgroundColor())) {
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
		else {
			Rectangle imageRect;
			if (rect.width > rect.height) {
				imageRect = new Rectangle(point.x + (rect.width - rect.height)
						/ 2, point.y, rect.height, rect.height);
			} else {
				imageRect = new Rectangle(point.x, point.y
						+ (rect.height - rect.width) / 2, rect.width,
						rect.width);

			}
			graphics.drawImage(image, new Rectangle(0, 0,
					image.getBounds().width, image.getBounds().height),
					imageRect);
		}

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