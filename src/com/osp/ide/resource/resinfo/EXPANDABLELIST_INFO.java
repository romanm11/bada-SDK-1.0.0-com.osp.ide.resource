package com.osp.ide.resource.resinfo;

public class EXPANDABLELIST_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String bGBitmapPath = "";
	public String textOfEmptyList = "";
    public String colorOfEmptyListText = DEFAULT_COLOR;
	
	// public String customClass = "";

	public EXPANDABLELIST_INFO clone() {
		EXPANDABLELIST_INFO info = new EXPANDABLELIST_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.textOfEmptyList = textOfEmptyList;
		info.fastScroll = fastScroll;
		info.itemDivider = itemDivider;
		info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		EXPANDABLELIST_INFO info = (EXPANDABLELIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.textOfEmptyList = textOfEmptyList;
		info.fastScroll = fastScroll;
		info.itemDivider = itemDivider;
        info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		EXPANDABLELIST_INFO info = (EXPANDABLELIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.textOfEmptyList = textOfEmptyList;
		info.fastScroll = fastScroll;
		info.itemDivider = itemDivider;
        info.colorOfEmptyListText = colorOfEmptyListText;
	}
}
