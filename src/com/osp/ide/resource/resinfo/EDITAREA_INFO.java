package com.osp.ide.resource.resinfo;

public class EDITAREA_INFO extends ITEM_INFO implements FrameConst {
	public String guideText = "";
	public String text = "";
	public String inputStyle="";
	public int limitLength = 1000;
	
	public EDITAREA_INFO clone() {
		EDITAREA_INFO info = new EDITAREA_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.limitLength = limitLength;
		info.inputStyle = inputStyle;

		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		EDITAREA_INFO info = (EDITAREA_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.limitLength = limitLength;
		info.inputStyle = inputStyle;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		EDITAREA_INFO info = (EDITAREA_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.limitLength = limitLength;
		info.inputStyle = inputStyle;
	}
}
