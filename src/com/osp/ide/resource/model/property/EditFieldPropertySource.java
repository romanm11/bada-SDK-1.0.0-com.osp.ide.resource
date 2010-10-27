package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.EditableComboPropertyDescriptor;
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.resinfo.EDITFIELD_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class EditFieldPropertySource extends OspNodePropertySource {

	public EditFieldPropertySource(EditField node) {
		this.node = node;

		EDITFIELD_INFO info = node.getItem().clone();
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
                FrameNode.PROPERTY_STYLE, "EditField Style", cszStyle[STYLE_EDITFIELD]));
        StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(StyleDescriptor);

        PropertyDescriptor GroupStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_GROUPSTYLE, 
                "Group Style", cszGroupStyle));
        GroupStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(GroupStyleDescriptor);
      
        FrameNode parent = node.getParent();
        if(parent instanceof PanelFrame) {
            PropertyDescriptor InputStyleDescriptor = (new ComboBoxPropertyDescriptor(
                    FrameNode.PROPERTY_INPUTSTYLE, "Input Style", cszInputStyle));
            InputStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
            properties.add(InputStyleDescriptor);
        } else {
            PropertyDescriptor InputStyleDescriptor = new PropertyDescriptor(
                    FrameNode.PROPERTY_INPUTSTYLE, "Input Style");
            InputStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
            InputStyleDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
            properties.add(InputStyleDescriptor);
        }

        PropertyDescriptor ShowtitleTextDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_SHOWTITLETEXT, "Show Title Text", BOOL));
        ShowtitleTextDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(ShowtitleTextDescriptor);

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
    
		PropertyDescriptor GuideTextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_GUIDETEXT, "Guide Text", stringId));
		GuideTextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(GuideTextDescriptor);

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

        PropertyDescriptor KeypadEnabledDescriptor = (new ComboBoxPropertyDescriptor(
                FrameNode.PROPERTY_KEYPADENABLED, "Keypad Enabled", BOOL));
        KeypadEnabledDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(KeypadEnabledDescriptor);

		PropertyDescriptor TextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TEXT, "Text", stringId));
		TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TextDescriptor);

//        PropertyDescriptor BorderStyleDescriptor = (new ComboBoxPropertyDescriptor(
//        		FrameNode.PROPERTY_BORDERSTYLE, "Border Style", cszBorderStyle));
//        BorderStyleDescriptor.setCategory("Property");
//        properties.add(BorderStyleDescriptor);
        
		PropertyDescriptor LimitLengthlDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_LIMITLENGTH,
				"Text Limit Length [Range: 0 ~ 1000]"));
		LimitLengthlDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(LimitLengthlDescriptor);

		PropertyDescriptor titleTextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TITLETEXT, "Title Text", stringId));
		titleTextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(titleTextDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		EditField node = (EditField) this.node;

        int minY = getMinY(node);
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			return FrameNode.getStyleIndex(STYLE_EDITFIELD,
					node.getLayout().style);
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
		} else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)){
	            return node.getBorderStyleIndex();	
		} else if (id.equals(FrameNode.PROPERTY_LIMITLENGTH)) {
			return Integer.toString(node.getLimitLength());
		} else if (id.equals(FrameNode.PROPERTY_INPUTSTYLE)) {
            if(node.getParent() instanceof PanelFrame) {
    			return FrameNode.getInputStyleIndex(node.getInputStyle());
            }
            node.setInputStyle(cszInputStyle[0]);
            return cszInputStyle[0];
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			text = node.getTitleText();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			return node.getShowTitleText();
		} else if (id.equals(FrameNode.PROPERTY_KEYPADENABLED)) {
			return node.getKeypadEnabled();
		} else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
	        return node.getGroupStyleIndex();
		} else if (id.equals(FrameNode.PROPERTY_GUIDETEXT)) {
			text = node.getGuideText();

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
		EDITFIELD_INFO info = (EDITFIELD_INFO) this.info;
		EditField node = (EditField) this.node;

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
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            node.setBorderStyle(node.getBorderStyle());
		} else if (id.equals(FrameNode.PROPERTY_LIMITLENGTH)) {
			node.setLimitLength(info.limitLength);
		} else if (id.equals(FrameNode.PROPERTY_INPUTSTYLE)) {
			node.setInputStyle(info.inputStyle);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			node.setTitleText(info.titleText);
		} else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			node.setShowTitleText(info.ShowTitleText);
		} else if (id.equals(FrameNode.PROPERTY_KEYPADENABLED)) {
			node.setKeypadEnabled(info.KeypadEnabled);
		} else if (id.equals(FrameNode.PROPERTY_GUIDETEXT)) {
			node.setGuideText(info.guideText);
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            node.setGroupStyle(node.getGroupStyle());
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		EditField node = (EditField) this.node;

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
						"EditFieldPropertySource.setPropertyValue()", id
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
						"EditFieldPropertySource.setPropertyValue()", id
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
						"EditFieldPropertySource.setPropertyValue()", id
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
						"EditFieldPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_EDITFIELD][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_TEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			if (text.indexOf("::") < 0 && text.length() > node.getLimitLength())
				text = text.substring(0, node.getLimitLength());

			node.setText(text);
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String borderstyle = cszBorderStyle[(Integer) value];
            node.setBorderStyle(borderstyle);
		} else if (id.equals(FrameNode.PROPERTY_LIMITLENGTH)) {
			try {
				Integer length = Integer.parseInt((String) value);
				String empty = node.getText();

				if (length < 0)
					length = 0;
				if (length > 1000)
					length = 1000;
				if (empty.indexOf("::") < 0 && length < empty.length()) {
					empty = empty.substring(0, length);
					node.setText(empty);
				}
				node.setLimitLength(length);

			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"EditFieldPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_INPUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setInputStyle(cszInputStyle[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;
			node.setTitleText(text);
		} else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			int index = (Integer) value;
			if (index < 0 || index > 1)
				return;
			node.setShowTitleText(index);
		} else if (id.equals(FrameNode.PROPERTY_KEYPADENABLED)) {
			int index = (Integer) value;
			if (index < 0 || index > 1)
				return;
			node.setKeypadEnabled(index);
		} else if (id.equals(FrameNode.PROPERTY_GUIDETEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setGuideText(text);
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String bgstyle = cszGroupStyle[(Integer) value];
            node.setGroupStyle(bgstyle);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
