package com.osp.ide.resource.common;

import java.awt.Color;
import java.io.File;

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
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import com.osp.ide.resource.Activator;

public class ImageAdvisor {

	private int TOP_MARGIN = 4;
	private int SIDE_MARGIN = 6;
	private int GROUP_MARGIN = 10;
	public static final Point GOURP_SIZE = new Point(712, 238);
	private Point GOURP_LOCATION = new Point(4, 16);
	private Point FILEINFO_LOCATION = new Point(8, 16);
	private Point FILEINFO_SIZE = new Point(266, 216);
	private Point FILELIST_LOCATION = new Point(8, 16); // @jve:decl-index=0:
	private Point FILELIST_SIZE = new Point(250, 170);
	private Point IMGINFO_LOCATION = new Point(280, 16);
	private Point IMGINFO_SIZE = new Point(428, 214);
	private Point COLORLIST_LOCATION = new Point(8, 16); // @jve:decl-index=0:
	private Point COLORLIST_SIZE = new Point(262, 190);
	private Point BTN_SIZE = new Point(38, 24);
	private Point CHECK_SIZE = new Point(78, 12); // @jve:decl-index=0:
	private Point HVGROUP_SIZE = new Point(146, 86);
	private Point RADIO_SIZE = new Point(146, 14);

	private Group group = null;
	private Group fileList = null;
	private List imagelist = null;
	private Button btnAdd = null;
	private Button btnDel = null;
	private Button btnUp = null;
	private Button btnDown = null;
	private Group imgInfo = null;
	private Table colorTable = null;
	private Button fix = null;
	private Group vertical = null;
	private Button radioVAlign = null;
	private Button radioTop = null;
	private Button radioBottom = null;
	private Tag_info info;
	private int index;
	private Label sideMargin;
	private Text spaceW;
	private Group horizental;
	private Button radioHAlign;
	private Button radioLeft;
	private Label vMargin;
	private Button radioRight;
	private Text spaceH;
	private String sDir;

	protected boolean traverse = false;
	private Text sizeX;
	private Label comma;
	private Text sizeY;
	private Label gwalho;
	private Text temp;

	public void Dispose() {
		group.dispose();
		fileList.dispose();
		imagelist.dispose();
		btnAdd.dispose();
		btnDel.dispose();
		btnUp.dispose();
		btnDown.dispose();
		imgInfo.dispose();
		colorTable.dispose();
		fix.dispose();
		vertical.dispose();
		radioVAlign.dispose();
		radioTop.dispose();
		radioBottom.dispose();
		sideMargin.dispose();
		spaceW.dispose();
		horizental.dispose();
		radioHAlign.dispose();
		radioLeft.dispose();
		vMargin.dispose();
		radioRight.dispose();
		spaceH.dispose();
		sizeX.dispose();
		comma.dispose();
		sizeY.dispose();
		gwalho.dispose();
		temp.dispose();
	}

	/**
	 * This method initializes sShell
	 */
	public ImageAdvisor(Composite parent, Tag_info info, int index, String sDir) {
		this.info = info;
		this.index = index;
		this.sDir = sDir;
		createGroup(parent);
	}

