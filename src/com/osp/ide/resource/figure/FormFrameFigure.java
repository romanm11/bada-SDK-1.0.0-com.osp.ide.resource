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
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class FormFrameFigure extends AbstactFigure {

	public static final Color TWTITLE_BGCOLOR = new Color(null, 110, 158, 206);
	public static final Color TWTAB_BGCOLOR = new Color(null, 63, 63, 63);

	private Rectangle rect = null;
	public static final int BORDER = 2;
	private boolean grid = true;
	private int dimension = 10;
	private int headerHeight = 10;
	protected FormFramePart part;
	private File controlImage;
	private File softKey0Icon;
	private File softKey1Icon;
	private Color guideColor = ColorConstants.darkGray;
	private IndicatorFigure indicator;
	private TitleFigure title;
	private SoftKeyLeftFigure softkey0;
	private SoftKeyRightFigure softkey1;
	private SoftKeyOptionFigure optionkey;
	private TabFigure tab;
	private String[] softKeyText = new String[2];
	private boolean texttabLayoutStyle;
	private boolean icontabLayoutStyle;
	private File titleIcon;
	private int hAlign;
    private float opacity = 0;
    private Color backColor = ColorConstants.black;

	public FormFrameFigure(FormFramePart part) {
		super(((Form) part.getModel()).editor.screen);
		this.part = part;
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);
		SimpleLoweredBorder border = new SimpleLoweredBorder(BORDER);

		setForegroundColor(ColorConstants.white);
		if(isScotia())
			backColor = blue_gray;
		
		setBackgroundColor(backColor);

		setOpaque(true);
		setBorder(border);
	}

	@Override
	public void deleteImage() {
		super.deleteImage();
		if (indicator != null)
			indicator.deleteImage();
		if (title != null)
			title.deleteImage();
		if (softkey0 != null)
			softkey0.deleteImage();
		if (softkey1 != null)
			softkey1.deleteImage();
		if (optionkey != null)
			optionkey.deleteImage();
		if (tab != null)
			tab.deleteImage();
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
            	createImage(tag, size, backColor, opacity * 0.009f + 0.1f);
        }
    }

	public FormFramePart getFramePart() {
		return part;
	}

	public void setIndicator(boolean visible) {
		if (visible) {
			if (indicator == null) {
				indicator = new IndicatorFigure(screen);
			}
			add(indicator);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_INDICATOR], screen);
			int indicatorY;
			if (tag == null)
				indicatorY = Form.INDICATOR_DEFAULT_HEIGHT;
			else
				indicatorY = tag.dftlSize.y;

			indicator.setLayout(new Rectangle(0, 0, rect.width, indicatorY));
			indicator.setVisible(true);
		} else if (indicator != null) {
			indicator.setVisible(false);
			remove(indicator);
			indicator.deleteImage();
			indicator = null;
		}
	}

    public void setBackColor(Color bg) {
        if (bg == null || !bg.equals(backColor )) {
            backColor = bg;
            createImage();
            repaint();
        }
    }

	public void setHAlign(int align) {
		hAlign = align;
		if (title != null) {
			title.setHAlign(align);
		}
		repaint();
	}

	public void setTitle(boolean visible) {
		if (visible) {
			if (title == null) {
				title = new TitleFigure(screen);
				title.setTitleIcon(titleIcon);
				title.setHAlign(hAlign);
			}
			add(title);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_TITLE], screen);
			int titleY;
			if (tag == null) {
				titleY = Form.TITLE_DEFAULT_HEIGHT;
			} else {
				titleY = tag.dftlSize.y;
			}
			title.setLayout(new Rectangle(0, 0, rect.width, titleY));
			title.setVisible(true);
		} else if (title != null) {
			title.setVisible(false);
			remove(title);
			title.deleteImage();
			title = null;
		}
	}

	public void setSoftKey0(boolean visible) {
		if (visible) {
			if (softkey0 == null) {
				softkey0 = new SoftKeyLeftFigure(screen);
				softkey0.setSoftKeyText(softKeyText[0]);
				softkey0.setSoftKey0Icon(softKey0Icon);
			}
			add(softkey0);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
			Dimension size;
			if (tag == null) {
				size = new Dimension(Form.SOFTKEY0_DEFAULT_WIDTH,
						Form.SOFTKEY0_DEFAULT_HEIGHT);
			} else {
				size = tag.get(0).getSize();
			}
			softkey0.setLayout(new Rectangle(0, rect.height - size.height,
					size.width, size.height));
			softkey0.setVisible(true);
		} else if (softkey0 != null) {
			softkey0.setVisible(false);
			remove(softkey0);
			softkey0.deleteImage();
			softkey0 = null;
		}
	}

	public void setSoftKey1(boolean visible) {
		if (visible) {
			if (softkey1 == null) {
				softkey1 = new SoftKeyRightFigure(screen);
				softkey1.setSoftKeyText(softKeyText[1]);
				softkey1.setSoftKey1Icon(softKey1Icon);
			}
			add(softkey1);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
			Dimension size;
			if (tag == null) {
				size = new Dimension(Form.SOFTKEY0_DEFAULT_WIDTH,
						Form.SOFTKEY0_DEFAULT_HEIGHT);
			} else {
				size = tag.get(2).getSize();
			}
			softkey1.setLayout(new Rectangle(rect.width - size.width,
					rect.height - size.height, size.width, size.height));
			softkey1.setVisible(true);
		} else if (softkey1 != null) {
			softkey1.setVisible(false);
			remove(softkey1);
			softkey1.deleteImage();
			softkey1 = null;
		}
	}

	public void setOptionKey(boolean visible) {
		if (visible) {
			if (optionkey == null) {
				optionkey = new SoftKeyOptionFigure(screen);
			}
			add(optionkey);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
			Dimension size;
			if (tag == null) {
				size = new Dimension(Form.OPTION_DEFAULT_WIDTH,
						Form.OPTION_DEFAULT_HEIGHT);
			} else {
				size = tag.get(4).getSize();
			}
			optionkey.setLayout(new Rectangle((rect.width - size.width) / 2,
					rect.height - size.height, size.width, size.height));
			optionkey.setVisible(true);
		} else if (optionkey != null) {
			optionkey.setVisible(false);
			remove(optionkey);
			optionkey.deleteImage();
			optionkey = null;
		}
	}

	public void setSoftKeyText(int i, String softkeyText) {
		softKeyText[i] = softkeyText;
		if (i == 0 && softkey0 != null)
			softkey0.setSoftKeyText(softkeyText);
		else if (i == 1 && softkey1 != null)
			softkey1.setSoftKeyText(softkeyText);
	}

	public void setTextTab(boolean bSetting) {
		this.texttabLayoutStyle = bSetting;
		if (bSetting) {
			if (tab == null) {
				tab = new TabFigure(screen);
			}
			add(tab);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_TAB], screen);
			int tabY;
			if (tag == null) {
				tabY = Form.TAB_DEFAULT_HEIGHT;
			} else {
				tabY = tag.dftlSize.y;
			}
			Rectangle rect = new Rectangle(0, 0, this.rect.width, tabY);
			tab.setTextTab(bSetting);
			tab.setLayout(rect);
			tab.setVisible(true);
		} else if (tab != null && icontabLayoutStyle == false) {
			tab.setVisible(false);
			remove(tab);
			tab.deleteImage();
			tab = null;
		}
	}

	public void setIconTab(boolean bSetting) {
		this.icontabLayoutStyle = bSetting;
		if (bSetting) {
			if (tab == null) {
				tab = new TabFigure(screen);
			}
			add(tab);
			Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
					FrameConst.cszTag[FrameConst.WINDOW_TAB], screen);
			int tabY;
			if (tag == null) {
				tabY = Form.TAB_DEFAULT_HEIGHT;
			} else {
				tabY = tag.dftlSize.y;
			}
			Rectangle rect = new Rectangle(0, 0, this.rect.width, tabY);
			tab.setIconTab(bSetting);
			tab.setLayout(rect);
			tab.setVisible(true);
		} else if (tab != null && texttabLayoutStyle == false) {
			tab.setVisible(false);
			remove(tab);
			tab.deleteImage();
			tab = null;
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

		Tag_info indicatorInfo = ResourceExplorer.getResourceView()
				.getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_INDICATOR],
						screen);
		int indicatorY;
		if (indicatorInfo == null)
			indicatorY = Form.INDICATOR_DEFAULT_HEIGHT;
		else
			indicatorY = indicatorInfo.dftlSize.y;

		Rectangle indicatorRect = new Rectangle(0, 0, rect.width, indicatorY);
		if (indicator != null)
			setConstraint(indicator, indicatorRect);

		Tag_info tabInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TAB], screen);
		int tabY;
		Point tabMinSize;
		if (tabInfo == null) {
			tabY = Form.TAB_DEFAULT_HEIGHT;
			tabMinSize = new Point(Form.TAB_MIN_WIDTH,
					Form.TAB_MIN_HEIGHT);
		} else {
			tabY = tabInfo.dftlSize.y;
			tabMinSize = tabInfo.minSize;
		}

		Rectangle tabRect;
		if (indicator != null && indicator.isVisible())
			tabRect = new Rectangle(0, indicatorY - tabMinSize.y, rect.width,
					tabY);
		else
			tabRect = new Rectangle(0, -tabMinSize.y, rect.width, tabY);

		if (tab != null)
			setConstraint(tab, tabRect);

		Tag_info titleInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TITLE], screen);
		int titleY;
		Point titleMinSize;
		if (titleInfo == null) {
			titleY = Form.TITLE_DEFAULT_HEIGHT;
			titleMinSize = new Point(Form.TITLE_DEFAULT_WIDTH,
					Form.TITLE_DEFAULT_HEIGHT);
		} else {
			titleY = titleInfo.dftlSize.y;
			titleMinSize = titleInfo.minSize;
		}

		Rectangle titleRect;
		if (indicator != null && indicator.isVisible()) {
			titleRect = new Rectangle(0, indicatorY - titleMinSize.y,
					rect.width, titleY);
			if (tab != null && tab.isVisible())
				titleRect = new Rectangle(0, indicatorY - tabMinSize.y + tabY
						- tabMinSize.x - titleMinSize.y, rect.width, titleY);
			else if (title != null && title.isVisible())
				titleRect = new Rectangle(0, indicatorY - titleMinSize.y,
						rect.width, titleY);
		} else if (tab != null && tab.isVisible()) {
			titleRect = new Rectangle(0, tabY - tabMinSize.y - titleMinSize.y,
					rect.width, titleY);
		} else
			titleRect = new Rectangle(0, -titleMinSize.y, rect.width, titleY);

		if (title != null)
			setConstraint(title, titleRect);

		Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
		if (softkey0 != null && tag != null && tag.get(0) != null) {
			Dimension size = tag.get(0).getSize();
			if (softkey0.isSoftkey0Text()) {
				size = new Dimension(
						tag.get(1).getSize().width, tag.get(1)
								.getSize().height);

			}
			Rectangle softkeyRect = new Rectangle(0, rect.height - size.height,
					size.width, size.height);
			setConstraint(softkey0, softkeyRect);
		}

		if (softkey1 != null && tag != null && tag.get(2) != null) {
			Dimension size = tag.get(2).getSize();
			if (softkey1.isSoftkey1Text()) {
				size = new Dimension(
						tag.get(3).getSize().width, tag.get(3)
								.getSize().height);

			}
			Rectangle softkeyRect = new Rectangle(rect.width - size.width,
					rect.height - size.height, size.width, size.height);
			setConstraint(softkey1, softkeyRect);
		}

		if (optionkey != null && tag != null && tag.get(4) != null) {
			Dimension size = tag.get(4).getSize();
			Rectangle softkeyRect = new Rectangle(
					(rect.width - size.width) / 2, rect.height - size.height,
					size.width, size.height);
			setConstraint(optionkey, softkeyRect);
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
            guid.setFrame((Form) part.getModel());
            guid.setFrameRect(rect);
        }
        for (int i = 0; i < horizontalList.size(); i++) {
            OspElementGuide guid = (OspElementGuide) horizontalList.get(i);
            guid.setPosition(guid.getPosition() + (rect.x - oldRect.x));
            guid.setFrame((Form) part.getModel());
            guid.setFrameRect(rect);
        }
    }

	@Override
	protected void paintClientArea(Graphics graphics) {
		Point point = getLocation();
		part.getViewer().setProperty(SnapToGrid.PROPERTY_GRID_ORIGIN,
				new Point(point.x + 2, point.y + 2));

		if (controlImage != null) {
			Image image = new Image(null, controlImage.getAbsolutePath());
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

	public void setHeaderHeight(int headerheight) {
		if (headerHeight != headerheight)
			headerHeight = headerheight;
	}

	public void setSoftKey0Icon(File image) {
		softKey0Icon = image;
		if (softkey0 != null)
			softkey0.setSoftKey0Icon(image);
	}

	public void setSoftKey1Icon(File image) {
		softKey1Icon = image;
		if (softkey1 != null)
			softkey1.setSoftKey1Icon(image);
	}

	public void setGuideColor(Color color) {
		guideColor = color;
	}

	public void setTitleText(String titleText) {
		if (title != null)
			title.setText(titleText);
	}

	public Boolean getPaintGrid() {
		return grid;
	}

	public void setTitleIcon(File image) {
		titleIcon = image;
		if (title != null)
			title.setTitleIcon(image);
	}
	

    public void setOpacity(float opacity) {
        if (opacity != this.opacity) {
            this.opacity = opacity;
            createImage();
            repaint();
        }
    }
}
