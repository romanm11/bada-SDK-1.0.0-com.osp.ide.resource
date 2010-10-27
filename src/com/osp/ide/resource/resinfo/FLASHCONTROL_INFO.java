package com.osp.ide.resource.resinfo;

public class FLASHCONTROL_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String repeatMode = cszRepeatMode[0];
	public String localFilePath = "";
	public String urlFilePath ="";
	public String quality = cszQuality[0];
	
	public FLASHCONTROL_INFO clone() {
		FLASHCONTROL_INFO info = new FLASHCONTROL_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.repeatMode = repeatMode;
		info.localFilePath = localFilePath;
		info.urlFilePath = urlFilePath;
		info.quality = quality;
		copyLayout(info);
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		FLASHCONTROL_INFO info = (FLASHCONTROL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.repeatMode = repeatMode;
		info.localFilePath = localFilePath;
		info.urlFilePath = urlFilePath;
		info.quality = quality;
		copyLayout(info);
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		FLASHCONTROL_INFO info = (FLASHCONTROL_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.repeatMode = repeatMode;
		info.urlFilePath = urlFilePath;
		info.quality = quality;
	}
}
