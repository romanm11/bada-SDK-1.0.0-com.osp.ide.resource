package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.resinfo.FrameConst;

public class ListFigure extends AbstactFigure {

	private static final int X_MARGIN = 10;

	private String itemText = "";
	private Rectangle rect = null;
	private File controlImage;
	
	private int markWidth = 92;
	private int numberWidth = 60;
	private int numberFont = 20;
	
	private int line1Height;
	private int line2Height;
	private int col1Width;
	private int col2Width;

	private int format;
	private String style;

    private Color textColor;


	public ListFigure(String screen) {
		super(screen);
		ToolbarLayout layout = new ToolbarLayout();
		setLayoutManager(layout);

		line1Height = ListControl.LIST_DEFAULT_HEIGHT;
		markWidth = 92;
		if(tag != null && tag.dftlSize != null) {
			line1Height = tag.dftlSize.y;
	        if(!isLismore()) {
			    markWidth = 50;
			    numberWidth /= 2;
			    numberFont /= 2;
	        }
		}
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

		if (controlImage != null) {
			createImage(controlImage, new Dimension(rect.width, rect.height),
					5, 5, 5, 5);
		} else {
			// Dimension size = new Dimension(rect.width, line1Height);
			// createImage(imageList, size, null, SWT.LEFT,
			// SWT.CENTER);
			String text = textLabel.getText();
			if (text == null || text.isEmpty())
				textLabel.setText("1st Line");
			
			Dimension size = new Dimension(rect.width, rect.height);
			createImage(tag, size, null);
		}
	}

	public void setName(String text) {
		if (text == null || !text.equals(textLabel.getText())) {
			textLabel.setText(text);
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
            setForegroundColor(fg);
            createImage();
            repaint();
        }
    }

	public void setLine1Height(int height) {
		if (line1Height != height) {
			line1Height = height;
			repaint();
		}
	}

	public void setLine2Height(int height) {
		if (line2Height != height) {
			line2Height = height;
			repaint();
		}
	}

	public void setCol1Width(int width) {
		if (col1Width != width) {
			col1Width = width;
			repaint();
		}
	}

	public void setCol2Width(int width) {
		if (col2Width != width) {
			col2Width = width;
			repaint();
		}
	}
	
	public void setListItemFormat(int format) {
		if (this.format != format) {
			this.format = format;
			repaint();
		}
	}

	public void setStyle(String style) {
		if (this.style == null || !this.style.equals(style)) {
			this.style = style;
			repaint();
		}
	}

	public int getStyle() {
		return FrameNode.getStyleIndex(FrameConst.STYLE_LIST, style);
	}

	@Override
	public void addLayoutListener(LayoutListener listener) {
		super.addLayoutListener(listener);
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

		getParent().setConstraint(this, rect);
	}

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();

		if (controlImage != null) {
			graphics.drawImage(bgImage, point.x, point.y);
		} else {
			graphics.drawImage(bgImage, point.x, point.y);
		}
		
		paintListItemformat(point, graphics);
		drawLine(point, graphics);

		super.paintClientArea(graphics);
	}

	private void paintListItemformat(Point paramPoint, Graphics graphics) {
		Dimension size;
		int fontSize = 36, width, figureWidth;
		if(tag != null && tag.tSize != 0)
			fontSize = tag.tSize;
		
		Point point = paramPoint.getCopy();
		int colWidth = col1Width;
		if(getStyle() == FrameConst.LIST_STYLE_NUMBER) {
			point .x += numberWidth;
			colWidth -= numberWidth;
			if(colWidth < 0){
				colWidth = 0;
			}
			figureWidth = rect.width - numberWidth;
		} else if(getStyle() > FrameConst.LIST_STYLE_NUMBER)
			figureWidth = rect.width - markWidth;
		else
			figureWidth = rect.width;
		
		if(figureWidth <= 0) {
			return;
		}
		
		Image textImage;
		String text1 = "", text2 = "2nd Line";
		text1 = textLabel.getText();
		
		Image icon = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/icon_home.png").createImage();
		switch(format) {
		case FrameConst.LIST_ITEM_SINGLE_IMAGE: 
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;

			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y, width, line1Height);
//			graphics.drawRectangle(point.x, point.y, width, line1Height);
			break;
		case FrameConst.LIST_ITEM_SINGLE_TEXT:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			break;
		case FrameConst.LIST_ITEM_SINGLE_IMAGE_TEXT: 
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y, width, line1Height);
//			graphics.drawRectangle(point.x, point.y, width, line1Height);
			
			width = col2Width;
			if(colWidth + col2Width> figureWidth)
				width -= colWidth + col2Width - figureWidth;
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x + colWidth, point.y);
			textImage.dispose();
