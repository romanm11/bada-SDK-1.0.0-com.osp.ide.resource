package com.osp.ide.resource.editpopup;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.draw2d.parts.Thumbnail;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.MouseWheelHandler;
import org.eclipse.gef.MouseWheelZoomHandler;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.dnd.TemplateTransferDragSourceListener;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.internal.InternalGEFPlugin;
import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.palette.CombinedTemplateCreationEntry;
import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteSeparator;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightAction;
import org.eclipse.gef.ui.actions.MatchWidthAction;
import org.eclipse.gef.ui.actions.ToggleGridAction;
import org.eclipse.gef.ui.actions.ToggleRulerVisibilityAction;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.palette.PaletteViewerProvider;
import org.eclipse.gef.ui.palette.FlyoutPaletteComposite.FlyoutPreferences;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.actions.OspCopyNodeAction;
import com.osp.ide.resource.actions.OspCutNodeAction;
import com.osp.ide.resource.actions.OspDownMoveAction;
import com.osp.ide.resource.actions.OspElementRuler;
import com.osp.ide.resource.actions.OspElementRulerProvider;
import com.osp.ide.resource.actions.OspLeftMoveAction;
import com.osp.ide.resource.actions.OspPasteNodeAction;
import com.osp.ide.resource.actions.OspRightMoveAction;
import com.osp.ide.resource.actions.OspRulerComposite;
import com.osp.ide.resource.actions.OspUpMoveAction;
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.OspScalableRootEditPart;
import com.osp.ide.resource.common.OspZoomManager;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.DrawCanvas;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.part.OspEditPartFactory;
import com.osp.ide.resource.part.OspNodeCreationFactory;
import com.osp.ide.resource.part.OspTemplateTransferDragSourceListener;
import com.osp.ide.resource.part.tree.OspTreeEditPartFactory;
import com.osp.ide.resource.resinfo.BUTTON_INFO;
import com.osp.ide.resource.resinfo.CHECK_INFO;
import com.osp.ide.resource.resinfo.COLORPICKER_INFO;
import com.osp.ide.resource.resinfo.CUSTOMLIST_INFO;
import com.osp.ide.resource.resinfo.DATEPICKER_INFO;
import com.osp.ide.resource.resinfo.EDITAREA_INFO;
import com.osp.ide.resource.resinfo.EDITFIELD_INFO;
import com.osp.ide.resource.resinfo.EXPANDABLELIST_INFO;
import com.osp.ide.resource.resinfo.FLASHCONTROL_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.GROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.ICONLIST_INFO;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.LABEL_INFO;
import com.osp.ide.resource.resinfo.LIST_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANEL_INFO;
import com.osp.ide.resource.resinfo.PROGRESS_INFO;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resinfo.SLIDABLEGROUPEDLIST_INFO;
import com.osp.ide.resource.resinfo.SLIDABLELIST_INFO;
import com.osp.ide.resource.resinfo.SLIDER_INFO;
import com.osp.ide.resource.resinfo.TIMEPICKER_INFO;
import com.osp.ide.resource.resourceexplorer.NonePropertySorter;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspStringEditor;
import com.osp.ide.resource.string.OspUIString;

