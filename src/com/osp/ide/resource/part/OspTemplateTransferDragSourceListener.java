/**
 * 
 */
package com.osp.ide.resource.part;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.dnd.TemplateTransferDropTargetListener;
import org.eclipse.gef.requests.CreationFactory;

/**
 * @author SRD1_CHJ
 *
 */
public class OspTemplateTransferDragSourceListener extends TemplateTransferDropTargetListener {

    /**
     * @param viewer
     */
    public OspTemplateTransferDragSourceListener(EditPartViewer viewer) {
        super(viewer);
    }

    @Override
    protected CreationFactory getFactory(Object template) {
        return new OspNodeCreationFactory((Class<?>)template);
    }
}
