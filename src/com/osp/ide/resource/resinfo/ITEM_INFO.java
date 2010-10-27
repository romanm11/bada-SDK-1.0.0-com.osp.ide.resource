package com.osp.ide.resource.resinfo;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.draw2d.geometry.Rectangle;

public abstract class ITEM_INFO implements FrameConst
{// Form Item information
	public String Id = "";
	public Hashtable<String, Layout> layout = new Hashtable<String, Layout>();
	public int type;
	public String pID="";
	public String children="";
	public String BgStyle="";
	public String BorderStyle="";
	public String itemDivider = "TRUE";
	public String fastScroll = "TRUE";
	public String ShowTitleText = "FALSE";
    public String titleText = "";

	// 2010 04 22 insert ///////////
	public String groupStyle="GROUP_STYLE_NONE";
	////////////////////////////////
	
	public ITEM_INFO() {
	}
	
	public Rectangle GetRect(int index)
	{
		Layout ret = layout.get(cszFrmMode[index]);
		if(ret == null)
			ret =  new Layout();
		return new Rectangle(ret.x, ret.y, ret.width, ret.height);
	};
	public Rectangle GetRect(String mode)
	{
		Layout ret = layout.get(mode);
		if(ret == null)
			ret =  new Layout();
		return new Rectangle(ret.x, ret.y, ret.width, ret.height);
	};

	public Layout GetLayout(int index)
	{
		Layout ret = new Layout(layout.get(cszFrmMode[index]));
		if(ret == null)
			ret =  new Layout();
		return ret;
	};
	public Layout GetLayout(String mode)
	{
		Layout ret = new Layout(layout.get(mode));
		if(ret == null)
			ret =  new Layout();
		return ret;
	};
	
	public void SetRect(int index, Rectangle rect)
	{
		Layout layout = this.layout.get(cszFrmMode[index]);
		if(layout == null)
		{
			layout = new Layout();
			layout.mode = cszFrmMode[index];
			layout.x = rect.x; layout.y = rect.y; 
			layout.width = rect.width; layout.height = rect.height;
			this.layout.put(cszFrmMode[index], layout);
		}
		else
		{
			layout.x = rect.x; layout.y = rect.y; 
			layout.width = rect.width; layout.height = rect.height;
		}
	};
	public void SetRect(String mode, Rectangle rect)
	{
		Layout layout = this.layout.get(mode);
		if(layout == null)
		{
			layout = new Layout();
			layout.mode = mode;
			layout.x = rect.x; layout.y = rect.y; 
			layout.width = rect.width; layout.height = rect.height;
			this.layout.put(mode, layout);
		}
		else
		{
			layout.x = rect.x; layout.y = rect.y; 
			layout.width = rect.width; layout.height = rect.height;
		}
	};
	public void SetLayout(Layout newLayout)
	{
		if(layout.get(newLayout.mode) == null)
			layout.put(newLayout.mode, newLayout);
		else
		{
			layout.remove(newLayout.mode);
			layout.put(newLayout.mode, newLayout);
		}
	}
	public void copyLayout(ITEM_INFO info) {
		Enumeration<String> layoutKeys = this.layout.keys();
		while (layoutKeys.hasMoreElements()) {
			String key = layoutKeys.nextElement();
			Layout origin = this.layout
					.get(key);
			Layout layout = info.layout
					.remove(key);
			if(layout == null)
				layout = new Layout();
			layout.mode = origin.mode;
			layout.style = origin.style;
			layout.maxDropLineCount = origin.maxDropLineCount;
			info.layout.put(key, layout);
		}

	}

	public abstract ITEM_INFO clone();
	public abstract void copy(ITEM_INFO itemInfo);
	public abstract void updateCopy(ITEM_INFO itemInfo);
}
