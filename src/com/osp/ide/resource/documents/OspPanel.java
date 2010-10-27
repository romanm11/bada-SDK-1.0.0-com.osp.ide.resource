package com.osp.ide.resource.documents;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resinfo.PANEL_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspPanel implements FrameConst {
	public PANELFRAME_INFO m_infoPanel;

	// key:<KIND_CAPTION> <NAME>:String name
	public Hashtable<String, ITEM_INFO> m_items;
	private OspResourceManager m_manager;

	private boolean isDirty = false;
    public String screen;

	public OspPanel(OspResourceManager frame, String screen) {
		m_manager = frame;
		m_infoPanel = new PANELFRAME_INFO();

		this.screen = screen;
		// key:<KIND_CAPTION> <NAME>:String name
		m_items = new Hashtable<String, ITEM_INFO>();
	};

    public OspPanel getClone(String newScreen) {
        if(newScreen == null || newScreen.isEmpty())
            newScreen = screen;
        
        OspPanel newPanel = new OspPanel(m_manager, newScreen);

        newPanel.setDirty(true);
        newPanel.m_infoPanel = m_infoPanel.clone();
        if(OspResourceManager.isScotia(newScreen))
        	newPanel.m_infoPanel.bgColorOpacity = 100;
        Layout panelLayout = computeLayout(newScreen, m_infoPanel.layout.get(cszFrmMode[PORTRAIT]));
        newPanel.m_infoPanel.layout.remove(cszFrmMode[PORTRAIT]);
        newPanel.m_infoPanel.layout.put(panelLayout.mode, panelLayout);

        panelLayout = computeLayout(newScreen, m_infoPanel.layout.get(cszFrmMode[LANDCAPE]));
        newPanel.m_infoPanel.layout.remove(cszFrmMode[LANDCAPE]);
        newPanel.m_infoPanel.layout.put(panelLayout.mode, panelLayout);
        
        Enumeration<String> keys = m_items.keys();
        while(keys.hasMoreElements()) {
            ITEM_INFO item = m_items.get(keys.nextElement());
            if(item == null)
                continue;
            ITEM_INFO newItem = item.clone();
            if(!screen.equals(newScreen)) {
                Enumeration<String> layoutKeys = item.layout.keys();
                while(layoutKeys.hasMoreElements()) {
                    Layout newLayout = computeLayout(newScreen, item.layout.get(layoutKeys.nextElement()));
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
            
            newPanel.m_items.put(newItem.Id, newItem);
        }
        
        return newPanel;
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

		do {
			s = String.format("IDPC_%s%d", cszCtlType[type], i++);
		} while ((m_items.get(s)) != null);

		return s;
	};

	public PANELFRAME_INFO GetPanelInfo() {
		return m_infoPanel;
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
		int ret = -1;

		if (IsExistID(id))
			return ret;

		ITEM_INFO inputData = data;
		if(pID != null && !pID.isEmpty())
			inputData.pID = pID;
		m_items.put(id, inputData);

		ITEM_INFO pInfo = m_infoPanel;
		if (pInfo.children.isEmpty())
			pInfo.children = "|" + id + "|";
		else
			pInfo.children += id + "|";
		ret = 1;

		setDirty(true);
		return ret;
	};

	public boolean IsExistID(String id) {
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

        if (m_manager != null && m_manager.m_Popup != null
                && m_manager.m_Popup.get(screen) != null) {
            if (m_manager.m_Popup.get(screen).get(id) != null)
                return true;
        }

		if (m_items.get(id) != null || id.equals(m_infoPanel.Id))
			return true;
		else {
			if (Display.getCurrent() != null) {

			}
			return false;
		}
	};

	public void DeleteRsc(String id) {
		StringBuilder s, localId;
		ITEM_INFO item = m_items.get(id);
		if (item == null)
			return;

		item = m_items.get(id);
		if (item != null && item.pID != null && !item.pID.isEmpty()) {
			if (m_infoPanel != null) {
				s = new StringBuilder(m_infoPanel.children);
				localId = new StringBuilder(id);
				localId.append('|');

				while (s.indexOf(localId.toString()) > 0)
					s.delete(s.indexOf(localId.toString()), s.indexOf(localId
							.toString())
							+ localId.length());
				m_infoPanel.children = s.toString();
			}
		}
		m_items.remove(id); // delete
		setDirty(true);
	};

	public void EmptyItems(String id) {
		Vector<String> ary;

		int i;

		ITEM_INFO item = m_items.get(id);
		if (item == null)
			return;
		ary = getIdList(); // get item list

		for (i = 0; i < ary.size(); i++) // for item number
		{
			m_items.remove(ary.elementAt(i)); // delete item
		}
		// update table info
		item.children = "";
		m_items.remove(id);
		m_items.put(id, item);
		setDirty(true);
	};

	public Vector<String> getIdList() {
		Vector<String> ary = new Vector<String>();
		int j;

		String children = m_infoPanel.children;
		if (children .isEmpty())
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

	public void setIdList(Vector<String> ary) {
		StringBuilder s = new StringBuilder();
		int cnt = ary.size();

		for (int i = 0; i < cnt; i++) {
			if(i == 0)
				s.append("|");
			s.append(ary.get(i));
			s.append("|");
		}
		m_infoPanel.children = s.toString();
	};

	public void reorderIndex(int index, String name) {
		Vector<String> children = getIdList();
		
		if(index < 0 || index > children.size() -1)
			return;
		
		if(!children.remove(name))
			return;
		
		children.add(index, name);
		setIdList(children);
	}
	
	public void reorderTop(String name) {
		Vector<String> children = getIdList();
		
		if(!children.remove(name))
			return;
		
		children.add(name);	
		setIdList(children);
	}
	
	public void reorderBottom(String name) {
		Vector<String> children = getIdList();
		
		if(!children.remove(name))
			return;
		
		children.add(0, name);	
		setIdList(children);
	}
	
	public void reorderToUp(String name) {
		Vector<String> children = getIdList();
		
		int index = children.indexOf(name);
		if(index < children.size()-1) {
			if(!children.remove(name))
				return;
			
			children.add(index+1, name);	
			setIdList(children);
		}
	}
	
	public void reorderToDown(String name) {
		Vector<String> children = getIdList();
		
		int index = children.indexOf(name);
		if(index > 0) {
			if(!children.remove(name))
				return;
			
			children.add(index -1, name);	
			setIdList(children);
		}
	}
	
	public boolean ReplaceRscID(String oldID, String newID) {
		if (IsExistID(newID))
			return false;
		ITEM_INFO item = m_items.get(oldID);
		if (item == null)
			return false;

		if (item.type == WINDOW_SCROLLPANEL) {
			return false;
		}
		item.Id = newID;
		m_items.remove(oldID);
		m_items.put(item.Id, item);
		if (m_infoPanel.children.indexOf(oldID) > 0)
			m_infoPanel.children = m_infoPanel.children.replace(oldID, newID);

		setDirty(true);
		return true;
	};

	public boolean UpdateRsc(String id, ITEM_INFO data) {
		ITEM_INFO item = m_items.get(id);
		if (m_infoPanel.Id.equals(id)) {
			item = m_infoPanel;
			data.copy(item);
			setDirty(true);
			return true;
		}
		if (item == null)
			return false;

		m_items.remove(id);
		m_items.put(id, data);
		setDirty(true);
		return true;
	};

	public void PrintItemsInfo() {
		Enumeration<String> keys;
		String id;
		int pos = m_items.size();

		System.out.println("\n\rPrint All Items Information!");
		if (pos > 0) {
			keys = m_items.keys();
			StringBuilder s;
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

	public void save() {
		Enumeration<String> keys = m_manager.m_Form.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			Hashtable<String, OspForm> scenes = m_manager.m_Form.get(key);
			Enumeration<String> sceneKeys = scenes.keys();
			while(sceneKeys.hasMoreElements()) {
				String sceneKey = sceneKeys.nextElement();
				OspForm scene = scenes.get(sceneKey);
				Enumeration<String> itemKeys = scene.m_items.keys();
				while(itemKeys.hasMoreElements()) {
					String itemKey = itemKeys.nextElement();
					ITEM_INFO item = scene.m_items.get(itemKey);
					if(item instanceof SCROLLPANEL_INFO && 
							((SCROLLPANEL_INFO)item).panelId.equals(m_infoPanel.Id)) {
						m_manager.SaveXML(scene);
						clearFormDirty(scene.m_infoScene.Id);
						break;
					}
				}
			}
		}
		
        keys = m_manager.m_Popup.keys();
        while(keys.hasMoreElements()) {
            String key = keys.nextElement();
            Hashtable<String, OspPopup> popups = m_manager.m_Popup.get(key);
            Enumeration<String> popupKeys = popups.keys();
            while(popupKeys.hasMoreElements()) {
                String poppupKey = popupKeys.nextElement();
                OspPopup popup = popups.get(poppupKey);
                Enumeration<String> itemKeys = popup.m_items.keys();
                while(itemKeys.hasMoreElements()) {
                    String itemKey = itemKeys.nextElement();
                    ITEM_INFO item = popup.m_items.get(itemKey);
                    if(item instanceof SCROLLPANEL_INFO && 
                            ((SCROLLPANEL_INFO)item).panelId.equals(m_infoPanel.Id)) {
                        m_manager.SaveXML(popup);
                        clearPopupDirty(popup.m_infoPopup.Id);
                        break;
                    }
                }
            }
        }
		setDirty(false);
	}

	private void clearFormDirty(String id) {
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if(view == null)
			return;
		
		IWorkbenchPage page = view.getSite().getPage();
		if (page != null) {
			IEditorReference[] editors = page.getEditorReferences();
			if (editors != null) {
				for (int n = 0; n < editors.length; n++) {
					final IEditorPart editor = editors[n].getEditor(false);
					if (editor instanceof OspFormEditor &&
							((OspFormEditor) editor).m_id.equals(id)) {
						Display display = editor.getSite().getShell()
								.getDisplay();
						display.asyncExec(new Runnable() {
							@Override
							public void run() {
								((OspFormEditor) editor).clearDirty();
							}
						});
					}
				}
			}
		}
	}

    private void clearPopupDirty(String id) {
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if(view == null)
            return;
        
        IWorkbenchPage page = view.getSite().getPage();
        if (page != null) {
            IEditorReference[] editors = page.getEditorReferences();
            if (editors != null) {
                for (int n = 0; n < editors.length; n++) {
                    final IEditorPart editor = editors[n].getEditor(false);
                    if (editor instanceof OspPopupEditor &&
                            ((OspPopupEditor) editor).m_id.equals(id)) {
                        Display display = editor.getSite().getShell()
                                .getDisplay();
                        display.asyncExec(new Runnable() {
                            @Override
                            public void run() {
                                ((OspPopupEditor) editor).clearDirty();
                            }
                        });
                    }
                }
            }
        }
    }

}
