package com.osp.ide.resource.common;

import java.awt.Color;
import java.util.Vector;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

import com.osp.ide.resource.Activator;

public class Image_info {

	Vector<String> name = new Vector<String>();
	Vector<Color> bgColor = new Vector<Color>();
	Vector<Float> opacity = new Vector<Float>();
	Point size = new Point();
	int spaceW;
	int spaceH;
	boolean fix = false;
	boolean vAlign = false;
	boolean hAlign = false;
	boolean left = false;
	boolean right = false;
	boolean top = false;
	boolean bottom = false;

	public String getName(int index) {
		return name.elementAt(index);
	}

	public void setName(int index, String name) {
		try {
			this.name.remove(index);
		} catch (Exception e) {
			Activator.setErrorMessage("Image_info.setName()", e.getClass()
					+ " - " + e.getMessage(), e);
		}

		this.name.add(index, name);
	}

	public void removeName(String fileName) {
		name.remove(fileName);
	}

	public Color getColor(int index) {
		return bgColor.elementAt(index);
	}

	public void setColor(int index, Color bgColor) {
		try {
			this.bgColor.remove(index);
		} catch (Exception e) {
			Activator.setErrorMessage("Image_info.setColor()", e.getClass()
					+ " - " + e.getMessage(), e);
		}

		this.bgColor.add(index, bgColor);
	}

	public float getOpacity(int index) {
		return opacity.elementAt(index);
	}

	public void setOpacity(int index, float opacity) {
		try {
			this.opacity.remove(index);
		} catch (Exception e) {
			Activator.setErrorMessage("Image_info.setOpacity()", e.getClass()
					+ " - " + e.getMessage(), e);
		}

		this.opacity.add(index, opacity);
	}

	public int size() {
		return name.size();
	}

	public Dimension getSize() {
		return new Dimension(size.x, size.y);
	}

	public void setSize(Point size) {
		this.size = size;
	}

	public void setSize(int width, int height) {
		size.x = width;
		size.y = height;
	}

	public void setSizeX(int width) {
		size.x = width;
	}

	public void setSizeY(int height) {
		size.y = height;
	}

	public int getSpaceW() {
		return spaceW;
	}

	public void setSpaceW(int space) {
		spaceW = space;
	}

	public int getSpaceH() {
		return spaceH;
	}

	public void setSpaceH(int space) {
		spaceH = space;
	}

	public boolean isFix() {
		return fix;
	}

	public void setFix(boolean isfix) {
		fix = isfix;
	}

	public boolean isHAlign() {
		return hAlign;
	}

	public void setHAlign(boolean isHAlign) {
		hAlign = isHAlign;
	}

	public boolean isTop() {
		return top;
	}

	public void setTop(boolean isTop) {
		top = isTop;
	}

	public boolean isBottom() {
		return bottom;
	}

	public void setBottom(boolean isBottom) {
		bottom = isBottom;
	}

	public boolean isVAlign() {
		return vAlign;
	}

	public void setVAlign(boolean isVAlign) {
		vAlign = isVAlign;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean isLeft) {
		left = isLeft;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean isRight) {
		right = isRight;
	}

	public Image_info getCopy() {
		Image_info copyInfo = new Image_info();

		for (int i = 0; i < name.size(); i++)
			copyInfo.name.add(name.get(i));

		for (int i = 0; i < bgColor.size(); i++) {
			Color color = bgColor.get(i);
			copyInfo.bgColor.add(new Color(color.getRGB()));
		}

		for (int i = 0; i < opacity.size(); i++) {
			copyInfo.opacity.add(opacity.get(i));
		}
		copyInfo.size = new Point(size);
		copyInfo.spaceW = spaceW;
		copyInfo.spaceH = spaceH;
		copyInfo.fix = fix;
		copyInfo.vAlign = vAlign;
		copyInfo.hAlign = hAlign;
		copyInfo.left = left;
		copyInfo.right = right;
		copyInfo.top = top;
		copyInfo.bottom = bottom;

		return copyInfo;
	}

}
