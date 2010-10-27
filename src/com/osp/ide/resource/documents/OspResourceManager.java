/**
 * 
 */
package com.osp.ide.resource.documents;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.OspUIFile;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.resinfo.BUTTON_INFO;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

/**
 * @author SRD1_CHJ
 *
 */
public class OspResourceManager implements FrameConst {
    public static final int SCENETEMPLATE_COUNT = 3;
    public static final int SCENETEMPLATE_EMPTY = 0;
    public static final int SCENETEMPLATE_STANDARD = 1;
    public static final int SCENETEMPLATE_FORMBASEDAPP = 2;

    public static final String[] sceneDescription = { "Empty Scene",
            "Standard Scene" };

    public Hashtable<String, Hashtable<String, OspForm>> m_Form;
    public Hashtable<String, Hashtable<String, OspPopup>> m_Popup;
    public Hashtable<String, Hashtable<String, OspPanel>> m_Panels;
    public String m_project;

    private OspUIFile m_myFile;
    private String m_projectDirectory;
    protected String m_csError;
    private Hashtable<String, Vector<String>> m_delFormFile;
    private Hashtable<String, Vector<String>> m_insertFormFile;
    private Hashtable<String, Vector<String>> m_delPopupFile;
    private Hashtable<String, Vector<String>> m_insertPopupFile;
    private Hashtable<String, Vector<String>> m_delPanel;
    private Hashtable<String, Vector<String>> m_insertPanel;

    public OspResourceManager(String project) {
        init(project);
    };

    public OspResourceManager(String rootDir, String project) {
        m_projectDirectory = Path.fromPortableString(rootDir).toOSString();
        init(project);
    };

    /**
     * @param project
     */
    private void init(String project) {
        m_myFile = new OspUIFile();
        m_project = project;
        m_Form = new Hashtable<String, Hashtable<String, OspForm>>();
        m_Popup = new Hashtable<String, Hashtable<String, OspPopup>>();
        m_Panels = new Hashtable<String, Hashtable<String, OspPanel>>();
        m_delFormFile = new Hashtable<String, Vector<String>>();
        m_insertFormFile = new Hashtable<String, Vector<String>>();
        m_delPopupFile = new Hashtable<String, Vector<String>>();
        m_insertPopupFile = new Hashtable<String, Vector<String>>();
        m_delPanel = new Hashtable<String, Vector<String>>();
        m_insertPanel = new Hashtable<String, Vector<String>>();
        Vector<String> screens = Activator.getStringScreen(project);
        for (int i = 0; i < screens.size(); i++) {
            Vector<String> delFormFile = new Vector<String>();
            m_delFormFile.put(screens.get(i), delFormFile);
            Vector<String> insertFormFile = new Vector<String>();
            m_insertFormFile.put(screens.get(i), insertFormFile);
            Vector<String> delPopupFile = new Vector<String>();
            m_delPopupFile.put(screens.get(i), delPopupFile);
            Vector<String> insertPopupFile = new Vector<String>();
            m_insertPopupFile.put(screens.get(i), insertPopupFile);
            Vector<String> delPanel = new Vector<String>();
            m_delPanel.put(screens.get(i), delPanel);
            Vector<String> insertPanel = new Vector<String>();
            m_insertPanel.put(screens.get(i), insertPanel);
        }
    }

    public String getProjectDirectory() {
        return m_projectDirectory;
    }

    boolean LoadXML(String lpszPathName) {
        boolean ret = true;
        if (lpszPathName != null && !lpszPathName.isEmpty()) {
            m_projectDirectory = Path.fromPortableString(lpszPathName).toOSString();
        } else
            return false;

        StringBuilder s;
        Vector<String> point = Activator.getStringScreen(m_project);
        for (int n = 0; n < point.size(); n++) {
            s = new StringBuilder(m_projectDirectory);
            s.append(point.get(n));
            String dir = s.toString();
            File projectDir = new File(dir);
            loadFolder(projectDir, point.get(n));
        }

        return ret;
    };
    
    /**
     * @param files
     * @return
     */
    private void loadFolder(File projectDir, String screen) {
        File[] files = projectDir.listFiles();
        Hashtable<String, OspForm> scenes = new Hashtable<String, OspForm>();
        Hashtable<String, OspPopup> popups = new Hashtable<String, OspPopup>();
        Hashtable<String, OspPanel> panels = new Hashtable<String, OspPanel>();

        m_Form.remove(screen);
        m_Form.put(screen, scenes);
        m_Popup.remove(screen);
        m_Popup.put(screen, popups);
        m_Panels.remove(screen);
        m_Panels.put(screen, panels);
        
        for (int i = 0; files != null && i < files.length; i++) {
            File file = files[i];
            String ext;
            if (file.isFile())
                ext = file.getName().substring(
                        file.getName().indexOf('.') + 1);
            else
                continue;

            if (ext.equals("xml")) {
                if(OspFormMarkup.isFormXML(m_project, file)) {
                    // TODO first parameter not null
                    OspForm scene = new OspForm(this, screen,
                            m_projectDirectory, file.getAbsolutePath());
    
                    if (scene.m_Markup.loadXML(file.getAbsolutePath())) {
                        scenes.put(scene.m_infoScene.Id, scene);
                    }
                } else if(OspPopupMarkup.isPopupXML(m_project, file)) {
                    // TODO first parameter not null
                    OspPopup popup = new OspPopup(this, screen);
                    popup.m_infoPopup.headPath = m_projectDirectory;
                    popup.m_infoPopup.fileName = file.getAbsolutePath();

                    if (popup.m_Markup.loadXML(file.getAbsolutePath())) {
                        popups.put(popup.m_infoPopup.Id, popup);
                    }
                }
            }
        }
        
        Vector<String> delFormFile = m_delFormFile.get(screen);
        if (delFormFile != null)
            delFormFile.removeAllElements();
        Vector<String> insertFormFile = m_insertFormFile.get(screen);
        if (insertFormFile != null)
            insertFormFile.removeAllElements();
        Vector<String> delPopupFile = m_delPopupFile.get(screen);
        if (delPopupFile != null)
            delPopupFile.removeAllElements();
        Vector<String> insertPopupFile = m_insertPopupFile.get(screen);
        if (insertPopupFile != null)
            insertPopupFile.removeAllElements();
        Vector<String> delPanel = m_delPanel.get(screen);
        if(delPanel != null)
            delPanel.removeAllElements();
        Vector<String> insertPanel = m_insertPanel.get(screen);
        if(insertPanel != null)
            insertPanel.removeAllElements();
        setPanelDirty(screen, false);
    }
    
    public boolean loadFolder(String screen) {
        StringBuilder s = new StringBuilder(m_projectDirectory);
        s.append(screen);
        String dir = s.toString();
        File projectDir = new File(dir);
        loadFolder(projectDir, screen);
        return true;
    }
    
    public boolean AddScene(String screen, String path) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return false;

        File file = new File(path);
        String ext, id;
        if (file.isFile()) {
            String name = file.getName();
            ext = name.substring(name.indexOf('.') + 1);
            id = name.substring(0, name.indexOf('.'));
            if(scenes.get(id) != null)
                return false;
        } else
            return false;

