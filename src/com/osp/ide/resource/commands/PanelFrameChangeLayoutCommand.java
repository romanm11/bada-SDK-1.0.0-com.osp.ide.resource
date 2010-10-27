/**
 * 
 */
package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Point;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.figure.PanelFrameFigure;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;

/**
 * @author SRD1_CHJ
 *
 */
public class PanelFrameChangeLayoutCommand extends OspAbstractLayoutCommand {

    /* (non-Javadoc)
     * @see com.osp.ide.resource.commands.OspAbstractLayoutCommand#setConstraint(org.eclipse.draw2d.geometry.Rectangle)
     */
    @Override
    public void setConstraint(Rectangle rect) {
        this.layout.mode = model.getMode();
        this.layout.x = rect.x;
        this.layout.y = rect.y;
        this.layout.height = rect.height - PanelFrameFigure.BORDER*2;
        
        Point size = Activator.getSScreenToPoint(model.getScreen());
        if (model.getModeIndex() == FrameConst.PORTRAIT) {
            if(this.layout.height > size.y * 3)
                this.layout.height = size.y * 3;
        } else {
            if(this.layout.height > size.x * 3)
                this.layout.height = size.x * 3;
        }
    }

    /* (non-Javadoc)
     * @see com.osp.ide.resource.commands.OspAbstractLayoutCommand#setModel(java.lang.Object)
     */
    @Override
    public void setModel(Object model) {
        this.model = (PanelFrame) model;
        this.oldLayout = ((PanelFrame) model).getLayout();
        this.layout = new Layout(((PanelFrame) model).getLayout());
    }

}
