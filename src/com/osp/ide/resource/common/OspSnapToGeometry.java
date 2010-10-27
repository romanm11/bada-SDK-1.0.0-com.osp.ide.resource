/**
 * 
 */
package com.osp.ide.resource.common;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.GroupRequest;

/**
 * @author SRD1_CHJ
 *
 */
public class OspSnapToGeometry extends SnapToGeometry {

    private boolean cachedCloneBool;

    /**
     * @param container
     */
    public OspSnapToGeometry(GraphicalEditPart container) {
        super(container);
        
        setThreshold(10.0001);
    }

    /**
     * @see SnapToHelper#snapRectangle(Request, int, PrecisionRectangle, PrecisionRectangle)
     */
    @SuppressWarnings("unchecked")
    public int snapRectangle(Request request, int snapOrientation,
            PrecisionRectangle baseRect, PrecisionRectangle result) {
        
        baseRect = baseRect.getPreciseCopy();
        makeRelative(container.getContentPane(), baseRect);
        PrecisionRectangle correction = new PrecisionRectangle();
        makeRelative(container.getContentPane(), correction);

        //Recalculate snapping locations if needed
        boolean isClone = request.getType().equals(RequestConstants.REQ_CLONE);
        if (rows == null || cols == null || isClone != cachedCloneBool) {
            cachedCloneBool = isClone;
            List exclusionSet = Collections.EMPTY_LIST;
            if (!isClone && request instanceof GroupRequest)
                exclusionSet = ((GroupRequest)request).getEditParts();
            populateRowsAndCols(generateSnapPartsList(exclusionSet));
        }
        
        if ((snapOrientation & HORIZONTAL) != 0) {
            double xcorrect = getThreshold();
            xcorrect = getCorrectionFor(cols, request.getExtendedData(), true, 
                    baseRect.preciseX, baseRect.preciseRight());
            if (xcorrect != getThreshold()) {
                snapOrientation &= ~HORIZONTAL;
                correction.preciseX += xcorrect;
            }
        }
        
        if ((snapOrientation & VERTICAL) != 0) {
            double ycorrect = getThreshold();
            ycorrect = getCorrectionFor(rows, request.getExtendedData(), false, 
                    baseRect.preciseY - 1, baseRect.preciseBottom() + 1);
            if (ycorrect != getThreshold()) {
                snapOrientation &= ~VERTICAL;
                correction.preciseY += ycorrect;
            }
        }
        
        if ((snapOrientation & EAST) != 0) {
            double rightCorrection = getCorrectionFor(cols, request.getExtendedData(), 
                    true, baseRect.preciseRight() - 1, 1);
            if (rightCorrection != getThreshold()) {
                snapOrientation &= ~EAST;
                correction.preciseWidth += rightCorrection;
            }
        }
        
        if ((snapOrientation & WEST) != 0) {
            double leftCorrection = getCorrectionFor(cols, request.getExtendedData(), 
                    true, baseRect.preciseX, -1);
            if (leftCorrection != getThreshold()) {
                snapOrientation &= ~WEST;
                correction.preciseWidth -= leftCorrection;
                correction.preciseX += leftCorrection;
            }
        }
        
        if ((snapOrientation & SOUTH) != 0) {
            double bottom = getCorrectionFor(rows, request.getExtendedData(), false,
                    baseRect.preciseBottom() - 1, 1);
            if (bottom != getThreshold()) {
                snapOrientation &= ~SOUTH;
                correction.preciseHeight += bottom + 1;
            }
        }
        
        if ((snapOrientation & NORTH) != 0) {
            double topCorrection = getCorrectionFor(rows, request.getExtendedData(), 
                    false, baseRect.preciseY, -1);
            if (topCorrection != getThreshold()) {
                snapOrientation &= ~NORTH;
                correction.preciseHeight -= topCorrection;
                correction.preciseY += topCorrection + 1;
            }
        }
        
        correction.updateInts();
        makeAbsolute(container.getContentPane(), correction);
        result.preciseX += correction.preciseX;
        result.preciseY += correction.preciseY;
        result.preciseWidth += correction.preciseWidth;
        result.preciseHeight += correction.preciseHeight;
        result.updateInts();
        
        return snapOrientation;
    }
}
