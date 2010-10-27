package com.osp.ide.resource.documents;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.resinfo.COLORPICKER_INFO;
import com.osp.ide.resource.resinfo.DATEPICKER_INFO;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANEL_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resinfo.TIMEPICKER_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspForm implements FrameConst {
    // public OspUIFrame m_parent;
    public FORMFRAME_INFO m_infoScene;
    public OspFormMarkup m_Markup;

    // key:<KIND_CAPTION> <NAME>:String name
    public Hashtable<String, ITEM_INFO> m_items;
    protected OspResourceManager m_manager;
    private boolean isDirty = false;
    private String screen;

    public OspForm(OspResourceManager frame, String screen, String headPath, String filePath) {
        // m_parent = parent;
        m_manager = frame;
        this.screen = screen;
        m_infoScene = new FORMFRAME_INFO();
        m_infoScene.headPath = headPath;
        m_infoScene.fileName = filePath;

        m_Markup = new OspFormMarkup(this);

        // key:<KIND_CAPTION> <NAME>:String name
        m_items = new Hashtable<String, ITEM_INFO>();
    };

    public OspForm getClone(String newScreen) {
        if (newScreen == null || newScreen.isEmpty())
            newScreen = screen;
        
        OspForm newForm = new OspForm(m_manager, newScreen, m_infoScene.headPath, 
            m_infoScene.fileName.replace(screen, newScreen));

        newForm.setDirty(true);
        newForm.m_infoScene = m_infoScene.clone();
        if(OspResourceManager.isScotia(newScreen))
        	newForm.m_infoScene.bgColorOpacity = 100;
        newForm.m_infoScene.fileName = m_infoScene.fileName.replace(screen, newScreen);
        
        Point size = Activator.getSScreenToPoint(newScreen);
        Layout formLayout = newForm.m_infoScene.layout.get(cszFrmMode[PORTRAIT]);
        if(formLayout != null) {
            formLayout.width = size.x;
            formLayout.height = size.y;
        }
        formLayout = newForm.m_infoScene.layout.get(cszFrmMode[LANDCAPE]);
        if(formLayout != null) {
            formLayout.width = size.y;
            formLayout.height = size.x;
        }
        
        Enumeration<String> keys = m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = m_items.get(keys.nextElement());
            if (item == null)
                continue;
            ITEM_INFO newItem = item.clone();
            if (!screen.equals(newScreen)) {
                Enumeration<String> layoutKeys = item.layout.keys();
                while (layoutKeys.hasMoreElements()) {
                    Layout newLayout = computeLayout(newScreen, item.layout.get(layoutKeys.nextElement()));
                    
                    if(newItem instanceof COLORPICKER_INFO ||
                        newItem instanceof DATEPICKER_INFO ||
                        newItem instanceof TIMEPICKER_INFO) {
                        ResourceExplorer view = ResourceExplorer.getResourceView();
                        Tag_info tag = view.getImageInfo(cszTag[newItem.type], newScreen);
                        newLayout.height = tag.dftlSize.y;
                    }
                    
                    newItem.layout.put(newLayout.mode, newLayout);
                }
            }
            if(newItem instanceof LABEL_INFO) {
            	Point oldSize = Activator.getSScreenToPoint(screen);
                Point newSize = Activator.getSScreenToPoint(newScreen);
            	((LABEL_INFO) newItem).textSize = ((LABEL_INFO) item).textSize * newSize.x / oldSize.x;
            }
            
            if(OspResourceManager.isScotia(newScreen)) {
            	if(newItem instanceof LABEL_INFO)
            		((LABEL_INFO) newItem).bgColorOpacity = 100;
            	else if(newItem instanceof PANEL_INFO)
            		((PANEL_INFO) newItem).bgColorOpacity = 100;
            	else if(newItem instanceof SCROLLPANEL_INFO)
            		((SCROLLPANEL_INFO) newItem).bgColorOpacity = 100;
            }
            
            newForm.m_items.put(newItem.Id, newItem);
        }

        return newForm;
    }

    public Layout computeLayout(String point, Layout layout) {
        Point size = Activator.getSScreenToPoint(point);
        Point curSize = Activator.getSScreenToPoint(screen);
        // System.out.println(layout.mode + ": " + size + "] Layout : ( "
        // + layout.x + ", " + layout.y + ", " + layout.width
        // + ", " + layout.height + ")");

        Layout retLayout = new Layout();
        retLayout.mode = layout.mode;
        retLayout.style = layout.style;
        retLayout.maxDropLineCount = layout.maxDropLineCount;
        if (layout.mode.equals(cszFrmMode[FrameConst.PORTRAIT])) {
            retLayout.x = (size.x * layout.x) / curSize.x;
            retLayout.y = (size.y * layout.y) / curSize.y;
            retLayout.width = (size.x * layout.width) / curSize.x;
            retLayout.height = (size.y * layout.height) / curSize.y;
        } else {
            retLayout.x = (size.y * layout.x) / curSize.y;
            retLayout.y = (size.x * layout.y) / curSize.x;
            retLayout.width = (size.y * layout.width) / curSize.y;
            retLayout.height = (size.x * layout.height) / curSize.x;
        }
        // r.height = (lcdWidth * r.height)/lcdHeight;

        // System.out.println("    " + curSize + "] Layout : ( "
        // + retLayout.x + ", " + retLayout.y + ", " + retLayout.width
        // + ", " + retLayout.height + ")");
        return retLayout;
    }

    public OspResourceManager getFrame() {
        return m_manager;
    }

    public String getScreen() {
        return screen;
    }

    public String GetProjectDirectory() {
        return m_manager.getProjectDirectory();
    };

    public String MakeID(int type, String refer) {
        int i = 1;
        String s;

        // if (refer.isEmpty())
        // refer = "Unnamed";

        do {
            s = String.format("IDC_%s%d", cszCtlType[type], i++);
        } while ((m_items.get(s)) != null);

        return s;
    };

    public Vector<String> GetChildren(String pID) {
        String s;
        Enumeration<String> keys = null;
        Vector<String> list = new Vector<String>();
        ITEM_INFO item;

        keys = m_items.keys();
        for (int i = 0; keys.hasMoreElements(); i++) {
            s = keys.nextElement();
            if ((item = m_items.get(s)) != null) {
                if (item.pID.equals(pID))
                    list.add(item.Id);
            }
        }
        return list;
    };

    public int InsertRsc(String id, ITEM_INFO data, String pID) {
        // String s;
        // Enumeration<String> keys;
        int ret = -1;

        // System.out.println("m_ids] id : "+id+", type : "+type);

        if (IsExistID(screen, id))
            return ret;

        Hashtable<String, OspForm> scenes = m_manager.m_Form.get(screen);
        if (scenes == null)
            return ret;
        OspForm scene = scenes.get(m_infoScene.Id);
        if (scene == null)
            return ret;

        scene.m_items.put(id, data);
        scene.m_Markup.insertChildId(pID, id);
        ret = 1;

        if (data instanceof SCROLLPANEL_INFO && 
            ((SCROLLPANEL_INFO) data).panelId != null && !((SCROLLPANEL_INFO) data).panelId.isEmpty()) {
            Hashtable<String, OspPanel> panels = m_manager.m_Panels.get(screen);
            if (panels != null) {
                OspPanel panel = panels.get(((SCROLLPANEL_INFO) data).panelId);
                if (panel != null) {
                    panel.m_infoPanel.addParentId(data.Id);
                }
            }
        }

        setDirty(true);
        return ret;
    };

    public boolean IsExistID(String screenSize, String id) {
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view == null)
            return false;

        if (m_manager != null && m_manager.m_Form != null) {
            Hashtable<String, OspForm> scenes = m_manager.m_Form.get(screen);
            if (scenes != null && scenes.get(id) != null)
                return true;
        }

        if (m_manager != null && m_manager.m_Panels != null) {
            Hashtable<String, OspPanel> panels = m_manager.m_Panels.get(screen);
            if(panels != null && panels.get(id) != null)
                return true;
        }

        if (m_manager != null && m_manager.m_Popup != null && m_manager.m_Popup.get(screenSize) != null) {
            if (m_manager.m_Popup.get(screenSize).get(id) != null)
                return true;
        }

        if (m_items.get(id) != null || id.equals(m_infoScene.Id))
            return true;
        else {
            if (Display.getCurrent() != null) {
                // Shell shell = Display.getCurrent().getActiveShell();
                // MessageDialog.openError(shell, "IsExistID",
                // ID+" is exisit!");
            }
            return false;
        }
    };

    public void DeleteRsc(String id) {
        ITEM_INFO item = m_items.get(id);
        if (item == null)
            return;

        item = m_items.get(id);
        if (item != null && item.pID != null && !item.pID.isEmpty()) {
        	m_Markup.removeChildId(item.pID, item.Id);

            if (item instanceof SCROLLPANEL_INFO) {
                Hashtable<String, OspPanel> panels = m_manager.m_Panels.get(screen);
                OspPanel panel = panels.get(((SCROLLPANEL_INFO) item).panelId);
                if (panel != null)
                    panel.m_infoPanel.removeParentId(item.Id);
            }
        }
        m_items.remove(id); // delete
        setDirty(true);
    };

    public static Vector<String> PutIDsInArray(String children) {
        Vector<String> ary = new Vector<String>();
        int j;

        if (children.isEmpty())
            return ary;
        while (children.length() > 1) {
            while (children.charAt(0) == ('|'))
                children = children.substring(1);
            // System.out.println("items : "+items);
            if (0 > (j = children.indexOf('|')))
                break;
            ary.add(children.substring(0, j));
            // System.out.println("items.substring(0, "+j+") : "+items.substring(0,
            // j));
            children = children.substring(j);
        }
        return ary;
    }

    public static String MakeIDListFromArray(Vector<String> ary) {
        String s, ret = "";
        int cnt = ary.size();

        for (int i = 0; i < cnt; i++) {
            s = String.format("|%s|", ary.get(i));
            ret += s;
        }
        return ret;
    };


	public Vector<String> getKeys() {
		return new Vector<String>(m_items.keySet());
	}

    public Vector<String> getIdList(String children) {
        Vector<String> ary = new Vector<String>();
        int j;

        if (children.isEmpty())
            return ary;
        while (children.length() > 1) {
            while (children.length() > 1 && children.charAt(0) == ('|'))
                children = children.substring(1);
            // System.out.println("items : "+items);
            if (0 > (j = children.indexOf('|')))
                break;
            ary.add(children.substring(0, j));
            // System.out.println("items.substring(0, "+j+") : "+items.substring(0,
            // j));
            children = children.substring(j);
        }
        return ary;
    }

    public void setIdList(ITEM_INFO info, Vector<String> ary) {
        StringBuilder s = new StringBuilder();
        int cnt = ary.size();

        for (int i = 0; i < cnt; i++) {
            if (i == 0)
                s.append("|");
            s.append(ary.get(i));
            s.append("|");
        }
        info.children = s.toString();
    };

    public void reorderIndex(int index, ITEM_INFO info, String name) {
        Vector<String> children = getIdList(info.children);

        if (index < 0 || index > children.size() - 1)
            return;

        if (!children.remove(name))
            return;

        children.add(index, name);
        setIdList(info, children);
    }

    public void reorderTop(ITEM_INFO info, String name) {
        Vector<String> children = getIdList(info.children);

        if (!children.remove(name))
            return;

        children.add(name);
        setIdList(info, children);
    }

    public void reorderBottom(ITEM_INFO info, String name) {
        Vector<String> children = getIdList(info.children);

        if (!children.remove(name))
            return;

        children.add(0, name);
        setIdList(info, children);
    }

    public void reorderToUp(ITEM_INFO info, String name) {
        Vector<String> children = getIdList(info.children);

        int index = children.indexOf(name);
        if (index < children.size() - 1) {
            if (!children.remove(name))
                return;

            children.add(index + 1, name);
            setIdList(info, children);
        }
    }

    public void reorderToDown(ITEM_INFO info, String name) {
        Vector<String> children = getIdList(info.children);

        int index = children.indexOf(name);
        if (index > 0) {
            if (!children.remove(name))
                return;

            children.add(index - 1, name);
            setIdList(info, children);
        }
    }

    public boolean ReplaceRscID(String oldID, String newID) {
        String key = screen;
        Hashtable<String, OspForm> scenes = m_manager.m_Form.get(key);
        if (scenes == null)
            return false;

        OspForm scene = scenes.get(m_infoScene.Id);
        Vector<String> ary;
        if (scene == null || scene.IsExistID(key, newID))
            return false;
        ITEM_INFO item = scene.m_items.get(oldID);
        if (item == null)
            return false;

        if (item.type == WINDOW_FORM) {
            ary = PutIDsInArray(item.children);
            for (int i = 0; i < ary.size(); i++) {
                ITEM_INFO chItem = scene.m_items.get(ary.elementAt(i));
                if (chItem == null)
                    continue;
                chItem.pID = newID;
                scene.m_items.remove(chItem.Id);
                scene.m_items.put(chItem.Id, chItem);
            }
            scenes.remove(oldID);
            scenes.put(newID, this);
            return true;
        } else if (item.type == WINDOW_PANEL || item.type == WINDOW_SCROLLPANEL || item.type == WINDOW_OVERLAYPANEL || item.type == WINDOW_POPUP) {

            ITEM_INFO form = scene.m_infoScene;
            ary = PutIDsInArray(form.children);
            for (int i = 0; i < ary.size(); i++) {
                ITEM_INFO chItem = scene.m_items.get(ary.elementAt(i));
                if (chItem == null)
                    continue;
                if (chItem.pID.equals(oldID)) {
                    chItem.pID = newID;
                    scene.m_items.remove(chItem.Id);
                    scene.m_items.put(chItem.Id, chItem);
                }
            }

            if (item instanceof SCROLLPANEL_INFO) {
                Hashtable<String, OspPanel> panels = scene.m_manager.m_Panels.get(screen);
                if (panels != null) {
                    OspPanel panel = panels.get(((SCROLLPANEL_INFO) item).panelId);
                    if (panel != null) {
                        panel.m_infoPanel.removeParentId(oldID);
                        panel.m_infoPanel.addParentId(newID);
                    }
                }
            }
        }
        item.Id = newID;
        scene.m_items.remove(oldID);
        scene.m_items.put(item.Id, item);
        scene.m_Markup.replaceChildId(item.pID, oldID, newID);

        setDirty(true);
        return true;
    };

    public boolean UpdateRsc(String id, ITEM_INFO data) {
        String key = screen;
        Hashtable<String, OspForm> scenes = m_manager.m_Form.get(key);
        if (scenes == null)
            return false;
        OspForm scene = scenes.get(m_infoScene.Id);
        ITEM_INFO item;
        if (scene == null)
            return false;
        if (scene.m_infoScene.Id.equals(id)) {
            item = scene.m_infoScene;
            data.updateCopy(item);
            setDirty(true);
            return true;
        }
        item = scene.m_items.get(id);
        if (item == null)
            return false;
        if (this.equals(scene)) {
            m_items.remove(id);
            m_items.put(id, data);
        } else {
            scene.m_items.remove(id);
            data.updateCopy(item);
            scene.m_items.put(id, item);
        }
        
        setDirty(true);
        return true;
    };

    public void PrintItemsInfo() {
        Enumeration<String> keys;
        String id;
        int pos = m_items.size();

        System.out.println("\n\rPrint All Items Information!");
        if (pos > 0) {
            StringBuilder s;
            keys = m_items.keys();
            for (int i = 0; i < pos; i++) {
                id = keys.nextElement();
                s = new StringBuilder("Key : ");
                s.append(id);
                s.append(", Parent ID : ");
                s.append(m_items.get(id).pID);
                System.out.println(s.toString());
            }
        }
    }

    public boolean isDirty() {
        return isDirty;
    };

    public void setDirty(boolean dirty) {
        boolean oldDirty = isDirty;
        isDirty = dirty;
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if (view != null && oldDirty != dirty)
            view.refreshTree();
    }

}
