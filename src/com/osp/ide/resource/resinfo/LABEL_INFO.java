package com.osp.ide.resource.resinfo;


public class LABEL_INFO extends ITEM_INFO implements FrameConst {
	public String text = "";
	public String hAlign = "";
	public String vAlign = "";
	public String titleText = "";
	public String fgColor = DEFAULT_COLOR;
	public String bgColor = DEFAULT_COLOR;
	public String textColor = DEFAULT_COLOR;
	public String bgBitmap = "";
	public String textStyle = cszStyle[STYLE_LABEL_TEXT][0];
	public int textSize = 34;
	public int bgColorOpacity = 0;
	
	public LABEL_INFO clone() {
		LABEL_INFO info = new LABEL_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.titleText = titleText;
		info.fgColor = fgColor;
		info.bgColor = bgColor;
		info.textColor = textColor;
		info.bgBitmap = bgBitmap;
		info.textSize = textSize;
		info.bgColorOpacity = bgColorOpacity;
		info.textStyle = textStyle;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		LABEL_INFO info = (LABEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.titleText = titleText;
		info.fgColor = fgColor;
		info.bgColor = bgColor;
		info.textColor = textColor;
		info.bgBitmap = bgBitmap;
		info.textSize = textSize;
		info.bgColorOpacity = bgColorOpacity;
		info.textStyle = textStyle;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		LABEL_INFO info = (LABEL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		info.titleText = titleText;
		info.fgColor = fgColor;
		info.bgColor = bgColor;
		info.textColor = textColor;
		info.bgColorOpacity = bgColorOpacity;
		info.textStyle = textStyle;
	}
}
