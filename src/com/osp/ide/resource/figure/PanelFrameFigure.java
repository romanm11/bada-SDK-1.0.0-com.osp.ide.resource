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
import org.eclipse.swt.graphics.Image;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.actions.OspElementRulerProvider;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.part.PanelFramePart;

public class PanelFrameFigure extends AbstactFigure {

	private static final int TITLEICON_WIDTH = 30;
	private static final int TITLEICON_HEIGHT = 30;
	private Rectangle rect = null;
	public static final int BORDER = 2;
	private boolean grid = true;
	private int dimension = 10;
	protected PanelFramePart part;
	private File controlImage;
	private Color guideColor = ColorConstants.darkGray;
	private File titleIcon;
	private File bitmap;
	private String TitleText;
    private int opacity;
    private Color backColor = ColorConstants.black;

	@SuppressWarnings("deprecation")
	public PanelFrameFigure(PanelFramePart part) {
		super(((PanelFrame) part.getModel()).editor.m_screen);
		this.part = part;
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		SimpleLoweredBorder border = new SimpleLoweredBorder(BORDER);

		setForegroundColor(ColorConstants.white);
		if(isScotia())
			backColor = blue_gray;
		
		setBackgroundColor(backColor);
		setBorder(border);
		setFont(font);
		setOpaque(true);
	}

    /**
     * @return
     */
    public PanelFramePart getFramePart() {
        return part;
    }

    public void createImage() {
        if (rect == null)
            return;

        Vector<File_Info> nBitmap = new Vector<File_Info>();

        if (controlImage != null) {
            Dimension size = new Dimension(rect.width, rect.height);
            createImage(controlImage, size, nBitmap, SWT.CENTER, SWT.CENTER, 0, 0);
        } else {
            Dimension size = new Dimension(rect.width, rect.height);
            if(isScotia())
                createImage(tag, size, backColor, 1.0f);
            else
            	createImage(tag, size, backColor, opacity * 0.007f + 0.3f);
        }
    }

    public void setBackColor(Color bg) {
        if (bg == null || !bg.equals(backColor)) {
            backColor = bg;
            createImage();
            repaint();
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
            guid.setFrame((PanelFrame) part.getModel());
            guid.setFrameRect(rect);
        }
        for (int i = 0; i < horizontalList.size(); i++) {
            OspElementGuide guid = (OspElementGuide) horizontalList.get(i);
            guid.setPosition(guid.getPosition() + (rect.x - oldRect.x));
            guid.setFrame((PanelFrame) part.getModel());
            guid.setFrameRect(rect);
        }
    }

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();
		part.getViewer().setProperty(SnapToGrid.PROPERTY_GRID_ORIGIN,
				new Point(point.x + 2, point.y + 2));

		Image image = null;
		if (bitmap != null) {
			image = new Image(null, bitmap.getAbsolutePath());
		} else if (controlImage != null) {
			image = new Image(null, controlImage.getAbsolutePath());
		}

		if (image != null) {
			org.eclipse.swt.graphics.Rectangle imageRect = image.getBounds();
			graphics
					.drawImage(image, 0, 0, imageRect.width, imageRect.height,
							point.x + BORDER, point.y + BORDER, rect.width,
							rect.height);
			image.dispose();
			image = null;
		} else {
            if (bgImage != null && !bgImage.isDisposed())
                graphics.drawImage(bgImage, point.x + BORDER, point.y + BORDER);
		}
		if (titleIcon != null) {
			image = new Image(null, titleIcon.getAbsolutePath());
			org.eclipse.swt.graphics.Rectangle rect = image.getBounds();
			graphics.drawImage(image, 0, 0, rect.width, rect.height,
					point.x + 10, point.y + 10, TITLEICON_WIDTH,
					TITLEICON_HEIGHT);
			image.dispose();
			image = null;
		}

		if (TitleText != null && !TitleText.isEmpty()) {
			if (titleIcon != null)
				graphics.drawString(TitleText, point.x + 15 + TITLEICON_WIDTH,
						point.y + 5);
			else
				graphics.drawString(TitleText, point.x + 5, point.y + 5);
		}
		
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
		controlImage = image;
	}

	public void setGuideColor(Color color) {
		guideColor = color;
	}

	public Boolean getPaintGrid() {
		return grid;
	}

	public void setTitleText(String text) {
		TitleText = text;
	}

	public void setTitleIcon(File titleIcon) {
		this.titleIcon = titleIcon;
	}

	public void setBitmap(File bitmap) {
		this.bitmap = bitmap;
	}

    /**
     * @return 
     * @return
     */
    public Rectangle getRect() {
        // TODO Auto-generated method stub
        return rect;
    }

    /**
     * @param opacity
     */
    public void setOpacity(int opacity) {
        if (opacity != this.opacity) {
            this.opacity = opacity;
            createImage();
            repaint();
        }
    }
}