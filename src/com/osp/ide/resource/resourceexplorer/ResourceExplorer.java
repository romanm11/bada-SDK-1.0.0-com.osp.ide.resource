/**
 * 
 */
package com.osp.ide.resource.resourceexplorer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.internal.resources.ResourceInfo;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.action.ToolBarContributionItem;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.part.WorkbenchPart;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.ConfigManager;
import com.osp.ide.resource.common.ExcelFileReaderPOI;
import com.osp.ide.resource.common.ImageSettingDialog;
import com.osp.ide.resource.common.ImageUtil;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.common.XmlVersionConvertor;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editform.OspFormEditorInput;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpanel.OspPanelEditorInput;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.editpopup.OspPopupEditorInput;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.string.OspStringEditor;
import com.osp.ide.resource.string.OspStringEditorInput;
import com.osp.ide.resource.string.OspUIString;

/**
 * @author 
 * 
 */
@SuppressWarnings("restriction")
public class ResourceExplorer extends ViewPart implements ISaveablePart2 {
	public static final String ID = "com.osp.ide.resource.resourceexplorer.ResourceExplorer";
	private static final String ID_TOOLBAR = "ResourceExplorer Toolbar";
	public static final String PART_NAME = "Resource Explorer";
	public static final String TREE_ROOT = "Resource";
	public static final int KIND_NUM = 5;
	// enum kind {KIND_STRING, KIND_MENU, KIND_DIALOG, KIND_BITMAP, KIND_NONE};
	public static final int KIND_STRING = 0;
	public static final int KIND_MENU = 1;
	public static final int KIND_POPUP = 2;
	public static final int KIND_PANEL = 3;
	public static final int KIND_FORM = 4;
	public static final int KIND_RESOURCE = 5;

	public static final int DEPTH_ROOT = 0;
	public static final int DEPTH_GROUP = 1;
	public static final int DEPTH_TABLE = 2;

	public static final int YES = 0;
	public static final int NO = 1;
	public static final int CANCEL = 2;

	public static Hashtable<String, ConfigManager> configmanager;
	private static ResourceExplorer thisInstance;

	public static final String cszKindName[] = { "String", "Menu", "Popups",
			"ScrollPanels", "Forms", "Resource" };

	public static int GetKindIndex(String name) {
		for (int i = 0; i < cszKindName.length; i++) {
			if (name.equals(cszKindName[i]))
				return i;
		}
		return -1;
	}

	public static final String SAVE_ALL = "Save All Resources";
    public static final String SAVE_ID = "Save Id";

	public static final String RELOAD_ALL = "Reload All Resources";
	public static final String RELOAD = "Reload ";
    public static final String SAVE = "Save ";

	public static final String MAKE_RBIN = "Compile Resource";

	final static String CAPTURE_PATH = "tmp/";
	final static String IMG_FORMAT_JPG = "jpg";
	final static String IMG_FORMAT_PNG = "png";
	final static String IMG_FORMAT_BMP = "bmp";
	final static String IMG_FORMAT_GIF = "gif";

	public static final String PaintID = "Paint.Picture";
	private static final String ACTION_WIZARD = "Open Insert Wizard";
	private static final String ACTION_INSERT = "Insert Resource";
	private static final String ACTION_DELETE = "Delete Resource";
	private static final String ACTION_CREATE_FORM = "Visible Form";
	public static final String ACTION_SELECTLANGUAGE = "Language Setting";
	public static final String ACTION_PREVIEWSCENE = "Preview Form";
	static final String TEST_ACTION = "Test Action";
	private static final String ACTION_CREATE_STRING = "Create String";
	
    private static final String IMPORTSTRING_ACTION = "Import Strings From Excel File";
    
    private static final String GENERATE_WVGA = "Generate WVGA ";
    private static final String GENERATE_WQVGA = "Generate WQVGA ";
    private static final String RESOURCES = "Resources";
    public static final String WQVGA = "240X400";
    private static final String WVGA = "480X800";
    
	protected String ACTION_IMGSETTING;

	String curProject = "";
	TreeViewer resourceTreeViewer;
	OspUIString string;
	OspResourceManager manager;
	MenuManager contextMenuMgr;
	private ISelectionListener projectSelectionListener;
	private ResourcePropertyPage propertySheet = null;
	private boolean preClose = false;
	private Action openWizardAction;
	private Action insertAction;
	private Action deleteAction;
	private Action saveAction;
	private Action reLoadAction;
	private Action createXmlAction;
	private Action createStringAction;
	private Action saveAllAction;
	private Action testAction;
	private Action langAction;
	private Action previewAction;
	private Action imgSettingAction;
    private Action generateVGAAction;
    private Action importStringAction;
    
	private ToolBarManager resourceToolbar;
	private String resDir = "\\Res\\";

	/**
	 * 
	 */
	public ResourceExplorer() {
		super();
		ResourceExplorer.thisInstance = this;
	}

	@Override
	protected void setPartName(String partName) {
		super.setPartName(partName);
	}

	public static ResourceExplorer getResourceView() {
		return ResourceExplorer.thisInstance;
	}
	
	public String getResourceDir(){
		return resDir;
	}
	
	public String findResourceDir(String projectName) {
		if(projectName == null || projectName.isEmpty()) {
			projectName = curProject;
			if(projectName == null || projectName.isEmpty())
				return "";
		}
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		if(project == null)
			return "";
		
		File root = project.getLocation().toFile();
		if(root == null || !root.exists())
			return "";
		File[] files = root.listFiles();
		StringBuilder s;
		for(int i=0; i<files.length; i++) {
			if(files[i] != null && files[i].isDirectory()) {
				String name = files[i].getName();
				if(name.toLowerCase(Locale.getDefault()).equals("res")) {
					s = new StringBuilder("\\");
					s.append(name);
					s.append("\\");
					return s.toString();
				}
			}
		}
		return "";
	}

	public Tag_info getImageInfo(String type) {
		Point point = Activator.getMaxScreen(curProject);
		String screenSize = point.x + "x" + point.y;
		ConfigManager config = configmanager.get(screenSize);
		if (config == null)
			return null;
		return config.getImageInfo(type);
	}

	public Tag_info getImageInfo(String type, String screenSize) {
		if (screenSize == null || screenSize.isEmpty()) {
			Point point = Activator.getMaxScreen(curProject);
			screenSize = point.x + "x" + point.y;
		}

		ConfigManager config = configmanager.get(screenSize);
		if (config == null)
			return null;
		return config.getImageInfo(type);
	}

	public ConfigManager getConfig(String screenSize) {
		return configmanager.get(screenSize);
	}

	public void loadConfig() {
        File dir = ImageSettingDialog.getImageDir();
		
		if(dir == null)
			return;

		StringBuilder s;
		configmanager = new Hashtable<String, ConfigManager>();
		Vector<String> screenSize = Activator.getStringScreen("");
		for (int i = 0; i < screenSize.size(); i++) {
			Point size = Activator.getSScreenToPoint(screenSize.get(i));
			s = new StringBuilder(dir.getAbsolutePath());
			s.append(File.separator);
			s.append(size.x);
			s.append("x");
			s.append(size.y);
			ConfigManager config = new ConfigManager(s.toString());
			configmanager.put(screenSize.get(i), config);
		}
	}

	public void setCurProject(String project, boolean isDelete) {
		Display display;
		if (curProject.equals(project)) {
            refreshTree();
			return;
		}
		Activator.setErrorMessage("", "", null);
		String rootDir = getProjectDir();
		String oldProject = curProject;
		curProject = project;
		if (curProject.isEmpty()) {
			closeAllEditor(false);
			if (manager != null && rootDir != null) {
				refreshProject(oldProject);
			}

			langAction.setEnabled(false);
			loadResource("");
			refreshTree();
			return;
		}
		if (!isDelete && (isDirty() || isOrphanPanelExist())) {
			if (saveWithDialog(oldProject, true) == CANCEL) {
				curProject = oldProject;
				refreshTree();
				return;
			}
		}
		display = getSite().getShell().getDisplay();
		display.syncExec(new Runnable() {
			@Override
			public void run() {
				loadConfig();
			}
		});
		
		resDir = findResourceDir(curProject);
		
        Project prjct = (Project) ResourcesPlugin.getWorkspace().getRoot().getProject(
                curProject);
        if(prjct != null && !ResourcesPlugin.getWorkspace().isTreeLocked()) {
            XmlVersionConvertor.convertCurVersion(prjct, true);
        }
        // ////////////////////////////////////////////////////
//        IFile file = prjct.getFolder("src").getFile("Form1.cpp");
//        String[] listneres = { "Test" };
//        if (file.exists()) {
//            EventListenerUtil util = new EventListenerUtil(file, listneres);
//            try {
//                util.run(null);
//            } catch (CoreException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
        // ////////////////////////////////////////////////////

		closeAllEditor(true);
		loadResource("");

		refreshTree();

		if (manager != null && rootDir != null) {
			refreshProject(oldProject);
		}
	}

	public boolean isDirty() {
		if (manager != null
				&& (manager.isSceneDirty() || manager.isPanelDirty()))
			return true;
		if (manager != null && manager.isPopupDirty())
			return true;

		return false;
	}

	private boolean isDirty(String screen) {
		if (manager != null
				&& (manager.isSceneDirty(screen) || manager.isPanelDirty()))
			return true;
		if (manager != null && manager.isPopupDirty(screen))
			return true;

		return false;
	}

	public int saveWithDialog(String project, boolean checkPanel) {
		int isSave = 0;
		
		Shell shell = getSite().getShell();
		if (project == null || project.isEmpty())
			project = getCurProject();

		if (checkPanel && isOrphanPanelIgnore() == false) {
			return CANCEL;
		}
		if (isDirty()) {

			if (MessageDialog.openQuestion(shell, "Resource : " + project,
					"Form file has been modified. Save changes?")) {
				saveForm(false);
				savePopup();
				isSave = YES;
			} else {
				isSave = NO;
				closeAllEditor(false);
			}
		}

		refreshProject();
		return isSave;
	}

