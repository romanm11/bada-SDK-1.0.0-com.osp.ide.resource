package com.osp.ide.resource.commands;

import org.eclipse.gef.commands.Command;

import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.model.FrameNode;

public class ChangeGuideCommand extends Command {
    private FrameNode element;  
    private OspElementGuide oldGuide, newGuide;  
    private int oldAlign, newAlign;  
    private boolean horizontal;  
  
    public ChangeGuideCommand(FrameNode element, boolean horizontalGuide) {  
        super();  
        this.element = element;  
        horizontal = horizontalGuide;  
    }  
  
    protected void changeGuide(OspElementGuide beforeGuide, OspElementGuide afterGuide,  
            int newAlignment) {  
        if (beforeGuide != null && beforeGuide != afterGuide) {  
            beforeGuide.detachElement(element);  
        }  
        // You need to re-attach the part even if the oldGuide and the newGuide  
        // are the same  
        // because the alignment could have changed  
        if (afterGuide != null) {  
            afterGuide.attachElement(element, newAlignment);  
        }  
    }  
  
    public void execute() {  
        // Cache the old values  
        oldGuide = horizontal ? element.getHorizontalGuide() : element  
                .getVerticalGuide();  
        if (oldGuide != null)  
            oldAlign = oldGuide.getAlignment(element);  
  
        redo();  
    }  
  
    public void redo() {  
        changeGuide(oldGuide, newGuide, newAlign);  
    }  
  
    public void setNewGuide(OspElementGuide guide, int alignment) {  
        newGuide = guide;  
        newAlign = alignment;  
    }  
  
    public void undo() {  
        changeGuide(newGuide, oldGuide, oldAlign);  
    }  
}