//			graphics.drawRectangle(point.x + col1Width, point.y, size.width, size.height);
			break;
		case FrameConst.LIST_ITEM_SINGLE_TEXT_IMAGE:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			
			width = col2Width;
			if(colWidth + col2Width > figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x + colWidth, point.y, width, line1Height);
//			graphics.drawRectangle(point.x + col1Width, point.y, width, line1Height);
			break;
		case FrameConst.LIST_ITEM_SINGLE_IMAGE_TEXT_IMAGE:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y, width, line1Height);
//			graphics.drawRectangle(point.x, point.y, width, line1Height);
			
			width = col2Width;
			if(colWidth + col2Width> figureWidth)
				width -= colWidth + col2Width - figureWidth;
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x + colWidth, point.y);
			textImage.dispose();
//			graphics.drawRectangle(point.x + col1Width, point.y, size.width, size.height);

			width = figureWidth - colWidth - col2Width;
			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x + colWidth + col2Width, point.y, width, line1Height);
//			graphics.drawRectangle(point.x + col1Width + col2Width, point.y, width, line1Height);
			break;
		case FrameConst.LIST_ITEM_DOUBLE_IMAGE_TEXT_FULLTEXT:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			if(width > 0 && line1Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y, width, line1Height);
//			graphics.drawRectangle(point.x, point.y, width, line1Height);
			
			width = col2Width;
			if(colWidth + col2Width> figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line1Height > 0) {
				size = new Dimension(width, line1Height);
				textImage = getTextSWTImage(size, textColor, fontSize, text1, 
						SWT.LEFT, SWT.CENTER, 10, 0);
				graphics.drawImage(textImage, point.x + colWidth, point.y);
				textImage.dispose();
//				graphics.drawRectangle(point.x + col1Width, point.y, size.width, size.height);
			}
			
			size = new Dimension(figureWidth, line2Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text2, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y + line1Height);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y + line1Height, size.width, size.height);
			break;
		case FrameConst.LIST_ITEM_DOUBLE_FULLTEXT_IMAGE_TEXT:
			size = new Dimension(figureWidth, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			if(width > 0 && line2Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y + line1Height, width, line2Height);
//			graphics.drawRectangle(point.x, point.y + line1Height, width, line2Height);
			
			width = col2Width;
			if(colWidth + col2Width> figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line2Height > 0) {
				size = new Dimension(width, line2Height);
				textImage = getTextSWTImage(size, textColor, fontSize, text2, 
						SWT.LEFT, SWT.CENTER, 10, 0);
				graphics.drawImage(textImage, point.x + colWidth, point.y + line1Height);
				textImage.dispose();
//				graphics.drawRectangle(point.x + col1Width, point.y + line1Height, size.width, size.height);
			}
			
			break;
		case FrameConst.LIST_ITEM_DOUBLE_TEXT_IMAGE_FULLTEXT:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			
			width = col2Width;
			if(colWidth + col2Width > figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line1Height > 0) {
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x + colWidth, point.y, width, line1Height);
//				graphics.drawRectangle(point.x + col1Width, point.y, width, line1Height);
			}
			
			size = new Dimension(figureWidth, line2Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text2, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y + line1Height);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y + line1Height, size.width, size.height);
			break;
		case FrameConst.LIST_ITEM_DOUBLE_FULLTEXT_TEXT_IMAGE:
			size = new Dimension(figureWidth, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			size = new Dimension(width, line2Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text2, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y + line1Height);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y + line1Height, size.width, size.height);
			
			width = col2Width;
			if(colWidth + col2Width > figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line2Height > 0) {
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x + colWidth, point.y + line1Height, width, line2Height);
//				graphics.drawRectangle(point.x + col1Width, point.y + line1Height, width, line2Height);
			}
			break;
		case FrameConst.LIST_ITEM_DOUBLE_IMAGE_TEXT_TEXT:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			if(width > 0 && line1Height + line2Height > 0)
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x, point.y, width, line1Height + line2Height);
//			graphics.drawRectangle(point.x, point.y, width, line1Height + line2Height);
			
			width = col2Width;
			if(colWidth + col2Width> figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line1Height > 0) {
				size = new Dimension(width, line1Height);
				textImage = getTextSWTImage(size, textColor, fontSize, text1, 
						SWT.LEFT, SWT.CENTER, 10, 0);
				graphics.drawImage(textImage, point.x + colWidth, point.y);
				textImage.dispose();
//				graphics.drawRectangle(point.x + col1Width, point.y, size.width, size.height);
			
				size = new Dimension(width, line2Height);
				textImage = getTextSWTImage(size, textColor, fontSize, text2, 
						SWT.LEFT, SWT.CENTER, 10, 0);
				graphics.drawImage(textImage, point.x + colWidth, point.y + line1Height);
				textImage.dispose();
//				graphics.drawRectangle(point.x + col1Width, point.y + line1Height, size.width, size.height);
			}
			break;
		case FrameConst.LIST_ITEM_DOUBLE_TEXT_TEXT_IMAGE:
			if(colWidth > figureWidth)
				width = figureWidth;
			else
				width = colWidth;
			
			size = new Dimension(width, line1Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text1, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y, size.width, size.height);
			
			size = new Dimension(width, line2Height);
			textImage = getTextSWTImage(size, textColor, fontSize, text2, 
					SWT.LEFT, SWT.CENTER, 10, 0);
			graphics.drawImage(textImage, point.x, point.y + line1Height);
			textImage.dispose();