        if (ext.equals("xml")) {
            // TODO first parameter not null
            OspForm scene = new OspForm(this, screen, m_projectDirectory, file
                    .getAbsolutePath());

            if (scene.m_Markup.loadXML(file.getAbsolutePath())) {
                if (scenes.get(scene.m_infoScene.Id) != null)
                    return false;
                scenes.put(scene.m_infoScene.Id, scene);
            }
        }
        m_Form.put(screen, scenes);
        return true;
    }

    public boolean AddPopup(String screen, String path) {
        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        if(popups == null)
            return false;
        File file = new File(path);
        String ext, id;
        if (file.isFile()) {
            String name = file.getName();
            ext = name.substring(name.indexOf('.') + 1);
            id = name.substring(0, name.indexOf('.'));
            if(popups.get(id) != null)
                return false;
        } else
            return false;

        if (ext.equals("xml")) {
            // TODO first parameter not null
            OspPopup popup = new OspPopup(this, screen);
            popup.m_infoPopup.headPath = m_projectDirectory;
            popup.m_infoPopup.fileName = file.getAbsolutePath();

            if (popup.m_Markup.loadXML(file.getAbsolutePath())) {
                if(popups.get(popup.m_infoPopup.Id) != null)
                    return false;
                popups.put(popup.m_infoPopup.Id, popup);
            }
        }

        m_Popup.put(screen, popups);
        return true;
    }

    public boolean OpenFrames(String pathName) {
        if (pathName.isEmpty())
            return false;
        if (!m_myFile.IsFileExist(pathName))
            return false;

        if (!LoadXML(pathName)) {
            System.out.println("Failed to load Scene file : " + pathName);
            return false;
        }

        return true;
    };

    public String MakeFrameID(String screen) {
        int i = 1;
        String s;
        Hashtable<String, OspForm> scenes = m_Form.get(screen);

        do {
            s = String.format("IDF_%s%d", cszCtlType[WINDOW_FORM], i++);
        } while (scenes != null && (scenes.get(s)) != null);

        return s;
    };

    public String MakePopupID(String screen) {
        int i = 1;
        String s;
        Hashtable<String, OspPopup> popups = m_Popup.get(screen);

        do {
            s = String.format("IDP_%s%d", cszCtlType[WINDOW_POPUP], i++);
        } while (popups != null && (popups.get(s)) != null);

        return s;
    };

    public String MakePanelID(String screen) {
        int i = 1;
        String s;
        
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);

        do {
            s = String.format("IDN_%s%d", cszCtlType[WINDOW_PANEL], i++);
        } while (m_Panels != null && (panels.get(s)) != null);

        return s;
    };

    public void DeleteScene(String screen, String id) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return;
        OspForm scene = scenes.get(id);
        if (scene == null)
            return;

        scene = scenes.remove(id);
        Enumeration<String> keys = scene.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = scene.m_items.get(keys.nextElement());
            if (item instanceof SCROLLPANEL_INFO) {
                String panelId = ((SCROLLPANEL_INFO) item).panelId;
                if (panelId == null)
                    continue;
                Hashtable<String, OspPanel> panels = m_Panels.get(screen);
                if(panels == null)
                    continue;
                OspPanel panel = panels.get(panelId);
                if (panel != null)
                    panel.m_infoPanel.removeParentId(item.Id);
            }
        }

        addDelSceneFile(screen, scene.m_infoScene.fileName);
    };

    public void DeletedScene(String screen, String id) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return;
        OspForm scene = scenes.get(id);
        if (scene == null)
            return;

        scene = scenes.remove(id);
        Enumeration<String> keys = scene.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = scene.m_items.get(keys.nextElement());
            if (item instanceof SCROLLPANEL_INFO) {
                String panelId = ((SCROLLPANEL_INFO) item).panelId;
                if (panelId == null)
                    continue;
                Hashtable<String, OspPanel> panels = m_Panels.get(screen);
                if(panels == null)
                    continue;
                OspPanel panel = panels.get(panelId);
                if (panel != null)
                    panel.m_infoPanel.removeParentId(item.Id);
            }
        }

        File file = new File(scene.m_infoScene.fileName);
        if (file.exists())
            file.delete();
    };

    public void DeletePopup(String screen, String id) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        OspPopup popup = popupes.get(id);
        if (popup == null)
            return;

        popup = popupes.remove(id);
        Enumeration<String> keys = popup.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = popup.m_items.get(keys.nextElement());
            if (item instanceof SCROLLPANEL_INFO) {
                String panelId = ((SCROLLPANEL_INFO) item).panelId;
                if (panelId == null)
                    continue;
                Hashtable<String, OspPanel> panels = m_Panels.get(screen);
                if(panels == null)
                    continue;
                OspPanel panel = panels.get(panelId);
                if (panel != null)
                    panel.m_infoPanel.removeParentId(item.Id);
            }
        }

        addDelPopupFile(screen, popup.m_infoPopup.fileName);
    };

    public void DeletedPopup(String screen, String id) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        if(popupes == null)
            return;
        OspPopup popup = popupes.get(id);
        if (popup == null)
            return;

        popup = popupes.remove(id);
        Enumeration<String> keys = popup.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = popup.m_items.get(keys.nextElement());
            if (item instanceof SCROLLPANEL_INFO) {
                String panelId = ((SCROLLPANEL_INFO) item).panelId;
                if (panelId == null)
                    continue;
                Hashtable<String, OspPanel> panels = m_Panels.get(screen);
                if(panels == null)
                    continue;
                OspPanel panel = panels.get(panelId);
                if (panel != null)
                    panel.m_infoPanel.removeParentId(item.Id);
            }
        }

        File file = new File(popup.m_infoPopup.fileName);
        if (file.exists())
            file.delete();
    };

    public void DeletePanel(String screen, String id) {
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if(panels == null)
            return;
        OspPanel panel = panels.get(id);
        if (panel == null)
            return;

        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        Enumeration<String> formKeys = scenes.keys();
        while (formKeys.hasMoreElements()) {
            OspForm form = scenes.get(formKeys.nextElement());
            if(form == null)
                continue;
            
            Enumeration<String> itemKeys = form.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                ITEM_INFO item = form.m_items.get(itemKeys.nextElement());
                if(item == null)
                    continue;
                
                if(item instanceof SCROLLPANEL_INFO && 
                    ((SCROLLPANEL_INFO)item).panelId.equals(panel.m_infoPanel.Id)) {
                    ((SCROLLPANEL_INFO)item).bgColor = DEFAULT_COLOR;
                    if(isScotia(screen))
                        ((SCROLLPANEL_INFO)item).bgColorOpacity = 100;
                    else
                    	((SCROLLPANEL_INFO)item).bgColorOpacity = 0;
                }
            }
        }
        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        Enumeration<String> popupKeys = popups.keys();
        while (formKeys.hasMoreElements()) {
            OspPopup popup = popups.get(popupKeys.nextElement());
            if(popup == null)
                continue;
            
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                ITEM_INFO item = popup.m_items.get(itemKeys.nextElement());
                if(item == null)
                    continue;
                
                if(item instanceof SCROLLPANEL_INFO && 
                    ((SCROLLPANEL_INFO)item).panelId.equals(panel.m_infoPanel.Id)) {
                    ((SCROLLPANEL_INFO)item).bgColor = DEFAULT_COLOR;
                    if(isScotia(screen))
                    	((SCROLLPANEL_INFO)item).bgColorOpacity = 100;
                    else
                    	((SCROLLPANEL_INFO)item).bgColorOpacity = 0;
                }
            }
        }
        OspPanelEditor.refreshFrameEditor();
        
        panels.remove(id);
        addDelPanel(screen, id);
    };

    public int getFormCount() {

        Vector<String> point = Activator.getStringScreen(m_project);
        if (point == null || point.size() == 0)
            return 0;
        Hashtable<String, OspForm> scenes = m_Form.get(point.get(0));
        if (scenes == null)
            return 0;
        return scenes.size();
    }

    public boolean RenameScene(String screen, String oldId, String newId) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return false;
        OspForm scene = scenes.get(oldId);
        if (scenes.get(newId) != null || scene == null
                || scene.IsExistID(screen, newId))
            return false;

        scenes.remove(oldId);

        Enumeration<String> keys = scene.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = scene.m_items.get(keys.nextElement());
            if (item.pID.equals(oldId))
                item.pID = newId;
        }

        scene.m_infoScene.Id = newId;

        addDelSceneFile(screen, scene.m_infoScene.fileName);

        scene.m_infoScene.fileName = scene.m_infoScene.fileName.replace(oldId,
                newId);
        scene.m_Markup.setFileName(scene.m_infoScene.fileName);

        addInsertSceneFile(screen, scene.m_infoScene.fileName);

        scene.setDirty(true);
        scenes.put(newId, scene);
        return true;
    }

    public boolean RenamedScene(String screen, String oldId, String newId) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return false;
        OspForm scene = scenes.get(oldId);
        if (scenes.get(newId) != null || scene == null
                || scene.IsExistID(screen, newId))
            return false;

        scenes.remove(oldId);

        Enumeration<String> keys = scene.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = scene.m_items.get(keys.nextElement());
            if (item.pID.equals(oldId))
                item.pID = newId;
        }

        scene.m_infoScene.Id = newId;
        scene.m_infoScene.fileName = scene.m_infoScene.fileName.replace(oldId,
                newId);
        scene.m_Markup.setFileName(scene.m_infoScene.fileName);
        scenes.put(newId, scene);
        return true;
    }

    public boolean RenamePopup(String screen, String oldId, String newId) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        if(popupes == null)
            return false;
        
        OspPopup popup = popupes.get(oldId);
        if (popupes.get(newId) != null || popup == null || popup.IsExistID(screen, newId))
            return false;

        popupes.remove(oldId);
        
        Enumeration<String> keys = popup.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = popup.m_items.get(keys.nextElement());
            if (item.pID.equals(oldId))
                item.pID = newId;
        }

        popup.m_infoPopup.Id = newId;
        
        addDelPopupFile(screen, popup.m_infoPopup.fileName);
        
        popup.m_infoPopup.fileName = popup.m_infoPopup.fileName.replace(
                oldId, newId);
        popup.m_Markup.setFileName(popup.m_infoPopup.fileName);
        
        addInsertPopupFile(screen, popup.m_infoPopup.fileName);
        
        popup.setDirty(true);
        popupes.put(newId, popup);

        return true;
    }

    public boolean RenamedPopup(String screen, String oldId, String newId) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        if(popupes == null)
            return false;
        
        OspPopup popup = popupes.get(oldId);
        if (popupes.get(newId) != null || popup == null || popup.IsExistID(screen, newId))
            return false;

        popupes.remove(oldId);
        
        Enumeration<String> keys = popup.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = popup.m_items.get(keys.nextElement());
            if (item.pID.equals(oldId))
                item.pID = newId;
        }

        popup.m_infoPopup.Id = newId;
        popup.m_infoPopup.fileName = popup.m_infoPopup.fileName.replace(
                oldId, newId);
        popup.m_Markup.setFileName(popup.m_infoPopup.fileName);
        popupes.put(newId, popup);

        return true;
    }

    public boolean IsExistPanelID(String screen, String id) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes != null && scenes.get(id) != null)
            return true;

        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        if (popups != null && popups.get(id) != null)
            return true;

        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if (panels != null && panels.get(id) != null)
            return true;

        return false;
    };

    public boolean RenamePanel(String screen, String oldId, String newId) {
        if (IsExistPanelID(screen, newId))
            return false;
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if(panels == null)
            return false;
        OspPanel panel = panels.get(oldId);
        if (panel == null || panel.IsExistID(newId))
            return false;

        panels.remove(oldId);
        addDelPanel(screen, oldId);
        Enumeration<String> keys = panel.m_items.keys();
        while (keys.hasMoreElements()) {
            ITEM_INFO item = panel.m_items.get(keys.nextElement());
            if (item.pID.equals(oldId))
                item.pID = newId;
        }

        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if(scenes != null) {
            Enumeration<String> sceneKeys = scenes.keys();
            while (sceneKeys.hasMoreElements()) {
                String sceneKey = sceneKeys.nextElement();
                OspForm scene = scenes.get(sceneKey);
                Enumeration<String> itemKeys = scene.m_items.keys();
                while (itemKeys.hasMoreElements()) {
                    String itemKey = itemKeys.nextElement();
                    ITEM_INFO item = scene.m_items.get(itemKey);
                    if (item instanceof SCROLLPANEL_INFO
                            && ((SCROLLPANEL_INFO) item).panelId.equals(oldId))
                        ((SCROLLPANEL_INFO) item).panelId = newId;
                }
            }
        }

        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        if(popups != null) {
            Enumeration<String> sceneKeys = popups.keys();
            while (sceneKeys.hasMoreElements()) {
                String sceneKey = sceneKeys.nextElement();
                OspPopup scene = popups.get(sceneKey);
                Enumeration<String> itemKeys = scene.m_items.keys();
                while (itemKeys.hasMoreElements()) {
                    String itemKey = itemKeys.nextElement();
                    ITEM_INFO item = scene.m_items.get(itemKey);
                    if (item instanceof SCROLLPANEL_INFO
                            && ((SCROLLPANEL_INFO) item).panelId.equals(oldId))
                        ((SCROLLPANEL_INFO) item).panelId = newId;
                }
            }
        }

        panel.m_infoPanel.Id = newId;
        panel.setDirty(true);
        panels.put(newId, panel);
        addInsertPanel(screen, newId);
        return true;
    }

    void insertPanelRsc(String screen, String panelId, ITEM_INFO info) {
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if(panels == null)
            return;
        OspPanel panel = panels.get(panelId);
        if (panel != null && panel.m_items.get(info.Id) == null) {
            panel.InsertRsc(info.Id, info, panelId);
        }
    }

    ITEM_INFO getParentInfo(OspForm scene, String parent) {
    	if(scene.m_infoScene.Id.equals(parent))
    		return scene.m_infoScene;
    	
        return scene.m_items.get(parent);
    }

    int getParentType(OspForm scene, String parent) {
        ITEM_INFO parentInfo = scene.m_items.get(parent);
        if (parentInfo == null)
            return WINDOW_FORM;
        return parentInfo.type;
    }

    public static int GetTagIndex(String s) {
        for (int i = 0; i < CTL_TYPE_NUM; i++) {
            if (cszTag[i].equals(s))
                return i;
        }
        return 0;
    }

    public static int GetTypeIndex(String s) {
        for (int i = 0; i < CTL_TYPE_NUM; i++) {
            if (cszCtlType[i].equals(s))
                return i;
        }
        return 0;
    }

    public FORMFRAME_INFO InsertScene(String screen, int type) {
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return null;

        String id = MakeFrameID(screen);

        String fileName = m_projectDirectory + screen + java.io.File.separatorChar;
        File file = new File(fileName);
        if (!file.exists())
            file.mkdir();

        fileName += id + ".xml";
        // TODO first parameter not null
        OspForm scene = new OspForm(this, screen, m_projectDirectory, fileName);

        scene.m_infoScene.Id = id;
        scene.m_infoScene.type = WINDOW_FORM;

		if(isScotia(scene.getScreen()))
	        scene.m_infoScene.bgColorOpacity = 100;
		else
			scene.m_infoScene.bgColorOpacity = 0;
        scene.m_infoScene.hAlign = cszHAlign[ALIGN_CENTER];
        // scene.m_infoScene.softkeyStyle =
        // SOFTKEY_STYLE[SOFTKEY_STYLE_NORMAL];
        scene.m_Markup.setFileName(scene.m_infoScene.fileName);

        Layout layout = null;
        layout = new Layout();
        layout.mode = cszFrmMode[PORTRAIT];
        layout.style = cszStyle[STYLE_FORM][1] + "|" + cszStyle[STYLE_FORM][2]
                + "|";
        layout.x = 0;
        layout.y = 0;
        layout.width = Activator.getSScreenToPoint(screen).x;
        layout.height = Activator.getSScreenToPoint(screen).y;
        scene.m_infoScene.SetLayout(layout);
        layout = new Layout();
        layout.mode = cszFrmMode[LANDCAPE];
        layout.style = cszStyle[STYLE_FORM][1] + "|" + cszStyle[STYLE_FORM][2]
                + "|";
        layout.x = 0;
        layout.y = 0;
        layout.width = Activator.getSScreenToPoint(screen).y;
        layout.height = Activator.getSScreenToPoint(screen).x;
        scene.m_infoScene.SetLayout(layout);
        scene.setDirty(true);
        scenes.put(scene.m_infoScene.Id, scene);
        switch (type) {
        case SCENETEMPLATE_EMPTY:
            break;
        case SCENETEMPLATE_STANDARD:
            layout = scene.m_infoScene.GetLayout(PORTRAIT);
            layout.style = cszStyle[STYLE_FORM][1] + "|"
                    + cszStyle[STYLE_FORM][2] + "|" + cszStyle[STYLE_FORM][5]
                    + "|";
            scene.m_infoScene.SetLayout(layout);
            layout = scene.m_infoScene.GetLayout(LANDCAPE);
            layout.style = cszStyle[STYLE_FORM][1] + "|"
                    + cszStyle[STYLE_FORM][2] + "|" + cszStyle[STYLE_FORM][5]
                    + "|";
            scene.m_infoScene.SetLayout(layout);

            scene.m_infoScene.title = "Title";
            scene.m_infoScene.softkey0 = "";
            scene.m_infoScene.softkey1 = "";
            break;
        case SCENETEMPLATE_FORMBASEDAPP:
            layout = scene.m_infoScene.GetLayout(PORTRAIT);
            layout.style = cszStyle[STYLE_FORM][1] + "|"
                    + cszStyle[STYLE_FORM][2];
            scene.m_infoScene.SetLayout(layout);
            layout = scene.m_infoScene.GetLayout(LANDCAPE);
            layout.style = cszStyle[STYLE_FORM][1] + "|"
                    + cszStyle[STYLE_FORM][2];
            scene.m_infoScene.SetLayout(layout);
            scene.m_infoScene.title = "Hello bada!";

            BUTTON_INFO data = new BUTTON_INFO();
            data.Id = "IDC_BUTTON_OK";
            data.type = WINDOW_BUTTON;
            data.pID = id;
            data.text = "OK";
            data.hAlign = cszHAlign[1];
            data.vAlign = cszVAlign[1];
            layout = new Layout();
            layout.mode = cszFrmMode[PORTRAIT];
            layout.x = 120;
            layout.y = 236 + getMinY(scene.m_infoScene, layout.mode);
            layout.width = 241;
            layout.height = 91;
            data.SetLayout(layout);
            layout = new Layout();
            layout.mode = cszFrmMode[LANDCAPE];
            layout.x = 200;
            layout.y = 108 + getMinY(scene.m_infoScene, layout.mode);
            layout.width = 401;
            layout.height = 42;
            data.SetLayout(layout);
            scene.InsertRsc(data.Id, data, id);
            break;
        }
        // NewXML(m_projectDirectory, scene.m_infoScene.Id);
        // NewXML(scene.m_infoScene.fileName);

        addInsertSceneFile(screen, scene.m_infoScene.fileName);
        return m_Form.get(screen).get(id).m_infoScene;
    }
    
    public static boolean isScotia(String screen) {
		return screen  != null && screen.toUpperCase(Locale.getDefault()).equals(ResourceExplorer.WQVGA);
	}

	public POPUPFRAME_INFO InsertPopup(String screen, int type) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        if(popupes == null)
            return null;
        
        String id = MakePopupID(screen);
        // TODO first parameter not null
        OspPopup popup = new OspPopup(this, screen);

        popup.m_infoPopup.Id = id;
        popup.m_infoPopup.type = WINDOW_POPUP;
        popup.m_infoPopup.headPath = m_projectDirectory;
        popup.m_infoPopup.fileName = m_projectDirectory + screen + "/";
        
        File file = new File(popup.m_infoPopup.fileName);
        if (!file.exists())
            file.mkdir();
        
        popup.m_infoPopup.fileName += popup.m_infoPopup.Id + ".xml";
        popup.m_Markup.setFileName(popup.m_infoPopup.fileName);

        Layout layout = null;
        layout = new Layout();
        layout.mode = cszFrmMode[PORTRAIT];
        layout.x = 0;
        layout.y = 0;
        layout.width = 400;
        layout.height = 300;
        popup.m_infoPopup.SetLayout(layout);
        layout = new Layout();
        layout.mode = cszFrmMode[LANDCAPE];
        layout.x = 0;
        layout.y = 0;
        layout.width = 400;
        layout.height = 300;
        popup.m_infoPopup.SetLayout(layout);
        // NewXML(m_projectDirectory, scene.m_infoScene.Id);
        // NewXML(scene.m_infoScene.fileName);
        popup.setDirty(true);
        popupes.put(popup.m_infoPopup.Id, popup);

        addInsertPopupFile(screen, popup.m_infoPopup.fileName);
        return m_Popup.get(screen).get(id).m_infoPopup;
    }

    public PANELFRAME_INFO InsertPanel(String screen, ITEM_INFO form) {
        String id = MakePanelID(screen);
        org.eclipse.swt.graphics.Point size = Activator.getSScreenToPoint(screen);

        // TODO first parameter not null
        OspPanel panel = new OspPanel(this, screen);

        panel.m_infoPanel.Id = id;
        panel.m_infoPanel.type = WINDOW_SCROLLPANEL;
        if(isScotia(screen))
        	panel.m_infoPanel.bgColorOpacity = 100;
        else
        	panel.m_infoPanel.bgColorOpacity = 0;
        
        if(form != null && form instanceof FORMFRAME_INFO)
            panel.m_infoPanel.formColor = ((FORMFRAME_INFO)form).bg;
        else
            panel.m_infoPanel.formColor = OspResourceManager.ColorToString(ColorConstants.black);

        Layout layout = null;
        layout = new Layout();
        layout.mode = cszFrmMode[PORTRAIT];
        layout.x = 0;
        layout.y = 0;
        layout.width = size.x;
        layout.height = size.y;
        panel.m_infoPanel.SetLayout(layout);
        layout = new Layout();
        layout.mode = cszFrmMode[LANDCAPE];
        layout.x = 0;
        layout.y = 0;
        layout.width = size.y;
        layout.height = size.x;
        panel.m_infoPanel.SetLayout(layout);
        panel.setDirty(true);
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if(panels == null) {
            panels = new Hashtable<String, OspPanel>();
            m_Panels.put(screen, panels);
        }
        if(panels.get(panel.m_infoPanel.Id) != null)
            return panels.get(panel.m_infoPanel.Id).m_infoPanel;
            
        panels.put(panel.m_infoPanel.Id, panel);
        
        setPanelParent(panel);

        addInsertPanel(screen, panel.m_infoPanel.Id);
        return panel.m_infoPanel;
    }

    public PANELFRAME_INFO InsertPanel(
        SCROLLPANEL_INFO scrollpanelInfo, String screen) {

        org.eclipse.swt.graphics.Point size = Activator.getSScreenToPoint(screen);
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if(panels == null) {
            panels = new Hashtable<String, OspPanel>();
            m_Panels.put(screen, panels);
        }
        OspPanel panel;
        if ((panel = panels.get(scrollpanelInfo.panelId)) != null)
            return panel.m_infoPanel;
        // TODO first parameter not null
        panel = new OspPanel(this, screen);
    
        panel.m_infoPanel.Id = scrollpanelInfo.panelId;
        panel.m_infoPanel.type = WINDOW_SCROLLPANEL;
    
        Layout origin = scrollpanelInfo.panelLayout.get(cszFrmMode[PORTRAIT]);
        if (origin == null)
            origin = new Layout();
        Layout layout = origin.copy();
        layout.mode = cszFrmMode[PORTRAIT];
        layout.x = 0;
        layout.y = 0;
        if (layout.width == 0)
            layout.width = size.x;
        if (layout.height == 0)
            layout.height = size.y;
        panel.m_infoPanel.SetLayout(layout);
        origin = scrollpanelInfo.panelLayout.get(cszFrmMode[LANDCAPE]);
        if (origin == null)
            origin = new Layout();
        layout = origin.copy();
        layout.mode = cszFrmMode[LANDCAPE];
        layout.x = 0;
        layout.y = 0;
        if (layout.width == 0)
            layout.width = size.y;
        if (layout.height == 0)
            layout.height = size.x;
        panel.m_infoPanel.SetLayout(layout);
        panels.put(panel.m_infoPanel.Id, panel);
    
        return panel.m_infoPanel;
    }

    public boolean addForm(OspForm newForm) {
        Hashtable<String, OspForm> tForms = m_Form.get(newForm.getScreen());
        if(tForms == null)
            return false;
        
        if(tForms.get(newForm.m_infoScene.Id) != null) {
            Shell shell = Display.getCurrent().getActiveShell();
            if (!MessageDialog.openQuestion(shell, "Add Form : " + newForm.m_infoScene.Id,
                    "Form already exists. Do you want to generate a new Form?"))
                return false;
        } else {
            addInsertSceneFile(newForm.getScreen(), newForm.m_infoScene.fileName);
        }

        tForms.remove(newForm.m_infoScene.Id);
        tForms.put(newForm.m_infoScene.Id, newForm);
        
        Enumeration<String> keys = newForm.m_items.keys();
        while(keys.hasMoreElements()) {
            ITEM_INFO item = newForm.m_items.get(keys.nextElement());
            if(item != null && item instanceof SCROLLPANEL_INFO) {
                Hashtable<String, OspPanel> panels = m_Panels.get(newForm.getScreen());
                if(panels == null)
                    continue;
                
                OspPanel panel = panels.get(((SCROLLPANEL_INFO)item).panelId);
                if(panel == null) {
                    ((SCROLLPANEL_INFO)item).bgColor = DEFAULT_COLOR;
                    if(isScotia(newForm.getScreen()))
                    	((SCROLLPANEL_INFO)item).bgColorOpacity = 100;
                    else
                    	((SCROLLPANEL_INFO)item).bgColorOpacity = 0;
                } else {
                    ((SCROLLPANEL_INFO)item).bgColor = panel.m_infoPanel.bgColor;
                    if(isScotia(newForm.getScreen()))
                    	panel.m_infoPanel.bgColorOpacity = 100;
                    
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = panel.m_infoPanel.bgColorOpacity;
                    panel.m_infoPanel.addParentId(item.Id);
                }
            }
        }
        return true;
    }

    public boolean addPopup(OspPopup newPopup) {
        Hashtable<String, OspPopup> tPopups = m_Popup.get(newPopup.screen);
        if(tPopups == null)
            return false;
        
        if(tPopups.get(newPopup.m_infoPopup.Id) != null) {
            Shell shell = Display.getCurrent().getActiveShell();
            if (!MessageDialog.openQuestion(shell, "Add Popup : " + newPopup.m_infoPopup.Id,
                    "Popup already exists. Do you want to generate a new Popup?"))
                return false;
        } else {
            addInsertPopupFile(newPopup.screen, newPopup.m_infoPopup.fileName);
        }

        tPopups.remove(newPopup.m_infoPopup.Id);
        tPopups.put(newPopup.m_infoPopup.Id, newPopup);
        
        Enumeration<String> keys = newPopup.m_items.keys();
        while(keys.hasMoreElements()) {
            ITEM_INFO item = newPopup.m_items.get(keys.nextElement());
            if(item != null && item instanceof SCROLLPANEL_INFO) {
                Hashtable<String, OspPanel> panels = m_Panels.get(newPopup.screen);
                if(panels == null)
                    continue;
                
                OspPanel panel = panels.get(((SCROLLPANEL_INFO)item).panelId);
                if(panel == null) {
                    ((SCROLLPANEL_INFO)item).bgColor = DEFAULT_COLOR;
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = 0;
                } else {
                    ((SCROLLPANEL_INFO)item).bgColor = panel.m_infoPanel.bgColor;
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = panel.m_infoPanel.bgColorOpacity;
                    panel.m_infoPanel.addParentId(item.Id);
                }
            }
        }
        return true;
    }

    public boolean addPanel(OspPanel newPanel) {
        Hashtable<String, OspPanel> tPanels = m_Panels.get(newPanel.getScreen());
        if(tPanels == null)
            return false;
        
        if(tPanels.get(newPanel.m_infoPanel.Id) != null) {
            Shell shell = Display.getCurrent().getActiveShell();
            if (!MessageDialog.openQuestion(shell, "Add Panel : " + newPanel.m_infoPanel.Id,
                    "Panel already exists. Do you want to generate a new Panel?"))
                return false;
        } else {
            addInsertPanel(newPanel.getScreen(), newPanel.m_infoPanel.Id);
        }

        tPanels.remove(newPanel.m_infoPanel.Id);
        tPanels.put(newPanel.m_infoPanel.Id, newPanel);

        setPanelParent(newPanel);
        return true;
    }

    private void addInsertSceneFile(String screen, String fileName) {

        IPath path = Path.fromPortableString(fileName);
        fileName = path.toOSString();

        Vector<String> insertFiles = m_insertFormFile.get(screen);
        Vector<String> delFiles = m_delFormFile.get(screen);
        if (insertFiles != null && !insertFiles.contains(fileName)) {
            if (delFiles != null && delFiles.contains(fileName))
                delFiles.remove(fileName);
            else
                insertFiles.add(fileName);
        }
    }

    private void addInsertPopupFile(String screen, String fileName) {
        
        IPath path = Path.fromPortableString(fileName);
        fileName = path.toOSString();
        
        Vector<String> insertFiles = m_insertPopupFile.get(screen);
        Vector<String> delFiles = m_delPopupFile.get(screen);
        if(insertFiles != null && !insertFiles.contains(fileName)) {
            if(delFiles != null && delFiles.contains(fileName))
                delFiles.remove(fileName);
            else
                insertFiles.add(fileName);
        }
    }

    private void addInsertPanel(String screen, String panelName) {

        Vector<String> insertPanels = m_insertPanel.get(screen);
        Vector<String> delPanels = m_delPanel.get(screen);
        if (insertPanels != null && !insertPanels.contains(panelName)) {
            if (delPanels != null && delPanels.contains(panelName))
                delPanels.remove(panelName);
            else
                insertPanels.add(panelName);
        }
    }

    private void addDelSceneFile(String screen, String fileName) {

        IPath path = Path.fromPortableString(fileName);
        fileName = path.toOSString();

        Vector<String> delFile = m_delFormFile.get(screen);
        Vector<String> insertFile = m_insertFormFile.get(screen);
        if (delFile != null && !delFile.contains(fileName)) {
            if (insertFile != null && insertFile.contains(fileName))
                insertFile.remove(fileName);
            else
                delFile.add(fileName);
        }
    }

    private void addDelPopupFile(String screen, String fileName) {
        
        IPath path = Path.fromPortableString(fileName);
        fileName = path.toOSString();
        
        Vector<String> delFile = m_delPopupFile.get(screen);
        Vector<String> insertFile = m_insertPopupFile.get(screen);
        if(delFile != null && !delFile.contains(fileName)) {
            if(insertFile != null && insertFile.contains(fileName))
                insertFile.remove(fileName);
            else
                delFile.add(fileName);
        }
    }

    private void addDelPanel(String screen, String panelName) {

        Vector<String> delPanels = m_delPanel.get(screen);
        Vector<String> insertPanels = m_insertPanel.get(screen);
        if (delPanels != null && !delPanels.contains(panelName)) {
            if (insertPanels != null && insertPanels.contains(panelName))
                insertPanels.remove(panelName);
            else
                delPanels.add(panelName);
        }
    }

    /**
     * @param panel
     */
    private void setPanelParent(OspPanel panel) {
        panel.m_infoPanel.removeAllParent();
        Hashtable<String, OspForm> scenes = m_Form.get(panel.screen);
        Enumeration<String> formKeys = scenes.keys();
        while (formKeys.hasMoreElements()) {
            OspForm form = scenes.get(formKeys.nextElement());
            if(form == null)
                continue;
            
            Enumeration<String> itemKeys = form.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                ITEM_INFO item = form.m_items.get(itemKeys.nextElement());
                if(item == null)
                    continue;
                
                if(item instanceof SCROLLPANEL_INFO && 
                    ((SCROLLPANEL_INFO)item).panelId.equals(panel.m_infoPanel.Id)) {
                    panel.m_infoPanel.addParentId(item.Id);
                    ((SCROLLPANEL_INFO)item).bgColor = panel.m_infoPanel.bgColor;
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = panel.m_infoPanel.bgColorOpacity;
                }
            }
        }
        Hashtable<String, OspPopup> popups = m_Popup.get(panel.screen);
        Enumeration<String> popupKeys = popups.keys();
        while (popupKeys.hasMoreElements()) {
            OspPopup popup = popups.get(popupKeys.nextElement());
            if(popup == null)
                continue;
            
            Enumeration<String> itemKeys = popup.m_items.keys();
            while(itemKeys.hasMoreElements()) {
                ITEM_INFO item = popup.m_items.get(itemKeys.nextElement());
                if(item == null)
                    continue;
                
                if(item instanceof SCROLLPANEL_INFO && 
                    ((SCROLLPANEL_INFO)item).panelId.equals(panel.m_infoPanel.Id)) {
                    panel.m_infoPanel.addParentId(item.Id);
                    ((SCROLLPANEL_INFO)item).bgColor = panel.m_infoPanel.bgColor;
                    ((SCROLLPANEL_INFO)item).bgColorOpacity = panel.m_infoPanel.bgColorOpacity;
                }
            }
        }
        OspPanelEditor.refreshFrameEditor();
    }

    private boolean isIndicatorStyle(ITEM_INFO item, String mode) {
        Layout layout = item.GetLayout(mode);
        if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0)
            return false;
        else
            return true;
    }

    private boolean isTitleStyle(ITEM_INFO item, String mode) {
        Layout layout = item.GetLayout(mode);
        if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 0)
            return false;
        else
            return true;
    }

    private boolean isTabStyle(ITEM_INFO item, String mode) {
        Layout layout = item.GetLayout(mode);
        if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 0
                && layout.style
                        .indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 0)
            return false;
        else
            return true;
    }
    
    int getPopupMinY(ITEM_INFO item, String mode) {
        int minY = 0;
        
        POPUPFRAME_INFO info = (POPUPFRAME_INFO) item;
        
        if(info.titleText != null && !info.titleText.isEmpty())
            minY = 69;
        
        return minY;
    }

    int getMinY(ITEM_INFO item, String mode) {
        int minY = 0;

        if(!(item instanceof FORMFRAME_INFO))
        	return minY;
        
        FORMFRAME_INFO info = (FORMFRAME_INFO) item;
        IPath path = Path.fromOSString(info.fileName);
        String screen = new File(path.toFile().getParent()).getName();

        Tag_info indicatorInfo = ResourceExplorer.getResourceView()
                .getImageInfo(FrameConst.cszTag[FrameConst.WINDOW_INDICATOR],
                        screen);

        int indicatorY;
        if (indicatorInfo == null)
            indicatorY = Form.INDICATOR_DEFAULT_HEIGHT;
        else
            indicatorY = indicatorInfo.dftlSize.y;

        Tag_info tabInfo = ResourceExplorer.getResourceView().getImageInfo(
                FrameConst.cszTag[FrameConst.WINDOW_TAB], screen);

        int tabY;
        Point tabMinSize;
        if (tabInfo == null) {
            tabY = Form.TAB_DEFAULT_HEIGHT;
            tabMinSize = new Point(Form.TAB_MIN_WIDTH,
                    Form.TAB_MIN_HEIGHT);
        } else {
            tabY = tabInfo.dftlSize.y;
            tabMinSize = tabInfo.minSize;
        }

        Tag_info titleInfo = ResourceExplorer.getResourceView().getImageInfo(
                FrameConst.cszTag[FrameConst.WINDOW_TITLE], screen);

        int titleY;
        Point titleMinSize;
        if (titleInfo == null) {
            titleY = Form.TITLE_DEFAULT_HEIGHT;
            titleMinSize = new Point(Form.TITLE_DEFAULT_WIDTH,
                    Form.TITLE_DEFAULT_HEIGHT);
        } else {
            titleY = titleInfo.dftlSize.y;
            titleMinSize = titleInfo.minSize;
        }

        if (isIndicatorStyle(item, mode)) {
            minY = indicatorY;
            if (isTabStyle(item, mode)) {
                minY = indicatorY - tabMinSize.y + tabY - tabMinSize.x;
                if (isTitleStyle(item, mode))
                    minY = indicatorY - tabMinSize.y + tabY - tabMinSize.x
                            - titleMinSize.y + titleY - titleMinSize.x;
            } else if ((isTitleStyle(item, mode)))
                minY = indicatorY - titleMinSize.y + titleY - titleMinSize.x;
        } else if (isTabStyle(item, mode)) {
            minY = tabY - tabMinSize.y - tabMinSize.x;
            if (isTitleStyle(item, mode))
                minY = tabY - tabMinSize.y - titleMinSize.y + titleY
                        - titleMinSize.x;
        } else if (isTitleStyle(item, mode)) {
            minY = titleY - titleMinSize.y - titleMinSize.x;
        }

        return minY;
    }

    void SaveXML(OspForm scene) {
        scene.m_Markup.storeXML();
        scene.setDirty(false);
        Vector<String> insertFile = m_insertFormFile.get(scene.getScreen());
        if (insertFile != null) {
            String fileName = scene.m_infoScene.fileName;
            IPath path = Path.fromPortableString(fileName);
            fileName = path.toOSString();
            insertFile.remove(fileName);
        }
    }

    void SaveXML(OspPopup popup) {
        popup.m_Markup.storeXML();
        popup.setDirty(false);
        Vector<String> insertFile = m_insertPopupFile.get(popup.screen);
        if(insertFile != null) {
            String fileName = popup.m_infoPopup.fileName;
            IPath path = Path.fromPortableString(fileName);
            fileName = path.toOSString();
            insertFile.remove(fileName);
        }
    }

    public void SaveFormXML(String screen, String id) {
        String key = screen;
        Hashtable<String, OspForm> scenes = m_Form.get(key);

        if (scenes != null && scenes.get(id) != null) {
            SaveXML(scenes.get(id));
        }
    }
    
    public void SavePopupXML(String screen, String id) {
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        
        if(popupes != null && popupes.get(id) != null) {
            SaveXML(popupes.get(id));
        }
    }
    
    public void SaveAllForm(boolean isWorning) {
        Enumeration<String> keys = m_Form.keys();
        while(keys.hasMoreElements())
            SaveAllForm(keys.nextElement(), isWorning);
    }

    public void SaveAllPopup(String screen) {
        Vector<String> delFile = m_delPopupFile.get(screen);
        while(delFile.size() > 0) {
            File file = new File(delFile.remove(0));
            if(file != null && file.exists())
                file.delete();
        }
        
        Hashtable<String, OspPopup> popupes = m_Popup.get(screen);
        Enumeration<String> keys2 = popupes.keys();
        while (keys2.hasMoreElements())
            SaveXML(popupes.get(keys2.nextElement()));
    };

    public void SaveAllForm(String screen, boolean isWorning) {

        Vector<String> delFile = m_delFormFile.get(screen);
        while (delFile.size() > 0) {
            File file = new File(delFile.remove(0));
            if (file != null && file.exists())
                file.delete();
        }

        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        Enumeration<String> keys2 = scenes.keys();
        while (keys2.hasMoreElements())
            SaveXML(scenes.get(keys2.nextElement()));

        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if (panels == null)
            return;
        Enumeration<String> panelKeys = panels.keys();
        while(panelKeys.hasMoreElements()) {
            OspPanel panel = panels.get(panelKeys.nextElement());
            if(panel == null)
                continue;
            if (panel != null && panel.m_infoPanel.parent.size() > 0) {
                panel.setDirty(false);
            } else if(panel != null && panel.m_infoPanel.parent.size() <= 0 && isWorning){
            	if(Display.getCurrent() != null) {
	                if(MessageDialog.openQuestion(Display.getCurrent().getActiveShell(), 
	                    "Save", "ScrollPanel [" + screen + "]: " + panel.GetPanelInfo().Id + 
	                    " needs to be assigned a valid ScrollPanel ID or else it will be discarded. Click Yes to discard the ScrollPanel.")) {
	                    panels.remove(panel.GetPanelInfo().Id);
	                    ResourceExplorer.closePanelEditor(screen, panel.GetPanelInfo().Id);
	                }
            	}
            }
        }

        Vector<String> delPanel = m_delPanel.get(screen);
        if(delPanel != null)
            delPanel.removeAllElements();

        Vector<String> insertPanel = m_insertPanel.get(screen);
        if(insertPanel != null) {
            for(int i=insertPanel.size(); i>0; i--) {
                String panelId = insertPanel.get(i-1);
                OspPanel panel = panels.get(panelId);
                if(panel == null || !panel.isDirty())
                    insertPanel.remove(i-1);
            }
        }
    };

    public static String ColorToString(Color color) {
        String sRet = DEFAULT_COLOR;
        if (color != null)
            sRet = String.format("#%02X%02X%02X", color.getRed(), color
                    .getGreen(), color.getBlue());

        return sRet;
    }

    public static Color FormatRGB(String color) {
        String s;
        if (color == null || color.equals(DEFAULT_COLOR))
            return null;
        int iVal[] = { 0, 0, 0 };
        Color c = null;

        if (color.isEmpty() || color.length() < 7)
            return new Color(null, 255, 255, 255);

        for (int i = 0; i < 3; i++) {
            s = "";

            s = color.substring(i * 2 + 1, i * 2 + 3);
            try {
                iVal[i] = Integer.parseInt(s, 16);
            } catch (NumberFormatException e) {
                iVal[i] = 0;
            }
        }

        c = new Color(null, iVal[0], iVal[1], iVal[2]);
        return c;
    }

    public static void StringParseToArray(String items, Vector<String> ary) {
        int j;

        if (items.isEmpty())
            return;
        while (items.length() > 0) {
            j = items.indexOf(';');
            if (j < 0) {
                if (items.length() < 1)
                    break;
                else
                    j = items.length();
            } else if (j == 0) {
                ary.add(DEFAULT_COLOR);
                items = items.substring(1);
                continue;
            }
            ary.add(items.substring(0, j));
            // System.out.println("items.substring(0, " + j + ") : "
            // + items.substring(0, j));
            if (items.length() > j + 1)
                items = items.substring(j + 1);
            else
                break;
        }
    };

    public static String ArrayToItemColor(Vector<String> ary) {
        String ret = "", s = "";
        StringBuilder sBuilder;
        for (int i = 0; i < ary.size(); i++) {
            s = ary.elementAt(i);
            if (s == null || s.isEmpty())
                s = DEFAULT_COLOR;
            if (i == 0) {
                sBuilder = new StringBuilder(s);
                sBuilder.append(";");
                ret = sBuilder.toString();
            } else {
                sBuilder = new StringBuilder(ret);
                sBuilder.append(s);
                sBuilder.append(";");
                ret = sBuilder.toString();
            }
        }
        return ret;
    }

    public boolean isSceneDirty() {
        if (m_Form == null)
            return false;

        Enumeration<String> sceneKey = m_Form.keys();
        while (sceneKey.hasMoreElements()) {
            String screen = sceneKey.nextElement();
            Vector<String> delFile = m_delFormFile.get(screen);
            if (delFile != null && delFile.size() > 0)
                return true;

            Hashtable<String, OspForm> scenes = m_Form.get(screen);
            if (scenes == null)
                continue;
            Enumeration<String> keys = scenes.keys();
            while (keys.hasMoreElements()) {
                OspForm scene = scenes.get(keys.nextElement());
                if (scene == null)
                    continue;
                if (scene.isDirty())
                    return true;
            }
        }

        return false;
    };

    public boolean isSceneDirty(String screen) {
        Vector<String> delFile = m_delFormFile.get(screen);
        if (delFile != null && delFile.size() > 0)
            return true;

        if (m_Form == null)
            return false;
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return false;
        Enumeration<String> keys = scenes.keys();
        while (keys.hasMoreElements()) {
            OspForm scene = scenes.get(keys.nextElement());
            if (scene == null)
                continue;
            if (scene.isDirty())
                return true;
        }

        return false;
    };

    public boolean isPopupDirty() {
        if(m_Popup == null)
            return false;
        Enumeration<String> popupKeys = m_Popup.keys();
        while(popupKeys.hasMoreElements()) {
            String screen = popupKeys.nextElement();
            Vector<String> delFile = m_delPopupFile.get(screen);
            if(delFile != null && delFile.size() > 0)
                return true;
            
            Hashtable<String, OspPopup> popups = m_Popup.get(screen);
            if(popups == null)
                continue;
            Enumeration<String> keys = popups.keys();
            while(keys.hasMoreElements()) {
                OspPopup popup = popups.get(keys.nextElement());
                if(popup == null)
                    continue;
                if(popup.isDirty())
                    return true;
            }
        }
        return false;
    };
    
    public boolean isPopupDirty(String screen) {
        Vector<String> delFile = m_delPopupFile.get(screen);
        if(delFile != null && delFile.size() > 0)
            return true;
    
        if(m_Popup == null)
            return false;
        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        if(popups == null)
            return false;
        Enumeration<String> keys = popups.keys();
        while(keys.hasMoreElements()) {
            OspPopup popup = popups.get(keys.nextElement());
            if(popup == null)
                continue;
            if(popup.isDirty())
                return true;
        }
        return false;
    };
    
    public void setSceneDirty(String screen, boolean dirty) {
        if (m_Form == null)
            return;
        Hashtable<String, OspForm> scenes = m_Form.get(screen);
        if (scenes == null)
            return;
        Enumeration<String> keys = scenes.keys();
        while (keys.hasMoreElements()) {
            OspForm scene = scenes.get(keys.nextElement());
            if (scene == null)
                continue;
            scene.setDirty(dirty);
        }
    }

    public void setPopupDirty(String screen, boolean dirty) {
        if(m_Popup == null)
            return;
        Hashtable<String, OspPopup> popups = m_Popup.get(screen);
        if(popups == null)
            return;
        Enumeration<String> keys = popups.keys();
        while(keys.hasMoreElements()) {
            OspPopup popup = popups.get(keys.nextElement());
            if(popup == null)
                continue;
            popup.setDirty(dirty);
        }
    }
    public boolean isPanelDirty() {
        if(m_Panels == null)
            return false;
        
        Enumeration<String> panelKeys = m_Panels.keys(); 
        while(panelKeys.hasMoreElements()){
            String screen = panelKeys.nextElement();
            Vector<String> delPanel = m_delPanel.get(screen);
            if (delPanel != null && delPanel.size() > 0)
                return true;
    
            Hashtable<String, OspPanel> panels = m_Panels.get(screen);
            if (panels == null)
                return false;
            Enumeration<String> keys = panels.keys();
            while (keys.hasMoreElements()) {
                OspPanel panel = panels.get(keys.nextElement());
                if (panel == null)
                    continue;
                if (panel.isDirty())
                    return true;
            }
        }
        return false;
    };

    public boolean isPanelDirty(String screen) {
        Vector<String> delPanel = m_delPanel.get(screen);
        if (delPanel != null && delPanel.size() > 0)
            return true;

        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if (panels == null)
            return false;
        Enumeration<String> keys = panels.keys();
        while (keys.hasMoreElements()) {
            OspPanel panel = panels.get(keys.nextElement());
            if (panel == null)
                continue;
            if (panel.isDirty())
                return true;
        }
        return false;
    };

    public void setPanelDirty(String screen, boolean dirty) {
        Hashtable<String, OspPanel> panels = m_Panels.get(screen);
        if (panels == null)
            return;
        Enumeration<String> keys = panels.keys();
        while (keys.hasMoreElements()) {
            OspPanel panel = panels.get(keys.nextElement());
            if (panel == null)
                continue;
            panel.setDirty(dirty);
        }
    }
    // public void finalSave(String szFileName) {
    // OpenFrame(szFileName);
    // File file = new File(m_file);
    // if (m_Scene.GetFrameList().size() == 0) {
    // if (file.exists())
    // file.delete();
    // return;
    // }
    // }
}
