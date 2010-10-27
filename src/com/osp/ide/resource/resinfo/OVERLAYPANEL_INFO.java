package com.osp.ide.resource.resinfo;

public class OVERLAYPANEL_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String title = "";
	public String bgColor = DEFAULT_COLOR;
	public int bgColorOpacity = 100;

	// public String customClass = "";

	public OVERLAYPANEL_INFO clone() {
		OVERLAYPANEL_INFO info = new OVERLAYPANEL_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
		// public String customClass = "";
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		OVERLAYPANEL_INFO info = (OVERLAYPANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
		// public String customClass = "";
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		OVERLAYPANEL_INFO info = (OVERLAYPANEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
	}
}
