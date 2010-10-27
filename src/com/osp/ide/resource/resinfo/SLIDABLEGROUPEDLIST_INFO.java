package com.osp.ide.resource.resinfo;

public class SLIDABLEGROUPEDLIST_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String bGBitmapPath = "";
	public String textOfEmptyList = "";
	// public String customClass = "";
    public String colorOfEmptyListText = DEFAULT_COLOR;

	public SLIDABLEGROUPEDLIST_INFO clone() {
		SLIDABLEGROUPEDLIST_INFO info = new SLIDABLEGROUPEDLIST_INFO();
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
		SLIDABLEGROUPEDLIST_INFO info = (SLIDABLEGROUPEDLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.itemText = itemText;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
		info.fastScroll = fastScroll;
        info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		SLIDABLEGROUPEDLIST_INFO info = (SLIDABLEGROUPEDLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.itemText = itemText;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
		info.fastScroll = fastScroll;
        info.colorOfEmptyListText = colorOfEmptyListText;
	}
}
