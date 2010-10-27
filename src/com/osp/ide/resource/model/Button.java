package com.osp.ide.resource.model;

import java.io.File;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.BUTTON_INFO;
import com.osp.ide.resource.resourceexplorer.TreeObject;

public class Button extends FrameNode {

	public static final int BUTTON_DEFAULT_WIDTH = 200;
	public static final int BUTTON_DEFAULT_HEIGHT = 62;
	public static final int BUTTON_MIN_WIDTH = 20;
	public static final int BUTTON_MIN_HEIGHT = 20;

	public Button() {
		super();
		item = new BUTTON_INFO();
	}

	public Button(String name, BUTTON_INFO item) {
		super(name, item);
	}

	public Button(Object scene, int mode) {
		super(scene, mode);
		item = new BUTTON_INFO();
		((BUTTON_INFO) item).hAlign = cszHAlign[ALIGN_CENTER];
		((BUTTON_INFO) item).vAlign = cszVAlign[ALIGN_CENTER];
	}

	public void setItem(BUTTON_INFO item) {
		this.item = item;
	}

	public void setText(String text) {
		String oldText = ((BUTTON_INFO) item).text;
		((BUTTON_INFO) item).text = text;

		firePropertyChange(PROPERTY_TEXT, oldText, text);
	}

	public String getText() {
		return ((BUTTON_INFO) item).text;
	}

	public void setTextDirection(String direction) {
		String oldDirection = ((BUTTON_INFO) item).textDirection;
		((BUTTON_INFO) item).textDirection = direction;

		firePropertyChange(PROPERTY_TEXTDIRECTION, oldDirection,
				direction);
	}

	public int getTextDirection() {
		return getTextDirectionIndex(((BUTTON_INFO) item).textDirection);
	}

	public void setHAlign(String hAlign) {
		String oldAlign = ((BUTTON_INFO) item).hAlign;
		((BUTTON_INFO) item).hAlign = hAlign;

		firePropertyChange(PROPERTY_HALIGN, oldAlign, hAlign);
	}

	public int getHAlign() {
		return getHAlignIndex(((BUTTON_INFO) item).hAlign);
	}

	public void setVAlign(String vAlign) {
		String oldAlign = ((BUTTON_INFO) item).vAlign;
		((BUTTON_INFO) item).vAlign = vAlign;

		firePropertyChange(PROPERTY_VALIGN, oldAlign, vAlign);
	}

	public int getVAlign() {
		return getVAlignIndex(((BUTTON_INFO) item).vAlign);
	}

	public void setNormalBGColor(String normalBGColor) {
		String oldColor = ((BUTTON_INFO) item).normalBGColor;
		((BUTTON_INFO) item).normalBGColor = normalBGColor;

		firePropertyChange(PROPERTY_NBGCOLOR, oldColor,
				normalBGColor);
	}

	public String getNormalBGColor() {
		return ((BUTTON_INFO) item).normalBGColor;
	}

	public void setNormalFGColor(String normalFGColor) {
		String oldColor = ((BUTTON_INFO) item).normalFGColor;
		((BUTTON_INFO) item).normalFGColor = normalFGColor;

		firePropertyChange(PROPERTY_NTEXTCOLOR, oldColor,
				normalFGColor);
	}

	public String getNormalFGColor() {
		return ((BUTTON_INFO) item).normalFGColor;
	}

	public void setPressedBGColor(String pressedBGColor) {
		String oldColor = ((BUTTON_INFO) item).pressedBGColor;
		((BUTTON_INFO) item).pressedBGColor = pressedBGColor;

		firePropertyChange(PROPERTY_PBGCOLOR, oldColor,
				pressedBGColor);
	}

	public String getPressedBGColor() {
		return ((BUTTON_INFO) item).pressedBGColor;
	}

	public void setPressedFGColor(String pressedFGColor) {
		String oldColor = ((BUTTON_INFO) item).pressedFGColor;
		((BUTTON_INFO) item).pressedFGColor = pressedFGColor;

		firePropertyChange(PROPERTY_PTEXTCOLOR, oldColor,
				pressedFGColor);
	}

	public String getPressedFGColor() {
		return ((BUTTON_INFO) item).pressedFGColor;
	}

	public void setDisableBGColor(String disableBGColor) {
		String oldColor = ((BUTTON_INFO) item).disableBGColor;
		((BUTTON_INFO) item).disableBGColor = disableBGColor;

		firePropertyChange(PROPERTY_DBGCOLOR, oldColor,
				disableBGColor);
	}

	public String getDisableBGColor() {
		return ((BUTTON_INFO) item).disableBGColor;
	}

	public void setDisableFGColor(String disableFGColor) {
		String oldColor = ((BUTTON_INFO) item).disableFGColor;
		((BUTTON_INFO) item).disableFGColor = disableFGColor;

		firePropertyChange(PROPERTY_DTEXTCOLOR, oldColor,
				disableFGColor);
	}

	public String getDisableFGColor() {
		return ((BUTTON_INFO) item).disableFGColor;
	}

