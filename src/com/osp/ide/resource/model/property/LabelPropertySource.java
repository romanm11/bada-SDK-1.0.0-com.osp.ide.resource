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
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class LabelPropertySource extends OspNodePropertySource {

	public LabelPropertySource(Label node) {
		this.node = node;

		LABEL_INFO info = node.getItem().clone();
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
    
		PropertyDescriptor BgImageDescriptor = new FilePropertyDescriptor(
				FrameNode.PROPERTY_BGBITMAPPATH, "Background Bitmap Path");
		BgImageDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(BgImageDescriptor);

		PropertyDescriptor BgColorDescriptor = (new ResourceColorPropertyDescriptor(
				FrameNode.PROPERTY_NBGCOLOR, "Background Color"));
		BgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(BgColorDescriptor);

        if(node.isScotia()) {
	        PropertyDescriptor TransparencylDescriptor = 
	            (new PropertyDescriptor(FrameNode.PROPERTY_BGOPACITY, 
	                "Background Opacity [0% ~ 100%]"));
	        TransparencylDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
            TransparencylDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
	        properties.add(TransparencylDescriptor);
        } else {
	        PropertyDescriptor TransparencylDescriptor = 
	            (new TextPropertyDescriptor(FrameNode.PROPERTY_BGOPACITY, 
	                "Background Opacity [0% ~ 100%]"));
	        TransparencylDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
	        properties.add(TransparencylDescriptor);
        }

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

		PropertyDescriptor TextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TEXT, "Text", stringId));
		TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextDescriptor);

		PropertyDescriptor nFgColorDescriptor = (new ResourceColorPropertyDescriptor(
				FrameNode.PROPERTY_TEXTCOLOR, "Text Color"));
		nFgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(nFgColorDescriptor);

		PropertyDescriptor textSizeDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_TEXTSIZE, "Text Size"));
		textSizeDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(textSizeDescriptor);

		PropertyDescriptor TextStyleDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_TEXTSTYLE, "Text Style",
				cszStyle[STYLE_LABEL_TEXT]));
		TextStyleDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextStyleDescriptor);

		// properties.add(new FilePropertyDescriptor(
		// TWFrameNode.PROPERTY_CONTROLIMAGE, "Control Image"));

		// int style = TWFrameNode
		// .getStyleIndex(STYLE_LABEL, node.getLayout().style);
		// if (style > LABEL_STYLE_SCROLL) {
		// PropertyDescriptor TitleTextDescriptor = (new
		// EditableComboPropertyDescriptor(
		// TWFrameNode.PROPERTY_TITLETEXT, "Title Text", stringId));
		// TitleTextDescriptor.setCategory("Property");
		// properties.add(TitleTextDescriptor);
		// } else
		// ((TWLabel) node).setTitleText("");

		// PropertyDescriptor bShowStateDescriptor = (new
		// ComboBoxPropertyDescriptor(
		// TWFrameNode.PROPERTY_BBACKGROUNDTRANSPARENCY,
		// "BG Transparency", BOOL));
		// bShowStateDescriptor.setCategory("Property");
		// properties.add(bShowStateDescriptor);

		// PropertyDescriptor StyleDescriptor = (new ComboBoxPropertyDescriptor(
		// TWFrameNode.PROPERTY_STYLE, "Style", cszStyle[STYLE_LABEL]));
		// StyleDescriptor.setCategory("Layout");
		// properties.add(StyleDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		Label node = (Label) this.node;

        int minY = getMinY(node);
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			return FrameNode.getStyleIndex(STYLE_LABEL, node.getLayout().style);
		} else if (id.equals(FrameNode.PROPERTY_POINTX))
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
		} else if (id.equals(FrameNode.PROPERTY_BGBITMAPPATH)) {
			File file = node.getBGBitmap();

			if (file == null)
				return "";
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_NBGCOLOR)) {
			if (node.getBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getBGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_TEXTCOLOR)) {
			if (node.getTextColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getTextColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			return Integer.toString(node.getBGColorTransparency());
		} else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
			return Integer.toString(node.getTextSize());
		} else if (id.equals(FrameNode.PROPERTY_TEXTSTYLE)) {
			return FrameNode.getStyleIndex(STYLE_LABEL_TEXT, node
					.getTextStyle());
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			text = node.getTitleText();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_HALIGN))
			return node.getHAlign();
		else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
			return node.getVAlign();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		LABEL_INFO info = (LABEL_INFO) this.info;
		Label node = (Label) this.node;

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
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			node.setStyle(info.GetLayout(node.getModeIndex()).style);
		} else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			node.setText(info.text);
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			node.setHAlign(info.hAlign);
		} else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
			node.setVAlign(info.vAlign);
		} else if (id.equals(FrameNode.PROPERTY_NBGCOLOR)) {
			node.setBGColor(info.bgColor);
		} else if (id.equals(FrameNode.PROPERTY_TEXTCOLOR)) {
			node.setTextColor(info.textColor);
		} else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
			node.setTextSize(info.textSize);
		} else if (id.equals(FrameNode.PROPERTY_TEXTSTYLE)) {
			node.setTextStyle(info.textStyle);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			node.setTitleText(info.titleText);
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			node.setBGColorTransparency(info.bgColorOpacity);
		} else if (id.equals(FrameNode.PROPERTY_BGBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.bgBitmap == null || info.bgBitmap.isEmpty())
				file = null;
			else {
				file = new File(path + info.bgBitmap);
				if (!file.exists())
					file = null;
			}

			node.setBGBitmap(file);
			// } else if (id.equals(TWFrameNode.PROPERTY_CONTROLIMAGE)) {
			// File file = new File(controlImage);
			// String name = file.getName();
			// if (name.equals(FileDialogCellEditor.NONE) || name.isEmpty())
			// file = null;
			// node.setImage(file);
		}

	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Label node = (Label) this.node;

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
						"LabelPropertySource.setPropertyValue()", id
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
						"LabelPropertySource.setPropertyValue()", id
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
						"LabelPropertySource.setPropertyValue()", id
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
						"LabelPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_LABEL][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setText(text);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setTitleText(text);
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

			node.setBGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_TEXTCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setTextColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
			try {
				Integer textSize = Integer.parseInt((String) value);
//				if (textSize >= 9 && textSize <= 400)
//					node.setTextSize(textSize);
//				else if (textSize < 9) {
//					textSize = 9;
//					node.setTextSize(textSize);
//				} else if (textSize > 400) {
//					textSize = 400;
//					node.setTextSize(textSize);
//				}
				if(textSize < 9)
				    textSize = 9;
				node.setTextSize(textSize);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"LabelPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_TEXTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_LABEL_TEXT][(Integer) value];
			node.setTextStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			try {
				Integer Transparency = Integer.parseInt((String) value);
				if (Transparency >= 0 && Transparency <= 100)
					node.setBGColorTransparency(Transparency);
				else if (Transparency < 0) {
					Transparency = 0;
					node.setBGColorTransparency(Transparency);
				} else if (Transparency > 100) {
					Transparency = 100;
					node.setBGColorTransparency(Transparency);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"LabelPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_BGBITMAPPATH)) {
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
							"LabelPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}

			node.setBGBitmap(file);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
