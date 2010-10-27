package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.osp.ide.resource.common.Tag_info;

public class ProgressFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;

	public ProgressFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		textLabel.setForegroundColor(ColorConstants.darkGray);
		add(textLabel);
	}

	public void createImage() {
		if (rect == null)
			return;

		// BufferedImage swtImage;
		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height), 5, 5, 5,
					5);
		} else {
			if(tag == null || tag.get(2) == null)
				return;
			Tag_info localTag = tag.getClone();
			localTag.get(2).setSizeX(
					rect.width / 2 - localTag.get(2).getSpaceW());
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(localTag, size, null);
		}

		// Color fgColor = getForegroundColor();
		// if (leftText != null && !leftText.isEmpty())
		// swtImage = TWImageUtil.drawStringAWT(swtImage, fgColor, leftText,
		// getFont(),
		// SWT.LEFT, SWT.BOTTOM, 10);
		// if (centerText != null && !centerText.isEmpty())
		// swtImage = TWImageUtil.drawStringAWT(swtImage, fgColor, centerText,
		// getFont(),
		// SWT.CENTER, SWT.BOTTOM, 10);
		// if (rightText != null && !rightText.isEmpty())
		// swtImage = TWImageUtil.drawStringAWT(swtImage, fgColor, rightText,
		// getFont(),
		// SWT.RIGHT, SWT.BOTTOM, 10);

		// if(bgImage != null)
		// bgImage.dispose();
		// bgImage = null;
		// bgImage = new Image(Display.getDefault(), TWImageUtil
		// .convertToSWT(swtImage));
	}

	// public void setText(String text) {
	// textLabel.setText(text);
	// }
	//
	// public Label getLabel() {
	// return this.textLabel;
	// }

	// @Override
	// public void setBackgroundColor(Color bg) {
	// super.setBackgroundColor(bg);
	// createImage();
	// repaint();
	// }

	// @Override
	// public void setForegroundColor(Color fg) {
	// super.setForegroundColor(fg);
	// createImage();
	// repaint();
	// }

	public void setLayout(Rectangle rect) {
		if (this.rect == null || this.rect.height != rect.height
				|| this.rect.width != rect.width) {
			this.rect = rect;
			createImage();
		}

		Rectangle labelRect = new Rectangle(0, 0, rect.width, rect.height);
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
		controlImage = image;
		createImage();
		repaint();
	}

	// public void setLeftText(String leftText) {
	// this.leftText = leftText;
	// createImage();
	// repaint();
	// }
	//
	// public void setCenterText(String centerText) {
	// this.centerText = centerText;
	// createImage();
	// repaint();
	// }
	//
	// public void setRightText(String rightText) {
	// this.rightText = rightText;
	// createImage();
	// repaint();
	// }
}
