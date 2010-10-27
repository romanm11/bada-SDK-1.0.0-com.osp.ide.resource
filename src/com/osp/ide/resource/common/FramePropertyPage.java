package com.osp.ide.resource.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.SubMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.NewPropertySheetHandler;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;

import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.resinfo.FrameConst;

public class FramePropertyPage extends PropertySheetPage implements
		PropertyChangeListener {
	public static final Color COLOR_PROPERTY_BG = new Color(null, 227, 227, 227);
	public static final Color COLOR_NOT_APPLAY = new Color(null, 212, 212, 212);
    public static final String PROPERTY_DISABLE = "Property Diable";
    private IMenuManager menuManager;

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		super.selectionChanged(part, selection);
		setColumnWidth();
		setRowBackground();
	}

	@Override
	public void createControl(Composite parent) {
		// TODO Auto-generated method stub
		super.createControl(parent);
	}

	@Override
	public void setSorter(PropertySheetSorter sorter) {
		// TODO Auto-generated method stub
		super.setSorter(sorter);
	}

    /* (non-Javadoc)
     * @see org.eclipse.ui.views.properties.PropertySheetPage#makeContributions(org.eclipse.jface.action.IMenuManager, org.eclipse.jface.action.IToolBarManager, org.eclipse.jface.action.IStatusLineManager)
     */
    @Override
    public void makeContributions(IMenuManager menuManager, IToolBarManager toolBarManager, IStatusLineManager statusLineManager) {
        // TODO Auto-generated method stub
        super.makeContributions(menuManager, toolBarManager, statusLineManager);
        this.menuManager = menuManager;
        setNewCommandVisible(false);
    }

	/**
     * @param enable
     */
    private void setNewCommandVisible(boolean enable) {
        if(menuManager instanceof SubMenuManager) {
            IContributionItem newMenu = ((SubMenuManager)menuManager).getParent().find(NewPropertySheetHandler.ID);
            if(newMenu != null)
                newMenu.setVisible(enable);
        }
    }

    public void setColumnWidth() {
		Tree control = (Tree) getControl();
		if (control == null)
			return;

		TreeColumn[] columns = control.getColumns();
		// for(int i=0 ;i<columns.length; i++)
		// {
		// System.out.println(columns[i]+" : "+columns[i].getWidth());
		// columns[i].setWidth(10);
		// }
		columns[0].setText("Property");
		columns[0].setWidth(290);
		columns[1].setWidth(500);
	}

	public void setRowBackground() {
		Tree control = (Tree) getControl();
		if (control == null)
			return;

		TreeItem[] items = control.getItems();
		for (int i = 0; i < items.length; i++) {
			items[i].setBackground(COLOR_PROPERTY_BG);
			if (items[i].getText().equals("Misc")) {
				items[i].setBackground(COLOR_NOT_APPLAY);
				items[i].setText("Misc (Not Apply)");
				items[i].setForeground(ColorConstants.darkGray);
				items[i].setGrayed(true);
				TreeItem[] subItems = items[i].getItems();
				for (int j = 0; j < subItems.length; j++) {
					subItems[j].setBackground(COLOR_NOT_APPLAY);
					subItems[j].setForeground(ColorConstants.darkGray);
				}
			}
			
			TreeItem[] subItems = items[i].getItems();
			for(TreeItem subItem:subItems ) {
			    Object data = subItem.getData();
			    if(data == null || !(data instanceof UndoablePropertySheetEntry))
			        continue;
			    
			    UndoablePropertySheetEntry entry = (UndoablePropertySheetEntry) data;
			    
			    String description = entry.getDescription();
			    if(description != null && description.equals(PROPERTY_DISABLE))
			        subItem.setForeground(ColorConstants.gray);
			    else if(subItem.getForeground().equals(ColorConstants.gray))
                    subItem.setForeground(ColorConstants.black);
			}
		}
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
		setRowBackground();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		((FrameNode) evt.getSource())
				.setDock(FrameConst.cszDock[FrameConst.DOCK_NONE]);
		refresh();
	}
}
