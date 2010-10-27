package com.osp.ide.resource.model.property;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.EditableComboPropertyDescriptor;
import com.osp.ide.resource.common.FileDialogCellEditor;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class PopupFramePropertySource extends OspNodePropertySource {

	public PopupFramePropertySource(Popup node) {
		this.node = node;

		POPUPFRAME_INFO info = node.getItem().clone();
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
		
		PropertyDescriptor WidthDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_WIDTH, "Width"));
		WidthDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
		properties.add(WidthDescriptor);

		PropertyDescriptor NameDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_RENAME, "ID"));
		NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(NameDescriptor);

		PropertyDescriptor TitleTextDescriptor = (new EditableComboPropertyDescriptor(
				FrameNode.PROPERTY_TITLETEXT, "Title Text", stringId));
		TitleTextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(TitleTextDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		Popup node = (Popup) this.node;

        int left = 19, top = 25;
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null) {
            Tag_info tag = view.getImageInfo(cszTag[info.type], node
                    .getScreen());
            if(tag != null) {
                left = Integer.parseInt(tag.temp1);
                top = Integer.parseInt(tag.temp2);
            }
        }
		String text = "";
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_WIDTH))
			return Integer.toString(node.getLayout().width + left);
		else if (id.equals(FrameNode.PROPERTY_HEIGHT))
			return Integer.toString(node.getLayout().height + top);
		else if (id.equals(FrameNode.PROPERTY_TYPE))
			return "WINDOW_" + cszCtlType[node.getType()];
		else if (id.equals(FrameNode.PROPERTY_TITLEICON)) {
			File file = node.getTitleIcon();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_BITMAP)) {
			File file = node.getBitmap();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			text = node.getTitleText();

			if (text.indexOf("::") < 0)
				return text;
			else {
				return getStringIndex(text.replace("::", ""));
			}
		}
		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		POPUPFRAME_INFO info = (POPUPFRAME_INFO) this.info;
		Popup node = (Popup) this.node;

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
		} else if (id.equals(FrameNode.PROPERTY_TITLEICON)) {
			File file;
			String path = node.editor.m_Popup.getProjectDirectory();
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
		} else if (id.equals(FrameNode.PROPERTY_BITMAP)) {
			File file;
			String path = node.editor.m_Popup.getProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.bitmap == null || info.bitmap.isEmpty())
				file = null;
			else {
				file = new File(path + info.bitmap);
				if (!file.exists())
					file = null;
			}
			node.setBitmap(file);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			node.setTitleText(info.titleText);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Popup node = (Popup) this.node;
		Point size = Activator.getSScreenToPoint(node.getScreen());

        int left = 19, top = 25;
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null) {
            Tag_info tag = view.getImageInfo(cszTag[info.type], node
                    .getScreen());
            if(tag != null) {
                left = Integer.parseInt(tag.temp1);
                top = Integer.parseInt(tag.temp2);
            }
        }
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
						"PopupFramePropertySource.setPropertyValue()", id
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
						"PopupFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
			try {
				Layout rect = node.getLayout();
				Integer pointwidth = Integer.parseInt((String) value);
				rect.width = pointwidth;
				if (rect.mode.equals(cszFrmMode[LANDCAPE])) {
					if (rect.width > size.y)
						rect.width = size.y;
				} else {
					if (rect.width > size.x)
						rect.width = size.x;
				}
				rect.width -= left;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PopupFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
			try {
				Layout rect = node.getLayout();
				Integer pointheight = Integer.parseInt((String) value);
				rect.height = pointheight;
				if (rect.mode.equals(cszFrmMode[LANDCAPE])) {
					if (rect.height > size.x)
						rect.height = size.x;
				} else {
					if (rect.height > size.y)
						rect.height = size.y;
				}
				rect.height -= top;
				node.setLayout(rect);
			} catch (NumberFormatException e) {
				Activator.setErrorMessage(
						"PopupFramePropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
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
							"PopupFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setTitleIcon(file);
		} else if (id.equals(FrameNode.PROPERTY_BITMAP)) {
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
							"PopupFramePropertySource.setPropertyValue()", id
									+ " ImageFormatException - "
									+ e.getMessage(), e);
					return;
				}
			}
			node.setBitmap(file);
		} else if (id.equals(FrameNode.PROPERTY_TITLETEXT)) {
			if (value instanceof Integer)
				text = "::" + stringId[(Integer) value];
			else
				text = (String) value;

			node.setTitleText(text);
		}
	}

}
