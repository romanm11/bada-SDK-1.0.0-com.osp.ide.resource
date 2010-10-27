package com.osp.ide.resource.documents;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.graphics.Point;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.common.VersionMngXmlStore;
import com.osp.ide.resource.resinfo.BUTTON_INFO;
import com.osp.ide.resource.resinfo.CHECK_INFO;
import com.osp.ide.resource.resinfo.COLORPICKER_INFO;
import com.osp.ide.resource.resinfo.CUSTOMLIST_INFO;
import com.osp.ide.resource.resinfo.DATEPICKER_INFO;
import com.osp.ide.resource.resinfo.EDITAREA_INFO;
import com.osp.ide.resource.resinfo.EDITFIELD_INFO;
import com.osp.ide.resource.resinfo.EXPANDABLELIST_INFO;
import com.osp.ide.resource.resinfo.FLASHCONTROL_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.GROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.ICONLIST_INFO;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resinfo.LIST_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.OVERLAYPANEL_INFO;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resinfo.PANEL_INFO;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.resinfo.PROGRESS_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resinfo.SLIDABLEGROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.SLIDABLELIST_INFO;
import com.osp.ide.resource.resinfo.SLIDER_INFO;
import com.osp.ide.resource.resinfo.TIMEPICKER_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspPopupMarkup extends OspMarkup implements FrameConst {
	private static final String ELEMENT_NAME = "ScenePopup";

	private OspPopup m_Popup;

	public OspPopupMarkup() {
		super();
	}

	public OspPopupMarkup(OspPopup popup) {
		super();
		m_Popup = popup;
		m_project = m_Popup.m_manager.m_project;
	}

	public void setFileName(String fileName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				m_project);
		String name = m_myFile.GetFileName(fileName);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return;
		IFile file = project.getFolder(view.getResourceDir()).getFolder(
				m_Popup.screen).getFile(name);

		m_file = file;
	}

	public boolean loadXML() {
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return false;
		if (m_file == null && m_Popup != null && m_Popup.m_infoPopup != null
				&& m_Popup.m_infoPopup.fileName != null
				&& !m_Popup.m_infoPopup.fileName.isEmpty()) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(m_project);
			String name = m_myFile.GetFileName(m_Popup.m_infoPopup.fileName);
			IFile file = project.getFolder(view.getResourceDir()).getFolder(
					m_Popup.screen).getFile(name);
			m_file = file;
		}
		return loadXML(m_file);
	}

	/**
	 * @return
	 */
	private static IProject getProject(String project) {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(project);
	}

	public boolean loadXML(String fileName) {
		if (fileName == null || fileName.isEmpty())
			return false;

		IProject project = getProject(m_project);
		String name = m_myFile.GetFileName(fileName);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return false;
		IFile file = project.getFolder(view.getResourceDir()).getFolder(
				m_Popup.screen).getFile(name);
		m_file = file;

		return loadXML(file);
	}

	public boolean loadXML(IFile file) {
		if (file == null || !file.exists())
			return false;

		InputStream stream = null;
		try {
			stream = file.getContents();
		} catch (CoreException e1) {
			IProject project = getProject(m_project);
			if (project != null) {
				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, null);
					stream = file.getContents();
				} catch (CoreException e) {
					Activator.setErrorMessage("OspFormMarkup.loadXML(IFile)", e
							.getLocalizedMessage(), e);
					return false;
				}
			} else {
				Activator.setErrorMessage("OspFormMarkup.loadXML(IFile)", e1
						.getLocalizedMessage(), e1);
				return false;
			}
		}
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			String path = "file://";
			String folder = Activator.getDefault().getResourceLocationURL(
					DTD_FOLDER).getFile();
			path += folder + DTD_NAME;
			File temp = new File(folder + DTD_NAME);
			if (temp == null || !temp.exists())
				temp.createNewFile();
			Document dom = db.parse(stream, path);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				m_dVersion = getAttrValue(root, cszAttribName[DVERSION]);
				// System.out.println("Language : " + m_language);

				if (getPopupInfo(root) == false)
					return false;
				getContainersInfo(root, m_Popup.m_infoPopup);
				getScrollPanelInfo(root, m_Popup.m_infoPopup);
				getControlsInfo(root, m_Popup.m_infoPopup);
			} else
				return false;

		} catch (ParserConfigurationException e) {
			return false;
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspPopupMarkup.loadXML(IFile)", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	/**
	 * @param root
	 * @param mInfoPopup
	 */
	private void getContainersInfo(Element root, POPUPFRAME_INFO mInfoPopup) {
		ITEM_INFO info = null;
		for (Node node = root.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			int index = getTagIndex(node.getNodeName());
			info = newContainerItemInfo(index);
			if (info == null || info instanceof OVERLAYPANEL_INFO)
				continue;

			info.Id = getAttrValue(node, cszAttribName[ID]);
			String pid = getAttrValue(node, cszAttribName[PARENT]);
			if (m_Popup.m_infoPopup.oldId != null
					&& !m_Popup.m_infoPopup.oldId.isEmpty()
					&& m_Popup.m_infoPopup.oldId.equals(pid))
				info.pID = m_Popup.m_infoPopup.Id;
			else
				info.pID = pid;

			getControlInfo(node, info);

			if (m_Popup.m_items.get(info.pID) instanceof SCROLLPANEL_INFO) {
				SCROLLPANEL_INFO panel = ((SCROLLPANEL_INFO) m_Popup.m_items
						.get(info.pID));
				String panelId = panel.panelId;
				info.pID = panelId;

				Hashtable<String, OspPanel> panels = m_Popup.m_manager.m_Panels
						.get(m_Popup.screen);
				if (panels == null)
					continue;
				OspPanel panelFrame = panels.get(panelId);
				if (panelFrame != null
						&& panelFrame.m_items.get(info.Id) == null) {
					panelFrame.m_items.put(info.Id, info);
					ITEM_INFO pInfo = panelFrame.m_infoPanel;
					if (pInfo.children.isEmpty())
						pInfo.children = "|" + info.Id + "|";
					else
						pInfo.children += info.Id + "|";
				}
				continue;
			}
			if (info.Id != null && !info.Id.isEmpty()) {
				m_Popup.m_items.put(info.Id, info);
			}
			insertChildId(info.pID, info.Id);
		}
	}

	/**
	 * @param root
	 * @param mInfoPopup
	 */
	private void getControlsInfo(Element root, POPUPFRAME_INFO mInfoPopup) {
		ITEM_INFO info = null;
		for (Node node = root.getFirstChild(); node != null; node = node
				.getNextSibling()) {
			int index = getTagIndex(node.getNodeName());
			info = newControlItemInfo(index);
			if (info == null)
				continue;

			info.Id = getAttrValue(node, cszAttribName[ID]);
			String pid = getAttrValue(node, cszAttribName[PARENT]);
			if (m_Popup.m_infoPopup.oldId != null
					&& !m_Popup.m_infoPopup.oldId.isEmpty()
					&& m_Popup.m_infoPopup.oldId.equals(pid))
				info.pID = m_Popup.m_infoPopup.Id;
			else
				info.pID = pid;

			getControlInfo(node, info);

			if (m_Popup.m_items.get(info.pID) instanceof SCROLLPANEL_INFO) {
				SCROLLPANEL_INFO panel = ((SCROLLPANEL_INFO) m_Popup.m_items
						.get(info.pID));
				String panelId = panel.panelId;
				info.pID = panelId;

				Hashtable<String, OspPanel> panels = m_Popup.m_manager.m_Panels
						.get(m_Popup.screen);
				if (panels == null)
					continue;
				OspPanel panelFrame = panels.get(panelId);
				if (panelFrame != null
						&& panelFrame.m_items.get(info.Id) == null) {
					panelFrame.m_items.put(info.Id, info);
					ITEM_INFO pInfo = panelFrame.m_infoPanel;
					if (pInfo.children.isEmpty())
						pInfo.children = "|" + info.Id + "|";
					else
						pInfo.children += info.Id + "|";
				}
				continue;
			}
			if (info.Id != null && !info.Id.isEmpty()) {
				m_Popup.m_items.put(info.Id, info);
			}
			insertChildId(info.pID, info.Id);
		}
	}

	void insertChildId(String pID, String id) {
		StringBuilder s;
		ITEM_INFO pInfo;
		if (m_Popup.m_infoPopup.Id.equals(pID))
			pInfo = m_Popup.m_infoPopup;
		else
			pInfo = m_Popup.m_items.get(pID);

		if (pInfo == null)
			return;

		if (pInfo.children.isEmpty()) {
			s = new StringBuilder("|");
			s.append(id);
			s.append("|");
			pInfo.children = s.toString();
		} else if (pInfo.children.indexOf(id) < 0) {
			s = new StringBuilder(pInfo.children);
			s.append(id);
			s.append("|");
			pInfo.children = s.toString();
		}
	}

	public void removeChildId(String pID, String id) {
		StringBuilder s;
		ITEM_INFO pInfo;
		if (m_Popup.m_infoPopup.Id.equals(pID))
			pInfo = m_Popup.m_infoPopup;
		else
			pInfo = m_Popup.m_items.get(pID);

		if (pInfo != null) {
			s = new StringBuilder(pInfo.children);
			StringBuilder localId = new StringBuilder(id);
			localId.append('|');

			while (s.indexOf(localId.toString()) > 0)
				s.delete(s.indexOf(localId.toString()), s.indexOf(localId
						.toString())
						+ localId.length());
			pInfo.children = s.toString();
		}
	}

	void replaceChildId(String pID, String oldId, String id) {
		ITEM_INFO pInfo;
		if (m_Popup.m_infoPopup.Id.equals(pID))
			pInfo = m_Popup.m_infoPopup;
		else
			pInfo = m_Popup.m_items.get(pID);

		if (pInfo == null)
			return;

		if (pInfo.children.indexOf(oldId) > 0)
			pInfo.children = pInfo.children.replace(oldId, id);
	}

	public static String getDVersion(IProject project, File file) {
		String dVersion = "";
		if (project == null || file == null || !file.exists())
			return dVersion;

		String name = file.getName();
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return dVersion;
		String parent = file.getParentFile().getName();
		IFile ifile = project.getFolder(view.getResourceDir())
				.getFolder(parent).getFile(name);

		Document dom;
		InputStream stream = null;
		try {
			stream = ifile.getContents();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// path : DTD path
			String path = "file://";
			String folder = Activator.getDefault().getResourceLocationURL(
					DTD_FOLDER).getFile();
			path += folder + DTD_NAME;
			File temp = new File(folder + DTD_NAME);
			if (temp == null || !temp.exists())
				temp.createNewFile();

			dom = db.parse(stream, path);
			// DTD path : path where is identical with file
			// Document dom = db.parse(file.getLocation().toOSString());

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				dVersion = getAttrValue(root, cszAttribName[DVERSION]);
			}
		} catch (CoreException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IProject)", e
					.getLocalizedMessage(), e);
		} catch (ParserConfigurationException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IProject)", e
					.getLocalizedMessage(), e);
		} catch (SAXException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IProject)", e
					.getLocalizedMessage(), e);
		} catch (IOException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IProject)", e
					.getLocalizedMessage(), e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage(
						"OspPopupMarkup.getDVersion(IProject)", "finally] "
								+ e.getClass() + " - " + e.getMessage(), e);
			}
		}
		return dVersion;
	}

	public static String getDVersion(IFile file) {
		String dVersion = "";
		if (file == null || !file.exists())
			return dVersion;

		InputStream stream = null;
		try {
			stream = file.getContents();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			// path : DTD path
			String path = "file://";
			String folder = Activator.getDefault().getResourceLocationURL(
					DTD_FOLDER).getFile();
			path += folder + DTD_NAME;
			File temp = new File(folder + DTD_NAME);
			if (temp == null || !temp.exists())
				temp.createNewFile();

			Document dom = db.parse(stream, path);
			// DTD path : path where is identical with file
			// Document dom = db.parse(file.getLocation().toOSString());

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				dVersion = getAttrValue(root, cszAttribName[DVERSION]);
			}
		} catch (CoreException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IFile)", e
					.getLocalizedMessage(), e);
		} catch (ParserConfigurationException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IFile)", e
					.getLocalizedMessage(), e);
		} catch (SAXException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IFile)", e
					.getLocalizedMessage(), e);
		} catch (IOException e) {
			Activator.setErrorMessage("OspPopupMarkup.getDVersion(IFile)", e
					.getLocalizedMessage(), e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspPopupMarkup.getDVersion(IFile)",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}
		return dVersion;
	}

	public String getDVersion() {
		return m_dVersion;
	}

	private void getScrollPanelInfo(Node root, POPUPFRAME_INFO popup) {
		for (Node scrollPanel = root.getFirstChild(); scrollPanel != null; scrollPanel = scrollPanel
				.getNextSibling()) {
			int index = getTagIndex(scrollPanel.getNodeName());
			if (index == WINDOW_SCROLLPANEL) {
				NamedNodeMap namedMap = scrollPanel.getAttributes();
				if (namedMap == null)
					continue;
				int value;
				String s;

				SCROLLPANEL_INFO info = new SCROLLPANEL_INFO();
				info.type = WINDOW_SCROLLPANEL;

				info.Id = getAttrValue(scrollPanel, cszAttribName[ID]);
				info.pID = getAttrValue(scrollPanel, cszAttribName[PARENT]);

				for (Node node = scrollPanel.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					index = getAttrIndex(node.getNodeName());
					Layout layout;
					switch (index) {
					case PROPERTY:
						info.panelId = getAttrValue(node,
								cszAttribName[PANELID]);

						s = getAttrValue(node, cszAttribName[BGCOLOR]);
						if (s == null || s.isEmpty())
							s = DEFAULT_COLOR;
						else
							info.bgColor = s;

						s = getAttrValue(node, cszAttribName[BGCOLOROPACITY]);
						try {
							value = Integer.parseInt(s);
						} catch (NumberFormatException e) {
							value = 0;
						}
						info.bgColorOpacity = value;
						break;
					case PANELLAYOUT:
						layout = new Layout();
						layout.mode = getAttrValue(node, cszAttribName[MODE]);
						if (layout.mode.isEmpty())
							layout.mode = cszFrmMode[DEFAULT];
						s = getAttrValue(node, cszAttribName[X]);
						if (s != null && !s.isEmpty())
							layout.x = Integer.parseInt(s);
						s = getAttrValue(node, cszAttribName[Y]);
						if (s != null && !s.isEmpty())
							layout.y = Integer.parseInt(s);
						s = getAttrValue(node, cszAttribName[WIDTH]);
						if (s != null && !s.isEmpty())
							layout.width = Integer.parseInt(s);
						s = getAttrValue(node, cszAttribName[HEIGHT]);
						if (s != null && !s.isEmpty())
							layout.height = Integer.parseInt(s);

						info.panelLayout.put(layout.mode, layout);
						break;
					case LAYOUT:
						getLayout(node, info);
						break;
					}
				}

				Enumeration<String> keys = info.layout.keys();
				while (keys.hasMoreElements()) {
					String key = keys.nextElement();
					Layout layout = info.panelLayout.get(key);
					if (layout == null) {
						layout = new Layout();
						layout.mode = key;
						info.panelLayout.put(layout.mode, layout);
					}
					if (layout.width <= 0)
						layout.width = info.GetLayout(layout.mode).width;
					if (layout.height <= 0)
						layout.height = info.GetLayout(layout.mode).height + 100;
				}

				if (info.panelId != null && !info.panelId.isEmpty()) {
					PANELFRAME_INFO panel = m_Popup.m_manager.InsertPanel(info,
							m_Popup.screen);
					panel.addParentId(info.Id);
					panel.bgColor = info.bgColor;
					panel.bgColorOpacity = info.bgColorOpacity;
					panel.formColor = DEFAULT_COLOR;
				}

				m_Popup.m_items.put(info.Id, info);
				insertChildId(info.pID, info.Id);
			}
		}
	}

	private boolean getPopupInfo(Node root) {
		for (Node form = root.getFirstChild(); form != null; form = form
				.getNextSibling()) {
			int index = getTagIndex(form.getNodeName());
			if (index == WINDOW_POPUP) {
				NamedNodeMap namedMap = form.getAttributes();
				if (namedMap == null)
					continue;
				POPUPFRAME_INFO info = m_Popup.m_infoPopup;

				info.type = WINDOW_POPUP;
				info.Id = getAttrValue(form, cszAttribName[ID]);
				File file = new File(m_Popup.m_infoPopup.fileName);
				String s = file.getName().substring(0,
						file.getName().indexOf('.'));
				if (!info.Id.equals(s)) {
					info.oldId = info.Id;
					info.Id = s;
				}

				for (Node node = form.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					index = getAttrIndex(node.getNodeName());
					Layout layout;
					switch (index) {
					case PROPERTY:
						info.titleText = getAttrValue(node,
								cszAttribName[TITLETEXT]);
						break;
					case LAYOUT:
						layout = new Layout();
						layout.mode = getAttrValue(node, cszAttribName[MODE]);
						if (layout.mode.isEmpty())
							layout.mode = cszFrmMode[DEFAULT];
						layout.style = getAttrValue(node, cszAttribName[STYLE]);

						int left = 19,
						top = 25;
						ResourceExplorer view = ResourceExplorer
								.getResourceView();
						if (view != null) {
							Tag_info tag = view.getImageInfo(cszTag[info.type],
									m_Popup.screen);
							if (tag != null) {
								left = Integer.parseInt(tag.temp1);
								top = Integer.parseInt(tag.temp2);
							}
						}
						s = getAttrValue(node, cszAttribName[X]);
						if (s != null && !s.isEmpty())
							layout.x = Integer.parseInt(s);

						s = getAttrValue(node, cszAttribName[Y]);
						if (s != null && !s.isEmpty()) {
							layout.y = Integer.parseInt(s);
						}

						s = getAttrValue(node, cszAttribName[WIDTH]);
						if (s != null && !s.isEmpty())
							layout.width = Integer.parseInt(s) - left;

						s = getAttrValue(node, cszAttribName[HEIGHT]);
						if (s != null && !s.isEmpty())
							layout.height = Integer.parseInt(s) - top;

						info.layout.put(layout.mode, layout);
					}
				}
				if (m_Popup.m_infoPopup.Id != null
						&& !m_Popup.m_infoPopup.Id.isEmpty())
					return true;
			}
		}
		return false;
	}

	protected void getLayout(Node node, ITEM_INFO info) {
		Layout layout = new Layout();
		String s;

		layout.mode = getAttrValue(node, cszAttribName[MODE]);
		if (layout.mode.isEmpty())
			layout.mode = cszFrmMode[DEFAULT];

		layout.style = getAttrValue(node, cszAttribName[STYLE]);

		Point minSize = new Point(92, 72);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view != null) {
			Tag_info tag = view.getImageInfo(cszTag[info.type], m_Popup.screen);
			if (tag != null && tag.minSize != null) {
				minSize = new Point(tag.minSize.x, tag.minSize.y);
				if (info instanceof EDITFIELD_INFO && layout.isSmall()) {
					try {
						minSize.y -= tag.tSize - 4; // 30, 14
					} catch (NumberFormatException e) {
					}
				} else if (info instanceof EDITFIELD_INFO
						&& info.ShowTitleText.equals(BOOL[BOOL_TRUE])) {
					try {
						minSize.y += Integer.parseInt(tag.temp1); // 26, 14
					} catch (NumberFormatException e) {
					}
				} else if (info instanceof CHECK_INFO
						&& info.ShowTitleText.equals(BOOL[BOOL_TRUE])) {
					try {
						minSize.y += Integer.parseInt(tag.temp1); // 34, 18
					} catch (NumberFormatException e) {
					}
				} else if (info instanceof SLIDER_INFO
						&& info.ShowTitleText.equals(BOOL[BOOL_TRUE])) {
					try {
						minSize.y += Integer.parseInt(tag.temp1); // 34, 18
					} catch (NumberFormatException e) {
					}
				} else if (info instanceof COLORPICKER_INFO
						&& layout.mode.equals(cszFrmMode[LANDCAPE])) {
					minSize.x = Integer.parseInt(tag.temp1);
				}

				if (info instanceof CHECK_INFO
						&& layout.style.indexOf("DIVIDER") > 0) {
					try {
						minSize.x += Integer.parseInt(tag.temp2); // 34, 18
					} catch (NumberFormatException e) {
					}
				}
			}
		}

		s = getAttrValue(node, cszAttribName[X]);
		if (s != null && !s.isEmpty())
			layout.x = Integer.parseInt(s);

		if (layout.x < 0)
			layout.x = 0;

		s = getAttrValue(node, cszAttribName[Y]);
		if (s != null && !s.isEmpty()) {
			layout.y = Integer.parseInt(s);
			int minY = 0;
			if (info.pID.equals(m_Popup.m_infoPopup.Id))
				minY = m_Popup.m_manager.getPopupMinY(m_Popup.m_infoPopup,
						layout.mode);
			layout.y += minY;
		}

		if (layout.y < 0)
			layout.y = 0;

		s = getAttrValue(node, cszAttribName[WIDTH]);
		if (s != null && !s.isEmpty())
			layout.width = Integer.parseInt(s);

		if (layout.width < minSize.x)
			layout.width = minSize.x;

		s = getAttrValue(node, cszAttribName[HEIGHT]);
		if (s != null && !s.isEmpty())
			layout.height = Integer.parseInt(s);

		if (layout.height < minSize.y)
			layout.height = minSize.y;

		if (info instanceof COLORPICKER_INFO || info instanceof DATEPICKER_INFO
				|| info instanceof TIMEPICKER_INFO) {
			layout.width = minSize.x;
			layout.height = minSize.y;
		}

		s = getAttrValue(node, cszAttribName[MAXDROPLINECOUNT]);
		if (s != null && !s.isEmpty())
			layout.maxDropLineCount = Integer.parseInt(s);

		layout.dock = getAttrValue(node, cszAttribName[DOCK]);
		layout.fit = getAttrValue(node, cszAttribName[FIT]);

		info.layout.put(layout.mode, layout);
	}

	public static boolean isPopupXML(IFile file) {
		Document dom;
		InputStream stream = null;
		try {
			stream = file.getContents();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			String path = "file://";
			String folder = Activator.getDefault().getResourceLocationURL(
					DTD_FOLDER).getFile();
			path += folder + DTD_NAME;
			File temp = new File(folder + DTD_NAME);
			if (temp == null || !temp.exists())
				temp.createNewFile();
			dom = db.parse(stream, path);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME))
				return true;
			else
				return false;
		} catch (CoreException e) {
			return false;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspPopupMarkup.isPopupXML(IFile)", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}
	}

	/**
	 * @param project
	 * @param file
	 * @return
	 */
	public static boolean isPopupXML(String project, File file) {
		// TODO Auto-generated method stub
		return isPopupXML(getProject(project), file);
	}

	public static boolean isPopupXML(IProject project, File file) {
		String name = file.getName();
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return false;
		String parent = file.getParentFile().getName();
		IFile ifile = project.getFolder(view.getResourceDir())
				.getFolder(parent).getFile(name);

		Document dom;
		InputStream stream = null;
		try {
			stream = ifile.getContents();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			String path = "file://";
			String folder = Activator.getDefault().getResourceLocationURL(
					DTD_FOLDER).getFile();
			path += folder + DTD_NAME;
			File temp = new File(folder + DTD_NAME);
			if (temp == null || !temp.exists())
				temp.createNewFile();
			dom = db.parse(stream, path);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME))
				return true;
			else
				return false;
		} catch (CoreException e) {
			return false;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (SAXException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage(
						"OspPopupMarkup.isPopupXML(IProject)", e.getClass()
								+ " - " + e.getMessage(), e);
			}
		}
	}

	public boolean storeXML() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		ByteArrayInputStream stream = null;
		try { // get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// create an instance of DOM
			Document dom = db.newDocument();
            appandComment(dom, dom, MODIFY_WORNING);
            
			Element rootEle = dom.createElement(ELEMENT_NAME);
			dom.appendChild(rootEle);
			rootEle.setAttribute(cszAttribName[DVERSION],
					VersionMngXmlStore.CUR_DVERSION);
			rootEle.setAttribute(cszAttribName[BVERSION], m_bVersion);
			appendPopup(dom, rootEle);
			appandChildren(dom, rootEle, m_Popup.m_infoPopup.children);

			String buff = write(dom);
			if (buff == null)
				return false;

			if (m_file == null) {
				setFileName(m_Popup.m_infoPopup.fileName);
			}

			stream = new ByteArrayInputStream(buff.getBytes("UTF-8"));
			if (m_file.getParent() != null) {
				IPath path = m_file.getParent().getLocation();
				File folder = new File(path.toOSString());
				if (folder != null && !folder.exists()) {
					folder.mkdir();
					m_file.getProject().refreshLocal(IResource.DEPTH_INFINITE,
							null);
				}
			}

			if (m_file.exists()) {
				try {
					ResourceAttributes attr = m_file.getResourceAttributes();
					attr.setReadOnly(false);
					m_file.setResourceAttributes(attr);

					m_file.setContents(stream, IResource.FORCE,
							new NullProgressMonitor()); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.setErrorMessage("OspPopupMarkup.storeXML()", e
							.getClass()
							+ " - " + e.getMessage(), e);
				}
			} else {
				m_file.create(stream, IResource.FORCE,
						new NullProgressMonitor()); //$NON-NLS-1$				
			}
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspPopupMarkup.storeXML()",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}
		return true;
	}

	/**
	 * @param dom
	 * @param rootEle
	 */
	private void appandChildren(Document dom, Element rootEle, String children) {
		Enumeration<String> keys = getKeys(children, false, false).elements();
		while (keys.hasMoreElements()) {
			String id = keys.nextElement();
			ITEM_INFO info = m_Popup.m_items.get(id);

			if (info == null)
				continue;

			appandChild(dom, rootEle, info, false);
			appandChildren(dom, rootEle, info.children);
		}
	}

	private void appendPopup(Document dom, Element rootEle) {
		POPUPFRAME_INFO info = m_Popup.m_infoPopup;

		Element form = dom.createElement(cszTag[WINDOW_POPUP]);
		form.setAttribute(cszAttribName[ID], info.Id);

		Element property = dom.createElement(cszAttribName[PROPERTY]);
		property.setAttribute(cszAttribName[TITLETEXT], info.titleText);

		form.appendChild(property);

		int left = 19, top = 25;
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view != null) {
			Tag_info tag = view.getImageInfo(cszTag[info.type], m_Popup.screen);
			if (tag != null) {
				left = Integer.parseInt(tag.temp1);
				top = Integer.parseInt(tag.temp2);
			}
		}

		Enumeration<String> keys = null;
		keys = info.layout.keys();
		for (int i = 0; keys.hasMoreElements(); i++) {
			Element elementLayout = dom.createElement(cszAttribName[LAYOUT]);
			Layout layout = info.layout.get(keys.nextElement());
			elementLayout.setAttribute(cszAttribName[MODE], layout.mode);
			elementLayout.setAttribute(cszAttribName[STYLE], layout.style);
			elementLayout.setAttribute(cszAttribName[X], "0");
			elementLayout.setAttribute(cszAttribName[Y], "0");
			elementLayout.setAttribute(cszAttribName[WIDTH], Integer
					.toString(layout.width + left));
			elementLayout.setAttribute(cszAttribName[HEIGHT], Integer
					.toString(layout.height + top));

			form.appendChild(elementLayout);
		}

		rootEle.appendChild(form);
	}

	private Element appandChild(Document dom, Element parent, ITEM_INFO info,
			boolean isScrollPanel) {
		Element element = dom.createElement(cszTag[info.type]);
		element.setAttribute(cszAttribName[ID], info.Id);
		element.setAttribute(cszAttribName[PARENT], info.pID);

		appandChildInfo(dom, element, parent, info);
		appandLayout(dom, element, info, isScrollPanel);

		parent.appendChild(element);
		if (info instanceof SCROLLPANEL_INFO) {
			String panelId = ((SCROLLPANEL_INFO) info).panelId;
			if (panelId == null || panelId.isEmpty())
				return element;
			Hashtable<String, OspPanel> panels = m_Popup.m_manager.m_Panels
					.get(m_Popup.screen);
			if (panels == null)
				return element;
			OspPanel panel = panels.get(panelId);
			if (panel == null)
				return element;
			Enumeration<String> keys = panel.getIdList().elements();
			while (keys.hasMoreElements()) {
				String id = keys.nextElement();
				ITEM_INFO item = panel.m_items.get(id);

				appendChild(dom, parent, item, info.Id, true);
			}
		}
		return element;
	}

	private Element appendChild(Document dom, Element parent, ITEM_INFO info,
			String pId, boolean isScrollPanel) {
		Element element = dom.createElement(cszTag[info.type]);
		element.setAttribute(cszAttribName[ID], info.Id);
		element.setAttribute(cszAttribName[PARENT], pId);

		appandChildInfo(dom, element, parent, info);
		appandLayout(dom, element, info, isScrollPanel);

		parent.appendChild(element);
		if (info instanceof SCROLLPANEL_INFO) {
			String panelId = ((SCROLLPANEL_INFO) info).panelId;
			if (panelId == null || panelId.isEmpty())
				return element;
			Hashtable<String, OspPanel> panels = m_Popup.m_manager.m_Panels
					.get(m_Popup.screen);
			if (panels == null)
				return element;
			OspPanel panel = panels.get(panelId);
			if (panel == null)
				return element;
			Enumeration<String> keys = panel.getIdList().elements();
			while (keys.hasMoreElements()) {
				String id = keys.nextElement();
				ITEM_INFO item = panel.m_items.get(id);

				appendChild(dom, parent, item, info.Id, true);
			}
		}
		return element;
	}

	private void appandChildInfo(Document dom, Element element, Element parent,
			ITEM_INFO info) {
		switch (info.type) {
		case WINDOW_BUTTON:
			BUTTON_INFO buttonInfo = (BUTTON_INFO) info;
			appandButtonInfo(dom, element, buttonInfo);
			break;
		case WINDOW_CHECKBUTTON:
			CHECK_INFO checkInfo = (CHECK_INFO) info;
			appandCheckInfo(dom, element, checkInfo);
			break;
		case WINDOW_COLORPICKER:
			COLORPICKER_INFO colorpickerInfo = (COLORPICKER_INFO) info;
			appandColorPickerInfo(dom, element, colorpickerInfo);
			break;
		case WINDOW_CUSTOMLIST:
			CUSTOMLIST_INFO customInfo = (CUSTOMLIST_INFO) info;
			appandCustomListInfo(dom, element, customInfo);
			break;
		case WINDOW_DATEPICKER:
		case WINDOW_EDITDATE:
			DATEPICKER_INFO datepickerInfo = (DATEPICKER_INFO) info;
			appandDatePickerInfo(dom, element, datepickerInfo);
			break;
		case WINDOW_EDITAREA:
			EDITAREA_INFO editareaInfo = (EDITAREA_INFO) info;
			appandEditAreaIfno(dom, element, editareaInfo);
			break;
		case WINDOW_EDITFIELD:
			EDITFIELD_INFO editfieldInfo = (EDITFIELD_INFO) info;
			appandEditFieldInfo(dom, element, editfieldInfo);
			break;
		case WINDOW_EXPANDABLELIST:
			EXPANDABLELIST_INFO expandablelistInfo = (EXPANDABLELIST_INFO) info;
			appandExpandableListInfo(dom, element, expandablelistInfo);
			break;
		case WINDOW_FLASHCONTROL:
			FLASHCONTROL_INFO flashcontrol = (FLASHCONTROL_INFO) info;
			appandFlashInfo(dom, element, flashcontrol);
			break;
		case WINDOW_GROUPEDLIST:
			GROUPEDLIST_INFO groupedlistinfo = (GROUPEDLIST_INFO) info;
			appandGroupedListInfo(dom, element, groupedlistinfo);
			break;
		case WINDOW_ICONLIST:
			ICONLIST_INFO iconlistinfo = (ICONLIST_INFO) info;
			appandIconListInfo(dom, element, iconlistinfo);
			break;
		case WINDOW_LABEL:
			LABEL_INFO labelInfo = (LABEL_INFO) info;
			appandLabelIfno(dom, element, labelInfo);
			break;
		case WINDOW_LIST:
			LIST_INFO listInfo = (LIST_INFO) info;
			appandListInfo(dom, element, listInfo);
			break;
		// case WINDOW_OVERLAYPANEL:
		// OVERLAYPANEL_INFO overlaypanelinfo = (OVERLAYPANEL_INFO) info;
		// appandOverlayPanelInfo(dom, element, overlaypanelinfo);
		// break;
		case WINDOW_PANEL:
			PANEL_INFO panelinfo = (PANEL_INFO) info;
			appandPanelInfo(dom, element, panelinfo);
			break;
		case WINDOW_PROGRESS:
			PROGRESS_INFO progressInfo = (PROGRESS_INFO) info;
			appandProgressInfo(dom, element, progressInfo);
			break;
		case WINDOW_SCROLLPANEL:
			SCROLLPANEL_INFO scrollpanelInfo = (SCROLLPANEL_INFO) info;
			appandScrollPanelInfo(dom, element, scrollpanelInfo);
			break;
		case WINDOW_SLIDABLEGROUPEDLIST:
			SLIDABLEGROUPEDLIST_INFO sglistInfo = (SLIDABLEGROUPEDLIST_INFO) info;
			appandSGListInfo(dom, element, sglistInfo);
			break;
		case WINDOW_SLIDABLELIST:
			SLIDABLELIST_INFO slistInfo = (SLIDABLELIST_INFO) info;
			appandSListInfo(dom, element, slistInfo);
			break;
		case WINDOW_SLIDER:
			SLIDER_INFO slideInfo = (SLIDER_INFO) info;
			appandSliderInfo(dom, element, slideInfo);
			break;
		case WINDOW_TIMEPICKER:
		case WINDOW_EDITTIME:
			TIMEPICKER_INFO timepickerInfo = (TIMEPICKER_INFO) info;
			appandTimepickerInfo(dom, element, timepickerInfo);
			break;
		default:
			break;
		}
	}

	private void appandLayout(Document dom, Element parent, ITEM_INFO info,
			boolean isScrollPanel) {
		Enumeration<String> keys = null;
		keys = info.layout.keys();
		for (int i = 0; keys.hasMoreElements(); i++) {
			Element element = dom.createElement(cszAttribName[LAYOUT]);
			Layout layout = info.layout.get(keys.nextElement());
			if (layout == null)
				continue;

			element.setAttribute(cszAttribName[MODE], layout.mode);

			if (info.type == WINDOW_FORM || info.type == WINDOW_SPIN
					|| info.type == WINDOW_EDITFIELD
					|| info.type == WINDOW_EDITTIME
					|| info.type == WINDOW_PROGRESS || info.type == WINDOW_LIST
					|| info.type == WINDOW_CUSTOMLIST
					|| info.type == WINDOW_ICONLIST
					|| info.type == WINDOW_FLASHCONTROL
					|| info.type == WINDOW_EXPANDABLELIST
					|| info.type == WINDOW_GROUPEDLIST
					|| info.type == WINDOW_SLIDABLEGROUPEDLIST
					|| info.type == WINDOW_SLIDABLELIST
					|| info.type == WINDOW_TAB
					|| info.type == WINDOW_CHECKBUTTON)
				element.setAttribute(cszAttribName[STYLE], layout.style);

			int minY = 0;
			if (info.pID.equals(m_Popup.m_infoPopup.Id))
				minY = m_Popup.m_manager.getPopupMinY(m_Popup.m_infoPopup,
						layout.mode);

			if (layout.dock.isEmpty() || layout.dock.equals(cszDock[DOCK_NONE])) {
				element.setAttribute(cszAttribName[X], Integer
						.toString(layout.x));
				if (isScrollPanel)
					element.setAttribute(cszAttribName[Y], Integer
							.toString(layout.y));
				else
					element.setAttribute(cszAttribName[Y], Integer
							.toString(layout.y - minY));
			} else
				element.setAttribute(cszAttribName[DOCK], layout.dock);

			if (layout.fit.isEmpty()) {
				element.setAttribute(cszAttribName[WIDTH], Integer
						.toString(layout.width));
				element.setAttribute(cszAttribName[HEIGHT], Integer
						.toString(layout.height));
			} else
				element.setAttribute(cszAttribName[FIT], layout.fit);
			parent.appendChild(element);
		}
	}

	private void appandScrollPanelInfo(Document dom, Element parent,
			SCROLLPANEL_INFO info) {
		String s;
		Element element = dom.createElement(cszAttribName[PROPERTY]);
		Hashtable<String, OspPanel> panels = m_Popup.m_manager.m_Panels
				.get(m_Popup.screen);
		if (panels == null)
			return;
		OspPanel panel = panels.get(info.panelId);
		if (panel != null) {
			element.setAttribute(cszAttribName[PANELID], info.panelId);
			if (panel.m_infoPanel.bgColor.equals(DEFAULT_COLOR))
				element.setAttribute(cszAttribName[BGCOLOR], "");
			else
				element.setAttribute(cszAttribName[BGCOLOR],
						panel.m_infoPanel.bgColor);
			s = Integer.toString(panel.m_infoPanel.bgColorOpacity);
			element.setAttribute(cszAttribName[BGCOLOROPACITY], s);

			parent.appendChild(element);

			com.osp.ide.resource.resinfo.PANELFRAME_INFO panelInfo = panel.m_infoPanel;
			Enumeration<String> layoutkeys = panelInfo.layout.keys();
			for (int i = 0; layoutkeys.hasMoreElements(); i++) {
				String mode = layoutkeys.nextElement();
				Layout layout = panelInfo.layout.get(mode);
				if (layout == null)
					continue;
				Element elementLayout = dom
						.createElement(cszAttribName[PANELLAYOUT]);
				elementLayout.setAttribute(cszAttribName[MODE], layout.mode);

				elementLayout.setAttribute(cszAttribName[X], Integer
						.toString(layout.x));
				elementLayout.setAttribute(cszAttribName[Y], Integer
						.toString(layout.y));

				elementLayout.setAttribute(cszAttribName[WIDTH], Integer
						.toString(layout.width));
				elementLayout.setAttribute(cszAttribName[HEIGHT], Integer
						.toString(layout.height));

				parent.appendChild(elementLayout);
			}
		} else {
			if (info.bgColor.equals(DEFAULT_COLOR))
				element.setAttribute(cszAttribName[BGCOLOR], "");
			else
				element.setAttribute(cszAttribName[BGCOLOR], info.bgColor);
			s = Integer.toString(info.bgColorOpacity);
			element.setAttribute(cszAttribName[BGCOLOROPACITY], s);

			parent.appendChild(element);
		}
	}

	public Vector<String> getKeys(String children, boolean isReverse,
			boolean isPrint) {

		Vector<String> v = m_Popup.getIdList(children);

		if (isReverse)
			Collections.reverse(v);

		if (isPrint) {
			StringBuilder s;
			Enumeration<String> keys = v.elements();
			while (keys.hasMoreElements()) {
				String id = keys.nextElement();

				s = new StringBuilder("Key : ");
				s.append(id);
				System.out.println(s.toString());
			}
		}
		return v;
	}

	public Vector<String> getSortedKeys(boolean isReverse, boolean isPrint) {
		Vector<String> v = new Vector<String>(m_Popup.m_items.keySet());
		Collections.sort(v);

		if (isReverse)
			Collections.reverse(v);

		if (isPrint) {
			StringBuilder s;
			Enumeration<String> keys = v.elements();
			while (keys.hasMoreElements()) {
				String id = keys.nextElement();

				s = new StringBuilder("Key : ");
				s.append(id);
				System.out.println(s.toString());
			}
		}
		return v;
	}

	public IFile getFile() {
		return m_file;
	}

}
