package com.osp.ide.resource.editform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import com.osp.ide.resource.resinfo.FrameConst;

public class FormTemplateXmlStore {
	public static final String TEMPLATE_FOLDER = "template/";
//    public static final String TEMPLATE_FOLDER = "plugins/com.osp.ide.resource.template/";
	private static final String FORMTEMPLATE_NAME = "ui_builder_class_gen_template.xml";

	public static final String ELEMENT_NAME = "xml"; //$NON-NLS-1$

	public static final String TAG_BASE = "base"; //$NON-NLS-1$
	public static final String TAG_MACRO = "macro"; //$NON-NLS-1$
	public static final String TAG_VALUE = "value";
	public static final String TAG_HEADER = "header"; //$NON-NLS-1$
	public static final String TAG_SOURCE = "source"; //$NON-NLS-1$	
	public static final String TAG_METHOD = "method";
	public static final String TAG_SCOPE = "scope"; //$NON-NLS-1$	

	public static final String TAG_CLASSES = "classes"; //$NON-NLS-1$
	public static final String TAG_CLASS = "class"; //$NON-NLS-1$
	public static final String TAG_INTERFACE = "interface"; //$NON-NLS-1$

	public static final String TAG_INTERFACES = "interfaces"; //$NON-NLS-1$

	public static final String ATTR_VERSION = "version";
	public static final String ATTR_NAME = "name";
	public static final String ATTR_BASECLASS = "base_class";

	private static FormTemplateXmlStore formTemplate;

	private String filePath = "";

	private String m_Header = "";
	private String m_Source = "";

	private String m_MacroName;
	private String m_Macro = "";

	private String m_Version;
	private Hashtable<String, InterfaceList> classes = new Hashtable<String, InterfaceList>();
	private Hashtable<String, Interface> interfaces = new Hashtable<String, Interface>();
	private static boolean exist = true;

	public class Interface {
		String name = "";
//		String header = "";
//		String source = "";
		String scope = "";
		
		Vector<String> methodDecs = new Vector<String>();
		Vector<String> methodDefs = new Vector<String>();

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSource() {
            StringBuilder source = new StringBuilder();
            for(int i=0; i<methodDefs.size(); i++) {
                source.append(methodDefs.get(i));
            }
			return source.toString();
		}

//		public void setSource(String source) {
//			this.source = source;
//		}

		public String getHeader() {
		    StringBuilder header = new StringBuilder();
		    for(int i=0; i<methodDecs.size(); i++) {
		        header.append(methodDecs.get(i));
		    }
			return header.toString();
		}

//		public void setHeader(String header) {
//			this.header = header;
//		}
		
		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}
	}

	public static FormTemplateXmlStore getFormTemplateStore() {
		if (formTemplate == null) {
			String path = Activator.getDefault().getResourceLocationURL(
					TEMPLATE_FOLDER + FORMTEMPLATE_NAME).getFile();
//            String path = TEMPLATE_FOLDER + FORMTEMPLATE_NAME;
			formTemplate = new FormTemplateXmlStore(path);
		}

		return formTemplate;
	}

	public static void reLoad(String path) {
		if (path == null || path.isEmpty()) {
			path = getFormTemplateStore().filePath;
		}

		getFormTemplateStore().loadXML(path);
	}

	public FormTemplateXmlStore(String path) {
		if (path == null || path.isEmpty())
//          filePath = TEMPLATE_FOLDER + FORMTEMPLATE_NAME;
			filePath = Activator.getDefault().getResourceLocationURL(
					TEMPLATE_FOLDER + FORMTEMPLATE_NAME).getFile();
		else
			filePath = path;
		loadXML(filePath);
	}

	private boolean loadXML(String path) {
		clear();
		if (path == null || path.length() == 0) {
			exist = false;
			return false;
		}

		FileInputStream stream = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				exist = false;
	            Shell shell = Display.getCurrent().getActiveShell();
	            MessageDialog.openError(shell, "Form Template XML",
	                    "Form template file does not exist.\r\n\r\n"
	                            + "The program may not behave as expected.");
				return false;
			} else {
			    exist = true;
			}

			stream = new FileInputStream(file);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(stream);
			
			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				m_Version = root.getAttribute(ATTR_VERSION);

				NodeList base = root.getElementsByTagName(TAG_BASE);
				loadBase(base);

				NodeList classes = root.getElementsByTagName(TAG_CLASSES);
				loadClasses(classes);

				NodeList interfacees = root
						.getElementsByTagName(TAG_INTERFACES);
				loadInterfaces(interfacees);
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

	private void loadBase(NodeList base) {
		for (Node node = base.item(0).getFirstChild(); node != null; node = node
				.getNextSibling()) {
			String name = node.getNodeName();
			if (name.equals(TAG_MACRO)) {
				m_MacroName = getAttrValue(node, ATTR_NAME);
				for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode
						.getNextSibling()) {
					name = subNode.getNodeName();
					if (name.equals(TAG_VALUE)) {
						String value = subNode.getFirstChild().getNextSibling()
								.getNodeValue();
						m_Macro = value;
						// System.out.println(value);
					}
				}
			} else if (name.equals(TAG_HEADER)) {
				String value = node.getFirstChild().getNextSibling()
						.getNodeValue();
				m_Header = value;
				// System.out.println(value);
			} else if (name.equals(TAG_SOURCE)) {
				String value = node.getFirstChild().getNextSibling()
						.getNodeValue();
				m_Source = value;
				// System.out.println(value);
			}
		}

	}

	private void loadClasses(NodeList classes) {
		for (Node node = classes.item(0).getFirstChild(); node != null; node = node
				.getNextSibling()) {
			String name = node.getNodeName();
			if (name.equals(TAG_CLASS)) {
				InterfaceList control = new InterfaceList();
				control.setName(getAttrValue(node, ATTR_NAME));
				control.setBaseClass(getAttrValue(node, ATTR_BASECLASS));
				for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode
						.getNextSibling()) {
					name = subNode.getNodeName();
					if (name.equals(TAG_INTERFACE)) {
						control.getInterface().add(
								subNode.getFirstChild().getNodeValue());
					}
				}
				this.classes.put(control.getName(), control);
			}
		}
	}

	private void loadInterfaces(NodeList interfacees) {
		for (Node node = interfacees.item(0).getFirstChild(); node != null; node = node
				.getNextSibling()) {
			String name = node.getNodeName();
			if (name.equals(TAG_INTERFACE)) {
				Interface control = new Interface();
				control.setName(getAttrValue(node, ATTR_NAME));
				for (Node subNode = node.getFirstChild(); subNode != null; subNode = subNode
						.getNextSibling()) {
					name = subNode.getNodeName();
					if (name.equals(TAG_SCOPE)) {
						control.setScope(subNode.getFirstChild()
								.getNodeValue());
					} else if (name.equals(TAG_HEADER)) {
						Node nextNode = subNode.getFirstChild().getNextSibling();
//						control.setHeader(nextNode.getNodeValue());
//						nextNode = nextNode.getNextSibling();
						while (nextNode != null) {
							if (nextNode.getNodeName().equals(TAG_METHOD)) {
								control.methodDecs.add(nextNode.getFirstChild().getNextSibling().getNodeValue());
							}
							nextNode = nextNode.getNextSibling();
						}
					} else if (name.equals(TAG_SOURCE)) {
						Node nextNode = subNode.getFirstChild().getNextSibling();
//						control.setSource(nextNode.getNodeValue());
//						nextNode = nextNode.getNextSibling();
						while (nextNode != null) {
							if (nextNode.getNodeName().equals(TAG_METHOD)) {
								control.methodDefs.add(nextNode.getFirstChild().getNextSibling().getNodeValue());
							}
							nextNode = nextNode.getNextSibling();
						}
					}
				}
				this.interfaces.put(control.getName(), control);
			}
		}
	}

	private String getAttrValue(Node node, String string) {
		Node item = node.getAttributes().getNamedItem(string);
		if (item == null)
			return "";
		return item.getNodeValue();
	}

	public void clear() {
	}

	public static String getVersion() {
		return getFormTemplateStore().m_Version;
	}

	public static String getHeader() {
		return getFormTemplateStore().m_Header;
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

	public static String getSource() {
		return getFormTemplateStore().m_Source;
	}

	public static String getMacroName() {
		return getFormTemplateStore().m_MacroName;
	}

	public static String getMacro() {
		return getFormTemplateStore().m_Macro;
	}

	public static Interface getInterface(String name) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		return interFace;
	}

	public static int getMethodsNum(String name) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return 0;
