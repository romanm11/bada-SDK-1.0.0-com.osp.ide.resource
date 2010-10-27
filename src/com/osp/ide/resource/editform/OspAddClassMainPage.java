package com.osp.ide.resource.editform;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.resinfo.FrameConst;

public class OspAddClassMainPage extends WizardPage {

	private Label className;
	private Label lblCtrl;
	private Label lblEvent;
	private Label lblControl;
	private Tree formTree;
	private Tree controlBassTree;
	private Tree controlTree;
	private Text classNameText;
	private boolean bWizardFinish = false;

	OspAddClassWidzard createWizard;

	protected OspAddClassMainPage(String pageName,
			OspAddClassWidzard wizard) {
		super(pageName);
		setTitle("Select EventListener");
		setDescription("Select Control and EventListener.");
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "icons/app_wizard.bmp"));
		createWizard = wizard;
	}

	public void createControl(Composite parent) {

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		createClassNameGroup(composite);
		createTreeGroup(composite);

		setControl(parent);

		updatePageComplete();
		setMessage(null);
		setErrorMessage(null);
	}

	private void createClassNameGroup(Composite parent) {
		className = new Label(parent, SWT.NONE);
		className.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1));
		className.setText("ClassName");

		classNameText = new Text(parent, SWT.BORDER);
		classNameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 2, 1));
		createWizard.settingClassName(classNameText);
		classNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateNameStatus();
				updatePageComplete();
			}
		});
		
		Label lblSpace = new Label(parent, SWT.NONE);
		lblSpace.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1));
	}

	protected void createTreeGroup(Composite parent) {

		lblCtrl = new Label(parent, SWT.NONE);
		lblCtrl.setText("Form EventListener");
		new Label(parent, SWT.NONE);

		lblEvent = new Label(parent, SWT.NONE);
		lblEvent.setText("Control Base EventListener");

		formTree = new Tree(parent, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		formTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));

		createWizard.settingTree(0, formTree);

		boolean exist = FormTemplateXmlStore.isExists(true);
		Vector<String> list;
		if(exist) {
			list = FormTemplateXmlStore
					.getInterfaceList(FrameConst.cszTag[FrameConst.WINDOW_FORM]);
			for (int i = 0; i < list.size(); i++) {
				TreeItem iItem = new TreeItem(formTree, SWT.NONE);
				iItem.setText(list.elementAt(i));
			}
		}

		controlBassTree = new Tree(parent, SWT.CHECK | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		controlBassTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));

		createWizard.settingTree(1, controlBassTree);

		list = FormTemplateXmlStore.getInterfaceList("Control");
		for (int i = 0; i < list.size(); i++) {
			TreeItem iItem = new TreeItem(controlBassTree, SWT.NONE);
			iItem.setText(list.elementAt(i));
		}

		Label lblSpace = new Label(parent, SWT.NONE);
		lblSpace.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 3, 1));
		
		lblControl = new Label(parent, SWT.NONE);
		lblControl.setText("Control");
		new Label(parent, SWT.NONE);

		controlTree = new Tree(parent, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL
				| SWT.H_SCROLL);
		GridData gd_tree_2 = new GridData(SWT.FILL, SWT.FILL, false, true, 3, 1);

		createWizard.settingTree(2, controlTree);

		controlTree.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (e.detail == SWT.CHECK && e.item instanceof TreeItem) {
					TreeItem item = (TreeItem) e.item;
					if (item.getItems().length > 0) {
						TreeItem[] items;
						if (item.getChecked() == true) {
							boolean setCtlTree = true;
							items = item.getItems();
							item.setGrayed(false);
							for (int i = 0; i < items.length; i++) {
								items[i].setChecked(setCtlTree);
							}
						} else {
							boolean setCtlTree = false;
							items = item.getItems();
							for (int i = 0; i < items.length; i++) {
								items[i].setChecked(setCtlTree);
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

			}
		});

		Form form = createWizard.getForm();
		List<Integer> typelist = form.getChildrenTypeList();
		for (int i = 0; i < typelist.size(); i++) {
			Vector<String> interfaceList = FormTemplateXmlStore
					.getInterfaceList(typelist.get(i));
			if (interfaceList.size() <= 0)
				continue;

			TreeItem iItem = new TreeItem(controlTree, SWT.NONE);
			iItem.setText(FrameConst.cszTag[typelist.get(i)]);
			for (int n = 0; n < interfaceList.size(); n++) {
				TreeItem subItem = new TreeItem(iItem, SWT.NONE);
				subItem.setText(interfaceList.elementAt(n));
			}
		}

		gd_tree_2.heightHint = 197;
		controlTree.setLayoutData(gd_tree_2);
	}

	private void updateNameStatus() {
		String text = classNameText.getText();
		char ch = 0;
			
		for(int i =0; i< text.length(); i++){
			ch = text.charAt(i);
			if((ch == '.')){
				setMessage(null);
				setErrorMessage(null);
				setErrorMessage("class name must not be qualified.");
				bWizardFinish = false;
				return;
			}else if ((ch == '$')){
				setMessage(null);
				setErrorMessage(null);
				setMessage(
						"class name is discouraged. By convention, bada type names usually don't contain the $ character.",
						WARNING);	
				bWizardFinish = true;
				return;
			}
		}
		
		if (text.length() == 0) {
			setMessage(null);
			setErrorMessage(null);
			setMessage("class name is empty.");
			bWizardFinish = false;
			return;
		} else if ((!text.equals(text.trim())) || (text.indexOf(" ") != -1)) { //$NON-NLS-1$
            setMessage(null);
            setErrorMessage(null);
            setMessage("class name can not contain spaces.");
            bWizardFinish = false;
            return;
		}else if(text.length() > 80){
			setMessage(null);
			setErrorMessage(null);
			setMessage("class name exceeds 80 characters.");
			bWizardFinish = false;
			return;
		} else {
			for (int i = 0; i < text.length(); i++) {
				ch = text.charAt(i);
				if (i == 0) {
					if (!IsFirstCharCheck(ch))
						return;
				} else{
					IsCharCheeck(ch);
				}
			}
		}
	}

	private boolean IsFirstCharCheck(char ch) {
		String text = classNameText.getText();
		if (((ch >= 'a') && (ch <= 'z'))) {
			setMessage(null);
			setErrorMessage(null);
			setMessage(
					"Start class name with upper case letter.",
					WARNING);			
			bWizardFinish = true;
			return true;
		} else if (((ch >= '0') && (ch <= '9'))) {
			setMessage(null);
			setErrorMessage(null);
			setErrorMessage("class name is not valid. The type name '" + text
					+ "' is not a valid identifier.");
			bWizardFinish = false;
			return false;
		}
		setMessage(null);
		setErrorMessage(null);
		setMessage("Create a new class.");
		bWizardFinish = true;
		return true;

	}

	private boolean IsCharCheeck(char ch) {

		if (((ch >= 'A') && (ch <= 'Z')) || ((ch >= 'a') && (ch <= 'z'))
				|| ((ch >= '0') && (ch <= '9')) || (ch == '_')) {
			
			bWizardFinish = true;
			return true;
		} else {
			bWizardFinish = false;
			return false;
		}
	}

	private void updatePageComplete() {
		setPageComplete(false);

		if (bWizardFinish == false)
			return;

		setPageComplete(true);

	}
}
