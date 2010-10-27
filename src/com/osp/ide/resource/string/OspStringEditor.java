package com.osp.ide.resource.string;

import java.util.Enumeration;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

@SuppressWarnings("restriction")
public class OspStringEditor extends EditorPart {

	private static final String TEST_ACTION = "Test String Action";
	public static String ID = "com.osp.ide.resource.editor.string.OspStrEditor";
	public OspUIString m_strings;
	String m_id = "";
	Table table;
	Vector<String> m_language;
	boolean isDirty = false;
	private SelectionListener listener;
	protected boolean traverse = false;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		// TODO Auto-generated method stub
		this.setSite(site);
		this.setInput(input);
	}

	@Override
	protected void setInput(IEditorInput input) {
		// TODO Auto-generated method stub
		m_id = input.getName();
		if (input instanceof OspStringEditorInput)
			m_strings = ((OspStringEditorInput) input).strings;
		else if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) input).getFile();
			ResourceExplorer view = ResourceExplorer.getResourceView();
			if (!view.getCurProject().equals(file.getProject().getName()))
				view.setCurProject(file.getProject().getName(), false);
			m_strings = view.getString();
		}

		super.setInput(input);
		setPartName(m_id);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		m_strings.SaveAll();
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		m_strings.SaveAll();
		return false;
	}

	@Override
	public void dispose() {
		WorkbenchWindow window = (WorkbenchWindow) getSite()
				.getWorkbenchWindow();
		MenuManager menuManager = window.getMenuBarManager();
		MenuManager projectMenu = (MenuManager) menuManager
				.find(IWorkbenchActionConstants.M_PROJECT);
		if (projectMenu != null) {
			IContributionItem testMenu;
			if ((testMenu = projectMenu.find(TEST_ACTION)) != null)
				projectMenu.remove(testMenu);
		}

		super.dispose();
	}

	public void createActions() {
		listener = new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				Object source = e.getSource();
				String name = ((Item) source).getText();
				if(name != null && name.equals("ID")) {
					fillTable(true);
				} else {
					ResourceExplorer view = ResourceExplorer.getResourceView();
					if (view == null)
						return;
	
					view.selectLanguage(name);
				}
			}
		};
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		createActions();

		m_language = new Vector<String>();
		final Table table = new Table(parent, SWT.MULTI | SWT.CENTER | SWT.FULL_SELECTION);
		this.table = table;
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(100, 100);

		initCursor();
		
		initTitles();
		fillTable(true);

		final Menu popupMenu = new Menu(table.getShell(), SWT.POP_UP);
		MenuItem InsertMenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		InsertMenuItem.setText("Insert");

		final MenuItem DeletemenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		DeletemenuItem.setText("Delete");

		MenuItem SelectLangItem = new MenuItem(popupMenu, SWT.CASCADE);
		SelectLangItem.setText("Language setting");

		popupMenu.addMenuListener(new MenuListener(){

			@Override
			public void menuHidden(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void menuShown(MenuEvent e) {
				int[] indices = table.getSelectionIndices();
				if(indices == null || indices.length == 0)
					DeletemenuItem.setEnabled(false);
				else
					DeletemenuItem.setEnabled(true);
			}});
		
		table.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent event) {
				if (event.button == 3) {
					popupMenu.setVisible(true);
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		InsertMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = new TableItem(table, SWT.LEFT);
				// item.setBackground(0, new
				// org.eclipse.swt.graphics.Color(null, 235, 233, 218));
				String id = m_strings.InsertText("");
				item.setText(0, String.valueOf((table.indexOf(item) + 1)));
				item.setText(1, id);

				isDirty = true;
				refreshFrameEditor();
				firePropertyChange(IEditorPart.PROP_DIRTY);
				// m_strings.SaveProject();
				table.showItem(item);
			}
		});

		DeletemenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
			    deleteSelectedItem();
			}
		});

		SelectLangItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				ResourceExplorer view = ResourceExplorer.getResourceView();
				if (view == null)
					return;

				view.selectLanguage();
			}
		});
	}

	/**
     * @param event
     */
    protected void deleteSelectedItem() {
        int[] indices = table.getSelectionIndices();
        TableItem item;
        for (int i = 0; i < indices.length; i++) {
            item = table.getItem(indices[i]);
            m_strings.DeleteText(item.getText(1));
            // refreshMenuEditor();
        }
        table.remove(indices);
        isDirty = true;
        fillTable(true);
        refreshFrameEditor();
        firePropertyChange(IEditorPart.PROP_DIRTY);
    }

    /**
     * @param event
     */
    protected void handleKeyReleased(KeyEvent event) {
        if (event.keyCode == SWT.DEL && event.stateMask == SWT.SHIFT){
            deleteSelectedItem();
        }
    }

    /**
     * 
     */
    private TableCursor initCursor() {
        final TableCursor cursor = new TableCursor(table, SWT.NONE);
        final ControlEditor editor = new ControlEditor(cursor);
        editor.grabHorizontal = true;
        editor.grabVertical = true;

        cursor.addTraverseListener(new TraverseListener(){

            @Override
            public void keyTraversed(TraverseEvent e) {
                traverse  = true;
            }
        });
        
        cursor.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent event) {
                handleKeyReleased(event);
            }
        });
        
        cursor.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
