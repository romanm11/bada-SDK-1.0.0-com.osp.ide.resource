package com.osp.ide.resource.model.property;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.EditableComboPropertyDescriptor;
import com.osp.ide.resource.common.FileDialogCellEditor;
import com.osp.ide.resource.common.FilePropertyDescriptor;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.BUTTON_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class ButtonPropertySource extends OspNodePropertySource {

	public ButtonPropertySource(Button node) {
		this.node = node;

		BUTTON_INFO info = node.getItem().clone();
		this.info = info;

		if (this.info != null) {
			int mode = node.getModeIndex();
			Layout rect = node.getLayout(mode);
			Layout newLayout = new Layout(rect);
			this.info.SetLayout(newLayout);
			if (mode == PORTRAIT) {
				rect = node.getLayout(LANDCAPE);
			} else {
				rect = node.getLayout(PORTRAIT);
			}
			newLayout = new Layout(rect);
			this.info.SetLayout(newLayout);
		}

	}

	protected void initDescriptor() {
		properties = new ArrayList<IPropertyDescriptor>();

		initStringId();
		initControlId();

		PropertyDescriptor HalignDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_HALIGN, "Text Horizontal Align", cszHAlign));
		HalignDescriptor.setCategory(FrameNode.GROUP_STYLE);
		properties.add(HalignDescriptor);

		PropertyDescriptor ValignDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_VALIGN, "Text Vertical Align", cszVAlign));
		ValignDescriptor.setCategory(FrameNode.GROUP_STYLE);
		properties.add(ValignDescriptor);
		
        PropertyDescriptor HeightDescriptor = (new TextPropertyDescriptor(
                FrameNode.PROPERTY_HEIGHT, "Height"));
        HeightDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(HeightDescriptor);

        PropertyDescriptor ParentDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_PARENT, "Parent ID", controlId));
        ParentDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(ParentDescriptor);
    
        PropertyDescriptor WidthDescriptor = (new TextPropertyDescriptor(
                FrameNode.PROPERTY_WIDTH, "Width"));
        WidthDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(WidthDescriptor);
    
        PropertyDescriptor PointXlDescriptor = (new TextPropertyDescriptor(
                FrameNode.PROPERTY_POINTX, "X Position"));
        PointXlDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointXlDescriptor);
    
        PropertyDescriptor PointYDescriptor = (new TextPropertyDescriptor(
                FrameNode.PROPERTY_POINTY, "Y Position"));
        PointYDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointYDescriptor);
    
		PropertyDescriptor dFgColorDescriptor = (new ResourceColorPropertyDescriptor(
				FrameNode.PROPERTY_DTEXTCOLOR, "Disable Text Color"));
		dFgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(dFgColorDescriptor);

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

        PropertyDescriptor NormalBGBitmapPathDescriptor = new FilePropertyDescriptor(
                FrameNode.PROPERTY_NORMALBGBITMAPPATH,
                "Normal Background Bitmap Path");
        NormalBGBitmapPathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(NormalBGBitmapPathDescriptor);

		PropertyDescriptor nFgColorDescriptor = (new ResourceColorPropertyDescriptor(
				FrameNode.PROPERTY_NTEXTCOLOR, "Normal Text Color"));
		nFgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(nFgColorDescriptor);

        PropertyDescriptor PressedBGBitmapPathDescriptor = new FilePropertyDescriptor(
                FrameNode.PROPERTY_PRESSEDBGBITMAPPATH,
                "Pressed Background Bitmap Path");
        PressedBGBitmapPathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(PressedBGBitmapPathDescriptor);

		PropertyDescriptor pFgColorDescriptor = (new ResourceColorPropertyDescriptor(
				FrameNode.PROPERTY_PTEXTCOLOR, "Pressed Text Color"));
		pFgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(pFgColorDescriptor);

		PropertyDescriptor TextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TEXT, "Text", stringId));
		TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		Button node = (Button) this.node;

		int minY = getMinY(node);
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_POINTX))
			return Integer.toString(node.getLayout().x);
		else if (id.equals(FrameNode.PROPERTY_POINTY))
			return Integer.toString(node.getLayout().y - minY);
		else if (id.equals(FrameNode.PROPERTY_WIDTH))
			return Integer.toString(node.getLayout().width);
		else if (id.equals(FrameNode.PROPERTY_HEIGHT))
			return Integer.toString(node.getLayout().height);
		else if (id.equals(FrameNode.PROPERTY_TYPE))
			return "WINDOW_" + cszCtlType[node.getType()];
		else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			text = node.getText();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_TEXTDIRECTION))
			return node.getTextDirection();
		else if (id.equals(FrameNode.PROPERTY_HALIGN))
			return node.getHAlign();
		else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
			return node.getVAlign();
		} else if (id.equals(FrameNode.PROPERTY_NBGCOLOR)) {
			if (node.getNormalBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getNormalBGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_NTEXTCOLOR)) {
			if (node.getNormalFGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getNormalFGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_PBGCOLOR)) {
			if (node.getPressedBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getPressedBGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_PTEXTCOLOR)) {
			if (node.getPressedFGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getPressedFGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_DBGCOLOR)) {
			if (node.getDisableBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getDisableBGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_DTEXTCOLOR)) {
			if (node.getDisableFGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getDisableFGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPPATH)) {
			File file = node.getNormalBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPX))
			return Integer.toString(node.getNormalBitmapX());
		else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPY))
			return Integer.toString(node.getNormalBitmapY());
		else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPPATH)) {
			File file = node.getPressedBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPX))
			return Integer.toString(node.getPressedBitmapX());
		else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPY))
			return Integer.toString(node.getPressedBitmapY());
		else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPPATH)) {
			File file = node.getDisabledBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPX))
			return Integer.toString(node.getDisabledBitmapX());
		else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPY))
			return Integer.toString(node.getDisabledBitmapY());
		else if (id.equals(FrameNode.PROPERTY_NORMALBGBITMAPPATH)) {
			File file = node.getNormalBGBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBGBITMAPPATH)) {
			File file = node.getPressedBGBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}
		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		BUTTON_INFO info = (BUTTON_INFO) this.info;
		Button node = (Button) this.node;

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName(info.Id);
		} else if (id.equals(FrameNode.PROPERTY_TYPE)) {
			node.setType(info.type);
		} else if (id.equals(FrameNode.PROPERTY_PARENT)) {
			node.setParentId(info.pID);
		} else if (id.equals(FrameNode.PROPERTY_POINTX)) {
			Layout rect = node.getLayout();
			rect.x = info.GetLayout(node.getModeIndex()).x;
			node.setLayout(rect);
		} else if (id.equals(FrameNode.PROPERTY_POINTY)) {
			Layout rect = node.getLayout();
			rect.y = info.GetLayout(node.getModeIndex()).y;
			node.setLayout(rect);
		} else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
			Layout rect = node.getLayout();
			rect.width = info.GetLayout(node.getModeIndex()).width;
			node.setLayout(rect);
		} else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
			Layout rect = node.getLayout();
			rect.height = info.GetLayout(node.getModeIndex()).height;
			node.setLayout(rect);
		} else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			node.setText(info.text);
		} else if (id.equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			node.setTextDirection(info.textDirection);
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			node.setHAlign(info.hAlign);
		} else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
			node.setVAlign(info.vAlign);
		} else if (id.equals(FrameNode.PROPERTY_NBGCOLOR)) {
			node.setNormalBGColor(info.normalBGColor);
		} else if (id.equals(FrameNode.PROPERTY_NTEXTCOLOR)) {
			node.setNormalFGColor(info.normalFGColor);
		} else if (id.equals(FrameNode.PROPERTY_PBGCOLOR)) {
			node.setPressedBGColor(info.pressedBGColor);
		} else if (id.equals(FrameNode.PROPERTY_PTEXTCOLOR)) {
			node.setPressedFGColor(info.pressedFGColor);
		} else if (id.equals(FrameNode.PROPERTY_DBGCOLOR)) {
			node.setDisableBGColor(info.disableBGColor);
		} else if (id.equals(FrameNode.PROPERTY_DTEXTCOLOR)) {
			node.setDisableFGColor(info.disableFGColor);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.normalBitmapPath == null
					|| info.normalBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.normalBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setNormalBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPX)) {
			node.setNormalBitmapX(info.normalBitmapX);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPY)) {
			node.setNormalBitmapY(info.normalBitmapY);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.pressedBitmapPath == null
					|| info.pressedBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.pressedBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setPressedBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPX)) {
			node.setPressedBitmapX(info.pressedBitmapX);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPY)) {
			node.setPressedBitmapY(info.pressedBitmapY);
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.disabledBitmapPath == null
					|| info.disabledBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.disabledBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setDisabledBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPX)) {
			node.setDisabledBitmapX(info.disabledBitmapX);
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPY)) {
			node.setDisabledBitmapY(info.disabledBitmapY);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBGBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.normalBGBitmapPath == null
					|| info.normalBGBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.normalBGBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setNormalBGBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBGBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.pressedBGBitmapPath == null
					|| info.pressedBGBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.pressedBGBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setPressedBGBitmapPath(file);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;
		
		Button node = (Button) this.node;

		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName((String) value);
		} else if (id.equals(FrameNode.PROPERTY_PARENT)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setParentId(controlId[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_POINTX)) {
			try {
				Layout rect = node.getLayout();
				Integer pointx = Integer.parseInt((String) value);
				rect.x = pointx;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_POINTY)) {
			try {
				Layout rect = node.getLayout();
				Integer pointy = Integer.parseInt((String) value);
				int minY = getMinY(node);
				rect.y = pointy + minY;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
			try {
				Layout rect = node.getLayout();
				Integer pointwidth = Integer.parseInt((String) value);
				rect.width = pointwidth;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
			try {
				Layout rect = node.getLayout();
				Integer pointheight = Integer.parseInt((String) value);
				rect.height = pointheight;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setText(text);
		} else if (id.equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setTextDirection(cszTextDirection[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setHAlign(cszHAlign[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setVAlign(cszVAlign[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_NBGCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setNormalBGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_NTEXTCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setNormalFGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_PBGCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setPressedBGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_PTEXTCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setPressedFGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_DBGCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setDisableBGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_DTEXTCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setDisableFGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPPATH)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.getParent().GetProjectDirectory();
				fileName = path + FrameNode.BITMAP_FOLDER + fileName;
			}
			
			File file = new File(fileName);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			else {
				Image userImage;
				try {
					userImage = new Image(null, file.getAbsolutePath());
					userImage.dispose();
					userImage = null;
				} catch (SWTException e) {
					Activator.setErrorMessage(
							"ButtonPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setNormalBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPX)) {
			int x = 0;
			try {
				x = Integer.parseInt((String) value);

				if (x > node.getLayout().width)
					return;

				node.setNormalBitmapX(x);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_NORMALBITMAPY)) {
			int y = 0;
			try {
				y = Integer.parseInt((String) value);

				if (y > node.getLayout().height)
					return;

				node.setNormalBitmapY(y);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPPATH)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.getParent().GetProjectDirectory();
				fileName = path + FrameNode.BITMAP_FOLDER + fileName;
			}
			
			File file = new File(fileName);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			else {
				Image userImage;
				try {
					userImage = new Image(null, file.getAbsolutePath());
					userImage.dispose();
					userImage = null;
				} catch (SWTException e) {
					Activator.setErrorMessage(
							"ButtonPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setPressedBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPX)) {
			int x = 0;
			try {
				x = Integer.parseInt((String) value);
				node.setPressedBitmapX(x);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBITMAPY)) {
			int y = 0;
			try {
				y = Integer.parseInt((String) value);
				node.setPressedBitmapY(y);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPPATH)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.getParent().GetProjectDirectory();
				fileName = path + FrameNode.BITMAP_FOLDER + fileName;
			}
			
			File file = new File(fileName);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			else {
				Image userImage;
				try {
					userImage = new Image(null, file.getAbsolutePath());
					userImage.dispose();
					userImage = null;
				} catch (SWTException e) {
					Activator.setErrorMessage(
							"ButtonPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setDisabledBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPX)) {
			int x = 0;
			try {
				x = Integer.parseInt((String) value);
				node.setDisabledBitmapX(x);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_DISABLEDBITMAPY)) {
			int y = 0;
			try {
				y = Integer.parseInt((String) value);
				node.setDisabledBitmapY(y);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ButtonPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_NORMALBGBITMAPPATH)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.getParent().GetProjectDirectory();
				fileName = path + FrameNode.BITMAP_FOLDER + fileName;
			}
			
			File file = new File(fileName);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			else {
				Image userImage;
				try {
					userImage = new Image(null, file.getAbsolutePath());
					userImage.dispose();
					userImage = null;
				} catch (SWTException e) {
					Activator.setErrorMessage(
							"ButtonPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setNormalBGBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_PRESSEDBGBITMAPPATH)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.getParent().GetProjectDirectory();
				fileName = path + FrameNode.BITMAP_FOLDER + fileName;
			}
			
			File file = new File(fileName);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			else {
				Image userImage;
				try {
					userImage = new Image(null, file.getAbsolutePath());
					userImage.dispose();
					userImage = null;
				} catch (SWTException e) {
					Activator.setErrorMessage(
							"ButtonPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setPressedBGBitmapPath(file);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
        }
	}

}
