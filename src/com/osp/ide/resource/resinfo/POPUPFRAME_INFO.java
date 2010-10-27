package com.osp.ide.resource.resinfo;

public class POPUPFRAME_INFO extends ITEM_INFO implements FrameConst {
	public String fileName = "";
	public String headPath = "";

	public String titleIcon = "";
	public String bitmap = "";
	public String oldId = "";
	
	public POPUPFRAME_INFO clone() {
		POPUPFRAME_INFO info = new POPUPFRAME_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.fileName = fileName;
		info.headPath = headPath;
		info.children = children;
		info.titleText = titleText;
		info.titleIcon = titleIcon;
		info.bitmap = bitmap;
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		POPUPFRAME_INFO info = (POPUPFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.fileName = fileName;
		info.headPath = headPath;
		info.children = children;
		info.titleText = titleText;
		info.titleIcon = titleIcon;
		info.bitmap = bitmap;
	}

	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		POPUPFRAME_INFO info = (POPUPFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.titleText = titleText;
	}
}
