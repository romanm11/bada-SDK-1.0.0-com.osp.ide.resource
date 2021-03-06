package com.osp.ide.resource.figure;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.common.ImageUtil;

public class SoftKeyLeftFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private String softKeyText = "";
	private File softKey0Icon;
	private Image image0;
	private int iconWidth = 60;
	private int iconHeight = 60;

	public SoftKeyLeftFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		textLabel.setForegroundColor(new Color(null, 0, 128, 255));
		setForegroundColor(new Color(null, 255, 255, 255));
		
        if(tag != null) {
            try {
                iconWidth = iconHeight = Integer.parseInt(tag.temp1);
            } catch(NumberFormatException e) {
            }
        }
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

	public void setSoftKeyText(String softkeyText) {
		String oldString = softKeyText;
		softKeyText = softkeyText;
		if (oldString != null && !oldString.equals(softkeyText)) {
			createImage();
			repaint();
		}
	}

	public boolean isSoftkey0Text() {
		if (softKeyText != null && !softKeyText.isEmpty())
			return true;
		return false;
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

		BufferedImage left;
		Dimension size = new Dimension(tag.get(0).getSize());
		Dimension textSize = new Dimension(tag.get(1).getSize());

		if (isSoftkey0Text()) {
			left = createImage(tag.get(1), textSize, null, softKeyText,
					SWT.CENTER, SWT.CENTER, tag.textlMargin,
					tag.textrMargin);
		} else if (softKey0Icon == null) {
			left = makeImage(tag.subList(0, 1), size, null);
		} else {
			left = makeImage(tag.subList(0, 1), size, null);
			Dimension iconSize = new Dimension (iconWidth, iconHeight);
			BufferedImage temp = getBufferedImage(softKey0Icon, iconSize);
			Graphics2D g2 = left.createGraphics();
			g2.drawImage(temp, (size.width - iconWidth) / 2, 
					(size.height - iconHeight) / 2, null);
			g2.dispose();
		}

		if (image0 != null)
			image0.dispose();
		image0 = null;
		image0 = new Image(Display.getDefault(), ImageUtil.convertToSWT(left));

	}

	@Override
	public void deleteImage() {
		super.deleteImage();
		if (image0 != null) {
			image0.dispose();
			image0 = null;
		}
	}

	@Override
	public void addLayoutListener(LayoutListener listener) {
		super.addLayoutListener(listener);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();

		graphics.drawImage(image0, point.x, point.y);

		super.paintClientArea(graphics);
	}

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}

	public void setSoftKey0Icon(File image) {
		if (image == null || !image.equals(softKey0Icon)) {
			softKey0Icon = image;
			createImage();
			repaint();
		}
	}
}