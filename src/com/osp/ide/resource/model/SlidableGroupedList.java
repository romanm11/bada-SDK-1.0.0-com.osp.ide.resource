package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SLIDABLEGROUPEDLIST_INFO;

public class SlidableGroupedList extends FrameNode {

	public static final int SGROUPEDLIST_DEFAULT_WIDTH = 200;
	public static final int SGROUPEDLIST_DEFAULT_HEIGHT = 90;
	public static final int SGROUPEDLIST_MIN_WIDTH = 81;
	public static final int SGROUPEDLIST_MIN_HEIGHT = 81;

	public SlidableGroupedList() {
		super();
		item = new SLIDABLEGROUPEDLIST_INFO();
	}

	public SlidableGroupedList(String name, SLIDABLEGROUPEDLIST_INFO item) {
		super(name, item);
	}

	public SlidableGroupedList(Object scene, int mode) {
		super(scene, mode);
		item = new SLIDABLEGROUPEDLIST_INFO();
	}

	public void setItem(SLIDABLEGROUPEDLIST_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((SLIDABLEGROUPEDLIST_INFO) item).itemText;
		((SLIDABLEGROUPEDLIST_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((SLIDABLEGROUPEDLIST_INFO) item).itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((SLIDABLEGROUPEDLIST_INFO) item).colorOfEmptyListText;
        ((SLIDABLEGROUPEDLIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((SLIDABLEGROUPEDLIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((SLIDABLEGROUPEDLIST_INFO) item).textOfEmptyList;
		((SLIDABLEGROUPEDLIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}

	public String getTextOfEmptyList() {
		return ((SLIDABLEGROUPEDLIST_INFO) item).textOfEmptyList;
	}

	public SLIDABLEGROUPEDLIST_INFO getItem() {
		return (SLIDABLEGROUPEDLIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		SlidableGroupedList customcontrol = new SlidableGroupedList(
				getParent().getDocuments(), mode);
		customcontrol.parent = getParent();
		customcontrol.setCopyItem((SLIDABLEGROUPEDLIST_INFO) this.item);

		return customcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

