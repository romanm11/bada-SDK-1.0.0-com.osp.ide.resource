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
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SLIDER_INFO;

public class SliderPropertySource extends OspNodePropertySource {

	public SliderPropertySource(Slider node) {
		this.node = node;

		SLIDER_INFO info = node.getItem().clone();
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
    		PropertyDescriptor BgStyleDescriptor = (new ComboBoxPropertyDescriptor(
    				FrameNode.PROPERTY_BACKGROUNDSTYLE, "Background Style",
    				cszBgStyle));
    		BgStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
    		properties.add(BgStyleDescriptor);
        } else {
            PropertyDescriptor BgStyleDescriptor = (new PropertyDescriptor(
                    FrameNode.PROPERTY_BACKGROUNDSTYLE, "Background Style"));
            BgStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
            BgStyleDescriptor.setDescription(FramePropertyPage.PROPERTY_DISABLE);
            properties.add(BgStyleDescriptor);
        }
        
        PropertyDescriptor GroupStyleDescriptor = (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_GROUPSTYLE, "Group Style", cszGroupStyle));
        GroupStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(GroupStyleDescriptor);
        
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
		
		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);
		
		PropertyDescriptor LeftIconBitmapPathDescriptor = new FilePropertyDescriptor(
				FrameNode.PROPERTY_LEFTICONBITMAPPATH, "Left Icon Bitmap Path");
		LeftIconBitmapPathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(LeftIconBitmapPathDescriptor);
		
		PropertyDescriptor MaxDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_MAX, "Max [Range: -99 ~ 999]"));
		MaxDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(MaxDescriptor);

		PropertyDescriptor MinDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_MIN, "Min [Range: -99 ~ 999]"));
		MinDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(MinDescriptor);
		
//		PropertyDescriptor TypeDescriptor = (new PropertyDescriptor(
//				FrameNode.PROPERTY_TYPE, "Type"));
//		TypeDescriptor.setCategory("Property");
//		properties.add(TypeDescriptor);
		
		PropertyDescriptor RightIconBitmapPathDescriptor = new FilePropertyDescriptor(
				FrameNode.PROPERTY_RIGHTICONBITMAPPATH,
				"Right Icon Bitmap Path");
		RightIconBitmapPathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(RightIconBitmapPathDescriptor);

		PropertyDescriptor titleTextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TITLETEXT, "Title Text", stringId));
		titleTextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(titleTextDescriptor);

        PropertyDescriptor titleTextColorDescriptor = (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_TITLETEXTCOLOR, "Title Text Color"));
        titleTextColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(titleTextColorDescriptor);

        PropertyDescriptor ValueDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_VALUE, getMaxValue()));
		ValueDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(ValueDescriptor);

//      PropertyDescriptor BorderStyleDescriptor = (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_BORDERSTYLE, "Border Style", cszBorderStyle));
//      BorderStyleDescriptor.setCategory("Property");
//      properties.add(BorderStyleDescriptor);

		// PropertyDescriptor BgColorDescriptor = (new
		// ResourceColorPropertyDescriptor(
		// TWFrameNode.PROPERTY_BGCOLOR, "BGColor"));
		// BgColorDescriptor.setCategory("Property");
		// properties.add(BgColorDescriptor);

		// PropertyDescriptor StepDescriptor = (new TextPropertyDescriptor(
		// TWFrameNode.PROPERTY_STEPSIZE, "Step Size"));
		// StepDescriptor.setCategory("Property");
		// properties.add(StepDescriptor);

