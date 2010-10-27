package com.osp.ide.resource.common;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
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

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.resinfo.FrameConst;

public class ConfigManager implements FrameConst {

	private static final String ELEMENT_NAME = "Config";
	public static final String IMAGE_FOLDER = "Image/";

	public static final int AttrNum = 21;
	public static final int ATTR_IMAGE = 0;
	public static final int ATTR_IMAGEFILE = 1;
	public static final int ATTR_BGCOLOR = 2;
	public static final int ATTR_OPACITY = 3;
	public static final int ATTR_SIZE = 4;
	public static final int ATTR_SPACEW = 5;
	public static final int ATTR_SPACEH = 6;
	public static final int ATTR_FIX = 7;
	public static final int ATTR_VALIGN = 8;
	public static final int ATTR_HALIGN = 9;
	public static final int ATTR_LEFT = 10;
	public static final int ATTR_RIGHT = 11;
	public static final int ATTR_TOP = 12;
	public static final int ATTR_BOTTOM = 13;
	public static final int ATTR_DFTLSIZE = 14;
	public static final int ATTR_MINSIZE = 15;
	public static final int ATTR_LMARGIN = 16;
	public static final int ATTR_RMARGIN = 17;
	public static final int ATTR_TEXTSIZE = 18;
    public static final int ATTR_TEMP1 = 19;
    public static final int ATTR_TEMP2 = 20;

	public static final String imageAttr[] = { "Image", "ImageFile",
			"BackgroundColor", "Opacity", "Size", "SpaceW", "SpaceH", "Fix",
			"VAlign", "HAlign", "Left", "Right", "Top", "Bottom",
			"DefaultSize", "MinSize", "TextLMargin", "TextRMargin", "TextSize",
			"Temp1", "Temp2" };

	private File file = null;
	private Hashtable<String, Tag_info> m_tagInfo;
    private String screen;

	public ConfigManager(String sImgPath) {
		String osPath = sImgPath;
		osPath += "\\." + ELEMENT_NAME;
		IPath path = Path.fromPortableString(osPath);
		this.file = path.toFile();

	    screen = file.getParentFile().getName();
		initInfo();
		loadXML();
	}

	private void initInfo() {
		m_tagInfo = new Hashtable<String, Tag_info>();
	}

	public int getTagIndex(String tag) {
		for (int i = 0; i < CTL_TYPE_NUM; i++) {
			if (tag.equals(cszTag[i]))
				return i;
		}
		return -1;
	}

	public int getAttrIndex(String attr) {
		for (int i = 0; i < AttrNum; i++) {
			if (attr.equals(imageAttr[i]))
				return i;
		}
		return -1;
	}

	public Color FormatRGB(String color) {
		int j, len;
		StringBuilder s;
		int iVal[] = { 0, 0, 0 };
		Color c = null;

		if ((j = color.indexOf('(')) < 0) {
			if ((j = color.indexOf("RGB")) > -1)
				color = color.substring(j + 3);
		} else
			color = color.substring(j + 1);

		for (int i = 0; i < 3; i++) {
			s = new StringBuilder("");
			len = color.length();

			for (j = 0; j < len; j++) // find number
			{
				if (color.charAt(j) >= '0' && color.charAt(j) <= '9')
					break;
			}

			for (; j < len; j++) // find not number
			{
				if (color.charAt(j) < '0' || color.charAt(j) > '9')
					break;

				s = s.append(color.charAt(j));
			}

			color = color.substring(j);
			try {
				iVal[i] = Integer.parseInt(s.toString());
			} catch (NumberFormatException e) {
				iVal[i] = 0;
			}

			if (j == len)
				break;
		}

		c = new Color(iVal[0], iVal[1], iVal[2]);
		return c;
	};

	public static String FormatRGB(Color color) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();

