package com.osp.ide.resource.resourceexplorer;

import java.util.Vector;

import org.eclipse.cdt.internal.ui.CPluginImages;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;

@SuppressWarnings("restriction")
public class OspResourceMainWizardPage extends WizardPage {
	private static final Image IMG_CATEGORY = CPluginImages
			.get(CPluginImages.IMG_OBJS_SEARCHFOLDER);
	private static final Image SCENEIMG_FILE = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/frame.png")
			.createImage();
	private static final Image SCENEIMG_ITEM = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
					"icons/frame_Sub.png").createImage();
//    private static final Image PANELIMG_FILE = AbstractUIPlugin
//            .imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/panel.png")
//            .createImage();
//    private static final Image PANELIMG_ITEM = AbstractUIPlugin
//            .imageDescriptorFromPlugin(Activator.PLUGIN_ID,
//                    "icons/panel_outline.png").createImage();
	private static final Image POPUPIMG_FILE = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/popup.png")
			.createImage();
	private static final Image POPUPIMG_ITEM = AbstractUIPlugin
			.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/popup_outline.png")
			.createImage();

	private Text textArea = null;
	private Tree mainTree;
	private Tree subTree;
	private String Pname = null;

	OspCreateResourceWizard createWizard;
	private String curProject;

	protected OspResourceMainWizardPage(String pageName,
			OspCreateResourceWizard wizard) {
		super(pageName);
		setTitle("bada Resource files");
		setDescription("Create resource files of the selected project");
		setImageDescriptor(AbstractUIPlugin
				.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/app_wizard.bmp"));
		createWizard = wizard;
	}

	public OspResourceMainWizardPage(String pageName,
			OspCreateResourceWizard wizard, String curProject) {
		this(pageName, wizard);
		this.curProject = curProject;
	}

	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		Composite composite = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout();
		composite.setLayout(gridLayout);

		createModuleNameGroup(composite);

		setControl(composite);

		updatePageComplete();
		setMessage(null);
		setErrorMessage(null);

	}

	private void createModuleNameGroup(Composite parent) {

		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		parent.setLayout(layout);
		parent.setLayoutData(new GridData(GridData.FILL_BOTH));

		createLabel(parent, "Select Project");
		mainTree = new Tree(parent, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		mainTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainTree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				String Pname = ((TreeItem) e.item).getText();
				if (Pname.equals(createWizard.getSelectedProject()))
					return;
				createWizard.setSelectedProject(Pname);
				subTree.setEnabled(true);
				fillSubTree();

				if (isCheckedSubTree() == false)
					textArea
							.setText("Select resource files to create for the selected project");
			}
		});
		subTree = new Tree(parent, SWT.SINGLE | SWT.BORDER | SWT.CHECK
				| SWT.V_SCROLL | SWT.H_SCROLL);
		subTree.setLayoutData(new GridData(GridData.FILL_BOTH));
		subTree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CHECK && e.item instanceof TreeItem) {
					TreeItem item = (TreeItem) e.item;
					if (item.getItems().length > 0) {
						TreeItem[] items;
						if (item.getChecked() == true) {
							boolean setSceneFile = true;
							items = item.getItems();
							item.setGrayed(false);
							for (int i = 0; i < items.length; i++) {
								items[i].setChecked(setSceneFile);
							}
						} else {
							boolean setSceneFile = false;
							items = item.getItems();
							for (int i = 0; i < items.length; i++) {
								items[i].setChecked(setSceneFile);
							}
						}
					} else {
						TreeItem[] items = item.getParentItem().getItems();
						boolean checked = item.getParentItem().getChecked();
						boolean grayed = item.getParentItem().getGrayed();
						for (int i = 0; i < items.length; i++) {
							if (i == 0) {
								checked = items[i].getChecked();
								grayed = items[i].getChecked();
							} else {
								checked &= items[i].getChecked();
								grayed |= items[i].getChecked();
							}
						}
						if (checked) {
							item.getParentItem().setChecked(true);
							item.getParentItem().setGrayed(false);
						} else if (grayed) {
							item.getParentItem().setChecked(true);
							item.getParentItem().setGrayed(true);
						} else {
							item.getParentItem().setChecked(false);
							item.getParentItem().setGrayed(false);
						}
					}
				}
				setTextAreaDescription();
				updatePageComplete();
			}
		});
		mainTree.setEnabled(false);
		subTree.setEnabled(false);
		textArea = createTextArea(parent);
		textArea.setText("Select the project");

		IStructuredSelection selection = createWizard.getSelection();

		IProject[] project;
		project = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (curProject == null || curProject.isEmpty()) {
			if (selection.isEmpty() || selection.getFirstElement() == null) {
				for (int i = 0; i < project.length; i++) {
					if (project[i] == null || project[i].isHidden()
							|| !project[i].isOpen())
						continue;
					Pname = project[i].getName();
					createTree(Pname, mainTree, IMG_CATEGORY);
					mainTree.setEnabled(true);
				}
			} else {
				Object element = selection.getFirstElement();

				if (element instanceof IResource) {
					project = new IProject[1];
					project[0] = ((IResource) element).getProject();
					if (project[0] != null && !project[0].isHidden()
							&& project[0].isOpen()) {
						Pname = ((IResource) element).getProject().getName();
						createTree(Pname, mainTree, IMG_CATEGORY);
						mainTree.setEnabled(true);
					}
				} else if (element instanceof TreeObject) {
				    ResourceExplorer view = ResourceExplorer.getResourceView();
				    if(view == null)
				        return;
				    
                    Pname = view.curProject;
                    createTree(Pname, mainTree, IMG_CATEGORY);
                    mainTree.setEnabled(true);
				} else {
	                for (int i = 0; i < project.length; i++) {
	                    if (project[i] == null || project[i].isHidden()
	                            || !project[i].isOpen())
	                        continue;
	                    Pname = project[i].getName();
	                    createTree(Pname, mainTree, IMG_CATEGORY);
	                    mainTree.setEnabled(true);
	                }
				}
			}
		} else {
			Pname = curProject;
			createTree(Pname, mainTree, IMG_CATEGORY);
			mainTree.setEnabled(true);
		}
		if (project.length > 0) {
			fillSubTree();
		} else
			textArea
					.setText("First of all, create a project, please try again");
	}

	private void setTextAreaDescription() {
		if (isCheckedSubTree() == true) {
			textArea.setText("Create resource file of the selected project");
		} else {
			textArea
					.setText("Select resource file to create for the selected project");
		}
	}

	private void createTree(String name, Tree tree, Image image) {
		TreeItem maintree = new TreeItem(tree, SWT.NONE);
		maintree.setText(name);
		maintree.setImage(image);
	}

	private void fillSubTree() {
		if (subTree == null || subTree.isDisposed())
			return;
		subTree.removeAll();
		TreeItem mainItem = new TreeItem(subTree, SWT.NONE);
		mainItem.setText(OspCreateResourceWizard.SCENEFILE);
		mainItem.setImage(SCENEIMG_FILE);
		TreeItem subItem;
		Vector<String> screen = Activator.getStringScreen(createWizard
				.getSelectedProject());
		for (int i = 0; i < screen.size(); i++) {
			subItem = new TreeItem(mainItem, SWT.CHECK);
			subItem.setText(screen.elementAt(i));
			subItem.setImage(SCENEIMG_ITEM);
		}
		mainItem.setExpanded(true);

//        TreeItem panelItem = new TreeItem(subTree, SWT.NONE);
//        panelItem.setText(OspCreateResourceWizard.PANELFILE);
//        panelItem.setImage(PANELIMG_FILE);
//        for (int i = 0; i < screen.size(); i++) {
//            subItem = new TreeItem(panelItem, SWT.CHECK);
//            subItem.setText(screen.elementAt(i));
//            subItem.setImage(PANELIMG_ITEM);
//        }
//        panelItem.setExpanded(true);

		TreeItem popupItem = new TreeItem(subTree, SWT.NONE);
		popupItem.setText(OspCreateResourceWizard.POPUPFILE);
		popupItem.setImage(POPUPIMG_FILE);
		for (int i = 0; i < screen.size(); i++) {
			subItem = new TreeItem(popupItem, SWT.CHECK);
			subItem.setText(screen.elementAt(i));
			subItem.setImage(POPUPIMG_ITEM);
		}
		popupItem.setExpanded(true);
	}

	public Tree getSubTree() {
		return subTree;
	}

	private Label createLabel(Composite container, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.horizontalSpan = 2;
		label.setLayoutData(gd);
		return label;
	}

	private Text createTextArea(Composite container) {
		textArea = new Text(container, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER
				| SWT.READ_ONLY);
		GridData gd = new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.horizontalSpan = 2;
		gd.heightHint = 40;
		textArea.setLayoutData(gd);

		return textArea;
	}

	private boolean isCheckedSubTree() {
		TreeItem[] items = subTree.getItems();
		for (int i = 0; i < items.length; i++) {
			TreeItem[] subItems = items[i].getItems();
			for (int j = 0; j < subItems.length; j++) {
				boolean check = subItems[j].getChecked();
				if (check)
					return true;
			}
		}
		return false;
	}

	private void updatePageComplete() {
		setPageComplete(false);

		if (isCheckedSubTree() == false)
			return;

		setPageComplete(true);
		setMessage(null);
		setErrorMessage(null);
	}
}
