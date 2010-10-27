package com.osp.ide.resource.resinfo;

public class SLIDABLELIST_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String bGBitmapPath = "";
	public String textOfEmptyList = "";
	// public String customClass = "";
    public String colorOfEmptyListText = DEFAULT_COLOR;

	public SLIDABLELIST_INFO clone() {
		SLIDABLELIST_INFO info = new SLIDABLELIST_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
		info.fastScroll = fastScroll;
		info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		SLIDABLELIST_INFO info = (SLIDABLELIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
		info.fastScroll = fastScroll;
        info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		SLIDABLELIST_INFO info = (SLIDABLELIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
        info.colorOfEmptyListText = colorOfEmptyListText;
	}
}