//		PropertyDescriptor ModeDescriptor = (new PropertyDescriptor(
//				FrameNode.PROPERTY_MODE, "Mode"));
//		ModeDescriptor.setCategory("Layout");
//		properties.add(ModeDescriptor);
	}

	public String getMaxValue() {
		Slider node = (Slider) this.node;
		String sMaxValue = node.getMax();
		String sMinValue = node.getMin();

		String sRangeValue = "Value [Range: " + sMinValue + " ~ " + sMaxValue
				+ "]";
		return sRangeValue;
	}

	@Override
	public Object getPropertyValue(Object id) {
		Slider node = (Slider) this.node;

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
		else if (id.equals(FrameNode.PROPERTY_VALUE)) {
			return node.getValue();
		} else if (id.equals(FrameNode.PROPERTY_MIN)) {
			return node.getMin();
		} else if (id.equals(FrameNode.PROPERTY_MAX)) {
			return node.getMax();
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            if (node.getTitleTextColor().equals(DEFAULT_COLOR))
                return DEFAULT_COLOR;
            return OspResourceManager.FormatRGB(node.getTitleTextColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			text = node.getTitleText();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			return node.getShowTitleText();
		} else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
			return node.getBorderStyleIndex();
		} else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
	        return node.getGroupStyleIndex();
		} else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
		    if(node.getGroupStyleIndex() == GROUP_STYLE_NONE)
		        return node.getBgStyleIndex();
		    else
		        return node.getBgStyle();
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			if (node.getBGColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getBGColor()).getRGB();
		} else if (id.equals(FrameNode.PROPERTY_STEPSIZE)) {
			return node.getStepSize();
		} else if (id.equals(FrameNode.PROPERTY_LEFTICONBITMAPPATH)) {
			File file = node.getLeftIconBitmapPath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_RIGHTICONBITMAPPATH)) {
			File file = node.getRightIconBitmapPath();
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
		SLIDER_INFO info = (SLIDER_INFO) this.info;
		Slider node = (Slider) this.node;

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
		} else if (id.equals(FrameNode.PROPERTY_VALUE)) {
			int nValue, nMinValue, nMaxValue;
			nMaxValue = Integer.parseInt(node.getMax());
			nMinValue = Integer.parseInt(node.getMin());
			nValue = info.min;
			if (nValue >= nMinValue || nValue <= nMaxValue)
				node.setValue(nValue);
		} else if (id.equals(FrameNode.PROPERTY_MIN)) {
			int nDfltValue, nMaxValue;
			nMaxValue = Integer.parseInt(node.getMax());
			nDfltValue = info.min;
			if (nMaxValue > nDfltValue)
				node.setMin(nDfltValue);
		} else if (id.equals(FrameNode.PROPERTY_MAX)) {
			try {
				int nDfltValue, nMinValue;
				nMinValue = Integer.parseInt(node.getMin());
				nDfltValue = info.max;
				if (nMinValue < nDfltValue)
					node.setMax(nDfltValue);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"SliderPropertySource.resetPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
        } else if (id.equals(FrameNode.PROPERTY_TITLETEXTCOLOR)) {
            node.setTitleTextColor(info.titleTextColor);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			node.setTitleText(info.titleText);
		} else if (id.equals(FrameNode.PROPERTY_SHOWTITLETEXT)) {
			node.setShowTitleText(info.ShowTitleText);
        } else if (id.equals(FrameNode.PROPERTY_BORDERSTYLE)) {
            node.setBorderStyle(node.getBorderStyle());
        } else if (id.equals(FrameNode.PROPERTY_GROUPSTYLE)) {
            node.setGroupStyle(node.getGroupStyle());
		} else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			node.setBgStyle(node.getBgStyle());
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			node.setBGColor(info.bgColor);
		} else if (id.equals(FrameNode.PROPERTY_STEPSIZE)) {
			node.setStepSize(info.stepSize);
		} else if (id.equals(FrameNode.PROPERTY_LEFTICONBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.leftIconBitmapPath == null
					|| info.leftIconBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.leftIconBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setLeftIconBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_RIGHTICONBITMAPPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.rightIconBitmapPath == null
					|| info.rightIconBitmapPath.isEmpty())
				file = null;
			else {
				file = new File(path + info.rightIconBitmapPath);
				if (!file.exists())
					file = null;
			}
			node.setLeftIconBitmapPath(file);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Slider node = (Slider) this.node;

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
						"SliderPropertySource.setPropertyValue()", id
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
						"SliderPropertySource.setPropertyValue()", id
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
						"SliderPropertySource.setPropertyValue()", id
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
						"SliderPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_VALUE)) {
			try {
				int nValue, nMinValue, nMaxValue;
				nMaxValue = Integer.parseInt(node.getMax());
				nMinValue = Integer.parseInt(node.getMin());
				nValue = Integer.parseInt((String) value);
				if (nValue >= nMinValue && nValue <= nMaxValue) {
					node.setValue(nValue);
				} else if (nValue < nMinValue) {
					node.setValue(nMinValue);
				} else if (nValue > nMaxValue) {
					node.setValue(nMaxValue);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"SliderPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_MIN)) {
			try {
				int nValue = Integer.parseInt((String) value);
				int nMaxValue;
				nMaxValue = Integer.parseInt(node.getMax());
				if (nValue >= -99 && nMaxValue > nValue) {
					node.setMin(nValue);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue > nSliderValue)
						node.setValue(nValue);
				} else if (nValue < -99) {
					node.setMin(-99);

				} else if (nMaxValue <= nValue) {
					node.setMin(nMaxValue - 1);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nSliderValue <= nMaxValue - 1)
						node.setValue(nMaxValue - 1);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"SliderPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_MAX)) {
			try {
				int nValue = Integer.parseInt((String) value);
				int nMinValue;
				nMinValue = Integer.parseInt(node.getMin());
				if (nValue <= 999 && nMinValue < nValue) {
					node.setMax(nValue);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue < nSliderValue)
						node.setValue(nValue);
				} else if (nValue < -99) {
					node.setMax(-99);
				} else if (nValue >= 999) {
					node.setMax(999);
				} else if (nMinValue < nValue) {
					node.setMax(nMinValue);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nSliderValue >= nValue)
						node.setValue(nValue);
				} else if (nMinValue >= nValue){
					node.setMax(nMinValue + 1);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nSliderValue > nMinValue + 1)
						node.setValue(nMinValue + 1);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"SliderPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
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
            else if (index != GROUP_STYLE_NONE)
                node.setBgStyle(cszBgStyle[BACKGROUND_STYLE_DEFAULT]);
            
            String bgstyle = cszGroupStyle[(Integer) value];
            node.setGroupStyle(bgstyle);
		} else if (id.equals(FrameNode.PROPERTY_BACKGROUNDSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String bgstyle = cszBgStyle[(Integer) value];
			node.setBgStyle(bgstyle);
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
		} else if (id.equals(FrameNode.PROPERTY_STEPSIZE)) {
			try {
				int nValue = Integer.parseInt((String) value);
				node.setStepSize(nValue);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"SliderPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_MINTEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setMinText(text);
		} else if (id.equals(FrameNode.PROPERTY_LEFTICONBITMAPPATH)) {
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
							"SliderPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setLeftIconBitmapPath(file);
		} else if (id.equals(FrameNode.PROPERTY_RIGHTICONBITMAPPATH)) {
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
							"SliderPropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setRightIconBitmapPath(file);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
