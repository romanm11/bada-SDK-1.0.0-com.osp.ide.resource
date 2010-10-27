package com.osp.ide.resource.model;

import java.util.ArrayList;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.OVERLAYPANEL_INFO;

public class OverlayPanel extends FrameNode {

	public static final int OVERLAYPANEL_DEFAULT_WIDTH = 200;
	public static final int OVERLAYPANEL_DEFAULT_HEIGHT = 62;
	public static final int OVERLAYPANEL_MIN_WIDTH = 81;
	public static final int OVERLAYPANEL_MIN_HEIGHT = 81;

	public OverlayPanel() {
		super();
		item = new OVERLAYPANEL_INFO();
	}

	public OverlayPanel(String name, OVERLAYPANEL_INFO item) {
		super(name, item);
	}

	public OverlayPanel(Object scene, int mode) {
		super(scene, mode);
		item = new OVERLAYPANEL_INFO();
	}

	public void setItem(OVERLAYPANEL_INFO item) {
		this.item = item;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return ((OVERLAYPANEL_INFO) item).title;
	}

	public void setTitle(String title) {
		String oldTitle = ((OVERLAYPANEL_INFO) item).title;
		((OVERLAYPANEL_INFO) item).title = title;

		firePropertyChange(PROPERTY_FRAMETITLE, oldTitle, title);
	}

	public String getItemText() {
		return ((OVERLAYPANEL_INFO) item).itemText;
	}

	public OVERLAYPANEL_INFO getItem() {
		return (OVERLAYPANEL_INFO) this.item;
	}
	
	public void setBGColor(String bgColor) {
		String oldColor = ((OVERLAYPANEL_INFO) item).bgColor;
		((OVERLAYPANEL_INFO) item).bgColor = bgColor;

		firePropertyChange(PROPERTY_BGCOLOR, oldColor, bgColor);
	}
	public String getBGColor() {
		return ((OVERLAYPANEL_INFO) item).bgColor;
	}
	

	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((OVERLAYPANEL_INFO) item).bgColorOpacity;
		((OVERLAYPANEL_INFO) item).bgColorOpacity = bgColorOpacity;

		firePropertyChange(PROPERTY_BGOPACITY, oldValue, bgColorOpacity);
	}

	public int getBGColorTransparency() {
		return ((OVERLAYPANEL_INFO) item).bgColorOpacity;
	}
	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		OverlayPanel overlaypanel = new OverlayPanel(
				getParent().getDocuments(), mode);
		overlaypanel.parent = getParent();
		overlaypanel.setCopyItem((OVERLAYPANEL_INFO) this.item);
		overlaypanel.children = new ArrayList<FrameNode>();
		for(int i=0; i<getChildrenArray().size(); i++) {
			overlaypanel.children.add((FrameNode)getChildrenArray().get(i).clone());
		}

		return overlaypanel;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}
}

