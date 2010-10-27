package com.osp.ide.resource.model;

import java.io.File;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SLIDER_INFO;
import com.osp.ide.resource.resourceexplorer.TreeObject;

public class Slider extends FrameNode {

	public static final int SLIDER_DEFAULT_WIDTH = 200;
	public static final int SLIDER_DEFAULT_HEIGHT = 54;
	public static final int SLIDER_MIN_WIDTH = 100;
	public static final int SLIDER_MIN_HEIGHT = 81;

	public Slider() {
		super();
		item = new SLIDER_INFO();
	}

	public Slider(String name, SLIDER_INFO item) {
		super(name, item);
	}

	public Slider(Object scene, int mode) {
		super(scene, mode);
		item = new SLIDER_INFO();
	}

	public void setItem(SLIDER_INFO item) {
		this.item = item;
	}

	public void setValue(int value) {
		if(value < ((SLIDER_INFO) item).min 
				|| value > ((SLIDER_INFO) item).max)
			return;	
		
		int oldValue = ((SLIDER_INFO) item).value;
		((SLIDER_INFO) item).value = value;

		firePropertyChange(PROPERTY_VALUE, oldValue,
				((SLIDER_INFO) item).value);
	}

	public String getValue() {
		return Integer.toString(((SLIDER_INFO) item).value);
	}

	public void setMin(int min) {
		int oldValue = ((SLIDER_INFO) item).min;
		((SLIDER_INFO) item).min = min;

		firePropertyChange(PROPERTY_MIN, oldValue,
				((SLIDER_INFO) item).min);
	}

	public String getMin() {
		return Integer.toString(((SLIDER_INFO) item).min);
	}

	public void setMax(int max) {
		int oldValue = ((SLIDER_INFO) item).max;
		((SLIDER_INFO) item).max = max;

		firePropertyChange(PROPERTY_MAX, oldValue,
				((SLIDER_INFO) item).max);
	}

	public String getMax() {
		return Integer.toString(((SLIDER_INFO) item).max);
	}

	public void setBGColor(String bgColor) {
		String oldColor = ((SLIDER_INFO) item).bgColor;
		((SLIDER_INFO) item).bgColor = bgColor;

		firePropertyChange(PROPERTY_BGCOLOR, oldColor, bgColor);
	}

	public String getBGColor() {
		return ((SLIDER_INFO) item).bgColor;
	}

	public void setStepSize(int stepSize) {
		int oldValue = ((SLIDER_INFO) item).stepSize;
		((SLIDER_INFO) item).stepSize = stepSize;

		firePropertyChange(PROPERTY_STEPSIZE, oldValue,
				((SLIDER_INFO) item).stepSize);
	}

	public String getStepSize() {
		return Integer.toString(((SLIDER_INFO) item).stepSize);
	}

	public void setLeftIconBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((SLIDER_INFO) item).leftIconBitmapPath == null
				|| ((SLIDER_INFO) item).leftIconBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((SLIDER_INFO) item).leftIconBitmapPath);
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
			((SLIDER_INFO) item).leftIconBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((SLIDER_INFO) item).leftIconBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_LEFTICONBITMAPPATH,
				oldValue, newValue);
	}

	public File getLeftIconBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((SLIDER_INFO) item).leftIconBitmapPath == null
				|| ((SLIDER_INFO) item).leftIconBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((SLIDER_INFO) item).leftIconBitmapPath);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((SLIDER_INFO) item).leftIconBitmapPath;
			((SLIDER_INFO) item).leftIconBitmapPath = "";
	        firePropertyChange(PROPERTY_LEFTICONBITMAPPATH, oldValue, "");
			return null;
		}
	}

	public void setRightIconBitmapPath(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((SLIDER_INFO) item).rightIconBitmapPath == null
				|| ((SLIDER_INFO) item).rightIconBitmapPath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path
					+ ((SLIDER_INFO) item).rightIconBitmapPath);
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
			((SLIDER_INFO) item).rightIconBitmapPath = "";
		} else {
			newValue = newFile.getName();
			((SLIDER_INFO) item).rightIconBitmapPath = BITMAP_FOLDER
					+ newFile.getName();
		}

		firePropertyChange(PROPERTY_RIGHTICONBITMAPPATH,
				oldValue, newValue);
	}

	public File getRightIconBitmapPath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((SLIDER_INFO) item).rightIconBitmapPath == null
				|| ((SLIDER_INFO) item).rightIconBitmapPath.isEmpty())
			return null;
		File file = new File(path + ((SLIDER_INFO) item).rightIconBitmapPath);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((SLIDER_INFO) item).rightIconBitmapPath;
			((SLIDER_INFO) item).rightIconBitmapPath = "";
	        firePropertyChange(PROPERTY_RIGHTICONBITMAPPATH, oldValue, "");
			return null;
		}
	}

    public void setTitleTextColor(String textColor) {
        String oldColor = ((SLIDER_INFO) item).titleTextColor;
        ((SLIDER_INFO) item).titleTextColor = textColor;

        firePropertyChange(PROPERTY_TITLETEXTCOLOR, oldColor,
            textColor);
    }

    /**
     * @return
     */
    public String getTitleTextColor() {
        return ((SLIDER_INFO) item).titleTextColor;
    }
    
	public void setMinText(String minText) {
		String oldText = ((SLIDER_INFO) item).minText;
		((SLIDER_INFO) item).minText = minText;

		firePropertyChange(PROPERTY_MINTEXT, oldText, minText);
	}

	public String getMinText() {
		return ((SLIDER_INFO) item).minText;
	}

	public void setMaxText(String maxText) {
		String oldText = ((SLIDER_INFO) item).maxText;
		((SLIDER_INFO) item).maxText = maxText;

		firePropertyChange(PROPERTY_MAXTEXT, oldText, maxText);
	}

	public String getMaxText() {
		return ((SLIDER_INFO) item).maxText;
	}

	public SLIDER_INFO getItem() {
		return (SLIDER_INFO) this.item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Slider slide = new Slider(getParent().getDocuments(), mode);
		slide.parent = getParent();
		slide.setCopyItem((SLIDER_INFO) this.item);

		return slide;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}
