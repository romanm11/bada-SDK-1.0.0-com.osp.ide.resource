package com.osp.ide.resource.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.Path;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.FormTemplateXmlStore;

public class LanguageListXmlStore {
	public static final String FILE_NAME = "lang_country_list.xml"; //$NON-NLS-1$
	public static final String ELEMENT_NAME = "languages"; //$NON-NLS-1$

	public static final String TAG_LANG = "lang"; //$NON-NLS-1$

	public static final String ATTR_ID = "id"; //$NON-NLS-1$
	public static final String ATTR_NAME = "name"; //$NON-NLS-1$

	private String filePath = "";
	private ArrayList<LanguageData> languageList = new ArrayList<LanguageData>();

	public LanguageListXmlStore() {
		filePath = Activator.getDefault().getResourceLocationURL(
				FormTemplateXmlStore.TEMPLATE_FOLDER + FILE_NAME).getFile();
//        filePath = FormTemplateXmlStore.TEMPLATE_FOLDER + FILE_NAME;

		loadXML(filePath);
	}

	public ArrayList<LanguageData> getLanguageList() {
		return languageList;
	}

	private boolean loadXML(String path) {
		clear();
		if (path == null || path.length() == 0)
			return false;

		FileInputStream stream = null;
		try {
	        path = Path.fromPortableString(path).toOSString();
			File file = new File(path);
			if (!file.exists())
				return false;

			stream = new FileInputStream(file);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(stream);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			if (rootName.equals(ELEMENT_NAME)) {
				String id = "", name = "";
				for (Node text = root.getFirstChild(); text != null; text = text
						.getNextSibling()) {
					if (text != null && text.getNodeName() != null
							&& text.getNodeName().equals(TAG_LANG)) {
						id = text.getAttributes().getNamedItem(ATTR_ID)
								.getNodeValue();
						name = text.getAttributes().getNamedItem(ATTR_NAME)
								.getNodeValue();
						if (id != null && !id.isEmpty()) {
							LanguageData data = new LanguageData();
							data.id = id;
							data.name = name;
							languageList.add(data);
						}
					}
				}
			} else
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
				Activator.setErrorMessage("LanguageListXmlStore.loadXML()", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	public void clear() {
		if (languageList != null)
			languageList.clear();
	}

    /**
     * @return
     */
    public static boolean exists() {
//      String path = FormTemplateXmlStore.TEMPLATE_FOLDER + FILE_NAME;
        String path = Activator.getDefault().getResourceLocationURL(
            FormTemplateXmlStore.TEMPLATE_FOLDER + FILE_NAME).getFile();;
        path = Path.fromPortableString(path).toOSString();
        File file = new File(path);
        
        return file.exists();
    }

}
