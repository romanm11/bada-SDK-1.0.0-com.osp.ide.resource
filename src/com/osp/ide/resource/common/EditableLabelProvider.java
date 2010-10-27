package com.osp.ide.resource.common;

import org.eclipse.jface.viewers.LabelProvider;

public class EditableLabelProvider extends LabelProvider {

	/**
	 * The array of String labels.
	 */
	private String[] values;

	/**
	 * @param values
	 *            the possible label values that this
	 *            <code>ILabelProvider</code> may return.
	 */
	public EditableLabelProvider(String[] values) {
		this.values = values;
	}

	/**
	 * @return the possible label values that this <code>ILabelProvider</code>
	 *         may return.
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * @param values
	 *            the possible label values that this
	 *            <code>ILabelProvider</code> may return.
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * Returns the <code>String</code> that maps to the given
	 * <code>Integer</code> offset in the values array.
	 * 
	 * @param element
	 *            an <code>Integer</code> object whose value is a valid location
	 *            within the values array of the receiver
	 * @return a <code>String</code> from the provided values array, or the
	 *         empty <code>String</code>
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element == null) {
			return ""; //$NON-NLS-1$
		}

		if (element instanceof Integer) {
			int index = ((Integer) element).intValue();
			if (index >= 0 && index < values.length) {
				return values[index];
			}
			return ""; //$NON-NLS-1$
		}
		else if(element instanceof String)
			return (String) element;
		
		return ""; //$NON-NLS-1$

	}
}
