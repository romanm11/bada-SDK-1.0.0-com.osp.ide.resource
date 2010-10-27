package com.osp.ide.resource.figure;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.SimpleLoweredBorder;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.actions.OspElementRulerProvider;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.part.PopupFramePart;

public class PopupFrameFigure extends AbstactFigure {

	private static final int TITLEICON_WIDTH = 30;
	private static final int TITLEICON_HEIGHT = 30;
	private Rectangle rect = null;
	public static final int BORDER = 2;
	private boolean grid = true;
	private int dimension = 10;
	protected PopupFramePart part;
	private Color guideColor = ColorConstants.darkGray;
	private File titleIcon;
	private File bitmap;

	@SuppressWarnings("deprecation")
	public PopupFrameFigure(PopupFramePart part) {
		super(((Popup) part.getModel()).editor.screen);
		this.part = part;
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		SimpleLoweredBorder border = new SimpleLoweredBorder(BORDER);

		setForegroundColor(ColorConstants.white);
		if(isScotia())
			setBackgroundColor(light_blue);
		else
			setBackgroundColor(ColorConstants.black);
		setBorder(border);
		setOpaque(true);
		setFont(font);
	}

    /**
     * @return
     */
    public PopupFramePart getFramePart() {
        return part;
    }
    
	public void createImage() {
		if (rect == null)
			return;

		Vector<File_Info> nBitmap = new Vector<File_Info>();
		Tag_info imageList = new Tag_info();
//        Tag_info imageList = tag.getClone();
		if (titleIcon != null) {
			File_Info fInfo = new File_Info();
			fInfo.image = titleIcon;
			fInfo.location = new Point(10, 8);
			fInfo.size = new Dimension(TITLEICON_WIDTH, TITLEICON_HEIGHT);
			nBitmap.add(fInfo);
			imageList.textlMargin = TITLEICON_WIDTH + 5;
		} else
			imageList.textlMargin = 5;

		if (bitmap != null) {
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(bitmap, size, nBitmap, SWT.LEFT, SWT.TOP,
					imageList.textlMargin, imageList.textrMargin);
		} else {
			Dimension size = new Dimension(rect.width, Popup.POPUP_TITLE_HEIGHT);
			createImage(imageList, size, nBitmap, "", SWT.CENTER, SWT.CENTER);
		}
	}

	public void setLayout(Rectangle rect) {
		Rectangle oldRect = this.rect;
        if (oldRect == null || oldRect.height != rect.height
                || oldRect.width != rect.width) {
            this.rect = rect;
            createImage();
            repaint();
        }

		getParent().setConstraint(
				this,
				new Rectangle(rect.x, rect.y, rect.width + BORDER * 2,
						rect.height + BORDER * 2));
	}

    /* (non-Javadoc)
     * @see org.eclipse.draw2d.Figure#setBounds(org.eclipse.draw2d.geometry.Rectangle)
     */
    @Override
    public void setBounds(Rectangle rect) {
        Rectangle oldRect = getBounds().getCopy();
        super.setBounds(rect);
        setGuidesPosition(oldRect, rect);
    }

    /**
     * @param oldRect
     * @param rect 
     */
    private void setGuidesPosition(Rectangle oldRect, Rectangle rect) {
        OspElementRulerProvider verticalRuler = (OspElementRulerProvider) part
                .getViewer().getProperty(RulerProvider.PROPERTY_VERTICAL_RULER);
        verticalRuler.setFrameRect(rect);
        List<?> verticalList = verticalRuler.getGuides();
        OspElementRulerProvider horizontalRuler = (OspElementRulerProvider) part
                .getViewer().getProperty(
                        RulerProvider.PROPERTY_HORIZONTAL_RULER);
        horizontalRuler.setFrameRect(rect);
        List<?> horizontalList = horizontalRuler.getGuides();
        
        for (int i = 0; i < verticalList.size(); i++) {
            OspElementGuide guid = (OspElementGuide) verticalList.get(i);
            guid.setPosition(guid.getPosition() + (rect.y - oldRect.y));
            guid.setFrame((Popup) part.getModel());
            guid.setFrameRect(rect);
        }
        for (int i = 0; i < horizontalList.size(); i++) {
            OspElementGuide guid = (OspElementGuide) horizontalList.get(i);
            guid.setPosition(guid.getPosition() + (rect.x - oldRect.x));
            guid.setFrame((Popup) part.getModel());
            guid.setFrameRect(rect);
        }
    }

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();
		part.getViewer().setProperty(SnapToGrid.PROPERTY_GRID_ORIGIN,
				new Point(point.x + 2, point.y + 2));

		if (bgImage != null && !bgImage.isDisposed())
			graphics.drawImage(bgImage, point.x, point.y);

        if (grid) {
            graphics.setForegroundColor(guideColor);
            int[] lineStyle = { 1, 1 };

            graphics.setLineDash(lineStyle);
            graphics.setLineStyle(SWT.LINE_CUSTOM);
            Point p1 = new Point(), p2 = new Point();
            for (int x = BORDER; x < rect.width; x += dimension) {
                p1.x = p2.x = point.x + x;
                p1.y = point.y;
                p2.y = point.y + rect.height + BORDER;
                graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            for (int y = BORDER; y < rect.height; y += dimension) {
                p1.y = p2.y = point.y + y;
                p1.x = point.x;
                p2.x = point.x + rect.width + BORDER;
                graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
            int[] lineDash = null;
            graphics.setLineDash(lineDash);
        }       
        
		super.paintClientArea(graphics);
	}

	public Rectangle getLayout() {
		return rect;
	}

	public void paintGrid(Boolean val) {
		if (val)
			grid = false;
		else
			grid = true;

		repaint();
	}

	public void setDimension(int height) {
		dimension = height;
		repaint();
	}

	public void setControlImage(File image) {
	}

	public void setGuideColor(Color color) {
		guideColor = color;
	}

	public Boolean getPaintGrid() {
		return grid;
	}

	public void setTitleText(String text) {
		if (text == null || !textLabel.getText().equals(text)) {
			textLabel.setText(text);
			createImage();
			repaint();
		}
	}

	public void setTitleIcon(File titleIcon) {
		if (titleIcon == null || !titleIcon.equals(this.titleIcon)) {
			this.titleIcon = titleIcon;
			createImage();
			repaint();
		}
	}

	public void setBitmap(File bitmap) {
		this.bitmap = bitmap;
	}

    /**
     * @return
     */
    public Rectangle getRect() {
        return rect;
    }

}
