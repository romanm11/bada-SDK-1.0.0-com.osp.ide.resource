package com.osp.ide.resource.model;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.draw2d.IFigure;

import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.figure.PanelFrameFigure;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.string.OspUIString;

public class PanelFrame extends FrameNode {
	public static final int PANEL_DEFAULT_WIDTH = 200;
	public static final int PANEL_DEFAULT_HEIGHT = 62;
	public static final int PANEL_MIN_WIDTH = 81;
	public static final int PANEL_MIN_HEIGHT = 81;

	private int dimension;
	public OspPanelEditor editor;
	private PanelFrameFigure figure;

	public PanelFrame(OspPanelEditor editor, OspPanel panel, String id,
			int modeIndex) {
		super();
		this.editor = editor;
		item = panel.m_infoPanel;
		setDocuments(panel);
		setName(id);
		setMode(modeIndex);
	}

	public void setItem(PANELFRAME_INFO item) {
		this.item = item;
	}

	public PANELFRAME_INFO getItem() {
		return (PANELFRAME_INFO) this.item;
	}

	public OspUIString getString() {
		if(editor != null)
			return editor.m_string;
		else
			return null;
	}

	public void setCopyItem(PANELFRAME_INFO item) {
		PANELFRAME_INFO copyItem = (PANELFRAME_INFO) this.item;
		item.copy(copyItem);

		this.item = copyItem;
	}

	public void setDimension(int dim) {
		int oldDim = dimension;
		dimension = dim;
		getListeners().firePropertyChange(PROPERTY_DIMENSION, oldDim, dim);
	}

