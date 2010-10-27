package com.osp.ide.resource.resinfo;

public class PROGRESS_INFO extends ITEM_INFO implements FrameConst {
	public int value = 50;
	public int min;
	public int max = 100;
	// public int text;
	// public int step;
	// public String fgColor = DEFAULT_COLOR;
	public String bgColor = DEFAULT_COLOR;
	public String leftText = "";
	public String centerText = "";
	public String rightText = "";
	public boolean bShowValueState = true;

	// public String leftTextColor = DEFAULT_COLOR;
	// public String centerTextColor = DEFAULT_COLOR;
	// public String rightTextColor = DEFAULT_COLOR;

	public PROGRESS_INFO clone() {
		PROGRESS_INFO info = new PROGRESS_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		// public int text;
		// public int step;
		// public String fgColor = DEFAULT_COLOR;
		info.bgColor = bgColor;
		info.leftText = leftText;
		//info.centerText = centerText;
		info.rightText = rightText;
		info.bShowValueState = bShowValueState;
		// public String leftTextColor = DEFAULT_COLOR;
		// public String centerTextColor = DEFAULT_COLOR;
		// public String rightTextColor = DEFAULT_COLOR;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		PROGRESS_INFO info = (PROGRESS_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		// public int text;
		// public int step;
		// public String fgColor = DEFAULT_COLOR;
		info.bgColor = bgColor;
		info.leftText = leftText;
		//info.centerText = centerText;
		info.rightText = rightText;
		info.bShowValueState = bShowValueState;
		// public String leftTextColor = DEFAULT_COLOR;
		// public String centerTextColor = DEFAULT_COLOR;
		// public String rightTextColor = DEFAULT_COLOR;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		PROGRESS_INFO info = (PROGRESS_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.value = value;
		info.min = min;
		info.max = max;
		info.bgColor = bgColor;
		info.leftText = leftText;
		info.rightText = rightText;
		info.bShowValueState = bShowValueState;
	}
}
