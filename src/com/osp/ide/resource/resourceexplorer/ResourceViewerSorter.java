package com.osp.ide.resource.resourceexplorer;

import java.text.Collator;

import org.eclipse.jface.viewers.ContentViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * A viewer sorter is used by a {@link StructuredViewer} to reorder the elements 
 * provided by its content provider.
 * <p>
 * The default <code>compare</code> method compares elements using two steps. 
 * The first step uses the values returned from <code>category</code>. 
 * By default, all elements are in the same category. 
 * The second level is based on a case insensitive compare of the strings obtained 
 * from the content viewer's label provider via <code>ILabelProvider.getText</code>.
 * </p>
 * <p>
 * Subclasses may implement the <code>isSorterProperty</code> method;
 * they may reimplement the <code>category</code> method to provide 
 * categorization; and they may override the <code>compare</code> methods
 * to provide a totally different way of sorting elements.
 * </p>
 * <p>
 * It is recommended to use <code>ViewerComparator</code> instead.
 * </p>
 * @see IStructuredContentProvider
 * @see StructuredViewer
 */
public class ResourceViewerSorter extends ViewerSorter {
    /**
     * The collator used to sort strings.
     * 
     * @deprecated as of 3.3 Use {@link ViewerComparator#getComparator()}
     */
    protected Collator collator;

    /**
     * Creates a new viewer sorter, which uses the default collator
     * to sort strings.
     */
    public ResourceViewerSorter() {
        this(Collator.getInstance());
    }

    /**
     * Creates a new viewer sorter, which uses the given collator
     * to sort strings.
     *
     * @param collator the collator to use to sort strings
     */
    public ResourceViewerSorter(Collator collator) {
    	super(collator);
        this.collator = collator;
    }

    /**
     * Returns the collator used to sort strings.
     *
     * @return the collator used to sort strings
     * @deprecated as of 3.3 Use {@link ViewerComparator#getComparator()}
     */
    public Collator getCollator() {
        return collator;
    }

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        int cat1 = category(e1);
        int cat2 = category(e2);

        if (cat1 != cat2) {
			return cat1 - cat2;
		}
    	
        String name1;
        String name2;

        if (viewer == null || !(viewer instanceof ContentViewer)) {
            name1 = e1.toString();
            name2 = e2.toString();
        } else {
            IBaseLabelProvider prov = ((ContentViewer) viewer)
                    .getLabelProvider();
            if (prov instanceof ILabelProvider) {
                ILabelProvider lprov = (ILabelProvider) prov;
                name1 = lprov.getText(e1);
                name2 = lprov.getText(e2);
            } else {
                name1 = e1.toString();
                name2 = e2.toString();
            }
        }
        
        if (name1 == null) {
			name1 = "";//$NON-NLS-1$
		}
        if (name2 == null) {
			name2 = "";//$NON-NLS-1$
		}

        if(name1.charAt(0) == '*')
        	name1 = name1.substring(1);
        if(name2.charAt(0) == '*')
        	name2 = name2.substring(1);
        
        // use the comparator to compare the strings
        return getComparator().compare(name1, name2);
	}

}