@SuppressWarnings("restriction")
public class OspPopupEditor extends GraphicalEditorWithFlyoutPalette implements
		FrameConst {
	protected class OutlinePage extends ContentOutlinePage {

		private SashForm sash;

		private ScrollableThumbnail thumbnail;
		// private Thumbnail thumbnail;
		private DisposeListener disposeListener;

		public OutlinePage() {
			super(new TreeViewer());
		}

		public void createControl(Composite parent) {
			sash = new SashForm(parent, SWT.VERTICAL);

			getViewer().createControl(sash);

			getViewer().setEditDomain(getEditDomain());
			getViewer().setEditPartFactory(new OspTreeEditPartFactory());
			getViewer().setContents(model);

			getSelectionSynchronizer().addViewer(getViewer());

			// overview
			Canvas canvas = new Canvas(sash, SWT.BORDER);
			LightweightSystem lws = new LightweightSystem(canvas);

			RootEditPart rootEditPart = getGraphicalViewer().getRootEditPart();
			thumbnail = new ScrollableThumbnail(
					(Viewport) ((ScalableRootEditPart) rootEditPart)
							.getFigure());
			thumbnail.setSource(((ScalableRootEditPart) rootEditPart)
					.getLayer(LayerConstants.PRINTABLE_LAYERS));

			lws.setContents(thumbnail);

			disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
			getGraphicalViewer().getControl().addDisposeListener(
					disposeListener);
		}

		public void init(IPageSite pageSite) {
			super.init(pageSite);

			// toolbar
			IActionBars bars = getSite().getActionBars();
			ActionRegistry ar = getActionRegistry();
			bars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
					getActionRegistry().getAction(ActionFactory.UNDO.getId()));
			bars.setGlobalActionHandler(ActionFactory.REDO.getId(),
					getActionRegistry().getAction(ActionFactory.REDO.getId()));
			bars
					.setGlobalActionHandler(ActionFactory.DELETE.getId(),
							getActionRegistry().getAction(
									ActionFactory.DELETE.getId()));
			bars.setGlobalActionHandler(ActionFactory.CUT.getId(), ar
					.getAction(ActionFactory.CUT.getId()));
			bars.setGlobalActionHandler(ActionFactory.COPY.getId(), ar
					.getAction(ActionFactory.COPY.getId()));
			bars.setGlobalActionHandler(ActionFactory.PASTE.getId(), ar
					.getAction(ActionFactory.PASTE.getId()));

			bars.updateActionBars();

			// outline
			getViewer().setKeyHandler(keyHandler);

			// ContextMenu
			ContextMenuProvider provider = new OspPopupContextMenuProvider(
					getViewer(), getActionRegistry());
			getViewer().setContextMenu(provider);

		}

		public Control getControl() {
			return sash;
		}

		public Thumbnail getThumbnail() {
			return thumbnail;
		}

		public void dispose() {
			getSelectionSynchronizer().removeViewer(getViewer());
			Control control = getGraphicalViewer().getControl();
			if (control != null && !control.isDisposed())
				control.removeDisposeListener(disposeListener);
			super.dispose();
		}
	}

	public static final String ID = "com.osp.ide.resource.editor.popup.OspPopupEditor";

	private OutlinePage outline;
	private DrawCanvas model;
	private KeyHandler keyHandler;
	public OspResourceManager m_Popup;
	public OspUIString m_string;
	public OspPopup m_OspPopup;
	public String m_id = "";
	public String mode = cszFrmMode[PORTRAIT];
	private FramePropertyPage propertySheet = null;
	private OspRulerComposite rulerComp;

	private int dimension = 10;

	private OspLeftMoveAction leftMoveAction;

	private OspRightMoveAction rightMoveAction;

	private OspDownMoveAction downMoveAction;

	private OspUpMoveAction upMoveAction;

	private Popup popupModel;

	public String screen;

    private OspZoomManager zoomManager;

	private boolean controlSnap = true;

	public OspPopupEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	protected void setInput(IEditorInput input) {
		// TODO Auto-generated method stub
		m_id = input.getName();
		m_Popup = ((OspPopupEditorInput) input).popup;
		m_string = ((OspPopupEditorInput) input).string;
		screen = ((OspPopupEditorInput) input).screen;
		Hashtable<String, OspPopup> popups = m_Popup.m_Popup.get(screen);
		if(popups != null)
			m_OspPopup = popups.get(m_id);
		super.setInput(input);
		setPartName("Popup[" + screen + "]: " + m_id);
	}

	public void setId(String id) {
		m_id = id;
		super.setPartName("Popup[" + screen + "]: " + m_id);
		setTitleToolTip(m_id + screen);
		OspPopupEditorInput input = (OspPopupEditorInput) getEditorInput();
		if (input != null)
			input.ID = m_id;
		if (popupModel != null)
			popupModel.setName(id);
	}

	@Override
	protected PaletteRoot getPaletteRoot() {

		PaletteRoot root = new PaletteRoot();

//		PaletteDrawer commandDrawer = new PaletteDrawer("GUI Editor", 
//	    	AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/button.png"));
		PaletteDrawer commandDrawer = new PaletteDrawer("GUI Editor");
		root.add(commandDrawer);

		SelectionToolEntry selectionToolEntry = new SelectionToolEntry();
		commandDrawer.add(selectionToolEntry);
		MarqueeToolEntry marquee = new MarqueeToolEntry();
		marquee.setDescription("");
		commandDrawer.add(marquee);
		commandDrawer.add(new PaletteSeparator());

		CombinedTemplateCreationEntry term = new CombinedTemplateCreationEntry(
				"Button", "", Button.class,
				new OspNodeCreationFactory(Button.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/button.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largebutton.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Check Button",
				"", Check.class, new OspNodeCreationFactory(
						Check.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/check.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largecheck.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Color Picker",
				"", ColorPicker.class, new OspNodeCreationFactory(
						ColorPicker.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/colorpicker.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largecolorpicker.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Custom List",
				"", CustomList.class, new OspNodeCreationFactory(
						CustomList.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/customcontrol.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largecustomcontrol.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Edit Area",
				"", EditArea.class, new OspNodeCreationFactory(
						EditArea.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/editarea.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeeditarea.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Edit Date",
				"", EditDate.class, new OspNodeCreationFactory(
						EditDate.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/datepicker.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largedatepicker.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Edit Field",
				"", EditField.class, new OspNodeCreationFactory(
						EditField.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/editfield.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeeditfield.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Edit Time",
				"", EditTime.class, new OspNodeCreationFactory(
						EditTime.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/timepicker.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largetimepicker.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Expandable List",
				"", ExpandableList.class,
				new OspNodeCreationFactory(ExpandableList.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/expandable.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeexpandable.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Flash",
				"", Flash.class, new OspNodeCreationFactory(
						Flash.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/flashcontrol.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeflashcontrol.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Grouped List",
				"", GroupedList.class, new OspNodeCreationFactory(
						GroupedList.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/groupedlist.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largegroupedlist.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Icon List",
				"", IconList.class, new OspNodeCreationFactory(
						IconList.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/iconlist.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeiconlist.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Label",
				"", Label.class, new OspNodeCreationFactory(
						Label.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/label.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largelabel.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("List", "",
		    ListControl.class, new OspNodeCreationFactory(ListControl.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/listctl.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largelistctl.png"));
		commandDrawer.add(term);

//		term = new CombinedTemplateCreationEntry("Overlay Panel",
//				"Creation Overlay Panel Model", OverlayPanel.class,
//				new OspNodeCreationFactory(OverlayPanel.class),
//				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
//						"icons/overlaypanel.png"), AbstractUIPlugin
//						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
//								"icons/largeoverlaypanel.png"));
//		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Panel",
				"", Panel.class, new OspNodeCreationFactory(
						Panel.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/panel.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largepanel.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Progress",
				"", Progress.class, new OspNodeCreationFactory(
						Progress.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/progress.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeprogress.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Scroll Panel",
				"", ScrollPanel.class, new OspNodeCreationFactory(
						ScrollPanel.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/scrollpanel.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largescrollpanel.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Slidable Grouped List",
				"", SlidableGroupedList.class,
				new OspNodeCreationFactory(SlidableGroupedList.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/slidablegroupedlist.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeslidablegroupedlist.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Slidable List",
				"", SlidableList.class,
				new OspNodeCreationFactory(SlidableList.class),
				AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
						"icons/slidablelist.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeslidablelist.png"));
		commandDrawer.add(term);

		term = new CombinedTemplateCreationEntry("Slider",
				"", Slider.class, new OspNodeCreationFactory(
						Slider.class), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/slider.png"), AbstractUIPlugin
						.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
								"icons/largeslider.png"));
		commandDrawer.add(term);

		root.setDefaultEntry(selectionToolEntry);
		return root;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		m_Popup.SavePopupXML(screen, m_OspPopup.m_infoPopup.Id);
		m_string.SaveAll();
		getCommandStack().markSaveLocation();
	}

	@Override
	public void commandStackChanged(EventObject event) {
		boolean oldDirty = isDirty();
		firePropertyChange(IEditorPart.PROP_DIRTY);
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view != null && oldDirty != isDirty())
			view.refreshTree();
		super.commandStackChanged(event);
	}

    /**
     * 
     */
    public void fireDirty() {
        firePropertyChange(IEditorPart.PROP_DIRTY);
    }

	@Override
	public boolean isDirty() {
		return getCommandStack().isDirty() || m_OspPopup.isDirty();
	}

	public void clearDirty() {
		getCommandStack().markSaveLocation();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		if (rulerComp != null && !rulerComp.isDisposed()) {
			rulerComp.dispose();
			rulerComp = null;
		}
		if (propertySheet != null) {
			propertySheet.dispose();
			propertySheet = null;
		}
		if (leftMoveAction != null) {
			leftMoveAction.dispose();
			leftMoveAction = null;
		}
		if (rightMoveAction != null) {
			rightMoveAction.dispose();
			rightMoveAction = null;
		}
		if (upMoveAction != null) {
			upMoveAction.dispose();
			upMoveAction = null;
		}
		if (downMoveAction != null) {
			downMoveAction.dispose();
			downMoveAction = null;
		}
		if (outline != null) {
			outline.dispose();
			outline = null;
		}
		super.dispose();
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view == null)
			return true;
		return false;
	}

	public static int getModeIndex(String mode) {
		for (int i = 0; i < 3; i++)
			if (mode.equals(cszFrmMode[i]))
				return i;
		return PORTRAIT;
	}

	public int getModeIndex() {
		for (int i = 0; i < 3; i++)
			if (mode.equals(cszFrmMode[i]))
				return i;
		return PORTRAIT;
	}

	public String getMode() {
		return mode;
	}

	public DrawCanvas CreateDrawCanvas() {
		DrawCanvas drawcanvas = new DrawCanvas();

		String popupID = m_id;
		Vector<String> ary = new Vector<String>();

		popupModel = new Popup(this, m_OspPopup, popupID, getModeIndex(mode));

		ary = new Vector<String>(m_OspPopup.getIdList(popupModel.getItem().children));;

		createControls(popupModel, ary);
		
		drawcanvas.addChild(popupModel, false);
		return drawcanvas;
	}

	private void createControls(FrameNode parent, Vector<String> ary) {
		ITEM_INFO item;
		for (int i = 0; i < ary.size(); i++) {
			if ((item = m_OspPopup.m_items.get(ary.get(i))) != null) {
				FrameNode child = createControls(parent, item, ary.get(i));
				
				if(child != null)
					createControls(child, m_OspPopup.getIdList(child.getItem().children));
			}
		}
	}

	private FrameNode createControls(FrameNode parent, ITEM_INFO item, String id) {
		FrameNode node = null;
		switch (item.type) {
		case WINDOW_BUTTON:
			node = new Button(id,
					(BUTTON_INFO) item);
			break;
		case WINDOW_CHECKBUTTON:
			node = new Check(id, (CHECK_INFO) item);
			break;
		case WINDOW_COLORPICKER:
			node = new ColorPicker(id,
					(COLORPICKER_INFO) item);
			break;
		case WINDOW_CUSTOMLIST:
			node = new CustomList(id,
					(CUSTOMLIST_INFO) item);
			break;
		case WINDOW_DATEPICKER:
		case WINDOW_EDITDATE:
			node = new EditDate(id,
					(DATEPICKER_INFO) item);
			break;
		case WINDOW_EDITFIELD:
			node = new EditField(id,
					(EDITFIELD_INFO) item);
			break;
		case WINDOW_EDITAREA:
			node = new EditArea(id,
					(EDITAREA_INFO) item);
			break;
		case WINDOW_EXPANDABLELIST:
			node = new ExpandableList(id, 
					(EXPANDABLELIST_INFO) item);
			break;
		case WINDOW_GROUPEDLIST:
			node = new GroupedList(id,
					(GROUPEDLIST_INFO) item);
			break;
		case WINDOW_LABEL:
			node = new Label(id, (LABEL_INFO) item);
			break;
		case WINDOW_SLIDER:
			node = new Slider(id,
					(SLIDER_INFO) item);
			break;
		case WINDOW_PROGRESS:
			node = new Progress(id,
					(PROGRESS_INFO) item);
			break;
		case WINDOW_ICONLIST:
			node = new IconList(id,
					(ICONLIST_INFO) item);
			break;
		case WINDOW_LIST:
			node = new ListControl(id, (LIST_INFO) item);
			break;
		case WINDOW_SCROLLPANEL:
			node = new ScrollPanel(id,
					(SCROLLPANEL_INFO) item);
			break;
		case WINDOW_FLASHCONTROL:
			node = new Flash(
					id, (FLASHCONTROL_INFO) item);
			break;
		case WINDOW_PANEL:
			node = new Panel(id, (PANEL_INFO) item);
			break;
		case WINDOW_SLIDABLEGROUPEDLIST:
			node = new SlidableGroupedList(
					id, (SLIDABLEGROUPEDLIST_INFO) item);
			break;
		case WINDOW_SLIDABLELIST:
			node = new SlidableList(
					id, (SLIDABLELIST_INFO) item);
			break;
		case WINDOW_TIMEPICKER:
		case WINDOW_EDITTIME:
			node = new EditTime(
					id, (TIMEPICKER_INFO) item);
			break;
		}
		if(node != null && parent != null)
			parent.addChild(node, false);
		
		return node;
	}

	@Override
	public GraphicalViewer getGraphicalViewer() {
		// TODO Auto-generated method stub
		return super.getGraphicalViewer();
	}

	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		model = CreateDrawCanvas();

		OspElementRulerProvider verticalRuler = (OspElementRulerProvider) viewer
				.getProperty(RulerProvider.PROPERTY_VERTICAL_RULER);
		verticalRuler.setFrame(popupModel);
		OspElementRulerProvider horizontalRuler = (OspElementRulerProvider) viewer
				.getProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER);
		horizontalRuler.setFrame(popupModel);

		viewer.setContents(model);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(0.75);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(1.0);
		
		if(zoomManager != null)
		    zoomManager.setFrame(popupModel.getFigure());
        
        viewer.addDropTargetListener(new OspTemplateTransferDragSourceListener(viewer));
	}

	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub
		FlyoutPreferences palette = this.getPalettePreferences();
		palette.setPaletteState(FlyoutPaletteComposite.STATE_PINNED_OPEN);
		
		GEFMessages.Palette_Label = "ToolBox";
		InternalGEFPlugin.getDefault().getImageRegistry().remove(InternalImages.IMG_PALETTE);
//		ImageDescriptor icon = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "icons/button.png");
//      InternalGEFPlugin.getDefault().getImageRegistry().put(InternalImages.IMG_PALETTE, icon);
        
		super.createPartControl(parent);
		hookKeyboard();
		
        try {
            getSite().getPage().showView(IPageLayout.ID_OUTLINE);
        } catch (PartInitException e) {
            Activator.setErrorMessage("OspFormEditor.createpartControl", e.getMessage(), e);
        }
	}

    /* (non-Javadoc)
     * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#createPaletteViewerProvider()
     */
    @Override
    protected PaletteViewerProvider createPaletteViewerProvider() {
        return new PaletteViewerProvider(getEditDomain()) {
            protected void configurePaletteViewer(PaletteViewer viewer) {
                super.configurePaletteViewer(viewer);
                
                viewer.addDragSourceListener(new TemplateTransferDragSourceListener(viewer));
            }
        };
    }

	@Override
	protected void createGraphicalViewer(Composite parent) {
		rulerComp = new OspRulerComposite(parent, SWT.NONE);
		super.createGraphicalViewer(rulerComp);
		rulerComp
				.setGraphicalViewer((ScrollingGraphicalViewer) getGraphicalViewer());
	}

	@Override
	protected Control getGraphicalControl() {
		return rulerComp;
	}

	protected void configureGraphicalViewer() {
		double[] zoomLevels;

		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();
		getGraphicalViewer().getControl().setBackground(
				ColorConstants.lightGray);
		viewer.setEditPartFactory(new OspEditPartFactory());

		OspScalableRootEditPart rootEditPart = new OspScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);

		zoomManager = rootEditPart.getZoomManager();
		getActionRegistry().registerAction(new ZoomInAction(zoomManager));
		getActionRegistry().registerAction(new ZoomOutAction(zoomManager));

		zoomLevels = new double[] { 0.25, 0.5, 0.75, 1.0, 1.5, 2.0, 2.5, 3.0,
				4.0, 5.0, 10.0, 20.0 };
		zoomManager.setZoomLevels(zoomLevels);
		ArrayList<String> zoomContributions = new ArrayList<String>();
		zoomContributions.add(ZoomManager.FIT_ALL);
		zoomContributions.add(ZoomManager.FIT_HEIGHT);
		zoomContributions.add(ZoomManager.FIT_WIDTH);
		zoomManager.setZoomLevelContributions(zoomContributions);

		keyHandler = new KeyHandler();

		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));

		keyHandler.put(KeyStroke.getPressed('+', SWT.KEYPAD_ADD, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_IN));

		keyHandler.put(KeyStroke.getPressed('-', SWT.KEYPAD_SUBTRACT, 0),
				getActionRegistry().getAction(GEFActionConstants.ZOOM_OUT));

		viewer.setProperty(MouseWheelHandler.KeyGenerator.getKey(SWT.CTRL),
				MouseWheelZoomHandler.SINGLETON);

		viewer.setKeyHandler(keyHandler);

		// ContextMenu
		ContextMenuProvider provider = new OspPopupContextMenuProvider(viewer,
				getActionRegistry());
		viewer.setContextMenu(provider);

		viewer.setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, true);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_VISIBLE, true);
		// 간격 설정
		setGridDimension(dimension);

		IAction action = new ToggleGridAction(viewer);
		action.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				model.getChildrenArray().get(0).getListeners()
						.firePropertyChange(FrameNode.PROPERTY_GRID,
								event.getOldValue(), event.getNewValue());
			}
		});
		getActionRegistry().registerAction(action);

		configureRuler();
	}

	private void configureRuler() {
		GraphicalViewer viewer = getGraphicalViewer();

		viewer.setProperty(RulerProvider.PROPERTY_VERTICAL_RULER,
				new OspElementRulerProvider(new OspElementRuler(false)));
		viewer.setProperty(RulerProvider.PROPERTY_HORIZONTAL_RULER,
				new OspElementRulerProvider(new OspElementRuler(true)));
		viewer.setProperty(RulerProvider.PROPERTY_RULER_VISIBILITY, true);

		IAction action = new ToggleRulerVisibilityAction(getGraphicalViewer());
		action.addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				model.getChildrenArray().get(0).getListeners()
						.firePropertyChange(FrameNode.PROPERTY_RULER,
								event.getOldValue(), event.getNewValue());
			}

		});
		getActionRegistry().registerAction(action);
	}

	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	public void createActions() {
		super.createActions();

		ActionRegistry registry = getActionRegistry();
		IAction action = new OspCutNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new OspCopyNodeAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new OspPasteNodeAction(this, screen);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.LEFT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.CENTER);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.RIGHT);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.TOP);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.MIDDLE);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new AlignmentAction((IWorkbenchPart) this,
				PositionConstants.BOTTOM);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MatchWidthAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MatchHeightAction(this);
		registry.registerAction(action);
		getSelectionActions().add(action.getId());

		leftMoveAction = new OspLeftMoveAction(this);
		registry.registerAction(leftMoveAction);
		getSelectionActions().add(leftMoveAction.getId());

		rightMoveAction = new OspRightMoveAction(this);
		registry.registerAction(rightMoveAction);
		getSelectionActions().add(rightMoveAction.getId());

		downMoveAction = new OspDownMoveAction(this);
		registry.registerAction(downMoveAction);
		getSelectionActions().add(downMoveAction.getId());

		upMoveAction = new OspUpMoveAction(this);
		registry.registerAction(upMoveAction);
		getSelectionActions().add(upMoveAction.getId());
	}

	protected void handleKeyPressed(KeyEvent event) {
		if (event.keyCode == SWT.ARROW_LEFT && event.stateMask == 0) {
			leftMoveAction.run();
		} else if (event.keyCode == SWT.ARROW_RIGHT && event.stateMask == 0) {
			rightMoveAction.run();
		} else if (event.keyCode == SWT.ARROW_UP && event.stateMask == 0) {
			upMoveAction.run();
		} else if (event.keyCode == SWT.ARROW_DOWN && event.stateMask == 0) {
			downMoveAction.run();
		}
	}

	private void hookKeyboard() {
		getGraphicalViewer().getControl().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				handleKeyPressed(event);
			}

			public void keyReleased(KeyEvent event) {
				// Activator.setStatusMessage("");
			}
		});
	}

	public Thumbnail getThumbnail() {
		return outline.getThumbnail();
	}

	@SuppressWarnings("unchecked")
	public Object getAdapter(Class type) {
		if (type == ZoomManager.class)
			return ((ScalableRootEditPart) getGraphicalViewer()
					.getRootEditPart()).getZoomManager();
		if (type == IContentOutlinePage.class) {
			outline = new OutlinePage();
			return outline;
		}
		if (type.equals(IPropertySheetPage.class)) {
			if (propertySheet == null) {
				propertySheet = new FramePropertyPage();
				NonePropertySorter sorter = new NonePropertySorter();
				propertySheet.setSorter(sorter);
				propertySheet.setRootEntry(new UndoablePropertySheetEntry(
						getCommandStack()));
			}
			return propertySheet;
		}
		return super.getAdapter(type);
	}

	public FramePropertyPage getPropertySheetPage() {
		if (propertySheet == null) {
			propertySheet = new FramePropertyPage();
			NonePropertySorter sorter = new NonePropertySorter();
			propertySheet.setSorter(sorter);
			propertySheet.setRootEntry(new UndoablePropertySheetEntry(
					getCommandStack()));
		}
		return propertySheet;
	}

	public void setGridDimension(int dim) {
		dimension = dim;
		GraphicalViewer viewer = getGraphicalViewer();
		Dimension dimension = new Dimension(dim, dim);
		viewer.setProperty(SnapToGrid.PROPERTY_GRID_SPACING, dimension);
		if (model != null)
			((Popup) model.getChildrenArray().get(0)).setDimension(dim);
	}

	public String getGridDimension() {
		return Integer.toString(dimension);
	}

	public void ReDrawFrame() {
		Popup popup = (Popup) model.getChildrenArray().get(0);
		int mode = getModeIndex(this.mode);
		
		reDrawFrame(popup, mode);

		GraphicalViewer viewer = getGraphicalViewer();
		double zoom = ((ScalableRootEditPart) viewer.getRootEditPart())
				.getZoomManager().getZoom();
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(3);
		((ScalableRootEditPart) viewer.getRootEditPart()).getZoomManager()
				.setZoom(zoom);
	}

	private void reDrawFrame(FrameNode model, int mode) {
		Layout oldRect, newRect;
		model.setMode(mode);
		java.util.List<FrameNode> children = model.getChildrenArray();
		for (int i = 0; i < children.size(); i++) {
			FrameNode child = children.get(i);
			if(child != null)
				reDrawFrame(child, mode);
		}
		if(model instanceof Popup)
			model.reSize(mode);
		
		newRect = model.getLayout(mode);
		if (mode == PORTRAIT)
			oldRect = model.getLayout(LANDCAPE);
		else
			oldRect = model.getLayout(PORTRAIT);

		model.getListeners().firePropertyChange(FrameNode.PROPERTY_LAYOUT,
				oldRect, newRect);
	}

	public Popup getModel() {
		return popupModel;
	}

	public static void refreshStringEditor() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null
					&& editors[n].getId().equals(OspStringEditor.ID)) {
				((OspStringEditor) editors[n].getEditor(false)).fillTable(false);
			}
		}
	}

	public Boolean isGrid() {
		return popupModel.getFigure().getPaintGrid();
	}

	public void setGridVisible(Boolean gridVisible) {
		if (gridVisible)
			gridVisible = false;
		else
			gridVisible = true;

		popupModel.getFigure().paintGrid(gridVisible);
	}
	
    /**
     * @return
     */
    public boolean getControlSnap() {
        return controlSnap;
    }

    /**
     * @param enable
     */
    public void setControlSnap(boolean enable) {
        controlSnap  = enable;
    }

}
