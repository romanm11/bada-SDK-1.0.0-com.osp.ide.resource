package com.osp.ide.resource.editform;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.osp.ide.resource.Activator;

public class FormClassGenerator {

	public static final String FILE_SEP_FSLASH="/";
    public static final String DIR_SOURCE = "src";
    public static final String DIR_INCLUDE = "inc";
    public static final String EXT_CC = ".cpp";
    public static final String EXT_HEADER = ".h";
	
	public final static String METHOD_DEF_BODY = "METHOD_DEF_BODY";

	public final static String HEADER_DEF = "HEADER_DEF"; // bk

	public final static String INC_USER_DEF_HEADERS = "INC_USER_DEF_HEADERS";
	public final static String CLASS_NAME = "CLASS_NAME";
	public final static String LISTENER_IMPL = "LISTENER_IMPL";
	public final static String METHOD_DECL = "METHOD_DECL";
	public final static String FORM_NAME = "FORM_NAME";
	public final static String METHOD_DEF = "METHOD_DEF";

	public FormClassGenerator() {

	}

	public void createClass(IProject prj, String className, String userHeader,
			String formName, List<String> listeners) {
		Map<String, String> valueStore = null;

		String distPath = DIR_INCLUDE + FILE_SEP_FSLASH
				+ className + EXT_HEADER;
		IFile iFile = prj.getFile(distPath);
		if (iFile.exists()) {
			Shell shell = Display.getCurrent().getActiveShell();
			if (!MessageDialog.openQuestion(shell, "Make Class : " + distPath,
					"Class already exists. Do you want to make a new class?"))
				return;
		}
		// create project_name.cpp
		valueStore = getValueStore(className, userHeader, formName, listeners);
		createTrmplateFile(prj, FormTemplateXmlStore.getSource(),
				DIR_SOURCE + FILE_SEP_FSLASH + className
						+ EXT_CC, valueStore);

		// create project_name.h
		createTrmplateFile(prj, FormTemplateXmlStore.getHeader(),
				DIR_INCLUDE + FILE_SEP_FSLASH + className
						+ EXT_HEADER, valueStore);

	}

	Map<String, String> getValueStore(String baseName, String userHeader,
			String formName, List<String> listeners) {
		Map<String, String> valueStore = new HashMap<String, String>();

		valueStore.put(METHOD_DEF_BODY, FormTemplateXmlStore.getMacro());
		valueStore.put(HEADER_DEF, "_" + baseName.toUpperCase(Locale.getDefault()) + "_H_"); // bk

		valueStore.put(INC_USER_DEF_HEADERS, userHeader);
		valueStore.put(CLASS_NAME, baseName);

		StringBuilder listenerImpl = new StringBuilder("");
		StringBuilder methodDecl = new StringBuilder("");
		StringBuilder methodDef = new StringBuilder("");

		for (int i = 0; i < listeners.size(); i++) {
		    String impl = FormTemplateXmlStore
                .getListenerImpl(listeners.get(i));
			if(impl == null || impl.isEmpty()) {
			    String listener = listeners.get(i);
			    Activator.setErrorMessage("FormClassGenerator.getValueStore()", 
			        "interface definition empty : " + listener, null);
			    continue;
			}
			
			listenerImpl.append(",\n	public ");
            listenerImpl.append(FormTemplateXmlStore
                .getListenerImpl(listeners.get(i)));
			int methodsNum = FormTemplateXmlStore.getMethodsNum(listeners.get(i));
			for (int j = 0; j < methodsNum; j++)
			{
				if (!FormTemplateXmlStore.checkDuplication(listeners.get(i), methodDecl.toString(), j))
				{
					methodDecl.append(FormTemplateXmlStore.getInterfaceHeader(listeners.get(i), j));
					methodDef.append(FormTemplateXmlStore.getInterfaceSource(listeners.get(i), j));
				}
			}
		}

		valueStore.put(LISTENER_IMPL, listenerImpl.toString());
		valueStore.put(METHOD_DECL, methodDecl.toString());
		valueStore.put(FORM_NAME, formName);
		valueStore.put(METHOD_DEF, methodDef.toString());

		return valueStore;
	}

	public boolean createTrmplateFile(IProject prj, String fileContents,
			String distPath, Map<String, String> valueStore) {
		fileContents = FormClassHelper.getValueAfterExpandingMacros(
				fileContents, FormClassHelper.getReplaceKeys(fileContents),
				valueStore);
		fileContents = FormClassHelper.getValueAfterExpandingMacros(
				fileContents, FormClassHelper.getReplaceKeys(fileContents),
				valueStore);

		if (fileContents == null || fileContents.length() == 0)
			return false;

		InputStream contents = new ByteArrayInputStream(fileContents.getBytes(Charset.defaultCharset()));

		try {
			IFile iFile = prj.getFile(distPath);
			if (iFile.exists()) {
				iFile.setContents(contents, true, true, null);
			} else {
				iFile.create(contents, true, null);
				iFile.refreshLocal(IResource.DEPTH_ONE, null);
			}
		} catch (CoreException e) {
			return false;
		}

		return true;
	}

	public boolean createDirectory(IProject prj, String name) {
		IFolder folder = prj.getFolder(name);
		if (!folder.exists()) {
			try {
				folder.create(true, true, new NullProgressMonitor());
			} catch (CoreException e) {
				return false;
			}
		}

		return true;
	}

	public boolean copyFile(IProject prj, URL tmplPath, String distPath) {
		IPath prjPath = prj.getLocation();
		prjPath = prjPath.append(distPath);

		File file = prjPath.makeAbsolute().toFile();

		return FormClassHelper.copyBinaryFile(tmplPath, file);
	}

}
