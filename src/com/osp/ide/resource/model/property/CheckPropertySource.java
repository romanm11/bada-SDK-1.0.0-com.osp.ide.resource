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
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.CHECK_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class CheckPropertySource extends OspNodePropertySource {

    public CheckPropertySource(Check node) {
        this.node = node;

        CHECK_INFO info = node.getItem().clone();
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

        if(node.getGroupStyleIndex() == GROUP_STYLE_NONE) {
            PropertyDescriptor BgStyleDescriptor = 
                (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_BACKGROUNDSTYLE, 
                    "Background Style", cszBgStyle));
            BgStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
            properties.add(BgStyleDescriptor);
        } else {
            PropertyDescriptor BgStyleDescriptor = 
                (new PropertyDescriptor(FrameNode.PROPERTY_BACKGROUNDSTYLE, 
                    "Background Style"));
            BgStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
            BgStyleDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
            properties.add(BgStyleDescriptor);
        }

        PropertyDescriptor StyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_STYLE, 
                "CheckButton Style", cszStyle[STYLE_CHECK]));
        StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(StyleDescriptor);

        PropertyDescriptor GroupStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_GROUPSTYLE, 
                "Group Style", cszGroupStyle));
        GroupStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(GroupStyleDescriptor);
      
        PropertyDescriptor ShowtitleTextDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_SHOWTITLETEXT, 
                "Show Title Text", BOOL));
        ShowtitleTextDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(ShowtitleTextDescriptor);

        PropertyDescriptor HalignDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_HALIGN, 
                "Text Horizontal Align", cszHAlign));
        HalignDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(HalignDescriptor);

        PropertyDescriptor ValignDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_VALIGN, 
                "Text Vertical Align", cszVAlign));
        ValignDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(ValignDescriptor);

        PropertyDescriptor HeightDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_HEIGHT, "Height"));
        HeightDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(HeightDescriptor);
        
        PropertyDescriptor ParentDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_PARENT, 
                "Parent ID", controlId));
        ParentDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(ParentDescriptor);

        PropertyDescriptor WidthDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_WIDTH, "Width"));
        WidthDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(WidthDescriptor);

        PropertyDescriptor PointXlDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_POINTX, "X Position"));
        PointXlDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointXlDescriptor);

        PropertyDescriptor PointYDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_POINTY, "Y Position"));
        PointYDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointYDescriptor);

        PropertyDescriptor CheckGroupDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_GROUPID, "Group ID"));
        CheckGroupDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(CheckGroupDescriptor);

        PropertyDescriptor NameDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_RENAME, "ID"));
        NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(NameDescriptor);

        PropertyDescriptor TextDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_TEXT, "Text", stringId));
        TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TextDescriptor);
        
        PropertyDescriptor TextColorDescriptor = 
            (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_TEXTCOLOR, 
                "Text Color"));
        TextColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TextColorDescriptor);

        PropertyDescriptor titleTextDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_TITLETEXT, 
                "Title Text", stringId));
        titleTextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(titleTextDescriptor);

        PropertyDescriptor titleTextColorDescriptor = 
            (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_TITLETEXTCOLOR, 
                "Title Text Color"));
        titleTextColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(titleTextColorDescriptor);

