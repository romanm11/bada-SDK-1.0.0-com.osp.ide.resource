package com.osp.ide.resource.resourceexplorer;

import java.util.Enumeration;
import java.util.Hashtable;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.documents.OspResourceManager;

public class ResourceSelectScenePage extends WizardPage {

	private Image[] img_scenetelplate_unselected;
	private Image[] img_scenetelplate_selected;
	private Hashtable<String, Button> scene_template;

	private IStatus fCurrStatus;
	private boolean fPageVisible;
	private Composite templateView;
	private Text textArea;
	private ScrolledComposite tempView;
	private int selectedTemplate;

	protected ResourceSelectScenePage(String pageName) {
		super(pageName);
		fPageVisible = false;
		fCurrStatus = new StatusInfo();

		setTitle("Select Scene Template");
		setDescription("Select your Scene Template.");

		scene_template = new Hashtable<String, Button>();
		initImage();
	}

	private void initImage() {
		StringBuilder s;
		img_scenetelplate_unselected = new Image[OspResourceManager.SCENETEMPLATE_COUNT];
		img_scenetelplate_selected = new Image[OspResourceManager.SCENETEMPLATE_COUNT];

		for (int i = 0; i < OspResourceManager.SCENETEMPLATE_COUNT; i++) {
			s = new StringBuilder("icons/scenetemplate_unselected_");
			s.append(i);
			s.append(".jpg");
			img_scenetelplate_unselected[i] = AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID, s.toString())
					.createImage();
			s = new StringBuilder("icons/scenetemplate_selected_");
			s.append(i);
			s.append(".jpg");
			img_scenetelplate_selected[i] = AbstractUIPlugin
					.imageDescriptorFromPlugin(Activator.PLUGIN_ID, s.toString())
					.createImage();
		}
	}

	@Override
	public void dispose() {
		for (int i = 0; i < OspResourceManager.SCENETEMPLATE_COUNT; i++) {
			if(img_scenetelplate_unselected[i] != null) {
				img_scenetelplate_unselected[i].dispose();
				img_scenetelplate_unselected[i] = null;
			}
			if(img_scenetelplate_selected[i] != null) {
				img_scenetelplate_selected[i].dispose();
				img_scenetelplate_selected[i] = null;
			}
		}
		if(templateView != null && !templateView.isDisposed()) {
			templateView.dispose();
			templateView = null;
		}
		
		Enumeration<String> keys = scene_template.keys();
		while(keys.hasMoreElements()) {
			Button template = scene_template.get(keys.nextElement());
			if(template != null && !template.isDisposed()) {
				template.dispose();
				template = null;
			}
		}
		
		if(tempView != null && !tempView.isDisposed()) {
			tempView.dispose();
			tempView = null;
		}
		
		if(textArea != null && !textArea.isDisposed()) {
			textArea.dispose();
			textArea = null;
		}
			
		super.dispose();
	}

	@Override
	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());

		initializeDialogUnits(parent);

		composite.setSize(getShell().getSize().x - 20, 300);

		Label label = new Label(composite, SWT.NONE);
		label.setText("Select Scene Template");
		label.setLocation(5, 10);
		label.setSize(getShell().getSize().x - 30, 20);

		createTemplateView(composite);

		textArea = new Text(composite, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER
				| SWT.READ_ONLY);
		textArea.setLocation(5, tempView.getLocation().y + tempView.getSize().y
				+ 10);
		textArea.setSize(getShell().getSize().x - 30, 80);

		setControl(composite);
		setSelectedTemplate(Integer.toString(0));
	}

	private void createTemplateView(Composite composite) {
		tempView = new ScrolledComposite(composite, SWT.BORDER | SWT.VERTICAL);
		tempView.setSize(composite.getSize().x - 10, 240);
		tempView.setLocation(5, 30);
		tempView.getVerticalBar().setIncrement(40);
		templateView = new Composite(tempView, SWT.NONE);
		// templateView.setBackground(ColorConstants.gray);
		templateView.setSize(tempView.getSize().x - 20, 220);
		tempView.setContent(templateView);

		Button standard = null;
		String sTemp = "";
		for (int i = 0; i < OspResourceManager.SCENETEMPLATE_COUNT; i++) {
			standard = new Button(templateView, SWT.TOGGLE | SWT.CENTER);
			sTemp = Integer.toString(i);
			standard.setData(sTemp);
			standard.setImage(img_scenetelplate_unselected[i]);
			standard.setSize(img_scenetelplate_unselected[i].getBounds().width,
					img_scenetelplate_unselected[i].getBounds().height);
			standard.setLocation((standard.getSize().x + 10) * (i % 4) + 10,
					(standard.getSize().y + 10) * (int) (i / 4) + 10);
			standard.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					Button button = (Button) e.getSource();
					if (button.getSelection())
						setSelectedTemplate((String) button.getData());
				}
			});

			scene_template.put(sTemp, standard);
		}

		templateView.setSize(templateView.getSize().x, standard.getLocation().y
				+ standard.getSize().y + 5);
		tempView.setMinSize(templateView.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		templateView.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
			}
			@Override
			public void mouseUp(MouseEvent e) {
				templateView.setFocus();
			}});
	}

	protected void setSelectedTemplate(String text) {
		Enumeration<String> keys = scene_template.keys();
		String key;
		while (keys.hasMoreElements()) {
			key = keys.nextElement();
			int index = Integer.parseInt(key);
			Button button = scene_template.get(key);
			if (button != null && key.equals(text)) {
				button.setImage(img_scenetelplate_selected[index]);
				selectedTemplate = index;
				if(textArea != null)
					textArea.setText(OspResourceManager.sceneDescription[index]);
			} else if (button != null) {
				button.setImage(img_scenetelplate_unselected[index]);
				button.setSelection(false);
			}
		}
	}

	public int getSelectedTemplate() {
		return selectedTemplate;
	}

	// ---- WizardPage ----------------

	/*
	 * @see WizardPage#becomesVisible
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		fPageVisible = visible;
		// policy: wizards are not allowed to come up with an error message
		if (visible && fCurrStatus.matches(IStatus.ERROR)) {
			StatusInfo status = new StatusInfo();
			status.setError(""); //$NON-NLS-1$
			fCurrStatus = status;
		}
		updateStatus(fCurrStatus);
	}

	/**
	 * Updates the status line and the ok button according to the given status
	 * 
	 * @param status
	 *            status to apply
	 */
	protected void updateStatus(IStatus status) {
		fCurrStatus = status;
		if(status != null)
			setPageComplete(!status.matches(IStatus.ERROR));
		if (fPageVisible) {
			applyToStatusLine(this, status);
		}
	}

	/**
	 * Updates the status line and the ok button according to the status
	 * evaluate from an array of status. The most severe error is taken. In case
	 * that two status with the same severity exists, the status with lower
	 * index is taken.
	 * 
	 * @param status
	 *            the array of status
	 */
	protected void updateStatus(IStatus[] status) {
		updateStatus(getMostSevere(status));
	}

	public static IStatus getMostSevere(IStatus[] status) {
		IStatus max = null;
		for (int i = 0; i < status.length; i++) {
			IStatus curr = status[i];
			if (curr.matches(IStatus.ERROR)) {
				return curr;
			}
			if (max == null || curr.getSeverity() > max.getSeverity()) {
				max = curr;
			}
		}
		return max;
	}

	public static void applyToStatusLine(DialogPage page, IStatus status) {
		String message = status.getMessage();
		switch (status.getSeverity()) {
		case IStatus.OK:
			page.setMessage(message, IMessageProvider.NONE);
			page.setErrorMessage(null);
			break;
		case IStatus.WARNING:
			page.setMessage(message, IMessageProvider.WARNING);
			page.setErrorMessage(null);
			break;
		case IStatus.INFO:
			page.setMessage(message, IMessageProvider.INFORMATION);
			page.setErrorMessage(null);
			break;
		default:
			if (message.length() == 0) {
				message = null;
			}
			page.setMessage(null);
			page.setErrorMessage(message);
			break;
		}
	}
}
