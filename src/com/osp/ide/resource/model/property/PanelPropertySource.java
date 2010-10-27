package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANEL_INFO;

public class PanelPropertySource extends OspNodePropertySource {

	public PanelPropertySource(Panel node) {
		this.node = node;

		PANEL_INFO info = node.getItem().clone();
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

        PropertyDescriptor GroupStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_GROUPSTYLE, 
                "Group Style", cszGroupStyle));
        GroupStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(GroupStyleDescriptor);
        
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

        if(node.getGroupStyleIndex() == GROUP_STYLE_NONE) {
    		PropertyDescriptor BgColorDescriptor = (new ResourceColorPropertyDescriptor(
    				FrameNode.PROPERTY_BGCOLOR, "Background Color"));
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
        } else {
            PropertyDescriptor BgColorDescriptor = (new PropertyDescriptor(
                    FrameNode.PROPERTY_BGCOLOR, "Background Color"));
            BgColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
            BgColorDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
            properties.add(BgColorDescriptor);
            
            PropertyDescriptor TransparencylDescriptor = (new PropertyDescriptor(
                    FrameNode.PROPERTY_BGOPACITY,
                    "Background Opacity [Range: 0% ~ 100%]"));
            TransparencylDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
            TransparencylDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
            properties.add(TransparencylDescriptor);
        }

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

//        PropertyDescriptor BorderStyleDescriptor = (new ComboBoxPropertyDescriptor(
//        		FrameNode.PROPERTY_BORDERSTYLE, "Border Style", cszBorderStyle));
//        BorderStyleDescriptor.setCategory("Property");
//        properties.add(BorderStyleDescriptor);
        
	}

	@Override
	public Object getPropertyValue(Object id) {
		Panel node = (Panel) this.node;

        int minY = getMinY(node);
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
        else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE))
            return node.getBorderStyleIndex();
        else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE))
            return node.getGroupStyleIndex();
		else if (id.equals(FrameNode.PROPERTY_BGOPACITY))
			return Integer.toString(node.getBGColorTransparency());
		else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			if (node.getBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getBGColor()).getRGB();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		PANEL_INFO info = (PANEL_INFO) this.info;
		Panel node = (Panel) this.node;

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
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            node.setBorderStyle(node.getBorderStyle());
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            int style = node.getGroupStyleIndex();
            if(style != GROUP_STYLE_NONE) {
                node.setBGColorTransparency(100);
                node.setBGColor(OspResourceManager.ColorToString(new Color(null, 0, 0, 0)));
            }
            node.setGroupStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			node.setBGColorTransparency(info.bgColorOpacity);
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			node.setBGColor(info.bgColor);
		} else if (id.equals(FrameNode.PROPERTY_FRAMETITLE)) {
			node.setTitle(info.title);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Panel node = (Panel) this.node;

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
						"PanelPropertySource.setPropertyValue()", id
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
						"PanelPropertySource.setPropertyValue()", id
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
						"PanelPropertySource.setPropertyValue()", id
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
						"PanelPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String borderstyle = cszBorderStyle[(Integer) value];
            node.setBorderStyle(borderstyle);
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            else if (index != GROUP_STYLE_NONE) {
                node.setBGColorTransparency(100);
                node.setBGColor(OspResourceManager.ColorToString(new Color(null, 0, 0, 0)));
            }
            
            String bgstyle = cszGroupStyle[(Integer) value];
            node.setGroupStyle(bgstyle);
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
						"PanelPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			String newColor;
			if (value instanceof RGB) {
				Color color = new Color(null, (RGB) value);
				newColor = OspResourceManager.ColorToString(color);
				color.dispose();
				color = null;
			} else
				newColor = DEFAULT_COLOR;

			node.setBGColor(newColor);
		} else if (id.equals(FrameNode.PROPERTY_FRAMETITLE)) {
			if (value instanceof Integer)
				node.setTitle("::" + stringId[(Integer) value]);
			else
				node.setTitle((String) value);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