//			graphics.drawRectangle(point.x, point.y + line1Height, size.width, size.height);
			
			width = col2Width;
			if(colWidth + col2Width > figureWidth)
				width -= colWidth + col2Width - figureWidth;
			if(width > 0 && line1Height + line2Height > 0) {
				graphics.drawImage(icon, 0, 0, icon.getBounds().width, icon.getBounds().height, 
						point.x + colWidth, point.y, width, line1Height + line2Height);
//				graphics.drawRectangle(point.x + col1Width, point.y, width, line1Height + line2Height);
			}
			break;
		default:
			
		}
		icon.dispose();
	}

	private void drawLine(Point paramPoint, Graphics graphics) {
		int lineHeight=0;
		Point point = paramPoint.getCopy();
		switch(format) {
		case FrameConst.LIST_ITEM_SINGLE_IMAGE: 
		case FrameConst.LIST_ITEM_SINGLE_TEXT:
		case FrameConst.LIST_ITEM_SINGLE_IMAGE_TEXT: 
		case FrameConst.LIST_ITEM_SINGLE_TEXT_IMAGE:
		case FrameConst.LIST_ITEM_SINGLE_IMAGE_TEXT_IMAGE:
			lineHeight = line1Height;
			break;
		case FrameConst.LIST_ITEM_DOUBLE_IMAGE_TEXT_FULLTEXT:
		case FrameConst.LIST_ITEM_DOUBLE_FULLTEXT_IMAGE_TEXT:
		case FrameConst.LIST_ITEM_DOUBLE_TEXT_IMAGE_FULLTEXT:
		case FrameConst.LIST_ITEM_DOUBLE_FULLTEXT_TEXT_IMAGE:
		case FrameConst.LIST_ITEM_DOUBLE_IMAGE_TEXT_TEXT:
		case FrameConst.LIST_ITEM_DOUBLE_TEXT_TEXT_IMAGE:
			lineHeight = line1Height + line2Height;
			break;
		default:
			lineHeight = line1Height;
		}
		
		if(lineHeight <= 0)
			return;
		
		for (int i = 0; lineHeight  * i < rect.height; i++) {
			drawStyle(i, graphics, point, lineHeight);
		}
		drawListLine(graphics, rect, point, lineHeight);
		
		int styleIndex = getStyle();
		if(styleIndex  == FrameConst.LIST_STYLE_MARK_WITH_DIVIDER ||
				styleIndex == FrameConst.LIST_STYLE_ONOFF_WITH_DIVIDER ||
				styleIndex == FrameConst.LIST_STYLE_RADIO_WITH_DIVIDER) {
			drawDivider(graphics, rect, point, markWidth);
		}
	}

	private void drawStyle(int i, Graphics graphics, Point point, int lineHeight) {
		if(getStyle() == FrameConst.LIST_STYLE_NUMBER) {
			Dimension size = new Dimension(numberWidth, line1Height);
			Image textImage = getTextSWTImage(size, null, numberFont, 
					Integer.toString(i+1), 
					SWT.CENTER, SWT.CENTER, 0, 0);
			graphics.drawImage(textImage, point.x, point.y + lineHeight*i);
			textImage.dispose();
		} else if(getStyle() == FrameConst.LIST_STYLE_MARK || 
				getStyle() == FrameConst.LIST_STYLE_MARK_WITH_DIVIDER) {
			Image mark;
			if(isLismore())
			    mark = AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, "icons/00_check_bg.png").createImage();
			else
                mark = AbstractUIPlugin.imageDescriptorFromPlugin(
                    Activator.PLUGIN_ID, "icons/00_check_bg2.png").createImage();
			
			int posX = rect.width - (markWidth + mark.getBounds().width)/2;
			int posY = (line1Height - mark.getBounds().height)/2;
			graphics.drawImage(mark, point.x + posX, point.y + lineHeight*i + posY);
			mark.dispose();
		} else if(getStyle() == FrameConst.LIST_STYLE_ONOFF || 
				getStyle() == FrameConst.LIST_STYLE_ONOFF_WITH_DIVIDER) {
			Image mark;
			if(isLismore())
			    mark = AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, "icons/00_on_off_button_bg.png").createImage();
			else
                mark = AbstractUIPlugin.imageDescriptorFromPlugin(
                    Activator.PLUGIN_ID, "icons/00_on_off_button_bg2.png").createImage();
			    
			int posX = rect.width - (markWidth + mark.getBounds().width)/2;
			int posY = (line1Height - mark.getBounds().height)/2;
			graphics.drawImage(mark, point.x + posX, point.y + lineHeight*i + posY);
			mark.dispose();
		}
	}

	public void printItemText(Graphics graphics, Point point) {
		String text, subText;
		int i, n = 0;
		if (itemText != null && !itemText.isEmpty()) {
			subText = itemText;
			while ((i = subText.indexOf(';')) > 0) {
				text = subText.substring(0, i);
				graphics.drawString(text, point.x + X_MARGIN, point.y
						+ line1Height * n++
						+ (line1Height - FrameNode.FONT_SIZE) / 2);
				subText = subText.substring(i + 1);
			}
			if (!subText.isEmpty()) {
				graphics.drawString(subText, point.x + X_MARGIN, point.y
						+ line1Height * n++
						+ (line1Height - FrameNode.FONT_SIZE) / 2);
				subText = subText.substring(i + 1);
			}
		}
	}

	public String getItemText() {
		return itemText;
	}

	public void setControlImage(File image) {
		if (image == null || !image.equals(controlImage)) {
			controlImage = image;
			createImage();
			repaint();
		}
	}
}
