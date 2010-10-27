package com.osp.ide.resource.resinfo;

import org.eclipse.draw2d.geometry.Rectangle;

public class Layout 
{
	public String mode="";
	public String style="";
	public int x;
	public int y;
	public int width;
	public int height;
	public String dock="";
	public String fit="";
	public int maxDropLineCount;
	public int BGColorTransparency;
	
	public Layout() {
	}
	public Layout(Layout rect) {
		if(rect == null)
			rect = new Layout();
		dock = rect.dock;
		fit = rect.fit;
		height = rect.height;
		maxDropLineCount = rect.maxDropLineCount;
		mode = rect.mode;
		style = rect.style;

		
		width = rect.width;
		x = rect.x;
		y = rect.y;
	}
	public Layout(int mode, Rectangle rect) {
		this.mode = FrameConst.cszFrmMode[mode];
		height = rect.height;
		width = rect.width;
		x = rect.x;
		y = rect.y;
	}
	public Layout(String mode, Rectangle rect) {
		this.mode = mode;
		height = rect.height;
		width = rect.width;
		x = rect.x;
		y = rect.y;
	}
	public Layout copy() {
		Layout copyLayout = new Layout(this);
		return copyLayout;
	}
    /**
     * @return
     */
    public boolean isSmall() {
        if(style == null || style.indexOf("SMALL") < 0)
            return false;
        else
            return true;
    }
}
