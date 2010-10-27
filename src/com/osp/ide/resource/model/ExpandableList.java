package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.EXPANDABLELIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class ExpandableList extends FrameNode {

	public static final int EXPANDABLELIST_DEFAULT_WIDTH = 200;
	public static final int EXPANDABLELIST_DEFAULT_HEIGHT = 90;
	public static final int EXPANDABLELIST_MIN_WIDTH = 81;
	public static final int EXPANDABLELIST_MIN_HEIGHT = 81;

	public ExpandableList() {
		super();
		item = new EXPANDABLELIST_INFO();
	}

	public ExpandableList(String name, EXPANDABLELIST_INFO item) {
		super(name, item);
	}

	public ExpandableList(Object scene, int mode) {
		super(scene, mode);
		item = new EXPANDABLELIST_INFO();
	}

	public void setItem(EXPANDABLELIST_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((EXPANDABLELIST_INFO) item).itemText;
		((EXPANDABLELIST_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((EXPANDABLELIST_INFO) item).itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((EXPANDABLELIST_INFO) item).colorOfEmptyListText;
        ((EXPANDABLELIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((EXPANDABLELIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((EXPANDABLELIST_INFO) item).textOfEmptyList;
		((EXPANDABLELIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}
	
	public String getTextOfEmptyList() {
		return ((EXPANDABLELIST_INFO) item).textOfEmptyList;
	}

	public EXPANDABLELIST_INFO getItem() {
		return (EXPANDABLELIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		ExpandableList customcontrol = new ExpandableList(getParent().getDocuments(), mode);
		customcontrol.parent = getParent();
		customcontrol.setCopyItem((EXPANDABLELIST_INFO) this.item);

		return customcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