		String s;
		s = String.format("0x%02x %02x %02x   RGB(%d, %d, %d)", red, green,
				blue, red, green, blue);
		return s;
	};

	public String getFilePath() {
		return file.getAbsolutePath();
	}

	public String getFileDirPath() {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	public boolean loadXML() {
		return loadXML(file);
	}

	private boolean loadXML(File path) {
		clear();
		if (path == null || !path.exists()) {
			Shell shell = Display.getCurrent().getActiveShell();
			MessageDialog.openError(shell, "Load Config",
					"Image folder or config file does not exist.\r\n\r\n"
							+ "The program may not behave as expected.");
			return false;
		}

		InputStream stream = null;
		try {
			stream = new FileInputStream(file);

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(stream);

			Element root = dom.getDocumentElement();
			String rootName = root.getNodeName();
			StringBuilder s;
			if (rootName.equals(ELEMENT_NAME)) {
				for (Node node = root.getFirstChild(); node != null; node = node
						.getNextSibling()) {
					String name = node.getNodeName();
					int index = getTagIndex(name);
					if (index < 0)
						continue;
					Tag_info info = new Tag_info();
					info.screen = screen;
					String tag = name;
					for (Node attr = node.getFirstChild(); attr != null; attr = attr
							.getNextSibling()) {
						String attrName = attr.getNodeName();
						if (attr.getFirstChild() == null)
							continue;
						int attrIndex = getAttrIndex(attrName);
						String value = "";
						switch (attrIndex) {
						case ATTR_DFTLSIZE:
							value = attr.getFirstChild().getNodeValue();
							if (value != null && !value.isEmpty()) {
								StringTokenizer stok = new StringTokenizer(
										value, " xX");
								if (stok.hasMoreTokens()) {
									try {
										info.dftlSize.x = Integer.parseInt(stok
												.nextToken());
										info.dftlSize.y = Integer.parseInt(stok
												.nextToken());
									} catch (NumberFormatException e) {
										s = new StringBuilder(
												"ATTR_DFTLSIZE NumberFormatException - ");
										s = s.append(e.getMessage());
										Activator.setErrorMessage(
												"ConfigManager.loadXML(IFile)",
												s.toString(), e);
									}
								}
							}
							break;
						case ATTR_MINSIZE:
							value = attr.getFirstChild().getNodeValue();
							if (value != null && !value.isEmpty()) {
								StringTokenizer stok = new StringTokenizer(
										value, " xX");
								if (stok.hasMoreTokens()) {
									try {
										info.minSize.x = Integer.parseInt(stok
												.nextToken());
										info.minSize.y = Integer.parseInt(stok
												.nextToken());
									} catch (NumberFormatException e) {
										s = new StringBuilder(
												"ATTR_MINSIZE NumberFormatException - ");
										s = s.append(e.getMessage());
										Activator.setErrorMessage(
												"ConfigManager.loadXML(IFile)",
												s.toString(), e);
									}
								}
							}
							break;
						case ATTR_LMARGIN:
							value = attr.getFirstChild().getNodeValue();
							try {
								info.textlMargin = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								info.textlMargin = 0;
							}
							break;
						case ATTR_RMARGIN:
							value = attr.getFirstChild().getNodeValue();
							try {
								info.textrMargin = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								info.textrMargin = 0;
							}
							break;
						case ATTR_TEXTSIZE:
							value = attr.getFirstChild().getNodeValue();
							try {
								info.tSize = Integer.parseInt(value);
							} catch (NumberFormatException e) {
								info.tSize = 0;
							}
							break;
                        case ATTR_TEMP1:
                            value = attr.getFirstChild().getNodeValue();
                            info.temp1 = value;
                            break;
                        case ATTR_TEMP2:
                            value = attr.getFirstChild().getNodeValue();
                            info.temp2 = value;
                            break;
						case ATTR_IMAGE:
							loadImageInfo(info, attr);
							break;
						}
					}
					if (tag != null && !tag.isEmpty())
						m_tagInfo.put(tag, info);
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("ConfigManager.loadXML(IFile)",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	private void loadImageInfo(Tag_info info, Node attr) {
		Image_info image = new Image_info();
		StringBuilder s;
		for (Node imageattr = attr.getFirstChild(); imageattr != null; imageattr = imageattr
				.getNextSibling()) {
			String imageattrName = imageattr.getNodeName();
			if (imageattr.getFirstChild() == null)
				continue;

			switch (getAttrIndex(imageattrName)) {
			case ATTR_IMAGEFILE:
				image.name.add(imageattr.getFirstChild().getNodeValue());
				break;
			case ATTR_BGCOLOR:
				image.bgColor.add(FormatRGB(imageattr.getFirstChild()
						.getNodeValue()));
				break;
			case ATTR_OPACITY:
				image.opacity.add(Float.parseFloat(imageattr.getFirstChild()
						.getNodeValue()));
				break;
			case ATTR_SIZE:
				image.size = new Point(0, 0);
				String value = imageattr.getFirstChild().getNodeValue();
				if (value != null && !value.isEmpty()) {
					StringTokenizer stok = new StringTokenizer(value, " xX");
					if (stok.hasMoreTokens()) {
						try {
							image.size.x = Integer.parseInt(stok.nextToken());
							image.size.y = Integer.parseInt(stok.nextToken());
						} catch (NumberFormatException e) {
							s = new StringBuilder(
									"ATTR_SIZE NumberFormatException - ");
							s = s.append(e.getMessage());
							Activator.setErrorMessage(
									"ConfigManager.loadImageInfo()", s
											.toString(), e);
						}
					}
				}
				break;
			case ATTR_SPACEW:
				try {
					image.spaceW = Integer.parseInt(imageattr.getFirstChild()
							.getNodeValue());
				} catch (NumberFormatException e) {
					image.spaceW = 0;
				}
				break;
			case ATTR_SPACEH:
				try {
					image.spaceH = Integer.parseInt(imageattr.getFirstChild()
							.getNodeValue());
				} catch (NumberFormatException e) {
					image.spaceH = 0;
				}
				break;
			case ATTR_FIX:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.fix = true;
				else
					image.fix = false;
				break;
			case ATTR_VALIGN:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.vAlign = true;
				else
					image.vAlign = false;
				break;
			case ATTR_HALIGN:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.hAlign = true;
				else
					image.hAlign = false;
				break;
			case ATTR_LEFT:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.left = true;
				else
					image.left = false;
				break;
			case ATTR_RIGHT:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.right = true;
				else
					image.right = false;
				break;
			case ATTR_TOP:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.top = true;
				else
					image.top = false;
				break;
			case ATTR_BOTTOM:
				if (imageattr.getFirstChild().getNodeValue().equals(
						BOOL[BOOL_TRUE]))
					image.bottom = true;
				else
					image.bottom = false;
				break;
			default:
			}
		}
		info.add(image);
	}

	private void appendChild(Document dom, Element parent, String name,
			String value) {
		Element element = dom.createElement(name);
		Text textNode = dom.createTextNode(value);
		element.appendChild(textNode);

		parent.appendChild(element);
	}

	public boolean storeXML() {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		FileOutputStream outStream = null;
		try {
			// get an instance of builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// create an instance of DOM
			Document dom = db.newDocument();

			Element rootEle = dom.createElement(ELEMENT_NAME);
			dom.appendChild(rootEle);

			Enumeration<String> keys = m_tagInfo.keys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				Tag_info info = m_tagInfo.get(key);
				Element tagEle = dom.createElement(key);

				appendChild(dom, tagEle, imageAttr[ATTR_DFTLSIZE],
						info.dftlSize.x + "X" + info.dftlSize.y);
				appendChild(dom, tagEle, imageAttr[ATTR_MINSIZE],
						info.minSize.x + "X" + info.minSize.y);
				appendChild(dom, tagEle, imageAttr[ATTR_LMARGIN], Integer
						.toString(info.textlMargin));
				appendChild(dom, tagEle, imageAttr[ATTR_RMARGIN], Integer
						.toString(info.textrMargin));
				appendChild(dom, tagEle, imageAttr[ATTR_TEXTSIZE], Integer
						.toString(info.tSize));
                appendChild(dom, tagEle, imageAttr[ATTR_TEMP1], info.temp1);
                appendChild(dom, tagEle, imageAttr[ATTR_TEMP2], info.temp2);

				storeImage(info, dom, tagEle);

				rootEle.appendChild(tagEle);
			}

			String buff = write(dom);
			if (buff == null)
				return false;

			outStream = new FileOutputStream(file);
			outStream.write(buff.getBytes(Charset.defaultCharset()), 0, buff
					.getBytes(Charset.defaultCharset()).length); //$NON-NLS-1$

			outStream.flush();
		} catch (ParserConfigurationException e) {
			Activator.setErrorMessage("ConfigManager.storeXML()", e.getClass()
					+ " - " + e.getMessage(), e);
			return false;
		} catch (UnsupportedEncodingException e) {
			Activator.setErrorMessage("ConfigManager.storeXML()", e.getClass()
					+ " - " + e.getMessage(), e);
			return false;
		} catch (IOException e) {
			Activator.setErrorMessage("ConfigManager.storeXML()", e.getClass()
					+ " - " + e.getMessage(), e);
			return false;
		} finally {
			try {
				if (outStream != null)
					outStream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("ConfigManager.storeXML()",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}
		return true;
	}

	private void storeImage(Tag_info info, Document dom, Element tagEle) {
		StringBuilder s;
		for (int n = 0; n < info.size(); n++) {
			Element imageEle = dom.createElement(imageAttr[ATTR_IMAGE]);
			Image_info image = info.elementAt(n);
			for (int i = 0; i < image.name.size(); i++) {
				appendChild(dom, imageEle, imageAttr[ATTR_IMAGEFILE],
						image.name.elementAt(i));
			}
			for (int i = 0; i < image.bgColor.size(); i++) {
				Color color = image.bgColor.elementAt(i);
				s = new StringBuilder("RGB {");
				s.append(color.getRed());
				s.append(", ");
				s.append(color.getGreen());
				s.append(", ");
				s.append(color.getBlue());
				s.append("}");
				appendChild(dom, imageEle, imageAttr[ATTR_BGCOLOR], s
						.toString());
			}
			for (int i = 0; i < image.opacity.size(); i++) {
				appendChild(dom, imageEle, imageAttr[ATTR_OPACITY],
						image.opacity.elementAt(i).toString());
			}
			appendChild(dom, imageEle, imageAttr[ATTR_SIZE], image.size.x + "X"
					+ image.size.y);
			appendChild(dom, imageEle, imageAttr[ATTR_SPACEW], Integer
					.toString(image.spaceW));
			appendChild(dom, imageEle, imageAttr[ATTR_SPACEH], Integer
					.toString(image.spaceH));
			if (image.fix)
				appendChild(dom, imageEle, imageAttr[ATTR_FIX], BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_FIX],
						BOOL[BOOL_FALSE]);

			if (image.vAlign)
				appendChild(dom, imageEle, imageAttr[ATTR_VALIGN],
						BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_VALIGN],
						BOOL[BOOL_FALSE]);

			if (image.hAlign)
				appendChild(dom, imageEle, imageAttr[ATTR_HALIGN],
						BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_HALIGN],
						BOOL[BOOL_FALSE]);

			if (image.left)
				appendChild(dom, imageEle, imageAttr[ATTR_LEFT],
						BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_LEFT],
						BOOL[BOOL_FALSE]);

			if (image.right)
				appendChild(dom, imageEle, imageAttr[ATTR_RIGHT],
						BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_RIGHT],
						BOOL[BOOL_FALSE]);

			if (image.top)
				appendChild(dom, imageEle, imageAttr[ATTR_TOP], BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_TOP],
						BOOL[BOOL_FALSE]);

			if (image.bottom)
				appendChild(dom, imageEle, imageAttr[ATTR_BOTTOM],
						BOOL[BOOL_TRUE]);
			else
				appendChild(dom, imageEle, imageAttr[ATTR_BOTTOM],
						BOOL[BOOL_FALSE]);

			tagEle.appendChild(imageEle);
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
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //$NON-NLS-1$
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
			transformer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", String
							.valueOf(4));

			transformer.transform(new DOMSource(dom), new StreamResult(sw));

			return sw.getBuffer().toString();

		} catch (TransformerConfigurationException e) {
			Activator.setErrorMessage("ConfigManager.write()", e.getClass()
					+ " - " + e.getMessage(), e);
		} catch (TransformerException e) {
			Activator.setErrorMessage("ConfigManager.write()", e.getClass()
					+ " - " + e.getMessage(), e);
		} finally {
			try {
				if (sw != null)
					sw.close();
			} catch (IOException e) {
				Activator.setErrorMessage("ConfigManager.write()", "finally] "
						+ e.getClass() + " - " + e.getMessage(), e);
			}
		}

		return null;
	}

	public void clear() {
		initInfo();
	}

	public Tag_info newImageInfo(String tag) {
		if (m_tagInfo.get(tag) != null)
			return m_tagInfo.get(tag);

		Tag_info info = new Tag_info();
		info.screen = screen;
		m_tagInfo.put(tag, info);
		return info;
	}

	public Tag_info getImageInfo(String tag) {
		return m_tagInfo.get(tag);
	}

	public void insertImageInfo(String tag, Tag_info info) {
		if (m_tagInfo.get(tag) != null)
			m_tagInfo.remove(tag);

		m_tagInfo.put(tag, info);
	}

	public void deleteImageInfo(String tag) {
		m_tagInfo.remove(tag);
	}

	public void deleteAll() {
		Enumeration<String> keys = m_tagInfo.keys();
		while (keys.hasMoreElements())
			deleteImageInfo(keys.nextElement());
	}

	public void setFile(String tag, int imageIndex, int index, String fileName) {
		Tag_info info = m_tagInfo.get(tag);
		if (info == null)
			return;
		Image_info imageList = info.elementAt(imageIndex);
		imageList.name.remove(index);
		imageList.name.add(index, fileName);
	}

	public void setFiles(String tag, int index, Vector<String> fileName) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		info.name = fileName;
	}

	public Vector<String> getFiles(String tag, int index) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		if (info == null)
			return new Vector<String>();
		return info.name;
	}

	public void setBGColor(String tag, int index, Vector<Color> bgColor) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		if (info != null)
			info.bgColor = bgColor;
	}

	public Vector<Color> getBGColor(String tag, int index) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		if (info == null)
			return new Vector<Color>();
		return info.bgColor;
	}

	public void setOpacity(String tag, int index, Vector<Float> opacity) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		if (info != null)
			info.opacity = opacity;
	}

	public Vector<Float> getOpacity(String tag, int index) {
		Image_info info = m_tagInfo.get(tag).elementAt(index);
		if (info == null)
			return new Vector<Float>();
		return info.opacity;
	}
}
