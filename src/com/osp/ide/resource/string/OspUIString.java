package com.osp.ide.resource.string;

import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspUIString implements StringConst {

	public static String DEFAULT_LANG1 = "eng-GB";
    public static String DEFAULT_LANG1NAME = "English (England(UK))";
	public static final String DEFAULT_LANG2 = "eng-US";
	public static final String DEFAULT_OLDLANG_NAME = "ENGLISH";
    public static final int DEFAULT_LANG1INDEX = 29;
	public Hashtable<String, OspStringMarkup> m_Strings = new Hashtable<String, OspStringMarkup>();
	public Hashtable<String, String> m_Languages = new Hashtable<String, String>();
	public String m_projectDirectory;
	public String m_project;

	public OspUIString(String project) {
		IProject iProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(project);
		if (iProject == null)
			return;
		m_project = project;
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return;
		String osPath = iProject.getLocation().toFile().getAbsolutePath();
		osPath += view.getResourceDir();
		m_projectDirectory = osPath;
	};

	public OspUIString(IProject project) {
		m_project = project.getName();
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return;
		String osPath = project.getLocation().toFile().getAbsolutePath();
		osPath += view.getResourceDir();
		m_projectDirectory = osPath;
	};

	public OspUIString(String rootDir, String project) {
		m_projectDirectory = rootDir;
		m_project = project;
	};

	boolean LoadXML(String lpszPathName) {// XML 파일을 읽어서 SetDoc(파일 내용)
		boolean ret = true;
		if (!lpszPathName.isEmpty())
			m_projectDirectory = lpszPathName;
		else
			return false;

		File projectDir = new File(m_projectDirectory);
		File[] files = projectDir.listFiles();
		for (int i = 0; files != null && i < files.length; i++) {
			File file = files[i];
			String ext;
			if (file.isFile())
				ext = file.getName().substring(file.getName().indexOf('.') + 1);
			else
				continue;

			if (ext.equals("xml")) {
				OspStringMarkup string = new OspStringMarkup(m_project, file
						.getName());

				if (string.loadXML()) {
					String name = OspStringMarkup.getLanguageName(string
							.getLanguage());
					if (name == null || name.isEmpty())
						continue;
					m_Strings.put(string.getLanguage(), string);
					m_Languages.put(string.getLanguage(), file.getName()
							.substring(0, file.getName().indexOf('.')));
				}
			}
		}

		return ret;
	};

	public boolean OpenStrings(String pathName) {
		if (pathName.isEmpty())
			pathName = m_projectDirectory;
		File file = new File(pathName);
		if (!file.exists())
			return false;

		if (!LoadXML(pathName)) {
			System.out.println("Failed to load String file : " + pathName);
			return false;
		}

		return true;
	};

	public void resetStrings(Hashtable<String, String> languages, String dftlString) {
	    String dftlId = OspStringMarkup.getLanguageId(dftlString);
	    if(dftlId != null) {
	        languages.put(dftlId, dftlString);
	        DEFAULT_LANG1 = dftlId;
	        DEFAULT_LANG1NAME = dftlString;
	    }
		Enumeration<String> keys = m_Strings.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if ((languages.get(key)) == null)
				DeleteString(key);
		}

		keys = languages.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if ((m_Strings.get(key)) == null)
				InsertString(key);
		}
		m_Languages = languages;
	}

	public void DeleteString(String id) {
		OspStringMarkup string = m_Strings.get(id);
		if (string == null)
			return;

		m_Strings.remove(id);
		m_Languages.remove(id);
		IFile file = string.getFile();
		if (file.exists()) {
			try {
				file.delete(false, null);
			} catch (CoreException e) {
				Activator.setErrorMessage("OspUIString.DeleteString()", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}
	};

	public OspStringMarkup InsertString(String id) {
		if (id == null
				|| id.isEmpty()
				|| id.toUpperCase(Locale.getDefault()).equals(
						DEFAULT_OLDLANG_NAME))
			id = DEFAULT_LANG1;
		
		String name = OspStringMarkup.getLanguageName(id);
		if(name == null || name.isEmpty()) {
		    id = DEFAULT_LANG1;
	        name = OspStringMarkup.getLanguageName(id);
	        if(name == null || name.isEmpty())
	            name = DEFAULT_LANG1NAME;
		}
		
		if(m_Strings.get(id) != null)
		    return m_Strings.get(id);
		
		OspStringMarkup string = new OspStringMarkup(m_project, id);

		m_Strings.put(id, string);
		m_Languages.put(id, name);
		return string;
	}

	public boolean SaveXML(OspStringMarkup string) {
		// m_Scene.PrintItemsInfo();

		return string.storeXML();
	};

	public String InsertText(String value) {
		Enumeration<String> keys = m_Strings.keys();

		String id;
		if (value.isEmpty())
			id = makeId();
		else
			id = makeId(value);

		while (keys.hasMoreElements()) {
			OspStringMarkup string = m_Strings.get(keys.nextElement());
	        if(string != null)
	            string.insertText(id, value);
		}
		return id;
	}

    public String InsertText(String langId, String value) {
        String id;
        if (value.isEmpty())
            id = makeId();
        else
            id = makeId(value);

        OspStringMarkup string = m_Strings.get(langId);
        if(string != null)
            string.insertText(id, value);

        return id;
    }

	private String makeId(String value) {
		String id;
		int index = 1;
		value = value.toUpperCase(Locale.getDefault());
		id = String.format("IDS_%s", value);
		while (isIdExist(id)) {
			id = String.format("IDS_%s%d", value, index++);
		}

		return id;
	}

	private String makeId() {
		String id;
		int i = 1;
		do {
			id = String.format("IDS_STRING%d", i++);
		} while (isIdExist(id));

		return id;
	}

	private boolean isIdExist(String id) {
		Enumeration<String> keys = m_Strings.keys();
		while (keys.hasMoreElements()) {
			Enumeration<String> ids = m_Strings.get(keys.nextElement())
					.getIds();
			while (ids.hasMoreElements()) {
				if (ids.nextElement().equals(id))
					return true;
			}
		}

		return false;
	}

	public void DeleteText(String id) {
		Enumeration<String> keys = m_Strings.keys();

		while (keys.hasMoreElements()) {
			OspStringMarkup string = m_Strings.get(keys.nextElement());
			string.deleteText(id);
		}
	}

	public boolean ReplaceTextId(String oldId, String newId) {
		Enumeration<String> keys = m_Strings.keys();

		while (keys.hasMoreElements()) {
			OspStringMarkup string = m_Strings.get(keys.nextElement());
			if (!string.replaceTextId(oldId, newId))
				return false;
		}
		return true;
	}

	public void SaveAll() {
		Enumeration<String> keys = m_Strings.keys();
		while (keys.hasMoreElements())
			SaveXML(m_Strings.get(keys.nextElement()));
	}

	public String getText(String id) {
		String text = "";
		OspStringMarkup string = m_Strings.get(DEFAULT_LANG1);
		if (string != null)
			text = string.getText(id);

		if (text == null || text.isEmpty())
			string = m_Strings.get(DEFAULT_LANG2);

		if (string != null)
			text = string.getText(id);

		Enumeration<String> keys = m_Languages.keys();
		while ((text == null || text.isEmpty()) && keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key != DEFAULT_LANG1 && key != DEFAULT_LANG2) {
				string = m_Strings.get(key);
				if (string != null)
					text = string.getText(id);
			}
		}

		return text;
	}

	public String getTextId(String value) {
		String text = "";
		OspStringMarkup string = m_Strings.get(DEFAULT_LANG1);
		if (string != null)
			text = string.getTextId(value);

		if (text == null || text.isEmpty())
			string = m_Strings.get(DEFAULT_LANG2);

		if (string != null)
			text = string.getTextId(value);

		Enumeration<String> keys = m_Languages.keys();
		while ((text == null || text.isEmpty()) && keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key != DEFAULT_LANG1 && key != DEFAULT_LANG2) {
				string = m_Strings.get(key);
				if (string != null)
					text = string.getTextId(value);
			}
		}
		return text;
	}

    /**
     * @param b
     * @return
     */
    public Vector<String> getValueSortedKeys(boolean isPrint) {
        Vector<String> keys = new Vector<String>();
        Vector<String> names = new Vector<String>();

        Enumeration<String> elements = m_Strings.keys();
        while (elements.hasMoreElements()) {
            String key = elements.nextElement();
            Object value = OspStringMarkup.getLanguageName(key);
            names.add((String) value);
        }

        Collections.sort(names);
        elements = names.elements();
        while (elements.hasMoreElements()) {
            String name = elements.nextElement();
            String key = OspStringMarkup.getLanguageId(name);
            keys.add(key);
            
            if (isPrint) {
                StringBuilder printStr = new StringBuilder("Key : ");
                System.out.println(printStr.toString());
                printStr = printStr.append(key);
                printStr = printStr.append(", Value : ");
                printStr = printStr.append(name);
            }
        }
        return keys;
    };

}
