package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SLIDABLELIST_INFO;

public class SlidableList extends FrameNode {

	public static final int SLIST_DEFAULT_WIDTH = 200;
	public static final int SLIST_DEFAULT_HEIGHT = 90;
	public static final int SLIST_MIN_WIDTH = 81;
	public static final int SLIST_MIN_HEIGHT = 81;

	public SlidableList() {
		super();
		item = new SLIDABLELIST_INFO();
	}

	public SlidableList(String name, SLIDABLELIST_INFO item) {
		super(name, item);
	}

	public SlidableList(Object scene, int mode) {
		super(scene, mode);
		item = new SLIDABLELIST_INFO();
	}

	public void setItem(SLIDABLELIST_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((SLIDABLELIST_INFO) item).itemText;
		((SLIDABLELIST_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((SLIDABLELIST_INFO) item).itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((SLIDABLELIST_INFO) item).colorOfEmptyListText;
        ((SLIDABLELIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((SLIDABLELIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((SLIDABLELIST_INFO) item).textOfEmptyList;
		((SLIDABLELIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}

	public String getTextOfEmptyList() {
		return ((SLIDABLELIST_INFO) item).textOfEmptyList;
	}

	public SLIDABLELIST_INFO getItem() {
		return (SLIDABLELIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		SlidableList customcontrol = new SlidableList(
				getParent().getDocuments(), mode);
		customcontrol.parent = getParent();
		customcontrol.setCopyItem((SLIDABLELIST_INFO) this.item);

		return customcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

