package com.osp.ide.resource.model;

import java.util.ArrayList;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANEL_INFO;

public class Panel extends FrameNode {

	public static final int PANEL_DEFAULT_WIDTH = 200;
	public static final int PANEL_DEFAULT_HEIGHT = 62;
	public static final int PANEL_MIN_WIDTH = 81;
	public static final int PANEL_MIN_HEIGHT = 81;

	public Panel() {
		super();
		item = new PANEL_INFO();
	}

	public Panel(String name, PANEL_INFO item) {
		super(name, item);
	}

	public Panel(Object scene, int mode) {
		super(scene, mode);
		item = new PANEL_INFO();
	}

	public void setItem(PANEL_INFO item) {
		this.item = item;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return ((PANEL_INFO) item).title;
	}

	public void setTitle(String title) {
		String oldTitle = ((PANEL_INFO) item).title;
		((PANEL_INFO) item).title = title;

		firePropertyChange(PROPERTY_FRAMETITLE, oldTitle, title);
	}

	public String getItemText() {
		return ((PANEL_INFO) item).itemText;
	}

	public void setBGColor(String bgColor) {
		String oldColor = ((PANEL_INFO) item).bgColor;
		((PANEL_INFO) item).bgColor = bgColor;

		firePropertyChange(PROPERTY_BGCOLOR, oldColor, bgColor);
	}

	public String getBGColor() {
		return ((PANEL_INFO) item).bgColor;
	}

	public PANEL_INFO getItem() {
		return (PANEL_INFO) this.item;
	}

	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((PANEL_INFO) item).bgColorOpacity;
		((PANEL_INFO) item).bgColorOpacity = bgColorOpacity;

		firePropertyChange(PROPERTY_BGOPACITY, oldValue, bgColorOpacity);
	}

	public int getBGColorTransparency() {
		return ((PANEL_INFO) item).bgColorOpacity;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Panel panel = new Panel(getParent().getDocuments(), mode);
		panel.parent = getParent();
		panel.setCopyItem((PANEL_INFO) this.item);
		panel.children = new ArrayList<FrameNode>();
		for(int i=0; i<getChildrenArray().size(); i++) {
			panel.children.add((FrameNode)getChildrenArray().get(i).clone());
		}

		return panel;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

