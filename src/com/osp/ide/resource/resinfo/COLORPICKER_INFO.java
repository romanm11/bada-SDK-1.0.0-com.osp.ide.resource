package com.osp.ide.resource.resinfo;

public class COLORPICKER_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";

	// public String customClass = "";

	public COLORPICKER_INFO clone() {
		COLORPICKER_INFO info = new COLORPICKER_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.titleText = titleText;
		// public String customClass = "";
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		COLORPICKER_INFO info = (COLORPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.titleText = titleText;
		// public String customClass = "";
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		COLORPICKER_INFO info = (COLORPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.titleText = titleText;
	}
}
