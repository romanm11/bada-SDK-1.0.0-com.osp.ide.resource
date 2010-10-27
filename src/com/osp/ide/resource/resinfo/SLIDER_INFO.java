package com.osp.ide.resource.resinfo;

public class SLIDER_INFO extends ITEM_INFO implements FrameConst {
	public int value = 500;
	public int min = -99;
	public int max = 999;
	public String bgColor = DEFAULT_COLOR;
	public int stepSize;
	public String leftIconBitmapPath = "";
	public String rightIconBitmapPath = "";
	public String maxText = "";
	public String minText = "";
    public String titleTextColor = DEFAULT_COLOR;

	public SLIDER_INFO clone() {
		SLIDER_INFO info = new SLIDER_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		info.bgColor = bgColor;
		info.stepSize = stepSize;
		info.leftIconBitmapPath = leftIconBitmapPath;
		info.rightIconBitmapPath = rightIconBitmapPath;
		info.titleText = titleText;
		info.maxText = maxText;
		info.minText = minText;
		info.BgStyle = BgStyle;
		info.ShowTitleText = ShowTitleText;
		info.titleTextColor = titleTextColor;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		SLIDER_INFO info = (SLIDER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		info.bgColor = bgColor;
		info.stepSize = stepSize;
		info.leftIconBitmapPath = leftIconBitmapPath;
		info.rightIconBitmapPath = rightIconBitmapPath;
		info.titleText = titleText;
		info.maxText = maxText;
		info.minText = minText;
		info.BgStyle = BgStyle;
		info.ShowTitleText = ShowTitleText;
        info.titleTextColor = titleTextColor;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		SLIDER_INFO info = (SLIDER_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		info.bgColor = bgColor;
		info.stepSize = stepSize;
		info.titleText = titleText;
		info.maxText = maxText;
		info.minText = minText;
		info.BgStyle = BgStyle;
		info.ShowTitleText = ShowTitleText;
        info.titleTextColor = titleTextColor;
        info.BorderStyle = BorderStyle;
        info.groupStyle = groupStyle;
	}
}
