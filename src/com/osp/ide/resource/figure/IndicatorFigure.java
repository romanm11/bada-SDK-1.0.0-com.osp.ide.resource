package com.osp.ide.resource.figure;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutListener;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.resinfo.FrameConst;

public class IndicatorFigure extends AbstactFigure {

	private Rectangle rect = null;
	org.eclipse.swt.graphics.Rectangle imageRect;

	public IndicatorFigure(String screen) {
		super(screen);
		XYLayout layout = new XYLayout();
		setLayoutManager(layout);

		image = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/portrait_indicator.png").createImage();
		imageRect = image.getBounds();
	}

    public void createImage() {
        if (rect == null)
            return;

        IFigure frame = this.getParent();
        
        FormFramePart framePart =((FormFrameFigure)frame).getFramePart();
        FrameNode frameModel = (FrameNode) framePart.getModel();
        
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

	@Override
	public void deleteImage() {
		super.deleteImage();
		imageRect = null;
	}

	public void setText(String text) {
		textLabel.setText(text);
	}

	public void setLayout(Rectangle rect) {
        if (this.rect == null || this.rect.height != rect.height || this.rect.width != rect.width) {
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

        if (bgImage != null && !bgImage.isDisposed())
            graphics.drawImage(bgImage, point.x, point.y);
//		FormFrameFigure frame = (FormFrameFigure) this.getParent();
//		Point point = frame.getLocation();

//		if (controlImage != null) {
//			Image image = new Image(null, controlImage.getAbsolutePath());
//			org.eclipse.swt.graphics.Rectangle imageRect = image.getBounds();
//			graphics.drawImage(image, 0, 0, imageRect.width, imageRect.height,
//					rect.x + point.x + FormFrameFigure.BORDER, rect.y + point.y
//							+ FormFrameFigure.BORDER, rect.width, rect.height);
//			image.dispose();
//			image = null;
//		} else {
//			FormFramePart framePart = frame.getFramePart();
//			FormFrame frameModel = (FormFrame) framePart.getModel();
//			if(frameModel != null && frameModel.getModeIndex() == FrameConst.LANDCAPE) {
//				graphics.drawImage(landImage, 0, 0, landImageRect.width, landImageRect.height,
//						rect.x + point.x + FormFrameFigure.BORDER, rect.y + point.y
//								+ FormFrameFigure.BORDER, rect.width, rect.height);
//			} else {
//				graphics.drawImage(image, 0, 0, imageRect.width, imageRect.height,
//						rect.x + point.x + FormFrameFigure.BORDER, rect.y + point.y
//								+ FormFrameFigure.BORDER, rect.width, rect.height);
//			}
//		}
		super.paintClientArea(graphics);
	}
}