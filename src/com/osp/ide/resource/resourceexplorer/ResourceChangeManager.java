/**
 * 
 */
package com.osp.ide.resource.resourceexplorer;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspFormMarkup;
import com.osp.ide.resource.documents.OspPopupMarkup;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.string.OspStringMarkup;

/**
 * @author 
 * 
 */
@SuppressWarnings("restriction")
public class ResourceChangeManager implements IResourceChangeListener {
    public class ProjectOpenTask extends TimerTask {
        private Timer timer;
        private Project project;
        private Display display;

        public ProjectOpenTask(Timer timer, Display display, Project project) {
            this.timer = timer;
            this.display = display;
            this.project = project;
        }

        @Override
        public void run() {
            display.asyncExec(new Runnable() {
                @Override
                public void run() {
                    try {
                        project.open(null);
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException e) {
                        e.printStackTrace();
                    }
                }
            });
            timer.cancel();
        }
    }

	private static ResourceChangeManager manager = null;

	/**
	 * 
	 */
	private ResourceChangeManager() {
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(
				this,
				IResourceChangeEvent.POST_CHANGE
						| IResourceChangeEvent.PRE_CLOSE
						| IResourceChangeEvent.PRE_DELETE
						| IResourceChangeEvent.PRE_BUILD
						| IResourceChangeEvent.POST_BUILD
						| IResourceChangeEvent.PRE_REFRESH);
	}

	public static ResourceChangeManager getInstance() {
		if (manager == null)
			manager = new ResourceChangeManager();
		return manager;
	}

