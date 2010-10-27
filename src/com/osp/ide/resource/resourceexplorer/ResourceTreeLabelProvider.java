/**
 * 
 */
package com.osp.ide.resource.resourceexplorer;

import org.eclipse.cdt.internal.ui.CPluginImages;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;

/**
 * @author bson
 * 
 */
@SuppressWarnings("restriction")
public class ResourceTreeLabelProvider extends LabelProvider {
	private static final Image IMG_CATEGORY = CPluginImages
			.get(CPluginImages.IMG_OBJS_SEARCHFOLDER);
	
	private static final Image IMG_DEFAULT = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/frame.png").createImage();
	private static final Image IMG_STRING = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/string.png").createImage();
	private static final Image IMG_PANEL = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/panel.png").createImage();
	private static final Image IMG_POPUP = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/popup.png").createImage();
	private static final Image IMG_MENU = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/menu.png").createImage();
	
	private static final Image IMG_DEFAULTSUB = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/frame_Sub.png").createImage();
	private static final Image IMG_STRINGSUB = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/menu_Sub.png").createImage();
	private static final Image IMG_PANELSUB = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/panel_outline.png").createImage();
    private static final Image IMG_PANELSUBGRAY = AbstractUIPlugin.imageDescriptorFromPlugin(
            Activator.PLUGIN_ID, "icons/panel_gray.png").createImage();
	private static final Image IMG_POPUPSUB = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/popup_outline.png").createImage();
	private static final Image IMG_MENUSUB = AbstractUIPlugin.imageDescriptorFromPlugin(
			Activator.PLUGIN_ID, "icons/menu_Sub.png").createImage();

	/**
	 * 
	 */
	public ResourceTreeLabelProvider() {
		super();
	}

	public String getText(Object element) {
		String node = ((TreeObject) element).getName();
		return node;
	}

	public Image getImage(Object element) {
		int kind = ((TreeObject) element).getKind();
		int depth = ((TreeObject) element).getDepth();
		Image image = null;

		if (depth == ResourceExplorer.DEPTH_ROOT) {
			switch (kind) {
			case ResourceExplorer.KIND_RESOURCE:
				image = IMG_CATEGORY;
				break;
			case ResourceExplorer.KIND_PANEL:
				image = IMG_PANEL;
				break;
			case ResourceExplorer.KIND_STRING:
				image = IMG_STRING;
				break;
			default:
				image = IMG_DEFAULT;
			}
			return image;
		} else if (depth == ResourceExplorer.DEPTH_GROUP) {
			switch (kind) {
			case ResourceExplorer.KIND_PANEL:
				image = IMG_PANEL;
				break;
			case ResourceExplorer.KIND_POPUP:
				image = IMG_POPUP;
				break;
			case ResourceExplorer.KIND_MENU:
				image = IMG_MENU;
				break;
			case ResourceExplorer.KIND_STRING:
				image = IMG_STRING;
				break;
			default:
				image = IMG_DEFAULT;
			}

			return image;
		} else {
			switch (kind) {
			case ResourceExplorer.KIND_PANEL:
				image = IMG_PANELSUB;
			    if(((TreeObject) element).info instanceof PANELFRAME_INFO) {
			        PANELFRAME_INFO panelInfo = (PANELFRAME_INFO) ((TreeObject) element).info;
			        if(panelInfo != null && panelInfo.parent.size() <= 0)
			            image = IMG_PANELSUBGRAY;
			    }
				break;
			case ResourceExplorer.KIND_POPUP:
				image = IMG_POPUPSUB;
				break;
			case ResourceExplorer.KIND_MENU:
				image = IMG_MENUSUB;
				break;
			case ResourceExplorer.KIND_STRING:
				image = IMG_STRINGSUB;
				break;
			default:
				image = IMG_DEFAULTSUB;
			}

			return image;
		}
	}

}
