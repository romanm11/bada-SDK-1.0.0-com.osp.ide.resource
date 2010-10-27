package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.CUSTOMLIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class CustomList extends FrameNode {

	public static final int CUSTOMLIST_DEFAULT_WIDTH = 200;
	public static final int CUSTOMLIST_DEFAULT_HEIGHT = 82;
	public static final int CUSTOMLIST_MIN_WIDTH = 81;
	public static final int CUSTOMLIST_MIN_HEIGHT = 81;

	public CustomList() {
		super();
		item = new CUSTOMLIST_INFO();
	}

	public CustomList(String name, CUSTOMLIST_INFO item) {
		super(name, item);
	}

	public CustomList(Object scene, int mode) {
		super(scene, mode);
		item = new CUSTOMLIST_INFO();
	}

	public void setItem(CUSTOMLIST_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((CUSTOMLIST_INFO) item).itemText;
		((CUSTOMLIST_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((CUSTOMLIST_INFO) item).itemText;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((CUSTOMLIST_INFO) item).colorOfEmptyListText;
        ((CUSTOMLIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((CUSTOMLIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((CUSTOMLIST_INFO) item).textOfEmptyList;
		((CUSTOMLIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}
	
	public String getTextOfEmptyList() {
		return ((CUSTOMLIST_INFO) item).textOfEmptyList;
	}
	
	public CUSTOMLIST_INFO getItem() {
		return (CUSTOMLIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		CustomList customcontrol = new CustomList(getParent().getDocuments(), mode);
		customcontrol.parent = getParent();
		customcontrol.setCopyItem((CUSTOMLIST_INFO) item);

		return customcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

