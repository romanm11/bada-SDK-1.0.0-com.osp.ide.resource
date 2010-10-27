package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;

public class EditFieldFigure extends AbstactFigure {

	private int TITLE_FONT_SIZE = 32;
	private Rectangle rect = null;
	private File controlImage;
	private String guideText = "";
	private String style;
	private String title;
	private int bShow;
    private int groupStyle=0;

	@SuppressWarnings("deprecation")
	public EditFieldFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		textLabel.setFont(font);
		textLabel.setLabelAlignment(PositionConstants.LEFT);

		add(textLabel);
		textLabel.setVisible(false);
		
        if(tag != null)
            TITLE_FONT_SIZE = tag.tSize - 2;
	}

	public void createImage() {
		if (rect == null || tag == null)
			return;

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					SWT.LEFT, SWT.TOP, 10);
		} else {
			Dimension size = new Dimension(rect.width, rect.height);
			if (size == null || size.width <= 0 || size.height <= 0)
				return;
			
			Tag_info info = tag.getClone();
			String text = textLabel.getText();
			if(text == null || text.isEmpty()) {
				textLabel.setText(guideText);
				text = guideText;
				setForegroundColor(ColorConstants.gray);
			} else {
				setForegroundColor(ColorConstants.black);
				if(style.indexOf("SMALL") < 0)
					setForegroundColor(OspResourceManager.FormatRGB(info.temp2));
			}
			
			if(style == null || style.indexOf("SMALL") < 0) {
				info.remove(1);
                setGroupStyleImage(info.get(0), groupStyle, false);
			} else {
			    info.remove(0);
			    setGroupStyleImage(info.get(0), groupStyle, true);
			}
			
			if(bShow == FrameConst.BOOL_FALSE || (style != null && style.indexOf("SMALL") > 0))
				createImage(info, size, null, SWT.LEFT, SWT.CENTER);
			else
				createImage(info, size, title, titleColor, TITLE_FONT_SIZE, SWT.LEFT, SWT.CENTER);
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
	public int getTitleSize() {
		int size = 0;
		
		if(bShow != FrameConst.BOOL_FALSE && getStyle() < 2)
			size += TITLE_FONT_SIZE;
		
		return size;
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

	public int getStyle() {
		return FrameNode.getStyleIndex(FrameConst.STYLE_EDITFIELD, style);
	}

	public void setStyle(String style) {
		if (style == null || !style.equals(this.style)) {
			this.style = style;
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
