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
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class FormFramePropertySource extends OspNodePropertySource {

	public FormFramePropertySource(Form node) {
		this.node = node;

		FORMFRAME_INFO info = node.getItem().clone();
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

        PropertyDescriptor indicatorLayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_INDICATORLAYOUTSTYLE, 
                "Show Indicator", BOOL));
        indicatorLayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(indicatorLayoutStyleDescriptor);

        PropertyDescriptor OptionKeyLayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_OPTIONKEYLAYOUTSTYLE, 
                "Show Optionkey", BOOL));
        OptionKeyLayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(OptionKeyLayoutStyleDescriptor);

        PropertyDescriptor SoftKey0LayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_SOFTKEY0LAYOUTSTYLE, 
                "Show Softkey0", BOOL));
        SoftKey0LayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(SoftKey0LayoutStyleDescriptor);

        PropertyDescriptor SoftKey1LayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_SOFTKEY1LAYOUTSTYLE, 
                "Show Softkey1", BOOL));
        SoftKey1LayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(SoftKey1LayoutStyleDescriptor);

        PropertyDescriptor TextTabLayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_TABLAYOUTSTYLE, 
                "Show Tab", sShowTab));
        TextTabLayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(TextTabLayoutStyleDescriptor);

        PropertyDescriptor TitleLayoutStyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_TITLELAYOUTSTYLE, 
                "Show Title", BOOL));
        TitleLayoutStyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(TitleLayoutStyleDescriptor);

        Form node = (Form) this.node;
        File icon = node.getTitleIcon();

        if (icon == null || !icon.exists()) {
            PropertyDescriptor HalignDescriptor = 
                (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_HALIGN, 
                    "Title Text Align", cszHAlign));
            HalignDescriptor.setCategory(FrameNode.GROUP_STYLE);
            properties.add(HalignDescriptor);
        }

        PropertyDescriptor BgColorDescriptor = 
            (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_BGCOLOR, 
                "Background Color"));
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

        PropertyDescriptor NameDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_RENAME, "ID"));
        NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(NameDescriptor);

        PropertyDescriptor OrientationDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_ORIENTATION, 
                "Orientation Mode", cszOrientation));
        OrientationDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(OrientationDescriptor);

        PropertyDescriptor SoftKey0NIconDescriptor = 
            (new FilePropertyDescriptor(FrameNode.PROPERTY_SOFTKEY0NICON, 
                "Softkey 0 Normal Icon Path"));
        SoftKey0NIconDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey0NIconDescriptor);

        PropertyDescriptor SoftKey0PIconDescriptor = 
            (new FilePropertyDescriptor(FrameNode.PROPERTY_SOFTKEY0PICON, 
                "Softkey 0 Pressed Icon Path"));
        SoftKey0PIconDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey0PIconDescriptor);

        PropertyDescriptor SoftKey1TextDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_SOFTKEY0TEXT, 
                "Softkey 0 Text", stringId));
        SoftKey1TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey1TextDescriptor);

        PropertyDescriptor SoftKey1NIconDescriptor = 
            (new FilePropertyDescriptor(FrameNode.PROPERTY_SOFTKEY1NICON, 
                "Softkey 1 Normal Icon Path"));
        SoftKey1NIconDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey1NIconDescriptor);

        PropertyDescriptor SoftKey1PIconDescriptor = 
            (new FilePropertyDescriptor(FrameNode.PROPERTY_SOFTKEY1PICON, 
                "Softkey 1 Pressed Icon Path"));
        SoftKey1PIconDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey1PIconDescriptor);

        PropertyDescriptor SoftKey2TextDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_SOFTKEY1TEXT, 
                "Softkey 1 Text", stringId));
        SoftKey2TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(SoftKey2TextDescriptor);

        PropertyDescriptor TitleIconDescriptor = 
            (new FilePropertyDescriptor(FrameNode.PROPERTY_TITLEICON, 
                "Title Icon Path"));
        TitleIconDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TitleIconDescriptor);

        PropertyDescriptor TitleDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_FRAMETITLE, 
                "Title Text", stringId));
        TitleDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TitleDescriptor);

    }

	@Override
	public Object getPropertyValue(Object id) {
		Form node = (Form) this.node;

		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_TYPE))
			return "WINDOW_" + cszCtlType[node.getType()];
		else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			return FrameNode.getStyleIndex(STYLE_FORM, node.getLayout().style);
		} else if (id.equals(FrameNode.PROPERTY_FRAMETITLE)) {
			text = node.getTitle();
			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}

		} else if (id.equals(FrameNode.PROPERTY_TITLEICON)) {
			File file = node.getTitleIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			return node.getHAlign();
		} else if (id.equals(FrameNode.PROPERTY_HEADERHEIGHT)) {
			return Integer.toString(node.getHeaderHeight());
		} else if (id.equals(FrameNode.PROPERTY_ORIENTATION))
			return node.getOrientation();
		else if (id.equals(FrameNode.PROPERTY_SOFTKEY0TEXT)) {
			text = node.getSoftkey0Text();
			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1TEXT)) {
			text = node.getSoftkey1Text();
			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0NICON)) {
			File file = node.getSoftKey0NIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1NICON)) {
			File file = node.getSoftKey1NIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0PICON)) {
			File file = node.getSoftKey0PIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1PICON)) {
			File file = node.getSoftKey1PIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_INDICATORLAYOUTSTYLE)) {
			return node.getIndicatorLayoutStyle();
		} else if (id.equals(FrameNode.PROPERTY_TITLELAYOUTSTYLE)) {
			return node.getTitleLayoutStyle();
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0LAYOUTSTYLE)) {
			return node.getSoftkey0LayoutStyle();
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1LAYOUTSTYLE)) {
			return node.getSoftkey1LayoutStyle();
		} else if (id.equals(FrameNode.PROPERTY_OPTIONKEYLAYOUTSTYLE)) {
			return node.getOptionkeyLayoutStyle();
        } else if (id.equals(FrameNode.PROPERTY_TABLAYOUTSTYLE)) {
            return node.getTabLayoutStyle();
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			return Integer.toString(node.getBGColorTransparency());
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			if (node.getBgColor().equals(DEFAULT_COLOR))
				return DEFAULT_COLOR;
			return OspResourceManager.FormatRGB(node.getBgColor()).getRGB();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}
		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		FORMFRAME_INFO info = (FORMFRAME_INFO) this.info;
		Form node = (Form) this.node;

		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			node.reName(info.Id);
		} else if (id.equals(FrameNode.PROPERTY_TYPE)) {
			node.setType(info.type);
		} else if (id.equals(FrameNode.PROPERTY_PARENT)) {
			node.setParentId(info.pID);
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			node.setStyle(info.GetLayout(node.getModeIndex()).style);
		} else if (id.equals(FrameNode.PROPERTY_FRAMETITLE)) {
			node.setTitle(info.title);
		} else if (id.equals(FrameNode.PROPERTY_TITLEICON)) {
			File file;
			String path = node.GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.titleIcon == null || info.titleIcon.isEmpty())
				file = null;
			else {
				file = new File(path + info.titleIcon);
				if (!file.exists())
					file = null;
			}
			node.setTitleIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			node.setHAlign(info.hAlign);
		} else if (id.equals(FrameNode.PROPERTY_HEADERHEIGHT)) {
			node.setHeaderHeight(info.headerHeight);
		} else if (id.equals(FrameNode.PROPERTY_ORIENTATION)) {
			node.setOrientation(info.orientation);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0TEXT)) {
			node.setSoftkey0Text(info.softkey0);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1TEXT)) {
			node.setSoftkey1Text(info.softkey1);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0NICON)) {
			File file;
			String path = node.GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.softkey0NIcon == null || info.softkey0NIcon.isEmpty())
				file = null;
			else {
				file = new File(path + info.softkey0NIcon);
				if (!file.exists())
					file = null;
			}
			node.setSoftKey0NIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1NICON)) {
			File file;
			String path = node.GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.softkey1NIcon == null || info.softkey1NIcon.isEmpty())
				file = null;
			else {
				file = new File(path + info.softkey1NIcon);
				if (!file.exists())
					file = null;
			}
			node.setSoftKey1NIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0PICON)) {
			File file;
			String path = node.GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.softkey0PIcon == null || info.softkey0PIcon.isEmpty())
				file = null;
			else {
				file = new File(path + info.softkey0PIcon);
				if (!file.exists())
					file = null;
			}
			node.setSoftKey0PIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1PICON)) {
			File file;
			String path = node.GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.softkey1PIcon == null || info.softkey1PIcon.isEmpty())
				file = null;
			else {
				file = new File(path + info.softkey1PIcon);
				if (!file.exists())
					file = null;
			}
			node.setSoftKey1PIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_INDICATORLAYOUTSTYLE)) {
			if (info.GetLayout(node.getModeIndex()).style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) > 0)
				node.setIndicatorLayoutStyle(BOOL_TRUE);
			else
				node.setIndicatorLayoutStyle(BOOL_FALSE);
		} else if (id.equals(FrameNode.PROPERTY_TITLELAYOUTSTYLE)) {
			if (info.GetLayout(node.getModeIndex()).style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) > 0)
				node.setTitleLayoutStyle(BOOL_TRUE);
			else
				node.setTitleLayoutStyle(BOOL_FALSE);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0LAYOUTSTYLE)) {
			if (info.GetLayout(node.getModeIndex()).style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) > 0)
				node.setSoftkey0LayoutStyle(BOOL_TRUE);
			else
				node.setSoftkey0LayoutStyle(BOOL_FALSE);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1LAYOUTSTYLE)) {
			if (info.GetLayout(node.getModeIndex()).style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) > 0)
				node.setSoftkey1LayoutStyle(BOOL_TRUE);
			else
				node.setSoftkey1LayoutStyle(BOOL_FALSE);
		} else if (id.equals(FrameNode.PROPERTY_OPTIONKEYLAYOUTSTYLE)) {
			if (info.GetLayout(node.getModeIndex()).style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) > 0)
				node.setOptionkeyLayoutStyle(BOOL_TRUE);
			else
				node.setOptionkeyLayoutStyle(BOOL_FALSE);
        } else if (id.equals(FrameNode.PROPERTY_TABLAYOUTSTYLE)) {
            if (info.GetLayout(node.getModeIndex()).style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) >= 0) {
                node.setTabLayoutStyle(SHOWTAB_TEXT);
            } else if (info.GetLayout(node.getModeIndex()).style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) >= 0) {
                node.setTabLayoutStyle(SHOWTAB_ICON);
            } else {
                node.setTabLayoutStyle(SHOWTAB_NONE);
            }
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			node.setBGColorTransparency(info.bgColorOpacity);
		} else if (id.equals(FrameNode.PROPERTY_BGCOLOR)) {
			node.setBgColor(info.bg);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Form node = (Form) this.node;

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
			String style = node.getLayout().style;
			style = cszStyle[STYLE_FORM][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_FRAMETITLE)) {
			if (value instanceof Integer)
				node.setTitle("::" + stringId[(Integer) value]);
			else
				node.setTitle((String) value);
		} else if (id.equals(FrameNode.PROPERTY_TITLEICON)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.GetProjectDirectory();
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
							"FormFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setTitleIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setHAlign(cszHAlign[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_HEADERHEIGHT)) {
			try {
				Integer headerheight = Integer.parseInt((String) value);
				node.setHeaderHeight(headerheight);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"FormFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_ORIENTATION)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setOrientation((Integer) value);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0TEXT)) {
			if (value instanceof Integer)
				node.setSoftkey0Text("::" + stringId[(Integer) value]);
			else
				node.setSoftkey0Text((String) value);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1TEXT)) {
			if (value instanceof Integer)
				node.setSoftkey1Text("::" + stringId[(Integer) value]);
			else
				node.setSoftkey1Text((String) value);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0NICON)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.GetProjectDirectory();
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
							"FormFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setSoftKey0NIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1NICON)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.GetProjectDirectory();
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
							"FormFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setSoftKey1NIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0PICON)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.GetProjectDirectory();
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
							"FormFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setSoftKey0PIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1PICON)) {
			String fileName = (String) value;
			if(fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
				String path = node.GetProjectDirectory();
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
							"FormFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setSoftKey1PIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_INDICATORLAYOUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setIndicatorLayoutStyle((Integer) value);
		} else if (id.equals(FrameNode.PROPERTY_TITLELAYOUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setTitleLayoutStyle((Integer) value);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY0LAYOUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setSoftkey0LayoutStyle((Integer) value);
		} else if (id.equals(FrameNode.PROPERTY_SOFTKEY1LAYOUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setSoftkey1LayoutStyle((Integer) value);
		} else if (id.equals(FrameNode.PROPERTY_OPTIONKEYLAYOUTSTYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setOptionkeyLayoutStyle((Integer) value);
        } else if (id.equals(FrameNode.PROPERTY_TABLAYOUTSTYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            node.setTabLayoutStyle(index);
		} else if (id.equals(FrameNode.PROPERTY_BGOPACITY)) {
			try {
				Integer Transparency = Integer.parseInt((String) value);
				if (Transparency <= 100 && Transparency >= 0)
					node.setBGColorTransparency(Transparency);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"FormFramePropertySource.setPropertyValue()", id
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

			node.setBgColor(newColor);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
