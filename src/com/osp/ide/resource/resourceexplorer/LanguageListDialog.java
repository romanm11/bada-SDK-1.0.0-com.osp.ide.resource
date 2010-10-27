package com.osp.ide.resource.resourceexplorer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import com.osp.ide.resource.common.LanguageData;
import com.osp.ide.resource.common.LanguageListXmlStore;
import com.osp.ide.resource.string.OspStringEditor;
import com.osp.ide.resource.string.OspStringMarkup;
import com.osp.ide.resource.string.OspUIString;

/**
 * This class demonstrates how to create your own dialog classes. It allows
 * users to input a String
 */
public class LanguageListDialog extends Dialog {

    class ViewThread extends Thread {
        List list;

        public ViewThread(List list) {
            this.list = list;
        }

        public void run() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(3000);
                }
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    // dialog °á°ú°ª
    public static final String TITLE = "Select Language";
//    private static final String MARK_DEFAULT = " - default";
    boolean result = false;

    private Shell sShell = null; // @jve:decl-index=0:visual-constraint="3,10"
    private List langList = null;
    private Button btnAdd = null;
    private Button btnDelete = null;
    private Button btnOK = null;
    private Button btnCancel = null;
    private Group group;
    private OspUIString m_strings;
    private List selectList;

//    private Button btnDefault;
//    private String m_SelectedName = "";

    // /////////////////////////////////////////////////////

    public LanguageListDialog(Shell parent, OspUIString strings) {
        super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        m_strings = strings;
    }

    public LanguageListDialog(Shell parent, OspUIString strings, String selectedName) {
        super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        m_strings = strings;
//        m_SelectedName = selectedName;
    }

    public boolean open() {
        sShell = new Shell(getParent(), getStyle());
        sShell.setText(TITLE);
        createContents(sShell);
        sShell.setBounds(0, 0, 440, 345);
        sShell.pack();
        Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
        sShell.setSize(size.x + 5, size.y + 5);

        Monitor primary = Display.getCurrent().getPrimaryMonitor();
        org.eclipse.swt.graphics.Rectangle bound = primary.getBounds();
        org.eclipse.swt.graphics.Rectangle rect = sShell.getBounds();

        int x = bound.x + (bound.width - rect.width) / 2;
        int y = bound.y + (bound.height - rect.height) / 2;

        sShell.setLocation(x, y);
        sShell.open();

        Display display = getParent().getDisplay();
        while (!sShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return result;
    }

    private void createContents(final Shell shell) {

        group = new Group(sShell, SWT.NONE);
        group.setText("Select Language");
        group.setLayout(null);
        group.setBounds(new Rectangle(5, 5, 470, 420));

        langList = new List(group, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        langList.setBounds(new Rectangle(10, 15, 210, 360));
        setLangListEntry();

        selectList = new List(group, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        selectList.setBounds(new Rectangle(250, 15, 210, 360));
        setSelectListEntry();

//        selectList.addMouseListener(new MouseListener() {
//
//            @Override
//            public void mouseDoubleClick(MouseEvent e) {
//                String lang = selectList.getItem(selectList.getFocusIndex());
//                setDefaultLang(lang);
//            }
//
//            @Override
//            public void mouseDown(MouseEvent e) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void mouseUp(MouseEvent e) {
//                // TODO Auto-generated method stub
//
//            }
//        });

        btnAdd = new Button(group, SWT.NONE);
        btnAdd.setBounds(new Rectangle(224, 116, 22, 24));
        btnAdd.setText(">>");
        btnAdd.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                String[] selections = langList.getSelection();
                for (int i = 0; i < selections.length; i++) {
                    String lang = selections[i];
                    langList.remove(lang);
                    if (selectList.indexOf(lang) < 0) {
                        selectList.add(lang);
                    }
                }

                if (selections.length > 0)
                    listSorting(selectList);
                langList.deselectAll();
                ViewThread thread = new ViewThread(langList);
                thread.start();
            }
        });

        btnDelete = new Button(group, SWT.NONE);
        btnDelete.setBounds(new Rectangle(224, 142, 22, 24));
        btnDelete.setText("<<");
        btnDelete.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                String[] selections = selectList.getSelection();
                for (int i = 0; i < selections.length; i++) {
//                    if (selectList.indexOf(selections[i]) < 0 || selections[i].indexOf(MARK_DEFAULT) >= 0)
//                        continue;
                    if (selectList.getItemCount() <= 1)
                    	continue;

                    String lang = selections[i];
                    selectList.remove(lang);
                    if (langList.indexOf(lang) < 0) {
                        langList.add(lang);
                    }
                }

                if (selections.length > 0)
                    listSorting(langList);
                TreeObject.refreshProject();
            }
        });

        btnOK = new Button(group, SWT.NONE);
        btnOK.setBounds(new Rectangle(160, 380, 70, 30));
        btnOK.setText("Ok");
        btnOK.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Hashtable<String, String> languages = new Hashtable<String, String>();
                String[] items = selectList.getItems();
                for (int i = 0; i < items.length; i++) {
                    if (OspStringMarkup.getLanguageId(items[i]) != null)
                        languages.put(OspStringMarkup.getLanguageId(items[i]), items[i]);
                }

