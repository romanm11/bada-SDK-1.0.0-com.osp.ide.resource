package com.osp.ide.resource.common;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import com.osp.ide.resource.figure.AbstactFigure;
import com.osp.ide.resource.part.OspAbstractEditPart;

/**
 * DirectEditManager for Activities
 * 
 * @author Daniel Lee
 */
public class OspDirectEditManager extends DirectEditManager {

	Font scaledFont;
	protected VerifyListener m_verifyListener;
	private Label m_Label;
	private AbstactFigure figure;

	/**
	 * Creates a new ActivityDirectEditManager with the given attributes.
	 * 
	 * @param source
	 *            the source EditPart
	 * @param editorType
	 *            type of editor
	 * @param locator
	 *            the CellEditorLocator
	 */
	@SuppressWarnings("unchecked")
	public OspDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator) {
		super(source, editorType, locator);
		if(source instanceof OspAbstractEditPart) {
			figure = ((OspAbstractEditPart) source).figure;
		}
		
		m_Label = figure.getLabel();
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#bringDown()
	 */
	protected void bringDown() {
		// This method might be re-entered when super.bringDown() is called.
		Font disposeFont = scaledFont;
		scaledFont = null;
		super.bringDown();
		if (disposeFont != null)
			disposeFont.dispose();
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#initCellEditor()
	 */
	protected void initCellEditor() {
		Text text = (Text) getCellEditor().getControl();
		m_verifyListener = new VerifyListener() {
			public void verifyText(VerifyEvent event) {
//				event.doit = event.text.matches("[[!-~]]*");
				if(event.character == SWT.CR)
					return;
				Text text = (Text) getCellEditor().getControl();
				String oldText = text.getText();
				String leftText = oldText.substring(0, event.start);
				String rightText = oldText.substring(event.end, oldText
						.length());
				GC gc = new GC(text);
				Point size = gc.textExtent(leftText + event.text + rightText);
				gc.dispose();
				if (size.x != 0)
					size = text.computeSize(size.x, SWT.DEFAULT);
				
				Tag_info info = figure.getTagInfo();
				int lMargin = 0, rMargin = 0;
				if(info != null) {
					lMargin = info.textlMargin;
					rMargin = info.textrMargin;
				}
				if(size.x > figure.getBounds().width - lMargin - rMargin)
					size.x = figure.getBounds().width - lMargin - rMargin;
				getCellEditor().getControl().setSize(size.x, size.y);
			}
		};

		String initialLabelText = m_Label.getText();
		getCellEditor().setValue(initialLabelText);

		FontData data = m_Label.getFont().getFontData()[0];
		Dimension fontSize = new Dimension(0, data.getHeight());
		m_Label.translateToAbsolute(fontSize);
		data.setHeight(fontSize.height);
	    scaledFont = new Font(null, data);
		text.setFont(scaledFont);
		text.addVerifyListener(m_verifyListener);
	}

	/**
	 * @see org.eclipse.gef.tools.DirectEditManager#unhookListeners()
	 */
	protected void unhookListeners() {
		super.unhookListeners();
		Text text = (Text) getCellEditor().getControl();
		text.removeVerifyListener(m_verifyListener);
		m_verifyListener = null;
	}

}
