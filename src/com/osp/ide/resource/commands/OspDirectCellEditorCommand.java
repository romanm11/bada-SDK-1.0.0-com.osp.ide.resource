package com.osp.ide.resource.commands;

import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.string.OspUIString;

/**
 * Command to rename Activities.
 * 
 * @author Daniel Lee
 */
public class OspDirectCellEditorCommand extends Command {

	private FrameNode source;
	private String name, oldName;

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute() {
        FrameNode parent = source.getParent();
        
	    if(name == null)
	        name = "";
	    
		if (source instanceof Button) {
			((Button) source).setText(name);
		} else if (source instanceof Check) {
			((Check) source).setText(name);
		} else if (source instanceof EditField) {
			((EditField) source).setText(name);
		} else if (source instanceof EditArea) {
			((EditArea) source).setText(name);
		} else if (source instanceof Label) {
			((Label) source).setText(name);
		}
		
		if(parent instanceof Form)
			((Form)parent).editor.getPropertySheetPage().refresh();
		else if(parent instanceof PanelFrame)
			((PanelFrame)parent).editor.getPropertySheetPage().refresh();
		else if(parent instanceof Popup)
			((Popup)parent).editor.getPropertySheetPage().refresh();

	}	

	/**
     * 
     */
    protected String insertString() {
        String id = "";
        OspUIString string = null;
        FrameNode parent = source.getParent();
        if(parent instanceof Form)
            string = ((Form)parent).getString();
        else if(parent instanceof PanelFrame)
            string = ((PanelFrame)parent).getString();
        else if(parent instanceof Popup)
            string = ((Popup)parent).getString();
        
        if (string != null && string.m_Strings.size() == 0) {
            string.InsertString(OspUIString.DEFAULT_LANG1);
            if(parent instanceof Form)
                ((Form)parent).refreshChildren();
            else if(parent instanceof PanelFrame)
                ((PanelFrame)parent).refreshChildren();
            else if(parent instanceof Popup)
                ((Popup)parent).refreshChildren();
        }

        if(source instanceof EditArea) {
            if(name.length() > ((EditArea)source).getLimitLength())
                name = name.substring(0, ((EditArea)source).getLimitLength());
        }else if(source instanceof EditField) {
            if(name.length() > ((EditField)source).getLimitLength())
                name = name.substring(0, ((EditField)source).getLimitLength());
        }
    
        if(name == null || name.isEmpty()) {
            id = "";
        } else if (name.indexOf("::") >= 0) {
            if((id = string.getText(name.replace("::", ""))) != null)
                id = name;
            else
                id = name;
        } else {
            id = string.getTextId(name);
            if (id == null) {
                id = string.InsertText(name);
                OspFormEditor.refreshStringEditor();
            }
            id = "::" + id;
        }       
        
        return id;
    }

	/**
	 * Sets the new Activity name
	 * 
	 * @param string
	 *            the new name
	 */
	public void setName(String string) {
		name = string;
	}

	public String getNmae() {
		return name;
	}

	/**
	 * Sets the old Activity name
	 * 
	 * @param string
	 *            the old name
	 */
	public void setOldName(String string) {
		oldName = string;
	}

	/**
	 * Sets the source Activity
	 * 
	 * @param activity
	 *            the source Activity
	 */
	public void setSource(FrameNode model) {
		source = model;
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo() {
		if (source instanceof Button) {
			((Button) source).setText(oldName);
		} else if (source instanceof Check) {
			((Check) source).setText(oldName);
		} else if (source instanceof EditField) {
			((EditField) source).setText(oldName);
		} else if (source instanceof EditArea) {
			((EditArea) source).setText(oldName);
		} else if (source instanceof Label) {
			((Label) source).setText(oldName);
		}
	}
}