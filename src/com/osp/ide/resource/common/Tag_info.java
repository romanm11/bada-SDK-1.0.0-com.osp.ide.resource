package com.osp.ide.resource.common;

import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Point;

public class Tag_info {
    public String screen = "";
	public Point dftlSize = new Point();
	public Point minSize = new Point();
	public int textlMargin;
	public int textrMargin;
	public int tSize;
	public String temp1 = "";
	public String temp2 = "";
	Vector<Image_info> info = new Vector<Image_info>();
	
	public Tag_info getCopy() {
		Tag_info info = new Tag_info();
		info.screen = screen;
		info.dftlSize = dftlSize.getCopy();
		info.minSize = minSize.getCopy();
		info.textlMargin = textlMargin;
		info.textrMargin = textrMargin;
		info.tSize = tSize;
		info.temp1 = temp1;
		info.temp2 = temp2;
		
		return info;
	}

	public Tag_info getClone() {
		Tag_info info = getCopy();
		
		for(int i=0; i< this.info.size(); i++) {
			info.add(this.info.get(i).getCopy());
		}
		
		return info;
	}
	public void add(Image_info image) {
		info.add(image);
	}

	public int size() {
		return info.size();
	}

	public Image_info elementAt(int n) {
		return info.elementAt(n);
	}

	public void add(int i, Image_info imageInfo) {
		info.add(i, imageInfo);
	}

	public void remove(int i) {
		info.remove(i);
	}

	public Image_info get(int i) {
		return info.get(i);
	}

	public List<Image_info> subList(int i, int j) {
		return info.subList(i, j);
	}
}
