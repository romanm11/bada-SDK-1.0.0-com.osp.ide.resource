package com.osp.ide.resource.resourceexplorer;

import java.util.Hashtable;

import org.eclipse.core.resources.IFile;
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

import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editform.OspFormEditorInput;

public class FormOpenEditor extends EditorPart {
	public static final String NAME = "SceneOpenEditor";
	public static String ID = "com.osp.ide.resource.resourceexplorer.FormOpenEditor";
	String project = "";
	String id = "";
	private IPartListener listener;
	private String folder;

	public FormOpenEditor() {
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
		project = file.getProject().getName();
		folder = file.getParent().getName();
		id = file.getName().substring(0, file.getName().indexOf('.'));
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
				if (part instanceof FormOpenEditor) {
					IWorkbenchPage page = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage();
					page.removePartListener(listener);
					page.closeEditor((IEditorPart) part, false);
					ResourceExplorer view = (ResourceExplorer) page.findView(ResourceExplorer.ID);
					if (view == null)
						return;
					view.setFocus();
					
					view.setCurProject(project, false);
					
					IEditorReference[] editors = page.getEditorReferences();
					for (int n = 0; n < editors.length; n++) {
                        if (editors[n] != null
                                && editors[n].getId().equals(OspFormEditor.ID)) {
                            final OspFormEditor editor = (OspFormEditor) editors[n]
                                    .getEditor(false);
                            if (editor.m_id.equals(id)
                                    && editor.screen.equals(folder)) {
                                page.bringToTop(editors[n].getPart(false));
                                page.activate(editors[n].getPart(false));
                                return;
                            }
                        }
					}

					try {
						Hashtable<String, OspForm> scenes = view.manager.m_Form.get(folder);
						if(scenes == null || scenes.get(id) == null) {
							MessageDialog.openWarning(null, "Warning", "Save your resource files and then try to reload.");
							return;
						}
						page.openEditor(new OspFormEditorInput(view.manager, folder, id, view.string),
							OspFormEditor.ID, true);
						
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
