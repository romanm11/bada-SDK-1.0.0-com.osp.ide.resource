package com.osp.ide.resource.resinfo;


public class BUTTON_INFO extends ITEM_INFO implements FrameConst {
	public String text = "";
	public String textDirection = "";
	public String hAlign = "";
	public String vAlign = "";
	// public boolean outline;
	public String normalBGColor = DEFAULT_COLOR;
	public String normalFGColor = DEFAULT_COLOR;
	// public Color normalOutline=new Color(null, 255, 255, 255);
	public String pressedBGColor = DEFAULT_COLOR;
	public String pressedFGColor = DEFAULT_COLOR;
	// public Color pressedOutline=new Color(null, 255, 255, 255);
	public String disableBGColor = DEFAULT_COLOR;
	public String disableFGColor = DEFAULT_COLOR;
	// public Color disableOutline=new Color(null, 255, 255, 255);
	public String normalBitmapPath = "";
	public int normalBitmapX = 0;
	public int normalBitmapY = 0;
	public String pressedBitmapPath = "";
	public int pressedBitmapX = 0;
	public int pressedBitmapY = 0;
	public String disabledBitmapPath = "";
	public int disabledBitmapX = 0;
	public int disabledBitmapY = 0;
	public String normalBGBitmapPath = "";
	public String pressedBGBitmapPath = "";
	
	public BUTTON_INFO clone() {
		BUTTON_INFO info = new BUTTON_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.textDirection = textDirection;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		// public boolean outline;
		info.normalBGColor = normalBGColor;
		info.normalFGColor = normalFGColor;
		// public Color normalOutline=new Color(null, 255, 255, 255);
		info.pressedBGColor = pressedBGColor;
		info.pressedFGColor = pressedFGColor;
		// public Color pressedOutline=new Color(null, 255, 255, 255);
		info.disableBGColor = disableBGColor;
		info.disableFGColor = disableFGColor;
		// public Color disableOutline=new Color(null, 255, 255, 255);
		info.normalBitmapPath = normalBitmapPath;
		info.normalBitmapX = normalBitmapX;
		info.normalBitmapY = normalBitmapY;
		info.pressedBitmapPath = pressedBitmapPath;
		info.pressedBitmapX = pressedBitmapX;
		info.pressedBitmapY = pressedBitmapY;
		info.disabledBitmapPath = disabledBitmapPath;
		info.disabledBitmapX = disabledBitmapX;
		info.disabledBitmapY = disabledBitmapY;
		info.normalBGBitmapPath = normalBGBitmapPath;
		info.pressedBGBitmapPath = pressedBGBitmapPath;
		return info;
	}
	
	@Override
	public void copy(ITEM_INFO itemInfo) {
		BUTTON_INFO info = (BUTTON_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.textDirection = textDirection;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		// public boolean outline;
		info.normalBGColor = normalBGColor;
		info.normalFGColor = normalFGColor;
		// public Color normalOutline=new Color(null, 255, 255, 255);
		info.pressedBGColor = pressedBGColor;
		info.pressedFGColor = pressedFGColor;
		// public Color pressedOutline=new Color(null, 255, 255, 255);
		info.disableBGColor = disableBGColor;
		info.disableFGColor = disableFGColor;
		// public Color disableOutline=new Color(null, 255, 255, 255);
		info.normalBitmapPath = normalBitmapPath;
		info.normalBitmapX = normalBitmapX;
		info.normalBitmapY = normalBitmapY;
		info.pressedBitmapPath = pressedBitmapPath;
		info.pressedBitmapX = pressedBitmapX;
		info.pressedBitmapY = pressedBitmapY;
		info.disabledBitmapPath = disabledBitmapPath;
		info.disabledBitmapX = disabledBitmapX;
		info.disabledBitmapY = disabledBitmapY;
		info.normalBGBitmapPath = normalBGBitmapPath;
		info.pressedBGBitmapPath = pressedBGBitmapPath;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		BUTTON_INFO info = (BUTTON_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.text = text;
		info.textDirection = textDirection;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
	}
}
