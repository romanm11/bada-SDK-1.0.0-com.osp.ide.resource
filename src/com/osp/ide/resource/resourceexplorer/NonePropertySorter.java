package com.osp.ide.resource.resourceexplorer;

import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public class NonePropertySorter extends PropertySheetSorter {

	@Override
	public int compare(IPropertySheetEntry entryA, IPropertySheetEntry entryB) {
		// TODO Auto-generated method stub
		return 0;
		//return super.compare(entryA, entryB);
	}

	@Override
	public int compareCategories(String categoryA, String categoryB) {
		// TODO Auto-generated method stub
		return 0;
		//return super.compareCategories(categoryA, categoryB);
	}

	@Override
	public void sort(IPropertySheetEntry[] entries) {
		// TODO Auto-generated method stub
		//super.sort(entries);
	}

}
