package com.osp.ide.resource.model;

import java.io.File;

import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.FLASHCONTROL_INFO;
import com.osp.ide.resource.resourceexplorer.TreeObject;

public class Flash extends FrameNode {

	public static final int FLASHCONTROL_DEFAULT_WIDTH = 200;
	public static final int FLASHCONTROL_DEFAULT_HEIGHT = 62;
	public static final int FLASHCONTROL_MIN_WIDTH = 81;
	public static final int FLASHCONTROL_MIN_HEIGHT = 81;

	public Flash() {
		super();
		item = new FLASHCONTROL_INFO();
	}

	public Flash(String name, FLASHCONTROL_INFO item) {
		super(name, item);
	}

	public Flash(Object scene, int mode) {
		super(scene, mode);
		item = new FLASHCONTROL_INFO();
	}

	public void setItem(FLASHCONTROL_INFO item) {
		this.item = item;
	}

	public void setItemText(String itemText) {
		String oldText = ((FLASHCONTROL_INFO) item).itemText;
		((FLASHCONTROL_INFO) item).itemText = itemText;

		firePropertyChange(PROPERTY_ITEMTEXT, oldText, itemText);
	}

	public String getItemText() {
		return ((FLASHCONTROL_INFO) item).itemText;
	}

	public FLASHCONTROL_INFO getItem() {
		return (FLASHCONTROL_INFO) this.item;
	}

	public File getLocalFilePath() {
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FLASHCONTROL_INFO) item).localFilePath == null
				|| ((FLASHCONTROL_INFO) item).localFilePath.isEmpty())
			return null;
		File file = new File(path + ((FLASHCONTROL_INFO) item).localFilePath);
		if (file.exists())
			return file;
		else {
			((FLASHCONTROL_INFO) item).localFilePath = "";
			return null;
		}
	}

	public void setLocalFilePath(File contentsPath) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = getParent().GetProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FLASHCONTROL_INFO) item).localFilePath == null
				|| ((FLASHCONTROL_INFO) item).localFilePath.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FLASHCONTROL_INFO) item).localFilePath);
			oldValue = oldFile.getName();
		}

		if (contentsPath != null && contentsPath.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + contentsPath.getName());
			TreeObject.copyFile(contentsPath, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FLASHCONTROL_INFO) item).localFilePath = "";
		} else {
			newValue = newFile.getName();
			((FLASHCONTROL_INFO) item).localFilePath = BITMAP_FOLDER
					+ newFile.getName();
		}
		
		firePropertyChange(PROPERTY_LOCALFILEPATH, oldValue,
				newValue);
	}
	
	public void setUrlFilePath(String path) {
		String oldText = ((FLASHCONTROL_INFO) item).urlFilePath;
		((FLASHCONTROL_INFO) item).urlFilePath = path;

		firePropertyChange(PROPERTY_URLFILEPATH, oldText, path);
	}
	
	public String getUrlFilePath() {
		return ((FLASHCONTROL_INFO) item).urlFilePath;
	}

	public int getQuality() {
		if (((FLASHCONTROL_INFO) this.item).quality
				.equals(cszQuality[FLASH_QUALITY_MEDIUM]))
			return FLASH_QUALITY_MEDIUM;
		else if (((FLASHCONTROL_INFO) this.item).quality
				.equals(cszQuality[FLASH_QUALITY_HIGH]))
			return FLASH_QUALITY_HIGH;
		else
			return FLASH_QUALITY_LOW;
	}
	
	public void setQuality(String quality) {
		String oldQuality = ((FLASHCONTROL_INFO) item).quality;
		((FLASHCONTROL_INFO) item).quality = quality;

		firePropertyChange(PROPERTY_REPEATMODE, oldQuality,
				quality);
	}

	public int getRepeatMode() {
		if (((FLASHCONTROL_INFO) this.item).repeatMode
				.equals(cszRepeatMode[FLASH_REPEAT_LOOP]))
			return FLASH_REPEAT_LOOP;

		return FLASH_REPEAT_NONE;
	}

	public void setRepeatMode(String repeatMode) {
		String oldMode = ((FLASHCONTROL_INFO) item).repeatMode;
		((FLASHCONTROL_INFO) item).repeatMode = repeatMode;

		firePropertyChange(PROPERTY_REPEATMODE, oldMode,
				repeatMode);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		int mode = getModeIndex();
		Flash flashcontrol = new Flash(
				getParent().getDocuments(), mode);
		flashcontrol.parent = getParent();
		flashcontrol.setCopyItem((FLASHCONTROL_INFO) this.item);

		return flashcontrol;
	}

	@Override
	public void setLayout(Layout newLayout) {

		newLayout = getSuitableLayout(newLayout);
		if(newLayout != null)
			super.setLayout(newLayout);
	}

}

