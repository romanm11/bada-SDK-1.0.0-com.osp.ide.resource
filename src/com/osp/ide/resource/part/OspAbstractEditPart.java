package com.osp.ide.resource.part;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.IBuffer;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.OspCellEditorLocator;
import com.osp.ide.resource.common.OspDirectEditManager;
import com.osp.ide.resource.common.OspSnapToGeometry;
import com.osp.ide.resource.common.OspTextCellEditor;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editform.OspCDTFileUtil;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpanel.OspPanelEditorInput;
import com.osp.ide.resource.figure.AbstactFigure;
import com.osp.ide.resource.figure.EditFieldFigure;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspUIString;

public abstract class OspAbstractEditPart extends
		AbstractGraphicalEditPart implements PropertyChangeListener {
	protected OspDirectEditManager m_manager;
	public AbstactFigure figure;
	protected int oldOrder=0;

	public void activate() {
		super.activate();
		((FrameNode) getModel()).addPropertyChangeListener(this);
	}

	public void deactivate() {
		super.deactivate();
		((FrameNode) getModel()).removePropertyChangeListener(this);
		AbstactFigure figure = (AbstactFigure) getFigure();
		if (figure != null) {
			figure.deleteImage();
		}
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

	@Override
	public void refreshChildren() {
		// TODO Auto-generated method stub
		super.refreshChildren();
	}

	@SuppressWarnings("unchecked")
	@Override
    public Object getAdapter(Class key) {
        if (key == SnapToHelper.class) {
            List<SnapToHelper> snaps = new ArrayList<SnapToHelper>();

            Boolean val = (Boolean) getViewer().getProperty(
                    RulerProvider.PROPERTY_RULER_VISIBILITY);
            if (val != null && val)
                snaps.add(new SnapToGuides(this));

            try {
                val = getControlSnapEnable();
                if(val != null && val) {
                    OspSnapToGeometry modelSnap = new OspSnapToGeometry(this);
                    snaps.add(modelSnap);
                }
            } catch (Exception e) {
                Activator.setErrorMessage("OspAbstractEditPart.getAdapter", 
                    "Control Snap Enable Failed", e);
            }

            val = (Boolean) getViewer().getProperty(
                    SnapToGrid.PROPERTY_GRID_ENABLED);

            if (val != null && val.booleanValue()) {
                SnapToGrid snap = new SnapToGrid(this);
                snaps.add(snap);
            }

            return new CompoundSnapToHelper(snaps.toArray(new SnapToHelper[0]));

//          OspElementRulerProvider verticalRuler = (OspElementRulerProvider) getViewer()
//                  .getProperty(RulerProvider.PROPERTY_VERTICAL_RULER);
//          OspElementRulerProvider horizontalRuler = (OspElementRulerProvider) getViewer()
//                  .getProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER);
            
//          if (snaps.size() == 0)
//              return null;
//          if (snaps.size() == 1)
//              return snaps.get(0);
//          if (snaps.size() == 2) {
//              if (verticalRuler.getGuides().size() == 0
//                      && horizontalRuler.getGuides().size() == 0)
//                  return snaps.get(1);
//              else
//                  return snaps.get(0);
//          }
        }
        return super.getAdapter(key);
    }

    /**
     * @return
     */
    private Boolean getControlSnapEnable() {
        Boolean enable = true;
        
        FrameNode model = (FrameNode) getModel();
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
            enable = ((Form) form).editor.getControlSnap();
        else if(form instanceof PanelFrame)
            enable = ((PanelFrame) form).editor.getControlSnap();
        else if(form instanceof Popup)
            enable = ((Popup) form).editor.getControlSnap();
        
        return enable;
    }

    @Override
	public void setSelected(int value) {
		super.setSelected(value);
		String message;
		FrameNode model = (FrameNode) getModel();
		Layout rect = model.getLayout();
		int RepointY = 0;

		if(model.getParent() != null && model.getParent() instanceof Form && 
	            model.getParent().getName().equals(model.getParentId()))
				RepointY = ((Form) model.getParent()).getMinY();
		else if(model.getParent() != null && model.getParent() instanceof Popup && 
	            model.getParent().getName().equals(model.getParentId()))
	            RepointY = ((Popup) model.getParent()).getMinY();

		if (value == SELECTED_PRIMARY) {
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName() + 
	        		") -   X:" + rect.x + "  Y:"
					+ (rect.y - RepointY) + "  W:" + rect.width + "  H:"
					+ rect.height;
			oldOrder = getOrder();
			reorderTop(false, true);
		} else {
			message = "";
			reorderIndex(oldOrder, true);
		}

		Activator.setStatusMessage(message);
	}
	
	public int getOrder() {
		if (getParent() != null && getParent().getChildren() != null) {
			List<?> list = getParent().getChildren();
			return list.indexOf(this);
		}
		
		return -1;
	}
	
	public void reorderIndex(int index, boolean isFigureOnly) {
		if (this instanceof FormFramePart || 
				this instanceof PanelFramePart ||
				this instanceof PopupFramePart)
			return;
		if (getParent() != null && getParent().getChildren() != null) {
			int size = getParent().getChildren().size();
			if(index < 0 || index >= size)
				return;
			
			((OspAbstractEditPart) getParent()).reorderChild(this,
					index);
			
			if(isFigureOnly)
				return;
			
			FrameNode model = (FrameNode) getModel();
			Object frame = model.getParent();
			ITEM_INFO pInfo = model.getParent().getItem();
			if(frame instanceof Form) {
				OspForm ospScene = (OspForm) ((Form)frame).getDocuments();
				ospScene.reorderIndex(index, pInfo, model.getName());
			} else if(frame instanceof Popup) {
				OspPopup ospPopup = (OspPopup) ((Popup)frame).getDocuments();
				ospPopup.reorderIndex(index, pInfo, model.getName());
			} else if(frame instanceof PanelFrame) {
				OspPanel ospPanel = (OspPanel) ((PanelFrame)frame).getDocuments();
				ospPanel.reorderIndex(index, model.getName());
			}
		}
	}

	public void reorderTop(boolean isContainsUp, boolean isFigureOnly) {
		if (this instanceof FormFramePart || 
				this instanceof PanelFramePart ||
				this instanceof PopupFramePart)
			return;
		if (getParent() != null && getParent().getChildren() != null) {
			int size = getParent().getChildren().size();
			((OspAbstractEditPart) getParent()).reorderChild(this,
					size - 1);
				
			if(isContainsUp) {
				// Contains figure up to z-order
				Object[] sibling = getParent().getChildren().toArray();
				Rectangle bounds = getFigure().getBounds();
				for(int i=0; i<sibling.length; i++) {
					OspAbstractEditPart child = (OspAbstractEditPart) sibling[i];
					if(child.equals(this))
						continue;
					
					if(bounds.contains(child.getFigure().getBounds()))
						((OspAbstractEditPart) getParent()).reorderChild(child,
								size - 1);
				}
			}
			
			if(isFigureOnly)
				return;
			
			FrameNode model = (FrameNode) getModel();
			FrameNode frame = model.getFrameModel();
			ITEM_INFO pInfo = model.getParent().getItem();
			if(frame instanceof Form) {
				OspForm ospScene = (OspForm) ((Form)frame).getDocuments();
				ospScene.reorderTop(pInfo, model.getName());
			} else if(frame instanceof Popup) {
				OspPopup ospPopup = (OspPopup) ((Popup)frame).getDocuments();
				ospPopup.reorderTop(pInfo, model.getName());
			} else if(frame instanceof PanelFrame) {
				OspPanel ospPanel = (OspPanel) ((PanelFrame)frame).getDocuments();
				ospPanel.reorderTop(model.getName());
			}
		}
	}

	public void reorderBottom(boolean isFigureOnly) {
		if (this instanceof FormFramePart || 
				this instanceof PanelFramePart ||
				this instanceof PopupFramePart)
			return;
		if (getParent() != null && getParent().getChildren() != null) {
			((OspAbstractEditPart) getParent()).reorderChild(this, 0);
			
			if(isFigureOnly)
				return;
			
			FrameNode model = (FrameNode) getModel();
			FrameNode frame = model.getFrameModel();
			ITEM_INFO pInfo = model.getParent().getItem();
			if(frame instanceof Form) {
				OspForm ospScene = (OspForm) ((Form)frame).getDocuments();
				ospScene.reorderBottom(pInfo, model.getName());
			} else if(frame instanceof Popup) {
				OspPopup ospPopup = (OspPopup) ((Popup)frame).getDocuments();
				ospPopup.reorderBottom(pInfo, model.getName());
			} else if(frame instanceof PanelFrame) {
				OspPanel ospPanel = (OspPanel) ((PanelFrame)frame).getDocuments();
				ospPanel.reorderBottom(model.getName());
			}
				
		}
	}
	
	public void reorderToUp(boolean isFigureOnly) {
		if (this instanceof FormFramePart || 
				this instanceof PanelFramePart ||
				this instanceof PopupFramePart)
			return;
		if (getParent() != null && getParent().getChildren() != null) {
			OspAbstractEditPart framepart = (OspAbstractEditPart) getParent();
			int index = framepart.getChildren().indexOf(this);
			if(index < framepart.getChildren().size() -1)
				framepart.reorderChild(this, index + 1);
			
			if(isFigureOnly)
				return;
			
			FrameNode model = (FrameNode) getModel();
			FrameNode frame = model.getFrameModel();
			ITEM_INFO pInfo = model.getParent().getItem();
			if(frame instanceof Form) {
				OspForm ospScene = (OspForm) ((Form)frame).getDocuments();
				ospScene.reorderToUp(pInfo, model.getName());
			} else if(frame instanceof Popup) {
				OspPopup ospPopup = (OspPopup) ((Popup)frame).getDocuments();
				ospPopup.reorderToUp(pInfo, model.getName());
			} else if(frame instanceof PanelFrame) {
				OspPanel ospPanel = (OspPanel) ((PanelFrame)frame).getDocuments();
				ospPanel.reorderToUp(model.getName());
			}
				
		}
	}
	
	public void reorderToDown(boolean isFigureOnly) {
		if (this instanceof FormFramePart || 
				this instanceof PanelFramePart ||
				this instanceof PopupFramePart)
			return;
		if (getParent() != null && getParent().getChildren() != null) {
			OspAbstractEditPart framepart = (OspAbstractEditPart) getParent();
			int index = framepart.getChildren().indexOf(this);
			if(index > 0)
				framepart.reorderChild(this, index - 1);
			
			if(isFigureOnly)
				return;
			
			FrameNode model = (FrameNode) getModel();
			FrameNode frame = model.getFrameModel();
			ITEM_INFO pInfo = model.getParent().getItem();
			if(frame instanceof Form) {
				OspForm ospScene = (OspForm) ((Form)frame).getDocuments();
				ospScene.reorderToDown(pInfo, model.getName());
			} else if(frame instanceof Popup) {
				OspPopup ospPopup = (OspPopup) ((Popup)frame).getDocuments();
				ospPopup.reorderToDown(pInfo, model.getName());
			} else if(frame instanceof PanelFrame) {
				OspPanel ospPanel = (OspPanel) ((PanelFrame)frame).getDocuments();
				ospPanel.reorderToDown(model.getName());
			}
				
		}
	}
	
	@Override
	protected void createEditPolicies() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
        FrameNode model = (FrameNode) getModel();
        if (evt.getPropertyName().equals(FrameNode.PROPERTY_RENAME))
            refreshVisuals();
        if (evt.getPropertyName().equals(FrameNode.PROPERTY_LAYOUT))
            refreshVisuals();
        if (evt.getPropertyName().equals(FrameNode.PROPERTY_PARENT)) {
            if(getParent() != null) {
            	model.setLayout(model.getLayout());
            	refreshVisuals();
            }
		} else if (evt.getPropertyName().equals(FrameNode.PROPERTY_REMOVE)) {
			refreshChildren();
        }
	}

	@Override
	public void performRequest(Request req) {
        if (req.getType() == RequestConstants.REQ_DIRECT_EDIT)
            performDirectEdit();
        else if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			try {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				page.showView(IPageLayout.ID_PROP_SHEET);

				if(!(getModel() instanceof FrameNode))
				    return;
				
				FrameNode model = (FrameNode) getModel();
				FrameNode frame = model.getFrameModel();

				if (model instanceof ScrollPanel &&
				    (frame instanceof Form || frame instanceof Popup )) {
				    openPanel(page);
				}
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}

    /**
     * @param page 
     * @param project 
     * @param unit
     * @throws PartInitException 
     */
    protected void openCppFile(IProject project, IWorkbenchPage page, ITranslationUnit unit) throws PartInitException {
        String source;
        try {
            IBuffer buffer = unit.getBuffer();
            source = buffer.getContents();
        } catch (CModelException e) {
            e.printStackTrace();
            return;
        }
        IResource resource = unit.getResource();
        if(resource instanceof IFile) {
            IFile file = (IFile) resource;
            IEditorPart editor = IDE.openEditor(page, file, true);
            if(editor.isDirty()) {
                MessageDialog.openQuestion(page.getWorkbenchWindow().getShell(), 
                    "Save Resource", "'" + file.getName() + "' has been modified. Save Changes?");
            }
            
            if(editor instanceof TextEditor) {
                TextEditor tEditor = (TextEditor) editor;
                String offetString = getOffsetString(project, source);
                int offset  = source.indexOf(offetString);
                tEditor.selectAndReveal(offset, offetString.length());
            }
        }
    }

    /**
     * @param project 
     * @param source 
     */
    private String getOffsetString(IProject project, String source) {
    	FrameNode model = (FrameNode) getModel();
    	String offsetString = "";
    	
    	if (model instanceof Form) {
    		offsetString = OspCDTFileUtil.getClassName(project, 
	        		((FrameNode)getModel()).getName()) + "::";
    	} else {
    		offsetString = OspCDTFileUtil.getClassName(project, 
	        		((FrameNode)getModel()).getFrameModel().getName()) + "::OnInitializing";
    	}
    	
    	return offsetString;
    }

    /**
     * @param model
     * @return
     */
    public IProject getProject() {
        String pName = "";
        
        ResourceExplorer view = ResourceExplorer.getResourceView();
        if(view == null)
        	return ResourcesPlugin.getWorkspace().getRoot().getProject();
        
        pName = view.getCurProject();
        
        return ResourcesPlugin.getWorkspace().getRoot().getProject(pName);
    }

    /**
     * @param page 
     * 
     */
    protected void openPanel(IWorkbenchPage page) {
        ScrollPanel model = (ScrollPanel) getModel();
        String panelId = model.getPanelId();
        FrameNode frame = model.getFrameModel();
        OspUIString string;
        OspResourceManager manager;
        if(frame instanceof Form) {
            manager = ((Form) frame).editor.m_frame;
            string = ((Form) frame).editor.m_string;
        } else if(frame instanceof Popup) {
            manager = ((Popup) frame).editor.m_Popup;
            string = ((Popup) frame).editor.m_string;
        } else
            return;
        
        if(panelId != null && !panelId.isEmpty()) {
            Hashtable<String, OspPanel> panels = manager.m_Panels.get(frame.getScreen());
            if(panels == null)
                return;
            OspPanel panel = panels.get(panelId);
            if(panel == null)
                return;
        } else if(panelId == null || panelId.isEmpty()) {
            PANELFRAME_INFO panelInfo = manager.InsertPanel(frame.getScreen(), frame.getItem());
            model.setPanelId(panelInfo.Id);
            
            Enumeration<String> keys = panelInfo.layout.keys();
            while(keys.hasMoreElements()) {
                String key = keys.nextElement();
                if(key != null)
                    panelInfo.layout.get(key).height = model.getItem().layout.get(key).height + 100;
            }
            
            ResourceExplorer view = ResourceExplorer.getResourceView();
            if(view != null)
                view.refreshTree();
        }
        
        if (model.getPanelId() != null
                && !model.getPanelId().isEmpty()) {
            IEditorReference[] editors = page.getEditorReferences();
            for (int n = 0; n < editors.length; n++) {
                if (editors[n] != null
                    && editors[n].getId().equals(
                            OspPanelEditor.ID)) {
                    final OspPanelEditor editor = (OspPanelEditor) editors[n]
                            .getEditor(false);
                    if (editor.m_id.equals(model.getPanelId())
                            && editor.m_screen.equals(frame.getScreen())) {
                    page.bringToTop(editors[n].getPart(false));
                    page.activate(editors[n].getPart(false));
                    return;
                    }
                }
            }

            try {
                page.openEditor(new OspPanelEditorInput(
                        manager, 
                        frame.getScreen(), model.getPanelId(),
                        string), OspPanelEditor.ID,
                        true);
            } catch (PartInitException e) {
                e.printStackTrace();
            }
        }
    }

    protected void performDirectEdit() {
        EditPartViewer viewer = getViewer();
        double zoom = ((ScalableRootEditPart) viewer.getRootEditPart())
                .getZoomManager().getZoom();
        
        if(zoom != 1.0)
            return;
        
        if(this instanceof ButtonPart ||
            this instanceof CheckPart ||
            this instanceof EditAreaPart ||
            this instanceof EditFieldPart ||
            this instanceof LabelPart ) {
        
            if(getFigure() instanceof EditFieldFigure) {
                int style = ((EditFieldFigure)getFigure()).getStyle();
                if(style == FrameConst.EDIT_FIELD_STYLE_PASSWORD ||
                     style == FrameConst.EDIT_FIELD_STYLE_PASSWORD_SMALL)
                    return;
            }
    
            if (m_manager == null) {
                m_manager = new OspDirectEditManager(this, OspTextCellEditor.class,
                        new OspCellEditorLocator(getFigure()));
            }
            m_manager.show();
        }
    }
}
