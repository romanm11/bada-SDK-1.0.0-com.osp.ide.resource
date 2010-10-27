package com.osp.ide.resource.model.property;

import java.util.ArrayList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PROGRESS_INFO;

public class ProgressPropertySource extends OspNodePropertySource {

	public ProgressPropertySource(Progress node) {
		this.node = node;

		PROGRESS_INFO info = node.getItem().clone();
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
		
		PropertyDescriptor MaxDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_MAX, "Max"));
		MaxDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(MaxDescriptor);
		
		PropertyDescriptor MinDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_MIN, "Min"));
		MinDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(MinDescriptor);
		
		PropertyDescriptor ValueDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_VALUE, "Value"));
		ValueDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(ValueDescriptor);
		
//		PropertyDescriptor TypeDescriptor = (new PropertyDescriptor(
//				FrameNode.PROPERTY_TYPE, "Type"));
//		TypeDescriptor.setCategory("Property");
//		properties.add(TypeDescriptor);

		// PropertyDescriptor BgColorDescriptor = (new
		// ResourceColorPropertyDescriptor(
		// TWFrameNode.PROPERTY_BGCOLOR, "Background Color"));
		// BgColorDescriptor.setCategory("Property");
		// properties.add(BgColorDescriptor);

		// PropertyDescriptor LeftTextDescriptor = (new
		// EditableComboPropertyDescriptor(
		// TWFrameNode.PROPERTY_LEFTTEXT, "Left Text", stringId));
		// LeftTextDescriptor.setCategory("Property");
		// properties.add(LeftTextDescriptor);

		// PropertyDescriptor CenterTextDescriptor = (new
		// EditableComboPropertyDescriptor(
		// TWFrameNode.PROPERTY_CENTERTEXT, "Center Text", stringId));
		// CenterTextDescriptor.setCategory("Property");
		// properties.add(CenterTextDescriptor);

		// PropertyDescriptor RightTextDescriptor = (new
		// EditableComboPropertyDescriptor(
		// TWFrameNode.PROPERTY_RIGHTTEXT, "Right Text", stringId));
		// RightTextDescriptor.setCategory("Property");
		// properties.add(RightTextDescriptor);

		// PropertyDescriptor bShowValueStateDescriptor = (new
		// ComboBoxPropertyDescriptor(
		// TWFrameNode.PROPERTY_BSHOWVALUESTATE, "Show Value State", BOOL));
		// bShowValueStateDescriptor.setCategory("Property");
		// properties.add(bShowValueStateDescriptor);

