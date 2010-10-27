package com.osp.ide.resource.model;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PROGRESS_INFO;

public class Progress extends FrameNode {

	public static final int PROGRESS_DEFAULT_WIDTH = 200;
	public static final int PROGRESS_DEFAULT_HEIGHT = 62;
	public static final int PROGRESS_MIN_WIDTH = 20;
	public static final int PROGRESS_MIN_HEIGHT = 20;

	public Progress() {
		super();
		item = new PROGRESS_INFO();
	}

	public Progress(String name, PROGRESS_INFO item) {
		super(name, item);
	}

	public Progress(Object scene, int mode) {
		super(scene, mode);
		item = new PROGRESS_INFO();
	}

	public void setItem(PROGRESS_INFO item) {
		this.item = item;
	}

	public void setValue(int value) {
		if(value < ((PROGRESS_INFO) item).min 
				|| value > ((PROGRESS_INFO) item).max)
			return;
		int oldValue = ((PROGRESS_INFO) item).value;
		((PROGRESS_INFO) item).value = value;

		firePropertyChange(PROPERTY_MIN, oldValue,
				((PROGRESS_INFO) item).value);
	}

	public String getValue() {
		return Integer.toString(((PROGRESS_INFO) item).value);
	}

	public void setMin(int min) {
		int oldValue = ((PROGRESS_INFO) item).min;
		((PROGRESS_INFO) item).min = min;

		firePropertyChange(PROPERTY_MIN, oldValue,
				((PROGRESS_INFO) item).min);
	}

	public String getMin() {
		return Integer.toString(((PROGRESS_INFO) item).min);
	}

	public void setMax(int max) {
		int oldValue = ((PROGRESS_INFO) item).max;
		((PROGRESS_INFO) item).max = max;

		firePropertyChange(PROPERTY_MAX, oldValue,
				((PROGRESS_INFO) item).max);
	}

	public String getMax() {
		return Integer.toString(((PROGRESS_INFO) item).max);
	}

	public PROGRESS_INFO getItem() {
		return (PROGRESS_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Progress progress = new Progress(getParent().getDocuments(), mode);
		progress.parent = getParent();
		progress.setCopyItem((PROGRESS_INFO) this.item);

		return progress;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