//        PropertyDescriptor BorderStyleDescriptor = (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_BORDERSTYLE, "Border Style", cszBorderStyle));
//        BorderStyleDescriptor.setCategory("Property");
//        properties.add(BorderStyleDescriptor);
        
    }

    @Override
    public Object getPropertyValue(Object id) {
        Check node = (Check) this.node;

        int minY = getMinY(node);
        String text = "";
        if (id.equals(FrameNode.PROPERTY_RENAME)) {
            return node.getName();
        } else if (id.equals(FrameNode.PROPERTY_MODE))
            return node.getLayout().mode;
        else if (id.equals(FrameNode.PROPERTY_PARENT))
            return getControlIndex(node.getParentId());
        else if (id.equals(FrameNode.PROPERTY_STYLE))
            return FrameNode.getStyleIndex(STYLE_CHECK, node.getLayout().style);
        else if (id.equals(FrameNode.PROPERTY_POINTX))
            return Integer.toString(node.getLayout().x);
        else if (id.equals(FrameNode.PROPERTY_POINTY))
            return Integer.toString(node.getLayout().y - minY);
        else if (id.equals(FrameNode.PROPERTY_WIDTH))
            return Integer.toString(node.getLayout().width);
        else if (id.equals(FrameNode.PROPERTY_HEIGHT))
            return Integer.toString(node.getLayout().height);
        else if (id.equals(FrameNode.PROPERTY_GROUPID))
            return node.getGroupId();
        else if (id.equals(FrameNode.PROPERTY_TYPE))
            return "WINDOW_" + cszCtlType[node.getType()];
        else if (id.equals(FrameNode.PROPERTY_TEXTCOLOR)) {
            if (node.getTextColor().equals(DEFAULT_COLOR))
                return DEFAULT_COLOR;
            return OspResourceManager.FormatRGB(node.getTextColor()).getRGB();
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            if (node.getTitleTextColor().equals(DEFAULT_COLOR))
                return DEFAULT_COLOR;
            return OspResourceManager.FormatRGB(node.getTitleTextColor()).getRGB();
        } else if (id.equals(FrameNode.PROPERTY_TEXT)) {
            text = node.getText();

            if (text.indexOf("::") < 0)
                return text;
            else {
                return getStringIndex(text.replace("::", ""));
            }
        } else if (id.equals(FrameNode.PROPERTY_TEXTDIRECTION))
            return node.getTextDirection();
        else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE))
            if(node.getGroupStyleIndex() == GROUP_STYLE_NONE)
                return node.getBgStyleIndex();
            else
                return node.getBgStyle();
        else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE))
            return node.getGroupStyleIndex();
        else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
            text = node.getTitleText();

            if (text.indexOf("::") < 0)
                return text;
            else {
                return getStringIndex(text.replace("::", ""));
            }
        } else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT))
            return node.getShowTitleText();
        else if (id.equals(FrameNode.PROPERTY_HALIGN))
            return node.getHAlign();
        else if (id.equals(FrameNode.PROPERTY_VALIGN))
            return node.getVAlign();
        else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            return node.getBorderStyleIndex();
		} else if(handlerList != null && handlerList.contains(id))
		    return 0;
		    
        return null;
    }

    @Override
    public void resetPropertyValue(Object id) {
        CHECK_INFO info = (CHECK_INFO) this.info;
        Check node = (Check) this.node;

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
        } else if (id.equals(FrameNode.PROPERTY_GROUPID)) {
            node.setGroupId(info.GroupID);
        } else if (id.equals(FrameNode.PROPERTY_TEXTCOLOR)) {
            node.setTextColor(info.textColor);
        } else if (id.equals(FrameNode.PROPERTY_TEXT)) {
            node.setText(info.text);
        } else if (id.equals(FrameNode.PROPERTY_TEXTDIRECTION)) {
            node.setTextDirection(info.textDirection);
        } else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
            node.setBgStyle(node.getBgStyle());
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            node.setGroupStyle(node.getGroupStyle());
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            node.setTitleTextColor(info.titleTextColor);
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
            node.setTitleText(info.titleText);
        } else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
            node.setShowTitleText(info.ShowTitleText);
        } else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
            node.setHAlign(info.hAlign);
        } else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
            node.setVAlign(info.vAlign);
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            node.setBorderStyle(node.getBorderStyle());
        }
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (isValidate(id, value) == false)
            return;

        Check node = (Check) this.node;

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
            String style = cszStyle[STYLE_CHECK][(Integer) value];
            node.setStyle(style);
        } else if (id.equals(FrameNode.PROPERTY_POINTX)) {
            try {
                Layout rect = node.getLayout();
                Integer pointx = Integer.parseInt((String) value);
                rect.x = pointx;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("CheckPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_POINTY)) {
            try {
                Layout rect = node.getLayout();
                Integer pointy = Integer.parseInt((String) value);
                int minY = getMinY(node);
                rect.y = pointy + minY;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("CheckPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
            try {
                Layout rect = node.getLayout();
                Integer pointwidth = Integer.parseInt((String) value);
                rect.width = pointwidth;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("CheckPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
            try {
                Layout rect = node.getLayout();
                Integer pointheight = Integer.parseInt((String) value);
                rect.height = pointheight;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("CheckPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_GROUPID)) {
            try {
                Integer groupId = Integer.parseInt((String) value);
                node.setGroupId(groupId);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("CheckPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
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
        } else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String bgstyle = cszBgStyle[(Integer) value];
            node.setBgStyle(bgstyle);
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            else if (index != GROUP_STYLE_NONE)
                node.setBgStyle(cszBgStyle[BACKGROUND_STYLE_DEFAULT]);
            
            String bgstyle = cszGroupStyle[(Integer) value];
            node.setGroupStyle(bgstyle);
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            String newColor;
            if (value instanceof RGB) {
                Color color = new Color(null, (RGB) value);
                newColor = OspResourceManager.ColorToString(color);
                color.dispose();
                color = null;
            } else
                newColor = DEFAULT_COLOR;

            node.setTitleTextColor(newColor);
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
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String borderstyle = cszBorderStyle[(Integer) value];
            node.setBorderStyle(borderstyle);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
        }
    }

}
