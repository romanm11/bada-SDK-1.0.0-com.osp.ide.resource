/**
 * 
 */
package com.osp.ide.resource.common;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author SRD1_CHJ
 * 
 */
@SuppressWarnings("restriction")
public class ResourceColorDialog extends ColorDialog {

    int x, y;
    int width, height;

    /**
     * @param parent
     */
    public ResourceColorDialog(Shell parent) {
        super(parent);
    }

    int CCHookProc(int hdlg, int uiMsg, int lParam, int lpData) {
        switch ((int) /* 64 */uiMsg) {
        case OS.WM_INITDIALOG: {
            RECT rect = new RECT();
            OS.GetWindowRect(hdlg, rect);
            width = rect.right - rect.left;
            height = rect.bottom - rect.top;
            
            Rectangle parentRect = getParent().getBounds();
            x = parentRect.x + (parentRect.width - width)/2;
            y = parentRect.y + (parentRect.height - height)/2;
            String title = getText();
            if (title != null && title.length() != 0) {
                /* Use the character encoding for the default locale */
                TCHAR buffer = new TCHAR(0, title, true);
                OS.SetWindowText(hdlg, buffer);
            }
            OS.SetWindowPos(hdlg, 0, x, y, width, height, 0);
            break;
        }
        case OS.WM_DESTROY: {
            RECT rect = new RECT();
            OS.GetWindowRect(hdlg, rect);
            int newWidth = rect.right - rect.left;
            int newHeight = rect.bottom - rect.top;
            if (newWidth < width || newHeight < height) {
                // display.fullOpen = false;
            } else {
                if (newWidth > width || newHeight > height) {
                    // display.fullOpen = true;
                }
            }
            break;
        }
        }
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Dialog#checkSubclass()
     */
    @Override
    protected void checkSubclass() {
    }

}
