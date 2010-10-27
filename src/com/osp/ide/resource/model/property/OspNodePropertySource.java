package com.osp.ide.resource.model.property;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.string.OspStringMarkup;
import com.osp.ide.resource.string.OspUIString;

public abstract class OspNodePropertySource implements IPropertySource, FrameConst {

    protected String[] BOOL = {"true", "false"};

	protected FrameNode node;
    Vector<String> handlerList;
    
	ArrayList<IPropertyDescriptor> properties = null;
	protected String[] stringId;

	protected ITEM_INFO info;

	protected String[] controlId;
	protected String[] panelId;

	/**
	 * Returns the property value when this property source is used as a value.
	 * We can return <tt>null</tt> here
	 */
	@Override
	public Object getEditableValue() {
		return null;
	}

    /**
     * @param node
     * @return
     */
    protected int getMinY(FrameNode node) {
        int minY = 0;
        if (node.getParent() instanceof Form && 
            node.getParent().getName().equals(node.getParentId()))
            minY = ((Form) node.getParent()).getMinY();
        else if (node.getParent() instanceof Popup && 
            node.getParent().getName().equals(node.getParentId()))
            minY = ((Popup) node.getParent()).getMinY();
        
        return minY;
    }

	protected OspUIString getString(FrameNode model) {
		OspUIString string = null;

		FrameNode form;
		if(model instanceof Form)
			form = model;
		else if(model instanceof PanelFrame)
			form = model;
		else if(model instanceof Popup)
			form = model;
		else
			form = model.getFrameModel();
		
		if(form instanceof Form)
			string= ((Form) form).editor.m_string;
		else if(form instanceof PanelFrame)
			string= ((PanelFrame) form).editor.m_string;
		else if(form instanceof Popup)
			string= ((Popup) form).editor.m_string;
		
		if (string != null && string.m_Strings.size() == 0) {
			string.InsertString(OspUIString.DEFAULT_LANG1);
			if(form instanceof Form)
				((Form) form).refreshChildren();
			else if(form instanceof PanelFrame)
				((PanelFrame) form).refreshChildren();
			else if(form instanceof Popup)
				((Popup) form).refreshChildren();
		}
		
		return string;
	}

	protected void initStringId() {
		Vector<String> stringArray = new Vector<String>();
		OspUIString strings = getString(node);
		
		if(strings == null) {
			stringId = new String[stringArray.size() + 1];
			stringId[0] = "";
			return;
		}

		Enumeration<String> keys = strings.m_Strings.keys();
		while (keys.hasMoreElements()) {
			OspStringMarkup string = strings.m_Strings.get(keys.nextElement());
			if (string == null) {
				string = strings.InsertString(OspUIString.DEFAULT_LANG1);
				((Form) node).refreshChildren();
			}

			Vector<String> elements = string.getSortedKeys(false, false);
			if(elements == null)
				return;
			Enumeration<String> ids = elements.elements();
			while (ids.hasMoreElements()) {
				String id = ids.nextElement();
				if (stringArray.indexOf(id) < 0)
					stringArray.add(id);
			}
		}

		stringId = new String[stringArray.size() + 1];
		stringId[0] = "";
		for (int i = 0; i < stringArray.size(); i++)
			stringId[i + 1] = stringArray.elementAt(i);
	}

	protected int getStringIndex(String id) {
		for (int i = 0; i < stringId.length; i++) {
			if (stringId[i].equals(id))
				return i;
		}
		return 0;
	}

	protected void initPanelId() {
		Vector<String> panelArray = new Vector<String>();
		OspResourceManager frame;
		FrameNode form;
		if (node instanceof Form) {
			frame = ((Form) node).editor.m_frame;
			form = node;
		} else if (node instanceof Popup) {
            frame = ((Popup) node).editor.m_Popup;
            form = node;
		} else if(node instanceof ScrollPanel) {
            form = node.getFrameModel();
            if(form instanceof Form) {
                panelArray.add(((ScrollPanel)node).getPanelId());
                frame = ((Form) form).editor.m_frame;
            } else if(form instanceof Popup) {
                panelArray.add(((ScrollPanel)node).getPanelId());
                frame = ((Popup) form).editor.m_Popup;
            } else
                return;
		}else {
			form = node.getFrameModel();
            if(form instanceof Form) {
                frame = ((Form) form).editor.m_frame;
            } else if(form instanceof Popup) {
                frame = ((Popup) form).editor.m_Popup;
            } else {
				panelId = new String[panelArray.size() + 1];
				panelId[0] = "";
				return;
			}
		}

		Hashtable<String, OspPanel> panels = frame.m_Panels.get(form.getScreen());
		Vector<String> ids = Activator.getSortedKeys(panels, false);
		for(int i=0; i<ids.size(); i++){
				String id = ids.get(i);
				OspPanel panel = panels.get(id);
				if ((panel != null && panel.GetPanelInfo().parent.size() <= 0) && 
				    panelArray.indexOf(id) < 0)
					panelArray.add(id);
		}

		panelId = new String[panelArray.size() + 1];
		panelId[0] = "";
		for (int i = 0; i < panelArray.size(); i++)
			panelId[i + 1] = panelArray.elementAt(i);
	}
	
	protected int getPanelIndex(String id) {
		for (int i = 0; i < panelId.length; i++) {
			if (panelId[i].equals(id))
				return i;
		}
		return 0;
	}

	protected void initControlId() {
		Object documents = node.getDocuments();
		
		if(documents instanceof OspForm)
			initFormControlId((OspForm)documents);
		else if(documents instanceof OspPanel)
			initPanelControlId((OspPanel)documents);
		else if(documents instanceof OspPopup)
			initPopupControlId((OspPopup)documents);
		else {
			controlId = new String[1];
			controlId[0] = "";
		}
	}
	
