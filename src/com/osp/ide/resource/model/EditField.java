package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.EDITFIELD_INFO;

public class EditField extends FrameNode {

	public static final int EDITFIELD_DEFAULT_WIDTH = 200;
	public static final int EDITFIELD_DEFAULT_HEIGHT = 62;
	public static final int EDITFIELD_MIN_WIDTH = 81;
	public static final int EDITFIELD_MIN_HEIGHT = 80;

	public EditField() {
		super();
		item = new EDITFIELD_INFO();
	}

	public EditField(String name, EDITFIELD_INFO item) {
		super(name, item);
	}

	public EditField(Object scene, int mode) {
		super(scene, mode);
		item = new EDITFIELD_INFO();
	}

	public void setItem(EDITFIELD_INFO item) {
		this.item = item;
	}

	public void setText(String text) {
		String oldText = ((EDITFIELD_INFO) item).text;
		((EDITFIELD_INFO) item).text = text;

		firePropertyChange(PROPERTY_TEXT, oldText, text);
	}
	
	public void setLimitLength(int length) {
		int oldLength = ((EDITFIELD_INFO) item).limitLength;
		((EDITFIELD_INFO) item).limitLength = length;

		firePropertyChange(PROPERTY_LIMITLENGTH, oldLength, length);
	}

	public void setInputStyle(String inputStyle) {
		String oldText = ((EDITFIELD_INFO) item).inputStyle;
		((EDITFIELD_INFO) item).inputStyle = inputStyle;

		firePropertyChange(PROPERTY_INPUTSTYLE, oldText,
				inputStyle);
	}

	public String getText() {
		return ((EDITFIELD_INFO) item).text;
	}

	public void setGuideText(String guideText) {
		String oldText = ((EDITFIELD_INFO) item).guideText;
		((EDITFIELD_INFO) item).guideText = guideText;

		firePropertyChange(PROPERTY_GUIDETEXT, oldText,
				guideText);
	}

	public String getGuideText() {
		return ((EDITFIELD_INFO) item).guideText;
	}

	public EDITFIELD_INFO getItem() {
		return (EDITFIELD_INFO) this.item;
	}

	public int getLimitLength() {
		return ((EDITFIELD_INFO) item).limitLength;
	}
	
	public String getInputStyle() {
		return ((EDITFIELD_INFO) item).inputStyle;
	}
	public int getKeypadEnabled() {
		for (int i = 0; i < BOOL.length; i++) {
			if (BOOL[i].equals(((EDITFIELD_INFO) item).KeypadEnabled))
				return i;
		}
		return 0;
	}
	
	public void setKeypadEnabled(int KeypadEnabled) {
		String oldKeypadEnabled = ((EDITFIELD_INFO) item).KeypadEnabled;
		((EDITFIELD_INFO) item).KeypadEnabled = BOOL[KeypadEnabled];

		firePropertyChange(PROPERTY_SHOWTITLETEXT, oldKeypadEnabled,
				((EDITFIELD_INFO) item).KeypadEnabled);
	}
	
	public void setKeypadEnabled(String KeypadEnabled) {
		String oldKeypadEnabled = ((EDITFIELD_INFO) item).KeypadEnabled;
		((EDITFIELD_INFO) item).KeypadEnabled = KeypadEnabled;

		firePropertyChange(PROPERTY_SHOWTITLETEXT, oldKeypadEnabled,
				KeypadEnabled);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		EditField editfield = new EditField(getParent().getDocuments(), mode);
		editfield.parent = getParent();
		editfield.setCopyItem((EDITFIELD_INFO) this.item);

		return editfield;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

