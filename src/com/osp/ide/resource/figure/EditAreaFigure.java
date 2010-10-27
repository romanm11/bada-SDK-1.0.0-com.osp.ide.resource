package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.documents.OspResourceManager;

public class EditAreaFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private AlignLayout layout;
	private String guideText = "";

	@SuppressWarnings("deprecation")
	public EditAreaFigure(String screen) {
		super(screen);
		layout = new AlignLayout();
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);
		layout.setVerticalAlignment(AlignLayout.ALIGN_TOPLEFT);

		textLabel.setFont(font);
		textLabel.setLabelAlignment(PositionConstants.LEFT);

		add(textLabel);
		textLabel.setVisible(false);
	}

	public void createImage() {
		if (rect == null || tag == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					SWT.LEFT, SWT.TOP, 10);
		} else {
			String text = textLabel.getText();
			if(text == null || text.isEmpty()) {
				textLabel.setText(guideText);
				text = guideText;
				setForegroundColor(ColorConstants.gray);
			} else {
				setForegroundColor(OspResourceManager.FormatRGB(tag.temp1));
			}
			
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(tag, size, null, SWT.LEFT, SWT.TOP);
		}
	}

	public void setText(String text) {
		textLabel.setText(text);
		createImage();
		repaint();
	}

	public void setGuideText(String text) {
		if (text == null || !text.equals(guideText)) {
			guideText = text;
			createImage();
			repaint();
		}
	}
	
	@Override
	public void setForegroundColor(Color fg) {
		super.setForegroundColor(fg);
		if (fg == null || !fg.equals(getForegroundColor())) {
			textLabel.setForegroundColor(fg);
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

		Rectangle labelRect = new Rectangle(10, 0, rect.width, rect.height);
		textLabel.setBounds(rect);
		this.setConstraint(textLabel, labelRect);
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

}