	protected void initFormControlId(OspForm documents) {
		Vector<String> controlArray = new Vector<String>();

		if(documents != null) {
			controlArray.add(documents.m_infoScene.Id);
			Vector<String> keys = Activator.getSortedKeys(documents.m_items, false);
			for(int i=0; i<keys.size(); i++) {
				 ITEM_INFO item = documents.m_items.get(keys.get(i));
				 if(item == null)
					 continue;
				 String id = item.Id;
	
				if (!id.equals(node.getName()) && controlArray.indexOf(id) < 0) {
					//Form, Panel, OverlayPanel, Popup
					if(item.type == WINDOW_PANEL 
							|| item.type == WINDOW_OVERLAYPANEL 
							|| item.type == WINDOW_POPUP ) {
						if(item.pID.equals(node.getName()))
							continue;
						controlArray.add(id);
					}
				}
			}
		}

		controlId = new String[controlArray.size()];
		//controlId[0] = "";
		for (int i = 0; i < controlArray.size(); i++)
			controlId[i] = controlArray.elementAt(i);
	}

	protected void initPanelControlId(OspPanel documents) {
		Vector<String> controlArray = new Vector<String>();

		if(documents != null) {
			controlArray.add(documents.m_infoPanel.Id);
			Vector<String> keys = Activator.getSortedKeys(documents.m_items, false);
			for(int i=0; i<keys.size(); i++) {
				 ITEM_INFO item = documents.m_items.get(keys.get(i));
				 if(item == null)
					 continue;
				 String id = item.Id;
	
				if (!id.equals(node.getName()) && controlArray.indexOf(id) < 0) {
					//Form, Panel, ScrollPanel, OverlayPanel, Popup
					if(item.type == WINDOW_PANEL
							|| item.type == WINDOW_OVERLAYPANEL 
							|| item.type == WINDOW_POPUP ) {
						if(item.pID.equals(node.getName()))
							continue;
						controlArray.add(id);
					}
				}
			}
		}

		controlId = new String[controlArray.size()];
		for (int i = 0; i < controlArray.size(); i++)
			controlId[i] = controlArray.elementAt(i);
	}
	
	protected void initPopupControlId(OspPopup documents) {
		Vector<String> controlArray = new Vector<String>();

		if(documents != null) {
			controlArray.add(documents.m_infoPopup.Id);
			Vector<String> keys = Activator.getSortedKeys(documents.m_items, false);
			for(int i=0; i<keys.size(); i++) {
				 ITEM_INFO item = documents.m_items.get(keys.get(i));
				 if(item == null)
					 continue;
				 String id = item.Id;
	
				if (!id.equals(node.getName()) && controlArray.indexOf(id) < 0) {
					//Form, Panel, ScrollPanel, OverlayPanel, Popup
					if(item.type == WINDOW_PANEL
							|| item.type == WINDOW_OVERLAYPANEL 
							|| item.type == WINDOW_POPUP ) {
						if(item.pID.equals(node.getName()))
							continue;
						controlArray.add(id);
					}
				}
			}
		}

		controlId = new String[controlArray.size()];
		for (int i = 0; i < controlArray.size(); i++)
			controlId[i] = controlArray.elementAt(i);
	}
	
	protected int getControlIndex(String id) {
		for (int i = 0; i < controlId.length; i++) {
			if (controlId[i].equals(id))
				return i;
		}
		return 0;
	}
	
	protected IPropertyDescriptor getPropertyDescriptor(Object id) {
		for(IPropertyDescriptor property : properties) {
			if(property != null && property.getId() != null && 
					property.getId().equals(id))
				return property;
		}
		return null;
	}

	protected void operateHandler(String id, String op) {
        System.out.println(id + " : " + op);
	}

    protected abstract void initDescriptor();


	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		initDescriptor();
		return properties.toArray(new IPropertyDescriptor[0]);
	}

	/**
	 * Returns if the property with the given id has been changed since its
	 * initial default value. We do not handle default properties, so we return
	 * <tt>false</tt>.
	 */
	@Override
	public boolean isPropertySet(Object id) {
		return true;
	}

	@Override
	public Object getPropertyValue(Object id) {
		return null;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}
	
	protected boolean isValidate(Object id, Object value) {
		if (id.equals(FrameNode.PROPERTY_BGOPACITY) ||
			id.equals(FrameNode.PROPERTY_POINTX) ||
		    id.equals(FrameNode.PROPERTY_POINTY) ||
			id.equals(FrameNode.PROPERTY_WIDTH) ||
		    id.equals(FrameNode.PROPERTY_HEIGHT) ||
			id.equals(FrameNode.PROPERTY_GROUPID) ||
			id.equals(FrameNode.PROPERTY_LIMITLENGTH) ||
		    id.equals(FrameNode.PROPERTY_ITEMWIDTH) ||
		    id.equals(FrameNode.PROPERTY_ITEMHEIGHT) ||		    
		    id.equals(FrameNode.PROPERTY_TEXTSIZE) ||
		    id.equals(FrameNode.PROPERTY_LINE1HEIGHT) ||
		    id.equals(FrameNode.PROPERTY_LINE2HEIGHT) ||		    
		    id.equals(FrameNode.PROPERTY_COLUME1WIDTH) ||
		    id.equals(FrameNode.PROPERTY_COLUME2WIDTH) ||	
			id.equals(FrameNode.PROPERTY_VALUE) ||	
		    id.equals(FrameNode.PROPERTY_MIN) ||
		    id.equals(FrameNode.PROPERTY_MAX)
		) {
			try {
				Integer.parseInt((String)value);
			}
			catch (Exception e) {
				return false;
			}
		}
		else {
			return true;
		}
	
		return true;
	}
}
