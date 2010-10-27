package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;

public class PanelFramePropertySource extends OspNodePropertySource {

	public PanelFramePropertySource(PanelFrame node) {
		this.node = node;

		PANELFRAME_INFO info = node.getItem().clone();
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

		PropertyDescriptor HeightDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_HEIGHT, "Height"));
		HeightDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
		properties.add(HeightDescriptor);
		
		PropertyDescriptor WidthDescriptor = (new PropertyDescriptor(
				FrameNode.PROPERTY_WIDTH, "Width"));
		WidthDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
		WidthDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
		properties.add(WidthDescriptor);

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
		
		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);
	}

	@Override
	public Object getPropertyValue(Object id) {
		PanelFrame node = (PanelFrame) this.node;

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_WIDTH))
			return Integer.toString(node.getLayout().width);
		else if (id.equals(FrameNode.PROPERTY_HEIGHT))
			return Integer.toString(node.getLayout().height);
		else if (id.equals(FrameNode.PROPERTY_TYPE))
			return "WINDOW_" + cszCtlType[node.getType()];
		else if (id.equals(FrameNode.PROPERTY_BGOPACITY))
			return Integer.toString(node.getBGColorTransparency());
		else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			if (node.getBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getBGColor()).getRGB();
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		PANELFRAME_INFO info = (PANELFRAME_INFO) this.info;
		PanelFrame node = (PanelFrame) this.node;

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName(info.Id);
		} else if (id.equals(FrameNode.PROPERTY_TYPE)) {
			node.setType(info.type);
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
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			node.setBGColorTransparency(info.bgColorOpacity);
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			node.setBGColor(info.bgColor);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		PanelFrame node = (PanelFrame) this.node;
        Point size = Activator.getSScreenToPoint(node.getScreen());

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName((String) value);
		} else if (id.equals(FrameNode.PROPERTY_POINTX)) {
			try {
				Layout rect = node.getLayout();
				Integer pointx = Integer.parseInt((String) value);
				rect.x = pointx;

				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PanelFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_POINTY)) {
			try {
				Layout rect = node.getLayout();
				Integer pointy = Integer.parseInt((String) value);
				rect.y = pointy;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PanelFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
			try {
				Layout rect = node.getLayout();
				Integer pointwidth = Integer.parseInt((String) value);

				if (node.getModeIndex() == FrameConst.PORTRAIT) {
					if(pointwidth > size.x * 3)
					    pointwidth = size.x * 3;
				} else {
                    if(pointwidth > size.y * 3)
                        pointwidth = size.y * 3;
				}

				rect.width = pointwidth;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PanelFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
			try {
				Layout rect = node.getLayout();
				Integer pointheight = Integer.parseInt((String) value);

                if (node.getModeIndex() == FrameConst.PORTRAIT) {
                    if(pointheight > size.y * 3)
                        pointheight = size.y * 3;
                } else {
                    if(pointheight > size.x * 3)
                        pointheight = size.x * 3;
                }

				rect.height = pointheight;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PanelFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
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
						"PanelFramePropertySource.setPropertyValue()", id
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
		}
	}
}
