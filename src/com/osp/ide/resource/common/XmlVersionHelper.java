package com.osp.ide.resource.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.internal.ide.StringMatcher.Position;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.VersionMngXmlStore.UpdateInfo;
import com.osp.ide.resource.common.VersionMngXmlStore.UpdateList;
import com.osp.ide.resource.common.VersionMngXmlStore.UpdateTagInfo;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

@SuppressWarnings("restriction")
public class XmlVersionHelper {
	public static String readFromFile(IProject project, File file) {
		String buffer = "", temp = "";
		int len = 0;
		if (project == null || file == null)
			return buffer.toString();

		if (!file.exists()) {
			return "";
		} else {
			BufferedReader br = null;
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);

				br = new BufferedReader(new InputStreamReader(in, "UTF8"));

				while ((temp = br.readLine()) != null) {
					if (len == 0)
						buffer = temp + "\r\n";
					else
						buffer += temp + "\r\n";
					len += temp.length() + "\r\n".length();
				}
			} catch (IOException e) {
				return "";
			} finally {
				try {
					if (br != null)
						br.close();
					if (in != null)
						in.close();
				} catch (IOException e) {
					Activator.setErrorMessage(
							"XmlVersionHelper.readFromFile()", e.getClass()
									+ " - " + e.getMessage(), e);
				}
			}

		}
		return buffer;
	}

	public static boolean writeToFile(IProject project, File file, String data) {
		if (project == null || data == null)
			return false;

		String name = file.getName();
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return false;
		String parent = file.getParentFile().getName();
		IFile source = project.getFolder(view.getResourceDir()).getFolder(
				parent).getFile(name);

		ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
			if (source.exists()) {
				try {
					ResourceAttributes attr = source.getResourceAttributes();
					attr.setReadOnly(false);
					source.setResourceAttributes(attr);

					source.setContents(stream, IResource.FORCE,
							new NullProgressMonitor()); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.setErrorMessage("XmlVersionHelper.writeToFile()",
							e.getClass() + " - " + e.getMessage(), e);
				}
			} else {
				source.create(stream, IResource.FORCE,
						new NullProgressMonitor()); //$NON-NLS-1$				
			}
		} catch (UnsupportedEncodingException e1) {
			return false;
		} catch (CoreException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("XmlVersionHelper.writeToFile()",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	public static boolean writeToFile(IFile source, String data) {
		if (source == null || data == null)
			return false;

		ByteArrayInputStream stream = null;
		try {
			stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
			if (source.exists()) {
				try {
					ResourceAttributes attr = source.getResourceAttributes();
					attr.setReadOnly(false);
					source.setResourceAttributes(attr);

					source.setContents(stream, IResource.FORCE,
							new NullProgressMonitor()); //$NON-NLS-1$
				} catch (CoreException e) {
					Activator.setErrorMessage(
							"XmlVersionHelper.writeToFile()2", e.getClass()
									+ " - " + e.getMessage(), e);
				}
			} else {
				source.create(stream, IResource.FORCE,
						new NullProgressMonitor()); //$NON-NLS-1$				
			}
		} catch (UnsupportedEncodingException e1) {
			return false;
		} catch (CoreException e) {
			return false;
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				Activator.setErrorMessage("XmlVersionHelper.writeToFile()2",
						"finally] " + e.getClass() + " - " + e.getMessage(), e);
			}
		}

		return true;
	}

	public static String convertXml(String source, String sVersion) {
		if (source == null || source.isEmpty() || sVersion == null
				|| sVersion.isEmpty())
			return source;

		UpdateList updateList = VersionMngXmlStore.getControl(sVersion);

		for (int i = 0; i < updateList.tagList.size(); i++) {
			source = convertTag(source, updateList.tagList.get(i));
		}
		Position pos = getAttrPos(source, "Dversion");
		StringBuilder sBuilder = new StringBuilder(source);
		String sTemp = "Dversion=\"" + sVersion + "\"";
		sBuilder.replace(pos.getStart(), pos.getEnd(), sTemp);
		String bVersion = "Bversion=\""
			+ Activator.getBVersion() + "\"";
		pos = getAttrPos(source, "Bversion");
		if(pos.getStart() == 0 && pos.getEnd() ==0) {
			bVersion += ' ';
			sBuilder.insert(sBuilder.indexOf(sVersion), bVersion);
		} else
			sBuilder.replace(pos.getStart(), pos.getEnd(), bVersion);

		return source;
	}

	public static String convertXml(String source, int versionIndex) {
		if (source == null || source.isEmpty())
			return "";

		UpdateList updateList = VersionMngXmlStore.getControl(versionIndex);

		for (int i = 0; i < updateList.tagList.size(); i++) {
			source = convertTag(source, updateList.tagList.get(i));
		}
		Position pos = getAttrPos(source, "Dversion");
		StringBuilder sBuilder = new StringBuilder(source);
		String sVersion = "Dversion=\""
				+ VersionMngXmlStore.DVERSION_LIST[versionIndex] + "\"";
		sBuilder.replace(pos.getStart(), pos.getEnd(), sVersion);
		String bVersion = "Bversion=\""
			+ Activator.getBVersion() + "\"";
		pos = getAttrPos(source, "Bversion");
		if(pos.getStart() == 0 && pos.getEnd() ==0) {
			bVersion += ' ';
			sBuilder.insert(sBuilder.indexOf(sVersion), bVersion);
		} else
			sBuilder.replace(pos.getStart(), pos.getEnd(), bVersion);

		return sBuilder.toString();
	}

	private static String convertTag(String source, UpdateTagInfo tagInfo) {
		if (source == null || source.isEmpty() || tagInfo == null)
			return source;

		String result = "", temp = "";

		Vector<String> tag = getTag(source, tagInfo.tagInfo.name);
		for (int n = 0; n < tag.size(); n++) {
			result = tag.get(n);
			Vector<String> property = getSubTag(tag.get(n), "property");
			for (int i = 0; i < property.size(); i++) {
				temp = convertProperty(property.get(i), tagInfo.property);
				result = result.replace(property.get(i), temp);
			}
			Vector<String> layout = getSubTag(tag.get(n), "layout");
			for (int i = 0; i < layout.size(); i++) {
				temp = convertProperty(layout.get(i), tagInfo.layout);
				result = result.replace(layout.get(i), temp);
			}

			if (tagInfo.tagInfo.op != null
					&& tagInfo.tagInfo.op.equals(VersionMngXmlStore.OP_MODIFY)
					&& result.contains(tagInfo.tagInfo.name)) {
				result = result.replaceAll(tagInfo.tagInfo.name,
						tagInfo.tagInfo.value);
				source = source.replace(tag.get(n), result);
			} else if (tagInfo.tagInfo.op != null
					&& tagInfo.tagInfo.op.equals(VersionMngXmlStore.OP_DELETE)
					&& result.contains(tagInfo.tagInfo.name)) {
				source = source.replace(tag.get(n), "");
			} else if (tag.get(n) != null && !tag.get(n).equals(result))
				source = source.replace(tag.get(n), result);
				
		}

		return source;
	}

	private static String convertProperty(String subTag,
			Vector<UpdateInfo> updateInfo) {
		StringBuilder result = new StringBuilder(subTag);
		if (updateInfo == null)
			return result.toString();

		for (int i = 0; i < updateInfo.size(); i++) {
			UpdateInfo info = updateInfo.get(i);

			if (info.op != null && info.op.equals(VersionMngXmlStore.OP_ADD)
					&& !checkContainProperty(subTag, info.name)) {
				StringBuilder sTemp = new StringBuilder(" ");
				sTemp.append(info.name);
				sTemp.append("=\"");
				sTemp.append(info.value);
				sTemp.append("\"");

				result.insert(result.length() - 2, sTemp);
			} else if (info.op != null
					&& info.op.equals(VersionMngXmlStore.OP_DELETE)
					&& checkContainProperty(subTag, info.name)) {
				Position pos = getAttrPos(result.toString(), info.name);
				result.replace(pos.getStart(), pos.getEnd(), "");
			} else if (info.op != null
					&& info.op.equals(VersionMngXmlStore.OP_MODIFY)
					&& checkContainProperty(subTag, info.name)) {
				int start = getTagPos(result.toString(), info.name);
				result.replace(start, start + info.name.length(), info.value);
			}
		}

		return result.toString();
	}

	/**
     * @param source
     * @param name
     * @return
     */
    private static int getTagPos(String source, String name) {
        int pos = -1;
        if (source == null || source.isEmpty() || name == null
            || name.isEmpty())
            return pos;
    
        int start;
        String temp = source;
    
        while((start = temp.indexOf(name)) >= 0) {
            temp = temp.substring(start);
            if (temp.charAt(name.length()) == ' ' || temp.charAt(name.length()) == '=')
                return start;
            
            temp = temp.substring(name.length());
        }
        
        return pos;
    }

    /**
     * @param source
     * @param name
     * @return
     */
    private static boolean checkContainProperty(String source, String name) {
        StringTokenizer tokens = new StringTokenizer(source, " =");
        while(tokens.hasMoreTokens()) {
            String token = tokens.nextToken();
            if(token.equals(name))
                return true;
        }
        
        return false;
    }

    private static Vector<String> getTag(String source, String tag) {
		Vector<String> sTag = new Vector<String>();
		if (source == null || source.isEmpty() || tag == null || tag.isEmpty())
			return sTag;

		String subString, sTemp;
		int start, end;

		subString = source;
		sTemp = "<" + tag;
		start = subString.indexOf(sTemp);
		while (start >= 0) {
			subString = subString.substring(start);
			sTemp = "</" + tag + ">";
			end = subString.indexOf(sTemp);
			if (end < 0)
				break;

			end += sTemp.length();
			sTag.add(subString.substring(0, end));

			subString = subString.substring(end);
			sTemp = "<" + tag;
			start = subString.indexOf(sTemp);
		}
		return sTag;
	}

	private static Vector<String> getSubTag(String source, String tag) {
		Vector<String> sSubTag = new Vector<String>();
		if (source == null || source.isEmpty() || tag == null || tag.isEmpty())
			return sSubTag;

		String subString, sTemp;
		int start, end;

		subString = source;
		sTemp = "<" + tag;

		start = subString.indexOf(sTemp);
		while (start >= 0) {
			subString = subString.substring(start);
			sTemp = "/>";
			end = subString.indexOf(sTemp);
			if (end < 0)
				break;

			end += sTemp.length();
			sSubTag.add(subString.substring(0, end));

			subString = subString.substring(end);
			sTemp = "<" + tag;
			start = subString.indexOf(sTemp);
		}
		return sSubTag;
	}

	private static Position getAttrPos(String source, String attr) {
		Position pos = new Position(0, 0);
		if (source == null || source.isEmpty() || attr == null
				|| attr.isEmpty())
			return pos;

		int start, end;
		String temp;

		start = source.indexOf(attr);
		if (start < 0)
			return pos;
		temp = source.substring(start);
		end = temp.indexOf("\"") + 1;
		if (end < 0)
			return pos;
		temp = temp.substring(end);
		end = temp.indexOf("\"") + end + start + 1;

		pos = new Position(start, end);

		return pos;
	}

}
