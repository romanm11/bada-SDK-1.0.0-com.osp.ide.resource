package com.osp.ide.resource.resinfo;

import java.util.Enumeration;
import java.util.Hashtable;

import com.osp.ide.resource.resourceexplorer.ResourceExplorer;


public class PANELFRAME_INFO extends ITEM_INFO implements FrameConst {
	public String itemText = "";
	public String title = "";
	public String bgColor = DEFAULT_COLOR;
	public Hashtable<String, Integer> parent = new Hashtable<String, Integer>();
	public int bgColorOpacity = 0;
    public String formColor = DEFAULT_COLOR;

	public PANELFRAME_INFO clone() {
		PANELFRAME_INFO info = new PANELFRAME_INFO();
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
		return info;
	}

	@Override
	public void copy(ITEM_INFO itemInfo) {
		PANELFRAME_INFO info = (PANELFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
	}

	public void addParentId(String id) {
		Integer count;
		if ((count = parent.get(id)) == null) {
			parent.put(id, 1);
		} else {
			count ++;
			parent.remove(id);
			parent.put(id, count);
		}
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null)
            view.refreshTree();
	}

	public void removeParentId(String id) {
		Integer count;
		if ((count = parent.get(id)) != null) {
			count --;
			parent.remove(id);
			parent.put(id, count);
			if(count.equals(0))
				parent.remove(id);
		}
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null)
            view.refreshTree();
	}
	
	@Override
	public void updateCopy(ITEM_INFO itemInfo) {
		PANELFRAME_INFO info = (PANELFRAME_INFO) itemInfo;
		info.Id = Id;
		info.type = type;
		info.pID = pID;
		info.children = children;
		info.itemText = itemText;
		info.bgColor = bgColor;
		info.bgColorOpacity = bgColorOpacity;
	}

    /**
     * 
     */
    public void removeAllParent() {
        Enumeration<String> keys = parent.keys();
        while(keys.hasMoreElements()) {
            parent.remove(keys.nextElement());
        }
    }
}
