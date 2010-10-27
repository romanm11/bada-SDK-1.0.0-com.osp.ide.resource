package com.osp.ide.resource.resourceexplorer;

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
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.IPropertySourceProvider;
import org.eclipse.ui.views.properties.NewPropertySheetHandler;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;


public class ResourcePropertyPage extends PropertySheetPage implements IPropertySourceProvider {
	public static final Color COLOR_PROPERTY_BG = new Color(null, 227, 227, 227);
    private IMenuManager menuManager;
	
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// TODO Auto-generated method stub
		super.selectionChanged(part, selection);
		setColumnWidth();
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

    @Override
	public IPropertySource getPropertySource(Object object) {
		if(object instanceof IPropertySource)
			return (IPropertySource)object;
		return null;
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
		if(control == null)
			return;
		
		TreeColumn[] columns = control.getColumns();
		columns[0].setWidth(250);
		columns[1].setWidth(500);
	}
}
