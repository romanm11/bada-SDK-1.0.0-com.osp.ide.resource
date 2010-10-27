package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.TIMEPICKER_INFO;

public class EditTime extends FrameNode {

	public static final int TIMEPICKER_DEFAULT_WIDTH = 480;
	public static final int TIMEPICKER_DEFAULT_HEIGHT = 800;
	public static final int TIMEPICKER_MIN_WIDTH = 81;
	public static final int TIMEPICKER_MIN_HEIGHT = 81;

	public EditTime() {
		super();
		item = new TIMEPICKER_INFO();
	}

	public EditTime(String name, TIMEPICKER_INFO item) {
		super(name, item);
	}

	public EditTime(Object scene, int mode) {
		super(scene, mode);
		item = new TIMEPICKER_INFO();
	}

	public void setItem(TIMEPICKER_INFO item) {
		this.item = item;
	}

	public void set24Hour(boolean state) {
		((TIMEPICKER_INFO) item).b24Hour = state;
	}

	public void set24Hour(int state) {
		if (state == BOOL_TRUE)
			((TIMEPICKER_INFO) item).b24Hour = true;
		else
			((TIMEPICKER_INFO) item).b24Hour = false;
	}

	public int get24Hour() {
		if (((TIMEPICKER_INFO) item).b24Hour)
			return BOOL_TRUE;
		else
			return BOOL_FALSE;
	}

	public String getItemText() {
		return ((TIMEPICKER_INFO) item).itemText;
	}

	public TIMEPICKER_INFO getItem() {
		return (TIMEPICKER_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		EditTime timepicker = new EditTime(
				getParent().getDocuments(), mode);
		timepicker.parent = getParent();
		timepicker.setCopyItem((TIMEPICKER_INFO) this.item);

		return timepicker;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getNonresizeLayout(newLayout);
		super.setLayout(newLayout);
	}
}

