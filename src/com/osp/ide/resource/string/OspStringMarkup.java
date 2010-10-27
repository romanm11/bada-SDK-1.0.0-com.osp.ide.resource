package com.osp.ide.resource.string;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.LanguageData;
import com.osp.ide.resource.common.LanguageListXmlStore;
import com.osp.ide.resource.common.VersionMngXmlStore;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspStringMarkup implements StringConst {

	private static final String ELEMENT_NAME = "string_table";
    protected static final String MODIFY_WORNING = 
        "\tThis XML file was automatically generated by UiBuilder - do not modify by hand.";

	private IFile m_file = null;
	private String m_language = "";
	private String m_project = "";
	private Hashtable<String, String> m_text;

	protected String m_dVersion;
	protected String m_bVersion;
	
	public OspStringMarkup() {
		initInfo();
	}

	public OspStringMarkup(String sProject, String fileName) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				sProject);
		if (project == null)
			return;
		if (fileName.indexOf('.') < 0) {
			m_language = fileName;
			fileName += ".xml";
		} else {
			m_language = fileName.substring(0, fileName.indexOf('.'));
		}
		if (m_language.toUpperCase(Locale.getDefault()).equals(
				OspUIString.DEFAULT_OLDLANG_NAME))
			m_language = OspUIString.DEFAULT_LANG1;

		m_project = sProject;
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return;
		m_file = project.getFolder(view.getResourceDir()).getFile(fileName);
		// this.file = ResourcesPlugin.getWorkspace().getRoot().getFile(
		// new Path(FILE_NAME));

		initInfo();
		// clear();
	}

	private void initInfo() {
		m_text = new Hashtable<String, String>();
		m_bVersion = Activator.getBVersion();
	}

	public int getTagIndex(String tag) {
		for (int i = 0; i < STRING_TAG_NUM; i++) {
			if (tag.equals(cszTag[i]))
				return i;
		}
		return -1;
	}

	public int getAttrIndex(String attr) {
		for (int i = 0; i < STRING_ATTR_NUM; i++) {
			if (attr.equals(cszAttr[i]))
				return i;
		}
		return -1;
	}

	public String getAbsoluteDir() {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				m_project);
		if (project == null)
			return null;

		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return null;
		String osPath = project.getLocation().toFile().getAbsolutePath();
		osPath += view.getResourceDir();
		return osPath;
	}

	public boolean loadXML() {
		clear();
		if (m_file != null && !m_file.exists()) {
			storeXML();
		}

		if (m_file == null)
			return false;

		InputStream stream = null;
		try {
			stream = m_file.getContents();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(stream);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				// System.out.println("Language : " + m_language);
				String id = "", value = "";
				for (Node text = root.getFirstChild(); text != null; text = text
						.getNextSibling()) {
					int index = getTagIndex(text.getNodeName());
					if (index == TAG_TEXT) {
						value = text.getTextContent();
						id = text.getAttributes()
								.getNamedItem(cszAttr[ATTR_ID]).getNodeValue();
					}
					if (id != null && !id.isEmpty())
						m_text.put(id, value);
				}
			} else
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
				Activator.setErrorMessage("OspStringMarkup.loadXML()", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	public static boolean isStringXML(File file) {
		InputStream stream = null;
		Document dom;
		DocumentBuilder db;
		DocumentBuilderFactory dbf;
		try {
			stream = new FileInputStream(file);

			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();

			dom = db.parse(stream);
			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME))
				return true;
			else
				return false;
		} catch (FileNotFoundException e) {
			return false;
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
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspStringMarkup.isStringXML()", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}

	}

	private Element appendChild(Document dom, Element parent, String name,
			String id, String value) {
		Element element = dom.createElement(name);
		element.setAttribute(cszAttr[ATTR_ID], id);
		Text textNode = dom.createTextNode(value);
		element.appendChild(textNode);

		parent.appendChild(element);

		return element;
	}

    /**
     * @param dom
     * @param element
     * @param comment
     */
    protected void appandComment(Document dom, Document element, String comment) {
        String newLine = System.getProperty("line.separator", "\n");
        comment = newLine + comment + newLine;
        Comment node = dom.createComment(comment);
        
        element.appendChild(node);
    }

	public boolean storeXML() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		ByteArrayInputStream stream = null;
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// create an instance of DOM
			Document dom = db.newDocument();
            appandComment(dom, dom, MODIFY_WORNING);
            
			Element rootEle = dom.createElement(ELEMENT_NAME);
			dom.appendChild(rootEle);
			rootEle.setAttribute(FrameConst.cszAttribName[FrameConst.DVERSION],
					VersionMngXmlStore.CUR_DVERSION);
            rootEle.setAttribute(FrameConst.cszAttribName[FrameConst.BVERSION],
                    m_bVersion);

			// Enumeration<String> keys = getSortedKeys(false,
			// false).elements();
			Enumeration<String> keys = m_text.keys();
			while (keys.hasMoreElements()) {
				String id = keys.nextElement();
				String value = m_text.get(id);
				appendChild(dom, rootEle, cszTag[TAG_TEXT], id, value);
			}

			String buff = write(dom);
			if (buff == null)
				return false;

			stream = new ByteArrayInputStream(buff.getBytes("UTF-8"));

			oldFileCheck();

			if (m_file.exists()) {
				try {
					ResourceAttributes attr = m_file.getResourceAttributes();
					attr.setReadOnly(false);
					m_file.setResourceAttributes(attr);

					m_file.setContents(stream, IResource.FORCE,
							new NullProgressMonitor()); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.setErrorMessage("OspStringMarkup.storeXML()", e
							.getClass()
							+ " - " + e.getMessage(), e);
				}
			} else {
				m_file.create(stream, IResource.FORCE,
						new NullProgressMonitor()); //$NON-NLS-1$				
			}
		} catch (CoreException e) {
			return false;
		} catch (ParserConfigurationException e) {
			return false;
		} catch (UnsupportedEncodingException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspStringMarkup.storeXML()",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}
		return true;
	}

	private void oldFileCheck() {
		String fileName = m_file.getName().replace(
				'.' + m_file.getFileExtension(), "");
		if (fileName.toUpperCase(Locale.getDefault()).equals(
				OspUIString.DEFAULT_OLDLANG_NAME)) {
			try {
				m_file.delete(true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}

			ResourceExplorer view = ResourceExplorer.getResourceView();
			if (view == null)
				return;
			fileName = OspUIString.DEFAULT_LANG1 + ".xml";
			m_file = m_file.getProject().getFolder(view.getResourceDir())
					.getFile(fileName);
		}
	}

	private String write(Document dom) {
		TransformerFactory factory = TransformerFactory.newInstance();
		factory.setAttribute("indent-number", Integer.valueOf(4));

		StringWriter sw = null;
		try {
			sw = new StringWriter();
			Transformer transformer = factory.newTransformer();

			transformer.setOutputProperty(OutputKeys.METHOD, "xml"); //$NON-NLS-1$
			if (m_language.equals("kor-KR"))
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			else
				transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", String
							.valueOf(4));

			// transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
			// "UIForm.dtd");

			transformer.transform(new DOMSource(dom), new StreamResult(sw));

			return sw.getBuffer().toString();

		} catch (TransformerConfigurationException e) {
			Activator.setErrorMessage("OspStringMarkup.write()", e.getClass()
					+ " - " + e.getMessage(), e);
		} catch (TransformerException e) {
			Activator.setErrorMessage("OspStringMarkup.write()", e.getClass()
					+ " - " + e.getMessage(), e);
		} finally {
			try {
				if (sw != null)
					sw.close();
			} catch (IOException e) {
				Activator.setErrorMessage("OspStringMarkup.write()",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}

		return null;
	}

	public void clear() {
		initInfo();
	}

	public static int getLangIdToIndex(String id) {
		int index = -1;

        if(!LanguageListXmlStore.exists()) {
            Shell shell = Display.getCurrent().getActiveShell();
            MessageDialog.openError(shell, "Language List",
                    "Language list file does not exist.\r\n\r\n"
                            + "The program may not behave as expected.");
            return OspUIString.DEFAULT_LANG1INDEX;
        }
        
		ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore()
				.getLanguageList();
		for (int i = 0; i < cszLanguage.size(); i++) {
			if (id.equals(cszLanguage.get(i).getId())) {
				index = i;
				break;
			}
		}

		return index;
	}

	public static int getLangNameToIndex(String name) {
		int index = -1;

        if(!LanguageListXmlStore.exists()) {
            Shell shell = Display.getCurrent().getActiveShell();
            MessageDialog.openError(shell, "Language List",
                    "Language list file does not exist.\r\n\r\n"
                            + "The program may not behave as expected.");
            return OspUIString.DEFAULT_LANG1INDEX;
        }
        
		ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore()
				.getLanguageList();
		for (int i = 0; i < cszLanguage.size(); i++) {
			if (name.equals(cszLanguage.get(i).getName())) {
				index = i;
				break;
			}
		}

		return index;
	}

	public static String getLanguageName(String id) {
		int index = -1;

		if(!LanguageListXmlStore.exists()) {
            Shell shell = Display.getCurrent().getActiveShell();
            MessageDialog.openError(shell, "Language List",
                    "Language list file does not exist.\r\n\r\n"
                            + "The program may not behave as expected.");
            return OspUIString.DEFAULT_LANG1NAME;
		}
		
		ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore()
				.getLanguageList();
		for (int i = 0; i < cszLanguage.size(); i++) {
			if (id.equals(cszLanguage.get(i).getId())) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return null;

		return cszLanguage.get(index).getName();
	}

	public static String getLanguageId(String name) {
		int index = -1;

        if(!LanguageListXmlStore.exists()) {
            Shell shell = Display.getCurrent().getActiveShell();
            MessageDialog.openError(shell, "Language List",
                    "Language list file does not exist.\r\n\r\n"
                            + "The program may not behave as expected.");
            return OspUIString.DEFAULT_LANG1;
        }
        
		ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore()
				.getLanguageList();
		for (int i = 0; i < cszLanguage.size(); i++) {
			if (name.equals(cszLanguage.get(i).getName())) {
				index = i;
				break;
			}
		}
		if (index < 0)
			return null;

		return cszLanguage.get(index).getId();
	}

	public String getLanguage() {
		return m_language;
	}

	public int getLanguageIndex() {
		return getLangIdToIndex(m_language);
	}

	public boolean setText(String id, String value) {
		m_text.remove(id);

		m_text.put(id, value);
		return true;
	}

	public String insertText(String id, String value) {
		if (m_text.get(id) != null)
			return m_text.get(id);

		m_text.put(id, value);
		return value;
	}

	protected String getText(String id) {
		return m_text.get(id);
	}

	protected String getTextId(String text) {
		Enumeration<String> keys = m_text.keys();
		while (keys.hasMoreElements()) {
			String id = keys.nextElement();
			if (m_text.get(id).equals(text))
				return id;
		}
		return null;
	}

	public Vector<String> getSortedKeys(boolean isReverse, boolean isPrint) {
		Vector<String> v = new Vector<String>(m_text.keySet());
		Collections.sort(v);
		if (isReverse)
			Collections.reverse(v);

		if (isPrint) {
			StringBuilder s;
			Enumeration<String> keys = v.elements();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				String value = m_text.get(key);

				s = new StringBuilder("Key : ");
				s.append(key);
				s.append(", Value : ");
				s.append(value);
				System.out.println(s.toString());
			}
		}
		return v;
	}

	public Enumeration<String> getIds() {
		return m_text.keys();
	}

	public boolean replaceTextId(String oldId, String newId) {
		String value;
		if (m_text.get(oldId) == null || m_text.get(newId) != null)
			return false;

		value = m_text.remove(oldId);
		m_text.put(newId, value);
		return true;
	}

	public void deleteText(String id) {
		m_text.remove(id);
	}

	public IFile getFile() {
		return m_file;
	}

    /**
     * @param id
     * @param value
     * @param isOverWrite 
     */
    public void insertImportText(String id, String value, boolean isOverWrite) {
        if(isOverWrite) {
            if(m_text.get(id) != null)
                m_text.remove(id);
            m_text.put(id, value);
        } else {
            int i = 1;
            String originId = id;
            while (m_text.get(id) != null) {
                id = originId + i++;
            }
            m_text.put(id, value);
        }

    }
}