package com.osp.ide.resource.resinfo;

public class CUSTOMLIST_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String bGBitmapPath = "";
	public String BgStyleIndex = "";
	public String textOfEmptyList = "";
    public String colorOfEmptyListText = DEFAULT_COLOR;

	// public String customClass = "";

	public CUSTOMLIST_INFO clone() {
		CUSTOMLIST_INFO info = new CUSTOMLIST_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.BgStyleIndex = BgStyleIndex;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
		info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		CUSTOMLIST_INFO info = (CUSTOMLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bGBitmapPath = bGBitmapPath;
		info.BgStyleIndex = BgStyleIndex;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
        info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		CUSTOMLIST_INFO info = (CUSTOMLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.BgStyleIndex = BgStyleIndex;
		info.textOfEmptyList = textOfEmptyList;
		info.itemDivider = itemDivider;
        info.colorOfEmptyListText = colorOfEmptyListText;
	}
}
