package com.osp.ide.resource.figure;

import java.io.File;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;

public class LabelFigure extends AbstactFigure {

	LineBorder line = new LineBorder(1);

	private Rectangle rect = null;
	private AlignLayout layout;
	private File controlImage;
	private float opacity = 100;

    private int style;

	@SuppressWarnings("deprecation")
	public LabelFigure(String screen) {
		super(screen);
		layout = new AlignLayout();
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);

		textLabel.setFont(font);
		textLabel.setForegroundColor(ColorConstants.white);
		if(isScotia()) {
			setBackgroundColor(blue_gray);
			setOpaque(true);
		}

		add(textLabel);
		textLabel.setVisible(false);
	}

	public void createImage() {
		if (rect == null)
			return;

		Vector<File_Info> nBitmap = new Vector<File_Info>();

		if (controlImage != null) {
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(controlImage, size, nBitmap, getSWTHAlign(),
					getSWTVAlign(), tag.textlMargin,
					tag.textrMargin, 100 * 0.007f + 0.3f);
		} else {
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(tag, size, getBackgroundColor(), getSWTHAlign(),
					getSWTVAlign(), opacity * 0.007f + 0.3f);
		}
	}

	public void setText(String text) {
		if(text == null || !text.equals(textLabel.getText())) {
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

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}

	public void setTextSize(int textSize) {
		Font font = getFont();
		textSize *= FrameConst.FONT_RATE;
		if (font != null && textSize >= 0) {
		    if(!font.isDisposed()) {
		        if(font.getFontData()[0].height == textSize)
		            return;
    			font.dispose();
    			font = null;
		    }

		    font = new Font(null, new FontData(FrameNode.FontName, textSize, style));
			textLabel.setFont(font);
			setFont(font);

			createImage();
			repaint();
		}
	}

	public void setTextStyle(String textStyle) {
		Font font = getFont();

		style = FrameNode.getStyleIndex(FrameConst.STYLE_LABEL_TEXT, textStyle);
		switch(style) {
		case FrameConst.LABEL_TEXT_STYLE_BOLD:
			style = SWT.BOLD;
			break;
		case FrameConst.LABEL_TEXT_STYLE_ITALIC:
			style = SWT.ITALIC;
			break;
		default:
			style = SWT.NONE;
			break;
		}
		
		if (font != null 
				&& ((font.getFontData()[0].getStyle() & style) != style || style == SWT.NONE)) {
			int size = font.getFontData()[0].getHeight();
			font.dispose();
			font = null;

			font = new Font(null, new FontData(FrameNode.FontName, size, style));
			textLabel.setFont(font);
			setFont(font);

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
