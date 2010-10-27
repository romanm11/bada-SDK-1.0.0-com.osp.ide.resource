package com.osp.ide.resource.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;

public class VersionMngXmlStore {
	public class UpdateInfo {
		public String name = "";
		public String op = "";
		public String value = "";
	}
	
	public class UpdateTagInfo {
		public UpdateInfo tagInfo = new UpdateInfo();
		public Vector<UpdateInfo> property = new Vector<UpdateInfo>();
		public Vector<UpdateInfo> layout = new Vector<UpdateInfo>();
	}
	
	public class UpdateList {
		String version = "";
		Vector<UpdateTagInfo> tagList = new Vector<UpdateTagInfo>();
		
		public String getVersion() {
			return version;
		}
		
		public int getTagSize() {
			return tagList.size();
		}
		
		public UpdateTagInfo get(int index) {
			if(tagList == null || index < 0 || index >= tagList.size())
				return null;
			return tagList.get(index);
		}
	}
	
	public static final String CUR_DVERSION = "20100701";
	public static final String DVERSION_LIST[] = { "2", "3", "20100701" };
	
	public static int getCurVersionIndex() {
		return getVersionIndex(CUR_DVERSION);
	}
	
	public static int getVersionIndex(String sVersion) {
		int index = -1;
		
		if(sVersion == null || sVersion.isEmpty())
			return index;
		
		for(int i=0; i<DVERSION_LIST.length; i++) {
			if(sVersion.equals(DVERSION_LIST[i])) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static final String TEMPLATE_FOLDER = "template/";
	private static final String FORMTEMPLATE_NAME = "version_mng.xml";

	public static final String ELEMENT_NAME = "VersionMng"; //$NON-NLS-1$
	
	public static final String ATTR_VERSION = "version";

	public static final String TAG_DVERSION = "DVersion"; //$NON-NLS-1$
	public static final String TAG = "tag"; //$NON-NLS-1$
	public static final String TAG_PROPERTY = "property"; //$NON-NLS-1$
	public static final String TAG_LAYOUT = "layout";

	public static final String ATTR_NAME = "name";
	public static final String ATTR_OP = "op";
	public static final String ATTR_VALUE = "value";

	public static final String OP_NONE = "NONE";
	public static final String OP_MODIFY = "MODIFY";
	public static final String OP_ADD = "ADD";
	public static final String OP_DELETE = "DELETE";
	
	private static VersionMngXmlStore versionMnger;

	private String filePath = "";

	private Hashtable<String, UpdateList> updateList = new Hashtable<String, UpdateList>();
	private static boolean exist = true;

	public static VersionMngXmlStore getVersionXmlStore() {
		if (versionMnger == null) {
			String path = Activator.getDefault().getResourceLocationURL(
					TEMPLATE_FOLDER + FORMTEMPLATE_NAME).getFile();
			versionMnger = new VersionMngXmlStore(path);
		}

		return versionMnger;
	}

	public static void reLoad(String path) {
		if (path == null || path.isEmpty()) {
			path = getVersionXmlStore().filePath;
		}

		getVersionXmlStore().loadXML(path);
	}
	
	public static Hashtable<String, UpdateList> getUpdateList() {
		return getVersionXmlStore().updateList;
	}

	public VersionMngXmlStore(String path) {
		if (path == null || path.isEmpty())
			filePath = Activator.getDefault().getResourceLocationURL(
					TEMPLATE_FOLDER + FORMTEMPLATE_NAME).getFile();
		else
			filePath = path;
		loadXML(filePath);
	}

	private boolean loadXML(String path) {
		if (path == null || path.length() == 0) {
			exist = false;
			return false;
		}

		FileInputStream stream = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				exist = false;
				return false;
			}

			stream = new FileInputStream(file);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(stream);
			
			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				NodeList base = root.getElementsByTagName(TAG_DVERSION);
				loadVersionInfo(base);
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(stream != null)
					stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	private void loadVersionInfo(NodeList base) {
		
		for (Node node = base.item(0); node != null; node = node
				.getNextSibling()) {
			String version = getAttrValue(node, ATTR_VERSION);
			if(version == null || version.isEmpty())
				continue;
			UpdateList versionInfo = new UpdateList();
			versionInfo.version = version;
			for(Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
				String name = subNode.getNodeName();
				
				if(name != null && name.equals(TAG)) {
					loadTagInfo(versionInfo, subNode);
				}
			}
			
			if(!updateList.contains(version))
				updateList.put(version, versionInfo);
		}
	}

	private void loadTagInfo(UpdateList versionInfo, Node node) {
		UpdateTagInfo tagInfo = new UpdateTagInfo();
		getTagInfo(node, tagInfo.tagInfo);
		for(Node subNode = node.getFirstChild(); subNode != null; subNode = subNode.getNextSibling()) {
			String name = subNode.getNodeName();
			
			if(name != null && name.equals(TAG_PROPERTY)) {
				UpdateInfo property = new UpdateInfo();
				getTagInfo(subNode, property);
				tagInfo.property.add(property);
			} else if(name != null && name.equals(TAG_LAYOUT)) {
				UpdateInfo layout = new UpdateInfo();
				getTagInfo(subNode, layout);
				tagInfo.layout.add(layout);
			}
		}
		versionInfo.tagList.add(tagInfo);
	}

	private void getTagInfo(Node node, UpdateInfo tagInfo) {
		tagInfo.name = getAttrValue(node, ATTR_NAME);
		tagInfo.op = getAttrValue(node, ATTR_OP);
		tagInfo.value = getAttrValue(node, ATTR_VALUE);
	}

	private String getAttrValue(Node node, String string) {
		if(node == null || node.getAttributes() == null)
			return "";
		Node item = node.getAttributes().getNamedItem(string);
		if (item == null)
			return "";
		return item.getNodeValue();
	}

	public static boolean isExists(boolean isErrDlgOpen) {
		if (!exist && isErrDlgOpen) {
			Shell shell = Display.getCurrent().getActiveShell();
			MessageDialog.openError(shell, FORMTEMPLATE_NAME,
					"Template folder or template XML file does not exist.\r\n\r\n"
							+ "The program may not behave as expected.");
		}
		return exist;
	}

	public static UpdateList getControl(int versionIndex) {
		if(versionIndex >= DVERSION_LIST.length)
			return null;
		
		String version = DVERSION_LIST[versionIndex];
		UpdateList control = getVersionXmlStore().updateList.get(version);
		return control;
	}

	public static UpdateList getControl(String version) {
		UpdateList control = getVersionXmlStore().updateList.get(version);
		return control;
	}
	
	public static void printInfo() {
		Hashtable<String, UpdateList> updateList = getUpdateList();
		Enumeration<String> keys = updateList.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			UpdateList update = updateList.get(key);
			
			System.out.println("Version : " + key + "]    Tag Size : " + update.getTagSize());
			for(int i=0; i<update.getTagSize(); i++) {
				UpdateTagInfo tag = update.get(i);
				System.out.print("Tag : " + tag.tagInfo.name + "] ");
				Vector<UpdateInfo> properties = tag.property;
				Vector<UpdateInfo> layouts = tag.layout;
				System.out.println("Property Size : " + properties.size() + ", Layout Size : " + layouts.size());
				for(int j=0; j<properties.size(); j++) {
					UpdateInfo property = properties.get(j);
					System.out.println("Property : " + property.name + ", " + property.op + ", " + property.value);
				}
				for(int j=0; j<layouts.size(); j++) {
					UpdateInfo layout = layouts.get(j);
					System.out.println("Layout : " + layout.name + ", " + layout.op + ", " + layout.value);
				}
			}
			System.out.println("");
		}
	}
}
