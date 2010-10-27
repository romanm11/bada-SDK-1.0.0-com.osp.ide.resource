package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.resinfo.FrameConst;

public class SlidableListFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File backgroundImage;
    private Color textColor;

	public SlidableListFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

		setForegroundColor(ColorConstants.darkGray);
	}

    @Override
    public void deleteImage() {
        super.deleteImage();
        if(textColor != null && !textColor.isDisposed()) {
            textColor.dispose();
            textColor = null;
        }
    }

	public void createImage() {
		if (rect == null)
			return;

		if (backgroundImage != null) {
			createImage(backgroundImage,
					new Dimension(rect.width, rect.height), 5, 5, 5, 5);
		} else {
//			Dimension size = new Dimension(rect.width, defaultHeight);
//			createImage(imageList, size, null, SWT.LEFT, SWT.CENTER);
			String text = textLabel.getText();
			if (text == null || text.isEmpty())
				textLabel.setText("Blank");
			
            Point textLoc;
            if(tag != null)
                textLoc = new Point((tag.tSize*FrameConst.FONT_RATE) / 3, (tag.tSize*FrameConst.FONT_RATE));
            else
                textLoc = new Point(10, 30);
            
            Dimension size = new Dimension(rect.width, rect.height);
            createImage(tag, size, textColor, "", textLoc, null, SWT.LEFT, SWT.TOP);
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

    public void setTextColor(Color fg) {
        if (fg != null && !fg.equals(textColor)) {
            if(textColor != null && !textColor.isDisposed()) {
                textColor.dispose();
                textColor = null;
            }
            textColor = fg;
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

		getParent().setConstraint(this, rect);
	}

	@Override
	public void addLayoutListener(LayoutListener listener) {
		super.addLayoutListener(listener);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {

		Point point = getLocation();

		if (backgroundImage != null) {
			graphics.drawImage(bgImage, point.x, point.y);
		} else {
//			for (int i = 0; defaultHeight * i < rect.height; i++) {
//				graphics.drawImage(bgImage, point.x, point.y + defaultHeight
//						* i);
//			}
			graphics.drawImage(bgImage, point);
		}

		drawDiagonalLine(graphics, rect, point);

		super.paintClientArea(graphics);
	}

	public void setBGBitmap(File image) {
		if (image == null || !image.equals(backgroundImage)) {
			backgroundImage = image;
			createImage();
			repaint();
		}
	}
}