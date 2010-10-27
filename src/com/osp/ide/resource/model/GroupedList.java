package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.GROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class GroupedList extends FrameNode {

	public static final int GROUPEDLIST_DEFAULT_WIDTH = 200;
	public static final int GROUPEDLIST_DEFAULT_HEIGHT = 90;
	public static final int GROUPEDLIST_MIN_WIDTH = 81;
	public static final int GROUPEDLIST_MIN_HEIGHT = 81;

	public GroupedList() {
		super();
		item = new GROUPEDLIST_INFO();
	}

	public GroupedList(String name, GROUPEDLIST_INFO item) {
		super(name, item);
	}

	public GroupedList(Object scene, int mode) {
		super(scene, mode);
		item = new GROUPEDLIST_INFO();
	}

	public void setItem(GROUPEDLIST_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((GROUPEDLIST_INFO) item).itemText;
		((GROUPEDLIST_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((GROUPEDLIST_INFO) item).itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((GROUPEDLIST_INFO) item).colorOfEmptyListText;
        ((GROUPEDLIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((GROUPEDLIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((GROUPEDLIST_INFO) item).textOfEmptyList;
		((GROUPEDLIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}
	
	public String getTextOfEmptyList() {
		return ((GROUPEDLIST_INFO) item).textOfEmptyList;
	}

	public GROUPEDLIST_INFO getItem() {
		return (GROUPEDLIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		GroupedList customcontrol = new GroupedList(
				getParent().getDocuments(), mode);
		customcontrol.parent = getParent();
		customcontrol.setCopyItem((GROUPEDLIST_INFO) this.item);

		return customcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

