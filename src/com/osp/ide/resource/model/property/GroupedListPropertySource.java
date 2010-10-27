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
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.resinfo.GROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class GroupedListPropertySource extends OspNodePropertySource {

	public GroupedListPropertySource(GroupedList node) {
		this.node = node;

		GROUPEDLIST_INFO info = node.getItem().clone();
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

        PropertyDescriptor StyleDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_STYLE, "CustomList Style", cszStyle[STYLE_CUSTOMLIST]));
        StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(StyleDescriptor);

		PropertyDescriptor FastScrollDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_FASTSCROLL, "Show Fast Scroll", BOOL));
		FastScrollDescriptor.setCategory(FrameNode.GROUP_STYLE);
		properties.add(FastScrollDescriptor);

		PropertyDescriptor ItemDividerDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_ITEMDIVIDER, "Show Item Divider", BOOL));
		ItemDividerDescriptor.setCategory(FrameNode.GROUP_STYLE);
		properties.add(ItemDividerDescriptor);

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

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

		PropertyDescriptor TextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TEXTOFEMPTYLIST, "Text Of Empty List",
				stringId));
		TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		GroupedList node = (GroupedList) this.node;

        int minY = getMinY(node);
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE))
			return FrameNode.getStyleIndex(STYLE_CUSTOMLIST,
					node.getLayout().style);
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
		else if (id.equals(FrameNode.PROPERTY_ITEMDIVIDER))
			return node.getItemDivider();
		else if (id.equals(FrameNode.PROPERTY_FASTSCROLL))
			return node.getFastScroll();
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
		GROUPEDLIST_INFO info = (GROUPEDLIST_INFO) this.info;
		GroupedList node = (GroupedList) this.node;

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName(info.Id);
		} else if (id.equals(FrameNode.PROPERTY_TYPE)) {
			node.setType(info.type);
		} else if (id.equals(FrameNode.PROPERTY_PARENT)) {
			node.setParentId(info.pID);
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			node.setStyle(info.GetLayout(node.getModeIndex()).style);
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
		} else if (id.equals(FrameNode.PROPERTY_ITEMDIVIDER)) {
			node.setItemDivider(info.itemDivider);
		} else if (id.equals(FrameNode.PROPERTY_FASTSCROLL)) {
			node.setFastScroll(info.fastScroll);
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

		GroupedList node = (GroupedList) this.node;
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName((String) value);
		} else if (id.equals(FrameNode.PROPERTY_PARENT)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setParentId(controlId[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_CUSTOMLIST][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_POINTX)) {
			try {
				Layout rect = node.getLayout();
				Integer pointx = Integer.parseInt((String) value);
				rect.x = pointx;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"GroupedListPropertySource.setPropertyValue()", id
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
						"GroupedListPropertySource.setPropertyValue()", id
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
						"GroupedListPropertySource.setPropertyValue()", id
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
						"GroupedListPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_ITEMDIVIDER)) {
			int index = (Integer) value;
			if (index < 0 || index > 1)
				return;
			node.setItemDivider(index);
		} else if (id.equals(FrameNode.PROPERTY_FASTSCROLL)) {
			int index = (Integer) value;
			if (index < 0 || index > 1)
				return;
			node.setFastScroll(index);
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
