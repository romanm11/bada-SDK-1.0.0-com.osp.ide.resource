package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.dialogs.MessageDialog;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpolicies.OspDeletePolicy;
import com.osp.ide.resource.figure.ScrollPanelFigure;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.resinfo.COLORPICKER_INFO;
import com.osp.ide.resource.resinfo.DATEPICKER_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.TIMEPICKER_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class ScrollPanelPart extends OspAbstractEditPart {

	@Override
	protected IFigure createFigure() {
		ScrollPanel model = (ScrollPanel) getModel();
		ScrollPanelFigure figure = new ScrollPanelFigure(model.getParent().getScreen());
		figure.setBackColor(OspResourceManager.FormatRGB(model
				.getBGColor()));
		figure.setOpacity(model.getBGColorTransparency());
		return figure;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OspDeletePolicy());
	}

	protected void refreshVisuals() {
		ScrollPanelFigure figure = (ScrollPanelFigure) getFigure();
		ScrollPanel model = (ScrollPanel) getModel();

		figure.setBackColor(OspResourceManager.FormatRGB(((ScrollPanel) getModel())
				.getBGColor()));
		figure.setOpacity(model.getBGColorTransparency());
		figure.setName(model.getName());
		
		Layout layout = model.getLayout();
        Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
                layout.height);
        figure.setLayout(rect);
	}

	public List<FrameNode> getModelChildren() {
		return new ArrayList<FrameNode>();
	}

	public void propertyChange(PropertyChangeEvent evt) {
        ScrollPanelFigure figure = (ScrollPanelFigure) getFigure();
		super.propertyChange(evt);
		if (evt.getPropertyName().equals(FrameNode.PROPERTY_CUSTOMCLASS)) {
			refreshVisuals();
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_BGCOLOR)) {
			figure.setBackColor(
							OspResourceManager.FormatRGB(((ScrollPanel) getModel())
									.getBGColor()));
			refreshVisuals();
		} else if(evt.getPropertyName().equals(FrameNode.PROPERTY_BGOPACITY)) {
			refreshVisuals();
        } else if(evt.getPropertyName().equals(FrameNode.PROPERTY_LAYOUT)) {
            setFigureLayout(evt);
        } else if(evt.getPropertyName().equals(FrameNode.PROPERTY_PANELID)) {
            setPanelProperty(evt);
		}
	}

    /**
     * @param evt
     */
    private void setFigureLayout(PropertyChangeEvent evt) {
        ScrollPanel model = (ScrollPanel) getModel();
        ScrollPanelFigure figure = (ScrollPanelFigure) getFigure();
        Object oldValue = evt.getOldValue();
        if(!(oldValue instanceof Rectangle))
            return;
        Rectangle oldLayout = (Rectangle) oldValue;
        Layout layout = model.getLayout();
        FrameNode frame;
        if ((frame = model.getParent()) == null)
            return;

        Hashtable<String, OspPanel> panels;
        if(frame instanceof Form)
            panels = ((Form)frame).editor.m_frame.m_Panels.get(model.getScreen());
        else if(frame instanceof Popup)
            panels = ((Popup)frame).editor.m_Popup.m_Panels.get(model.getScreen());
        else
            return;
        
        OspPanel panel = panels.get(model.getPanelId());
        if(panel != null) {
            Layout panelLayout = panel.m_infoPanel.layout.get(model.getMode()).copy();
            panelLayout.width = layout.width;
            if(panelLayout.height < layout.height)
                panelLayout.height = layout.height;
            if(resetChild(panelLayout)) {
                panel.m_infoPanel.layout.get(layout.mode).width = layout.width;
                if(panel.m_infoPanel.layout.get(layout.mode).height < layout.height)
                    panel.m_infoPanel.layout.get(layout.mode).height = layout.height;
                panel.UpdateRsc(panel.m_infoPanel.Id, panel.m_infoPanel);
                OspFormEditor.refreshPanelEditor();
            } else {
                layout = model.getLayout();
                layout.x = oldLayout.x;
                layout.y = oldLayout.y;
                layout.width = oldLayout.width;
                layout.height = oldLayout.height;
                model.getItem().SetLayout(layout);
            }
        }
        
        Rectangle rect = new Rectangle(layout.x, layout.y, layout.width,
                layout.height);
        figure.setLayout(rect);
    }

    /**
     * @param evt 
     * 
     */
    private void setPanelProperty(PropertyChangeEvent evt) {
        ScrollPanel model = (ScrollPanel) getModel();
        Object oldValue = evt.getOldValue();
        if(!(oldValue instanceof String))
            return;
        
        String panelId = (String) oldValue;
        FrameNode parent;
        if ((parent = model.getFrameModel()) == null)
            return;

        String frameColor;
        Hashtable<String, OspPanel> panels;
        if(parent instanceof Form) {
            panels = ((Form)parent).editor.m_frame.m_Panels.get(model.getScreen());
            frameColor = ((Form)parent).getBgColor();
        } else if(parent instanceof Popup) {
            panels = ((Popup)parent).editor.m_Popup.m_Panels.get(model.getScreen());
            frameColor = FrameConst.DEFAULT_COLOR;
        } else
            return;
        
        if(panels !=null) {
            OspPanel panel = panels.get(model.getPanelId());
            if(panel != null) {

                Layout layout = panel.m_infoPanel.layout.get(model.getMode()).copy();
                layout.width = model.getItem().layout.get(model.getMode()).width;
                if(layout.height < model.getItem().layout.get(model.getMode()).height)
                    layout.height = model.getItem().layout.get(model.getMode()).height;
                if(resetChild(layout)) {
                    panel.m_infoPanel.layout.get(model.getMode()).width = layout.width;
                    if(panel.m_infoPanel.layout.get(model.getMode()).height < layout.height)
                        panel.m_infoPanel.layout.get(model.getMode()).height = layout.height;
                } else {
                    model.getItem().panelId = panelId;
                    return;
                }

                panel.m_infoPanel.addParentId(model.getName());
                String settingBgColor = panel.m_infoPanel.bgColor;
                model.setBGColorTransparency(panel.m_infoPanel.bgColorOpacity);
                model.setBGColor(settingBgColor);
                panel.m_infoPanel.formColor = frameColor;
            } else {
                model.setBGColor(FrameConst.DEFAULT_COLOR);
                model.setBGColorTransparency(0);
            }
        }
        
        OspPanel oldPanel = panels.get(panelId);
        if(oldPanel != null && !oldPanel.m_infoPanel.Id.equals(model.getPanelId())) {
            oldPanel.m_infoPanel.removeParentId(model.getName());
            oldPanel.m_infoPanel.formColor = FrameConst.DEFAULT_COLOR;
        }
        OspFormEditor.refreshPanelEditor();
    }

    /**
     * 
     */
    protected void resetPickerLayout(int width) {
        ScrollPanel model = (ScrollPanel) getModel();
        
        FrameNode parent;
        if ((parent = model.getFrameModel()) == null)
            return;
        
        Hashtable<String, OspPanel> panels;
        if(parent instanceof Form) {
            panels = ((Form)parent).editor.m_frame.m_Panels.get(model.getScreen());
        } else if(parent instanceof Popup) {
            panels = ((Popup)parent).editor.m_Popup.m_Panels.get(model.getScreen());
        } else
            return;

        OspPanel panel;
        if(panels !=null) {
            panel = panels.get(model.getPanelId());
            if(panel == null) {
                return;
            }
        } else
            return;

        Enumeration<String> keys = panel.m_items.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            ITEM_INFO child = panel.m_items.get(key);
            if(child instanceof COLORPICKER_INFO ||
                child instanceof DATEPICKER_INFO ||
                child instanceof TIMEPICKER_INFO) {
                Layout layout = child.GetLayout(model.getModeIndex());
                layout.width = width;
                child.SetLayout(layout);
            }
        }
    }

    /**
     * 
     */
    public boolean resetChild(Layout layout) {
        if(isOpenMessage(layout)) {
            if(MessageDialog.openQuestion(null, "Resize ScrollPanel", 
                "With the specified dimension, some child controls " + 
                "falls outside of ScrollPanel¡¯s bounds. " + 
                "Remove these controls and then change the size of ScrollPanel?")) {
                removeGetOutChild(layout);
                resetPickerLayout(layout.width);
                return true;
            } else
                return false;
        }
        resetPickerLayout(layout.width);
        return true;
    }
    
    /**
     * @param layout
     * @return
     */
    private boolean isOpenMessage(Layout layout) {
        ScrollPanel model = (ScrollPanel) getModel();
        
        FrameNode parent;
        if ((parent = model.getFrameModel()) == null)
            return false;
        
        Hashtable<String, OspPanel> panels;
        if(parent instanceof Form) {
            panels = ((Form)parent).editor.m_frame.m_Panels.get(model.getScreen());
        } else if(parent instanceof Popup) {
            panels = ((Popup)parent).editor.m_Popup.m_Panels.get(model.getScreen());
        } else
            return false;

        OspPanel panel;
        if(panels !=null) {
            panel = panels.get(model.getPanelId());
            if(panel == null) {
                return false;
            }
        } else
            return false;

        Enumeration<String> keys = panel.m_items.keys();
        while(keys.hasMoreElements()) {
            ITEM_INFO child = panel.m_items.get(keys.nextElement());
            
            Layout childLayout = child.GetLayout(model.getModeIndex());
            
            if(child instanceof COLORPICKER_INFO ||
                child instanceof DATEPICKER_INFO ||
                child instanceof TIMEPICKER_INFO) {
                Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
                    FrameConst.cszTag[child.type], model.getScreen());
                
                childLayout.width = info.minSize.x;
            }
            
            if(childLayout.x + childLayout.width > layout.width ||
                childLayout.y + childLayout.height > layout.height) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     */
    private void removeGetOutChild(Layout layout) {
        ScrollPanel model = (ScrollPanel) getModel();
        
        FrameNode parent;
        if ((parent = model.getFrameModel()) == null)
            return;
        
        Hashtable<String, OspPanel> panels;
        if(parent instanceof Form) {
            panels = ((Form)parent).editor.m_frame.m_Panels.get(model.getScreen());
        } else if(parent instanceof Popup) {
            panels = ((Popup)parent).editor.m_Popup.m_Panels.get(model.getScreen());
        } else
            return;

        OspPanel panel;
        if(panels !=null) {
            panel = panels.get(model.getPanelId());
            if(panel == null) {
                return;
            }
        } else
            return;

        Enumeration<String> keys = panel.m_items.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            ITEM_INFO child = panel.m_items.get(key);
            
            Layout childLayout = child.GetLayout(model.getModeIndex());
            if(child instanceof COLORPICKER_INFO ||
                child instanceof DATEPICKER_INFO ||
                child instanceof TIMEPICKER_INFO) {
                Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
                    FrameConst.cszTag[child.type], model.getScreen());
                
                childLayout.width = info.minSize.x;
            }
            
            if(childLayout.x + childLayout.width > layout.width ||
                childLayout.y + childLayout.height > layout.height) {
                panel.m_items.remove(key);
            }
        }
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if(view != null)
            view.reOpenPanelEditor(model.getScreen(), model.getPanelId(), false);
    }
}