//              table.setSelection(new TableItem[] { cursor.getRow() });
                
                int column = cursor.getColumn();
                if (column == 0)
                    return;
                if(traverse) {
                    traverse = false;
                    cursor.setSelection(cursor.getRow(), cursor.getColumn());
                    return;
                }
                final Text text = new Text(cursor, SWT.LEFT);

                TableItem row = cursor.getRow();
                String temp = row.getText(column);
                text.setText(temp);
                text.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        if (e.keyCode == SWT.CR) {
                            TableItem row = cursor.getRow();
                            int column = cursor.getColumn();
                            if (column == 1) {
                                String oldId = row.getText(1);
                                String id = text.getText();
                                if (!id.isEmpty() && !oldId.equals(id)) {
                                    if(!m_strings.ReplaceTextId(oldId, id)) {
                                        text.dispose();
                                        cursor.setSelection(cursor.getRow(), cursor.getColumn());
                                        return;
                                    }
                                    row.setText(column, id);
                                    // refreshMenuEditor();
                                    isDirty = true;
                                    refreshFrameEditor();
                                    firePropertyChange(IEditorPart.PROP_DIRTY);
                                }
                            } else if (column > 1) {
                                String id = row.getText(1);
                                String oldValue = row.getText(column);
                                String value = text.getText();
                                if (!oldValue.equals(value)) {
                                    OspStringMarkup string = m_strings.m_Strings
                                            .get(m_language.get(column - 2));
                                    string.setText(id, value);
                                    row.setText(column, value);
                                    isDirty = true;
                                    refreshFrameEditor();
                                    firePropertyChange(IEditorPart.PROP_DIRTY);
                                    // refreshMenuEditor();
                                }
                            }
                            text.dispose();
                            cursor.setSelection(cursor.getRow(), cursor.getColumn());
                        }
                        if (e.character == SWT.ESC) {
                            text.dispose();
                        }
                    }
                });

                text.addFocusListener(new FocusAdapter() {
                    public void focusLost(FocusEvent e) {
                        TableItem row = cursor.getRow();
                        int column = cursor.getColumn();
                        if (column == 1) {
                            String oldID = row.getText(1);
                            String id = text.getText();
                            if (!id.isEmpty() && !oldID.equals(id)) {
                                for (int i = 0; i < m_language.size(); i++) {
                                    OspStringMarkup string = m_strings.m_Strings
                                            .get(m_language.get(i));
                                    if(string != null && !string.replaceTextId(oldID, id)) {
                                        text.dispose();
                                        if(cursor.getColumn() > 0)
                                            cursor.setSelection(cursor.getRow(), cursor.getColumn());
                                        return;
                                    }
                                }
                                row.setText(column, id);
                                isDirty = true;
                                refreshFrameEditor();
                                firePropertyChange(IEditorPart.PROP_DIRTY);
                                // refreshMenuEditor();
                            }
                        } else if (column > 1) {
                            String id = row.getText(1);
                            String oldValue = row.getText(column);
                            String value = text.getText();
                            if (!oldValue.equals(value)) {
                                OspStringMarkup string = m_strings.m_Strings
                                        .get(m_language.get(column - 2));
                                if(string != null)
                                    string.setText(id, value);
                                row.setText(column, value);
                                // refreshMenuEditor();
                                isDirty = true;
                                refreshFrameEditor();
                                firePropertyChange(IEditorPart.PROP_DIRTY);
                            }
                        }
                        text.dispose();
                        
                        if(cursor.getColumn() > 0)
                            cursor.setSelection(cursor.getRow(), cursor.getColumn());
                    }
                });
                editor.setEditor(text);
                // text.setFocus();
            }
        });
        
        return cursor;
    }

    private void initTitles() {
		int i = 0;
		String[] titles = new String[m_strings.m_Strings.size() + 2];
		m_language.removeAllElements();
		titles[i++] = "No";
		titles[i++] = "ID";
		
		TableColumn[] columns = table.getColumns();
		for(int colIndex=0; colIndex<columns.length; colIndex++) {
		    columns[colIndex].dispose();
		    columns[colIndex] = null;
		}

		Vector<String> keys = m_strings.getValueSortedKeys(false);
		for (int index=0; index<keys.size(); index++) {
			String lang = keys.get(index);
			String title = OspStringMarkup.getLanguageName(lang);
			if(title == null || title.isEmpty())
				continue;
			m_language.add(lang);
			titles[i++] = title;
		}

        for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
            TableColumn column;
            if (loopIndex == 0) {
                column = new TableColumn(table, SWT.CENTER);
                column.setText(titles[loopIndex]);
                table.getColumn(loopIndex).pack();
                column.setWidth(35);
                column.addSelectionListener(listener);
            } else if (loopIndex == 1) {
                column = new TableColumn(table, SWT.LEFT);
                column.setText(titles[loopIndex]);
                table.getColumn(loopIndex).pack();
                column.setWidth(150);
                column.addSelectionListener(listener);
            } else if (loopIndex > 1) {
                if(titles[loopIndex] == null || titles[loopIndex].isEmpty())
                    continue;
                column = new TableColumn(table, SWT.LEFT);
                column.setText(titles[loopIndex]);
                table.getColumn(loopIndex).pack();
                column.setWidth(180);
                column.addSelectionListener(listener);
            }
        }

	}

	public void fillTable(boolean isSorting) {
	    table.removeAll();

		for (int i = 0; i < m_language.size(); i++) {
			OspStringMarkup string = m_strings.m_Strings.get(m_language.get(i));
			if (string == null)
				continue;

			Enumeration<String> keys;
			if(isSorting)
				keys = string.getSortedKeys(false, false).elements();
			else
				keys = string.getIds();

			while (keys.hasMoreElements()) {
				String id = keys.nextElement();
				TableItem item = getTableItem(id);
				// item.setBackground(0, new
				// org.eclipse.swt.graphics.Color(null, 235, 233, 218));
				item.setText(0, String.valueOf((table.indexOf(item) + 1)));
				item.setText(1, id);
				item.setText(i + 2, string.getText(id));
			}
		}
	}
	
	private TableItem getTableItem(String id) {
		TableItem item = null;
		TableItem[] items = table.getItems();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getText(1).equals(id))
				return items[i];
		}

		item = new TableItem(table, SWT.CENTER);
		return item;
	}

	public static void refreshFrameEditor() {
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editors = page.getEditorReferences();
		for (int n = 0; n < editors.length; n++) {
			if (editors[n] != null) {
				if (editors[n].getId().equals(OspFormEditor.ID)) {
					((OspFormEditor) editors[n].getEditor(false)).getModel()
							.refreshChildren();
				} else if(editors[n].getId().equals(OspPanelEditor.ID)) {
					((OspPanelEditor) editors[n].getEditor(false)).getModel()
					.refreshChildren();
				} else if(editors[n].getId().equals(OspPopupEditor.ID)) {
					((OspPopupEditor) editors[n].getEditor(false)).getModel()
					.refreshChildren();
				}
			}
		}
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	public void clearDirty() {
		isDirty = false;
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}
}
