package com.osp.ide.resource.resourceexplorer;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.Viewport;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspForm;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editform.OspFormEditorInput;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.part.DrawCanvasPart;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.resinfo.FrameConst;

/**
 * This class demonstrates how to create your own dialog classes. It allows
 * users to input a String
 */
public class FormPreviewDialog extends Dialog {

	// dialog 결과값
	public static final String TITLE = "Preview";
	private static final String SCENARIO = "Scenario";
	private static final int TOP_MARGIN = 20;
	private static final int SIDE_MARGIN = 8;
	private static final Point PREVIEW_SIZE = new Point(344, 560);
	private static final Point MINIVIEW_SIZE = new Point(104, 160);
	public static final int EFFECT_TYPE = 1;
	boolean result = false;

	private Shell sShell = null; // @jve:decl-index=0:visual-constraint="3,10"
	private Hashtable<String, ImageThumbnail> sceneImageList = new Hashtable<String, ImageThumbnail>();
	private Hashtable<String, OspFormEditor> openedTWEditors = new Hashtable<String, OspFormEditor>();
	private Hashtable<String, Boolean> isGrid = new Hashtable<String, Boolean>();
	private Hashtable<String, String> mode = new Hashtable<String, String>();
	private PreviewCanvas preView;

	private Button btnClose = null;
	private Button btnStart = null;
	private ResourceExplorer view;
	private ScrolledComposite sideView;
	private Canvas scrollView;
	private Point windowSize;
	private boolean isMax;
	protected String selectedView;
	private Point windowLocation;
	private Combo countCombo;
	private Vector<EffectAdvisor> effectList = new Vector<EffectAdvisor>();
	protected int sceneCount;
	private ScrolledComposite scenario;
	private Composite composite;
	private Button btnTest;
	private Button btnTest2;
	private Composite sceneView;

	// /////////////////////////////////////////////////////

