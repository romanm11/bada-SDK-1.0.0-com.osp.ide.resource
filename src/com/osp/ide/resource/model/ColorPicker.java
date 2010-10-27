package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.COLORPICKER_INFO;

public class ColorPicker extends FrameNode {

	public static final int COLORPICKER_DEFAULT_WIDTH = 480;
	public static final int COLORPICKER_DEFAULT_HEIGHT = 800;
	public static final int COLORPICKER_MIN_WIDTH = 81;
	public static final int COLORPICKER_MIN_HEIGHT = 81;

	public ColorPicker() {
		super();
		item = new COLORPICKER_INFO();
	}

	public ColorPicker(String name, COLORPICKER_INFO item) {
		super(name, item);
	}

	public ColorPicker(Object scene, int mode) {
		super(scene, mode);
		item = new COLORPICKER_INFO();
	}

	public void setItem(COLORPICKER_INFO item) {
		this.item = item;
	}

	public String getItemText() {
		return ((COLORPICKER_INFO) item).itemText;
	}

	public COLORPICKER_INFO getItem() {
		return (COLORPICKER_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		ColorPicker colorpicker = new ColorPicker(getParent().getDocuments(), mode);
		colorpicker.parent = getParent();
		colorpicker.setCopyItem((COLORPICKER_INFO) item);

		return colorpicker;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getNonresizeLayout(newLayout);		
		super.setLayout(newLayout);
	}
}

