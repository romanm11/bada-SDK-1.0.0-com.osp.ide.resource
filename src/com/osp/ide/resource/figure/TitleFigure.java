package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.resinfo.FrameConst;

public class TitleFigure extends AbstactFigure {

	public static final int TEMP_HEIGHT = 12;

	private int TITLEICON_WIDTH = 32;

	private int TITLEICON_HEIGHT = 32;

	private Rectangle rect = null;

	private File titleIcon;

	private int hAlign = SWT.LEFT;

	@SuppressWarnings("deprecation")
	public TitleFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		int textSize = Form.DEFAULT_TEXTSIZE;
		if (tag != null)
			textSize = tag.tSize;
		
		TITLEICON_WIDTH = textSize;
		TITLEICON_HEIGHT = textSize;
		
		textLabel.setForegroundColor(ColorConstants.white);
		textLabel.setFont(font);
		textLabel.setLabelAlignment(PositionConstants.LEFT);
		add(textLabel);
		textLabel.setVisible(false);
	}

	public void createImage() {
		if (rect == null || rect.width <= 0 || tag == null)
			return;

		File_Info fInfo = new File_Info();
		Tag_info imageList = tag.getClone();
		
		if (titleIcon != null) {
			fInfo.image = titleIcon;
			fInfo.location = new Point(TITLEICON_WIDTH * 2 / 3, TITLEICON_HEIGHT - 6);
			fInfo.size = new Dimension(TITLEICON_WIDTH, TITLEICON_HEIGHT);
			imageList.textlMargin = TITLEICON_WIDTH + 6;
		} else
			imageList.textlMargin = TITLEICON_WIDTH * 2 / 3;

		Dimension size = new Dimension(rect.width, rect.height);
		
		if(titleIcon == null)
			createImage(imageList, size, fInfo, TITLEICON_WIDTH / 8, (rect.height - TITLEICON_HEIGHT) / 2, textLabel.getText(),
					hAlign, SWT.BOTTOM);
		else
			createImage(imageList, size, fInfo, TITLEICON_WIDTH / 2, (rect.height - TITLEICON_HEIGHT) / 2, textLabel.getText(),
					SWT.LEFT, SWT.BOTTOM);
	}

	public void setText(String text) {
	    if(textLabel.getText() == null || !textLabel.getText().equals(text)) {
    		textLabel.setText(text);
    		createImage();
	    }
	}

	public void setLayout(Rectangle rect) {
		if (this.rect == null || this.rect.height != rect.height
				|| this.rect.width != rect.width) {
			this.rect = rect;
			createImage();
		}

		Rectangle labelRect = new Rectangle(20, 0, rect.width, rect.height);
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

	public void setTitleIcon(File image) {
		if (image == null || !image.equals(titleIcon)) {
			titleIcon = image;
			createImage();
			repaint();
		}
	}
	
	public int getHAlign() {
		switch(hAlign) {
		case SWT.RIGHT:
			return FrameConst.ALIGN_RIGHT;
		case SWT.CENTER:
			return FrameConst.ALIGN_CENTER;
		case SWT.LEFT:
		default:
			return FrameConst.ALIGN_LEFT;
		}
	}

	public void setHAlign(int align) {
		if (getHAlign() != align) {
			switch(align) {
			case FrameConst.ALIGN_RIGHT:
				hAlign = SWT.RIGHT;
				break;
			case FrameConst.ALIGN_CENTER:
				hAlign = SWT.CENTER;
				break;
			case FrameConst.ALIGN_LEFT:
			default:
				hAlign = SWT.LEFT;
				break;
			}
			createImage();
			repaint();
		}
	}

}