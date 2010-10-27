package com.osp.ide.resource.resinfo;

public class DATEPICKER_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String title = "";
	public String titleText = "";
	
	// public String customClass = "";

	public DATEPICKER_INFO clone() {
		DATEPICKER_INFO info = new DATEPICKER_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.title = title;
		info.titleText = titleText;
		// public String customClass = "";
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		DATEPICKER_INFO info = (DATEPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.title = title;
		info.titleText = titleText;
		// public String customClass = "";
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		DATEPICKER_INFO info = (DATEPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.title = title;
		info.titleText = titleText;
	}
}
