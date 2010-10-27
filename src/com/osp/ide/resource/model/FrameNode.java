package com.osp.ide.resource.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.views.properties.IPropertySource;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.common.FramePropertyPage;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.model.property.ButtonPropertySource;
import com.osp.ide.resource.model.property.CheckPropertySource;
import com.osp.ide.resource.model.property.ColorPickerPropertySource;
import com.osp.ide.resource.model.property.CustomListPropertySource;
import com.osp.ide.resource.model.property.DatePickerPropertySource;
import com.osp.ide.resource.model.property.DrawCanvasPropertySource;
import com.osp.ide.resource.model.property.EditAreaPropertySource;
import com.osp.ide.resource.model.property.EditFieldPropertySource;
import com.osp.ide.resource.model.property.ExpandableListPropertySource;
import com.osp.ide.resource.model.property.FlashControlPropertySource;
import com.osp.ide.resource.model.property.FormFramePropertySource;
import com.osp.ide.resource.model.property.GroupedListPropertySource;
import com.osp.ide.resource.model.property.IconListPropertySource;
import com.osp.ide.resource.model.property.LabelPropertySource;
import com.osp.ide.resource.model.property.ListPropertySource;
import com.osp.ide.resource.model.property.OverlayPanelPropertySource;
import com.osp.ide.resource.model.property.PanelFramePropertySource;
import com.osp.ide.resource.model.property.PanelPropertySource;
import com.osp.ide.resource.model.property.PopupFramePropertySource;
import com.osp.ide.resource.model.property.ProgressPropertySource;
import com.osp.ide.resource.model.property.ScrollPanelPropertySource;
import com.osp.ide.resource.model.property.SlidableGroupedListPropertySource;
import com.osp.ide.resource.model.property.SlidableListPropertySource;
import com.osp.ide.resource.model.property.SliderPropertySource;
import com.osp.ide.resource.model.property.TimePickerPropertySource;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.ITEM_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.PANELFRAME_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public abstract class FrameNode implements IAdaptable, FrameConst {

    public String name;
	protected ITEM_INFO item;
	protected List<FrameNode> children;
	protected FrameNode parent;
	private PropertyChangeSupport listeners;
	private IPropertySource propertySource;
	private OspElementGuide verticalGuide, horizontalGuide;
	private int mode;
	private AbstractGraphicalEditPart part;
	public int minY=0;
	private Object doc;
	private boolean isOpen;

	public static final int FONT_SIZE = 30;
	public static final int DIALOG_FIGURE_WIDTHSIZE = 280;
	public static final int DIALOG_FIGURE_HEIGHTSIZE = 320;
	public static final String FontName = "Verdana";
	public static final String BITMAP_FOLDER = "/Bitmap/";
    public static final String BITMAP_FOLDER_NAME = "Bitmap";
	public static final String PROPERTY_ADD = "AddChild";
	public static final String PROPERTY_B24HOUR = "b24Hour";
	public static final String PROPERTY_BACKGROUNDSTYLE = "BackgroundStyle";
	public static final String PROPERTY_BGOPACITY = "BackgroundOpacity";
	public static final String PROPERTY_BEDITMODEENABLE = "bEditModeEnable";
	public static final String PROPERTY_BGBITMAPPATH = "BGBitmapPath";
	public static final String PROPERTY_BGCOLOR = "BackgroundColor";
	public static final String PROPERTY_BITMAP = "Bitmap";
	public static final String PROPERTY_BODER = "Boder";
	public static final String PROPERTY_BORDERSTYLE = "boderStyle";
	public static final String PROPERTY_BSHOWVALUESTATE = "bShowValueState";
	public static final String PROPERTY_BSHOWSTATE = "bShowState";
	public static final String PROPERTY_CENTERTEXT = "CenterText";
	public static final String PROPERTY_CENTERTEXTCOLOR = "CenterTextColor";
	public static final String PROPERTY_COLOR = "Color";
	public static final String PROPERTY_CUSTOMCLASS = "CustomClass";
	public static final String PROPERTY_DBGCOLOR = "DisableBGColor";
	public static final String PROPERTY_DIMENSION = "Grid Dimension";
	public static final String PROPERTY_DISABLEDBITMAPPATH = "DisabledBitmapPath";
	public static final String PROPERTY_DISABLEDBITMAPX = "DisabledBitmapX";
	public static final String PROPERTY_DISABLEDBITMAPY = "DisabledBitmapY";
	public static final String PROPERTY_DOCK = "Docking";
	public static final String PROPERTY_DOUTLINE = "DisableOutline";
	public static final String PROPERTY_DTEXTCOLOR = "DisableTextColor";
	public static final String PROPERTY_FONT = "Font";
	public static final String PROPERTY_FGCOLOR = "FGColor";
	public static final String PROPERTY_FRAMETITLE = "FrameTitle";
	public static final String PROPERTY_GRID = "Grig Toggle";
	public static final String PROPERTY_GROUPID = "GroupID";
	public static final String PROPERTY_GUIDETEXT = "GuideText";
	public static final String PROPERTY_HALIGN = "HAlign";
	public static final String PROPERTY_HEIGHT = "HEIGHT";
	public static final String PROPERTY_HEADERHEIGHT = "HeaderHeight";
	public static final String PROPERTY_INDICATORLAYOUTSTYLE = "IndicatorLayoutStyle";
	public static final String PROPERTY_INPUTSTYLE = "InputStyle";
	public static final String PROPERTY_INTTEXT = "IntText";
	public static final String PROPERTY_ITEMCOUNT = "ItemCount";
	public static final String PROPERTY_ITEMCOLOR = "ItemColor";
	public static final String PROPERTY_ITEMFOCUSEDBITMAP = "ItemFocusedBitmap";
	public static final String PROPERTY_ITEMNORMALBITMAP = "ItemNormalBitmap";
	public static final String PROPERTY_ITEMWIDTH = "ItemWidth";
	public static final String PROPERTY_ITEMHEIGHT = "ItemHeight";
	public static final String PROPERTY_ITEMTEXT = "ItemText";
	public static final String PROPERTY_LAYOUT = "Layout";
	public static final String PROPERTY_LEFTICONBITMAPPATH = "LeftIconBitmapPath";
	public static final String PROPERTY_LEFTMARGIN = "LeftMargin";
	public static final String PROPERTY_LEFTTEXT = "LeftText";
	public static final String PROPERTY_LEFTTEXTCOLOR = "LeftTextColor";
	public static final String PROPERTY_LISTITEMFORMAT = "ListItemFormat";
	public static final String PROPERTY_LIMITLENGTH = "LimitLength";
	public static final String PROPERTY_LINE1HEIGHT = "Line1Height";
	public static final String PROPERTY_LINE2HEIGHT = "Line2Height";
	public static final String PROPERTY_LOCALFILEPATH = "LocalFilePath";
	public static final String PROPERTY_MAX = "Max";
	public static final String PROPERTY_MAXDROPLINECOUNT = "MaxDropLineCount";
	public static final String PROPERTY_MAXTEXT = "MaxText";
	public static final String PROPERTY_MIN = "Min";
	public static final String PROPERTY_MINTEXT = "MinText";
	public static final String PROPERTY_MODE = "Mode";
	public static final String PROPERTY_NBGCOLOR = "NormalBGColor";
	public static final String PROPERTY_NORMALBGBITMAPPATH = "NormalBGBitmapPath";
	public static final String PROPERTY_NORMALBITMAPPATH = "NormalBitmapPath";
	public static final String PROPERTY_NORMALBITMAPX = "NormalBitmapX";
	public static final String PROPERTY_NORMALBITMAPY = "NormalBitmapY";
	public static final String PROPERTY_NOUTLINE = "NormalOutline";
	public static final String PROPERTY_NTEXTCOLOR = "NormalTextColor";
	public static final String PROPERTY_OPTIONKEYLAYOUTSTYLE = "OptionKeyLayoutStyle";
	public static final String PROPERTY_ORIENTATION = "Orientation";
	public static final String PROPERTY_OUTLINE = "Outline";
	public static final String PROPERTY_PANELID = "PanelId";
	public static final String PROPERTY_PARENT = "ParentId";
	public static final String PROPERTY_PBGCOLOR = "PressedBGColor";
	public static final String PROPERTY_POUTLINE = "PressedOutline";
	public static final String PROPERTY_POINTX = "POINTX";
	public static final String PROPERTY_POINTY = "POINTY";
	public static final String PROPERTY_PRESSEDBGBITMAPPATH = "PressedBGBitmapPath";
	public static final String PROPERTY_PRESSEDBITMAPPATH = "PressedBitmapPath";
	public static final String PROPERTY_PRESSEDBITMAPX = "PressedBitmapX";
	public static final String PROPERTY_PRESSEDBITMAPY = "PressedBitmapY";
	public static final String PROPERTY_PTEXTCOLOR = "PressedTextColor";
	public static final String PROPERTY_QUALITY = "Quality";
	public static final String PROPERTY_REFRESH = "Refresh";
	public static final String PROPERTY_REMOVE = "RemoveChild";
	public static final String PROPERTY_RENAME = "Rename";
	public static final String PROPERTY_REPEATMODE = "RepeatMode";
	public static final String PROPERTY_RESERVE1 = "Reserve1";
	public static final String PROPERTY_RESERVE2 = "Reserve2";
	public static final String PROPERTY_RESERVE3 = "Reserve3";
	public static final String PROPERTY_RIGHTICONBITMAPPATH = "RightIconBitmapPath";
	public static final String PROPERTY_RIGHTTEXT = "RightText";
	public static final String PROPERTY_RIGHTTEXTCOLOR = "RightTextColor";
	public static final String PROPERTY_RULER = "Ruler Toggle";
	public static final String PROPERTY_SOFTKEY0TEXT = "SoftKey0Text";
	public static final String PROPERTY_SOFTKEY1TEXT = "SoftKey1Text";
	public static final String PROPERTY_SOFTKEY0NICON = "SoftKey0NormalIcon";
	public static final String PROPERTY_SOFTKEY1NICON = "SoftKey1NormalIcon";
	public static final String PROPERTY_SOFTKEY0PICON = "SoftKey0PressedIcon";
	public static final String PROPERTY_SOFTKEY1PICON = "SoftKey1PressedIcon";
	public static final String PROPERTY_SOFTKEY0LAYOUTSTYLE = "SoftKey0LayoutStyle";
	public static final String PROPERTY_SOFTKEY1LAYOUTSTYLE = "SoftKey1LayoutStyle";
	public static final String PROPERTY_STEP = "Step";
	public static final String PROPERTY_STEPSIZE = "StepSize";
	public static final String PROPERTY_STYLE = "Style";
	public static final String PROPERTY_SUBSTYLE = "SubStyle";
	public static final String PROPERTY_TEXT = "Text";
	public static final String PROPERTY_TEXTCOLOR = "TextColor";
	public static final String PROPERTY_TEXTDIRECTION = "TextDirection";
	public static final String PROPERTY_TEXTOFEMPTYLIST = "TextOfEmptyList";
	public static final String PROPERTY_TEXTSIZE = "TextSize";
	public static final String PROPERTY_TEXTSTYLE = "TextStyle";
	public static final String PROPERTY_TABLAYOUTSTYLE = "ShowTab";
	public static final String PROPERTY_TITLEICON = "Title Icon";
	public static final String PROPERTY_TITLELAYOUTSTYLE = "TitleLayoutStyle";
	public static final String PROPERTY_TITLETEXT = "TitleText";
    public static final String PROPERTY_TITLETEXTCOLOR = "ColorOfTitleText";
	public static final String PROPERTY_TOPMARGIN = "TopMargin";
	public static final String PROPERTY_TYPE = "Type";
	public static final String PROPERTY_URLFILEPATH = "urlFilePath";
	public static final String PROPERTY_VALIGN = "VAlign";
	public static final String PROPERTY_VALUE = "Value";
	public static final String PROPERTY_WIDTH = "WIDTH";
	public static final String PROPERTY_ITEMDIVIDER = "itemDivider";
	public static final String PROPERTY_COLOROFEMPTYLISTTEXT = "colorOfEmptyListText";
	public static final String PROPERTY_FASTSCROLL = "fastScroll";
	public static final String PROPERTY_COLUME1WIDTH = "colume1Width";
	public static final String PROPERTY_COLUME2WIDTH = "colume2Width";
	public static final String PROPERTY_SHOWTITLETEXT = "ShowtitleText";
	public static final String PROPERTY_KEYPADENABLED = "KeypadEnabled";
	public static final String PROPERTY_GROUPSTYLE = "GroupStyle";
	public static final String GROUP_STYLE = "Style";
	public static final String GROUP_LAYOUT = "Layout";
	public static final String GROUP_PROPERTIES = "Properties";
    public static final String GROUP_EVENTHANDLER = "Event Handler";
	
	public FrameNode() {
		this.name = "Unknown";
		this.children = new ArrayList<FrameNode>();
		this.parent = null;
		this.listeners = new PropertyChangeSupport(this);
		this.propertySource = null;
	}

	public FrameNode(String name, ITEM_INFO item) {
		this.name = name;
		this.item = item;
		this.children = new ArrayList<FrameNode>();
		this.parent = null;
		this.listeners = new PropertyChangeSupport(this);
		this.propertySource = null;
	}

	public FrameNode(Object scene, int mode) {

		setDocuments(scene);
		this.children = new ArrayList<FrameNode>();
		this.parent = null;
		this.listeners = new PropertyChangeSupport(this);
		this.propertySource = null;
		this.mode = mode;
	}

	public abstract Object clone() throws CloneNotSupportedException;
	
	public Object getDocuments() {
		FrameNode frame = this;
		while (frame != null && !(frame instanceof Form ||
				frame instanceof Popup ||
				frame instanceof PanelFrame)) {
			frame = frame.getFrameModel();
		}
		
		if(frame == null)
			return null;
		
		return frame.doc;
	}
	
	public void setDocuments(Object doc) {
		this.doc = doc;
	}
	
	public String getScreen() {
        if(doc == null && !(this instanceof Form) &&
            !(this instanceof PanelFrame) &&
            !(this instanceof PanelFrame)) {
            doc = getDocuments();
        }
            
		if(doc != null && doc instanceof OspForm)
			return ((OspForm) doc).getScreen();
		else if(doc != null && doc instanceof OspPopup)
			return ((OspPopup) doc).screen;
        else if(doc != null && doc instanceof OspPanel)
            return ((OspPanel) doc).screen;
		
		return "";
	}
	
	public boolean isScotia() {
		String screen = getScreen();
		return screen != null && screen.toUpperCase(Locale.getDefault()).equals(ResourceExplorer.WQVGA);
	}

	public String MakeID(int type, String refer) {
		if(doc == null)
			doc = getDocuments();
		
		if(doc != null && doc instanceof OspForm)
			return ((OspForm) doc).MakeID(type, refer);
		if(doc != null && doc instanceof OspPanel)
			return ((OspPanel) doc).MakeID(type, refer);
		if(doc != null && doc instanceof OspPopup)
			return ((OspPopup) doc).MakeID(type, refer);

		return "";
	}

	public boolean existDoc() {
		if(doc == null)
			doc = getDocuments();
		
		if(doc != null && doc instanceof OspForm)
			return ((OspForm) doc).IsExistID(getScreen(), getName());
		if(doc != null && doc instanceof OspPanel)
			return ((OspPanel) doc).IsExistID(getName());
		if(doc != null && doc instanceof OspPopup)
			return ((OspPopup) doc).IsExistID(getScreen(), getName());

		return false;
	}

	public String GetProjectDirectory() {
		if(doc == null)
			doc = getDocuments();
		
		if(doc != null && doc instanceof OspForm)
			return ((OspForm) doc).GetProjectDirectory();
		if(doc != null && doc instanceof OspPanel)
			return ((OspPanel) doc).GetProjectDirectory();
		if(doc != null && doc instanceof OspPopup)
			return ((OspPopup) doc).GetProjectDirectory();
		
		return "";
	}

	public static int GetDockIndex(String dock) {
		for (int i = 0; i < cszDock.length; i++) {
			if (dock.equals(cszDock[i]))
				return i;
		}
		return 0;
	}

	public static int getTextDirectionIndex(String align) {
		for (int i = 0; i < cszTextDirection.length; i++) {
			if (cszTextDirection[i].equals(align))
				return i;
		}
		return 0;
	}

	public static int getHAlignIndex(String align) {
		for (int i = 0; i < cszHAlign.length; i++) {
			if (cszHAlign[i].equals(align))
				return i;
		}
		return 0;
	}

	public static int getVAlignIndex(String align) {
		for (int i = 0; i < cszVAlign.length; i++) {
			if (cszVAlign[i].equals(align))
				return i;
		}
		return 0;
	}

	public static int getListFormatIndex(String align) {
		for (int i = 0; i < cszListItemFormat.length; i++) {
			if (cszListItemFormat[i].equals(align))
				return i;
		}
		return 0;
	}

	public static int getStyleIndex(int index, String style) {
		for (int i = 0; i < cszStyle[index].length; i++) {
			if (cszStyle[index][i].equals(style))
				return i;
		}
		return 0;
	}
	
	public int getBgStyleIndex() {
		for (int i = 0; i < cszBgStyle.length; i++) {
			if (cszBgStyle[i].equals(item.BgStyle))
				return i;
		}
		return 0;
	}
	
	public String getBgStyle() {
		for (int i = 0; i < cszBgStyle.length; i++) {
			if (cszBgStyle[i].equals(item.BgStyle))
				return item.BgStyle;
		}
		return null;
	}
	
	public static int getInputStyleIndex(String inputstyle) {
		for (int i = 0; i < cszInputStyle.length; i++) {
			if (cszInputStyle[i].equals(inputstyle))
				return i;
		}
		return 0;
	}
	
	public int getItemDivider() {
		for (int i = 0; i < BOOL.length; i++) {
			if (BOOL[i].equals(item.itemDivider))
				return i;
		}
		return 0;
	}
	
	public int getShowTitleText() {
		for (int i = 0; i < BOOL.length; i++) {
			if (BOOL[i].equals(item.ShowTitleText))
				return i;
		}
		return 0;
	}
	
	public int getBorderStyleIndex() {
		for (int i = 0; i < cszBorderStyle.length; i++) {
			if (cszBorderStyle[i].equals(item.BorderStyle))
				return i;
		}
		return 0;
	}
	
	public String getBorderStyle() {
		for (int i = 0; i < cszBorderStyle.length; i++) {
			if (cszBorderStyle[i].equals(item.BorderStyle))
				return item.BorderStyle;
		}
		return null;
	}
	
	public void setShowTitleText(int ShowTitleText) {
		String oldShowTitleText = item.ShowTitleText;
		item.ShowTitleText = BOOL[ShowTitleText];
		
		if(ShowTitleText == BOOL_FALSE)
	        item.titleText = "";
		
		setLayout(item.GetLayout(getModeIndex()));
		
		firePropertyChange(PROPERTY_SHOWTITLETEXT, oldShowTitleText,
				item.ShowTitleText);
	}
	
    public void setTitleText(String titleText) {
        String oldText = item.titleText;
        item.titleText = titleText;
        
        if(item.titleText != null && !item.titleText.isEmpty() && item.ShowTitleText.equals(BOOL[BOOL_FALSE]))
            item.ShowTitleText = BOOL[BOOL_TRUE];
        
        setLayout(item.GetLayout(getModeIndex()));

        firePropertyChange(PROPERTY_TITLETEXT, oldText,
                titleText);
    }

    public String getTitleText() {
        return item.titleText;
    }
    
	protected void firePropertyChange(String property,
			Object oldValue, Object newValue) {
		if(oldValue != null && oldValue.equals(newValue))
			return;
		
		Object documents = getDocuments();
		
		if(documents == null)
			return;
		
		if(documents instanceof OspForm)
			((OspForm) documents).UpdateRsc(this.name, this.item);
		else if(documents instanceof OspPanel)
			((OspPanel) documents).UpdateRsc(this.name, this.item);
		else if(documents instanceof OspPopup)
			((OspPopup) documents).UpdateRsc(this.name, this.item);
		
		
		if(property != null && !property.isEmpty())
			getListeners().firePropertyChange(property, oldValue, newValue);
	}

	public void setShowTitleText(String ShowTitleText) {
		String oldShowTitleText = item.ShowTitleText;
		item.ShowTitleText = ShowTitleText;
		
		if(ShowTitleText != null && ShowTitleText.equals(BOOL[BOOL_FALSE]))
	        item.titleText = "";
		
		firePropertyChange(PROPERTY_SHOWTITLETEXT, oldShowTitleText,
				ShowTitleText);
	}
	
	public void setItemDivider(int itemDivider) {
		String oldDivider = item.itemDivider;
		item.itemDivider = BOOL[itemDivider];
		
		firePropertyChange(PROPERTY_ITEMDIVIDER, oldDivider,
				item.itemDivider);
	}
	
	public void setItemDivider(String itemDivider) {
		String oldDivider = item.itemDivider;
		item.itemDivider = itemDivider;
		
		firePropertyChange(PROPERTY_ITEMDIVIDER, oldDivider,
				itemDivider);
	}
	
	public int getFastScroll() {
		for (int i = 0; i < BOOL.length; i++) {
			if (BOOL[i].equals(item.fastScroll))
				return i;
		}
		return 0;
	}
	
	public void setFastScroll(int fastScroll) {
		String oldDivider = item.fastScroll;
		item.fastScroll = BOOL[fastScroll];
		
		firePropertyChange(PROPERTY_ITEMDIVIDER, oldDivider,
				item.fastScroll);
	}
	
	public void setFastScroll(String fastScroll) {
		String oldDivider = item.fastScroll;
		item.fastScroll = fastScroll;
		
		firePropertyChange(PROPERTY_ITEMDIVIDER, oldDivider,
				fastScroll);
	}
	
	public void setName(String name) {
		String oldName = this.name;
		this.name = name;
		this.item.Id = name;
		getListeners().firePropertyChange(PROPERTY_RENAME, oldName, this.name);
	}

	public String getName() {
		return this.name;
	}

	public void reName(String newName) {
		String oldReName = this.name;
		OspForm scene = null;
		OspPanel panel = null;
		OspPopup popup = null;
		
		if(newName == null || newName.isEmpty() || oldReName.equals(newName))
			return;

		if (this instanceof Form) {
			scene = (OspForm) doc;
			OspResourceManager frame = scene.getFrame();
			if (!frame.RenameScene(scene.getScreen(), scene.m_infoScene.Id, newName))
				return;

			OspFormEditor editor = ((Form)this).editor;
			editor.setId(newName);
			this.name = newName;
			ResourceExplorer view = ResourceExplorer.getResourceView();
			view.refreshTree();
			view.refreshProject();
			return;
		} else if (this instanceof PanelFrame) {
			panel = (OspPanel) doc;
			OspResourceManager frame = panel.getFrame();
			if (!frame.RenamePanel(panel.getScreen(), panel.m_infoPanel.Id, newName))
				return;

			OspPanelEditor editor = ((PanelFrame)this).editor;
			editor.setId(newName);
			this.name = newName;
			ResourceExplorer view = ResourceExplorer.getResourceView();
			view.refreshTree();
			view.refreshProject();
			return;
		} else if (this instanceof Popup) {
			popup = (OspPopup) doc;
			OspResourceManager frame = popup.getPopup();
			if (!frame.RenamePopup(popup.screen, popup.m_infoPopup.Id, newName))
				return;

			OspPopupEditor editor = ((Popup)this).editor;
			editor.setId(newName);
			this.name = newName;
			ResourceExplorer view = ResourceExplorer.getResourceView();
			view.refreshTree();
			view.refreshProject();
			return;
		} else {
			Object documents = getDocuments();
			
			if(documents != null && documents instanceof OspForm) {
				scene = (OspForm) documents;
				
				if (!scene.ReplaceRscID(oldReName, newName))
					return;
			} else if(documents != null && documents instanceof OspPanel) {
				panel = (OspPanel) documents;
				
				if (!panel.ReplaceRscID(oldReName, newName))
					return;
			} else if(documents != null && documents instanceof OspPopup) {
				popup = (OspPopup) documents;
				
				if (!popup.ReplaceRscID(oldReName, newName))
					return;
			}
			
			this.name = newName;
			getListeners()
					.firePropertyChange(PROPERTY_RENAME, oldReName, this.name);
			
			FrameNode parent = getFrameModel();
			if(parent instanceof Form)
			    ((Form)parent).refreshChildren();
			else if(parent instanceof PanelFrame)
                ((PanelFrame)parent).refreshChildren();
			else if(parent instanceof Popup)
			    ((Popup)parent).refreshChildren();
		}
	}

	public void setLayout(Layout newLayout) {

		Layout oldLayout = this.item.GetLayout(newLayout.mode);
		Rectangle oldRect = new Rectangle(oldLayout.x, oldLayout.y,
				oldLayout.width, oldLayout.height);
		this.item.SetLayout(newLayout);
		Rectangle newRect = new Rectangle(newLayout.x, newLayout.y,
				newLayout.width, newLayout.height);

		firePropertyChange(PROPERTY_LAYOUT, oldRect, newRect);
	}

	protected Layout getNonresizeLayout(Layout newLayout) {
		
		int minY = 0, maxY = 800;
		int maxWidth = 800;

		FrameNode frame = getParent();
		if (frame != null && frame instanceof Form) {
			minY = ((Form) frame).getMinY();
		} else if(frame != null && frame instanceof Popup) {
			minY = ((Popup) frame).getMinY();
		} else if (frame == null)
			return newLayout;

        maxY = frame.item.GetLayout(newLayout.mode).height;
        maxWidth = frame.item.GetLayout(newLayout.mode).width;
        

		if(newLayout.x < 0)
			newLayout.x = 0;
		if (newLayout.x + newLayout.width > maxWidth)
			newLayout.x -= newLayout.x + newLayout.width - maxWidth;

		if(newLayout.y < minY)
			newLayout.y = minY;
		if (newLayout.y + newLayout.height > maxY)
			newLayout.y -= newLayout.y + newLayout.height - maxY;
		
		if (newLayout.y + newLayout.height > maxY)
			newLayout.height = maxY - newLayout.y;
		
		return newLayout;
	}

	protected Layout getSuitableLayout(Layout newLayout) {
		Layout oldLayout = this.item.GetLayout(newLayout.mode);
		int maxY = 800, width = 800;
		
		org.eclipse.swt.graphics.Point screenSize = Activator.getSScreenToPoint(getScreen());
		if(newLayout.mode.equals(cszFrmMode[PORTRAIT])) {
			width = screenSize.x;
			maxY = screenSize.y;
		} else {
			width = screenSize.y;
			maxY = screenSize.x;
		}
		
		Point minSize = new Point();
		Tag_info tag;
		FrameNode frame = getParent();
		if (frame != null && frame instanceof Form) {
			minY = ((Form) frame).getMinY();
		} else if (frame != null && frame instanceof Popup) {
		    minY = ((Popup) frame).getMinY();
		} else if(frame == null)
		    return newLayout;
		else
			minY = 0;
		
        maxY = frame.item.GetLayout(newLayout.mode).height;
        width = frame.item.GetLayout(newLayout.mode).width;
        ResourceExplorer view = ResourceExplorer.getResourceView();
        tag = view.getImageInfo(cszTag[getType()],
                frame.getScreen());
        if(tag != null && tag.minSize != null)
            minSize = tag.minSize.getCopy();
        
		if (minSize.x <= 0)
			minSize.x = getDefaultMinWidth();
		if (minSize.y <= 0)
			minSize.y = getDefaultMinHeight();
		
		if(this instanceof EditField && 
				newLayout.isSmall() && tag != null) {
		    try {
			    minSize.y -= tag.tSize - 4;    // 30, 14
		    } catch(NumberFormatException e) {}
		} else if(this instanceof EditField && 
				getShowTitleText() == BOOL_TRUE && tag != null) {
            try {
                minSize.y += Integer.parseInt(tag.temp1);    // 26, 14
            } catch(NumberFormatException e) {}
		} else if(this instanceof Check &&
				getShowTitleText() == BOOL_TRUE && tag != null) {
		    try {
			    minSize.y += Integer.parseInt(tag.temp1);    // 34, 18
            }catch(NumberFormatException e) {}
		} else if(this instanceof Slider &&
				getShowTitleText() == BOOL_TRUE && tag != null) {
		    try {
			    minSize.y += Integer.parseInt(tag.temp1);    // 16, 12
            }catch(NumberFormatException e) {}
		}

        if(this instanceof Check &&
                newLayout.style.indexOf("DIVIDER") > 0) {
            try {
                minSize.x += Integer.parseInt(tag.temp2);    // 34, 18
            }catch(NumberFormatException e) {}
        }

		// control 이동할때
		if (oldLayout.width == newLayout.width
				&& oldLayout.height == newLayout.height) {
			if (newLayout.x + newLayout.width > width)
				newLayout.x = width - newLayout.width;
			if (newLayout.x < 0)
				newLayout.x = 0;
			if (newLayout.y + newLayout.height > maxY)
				newLayout.y = maxY - newLayout.height;
			if (newLayout.y < minY)
				newLayout.y = minY;
		}
		// 컨트롤의 width 다를때
		if (oldLayout.width != 0 && oldLayout.width != newLayout.width) {
				// control 작아질때
			if (oldLayout.width > newLayout.width && newLayout.width < getMinWidth(newLayout)) {
				newLayout.width = getMinWidth(newLayout);
				// control 왼쪽으로 커질때
			} else if (oldLayout.x > newLayout.x) {
				if (newLayout.x < 0) {
					newLayout.width = newLayout.x + newLayout.width;
					newLayout.x = 0;
				}
				// control 오른쪽으로 커질때
			} else if (oldLayout.width < newLayout.width) {
				if (newLayout.x + newLayout.width > width)
					newLayout.width = width
							- newLayout.x;
				// Control Width 0보다 작아질때
			} else if (newLayout.x > oldLayout.x + oldLayout.width) {
				return null;
			}
		}
		// control Height 다를때
		if (oldLayout.height != 0 && oldLayout.height != newLayout.height) {
			// control 작아질때
			if (oldLayout.height > newLayout.height && newLayout.height < getMinHeight(newLayout)) {
				newLayout.height = getMinHeight(newLayout);
			// control 위쪽으로 커질때
			} else if (oldLayout.y > newLayout.y) {
				if (newLayout.y <= minY) {
					newLayout.height = newLayout.y + newLayout.height - minY;
					newLayout.y = minY;
				}
				// control 아래쪽으로 커질때
			} else if (oldLayout.height < newLayout.height) {
				if (newLayout.y + newLayout.height > maxY)
					newLayout.height = maxY - newLayout.y;
				// Control Height 0보다 작아질때
			} else if (newLayout.y > oldLayout.y + oldLayout.height) {
				return null;
			}
		}
		// control 최초 생성시 minY 보다 작을때 
		if (oldLayout.width == 0 && newLayout.y < minY) {
			newLayout.y = minY;
		}
		// control 최초 생성시 최소 사이즈보다 작을때
		if (oldLayout.width == 0 && newLayout.width < minSize.x) {
			newLayout.width = minSize.x;
		}
		// control 최소 사이즈보다 작을때
		if (newLayout.width < minSize.x) {
			newLayout.width = minSize.x;
			if (oldLayout.x < newLayout.x)
				newLayout.x = oldLayout.x + oldLayout.width - minSize.x;
		}

		// control 최초 생성시 최소 사이즈보다 작을때
		if (oldLayout.height == 0 && newLayout.height < minSize.y) {
			newLayout.height = minSize.y;
			if (newLayout.y + newLayout.height > maxY)
				newLayout.y = maxY - newLayout.height;
		}
		// control 최소 사이즈보다 작을때
		if (newLayout.height < minSize.y) {
			newLayout.height = minSize.y;
			if (oldLayout.y < newLayout.y)
				newLayout.y = oldLayout.y + oldLayout.height - minSize.y;
		}
		
		return newLayout;
	}
	
	private int getMinWidth(Layout layout) {
		int minWidth = layout.width;
		List<FrameNode> children = getChildrenArray();
		for(int i=0; i<children.size(); i++) {
			FrameNode child = children.get(i);
			Layout childLayout = child.getLayout();
			if(minWidth < childLayout.x + childLayout.width)
				minWidth = childLayout.x + childLayout.width;
		}
		return minWidth;
	}

	private int getMinHeight(Layout layout) {
		int minHeight = layout.height;
		List<FrameNode> children = getChildrenArray();
		for(int i=0; i<children.size(); i++) {
			FrameNode child = children.get(i);
			Layout childLayout = child.getLayout();
			if(minHeight < childLayout.y + childLayout.height)
				minHeight = childLayout.y + childLayout.height;
		}
		return minHeight;
	}

	private int getDefaultMinHeight() {
		return defaultMinHeight[getType()];
	}

	private int getDefaultMinWidth() {
		// TODO Auto-generated method stub
		return defaultMinWidth[getType()];
	}

	public void copyLayout(Layout rect, Layout newLayout) {
		newLayout.dock = rect.dock;
		newLayout.fit = rect.fit;
		newLayout.height = rect.height;
		newLayout.maxDropLineCount = rect.maxDropLineCount;
		newLayout.mode = rect.mode;
		newLayout.style = rect.style;
		newLayout.width = rect.width;
		newLayout.x = rect.x;
		newLayout.y = rect.y;
	}

	public int getModeIndex() {
		if (this instanceof DrawCanvas)
			return getChildrenArray().get(0).mode;
		else
			return mode;
	}

	public String getMode() {
		if (this instanceof DrawCanvas)
			return cszFrmMode[getChildrenArray().get(0).mode];
		else
			return cszFrmMode[mode];
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

    /**
     * @param pID
     */
    protected FrameNode getChild(String id) {
        for(int i=0; i<children.size(); i++) {
            FrameNode child = children.get(i);
            if(child.getName().equals(id))
                return child;
        }
        return null;
    }

	public Layout getLayout() {
		// System.out.println("Mode : " + getModeIndex());
		return this.item.GetLayout(getModeIndex());
	}

	public Layout getLayout(int index) {
		return this.item.GetLayout(index);
	}

    public Layout getLayout(String mode) {
        return this.item.GetLayout(mode);
    }

	public void setType(int type) {
		int oldType = this.item.type;
		this.item.type = type;
		firePropertyChange(PROPERTY_TYPE, oldType,
				this.item.type);
	}

	public int getType() {
		return this.item.type;
	}

	public boolean addChild(FrameNode child, boolean isInsert) {
		boolean b = this.children.add(child);
		if (b) {
			if (!(child instanceof Form || 
					child instanceof PanelFrame || 
					child instanceof Popup || 
					child instanceof DrawCanvas))
				child.setMode(mode);

			child.setParent(this, isInsert);
			getListeners().firePropertyChange(PROPERTY_ADD, null, child);
		}
		return b;
	}

	public boolean removeChild(FrameNode child) {
		boolean b = children.remove(child);
		if (b) {
			Object documents;
			documents = getDocuments();
			if(documents == null)
				return false;

			deleteModelDoc(documents, child);
			
			getListeners().firePropertyChange(PROPERTY_REMOVE, child, null);
		}
		return b;
	}

	private void deleteModelDoc(Object documents, FrameNode model) {
		if(documents instanceof OspForm)
			((OspForm)documents).DeleteRsc(model.getName());
		else if(documents instanceof OspPopup)
			((OspPopup)documents).DeleteRsc(model.getName());
		else if(documents instanceof OspPanel)
			((OspPanel)documents).DeleteRsc(model.getName());
		
		for(int i=0; i<model.children.size(); i++) {
			deleteModelDoc(documents, model.children.get(i));
		}
	}

	public List<FrameNode> getChildrenArray() {
		return this.children;
	}

	public void setParent(FrameNode parent, boolean isInsert) {
		this.parent = parent;

		String name = this.name;
		item.Id = name;
		if(this instanceof DrawCanvas) {
			setName(name);
			return;
		}
		
		if(isInsert)
			insertModelDoc(this, parent);
	}

	private void insertModelDoc(FrameNode model, FrameNode parent) {
		if (!(this instanceof Form) && 
				!(this instanceof PanelFrame) && 
				!(this instanceof Popup)) {
			doc = parent.getDocuments();
			model.item.pID = parent.name;
			model.setLayout(model.getLayout());
			if(doc instanceof OspForm) {
				while (((OspForm)doc).InsertRsc(model.name, model.item, parent.getName()) == -1) {
					model.name = ((OspForm)doc).MakeID(model.item.type, "");
					model.item.Id = model.name;
				}
				
				if(model instanceof ScrollPanel && 
				    (((ScrollPanel)model).getPanelId() == null || ((ScrollPanel)model).getPanelId().isEmpty())) {
					OspResourceManager frame = parent.getFrame();
					if(frame == null)
						return;
                    PANELFRAME_INFO panelInfo = frame.
                            InsertPanel(parent.getScreen(), getFrameModel().item);
                    ((ScrollPanel)model).setPanelId(panelInfo.Id);
                    
                    Enumeration<String> keys = panelInfo.layout.keys();
                    while(keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        if(key != null) {
                            panelInfo.layout.get(key).width = item.layout.get(key).width;
                            panelInfo.layout.get(key).height = item.layout.get(key).height + 100;
                        }
                    }
                    
                    panelInfo.addParentId(getName());
                    ResourceExplorer view = ResourceExplorer.getResourceView();
                    if(view != null)
                        view.refreshTree();
                }
			} else if(doc instanceof OspPanel) {
				while (((OspPanel)doc).InsertRsc(model.name, item, this.parent.getName()) == -1) {
					model.name = ((OspPanel)doc).MakeID(item.type, "");
					model.item.Id = model.name;
				}
			} else if(doc instanceof OspPopup) {
				while (((OspPopup)doc).InsertRsc(model.name, model.item, parent.getName()) == -1) {
					model.name = ((OspPopup)doc).MakeID(model.item.type, "");
					model.item.Id = model.name;
				}
				
                if(this instanceof ScrollPanel && 
                    (((ScrollPanel)model).getPanelId() == null || ((ScrollPanel)model).getPanelId().isEmpty())) {
					OspResourceManager frame = parent.getFrame();
					if(frame == null)
						return;
                    PANELFRAME_INFO panelInfo = frame.
                            InsertPanel(parent.getScreen(), parent.item);
                    ((ScrollPanel)model).setPanelId(panelInfo.Id);
                    
                    Enumeration<String> keys = panelInfo.layout.keys();
                    while(keys.hasMoreElements()) {
                        String key = keys.nextElement();
                        if(key != null) {
                            panelInfo.layout.get(key).width = item.layout.get(key).width;
                            panelInfo.layout.get(key).height = item.layout.get(key).height + 100;
                        }
                    }
                    
                    panelInfo.addParentId(getName());
                    ResourceExplorer view = ResourceExplorer.getResourceView();
                    if(view != null)
                        view.refreshTree();
                }
			}
		}
		
		for(int i=0; i<model.children.size(); i++){
			insertModelDoc(model.children.get(i), model);
		}
	}

	public FrameNode getContainer(String id) {
		FrameNode frame = this;
		
		if(frame != null && !frame.getName().equals(id)) {
			for(int i=0; i<children.size(); i++) {
				frame = children.get(i).getContainer(id);
				if(frame != null && frame.getName().equals(id))
					return frame;
			}
			return null;
		}
		
		return frame;
	}

	public FrameNode getFrameModel() {
		FrameNode frame = this;
		while (frame != null && !(frame instanceof Form ||
				frame instanceof Popup ||
				frame instanceof PanelFrame)) {
			frame = frame.getParent();
		}
		
		return frame;
	}

	private OspResourceManager getFrame() {
		FrameNode frame = this;
		while (frame != null && !(frame instanceof Form ||
				frame instanceof Popup ||
				frame instanceof PanelFrame)) {
			frame = frame.getParent();
		}
		
		if(frame != null && frame instanceof Form)
			return ((Form)frame).editor.m_frame;
		else if(frame != null && frame instanceof Popup)
			return ((Popup)frame).editor.m_Popup;
		else if(frame != null && frame instanceof PanelFrame)
			return ((PanelFrame)frame).editor.m_frame;
		
		return null;
	}

	public FrameNode getParent() {
		return this.parent;
	}

	public void setParentId(String pId) {
		FrameNode frame = parent.getFrameModel();
		FrameNode container = frame.getContainer(pId);
		
//		Point size = new Point(getLayout().width, getLayout().height);
//		if(this instanceof ColorPicker ||
//				this instanceof DatePicker ||
//				this instanceof TimePicker) {
//			Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
//					FrameConst.cszTag[getType()], parent.getScreen());
//			size = info.minSize;
//		}
		Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[getType()], parent.getScreen());
		if(container.getLayout().width < info.minSize.x || container.getLayout().height < info.minSize.y) {
			if(isOpen)
				return;
			if(container instanceof PanelFrame || container instanceof Panel || container instanceof OverlayPanel) {
				isOpen = true;
				MessageDialog.openError(null, FrameConst.cszTag[getType()], 
						"This Control cannot be inserted to the Panel because it is larger than the Panel.");
				isOpen = false;
			} else if(container instanceof Popup) {
				isOpen = true;
				MessageDialog.openError(null, FrameConst.cszTag[getType()], 
						"This Control cannot be inserted to the Popup because it is larger than the Popup.");
				isOpen = false;
			}

			return;
		}
		
		boolean b = parent.removeChild(this);
		if(b) {
			String oldPid = item.pID;
			item.pID = pId;
			container.addChild(this, true);
			firePropertyChange(PROPERTY_PARENT, oldPid, pId);
			
			if(oldPid == null || !oldPid.equals(pId)) {
				Object doc = getDocuments();
				
				if(doc != null && doc instanceof OspForm)
					((OspForm) doc).setDirty(true);
				else if(doc != null && doc instanceof OspPopup)
					((OspPopup) doc).setDirty(true);
		        else if(doc != null && doc instanceof OspPanel)
		            ((OspPanel) doc).setDirty(true);
			}
		}
	}

	public String getParentId() {
		return item.pID;
	}

    /**
     * @param mode
     */
    public void reSize(int mode) {
        Layout childLayout = null;
        Layout panelLayout = getLayout(mode);
        java.util.List<FrameNode> children = getChildrenArray();
        for (int i = 0; i < children.size(); i++) {
            childLayout = children.get(i).getLayout(mode);
            
            if(childLayout.x + childLayout.width > panelLayout.width) {
                panelLayout.width = childLayout.x + childLayout.width;
            } else if(childLayout.y + childLayout.height > panelLayout.height) {
                panelLayout.height = childLayout.y + childLayout.height;
            }
        }
        if(childLayout != null && (childLayout.x + childLayout.width > panelLayout.width ||
            childLayout.y + childLayout.height > panelLayout.height))
            setLayout(panelLayout);
        else
            item.SetLayout(panelLayout);
    }
    
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	public PropertyChangeSupport getListeners() {
		return listeners;
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IPropertySource.class) {
			if (propertySource == null) {
				if (this instanceof Button)
					propertySource = new ButtonPropertySource((Button) this);
				else if (this instanceof Check)
					propertySource = new CheckPropertySource((Check) this);
				else if (this instanceof ColorPicker)
					propertySource = new ColorPickerPropertySource(
							(ColorPicker) this);
				else if (this instanceof EditDate)
					propertySource = new DatePickerPropertySource(
							(EditDate) this);
				else if (this instanceof CustomList)
					propertySource = new CustomListPropertySource(
							(CustomList) this);
				else if (this instanceof DrawCanvas)
					propertySource = new DrawCanvasPropertySource(
							(DrawCanvas) this);
				else if (this instanceof EditArea)
					propertySource = new EditAreaPropertySource(
							(EditArea) this);
				else if (this instanceof EditField)
					propertySource = new EditFieldPropertySource(
							(EditField) this);
				else if (this instanceof Flash)
					propertySource = new FlashControlPropertySource(
							(Flash) this);
				else if (this instanceof Form)
					propertySource = new FormFramePropertySource((Form) this);
				else if (this instanceof IconList)
					propertySource = new IconListPropertySource(
							(IconList) this);
				else if (this instanceof Label)
					propertySource = new LabelPropertySource((Label) this);
				else if (this instanceof com.osp.ide.resource.model.ListControl)
					propertySource = new ListPropertySource(
							(com.osp.ide.resource.model.ListControl) this);
				else if (this instanceof Progress)
					propertySource = new ProgressPropertySource(
							(Progress) this);
				else if (this instanceof ScrollPanel)
					propertySource = new ScrollPanelPropertySource(
							(ScrollPanel) this);
				else if (this instanceof Slider)
					propertySource = new SliderPropertySource((Slider) this);
				else if (this instanceof ExpandableList)
					propertySource = new ExpandableListPropertySource(
							(ExpandableList) this);
				else if (this instanceof GroupedList)
					propertySource = new GroupedListPropertySource(
							(GroupedList) this);
				else if (this instanceof OverlayPanel)
					propertySource = new OverlayPanelPropertySource(
							(OverlayPanel) this);
				else if (this instanceof Panel)
					propertySource = new PanelPropertySource((Panel) this);
				else if (this instanceof SlidableGroupedList)
					propertySource = new SlidableGroupedListPropertySource(
							(SlidableGroupedList) this);
				else if (this instanceof SlidableList)
					propertySource = new SlidableListPropertySource(
							(SlidableList) this);
				else if (this instanceof EditTime)
					propertySource = new TimePickerPropertySource(
							(EditTime) this);
				else if (this instanceof PanelFrame)
					propertySource = new PanelFramePropertySource(
							(PanelFrame) this);
				else if (this instanceof Popup)
					propertySource = new PopupFramePropertySource(
							(Popup) this);
				
				FramePropertyPage propertyPage = null;

				FrameNode frame = getFrameModel();
				if(frame instanceof Form)
					propertyPage = ((Form)frame).editor.getPropertySheetPage();
				else if(frame instanceof PanelFrame)
					propertyPage = ((PanelFrame)frame).editor.getPropertySheetPage();
				else if(frame instanceof Popup)
					propertyPage = ((Popup)frame).editor.getPropertySheetPage();
				
				if(propertyPage != null)
					listeners.addPropertyChangeListener(propertyPage);

			}
			return propertySource;
		}
		return null;
	}

	public boolean contains(FrameNode child) {
		return children.contains(child);
	}

	public String getDock() {
		Layout layout = item.layout.get(cszFrmMode[getModeIndex()]);
		if(layout != null)
			return layout.dock;
		else
			return "";
	}

	public void setDock(String dock) {
		Layout layout = item.layout.get(cszFrmMode[getModeIndex()]);
		if(layout != null)
			layout.dock = dock;
	}

	// private ElementGuide verticalGuide, horizontalGuide;
	public OspElementGuide getHorizontalGuide() {
		return horizontalGuide;
	}

	public OspElementGuide getVerticalGuide() {
		return verticalGuide;
	}

	public void setHorizontalGuide(OspElementGuide elementGuide) {
		horizontalGuide = elementGuide;
	}

	public void setVerticalGuide(OspElementGuide elementGuide) {
		verticalGuide = elementGuide;
	}

	public void setMaxDropLineCount(int count) {
		Layout layout = item.GetLayout(getModeIndex());
		int oldCount = layout.maxDropLineCount;
		layout.maxDropLineCount = count;
		item.SetLayout(layout);

		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = item.GetLayout(LANDCAPE);
		} else {
			otherlayout = item.GetLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		item.SetLayout(otherlayout);
		
		firePropertyChange(PROPERTY_MAXDROPLINECOUNT, oldCount,
				count);
	}

	public void setStyle(int mode, int type, int index) {
		Layout layout = item.GetLayout(getModeIndex());
		String oldStyle = layout.style;
		layout.style = cszStyle[type][index];
		item.SetLayout(layout);

		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = item.GetLayout(LANDCAPE);
		} else {
			otherlayout = item.GetLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		item.SetLayout(otherlayout);
		
		firePropertyChange(PROPERTY_STYLE, oldStyle,
				layout.style);
	}

	public void setStyle(int type, int index) {
		Layout layout = item.GetLayout(getModeIndex());
		String oldStyle = layout.style;
		layout.style = cszStyle[type][index];
		item.SetLayout(layout);

		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = item.GetLayout(LANDCAPE);
		} else {
			otherlayout = item.GetLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		item.SetLayout(otherlayout);
			
		firePropertyChange(PROPERTY_STYLE, oldStyle,
				layout.style);
	}

	public String getStyle() {
		Layout layout = this.item.GetLayout(getModeIndex());
		return layout.style;
	}

	public int getStyleIndex(int type) {
		Layout layout = this.item.GetLayout(getModeIndex());
		for (int i = 0; i < cszStyle[type].length; i++) {
			if (cszStyle[type][i].equals(layout.style))
				return i;
		}
		return 0;
	}
	
	public void setStyle(String style) {
		Layout layout = item.GetLayout(getModeIndex());
		String oldStyle = layout.style;
		layout.style = style;
		item.SetLayout(layout);

		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = item.GetLayout(LANDCAPE);
		} else {
			otherlayout = item.GetLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		item.SetLayout(otherlayout);
		
		firePropertyChange(PROPERTY_STYLE, oldStyle,
				style);
	}

    public void setGroupStyle(int index) {
        String oldStyle = item.groupStyle;
        item.groupStyle = cszGroupStyle[index];

        firePropertyChange(PROPERTY_GROUPSTYLE, oldStyle,
            item.groupStyle);
    }

    public String getGroupStyle() {
        return item.groupStyle;
    }

    public int getGroupStyleIndex() {
        for (int i = 0; i < cszGroupStyle.length; i++) {
            if (cszGroupStyle[i].equals(item.groupStyle))
                return i;
        }
        return 0;
    }
    
    public void setGroupStyle(String style) {
        String oldStyle = item.groupStyle;
        item.groupStyle = style;
        
        firePropertyChange(PROPERTY_GROUPSTYLE, oldStyle,
                style);
    }

	public void setBgStyle(String bgstyle) {
		String oldStyle = item.BgStyle;
		item.BgStyle = bgstyle;
		
		firePropertyChange(PROPERTY_BACKGROUNDSTYLE, oldStyle,
				bgstyle);
	}
	public void setBorderStyle(String borderstyle) {
		String oldBorderStyle = item.BorderStyle;
		item.BorderStyle = borderstyle;
		
		firePropertyChange(PROPERTY_BORDERSTYLE, oldBorderStyle,
				borderstyle);
	}
	
	public void refresh() {
		FrameNode child;
		for (int i = 0; i < children.size(); i++) {
			child = children.get(i);
			child.refresh();
		}
		getListeners()
				.firePropertyChange(PROPERTY_RENAME, "Refresh", getName());
	}

	public void setPart(AbstractGraphicalEditPart part) {
		this.part = part;
	}

	public AbstractGraphicalEditPart getPart() {
		return part;
	}

	public void setCopyItem(ITEM_INFO item) {
		Layout frameLayout;
		FrameNode frame = getFrameModel();
		
		if(frame == null)
			frame = this;
		
		item.copy(this.item);
		name = this.item.Id = frame.MakeID(getType(), "");

		Layout rect = item.GetLayout(mode);
		Layout newLayout = new Layout(rect);
		newLayout.x = newLayout.x + 10;
		newLayout.y = newLayout.y + 10;
		
		frameLayout = frame.getLayout(mode);
		if(newLayout.x + newLayout.width > frameLayout.width)
			newLayout.x -= newLayout.x + newLayout.width - frameLayout.width;
		if(newLayout.y + newLayout.height > frameLayout.height)
			newLayout.y -= newLayout.y + newLayout.height - frameLayout.height;
		
		setLayout(newLayout);
		
		if (mode == PORTRAIT) {
			rect = item.GetLayout(LANDCAPE);
			frameLayout = frame.getLayout(LANDCAPE);
		} else {
			rect = item.GetLayout(PORTRAIT);
			frameLayout = frame.getLayout(PORTRAIT);
		}
		newLayout = new Layout(rect);
		newLayout.x = newLayout.x + 10;
		newLayout.y = newLayout.y + 10;
		
		if(newLayout.x + newLayout.width > frameLayout.width)
			newLayout.x -= newLayout.x + newLayout.width - frameLayout.width;
		if(newLayout.y + newLayout.height > frameLayout.height)
			newLayout.y -= newLayout.y + newLayout.height - frameLayout.height;
		
		setLayout(newLayout);
	}

    /**
     * @return
     */
    public ITEM_INFO getItem() {
        return item;
    }

}
