package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;

public class DatePickerFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private String TitleText = "";

	public DatePickerFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

		textLabel.setForegroundColor(ColorConstants.darkGray);

		image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/twDatePicker.png").createImage();
	}

	public void createImage() {
		if (rect == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height), 5, 5, 5,
					5);
		} else {
			Dimension size = new Dimension(rect.width, rect.height);
			if(tag != null && tag.size() != 0) {
				Tag_info info = tag.getClone();
				if(info.minSize.x > rect.width) {
					info.get(0).setHAlign(false);
					info.get(0).setLeft(true);
				}
				createImage(info, size, null);
			} else
				createImage(image, new Dimension(rect.width, rect.height)
					, 5, 5, 5, 5);
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
		controlImage = image;
		createImage();
		repaint();
	}

	public void setTitleText(String text) {
		TitleText = text;
		repaint();
	}
	
	public String getTitleText() {
		return TitleText;
	}

}