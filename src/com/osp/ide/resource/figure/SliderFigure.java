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
import org.eclipse.swt.graphics.Image;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.resinfo.FrameConst;

public class SliderFigure extends AbstactFigure {

	private int TITLE_FONT_SIZE = 30;
	private Rectangle rect = null;
	private File controlImage;
	private File leftIcon;
	private File rightIcon;
	private int iconWidth;
	private Image imageLeft;
	private Image imageRight;
	private int bgStyleIndex;
	private int bShow;
	private String title;
    private int groupStyle=0;

	public SliderFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

		setBackgroundColor(ColorConstants.gray);
		textLabel.setForegroundColor(ColorConstants.darkGray);
		
		if (tag != null && tag.get(1) != null) {
			iconWidth = tag.textrMargin;
            TITLE_FONT_SIZE = tag.tSize;
		} else
			iconWidth = 50;
	}

	@Override
	public void deleteImage() {
		super.deleteImage();

		if (imageLeft != null) {
			imageLeft.dispose();
			imageLeft = null;
		}
		if (imageRight != null) {
			imageRight.dispose();
			imageRight = null;
		}
	}

	public void createImage() {
		if (rect == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					5, 5, 5, 5);
		} else {
			if (tag == null || tag.size() < 3)
				return;
			iconWidth = tag.textrMargin;
			
			Tag_info localTag = tag.getClone();
			
			setGroupStyleImage(localTag.get(0), groupStyle, false);
			
			if(leftIcon != null && rightIcon != null) {
				localTag.get(2).setSizeX(
						rect.width / 2 - tag.get(1).getSpaceW());
				localTag.textlMargin = localTag.textrMargin + TITLE_FONT_SIZE / 3;
				localTag.textrMargin += TITLE_FONT_SIZE / 3;
			} else if(leftIcon != null) {
				localTag.get(1).setLeft(true);
				localTag.get(1).setRight(false);
				localTag.get(1).setHAlign(false);
				
				localTag.get(2).setSizeX(
						rect.width / 2 - tag.get(1).getSpaceW());
				localTag.textlMargin = localTag.textrMargin + TITLE_FONT_SIZE / 3;
				localTag.textrMargin = 0;
			} else if(rightIcon != null) {
				localTag.get(1).setLeft(false);
				localTag.get(1).setRight(true);
				localTag.get(1).setHAlign(false);
				
				localTag.get(2).setSizeX(
						rect.width / 2);
				localTag.get(2).setSpaceW(0);
				
				localTag.textlMargin = 0;
				localTag.textrMargin += TITLE_FONT_SIZE / 3;
			} else {
				localTag.get(1).setLeft(false);
				localTag.get(1).setRight(false);
				localTag.get(1).setHAlign(true);
				localTag.get(1).setSpaceW(0);
				
				localTag.get(2).setSizeX(
						rect.width / 2);
				localTag.get(2).setSpaceW(0);
				
				localTag.textlMargin = 0;
				localTag.textrMargin = 0;
			}

			localTag.textlMargin = TITLE_FONT_SIZE * 2 / 3;
			localTag.textrMargin = 0;
			
			if(bgStyleIndex == FrameConst.BACKGROUND_STYLE_NONE) {
				localTag.get(0).setOpacity(0, 0.3f);
			}
			Dimension size = new Dimension(rect.width, rect.height);
			if(bShow == FrameConst.BOOL_FALSE)
				createImage(localTag, size, null, SWT.LEFT, SWT.TOP);
			else
				createImage(localTag, size, title, titleColor, TITLE_FONT_SIZE, SWT.LEFT, SWT.TOP);
		}
	}

	public void setName(String text) {
		if (text == null || !text.equals(textLabel.getText())) {
			textLabel.setText(text);
			createImage();
			repaint();
		}
	}

	public void setTitleText(String title) {
		if (title == null || !title.equals(this.title)) {
			this.title = title;
			createImage();
			repaint();
		}
	}

    /**
     * @param titleTextColor
     */
    public void setTitleTextColor(Color titleTextColor) {
        if (titleTextColor != null && !titleTextColor.equals(titleColor)) {
            if(titleColor != null && !titleColor.isDisposed()) {
                titleColor.dispose();
                titleColor = null;
            }
            titleColor = titleTextColor;
            createImage();
            repaint();
        } else if(titleTextColor != null && !titleTextColor.isDisposed()) {
            titleTextColor.dispose();
            titleTextColor = null;
        }
    }
	@Override
	public void setBackgroundColor(Color bg) {
		if (bg == null || !bg.equals(getBackgroundColor())) {
			if(bg == null)
				bg = ColorConstants.darkGray;
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

		if (leftIcon != null) {
			org.eclipse.swt.graphics.Rectangle imageRect = imageLeft
					.getBounds();
			graphics.drawImage(imageLeft, point.x + 6, point.y
					+ rect.height - imageRect.height - 8);
		}

		if (rightIcon != null) {
			org.eclipse.swt.graphics.Rectangle imageRect = imageRight
					.getBounds();
			graphics.drawImage(imageRight, point.x + rect.width - iconWidth - 6,
					point.y + rect.height - imageRect.height - 8);
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

	public void setLeftIconBitmap(File leftIcon) {
		this.leftIcon = leftIcon;
		if (leftIcon != null) {
			if (imageLeft != null && !imageLeft.isDisposed())
				imageLeft.dispose();
			imageLeft = null;
			imageLeft = getImage(leftIcon, new Dimension(iconWidth, iconWidth));
		}
		createImage();
		repaint();
	}

	public void setRightIconBitmap(File rightIcon) {
		this.rightIcon = rightIcon;
		if (rightIcon != null) {
			if (imageRight != null && !imageRight.isDisposed())
				imageRight.dispose();
			imageRight = null;
			imageRight = getImage(rightIcon,
					new Dimension(iconWidth, iconWidth));
		}
		createImage();
		repaint();
	}

	public void setBgStyle(int bgStyleIndex) {
		if (this.bgStyleIndex != bgStyleIndex) {
			this.bgStyleIndex = bgStyleIndex;
			createImage();
			repaint();
		}
	}

	public void setShowTitle(int bShow) {
		if (this.bShow != bShow) {
			this.bShow = bShow;
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
