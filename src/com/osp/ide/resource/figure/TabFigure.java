package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;

public class TabFigure extends AbstactFigure {

	Image textImage = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/frameTab.png").createImage();
	org.eclipse.swt.graphics.Rectangle textImageRect = textImage.getBounds();
	Image iconImage = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/frameTab.png").createImage();
	org.eclipse.swt.graphics.Rectangle iconImageRect = textImage.getBounds();

	private Rectangle rect = null;
	private File controlImage;
	private boolean iconTab;
	private boolean textTab;

	public TabFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

	}

	public void createImage() {
		if (rect == null || tag == null)
			return;
		
		Tag_info info = tag.getClone();

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					5, 5, 5, 5);
		} else {
			if (!textTab) {
				Dimension size = new Dimension(rect.width, rect.height);
				createImage(info, size, null);
			} else {
				if (info == null || info.size() < 3)
					return;

				info.remove(1);
				Dimension size = new Dimension(rect.width, rect.height);
				createImage(info.subList(0, 2), size, null);
			}
		}
	}

	@Override
	public void deleteImage() {
		super.deleteImage();
		if (textImage != null) {
			textImage.dispose();
			textImage = null;
		}

		if (iconImage != null) {
			iconImage.dispose();
			iconImage = null;
		}
	}

	public void setName(String text) {
		textLabel.setText(text);
	}

	public void setLayout(Rectangle rect) {
		if (this.rect == null || this.rect.height != rect.height
				|| this.rect.width != rect.width) {
			this.rect = rect;
			createImage();
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

		if (bgImage != null)
			graphics.drawImage(bgImage, point.x, point.y);

		super.paintClientArea(graphics);
	}

	public void setControlImage(File image) {
		controlImage = image;
		createImage();
		repaint();
	}

	public void setTextTab(boolean setting) {
		textTab = setting;
		if (textTab == true)
			iconTab = false;
		createImage();
		repaint();
	}

	public void setIconTab(boolean setting) {
		iconTab = setting;
		if (iconTab == true)
			textTab = false;
		createImage();
		repaint();
	}
}