package com.osp.ide.resource.resinfo;

public class PANEL_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String title = "";
	public String bgColor = DEFAULT_COLOR;
	public String fileName = "";
	public int bgColorOpacity = 0;
	
	

	// public String customClass = "";

	public PANEL_INFO clone() {
		PANEL_INFO info = new PANEL_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.fileName = fileName;
		info.bgColorOpacity = bgColorOpacity;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
		
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		PANEL_INFO info = (PANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.fileName = fileName;
		info.bgColorOpacity = bgColorOpacity;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		PANEL_INFO info = (PANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.fileName = fileName;
		info.bgColorOpacity = bgColorOpacity;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
	}
}