	public void setNormalBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((BUTTON_INFO) item).normalBitmapPath == null
				|| ((BUTTON_INFO) item).normalBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((BUTTON_INFO) item).normalBitmapPath);
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
			((BUTTON_INFO) item).normalBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((BUTTON_INFO) item).normalBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_NORMALBITMAPPATH, oldValue,
				newValue);
	}

	public File getNormalBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((BUTTON_INFO) item).normalBitmapPath == null
				|| ((BUTTON_INFO) item).normalBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((BUTTON_INFO) item).normalBitmapPath);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((BUTTON_INFO) item).normalBitmapPath;
			((BUTTON_INFO) item).normalBitmapPath = "";
	        firePropertyChange(PROPERTY_NORMALBITMAPPATH, oldValue, "");
			return null;
		}
	}

	public void setNormalBitmapX(int value) {
		int oldValue = ((BUTTON_INFO) item).normalBitmapX;
		((BUTTON_INFO) item).normalBitmapX = value;

		firePropertyChange(PROPERTY_NORMALBITMAPX, oldValue,
				value);
	}

	public int getNormalBitmapX() {
		return ((BUTTON_INFO) item).normalBitmapX;
	}

	public void setNormalBitmapY(int value) {
		int oldValue = ((BUTTON_INFO) item).normalBitmapY;
		((BUTTON_INFO) item).normalBitmapY = value;

		firePropertyChange(PROPERTY_NORMALBITMAPY, oldValue,
				value);
	}

	public int getNormalBitmapY() {
		return ((BUTTON_INFO) item).normalBitmapY;
	}

	public void setPressedBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((BUTTON_INFO) item).pressedBitmapPath == null
				|| ((BUTTON_INFO) item).pressedBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((BUTTON_INFO) item).pressedBitmapPath);
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
			((BUTTON_INFO) item).pressedBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((BUTTON_INFO) item).pressedBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_PRESSEDBITMAPPATH, oldValue,
				newValue);
	}

	public File getPressedBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((BUTTON_INFO) item).pressedBitmapPath == null
				|| ((BUTTON_INFO) item).pressedBitmapPath.isEmpty())
			return null;

		File file = new File(path + ((BUTTON_INFO) item).pressedBitmapPath);
		if (file.exists())
			return file;
		else {
			((BUTTON_INFO) item).pressedBitmapPath = "";
			return null;
		}
	}

	public void setPressedBitmapX(int value) {
		int oldValue = ((BUTTON_INFO) item).pressedBitmapX;
		((BUTTON_INFO) item).pressedBitmapX = value;

		firePropertyChange("", oldValue, value);
	}

	public int getPressedBitmapX() {
		return ((BUTTON_INFO) item).pressedBitmapX;
	}

	public void setPressedBitmapY(int value) {
		int oldValue = ((BUTTON_INFO) item).pressedBitmapY;
		((BUTTON_INFO) item).pressedBitmapY = value;

		firePropertyChange("", oldValue, value);
	}

	public int getPressedBitmapY() {
		return ((BUTTON_INFO) item).pressedBitmapY;
	}

	public void setDisabledBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((BUTTON_INFO) item).disabledBitmapPath == null
				|| ((BUTTON_INFO) item).disabledBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((BUTTON_INFO) item).disabledBitmapPath);
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
			((BUTTON_INFO) item).disabledBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((BUTTON_INFO) item).disabledBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_DISABLEDBITMAPPATH, oldValue,
				newValue);
	}

	public File getDisabledBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((BUTTON_INFO) item).disabledBitmapPath == null
				|| ((BUTTON_INFO) item).disabledBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((BUTTON_INFO) item).disabledBitmapPath);
		if (file.exists())
			return file;
		else {
			((BUTTON_INFO) item).disabledBitmapPath = "";
			return null;
		}
	}

	public void setDisabledBitmapX(int value) {
		int oldValue = ((BUTTON_INFO) item).disabledBitmapX;
		((BUTTON_INFO) item).disabledBitmapX = value;

		firePropertyChange("", oldValue, value);
	}

	public int getDisabledBitmapX() {
		return ((BUTTON_INFO) item).disabledBitmapX;
	}

	public void setDisabledBitmapY(int value) {
		int oldValue = ((BUTTON_INFO) item).disabledBitmapY;
		((BUTTON_INFO) item).disabledBitmapY = value;

		firePropertyChange("", oldValue, value);
	}

	public int getDisabledBitmapY() {
		return ((BUTTON_INFO) item).disabledBitmapY;
	}

	public void setNormalBGBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((BUTTON_INFO) item).normalBGBitmapPath == null
				|| ((BUTTON_INFO) item).normalBGBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((BUTTON_INFO) item).normalBGBitmapPath);
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
			((BUTTON_INFO) item).normalBGBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((BUTTON_INFO) item).normalBGBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_NORMALBGBITMAPPATH,
				oldValue, newValue);
	}

	public File getNormalBGBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((BUTTON_INFO) item).normalBGBitmapPath == null
				|| ((BUTTON_INFO) item).normalBGBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((BUTTON_INFO) item).normalBGBitmapPath);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((BUTTON_INFO) item).normalBGBitmapPath;
			((BUTTON_INFO) item).normalBGBitmapPath = "";
	        firePropertyChange(PROPERTY_NORMALBGBITMAPPATH, oldValue, "");
			return null;
		}
	}

	public void setPressedBGBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((BUTTON_INFO) item).pressedBGBitmapPath == null
				|| ((BUTTON_INFO) item).pressedBGBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path
					+ ((BUTTON_INFO) item).pressedBGBitmapPath);
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
			((BUTTON_INFO) item).pressedBGBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((BUTTON_INFO) item).pressedBGBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_PRESSEDBGBITMAPPATH, oldValue,
				newValue);
	}

	public File getPressedBGBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((BUTTON_INFO) item).pressedBGBitmapPath == null
				|| ((BUTTON_INFO) item).pressedBGBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((BUTTON_INFO) item).pressedBGBitmapPath);
		if (file.exists())
			return file;
		else {
			((BUTTON_INFO) item).pressedBGBitmapPath = "";
			return null;
		}
	}

	public BUTTON_INFO getItem() {
		return (BUTTON_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Button button = new Button(getParent().getDocuments(), mode);
		button.parent = getParent();
		button.setCopyItem((BUTTON_INFO) item);

		return button;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}
