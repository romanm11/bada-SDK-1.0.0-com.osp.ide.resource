package com.osp.ide.resource.common;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class FilePropertyDescriptor extends PropertyDescriptor {
	String[] filter=null;

	public FilePropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	public FilePropertyDescriptor(Object id, String displayName, String[] filtetExt) {
		super(id, displayName);
		this.filter = filtetExt;
	}
	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new FileDialogCellEditor(parent);
		if(filter != null)
			((FileDialogCellEditor) editor).setFilterExt(filter);
		
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}
}
