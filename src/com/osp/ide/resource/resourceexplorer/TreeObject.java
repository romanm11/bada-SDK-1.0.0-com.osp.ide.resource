package com.osp.ide.resource.resourceexplorer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.string.OspUIString;

public class TreeObject implements IAdaptable, IPropertySource {
	public static final String PROPERTY_ID = "Id";

	public static final int BOOL_TRUE = 0;
	public static final int BOOL_FALSE = 1;
	public static final String[] BOOL = { "true", "false" };

	public static final String PROPERTY_PROJECT = "Project setting";
	public static final String PROPERTY_PATH = "Resource out path";

	private int nDepth;
	private int nKind;
	private String sID = "";
	Object info = null;
	OspUIString string;
	OspResourceManager manager;
	File file;

	ArrayList<IPropertyDescriptor> descriptors;

	private TreeObject parent;
	private List<TreeObject> children;

	public TreeObject(OspUIString string, String id, int depth, int kind) {
		this.string = string;
		sID = id;
		nDepth = depth;
		nKind = kind;
		initDescriptor();
		children = new ArrayList<TreeObject>();
	}

	public TreeObject(OspResourceManager manager, String id, int depth, int kind,
			Object object) {
		this.manager = manager;
		sID = id;
		nDepth = depth;
		nKind = kind;
		info = object;
		initDescriptor();
        children = new ArrayList<TreeObject>();
	}

	/**
     * @param copyOfTreeObject
     */
    private void setParent(TreeObject parent) {
        this.parent = parent;
    }

	public TreeObject getParent() {
		return parent;
	}

    /**
     * 
     */
    public void resetChildren() {
        while(children.size() > 0)
            children.remove(0);
    }
	
	public List<TreeObject> getChildren() {
	    return children;
	}
	
	public TreeObject getChild(String id) {
        for(int i=0; i<children.size(); i++) {
            if(children.get(i) != null && 
                children.get(i).getId().equals(id)) {
                return children.get(i);
            }
        }
        return null;
	}
	
	public void addChildren(TreeObject child) {
	    removeChild(child);
	    
	    children.add(child);
	    child.setParent(this);
	}
	
	public void removeChild(TreeObject child) {
	    children.remove(child);
	}
	
    public void removeChild(String id) {
        TreeObject child = getChild(id);
        if(child != null)
            children.remove(child);
    }
    
