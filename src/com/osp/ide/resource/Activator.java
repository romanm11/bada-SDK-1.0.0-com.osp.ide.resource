package com.osp.ide.resource;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.osp.ide.resource.common.Image_info;
import com.osp.ide.resource.common.ScreenSizeReader;
import com.osp.ide.resource.editform.OspFormEditor;
import com.osp.ide.resource.editpanel.OspPanelEditor;
import com.osp.ide.resource.editpopup.OspPopupEditor;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;
import com.osp.ide.resource.string.OspStringEditor;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.osp.ide.resource";
	public static final String MANIFEST_NAME = "manifest.xml";

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public URL getResourceLocationURL(String path) {
		if (getBundle() != null) {
			URL installURL = getBundle().getEntry("/" + path);
			URL localURL = null;
			try {
				localURL =  FileLocator.toFileURL(installURL);
				return localURL;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return installURL;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static Vector<String> getSortedKeys(Hashtable table, boolean isPrint) {
		Vector<String> v = new Vector<String>(table.keySet());
		Collections.sort(v);

		if (isPrint) {
			Enumeration<String> keys = v.elements();
			while (keys.hasMoreElements()) {
				StringBuilder printStr = new StringBuilder("Key : ");
				String key = keys.nextElement();
				printStr = printStr.append(key);
				printStr = printStr.append(", Value : ");
				Object value = table.get(key);
				printStr = printStr.append(value);

				System.out.println(printStr.toString());
			}
		}
		return v;
	}

	public static Point getSScreenToPoint(String sPoint) {
		Point point = new Point(480, 800);
		if (sPoint != null && !sPoint.isEmpty()) {
			StringTokenizer stok = new StringTokenizer(sPoint, " xX");
			point = new Point(0, 0);
			if (stok.hasMoreTokens()) {
				try {
					point.x = Integer.parseInt(stok.nextToken());
					point.y = Integer.parseInt(stok.nextToken());
				} catch (NumberFormatException e) {
					setErrorMessage("Activator.getSScreenToPoint()",
							"Activator getScreen() NumberFormatException - "
									+ e.getMessage(), e);
				}
			}
			if (point.x > point.y) {
				int temp = point.y;
				point.y = point.x;
				point.x = temp;
			}
		}
		return point;
	}

	public static Vector<String> getStringScreen(String project) {
        Vector<String> retPoint = new Vector<String>();
	    
        retPoint.add("480x800");
        retPoint.add("240x400");
        
        return retPoint;
	}
	    
	public static Vector<String> getScreenFromManifest(String project) {
		Vector<String> retPoint = new Vector<String>();
		String point = "480x800";
		retPoint.add(point);
		if (project == null || project.isEmpty()) {
			ResourceExplorer view = ResourceExplorer.getResourceView();
			if (view == null)
				return retPoint;
			project = view.getCurProject();
		}
		if (project == null || project.isEmpty())
			return retPoint;
		IProject fProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(project);
		if (fProject == null)
			return retPoint;

		IFile file = fProject.getFile(MANIFEST_NAME);
		ScreenSizeReader store = new ScreenSizeReader(file.getLocation()
				.toOSString());
		Vector<String> vectorValue = store.getScreenSizes();
		if (vectorValue == null || vectorValue.isEmpty()
				|| vectorValue.size() == 0)
			return retPoint;
		else
			return vectorValue;
	}

	public static Point getMaxScreen(String project) {
        Point maxPoint = new Point(480, 800);
        
        return maxPoint;
	}
	
    public static Point getMaxScreenFromManifest(String project) {
		Point maxPoint = new Point(480, 800);
		if (project == null || project.isEmpty()) {
			ResourceExplorer view = ResourceExplorer.getResourceView();
			if (view == null)
				return maxPoint;
			project = view.getCurProject();
		}
		IProject fProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(project);
		if (fProject == null)
			return maxPoint;

		IFile file = fProject.getFile(MANIFEST_NAME);
		ScreenSizeReader store = new ScreenSizeReader(file.getLocation()
				.toOSString());
		Vector<String> vectorValue = store.getScreenSizes();
		for (int i = 0; i < vectorValue.size(); i++) {
			String value = vectorValue.elementAt(i);
			Point point = new Point(0, 0);
			if (value != null && !value.isEmpty()) {
				StringTokenizer stok = new StringTokenizer(value, " xX");
				if (stok.hasMoreTokens()) {
					try {
						point.x = Integer.parseInt(stok.nextToken());
						point.y = Integer.parseInt(stok.nextToken());
					} catch (NumberFormatException e) {
						StringBuilder message = new StringBuilder(
								"Activator getScreen() NumberFormatException - ");
						message = message.append(e.getMessage());
						setErrorMessage("Activator.getMaxScreen()", message
								.toString(), null);
					}
				}
				if (point.x > point.y) {
					int temp = point.y;
					point.y = point.x;
					point.x = temp;
				}
				if (point.x > maxPoint.x)
					maxPoint = point;
			}
		}
		return maxPoint;
	}

	public static void setErrorMessage(String location, String message, Exception e) {
		WorkbenchWindow window = (WorkbenchWindow) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IStatusLineManager statusManager = window.getActionBars()
					.getStatusLineManager();
			statusManager.setErrorMessage(message);
			if(message == null || message.isEmpty())
			    return;
			
			if(e != null)
				message = getMessage(message, e);
			writeLog(Status.ERROR, location, message);
		}
	}

	private static String getMessage(String message, Exception e) {
		StringBuilder builder = new StringBuilder(e.getClass().toString());
		builder.append(message);
		StackTraceElement[] trace = e.getStackTrace();
		for(int i=0; i<trace.length; i++) {
			builder.append("\n at ");
			builder.append(trace[i].getClassName() + "(");
			builder.append(trace[i].getMethodName() +":");
			builder.append(trace[i].getLineNumber() + ")");
		}
		return builder.toString();
	}

	public static void setErrorMessage(Image image, String location,
			String message, Exception e) {
		WorkbenchWindow window = (WorkbenchWindow) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IStatusLineManager statusManager = window.getActionBars()
					.getStatusLineManager();
			statusManager.setErrorMessage(image, message);
            if(message == null || message.isEmpty())
                return;
            
			if(e != null)
				message = getMessage(message, e);
			writeLog(Status.ERROR, location, message);
		}
	}

	public static void setStatusMessage(String message) {
		WorkbenchWindow window = (WorkbenchWindow) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IStatusLineManager statusManager = window.getActionBars()
					.getStatusLineManager();
			statusManager.setMessage(message);
		}
	}

	public static void setStatusMessage(Image image, String message) {
		WorkbenchWindow window = (WorkbenchWindow) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			IStatusLineManager statusManager = window.getActionBars()
					.getStatusLineManager();
			statusManager.setMessage(image, message);
		}
	}

	public static Vector<Image_info> getImageList(String type) {
		return null;
	}

    public static IEditorReference[] getEditorReference(String editorId) {
        ArrayList<IEditorReference> retEditor = new ArrayList<IEditorReference>();
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            if (editors[n] != null
                    && editors[n].getId().equals(editorId)) {
                retEditor.add(editors[n]);
            }
        }
        
        return retEditor.toArray(new IEditorReference[0]);
    }

    public static IEditorReference[] getAllFrameEditorReference() {
        ArrayList<IEditorReference> retEditor = new ArrayList<IEditorReference>();
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            if (editors[n].getId().equals(OspFormEditor.ID)
                || editors[n].getId().equals(OspPopupEditor.ID)
                || editors[n].getId().equals(OspPanelEditor.ID)) {
                retEditor.add(editors[n]);
            }
        }
        
        return retEditor.toArray(new IEditorReference[0]);
    }

    public static IEditorReference[] getAllResourceEditorReference() {
        ArrayList<IEditorReference> retEditor = new ArrayList<IEditorReference>();
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorReference[] editors = page.getEditorReferences();
        for (int n = 0; n < editors.length; n++) {
            if (editors[n].getId().equals(OspFormEditor.ID)
                || editors[n].getId().equals(OspPopupEditor.ID)
                || editors[n].getId().equals(OspPanelEditor.ID)
                || editors[n].getId().equals(OspStringEditor.ID)) {
                retEditor.add(editors[n]);
            }
        }
        
        return retEditor.toArray(new IEditorReference[0]);
    }

    /**
     * @param newScreen
     * @return
     */
    public static String getCorrectScreen(String project, String screen) {
        Vector<String> screens = getStringScreen(project);
        for(int i=0; i<screens.size(); i++) {
            if(screen.toUpperCase(Locale.getDefault()).
                equals(screens.get(i).toUpperCase(Locale.getDefault())))
                return screens.get(i);
        }
        return "";
    }

    /**
     * @param status
     * @param location
     * @param message
     */
    public static void writeLog(int status, String location, String message) {
        ILog log = plugin.getLog();
        log.log(new Status(status, location, message));
    }
    
    /**
     * @return
     */
    public static String getBVersion() {
        String bVersion = "";
        
        bVersion = getDefault().getBundle().getVersion().toString();
        
        return bVersion;
    }
}
