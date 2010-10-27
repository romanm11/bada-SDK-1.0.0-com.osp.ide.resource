package com.osp.ide.resource.resourceexplorer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.osp.ide.resource.string.OspStringEditor;
import com.osp.ide.resource.string.OspStringEditorInput;

public class StringOpenEditor extends EditorPart {
	public static final String NAME = "SceneOpenEditor";
	public static String ID = "com.osp.ide.resource.resourceexplorer.StringOpenEditor";
	IProject project;
	private IPartListener listener;

	public StringOpenEditor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setInput(IEditorInput input) {
		super.setInput(input);
		IFile file = ((FileEditorInput) input).getFile();
		project = file.getProject();
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		ResourceExplorer view = (ResourceExplorer) page.findView(ResourceExplorer.ID);
		if(view == null) {
			MessageDialog.openWarning(null, "Warning", "Open Resource Explorer View first.");
		}

		listener = new IPartListener() {
			@Override
			public void partActivated(IWorkbenchPart part) {
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
			}

			@Override
			public void partOpened(IWorkbenchPart part) {
				if (part instanceof StringOpenEditor) {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					page.removePartListener(listener);
					page.closeEditor((IEditorPart) part, false);
					ResourceExplorer view = (ResourceExplorer) page.findView(ResourceExplorer.ID);
					if (view == null)
						return;
					view.setFocus();

					if (!view.getCurProject().equals(project.getName()))
						view.setCurProject(project.getName(), false);

					IEditorReference[] editors = page.getEditorReferences();
					for (int n = 0; n < editors.length; n++) {
						if (editors[n] != null
								&& editors[n]
										.getTitleToolTip()
										.equals(
										    ResourceExplorer.cszKindName[ResourceExplorer.KIND_STRING])) {
							page.bringToTop(editors[n].getPart(false));
							page.activate(editors[n].getPart(false));
							return;
						}
					}

					try {
						page.openEditor(new OspStringEditorInput(view.string),
								OspStringEditor.ID, true);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			}
		};
		page.addPartListener(listener);
	}

	@Override
	public void setFocus() {
	}
}
