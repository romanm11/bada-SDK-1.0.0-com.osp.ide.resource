package com.osp.ide.resource.resourceexplorer;

import java.util.Enumeration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EffectAdvisor {
	private static final int TOP_MARGIN = 10;
	public static final int GROUP_WIDTH = 328;
	public static final int GROUP_HEIGTH = 90;
	public static final int EFFECT_TYPE_FAID = 0;
	public static final int EFFECT_TYPE_SLIDE = 1;
	public static final int LABEL_WIDTH = 60;
	public static final int COMBO_WIDTH = 120;
	int positionY;
	private Enumeration<String> sceneList;

	private Combo inCombo;
	private Combo sceneCombo;
	private Combo outCombo;
	private Label sceneLabel;
	private Label scenetimeLabel;
	private Text scenetime;
	private Label inEffectLabel;
	private Label outEffectLabel;
	private Group group;
	private int index;
	private int type;
	private Label slideEffectLabel;
	private Combo slideCombo;

	public EffectAdvisor(final Composite composite,
			Enumeration<String> sceneList, int index, int type) {
		positionY = TOP_MARGIN + GROUP_HEIGTH * index;
		this.sceneList = sceneList;
		this.index = index;
		this.type = type;

		group = new Group(composite, SWT.NONE);
		group.setText("Scene " + (index + 1));
		group.setBounds(new Rectangle(4, positionY, GROUP_WIDTH, GROUP_HEIGTH));
		group.addMouseListener(new MouseListener(){
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
			@Override
			public void mouseDown(MouseEvent e) {
			}
			@Override
			public void mouseUp(MouseEvent e) {
				scenetime.setFocus();
			}});
		createControl();
	}

	private void createControl() {

		sceneLabel = new Label(group, SWT.RIGHT);
		sceneLabel.setText("Scene : ");
		if (type == EFFECT_TYPE_FAID)
			sceneLabel.setBounds(new Rectangle(4, 18, LABEL_WIDTH, 14));
		else
			sceneLabel.setBounds(new Rectangle(4, 24, LABEL_WIDTH, 14));

		sceneCombo = new Combo(group, SWT.READ_ONLY);
		sceneCombo.setBounds(new Rectangle(sceneLabel.getLocation().x
				+ sceneLabel.getSize().x + 2, sceneLabel.getLocation().y - 4,
				COMBO_WIDTH, 10));
		int sceneCount = 0;
		while (sceneList.hasMoreElements()) {
			sceneCombo.add(sceneList.nextElement(), 0);
			sceneCount++;
		}
		sceneCombo.select(index % sceneCount);
		sceneCombo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				scenetime.setFocus();
			}});

		scenetimeLabel = new Label(group, SWT.NONE);
		scenetimeLabel.setText("View Time : ");
		scenetimeLabel.setBounds(new Rectangle(sceneCombo.getLocation().x
				+ sceneCombo.getSize().x + 10, sceneLabel.getLocation().y, 76,
				14));

		scenetime = new Text(group, SWT.BORDER);
		scenetime.setBounds(new Rectangle(scenetimeLabel.getLocation().x
				+ scenetimeLabel.getSize().x, sceneLabel.getLocation().y - 3,
				50, sceneCombo.getSize().y - 2));
		scenetime.setText("2");
		scenetime.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(VerifyEvent e) {
				e.doit = e.text.matches("[0-9]*");
			}
		});

		if (type == EFFECT_TYPE_FAID) {
			createInEffectControl();
			createOutEffectControl();
		} else {
			createSlideEffectControl();
		}
	}

	public void setFocus() {
		scenetime.setFocus();
	}
	
	private void createSlideEffectControl() {
		slideEffectLabel = new Label(group, SWT.RIGHT);
		slideEffectLabel.setText("Effect : ");
		slideEffectLabel.setBounds(new Rectangle(4,
				sceneLabel.getLocation().y + 30, LABEL_WIDTH, 14));

		slideCombo = new Combo(group, SWT.READ_ONLY);
		slideCombo.setBounds(new Rectangle(slideEffectLabel.getLocation().x
				+ slideEffectLabel.getSize().x + 2, slideEffectLabel
				.getLocation().y - 4, COMBO_WIDTH, 10));
		for (int i = PreviewCanvas.EFFECT_SLIDE_NULL; i <= PreviewCanvas.EFFECT_SLIDE_BOTTOM; i++)
			slideCombo.add(PreviewCanvas.strEffect[i]);
		slideCombo.select(0);
		slideCombo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				scenetime.setFocus();
			}});
	}

	private void createInEffectControl() {
		inEffectLabel = new Label(group, SWT.RIGHT);
		inEffectLabel.setText("In Effect : ");
		inEffectLabel.setBounds(new Rectangle(4,
				sceneLabel.getLocation().y + 24, LABEL_WIDTH, 14));

		inCombo = new Combo(group, SWT.READ_ONLY);
		inCombo.setBounds(new Rectangle(inEffectLabel.getLocation().x
				+ inEffectLabel.getSize().x + 2,
				inEffectLabel.getLocation().y - 4, COMBO_WIDTH, 10));
		for (int i = PreviewCanvas.EFFECT_FAIDIN_NULL; i <= PreviewCanvas.EFFECT_FAIDIN_BOTTOM; i++)
			inCombo.add(PreviewCanvas.strEffect[i]);
		inCombo.select(0);
		inCombo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				scenetime.setFocus();
			}});
	}

	private void createOutEffectControl() {
		outEffectLabel = new Label(group, SWT.RIGHT);
		outEffectLabel.setText("Out Effect : ");
		outEffectLabel.setBounds(new Rectangle(4,
				sceneLabel.getLocation().y + 48, LABEL_WIDTH, 14));

		outCombo = new Combo(group, SWT.READ_ONLY);
		outCombo.setBounds(new Rectangle(outEffectLabel.getLocation().x
				+ outEffectLabel.getSize().x + 2,
				outEffectLabel.getLocation().y - 4, COMBO_WIDTH, 10));
		for (int i = PreviewCanvas.EFFECT_FAIDOUT_NULL; i <= PreviewCanvas.EFFECT_FAIDOUT_BOTTOM; i++)
			outCombo.add(PreviewCanvas.strEffect[i]);
		outCombo.select(0);
		outCombo.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				scenetime.setFocus();
			}});
	}

	public void deleteControl() {
		sceneLabel.dispose();
		scenetimeLabel.dispose();
		scenetime.dispose();
		sceneCombo.dispose();
		if (type == EFFECT_TYPE_FAID) {
			inEffectLabel.dispose();
			inCombo.dispose();
			outEffectLabel.dispose();
			outCombo.dispose();
		} else {
			slideEffectLabel.dispose();
			slideCombo.dispose();
		}
		group.dispose();
	}

	public int getIndex() {
		return index;
	}

	public String getSceneName() {
		return sceneCombo.getText();
	}

	public String getInEffect() {
		if (inCombo == null)
			return "";
		return inCombo.getText();
	}

	public String getOutEffect() {
		if (outCombo == null)
			return "";
		return outCombo.getText();
	}

	public String getSlideEffect() {
		if (slideCombo == null)
			return "";
		return slideCombo.getText();
	}

	public int getViewTime() {
		if (scenetime.getText().isEmpty())
			return 0;
		return Integer.parseInt(scenetime.getText());
	}
}
