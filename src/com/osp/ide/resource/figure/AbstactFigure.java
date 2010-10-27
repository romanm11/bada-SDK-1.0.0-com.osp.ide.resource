package com.osp.ide.resource.figure;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.ImageSettingDialog;
import com.osp.ide.resource.common.ImageUtil;
import com.osp.ide.resource.common.Image_info;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class AbstactFigure extends Figure {
    private static final String STRING_NINEPATCH = ".9.";

    protected class File_Info {
        public File image;
        public Point location;
        public Dimension size;

        public File_Info() {

        }
    }

    protected final Color blue_gray = new Color(null, 200, 211, 226);
    protected final Color light_blue = new Color(null, 211, 227, 244);

    protected Label textLabel = new Label();
    protected Color borderColor;
    protected Color titleColor = new Color(null, 119, 186, 221);
    public Image bgImage;
    public Image image;
    public String screen;
    protected Tag_info tag;
    private int diagLineWidth = 15;

    @SuppressWarnings("deprecation")
    public AbstactFigure(String screen) {
        if (screen == null || screen.isEmpty()) {
            org.eclipse.swt.graphics.Point point = Activator.getMaxScreen("");
            screen = point.x + "x" + point.y;
        }
        this.screen = screen;
        tag = getTagInfo();

        if (font != null && !font.isDisposed()) {
            font.dispose();
            font = null;
        }
        int textSize = Form.DEFAULT_TEXTSIZE;
        if (tag != null)
            textSize = tag.tSize;
        
        if(textSize < 20)
            font = new Font(null, new FontData(FrameNode.FontName, (int) (textSize * FrameConst.FONT_RATE), SWT.BOLD));
        else
            font = new Font(null, new FontData(FrameNode.FontName, (int) (textSize * FrameConst.FONT_RATE), SWT.NONE));
        setFont(font);
    }

    public Tag_info getTagInfo() {
        if (this instanceof FormFrameFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_FORM], screen);
        } else if (this instanceof ButtonFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_BUTTON], screen);
        } else if (this instanceof CheckFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_CHECKBUTTON], screen);
        } else if (this instanceof ColorPickerFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_COLORPICKER], screen);
        } else if (this instanceof DatePickerFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_DATEPICKER], screen);
        } else if (this instanceof EditFieldFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_EDITFIELD], screen);
        } else if (this instanceof EditAreaFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_EDITAREA], screen);
        } else if (this instanceof ExpandableListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_EXPANDABLELIST], screen);
        } else if (this instanceof FlashControlFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_FLASHCONTROL], screen);
        } else if (this instanceof GroupedListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_GROUPEDLIST], screen);
        } else if (this instanceof LabelFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_LABEL], screen);
        } else if (this instanceof SliderFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SLIDER], screen);
        } else if (this instanceof ProgressFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_PROGRESS], screen);
        } else if (this instanceof ListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_LIST], screen);
        } else if (this instanceof IconListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_ICONLIST], screen);
        } else if (this instanceof IndicatorFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_INDICATOR], screen);
        } else if (this instanceof TitleFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_TITLE], screen);
        } else if (this instanceof SoftKeyLeftFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
        } else if (this instanceof SoftKeyOptionFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
        } else if (this instanceof SoftKeyRightFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SOFTKEY], screen);
        } else if (this instanceof CustomListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_CUSTOMLIST], screen);
        } else if (this instanceof OverlayPanelFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_OVERLAYPANEL], screen);
        } else if (this instanceof PanelFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_PANEL], screen);
        } else if (this instanceof ScrollPanelFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SCROLLPANEL], screen);
        } else if (this instanceof SlidableGroupedListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SLIDABLEGROUPEDLIST], screen);
        } else if (this instanceof SlidableListFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SLIDABLELIST], screen);
        } else if (this instanceof TabFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_TAB], screen);
        } else if (this instanceof TimePickerFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_TIMEPICKER], screen);
        } else if (this instanceof PopupFrameFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_POPUP], screen);
        } else if (this instanceof PanelFrameFigure) {
            return ResourceExplorer.getResourceView().getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_SCROLLPANEL], screen);
        }

        return null;
    }

	protected boolean isScotia() {
		return screen != null && screen.toUpperCase(Locale.getDefault()).equals(ResourceExplorer.WQVGA);
	}

    public Label getLabel() {
        return textLabel;
    }

    public int getTitleSize() {
        return 0;
    }

    @SuppressWarnings("deprecation")
    public void deleteImage() {
        if (bgImage != null) {
            bgImage.dispose();
            bgImage = null;
        }
        if (image != null) {
            image.dispose();
            image = null;
        }
        if (font != null && !font.isDisposed()) {
            font.dispose();
            font = null;
        }
        if (borderColor != null && !borderColor.isDisposed()) {
            borderColor.dispose();
            borderColor = null;
        }
        if (titleColor != null && !titleColor.isDisposed()) {
            titleColor.dispose();
            titleColor = null;
        }
        if(blue_gray != null && blue_gray.isDisposed()) {
        	blue_gray.dispose();
        }
        if(light_blue != null && light_blue.isDisposed()) {
        	light_blue.dispose();
        }
    }

    public void createImage(Image imageparam, Dimension size, int hAlign, int vAlign, int left, int right, int top, int bottom) {
        BufferedImage origin, swtImage;
        Color bgcolor = getBackgroundColor();
        if (bgcolor == null || size == null || size.width <= 0 || size.height <= 0)
            return;

        if (imageparam == null)
            return;

        origin = ImageUtil.convertToAWT(imageparam.getImageData(), bgcolor);
        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = swtImage.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 0, 0, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 0, origin.getWidth() - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, origin.getWidth() - right, 0, origin.getWidth(), top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 0, top, left, origin.getHeight() - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, origin.getWidth() - right, origin.getHeight() - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, origin.getWidth() - right, top, origin.getWidth(), origin.getHeight() - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 0, origin.getHeight() - bottom, left, origin.getHeight(), null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, origin.getHeight() - bottom, origin.getWidth() - right, origin.getHeight(), null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, origin.getWidth() - right, origin.getHeight() - bottom, origin.getWidth(), origin.getHeight(), null);

        g2.dispose();

        String text = textLabel.getText();
        if (text != null && !text.isEmpty())
            swtImage = ImageUtil.drawStringAWT(swtImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Image imageparam, Dimension size, int left, int right, int top, int bottom) {
        BufferedImage origin, swtImage;
        Color bgcolor = getBackgroundColor();
        if (bgcolor == null || size == null || size.width <= 0 || size.height <= 0)
            return;

        if (imageparam == null)
            return;

        origin = ImageUtil.convertToAWT(imageparam.getImageData(), bgcolor);
        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = swtImage.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 0, 0, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 0, origin.getWidth() - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, origin.getWidth() - right, 0, origin.getWidth(), top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 0, top, left, origin.getHeight() - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, origin.getWidth() - right, origin.getHeight() - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, origin.getWidth() - right, top, origin.getWidth(), origin.getHeight() - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 0, origin.getHeight() - bottom, left, origin.getHeight(), null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, origin.getHeight() - bottom, origin.getWidth() - right, origin.getHeight(), null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, origin.getWidth() - right, origin.getHeight() - bottom, origin.getWidth(), origin.getHeight(), null);

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(File img, Dimension size, int hAlign, int vAlign, int margin) {
        BufferedImage origin, swtImage;
        Color bgcolor = getBackgroundColor();
        if (bgcolor == null || size == null || size.width <= 0 || size.height <= 0)
            return;

        if (img == null)
            return;

        try {
            origin = ImageIO.read(img);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return;
        }

        if (checkNinepatch(img.getName(), origin, size))
            origin = getNinepatch(origin, size);
        else
            origin = ImageUtil.createScaledImage(origin, size.width, size.height);

        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = swtImage.createGraphics();

        g2.drawImage(origin, 0, 0, null);

        g2.dispose();

        String text = textLabel.getText();
        if (text != null && !text.isEmpty())
            swtImage = ImageUtil.drawStringAWT(swtImage, getForegroundColor(), text, getFont(), hAlign, vAlign, margin);
        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public Image getImage(File img, Dimension size, int hAlign, int vAlign, int margin) {
        BufferedImage origin, swtImage;
        if (size == null || size.width <= 0 || size.height <= 0)
            return null;

        if (img == null)
            return null;

        try {
            origin = ImageIO.read(img);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        if (checkNinepatch(img.getName(), origin, size))
            origin = getNinepatch(origin, size);
        else
            origin = ImageUtil.createScaledImage(origin, size.width, size.height);

        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = swtImage.createGraphics();

        g2.drawImage(origin, 0, 0, null);

        g2.dispose();

        String text = textLabel.getText();
        if (text != null && !text.isEmpty())
            swtImage = ImageUtil.drawStringAWT(swtImage, getForegroundColor(), text, getFont(), hAlign, vAlign, margin);

        return new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public BufferedImage getNoneChromakeyImage(String name, Dimension size) {
        BufferedImage origin, target;
        if (size == null || size.width <= 0 || size.height <= 0)
            return null;

        if (name == null || name.isEmpty())
            return null;

        File dir = ImageSettingDialog.getImageDir();

        File file = new File(dir.getAbsolutePath() + File.separator + screen + "/" + name);
        try {
            origin = ImageIO.read(file);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        Dimension sourcesize = new Dimension(origin.getWidth() - 1, origin.getHeight() - 1);

        if (size.width <= 0 || size.height <= 0)
            return null;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();

        g2.drawImage(origin, 0, 0, size.width, size.height, 1, 1, sourcesize.width, sourcesize.height, null);

        g2.dispose();
        return target;
    }

    public Image getImage(File img, Dimension size) {
        BufferedImage origin, swtImage;
        if (size == null || size.width <= 0 || size.height <= 0)
            return null;

        if (img == null)
            return null;

        try {
            origin = ImageIO.read(img);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        if (checkNinepatch(img.getName(), origin, size))
            origin = getNinepatch(origin, size);
        else
            origin = ImageUtil.createScaledImage(origin, size.width, size.height);

        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = swtImage.createGraphics();

        g2.drawImage(origin, 0, 0, null);

        g2.dispose();

        return new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public BufferedImage getBufferedImage(File img, Dimension size) {
        BufferedImage origin, swtImage;
        if (size == null || size.width <= 0 || size.height <= 0)
            return null;

        if (img == null)
            return null;

        try {
            origin = ImageIO.read(img);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        if (checkNinepatch(img.getName(), origin, size))
            origin = getNinepatch(origin, size);
        else
            origin = ImageUtil.createScaledImage(origin, size.width, size.height);

        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = swtImage.createGraphics();

        g2.drawImage(origin, 0, 0, null);

        g2.dispose();

        return swtImage;
    }

    public void createImage(File img, Dimension size, int left, int right, int top, int bottom) {
        BufferedImage origin, swtImage;
        Color bgcolor = getBackgroundColor();
        if (bgcolor == null || size == null || size.width <= 0 || size.height <= 0)
            return;

        if (image == null)
            return;

        try {
            origin = ImageIO.read(img);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return;
        }

        swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Dimension sourcesize = new Dimension(origin.getWidth() - 1, origin.getHeight() - 1);
        Graphics2D g2 = swtImage.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 1, sourcesize.width - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, sourcesize.width - right, 1, sourcesize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 1, top, left, sourcesize.height - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, sourcesize.width - right, sourcesize.height - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, sourcesize.width - right, top, sourcesize.width, sourcesize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 1, sourcesize.height - bottom, left, sourcesize.height, null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, sourcesize.height - bottom, sourcesize.width - right, sourcesize.height, null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, sourcesize.width - right, sourcesize.height - bottom, sourcesize.width, sourcesize.height, null);

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, Color bgcolor, int hAlign, int vAlign) {
        if (size == null || size.width <= 0 || size.height <= 0)
            return;
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.elementAt(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                if (i == 0) {
                    temp = createImage(image, loc, size, sourcesize, bgcolor);
                } else
                    temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && info != null && size.width - info.textlMargin - info.textrMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - info.textlMargin - info.textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            if (this instanceof TitleFigure)
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, info.minSize.y);
            else
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, info.textlMargin, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, Color bgcolor, int hAlign, int vAlign, float opacity) {
        if (size == null || size.width <= 0 || size.height <= 0 || info == null)
            return;
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.elementAt(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                if (i == 0) {
                    temp = createImage(image, loc, size, sourcesize, bgcolor, opacity);
                } else
                    temp = createImage(image, loc, size, sourcesize, null, opacity);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor, opacity);
                else
                    temp = createImage(image, size, null, opacity);
            }

            g2.drawImage(temp, 0, 0, null);
        }

        int textlMargin = 0, textrMargin = 0;
        if (info != null) {
            textlMargin = info.textlMargin;
            textrMargin = info.textrMargin;
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textlMargin - textrMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textlMargin - textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            if (this instanceof TitleFigure)
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, info.minSize.y);
            else
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textlMargin, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(BufferedImage backgroundImage, Dimension size, Vector<File_Info> file, int hAlign, int vAlign, int textLMargin, int textRMargin) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        g2.drawImage(backgroundImage, 0, 0, null);
        for (int i = 0; i < file.size(); i++) {
            File_Info image = file.get(i);

            try {
                temp = ImageIO.read(image.image);
            } catch (IOException e) {
                Activator.setErrorMessage("createImage", e.getMessage(), e);
                continue;
            }

            g2.drawImage(temp, image.location.x, image.location.y, image.size.width, image.size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textLMargin - textRMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textLMargin - textRMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textLMargin, 0, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(File backgroundImage, Dimension size, Vector<File_Info> file, int hAlign, int vAlign, int textLMargin, int textRMargin) {
        BufferedImage temp = null, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        try {
            temp = ImageIO.read(backgroundImage);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
        }
        if (temp != null && checkNinepatch(backgroundImage.getName(), temp, size))
            temp = getNoneChromakeyNinepatch(temp, size);

        if(temp != null)
            g2.drawImage(temp, 0, 0, size.width, size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        for (int i = 0; i < file.size(); i++) {
            File_Info image = file.get(i);

            try {
                temp = ImageIO.read(image.image);
            } catch (IOException e) {
                Activator.setErrorMessage("createImage", e.getMessage(), e);
                continue;
            }

            if (image.location == null)
                image.location = new Point();
            if (image.size == null)
                image.size = new Dimension(temp.getWidth(), temp.getHeight());

            if (image.location.x + image.size.width > size.width || image.location.y + image.size.height > size.height) {
                image.location.x = 0;
                image.location.y = 0;
                image.size = size;
            }

            if (checkNinepatch(backgroundImage.getName(), temp, image.size))
                temp = getNoneChromakeyNinepatch(temp, image.size);

            g2.drawImage(temp, image.location.x, image.location.y, image.location.x + image.size.width, image.location.y + image.size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textLMargin - textRMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textLMargin - textRMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textLMargin, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(File backgroundImage, Dimension size, Vector<File_Info> file, int hAlign, int vAlign, int textLMargin, int textRMargin, float opacity) {
        BufferedImage temp = null, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        try {
            temp = ImageIO.read(backgroundImage);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
        }
        if (temp != null && checkNinepatch(backgroundImage.getName(), temp, size))
            temp = getNoneChromakeyNinepatch(temp, size);

        Composite oldComposite = g2.getComposite();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC, opacity);
        g2.setComposite(ac);

        if(temp != null)
            g2.drawImage(temp, 0, 0, size.width, size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        for (int i = 0; i < file.size(); i++) {
            File_Info image = file.get(i);

            try {
                temp = ImageIO.read(image.image);
            } catch (IOException e) {
                Activator.setErrorMessage("createImage", e.getMessage(), e);
                continue;
            }

            if (image.location == null)
                image.location = new Point();
            if (image.size == null)
                image.size = new Dimension(temp.getWidth(), temp.getHeight());

            g2.drawImage(temp, image.location.x, image.location.y, image.location.x + image.size.width, image.location.y + image.size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textLMargin - textRMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textLMargin - textRMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.setComposite(oldComposite);
            g2.drawImage(textImage, textLMargin, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, Vector<File_Info> file, String text, int hAlign, int vAlign) {
        if (info == null)
            return;

        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        temp = makeImage(info, size, null);
        g2.drawImage(temp, 0, 0, null);

        for (int i = 0; i < file.size(); i++) {
            File_Info image = file.get(i);

            try {
                temp = ImageIO.read(image.image);
            } catch (IOException e) {
                Activator.setErrorMessage("createImage", e.getMessage(), e);
                continue;
            }

            if (image.location == null)
                image.location = new Point();
            if (image.size == null)
                image.size = new Dimension(temp.getWidth(), temp.getHeight());

            if (image.location.x + image.size.width > size.width || image.location.y + image.size.height > size.height) {
                image.location.x = 0;
                image.location.y = 0;
                image.size = size;
            }

            if (checkNinepatch(image.image.getName(), temp, image.size))
                temp = getNoneChromakeyNinepatch(temp, image.size);

            g2.drawImage(temp, image.location.x, image.location.y, image.location.x + image.size.width, image.location.y + image.size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        }

        if (text == null || text.isEmpty())
            text = textLabel.getText();
        if (text != null && !text.isEmpty()) {
            BufferedImage textImage = new BufferedImage(size.width - info.textlMargin - info.textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            if (this instanceof TitleFigure)
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, info.minSize.y + 8);
            else
                textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 5);
            g2.drawImage(textImage, info.textlMargin, 0, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, File_Info file, int padding, int vPadding, String text, int hAlign, int vAlign) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        if (text == null || text.isEmpty())
            text = textLabel.getText();
        Graphics2D g2 = swtImage.createGraphics();

        temp = makeImage(info, size, null);
        g2.drawImage(temp, 0, 0, null);

        File_Info image = file;

        if (image.image != null) {
            try {
                temp = ImageIO.read(image.image);
            } catch (IOException e) {
                Activator.setErrorMessage("createImage", e.getMessage(), e);
                return;
            }

            Dimension textSize = getTextSize(g2, text);

            if (image.location == null)
                image.location = new Point();
            if (image.size == null)
                image.size = new Dimension(temp.getWidth(), temp.getHeight());

            switch (hAlign) {
            case SWT.LEFT:
                image.location.x = padding;
                break;
            case SWT.CENTER:
                image.location.x = (size.width - (image.size.width + textSize.width + padding)) / 2;
                break;
            case SWT.RIGHT:
                image.location.x = size.width - (image.size.width + textSize.width + padding + info.textrMargin + 6);
                break;
            default:
                image.location.x = padding;
            }

            switch (vAlign) {
            case SWT.TOP:
                image.location.y = vPadding;
                break;
            case SWT.CENTER:
                image.location.y = (size.height - image.size.height) / 2;
                break;
            case SWT.BOTTOM:
                image.location.y = size.height - image.size.height - vPadding;
                break;
            default:
                image.location.y = vPadding;
            }

            if (checkNinepatch(image.image.getName(), temp, image.size))
                temp = getNoneChromakeyNinepatch(temp, image.size);

            g2.drawImage(temp, image.location.x, image.location.y, image.location.x + image.size.width, image.location.y + image.size.height, 0, 0, temp.getWidth(), temp.getHeight(), null);
        }

        if (text != null && !text.isEmpty()) {
            BufferedImage textImage = new BufferedImage(size.width - info.textlMargin - info.textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);

            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, padding, vPadding + 4);

            g2.drawImage(textImage, info.textlMargin, 0, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    private Dimension getTextSize(Graphics2D g2, String text) {
        FontData fontdata = getFont().getFontData()[0];
        java.awt.Font awtFont = new java.awt.Font(fontdata.getName(), fontdata.getStyle(), fontdata.getHeight());

        int fontWidth = g2.getFontMetrics(awtFont).stringWidth(text);
        int fontHeight = g2.getFontMetrics(awtFont).getAscent();
        Dimension textSize = new Dimension(fontWidth, fontHeight);

        return textSize;
    }

    protected BufferedImage getTextImage(Dimension size, Color fgColor, int fontSize, String text, int hAlign, int vAlign, int padding, int vPadding) {
        if (fgColor == null)
            fgColor = getForegroundColor();

        if (size.width <= 0)
            size.width = 1;
        if (size.height <= 0)
            size.height = 1;

        BufferedImage textImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Font font;
        if(fontSize < 20)
            font= new Font(null, new FontData(FrameNode.FontName, (int) (fontSize * FrameConst.FONT_RATE), SWT.BOLD));
        else
            font= new Font(null, new FontData(FrameNode.FontName, (int) (fontSize * FrameConst.FONT_RATE), SWT.NONE));
        textImage = ImageUtil.drawStringAWT(textImage, fgColor, text, font, hAlign, vAlign, padding, vPadding);
        font.dispose();

        return textImage;
    }

    protected Image getTextSWTImage(Dimension size, Color fgColor, int fontSize, String text, int hAlign, int vAlign, int padding, int vPadding) {
        if (fgColor == null)
            fgColor = getForegroundColor();

        if (size.width <= 0)
            size.width = 1;
        if (size.height <= 0)
            size.height = 1;

        BufferedImage textImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Font font;
        if(fontSize < 20)
            font= new Font(null, new FontData(FrameNode.FontName, (int) (fontSize * FrameConst.FONT_RATE), SWT.BOLD));
        else
            font= new Font(null, new FontData(FrameNode.FontName, (int) (fontSize * FrameConst.FONT_RATE), SWT.NONE));
        textImage = ImageUtil.drawStringAWT(textImage, fgColor, text, font, hAlign, vAlign, padding, vPadding);
        font.dispose();

        return new Image(Display.getDefault(), ImageUtil.convertToSWT(textImage));
    }

    public void createImage(Tag_info info, Dimension size, String text, Dimension textSize, int hAlign, int vAlign) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        temp = makeImage(info, size, null);
        g2.drawImage(temp, 0, 0, null);

        if (text == null || text.isEmpty())
            text = textLabel.getText();
        if (text != null && !text.isEmpty()) {
            BufferedImage textImage = new BufferedImage(textSize.width - info.textlMargin - info.textrMargin, textSize.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, info.textlMargin, 0, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, String text, Point textLoc, Dimension textSize, int hAlign, int vAlign) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        temp = makeImage(info, size, null);
        g2.drawImage(temp, 0, 0, null);

        if (textSize == null)
            textSize = size.getCopy();
        if (text == null || text.isEmpty())
            text = textLabel.getText();
        if (text != null && !text.isEmpty()) {
            BufferedImage textImage = new BufferedImage(textSize.width, textSize.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textLoc.x, textLoc.y, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, Color fgColor, String text, Point textLoc, Dimension textSize, int hAlign, int vAlign) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        
        if(fgColor == null)
            fgColor = getForegroundColor();

        Graphics2D g2 = swtImage.createGraphics();

        temp = makeImage(info, size, null);
        g2.drawImage(temp, 0, 0, null);

        if (textSize == null)
            textSize = size.getCopy();
        if (text == null || text.isEmpty())
            text = textLabel.getText();
        if (text != null && !text.isEmpty()) {
            BufferedImage textImage = new BufferedImage(textSize.width, textSize.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, fgColor, text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textLoc.x, textLoc.y, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, String titleText, Color titleColor, int titleFontSize, int hAlign, int vAlign) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();

        if (info != null) {
            temp = makeImage(info, size, null);
            g2.drawImage(temp, 0, 0, null);
        }

        int textlMargin = 0, textrMargin = 0, vMargin = 0;
        if (info != null) {
            textlMargin = info.textlMargin;
            textrMargin = info.textrMargin;
        }

        if (size.width - textlMargin - textrMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textlMargin - textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            Font font;
            if(titleFontSize < 22)
                font = new Font(null, new FontData(FrameNode.FontName, (int) (titleFontSize * FrameConst.FONT_RATE) - 4, SWT.BOLD));
            else    
                font = new Font(null, new FontData(FrameNode.FontName, (int) (titleFontSize * FrameConst.FONT_RATE) - 4, SWT.NONE));
            
            if (this instanceof EditFieldFigure)
                vMargin = titleFontSize;
            else if (this instanceof CheckFigure) {
                if (((CheckFigure) this).getVAlign() == FrameConst.ALIGN_TOP)
                    vMargin = (int) (titleFontSize * FrameConst.FONT_RATE);
                else
                    vMargin = 0;
            } else
                vMargin = titleFontSize;

            if (titleText != null && !titleText.isEmpty()) {
                if (this instanceof EditFieldFigure)
                    textImage = ImageUtil.drawStringAWT(textImage, titleColor, titleText, font, titleFontSize / 3, titleFontSize / 6);
                else if (this instanceof SliderFigure)
                    textImage = ImageUtil.drawStringAWT(textImage, titleColor, titleText, font, 0, 6);
                else
                    textImage = ImageUtil.drawStringAWT(textImage, titleColor, titleText, font, titleFontSize / 3, titleFontSize / 6);
                g2.drawImage(textImage, textlMargin, 0, null);
            }
            font.dispose();
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textlMargin - textrMargin > 0) {
            BufferedImage textImage;
            if (this instanceof EditFieldFigure)
                textImage = new BufferedImage(size.width - textlMargin - textrMargin, size.height - vMargin - 10, BufferedImage.TYPE_INT_ARGB);
            else
                textImage = new BufferedImage(size.width - textlMargin - textrMargin, size.height - vMargin, BufferedImage.TYPE_INT_ARGB);

            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textlMargin, vMargin, null);
        }

        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public BufferedImage makeImage(Tag_info info, Dimension size, Color bgcolor, int hAlign, int vAlign, int tMargin) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.elementAt(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }

        int textlMargin = 0, textrMargin = 0;
        if (info != null) {
            textlMargin = info.textlMargin;
            textrMargin = info.textrMargin;
        }

        String text = textLabel.getText();
        if (text != null && !text.isEmpty() && size.width - textlMargin - textrMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - textlMargin - textrMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, textlMargin, 0, null);
        }
        g2.dispose();

        return swtImage;
    }

    public void createImage(Tag_info info, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(Tag_info info, Dimension size, Color bgcolor, float opacity) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null, opacity);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor, opacity);
                else
                    temp = createImage(image, size, null, opacity);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public BufferedImage makeImage(Tag_info info, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        return swtImage;
    }

    public void createImage(List<Image_info> info, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public void createImage(List<Image_info> info, Dimension size, Color bgcolor, float opacity) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            Image_info image = info.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null, opacity);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor, opacity);
                else
                    temp = createImage(image, size, null, opacity);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        if (bgImage != null)
            bgImage.dispose();
        bgImage = null;
        bgImage = new Image(Display.getDefault(), ImageUtil.convertToSWT(swtImage));
    }

    public BufferedImage makeImage(List<Image_info> list, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; list != null && i < list.size(); i++) {
            Image_info image = list.get(i);
            Point loc = new Point();

            if (image.isFix()) {

                Dimension sourcesize = image.getSize();
                if (sourcesize.width <= 0) {
                    sourcesize.width = size.width;
                    if (!image.isHAlign())
                        sourcesize.width -= image.getSpaceW();
                    else if (image.getSpaceW() != 0)
                        sourcesize.width -= image.getSpaceW() * 2;
                }
                if (sourcesize.height <= 0) {
                    sourcesize.height = size.height;
                    if (!image.isVAlign())
                        sourcesize.height -= image.getSpaceH();
                    else if (image.getSpaceH() != 0)
                        sourcesize.height -= image.getSpaceH() * 2;
                }

                if (image.isVAlign()) {
                    loc.y = (size.height - sourcesize.height) / 2;
                } else if (image.isTop()) {
                    loc.y = image.getSpaceH();
                } else if (image.isBottom()) {
                    loc.y = size.height - (sourcesize.height + image.getSpaceH());
                }

                if (image.isHAlign()) {
                    loc.x = (size.width - sourcesize.width) / 2;
                } else if (image.isRight()) {
                    loc.x = size.width - (sourcesize.width + image.getSpaceW());
                } else if (image.isLeft()) {
                    loc.x = image.getSpaceW();
                }

                temp = createImage(image, loc, size, sourcesize, null);
            } else {
                if (i == 0)
                    temp = createImage(image, size, bgcolor);
                else
                    temp = createImage(image, size, null);
            }

            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        return swtImage;
    }

    public BufferedImage makeImage(Image_info info, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        Point loc = new Point();

        if (info.isFix()) {
            Dimension sourcesize = info.getSize();
            if (sourcesize.width <= 0) {
                sourcesize.width = size.width;
                if (!info.isHAlign())
                    sourcesize.width -= info.getSpaceW();
                else if (info.getSpaceW() != 0)
                    sourcesize.width -= info.getSpaceW() * 2;
            }
            if (sourcesize.height <= 0) {
                sourcesize.height = size.height;
                if (!info.isVAlign())
                    sourcesize.height -= info.getSpaceH();
                else if (info.getSpaceH() != 0)
                    sourcesize.height -= info.getSpaceH() * 2;
            }

            if (info.isVAlign()) {
                loc.y = (size.height - sourcesize.height) / 2;
            } else if (info.isTop()) {
                loc.y = info.getSpaceH();
            } else if (info.isBottom()) {
                loc.y = size.height - (sourcesize.height + info.getSpaceH());
            }

            if (info.isHAlign()) {
                loc.x = (size.width - sourcesize.width) / 2;
            } else if (info.isRight()) {
                loc.x = size.width - (sourcesize.width + info.getSpaceW());
            } else if (info.isLeft()) {
                loc.x = info.getSpaceW();
            }

            temp = createImage(info, loc, size, sourcesize, bgcolor);
        } else {
            temp = createImage(info, size, bgcolor);
        }

        g2.drawImage(temp, 0, 0, null);
        g2.dispose();

        return swtImage;
    }

    public BufferedImage createImage(Image_info info, Dimension size, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            if (i == 0 && bgcolor != null) {
                java.awt.Color color = new java.awt.Color(bgcolor.getRed(), bgcolor.getGreen(), bgcolor.getBlue());
                temp = getImage(info.getName(i), size, color, info.getOpacity(i));
            } else
                temp = getImage(info.getName(i), size, info.getColor(i), info.getOpacity(i));
            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        return swtImage;
    }

    public BufferedImage createImage(Image_info info, Dimension size, Color bgcolor, float opacity) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            if (i == 0 && bgcolor != null) {
                java.awt.Color color = new java.awt.Color(bgcolor.getRed(), bgcolor.getGreen(), bgcolor.getBlue());
                temp = getImage(info.getName(i), size, color, opacity);
            } else
                temp = getImage(info.getName(i), size, info.getColor(i), opacity);
            g2.drawImage(temp, 0, 0, null);
        }
        g2.dispose();

        return swtImage;
    }

    public BufferedImage createImage(Image_info info, Dimension size, Color bgcolor, String text, int hAlign, int vAlign, int lMargin, int rMargin) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            if (i == 0 && bgcolor != null) {
                java.awt.Color color = new java.awt.Color(bgcolor.getRed(), bgcolor.getGreen(), bgcolor.getBlue());
                temp = getImage(info.getName(i), size, color, info.getOpacity(i));
            } else
                temp = getImage(info.getName(i), size, info.getColor(i), info.getOpacity(i));
            g2.drawImage(temp, 0, 0, null);
        }

        if (text != null && !text.isEmpty() && size.width - lMargin - rMargin > 0) {
            BufferedImage textImage = new BufferedImage(size.width - lMargin - rMargin, size.height, BufferedImage.TYPE_INT_ARGB);
            textImage = ImageUtil.drawStringAWT(textImage, getForegroundColor(), text, getFont(), hAlign, vAlign, 10);
            g2.drawImage(textImage, lMargin, 0, null);
        }

        g2.dispose();

        return swtImage;
    }

    public BufferedImage createImage(Image_info info, Point loc, Dimension size, Dimension sourcesize, Color bgcolor) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            if (i == 0 && bgcolor != null) {
                java.awt.Color color = new java.awt.Color(bgcolor.getRed(), bgcolor.getGreen(), bgcolor.getBlue());

                temp = getImage(info.getName(i), sourcesize, color, info.getOpacity(i));
            } else {
                temp = getImage(info.getName(i), sourcesize, info.getColor(i), info.getOpacity(i));
            }
            g2.drawImage(temp, loc.x, loc.y, null);
        }
        g2.dispose();

        return swtImage;
    }

    public BufferedImage createImage(Image_info info, Point loc, Dimension size, Dimension sourcesize, Color bgcolor, float opacity) {
        BufferedImage temp, swtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = swtImage.createGraphics();
        for (int i = 0; info != null && i < info.size(); i++) {
            if (i == 0 && bgcolor != null) {
                java.awt.Color color = new java.awt.Color(bgcolor.getRed(), bgcolor.getGreen(), bgcolor.getBlue());

                temp = getImage(info.getName(i), sourcesize, color, opacity);
            } else {
                temp = getImage(info.getName(i), sourcesize, info.getColor(i), opacity);
            }
            g2.drawImage(temp, loc.x, loc.y, null);
        }
        g2.dispose();

        return swtImage;
    }

    public BufferedImage getImage(String path, Dimension size, java.awt.Color bgcolor, float opacity) {
        if (path == null || path.isEmpty())
            return null;

        if (bgcolor.equals(new java.awt.Color(0, 0, 0)) && opacity == 0)
            return getNoneChromakey(path, size);
        else if (checkNinepatch(path, size))
            return getNinepatch(path, size, bgcolor, opacity);
        else
            return getTransImage(path, size, bgcolor, opacity);
    }

    public BufferedImage getNoneChromakey(String path, Dimension size) {
        if (path == null || path.isEmpty())
            return null;

        if (checkNinepatch(path, size))
            return getNoneChromakeyNinepatch(path, size);
        else
            return getNoneChromakeyImage(path, size);
    }

    public BufferedImage getNoneChromakeyNinepatch(String name, Dimension size) {
        BufferedImage origin, target;
        int n, left = 5, right = 5, top = 5, bottom = 5;
        if (size == null || size.width == 0 || size.height == 0)
            return null;

        if (name == null || name.isEmpty())
            return null;

        File dir = ImageSettingDialog.getImageDir();

        File file = new File(dir.getAbsolutePath() + File.separator + screen + "/" + name);
        try {
            origin = ImageIO.read(file);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        Dimension sourceSize = new Dimension(origin.getWidth(), origin.getHeight());
        for (n = 0; n < origin.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < origin.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(n, 0))) {
                right = sourceSize.width - n - 1;
                break;
            }
        }

        for (n = 0; n < origin.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < origin.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(0, n))) {
                bottom = sourceSize.height - n - 1;
                break;
            }
        }

        sourceSize.width -= 1;
        sourceSize.height -= 1;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 1, sourceSize.width - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, sourceSize.width - right, 1, sourceSize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 1, top, left, sourceSize.height - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, sourceSize.width - right, sourceSize.height - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, sourceSize.width - right, top, sourceSize.width, sourceSize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 1, sourceSize.height - bottom, left, sourceSize.height, null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, sourceSize.height - bottom, sourceSize.width - right, sourceSize.height, null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, sourceSize.width - right, sourceSize.height - bottom, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getNoneChromakeyNinepatch(BufferedImage imageparam, Dimension size) {
        BufferedImage target;
        int n, left = 5, right = 5, top = 5, bottom = 5;
        if (size == null || size.width == 0 || size.height == 0)
            return null;

        if (imageparam == null)
            return null;

        Dimension sourceSize = new Dimension(imageparam.getWidth(), imageparam.getHeight());
        for (n = 0; n < imageparam.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == imageparam.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < imageparam.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == imageparam.getRGB(n, 0))) {
                right = sourceSize.width - n - 1;
                break;
            }
        }

        for (n = 0; n < imageparam.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == imageparam.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < imageparam.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == imageparam.getRGB(0, n))) {
                bottom = sourceSize.height - n - 1;
                break;
            }
        }

        sourceSize.width -= 1;
        sourceSize.height -= 1;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(imageparam, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(imageparam, left, 0, size.width - right, top, left, 1, sourceSize.width - right, top, null);
        g2.drawImage(imageparam, size.width - right, 0, size.width, top, sourceSize.width - right, 1, sourceSize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(imageparam, 0, top, left, size.height - bottom, 1, top, left, sourceSize.height - bottom, null);
        g2.drawImage(imageparam, left, top, size.width - right, size.height - bottom, left, top, sourceSize.width - right, sourceSize.height - bottom, null);
        g2.drawImage(imageparam, size.width - right, top, size.width, size.height - bottom, sourceSize.width - right, top, sourceSize.width, sourceSize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(imageparam, 0, size.height - bottom, left, size.height, 1, sourceSize.height - bottom, left, sourceSize.height, null);
        g2.drawImage(imageparam, left, size.height - bottom, size.width - right, size.height, left, sourceSize.height - bottom, sourceSize.width - right, sourceSize.height, null);
        g2.drawImage(imageparam, size.width - right, size.height - bottom, size.width, size.height, sourceSize.width - right, sourceSize.height - bottom, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getNinepatch(String name, Dimension size, java.awt.Color bgcolor, float alphaValue) {
        BufferedImage origin, target;
        int n, left = 5, right = 5, top = 5, bottom = 5;
        if (bgcolor == null || size == null || size.width == 0 || size.height == 0)
            return null;

        if (name == null || name.isEmpty())
            return null;

        File dir = ImageSettingDialog.getImageDir();

        File file = new File(dir.getAbsolutePath() + File.separator + screen + "/" + name);
        try {
            origin = ImageIO.read(file);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        Dimension sourceSize = new Dimension(origin.getWidth(), origin.getHeight());
        for (n = 0; n < origin.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < origin.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(n, 0))) {
                right = sourceSize.width - n - 1;
                break;
            }
        }

        for (n = 0; n < origin.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < origin.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(0, n))) {
                bottom = sourceSize.height - n - 1;
                break;
            }
        }

        sourceSize.width -= 1;
        sourceSize.height -= 1;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, alphaValue);

        g2.setColor(bgcolor);
        g2.fillRect(0, 0, size.width, size.height);

        g2.setComposite(ac);
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 1, sourceSize.width - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, sourceSize.width - right, 1, sourceSize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 1, top, left, sourceSize.height - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, sourceSize.width - right, sourceSize.height - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, sourceSize.width - right, top, sourceSize.width, sourceSize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 1, sourceSize.height - bottom, left, sourceSize.height, null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, sourceSize.height - bottom, sourceSize.width - right, sourceSize.height, null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, sourceSize.width - right, sourceSize.height - bottom, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getNinepatch(BufferedImage origin, Dimension size) {
        BufferedImage target;
        int n, left = 5, right = 5, top = 5, bottom = 5;
        if (size == null || size.width == 0 || size.height == 0)
            return null;

        Dimension sourceSize = new Dimension(origin.getWidth(), origin.getHeight());
        for (n = 0; n < origin.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < origin.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(n, 0))) {
                right = sourceSize.width - n - 1;
                break;
            }
        }

        for (n = 0; n < origin.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < origin.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(0, n))) {
                bottom = sourceSize.height - n - 1;
                break;
            }
        }

        sourceSize.width -= 1;
        sourceSize.height -= 1;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();

        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 1, sourceSize.width - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, sourceSize.width - right, 1, sourceSize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 1, top, left, sourceSize.height - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, sourceSize.width - right, sourceSize.height - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, sourceSize.width - right, top, sourceSize.width, sourceSize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 1, sourceSize.height - bottom, left, sourceSize.height, null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, sourceSize.height - bottom, sourceSize.width - right, sourceSize.height, null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, sourceSize.width - right, sourceSize.height - bottom, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getNinepatch(BufferedImage origin, Dimension size, java.awt.Color bgcolor, float alphaValue) {
        BufferedImage target;
        int n, left = 5, right = 5, top = 5, bottom = 5;
        if (bgcolor == null || size == null || size.width == 0 || size.height == 0)
            return null;

        Dimension sourceSize = new Dimension(origin.getWidth(), origin.getHeight());
        for (n = 0; n < origin.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < origin.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(n, 0))) {
                right = sourceSize.width - n - 1;
                break;
            }
        }

        for (n = 0; n < origin.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == origin.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < origin.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == origin.getRGB(0, n))) {
                bottom = sourceSize.height - n - 1;
                break;
            }
        }

        sourceSize.width -= 1;
        sourceSize.height -= 1;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = target.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, alphaValue);

        g2.setColor(bgcolor);
        g2.fillRect(0, 0, size.width, size.height);

        g2.setComposite(ac);
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 1, 1, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 1, sourceSize.width - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, sourceSize.width - right, 1, sourceSize.width, top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 1, top, left, sourceSize.height - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, sourceSize.width - right, sourceSize.height - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, sourceSize.width - right, top, sourceSize.width, sourceSize.height - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 1, sourceSize.height - bottom, left, sourceSize.height, null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, sourceSize.height - bottom, sourceSize.width - right, sourceSize.height, null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, sourceSize.width - right, sourceSize.height - bottom, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getTransImage(String name, Dimension size, java.awt.Color bgcolor, float alphaValue) {
        BufferedImage origin, target;
        if (bgcolor == null || size == null || size.width == 0 || size.height == 0)
            return null;

        if (name == null || name.isEmpty())
            return null;

        File dir = ImageSettingDialog.getImageDir();

        File file = new File(dir.getAbsolutePath() + File.separator + screen + "/" + name);
        try {
            origin = ImageIO.read(file);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return null;
        }

        if (size.width <= 0 || size.height <= 0)
            return null;
        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        Dimension sourceSize = new Dimension(origin.getWidth() - 1, origin.getHeight() - 1);
        Graphics2D g2 = target.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, alphaValue);

        g2.setColor(bgcolor);
        g2.fillRect(0, 0, size.width, size.height);

        g2.setComposite(ac);

        g2.drawImage(origin, 0, 0, size.width, size.height, 1, 1, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public BufferedImage getTransImage(BufferedImage origin, Dimension size, java.awt.Color bgcolor, float alphaValue) {
        BufferedImage target;
        if (bgcolor == null || size == null || size.width == 0 || size.height == 0)
            return null;

        target = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
        if (origin == null)
            return target;
        Dimension sourceSize = new Dimension(origin.getWidth() - 1, origin.getHeight() - 1);
        Graphics2D g2 = target.createGraphics();
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.DST_IN, alphaValue);

        g2.setColor(bgcolor);
        g2.fillRect(0, 0, size.width, size.height);

        g2.setComposite(ac);

        g2.drawImage(origin, 0, 0, size.width, size.height, 1, 1, sourceSize.width, sourceSize.height, null);

        g2.dispose();
        return target;
    }

    public boolean checkNinepatch(String name, Dimension size) {
        if (name == null || name.isEmpty() || name.indexOf(STRING_NINEPATCH) < 0)
            return false;

        File dir = ImageSettingDialog.getImageDir();

        File file = new File(dir.getAbsolutePath() + File.separator + screen + "/" + name);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            Activator.setErrorMessage("createImage", e.getMessage(), e);
            return false;
        }

        int n, left = -1, right = -1, top = -1, bottom = -1;
        for (n = 0; n < image.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == image.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < image.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == image.getRGB(n, 0))) {
                right = image.getWidth() - n - 1;
                break;
            }
        }

        for (n = 0; n < image.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == image.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < image.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == image.getRGB(0, n))) {
                bottom = image.getHeight() - n - 1;
                break;
            }
        }

        if (left < 0 || top < 0 || left + right > size.width || top + bottom > size.height)
            return false;
        else
            return true;
    }

    public boolean checkNinepatch(String name, BufferedImage buffImage, Dimension size) {
        int n, left = -1, right = -1, top = -1, bottom = -1;
        if (buffImage == null || name == null || name.isEmpty() || name.indexOf(STRING_NINEPATCH) < 0)
            return false;

        for (n = 0; n < buffImage.getWidth(); n++) {
            if (java.awt.Color.black.getRGB() == buffImage.getRGB(n, 0)) {
                left = n;
                break;
            }
        }
        for (; n < buffImage.getWidth(); n++) {
            if (!(java.awt.Color.black.getRGB() == buffImage.getRGB(n, 0))) {
                right = buffImage.getWidth() - n - 1;
                break;
            }
        }

        for (n = 0; n < buffImage.getHeight(); n++) {
            if (java.awt.Color.black.getRGB() == buffImage.getRGB(0, n)) {
                top = n;
                break;
            }
        }
        for (; n < buffImage.getHeight(); n++) {
            if (!(java.awt.Color.black.getRGB() == buffImage.getRGB(0, n))) {
                bottom = buffImage.getHeight() - n - 1;
                break;
            }
        }

        if (left < 0 || top < 0 || left + right > size.width || top + bottom > size.height)
            return false;
        else
            return true;
    }

    public BufferedImage makeImage(Image imageparam, Dimension size, int left, int right, int top, int bottom) {
        BufferedImage origin, awtImage;
        Color bgcolor = getBackgroundColor();
        if (imageparam == null || bgcolor == null || size == null || size.width == 0 || size.height == 0)
            return null;

        origin = ImageUtil.convertToAWT(imageparam.getImageData(), bgcolor);
        awtImage = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_RGB);

        Graphics2D g2 = awtImage.createGraphics();
        // 위쪽 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, 0, left, top, 0, 0, left, top, null);
        g2.drawImage(origin, left, 0, size.width - right, top, left, 0, origin.getWidth() - right, top, null);
        g2.drawImage(origin, size.width - right, 0, size.width, top, origin.getWidth() - right, 0, origin.getWidth(), top, null);

        // 중간 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, top, left, size.height - bottom, 0, top, left, origin.getHeight() - bottom, null);
        g2.drawImage(origin, left, top, size.width - right, size.height - bottom, left, top, origin.getWidth() - right, origin.getHeight() - bottom, null);
        g2.drawImage(origin, size.width - right, top, size.width, size.height - bottom, origin.getWidth() - right, top, origin.getWidth(), origin.getHeight() - bottom, null);

        // 아래 세개 ///////////////////////////////////////////////////
        g2.drawImage(origin, 0, size.height - bottom, left, size.height, 0, origin.getHeight() - bottom, left, origin.getHeight(), null);
        g2.drawImage(origin, left, size.height - bottom, size.width - right, size.height, left, origin.getHeight() - bottom, origin.getWidth() - right, origin.getHeight(), null);
        g2.drawImage(origin, size.width - right, size.height - bottom, size.width, size.height, origin.getWidth() - right, origin.getHeight() - bottom, origin.getWidth(), origin.getHeight(), null);

        g2.dispose();

        return awtImage;
    }

    protected void drawDiagonalLine(Graphics graphics, Rectangle rect, Point point) {
        for (int i = 0, j = 0; i < rect.width + rect.height; i += diagLineWidth, j += diagLineWidth) {
            graphics.drawLine(point.x, point.y + j, point.x + i, point.y);
        }
    }

    protected void drawListLine(Graphics graphics, Rectangle rect, Point point, int height) {
        Color oldColor = graphics.getForegroundColor();
        Color lineColor = new Color(null, 60, 60, 60);
        graphics.setForegroundColor(lineColor);
        for (int i = 1; height * i < rect.height; i++) {
            graphics.drawLine(point.x, point.y + height * i, point.x + rect.width, point.y + height * i);
        }
        graphics.setForegroundColor(oldColor);
        lineColor.dispose();

    }

    /**
     * @return
     */
    protected boolean isLismore() {
        org.eclipse.swt.graphics.Point maxScreen = Activator.getMaxScreen("");
        String sMaxScreen = maxScreen.x + "X" + maxScreen.y;
        if(tag != null && tag.screen != null && !sMaxScreen.equals(tag.screen.toUpperCase(Locale.getDefault())))
            return false;
        else
            return true;
    }

    protected void drawDivider(Graphics graphics, Rectangle rect, Point paramPoint, int markWidth) {
        Point point = paramPoint.getCopy();
        point.y++;
        Color lineColor;
        if(!isLismore())
            lineColor = new Color(null, 182, 182, 182);
        else
            lineColor = new Color(null, 19, 27, 31);
        
        Color oldColor = graphics.getForegroundColor();
        graphics.setForegroundColor(lineColor);
        graphics.drawLine(point.x + rect.width - markWidth, point.y, point.x + rect.width - markWidth, point.y + rect.height);
        lineColor.dispose();
        if(!isLismore()) {
            lineColor = new Color(null, 223, 223, 223);
            lineColor.dispose();
            graphics.setForegroundColor(oldColor);
            return;
        } else
            lineColor = new Color(null, 66, 94, 109);
        graphics.setForegroundColor(lineColor);
        graphics.drawLine(point.x + rect.width - markWidth + 1, point.y, point.x + rect.width - markWidth + 1, point.y + rect.height);
        lineColor.dispose();
        graphics.setForegroundColor(oldColor);
    }

    /**
     * @param imageInfo
     * @param groupStyle
     * @param isInput
     */
    protected void setGroupStyleImage(Image_info imageInfo, int groupStyle, boolean isInput) {
        String image[] = {"00_dialogue_field_bg.9.png", 
            "00_dialogue_field_bg.9.png", 
            "00_dialogue_field_bg_top.9.png", 
            "00_dialogue_field_bg_center.9.png", 
            "00_dialogue_field_bg_bottom.9.png"};
        String imageInput[] = {"00_dialogue_field_input_field_bg.9.png", 
            "00_dialogue_field_input_field_bg.9.png", 
            "00_dialogue_field_input_field_top_bg.9.png", 
            "00_dialogue_field_input_field_center_bg.9.png", 
            "00_dialogue_field_input_field_bottom_bg.9.png"};

        if(isInput)
            imageInfo.setName(0, imageInput[groupStyle]);
        else
            imageInfo.setName(0, image[groupStyle]);
    }

    /**
     * @return
     */
    public double getZoom() {
        EditPartViewer viewer = getViewer();
        
        if(viewer == null)
            return 1.0;
        else
            return ((ScalableRootEditPart) viewer.getRootEditPart())
                        .getZoomManager().getZoom();
    }

    /**
     * @return
     */
    private EditPartViewer getViewer() {       
        EditPartViewer viewer = null;
        if(this instanceof FormFrameFigure)
            viewer = ((FormFrameFigure)this).part.getViewer();
        else if(this instanceof PanelFrameFigure)
            viewer = ((PanelFrameFigure)this).part.getViewer();
        else if(this instanceof PopupFrameFigure)
            viewer = ((PopupFrameFigure)this).part.getViewer();
        else {
            IFigure parentFigure = this.getParent();
            if(parentFigure instanceof FormFrameFigure)
                viewer = ((FormFrameFigure)parentFigure).part.getViewer();
            else if(parentFigure instanceof PanelFrameFigure)
                viewer = ((PanelFrameFigure)parentFigure).part.getViewer();
            else if(parentFigure instanceof PopupFrameFigure)
                viewer = ((PopupFrameFigure)parentFigure).part.getViewer();
        }
        
        return viewer;
    }

}
