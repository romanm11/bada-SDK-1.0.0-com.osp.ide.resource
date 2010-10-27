/**
 * 
 */
package com.osp.ide.resource.resourceexplorer;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.string.OspUIString;

/**
 * @author SRD1_CHJ
 * 
 */
public class ResourceTreeContentProvider implements IStructuredContentProvider, ITreeContentProvider {

    OspUIString string;
    OspResourceManager manager;
    private List<TreeObject> rootChildren = new ArrayList<TreeObject>();
    private TreeViewer viewer;

    /**
	 * 
	 */
    public ResourceTreeContentProvider(TreeViewer viewer) {
        this.viewer = viewer;
    }

    public void setString(OspUIString string) {
        // TODO Auto-generated constructor stub
        this.string = string;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
     * Object)
     */
    @Override
    public Object[] getChildren(Object parentElement) {
        if(parentElement.equals(ResourceExplorer.TREE_ROOT))
            return rootChildren.toArray();

        if (!(parentElement instanceof TreeObject))
            return new Object[0];
        TreeObject parent = (TreeObject) parentElement;

        return parent.getChildren() == null ? new Object[0] : parent.getChildren().toArray();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
     * )
     */
    @Override
    public Object getParent(Object element) {
        if (!(element instanceof TreeObject))
            return null;
        return ((TreeObject) element).getParent();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
     * Object)
     */
    @Override
    public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
     * .lang.Object)
     */
    @Override
    public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }
    
    /**
     * @param id
     */
    public TreeObject getRootItem(String id) {
        TreeObject item = null;
        
        if(rootChildren == null)
            return item;
        
        for(int i=0; i<rootChildren.size(); i++) {
            String itemId = rootChildren.get(i).getId();
            if(itemId.toUpperCase(Locale.getDefault()).equals(id.toUpperCase(Locale.getDefault()))) {
                item = rootChildren.get(i);
                break;
            }
        }
        return item;
    }

    /**
     * @param isReset
     */
    protected void refreshRootChildren(boolean isReset) {
        if(isReset) {
            rootChildren = new ArrayList<TreeObject>();

            Vector<String> screen = Activator.getStringScreen("");
            for (int i = 0; i < screen.size(); i++) {
                rootChildren.add(new TreeObject(null, screen.get(i), ResourceExplorer.DEPTH_ROOT, ResourceExplorer.KIND_RESOURCE));
            }

            rootChildren.add(new TreeObject(string, ResourceExplorer.cszKindName[ResourceExplorer.KIND_STRING], ResourceExplorer.DEPTH_ROOT, ResourceExplorer.KIND_STRING));
        }
        for(int i=0; i<rootChildren.size(); i++)
            refreshChildren(rootChildren.get(i), isReset);
    }

    /**
     * @param parent
     * @param isReset
     */
    protected void refreshChildren(TreeObject parent, boolean isReset) {
        if(parent == null)
            return;
        
        if(isReset)
            parent.resetChildren();

        switch (parent.getDepth()) {
        case ResourceExplorer.DEPTH_ROOT:
            if (parent.getKind() == ResourceExplorer.KIND_STRING)
                return;

            refreshGroup(parent, ResourceExplorer.KIND_FORM, isReset);
            refreshGroup(parent, ResourceExplorer.KIND_PANEL, isReset);
            refreshGroup(parent, ResourceExplorer.KIND_POPUP, isReset);
            break;
        case ResourceExplorer.DEPTH_GROUP:
            switch (parent.getKind()) {
            case ResourceExplorer.KIND_FORM:
                if (manager == null || manager.m_Form == null)
                    break;
                Hashtable<String, OspForm> scenes = manager.m_Form.get(parent.info);
                if (scenes == null)
                    break;
                syncTable(parent, scenes, ResourceExplorer.KIND_FORM);
                break;
            case ResourceExplorer.KIND_POPUP:
                if (manager == null || manager.m_Popup == null)
                    break;
                Hashtable<String, OspPopup> popups = manager.m_Popup.get(parent.info);
                if (popups == null)
                    break;
                syncTable(parent, popups, ResourceExplorer.KIND_POPUP);
                break;
            case ResourceExplorer.KIND_PANEL:
                if (manager == null || manager.m_Panels == null)
                    break;
                Hashtable<String, OspPanel> panels = manager.m_Panels.get(parent.info);
                if (panels == null)
                    break;
                syncTable(parent, panels, ResourceExplorer.KIND_PANEL);
                break;
            }
        }
    }

    /**
     * @param parent
     * @param kind
     * @param isReset
     */
    private void refreshGroup(TreeObject parent, int kind, boolean isReset) {
        String name = ResourceExplorer.cszKindName[kind];
        TreeObject child = null;
        
        List<TreeObject> children = parent.getChildren();
        for(int i=0; i<children.size(); i++) {
            if(children.get(i).getId().equals(name)) {
                child = children.get(i);
                break;
            }
        }

        if(child == null) {
            switch(kind) {
            case ResourceExplorer.KIND_FORM:
                child = new TreeObject(manager, name, ResourceExplorer.DEPTH_GROUP, kind, parent.getId());
                break;
            case ResourceExplorer.KIND_PANEL:
                child = new TreeObject(manager, name, ResourceExplorer.DEPTH_GROUP, kind, parent.getId());
                break;
            case ResourceExplorer.KIND_POPUP:
                child = new TreeObject(manager, name, ResourceExplorer.DEPTH_GROUP, kind, parent.getId());
                break;
            }
            parent.addChildren(child);
        }
        
        refreshChildren(child, isReset);
    }

    /**
     * @param parent
     * @param hashtable
     * @param kind
     */
    void syncTable(TreeObject parent, Object hashtable, int kind) {
        if(!(hashtable instanceof Hashtable<?, ?>))
            return;
        Hashtable<?, ?> tables = (Hashtable<?, ?>) hashtable;
        Enumeration<?> keys = tables.keys();
        
        switch(kind) {
        case ResourceExplorer.KIND_FORM:
            while (keys.hasMoreElements()) {
                String id = (String) keys.nextElement();
                if(parent.getChild(id) == null) {
                    Object table = tables.get(id);
                    if (table == null || !(table instanceof OspForm))
                        continue;
                    OspForm scene = (OspForm) table;
                    
                    final TreeObject child = new TreeObject(manager, id, ResourceExplorer.DEPTH_TABLE, kind, scene.m_infoScene);
                    parent.addChildren(child);
                    
                    if(viewer == null || viewer.getTree() == null)
                        continue;
                    Display display = viewer.getTree().getDisplay();
                    if(display == null || display.isDisposed())
                        continue;
                    
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            viewer.expandToLevel(child, 1);
                        }
                    });
                }
            }
            break;
        case ResourceExplorer.KIND_PANEL:
            while (keys.hasMoreElements()) {
                String id = (String) keys.nextElement();
                if(parent.getChild(id) == null) {
                    Object table = tables.get(id);
                    if (table == null || !(table instanceof OspPanel))
                        continue;
                    OspPanel panel = (OspPanel) table;
                    
                    final TreeObject child = new TreeObject(manager, id, ResourceExplorer.DEPTH_TABLE, kind, panel.m_infoPanel);
                    parent.addChildren(child);
                    
                    if(viewer == null || viewer.getTree() == null)
                        continue;
                    Display display = viewer.getTree().getDisplay();
                    if(display == null || display.isDisposed())
                        continue;
                    
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            viewer.expandToLevel(child, 1);
                        }
                    });
                }
            }
            break;
        case ResourceExplorer.KIND_POPUP:
            while (keys.hasMoreElements()) {
                String id = (String) keys.nextElement();
                if(parent.getChild(id) == null) {
                    Object table = tables.get(id);
                    if (table == null || !(table instanceof OspPopup))
                        continue;
                    OspPopup popup = (OspPopup) table;
                    
                    final TreeObject child = new TreeObject(this.manager, id, ResourceExplorer.DEPTH_TABLE, kind, popup.m_infoPopup);
                    parent.addChildren(child);
                    
                    if(viewer == null || viewer.getTree() == null)
                        continue;
                    Display display = viewer.getTree().getDisplay();
                    if(display == null || display.isDisposed())
                        continue;
                    
                    display.asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            viewer.expandToLevel(child, 1);
                        }
                    });
                }
            }
            break;
        }
        
        Object[] children = parent.getChildren().toArray();
        int size = children.length;
        if(tables.size() != size) {
            for(int i=0; i<size; i++) {
                if(children[i] instanceof TreeObject) {
                    TreeObject child = (TreeObject) children[i];
                    if(tables.get(child.getId()) == null)
                        parent.removeChild(child);
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
     * .viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        // TODO Auto-generated method stub
        refreshRootChildren(true);
    }

    public void setManager(OspResourceManager manager) {
        this.manager = manager;
   }
}
