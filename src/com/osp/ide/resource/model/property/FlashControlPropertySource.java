package com.osp.ide.resource.model.property;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.FileDialogCellEditor;
import com.osp.ide.resource.common.FilePropertyDescriptor;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FLASHCONTROL_INFO;
import com.osp.ide.resource.resinfo.Layout;

public class FlashControlPropertySource extends OspNodePropertySource {

	public FlashControlPropertySource(Flash node) {
		this.node = node;

		FLASHCONTROL_INFO info = node.getItem().clone();
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
				FrameNode.PROPERTY_STYLE, "Flash Style", cszStyle[STYLE_FLASHCONTROL]));
		StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
		properties.add(StyleDescriptor);
		
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

		String[] filter = { "*.swf" };
		PropertyDescriptor LocalFilePathDescriptor = new FilePropertyDescriptor(
				FrameNode.PROPERTY_LOCALFILEPATH, "Local File Path", filter);
		LocalFilePathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(LocalFilePathDescriptor);

		PropertyDescriptor QualityModeDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_QUALITY, "Quality", cszQuality));
		QualityModeDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(QualityModeDescriptor);

		PropertyDescriptor RepeatModeDescriptor = (new ComboBoxPropertyDescriptor(
				FrameNode.PROPERTY_REPEATMODE, "Repeat Mode", cszRepeatMode));
		RepeatModeDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(RepeatModeDescriptor);

		PropertyDescriptor UrlFilePathDescriptor = (new TextPropertyDescriptor(
				FrameNode.PROPERTY_URLFILEPATH, "Url File Path"));
		UrlFilePathDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
		properties.add(UrlFilePathDescriptor);

	}

	@Override
	public Object getPropertyValue(Object id) {
		Flash node = (Flash) this.node;
		String urlPath = "";

        int minY = getMinY(node);
		if (id.equals(FrameNode.PROPERTY_RENAME)) {
			return node.getName();
		} else if (id.equals(FrameNode.PROPERTY_MODE))
			return node.getLayout().mode;
		else if (id.equals(FrameNode.PROPERTY_PARENT))
			return getControlIndex(node.getParentId());
		else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			return FrameNode.getStyleIndex(STYLE_FLASHCONTROL,
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
		else if (id.equals(FrameNode.PROPERTY_URLFILEPATH)) {
			urlPath = node.getUrlFilePath();

			if (urlPath.indexOf("::") < 0)
				return urlPath;
			else
				return getStringIndex(urlPath.replace("::", ""));
		} else if (id.equals(FrameNode.PROPERTY_LOCALFILEPATH)) {
			File file = node.getLocalFilePath();
			if (file == null)
				return FileDialogCellEditor.NONE;
			else
				return file.getName();
		} else if (id.equals(FrameNode.PROPERTY_QUALITY)) {
			return node.getQuality();
		} else if (id.equals(FrameNode.PROPERTY_REPEATMODE)) {
			return node.getRepeatMode();
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
		}

		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
		FLASHCONTROL_INFO info = (FLASHCONTROL_INFO) this.info;
		Flash node = (Flash) this.node;

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
		} else if (id.equals(FrameNode.PROPERTY_LOCALFILEPATH)) {
			File file;
			String path = node.getParent().GetProjectDirectory();
			if (path == null || path.isEmpty())
				file = null;
			else if (info.localFilePath == null || info.localFilePath.isEmpty())
				file = null;
			else {
				file = new File(path + info.localFilePath);
				if (!file.exists())
					file = null;
			}
			node.setLocalFilePath(file);
		} else if (id.equals(FrameNode.PROPERTY_URLFILEPATH)) {
			node.setUrlFilePath(info.urlFilePath);
		} else if (id.equals(FrameNode.PROPERTY_QUALITY)) {
			node.setRepeatMode(info.quality);
		} else if (id.equals(FrameNode.PROPERTY_REPEATMODE)) {
			node.setRepeatMode(info.repeatMode);
		}
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if(isValidate(id, value) == false)
			return;

		Flash node = (Flash) this.node;
		String urlPath = "";

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
						"FlashControlPropertySource.setPropertyValue()", id
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
						"FlashControlPropertySource.setPropertyValue()", id
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
						"FlashControlPropertySource.setPropertyValue()", id
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
						"FlashControlPropertySource.setPropertyValue()", id
								+ " NumberFormatException - " + e.getMessage(), e);
			}
		} else if (id.equals(FrameNode.PROPERTY_STYLE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			String style = cszStyle[STYLE_FLASHCONTROL][(Integer) value];
			node.setStyle(style);
		} else if (id.equals(FrameNode.PROPERTY_LOCALFILEPATH)) {
			File file = new File((String) value);
			String name = file.getName();
			if (name.equals(FileDialogCellEditor.NONE))
				file = null;
			node.setLocalFilePath(file);
		} else if (id.equals(FrameNode.PROPERTY_URLFILEPATH)) {
			if (value instanceof Integer)
				urlPath = "::" + stringId[(Integer) value];
			else
				urlPath = (String) value;

			node.setUrlFilePath(urlPath);
		} else if (id.equals(FrameNode.PROPERTY_QUALITY)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setQuality(cszQuality[(Integer) value]);
		} else if (id.equals(FrameNode.PROPERTY_REPEATMODE)) {
			int index = (Integer) value;
			if (index < 0)
				return;
			node.setRepeatMode(cszRepeatMode[(Integer) value]);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
		}
	}

}
