package com.osp.ide.resource.common;

import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import org.eclipse.core.runtime.Path;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.editform.FormTemplateXmlStore;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

/**
 * This class demonstrates how to create your own dialog classes. It allows
 * users to input a String
 */
public class ImageSettingDialog extends Dialog {

	// dialog °á°ú°ª
	private String TITLE = "Image Setting";
	private Point VIEW_SIZE = new Point(728, 420);
	private Point VIEW_LOCATION = new Point(6, 10);
	private int TOP_MARGIN = 20;
	private Point TAGLABEL_LOCATION = new Point(10, 10);
	private Point TAG_SIZE = new Point(120, 10);
	private int LINE_MARGIN = 10;
	private int SIDE_MARGIN = 5;
	boolean result = false;

	private Shell sShell = null; // @jve:decl-index=0:visual-constraint="3,10"
	private Button btnClose = null;
	private ResourceExplorer view;
	private ConfigManager config;
	private Combo tagCombo;
	private Composite imageView;
	private ScrolledComposite imagelist;
	private Composite composite;
	private Button btnSave;
	private Label tagLabel;
	private Text imgNum;
	private Label numLabel;
	private Button btnApply;
	private Tag_info info;
	private Vector<ImageAdvisor> imageList;
	private Button btnReload;
	private Label dftlSizeLabel;
	private Text dsizeX;
	private Label dcomma;
	private Text dsizeY;
	private Label dgwalho;
	private Label minSizeLabel;
	private Text msizeX;
	private Label mcomma;
	private Text msizeY;
	private Label mgwalho;
	private Label textLabel;
	private Text lmargin;
	private Label tcomma;
	private Text rmargin;
	private Label tgwalho;
	private Label textSizeLabel;
	private Text tSize;
	private String screenSize;
	private Button btnReloadTemplate;
    private Label tempLabel1;
    private Text temp1;
    private Label tempLabel2;
    private Text temp2;

	// /////////////////////////////////////////////////////

	public void Dispose() {
		btnClose.dispose();
		tagCombo.dispose();
		imageView.dispose();
		imagelist.dispose();
		composite.dispose();
		btnSave.dispose();
		tagLabel.dispose();
		imgNum.dispose();
		numLabel.dispose();
		btnApply.dispose();
		btnReload.dispose();
		dftlSizeLabel.dispose();
		dsizeX.dispose();
		dcomma.dispose();
		dsizeY.dispose();
		dgwalho.dispose();
		minSizeLabel.dispose();
		msizeX.dispose();
		mcomma.dispose();
		msizeY.dispose();
		mgwalho.dispose();
		textLabel.dispose();
		lmargin.dispose();
		tcomma.dispose();
		rmargin.dispose();
		tgwalho.dispose();
		textSizeLabel.dispose();
		tSize.dispose();
		btnReloadTemplate.dispose();
	    tempLabel1.dispose();
	    temp1.dispose();
	    tempLabel2.dispose();
	    temp2.dispose();
		int size = imageList.size();
		for (int i = 0; i < size; i++) {
			imageList.elementAt(i).Dispose();
		}
		imageList.removeAllElements();
	}

	public ImageSettingDialog(Shell parent, ResourceExplorer view, String screenSize) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		if (view != null)
			this.view = view;

