package com.osp.ide.resource.resinfo;

public class CHECK_INFO extends ITEM_INFO implements FrameConst {
	public String text = "";
	public String textDirection = "";
	public int GroupID;
	public String hAlign="";
	public String vAlign="";
	public String BgStyleIndex;
    public String textColor = DEFAULT_COLOR;
    public String titleTextColor = DEFAULT_COLOR;
	
	public CHECK_INFO clone() {
		CHECK_INFO info = new CHECK_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.BgStyle = BgStyle;
		info.text = text;
		info.textDirection = textDirection;
		info.GroupID = GroupID;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.BorderStyle = BorderStyle;
		info.BgStyleIndex = BgStyleIndex;
		info.titleText = titleText;
		info.ShowTitleText = ShowTitleText;
		info.textColor = textColor;
		info.titleTextColor = titleTextColor;
		info.groupStyle = groupStyle;
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		CHECK_INFO info = (CHECK_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.BgStyle = BgStyle;
		info.text = text;
		info.textDirection = textDirection;
		info.GroupID = GroupID;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.BorderStyle = BorderStyle;
		info.BgStyleIndex = BgStyleIndex;
		info.titleText = titleText;
		info.ShowTitleText = ShowTitleText;
        info.textColor = textColor;
        info.titleTextColor = titleTextColor;
        info.groupStyle = groupStyle;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		CHECK_INFO info = (CHECK_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.BgStyle = BgStyle;
		info.text = text;
		info.textDirection = textDirection;
		info.GroupID = GroupID;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.BorderStyle = BorderStyle;
		info.BgStyleIndex = BgStyleIndex;
		info.titleText = titleText;
		info.ShowTitleText = ShowTitleText;
        info.textColor = textColor;
        info.titleTextColor = titleTextColor;
        info.groupStyle = groupStyle;
	}
}
