package com.osp.ide.resource.editform;

import java.util.ArrayList;

import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.part.OspAbstractEditPart;
import com.osp.ide.resource.part.DrawCanvasPart;
import com.osp.ide.resource.part.FormFramePart;

public class OspAddClassWidzard extends Wizard implements
		INewWizard {

	OspAddClassMainPage newProject;
	private String selectPName;

	private IStructuredSelection selection;
	private Form form;
	private Tree formTree;
	private Tree controlBaseTree;
	private Tree controlTree;

	private ArrayList<String> selectEventList = new ArrayList<String>();
	private Text className;

	public OspAddClassWidzard() {
		super();
		setDialogSettings(CUIPlugin.getDefault().getDialogSettings());
		setNeedsProgressMonitor(true);
		setForcePreviousAndNextButtons(false);
		setWindowTitle("bada New ClassWizard");
	}

	public OspAddClassWidzard(String curProject,
			ISelection selection) {
		this();
		Object element = ((StructuredSelection) selection).getFirstElement();
		if (element instanceof OspAbstractEditPart) {
			if (element instanceof FormFramePart)
				form = (Form) ((FormFramePart) element).getModel();
			else if (element instanceof DrawCanvasPart)
				form = (Form) ((DrawCanvasPart) element)
						.getModelChildren().get(0);
			else if (element instanceof OspAbstractEditPart)
				form = (Form) ((OspAbstractEditPart) element)
						.getParent().getModel();
		}
	}

	public Form getForm() {
		return form;
	}

	@Override
	public boolean performFinish() {
		TreeItem[] formItems = formTree.getItems();
		for (int i = 0; i < formItems.length; i++) {
			String item = formItems[i].getText();
			if (formItems[i].getChecked() && !selectEventList.contains(item))
				selectEventList.add(item);
		}
		TreeItem[] Baseitems = controlBaseTree.getItems();
		for (int i = 0; i < Baseitems.length; i++) {
			String item = Baseitems[i].getText();
			if (Baseitems[i].getChecked() && !selectEventList.contains(item))
				selectEventList.add(item);
		}
		TreeItem[] Contorlitems = controlTree.getItems();
		for (int i = 0; i < Contorlitems.length; i++) {
			TreeItem[] items;
			String item = null;

			if (Contorlitems[i].getChecked() == true) {
				items = Contorlitems[i].getItems();
				for (int j = 0; j < items.length; j++) {
					item = items[j].getText();
					if (items[j].getChecked()
							&& !selectEventList.contains(item)) {
						selectEventList.add(item);
					}
				}
			}
		}

		IProject prj = ResourcesPlugin.getWorkspace().getRoot().getProject(
				form.editor.m_frame.m_project);

		FormClassGenerator generator = new FormClassGenerator();

		generator.createClass(prj, className.getText(), "", form.getName(),
				selectEventList);

		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public void addPages() {
		String windowTitle = "badaResourceMainWizardPage";
		newProject = new OspAddClassMainPage(windowTitle, this);

		addPage(newProject);
	}

	public void setSelectedProject(String name) {
		selectPName = name;
	}

	public String getSelectedProject() {
		return selectPName;
	}

	public void settingTree(int index, Tree tree) {
		if (index == 0) {
			this.formTree = tree;
		} else if (index == 1) {
			this.controlBaseTree = tree;
		} else if (index == 2)
			this.controlTree = tree;
	}

	public void settingClassName(Text classNameText) {
		this.className = classNameText;
	}
}
