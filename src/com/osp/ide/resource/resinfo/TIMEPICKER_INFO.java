package com.osp.ide.resource.resinfo;

public class TIMEPICKER_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public boolean b24Hour = true;

	// public String customClass = "";

	public TIMEPICKER_INFO clone() {
		TIMEPICKER_INFO info = new TIMEPICKER_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.b24Hour = b24Hour;
		info.titleText = titleText;
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		TIMEPICKER_INFO info = (TIMEPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.b24Hour = b24Hour;
		info.titleText = titleText;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		TIMEPICKER_INFO info = (TIMEPICKER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.b24Hour = b24Hour;
		info.titleText = titleText;
	}
}
