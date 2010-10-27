/**
 * 
 */
package com.osp.ide.resource.common;

import org.eclipse.draw2d.ScalableLayeredPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.gef.editparts.ScalableRootEditPart;

/**
 * @author SRD1_CHJ
 *
 */
public class OspScalableRootEditPart extends ScalableRootEditPart {

    private OspZoomManager zoomManager;

    /**
     * Constructor for ScalableFreeformRootEditPart
     */
    public OspScalableRootEditPart() {
        zoomManager =
            new OspZoomManager((ScalableLayeredPane)getScaledLayers(), ((Viewport)getFigure()));
    }

    /* (non-Javadoc)
     * @see org.eclipse.gef.editparts.ScalableRootEditPart#getZoomManager()
     */
    @Override
    public OspZoomManager getZoomManager() {
        // TODO Auto-generated method stub
        return zoomManager;
    }

}
