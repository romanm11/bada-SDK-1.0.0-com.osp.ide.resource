package com.osp.ide.resource.common;

import java.io.File;
import java.util.Vector;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspPopupMarkup;
import com.osp.ide.resource.documents.OspFormMarkup;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class XmlVersionConvertor {

	public XmlVersionConvertor() {

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
	
	public static boolean convertCurVersion(IProject project, boolean curVersionConverting) {
		Vector<String> screens = Activator.getStringScreen(project.getName());
		ResourceExplorer view = ResourceExplorer.getResourceView();
	
		if(view.getResourceDir() == null || view.getResourceDir().isEmpty())
			return false;
		
		IFolder resDir = project.getFolder(view.getResourceDir()); 
		if(resDir == null)
			return false;
		
		for(int i=0; i<screens.size(); i++) {
			File subDir = resDir.getFolder(screens.get(i)).getLocation().toFile();
			if(subDir == null || !subDir.exists())
				continue;
			
			File[] files = subDir.listFiles();
			String version = "";
			int index, curIndex = VersionMngXmlStore.getCurVersionIndex();
			for(int j=0; j<files.length; j++) {
				if(OspFormMarkup.isFormXML(project, files[j])) {
					version = OspFormMarkup.getDVersion(project, files[j]);
					index = VersionMngXmlStore.getVersionIndex(version);
				} else if(OspPopupMarkup.isPopupXML(project, files[j])) {
					version = OspPopupMarkup.getDVersion(project, files[j]);
					index = VersionMngXmlStore.getVersionIndex(version);
				} else {
					continue;
				}
				
				if(curVersionConverting && index == curIndex)
					index --;
				
				if(index >= curIndex)
					continue;
				
				String doc = XmlVersionHelper.readFromFile(project, files[j]);
				
				for(int n=index; n<curIndex; n++) {
					doc = XmlVersionHelper.convertXml(doc, n+1);
				}
				
				XmlVersionHelper.writeToFile(project, files[j], doc);
			}
		}
		
		view.refreshProject();
		
		return true;
	}
}