//check assert for 	interFace.methodDecs.size() == interFace.methodDefs.size();
		return interFace.methodDecs.size();
	}
	
	public static boolean checkDuplication(String name, String generatedHeader, int index) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return false;
		
// 아래 코드는 String.matches 를 이용하도록 수정되어야 합니다		
        String decl = (interFace.methodDecs.get(index)).trim(); //Remove line
        //decl = "*" + decl + "*";
        String[] linesOfGeneratedHeader = generatedHeader.split("\n");
        for (int i = 0; i < linesOfGeneratedHeader.length; i++) {
            if (linesOfGeneratedHeader[i].indexOf(decl) >= 0)
                return true;
        }
		return false;
	}
	
	public static String getInterfaceHeader(String name, int index) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return "";
		return interFace.methodDecs.get(index);
	}

	public static String getInterfaceSource(String name, int index) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return "";
		return interFace.methodDefs.get(index);
	}

	public static String getInterfaceHeader(String name) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return "";
		return interFace.getHeader();
	}

	public static String getInterfaceSource(String name) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return "";
		return interFace.getSource();
	}

	public static String getListenerImpl(String name) {
		Interface interFace = getFormTemplateStore().interfaces.get(name);
		if (interFace == null)
			return "";
		return interFace.getScope() + "::" + name;
	}
	
	public static InterfaceList getControl(int type) {
		String name = FrameConst.cszTag[type];
		InterfaceList control = getFormTemplateStore().classes.get(name);
		return control;
	}

	public static InterfaceList getControl(String name) {
		InterfaceList control = getFormTemplateStore().classes.get(name);
		return control;
	}

	public static String getBaseClass(int type) {
		String name = FrameConst.cszTag[type];
		InterfaceList control = getFormTemplateStore().classes.get(name);
		if (control == null)
			return "";
		return control.getBaseClass();
	}

	public static String getBaseClass(String name) {
		InterfaceList control = getFormTemplateStore().classes.get(name);
		if (control == null)
			return "";
		return control.getBaseClass();
	}

	public static Vector<String> getInterfaceList(int type) {
		String name = FrameConst.cszTag[type];
		InterfaceList control = getFormTemplateStore().classes.get(name);
		if (control == null)
			return new Vector<String>();
		return control.getInterface();
	}

	public static Vector<String> getInterfaceList(String name) {
		InterfaceList control = getFormTemplateStore().classes.get(name);
		if (control == null)
			return new Vector<String>();
		return control.getInterface();
	}
}
