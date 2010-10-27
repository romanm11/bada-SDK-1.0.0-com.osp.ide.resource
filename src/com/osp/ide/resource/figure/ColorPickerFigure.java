package com.osp.ide.resource.figure;

import java.io.File;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.part.PanelFramePart;
import com.osp.ide.resource.part.PopupFramePart;
import com.osp.ide.resource.resinfo.FrameConst;

public class ColorPickerFigure extends AbstactFigure {

    private Rectangle rect = null;
    private File controlImage;
    private String TitleText = "";

    public ColorPickerFigure(String screen) {
        super(screen);
        ToolbarLayout layout = new ToolbarLayout();
        setLayoutManager(layout);

        textLabel.setForegroundColor(ColorConstants.darkGray);

        image = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/twColorPicker.png").createImage();
    }

    public void createImage() {
        if (rect == null)
            return;

        IFigure frame = this.getParent();
        
        FrameNode frameModel = null;
        if(frame instanceof FormFrameFigure) {
            FormFramePart framePart =((FormFrameFigure)frame).getFramePart();
            frameModel = (FrameNode) framePart.getModel();
        } else if(frame instanceof PopupFrameFigure) {
            PopupFramePart framePart =((PopupFrameFigure)frame).getFramePart();
            frameModel = (FrameNode) framePart.getModel();
        } else if(frame instanceof PanelFrameFigure) {
            PanelFramePart framePart =((PanelFrameFigure)frame).getFramePart();
            frameModel = (FrameNode) framePart.getModel();
        }
        
        if(frameModel == null)
            return;
        
        Dimension size = new Dimension(rect.width, rect.height);
        if (tag != null && tag.size() != 0) {
            Tag_info info = tag.getClone();
            if (frameModel != null && frameModel.getModeIndex() == FrameConst.LANDCAPE)
                info.remove(0);
            else
                info.remove(1);

            createImage(info, size, null);
        } else
            createImage(image, new Dimension(rect.width, rect.height), 5, 5, 5, 5);
    }

    public void setName(String text) {
        if (text == null || !text.equals(textLabel.getText())) {
            textLabel.setText(text);
            createImage();
            repaint();
        }
    }

    public void setLayout(Rectangle rect) {
        this.rect = rect;
        createImage();
        repaint();

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

    public void setControlImage(File image) {
        if (image == null || !image.equals(controlImage)) {
            controlImage = image;
            createImage();
            repaint();
        }
    }

    public void setTitleText(String text) {
        if (text == null || !text.equals(TitleText)) {
            TitleText = text;
            createImage();
            repaint();
        }
    }
}