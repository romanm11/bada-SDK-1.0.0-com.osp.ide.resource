package com.osp.ide.resource.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.documents.OspPanel;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.figure.FormFrameFigure;
import com.osp.ide.resource.resinfo.FORMFRAME_INFO;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.SCROLLPANEL_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.resourceexplorer.TreeObject;
import com.osp.ide.resource.string.OspUIString;

public class Form extends FrameNode {
	public static final int DEFAULT_TEXTSIZE = 28;
	public static final int INDICATOR_DEFAULT_HEIGHT = 38;
	public static final int TAB_DEFAULT_HEIGHT = 107;
	public static final int TAB_MIN_WIDTH = 7;
	public static final int TAB_MIN_HEIGHT = 20;
	public static final int TITLE_DEFAULT_WIDTH = 480;
	public static final int TITLE_DEFAULT_HEIGHT = 66;
	public static final int SOFTKEY0_DEFAULT_WIDTH = 91;
	public static final int SOFTKEY0_DEFAULT_HEIGHT = 76;
	public static final int OPTION_DEFAULT_WIDTH = 88;
	public static final int OPTION_DEFAULT_HEIGHT = 28;
	private int dimension;
	public OspFormEditor editor;
	private FormFrameFigure figure;

	public Form(OspFormEditor editor, OspForm scene, String id,
			int modeIndex) {
		super();
		this.editor = editor;
		item = scene.m_infoScene;
		setDocuments(scene);
		setName(id);
		setMode(modeIndex);
	}

	public String makeClassName() {
		String className = getName();
		
		className = className.replace("IDF_", "");
		className = className.toLowerCase(Locale.getDefault());
		className = Character.toUpperCase(className.charAt(0)) + className.substring(1);
		
		return className;
	}

	public FORMFRAME_INFO getItem() {
		return (FORMFRAME_INFO) this.item;
	}

	public List<Integer> getChildrenTypeList() {
		ArrayList<Integer> childrenTypeList = new ArrayList<Integer>();
		for(int i=0; i<children.size(); i++) {
			FrameNode child = children.get(i);
			addChildrenType(childrenTypeList, child);
		}
		
		return childrenTypeList;
	}

	private void addChildrenType(ArrayList<Integer> childrenTypeList,
			FrameNode model) {
		if(!childrenTypeList.contains(model.getType()))
			childrenTypeList.add(model.getType());
		
		for(int i=0; i<model.getChildrenArray().size(); i++) {
			FrameNode child = model.getChildrenArray().get(i);
			addChildrenType(childrenTypeList, child);
		}
	}

	public OspUIString getString() {
		if(editor != null)
			return editor.m_string;
		else
			return null;
	}

	public void setDimension(int dim) {
		int oldDim = dimension;
		dimension = dim;
		getListeners().firePropertyChange(PROPERTY_DIMENSION, oldDim, dim);
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return ((FORMFRAME_INFO) item).title;
	}

	public void setTitle(String title) {
		String oldTitle = ((FORMFRAME_INFO) item).title;
		if (!title.isEmpty() && getTitleLayoutStyle() == TreeObject.BOOL_FALSE)
			setTitleLayoutStyle(TreeObject.BOOL_TRUE);
		((FORMFRAME_INFO) item).title = title;
		
		if(((FORMFRAME_INFO) item).title != null && 
		    !((FORMFRAME_INFO) item).title.isEmpty() &&
		    isTitleStyle() == false)
            setTitleLayoutStyle(BOOL_TRUE);

		firePropertyChange(PROPERTY_FRAMETITLE, oldTitle, title);
	}

	public int getSoftkeyStyle() {
		// TODO Auto-generated method stub
		if (((FORMFRAME_INFO) item).softkeyStyle
				.equals(SOFTKEY_STYLE[SOFTKEY_STYLE_BACK]))
			return SOFTKEY_STYLE_BACK;
		return SOFTKEY_STYLE_NORMAL;
	}

	public void setSoftkeyStyle(String softkeyStyle) {
		String oldStyle = ((FORMFRAME_INFO) item).softkeyStyle;
		((FORMFRAME_INFO) item).softkeyStyle = softkeyStyle;

		firePropertyChange("", oldStyle, softkeyStyle);
	}

