package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;

public class IconListFigure extends AbstactFigure {

	private static final int SIDE_MARGIN = 5;
	private static final int INNER_MARGIN = 2;
	private Rectangle rect = null;
	private File controlImage;
	private int itemHeight;
	private int itemWidth;
	private Image icon;
	private Image check;
	private String style;
    private Color textColor;

	public IconListFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

		setForegroundColor(ColorConstants.darkGray);

		image = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/frameIconList.png").createImage();
		icon = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/icon_home.png").createImage();
		if(isLismore())
    		check = AbstractUIPlugin.imageDescriptorFromPlugin(
    				Activator.PLUGIN_ID, "icons/00_check_transbg.png").createImage();
		else
            check = AbstractUIPlugin.imageDescriptorFromPlugin(
                Activator.PLUGIN_ID, "icons/00_check_transbg2.png").createImage();
	}

	public void createImage() {
		if(rect == null || tag == null)
			return;
		
		if(controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height)
					, 5, 5, 5, 5);
		} else {
			Dimension size = new Dimension(rect.width, rect.height);
			if(tag != null && tag.size() != 0) {
    			Tag_info localTag = tag.getClone();
				createImage(localTag, size, null);
			} else
				createImage(image, new Dimension(rect.width, rect.height)
					, 5, 5, 5, 5);
		}
	}

	@Override
	public void deleteImage() {
		super.deleteImage();
		if(icon != null && !icon.isDisposed()) {
			icon.dispose();
			icon = null;
		}
		if(check != null && !check.isDisposed()) {
			check.dispose();
			check = null;
		}
        if(textColor != null && !textColor.isDisposed()) {
            textColor.dispose();
            textColor = null;
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

	public void setName(String text) {
		textLabel.setText(text);
	}

	public void setLayout(Rectangle rect) {
		if(this.rect == null || this.rect.height != rect.height || this.rect.width != rect.width) {
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

		if(bgImage != null && !bgImage.isDisposed())
			graphics.drawImage(bgImage, point.x, point.y);
		
		drawIcon(graphics);
		super.paintClientArea(graphics);
	}

	/**
     * @param graphics
     */
    private void drawIcon(Graphics graphics) {
        Point point = getLocation();
        Point pos, checkPos;
        
        if(itemWidth > 0 && itemHeight > 0 && icon != null && !icon.isDisposed()) {
            Color newColor = new Color(null, 48, 52, 48);
            for(int i=0;  SIDE_MARGIN + i*(itemHeight + INNER_MARGIN) < rect.height; i++) {
                for(int j=0; (SIDE_MARGIN + j*(itemWidth + INNER_MARGIN)) < rect.width; j++) {
                    pos = new Point(point.x + SIDE_MARGIN + j*(itemWidth + INNER_MARGIN), 
                            point.y + SIDE_MARGIN + i*(itemHeight + INNER_MARGIN));
                    graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
                            pos.x, pos.y, itemWidth, itemHeight);
                    Color oldColor = graphics.getForegroundColor();
                    graphics.setForegroundColor(newColor);
                    graphics.drawRectangle(pos.x, pos.y, itemWidth -1, itemHeight-1);
                    graphics.setForegroundColor(oldColor);
                    
                    if(getStyle() > 0) {
                        checkPos = new Point(pos.x + itemWidth - check.getBounds().width, pos.y);
                        graphics.drawImage(check, checkPos);
                    }
                }
            }
            newColor.dispose();
        }
    }

    public int getStyle() {
		return FrameNode.getStyleIndex(FrameConst.STYLE_ICONLIST, style);
	}

	public void setStyle(String style) {
		if (style == null || !style.equals(this.style)) {
			this.style = style;
			createImage();
			repaint();
		}
	}

	public void setControlImage(File image) {
		if(image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}

	public void setItemHeight(int itemHeight) {
		if(this.itemHeight != itemHeight) {
			this.itemHeight = itemHeight;
			createImage();
			repaint();
		}
	}
	public void setItemWidth(int itemWidth) {
		if(this.itemWidth != itemWidth) {
			this.itemWidth = itemWidth;
			createImage();
			repaint();
		}
	}
}