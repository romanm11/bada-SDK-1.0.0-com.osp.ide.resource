package com.osp.ide.resource.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;

public class ScreenSizeReader {
	public static final String ELEMENT_NAME = "Manifest"; //$NON-NLS-1$

	public static final String ATTR_DEVICEPROFILE = "DeviceProfile"; //$NON-NLS-1$
	public static final String ATTR_DP_SCREENSIZE = "ScreenSize"; //$NON-NLS-1$

	private String filePath = "";

	ArrayList<String> screenSizeList = new ArrayList<String>();

	public ScreenSizeReader(String path) {
		filePath = path;
		loadXML(filePath);
	}

	public ScreenSizeReader(IProject project) {
		if (project == null) {
			clear();
			return;
		}

		filePath = project.getLocation().append(Activator.MANIFEST_NAME)
				.toOSString();
		loadXML(filePath);
	}

	private boolean loadXML(String path) {
		clear();
		if (path == null || path.length() == 0)
			return false;

		FileInputStream stream = null;
		try {
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
				for (Node node = root.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					String name = node.getNodeName();
					if (node.getFirstChild() != null) {
						if (name.equals(ATTR_DEVICEPROFILE)) {
							for (Node nodeProfile = node.getFirstChild(); nodeProfile != null; nodeProfile = nodeProfile
									.getNextSibling()) {
								String nameProfileNode = nodeProfile
										.getNodeName();
								if (nodeProfile.getFirstChild() != null) {
									String valueProfileNode = nodeProfile
											.getFirstChild().getNodeValue();
									if (nameProfileNode
											.equals(ATTR_DP_SCREENSIZE))
										screenSizeList.add(valueProfileNode);
								}
							}
						}
					}
				}
			}
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
			}
		}

		return true;
	}

	public void clear() {
		screenSizeList.clear();
	}

	public Vector<String> getScreenSizes() {
		Vector<String> retValue = new Vector<String>();
		for (int i = 0; i < screenSizeList.size(); i++) {
			String temp = screenSizeList.get(i);

			retValue.add(i, temp);
		}

		return retValue;
	}
}
