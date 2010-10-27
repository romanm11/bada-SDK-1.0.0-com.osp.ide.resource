package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.resinfo.FrameConst;

public class PanelFigure extends AbstactFigure {

	private Rectangle rect = null;
	private File controlImage;
	private float opacity = 100;
    private int groupStyle=0;
    private Color backColor = null;

	public PanelFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		textLabel.setForegroundColor(ColorConstants.darkGray);
		if(isScotia()) {
			backColor = blue_gray;
			setOpaque(true);
		}
		setBackgroundColor(backColor);
	}

	public void createImage() {
		if (rect == null)
			return;
		
		Tag_info info = tag.getClone();

		Dimension size = new Dimension(rect.width, rect.height);
		if(groupStyle != FrameConst.GROUP_STYLE_NONE) {
		    setGroupStyleImage(info.get(0), groupStyle, false);
	        createImage(info, size, backColor);
		} else {
			if(isScotia())
			    createImage(info, size, backColor, 1.0f);
			else
				createImage(info, size, backColor, opacity * 0.007f + 0.3f);
		}
	}

	public void setName(String text) {
		textLabel.setText(text);
	}

	public void setBackColor(Color bg) {
        if (bg == null || !bg.equals(backColor )) {
            backColor = bg;
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

		super.paintClientArea(graphics);
	}

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
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

    /**
     * @param groupStyleIndex
     */
    public void setGroupStyle(int groupStyleIndex) {
        if(groupStyle != groupStyleIndex) {
            groupStyle = groupStyleIndex;
            createImage();
            repaint();
        }
    }
}