//		PropertyDescriptor ModeDescriptor = (new PropertyDescriptor(
//				FrameNode.PROPERTY_MODE, "Mode"));
//		ModeDescriptor.setCategory("Layout");
//		properties.add(ModeDescriptor);

		// PropertyDescriptor EditorFgColorDescriptor = (new
		// ResourceColorPropertyDescriptor(
		// TWFrameNode.PROPERTY_EDITORFGCOLOR, "Foreground Color"));
		// properties.add(EditorFgColorDescriptor);
	}

	@Override
	public Object getPropertyValue(Object id) {
		Progress node = (Progress) this.node;

        int minY = getMinY(node);
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE))
			return FrameNode.getStyleIndex(STYLE_PROGRESS,
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
		else if (id.equals(FrameNode.PROPERTY_VALUE)) {
			return node.getValue();
		} else if (id.equals(FrameNode.PROPERTY_MIN)) {
			return node.getMin();
		} else if (id.equals(FrameNode.PROPERTY_MAX)) {
			return node.getMax();
			// } else if (id.equals(TWFrameNode.PROPERTY_BGCOLOR)) {
			// if (node.getBGColor().equals(DEFAULT_COLOR))
			// return DEFAULT_COLOR;
			// return OspTWUIFrame.FormatRGB(node.getBGColor()).getRGB();
			// } else if (id.equals(TWFrameNode.PROPERTY_LEFTTEXT)) {
			// text = node.getLeftText();
			//
			// if (text.indexOf("::") < 0)
			// return text;
			// else {
			// return getStringIndex(text.replace("::", ""));
			// }
			// } else if (id.equals(TWFrameNode.PROPERTY_CENTERTEXT)) {
			// text = node.getCenterText();
			//
			// if (text.indexOf("::") < 0)
			// return text;
			// else {
			// return getStringIndex(text.replace("::", ""));
			// }
			// } else if (id.equals(TWFrameNode.PROPERTY_RIGHTTEXT)) {
			// text = node.getRightText();
			//
			// if (text.indexOf("::") < 0)
			// return text;
			// else {
			// return getStringIndex(text.replace("::", ""));
			// }
			// } else if (id.equals(TWFrameNode.PROPERTY_BSHOWVALUESTATE)) {
			// return node.getShowValueState();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		PROGRESS_INFO info = (PROGRESS_INFO) this.info;
		Progress node = (Progress) this.node;

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
						"ProgressPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
			// } else if (id.equals(TWFrameNode.PROPERTY_BGCOLOR)) {
			// node.setBGColor(info.bgColor);
			// } else if (id.equals(TWFrameNode.PROPERTY_LEFTTEXT)) {
			// node.setLeftText(info.leftText);
			// } else if (id.equals(TWFrameNode.PROPERTY_CENTERTEXT)) {
			// node.setCenterText(info.centerText);
			// } else if (id.equals(TWFrameNode.PROPERTY_RIGHTTEXT)) {
			// node.setRightText(info.rightText);
			// } else if (id.equals(TWFrameNode.PROPERTY_BSHOWVALUESTATE)) {
			// node.setShowValueState(info.bShowValueState);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;
		
		Progress node = (Progress) this.node;

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
						"ProgressPropertySource.setPropertyValue()", id
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
						"ProgressPropertySource.setPropertyValue()", id
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
						"ProgressPropertySource.setPropertyValue()", id
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
						"ProgressPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
			// } else if (id.equals(TWFrameNode.PROPERTY_STYLE)) {
			// int index = (Integer) value;
			// if(index < 0)
			// return;
			// String style = cszStyle[STYLE_PROGRESS][(Integer) value];
			// node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_VALUE)) {
			try {
				int nValue, nMinValue, nMaxValue;
				nMaxValue = Integer.parseInt(node.getMax());
				nMinValue = Integer.parseInt(node.getMin());
				nValue = Integer.parseInt((String) value);
				
				if (nValue < Integer.MIN_VALUE || nValue > Integer.MAX_VALUE) {
					return;
				}
				if (nValue >= nMinValue && nValue <= nMaxValue) {
					node.setValue(nValue);
				} else if (nValue < nMinValue) {
					node.setValue(nMinValue);
				} else if (nValue > nMaxValue) {
					node.setValue(nMaxValue);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ProgressPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_MIN)) {
			try {
				int nValue = Integer.parseInt((String) value);
				int nMaxValue;
				nMaxValue = Integer.parseInt(node.getMax());
				
				if (nValue < Integer.MIN_VALUE || nValue > Integer.MAX_VALUE) {
					return;
				}
				if (nValue >= 0 && nMaxValue > nValue) {
					node.setMin(nValue);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue > nSliderValue)
						node.setValue(nValue);
				} else if (nValue < 0) {
					node.setMin(0);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue > nSliderValue)
						node.setValue(nValue);
				} else if (nMaxValue <= nValue) {
					node.setMin(nMaxValue - 1);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue > nSliderValue)
						node.setValue(nValue);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ProgressPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_MAX)) {
			try {
				
				int nValue = Integer.parseInt((String) value);
				int nMinValue;
				nMinValue = Integer.parseInt(node.getMin());

				if (nValue < Integer.MIN_VALUE || nValue > Integer.MAX_VALUE) {
					return;
				}
				if (nValue > nMinValue) {
					node.setMax(nValue);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue < nSliderValue)
						node.setValue(nValue);
				} else if (nValue <= 0) {
					node.setMax(1);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nSliderValue > 1)
						node.setValue(1);
				} else if (nValue <= nMinValue) {
					node.setMax(nMinValue + 1);
					int nSliderValue = Integer.parseInt((String) node
							.getValue());
					if (nValue < nSliderValue)
						node.setValue(nMinValue + 1);
				}
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"ProgressPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
			// } else if (id.equals(TWFrameNode.PROPERTY_BGCOLOR)) {
			// String newColor;
			// if (value instanceof RGB) {
			// Color color = new Color(null,
			// (RGB) value);
			// newColor = OspTWUIFrame.ColorToString(color);
			// color.dispose();
			// color = null;
			// } else
			// newColor = DEFAULT_COLOR;
			//
			// node.setBGColor(newColor);
			// } else if (id.equals(TWFrameNode.PROPERTY_LEFTTEXT)) {
			// if (value instanceof Integer)
			// text = "::" + stringId[(Integer) value];
			// else
			// text = (String) value;
			//
			// node.setLeftText(text);
			// } else if (id.equals(TWFrameNode.PROPERTY_CENTERTEXT)) {
			// if (value instanceof Integer)
			// text = "::" + stringId[(Integer) value];
			// else
			// text = (String) value;
			//
			// node.setCenterText(text);
			// } else if (id.equals(TWFrameNode.PROPERTY_RIGHTTEXT)) {
			// if (value instanceof Integer)
			// text = "::" + stringId[(Integer) value];
			// else
			// text = (String) value;
			//
			// node.setRightText(text);
			// } else if (id.equals(TWFrameNode.PROPERTY_BSHOWVALUESTATE)) {
			// int index = (Integer) value;
			// if(index < 0)
			// return;
			// node.setShowValueState((Integer) value);
			// } else if (id.equals(TWFrameNode.PROPERTY_EDITORFGCOLOR)) {
			// Color newColor;
			// if (value instanceof RGB)
			// newColor = new Color(null, (RGB) value);
			// else
			// newColor = new Color(null, 255, 255, 255);
			//
			// node.setEditorFgColor(newColor);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