		imageList = new Vector<ImageAdvisor>();
		this.screenSize = screenSize;
	}

	public boolean open() {
		if (view == null)
			return false;

		config = view.getConfig(screenSize);

		sShell = new Shell(getParent(), getStyle());
		sShell.setText(TITLE + " (" + screenSize + ")");
		createContents(sShell);
		sShell.setBounds(0, 0, 440, 345);
		sShell.pack();
		Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		sShell.setSize(size.x + 5, size.y + 5);

		// Rectangle rect = Display.getDefault().getBounds();
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

		imageView = new Composite(sShell, SWT.BORDER);
		imageView.setToolTipText(TITLE);
		imageView.setSize(VIEW_SIZE.x, VIEW_SIZE.y);
		imageView.setLocation(VIEW_LOCATION.x, VIEW_LOCATION.y);

		createTagCombo();
		createDefaultSize();
		createMinSize();
		createTextMargin();
		createTextSize();
		createTemp();
		
		imagelist = new ScrolledComposite(imageView, SWT.VERTICAL);
		imagelist.setToolTipText(TITLE);
		imagelist.setSize(VIEW_SIZE.x, VIEW_SIZE.y - 54);
		imagelist.setLocation(0, 52);
		imagelist.getVerticalBar().setIncrement(ImageAdvisor.GOURP_SIZE.y / 4);

		composite = new Composite(imagelist, SWT.NONE);
		imagelist.setContent(composite);

	}

	private void createDefaultSize() {
		dftlSizeLabel = new Label(imageView, SWT.NONE);
		dftlSizeLabel.setText("DefltSize:(");
		dftlSizeLabel.setBounds(TAGLABEL_LOCATION.x, tagLabel.getLocation().y
				+ tagLabel.getSize().y + LINE_MARGIN, 58, 12);

		dsizeX = new Text(imageView, SWT.BORDER);
		dsizeX.setBounds(dftlSizeLabel.getLocation().x
				+ dftlSizeLabel.getSize().x + 2,
				dftlSizeLabel.getLocation().y - 2, 24, 16);
		dsizeX.setTextLimit(3);
		dsizeX.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		dsizeX.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = dsizeX.getText();
				if (text == null || text.isEmpty()) {
					dsizeX.setText("0");
				} else {
					info.dftlSize.x = Integer.parseInt(text);
				}
			}
		});

		dcomma = new Label(imageView, SWT.NONE);
		dcomma.setBounds(dsizeX.getLocation().x + dsizeX.getSize().x + 2,
				dftlSizeLabel.getLocation().y, 6, dftlSizeLabel.getSize().y);
		dcomma.setText(",");

		dsizeY = new Text(imageView, SWT.BORDER);
		dsizeY.setBounds(dcomma.getLocation().x + dcomma.getSize().x, dsizeX
				.getLocation().y, 24, 16);
		dsizeY.setTextLimit(3);
		dsizeY.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		dsizeY.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = dsizeY.getText();
				if (text == null || text.isEmpty()) {
					dsizeY.setText("0");
				} else {
					info.dftlSize.y = Integer.parseInt(text);
				}
			}
		});

		dgwalho = new Label(imageView, SWT.NONE);
		dgwalho.setBounds(dsizeY.getLocation().x + dsizeY.getSize().x + 2,
				dcomma.getLocation().y, 8, dftlSizeLabel.getSize().y);
		dgwalho.setText(")");

	}

	private void createMinSize() {
		minSizeLabel = new Label(imageView, SWT.NONE);
		minSizeLabel.setText("MinSize:(");
		minSizeLabel.setBounds(dgwalho.getLocation().x + dgwalho.getSize().x
				+ SIDE_MARGIN, dftlSizeLabel.getLocation().y, 54, 12);

		msizeX = new Text(imageView, SWT.BORDER);
		msizeX.setBounds(minSizeLabel.getLocation().x
				+ minSizeLabel.getSize().x + 2,
				minSizeLabel.getLocation().y - 2, 24, 16);
		msizeX.setTextLimit(3);
		msizeX.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		msizeX.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = msizeX.getText();
				if (text == null || text.isEmpty()) {
					msizeX.setText("0");
				} else {
					info.minSize.x = Integer.parseInt(text);
				}
			}
		});

		mcomma = new Label(imageView, SWT.NONE);
		mcomma.setBounds(msizeX.getLocation().x + msizeX.getSize().x + 2,
				minSizeLabel.getLocation().y, 6, minSizeLabel.getSize().y);
		mcomma.setText(",");

		msizeY = new Text(imageView, SWT.BORDER);
		msizeY.setBounds(mcomma.getLocation().x + mcomma.getSize().x, msizeX
				.getLocation().y, 24, 16);
		msizeY.setTextLimit(3);
		msizeY.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		msizeY.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = msizeY.getText();
				if (text == null || text.isEmpty()) {
					msizeY.setText("0");
				} else {
					info.minSize.y = Integer.parseInt(text);
				}
			}
		});

		mgwalho = new Label(imageView, SWT.NONE);
		mgwalho.setBounds(msizeY.getLocation().x + msizeY.getSize().x + 2,
				mcomma.getLocation().y, 8, minSizeLabel.getSize().y);
		mgwalho.setText(")");

	}

	private void createTextMargin() {
		textLabel = new Label(imageView, SWT.NONE);
		textLabel.setText("TMargin:L(");
		textLabel.setBounds(mgwalho.getLocation().x + mgwalho.getSize().x
				+ SIDE_MARGIN, dftlSizeLabel.getLocation().y, 62, 12);

		lmargin = new Text(imageView, SWT.BORDER);
		lmargin.setBounds(textLabel.getLocation().x
				+ textLabel.getSize().x + 2,
				textLabel.getLocation().y - 2, 24, 16);
		lmargin.setTextLimit(3);
		lmargin.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		lmargin.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = lmargin.getText();
				if (text == null || text.isEmpty()) {
					lmargin.setText("0");
				} else {
					info.textlMargin = Integer.parseInt(text);
				}
			}
		});

		tcomma = new Label(imageView, SWT.NONE);
		tcomma.setBounds(lmargin.getLocation().x + lmargin.getSize().x + 2,
				textLabel.getLocation().y, 22, textLabel.getSize().y);
		tcomma.setText("),R(");

		rmargin = new Text(imageView, SWT.BORDER);
		rmargin.setBounds(tcomma.getLocation().x + tcomma.getSize().x, lmargin
				.getLocation().y, 24, 16);
		rmargin.setTextLimit(3);
		rmargin.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		rmargin.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = rmargin.getText();
				if (text == null || text.isEmpty()) {
					rmargin.setText("0");
				} else {
					info.textrMargin = Integer.parseInt(text);
				}
			}
		});

		tgwalho = new Label(imageView, SWT.NONE);
		tgwalho.setBounds(rmargin.getLocation().x + rmargin.getSize().x + 2,
				tcomma.getLocation().y, 10, textLabel.getSize().y);
		tgwalho.setText(")");

	}

	private void createTextSize() {
		textSizeLabel = new Label(imageView, SWT.NONE);
		textSizeLabel.setText("TSize:");
		textSizeLabel.setBounds(tgwalho.getLocation().x + tgwalho.getSize().x
				+ SIDE_MARGIN, dftlSizeLabel.getLocation().y, 36, 12);

		tSize = new Text(imageView, SWT.BORDER);
		tSize.setBounds(textSizeLabel.getLocation().x
				+ textSizeLabel.getSize().x + 2,
				textSizeLabel.getLocation().y - 2, 24, 16);
		tSize.setTextLimit(2);
		tSize.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});
		tSize.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				String text = tSize.getText();
				if (text == null || text.isEmpty()) {
					tSize.setText("0");
				} else {
					info.tSize = Integer.parseInt(text);
				}
			}
		});
	}

    private void createTemp() {
        tempLabel1 = new Label(imageView, SWT.NONE);
        tempLabel1.setText("Temp1:");
        tempLabel1.setBounds(tSize.getLocation().x + tSize.getSize().x
                + LINE_MARGIN, dftlSizeLabel.getLocation().y, 42, 12);

        temp1 = new Text(imageView, SWT.BORDER);
        temp1.setBounds(tempLabel1.getLocation().x
                + tempLabel1.getSize().x + 2,
                tempLabel1.getLocation().y - 2, 66, 16);
        temp1.setTextLimit(7);
        temp1.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                e.doit = e.text.matches("[#]*[a-f]*[A-F]*[0-9]*");
            }
        });
        temp1.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String text = temp1.getText();
                if (text == null || text.isEmpty()) {
                    temp1.setText("0");
                } else {
                    info.temp1 = text;
                }
            }
        });
        tempLabel2 = new Label(imageView, SWT.NONE);
        tempLabel2.setText("Temp2:");
        tempLabel2.setBounds(temp1.getLocation().x + temp1.getSize().x
                + 5, dftlSizeLabel.getLocation().y, 42, 12);

        temp2 = new Text(imageView, SWT.BORDER);
        temp2.setBounds(tempLabel2.getLocation().x
                + tempLabel2.getSize().x + 2,
                tempLabel2.getLocation().y - 2, 66, 16);
        temp2.setTextLimit(7);
        temp2.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent e) {
                e.doit = e.text.matches("[#]*[a-f]*[A-F]*[0-9]*");
            }
        });
        temp2.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                String text = temp2.getText();
                if (text == null || text.isEmpty()) {
                    temp2.setText("0");
                } else {
                    info.temp2 = text;
                }
            }
        });
    }

	public Vector<String> getSortedKeys(boolean isPrint, String[] list){
		Vector<String> v = new Vector<String>();
		for(int i=0; i<list.length; i++)
			v.add(list[i]);
		
		Collections.sort(v);

		if(isPrint) {
			StringBuilder s;
			Enumeration<String> keys = v.elements();
			while(keys.hasMoreElements()) {
				String key = keys.nextElement();
				s = new StringBuilder("Key : ");
				s.append(key);
				System.out.println(s);
			}
		}
		return v;
	}
	
	private void createTagCombo() {
		tagLabel = new Label(imageView, SWT.NONE);
		tagLabel.setText("Select Control : ");
		tagLabel.setBounds(TAGLABEL_LOCATION.x, TAGLABEL_LOCATION.y, 88, 12);

		tagCombo = new Combo(imageView, SWT.DROP_DOWN | SWT.READ_ONLY);
		tagCombo.setBounds(tagLabel.getBounds().x + tagLabel.getBounds().width
				+ 2, tagLabel.getBounds().y - 4, TAG_SIZE.x, TAG_SIZE.y);

		Vector<String> sortedTag = getSortedKeys(false, FrameConst.cszTag);
		for (int i = 0; i < FrameConst.cszTag.length; i++)
			tagCombo.add(sortedTag.elementAt(i));

		tagCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				info = config.getImageInfo(tagCombo.getText());
				if (info == null) {
					info = new Tag_info();
					info.screen = screenSize;
					config.insertImageInfo(tagCombo.getText(), info);
				}
				initList();
			}
		});

		numLabel = new Label(imageView, SWT.NONE);
		numLabel.setText("Num Of Image : ");
		numLabel.setBounds(
				tagCombo.getLocation().x + tagCombo.getSize().x + 20,
				TAGLABEL_LOCATION.y, 88, 12);

		imgNum = new Text(imageView, SWT.BORDER);
		imgNum.setBounds(numLabel.getLocation().x + numLabel.getSize().x + 2,
				numLabel.getLocation().y - 2, 24, 16);
		imgNum.setTextLimit(1);
		imgNum.setText("0");
		imgNum.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});

		btnApply = new Button(imageView, SWT.NONE);
		btnApply.setBounds(imgNum.getLocation().x + imgNum.getSize().x + 6,
				imgNum.getLocation().y - 2, 40, 20);
		btnApply.setText("Apply");
		btnApply.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				setListSize(Integer.parseInt(imgNum.getText()));
			}
		});

		btnSave = new Button(imageView, SWT.NONE);
		btnSave.setBounds(btnApply.getLocation().x + btnApply.getSize().x + 20,
				btnApply.getLocation().y, 46, 20);
		btnSave.setText("Save");
		btnSave.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				config.storeXML();
			}
		});

		btnClose = new Button(imageView, SWT.NONE);
		btnClose.setBounds(btnSave.getLocation().x + btnSave.getSize().x + 4,
				btnSave.getLocation().y, 46, 20);
		btnClose.setText("Close");
		btnClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				result = true;
				Dispose();
				sShell.close();
			}
		});

		btnReload = new Button(imageView, SWT.NONE);
		btnReload.setBounds(btnClose.getLocation().x + btnClose.getSize().x
				+ 40, btnClose.getLocation().y, 46, 20);
		btnReload.setText("Reload");
		btnReload.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				config.loadXML();
				info = config.getImageInfo(tagCombo.getText());
				if (info == null) {
					info = new Tag_info();
					info.screen = screenSize;
					config.insertImageInfo(tagCombo.getText(), info);
				}
				initList();
			}
		});
		
		btnReloadTemplate = new Button(imageView, SWT.NONE);
		btnReloadTemplate.setBounds(btnReload.getLocation().x + btnReload.getSize().x
				+ 10, btnReload.getLocation().y, 106, 20);
		btnReloadTemplate.setText("Reload Template");
		btnReloadTemplate.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FormTemplateXmlStore.reLoad("");
				VersionMngXmlStore.reLoad("");
			}
		});

	}

	protected void initList() {

		imgNum.setText(String.valueOf(info.size()));

		if (info.size() > 1) {
			imageView.setSize(VIEW_SIZE.x + TOP_MARGIN, VIEW_SIZE.y);
			imagelist.setSize(VIEW_SIZE.x + TOP_MARGIN - 4, VIEW_SIZE.y - 54);
		} else {
			imageView.setSize(VIEW_SIZE.x, VIEW_SIZE.y);
			imagelist.setSize(VIEW_SIZE.x - 4, VIEW_SIZE.y - 54);
		}

		imageListclear();
		sShell.pack();
		Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		sShell.setSize(size.x + 5, size.y + 5);
		
		dsizeX.setText(Integer.toString(info.dftlSize.x));
		dsizeY.setText(Integer.toString(info.dftlSize.y));
		msizeX.setText(Integer.toString(info.minSize.x));
		msizeY.setText(Integer.toString(info.minSize.y));
		lmargin.setText(Integer.toString(info.textlMargin));
		rmargin.setText(Integer.toString(info.textrMargin));
		tSize.setText(Integer.toString(info.tSize));
		temp1.setText(info.temp1);
        temp2.setText(info.temp2);

		if (info.size() <= 0)
			return;

		File dir = getImageDir();

		StringBuilder s;
		ImageAdvisor image;
		for (int i = 0; i < info.size(); i++) {
			s = new StringBuilder(dir.getAbsolutePath());
			s.append(File.separator);
			s.append(screenSize);
			image = new ImageAdvisor(composite, info, i, s.toString());
			
			imageList.add(i, image);
			composite.setSize(composite.getSize().x, composite.getSize().y
					+ ImageAdvisor.GOURP_SIZE.y + 10);
		}

		imagelist.setMinSize(imagelist.getContent().computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	}

	/**
     * @return
     */
    public static File getImageDir() {
        String path = Activator.getDefault().getResourceLocationURL(
            ConfigManager.IMAGE_FOLDER).getFile();
        
        File dir = new File(Path.fromPortableString(path).toOSString());
        
        return dir;
    }

    private void imageListclear() {
		for (int i = 0; i < imageList.size(); i++) {
			imageList.get(i).Dispose();
		}
		imageList.clear();
		composite.setSize(imagelist.getSize().x, 20);
	}

	protected void setListSize(int listSize) {

		ImageAdvisor image;
		if (listSize > 1) {
			imageView.setSize(VIEW_SIZE.x + TOP_MARGIN, VIEW_SIZE.y);
			imagelist.setSize(VIEW_SIZE.x + TOP_MARGIN - 4, VIEW_SIZE.y - 54);
		} else {
			imageView.setSize(VIEW_SIZE.x, VIEW_SIZE.y);
			imagelist.setSize(VIEW_SIZE.x - 4, VIEW_SIZE.y - 54);
		}

		sShell.pack();
		Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		sShell.setSize(size.x + 5, size.y + 5);

        File dir = getImageDir();

		if (imageList.size() < listSize) {
			StringBuilder s;
			for (int i = imageList.size(); i < listSize; i++) {
				if (info.size() <= i) {
					Image_info imageInfo = new Image_info();
					info.add(i, imageInfo);
				}
				s = new StringBuilder(dir.getAbsolutePath());
				s.append(File.separator);
				s.append(screenSize);
				image = new ImageAdvisor(composite, info, i, s.toString());
				imageList.add(i, image);
				composite.setSize(composite.getSize().x, composite.getSize().y
						+ ImageAdvisor.GOURP_SIZE.y + 10);
			}
		} else if (imageList.size() > listSize) {
			for (int i = imageList.size(); i > listSize; i--) {
				image = imageList.remove(i - 1);
				image.Dispose();
				info.remove(i - 1);
				composite.setSize(composite.getSize().x, composite.getSize().y
						- ImageAdvisor.GOURP_SIZE.y - 10);
			}
		}

		imagelist.setMinSize(imagelist.getContent().computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	}

}
