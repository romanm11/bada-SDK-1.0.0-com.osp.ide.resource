package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.LIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class ListControl extends FrameNode {

	public static final int LIST_DEFAULT_WIDTH = 300;
	public static final int LIST_DEFAULT_HEIGHT = 90;
	public static final int LIST_MIN_WIDTH = 81;
	public static final int LIST_MIN_HEIGHT = 81;

	public ListControl() {
		super();
		item = new LIST_INFO();
    }

	public ListControl(String name, LIST_INFO item) {
		super(name, item);
	}

	public ListControl(Object scene, int mode) {
		super(scene, mode);
		item = new LIST_INFO();
	}

	public void setItem(LIST_INFO item) {
		this.item = item;
	}

	public void setListItemFormat(int index) {
		String oldText = ((LIST_INFO) item).ListItemFormat;
		((LIST_INFO) item).ListItemFormat = cszListItemFormat[index];

		firePropertyChange(PROPERTY_LISTITEMFORMAT, oldText,
				cszListItemFormat[index]);
	}

	public void setLine1Height(int height) {
		int oldHeight = ((LIST_INFO) item).line1Height;
		((LIST_INFO) item).line1Height = height;

		firePropertyChange(PROPERTY_LINE1HEIGHT, oldHeight,
				height);
	}

	public int getLine1Height() {
		return ((LIST_INFO) item).line1Height;
	}

	public void setLine2Height(int value) {
		int oldHeight = ((LIST_INFO) item).line2Height;
		((LIST_INFO) item).line2Height = value;

		firePropertyChange(PROPERTY_LINE2HEIGHT, oldHeight,
				value);
	}

	public int getLine2Height() {
		return ((LIST_INFO) item).line2Height;
	}

	public void setColume1Width(int width) {
		int oldWidth = ((LIST_INFO) item).colume1Width;
		((LIST_INFO) item).colume1Width = width;

		firePropertyChange(PROPERTY_COLUME1WIDTH, oldWidth,
				width);
	}

	public int getColume1Width() {
		return ((LIST_INFO) item).colume1Width;
	}

	public void setColume2Width(int value) {
		int oldWidth = ((LIST_INFO) item).colume2Width;
		((LIST_INFO) item).colume2Width = value;

		firePropertyChange(PROPERTY_COLUME2WIDTH, oldWidth,
				value);
	}

	public int getColume2Width() {
		return ((LIST_INFO) item).colume2Width;
	}

    public void setTextColor(String textColor) {
        String oldColor = ((LIST_INFO) item).colorOfEmptyListText;
        ((LIST_INFO) item).colorOfEmptyListText = textColor;

        firePropertyChange(PROPERTY_COLOROFEMPTYLISTTEXT, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((LIST_INFO) item).colorOfEmptyListText;
    }

	public void setTextOfEmptyList(String text) {
		String oldText = ((LIST_INFO) item).textOfEmptyList;
		((LIST_INFO) item).textOfEmptyList = text;
		
		firePropertyChange(PROPERTY_TEXTOFEMPTYLIST, oldText, text);
	}

	public String getTextOfEmptyList() {
		return ((LIST_INFO) item).textOfEmptyList;
	}

	public void setListItemFormat(String ListItemFormat) {
		String oldText = ((LIST_INFO) item).ListItemFormat;
		((LIST_INFO) item).ListItemFormat = ListItemFormat;

		firePropertyChange(PROPERTY_LISTITEMFORMAT, oldText,
				ListItemFormat);
	}

	public String getListItemFormat() {
		return ((LIST_INFO) item).ListItemFormat;
	}

	public LIST_INFO getItem() {
		return (LIST_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		ListControl listctl = new ListControl(getParent().getDocuments(), mode);
		listctl.parent = getParent();
		listctl.setCopyItem((LIST_INFO) this.item);

		return listctl;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}
