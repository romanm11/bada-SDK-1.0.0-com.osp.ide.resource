package com.osp.ide.resource.model;

import java.util.Hashtable;

import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;

public class ScrollPanel extends FrameNode {

	public static final int SCROLLPANEL_DEFAULT_WIDTH = 200;
	public static final int SCROLLPANEL_DEFAULT_HEIGHT = 62;
	public static final int SCROLLPANEL_MIN_WIDTH = 81;
	public static final int SCROLLPANEL_MIN_HEIGHT = 81;

	public ScrollPanel() {
		super();
		item = new SCROLLPANEL_INFO();
	}

	public ScrollPanel(String name, SCROLLPANEL_INFO item) {
		super(name, item);
	}

	public ScrollPanel(Object scene, int mode) {
		super(scene, mode);
		item = new SCROLLPANEL_INFO();
	}

	public void setItem(SCROLLPANEL_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((SCROLLPANEL_INFO) item).itemText;
		((SCROLLPANEL_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((SCROLLPANEL_INFO) item).itemText;
	}

	public SCROLLPANEL_INFO getItem() {
		return (SCROLLPANEL_INFO) this.item;
	}

	public void setPanelId(String id) {
		String oldValue = ((SCROLLPANEL_INFO) item).panelId;
		
		((SCROLLPANEL_INFO) item).panelId = id;
		firePropertyChange(PROPERTY_PANELID, oldValue, id);
	}
	
	public String getPanelId() {
		return ((SCROLLPANEL_INFO) item).panelId;
	}
	
	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((SCROLLPANEL_INFO) item).bgColorOpacity;
		((SCROLLPANEL_INFO) item).bgColorOpacity = bgColorOpacity;

		firePropertyChange(PROPERTY_BGOPACITY, oldValue, bgColorOpacity);
	}

	public int getBGColorTransparency() {
		return ((SCROLLPANEL_INFO) item).bgColorOpacity;
	}
	
	public void setBGColor(String bgColor) {
		String oldColor = ((SCROLLPANEL_INFO) item).bgColor;
		((SCROLLPANEL_INFO) item).bgColor = bgColor;
		
		firePropertyChange(PROPERTY_BGCOLOR, oldColor, bgColor);
		
		FrameNode frame;
		if ((frame = getParent()) == null || !(frame instanceof Form))
			return;

        Hashtable<String, OspPanel> panels = ((Form)frame).editor.m_frame.m_Panels.get(getScreen());
		OspPanel panel = panels.get(((SCROLLPANEL_INFO) item).panelId);
		if(panel !=null && panel.GetPanelInfo().bgColor != bgColor) {
			panel.GetPanelInfo().bgColor = bgColor;
//			OspFormEditor.refreshPanelEditor();
		}
	}
	
	public String getBGColor() {
		return ((SCROLLPANEL_INFO) item).bgColor;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		ScrollPanel scrollpanel = new ScrollPanel(getParent().getDocuments(), mode);
		scrollpanel.parent = getParent();
		scrollpanel.setCopyItem((SCROLLPANEL_INFO) this.item);

		return scrollpanel;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null) {
			super.setLayout(newLayout);
		}
	}

}

