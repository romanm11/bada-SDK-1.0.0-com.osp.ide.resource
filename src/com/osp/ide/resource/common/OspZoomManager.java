/**
 * 
 */
package com.osp.ide.resource.common;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.editparts.ZoomManager;

/**
 * @author SRD1_CHJ
 *
 */
public class OspZoomManager extends ZoomManager {

    IFigure frame;
    /**
     * @param pane
     * @param viewport
     */
    public OspZoomManager(ScalableFigure pane, Viewport viewport) {
        super(pane, viewport);
    }
    
    public void setFrame(IFigure frame) {
        this.frame = frame;
    }

    /* (non-Javadoc)
     * @see org.eclipse.gef.editparts.ZoomManager#getFitHeightZoomLevel()
     */
    @Override
    protected double getFitHeightZoomLevel() {
        return getFitXZoomLevel(1);
    }

    /* (non-Javadoc)
     * @see org.eclipse.gef.editparts.ZoomManager#getFitPageZoomLevel()
     */
    @Override
    protected double getFitPageZoomLevel() {
        return getFitXZoomLevel(2);
    }

    /* (non-Javadoc)
     * @see org.eclipse.gef.editparts.ZoomManager#getFitWidthZoomLevel()
     */
    @Override
    protected double getFitWidthZoomLevel() {
        return getFitXZoomLevel(0);
    }

    private double getFitXZoomLevel(int which) {
        if(frame == null)
            return 1.0;
        
        Dimension available = getViewport().getClientArea().getSize();
        available.width -= 4;
        available.height -= 4;
        
        Dimension desired = frame.getSize();
        
        double x = available.width;
        double y = desired.width;
        double scaleX = Math.min(x / y, getMaxZoom());
        x = available.height;
        y = desired.height;
       double scaleY = Math.min(x / y, getMaxZoom());
       
        if (which == 0) {
            if(desired.height * scaleX > available.height)
                scaleX -= 0.04;
            return scaleX;
        }
        if (which == 1) {
            if(desired.width * scaleY > available.width)
                scaleY -= 0.04;
            return scaleY;
        }
        return Math.min(scaleX, scaleY);
    }

}
