package com.osp.ide.resource.model;

import java.io.File;

import com.osp.ide.resource.resinfo.ICONLIST_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.ICONLIST_INFO.ListItem;
import com.osp.ide.resource.resourceexplorer.TreeObject;

public class IconList extends FrameNode {

	public static final int ICONLIST_DEFAULT_WIDTH = 200;
	public static final int ICONLIST_DEFAULT_HEIGHT = 140;
	public static final int ICONLIST_MIN_WIDTH = 20;
	public static final int ICONLIST_MIN_HEIGHT = 20;

	public IconList() {
		super();
		item = new ICONLIST_INFO();
	}

	public IconList(String name, ICONLIST_INFO item) {
		super(name, item);
	}

	public IconList(Object scene, int mode) {
		// TODO Auto-generated constructor stub
		super(scene, mode);
		item = new ICONLIST_INFO();
	}

	public void setItem(ICONLIST_INFO item) {
		this.item = item;
	}

	public void setItemWidth(int itemWidth) {
		int oldWidth = ((ICONLIST_INFO) item).itemWidth;
		((ICONLIST_INFO) item).itemWidth = itemWidth;

		firePropertyChange(PROPERTY_ITEMWIDTH, oldWidth, itemWidth);
	}

	public int getItemWidth() {
		return ((ICONLIST_INFO) item).itemWidth;
	}

	public void setItemHeight(int itemHeight) {
		int oldHeight = ((ICONLIST_INFO) item).itemHeight;
		((ICONLIST_INFO) item).itemHeight = itemHeight;

		firePropertyChange(PROPERTY_ITEMHEIGHT, oldHeight, itemHeight);
	}

	public int getItemHeight() {
		return ((ICONLIST_INFO) item).itemHeight;
	}

	public void setTopMargin(int topMargin) {
		int oldValue = ((ICONLIST_INFO) item).topMargin;
		((ICONLIST_INFO) item).topMargin = topMargin;

		firePropertyChange("", oldValue, topMargin);
	}

	public int getTopMargin() {
		return ((ICONLIST_INFO) item).topMargin;
	}

	public void setLeftMargin(int leftMargin) {
		int oldValue = ((ICONLIST_INFO) item).leftMargin;
		((ICONLIST_INFO) item).leftMargin = leftMargin;

		firePropertyChange("", oldValue, leftMargin);
	}

	public int getLeftMargin() {
		return ((ICONLIST_INFO) item).leftMargin;
	}

	public void setHAlign(String hAlign) {
		String oldAlign = ((ICONLIST_INFO) item).hAlign;
		((ICONLIST_INFO) item).hAlign = hAlign;

		firePropertyChange(PROPERTY_HALIGN, oldAlign, hAlign);
	}

	public int getHAlign() {
		return getHAlignIndex(((ICONLIST_INFO) item).hAlign);
	}

	public void setVAlign(String vAlign) {
		String oldAlign = ((ICONLIST_INFO) item).vAlign;
		((ICONLIST_INFO) item).vAlign = vAlign;

		firePropertyChange(PROPERTY_VALIGN, oldAlign, vAlign);
	}

	public int getVAlign() {
		return getVAlignIndex(((ICONLIST_INFO) item).vAlign);
	}

	public void setTextSize(int size) {
		if (size < 10)
			return;
		int oldValue = ((ICONLIST_INFO) item).textSize;
		((ICONLIST_INFO) item).textSize = size;
		((ICONLIST_INFO) item).listItem.setSize(size);

		firePropertyChange("", oldValue, size);
	}

	public int getTextSize() {
		return ((ICONLIST_INFO) item).textSize;
	}

	public void setItemText(int i, String itemText) {
		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(i);
		if (listItem == null)
			listItem = ((ICONLIST_INFO) item).getListItem(i);

		String oldValue = listItem.itemText;
		listItem.itemText = itemText;

		firePropertyChange("", oldValue, itemText);
	}

	public String getItemText(int i) {
		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(i);
		if (listItem == null)
			return "";

		return listItem.itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((ICONLIST_INFO) item).colorOfEmptyListText;
        ((ICONLIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((ICONLIST_INFO) item).colorOfEmptyListText;
    }

    public void setTextOfEmptyList(String text) {
        String oldText = ((ICONLIST_INFO) item).textOfEmptyList;
        ((ICONLIST_INFO) item).textOfEmptyList = text;
        
        firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
    }
    
    public String getTextOfEmptyList() {
        return ((ICONLIST_INFO) item).textOfEmptyList;
    }
    
	public void setItemNormalBitmap(int index, File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(index);
		if (listItem == null)
			listItem = ((ICONLIST_INFO) item).getListItem(index);

		String bitmapPath = listItem.itemNormalBitmap;
		if (bitmapPath == null || bitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + bitmapPath);
			oldValue = oldFile.getName();
		}

		if (bitmap != null && bitmap.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + bitmap.getName());
			TreeObject.copyFile(bitmap, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			listItem.itemNormalBitmap = "";
		} else {
			newValue = newFile.getName();
			listItem.itemNormalBitmap = BITMAP_FOLDER + newFile.getName();
		}

		firePropertyChange(PROPERTY_ITEMNORMALBITMAP, oldValue,
				newValue);
	}

	public File getItemNormalBitmap(Integer index) {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(index);
		if (listItem == null)
			return null;
		String bitmapPath = listItem.itemNormalBitmap;

		if (bitmapPath == null || bitmapPath.isEmpty())
			return null;
		File file = new File(path + bitmapPath);
		if (file.exists())
			return file;
		else {
			listItem.itemNormalBitmap = "";
			return null;
		}
	}

	public void setItemFocusedBitmap(int index, File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(index);
		if (listItem == null)
			listItem = ((ICONLIST_INFO) item).getListItem(index);

		String bitmapPath = listItem.itemFocusedBitmap;
		if (bitmapPath == null || bitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + bitmapPath);
			oldValue = oldFile.getName();
		}

		if (bitmap != null && bitmap.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + bitmap.getName());
			TreeObject.copyFile(bitmap, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			listItem.itemFocusedBitmap = "";
		} else {
			newValue = newFile.getName();
			listItem.itemFocusedBitmap = BITMAP_FOLDER + newFile.getName();
		}

		firePropertyChange(PROPERTY_ITEMNORMALBITMAP, oldValue,
				newValue);
	}

	public File getItemFocusedBitmap(Integer index) {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		ListItem listItem = ((ICONLIST_INFO) item).listItem.get(index);
		if (listItem == null)
			return null;
		String bitmapPath = listItem.itemFocusedBitmap;

		if (bitmapPath == null || bitmapPath.isEmpty())
			return null;
		File file = new File(path + bitmapPath);
		if (file.exists())
			return file;
		else {
			listItem.itemFocusedBitmap = "";
			return null;
		}
	}

	public ICONLIST_INFO getItem() {
		return (ICONLIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		int mode = getModeIndex();
		IconList iconlist = new IconList(
				getParent().getDocuments(), mode);
		iconlist.parent = getParent();
		iconlist.setCopyItem((ICONLIST_INFO) this.item);

		return iconlist;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