	public void setBGColor(String bgColor) {
		String oldColor = ((PANELFRAME_INFO) item).bgColor;
		((PANELFRAME_INFO) item).bgColor = bgColor;

		firePropertyChange(PROPERTY_BGCOLOR, oldColor, bgColor);
		
		Hashtable<String, OspForm> scenes = editor.m_frame.m_Form.get(getScreen());
		Enumeration<String> sceneKeys = scenes.keys();
		while(sceneKeys.hasMoreElements()) {
			String sceneKey = sceneKeys.nextElement();
			OspForm scene = scenes.get(sceneKey);
			Enumeration<String> itemKeys = scene.m_items.keys();
			while(itemKeys.hasMoreElements()) {
				String itemKey = itemKeys.nextElement();
				ITEM_INFO item = scene.m_items.get(itemKey);
				if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id))
					((SCROLLPANEL_INFO)item).bgColor = bgColor;
			}
		}
		
        Hashtable<String, OspPopup> popups = editor.m_frame.m_Popup.get(getScreen());
        Enumeration<String> popupKeys = popups.keys();
        while(popupKeys.hasMoreElements()) {
            String popupKey = popupKeys.nextElement();
            OspPopup popup = popups.get(popupKey);
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = popup.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id))
                    ((SCROLLPANEL_INFO)item).bgColor = bgColor;
            }
        }
		OspPanelEditor.refreshFrameEditor();
	}

	public String getBGColor() {
		return ((PANELFRAME_INFO) item).bgColor;
	}

	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((PANELFRAME_INFO) item).bgColorOpacity;
		((PANELFRAME_INFO) item).bgColorOpacity = bgColorOpacity;
		
		firePropertyChange(PROPERTY_BGOPACITY, oldValue, bgColorOpacity);

		Hashtable<String, OspForm> scenes = editor.m_frame.m_Form.get(getScreen());
		Enumeration<String> sceneKeys = scenes.keys();
		while(sceneKeys.hasMoreElements()) {
			String sceneKey = sceneKeys.nextElement();
			OspForm scene = scenes.get(sceneKey);
			Enumeration<String> itemKeys = scene.m_items.keys();
			while(itemKeys.hasMoreElements()) {
				String itemKey = itemKeys.nextElement();
				ITEM_INFO item = scene.m_items.get(itemKey);
				if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id))
					((SCROLLPANEL_INFO)item).bgColorOpacity = bgColorOpacity;
			}
		}
        Hashtable<String, OspPopup> popups = editor.m_frame.m_Popup.get(getScreen());
        Enumeration<String> popupKeys = popups.keys();
        while(popupKeys.hasMoreElements()) {
            String popupKey = popupKeys.nextElement();
            OspPopup popup = popups.get(popupKey);
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = popup.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id))
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = bgColorOpacity;
            }
        }
		OspPanelEditor.refreshFrameEditor();
	}

	/* (non-Javadoc)
     * @see com.osp.ide.resource.model.FrameNode#setLayout(com.osp.ide.resource.resinfo.Layout)
     */
    @Override
    public void setLayout(Layout newLayout) {
        Layout oldLayout = getLayout(newLayout.mode);
        if(oldLayout.height != newLayout.height) {
            newLayout.height = getMinHeight(newLayout);
        }
        super.setLayout(newLayout);
        updateLayout(newLayout);
    }

    /**
     * @param newLayout 
     * @return
     */
    private int getMinHeight(Layout newLayout) {
        int minHeight = newLayout.height;
        Hashtable<String, OspForm> scenes = editor.m_frame.m_Form.get(getScreen());
        Enumeration<String> sceneKeys = scenes.keys();
        while(sceneKeys.hasMoreElements()) {
            String sceneKey = sceneKeys.nextElement();
            OspForm scene = scenes.get(sceneKey);
            Enumeration<String> itemKeys = scene.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = scene.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id)) {
                    Layout layout = ((SCROLLPANEL_INFO)item).GetLayout(newLayout.mode);
                    if(minHeight < layout.height) {
                        minHeight = layout.height;
                    }
                }
            }
        }
        Hashtable<String, OspPopup> popups = editor.m_frame.m_Popup.get(getScreen());
        Enumeration<String> popupKeys = popups.keys();
        while(popupKeys.hasMoreElements()) {
            String popupKey = popupKeys.nextElement();
            OspPopup popup = popups.get(popupKey);
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = popup.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id)) {
                    Layout layout = ((SCROLLPANEL_INFO)item).GetLayout(newLayout.mode);
                    if(minHeight < layout.height) {
                        minHeight = layout.height;
                    }
                }
            }
        }
        return minHeight;
    }

    /**
     * @param newLayout
     */
    public void updateLayout(Layout newLayout) {
        Hashtable<String, OspForm> scenes = editor.m_frame.m_Form.get(getScreen());
        Enumeration<String> sceneKeys = scenes.keys();
        while(sceneKeys.hasMoreElements()) {
            String sceneKey = sceneKeys.nextElement();
            OspForm scene = scenes.get(sceneKey);
            Enumeration<String> itemKeys = scene.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = scene.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id)) {
                    ((SCROLLPANEL_INFO)item).panelLayout.remove(newLayout.mode);
                    ((SCROLLPANEL_INFO)item).panelLayout.put(newLayout.mode, newLayout);
                    Layout layout = ((SCROLLPANEL_INFO)item).GetLayout(newLayout.mode);
                    if(layout.width != newLayout.width) {
                        layout.width = newLayout.width;
                        ((SCROLLPANEL_INFO)item).SetLayout(layout);
                    }
                }
            }
        }
        Hashtable<String, OspPopup> popups = editor.m_frame.m_Popup.get(getScreen());
        Enumeration<String> popupKeys = popups.keys();
        while(popupKeys.hasMoreElements()) {
            String popupKey = popupKeys.nextElement();
            OspPopup popup = popups.get(popupKey);
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                String itemKey = itemKeys.nextElement();
                ITEM_INFO item = popup.m_items.get(itemKey);
                if(item instanceof SCROLLPANEL_INFO && ((SCROLLPANEL_INFO)item).panelId.equals(this.item.Id)) {
                    ((SCROLLPANEL_INFO)item).panelLayout.remove(newLayout.mode);
                    ((SCROLLPANEL_INFO)item).panelLayout.put(newLayout.mode, newLayout);
                    Layout layout = ((SCROLLPANEL_INFO)item).GetLayout(newLayout.mode);
                    if(layout.width != newLayout.width) {
                        layout.width = newLayout.width;
                        ((SCROLLPANEL_INFO)item).SetLayout(layout);
                    }
                }
            }
        }
        OspPanelEditor.refreshFrameEditor();
    }

    public int getBGColorTransparency() {
		return ((PANELFRAME_INFO) item).bgColorOpacity;
	}
	
    /**
     * @return
     */
    public String getFormColor() {
        return ((PANELFRAME_INFO) item).formColor;
    }

	public void refreshChildren() {
		refresh();
		if (editor != null && editor.getPropertySheetPage() != null)
			editor.getPropertySheetPage().refresh();
	}

	public void setFigure(IFigure figure) {
		this.figure = (PanelFrameFigure) figure;
	}

	public PanelFrameFigure getFigure() {
		return figure;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}

    /**
     * @param mode
     */
    public void reSize(String mode) {
        reSize(OspPopupEditor.getModeIndex(mode));
    }

    /* (non-Javadoc)
     * @see com.osp.ide.resource.model.FrameNode#addChild(com.osp.ide.resource.model.FrameNode)
     */
    @Override
    public boolean addChild(FrameNode child, boolean isInsert) {
        // TODO Auto-generated method stub
        boolean ret = super.addChild(child, isInsert);
        if(child instanceof ColorPicker ||
            child instanceof EditDate ||
            child instanceof EditTime) {
            if(getModeIndex() == PORTRAIT)
                reSize(LANDCAPE);
            else if(getModeIndex() == LANDCAPE)
                reSize(PORTRAIT);
        }
        return ret;
    }

}
