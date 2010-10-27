/**
 * 
 */
package com.osp.ide.resource.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.RetargetAction;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;

/**
 * @author PD_CHJ
 *
 */
public class ControlSnapAction extends RetargetAction {
    public static final String ID = "Control Snap Contribution";
    private IEditorPart editor;

    /**
     * @param actionID
     * @param text
     * @param style
     */
    public ControlSnapAction(String text, int style) {
        super(ID, text, style);
        ImageDescriptor newImage = AbstractUIPlugin.imageDescriptorFromPlugin(
            Activator.PLUGIN_ID, "icons/controlSnap.png");
        setImageDescriptor(newImage);
        setEnabled(true);
    }


    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.RetargetAction#setChecked(boolean)
     */
    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        setControlSnap(checked);
    }

    public void setControlSnap(boolean enable) {
        if(editor != null) {
            if(editor instanceof OspFormEditor)
                ((OspFormEditor)editor).setControlSnap(enable);
            else if(editor instanceof OspPanelEditor)
                ((OspPanelEditor)editor).setControlSnap(enable);
            else if(editor instanceof OspPopupEditor)
                ((OspPopupEditor)editor).setControlSnap(enable);
        }
    }

    public void setEditor(IEditorPart editor) {
        this.editor = editor;

        if(editor instanceof OspFormEditor)
            setChecked(((OspFormEditor)editor).getControlSnap());
        else if(editor instanceof OspPanelEditor)
            setChecked(((OspPanelEditor)editor).getControlSnap());
        else if(editor instanceof OspPopupEditor)
            setChecked(((OspPopupEditor)editor).getControlSnap());
    }


    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.RetargetAction#partActivated(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
    public void partActivated(IWorkbenchPart part) {
        // TODO Auto-generated method stub
        super.partActivated(part);
        if(part.equals(editor))
            setEnabled(true);
    }


    /* (non-Javadoc)
     * @see org.eclipse.ui.actions.RetargetAction#partDeactivated(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
    public void partDeactivated(IWorkbenchPart part) {
        // TODO Auto-generated method stub
        super.partDeactivated(part);
        if(part.equals(editor))
            setEnabled(false);
    }
    
}
