package com.osp.ide.resource.figure;

import java.io.File;
import java.util.Vector;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.resinfo.FrameConst;

public class ButtonFigure extends AbstactFigure {

	private Rectangle rect = null;
	private AlignLayout layout;
	private File normalBitmap;
	private File normalBGBitmap;

	private int pointX;
	private int pointY;

	@SuppressWarnings("deprecation")
	public ButtonFigure(String screen) {
		super(screen);
		layout = new AlignLayout();
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);

		textLabel.setFont(font);
		textLabel.setVisible(false);
		add(textLabel);
	}

	@Override
	public void deleteImage() {
		// TODO Auto-generated method stub
		super.deleteImage();
	}

	public void createImage() {
		if (rect == null || tag == null)
			return;

		Vector<File_Info> nBitmap = new Vector<File_Info>();
		if (normalBitmap != null) {
			File_Info fInfo = new File_Info();
			fInfo.image = normalBitmap;
			fInfo.location = new Point(pointX, pointY);
			fInfo.size = new Dimension(rect.width, rect.height);
			nBitmap.add(fInfo);
		}

		if (normalBGBitmap != null) {
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(normalBGBitmap, size, nBitmap, getSWTHAlign(),
					getSWTVAlign(), tag.textlMargin,
					tag.textrMargin);
		} else {
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(tag, size, nBitmap, "", getSWTHAlign(),
					getSWTVAlign());
		}
	}

	public void setText(String text) {
		if (text == null || !textLabel.getText().equals(text)) {
			textLabel.setText(text);
			createImage();
			repaint();
		}
	}

	@Override
	public void setForegroundColor(Color fg) {
		if (fg == null || !fg.equals(getForegroundColor())) {
			super.setForegroundColor(fg);
			textLabel.setForegroundColor(fg);
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

	public int getSWTVAlign() {
		switch (layout.verticalAlignment) {
		case AlignLayout.ALIGN_TOPLEFT:
			return SWT.TOP;
		case AlignLayout.ALIGN_CENTER:
			return SWT.CENTER;
		case AlignLayout.ALIGN_BOTTOMRIGHT:
			return SWT.BOTTOM;
		}
		return 0;
	}

	public int getSWTHAlign() {
		switch (layout.getMinorAlignment()) {
		case AlignLayout.ALIGN_TOPLEFT:
			return SWT.LEFT;
		case AlignLayout.ALIGN_CENTER:
			return SWT.CENTER;
		case AlignLayout.ALIGN_BOTTOMRIGHT:
			return SWT.RIGHT;
		}
		return 0;
	}

	public void setHAlign(int align) {
		if (getHAlign() != align) {
			switch (align) {
			case FrameConst.ALIGN_LEFT:
				layout.setMinorAlignment(AlignLayout.ALIGN_TOPLEFT);
				break;
			case FrameConst.ALIGN_CENTER:
				layout.setMinorAlignment(AlignLayout.ALIGN_CENTER);
				break;
			case FrameConst.ALIGN_RIGHT:
				layout.setMinorAlignment(AlignLayout.ALIGN_BOTTOMRIGHT);
				break;
			}
			if (rect != null) {
				Rectangle labelRect = new Rectangle(0, 0, rect.width,
						rect.height);
				textLabel.setBounds(rect);
				this.setConstraint(textLabel, labelRect);
			}
			createImage();
			repaint();
		}
	}

	public int getHAlign() {
		switch (layout.getMinorAlignment()) {
		case AlignLayout.ALIGN_TOPLEFT:
			return FrameConst.ALIGN_LEFT;
		case AlignLayout.ALIGN_CENTER:
			return FrameConst.ALIGN_CENTER;
		case AlignLayout.ALIGN_BOTTOMRIGHT:
			return FrameConst.ALIGN_RIGHT;
		}
		return 0;
	}

	public void setVAlign(int align) {
		if (getVAlign() != align) {
			switch (align) {
			case FrameConst.ALIGN_TOP:
				layout.setVerticalAlignment(AlignLayout.ALIGN_TOPLEFT);
				break;
			case FrameConst.ALIGN_MIDDLE:
				layout.setVerticalAlignment(AlignLayout.ALIGN_CENTER);
				break;
			case FrameConst.ALIGN_BOTTOM:
				layout.setVerticalAlignment(AlignLayout.ALIGN_BOTTOMRIGHT);
				break;
			}
			if (rect != null) {
				Rectangle labelRect = new Rectangle(0, 0, rect.width,
						rect.height);
				textLabel.setBounds(rect);
				this.setConstraint(textLabel, labelRect);
			}
			createImage();
			repaint();
		}
	}

	public int getVAlign() {
		switch (layout.verticalAlignment) {
		case AlignLayout.ALIGN_TOPLEFT:
			return FrameConst.ALIGN_TOP;
		case AlignLayout.ALIGN_CENTER:
			return FrameConst.ALIGN_MIDDLE;
		case AlignLayout.ALIGN_BOTTOMRIGHT:
			return FrameConst.ALIGN_BOTTOM;
		}
		return 0;
	}

	public void setNormalBitmap(File normalBitmapPath) {
		if (normalBitmap == null || !normalBitmap.equals(normalBitmapPath)) {
			normalBitmap = normalBitmapPath;
			createImage();
			repaint();
		}
	}

	public void setNormalBGBitmap(File normalBGBitmapPath) {
		if (normalBGBitmap == null
				|| !normalBGBitmap.equals(normalBGBitmapPath)) {
			normalBGBitmap = normalBGBitmapPath;
			createImage();
			repaint();
		}
	}

	public void setPointX(int pointX) {
		if (this.pointX != pointX) {
			this.pointX = pointX;
			createImage();
			repaint();
		}
	}

	public void setPointY(int pointY) {
		if (this.pointY != pointY) {
			this.pointY = pointY;
			createImage();
			repaint();
		}
	}

	public void setTextDirection(int textDirection) {
		// this.textDirection = textDirection;
		repaint();
	}
}