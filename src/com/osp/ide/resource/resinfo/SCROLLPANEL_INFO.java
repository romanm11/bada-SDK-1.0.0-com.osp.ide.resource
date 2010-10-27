package com.osp.ide.resource.resinfo;

import java.util.Enumeration;
import java.util.Hashtable;

public class SCROLLPANEL_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String customClass = "";
	public String panelId = "";
	public String bgColor = DEFAULT_COLOR;
	public Hashtable<String, Layout> panelLayout = new Hashtable<String, Layout>();
	public int bgColorOpacity = 0;
	
	public SCROLLPANEL_INFO clone() {
		SCROLLPANEL_INFO info = new SCROLLPANEL_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText = "";
		info.panelId = panelId;
		info.bgColor = bgColor;
		info.customClass = customClass = "";
		copyPanelLayout(info.panelLayout);
		info.bgColorOpacity = bgColorOpacity;
		return info;
	}

	public void copyPanelLayout(Hashtable<String, Layout> copyLayout) {
		Enumeration<String> layoutKeys = this.panelLayout.keys();
		while (layoutKeys.hasMoreElements()) {
			String key = layoutKeys.nextElement();
			Layout origin = panelLayout
					.get(key);
			copyLayout.remove(key);
			Layout newLayout = origin.copy();
			copyLayout.put(key, newLayout);
		}
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		SCROLLPANEL_INFO info = (SCROLLPANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText = "";
		info.customClass = customClass = "";
		info.panelId = panelId;
		info.bgColor = bgColor;
		copyPanelLayout(info.panelLayout);
		info.bgColorOpacity = bgColorOpacity;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		SCROLLPANEL_INFO info = (SCROLLPANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText = "";
		info.customClass = customClass = "";
		info.panelId = panelId;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
	}
}
