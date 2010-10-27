package com.osp.ide.resource.resinfo;

public class EDITFIELD_INFO extends ITEM_INFO implements FrameConst {
	public String guideText = "";
	public String text = "";
	public String inputStyle = "";
	public String KeypadEnabled ="TRUE";
	public int limitLength = 1000;
	
	
	public EDITFIELD_INFO clone() {
		EDITFIELD_INFO info = new EDITFIELD_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.inputStyle = inputStyle;
		info.BorderStyle = BorderStyle;
		info.limitLength = limitLength;		
		info.titleText = titleText;
		info.KeypadEnabled = KeypadEnabled;
		info.ShowTitleText = ShowTitleText;
		info.groupStyle = groupStyle;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		EDITFIELD_INFO info = (EDITFIELD_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.inputStyle = inputStyle;
		info.BorderStyle = BorderStyle;
		info.limitLength = limitLength;		
		info.titleText = titleText;
		info.KeypadEnabled = KeypadEnabled;
		info.ShowTitleText = ShowTitleText;
        info.groupStyle = groupStyle;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		EDITFIELD_INFO info = (EDITFIELD_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.guideText = guideText;
		info.text = text;
		info.inputStyle = inputStyle;
		info.BorderStyle = BorderStyle;
		info.limitLength = limitLength;		
		info.titleText = titleText;
		info.KeypadEnabled = KeypadEnabled;
		info.ShowTitleText = ShowTitleText;
        info.groupStyle = groupStyle;
	}
}