	public int saveWithDialog(String screen, String project, boolean checkPanel) {
		int isSave = 0;
        Shell shell = getSite().getShell();
		if (project == null || project.isEmpty())
			project = getCurProject();

		if (checkPanel && isOrphanPanelIgnore() == false) {
			return CANCEL;
		}
		if (isDirty(screen)) {

			if (MessageDialog.openQuestion(shell, "Resource : " + project,
					"Form file has been modified. Save changes?")) {
				saveForm(false);
				savePopup();
				isSave = YES;
			} else {
				isSave = NO;
				closeAllEditor(false);
			}
		}

		refreshProject();
		return isSave;
	}

	public boolean isOrphanPanelIgnore() {
		if (isOrphanPanelExist()) {
			Shell shell = getSite().getShell();
			if (!MessageDialog.openQuestion(shell, "Resource : " + curProject,
					"The ScrollPanel needs to be assigned a valid ScrollPanel ID or else it will be discarded. Click Yes to discard the ScrollPanel."))
				return false;
		}
		return true;
	}

	boolean isOrphanPanelExist() {
		if (manager == null || manager.m_Panels == null)
			return false;
		Enumeration<String> keys = manager.m_Panels.keys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			Hashtable<String, OspPanel> panels = manager.m_Panels.get(key);
			if(panels == null)
			    continue;
			Enumeration<String> panelKeys = panels.keys();
			while(panelKeys.hasMoreElements()) {
			    String id = panelKeys.nextElement();
    			OspPanel panel = panels.get(id);
    			if (panel != null && panel.m_infoPanel.parent.size() <= 0)
    				return true;
			}
		}
		return false;
	}

	public String getCurProject() {
		return curProject;
	}

	public void handleSelection(IWorkbenchPart sourcepart, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection elements = (IStructuredSelection) selection;
			final Object element;
			if ((element = elements.getFirstElement()) == null)
				return;

			if (element instanceof IProject) {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell()
						.getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								Project project = (Project) element;
								ResourceInfo info = project.getResourceInfo(
										false, false);
								int flags = project.getFlags(info);
								try {
									project.checkExists(flags, true);
								} catch (CoreException e) {
									e.printStackTrace();
									setCurProject("", false);
									return;
								}
								if (!project.isOpen(flags)) {
									return;
								}

								if (!preClose) {
									setCurProject(project.getName(), false);
								}
							}
						});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		resourceTreeViewer = new TreeViewer(parent, SWT.BORDER);
		resourceTreeViewer
				.setContentProvider(new ResourceTreeContentProvider(resourceTreeViewer));
		resourceTreeViewer.setLabelProvider(new ResourceTreeLabelProvider());
		resourceTreeViewer.setSorter(new ResourceViewerSorter());
		preClose = false;

		resourceTreeViewer.setInput(null);
		defineResourceTreeViewerEventHandler();

		// Selection Provider Setting
		getViewSite().setSelectionProvider(resourceTreeViewer);

		// Initialize Context Menu
		initializeContextMenu();

		hookKeyboard();
		// the listener we register with the selection service
		ResourceChangeManager.getInstance();
		if (projectSelectionListener == null) {
			projectSelectionListener = new ISelectionListener() {
				public void selectionChanged(IWorkbenchPart sourcepart,
						ISelection selection) {
					if (sourcepart == null || selection == null)
						return;
					if (0 == sourcepart.getSite().getRegisteredName()
							.compareTo("Project Explorer")) {
						handleSelection(sourcepart, selection);
					}
				}
			};
		}

		// Add the project selection listener
		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(projectSelectionListener);

	}

	public void loadResource(String projectName) {
		String res = findResourceDir(projectName);
		if(res == null || res.isEmpty()) {
			manager = null;
			string = null;
			return;
		}
		resDir = res;
		ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
				.getContentProvider();
		manager = loadXml(projectName, true);
		contentProvider.setManager(manager);
		string = loadString(projectName);
		contentProvider.setString(string);
		Display display = resourceTreeViewer.getTree().getDisplay();
		display.asyncExec(new Runnable(){
                @Override
                public void run() {
                    resourceTreeViewer.setInput(TREE_ROOT);
                    resourceTreeViewer.expandAll();
                }
            });
	}

	public void reLoadString(String projectName) {
		string = loadString(projectName);
		ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
				.getContentProvider();
		contentProvider.setString(string);
	}

	public void reLoadXml(String screen) {
	    if(manager == null)
	        return;
	    
	    manager.loadFolder(screen);
		reOpenFormEditor(screen, false);
        reOpenPanelEditor(screen, false);
        reOpenPopupEditor(screen, false);
	}

	private OspUIString loadString(String projectName) {
		IProject project;
		if (projectName.isEmpty()) {
			if (curProject.isEmpty())
				project = null;
			else
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(
						curProject);
		} else
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					projectName);
		if (project == null) {
			return null;
		}

		String rootDir = project.getLocation().toString() + getResourceDir();

		File file = new File(rootDir);
		if (!file.exists())
			return null;

		OspUIString string = new OspUIString(project.getName());
		string.OpenStrings("");

		if (manager == null) {
			langAction.setEnabled(false);
			imgSettingAction.setEnabled(false);
			return null;
		} else {
			if (string == null) {
				langAction.setEnabled(false);
				imgSettingAction.setEnabled(false);
			} else {
				langAction.setEnabled(true);
				imgSettingAction.setEnabled(true);
			}
			return string;
		}
	}

	public String getProjectDir() {
		String dir;
		if (curProject == null || curProject.isEmpty())
			return null;
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				curProject);
		if (project == null)
			return null;
		dir = project.getLocation().toString() + getResourceDir();
		return dir;
	}

	OspResourceManager loadXml(String XmlFileName, boolean isView) {
		IProject project;
		if (curProject.isEmpty()) {
			previewAction.setEnabled(false);
			project = null;
		} else
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					curProject);
		if (project == null) {
			previewAction.setEnabled(false);
			return null;
		}
		OspResourceManager frame = new OspResourceManager(curProject);
		String rootDir = project.getLocation().toString() + getResourceDir();

		File file = new File(rootDir);
		if (!file.exists()) {
			previewAction.setEnabled(false);
			return null;
		}

		if (!frame.OpenFrames(rootDir)) {
			System.err.println("Form file Open Error : " + rootDir);
			previewAction.setEnabled(false);
			return null;
		}
		if (!isView && frame.getFormCount() == 0) {
			previewAction.setEnabled(false);
			return null;
		}

		if (frame != null)
			previewAction.setEnabled(true);

		return frame;
	}

	public void refreshProject() {
		if (curProject == null || curProject.isEmpty())
			return;
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(curProject);
		Display display = getSite().getShell().getDisplay();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					if (!workspace.isTreeLocked())
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		});
	}

	void refreshProject(String projectName) {
		if (projectName == null || projectName.isEmpty())
			return;
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		Display display = getSite().getShell().getDisplay();
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				try {
					if (!workspace.isTreeLocked())
						project.refreshLocal(IResource.DEPTH_INFINITE, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void refreshTree() {
		ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
				.getContentProvider();

		if (contentProvider != null) {
			if (!curProject.isEmpty() && manager != null) {
				Display display = resourceTreeViewer.getTree().getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
				        ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
		                    .getContentProvider();
				        contentProvider.refreshRootChildren(false);
				        resourceTreeViewer.refresh();
		                setPartName("Resource : " + curProject);
					}
				});
			} else {
				Display display = resourceTreeViewer.getTree().getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						resourceTreeViewer.setInput(null);
						setPartName(PART_NAME);
					}
				});
			}
		}
		saveAllAction.setEnabled(isDirty());
	}

	public static void closeNosaveEditor(String title) {
		final IWorkbenchPage page = PlatformUI.getWorkbench().
		                    getActiveWorkbenchWindow().getActivePage();
		if(page == null)
		    return;
		
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getTitleToolTip().equals(title)) {
				final IEditorReference editor = editors[n];
				Display display = editor.getEditor(false).getSite().getShell()
						.getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						page.closeEditor(editor.getEditor(false), false);
						return;
					}
				});
			}
		}
	}

	public static void closeStringEditor(String title) {
        final IWorkbenchPage page = PlatformUI.getWorkbench().
                            getActiveWorkbenchWindow().getActivePage();
        if(page == null)
            return;
        
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getTitleToolTip().equals(title)) {
				final IEditorReference editor = editors[n];
				Display display = editor.getEditor(false).getSite().getShell()
						.getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						page.closeEditor(editor.getEditor(false), true);
						return;
					}
				});
			}
		}
	}

	public void closeEditor(String screen, String id) {
        final IWorkbenchPage page = getSite().getPage();
        if(page == null)
            return;
        
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getId().equals(OspFormEditor.ID)) {
				final OspFormEditor editor = (OspFormEditor) editors[n]
						.getEditor(false);
				if (editor.m_id.equals(id) && editor.screen.equals(screen)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							page.closeEditor(editor, true);
							return;
						}
					});
				}
			} else if (editors[n] != null
					&& editors[n].getId().equals(OspPopupEditor.ID)) {
				final OspPopupEditor editor = (OspPopupEditor) editors[n]
						.getEditor(false);
				if (editor.m_id.equals(id) && editor.screen.equals(screen)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							page.closeEditor(editor, true);
							return;
						}
					});
				}
            } else if (editors[n] != null
                    && editors[n].getId().equals(OspPanelEditor.ID)) {
                final OspPanelEditor editor = (OspPanelEditor) editors[n]
                        .getEditor(false);
                if (editor.m_id.equals(id) && editor.m_screen.equals(screen)) {
                    Display display = editor.getSite().getShell().getDisplay();
                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                            page.closeEditor(editor, true);
                            return;
                        }
                    });
                }
			}
		}
	}

	public static void closeFormEditor(String screen, String id) {
        final IWorkbenchPage page = PlatformUI.getWorkbench().
                getActiveWorkbenchWindow().getActivePage();
        if(page == null)
            return;

		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getId().equals(OspFormEditor.ID)) {
				final OspFormEditor editor = (OspFormEditor) editors[n]
						.getEditor(false);
				if (editor.m_id.equals(id) && editor.screen.equals(screen)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							page.closeEditor(editor, true);
							return;
						}
					});
				}
			}
		}
	}

	public static void closePopupEditor(String screen, String id) {
        final IWorkbenchPage page = PlatformUI.getWorkbench().
                getActiveWorkbenchWindow().getActivePage();
        if(page == null)
            return;
        
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getId().equals(OspPopupEditor.ID)) {
				final OspPopupEditor editor = (OspPopupEditor) editors[n]
						.getEditor(false);
				if (editor.m_id.equals(id) && editor.screen.equals(screen)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							page.closeEditor(editor, true);
							return;
						}
					});
				}
			}
		}
	}

    public static void closePanelEditor(String screen, String id) {
        final IWorkbenchPage page = PlatformUI.getWorkbench().
                getActiveWorkbenchWindow().getActivePage();
        if(page == null)
            return;

        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            if (editors[n] != null
                    && editors[n].getId().equals(OspPanelEditor.ID)) {
                final OspPanelEditor editor = (OspPanelEditor) editors[n].getEditor(false);
                if (editor.m_id.equals(id) && editor.m_screen.equals(screen)) {
                    Display display = editor.getSite().getShell()
                            .getDisplay();
                    display.syncExec(new Runnable() {
                        @Override
                        public void run() {
                            page.closeEditor(editor, true);
                            return;
                        }
                    });
                }
            }
        }
    }

	public void reopenStringEditor(String title) {
	    if(title == null || title.isEmpty())
	        title = cszKindName[KIND_STRING];
		final IWorkbenchPage page = getSite().getPage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getTitleToolTip().equals(title)) {
				final IEditorReference editor = editors[n];
				Display display = editor.getEditor(false).getSite().getShell()
						.getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						page.closeEditor(editor.getEditor(false), true);
						try {
							page.openEditor(new OspStringEditorInput(string),
									OspStringEditor.ID, true);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
						return;
					}
				});
			}
		}
	}

	public void reOpenFormEditor(final String screen, final boolean isSave) {
		final IWorkbenchPage page = getSite().getPage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			final IEditorPart editor = editors[n].getEditor(isSave);
			if (editor instanceof OspFormEditor) {
				final String id = ((OspFormEditor) editor).m_id;
				Display display = editor.getSite().getShell().getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						OspFormEditorInput input = (OspFormEditorInput) editor
								.getEditorInput();
						if(!input.screen.equals(screen))
						    return;
						
						page.closeEditor(editor, isSave);
						try {
						    if(manager.m_Form.get(screen).get(id) == null)
						        return;
						    
							page.openEditor(new OspFormEditorInput(manager,
									input.screen, id, string),
									OspFormEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
						} catch (PartInitException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		}
	}

    public void reOpenFormEditor(final String screen, final String id, final boolean isSave) {
        final IWorkbenchPage page = getSite().getPage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            final IEditorPart editor = editors[n].getEditor(isSave);
            if (editor instanceof OspFormEditor) {
                if(id == null || !id.equals(((OspFormEditor) editor).m_id))
                    continue;
                Display display = editor.getSite().getShell().getDisplay();
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        OspFormEditorInput input = (OspFormEditorInput) editor
                                .getEditorInput();
                        if(!input.screen.equals(screen))
                            return;
                        
                        page.closeEditor(editor, isSave);
                        try {
                            if(manager.m_Form.get(screen).get(id) == null)
                                return;
                            
                            page.openEditor(new OspFormEditorInput(manager,
                                    input.screen, id, string),
                                    OspFormEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
                        } catch (PartInitException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void reOpenPanelEditor(final String screen, final boolean isSave) {
        final IWorkbenchPage page = getSite().getPage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            final IEditorPart editor = editors[n].getEditor(isSave);
            if (editor instanceof OspPanelEditor) {
                final String id = ((OspPanelEditor) editor).m_id;
                Display display = editor.getSite().getShell().getDisplay();
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        OspPanelEditorInput input = (OspPanelEditorInput) editor
                                .getEditorInput();
                        if(!input.screen.equals(screen))
                            return;
                        page.closeEditor(editor, isSave);
                        try {
                            if(manager.m_Panels.get(screen).get(id) == null)
                                return;
                            page.openEditor(new OspPanelEditorInput(manager,
                                    input.screen, id, string), OspPanelEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
                        } catch (PartInitException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void reOpenPanelEditor(final String screen, final String id, final boolean isSave) {
        final IWorkbenchPage page = getSite().getPage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            final IEditorPart editor = editors[n].getEditor(isSave);
            if (editor instanceof OspPanelEditor) {
                if(id == null || !id.equals(((OspPanelEditor) editor).m_id))
                    continue;
                Display display = editor.getSite().getShell().getDisplay();
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        OspPanelEditorInput input = (OspPanelEditorInput) editor
                                .getEditorInput();
                        if(!input.screen.equals(screen))
                            return;
                        page.closeEditor(editor, isSave);
                        try {
                            if(manager.m_Panels.get(screen).get(id) == null)
                                return;
                            page.openEditor(new OspPanelEditorInput(manager,
                                    input.screen, id, string), OspPanelEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
                        } catch (PartInitException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

	public void reOpenPopupEditor(final String screen, final boolean isSave) {
		final IWorkbenchPage page = getSite().getPage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			final IEditorPart editor = editors[n].getEditor(isSave);
			if (editor instanceof OspPopupEditor) {
				final String id = ((OspPopupEditor) editor).m_id;
				Display display = editor.getSite().getShell().getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						OspPopupEditorInput input = (OspPopupEditorInput) editor
								.getEditorInput();
                        if(!input.screen.equals(screen))
                            return;
						page.closeEditor(editor, isSave);
						try {
						    if(manager.m_Popup.get(screen).get(id) == null)
						        return;
							page.openEditor(new OspPopupEditorInput(manager,
									input.screen, id, string),
									OspPopupEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
						} catch (PartInitException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		}
	}

    public void reOpenPopupEditor(final String screen, final String id, final boolean isSave) {
        final IWorkbenchPage page = getSite().getPage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            final IEditorPart editor = editors[n].getEditor(isSave);
            if (editor instanceof OspPopupEditor) {
                if(id == null || !id.equals(((OspPopupEditor) editor).m_id))
                    continue;
                Display display = editor.getSite().getShell().getDisplay();
                display.syncExec(new Runnable() {
                    @Override
                    public void run() {
                        OspPopupEditorInput input = (OspPopupEditorInput) editor
                                .getEditorInput();
                        if(!input.screen.equals(screen))
                            return;
                        page.closeEditor(editor, isSave);
                        try {
                            if(manager.m_Popup.get(screen).get(id) == null)
                                return;
                            page.openEditor(new OspPopupEditorInput(manager,
                                    input.screen, id, string),
                                    OspPopupEditor.ID, true);
                        } catch (NullPointerException e1) {
                            e1.printStackTrace();
                        } catch (PartInitException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        }
    }

	public void reOpenEditor(String screen, boolean isSave) {
		reOpenFormEditor(screen, isSave);
        reOpenPanelEditor(screen, isSave);
		reOpenPopupEditor(screen, isSave);
	}

	public void closeAllEditor(final boolean isSave) {
		final IWorkbenchPage page = getSite().getPage();
		if (page == null)
			return;

		IEditorReference[] editors = page.getEditorReferences();
		if (editors == null)
			return;
		for (int n = 0; n < editors.length; n++) {
			if (editors[n].getId().equals(OspFormEditor.ID)
					|| editors[n].getId().equals(OspPopupEditor.ID)
					|| editors[n].getId().equals(OspPanelEditor.ID)
					|| editors[n].getId().equals(OspStringEditor.ID)) {
				final IEditorReference editor = editors[n];
				Display display = editor.getEditor(false).getSite().getShell()
						.getDisplay();
				display.syncExec(new Runnable() {
					@Override
					public void run() {
						page.closeEditor(editor.getEditor(false), isSave);
					}
				});
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (resourceTreeViewer != null)
			resourceTreeViewer.getControl().setFocus();
	}

	public void saveString() {
		if (string == null)
			return;

		string.SaveAll();
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			IEditorReference[] editors = page.getEditorReferences();
			if (editors != null) {
				for (int n = 0; n < editors.length; n++) {
					IEditorPart editor = editors[n].getEditor(false);
					if (editor instanceof OspStringEditor)
						((OspStringEditor) editor).clearDirty();
				}
			}
		}
	}

    public void savePopup() {
        if (manager == null)
            return;
        
        Enumeration<String> keys = manager.m_Form.keys();
        while(keys.hasMoreElements())
            savePopup(keys.nextElement());
    }

	public void savePopup(final String screen) {
		if (manager == null)
			return;

		manager.SaveAllPopup(screen);
		saveString();
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			IEditorReference[] editors = page.getEditorReferences();
			if (editors != null) {
				for (int n = 0; n < editors.length; n++) {
					final IEditorPart editor = editors[n].getEditor(false);
					if (editor instanceof OspPopupEditor) {
		                Display display = editor.getSite().getShell().getDisplay();
		                display.syncExec(new Runnable() {
		                    @Override
		                    public void run() {
		                        OspPopupEditorInput input = (OspPopupEditorInput) editor
		                                .getEditorInput();
		                        if(!input.screen.equals(screen))
		                            return;
		                        
								((OspPopupEditor) editor).clearDirty();
							}
						});
					}
				}
			}
		}
	}

	public void saveForm(boolean isQuestion) {
        if (manager == null)
            return;
        
        Enumeration<String> keys = manager.m_Form.keys();
        while(keys.hasMoreElements())
            saveForm(keys.nextElement(), isQuestion);
	}

	public void saveForm(final String screen, boolean isQuestion) {
		if (manager == null)
			return;

		manager.SaveAllForm(screen, isQuestion);
		saveString();
		IWorkbenchPage page = getSite().getPage();
		if (page != null) {
			IEditorReference[] editors = page.getEditorReferences();
			if (editors != null) {
				for (int n = 0; n < editors.length; n++) {
					final IEditorPart editor = editors[n].getEditor(false);
					if (editor instanceof OspFormEditor) {
                        Display display = editor.getSite().getShell().getDisplay();
                        display.syncExec(new Runnable() {
                            @Override
                            public void run() {
                                OspFormEditorInput input = (OspFormEditorInput) editor
                                        .getEditorInput();
                                if(!input.screen.equals(screen))
                                    return;
                                
								((OspFormEditor) editor).clearDirty();
							}
						});
					} else if (editor instanceof OspPanelEditor) {
                        Display display = editor.getSite().getShell().getDisplay();
                        display.syncExec(new Runnable() {
                            @Override
                            public void run() {
                                OspPanelEditorInput input = (OspPanelEditorInput) editor
                                        .getEditorInput();
                                if(!input.screen.equals(screen))
                                    return;
                                
								((OspPanelEditor) editor).clearDirty();
							}
						});
					}
				}
			}
		}
	}

	@Override
	protected void firePropertyChange(int propertyId) {
		super.firePropertyChange(propertyId);
	}
	
	public Action getPreviewAction() {
	    return previewAction;
	}

    public Action getLanguageAction() {
        return langAction;
    }

    public Action getSaveAllAction() {
        return saveAllAction;
    }
    
	protected void createAction() {
		// create our action
		openWizardAction = new Action() {
			public void run() {
				IWorkbenchWindow window = getSite().getWorkbenchWindow();
				IWorkbenchWizard wizard;
				wizard = new OspCreateResourceWizard(ResourceExplorer.this
						.getCurProject());

				ISelection selection = window.getSelectionService()
						.getSelection();

				if (selection instanceof IStructuredSelection) {
					wizard.init(window.getWorkbench(),
							(IStructuredSelection) selection);
				} else {
					wizard.init(window.getWorkbench(),
							StructuredSelection.EMPTY);
				}

				Shell parent = window.getShell();
				WizardDialog dialog = new WizardDialog(parent, wizard);
				dialog.setHelpAvailable(false);
				dialog.create();
				if (dialog.open() == WizardDialog.OK) {
					ResourceExplorer.this
							.firePropertyChange(WorkbenchPart.PROP_TITLE);
				}
			}
		};
		openWizardAction.setText(ACTION_WIZARD);
        openWizardAction.setId(ACTION_WIZARD);
		insertAction = new Action() {
			public void run() {

				ResourceExplorer.this
						.firePropertyChange(WorkbenchPart.PROP_TITLE);
				Point point = Activator.getMaxScreen(curProject);
				String screen = point.x + "x" + point.y;
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;
                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;
                TreeObject item = (TreeObject) element;
				int depth = item.getDepth();
				int kind = item.getKind();
				if (item.info instanceof String)
					screen = (String) item.info;

				if (depth != DEPTH_GROUP)
					return;

				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (projects.length == 0)
					return;
				IWorkbenchPage page = getSite().getPage();

				if (kind == KIND_FORM) {
					FORMFRAME_INFO info = manager.InsertScene(screen,
							OspResourceManager.SCENETEMPLATE_EMPTY);

					refreshProject();

					if (info.Id != null && !info.Id.isEmpty()) {
						try {
							page.openEditor(new OspFormEditorInput(manager,
									screen, info.Id, string),
									OspFormEditor.ID, true);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				} else if (kind == KIND_POPUP) {
					POPUPFRAME_INFO info = manager.InsertPopup(screen,
							OspResourceManager.SCENETEMPLATE_EMPTY);

					refreshProject();

					if (info.Id != null && !info.Id.isEmpty()) {
						try {
							page.openEditor(new OspPopupEditorInput(manager,
									screen, info.Id, string),
									OspPopupEditor.ID, true);
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				} else if (kind == KIND_PANEL) {
					PANELFRAME_INFO iteminfo = manager.InsertPanel(screen, null);

					refreshProject();

					if (iteminfo.Id != null && !iteminfo.Id.isEmpty()) {
						try {
							page.openEditor(new OspPanelEditorInput(manager,
									screen, iteminfo.Id, string), OspPanelEditor.ID,
									true);
							OspStringEditor.refreshFrameEditor();
						} catch (PartInitException e) {
							e.printStackTrace();
						}
					}
				}

				ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();
                contentProvider.refreshRootChildren(false);
            	expandToLevel(item, 1);
				refreshTree();
			}
		};
		insertAction.setText(ACTION_INSERT);
        insertAction.setId(ACTION_INSERT);
		deleteAction = new Action() {
			public void run() {
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;
                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;
                TreeObject item = (TreeObject) element;

				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (projects.length == 0)
					return;
				
				String id = item.getId();
				int kind = item.getKind();
				if (item.getDepth() == DEPTH_ROOT
						|| item.getDepth() == DEPTH_GROUP)
					return;

				File file;
				String screen;
				switch (kind) {
				case KIND_STRING:
					closeStringEditor(id);
					break;
				case KIND_FORM:
					screen = getItemScreen(item);
					manager.DeleteScene(screen, id);
					IProject project = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(curProject);
					if (project == null)
						return;

					closeFormEditor(screen, id);
					refreshProject();
					break;
				case KIND_POPUP:
					screen = getItemScreen(item);
					manager.DeletePopup(screen, id);
					project = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(curProject);
					if (project == null)
						return;
					String resDir = project.getLocation().toString() + "\\."
							+ id;
					file = new File(resDir);
					if (file.exists())
						file.delete();

					closePopupEditor(screen, id);
					refreshProject();
					break;
				case KIND_PANEL:
                    screen = getItemScreen(item);
					manager.DeletePanel(screen, id);
					project = ResourcesPlugin.getWorkspace().getRoot()
							.getProject(curProject);
					if (project == null)
						return;

					closePanelEditor(screen, id);
					OspStringEditor.refreshFrameEditor();
					refreshProject();
				}

				refreshTree();
			}
		};
		deleteAction.setText(ACTION_DELETE);
        deleteAction.setId(ACTION_DELETE);
		saveAction = new Action() {
			public void run() {
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (projects.length == 0)
					return;
				if (saveAction.getText().equals(SAVE_ALL)) {
					saveString();
					saveForm(true);
					savePopup();
    				refreshProject();
    				refreshTree();
    				return;
				}
				
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;
                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;
                TreeObject item = (TreeObject) element;
                
                ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();

                switch(item.getDepth()) {
                case DEPTH_ROOT:
                    if(item.getKind() == KIND_STRING)
                        saveString();
                    else {
                        saveForm(item.getId(), true);
                        savePopup(item.getId());
                        contentProvider.refreshChildren(item, true);
                    }
                    expandToLevel(item, 1);
                    break;
                case DEPTH_GROUP:
                    saveForm(item.getParent().getId(), true);
                    savePopup(item.getParent().getId());
                    contentProvider.refreshChildren(item.getParent(), true);
                    expandToLevel(item.getParent(), 1);
                    break;
                case DEPTH_TABLE:
                    saveForm(item.getParent().getParent().getId(), true);
                    savePopup(item.getParent().getParent().getId());
                    contentProvider.refreshChildren(item.getParent().getParent(), true);
                    expandToLevel(item.getParent().getParent(), 1);
                    break;
                }
                refreshProject();
                refreshTree();
			}

		};
		saveAction.setText(SAVE_ALL);
        saveAction.setId(SAVE_ID);

        generateVGAAction = new Action() {
            public void run() {
                IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
                        .getProjects();
                if (projects.length == 0)
                    return;
                if (generateVGAAction.getText().indexOf(GENERATE_WQVGA) >= 0) {
                    generateWVGA(WQVGA);
                } else if (generateVGAAction.getText().indexOf(GENERATE_WVGA) >= 0) {
                    generateWVGA(WVGA);
                }
                refreshProject();
                refreshTree();
            }

        };
        generateVGAAction.setText(GENERATE_WQVGA);
        generateVGAAction.setId(GENERATE_WQVGA);
        
		saveAllAction = new Action() {
			public void run() {
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (projects.length == 0)
					return;
				saveString();
				saveForm(true);
				savePopup();
				refreshProject();
				refreshTree();
			}

		};
		saveAllAction.setText(SAVE_ALL);
		saveAllAction.setId(SAVE_ALL);

		reLoadAction = new Action() {
			public void run() {
                if (reLoadAction.getText().equals(RELOAD_ALL)) {
                    closeAllEditor(false);
                    loadResource("");
                    return;
                }
                
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;
                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;
                TreeObject item = (TreeObject) element;
                
                ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();

                switch(item.getDepth()) {
                case DEPTH_ROOT:
//                    closeAllEditor(false);
                    reLoadXml(item.getId());
                    contentProvider.refreshChildren(item, true);
                    expandToLevel(item, 1);
                    break;
                case DEPTH_GROUP:
//                    closeAllEditor(false);
                    reLoadXml(item.getParent().getId());
                    contentProvider.refreshChildren(item.getParent(), true);
                    expandToLevel(item.getParent(), 1);
                    break;
                case DEPTH_TABLE:
//                    closeAllEditor(false);
                    reLoadXml(item.getParent().getParent().getId());
                    contentProvider.refreshChildren(item.getParent().getParent(), true);
                    expandToLevel(item.getParent().getParent(), 1);
                    break;
                }
				refreshTree();
			}

		};
		reLoadAction.setText(RELOAD_ALL);
        reLoadAction.setId(RELOAD_ALL);

		createXmlAction = new Action() {
			public void run() {

				if (manager != null || getCurProject().isEmpty())
					return;
				reLoadXml("");
				reLoadString("");
			}
		};
		createXmlAction.setText(ACTION_CREATE_FORM);
        createXmlAction.setId(ACTION_CREATE_FORM);

		createStringAction = new Action() {
			public void run() {

				if (getCurProject().isEmpty())
					return;

				reLoadString("");
				if (string == null || string.m_Strings.size() == 0) {
					string = new OspUIString(getCurProject());
					string.InsertString(OspUIString.DEFAULT_LANG1);
				}
			}

		};
		createStringAction.setText(ACTION_CREATE_STRING);
        createStringAction.setId(ACTION_CREATE_STRING);

		langAction = new Action() {
			public void run() {
				if (curProject == null || curProject.isEmpty()) {
					Shell shell = Display.getCurrent().getActiveShell();
					MessageDialog.openWarning(shell, ACTION_SELECTLANGUAGE,
							"No project is selected. Please select a project and try again.");
					return;
				}

				Shell shell = new Shell();

				if (string == null)
					return;
				
				String stringName = langAction.getDescription();

				LanguageListDialog input;
				if(stringName != null && !stringName.isEmpty() && !stringName.equals(ACTION_SELECTLANGUAGE))
					input = new LanguageListDialog(shell, string, stringName);
				else
					input = new LanguageListDialog(shell, string);
				
				if (input.open() == true) {
					string.SaveAll();
					reopenStringEditor(cszKindName[KIND_STRING]);
					refreshProject();
				} else
					return;
			}

		};

		langAction.setToolTipText(ACTION_SELECTLANGUAGE);
		langAction.setId(ACTION_SELECTLANGUAGE);
		langAction.setImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/string.png"));
		langAction.setEnabled(false);

		imgSettingAction = new Action() {
			public String screen = "";

			public void run() {
				openImgSettingDlg(screen);
			}

		};

		imgSettingAction.setToolTipText(ACTION_IMGSETTING);
		imgSettingAction.setId(ACTION_IMGSETTING);
		imgSettingAction.setImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/bitmap.png"));
		imgSettingAction.setEnabled(false);
		
        importStringAction = new Action() {
            public void run() {
                importExcelToString();
            }
        };
        importStringAction.setText(IMPORTSTRING_ACTION);
        importStringAction.setId(IMPORTSTRING_ACTION);

		previewAction = new Action() {
			public void run() {
				IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (projects.length == 0) {
					Shell shell = Display.getCurrent().getActiveShell();
					MessageDialog.openWarning(shell, ACTION_PREVIEWSCENE,
							"Project is empty!");
					return;
				}

				if (manager == null) {
					Shell shell = Display.getCurrent().getActiveShell();
					MessageDialog.openWarning(shell, ACTION_PREVIEWSCENE,
							"Select Project!");
					return;
				}

				Shell shell = new Shell();

				FormPreviewDialog preview = new FormPreviewDialog(shell,
						ResourceExplorer.this);
				
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;
                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;
                TreeObject item = (TreeObject) element;
                int depth = item.getDepth();
                int kind = item.getKind();
                if(kind == KIND_STRING)
                    return;
                
                String screen = "";
                switch(depth) {
                case DEPTH_ROOT:
                    screen = item.getId();
                    break;
                case DEPTH_GROUP:
                    if (item.info instanceof String)
                        screen = (String) item.info;
                    break;
                case DEPTH_TABLE:
                    if(item.getParent().info instanceof String)
                        screen = (String)item.getParent().info;
                    break;
                default:
                    return;
                }
                
				if (preview.open(screen) == true) {
				} else
					return;
			}

		};
		previewAction.setToolTipText(ACTION_PREVIEWSCENE);
		previewAction.setId(ACTION_PREVIEWSCENE);
		previewAction.setImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/frame.png"));
		previewAction.setEnabled(false);

		testAction = new Action() {
			public void run() {
			    TestAction action = new TestAction();
			    action.testThumbnail();
			}
		};
		testAction.setText(TEST_ACTION);
		testAction.setId(TEST_ACTION);
	}

	/**
     * 
     */
    protected void importExcelToString() {
        String[] filterExt = { "*.xls;*.xlsx;" };
        
        FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow().getShell());
        
        fileDialog.setFilterExtensions(filterExt);
        String fileName = fileDialog.open();

        if(fileName != null && !fileName.isEmpty()) {
            ExcelFileReaderPOI reader = new ExcelFileReaderPOI(string, fileName);
            reader.importSheet(0, false);
            reader.refreshEditor();
        }
    }

    /**
     * @param id
     * @param vga
     */
    protected void generateWVGA(String vga) {
        TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
        if(selection.length <= 0)
            return;
        Object element = selection[0].getData();
        if(!(element instanceof TreeObject))
            return;

        TreeObject item = (TreeObject) element;
        String screen = generateVGAAction.getDescription();
        switch(item.getDepth()) {
        case DEPTH_ROOT:
            generateVGARoot(screen, vga, item);
            break;
        case DEPTH_GROUP:
            switch(item.getKind()) {
            case KIND_FORM:
                generateVGAFormAll(screen, vga, item);
                break;
            case KIND_PANEL:
                generateVGAPanelAll(screen, vga, item);
                break;
            case KIND_POPUP:
                generateVGAPopupAll(screen, vga, item);
                break;
            }
            break;
        case DEPTH_TABLE:
            switch(item.getKind()) {
            case KIND_FORM:
                generateVGAForm(screen, vga, item);
                break;
            case KIND_PANEL:
                generateVGAPanel(screen, vga, item);
                break;
            case KIND_POPUP:
                generateVGAPopup(screen, vga, item);
                break;
            }
        }
    }

    /**
     * @param screen
     * @param vga
     */
    private void generateVGARoot(String screen, String vga, TreeObject item) {
        generateVGAFormAll(screen, vga, item.getChild(cszKindName[KIND_FORM]));
        generateVGAPanelAll(screen, vga, item.getChild(cszKindName[KIND_PANEL]));
        generateVGAPopupAll(screen, vga, item.getChild(cszKindName[KIND_POPUP]));
    }

    /**
     * @param screen
     * @param vga
     * @param item 
     */
    private void generateVGAFormAll(String screen, String vga, TreeObject item) {
        Hashtable<String, OspForm> forms = manager.m_Form.get(screen);
        if(forms == null)
            return;
        
        Enumeration<String> keys = forms.keys();
        while(keys.hasMoreElements()) {
            String id = keys.nextElement();
            generateVGAForm(screen, vga, item.getChild(id));
        }
    }

    /**
     * @param screen
     * @param vga
     * @param item
     */
    private void generateVGAForm(String screen, String vga, TreeObject item) {
        Hashtable<String, OspForm> forms = manager.m_Form.get(screen);
        if(forms == null)
            return;
        
        OspForm form = forms.get(item.getId());
        if(form == null)
            return;

        vga = Activator.getCorrectScreen(curProject, vga);
        OspForm newForm = form.getClone(vga);
 
        if(manager.addForm(newForm)) {
            closeFormEditor(vga, item.getId());
            
            ResourceTreeContentProvider provider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();
            TreeObject itemScreen = provider.getRootItem(vga);
            if(itemScreen == null)
                return;
            TreeObject itemForms = itemScreen.getChild(cszKindName[KIND_FORM]);
            if(itemForms == null)
                return;
            forms = manager.m_Form.get(vga);
            provider.syncTable(itemForms, forms, KIND_FORM);
            TreeObject tItem = itemForms.getChild(item.getId());
            if(tItem == null)
                return;
             expandToLevel(tItem, 1);
        }
    }

    /**
     * @param screen
     * @param vga
     * @param item 
     */
    private void generateVGAPanelAll(String screen, String vga, TreeObject item) {
        Hashtable<String, OspPanel> panels = manager.m_Panels.get(screen);
        if(panels == null)
            return;
        
        Enumeration<String> keys = panels.keys();
        while(keys.hasMoreElements()) {
            String id = keys.nextElement();
            generateVGAPanel(screen, vga, item.getChild(id));
        }
    }

    /**
     * @param screen
     * @param vga
     * @param item
     */
    private void generateVGAPanel(String screen, String vga, TreeObject item) {
        Hashtable<String, OspPanel> panels = manager.m_Panels.get(screen);
        if(panels == null)
            return;
        
        OspPanel panel = panels.get(item.getId());
        if(panel == null)
            return;

        vga = Activator.getCorrectScreen(curProject, vga);
        OspPanel newPanel = panel.getClone(vga);
        
        if(manager.addPanel(newPanel)) {
            closePanelEditor(vga, item.getId());
            
            ResourceTreeContentProvider provider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();
            TreeObject itemScreen = provider.getRootItem(vga);
            if(itemScreen == null)
                return;
            TreeObject itemPanels = itemScreen.getChild(cszKindName[KIND_PANEL]);
            if(itemPanels == null)
                return;
            panels = manager.m_Panels.get(vga);
            provider.syncTable(itemPanels, panels, KIND_PANEL);
            TreeObject tItem = itemPanels.getChild(item.getId());
            if(tItem == null)
                return;
            expandToLevel(tItem, 1);
        }
    }

    /**
     * @param screen
     * @param vga
     * @param item 
     */
    private void generateVGAPopupAll(String screen, String vga, TreeObject item) {
        Hashtable<String, OspPopup> popups = manager.m_Popup.get(screen);
        if(popups == null)
            return;
        
        Enumeration<String> keys = popups.keys();
        while(keys.hasMoreElements()) {
            String id = keys.nextElement();
            generateVGAPopup(screen, vga, item.getChild(id));
        }
    }

    /**
     * @param screen
     * @param vga
     * @param item
     */
    private void generateVGAPopup(String screen, String vga, TreeObject item) {
        Hashtable<String, OspPopup> popups = manager.m_Popup.get(screen);
        if(popups == null)
            return;
        
        OspPopup popup = popups.get(item.getId());
        if(popup == null)
            return;

        vga = Activator.getCorrectScreen(curProject, vga);
        OspPopup newPopup = popup.getClone(vga);
        
        if(this.manager.addPopup(newPopup)) {
            closePopupEditor(vga, item.getId());

            ResourceTreeContentProvider provider = (ResourceTreeContentProvider) resourceTreeViewer.getContentProvider();
            TreeObject itemScreen = provider.getRootItem(vga);
            if(itemScreen == null)
                return;
            TreeObject itemPopups = itemScreen.getChild(cszKindName[KIND_POPUP]);
            if(itemPopups == null)
                return;
            popups = this.manager.m_Popup.get(vga);
            provider.syncTable(itemPopups, popups, KIND_POPUP);
            TreeObject tItem = itemPopups.getChild(item.getId());
            if(tItem == null)
                return;
            expandToLevel(tItem, 1);
        }
    }

    /**
     * @param item
     * @param level
     */
    private void expandToLevel(final TreeObject item, final int level) {
        Display display = resourceTreeViewer.getTree().getDisplay();
        display.asyncExec(new Runnable(){
                @Override
                public void run() {
                    resourceTreeViewer.expandToLevel(item, level);
                }
            });
    }

    protected String getItemScreen(TreeObject item) {
		ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
				.getContentProvider();
		TreeObject obj = (TreeObject) contentProvider.getParent(item);
		if (obj == null)
			return null;
		if (obj.info instanceof String)
			return (String) obj.info;
		return null;
	}

	protected void openImgSettingDlg(String screenSize) {
		if (curProject == null || curProject.isEmpty()) {
			Shell shell = Display.getCurrent().getActiveShell();
			MessageDialog.openWarning(shell, ACTION_IMGSETTING,
					"Project is not selected. Please select a project and try again.");
			return;
		}

		Shell shell = new Shell();

		if (string == null)
			return;

		ImageSettingDialog input = new ImageSettingDialog(shell,
				ResourceExplorer.this, screenSize);
		if (input.open() == true) {
			ConfigManager config = configmanager.get(screenSize);
			if(config != null)
				config.loadXML();
		} else {
			return;
		}
	}

	protected void captureEditorPart() {
		final Shell shell = new Shell(getSite().getShell(), SWT.FLAT
				| SWT.CLOSE);
		shell.setSize(1024, 800);
		shell.setText(TEST_ACTION);

		org.eclipse.swt.graphics.Rectangle rect = shell.getBounds();

		int x = -rect.width;
		int y = -rect.height;

		shell.setLocation(x, y);
		shell.setBackground(ColorConstants.lightGray);

		IWorkbenchPage page = getSite().getPage();
		IEditorPart editor = page.getActiveEditor();
		GraphicalViewer viewer;
		RootEditPart rootEditPart;
		AbstractGraphicalEditPart part;
		if (editor instanceof OspFormEditor) {
			viewer = ((OspFormEditor) editor).getGraphicalViewer();
			rootEditPart = viewer.getRootEditPart();
			part = ((OspFormEditor) editor).getModel().getPart();
		} else if (editor instanceof OspPopupEditor) {
			viewer = ((OspPopupEditor) editor).getGraphicalViewer();
			rootEditPart = viewer.getRootEditPart();
			part = ((OspPopupEditor) editor).getModel().getPart();
		} else if (editor instanceof OspPanelEditor) {
			viewer = ((OspPanelEditor) editor).getGraphicalViewer();
			rootEditPart = viewer.getRootEditPart();
			part = ((OspPanelEditor) editor).getModel().getPart();
		} else {
			return;
		}

		final Canvas canvas = new Canvas(shell, SWT.NULL);
		Rectangle frameRect = part.getFigure().getBounds();
		canvas.setSize(frameRect.width, frameRect.height);
		LightweightSystem lws = new LightweightSystem(canvas);

		final ImageThumbnail thumbnail = new ImageThumbnail(
				(Viewport) ((ScalableRootEditPart) rootEditPart).getFigure(),
				part);
		thumbnail.setSource(part.getFigure());
		lws.setContents(thumbnail);

		shell.open();
		shell.addShellListener(new ShellListener() {

			@Override
			public void shellActivated(ShellEvent e) {
				Image image = thumbnail.getThumbnailImage();
				if (image == null)
					return;
				String id = thumbnail.getId();
				BufferedImage bffImg = ImageUtil.convertToAWT(image
						.getImageData(), ColorConstants.black);
				String fullName = "";
				try {
					File dir = new File(CAPTURE_PATH);
					if (!dir.exists())
						dir.mkdir();
					dir = new File(CAPTURE_PATH + curProject);
					if (!dir.exists())
						dir.mkdir();

					Date start = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"_yyyyMMddHHmmss");
					String time = formatter.format(start);
					fullName = CAPTURE_PATH + curProject + "/" + id + time
							+ "." + IMG_FORMAT_JPG;
					String format = IMG_FORMAT_JPG;
					File file = new File(fullName);
					ImageIO.write(bffImg, format, file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				thumbnail.deactivate();
				canvas.dispose();
				shell.close();
			}

			@Override
			public void shellClosed(ShellEvent e) {
			}

			@Override
			public void shellDeactivated(ShellEvent e) {
				Image image = thumbnail.getThumbnailImage();
				if (image == null)
					return;
				String id = thumbnail.getId();
				BufferedImage bffImg = ImageUtil.convertToAWT(image
						.getImageData(), ColorConstants.black);
				String fullName = "";
				try {
					File dir = new File(CAPTURE_PATH);
					if (!dir.exists())
						dir.mkdir();
					dir = new File(CAPTURE_PATH + curProject);
					if (!dir.exists())
						dir.mkdir();

					Date start = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"_yyyyMMddHHmmss");
					String time = formatter.format(start);
					fullName = CAPTURE_PATH + curProject + "/" + id + time
							+ "." + IMG_FORMAT_JPG;
					String format = IMG_FORMAT_JPG;
					File file = new File(fullName);
					ImageIO.write(bffImg, format, file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				thumbnail.deactivate();
				canvas.dispose();
				shell.close();
			}

			@Override
			public void shellDeiconified(ShellEvent e) {
			}

			@Override
			public void shellIconified(ShellEvent e) {
			}
		});
	}

	protected void addMenuAll() {
		contextMenuMgr.removeAll();
		// add it
		contextMenuMgr.add(openWizardAction);
		openWizardAction.setEnabled(true);
        contextMenuMgr.add(saveAction);
		contextMenuMgr.add(insertAction);
		contextMenuMgr.add(deleteAction);
		contextMenuMgr.add(reLoadAction);
        contextMenuMgr.add(generateVGAAction);
        contextMenuMgr.add(importStringAction);

		WorkbenchWindow window = (WorkbenchWindow) getSite()
				.getWorkbenchWindow();

		MenuManager menuManager = window.getMenuBarManager();
		MenuManager projectMenu = (MenuManager) menuManager
				.find(IWorkbenchActionConstants.M_PROJECT);
		MenuManager fileMenu = (MenuManager) menuManager
				.find(IWorkbenchActionConstants.M_FILE);

		if (fileMenu != null) {
			IContributionItem saveAllMenu;
			if ((saveAllMenu = fileMenu.find(SAVE_ALL)) != null)
				fileMenu.remove(saveAllMenu);
			fileMenu.insertAfter(ActionFactory.SAVE_ALL.getId(), saveAllAction);
			saveAllAction.setEnabled(false);
		}
		if (projectMenu != null) {
		// IContributionItem testMenu;
		// if ((testMenu = projectMenu.find(TEST_ACTION)) != null)
		// projectMenu.remove(testMenu);
		// projectMenu.appendToGroup(IWorkbenchActionConstants.OPEN_EXT,
		// testAction);
//            closeMenu = projectMenu.find(IDEActionFactory.CLOSE_PROJECT.getId());
		}

		CoolBarManager coolBarManager = window.getCoolBarManager();
		ToolBarContributionItem toolbar = (ToolBarContributionItem) coolBarManager
				.find(ID_TOOLBAR);
		if (toolbar != null) {
			toolbar.dispose();
			coolBarManager.remove(ID_TOOLBAR);
		}

		resourceToolbar = new ToolBarManager(coolBarManager.getStyle());
		toolbar = new ToolBarContributionItem(resourceToolbar);
		toolbar.setId(ID_TOOLBAR);

		coolBarManager.add(toolbar);
		//resourceToolbar.add(langAction);
		resourceToolbar.add(new Separator());
		// resourceToolbar.add(previewAction);
		// resourceToolbar.add(new Separator());
		// resourceToolbar.add(imgSettingAction);
		// resourceToolbar.add(new Separator());

		// coolBarManager.appendToGroup("group.file", testAction);

		// required, for extensions
		contextMenuMgr
				.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));

		contextMenuMgr
				.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	protected void initializeContextMenu() {
		final TreeViewer viewer = resourceTreeViewer;
		// creating the Menu and registering it
		contextMenuMgr = new MenuManager("#PopupMenu");
		contextMenuMgr.setRemoveAllWhenShown(false);
		final Menu menu = contextMenuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getViewSite().registerContextMenu(contextMenuMgr, viewer);

		createAction();
		addMenuAll();

        // Enable / Disable Menu
		viewer.getControl().addMouseListener(new MouseListener(){
            @Override
            public void mouseDoubleClick(MouseEvent e) {
            }
            @Override
            public void mouseDown(MouseEvent e) {
                if(e.button != 3)
                    return;
                
                Point screenPoint = getViewSite().getShell().getDisplay()
                        .getCursorLocation();
                Point viewPoint = resourceTreeViewer.getControl().toControl(
                        screenPoint);
                
                if (getCurProject().isEmpty()) {
                    setVisibleFalse();
                } else {
        
                    if (null == resourceTreeViewer.getTree().getItem(
                            viewPoint)) {
                        setVisibleFalse();

                        contextMenuMgr.add(saveAction);
                        saveAction.setText(SAVE_ALL);
                        contextMenuMgr.add(reLoadAction);
                        reLoadAction.setText(RELOAD_ALL);
                        generateVGAAction.setText(GENERATE_WQVGA);
                        contextMenuMgr.add(importStringAction);
                    } else {
                        TreeObject item = (TreeObject) resourceTreeViewer
                                .getTree().getItem(viewPoint).getData();
                        
                        TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                        if(selection.length <= 0) {
                            setVisibleFalse();
                            return;
                        }
                        Object element = selection[0].getData();
                        if(!(element instanceof TreeObject)) {
                            setVisibleFalse();
                            return;
                        }
                        TreeObject selectedItem = (TreeObject) element;
                        if(selectedItem != null && !selectedItem.equals(item)) {
                            setVisibleFalse();
                            return;
                        }
                        
                        setVisibleMenu(item);
                    }
                    
                    if (string == null && manager == null) {
                        setVisibleFalse();
                    }
                }
            }
            
            private void setVisibleMenu(TreeObject item) {
                if(item == null) {
                    setVisibleFalse();
                    return;
                }
                
                contextMenuMgr.removeAll();

                contextMenuMgr.add(openWizardAction);
                contextMenuMgr.add(saveAction);
                
                String screen = "";
                String id;
                switch (item.getDepth()) {
                case DEPTH_ROOT:
                    screen = item.getId();
                    id = item.getId();

                    contextMenuMgr.add(reLoadAction);
                    if(item.getKind() == KIND_STRING) {
                        contextMenuMgr.add(importStringAction);
                        reLoadAction.setText(RELOAD_ALL);
                        saveAction.setText(SAVE_ALL);
                    } else {
                        reLoadAction.setText(RELOAD + screen + " " + RESOURCES);
                        saveAction.setText(SAVE + screen + " " + RESOURCES);
                    }
                    
                    setGenerateMenu(DEPTH_ROOT, item.getKind(), screen, id);
                    break;
                case DEPTH_GROUP:
                    if(item.getKind() != KIND_PANEL) {
                        contextMenuMgr.add(insertAction);
                    }
                    contextMenuMgr.add(reLoadAction);
                    reLoadAction.setText(RELOAD + item.getParent().getId() + " " + RESOURCES);
                    saveAction.setText(SAVE + item.getParent().getId() + " " + RESOURCES);

                    if(item.info instanceof String)
                        screen = (String)item.info;
                    id = item.getId();
                    setGenerateMenu(DEPTH_GROUP, item.getKind(), screen, id);
                    break;
                case DEPTH_TABLE:
                    if(item.getKind() != KIND_PANEL) {
                        contextMenuMgr.add(deleteAction);
                    }
                    contextMenuMgr.add(reLoadAction);
                    reLoadAction.setText(RELOAD + item.getParent().getParent().getId() + " " + RESOURCES);
                    saveAction.setText(SAVE + item.getParent().getParent().getId() + " " + RESOURCES);

                    if(item.getParent().info instanceof String)
                        screen = (String)item.getParent().info;
                    id = item.getId();
                    setGenerateMenu(DEPTH_TABLE, item.getKind(), screen, id);
                }
            }
            private void setVisibleFalse() {
                contextMenuMgr.removeAll();
                
                contextMenuMgr.add(openWizardAction);
            }
            
            @Override
            public void mouseUp(MouseEvent e) {
            }
        });
	}

	/**
     * @param depthRoot
     * @param kind
     * @param screen
     * @param string2
     */
    protected void setGenerateMenu(int depth, int kind, String screen, String id) {
        contextMenuMgr.add(generateVGAAction);
        switch(depth) {
        case DEPTH_ROOT:
            if(Activator.getStringScreen(curProject).size() <= 1) {
                contextMenuMgr.remove(generateVGAAction.getId());
                generateVGAAction.setText(GENERATE_WQVGA);
            } else if(screen.toUpperCase(Locale.getDefault()).equals(WQVGA)) {
                generateVGAAction.setText(GENERATE_WVGA + RESOURCES);
            } else if(screen.toUpperCase(Locale.getDefault()).equals(WVGA)) {
                generateVGAAction.setText(GENERATE_WQVGA + RESOURCES);
            } else {
                contextMenuMgr.remove(generateVGAAction.getId());
                generateVGAAction.setText(GENERATE_WQVGA);
            }
            break;
        case DEPTH_GROUP:
        case DEPTH_TABLE:
            if(Activator.getStringScreen(curProject).size() <= 1) {
                contextMenuMgr.remove(generateVGAAction.getId());
                generateVGAAction.setText(GENERATE_WQVGA);
            } else if(screen.toUpperCase(Locale.getDefault()).equals(WQVGA)) {
                generateVGAAction.setText(GENERATE_WVGA + id);
            } else if(screen.toUpperCase(Locale.getDefault()).equals(WVGA)) {
                generateVGAAction.setText(GENERATE_WQVGA + id);
            } else {
                contextMenuMgr.remove(generateVGAAction.getId());
                generateVGAAction.setText(GENERATE_WQVGA);
            }
            break;
        default:
            contextMenuMgr.remove(generateVGAAction.getId());
            generateVGAAction.setText(GENERATE_WQVGA);
        }
        generateVGAAction.setDescription(screen);
    }

    protected void defineResourceTreeViewerEventHandler() {
		resourceTreeViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				TreeSelection selected = (TreeSelection) event.getSelection();
				TreeObject item = (TreeObject) selected.getFirstElement();
				String node = item.getId();
				try {
					IWorkbenchPage page = getSite().getPage();
					if (item.getDepth() == DEPTH_GROUP)
						return;
					IEditorReference[] editors;
					switch (item.getKind()) {
					case KIND_STRING:
						editors = page.getEditorReferences();
						for (int n = 0; n < editors.length; n++) {
							if (editors[n] != null
									&& editors[n].getTitleToolTip()
											.equals(node)) {
								page.bringToTop(editors[n].getPart(false));
								page.activate(editors[n].getPart(false));
								return;
							}
						}

						if(string.m_Strings.size() <= 0) {
							string.InsertString(OspUIString.DEFAULT_LANG1);
						}
						
						page.openEditor(new OspStringEditorInput(string),
								OspStringEditor.ID, true);
						break;
					case KIND_FORM:
						ResourceTreeContentProvider contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
								.getContentProvider();
						TreeObject obj = (TreeObject) contentProvider
								.getParent(item.getParent());
						if(obj == null)
							return;
						String screen = obj.getName();

						editors = page.getEditorReferences();
						for (int n = 0; n < editors.length; n++) {
							if (editors[n] != null
									&& editors[n].getId().equals(
											OspFormEditor.ID)) {
								final OspFormEditor editor = (OspFormEditor) editors[n]
										.getEditor(false);
								if (editor.m_id.equals(node)
										&& editor.screen.equals(screen)) {
									page.bringToTop(editors[n].getPart(false));
									page.activate(editors[n].getPart(false));
									return;
								}
							}
						}

						Hashtable<String, OspForm> scenes = manager.m_Form
								.get(screen);
						if (scenes == null || scenes.get(node) == null) {
							MessageDialog.openWarning(null, "Warning", node + " does not exist.");
							return;
						}
						
						page
								.openEditor(new OspFormEditorInput(manager,
										screen, node, string),
										OspFormEditor.ID, true);
						break;
					case KIND_POPUP:
						contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
								.getContentProvider();
						obj = (TreeObject) contentProvider.getParent(item
								.getParent());
						if(obj == null)
							return;
						screen = obj.getName();

						editors = page.getEditorReferences();
						for (int n = 0; n < editors.length; n++) {
							if (editors[n] != null
									&& editors[n].getId().equals(
											OspPopupEditor.ID)) {
								final OspPopupEditor editor = (OspPopupEditor) editors[n]
										.getEditor(false);
								if (editor.m_id.equals(node)
										&& editor.screen.equals(screen)) {
									page.bringToTop(editors[n].getPart(false));
									page.activate(editors[n].getPart(false));
									return;
								}
							}
						}

						page.openEditor(new OspPopupEditorInput(manager, screen,
								node, string), OspPopupEditor.ID, true);
						break;
					case KIND_PANEL:
                        contentProvider = (ResourceTreeContentProvider) resourceTreeViewer
                            .getContentProvider();
                        obj = (TreeObject) contentProvider.getParent(item
                                .getParent());
                        if(obj == null)
                            return;
                        screen = obj.getName();

						editors = page.getEditorReferences();
						for (int n = 0; n < editors.length; n++) {
                            if (editors[n] != null
                                && editors[n].getId().equals(
                                        OspPanelEditor.ID)) {
                                final OspPanelEditor editor = (OspPanelEditor) editors[n]
                                        .getEditor(false);
                                if (editor.m_id.equals(node)
                                        && editor.m_screen.equals(screen)) {
        								page.bringToTop(editors[n].getPart(false));
        								page.activate(editors[n].getPart(false));
        								return;
                                }
							}
						}

						page.openEditor(new OspPanelEditorInput(manager, screen, node,
								string), OspPanelEditor.ID, true);
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (curProject != null && !curProject.isEmpty()) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(curProject);
			if (project != null) {
				if (isDirty()) {
					if (saveWithDialog(project.getName(), false) == CANCEL)
						return;
				}
				if (manager != null) {
					refreshProject();
				}
			}
		}

		closeAllEditor(true);
		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(projectSelectionListener);
		ResourceChangeManager.shutdown();
		WorkbenchWindow window = (WorkbenchWindow) getSite()
				.getWorkbenchWindow();
		MenuManager menuManager = window.getMenuBarManager();
		MenuManager fileMenu = (MenuManager) menuManager
				.find(IWorkbenchActionConstants.M_FILE);
		if (fileMenu != null) {
			IContributionItem saveAllMenu;
			if ((saveAllMenu = fileMenu.find(SAVE_ALL)) != null)
				fileMenu.remove(saveAllMenu).dispose();
		}
		MenuManager projectMenu = (MenuManager) menuManager
				.find(IWorkbenchActionConstants.M_PROJECT);
		if (projectMenu != null) {
			IContributionItem testMenu;
			if ((testMenu = projectMenu.find(TEST_ACTION)) != null)
				projectMenu.remove(testMenu).dispose();
		}

		langAction.setEnabled(false);
		imgSettingAction.setEnabled(false);
		previewAction.setEnabled(false);
		CoolBarManager coolBarManager = window.getCoolBarManager();
		ToolBarContributionItem toolbar = (ToolBarContributionItem) coolBarManager
				.find(ID_TOOLBAR);
		if(toolbar != null)
			toolbar.dispose();
		if(coolBarManager != null) {
			IContributionItem localToolbar = coolBarManager.remove(ID_TOOLBAR);
			if(localToolbar!= null)
				localToolbar.dispose();
			coolBarManager.refresh();
		}

        ResourceExplorer.thisInstance = null;
		super.dispose();
	}

	protected void handleKeyReleased(KeyEvent event) {
		if (event.keyCode == SWT.F5 && event.stateMask == 0) {
			IProject[] project = ResourcesPlugin.getWorkspace().getRoot()
					.getProjects();
			if (project.length > 0 && !curProject.isEmpty()) {
                TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
                if(selection.length <= 0)
                    return;

                Object element = selection[0].getData();
                if(!(element instanceof TreeObject))
                    return;

                final TreeObject selectedItem = (TreeObject) element;
                if(selectedItem != null) {
                    final boolean isExpand = resourceTreeViewer.getExpandedState(selectedItem);
                    Display display = resourceTreeViewer.getTree().getDisplay();
                    display.asyncExec(new Runnable(){
                            @Override
                            public void run() {
                                if(isExpand)
                                    resourceTreeViewer.collapseToLevel(selectedItem, 1);
                                else
                                    resourceTreeViewer.expandToLevel(selectedItem, 1);
                            }
                        });
                }
			}
		}else if (event.keyCode == SWT.DEL && event.stateMask == 0){
            TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
            if(selection.length <= 0) {
                return;
            }
            Object element = selection[0].getData();
            if(!(element instanceof TreeObject)) {
                return;
            }
            
            if(((TreeObject) element).getKind() == KIND_PANEL)
                return;
            
		    deleteAction.run();
        }else if (event.keyCode == SWT.INSERT && event.stateMask == 0){
            insertAction.run();
        }else if ((event.keyCode == 'R' || event.keyCode == 'r') && event.stateMask == SWT.CTRL){
            TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
            if(selection.length <= 0) {
                return;
            }
            Object element = selection[0].getData();
            if(!(element instanceof TreeObject)) {
                return;
            }
            
            reLoadAction.setText(RELOAD);
            reLoadAction.run();
        }else if ((event.keyCode == 'G' || event.keyCode == 'g') && event.stateMask == SWT.CTRL){
            TreeItem[] selection = resourceTreeViewer.getTree().getSelection();
            if(selection.length <= 0) {
                return;
            }
            Object element = selection[0].getData();
            if(!(element instanceof TreeObject)) {
                return;
            }
            TreeObject selectedItem = (TreeObject) element;
            
            String screen;
            switch (selectedItem.getDepth()) {
            case DEPTH_ROOT:
                screen = selectedItem.getId();
                break;
            case DEPTH_GROUP:
                if(selectedItem.info instanceof String)
                    screen = (String)selectedItem.info;
                else
                    return;
                break;
            case DEPTH_TABLE:
                if(selectedItem.getParent().info instanceof String)
                    screen = (String)selectedItem.getParent().info;
                else
                    return;
                break;
            default :
                return;
            }
            setGenerateMenu(selectedItem.getDepth(), selectedItem.getKind(), screen, selectedItem.getId());
            generateVGAAction.run();
		} else if (event.keyCode == SWT.F12
				&& event.stateMask == SWT.ALT + SWT.CTRL) {
			Tree tree = (Tree) resourceTreeViewer.getControl();
			TreeItem[] item = tree.getSelection();
			if (item.length == 0) {
				return;
			}
			TreeObject node = (TreeObject) item[0].getData();

			if (node.getKind() == ResourceExplorer.KIND_RESOURCE)
				openImgSettingDlg(node.getName());
		} else if (event.keyCode == SWT.PRINT_SCREEN
				&& event.stateMask == SWT.ALT) {
			captureEditorPart();
		} else if (event.stateMask == SWT.CTRL
				&& (event.keyCode == 's' || event.keyCode == 'S')) {
			if (isDirty()) {
				saveForm(true);
				savePopup();
				saveString();
                refreshProject();
                refreshTree();
			}
		}
	}

	private void hookKeyboard() {
		resourceTreeViewer.getControl().addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent event) {
				handleKeyReleased(event);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(IPropertySheetPage.class)) {
			return getPropertySheetPage();
		}
		return super.getAdapter(key);
	}

	private PropertySheetPage getPropertySheetPage() {
		if (propertySheet == null) {
			propertySheet = new ResourcePropertyPage();
			NonePropertySorter sorter = new NonePropertySorter();
			propertySheet.setSorter(sorter);
			propertySheet.setPropertySourceProvider(propertySheet);
		}
		return propertySheet;
	}

	public void setClose(boolean b) {
		preClose = b;
	}

	public OspUIString getString() {
		return string;
	}

	public void selectLanguage() {
		if (this.langAction != null)
			langAction.run();
	}

	public void selectLanguage(String name) {
		if (this.langAction != null) {
			langAction.setDescription(name);
			langAction.run();
			langAction.setDescription(ACTION_SELECTLANGUAGE);
		}
	}

	public void renameEditor(String oldId, final String id) {
		final IWorkbenchPage page = getSite().getPage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			final IEditorPart editor = editors[n].getEditor(false);
			if (editor instanceof OspFormEditor) {
				if (((OspFormEditor) editor).m_id.equals(oldId)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							((OspFormEditor) editor).setId(id);
						}
					});
				}
			} else if (editor instanceof OspPanelEditor) {
				if (((OspPanelEditor) editor).m_id.equals(oldId)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							((OspPanelEditor) editor).setId(id);
						}
					});
				}
			} else if (editor instanceof OspPopupEditor) {
				if (((OspPopupEditor) editor).m_id.equals(oldId)) {
					Display display = editor.getSite().getShell().getDisplay();
					display.syncExec(new Runnable() {
						@Override
						public void run() {
							((OspPopupEditor) editor).setId(id);
						}
					});
				}
			}
		}
	}

	public OspResourceManager getManager() {
		return manager;
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart2#promptToSaveOnClose()
     */
    @Override
    public int promptToSaveOnClose() {
        int ret = 0;
        if (curProject != null && !curProject.isEmpty()) {
            IProject project = ResourcesPlugin.getWorkspace().getRoot()
                    .getProject(curProject);
            if (project != null) {
                if(isOrphanPanelIgnore() == false)
                    ret = CANCEL;
            }
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#doSaveAs()
     */
    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
     */
    @Override
    public boolean isSaveAsAllowed() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISaveablePart#isSaveOnCloseNeeded()
     */
    @Override
    public boolean isSaveOnCloseNeeded() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * 
     */
    public void refreshFrameEditor() {
        IWorkbenchPage page = getSite().getPage();
        if(page == null)
            return;
        
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            final IEditorReference editor = editors[n];
            if (editor != null) {
                if (editor.getId().equals(OspFormEditor.ID)) {
                    Display display = ((OspFormEditor) editor.getEditor(false)).getSite().getShell().getDisplay();
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            ((OspFormEditor) editor.getEditor(false)).getModel()
                            .refreshChildren();
                        }
                    });
                } else if(editor.getId().equals(OspPanelEditor.ID)) {
                    Display display = ((OspPanelEditor) editor.getEditor(false)).getSite().getShell().getDisplay();
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            ((OspPanelEditor) editor.getEditor(false)).getModel()
                            .refreshChildren();
                        }
                    });
                } else if(editor.getId().equals(OspPopupEditor.ID)) {
                    Display display = ((OspPopupEditor) editor.getEditor(false)).getSite().getShell().getDisplay();
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            ((OspPopupEditor) editor.getEditor(false)).getModel()
                            .refreshChildren();
                        }
                    });
                }
            }
        }
    }

}
