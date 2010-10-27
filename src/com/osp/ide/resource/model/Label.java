package com.osp.ide.resource.model;

import java.io.File;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.resourceexplorer.TreeObject;

public class Label extends FrameNode {

	public static final int LABEL_DEFAULT_WIDTH = 200;
	public static final int LABEL_DEFAULT_HEIGHT = 62;
	public static final int LABEL_MIN_WIDTH = 81;
	public static final int LABEL_MIN_HEIGHT = 81;

	public Label() {
		super();
		item = new LABEL_INFO();
		((LABEL_INFO) item).hAlign = cszHAlign[ALIGN_LEFT];
		((LABEL_INFO) item).vAlign = cszVAlign[ALIGN_TOP];
	}

	public Label(String name, LABEL_INFO item) {
		super(name, item);
	}

	public Label(Object scene, int mode) {
		// TODO Auto-generated constructor stub
		super(scene, mode);
		item = new LABEL_INFO();
		((LABEL_INFO) item).hAlign = cszHAlign[ALIGN_LEFT];
		((LABEL_INFO) item).vAlign = cszVAlign[ALIGN_TOP];
		Tag_info tag = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_LABEL], getScreen());
		if(tag != null)
			((LABEL_INFO) item).textSize = tag.tSize;
		else
			((LABEL_INFO) item).textSize = 34;
	}

	public void setItem(LABEL_INFO item) {
		this.item = item;
	}

	public void setFGColor(String fgColor) {
		String oldColor = ((LABEL_INFO) item).fgColor;
		((LABEL_INFO) item).fgColor = fgColor;

		firePropertyChange(PROPERTY_FGCOLOR, oldColor, fgColor);
	}

	public String getFGColor() {
		return ((LABEL_INFO) item).fgColor;
	}

	public void setBGColor(String bgColor) {
		String oldColor = ((LABEL_INFO) item).bgColor;
		((LABEL_INFO) item).bgColor = bgColor;

		firePropertyChange(PROPERTY_NBGCOLOR, oldColor, bgColor);
	}

	public String getBGColor() {
		return ((LABEL_INFO) item).bgColor;
	}

	public void setTextColor(String textColor) {
		String oldColor = ((LABEL_INFO) item).textColor;
		((LABEL_INFO) item).textColor = textColor;

		firePropertyChange(PROPERTY_TEXTCOLOR, oldColor,
				textColor);
	}

	public String getTextColor() {
		return ((LABEL_INFO) item).textColor;
	}

	public void setTextSize(int textSize) {
		int oldSize = ((LABEL_INFO) item).textSize;
		((LABEL_INFO) item).textSize = textSize;

		firePropertyChange(PROPERTY_TEXTSIZE, oldSize, textSize);
	}

	public int getTextSize() {
		return ((LABEL_INFO) item).textSize;
	}

	public void setTextStyle(String textStyle) {
		String oldStyle = ((LABEL_INFO) item).textStyle;
		((LABEL_INFO) item).textStyle = textStyle;

		firePropertyChange(PROPERTY_TEXTSTYLE, oldStyle, textStyle);
	}

	public String getTextStyle() {
		return ((LABEL_INFO) item).textStyle;
	}

	public void setText(String text) {
		String oldText = ((LABEL_INFO) item).text;
		((LABEL_INFO) item).text = text;

		firePropertyChange(PROPERTY_TEXT, oldText, text);
	}

	public String getText() {
		return ((LABEL_INFO) item).text;
	}

	public void setHAlign(String hAlign) {
		String oldAlign = ((LABEL_INFO) item).hAlign;
		((LABEL_INFO) item).hAlign = hAlign;

		firePropertyChange(PROPERTY_HALIGN, oldAlign, hAlign);
	}

	public int getHAlign() {
		return getHAlignIndex(((LABEL_INFO) item).hAlign);
	}

	public void setVAlign(String vAlign) {
		String oldAlign = ((LABEL_INFO) item).vAlign;
		((LABEL_INFO) item).vAlign = vAlign;

		firePropertyChange(PROPERTY_VALIGN, oldAlign, vAlign);
	}

	public void setTitleText(String titleText) {
		String oldText = ((LABEL_INFO) item).titleText;
		((LABEL_INFO) item).titleText = titleText;

		firePropertyChange(PROPERTY_TITLETEXT, oldText,
				titleText);
	}

	public String getTitleText() {
		return ((LABEL_INFO) item).titleText;
	}

	public void setBGBitmap(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((LABEL_INFO) item).bgBitmap == null
				|| ((LABEL_INFO) item).bgBitmap.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((LABEL_INFO) item).bgBitmap);
			oldValue = oldFile.getName();
		}

		if (bitmap != null && bitmap.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + bitmap.getName());
			TreeObject.copyFile(bitmap, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((LABEL_INFO) item).bgBitmap = "";
		} else {
			newValue = newFile.getName();
			((LABEL_INFO) item).bgBitmap = BITMAP_FOLDER + newFile.getName();
		}

		firePropertyChange(PROPERTY_BGBITMAPPATH, oldValue,
				newValue);
	}

	public File getBGBitmap() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((LABEL_INFO) item).bgBitmap == null
				|| ((LABEL_INFO) item).bgBitmap.isEmpty())
			return null;
		File file = new File(path + ((LABEL_INFO) item).bgBitmap);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((LABEL_INFO) item).bgBitmap;
			((LABEL_INFO) item).bgBitmap = "";
	        firePropertyChange(PROPERTY_BGBITMAPPATH, oldValue, "");
			return null;
		}
	}

	public int getVAlign() {
		return getVAlignIndex(((LABEL_INFO) item).vAlign);
	}

	public LABEL_INFO getItem() {
		return (LABEL_INFO) this.item;
	}

	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((LABEL_INFO) item).bgColorOpacity;
		((LABEL_INFO) item).bgColorOpacity = bgColorOpacity;

		firePropertyChange(PROPERTY_BGOPACITY, oldValue,
				bgColorOpacity);
	}

	public int getBGColorTransparency() {
		return ((LABEL_INFO) item).bgColorOpacity;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		int mode = getModeIndex();
		Label label = new Label(this.getParent().getDocuments(), mode);
		label.parent = getParent();
		label.setCopyItem((LABEL_INFO) this.item);

		return label;
	}

	@Override
	public void setLayout(Layout newLayout) {
		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