	public void setIndicatorLayoutStyle(int indicator) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (indicator == TreeObject.BOOL_TRUE) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0) {
				layout.style += "|"
						+ cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR];
			}
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 2)
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR], "");
				else
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR], "");
			}

		}

		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_INDICATORLAYOUTSTYLE,
				oldStyle, layout.style);
	}

	public int getIndicatorLayoutStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public int getIndicatorLayoutStyle(int mode) {
		Layout layout = getLayout(mode);
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public boolean isIndicatorStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0)
			return false;
		else
			return true;
	}

	public boolean isIndicatorStyle(int mode) {
		Layout layout = getLayout(mode);
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_INDICATOR]) < 0)
			return false;
		else
			return true;
	}

	public void setTitleLayoutStyle(int title) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (title == TreeObject.BOOL_TRUE) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_TITLE];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 0) {
				layout.style += "|" + cszStyle[STYLE_FORM][FORM_STYLE_TITLE];
			}
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 2)
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_TITLE], "");
				else
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_TITLE], "");
			}

            ((FORMFRAME_INFO) item).title = "";
            ((FORMFRAME_INFO) item).titleIcon = "";
		}
		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_TITLELAYOUTSTYLE, oldStyle,
				layout.style);
	}

	public int getTitleLayoutStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public boolean isTitleStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 0)
			return false;
		else
			return true;
	}

	public boolean isTitleStyle(int mode) {
		Layout layout = getLayout(mode);
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TITLE]) < 0)
			return false;
		else
			return true;
	}

	public void setSoftkey0LayoutStyle(int softkey) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (softkey == TreeObject.BOOL_TRUE) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) < 0) {
				layout.style += "|" + cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0];
			}
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) < 2)
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0], "");
				else
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0], "");
			}
			
			((FORMFRAME_INFO) item).softkey0 = "";
			((FORMFRAME_INFO) item).softkey0NIcon = "";
			((FORMFRAME_INFO) item).softkey0PIcon = "";
		}
		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_SOFTKEY0LAYOUTSTYLE,
				oldStyle, layout.style);
	}

	public int getSoftkey0LayoutStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public boolean isSoftKey0Style() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) < 0)
			return false;
		else
			return true;
	}

	public void setSoftkey1LayoutStyle(int softkey) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (softkey == TreeObject.BOOL_TRUE) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) < 0) {
				layout.style += "|" + cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1];
			}
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) < 2)
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1], "");
				else
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1], "");
			}
			
            ((FORMFRAME_INFO) item).softkey1 = "";
            ((FORMFRAME_INFO) item).softkey1NIcon = "";
            ((FORMFRAME_INFO) item).softkey1PIcon = "";
		}
		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_SOFTKEY1LAYOUTSTYLE,
				oldStyle, layout.style);
	}

	public int getSoftkey1LayoutStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public boolean isSoftKey1Style() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) < 0)
			return false;
		else
			return true;
	}

	public boolean isSoftKeyStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY0]) < 0
				&& layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_SOFTKEY1]) < 0
				&& layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) < 0)
			return false;
		else
			return true;
	}

	public void setOptionkeyLayoutStyle(int softkey) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (softkey == TreeObject.BOOL_TRUE) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) < 0) {
				layout.style += "|"
						+ cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY];
			}
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) < 2)
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY], "");
				else
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY], "");
			}

		}
		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_OPTIONKEYLAYOUTSTYLE,
				oldStyle, layout.style);
	}

	public int getOptionkeyLayoutStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) < 0)
			return TreeObject.BOOL_FALSE;
		else
			return TreeObject.BOOL_TRUE;
	}

	public boolean isOptionKeyStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_OPTIONKEY]) < 0)
			return false;
		else
			return true;
	}

	public void setTabLayoutStyle(int showTab) {
		Layout layout = getLayout();
		String oldStyle = layout.style;
		if (showTab == SHOWTAB_TEXT) {
			if (layout.style.isEmpty())
				layout.style = cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB];
			else if (layout.style
					.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 0) {
				layout.style += "|" + cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB];
			}
		} else if (showTab == SHOWTAB_ICON) {
	            if (layout.style.isEmpty())
	                layout.style = cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB];
	            else if (layout.style
	                    .indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 0) {
	                layout.style += "|" + cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB];
	            }
		} else {
			if (!layout.style.isEmpty()) {
				if (layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 2) {
					layout.style = layout.style.replace(
							cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB], "");
				} else {
					layout.style = layout.style.replace("|"
							+ cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB], "");
				}
				
                if (layout.style
                        .indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 2) {
                    layout.style = layout.style.replace(
                            cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB], "");
                } else {
                    layout.style = layout.style.replace("|"
                            + cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB], "");
                }
			}

		}
		while (!layout.style.isEmpty() && layout.style.charAt(0) == '|')
			layout.style = layout.style.substring(1);
		setLayout(layout);
		
		Layout otherlayout;
		if(layout.mode.equals(cszFrmMode[PORTRAIT])) {
			otherlayout = getLayout(LANDCAPE);
		} else {
			otherlayout = getLayout(PORTRAIT);
		}
		otherlayout.style = layout.style;
		setLayout(otherlayout);
		
		firePropertyChange(PROPERTY_TABLAYOUTSTYLE,
				oldStyle, layout.style);
	}

	public int getTabLayoutStyle() {
		if (isTextTabStyle())
			return SHOWTAB_TEXT;
		else if (isIconTabStyle())
            return SHOWTAB_ICON;
		else
			return SHOWTAB_NONE;
	}

	public boolean isTextTabStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 0)
			return false;
		else
			return true;
	}

	public boolean isIconTabStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 0)
			return false;
		else
			return true;
	}

	public boolean isTabStyle() {
		Layout layout = getLayout();
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 0
				&& layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 0)
			return false;
		else
			return true;
	}

	public boolean isTabStyle(int mode) {
		Layout layout = getLayout(mode);
		if (layout.style.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_TEXTTAB]) < 0
				&& layout.style
						.indexOf(cszStyle[STYLE_FORM][FORM_STYLE_ICONTAB]) < 0)
			return false;
		else
			return true;
	}

	public void setBgColor(String color) {
		String oldColor = ((FORMFRAME_INFO) item).bg;
		((FORMFRAME_INFO) item).bg = color;
		
		for(int i=0; i<children.size(); i++) {
		    FrameNode child = children.get(i);
		    if(child instanceof ScrollPanel) {
		        SCROLLPANEL_INFO scrollpanel = (SCROLLPANEL_INFO) child.item;
                Hashtable<String, OspPanel> panels = editor.m_frame.m_Panels.get(getScreen());
                OspPanel panel = panels.get(scrollpanel.panelId);
                if(panel !=null && panel.GetPanelInfo().formColor != color) {
                    panel.GetPanelInfo().formColor = color;
                }
		    }
		}
        OspFormEditor.refreshPanelEditor();

		firePropertyChange(PROPERTY_BGCOLOR, oldColor,
				((FORMFRAME_INFO) item).bg);
	}

	public String getBgColor() {

		return ((FORMFRAME_INFO) item).bg;
	}

	public int getMinY() {
		int minY = 0;

		Tag_info indicatorInfo = ResourceExplorer.getResourceView()
				.getImageInfo(
						FrameConst.cszTag[FrameConst.WINDOW_INDICATOR], editor.screen);
		int indicatorY;
		if(indicatorInfo == null)
			indicatorY = INDICATOR_DEFAULT_HEIGHT;
		else
			indicatorY = indicatorInfo.dftlSize.y;
		
		Tag_info tabInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TAB], editor.screen);
		int tabY;
		Point tabMinSize;
		if(tabInfo == null) {
			tabY = TAB_DEFAULT_HEIGHT;
			tabMinSize = new Point(TAB_MIN_WIDTH, TAB_MIN_HEIGHT);
		} else {
			tabY = tabInfo.dftlSize.y;
			tabMinSize = tabInfo.minSize;
		}
		
		Tag_info titleInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TITLE], editor.screen);
		int titleY;
		Point titleMinSize;
		if(titleInfo == null) {
			titleY = TITLE_DEFAULT_HEIGHT;
			titleMinSize = new Point(TITLE_DEFAULT_WIDTH, TITLE_DEFAULT_HEIGHT);
		} else {
			titleY = titleInfo.dftlSize.y;
			titleMinSize = titleInfo.minSize;
		}

		if (isIndicatorStyle()) {
			minY = indicatorY;
			if (isTabStyle()) {
				minY = indicatorY - tabMinSize.y
						+ tabY - tabMinSize.x;
				if (isTitleStyle())
					minY = indicatorY - tabMinSize.y
							+ tabY - tabMinSize.x
							- titleMinSize.y + titleY
							- titleMinSize.x;
			} else if ((isTitleStyle()))
				minY = indicatorY - titleMinSize.y
						+ titleY - titleMinSize.x;
		} else if (isTabStyle()) {
			minY = tabY - tabMinSize.y - tabMinSize.x;
			if (isTitleStyle())
				minY = tabY - tabMinSize.y
						- titleMinSize.y + titleY
						- titleMinSize.x;
		} else if (isTitleStyle()) {
			minY = titleY - titleMinSize.y
					- titleMinSize.x;
		}

		return minY;
	}

	public int getMinY(int mode) {
		int minY = 0;

		Tag_info indicatorInfo = ResourceExplorer.getResourceView()
				.getImageInfo(
						FrameConst.cszTag[FrameConst.WINDOW_INDICATOR], editor.screen);
		int indicatorY;
		if(indicatorInfo == null)
			indicatorY = INDICATOR_DEFAULT_HEIGHT;
		else
			indicatorY = indicatorInfo.dftlSize.y;
		
		Tag_info tabInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TAB], editor.screen);
		int tabY;
		Point tabMinSize;
		if(tabInfo == null) {
			tabY = TAB_DEFAULT_HEIGHT;
			tabMinSize = new Point(TAB_MIN_WIDTH, TAB_MIN_HEIGHT);
		} else {
			tabY = tabInfo.dftlSize.y;
			tabMinSize = tabInfo.minSize;
		}
		
		Tag_info titleInfo = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[FrameConst.WINDOW_TITLE], editor.screen);
		int titleY;
		Point titleMinSize;
		if(titleInfo == null) {
			titleY = TITLE_DEFAULT_HEIGHT;
			titleMinSize = new Point(TITLE_DEFAULT_WIDTH, TITLE_DEFAULT_HEIGHT);
		} else {
			titleY = titleInfo.dftlSize.y;
			titleMinSize = titleInfo.minSize;
		}

		if (isIndicatorStyle(mode)) {
			minY = indicatorY;
			if (isTabStyle()) {
				minY = indicatorY - tabMinSize.y
						+ tabY - tabMinSize.x;
				if (isTitleStyle())
					minY = indicatorY - tabMinSize.y
							+ tabY - tabMinSize.x
							- titleMinSize.y + titleY
							- titleMinSize.x;
			} else if ((isTitleStyle()))
				minY = indicatorY - titleMinSize.y
						+ titleY - titleMinSize.x;
		} else if (isTabStyle(mode)) {
			minY = tabY - tabMinSize.y - tabMinSize.x;
			if (isTitleStyle())
				minY = tabY - tabMinSize.y
						- titleMinSize.y + titleY
						- titleMinSize.x;
		} else if (isTitleStyle(mode)) {
			minY = titleY - titleMinSize.y
					- titleMinSize.x;
		}

		return minY;
	}

	public void setSoftkey0Text(String value) {
		String oldText = ((FORMFRAME_INFO) item).softkey0;
		((FORMFRAME_INFO) item).softkey0 = value;
		if (value != null && !value.isEmpty()
				&& getSoftkey0LayoutStyle() == TreeObject.BOOL_FALSE)
			setSoftkey0LayoutStyle(TreeObject.BOOL_TRUE);

		firePropertyChange(PROPERTY_SOFTKEY0TEXT, oldText, value);
	}

	public String getSoftkey0Text() {
		return ((FORMFRAME_INFO) item).softkey0;
	}

	public void setSoftkey1Text(String value) {
		String oldText = ((FORMFRAME_INFO) item).softkey1;
		((FORMFRAME_INFO) item).softkey1 = value;
		if (value != null && !value.isEmpty()
				&& getSoftkey1LayoutStyle() == TreeObject.BOOL_FALSE)
			setSoftkey1LayoutStyle(TreeObject.BOOL_TRUE);

		firePropertyChange(PROPERTY_SOFTKEY1TEXT, oldText, value);
	}

	public String getSoftkey1Text() {
		return ((FORMFRAME_INFO) item).softkey1;
	}

	public void setHeaderHeight(int height) {
		((FORMFRAME_INFO) item).headerHeight = height;
	}

	public int getHeaderHeight() {
		return ((FORMFRAME_INFO) item).headerHeight;
	}

	public void setTitleIcon(File icon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FORMFRAME_INFO) item).titleIcon == null
				|| ((FORMFRAME_INFO) item).titleIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FORMFRAME_INFO) item).titleIcon);
			oldValue = oldFile.getName();
		}

		if (icon != null && icon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + icon.getName());
			TreeObject.copyFile(icon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FORMFRAME_INFO) item).titleIcon = "";
		} else {
			newValue = newFile.getName();
			((FORMFRAME_INFO) item).titleIcon = BITMAP_FOLDER
					+ newFile.getName();
		}
		
		if(((FORMFRAME_INFO) item).titleIcon != null && 
		    !((FORMFRAME_INFO) item).titleIcon.isEmpty() &&
		    isTitleStyle() == false)
		    setTitleLayoutStyle(BOOL_TRUE);

		firePropertyChange(PROPERTY_TITLEICON, oldValue,
				newValue);
	}

	public File getTitleIcon() {
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FORMFRAME_INFO) item).titleIcon == null
				|| ((FORMFRAME_INFO) item).titleIcon.isEmpty())
			return null;
		File file = new File(path + ((FORMFRAME_INFO) item).titleIcon);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((FORMFRAME_INFO) item).titleIcon;
			((FORMFRAME_INFO) item).titleIcon = "";
	        firePropertyChange(PROPERTY_TITLEICON, oldValue, "");
			return null;
		}
	}

	public void setHAlign(String hAlign) {
		String oldAlign = ((FORMFRAME_INFO) item).hAlign;
		((FORMFRAME_INFO) item).hAlign = hAlign;
		
		firePropertyChange(PROPERTY_HALIGN, oldAlign, hAlign);
	}

	public int getHAlign() {
		return getHAlignIndex(((FORMFRAME_INFO) item).hAlign);
	}

	public void setSoftKey0NIcon(File icon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FORMFRAME_INFO) item).softkey0NIcon == null
				|| ((FORMFRAME_INFO) item).softkey0NIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FORMFRAME_INFO) item).softkey0NIcon);
			oldValue = oldFile.getName();
		}

		if (icon != null && icon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + icon.getName());
			TreeObject.copyFile(icon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FORMFRAME_INFO) item).softkey0NIcon = "";
		} else {
			newValue = newFile.getName();
			((FORMFRAME_INFO) item).softkey0NIcon = BITMAP_FOLDER
					+ newFile.getName();
		}

		if (((FORMFRAME_INFO) item).softkey0NIcon != null
				&& !((FORMFRAME_INFO) item).softkey0NIcon.isEmpty()
				&& getSoftkey0LayoutStyle() == TreeObject.BOOL_FALSE)
			setSoftkey0LayoutStyle(TreeObject.BOOL_TRUE);

		firePropertyChange(PROPERTY_SOFTKEY0NICON, oldValue,
				newValue);
	}

	public File getSoftKey0NIcon() {
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FORMFRAME_INFO) item).softkey0NIcon == null
				|| ((FORMFRAME_INFO) item).softkey0NIcon.isEmpty())
			return null;
		File file = new File(path + ((FORMFRAME_INFO) item).softkey0NIcon);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((FORMFRAME_INFO) item).softkey0NIcon;
			((FORMFRAME_INFO) item).softkey0NIcon = "";
	        firePropertyChange(PROPERTY_SOFTKEY0NICON, oldValue, "");
			return null;
		}
	}

	public void setSoftKey1NIcon(File icon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FORMFRAME_INFO) item).softkey1NIcon == null
				|| ((FORMFRAME_INFO) item).softkey1NIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FORMFRAME_INFO) item).softkey1NIcon);
			oldValue = oldFile.getName();
		}

		if (icon != null && icon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + icon.getName());
			TreeObject.copyFile(icon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FORMFRAME_INFO) item).softkey1NIcon = "";
		} else {
			newValue = newFile.getName();
			((FORMFRAME_INFO) item).softkey1NIcon = BITMAP_FOLDER
					+ newFile.getName();
		}

		if (((FORMFRAME_INFO) item).softkey1NIcon != null
				&& !((FORMFRAME_INFO) item).softkey1NIcon.isEmpty()
				&& getSoftkey1LayoutStyle() == TreeObject.BOOL_FALSE)
			setSoftkey1LayoutStyle(TreeObject.BOOL_TRUE);

		firePropertyChange(PROPERTY_SOFTKEY1NICON, oldValue,
				newValue);
	}

	public File getSoftKey1NIcon() {
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FORMFRAME_INFO) item).softkey1NIcon == null
				|| ((FORMFRAME_INFO) item).softkey1NIcon.isEmpty())
			return null;
		File file = new File(path + ((FORMFRAME_INFO) item).softkey1NIcon);
		if (file.exists())
			return file;
		else {
		    String oldValue = ((FORMFRAME_INFO) item).softkey1NIcon;
			((FORMFRAME_INFO) item).softkey1NIcon = "";
	        firePropertyChange(PROPERTY_SOFTKEY1NICON, oldValue, "");
			return null;
		}
	}

	public void setSoftKey0PIcon(File icon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FORMFRAME_INFO) item).softkey0PIcon == null
				|| ((FORMFRAME_INFO) item).softkey0PIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FORMFRAME_INFO) item).softkey0PIcon);
			oldValue = oldFile.getName();
		}

		if (icon != null && icon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + icon.getName());
			TreeObject.copyFile(icon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FORMFRAME_INFO) item).softkey0PIcon = "";
		} else {
			newValue = newFile.getName();
			((FORMFRAME_INFO) item).softkey0PIcon = BITMAP_FOLDER
					+ newFile.getName();
		}

        if (((FORMFRAME_INFO) item).softkey0PIcon != null
                && !((FORMFRAME_INFO) item).softkey0PIcon.isEmpty()
                && getSoftkey0LayoutStyle() == TreeObject.BOOL_FALSE)
            setSoftkey0LayoutStyle(TreeObject.BOOL_TRUE);
            
		firePropertyChange(PROPERTY_SOFTKEY0PICON, oldValue,
				newValue);
	}
	public void setBGColorTransparency(int bgColorOpacity) {
		int oldValue = ((FORMFRAME_INFO) item).bgColorOpacity;
		((FORMFRAME_INFO) item).bgColorOpacity = bgColorOpacity;
		
		firePropertyChange(PROPERTY_BGOPACITY, oldValue,
				bgColorOpacity);
	}

	public int getBGColorTransparency() {
		return ((FORMFRAME_INFO) item).bgColorOpacity;
	}

	public File getSoftKey0PIcon() {
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FORMFRAME_INFO) item).softkey0PIcon == null
				|| ((FORMFRAME_INFO) item).softkey0PIcon.isEmpty())
			return null;
		File file = new File(path + ((FORMFRAME_INFO) item).softkey0PIcon);
		if (file.exists())
			return file;
		else {
			((FORMFRAME_INFO) item).softkey0PIcon = "";
			return null;
		}
	}

	public void setSoftKey1PIcon(File icon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((FORMFRAME_INFO) item).softkey1PIcon == null
				|| ((FORMFRAME_INFO) item).softkey1PIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((FORMFRAME_INFO) item).softkey1PIcon);
			oldValue = oldFile.getName();
		}

		if (icon != null && icon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + icon.getName());
			TreeObject.copyFile(icon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((FORMFRAME_INFO) item).softkey1PIcon = "";
		} else {
			newValue = newFile.getName();
			((FORMFRAME_INFO) item).softkey1PIcon = BITMAP_FOLDER
					+ newFile.getName();
		}

        if (((FORMFRAME_INFO) item).softkey1PIcon != null
                && !((FORMFRAME_INFO) item).softkey1PIcon.isEmpty()
                && getSoftkey1LayoutStyle() == TreeObject.BOOL_FALSE)
                setSoftkey1LayoutStyle(TreeObject.BOOL_TRUE);
            
		firePropertyChange(PROPERTY_SOFTKEY1PICON, oldValue,
				newValue);
	}

	public File getSoftKey1PIcon() {
		String path = editor.m_frame.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((FORMFRAME_INFO) item).softkey1PIcon == null
				|| ((FORMFRAME_INFO) item).softkey1PIcon.isEmpty())
			return null;
		File file = new File(path + ((FORMFRAME_INFO) item).softkey1PIcon);
		if (file.exists())
			return file;
		else {
			((FORMFRAME_INFO) item).softkey1PIcon = "";
			return null;
		}
	}
	
	public void refreshChildren() {
		refresh();
		if (editor != null && editor.getPropertySheetPage() != null)
			editor.getPropertySheetPage().refresh();
	}

	public void setOrientation(int orientation) {
	    String oldValue = ((FORMFRAME_INFO) item).orientation;
		((FORMFRAME_INFO) item).orientation = cszOrientation[orientation];
		
		firePropertyChange(PROPERTY_RENAME, oldValue, cszOrientation[orientation]);
	}

	public void setOrientation(String orientation) {
         String oldValue = ((FORMFRAME_INFO) item).orientation;
		((FORMFRAME_INFO) item).orientation = orientation;
        
        firePropertyChange(PROPERTY_RENAME, oldValue, orientation);
	}

	public int getOrientation() {
		int ret = 0;
		for (int i = 0; i < cszOrientation.length; i++) {
			if (((FORMFRAME_INFO) item).orientation.equals(cszOrientation[i]))
				return i;
		}
		return ret;
	}

	public void setFigure(IFigure figure) {
		this.figure = (FormFrameFigure) figure;
	}

	public FormFrameFigure getFigure() {
		return figure;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}

}
