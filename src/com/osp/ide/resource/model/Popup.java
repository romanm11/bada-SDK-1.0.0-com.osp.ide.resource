package com.osp.ide.resource.model;

import java.io.File;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;

import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.documents.OspPopup;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.figure.PopupFrameFigure;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.POPUPFRAME_INFO;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.resourceexplorer.TreeObject;
import com.osp.ide.resource.string.OspUIString;

public class Popup extends FrameNode {
	private static final int POPUP_MIN_WIDTH = 388;
	private static final int POPUP_MIN_HEIGHT = 130;
	public static final int POPUP_TITLE_HEIGHT = 62;
	private int dimension;
	public OspPopupEditor editor;
	private PopupFrameFigure figure;

	public Popup(OspPopupEditor editor, OspPopup popup, String id,
			int modeIndex) {
		super();
		this.editor = editor;
		item = popup.m_infoPopup;
		setDocuments(popup);
		setName(id);
		setMode(modeIndex);
	}

	// public void setItem(FRAME_INFO item) {
	// this.item = item;
	// }

	public POPUPFRAME_INFO getItem() {
		return (POPUPFRAME_INFO) this.item;
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

	public void setTitleIcon(File titleIcon) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_Popup.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((POPUPFRAME_INFO) item).titleIcon == null
				|| ((POPUPFRAME_INFO) item).titleIcon.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((POPUPFRAME_INFO) item).titleIcon);
			oldValue = oldFile.getName();
		}

		if (titleIcon != null && titleIcon.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + titleIcon.getName());
			TreeObject.copyFile(titleIcon, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((POPUPFRAME_INFO) item).titleIcon = "";
		} else {
			newValue = newFile.getName();
			((POPUPFRAME_INFO) item).titleIcon = BITMAP_FOLDER + newFile.getName();
		}

		firePropertyChange(PROPERTY_TITLEICON, oldValue, newValue);
	}

	public File getTitleIcon() {
		String path = editor.m_Popup.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((POPUPFRAME_INFO) item).titleIcon == null
				|| ((POPUPFRAME_INFO) item).titleIcon.isEmpty())
			return null;
		File file = new File(path + ((POPUPFRAME_INFO) item).titleIcon);
		if (file.exists())
			return file;
		else {
			((POPUPFRAME_INFO) item).titleIcon = "";
			return null;
		}
	}

	public void setBitmap(File bitmap) {
		String oldValue, newValue;
		File oldFile, newFile = null;
		String path = editor.m_Popup.getProjectDirectory();
		if (path == null || path.isEmpty())
			return;

		if (((POPUPFRAME_INFO) item).bitmap == null
				|| ((POPUPFRAME_INFO) item).bitmap.isEmpty())
			oldValue = "";
		else {
			oldFile = new File(path + ((POPUPFRAME_INFO) item).bitmap);
			oldValue = oldFile.getName();
		}

		if (bitmap != null && bitmap.exists()) {
			newFile = new File(path + BITMAP_FOLDER);
			if (!newFile.exists())
				newFile.mkdir();
			newFile = new File(path + BITMAP_FOLDER + bitmap.getName());
			TreeObject.copyFile(bitmap, newFile);
		}

		if (newFile == null || !newFile.exists()) {
			newValue = "";
			((POPUPFRAME_INFO) item).bitmap = "";
		} else {
			newValue = newFile.getName();
			((POPUPFRAME_INFO) item).bitmap = BITMAP_FOLDER + newFile.getName();
		}

		firePropertyChange(PROPERTY_BITMAP, oldValue, newValue);
	}

	public File getBitmap() {
		String path = editor.m_Popup.getProjectDirectory();
		if (path == null || path.isEmpty())
			return null;

		if (((POPUPFRAME_INFO) item).bitmap == null
				|| ((POPUPFRAME_INFO) item).bitmap.isEmpty())
			return null;
		File file = new File(path + ((POPUPFRAME_INFO) item).bitmap);
		if (file.exists())
			return file;
		else {
			((POPUPFRAME_INFO) item).bitmap = "";
			return null;
		}
	}

	// public void setBgColor(Color color) {
	// Color oldColor = ((FRAME_INFO) item).bg;
	// ((FRAME_INFO) item).bg = color;
	// getListeners().firePropertyChange(PROPERTY_BGCOLOR, oldColor,
	// ((FRAME_INFO) item).bg);
	// }
	//
	// public Color getBgColor() {
	//		
	// return ((FRAME_INFO) item).bg;
	// }

	public void refreshChildren() {
		refresh();
		if (editor != null && editor.getPropertySheetPage() != null)
			editor.getPropertySheetPage().refresh();
	}

	public void setFigure(IFigure figure) {
		this.figure = (PopupFrameFigure) figure;
	}

	public PopupFrameFigure getFigure() {
		return figure;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return null;
	}

	@Override
	public void setLayout(Layout newLayout) {

		Point minSize = new Point();
		ResourceExplorer view = ResourceExplorer.getResourceView();
		Tag_info tag = view.getImageInfo(cszTag[WINDOW_POPUP],
				editor.screen);
		if(tag != null && tag.minSize != null)
			minSize = tag.minSize;

		if(minSize.x <= 0)
			minSize.x = POPUP_MIN_WIDTH;
		if(minSize.y <= 0)
			minSize.y = POPUP_MIN_HEIGHT;
		
		if(newLayout.width < minSize.x)
			newLayout.width = minSize.x;
		
		if(newLayout.height < minSize.y)
			newLayout.height = minSize.y;
		
        super.setLayout(newLayout);
        
//        Layout layout;
//        if (newLayout.mode.equals(cszFrmMode[PORTRAIT])) {
//            layout = getItem().GetLayout(FrameConst.LANDCAPE);
//        } else if (newLayout.mode.equals(cszFrmMode[LANDCAPE])) {
//            layout = getItem().GetLayout(FrameConst.PORTRAIT);
//        } else
//            return;
//        
//        layout.height = newLayout.width;
//        layout.width = newLayout.height;
//        getItem().SetLayout(layout);
	}

    /**
     * @param mode
     */
    public void reSize(String mode) {
        reSize(OspPopupEditor.getModeIndex(mode));
    }

    /**
     * @param mode
     */
    public void reSize(int mode) {
        Layout childLayout = null;
        Layout popupLayout = getLayout(mode);
        java.util.List<FrameNode> children = getChildrenArray();
        for (int i = 0; i < children.size(); i++) {
            childLayout = children.get(i).getLayout(mode);
            
            if(childLayout.x + childLayout.width > popupLayout.width) {
                popupLayout.width = childLayout.x + childLayout.width;
            } else if(childLayout.y + childLayout.height > popupLayout.height) {
                popupLayout.height = childLayout.y + childLayout.height;
            }
        }
        if(childLayout != null && (childLayout.x + childLayout.width > popupLayout.width ||
            childLayout.y + childLayout.height > popupLayout.height))
            setLayout(popupLayout);
        else
            item.SetLayout(popupLayout);
    }
    
    /* (non-Javadoc)
     * @see com.osp.ide.resource.model.FrameNode#addChild(com.osp.ide.resource.model.FrameNode)
     */
    @Override
    public boolean addChild(FrameNode child, boolean isInsert) {
        // TODO Auto-generated method stub
        boolean ret = super.addChild(child, isInsert);
        if(child instanceof ColorPicker ||
            child instanceof EditDate ||
            child instanceof EditTime) {
            if(getModeIndex() == PORTRAIT)
                reSize(LANDCAPE);
            else if(getModeIndex() == LANDCAPE)
                reSize(PORTRAIT);
        }
        return ret;
    }

    /**
     * @return
     */
    public int getMinY() {
        if(getTitleText() != null && !getTitleText().isEmpty())
            return POPUP_TITLE_HEIGHT;
        return 0;
    }
    
}
