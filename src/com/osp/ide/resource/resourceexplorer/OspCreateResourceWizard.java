package com.osp.ide.resource.resourceexplorer;

import java.io.File;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.string.OspUIString;

public class OspCreateResourceWizard extends Wizard implements INewWizard {

	OspResourceMainWizardPage newProject;
	private String selectPName;
	private String curProject;
	private IStructuredSelection selection;
	static final String SCENEFILE = "Create Form File";
	static final String POPUPFILE = "Create Popup File";
//    static final String PANELFILE = "Create Panel File";
	private static final int TREEINDEX_FORM = 0;
	private static final int TREEINDEX_POPUP = 1;
//    private static final int TREEINDEX_PANEL = 2;

	public OspCreateResourceWizard() {
		super();
		setDialogSettings(CUIPlugin.getDefault().getDialogSettings());
		setNeedsProgressMonitor(true);
		setForcePreviousAndNextButtons(false);
		setWindowTitle("New bada Resource Files");

	}

	public OspCreateResourceWizard(String curProject) {
		this();
		this.curProject = curProject;
	}

	@Override
	public boolean performFinish() {

		if (selectPName == null)
			return false;

		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				selectPName);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if(view == null) {
            IWorkbenchPage page = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage();
            try {
                view = (ResourceExplorer) page.showView(ResourceExplorer.ID);
            } catch (PartInitException e) {
                e.printStackTrace();
                return true;
            }
		}
		String rootDir = project.getLocation().toString()
				+ view.getResourceDir();

		view.setCurProject(selectPName, false);

		OspResourceManager manager = view.manager;
		if (manager == null) {
			manager = new OspResourceManager(selectPName);
			manager.OpenFrames(selectPName);
		}
		TreeItem treeItem = newProject.getSubTree().getItem(TREEINDEX_FORM);
		if(treeItem != null) {
    		TreeItem[] treeItems = treeItem.getItems();
    		StringBuilder s;
    		for (int i = 0; i < treeItems.length; i++) {
    			s = new StringBuilder(rootDir);
    			s.append(treeItems[i].getText());
    			File subDir = new File(Path.fromPortableString(s.toString()).toOSString());
    
    			if (!subDir.exists())
    				subDir.mkdir();
    			
    			if(treeItems[i].getChecked() == false)
    				continue;
    			manager.InsertScene(treeItems[i].getText(), OspResourceManager.SCENETEMPLATE_EMPTY);
    		}
		}
		
//        treeItem = newProject.getSubTree().getItem(TREEINDEX_PANEL);
//        if(treeItem == null) {
//            TreeItem[] treeItems = treeItem.getItems();
//            for (int i = 0; i < treeItems.length; i++) {
//                StringBuilder s = new StringBuilder(rootDir);
//                s.append(treeItems[i].getText());
//                File subDir = new File(Path.fromPortableString(s.toString()).toOSString());
//    
//                if (!subDir.exists())
//                    subDir.mkdir();
//                
//                if(treeItems[i].getChecked() == false)
//                    continue;
//                frame.InsertPanel(treeItems[i].getText());
//            }
//        }
        
		treeItem = newProject.getSubTree().getItem(TREEINDEX_POPUP);
		if(treeItem != null) {
    		TreeItem[] treeItems = treeItem.getItems();
    		for (int i = 0; i < treeItems.length; i++) {
    			StringBuilder s = new StringBuilder(rootDir);
    			s.append(treeItems[i].getText());
    			File subDir = new File(Path.fromPortableString(s.toString()).toOSString());
    
    			if (!subDir.exists())
    				subDir.mkdir();
    			
    			if(treeItems[i].getChecked() == false)
    				continue;
    			manager.InsertPopup(treeItems[i].getText(), OspResourceManager.SCENETEMPLATE_EMPTY);
    		}
		}

		OspUIString string = view.string;
		if(string == null) {
			string = new OspUIString(selectPName);
			string.InsertString(OspUIString.DEFAULT_LANG1);
			string.SaveAll();
		}

		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}

		view.refreshTree();

		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection curSelection) {
		selection = curSelection;
	}
	
	public IStructuredSelection getSelection() {
		return selection;
	}

	public void addPages() {
		String windowTitle = "badaResourceMainWizardPage";
		if(curProject == null || curProject.isEmpty())
			newProject = new OspResourceMainWizardPage(windowTitle, this);
		else
			newProject = new OspResourceMainWizardPage(windowTitle, this, curProject);
		addPage(newProject);
	}

	public void setSelectedProject(String name) {
		selectPName = name;
	}
	
	public String getSelectedProject() {
		return selectPName;
	}

}