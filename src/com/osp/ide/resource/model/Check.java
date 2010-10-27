package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.CHECK_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class Check extends FrameNode {

	public static final int CHECK_DEFAULT_WIDTH = 200;
	public static final int CHECK_DEFAULT_HEIGHT = 60;
	public static final int CHECK_MIN_WIDTH = 81;
	public static final int CHECK_MIN_HEIGHT = 81;

	public Check() {
		super();
		item = new CHECK_INFO();
	}

	public Check(String name, CHECK_INFO item) {
		super(name, item);
	}

	public Check(Object scene, int mode) {
		super(scene, mode);
		item = new CHECK_INFO();
		((CHECK_INFO) item).hAlign = cszHAlign[ALIGN_LEFT];
		((CHECK_INFO) item).vAlign = cszVAlign[ALIGN_TOP];
	}

	public void setItem(CHECK_INFO item) {
		this.item = item;
	}

	public void setTextDirection(String direction) {
		String oldDirection = ((CHECK_INFO) item).textDirection;
		((CHECK_INFO) item).textDirection = direction;

		firePropertyChange(PROPERTY_TEXTDIRECTION, oldDirection,
				direction);
	}

	public int getTextDirection() {
		return getTextDirectionIndex(((CHECK_INFO) item).textDirection);
	}

	public void setGroupId(int GroupID) {
		int oldValue = ((CHECK_INFO) item).GroupID;
		((CHECK_INFO) item).GroupID = GroupID;

		firePropertyChange(PROPERTY_GROUPID, oldValue,
				((CHECK_INFO) item).GroupID);
	}

	public String getGroupId() {
		return Integer.toString(((CHECK_INFO) item).GroupID);
	
	}
	
	public void setHAlign(String hAlign) {
		String oldAlign = ((CHECK_INFO) item).hAlign;
		((CHECK_INFO) item).hAlign = hAlign;

		firePropertyChange(PROPERTY_HALIGN, oldAlign, hAlign);
	}

	public int getHAlign() {
		return getHAlignIndex(((CHECK_INFO) item).hAlign);
	}

	public void setVAlign(String vAlign) {
		String oldAlign = ((CHECK_INFO) item).vAlign;
		((CHECK_INFO) item).vAlign = vAlign;

		firePropertyChange(PROPERTY_VALIGN, oldAlign, vAlign);
	}

	public int getVAlign() {
		return getVAlignIndex(((CHECK_INFO) item).vAlign);
	}
	
    public void setTextColor(String textColor) {
        String oldColor = ((CHECK_INFO) item).textColor;
        ((CHECK_INFO) item).textColor = textColor;

        firePropertyChange(PROPERTY_TEXTCOLOR, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTextColor() {
        return ((CHECK_INFO) item).textColor;
    }
    
	public void setText(String text) {
		String oldText = ((CHECK_INFO) item).text;
		((CHECK_INFO) item).text = text;

		firePropertyChange(PROPERTY_TEXT, oldText, text);
	}
	
	public String getText() {
		return ((CHECK_INFO) item).text;
	}

    public void setTitleTextColor(String textColor) {
        String oldColor = ((CHECK_INFO) item).titleTextColor;
        ((CHECK_INFO) item).titleTextColor = textColor;

        firePropertyChange(PROPERTY_TITLETEXTCOLOR, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTitleTextColor() {
        return ((CHECK_INFO) item).titleTextColor;
    }
    
	public CHECK_INFO getItem() {
		return (CHECK_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Check check = new Check(getParent().getDocuments(), mode);
		check.parent = getParent();
		check.setCopyItem((CHECK_INFO) this.item);

		return check;
	}

	/* (non-Javadoc)
     * @see com.osp.ide.resource.model.FrameNode#setStyle(java.lang.String)
     */
    @Override
    public void setStyle(String style) {
        super.setStyle(style);
        setLayout(item.GetLayout(getModeIndex()));
    }

    @Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}
