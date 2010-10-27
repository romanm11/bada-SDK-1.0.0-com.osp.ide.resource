/**
 * 
 */
package com.osp.ide.resource.resourceexplorer;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.part.DrawCanvasPart;
import com.osp.ide.resource.part.FormFramePart;

/**
 * @author SRD1_CHJ
 *
 */
public class TestAction extends Action {
    /**
     * 
     */
    protected void testThumbnail() {
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
            .getProjects();
        if (projects.length == 0) {
            return;
        }
        
        final Display display = Display.getCurrent();
        
        final Shell shell = new Shell(display, SWT.TITLE | SWT.CLOSE);
        shell.setSize(500, 600);
        shell.open();
        shell.setText(ResourceExplorer.TEST_ACTION);
        
        Monitor primary = display.getPrimaryMonitor();
        org.eclipse.swt.graphics.Rectangle bound = primary.getBounds();
        org.eclipse.swt.graphics.Rectangle rect = shell.getBounds();
        
        int x = bound.x + (bound.width - rect.width) / 2;
        int y = bound.y + (bound.height - rect.height) / 2;
        
        shell.setLocation(x, y);
        shell.setBackground(ColorConstants.lightGray);
        DisposeListener disposeListener;
        RootEditPart rootEditPart;
        
        final IWorkbenchPage page = PlatformUI.getWorkbench().
                                        getActiveWorkbenchWindow().getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        GraphicalViewer viewer = null;
        for (int n = 0; n < editors.length; n++) {
            if (editors[n] != null
                    && editors[n].getId().equals(OspFormEditor.ID)) {
                viewer = ((OspFormEditor) editors[n].getEditor(false))
                        .getGraphicalViewer();
            }
        }
        if(viewer == null)
            return;
        rootEditPart = viewer.getRootEditPart();
        
        DrawCanvasPart drawCanvas = (DrawCanvasPart) rootEditPart
                .getChildren().get(0);
        FormFramePart framepart = (FormFramePart) drawCanvas.getChildren()
                .get(0);
        
        final Canvas canvas = new Canvas(shell, SWT.NULL);
        Rectangle frameRect = framepart.getFigure().getBounds();
        canvas.setSize((int) (frameRect.width
                * ImageThumbnail.THUMBNAIL_SCALE - 6),
                (int) (frameRect.height
                        * ImageThumbnail.THUMBNAIL_SCALE - 12));
        LightweightSystem lws = new LightweightSystem(canvas);
        
        final ImageThumbnail thumbnail = new ImageThumbnail(
                (Viewport) ((ScalableRootEditPart) rootEditPart)
                        .getFigure(), framepart);
        thumbnail.setSource(framepart.getFigure());
        
        lws.setContents(thumbnail);
        
        disposeListener = new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                if (thumbnail != null) {
                    thumbnail.deactivate();
                }
            }
        };
        viewer.getControl().addDisposeListener(disposeListener);
        
        thumbnail.addMouseListener(new MouseListener() {
        
            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }
        
            @Override
            public void mousePressed(MouseEvent me) {
            }
        
            @Override
            public void mouseReleased(MouseEvent me) {
                // //////////////////////////////////////////
                // Screen Capture test
                try {
                    java.awt.Rectangle tRect = new java.awt.Rectangle();
                    thumbnail.getPreferredSize();
                    tRect.x = shell.getLocation().x
                            + shell.getBorderWidth() + 2;
                    tRect.y = shell.getLocation().y + 28;
                    tRect.width = canvas.getSize().x;
                    tRect.height = canvas.getSize().y;
        
                    BufferedImage bffImg = new Robot()
                            .createScreenCapture(tRect);
        
                    String fullName = "d:/temp/capture."
                            + ResourceExplorer.IMG_FORMAT_JPG;
                    String format = ResourceExplorer.IMG_FORMAT_JPG;
                    File file = new File(fullName);
                    ImageIO.write(bffImg, format, file);
        
                    Shell imageShell = new Shell(Display.getCurrent(),
                            SWT.TITLE | SWT.CLOSE);
                    Canvas imageView = new Canvas(imageShell, SWT.NULL);
        
                    final Image image = new Image(imageView
                            .getDisplay(), thumbnail
                            .getThumbnailImage(), SWT.IMAGE_COPY);
                    System.out.println("Click Image Size : "
                            + image.getBounds().width + "- "
                            + image.getBounds().height);
        
                    Image captureImage = new Image(null, fullName);
                    imageView.setBackgroundImage(captureImage);
                    imageView.setSize(tRect.width, tRect.height);
                    imageShell.setSize(500, 600);
                    imageShell.pack();
                    imageShell.open();
        
                } catch (HeadlessException e) {
                    e.printStackTrace();
                } catch (AWTException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //
                // ///////////////////////////////////////////////////////////
            }
        });
        
        Button btnLeft = new Button(shell, SWT.NONE);
        btnLeft.setBounds(430, 10, 50, 20);
        btnLeft.setText("Left");
        btnLeft.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Point loc = thumbnail.getDrawLocation();
                loc.x = loc.x - 2;
                thumbnail.setDrawLocation(loc);
            }
        });
        
        Button btnRight = new Button(shell, SWT.NONE);
        btnRight.setBounds(btnLeft.getBounds().x,
                btnLeft.getBounds().y + 30, btnLeft.getBounds().width,
                btnLeft.getBounds().height);
        btnRight.setText("Right");
        btnRight.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Point loc = thumbnail.getDrawLocation();
                loc.x = loc.x + 2;
                thumbnail.setDrawLocation(loc);
            }
        });
        
        Button btnUp = new Button(shell, SWT.NONE);
        btnUp
                .setBounds(btnRight.getBounds().x,
                        btnRight.getBounds().y + 30, btnRight
                                .getBounds().width, btnRight
                                .getBounds().height);
        btnUp.setText("Up");
        btnUp.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Point loc = canvas.getLocation();
                loc.y = loc.y - 2;
                canvas.setLocation(loc);
            }
        });
        
        Button btnDwn = new Button(shell, SWT.NONE);
        btnDwn.setBounds(btnUp.getBounds().x, btnUp.getBounds().y + 30,
                btnUp.getBounds().width, btnUp.getBounds().height);
        btnDwn.setText("Dwn");
        btnDwn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                Point loc = canvas.getLocation();
                loc.y = loc.y + 2;
                canvas.setLocation(loc);
            }
        });
        
        Button btnRotC = new Button(shell, SWT.NONE);
        btnRotC.setBounds(btnDwn.getBounds().x,
                btnDwn.getBounds().y + 30, btnDwn.getBounds().width,
                btnDwn.getBounds().height);
        btnRotC.setText("RotC");
        btnRotC.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.addDegree();
            }
        });
        
        Button btnRot = new Button(shell, SWT.NONE);
        btnRot.setBounds(btnRotC.getBounds().x,
                btnRotC.getBounds().y + 30, btnRotC.getBounds().width,
                btnRotC.getBounds().height);
        btnRot.setText("Rot");
        btnRot.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.subDegree();
            }
        });
        
        Button btnVisible = new Button(shell, SWT.NONE);
        btnVisible.setBounds(btnRot.getBounds().x,
                btnRot.getBounds().y + 30, btnRot.getBounds().width,
                btnRot.getBounds().height);
        btnVisible.setText("Visible");
        btnVisible.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                if (thumbnail.isVisible())
                    thumbnail.setVisible(false);
                else
                    thumbnail.setVisible(true);
            }
        });
        
        Button btnEFInLeft = new Button(shell, SWT.NONE);
        btnEFInLeft.setBounds(btnVisible.getBounds().x, btnVisible
                .getBounds().y + 30, btnVisible.getBounds().width,
                btnVisible.getBounds().height);
        btnEFInLeft.setText("FILeft");
        btnEFInLeft.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidinLeft(0, 1000);
            }
        });
        
        Button btnEFInRight = new Button(shell, SWT.NONE);
        btnEFInRight.setBounds(btnEFInLeft.getBounds().x, btnEFInLeft
                .getBounds().y + 30, btnEFInLeft.getBounds().width,
                btnEFInLeft.getBounds().height);
        btnEFInRight.setText("FIRight");
        btnEFInRight.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidinRight(0, 1000);
            }
        });
        
        Button btnEFInTop = new Button(shell, SWT.NONE);
        btnEFInTop.setBounds(btnEFInRight.getBounds().x, btnEFInRight
                .getBounds().y + 30, btnEFInRight.getBounds().width,
                btnEFInRight.getBounds().height);
        btnEFInTop.setText("FITop");
        btnEFInTop.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidinTop(0, 1000);
            }
        });
        
        Button btnEFInBottom = new Button(shell, SWT.NONE);
        btnEFInBottom.setBounds(btnEFInTop.getBounds().x, btnEFInTop
                .getBounds().y + 30, btnEFInTop.getBounds().width,
                btnEFInTop.getBounds().height);
        btnEFInBottom.setText("FIBottom");
        btnEFInBottom.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidinBottom(0, 1000);
            }
        });
        
        Button btnEFOutLeft = new Button(shell, SWT.NONE);
        btnEFOutLeft.setBounds(btnEFInBottom.getBounds().x,
                btnEFInBottom.getBounds().y + 30, btnEFInBottom
                        .getBounds().width,
                btnEFInBottom.getBounds().height);
        btnEFOutLeft.setText("FOLeft");
        btnEFOutLeft.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidoutLeft(0, 1000);
            }
        });
        
        Button btnEFOutRight = new Button(shell, SWT.NONE);
        btnEFOutRight.setBounds(btnEFOutLeft.getBounds().x,
                btnEFOutLeft.getBounds().y + 30, btnEFOutLeft
                        .getBounds().width,
                btnEFOutLeft.getBounds().height);
        btnEFOutRight.setText("FORight");
        btnEFOutRight.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidoutRight(0, 1000);
            }
        });
        
        Button btnEFOutTop = new Button(shell, SWT.NONE);
        btnEFOutTop.setBounds(btnEFOutRight.getBounds().x,
                btnEFOutRight.getBounds().y + 30, btnEFOutRight
                        .getBounds().width,
                btnEFOutRight.getBounds().height);
        btnEFOutTop.setText("FOTop");
        btnEFOutTop.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidoutTop(0, 1000);
            }
        });
        
        Button btnEFOutBottom = new Button(shell, SWT.NONE);
        btnEFOutBottom.setBounds(btnEFOutTop.getBounds().x, btnEFOutTop
                .getBounds().y + 30, btnEFOutTop.getBounds().width,
                btnEFOutTop.getBounds().height);
        btnEFOutBottom.setText("FOBottom");
        btnEFOutBottom.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.faidoutBottom(0, 1000);
            }
        });
        
        Button btnReset = new Button(shell, SWT.NONE);
        btnReset.setBounds(btnEFOutBottom.getBounds().x, btnEFOutBottom
                .getBounds().y + 30, btnEFOutBottom.getBounds().width,
                btnEFOutBottom.getBounds().height);
        btnReset.setText("Reset");
        btnReset.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                thumbnail.resetLocation();
            }
        });
    }

}
