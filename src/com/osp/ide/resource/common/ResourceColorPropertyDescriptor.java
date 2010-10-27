package com.osp.ide.resource.common;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class ResourceColorPropertyDescriptor extends PropertyDescriptor {

	public ResourceColorPropertyDescriptor(Object id, String displayName) {
		super(id, displayName);
	}

	/**
	 * @see org.eclipse.ui.views.properties.IPropertyDescriptor#createPropertyEditor(Composite)
	 */
	@Override
	public CellEditor createPropertyEditor(Composite parent) {
		CellEditor editor = new ResourceColorCellEditor(parent);
		if (getValidator() != null)
			editor.setValidator(getValidator());
		return editor;
	}

}
