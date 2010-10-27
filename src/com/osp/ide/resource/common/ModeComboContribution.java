package com.osp.ide.resource.common;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import com.osp.ide.resource.resinfo.FrameConst;

public class ModeComboContribution implements IContributionItem {

	public static final String ID = "Mode Combo Contribution";
	private boolean enable = true;
	private boolean visible = true;
	private IEditorPart editor;
	private Combo control;
	private ToolItem item;

	public Control createControl(Composite parent) {
		if (control != null && !control.isDisposed()) {
			control.dispose();
			control = null;
		}

		control = new Combo(parent, SWT.READ_ONLY);
		control.add(FrameConst.cszFrmMode[FrameConst.PORTRAIT]);
		control.add(FrameConst.cszFrmMode[FrameConst.LANDCAPE]);
		control.select(0);
		control.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (editor == null) {
					control.select(0);
					return;
				}
				if (editor instanceof OspFormEditor) {
					((OspFormEditor) editor).mode = ((Combo) e.getSource())
							.getText();
					((OspFormEditor) editor).ReDrawFrame();
				} else if (editor instanceof OspPanelEditor) {
					((OspPanelEditor) editor).mode = ((Combo) e.getSource())
							.getText();
					((OspPanelEditor) editor).ReDrawFrame();
				} else if (editor instanceof OspPopupEditor) {
					((OspPopupEditor) editor).mode = ((Combo) e.getSource())
							.getText();
					((OspPopupEditor) editor).ReDrawFrame();
				}
			}
		});
		control.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				control.setText("");
			}
		});
		return control;
	}

	@Override
	public void dispose() {
		if (item != null && !item.isDisposed()) {
			item.dispose();
			item = null;
		}

		if (control != null && !control.isDisposed()) {
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
		if (item != null && !item.isDisposed()) {
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
		if (control != null && !control.isDisposed()) {
			if (editor instanceof OspFormEditor)
				control.setText(((OspFormEditor) editor).getMode());
			else if (editor instanceof OspPanelEditor)
				control.setText(((OspPanelEditor) editor).getMode());
			else if (editor instanceof OspPopupEditor)
				control.setText(((OspPopupEditor) editor).getMode());
		}
	}
}