    private void initDescriptor() {
		descriptors = new ArrayList<IPropertyDescriptor>();

		switch (nDepth) {
		case ResourceExplorer.DEPTH_ROOT:
			descriptors.add(new PropertyDescriptor(PROPERTY_ID, "Group Name"));
			break;
		case ResourceExplorer.DEPTH_GROUP:
			descriptors.add(new PropertyDescriptor(PROPERTY_ID, "Group Name"));
			break;
		case ResourceExplorer.DEPTH_TABLE:
			descriptors.add(new TextPropertyDescriptor(PROPERTY_ID,
					"Resource ID"));
			if (nKind == ResourceExplorer.KIND_FORM) {
				descriptors.add(new PropertyDescriptor(PROPERTY_PROJECT,
						"Form File Name"));
				descriptors.add(new PropertyDescriptor(PROPERTY_PATH,
						"Form File Path"));
				break;
			} else if(nKind == ResourceExplorer.KIND_POPUP) {
				descriptors.add(new PropertyDescriptor(PROPERTY_PROJECT,
						"Popup File Name"));
				descriptors.add(new PropertyDescriptor(PROPERTY_PATH,
						"Popup File Path"));
		break;
	}
		}
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		return descriptors.toArray(new IPropertyDescriptor[0]);
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id.equals(PROPERTY_ID)) {
			setId((String) value);
		} else if (id.equals(PROPERTY_PROJECT))
			setProject((String) value);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(PROPERTY_ID))
			return getId();
		else if (id.equals(PROPERTY_PROJECT))
			return getProject();
		else if (id.equals(PROPERTY_PATH))
			return getPath();

		return null;
	}

	public static void copyFile(File source, File target) {
		
		RandomAccessFile in = null;
		RandomAccessFile out = null;
		try {
			in = new RandomAccessFile(source, "r");
			out = new RandomAccessFile(target, "rw");

			byte[] byteArray = new byte[(int) in.length()];

			in.read(byteArray);
			out.write(byteArray);
		} catch (IOException e) {
		} finally {
			try {
				if(out != null)
					out.close();
				if(in != null)
					in.close();
			} catch (IOException e) {
			}
		}
		refreshProject();
	}

	public static void refreshProject() {
		try {
			String prjName = ResourceExplorer.getResourceView().getCurProject();
			IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(prjName);
			project.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public int getDepth() {
		return nDepth;
	}

	public int getKind() {
		return nKind;
	}

	public void setId(String id) {
		String oldId = getId();
		if (id == null || id.isEmpty() || oldId.equals(id))
			return;
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (nDepth == ResourceExplorer.DEPTH_TABLE) {
			switch (nKind) {
			case ResourceExplorer.KIND_STRING:
				break;
			case ResourceExplorer.KIND_FORM:
				if (!manager.RenameScene((String) getParent().info, oldId, id))
					return;
				break;
			case ResourceExplorer.KIND_POPUP:
				if (!manager.RenamePopup((String) getParent().info, oldId, id))
					return;
				break;
			case ResourceExplorer.KIND_PANEL:
				if (!manager.RenamePanel((String) getParent().info, oldId, id))
					return;
				break;
			}
		}
		if (view != null) {
			view.refreshTree();
			view.refreshProject();
			view.renameEditor(oldId, id);
		}
		sID = id;
	}

	public String getId() {
		return sID;
	}

	public String getName() {
	    String name = sID;
        
	    if(isDirty())
	        name = "*" + sID;
	    
        return name;
	}

	/**
     * @return
     */
    private boolean isDirty() {
        if(parent == null)
            return false;
        switch (getDepth()) {
        case ResourceExplorer.DEPTH_GROUP:
            String screen;
            if(info instanceof String)
                screen = (String) info;
            else
                screen = parent.getName();
            switch(getKind()) {
            case ResourceExplorer.KIND_FORM:
                return manager.isSceneDirty(screen);
            case ResourceExplorer.KIND_PANEL:
                return manager.isPanelDirty(screen);
            case ResourceExplorer.KIND_POPUP:
                return manager.isPopupDirty(screen);
            }
            break;
        case ResourceExplorer.DEPTH_TABLE:
            switch(getKind()) {
            case ResourceExplorer.KIND_FORM:
                Hashtable<String, OspForm> forms = manager.m_Form.get(parent.info);
                if(forms == null)
                    break;
                OspForm form = forms.get(getId());
                if(form == null)
                    break;
                return form.isDirty();
            case ResourceExplorer.KIND_PANEL:
                Hashtable<String, OspPanel> panels = manager.m_Panels.get(parent.info);
                if(panels == null)
                    break;
                OspPanel panel = panels.get(getId());
                if(panel == null)
                    break;
                return panel.isDirty();
            case ResourceExplorer.KIND_POPUP:
                Hashtable<String, OspPopup> popups = manager.m_Popup.get(parent.info);
                if(popups == null)
                    break;
                OspPopup localPopup = popups.get(getId());
                if(localPopup == null)
                    break;
                return localPopup.isDirty();
            }
            break;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key == IPropertySource.class) {
			return this;
		}
		return null;
	}

	private String getPath() {
		if (info != null) {
			if (nDepth == ResourceExplorer.DEPTH_TABLE
					&& nKind == ResourceExplorer.KIND_FORM) {
				return ((FORMFRAME_INFO) info).headPath;
			} else if (nDepth == ResourceExplorer.DEPTH_TABLE
					&& nKind == ResourceExplorer.KIND_POPUP) {
				return ((POPUPFRAME_INFO) info).headPath;
			}
		}
		return null;
	}

	private String getProject() {
		if (info != null) {
			File file, parent;
			if (nDepth == ResourceExplorer.DEPTH_TABLE
					&& nKind == ResourceExplorer.KIND_FORM) {
				file = new File(((FORMFRAME_INFO) info).fileName);
				parent = new File(file.getParent());
				return parent.getName() + java.io.File.separatorChar + file.getName();
			} else if (nDepth == ResourceExplorer.DEPTH_TABLE
					&& nKind == ResourceExplorer.KIND_POPUP) {
				file = new File(((POPUPFRAME_INFO) info).fileName);
				parent = new File(file.getParent());
				return parent.getName() + java.io.File.separatorChar + file.getName();
			}
		}
		return null;
	}

	private void setProject(String name) {
		if (info != null) {
			if (nDepth == ResourceExplorer.DEPTH_TABLE
					&& nKind == ResourceExplorer.KIND_FORM) {
			}
		}
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}
}
