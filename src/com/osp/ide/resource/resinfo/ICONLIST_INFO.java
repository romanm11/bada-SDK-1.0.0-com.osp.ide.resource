package com.osp.ide.resource.resinfo;

import java.util.Vector;

public class ICONLIST_INFO extends ITEM_INFO implements FrameConst {
	public int itemWidth = 60;
	public int itemHeight = 60;
	public int topMargin;
	public int leftMargin;
	public String hAlign = "";
	public String vAlign = "";
	public Vector<ListItem> listItem = new Vector<ListItem>();
	public int textSize = 10;
    public String colorOfEmptyListText = DEFAULT_COLOR;
    public String textOfEmptyList = "";

	public class ListItem {
		public String itemText = "";
		public String itemNormalBitmap = "";
		public String itemFocusedBitmap = "";
		
		public ListItem(){
			
		}
	}
	
	public ICONLIST_INFO() {
		super();
		listItem.setSize(textSize);
	}

	public ICONLIST_INFO clone() {
		ICONLIST_INFO info = new ICONLIST_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemWidth = itemWidth;
		info.itemHeight = itemHeight;
		info.topMargin = topMargin;
		info.leftMargin = leftMargin;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		copyListItem(info.listItem);
		info.textSize = textSize;
		info.colorOfEmptyListText = colorOfEmptyListText;
        info.textOfEmptyList = textOfEmptyList;
		copyLayout(info);
		return info;
	}
	
	public void copyListItem(Vector<ListItem> copyItem) {
		for (int i = 0; i < textSize; i++) {
			ListItem item = new ListItem();
			if(listItem.elementAt(i) == null)
				continue;
			item.itemText = listItem.elementAt(i).itemText;
			item.itemNormalBitmap = listItem.elementAt(i).itemNormalBitmap;
			item.itemFocusedBitmap = listItem.elementAt(i).itemFocusedBitmap;
			copyItem.add(i, item);
		}
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		ICONLIST_INFO info = (ICONLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemWidth = itemWidth;
		info.itemHeight = itemHeight;
		info.topMargin = topMargin;
		info.leftMargin = leftMargin;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
		copyListItem(info.listItem);
		info.textSize = textSize;
        info.colorOfEmptyListText = colorOfEmptyListText;
        info.textOfEmptyList = textOfEmptyList;
		copyLayout(info);
	}

	public ListItem getListItem(int i) {
		ListItem item = listItem.get(i);
		if(item == null) {
			item = new ListItem();
			listItem.add(i, item);
		}
		
		return item;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		ICONLIST_INFO info = (ICONLIST_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.hAlign = hAlign;
		info.vAlign = vAlign;
        info.textOfEmptyList = textOfEmptyList;
	}

}
