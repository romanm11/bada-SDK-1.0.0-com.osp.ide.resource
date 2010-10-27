package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.EDITAREA_INFO;

public class EditArea extends FrameNode {

	public static final int EDITAREA_DEFAULT_WIDTH = 200;
	public static final int EDITAREA_DEFAULT_HEIGHT = 124;
	public static final int EDITAREA_MIN_WIDTH = 81;
	public static final int EDITAREA_MIN_HEIGHT = 81;

	public EditArea() {
		super();
		item = new EDITAREA_INFO();
	}

	public EditArea(String name, EDITAREA_INFO item) {
		super(name, item);
	}

	public EditArea(Object scene, int mode) {
		super(scene, mode);
		item = new EDITAREA_INFO();
	}

	public void setItem(EDITAREA_INFO item) {
		this.item = item;
	}

	public void setText(String text) {
		String oldText = ((EDITAREA_INFO) item).text;
		((EDITAREA_INFO) item).text = text;

		firePropertyChange(PROPERTY_TEXT, oldText, text);
	}

	public String getText() {
		return ((EDITAREA_INFO) item).text;
	}

	public void setGuideText(String guideText) {
		String oldText = ((EDITAREA_INFO) item).guideText;
		((EDITAREA_INFO) item).guideText = guideText;

		firePropertyChange(PROPERTY_GUIDETEXT, oldText,
				guideText);
	}
	
	public String getGuideText() {
		return ((EDITAREA_INFO) item).guideText;
	}

	public void setLimitLength(int length) {
		int oldLength = ((EDITAREA_INFO) item).limitLength;
		((EDITAREA_INFO) item).limitLength = length;

		firePropertyChange(PROPERTY_LIMITLENGTH, oldLength, length);
	}
	
	public int getLimitLength() {
		return ((EDITAREA_INFO) item).limitLength;
	}
	
	public EDITAREA_INFO getItem() {
		return (EDITAREA_INFO) this.item;
	}
	
	public void setInputStyle(String inputStyle) {
		String oldText = ((EDITAREA_INFO) item).inputStyle;
		((EDITAREA_INFO) item).inputStyle = inputStyle;

		firePropertyChange(PROPERTY_INPUTSTYLE, oldText,
				inputStyle);
	}
	
	public String getInputStyle() {
		return ((EDITAREA_INFO) item).inputStyle;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		EditArea editarea = new EditArea(getParent().getDocuments(), mode);
		editarea.parent = getParent();
		editarea.setCopyItem((EDITAREA_INFO) this.item);

		return editarea;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

