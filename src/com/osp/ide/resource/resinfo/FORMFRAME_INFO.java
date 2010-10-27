package com.osp.ide.resource.resinfo;


public class FORMFRAME_INFO extends ITEM_INFO implements FrameConst {
	public String fileName = "";
	public String headPath = "";

	public String title = "";
	public String titleIcon = "";
	public String hAlign = "";
	public int headerHeight;
	public int bgColorOpacity = 0;
	public String softkeyStyle = "";
	public String bg = DEFAULT_COLOR;
	public String softkey0 = "";
	public String softkey1 = "";
	public String softkey0NIcon = "";
	public String softkey1NIcon = "";
	public String softkey0PIcon = "";
	public String softkey1PIcon = "";
	public String orientation = cszOrientation[ORIENTATION_PORTRAIT];
	public String oldId = "";
	

	@Override
	public FORMFRAME_INFO clone() {
		FORMFRAME_INFO info = new FORMFRAME_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.fileName = fileName;
		info.headPath = headPath;
		info.title = title;
		info.titleIcon = titleIcon;
		info.hAlign = hAlign;
		info.headerHeight = headerHeight;
		info.softkeyStyle = softkeyStyle;
		info.bg = bg;
		info.orientation = orientation;
		info.softkey0 = softkey0;
		info.softkey1 = softkey1;
		info.softkey0NIcon = softkey0NIcon;
		info.softkey1NIcon = softkey1NIcon;
		info.softkey0PIcon = softkey0PIcon;
		info.softkey1PIcon = softkey1PIcon;
		info.bgColorOpacity = bgColorOpacity;
		
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		FORMFRAME_INFO info = (FORMFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.fileName = fileName;
		info.headPath = headPath;
		info.title = title;
		info.titleIcon = titleIcon;
		info.hAlign = hAlign;
		info.headerHeight = headerHeight;
		info.softkeyStyle = softkeyStyle;
		info.bg = bg;
		info.orientation = orientation;
		info.softkey0 = softkey0;
		info.softkey1 = softkey1;
		info.softkey0NIcon = softkey0NIcon;
		info.softkey1NIcon = softkey1NIcon;
		info.softkey0PIcon = softkey0PIcon;
		info.softkey1PIcon = softkey1PIcon;
		info.bgColorOpacity = bgColorOpacity;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		FORMFRAME_INFO info = (FORMFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.title = title;
		info.titleIcon = titleIcon;
		info.hAlign = hAlign;
		info.softkeyStyle = softkeyStyle;
		info.orientation = orientation;
		info.softkey0 = softkey0;
		info.softkey1 = softkey1;
		info.softkey0NIcon = softkey0NIcon;
		info.softkey1NIcon = softkey1NIcon;
		info.softkey0PIcon = softkey0PIcon;
		info.softkey1PIcon = softkey1PIcon;
		info.bgColorOpacity = bgColorOpacity;
	}
}
