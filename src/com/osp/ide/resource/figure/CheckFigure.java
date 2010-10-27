package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.resinfo.FrameConst;

public class CheckFigure extends AbstactFigure {

	private int TITLE_FONT_SIZE = 32;
	private Rectangle rect = null;
	private File controlImage;
	private AlignLayout layout;
	private int textDirection;
	private int styleIndex;
	private int bgStyleIndex;
	String title = "";
	private int bShow;
    private Color textColor;
    private int groupStyle=0;

	@SuppressWarnings("deprecation")
	public CheckFigure(String screen) {
		super(screen);
		layout = new AlignLayout();
		layout.setStretchMinorAxis(false);
		setLayoutManager(layout);

		textLabel.setFont(font);
		textLabel.setVisible(false);
		textLabel.setLabelAlignment(PositionConstants.LEFT);
		add(textLabel);
		
		if(tag != null)
		    TITLE_FONT_SIZE = tag.tSize - 2;
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
		if (rect == null || tag == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					getSWTHAlign(), getSWTVAlign(), 10);
		} else {
			Tag_info lacalTag = tag.getClone();
			
			setGroupStyleImage(lacalTag.get(0), groupStyle, false);
	
			switch(styleIndex) {
			case FrameConst.CHECK_BUTTON_STYLE_MARK:
                lacalTag.remove(1);
                lacalTag.remove(2);
                break;
			case FrameConst.CHECK_BUTTON_STYLE_ONOFF:
				lacalTag.remove(1);
				lacalTag.remove(1);
				break;
			case FrameConst.CHECK_BUTTON_STYLE_RADIO:
				lacalTag.remove(1);
				lacalTag.remove(1);
                lacalTag.remove(1);
				break;
			case FrameConst.CHECK_BUTTON_STYLE_MARK_WITH_DIVIDER:
				lacalTag.remove(3);
				break;
			case FrameConst.CHECK_BUTTON_STYLE_ONOFF_WITH_DIVIDER:
				lacalTag.remove(2);
				break;
            case FrameConst.CHECK_BUTTON_STYLE_RADIO_WITH_DIVIDER:
                lacalTag.remove(2);
                lacalTag.remove(2);
				break;
			}
			if(bgStyleIndex == FrameConst.BACKGROUND_STYLE_NONE) {
				lacalTag.get(0).setOpacity(0, 0.3f);
				if(styleIndex == FrameConst.CHECK_BUTTON_STYLE_MARK_WITH_DIVIDER ||
				    styleIndex == FrameConst.CHECK_BUTTON_STYLE_RADIO_WITH_DIVIDER ||
				    styleIndex == FrameConst.CHECK_BUTTON_STYLE_ONOFF_WITH_DIVIDER)
				    lacalTag.remove(1);
			}
			Dimension size = new Dimension(rect.width, rect.height);
			
			if(bShow == FrameConst.BOOL_FALSE)
				createImage(lacalTag, size, null, getSWTHAlign(), getSWTVAlign());
			else
				createImage(lacalTag, size, title, titleColor, TITLE_FONT_SIZE, getSWTHAlign(), getSWTVAlign());
		}
	}

    public void setTextColor(Color fg) {
        if (fg != null && !fg.equals(textColor)) {
            if(textColor != null && !textColor.isDisposed()) {
                textColor.dispose();
                textColor = null;
            }
            textColor = fg;
            setForegroundColor(fg);
            createImage();
            repaint();
        }
    }

	public void setText(String text) {
		if (text == null || !text.equals(textLabel.getText())) {
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
		if(bShow != FrameConst.BOOL_FALSE && (title != null && !title.isEmpty()))
			labelRect.y += TITLE_FONT_SIZE;
		
		textLabel.setBounds(labelRect );

		this.setConstraint(textLabel, labelRect);
		getParent().setConstraint(this, rect);
	}

	@Override
	public int getTitleSize() {
		int size = 0;
		
		if(bShow != FrameConst.BOOL_FALSE && getVAlign() == FrameConst.ALIGN_TOP)
			size += TITLE_FONT_SIZE;
		
		return size;
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

		if(styleIndex == FrameConst.CHECK_BUTTON_STYLE_MARK_WITH_DIVIDER ||
				styleIndex == FrameConst.CHECK_BUTTON_STYLE_ONOFF_WITH_DIVIDER ||
				styleIndex == FrameConst.CHECK_BUTTON_STYLE_RADIO_WITH_DIVIDER)
			drawDivider(graphics, rect, point, tag.textrMargin);
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
		if (controlImage == null || !controlImage.equals(image)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}

	public void setTextDirection(int textDirection) {
		if (this.textDirection != textDirection) {
			this.textDirection = textDirection;
			createImage();
			repaint();
		}
	}

	public void setStyle(int styleIndex) {
		if (this.styleIndex != styleIndex) {
			this.styleIndex = styleIndex;
			createImage();
			repaint();
		}
	}

	public void setBgStyle(int bgStyleIndex) {
		if (this.bgStyleIndex != bgStyleIndex) {
			this.bgStyleIndex = bgStyleIndex;
			createImage();
			repaint();
		}
	}

	public void setTitleText(String titleText) {
		if (!title.equals(titleText)) {
			title = titleText;
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
     * @param black
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