	public FormPreviewDialog(Shell parent, ResourceExplorer view) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		if(view == null)
			return;
		this.view = view;
	}

	public boolean open(String screen) {
		ResourceExplorer resourceview = ResourceExplorer.getResourceView();
		if(resourceview == null)
			return false;
		windowSize = view.getSite().getShell().getSize();
		windowLocation = view.getSite().getShell().getLocation();
		isMax = view.getSite().getShell().getMaximized();
		view.getSite().getShell().setSize(0, 0);
		view.getSite().getShell().setLocation(0, -30);

		sShell = new Shell(getParent(), getStyle());
		ProgressMonitorDialog progress = new ProgressMonitorDialog(sShell);
		progress.setOpenOnRun(true);
		progress.open();
		progress.getProgressMonitor().beginTask(TITLE, 100);
		progress.getProgressMonitor().subTask("Open Preview Windows");

		sShell.setText(TITLE);
		progress.getProgressMonitor().worked(10);
		createTWContents(sShell, progress, screen);
				
		
		createScenario(progress);
		sShell.setBounds(0, 0, 800, 680);
		sShell.pack();
		Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		sShell.setSize(size.x + 5, size.y + 5);
		sShell.open();

		progress.getProgressMonitor().worked(10);
		progress.getProgressMonitor().done();
		progress.close();

		Monitor primary = Display.getCurrent().getPrimaryMonitor();
		org.eclipse.swt.graphics.Rectangle bound = primary.getBounds();
		org.eclipse.swt.graphics.Rectangle rect = sShell.getBounds();

		int x = bound.x + (bound.width - rect.width) / 2;
		int y = bound.y + (bound.height - rect.height) / 2;

		sShell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				IWorkbenchPage page = view.getSite().getPage();
				IEditorReference[] editors = page.getEditorReferences();
				for (int n = 0; n < editors.length; n++) {
					if (!(editors[n].getEditor(false) instanceof OspFormEditor))
						continue;

					OspFormEditor tweditor = openedTWEditors.get(editors[n].getTitleToolTip());
					if (tweditor != null)
						page.closeEditor(tweditor, false);
					else {
						tweditor = (OspFormEditor) editors[n].getEditor(false);
						tweditor.setGridVisible(isGrid.get(tweditor
								.getTitleToolTip()));
						tweditor.setMode(mode.get(tweditor.getTitleToolTip()));
					}
				}
				view.getSite().getShell().setSize(windowSize);
				view.getSite().getShell().setLocation(windowLocation);
				if (isMax)
					view.getSite().getShell().setMaximized(true);
			}
		});

		sShell.setLocation(x, y);

		Display display = getParent().getDisplay();
		while (!sShell.isDisposed()) {
			if (!display.isDisposed() && !display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	private void createScenario(ProgressMonitorDialog progress) {
		sceneView = new Composite(sShell, SWT.BORDER);
		sceneView.setToolTipText(SCENARIO);
		sceneView.setSize(PREVIEW_SIZE.x, PREVIEW_SIZE.y);
		sceneView.setLocation(sideView.getLocation().x + sideView.getSize().x
				+ SIDE_MARGIN, sideView.getLocation().y);

		Label label = new Label(sceneView, SWT.NONE);
		label.setText("Scene Count");
		label.setBounds(new Rectangle(6, 20, 86, 14));

		progress.getProgressMonitor().worked(10);
		createSceneCount();

		progress.getProgressMonitor().worked(10);
		scenario = new ScrolledComposite(sceneView, SWT.VERTICAL);
		scenario.setToolTipText(SCENARIO);
		scenario.setSize(PREVIEW_SIZE.x, PREVIEW_SIZE.y - 64);
		scenario.setLocation(0, 62);
		scenario.getVerticalBar().setIncrement(EffectAdvisor.GROUP_HEIGTH/2);

		composite = new Composite(scenario, SWT.NONE);
		scenario.setContent(composite);

		progress.getProgressMonitor().worked(10);
		btnStart = new Button(sceneView, SWT.NONE);
		btnStart.setBounds(new Rectangle(countCombo.getLocation().x
				+ countCombo.getSize().x + 20, countCombo.getLocation().y, 40,
				20));
		btnStart.setText("Start");
		btnStart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (effectList.size() <= 0)
					return;

				btnStart.setEnabled(false);
				effectList.get(0).setFocus();
				final Timer timer = new Timer(); // 타이머 생성
				{
					int delay = 0;
					for (int i = 0; i < sceneCount; i++) {
						EffectAdvisor preScene = effectList.get(i);
						EffectAdvisor postScene = null;
						String postImageName = null;
						if (i < sceneCount - 1) {
							postScene = effectList.get(i + 1);
							postImageName = postScene.getSceneName();
						}
						String preImageName = preScene.getSceneName();
						if (i == 0)
							preView.setToolTipText(preImageName);

						if (PreviewCanvas.getEffectIndex(preScene
								.getSlideEffect()) == PreviewCanvas.EFFECT_SLIDE_NULL)
							delay = delay + preScene.getViewTime() * 1000 + 100;
						else
							delay = delay + preScene.getViewTime() * 1000
									+ PreviewCanvas.EFFECT_DELAY;

						preView.effectSlide(preImageName, postImageName, delay,
								PreviewCanvas.EFFECT_DELAY, preScene
										.getSlideEffect());
					}
					timer.schedule(new TimerTask(){

						@Override
						public void run() {
							Display.getDefault().syncExec(new Runnable() {
								@Override
								public void run() {
									btnStart.setEnabled(true);
								}
							});
							timer.cancel();
						}}, delay + 1000);
				}
			}
		});

		btnTest = new Button(sceneView, SWT.NONE);
		btnTest.setBounds(new Rectangle(btnStart.getLocation().x
				+ btnStart.getSize().x + 10, btnStart.getLocation().y, 40, 20));
		btnTest.setText("Test");
		btnTest.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				preView.drawImage("IDF_FORM1", -50, 0);
			}
		});
		btnTest.setVisible(false);

		btnTest2 = new Button(sceneView, SWT.NONE);
		btnTest2.setBounds(new Rectangle(btnTest.getLocation().x
				+ btnTest.getSize().x + 10, btnTest.getLocation().y, 40, 20));
		btnTest2.setText("Test2");
		btnTest2.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				preView.redraw(10, 0, 40, preView.getSize().y, false);
			}
		});
		btnTest2.setVisible(false);

		sceneView.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				btnStart.setFocus();
			}

			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
	}

	private void createSceneCount() {
		countCombo = new Combo(sceneView, SWT.DROP_DOWN);
		countCombo.setBounds(new Rectangle(95, 16, 50, 10));

		for (int i = 1; i <= sceneImageList.size(); i++)
			countCombo.add(Integer.toString(i));

		countCombo.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				sceneCount = Integer.parseInt(countCombo.getText());
				createSceneControl();
				btnStart.setFocus();
			}
		});
		countCombo.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (Character.isDigit(e.character) || e.character == SWT.BS
						|| e.character == SWT.CR) {
					if (!countCombo.getText().isEmpty()) {
						sceneCount = Integer.parseInt(countCombo.getText());
						createSceneControl();
					}
				} else {
					if (countCombo.getItemCount() > 0) {
						countCombo.select(0);
						sceneCount = Integer.parseInt(countCombo.getText());
						createSceneControl();
					} else {
						countCombo.setText("");
					}
				}
			}
		});
	}

	protected void createSceneControl() {
		if (sceneImageList.size() <= 0)
			return;
		EffectAdvisor effect;
		effectListclear();
		if (sceneCount > 5) {
			sceneView.setSize(PREVIEW_SIZE.x + TOP_MARGIN, PREVIEW_SIZE.y);
			scenario.setSize(PREVIEW_SIZE.x + TOP_MARGIN - 4, PREVIEW_SIZE.y - 64);
		} else {
			sceneView.setSize(PREVIEW_SIZE.x, PREVIEW_SIZE.y);
			scenario.setSize(PREVIEW_SIZE.x, PREVIEW_SIZE.y - 64);
		}

		sShell.pack();
		Point size = sShell.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		sShell.setSize(size.x + 5, size.y + 5);

		for (int i = 0; i < sceneCount; i++) {
			effect = new EffectAdvisor(composite, sceneImageList.keys(), i,
					EFFECT_TYPE);
			effectList.add(i, effect);
			composite.setSize(composite.getSize().x, composite.getSize().y
					+ EffectAdvisor.GROUP_HEIGTH);
		}

		scenario.setMinSize(scenario.getContent().computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
	}

	private void effectListclear() {
		for (int i = 0; i < effectList.size(); i++) {
			effectList.get(i).deleteControl();
		}
		effectList.clear();
		composite.setSize(scenario.getSize().x, 20);
	}

	private void createTWContents(final Shell shell,
			ProgressMonitorDialog progress, String screen) {

		ResourceExplorer view = ResourceExplorer.getResourceView();
		if(view == null)
			return;
		Point frameSize = Activator.getSScreenToPoint(screen);
		Canvas backView = new Canvas(shell, SWT.NONE);
		backView.setSize(frameSize.x * PREVIEW_SIZE.y/frameSize.y, PREVIEW_SIZE.y);
		backView.setLocation(-(PREVIEW_SIZE.x + 10), TOP_MARGIN);
		preView = new PreviewCanvas(shell, SWT.BORDER);
		preView.setSize(frameSize.x * PREVIEW_SIZE.y/frameSize.y, PREVIEW_SIZE.y);
		preView.setLocation(SIDE_MARGIN, TOP_MARGIN);
		preView.setBackground(ColorConstants.lightGray);

		progress.getProgressMonitor().worked(10);
		createSideView(frameSize);
		Hashtable<String, OspForm> scenes = view.manager.m_Form.get(screen);
		if (scenes == null)
			return;
		Enumeration<String> keys = scenes.keys();
		Vector<String> vectorKeys = new Vector<String>();
		while (keys.hasMoreElements())
			vectorKeys.add(0, keys.nextElement());

		final IWorkbenchPage page = view.getSite().getPage();
		IEditorReference[] editors = page.getEditorReferences();

		for (int i = 0; i < vectorKeys.size(); i++) {
			progress.getProgressMonitor().worked(10);
			String key = vectorKeys.elementAt(i);
			OspFormEditor editor = null;
			GraphicalViewer viewer = null;
			for (int n = 0; n < editors.length; n++) {
				if (editors[n] != null
						&& editors[n].getTitleToolTip().equals(key)) {
					editor = ((OspFormEditor) editors[n].getEditor(false));
				}
			}
			if (editor == null) {
				try {
					editor = (OspFormEditor) page
							.openEditor(new OspFormEditorInput(view.manager, screen, key,
									view.string), OspFormEditor.ID, false);
					progress.getProgressMonitor().worked(10);
				} catch (PartInitException e) {
					e.printStackTrace();
					return;
				}
				openedTWEditors.put(editor.getTitleToolTip(), editor);
			}
			isGrid.put(editor.getTitleToolTip(), editor.isGrid());
			editor.setGridVisible(false);
			mode.put(editor.getTitleToolTip(), editor.getMode());
			editor.setMode(FrameConst.cszFrmMode[FrameConst.PORTRAIT]);
			viewer = editor.getGraphicalViewer();
			RootEditPart rootEditPart = viewer.getRootEditPart();
			DrawCanvasPart drawCanvas = (DrawCanvasPart) rootEditPart
					.getChildren().get(0);
			FormFramePart framepart = (FormFramePart) drawCanvas.getChildren().get(0);

			final ImageThumbnail thumbnail = new ImageThumbnail(
					(Viewport) ((ScalableRootEditPart) rootEditPart)
							.getFigure(), framepart, PREVIEW_SIZE, preView
							.getImageList());
			thumbnail.setSource(framepart.getFigure());

			LightweightSystem lws = new LightweightSystem(backView);
			lws.setContents(thumbnail);

			DisposeListener disposeListener = new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					if (thumbnail != null) {
						thumbnail.deactivate();
					}
				}
			};
			viewer.getControl().addDisposeListener(disposeListener);

			progress.getProgressMonitor().worked(10);
			createTWMiniView(i, rootEditPart, framepart);
			progress.getProgressMonitor().worked(10);
		}

		btnClose = new Button(shell, SWT.NONE);
		btnClose.setBounds(new Rectangle(sideView.getLocation().x, preView
				.getLocation().y
				+ PREVIEW_SIZE.y + 10, sideView.getSize().x, 30));
		btnClose.setText("Close");
		btnClose.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				result = true;
				shell.close();
			}
		});
	}

	private void createSideView(Point frameSize) {
		sideView = new ScrolledComposite(sShell, SWT.BORDER | SWT.VERTICAL);
		sideView.setSize(MINIVIEW_SIZE.x + 14, preView.getSize().y);
		sideView.setLocation(preView.getLocation().x + preView.getSize().x
				+ SIDE_MARGIN, TOP_MARGIN);
		
		sideView.getVerticalBar().setIncrement(MINIVIEW_SIZE.y/2);

		scrollView = new Canvas(sideView, SWT.NONE);
		sideView.setContent(scrollView);
		
		sideView.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
			}
			@Override
			public void mouseUp(MouseEvent e) {
				preView.setToolTipText(null);
			}});
		scrollView.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
			}
			@Override
			public void mouseUp(MouseEvent e) {
				preView.setToolTipText(null);
			}});
	}

	public void createTWMiniView(int index, RootEditPart rootEditPart,
			FormFramePart framepart) {
		final String key = ((Form) framepart.getModel()).getName();
		Canvas miniView = new Canvas(scrollView, SWT.NONE);
		miniView.setToolTipText(key);
		miniView.setSize(MINIVIEW_SIZE);
		miniView.setLocation(5, 5 + (int) ((MINIVIEW_SIZE.y + 5) * index));
		LightweightSystem miniLws = new LightweightSystem(miniView);
		final ImageThumbnail miniThumbnail = new ImageThumbnail(
				(Viewport) ((ScalableRootEditPart) rootEditPart).getFigure(),
				framepart, MINIVIEW_SIZE, null);
		miniThumbnail.setSource(framepart.getFigure());
		miniLws.setContents(miniThumbnail);
		sceneImageList.put(key, miniThumbnail);

		miniView.addMouseListener(new MouseListener() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
			}

			@Override
			public void mouseUp(MouseEvent e) {
				switch (e.button) {
				case 1:
					preView.setToolTipText(key);
					break;
				}
			}
		});
		if (sceneImageList.size() > 3)
			sideView.setSize(MINIVIEW_SIZE.x + 30, PREVIEW_SIZE.y);
		scrollView.setSize(MINIVIEW_SIZE.x + 10, miniView.getLocation().y
				+ miniView.getSize().y + 10);
		sideView.setMinSize(scrollView.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public class Refresh extends TimerTask {
		int type;
		private Timer timer;
		private ImageThumbnail imageThumbnail;

		public Refresh(Timer timer, ImageThumbnail imageThumbnail) {
			this.timer = timer;
			this.imageThumbnail = imageThumbnail;
		}

		synchronized public void run() {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				synchronized public void run() {
					imageThumbnail.resetLocation();
				}
			});
			timer.cancel();
		}
	}
}
