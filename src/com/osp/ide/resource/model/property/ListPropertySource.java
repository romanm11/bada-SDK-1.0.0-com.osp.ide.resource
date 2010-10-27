package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.EditableComboPropertyDescriptor;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.resinfo.LIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class ListPropertySource extends OspNodePropertySource {

	public ListPropertySource(ListControl node) {
		this.node = node;

		LIST_INFO info = node.getItem().clone();
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
		
		ListControl node = (ListControl) this.node;
		int itemFormat = FrameNode.getListFormatIndex(node.getListItemFormat());

        PropertyDescriptor StyleDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_STYLE, "List Style", cszStyle[STYLE_LIST]));
        StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(StyleDescriptor);

        PropertyDescriptor ListItemFormatDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_LISTITEMFORMAT, "List Item Format",
                cszListItemFormat));
        ListItemFormatDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(ListItemFormatDescriptor);

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
    
        PropertyDescriptor TextColorDescriptor = 
            (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT, 
                "Color Of Empty List Text"));
        TextColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TextColorDescriptor);

		PropertyDescriptor Colume1WidthDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_COLUME1WIDTH, "Column 1 Width"));
		Colume1WidthDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(Colume1WidthDescriptor);

		if(itemFormat > LIST_ITEM_SINGLE_TEXT) {
			PropertyDescriptor Colume2WidthDescriptor = (new TextPropertyDescriptor(
					FrameNode.PROPERTY_COLUME2WIDTH, "Column 2 Width"));
			Colume2WidthDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
			properties.add(Colume2WidthDescriptor);
		}

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

		PropertyDescriptor Line1HeightDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_LINE1HEIGHT, "Row 1 Height"));
		Line1HeightDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(Line1HeightDescriptor);

		if(itemFormat > LIST_ITEM_SINGLE_IMAGE_TEXT_IMAGE) {
			PropertyDescriptor Line2HeightDescriptor = (new TextPropertyDescriptor(
					FrameNode.PROPERTY_LINE2HEIGHT, "Row 2 Height"));
			Line2HeightDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
			properties.add(Line2HeightDescriptor);
		}

		PropertyDescriptor TextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TEXTOFEMPTYLIST, "Text Of Empty List",
				stringId));
		TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		ListControl node = (ListControl) this.node;

        int minY = getMinY(node);
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			return FrameNode.getStyleIndex(STYLE_LIST, node.getLayout().style);
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
		else if (id.equals(FrameNode.PROPERTY_LISTITEMFORMAT)) {
			return FrameNode.getListFormatIndex(node.getListItemFormat());
		} else if (id.equals(FrameNode.PROPERTY_LINE1HEIGHT))
			return Integer.toString(node.getLine1Height());
		else if (id.equals(FrameNode.PROPERTY_LINE2HEIGHT))
			return Integer.toString(node.getLine2Height());
		else if (id.equals(FrameNode.PROPERTY_COLUME1WIDTH))
			return Integer.toString(node.getColume1Width());
		else if (id.equals(FrameNode.PROPERTY_COLUME2WIDTH))
			return Integer.toString(node.getColume2Width());
		else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE))
			return node.getBgStyleIndex();
        else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            if (node.getTextColor().equals(DEFAULT_COLOR))
                return DEFAULT_COLOR;
            return OspResourceManager.FormatRGB(node.getTextColor()).getRGB();
        } else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
			text = node.getTextOfEmptyList();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		LIST_INFO info = (LIST_INFO) this.info;
		ListControl node = (ListControl) this.node;

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
		} else if (id.equals(FrameNode.PROPERTY_LISTITEMFORMAT)) {
			node.setListItemFormat(info.ListItemFormat);
		} else if (id.equals(FrameNode.PROPERTY_LINE1HEIGHT)) {
			node.setLine1Height(info.line1Height);
		} else if (id.equals(FrameNode.PROPERTY_LINE2HEIGHT)) {
			node.setLine2Height(info.line2Height);
		} else if (id.equals(FrameNode.PROPERTY_COLUME1WIDTH)) {
			node.setColume1Width(info.colume1Width);
		} else if (id.equals(FrameNode.PROPERTY_COLUME2WIDTH)) {
			node.setColume2Width(info.colume2Width);
		} else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			node.setBgStyle(node.getBgStyle());
        } else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            node.setTextColor(info.colorOfEmptyListText);
		} else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
			node.setTextOfEmptyList(info.textOfEmptyList);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		ListControl node = (ListControl) this.node;

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
						"ListPropertySource.setPropertyValue()", id
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
						"ListPropertySource.setPropertyValue()", id
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
						"ListPropertySource.setPropertyValue()", id
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
						"ListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_LIST][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_LISTITEMFORMAT)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setListItemFormat(cszListItemFormat[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_LINE1HEIGHT)) {
			int height = 1;
			try {
				height = Integer.parseInt((String) value);
				if (height > 0 && height <= 1000)
					node.setLine1Height(height);
				else if (height <= 0) {
					height = 1;
					node.setLine1Height(height);
				} else if (height > 1000) {
					height = 1000;
					node.setLine1Height(height);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_LINE2HEIGHT)) {
			int height = 1;
			try {
				height = Integer.parseInt((String) value);
				if (height > 0 && height <= 1000)
					node.setLine2Height(height);
				else if (height <= 0) {
					height = 1;
					node.setLine2Height(height);
				} else if (height > 1000) {
					height = 1000;
					node.setLine2Height(height);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_COLUME1WIDTH)) {
			int width = 1;
			try {
				width = Integer.parseInt((String) value);
				if (width > 0 && width <= 1000)
					node.setColume1Width(width);
				else if (width <= 0) {
					width = 1;
					node.setColume1Width(width);
				} else if (width > 1000) {
					width = 1000;
					node.setColume1Width(width);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_COLUME2WIDTH)) {
			int width = 1;
			try {
				width = Integer.parseInt((String) value);
				if (width > 0 && width <= 1000)
					node.setColume2Width(width);
				else if (width <= 0) {
					width = 1;
					node.setColume2Width(width);
				} else if (width > 1000) {
					width = 1000;
					node.setColume2Width(width);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String bgstyle = cszBgStyle[(Integer) value];
			node.setBgStyle(bgstyle);
        } else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            String newColor;
            if (value instanceof RGB) {
                Color color = new Color(null, (RGB) value);
                newColor = OspResourceManager.ColorToString(color);
                color.dispose();
                color = null;
            } else
                newColor = DEFAULT_COLOR;

            node.setTextColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setTextOfEmptyList(text);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