	/**
	 * This method initializes group
	 * 
	 */
	private void createGroup(Composite parent) {
		group = new Group(parent, SWT.NONE);
		group.setLayout(null);
		group.setText("Image Info");
		createImageList();
		createColorList();
		group.setBounds(GOURP_LOCATION.x, (GOURP_SIZE.y + GROUP_MARGIN) * index
				+ GROUP_MARGIN, GOURP_SIZE.x, GOURP_SIZE.y);

		temp = new Text(group, SWT.BORDER);
		group.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				temp.setFocus();
			}
		});
	}

	/**
	 * This method initializes imageList
	 * 
	 */
	private void createImageList() {
		fileList = new Group(group, SWT.NONE);
		fileList.setLayout(null);
		fileList.setText("File List");
		fileList.setBounds(FILEINFO_LOCATION.x, FILEINFO_LOCATION.y,
				FILEINFO_SIZE.x, FILEINFO_SIZE.y);
		fileList.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				temp.setFocus();
			}
		});

		imagelist = new List(fileList, SWT.NONE);
		imagelist.setBounds(FILELIST_LOCATION.x, FILELIST_LOCATION.y,
				FILELIST_SIZE.x, FILELIST_SIZE.y);
		Image_info image = info.elementAt(index);
		for (int i = 0; i < image.name.size(); i++)
			imagelist.add(image.name.elementAt(i));

		btnAdd = new Button(fileList, SWT.NONE);
		btnAdd.setBounds(FILELIST_LOCATION.x, imagelist.getLocation().y
				+ imagelist.getSize().y + TOP_MARGIN, BTN_SIZE.x, BTN_SIZE.y);
		btnAdd.setText("Add");
		btnAdd.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog fd = new FileDialog(group.getShell(), SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath(sDir);
				String[] filterExt = { "*.png", "*.*" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				if (selected == null || selected.length() == 0)
					return;

				File file = new File(selected);
				Image_info image = info.elementAt(ImageAdvisor.this.index);
				if (imagelist.indexOf(file.getName()) < 0) {
					imagelist.add(file.getName());
					image.name.add(file.getName());
				}

			}
		});

		btnDel = new Button(fileList, SWT.NONE);
		btnDel.setBounds(btnAdd.getLocation().x + btnAdd.getSize().x
				+ SIDE_MARGIN, imagelist.getLocation().y
				+ imagelist.getSize().y + TOP_MARGIN, BTN_SIZE.x, BTN_SIZE.y);
		btnDel.setText("Del");
		btnDel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int index;
				if ((index = imagelist.getFocusIndex()) < 0
						|| imagelist.getItemCount() <= 0)
					return;
				String fileName = imagelist.getItem(index);

				imagelist.remove(fileName);

				Image_info image = info.elementAt(ImageAdvisor.this.index);
				image.name.remove(fileName);
			}
		});

		btnUp = new Button(fileList, SWT.NONE);
		btnUp.setBounds(btnDel.getLocation().x + btnDel.getSize().x
				+ SIDE_MARGIN, imagelist.getLocation().y
				+ imagelist.getSize().y + TOP_MARGIN, BTN_SIZE.x, BTN_SIZE.y);
		btnUp.setText("Up");
		btnUp.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int index = imagelist.getSelectionIndex();
				if (index > 0 && index < imagelist.getItemCount()) {
					String item = imagelist.getItem(index);
					imagelist.remove(index);
					imagelist.add(item, index - 1);
					imagelist.setSelection(index - 1);

					Image_info image = info.elementAt(ImageAdvisor.this.index);
					image.name.remove(index);
					image.name.add(index - 1, item);
				}
			}
		});

		btnDown = new Button(fileList, SWT.NONE);
		btnDown.setBounds(btnUp.getLocation().x + btnUp.getSize().x
				+ SIDE_MARGIN, imagelist.getLocation().y
				+ imagelist.getSize().y + TOP_MARGIN, BTN_SIZE.x, BTN_SIZE.y);
		btnDown.setText("Down");
		btnDown.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				int index = imagelist.getSelectionIndex();
				if (index >= 0 && index < imagelist.getItemCount() - 1) {
					String item = imagelist.getItem(index);
					imagelist.remove(index);
					imagelist.add(item, index + 1);
					imagelist.setSelection(index + 1);

					Image_info image = info.elementAt(ImageAdvisor.this.index);
					image.name.remove(index);
					image.name.add(index + 1, item);
				}
			}
		});
	}

	/**
	 * This method initializes ColorList
	 * 
	 */
	private void createColorList() {
		imgInfo = new Group(group, SWT.NONE);
		imgInfo.setLayout(null);
		imgInfo.setText("Image info");
		imgInfo.setBounds(IMGINFO_LOCATION.x, IMGINFO_LOCATION.y,
				IMGINFO_SIZE.x, IMGINFO_SIZE.y);
		imgInfo.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				temp.setFocus();
			}
		});

		colorTable = new Table(imgInfo, SWT.CENTER | SWT.FULL_SELECTION);
		colorTable.setHeaderVisible(true);
		colorTable.setLinesVisible(true);
		colorTable.setBounds(COLORLIST_LOCATION.x, COLORLIST_LOCATION.y,
				COLORLIST_SIZE.x, COLORLIST_SIZE.y);
		String[] titles = initTitles();

		for (int loopIndex = 0; loopIndex < titles.length; loopIndex++) {
			TableColumn column;
			if (loopIndex == 0) {
				column = new TableColumn(colorTable, SWT.CENTER);
				column.setText(titles[loopIndex]);
				colorTable.getColumn(loopIndex).pack();
				column.setWidth(36);
			} else if (loopIndex > 0) {
				column = new TableColumn(colorTable, SWT.LEFT);
				column.setText(titles[loopIndex]);
				colorTable.getColumn(loopIndex).pack();
				if (loopIndex == 4)
					column.setWidth(64);
				else
					column.setWidth(54);
			}
		}

		fillTable();
		createAction();

		final Image_info image = info.elementAt(ImageAdvisor.this.index);

		fix = new Button(imgInfo, SWT.CHECK);
		fix.setBounds(colorTable.getLocation().x + colorTable.getSize().x
				+ SIDE_MARGIN, imgInfo.getLocation().y - 2, CHECK_SIZE.x,
				CHECK_SIZE.y);
		fix.setText("Fix Size : (");

		fix.setSelection(image.isFix());
		fix.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isFix = image.isFix();
				if (isFix)
					image.setFix(false);
				else
					image.setFix(true);
			}
		});

		sizeX = new Text(imgInfo, SWT.BORDER);
		sizeX.setBounds(fix.getLocation().x + fix.getSize().x + 2, fix
				.getLocation().y - 2, 24, 16);
		sizeX.setTextLimit(3);
		sizeX.setText(Integer.toString(image.getSize().width));
		sizeX.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		sizeX.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = sizeX.getText();
				if (text == null || text.isEmpty()) {
					sizeX.setText("0");
					image.setSizeX(0);
				} else {
					image.setSizeX(Integer.parseInt(text));
				}
			}
		});

		comma = new Label(imgInfo, SWT.NONE);
		comma.setBounds(sizeX.getLocation().x + sizeX.getSize().x + 2, fix
				.getLocation().y, 8, CHECK_SIZE.y);
		comma.setText(", ");

		sizeY = new Text(imgInfo, SWT.BORDER);
		sizeY.setBounds(comma.getLocation().x + comma.getSize().x, sizeX
				.getLocation().y, 24, 16);
		sizeY.setTextLimit(3);
		sizeY.setText(Integer.toString(image.getSize().height));
		sizeY.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		sizeY.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = sizeY.getText();
				if (text == null || text.isEmpty()) {
					sizeY.setText("0");
					image.setSizeY(0);
				} else {
					image.setSizeY(Integer.parseInt(text));
				}
			}
		});

		gwalho = new Label(imgInfo, SWT.NONE);
		gwalho.setBounds(sizeY.getLocation().x + sizeY.getSize().x + 2, comma
				.getLocation().y, 10, CHECK_SIZE.y);
		gwalho.setText(")");

		createVertical();
		createHorizental();
	}

	private void createAction() {
		final Menu popupMenu = new Menu(colorTable.getShell(), SWT.POP_UP);
		MenuItem InsertMenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		InsertMenuItem.setText("Insert");

		final MenuItem DeletemenuItem = new MenuItem(popupMenu, SWT.CASCADE);
		DeletemenuItem.setText("Delete");

		popupMenu.addMenuListener(new MenuListener() {

			@Override
			public void menuHidden(MenuEvent e) {

			}

			@Override
			public void menuShown(MenuEvent e) {
				int[] indices = colorTable.getSelectionIndices();
				if (indices == null || indices.length == 0)
					DeletemenuItem.setEnabled(false);
				else
					DeletemenuItem.setEnabled(true);
			}
		});

		colorTable.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent event) {
				if (event.button == 3) {
					popupMenu.setVisible(true);
				}
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {

			}

			@Override
			public void mouseUp(MouseEvent e) {

			}
		});

		InsertMenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = new TableItem(colorTable, SWT.LEFT);
				item.setText(0, String.valueOf((colorTable.indexOf(item))));
				item.setText(1, "0");
				item.setText(2, "0");
				item.setText(3, "0");
				item.setText(4, "1.0");
				Image_info image = info.elementAt(ImageAdvisor.this.index);
				image.bgColor.add(new Color(0, 0, 0));
				image.opacity.add(1.0f);
			}
		});

		DeletemenuItem.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int[] indices = colorTable.getSelectionIndices();
				TableItem item;
				for (int i = 0; i < indices.length; i++) {
					Image_info image = info.elementAt(ImageAdvisor.this.index);
					image.bgColor.remove(indices[i]);
					image.opacity.remove(indices[i]);
				}
				colorTable.remove(indices);
				for (int i = 0; i < colorTable.getItemCount(); i++) {
					item = colorTable.getItem(i);
					item.setText(0, String.valueOf(i));
				}
			}
		});

		final TableCursor cursor = new TableCursor(colorTable, SWT.NONE);
		final ControlEditor editor = new ControlEditor(cursor);
		editor.grabHorizontal = true;
		editor.grabVertical = true;

		cursor.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				traverse = true;
			}
		});

		cursor.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int column = cursor.getColumn();
				if (column == 0)
					return;
				if (traverse) {
					traverse = false;
					cursor.setSelection(cursor.getRow(), 0);
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

							saveColorOpacity(column, row, text.getText());

							text.dispose();
							cursor.setSelection(0, 0);
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
						if (row == null) {
							text.dispose();
							return;
						}

						saveColorOpacity(column, row, text.getText());

						text.dispose();
						cursor.setSelection(0, 0);
					}
				});
				editor.setEditor(text);
				// text.setFocus();
			}
		});
	}

	private boolean saveColorOpacity(int column, TableItem row, String text) {
		if (column > 0 && column < 4) {
			String oldRGB = row.getText(column);
			String rgb = text;
			if (!rgb.isEmpty() && !oldRGB.equals(rgb)) {
				Integer nRGB;
				try {
					nRGB = Integer.parseInt(rgb);
				} catch (NumberFormatException e1) {
					Activator.setErrorMessage(
							"ImageAdvisor.saveColorOpacity()",
							"Color NumberFormatException - " + e1.getMessage(), e1);
					return false;
				}
				if (nRGB >= 0 && nRGB <= 255) {
					row.setText(column, rgb);
				} else {
					Activator.setErrorMessage(
							"ImageAdvisor.saveColorOpacity()",
							"Color Invalide Range : " + nRGB, null);
					return false;
				}
			} else {
				return false;
			}
		} else if (column == 4) {
			String oldOpacity = row.getText(column);
			String opacity = text;
			if (!opacity.isEmpty() && !oldOpacity.equals(opacity)) {
				Float fOpacity;
				try {
					fOpacity = Float.parseFloat(opacity);
				} catch (NumberFormatException e1) {
					Activator.setErrorMessage(
							"ImageAdvisor.saveColorOpacity()",
							"Opacity NumberFormatException - "
									+ e1.getMessage(), e1);
					return false;
				}
				if (fOpacity >= 0.0 && fOpacity <= 1.0) {
					row.setText(column, opacity);
				} else {
					Activator.setErrorMessage(
							"ImageAdvisor.saveColorOpacity()",
							"Color Invalide Range : " + fOpacity, null);
					return false;
				}
			} else {
				return false;
			}
		} else
			return false;

		Color color = new Color(Integer.parseInt(row.getText(1)), Integer
				.parseInt(row.getText(2)), Integer.parseInt(row.getText(3)));
		Image_info image = info.elementAt(ImageAdvisor.this.index);
		image.bgColor.remove(Integer.parseInt(row.getText(0)));
		image.bgColor.add(Integer.parseInt(row.getText(0)), color);

		image.opacity.remove(Integer.parseInt(row.getText(0)));
		image.opacity.add(Integer.parseInt(row.getText(0)), Float
				.parseFloat(row.getText(4)));

		return true;
	}

	private String[] initTitles() {
		int i = 0;
		String[] titles = new String[5];
		titles[i++] = "Idx";
		titles[i++] = "Red";
		titles[i++] = "Green";
		titles[i++] = "Blue";
		titles[i++] = "Opacity";

		return titles;
	}

	public void fillTable() {
		colorTable.removeAll();

		Image_info info = this.info.elementAt(index);
		for (int i = 0; i < info.bgColor.size(); i++) {
			TableItem item = new TableItem(colorTable, SWT.LEFT);

			Color color = info.bgColor.elementAt(i);
			if (color == null)
				continue;
			item.setText(0, String.valueOf(colorTable.indexOf(item)));
			item.setText(1, String.valueOf(color.getRed()));
			item.setText(2, String.valueOf(color.getGreen()));
			item.setText(3, String.valueOf(color.getBlue()));
			item.setText(4, info.opacity.elementAt(i).toString());
		}
	}

	/**
	 * This method initializes vertival
	 * 
	 */
	private void createVertical() {
		vertical = new Group(imgInfo, SWT.NONE);
		vertical.setLayout(null);
		vertical.setBounds(fix.getLocation().x, fix.getLocation().y
				+ fix.getSize().y + TOP_MARGIN, HVGROUP_SIZE.x, HVGROUP_SIZE.y);
		vertical.setText("Vertical Margin");

		final Image_info info = this.info.elementAt(index);

		radioVAlign = new Button(vertical, SWT.RADIO);
		radioVAlign.setBounds(4, TOP_MARGIN + 10, RADIO_SIZE.x, RADIO_SIZE.y);
		radioVAlign.setText("VAlign Center");
		radioVAlign.setSelection(info.isVAlign());
		radioVAlign.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isVAlign = info.isVAlign();
				if (!isVAlign) {
					info.setVAlign(true);
					info.setTop(false);
					info.setBottom(false);
				}
			}
		});

		radioTop = new Button(vertical, SWT.RADIO);
		radioTop.setBounds(4, radioVAlign.getLocation().y
				+ radioVAlign.getSize().y + TOP_MARGIN, RADIO_SIZE.x,
				RADIO_SIZE.y);
		radioTop.setText("Top");
		radioTop.setSelection(info.isTop());
		radioTop.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isTop = info.isTop();
				if (!isTop) {
					info.setTop(true);
					info.setVAlign(false);
					info.setBottom(false);
				}
			}
		});

		radioBottom = new Button(vertical, SWT.RADIO);
		radioBottom
				.setBounds(4, radioTop.getLocation().y + radioTop.getSize().y
						+ TOP_MARGIN, RADIO_SIZE.x, RADIO_SIZE.y);
		radioBottom.setText("Bottom");
		radioBottom.setSelection(info.isBottom());
		radioBottom.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isBottom = info.isBottom();
				if (!isBottom) {
					info.setBottom(true);
					info.setVAlign(false);
					info.setTop(false);
				}
			}
		});

		vMargin = new Label(vertical, SWT.NONE);
		vMargin.setBounds(4, radioBottom.getLocation().y
				+ radioBottom.getSize().y + TOP_MARGIN, 46, 14);
		vMargin.setText("Margin : ");
		spaceH = new Text(vertical, SWT.BORDER);
		spaceH.setBounds(vMargin.getLocation().x + vMargin.getSize().x
				+ SIDE_MARGIN, vMargin.getLocation().y - 2, 46, 16);
		spaceH.setTextLimit(3);
		spaceH.setText(Integer.toString(info.getSpaceH()));
		spaceH.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		spaceH.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = spaceH.getText();
				if (text == null || text.isEmpty()) {
					spaceH.setText("0");
					info.setSpaceH(0);
				} else {
					info.setSpaceH(Integer.parseInt(text));
				}
			}
		});
	}

	/**
	 * This method initializes horizental
	 * 
	 */
	private void createHorizental() {
		horizental = new Group(imgInfo, SWT.NONE);
		horizental.setLayout(null);
		horizental.setBounds(vertical.getLocation().x, vertical.getLocation().y
				+ vertical.getSize().y + TOP_MARGIN, HVGROUP_SIZE.x,
				HVGROUP_SIZE.y);
		horizental.setText("Horizental Margin");

		final Image_info info = this.info.elementAt(index);

		radioHAlign = new Button(horizental, SWT.RADIO);
		radioHAlign.setBounds(4, TOP_MARGIN + 10, RADIO_SIZE.x, RADIO_SIZE.y);
		radioHAlign.setText("HAlign Center");
		radioHAlign.setSelection(info.isHAlign());
		radioHAlign.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isHAlign = info.isHAlign();
				if (!isHAlign) {
					info.setHAlign(true);
					info.setLeft(false);
					info.setRight(false);
				}
			}
		});

		radioLeft = new Button(horizental, SWT.RADIO);
		radioLeft.setBounds(4, radioHAlign.getLocation().y
				+ radioHAlign.getSize().y + TOP_MARGIN, RADIO_SIZE.x,
				RADIO_SIZE.y);
		radioLeft.setText("Left");
		radioLeft.setSelection(info.isLeft());
		radioLeft.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isLeft = info.isLeft();
				if (!isLeft) {
					info.setLeft(true);
					info.setHAlign(false);
					info.setRight(false);
				}
			}
		});

		radioRight = new Button(horizental, SWT.RADIO);
		radioRight.setBounds(4, radioTop.getLocation().y + radioTop.getSize().y
				+ TOP_MARGIN, RADIO_SIZE.x, RADIO_SIZE.y);
		radioRight.setText("Right");
		radioRight.setSelection(info.isRight());
		radioRight.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isRight = info.isRight();
				if (!isRight) {
					info.setRight(true);
					info.setHAlign(false);
					info.setLeft(false);
				}
			}
		});

		sideMargin = new Label(horizental, SWT.NONE);
		sideMargin.setBounds(4, radioRight.getLocation().y
				+ radioRight.getSize().y + TOP_MARGIN, 46, 14);
		sideMargin.setText("Margin : ");
		spaceW = new Text(horizental, SWT.BORDER);
		spaceW.setBounds(sideMargin.getLocation().x + sideMargin.getSize().x
				+ SIDE_MARGIN, sideMargin.getLocation().y - 2, 46, 16);
		spaceW.setTextLimit(3);
		spaceW.setText(Integer.toString(info.getSpaceW()));
		spaceW.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		spaceW.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = spaceW.getText();
				if (text == null || text.isEmpty()) {
					spaceW.setText("0");
					info.setSpaceW(0);
				} else {
					info.setSpaceW(Integer.parseInt(text));
				}
			}
		});
	}

}
