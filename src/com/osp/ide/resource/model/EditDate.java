package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.DATEPICKER_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class EditDate extends FrameNode {

	public static final int DATEPICKER_DEFAULT_WIDTH = 480;
	public static final int DATEPICKER_DEFAULT_HEIGHT = 800;
	public static final int DATEPICKER_MIN_WIDTH = 81;
	public static final int DATEPICKER_MIN_HEIGHT = 81;

	public EditDate() {
		super();
		item = new DATEPICKER_INFO();
	}

	public EditDate(String name, DATEPICKER_INFO item) {
		super(name, item);
	}

	public EditDate(Object scene, int mode) {
		super(scene, mode);
		item = new DATEPICKER_INFO();
	}

	public void setItem(DATEPICKER_INFO item) {
		this.item = item;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return ((DATEPICKER_INFO) item).title;
	}

	public void setTitle(String title) {
		String oldTitle = ((DATEPICKER_INFO) item).title;
		((DATEPICKER_INFO) item).title = title;

		firePropertyChange(PROPERTY_FRAMETITLE, oldTitle, title);
	}

	public void setTitleText(String titleText) {
		String oldTileText = ((DATEPICKER_INFO) item).titleText;
		((DATEPICKER_INFO) item).titleText = titleText;
		
		firePropertyChange(PROPERTY_TITLETEXT, oldTileText,
				titleText);
	}
	
	public String getItemText() {
		return ((DATEPICKER_INFO) item).itemText;
	}

	public DATEPICKER_INFO getItem() {
		return (DATEPICKER_INFO) this.item;
	}
	
	public String getTitleText() {
		return ((DATEPICKER_INFO) item).titleText;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		EditDate datepicker = new EditDate(
				getParent().getDocuments(), mode);
		datepicker.parent = getParent();
		datepicker.setCopyItem((DATEPICKER_INFO) this.item);

		return datepicker;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getNonresizeLayout(newLayout);
		super.setLayout(newLayout);
	}

}

