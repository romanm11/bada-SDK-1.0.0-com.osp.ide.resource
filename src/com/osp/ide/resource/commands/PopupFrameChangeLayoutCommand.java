/**
 * 
 */
package com.osp.ide.resource.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Point;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.figure.PopupFrameFigure;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

/**
 * @author SRD1_CHJ
 *
 */
public class PopupFrameChangeLayoutCommand extends OspAbstractLayoutCommand {

    /* (non-Javadoc)
     * @see com.osp.ide.resource.commands.OspAbstractLayoutCommand#setConstraint(org.eclipse.draw2d.geometry.Rectangle)
     */
    @Override
    public void setConstraint(Rectangle rect) {
        this.layout.mode = model.getMode();
        this.layout.x = rect.x;
        this.layout.y = rect.y;
        this.layout.width = rect.width - PopupFrameFigure.BORDER * 2;
        this.layout.height = rect.height - PopupFrameFigure.BORDER * 2;
        
        Point size = Activator.getSScreenToPoint(model.getScreen());
        Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
                FrameConst.cszTag[model.getType()], model.getScreen());
            try {
                size.x -= Integer.parseInt(info.temp1);
                size.y -= Integer.parseInt(info.temp2);
            } catch (NumberFormatException e) {
            }
            
        if (model.getModeIndex() == FrameConst.PORTRAIT) {
            if(this.layout.width > size.x)
                this.layout.width = size.x;
            if(this.layout.height > size.y)
                this.layout.height = size.y;
        } else {
            if(this.layout.width > size.y)
                this.layout.width = size.y;
            if(this.layout.height > size.x)
                this.layout.height = size.x;
        }
    }

    /* (non-Javadoc)
     * @see com.osp.ide.resource.commands.OspAbstractLayoutCommand#setModel(java.lang.Object)
     */
    @Override
    public void setModel(Object model) {
        this.model = (Popup) model;
        this.oldLayout = ((Popup) model).getLayout();
        this.layout = new Layout(((Popup) model).getLayout());
    }

}