	public static void shutdown() {
		if (manager != null) {
			ResourcesPlugin.getWorkspace()
					.removeResourceChangeListener(manager);
			manager = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org
	 * .eclipse.core.resources.IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		dbg_printEventInfo(event);

		if(event.getSource() instanceof IProject &&
				event.getType() == IResourceChangeEvent.PRE_BUILD)
			handlePreBuild(event);

		if (!(event.getSource() instanceof IWorkspace))
			return;
		
		switch (event.getType()) {
		case IResourceChangeEvent.POST_CHANGE:
			postChange(event);
			break;
		case IResourceChangeEvent.PRE_CLOSE:
			handlePreClose(event);
			break;
		case IResourceChangeEvent.PRE_DELETE:
			handlePreDelete(event);
			break;
		case IResourceChangeEvent.PRE_BUILD:
			handlePreBuild(event);
			break;
		case IResourceChangeEvent.POST_BUILD:
			handlePostBuild(event);
			break;
		case IResourceChangeEvent.PRE_REFRESH:
			handlePreRefresh(event);
			break;
		default:
			dbg_println("IResourceChangeEvent_UNKNOWN");
			break;
		}
	}

	public void postChange(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		if (delta == null)
			return;
		try {
			delta.accept(new ResourceDeltaVisitor());
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

    public void isOrphanPanelIgnore(final Project project){
        final ResourceExplorer view = ResourceExplorer.getResourceView();
            final Display display = view.getSite().getShell().getDisplay();
            display.syncExec(new Runnable(){

                @Override
                public void run() {
                    if (view != null && view.isOrphanPanelExist()) {
                        if (!MessageDialog.openQuestion(view.getSite().getShell(), "Resource : " + view.getCurProject(),
                                "The ScrollPanel needs to be assigned a valid ScrollPanel ID or else it will be discarded. Click Yes to discard the ScrollPanel.")) {
                            Timer close = new Timer();
                            Date curDate = new Date();
                            curDate.setTime(curDate.getTime() + 100);
                            close.schedule(new ProjectOpenTask(close, display, project), curDate);
                        } else {
                            view.saveWithDialog(project.getName(), false);
                            view.setCurProject("", false);
                        }
                    } else {
                        view.saveWithDialog(project.getName(), false);
                        view.setCurProject("", false);
                    }
                }});
    }

	public void handlePreClose(IResourceChangeEvent event) {
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (event.getResource() instanceof ResourceExplorer) {
			view.setClose(true);
		}
		
        if (event.getResource() instanceof IProject) {
            Project project = (Project)event.getResource();
            if(project.getName().equals(view.getCurProject()))
                isOrphanPanelIgnore(project);
        }
	}

	public void handlePreDelete(IResourceChangeEvent event) {
		if (event.getResource() instanceof IProject) {
			ResourceExplorer view = ResourceExplorer.getResourceView();
			view.closeAllEditor(false);
			String newProject = "";
			if (view.curProject.equals(event.getResource().getName())) {
				IProject[] project = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
				if (project.length != 0) {
					for (int i = 0; i < project.length; i++) {
						if (!project[i].getName().equals(
								event.getResource().getName())) {
							newProject = project[i].getName();
							break;
						}
					}
				}

				ResourceExplorer.getResourceView().setCurProject(newProject,
						true);
			}
		} else if (event.getResource() instanceof IFile) {
			IFile file = (IFile) event.getResource();
			System.out.println("ResourceChangeManager Delete : " + file);
		}
	}

	public void handlePreBuild(IResourceChangeEvent event) {
		ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null && event.getSource() instanceof IProject) {
            Project project = (Project)event.getSource();
            if(project.getName().equals(view.getCurProject()) && view.isDirty())
            	view.getSaveAllAction().run();
        }		
		switch (event.getBuildKind()) {
		case IncrementalProjectBuilder.FULL_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.AUTO_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.INCREMENTAL_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.CLEAN_BUILD:
			// TODO add your code
			break;
		default:
			break;
		}
	}

	public void handlePostBuild(IResourceChangeEvent event) {
		switch (event.getBuildKind()) {
		case IncrementalProjectBuilder.FULL_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.AUTO_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.INCREMENTAL_BUILD:
			// TODO add your code
			break;
		case IncrementalProjectBuilder.CLEAN_BUILD:
			// TODO add your code
			break;
		default:
			break;
		}

	}

	public void handlePreRefresh(IResourceChangeEvent event) {
		// TODO add your code
	}

	public boolean dbg_isEnable() {
		return false;
		// return true;
	}

	public void dbg_printEventInfo(IResourceChangeEvent event) {
		if (!dbg_isEnable())
			return;

		// event
		Object source = event.getSource();
		Object resource = event.getResource();
		IResourceDelta delta = event.getDelta();
		int type = event.getType();
		int buildKind = event.getBuildKind();

		if (source == null)
			dbg_print("null");
		else
			dbg_print(source.toString());
		dbg_print(" : ");

		if (resource == null)
			dbg_print("null");
		else
			dbg_print(resource.toString());
		dbg_print(" : ");

		if (delta == null)
			dbg_print("null");
		else
			dbg_print(delta.toString());
		dbg_print(" : ");

		switch (type) {
		case IResourceChangeEvent.POST_CHANGE:
			dbg_print("POST_CHANGE");
			break;
		case IResourceChangeEvent.PRE_CLOSE:
			dbg_print("PRE_CLOSE");
			break;
		case IResourceChangeEvent.PRE_DELETE:
			dbg_print("PRE_DELETE");
			break;
		case IResourceChangeEvent.PRE_BUILD:
			dbg_print("PRE_BUILD");
			break;
		case IResourceChangeEvent.POST_BUILD:
			dbg_print("POST_BUILD");
			break;
		case IResourceChangeEvent.PRE_REFRESH:
			dbg_print("PRE_REFRESH");
			break;
		default:
			dbg_print("UNKNOWN" + "(" + type + ")");
			break;
		}
		dbg_print(" : ");

		switch (buildKind) {
		case IncrementalProjectBuilder.FULL_BUILD:
			dbg_print("FULL_BUILD");
			break;
		case IncrementalProjectBuilder.AUTO_BUILD:
			dbg_print("AUTO_BUILD");
			break;
		case IncrementalProjectBuilder.INCREMENTAL_BUILD:
			dbg_print("INCREMENTAL_BUILD");
			break;
		case IncrementalProjectBuilder.CLEAN_BUILD:
			dbg_print("CLEAN_BUILD");
			break;
		default:
			dbg_print("UNKNOWN" + "(" + buildKind + ")");
			break;
		}
		dbg_println();
	}

	void dbg_print(String s) {
		if (dbg_isEnable())
			System.out.print(s);
	}

	void dbg_println() {
		if (dbg_isEnable())
			System.out.println();
	}

	void dbg_println(String s) {
		if (dbg_isEnable())
			System.out.println(s);
	}
}

class ResourceDeltaVisitor implements IResourceDeltaVisitor {
	public boolean visit(IResourceDelta delta) throws CoreException {
		StringBuffer buf = new StringBuffer(256);
		buf.append("    ");
		
		if(delta == null)
			return false;
		
		switch (delta.getKind()) {
		case IResourceDelta.NO_CHANGE:
			buf.append("NO_CHANGE");
			break;
		case IResourceDelta.ADDED:
			buf.append("ADDED");
			if (!(delta.getResource() instanceof IFile))
				break;
			addWork(delta);
			break;
		case IResourceDelta.REMOVED:
			buf.append("REMOVED");
			if (!(delta.getResource() instanceof IFile))
				break;
			deleteWork(delta);
			break;
		case IResourceDelta.CHANGED:
			buf.append("CHANGED");
			break;
		case IResourceDelta.ADDED_PHANTOM:
			buf.append("ADDED_PHANTOM");
			break;
		case IResourceDelta.REMOVED_PHANTOM:
			buf.append("REMOVED_PHANTOM");
			break;
		default:
			buf.append("[");
			buf.append(delta.getKind());
			buf.append("]");
			break;
		}
		buf.append(" ");
		buf.append(delta.getResource());
		dbg_println(buf.toString());
		return true;
	}

	public void deleteWork(IResourceDelta delta) {
		ResourceExplorer view;
		IFile file;

		file = (IFile) delta.getResource();
		if(file == null || file.getProject() == null || file.getProject().getLocation() == null)
			return;
		
		view = ResourceExplorer.getResourceView();
		if(view == null)
			return;
		String resPath = file.getProject().getLocation().toOSString() + view.getResourceDir();
		String fileResPath = file.getParent().getParent().getLocation().toOSString() + java.io.File.separatorChar;
		if(!fileResPath.equals(resPath) || !file.getProject().isOpen())
			return;
		
		if (file != null && file.getFileExtension() != null
				&& file.getFileExtension().equals("rsp")) {
			if (view != null
					&& view.getCurProject().equals(file.getProject().getName())) {
				view.closeAllEditor(false);
				view.reLoadString("");
			}
		} else if (file != null && file.getFileExtension() != null
				&& file.getFileExtension().equals("xml")
				&& !file.getName().equals(Activator.MANIFEST_NAME)) {

			IPath topath = delta.getMovedToPath();
			if (topath == null && file.getProject().getName().equals(view.getCurProject())) {
				String id = file.getName().substring(0, file.getName().indexOf('.'));
				String screen = file.getParent().getName();
				if(!isScreen(file.getProject().getName(), screen))
					return;
				
				view.closeEditor(screen, file.getName().substring(0, file.getName().indexOf('.')));
				view.manager.DeletedScene(screen, id);
				view.manager.DeletedPopup(screen, id);

				view.refreshTree();
				view.refreshProject();
			} else if(file != null && file.getParent() != null) {
			    String folder = file.getParent().getName();
			    if(folder != null && folder.equals(FrameNode.BITMAP_FOLDER_NAME)){
			        view.refreshFrameEditor();
			    }
			}
		}
	}

	public void addWork(IResourceDelta delta) {
		IFile file = (IFile) delta.getResource();
		ResourceExplorer view;
		view = ResourceExplorer.getResourceView();
		if(view == null)
			return;
		
		if(file == null)
			return;

		String resPath = file.getProject().getLocation().toOSString() + view.getResourceDir();
		String fileResPath = file.getParent().getParent().getLocation().toOSString() + java.io.File.separatorChar;
		if(!fileResPath.equals(resPath))
			return;
		
		if (file.getFileExtension() != null
				&& file.getFileExtension().equals("rsp")) {
			if (view.getCurProject().isEmpty()
					|| view.getCurProject().equals(file.getProject().getName())) {
				view.reLoadString("");
			}
		} else if (file != null && file.getFileExtension() != null
				&& file.getFileExtension().equals("xml")
				&& !file.getName().equals(Activator.MANIFEST_NAME)) {
			IPath path = ((IResourceDelta) delta).getMovedFromPath();
			if (path != null && file.getProject().getName().equals(view.getCurProject())) {
				String newId, oldId;
				newId = file.getName().substring(0, file.getName().indexOf('.'));
				oldId = path.lastSegment().substring(0, path.lastSegment().indexOf('.'));
				if (isTWFormXML(file) || isTWPopupXML(file)) {
					String screen = file.getParent().getName();
					if(!isScreen(file.getProject().getName(), screen))
						return;
					if (isTWFormXML(file)) {
						view.manager.RenamedScene(screen, oldId, newId);
					} else {
						view.manager.RenamedPopup(screen, oldId, newId);
					}

					view.renameEditor(oldId, newId);
					view.refreshTree();
					view.refreshProject();
				}
			} else if(file.getProject().getName().equals(view.getCurProject())){
				if (isTWFormXML(file) || isTWPopupXML(file)) {
					String screen = file.getParent().getName();
					if(!isScreen(file.getProject().getName(), screen))
						return;
					IPath dirPath = Path.fromPortableString(view.manager
							.getProjectDirectory()
							+ screen);
					File dir = dirPath.toFile();
					if (!dir.exists())
						return;
					if (isTWFormXML(file) || isTWPopupXML(file)) {
						if (isTWFormXML(file)) {
							view.manager.AddScene(screen, dir.getAbsolutePath() + java.io.File.separatorChar + file.getName());
						} else {
							view.manager.AddPopup(screen, dir.getAbsolutePath() + java.io.File.separatorChar + file.getName());
						}

						view.refreshTree();
						view.refreshProject();
					}
				}
			}
		}
	}

	private boolean isScreen(String project, String screen) {
		if(Activator.getStringScreen(project) == null ||
				Activator.getStringScreen(project).indexOf(screen) < 0)
			return false;
		else
			return true;
	}

	public boolean isTWFormXML(IFile file) {
		if (!OspFormMarkup.isFormXML(file))
			return false;

		return true;
	}

	public boolean isTWPopupXML(IFile file) {
		if (!OspPopupMarkup.isPopupXML(file))
			return false;

		return true;
	}

	public boolean isStringXML(IFile file) {
		return OspStringMarkup.isStringXML(file.getLocation().toFile());
	}

	public boolean dbg_isEnable() {
		return false;
		// return true;
	}

	public void dbg_printResourceDelta(IResourceDelta delta) {
		if (delta == null)
			return;
		switch (delta.getKind()) {
		case IResourceDelta.NO_CHANGE:
			break;
		case IResourceDelta.ADDED:
			break;
		case IResourceDelta.REMOVED:
			break;
		case IResourceDelta.CHANGED:
			break;
		case IResourceDelta.ADDED_PHANTOM:
			break;
		case IResourceDelta.REMOVED_PHANTOM:
			break;
		case IResourceDelta.ALL_WITH_PHANTOMS:
			break;
		default:
			break;
		}
	}

	void dbg_print(String s) {
		if (dbg_isEnable())
			System.out.print(s);
	}

	void dbg_println() {
		if (dbg_isEnable())
			System.out.println();
	}

	void dbg_println(String s) {
		if (dbg_isEnable())
			System.out.println(s);
	}
}
