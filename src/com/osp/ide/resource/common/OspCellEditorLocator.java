package com.osp.ide.resource.common;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Text;

import com.osp.ide.resource.figure.AbstactFigure;
import com.osp.ide.resource.figure.ButtonFigure;
import com.osp.ide.resource.figure.CheckFigure;
import com.osp.ide.resource.figure.EditAreaFigure;
import com.osp.ide.resource.figure.EditFieldFigure;
import com.osp.ide.resource.figure.LabelFigure;
import com.osp.ide.resource.figure.ProgressFigure;
import com.osp.ide.resource.resinfo.FrameConst;

/**
 * CellEditorLocator for Activities.
 * 
 * @author Daniel Lee
 */
public class OspCellEditorLocator implements CellEditorLocator {
	private Label m_label;
	private AbstactFigure m_figure;

	/**
	 * Creates a new ActivityCellEditorLocator for the given Label
	 * 
	 * @param label
	 *            the Label
	 */
	public OspCellEditorLocator(IFigure figure) {
		if (figure instanceof ButtonFigure) {
			m_figure = (ButtonFigure) figure;
			setLabel(((ButtonFigure) m_figure).getLabel());
		} else if (figure instanceof CheckFigure) {
			m_figure = (CheckFigure) figure;
			setLabel(((CheckFigure) m_figure).getLabel());
		} else if (figure instanceof EditFieldFigure) {
			m_figure = (EditFieldFigure) figure;
			setLabel(((EditFieldFigure) m_figure).getLabel());
		} else if (figure instanceof EditAreaFigure) {
			m_figure = (EditAreaFigure) figure;
			setLabel(((EditAreaFigure) m_figure).getLabel());
		} else if (figure instanceof LabelFigure) {
			m_figure = (LabelFigure) figure;
			setLabel(((LabelFigure) m_figure).getLabel());
		} else if (figure instanceof ProgressFigure) {
			m_figure = (ProgressFigure) figure;
			setLabel(m_figure.getLabel());
		}
	}

	/**
	 * @see CellEditorLocator#relocate(org.eclipse.jface.viewers.CellEditor)
	 */
	public void relocate(CellEditor celleditor) {
		if (m_figure instanceof ButtonFigure) {
			ButtonFigure figure = (ButtonFigure) m_figure;
			myRelocate(celleditor, figure.getHAlign(), figure.getVAlign());
			return;
		} else if (m_figure instanceof CheckFigure) {
			CheckFigure figure = (CheckFigure) m_figure;
			myRelocate(celleditor, figure.getHAlign(), figure.getVAlign());
			return;
		} else if (m_figure instanceof LabelFigure) {
			LabelFigure figure = (LabelFigure) m_figure;
			myRelocate(celleditor, figure.getHAlign(), figure.getVAlign());
			return;
			// }
		} else if (m_figure instanceof EditFieldFigure) {
			myRelocate(celleditor, FrameConst.ALIGN_LEFT, FrameConst.ALIGN_MIDDLE);
			return;
		}

		Text text = (Text) celleditor.getControl();
		Point pref = text.computeSize(-1, -1);
		Rectangle rect = m_label.getTextBounds().getCopy();
		if (pref.x > m_figure.getBounds().width)
			pref.x = m_figure.getBounds().width;
		m_label.translateToAbsolute(rect);
		text.setBounds(rect.x - 1, rect.y - 1, pref.x + 1, pref.y + 1);
	}

	public void myRelocate(CellEditor celleditor, int style) {
		Text text = (Text) celleditor.getControl();
		Rectangle figureRect = m_figure.getBounds();

		Tag_info info = m_figure.getTagInfo();

		int lMargin = 0, rMargin = 0;
		if (info != null) {
			lMargin = info.textlMargin;
			rMargin = info.textrMargin;
		}

		Point pref = text.computeSize(-1, -1);
		Rectangle rect = new Rectangle();
		rect.x = figureRect.x + lMargin;
		rect.y = figureRect.y + m_figure.getTitleSize();
		
		if (pref.x > figureRect.width + lMargin - rMargin)
			pref.x = figureRect.width + lMargin - rMargin;

		m_label.translateToAbsolute(rect);

		text.setBounds(rect.x, rect.y - 1, pref.x, pref.y + 1);
	}

	public void myRelocate(CellEditor celleditor, int hAlign, int vAlign) {
		Text text = (Text) celleditor.getControl();
		Rectangle figureRect = m_figure.getBounds();
		Rectangle textRect = m_label.getTextBounds().getCopy();

		if(vAlign == FrameConst.ALIGN_TOP)
			textRect.y += m_figure.getTitleSize();
		else if(vAlign == FrameConst.ALIGN_MIDDLE)
			textRect.y += m_figure.getTitleSize()/2;
		
		Tag_info info = m_figure.getTagInfo();

		int lMargin = 0, rMargin = 0;
		if (info != null) {
			lMargin = info.textlMargin;
			rMargin = info.textrMargin;
		}

		Point pref = text.computeSize(-1, -1);
		Rectangle rect = new Rectangle();
		if (hAlign == FrameConst.ALIGN_LEFT)
			rect.x = figureRect.x + lMargin;
		else if (hAlign == FrameConst.ALIGN_RIGHT) {
			rect.x = figureRect.x + lMargin + figureRect.width - rMargin - textRect.width;
			if (rect.x < figureRect.x + lMargin)
				rect.x = figureRect.x + lMargin;
			if (text.getText().length() == 0) {
				rect.x -= 12;
				pref.x = 12;
			}
		} else {
			rect.x = (figureRect.x + lMargin + (figureRect.width - rMargin) / 2)
					- textRect.width / 2;
			if (rect.x < figureRect.x + lMargin)
				rect.x = figureRect.x + lMargin;
		}
		rect.y = textRect.y;
		if (pref.x > figureRect.width + lMargin - rMargin)
			pref.x = figureRect.width + lMargin - rMargin;

		m_label.translateToAbsolute(rect);

		text.setBounds(rect.x, rect.y - 1, pref.x, pref.y + 1);
	}

	/**
	 * Returns the Label figure.
	 * 
	 * @return the Label
	 */
	protected Label getLabel() {
		return m_label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            The label to set
	 */
	protected void setLabel(Label label) {
		m_label = label;
	}

}
