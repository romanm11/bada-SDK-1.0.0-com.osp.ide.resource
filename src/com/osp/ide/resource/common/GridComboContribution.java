package com.osp.ide.resource.common;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IEditorPart;

import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;

public class GridComboContribution implements IContributionItem {
	public static final String ID = "Grid Combo Contribution";
	private boolean enable = true;
	private boolean visible = true;
	ToolItem item;
	private Combo control;
	private IEditorPart editor;

	public Control createControl(final Composite parent) {
		if(control != null && !control.isDisposed()) {
			control.dispose();
			control = null;
		}
		
		control = new Combo(parent, SWT.DROP_DOWN);
		control.add("5");
		control.add("10");
		control.add("20");
		control.add("50");
		control.add("100");
		control.select(1);
		
		control.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.getSource();
				int dim = Integer.parseInt(combo.getText());
				setDimension(dim);
				item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
			}
		});
		
		control.addVerifyListener(new VerifyListener(){

			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}});
		
		control.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				Combo combo = (Combo) e.getSource();
				try {
					int dim = Integer.parseInt(combo.getText());
					if (dim > 1 && dim < 100) {
						setDimension(dim);
					} else if(dim >= 100) {
						combo.select(4);
						setDimension(100);
					}
				} catch (NumberFormatException en) {
					combo.select(1);
					setDimension(10);
				}
				item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
			}
		});
		return control;
	}

	public void setDimension(int dim) {
		if(editor != null) {
			if(editor instanceof OspFormEditor)
				((OspFormEditor)editor).setGridDimension(dim);
			else if(editor instanceof OspPanelEditor)
				((OspPanelEditor)editor).setGridDimension(dim);
			else if(editor instanceof OspPopupEditor)
				((OspPopupEditor)editor).setGridDimension(dim);
		}
	}

	@Override
	public void dispose() {
		if(item != null && !item.isDisposed()) {
			item.dispose();
			item = null;
		}
		
		if(control != null && !control.isDisposed()) {
			control.dispose();
			control = null;
		}
	}

	@Override
	public void fill(Composite parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fill(Menu parent, int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void fill(ToolBar parent, int index) {
		// TODO Auto-generated method stub
		createControl(parent);
		if(item != null && !item.isDisposed()) {
			item.dispose();
			item = null;
		}
		
		item = new ToolItem(parent, SWT.SEPARATOR, index);
		item.setControl(control);
		item.setWidth(control.computeSize(SWT.DEFAULT, SWT.DEFAULT).x);
	}

	@Override
	public void fill(CoolBar parent, int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDynamic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enable;
	}

	@Override
	public boolean isGroupMarker() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSeparator() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public void saveWidgetState() {
	}

	@Override
	public void setParent(IContributionManager parent) {
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		this.visible = visible;
	}

	@Override
	public void update() {
	}

	@Override
	public void update(String id) {
	}

	public void setEditor(IEditorPart editor) {
		this.editor = editor;
		if(control != null && !control.isDisposed()) {
			if(editor instanceof OspFormEditor)
				control.setText(((OspFormEditor)editor).getGridDimension());
			else if(editor instanceof OspPanelEditor)
				control.setText(((OspPanelEditor)editor).getGridDimension());
			else if(editor instanceof OspPopupEditor)
				control.setText(((OspPopupEditor)editor).getGridDimension());
		}
	}
}