//                m_strings.resetStrings(languages, m_SelectedName);
                m_strings.resetStrings(languages, "");

                OspStringEditor.refreshFrameEditor();

                result = true;
                shell.close();
            }
        });

        btnCancel = new Button(group, SWT.NONE);
        btnCancel.setBounds(new Rectangle(242, 380, 70, 30));
        btnCancel.setText("Cancel");
        btnCancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                result = false;
                shell.close();
            }
        });

//        btnDefault = new Button(group, SWT.NONE);
//        btnDefault.setBounds(new Rectangle(390, 380, 70, 22));
//        btnDefault.setText("Set default");
//        btnDefault.setToolTipText("Set default language");
//        btnDefault.addSelectionListener(new SelectionAdapter() {
//            public void widgetSelected(SelectionEvent event) {
//                int[] indices = selectList.getSelectionIndices();
//                if (indices.length > 0) {
//                    String lang = selectList.getItem(indices[0]);
//                    setDefaultLang(lang);
//                }
//            }
//        });

    }

    /**
     * @param lang
     */
//    protected void setDefaultLang(String lang) {
//        if (selectList.indexOf(lang) >= 0 && lang.indexOf(MARK_DEFAULT) < 0) {
//            if (lang != null && !lang.equals(m_SelectedName)) {
//                selectList.remove(lang);
//
//                if (selectList.indexOf(m_SelectedName + MARK_DEFAULT) >= 0)
//                    selectList.remove(m_SelectedName + MARK_DEFAULT);
//                if (selectList.indexOf(m_SelectedName) < 0 && !m_SelectedName.isEmpty())
//                    selectList.add(m_SelectedName);
//
//                m_SelectedName = lang;
//                lang = lang + MARK_DEFAULT;
//                if (selectList.indexOf(lang) < 0)
//                    selectList.add(lang);
//
//                if (selectList.getItemCount() > 0)
//                    listSorting(selectList);
//            }
//        }
//    }

    protected void listSorting(List list) {
        if (list == null || list.isDisposed())
            return;

        Vector<String> items = new Vector<String>();
        for (int i = 0; i < list.getItemCount(); i++)
            items.add(list.getItem(i));

        Collections.sort(items);
        list.removeAll();

        for (int i = 0; i < items.size(); i++) {
            if (list.indexOf(items.elementAt(i)) < 0)
                list.add(items.elementAt(i));
        }

//        String lang = m_SelectedName + MARK_DEFAULT;
//        selectList.setSelection(selectList.indexOf(lang));
    }

    private void setLangListEntry() {
        String id = "", name = "";
        if (langList == null || langList.isDisposed())
            return;

        langList.removeAll();

        Vector<String> selectedList = m_strings.getValueSortedKeys(false);
        ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore().getLanguageList();
        for (int i = 0; i < cszLanguage.size(); i++) {
            id = cszLanguage.get(i).getId();
            name = cszLanguage.get(i).getName();
            if (langList.indexOf(name) < 0 && !selectedList.contains(id))
                langList.add(name);
        }
    }

    private void setSelectListEntry() {
        if (selectList == null || selectList.isDisposed())
            return;

        Enumeration<String> keys = m_strings.getValueSortedKeys(false).elements();
        selectList.removeAll();

        if (m_strings.m_Languages.size() == 0)
            selectList.add(OspStringMarkup.getLanguageName(OspUIString.DEFAULT_LANG1));
        ArrayList<LanguageData> cszLanguage = new LanguageListXmlStore().getLanguageList();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            int index = OspStringMarkup.getLangIdToIndex(key);
            if (index < 0 || index >= cszLanguage.size())
                continue;

            key = cszLanguage.get(OspStringMarkup.getLangIdToIndex(key)).getName();
            if (selectList.indexOf(key) < 0)
                selectList.add(key);
        }

//        setDefaultLang(OspUIString.DEFAULT_LANG1NAME);
    }
}
