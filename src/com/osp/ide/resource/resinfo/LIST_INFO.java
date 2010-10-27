package com.osp.ide.resource.resinfo;

public class LIST_INFO extends ITEM_INFO implements FrameConst {
	public String ListItemFormat = "";
	public String itemText = "";
	public int line1Height = 80;
	public int line2Height = 50;
	public String textOfEmptyList = "";
	public int colume1Width = 50;
	public int colume2Width = 50;
    public String colorOfEmptyListText = DEFAULT_COLOR;
	
	public LIST_INFO clone() {
		LIST_INFO info = new LIST_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.ListItemFormat = ListItemFormat;
		info.itemText = itemText;
		info.line1Height = line1Height;
		info.line2Height = line2Height;
		info.colume1Width = colume1Width;
		info.colume2Width = colume2Width;
		info.textOfEmptyList = textOfEmptyList;
		info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		LIST_INFO info = (LIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.ListItemFormat = ListItemFormat;
		info.itemText = itemText;
		info.line1Height = line1Height;
		info.line2Height = line2Height;
		info.colume1Width = colume1Width;
		info.colume2Width = colume2Width;
		info.textOfEmptyList = textOfEmptyList;
        info.colorOfEmptyListText = colorOfEmptyListText;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		LIST_INFO info = (LIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.ListItemFormat = ListItemFormat;
		info.itemText = itemText;
		info.textOfEmptyList = textOfEmptyList;
        info.colorOfEmptyListText = colorOfEmptyListText;
	}
}
