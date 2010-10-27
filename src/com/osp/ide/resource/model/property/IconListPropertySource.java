package com.osp.ide.resource.model.property;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.common.EditableComboPropertyDescriptor;
import com.osp.ide.resource.common.FileDialogCellEditor;
import com.osp.ide.resource.common.ResourceColorPropertyDescriptor;
import com.osp.ide.resource.documents.OspResourceManager;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.resinfo.ICONLIST_INFO;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resinfo.ICONLIST_INFO.ListItem;

public class IconListPropertySource extends OspNodePropertySource {

    public IconListPropertySource(IconList node) {
        this.node = node;

        ICONLIST_INFO info = node.getItem().clone();
        this.info = info;

        if (this.info != null) {
            int mode = node.getModeIndex();
            Layout rect = node.getLayout(mode);
            Layout newLayout = new Layout(rect);
            this.info.SetLayout(newLayout);
            if (mode == PORTRAIT) {
                rect = node.getLayout(LANDCAPE);
            } else {
                rect = node.getLayout(PORTRAIT);
            }
            newLayout = new Layout(rect);
            this.info.SetLayout(newLayout);

        }

    }

    protected void initDescriptor() {
        properties = new ArrayList<IPropertyDescriptor>();

        initStringId();
        initControlId();

        PropertyDescriptor StyleDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_STYLE, 
                "IconList Style", cszStyle[STYLE_ICONLIST]));
        StyleDescriptor.setCategory(FrameNode.GROUP_STYLE);
        properties.add(StyleDescriptor);

        PropertyDescriptor HeightDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_HEIGHT, "Height"));
        HeightDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(HeightDescriptor);
        
        PropertyDescriptor ParentDescriptor = 
            (new ComboBoxPropertyDescriptor(FrameNode.PROPERTY_PARENT, 
                "Parent ID", controlId));
        ParentDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(ParentDescriptor);

        PropertyDescriptor WidthDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_WIDTH, "Width"));
        WidthDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(WidthDescriptor);

        PropertyDescriptor PointXlDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_POINTX, "X Position"));
        PointXlDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointXlDescriptor);

        PropertyDescriptor PointYDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_POINTY, "Y Position"));
        PointYDescriptor.setCategory(FrameNode.GROUP_LAYOUT);
        properties.add(PointYDescriptor);

        PropertyDescriptor TextColorDescriptor = 
            (new ResourceColorPropertyDescriptor(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT, 
                "Color Of Empty List Text"));
        TextColorDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TextColorDescriptor);

        PropertyDescriptor NameDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_RENAME, "ID"));
        NameDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(NameDescriptor);

        PropertyDescriptor ItemHeightlDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_ITEMHEIGHT, "Item Height"));
        ItemHeightlDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(ItemHeightlDescriptor);

        PropertyDescriptor ItemWidthlDescriptor = 
            (new TextPropertyDescriptor(FrameNode.PROPERTY_ITEMWIDTH, "Item Width"));
        ItemWidthlDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(ItemWidthlDescriptor);

        PropertyDescriptor TextDescriptor = 
            (new EditableComboPropertyDescriptor(FrameNode.PROPERTY_TEXTOFEMPTYLIST, 
                "Text Of Empty List", stringId));
        TextDescriptor.setCategory(FrameNode.GROUP_PROPERTIES);
        properties.add(TextDescriptor);

        // PropertyDescriptor TopMarginlDescriptor = (new
        // TextPropertyDescriptor(
        // TWFrameNode.PROPERTY_TOPMARGIN, "Top Margin"));
        // TopMarginlDescriptor.setCategory("Property");
        // properties.add(TopMarginlDescriptor);

        // PropertyDescriptor LeftMarginDescriptor = (new
        // TextPropertyDescriptor(
        // TWFrameNode.PROPERTY_LEFTMARGIN, "Left Margin"));
        // LeftMarginDescriptor.setCategory("Property");
        // properties.add(LeftMarginDescriptor);

        // PropertyDescriptor HalignDescriptor = (new
        // ComboBoxPropertyDescriptor(
        // TWFrameNode.PROPERTY_HALIGN, "Horizontal Align",
        // cszButtonHAlign));
        // HalignDescriptor.setCategory("Property");
        // properties.add(HalignDescriptor);

        // PropertyDescriptor ValignDescriptor = (new
        // ComboBoxPropertyDescriptor(
        // TWFrameNode.PROPERTY_VALIGN, "Vertical Align", cszButtonVAlign));
        // ValignDescriptor.setCategory("Property");
        // properties.add(ValignDescriptor);

        // int size = ((TWIconList) node).getTextSize();
        // for (int i = 1; i <= size; i++) {
        // PropertyDescriptor itemTextDescriptor = (new TextPropertyDescriptor(
        // TWFrameNode.PROPERTY_ITEMTEXT + i, "Item " + i + " Text"));
        // itemTextDescriptor.setCategory("Property");
        // properties.add(itemTextDescriptor);
        //
        // PropertyDescriptor itemNormalBitmapDescriptor = (new
        // FilePropertyDescriptor(
        // TWFrameNode.PROPERTY_ITEMNORMALBITMAP + i, "Item " + i
        // + " NormalBitmap"));
        // itemNormalBitmapDescriptor.setCategory("Property");
        // properties.add(itemNormalBitmapDescriptor);
        //
        // PropertyDescriptor itemFocusedBitmapDescriptor = (new
        // FilePropertyDescriptor(
        // TWFrameNode.PROPERTY_ITEMFOCUSEDBITMAP + i, "Item " + i
        // + " FocusedBitmap"));
        // itemFocusedBitmapDescriptor.setCategory("Property");
        // properties.add(itemFocusedBitmapDescriptor);
        // }

        // PropertyDescriptor textSizeDescriptor = (new TextPropertyDescriptor(
        // TWFrameNode.PROPERTY_TEXTSIZE, "Text Size"));
        // textSizeDescriptor.setCategory("Property");
        // properties.add(textSizeDescriptor);

    }

    public String getItemWidhtMax() {
        IconList node = (IconList) this.node;
        int nWidth = node.getLayout().width / 2;

        if (node.getItemWidth() < 10)
            node.setItemWidth(10);
        else if (node.getItemWidth() > nWidth)
            node.setItemWidth(nWidth);

        String ItemWidthMax = "Item Width [Range: 10 ~ " + nWidth + "]";
        return ItemWidthMax;
    }

    public String getItemHeightMax() {
        IconList node = (IconList) this.node;
        int nHeight = node.getLayout().height / 2;

        if (node.getItemHeight() < 10)
            node.setItemHeight(10);
        else if (node.getItemHeight() > nHeight)
            node.setItemHeight(nHeight);

        String ItemHeightMax = "Item Height [Range: 10 ~ " + nHeight + "]";
        return ItemHeightMax;
    }

    @Override
    public Object getPropertyValue(Object id) {
        IconList node = (IconList) this.node;

        int minY = getMinY(node);
        if (id.equals(FrameNode.PROPERTY_RENAME)) {
            return node.getName();
        } else if (id.equals(FrameNode.PROPERTY_MODE))
            return node.getLayout().mode;
        else if (id.equals(FrameNode.PROPERTY_PARENT))
            return getControlIndex(node.getParentId());
        else if (id.equals(FrameNode.PROPERTY_STYLE)) {
            return FrameNode.getStyleIndex(STYLE_ICONLIST, node.getLayout().style);
        } else if (id.equals(FrameNode.PROPERTY_POINTX))
            return Integer.toString(node.getLayout().x);
        else if (id.equals(FrameNode.PROPERTY_POINTY))
            return Integer.toString(node.getLayout().y - minY);
        else if (id.equals(FrameNode.PROPERTY_WIDTH))
            return Integer.toString(node.getLayout().width);
        else if (id.equals(FrameNode.PROPERTY_HEIGHT))
            return Integer.toString(node.getLayout().height);
        else if (id.equals(FrameNode.PROPERTY_TYPE))
            return "WINDOW_" + cszCtlType[node.getType()];
        else if (id.equals(FrameNode.PROPERTY_ITEMWIDTH)) {
            return Integer.toString(node.getItemWidth());
        } else if (id.equals(FrameNode.PROPERTY_ITEMHEIGHT)) {
            return Integer.toString(node.getItemHeight());
        } else if (id.equals(FrameNode.PROPERTY_TOPMARGIN)) {
            return Integer.toString(node.getTopMargin());
        } else if (id.equals(FrameNode.PROPERTY_LEFTMARGIN)) {
            return Integer.toString(node.getLeftMargin());
        } else if (id.equals(FrameNode.PROPERTY_HALIGN))
            return node.getHAlign();
        else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
            return node.getVAlign();
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMTEXT) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            return node.getItemText(index);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMNORMALBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            File file = node.getItemNormalBitmap(index);
            if (file == null)
                return FileDialogCellEditor.NONE;
            else
                return file.getName();
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMFOCUSEDBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            File file = node.getItemFocusedBitmap(index);
            if (file == null)
                return FileDialogCellEditor.NONE;
            else
                return file.getName();
        } else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
            return Integer.toString(node.getTextSize());
        } else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            if (node.getTextColor().equals(DEFAULT_COLOR))
                return DEFAULT_COLOR;
            return OspResourceManager.FormatRGB(node.getTextColor()).getRGB();
        } else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
            String text = node.getTextOfEmptyList();

            if (text.indexOf("::") < 0)
                return text;
            else {
                return getStringIndex(text.replace("::", ""));
            }
		} else if(handlerList != null && handlerList.contains(id)) {
		    return 0;
        }

        return null;
    }

    @Override
    public void resetPropertyValue(Object id) {
        ICONLIST_INFO info = (ICONLIST_INFO) this.info;
        IconList node = (IconList) this.node;

        if (id.equals(FrameNode.PROPERTY_RENAME)) {
            node.reName(info.Id);
        } else if (id.equals(FrameNode.PROPERTY_TYPE)) {
            node.setType(info.type);
        } else if (id.equals(FrameNode.PROPERTY_PARENT)) {
            node.setParentId(info.pID);
        } else if (id.equals(FrameNode.PROPERTY_STYLE)) {
            node.setStyle(info.GetLayout(node.getModeIndex()).style);
        } else if (id.equals(FrameNode.PROPERTY_POINTX)) {
            Layout rect = node.getLayout();
            rect.x = info.GetLayout(node.getModeIndex()).x;
            node.setLayout(rect);
        } else if (id.equals(FrameNode.PROPERTY_POINTY)) {
            Layout rect = node.getLayout();
            rect.y = info.GetLayout(node.getModeIndex()).y;
            node.setLayout(rect);
        } else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
            Layout rect = node.getLayout();
            rect.width = info.GetLayout(node.getModeIndex()).width;
            node.setLayout(rect);
        } else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
            Layout rect = node.getLayout();
            rect.height = info.GetLayout(node.getModeIndex()).height;
            node.setLayout(rect);
        } else if (id.equals(FrameNode.PROPERTY_ITEMWIDTH)) {
            if (info.itemWidth < 0)
                node.setItemWidth(0);
            else
                node.setItemWidth(info.itemWidth);
            // Layout rect = node.getLayout();
            // if(rect.width/2 >= info.itemWidth)
            // node.setItemWidth(info.itemWidth);
            // else
            // node.setItemWidth(node.getItemWidth());
        } else if (id.equals(FrameNode.PROPERTY_ITEMHEIGHT)) {
            if (info.itemHeight < 0)
                node.setItemWidth(0);
            else
                node.setItemHeight(info.itemHeight);
            // Layout rect = node.getLayout();
            // if(rect.height/2 >= info.itemHeight)
            // node.setItemHeight(info.itemHeight);
            // else
            // node.setItemHeight(node.getItemHeight());
        } else if (id.equals(FrameNode.PROPERTY_TOPMARGIN)) {
            node.setTopMargin(info.topMargin);
        } else if (id.equals(FrameNode.PROPERTY_LEFTMARGIN)) {
            node.setLeftMargin(info.leftMargin);
        } else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
            node.setHAlign(info.hAlign);
        } else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
            node.setVAlign(info.vAlign);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMTEXT) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            node.setItemText(index, info.listItem.get(index).itemText);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMNORMALBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            ListItem item = info.listItem.get(index);
            if (item == null)
                return;
            File file;
            String path = node.getParent().GetProjectDirectory();
            if (path == null || path.isEmpty())
                file = null;
            else if (item.itemNormalBitmap == null || item.itemNormalBitmap.isEmpty())
                file = null;
            else {
                file = new File(path + item.itemNormalBitmap);
                if (!file.exists())
                    file = null;
            }
            node.setItemNormalBitmap(index, file);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMFOCUSEDBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            ListItem item = info.listItem.get(index);
            if (item == null)
                return;
            File file;
            String path = node.getParent().GetProjectDirectory();
            if (path == null || path.isEmpty())
                file = null;
            else if (item.itemFocusedBitmap == null || item.itemFocusedBitmap.isEmpty())
                file = null;
            else {
                file = new File(path + item.itemFocusedBitmap);
                if (!file.exists())
                    file = null;
            }
            node.setItemFocusedBitmap(index, file);
        } else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
            node.setTextSize(info.textSize);
        } else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            node.setTextColor(info.colorOfEmptyListText);
        } else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
            node.setTextOfEmptyList(info.textOfEmptyList);
        }
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if (isValidate(id, value) == false)
            return;

        IconList node = (IconList) this.node;

        if (id.equals(FrameNode.PROPERTY_RENAME)) {
            node.reName((String) value);
        } else if (id.equals(FrameNode.PROPERTY_PARENT)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            node.setParentId(controlId[(Integer) value]);
        } else if (id.equals(FrameNode.PROPERTY_STYLE)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            String style = cszStyle[STYLE_ICONLIST][(Integer) value];
            node.setStyle(style);
        } else if (id.equals(FrameNode.PROPERTY_POINTX)) {
            try {
                Layout rect = node.getLayout();
                Integer pointx = Integer.parseInt((String) value);
                rect.x = pointx;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_POINTY)) {
            try {
                Layout rect = node.getLayout();
                Integer pointy = Integer.parseInt((String) value);
                int minY = getMinY(node);
                rect.y = pointy + minY;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_WIDTH)) {
            try {
                Layout rect = node.getLayout();
                Integer pointwidth = Integer.parseInt((String) value);
                rect.width = pointwidth;
                node.setLayout(rect);
                // rect = node.getLayout();
                // if(node.getItemWidth() > rect.width/2)
                // node.setItemWidth(rect.width/2);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_HEIGHT)) {
            try {
                Layout rect = node.getLayout();
                Integer pointheight = Integer.parseInt((String) value);
                rect.height = pointheight;
                node.setLayout(rect);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_ITEMWIDTH)) {
            try {
                Layout rect = node.getLayout();
                Integer itemWidth = Integer.parseInt((String) value);

                if (itemWidth < 0)
                    node.setItemWidth(0);
                else if (itemWidth > rect.width)
                    node.setItemWidth(rect.width);
                else
                    node.setItemWidth(itemWidth);

            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_ITEMHEIGHT)) {
            try {
                Layout rect = node.getLayout();
                Integer itemHeight = Integer.parseInt((String) value);

                if (itemHeight < 0)
                    node.setItemHeight(0);
                else if (itemHeight > rect.height)
                    node.setItemHeight(rect.height);
                else
                    node.setItemHeight(itemHeight);

            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_TOPMARGIN)) {
            try {
                Integer topMargin = Integer.parseInt((String) value);
                node.setTopMargin(topMargin);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_LEFTMARGIN)) {
            try {
                Integer leftMargin = Integer.parseInt((String) value);
                node.setLeftMargin(leftMargin);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (id.equals(FrameNode.PROPERTY_HALIGN)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            node.setHAlign(cszHAlign[(Integer) value]);
        } else if (id.equals(FrameNode.PROPERTY_VALIGN)) {
            int index = (Integer) value;
            if (index < 0)
                return;
            node.setVAlign(cszVAlign[(Integer) value]);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMTEXT) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            node.setItemText(index, (String) value);
        } else if (id.equals(FrameNode.PROPERTY_TEXTSIZE)) {
            try {
                Integer textSize = Integer.parseInt((String) value);
                node.setTextSize(textSize);
            } catch (NumberFormatException e) {
                Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " NumberFormatException - " + e.getMessage(), e);
            }
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMNORMALBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            String fileName = (String) value;
            if (fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
                String path = node.getParent().GetProjectDirectory();
                fileName = path + FrameNode.BITMAP_FOLDER + fileName;
            }

            File file = new File(fileName);
            String name = file.getName();
            if (name.equals(FileDialogCellEditor.NONE))
                file = null;
            else {
                Image userImage;
                try {
                    userImage = new Image(null, file.getAbsolutePath());
                    userImage.dispose();
                    userImage = null;
                } catch (SWTException e) {
                    Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " ImageFormatException - " + e.getMessage(), e);
                    return;
                }
            }
            node.setItemNormalBitmap(index, file);
        } else if (((String) id).indexOf(FrameNode.PROPERTY_ITEMFOCUSEDBITMAP) >= 0) {
            String text = (String) id;
            Integer index = Integer.parseInt(text.substring(text.length() - 1));
            String fileName = (String) value;
            if (fileName.indexOf('/') < 0 && fileName.indexOf('\\') < 0) {
                String path = node.getParent().GetProjectDirectory();
                fileName = path + FrameNode.BITMAP_FOLDER + fileName;
            }

            File file = new File(fileName);
            String name = file.getName();
            if (name.equals(FileDialogCellEditor.NONE))
                file = null;
            else {
                Image userImage;
                try {
                    userImage = new Image(null, file.getAbsolutePath());
                    userImage.dispose();
                    userImage = null;
                } catch (SWTException e) {
                    Activator.setErrorMessage("IconListPropertySource.setPropertyValue()", id + " ImageFormatException - " + e.getMessage(), e);
                    return;
                }
            }
            node.setItemFocusedBitmap(index, file);
        } else if (id.equals(FrameNode.PROPERTY_COLOROFEMPTYLISTTEXT)) {
            String newColor;
            if (value instanceof RGB) {
                Color color = new Color(null, (RGB) value);
                newColor = OspResourceManager.ColorToString(color);
                color.dispose();
                color = null;
            } else
                newColor = DEFAULT_COLOR;

            node.setTextColor(newColor);
        } else if (id.equals(FrameNode.PROPERTY_TEXTOFEMPTYLIST)) {
            String text;
            if (value instanceof Integer)
                text = "::" + stringId[(Integer) value];
            else
                text = (String) value;

            node.setTextOfEmptyList(text);
		} else if(handlerList.contains(id)) {
			IPropertyDescriptor property = getPropertyDescriptor(id);
			if(property == null)
				return;
			
            String op = property.getLabelProvider().getText(value);
            operateHandler((String) id, op);
        }
    }
}
