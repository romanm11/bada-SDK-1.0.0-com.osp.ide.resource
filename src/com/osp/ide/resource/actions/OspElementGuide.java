package com.osp.ide.resource.actions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.osp.ide.resource.figure.FormFrameFigure;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.Popup;

public class OspElementGuide implements Serializable {
	/**
	 * Property used to notify listeners when the parts attached to a guide are
	 * changed
	 */
	public static final String PROPERTY_CHILDREN = "subparts changed";
	/**
	 * Property used to notify listeners when the guide is re-positioned
	 */
	public static final String PROPERTY_POSITION = "position changed";

	static final long serialVersionUID = 1;

	protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
	@SuppressWarnings("unchecked")
	private Map map;
	private int position;
	private boolean horizontal;
	private Shell tip = null;
	private Label label = null;
	private Rectangle frameRect;
	private FrameNode frame;

	public class ToolTipClose extends TimerTask {
		private Timer timer;
		private Display display;

		public ToolTipClose(Timer timer, Display display) {
			this.timer = timer;
			this.display = display;
		}

		@Override
		public void run() {
			display.asyncExec(new Runnable() {
				@Override
				public void run() {
					closeToolTip();
				}
			});
			timer.cancel();
		}
	}

	/**
	 * Empty default constructor
	 */
	public OspElementGuide() {
		// empty constructor
	}

	/**
	 * Constructor
	 * 
	 * @param isHorizontal
	 *            <code>true</code> if the guide is horizontal (i.e., placed on
	 *            a vertical ruler)
	 */
	public OspElementGuide(boolean isHorizontal) {
		setHorizontal(isHorizontal);
	}

	/**
	 * @see PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		listeners.addPropertyChangeListener(listener);
	}

	/*
	 * @TODO:Pratik use PositionConstants here
	 */
	/**
	 * Attaches the given part along the given edge to this guide. The
	 * LogicSubpart is also updated to reflect this attachment.
	 * 
	 * @param part
	 *            The part that is to be attached to this guide; if the part is
	 *            already attached, its alignment is updated
	 * @param alignment
	 *            -1 is left or top; 0, center; 1, right or bottom
	 */
	@SuppressWarnings("unchecked")
	public void attachElement(FrameNode element, int alignment) {
		if (getMap().containsKey(element) && getAlignment(element) == alignment)
			return;

		getMap().put(element, alignment);
		OspElementGuide parent = isHorizontal() ? element.getHorizontalGuide()
				: element.getVerticalGuide();
		if (parent != null && parent != this) {
			parent.detachElement(element);
		}
		if (isHorizontal()) {
			element.setHorizontalGuide(this);
		} else {
			element.setVerticalGuide(this);
		}
		listeners.firePropertyChange(PROPERTY_CHILDREN, null, element);
	}

	/**
	 * Detaches the given part from this guide. The LogicSubpart is also updated
	 * to reflect this change.
	 * 
	 * @param part
	 *            the part that is to be detached from this guide
	 */
	public void detachElement(FrameNode element) {
		if (getMap().containsKey(element)) {
			getMap().remove(element);
			if (isHorizontal()) {
				element.setHorizontalGuide(null);
			} else {
				element.setVerticalGuide(null);
			}
			listeners.firePropertyChange(PROPERTY_CHILDREN, null, element);
		}
	}

	/**
	 * This methods returns the edge along which the given part is attached to
	 * this guide. This information is used by
	 * {@link org.eclipse.gef.examples.logicdesigner.edit.LogicXYLayoutEditPolicy
	 * LogicXYLayoutEditPolicy} to determine whether to attach or detach a part
	 * from a guide during resize operations.
	 * 
	 * @param part
	 *            The part whose alignment has to be found
	 * @return an int representing the edge along which the given part is
	 *         attached to this guide; 1 is bottom or right; 0, center; -1, top
	 *         or left; -2 if the part is not attached to this guide
	 * @see org.eclipse.gef.examples.logicdesigner.edit.LogicXYLayoutEditPolicy#createChangeConstraintCommand(ChangeBoundsRequest,
	 *      EditPart, Object)
	 */
	public int getAlignment(FrameNode element) {
		Integer value = ((Integer)getMap().get(element));
		if (value != null)
			return value.intValue();
		return -2;
	}

	/**
	 * @return The Map containing all the parts attached to this guide, and
	 *         their alignments; the keys are LogicSubparts and values are
	 *         Integers
	 */
	@SuppressWarnings("unchecked")
	public Map getMap() {
		if (map == null) {
			map = new Hashtable();
		}
		return map;
	}

	/**
	 * @return the set of all the parts attached to this guide; a set is used
	 *         because a part can only be attached to a guide along one edge.
	 */
	@SuppressWarnings("unchecked")
	public Set getParts() {
		return getMap().keySet();
	}

	/**
	 * @return the position/location of the guide (in pixels)
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @return <code>true</code> if the guide is horizontal (i.e., placed on a
	 *         vertical ruler)
	 */
	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * @see PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		listeners.removePropertyChangeListener(listener);
	}

	/**
	 * Sets the orientation of the guide
	 * 
	 * @param isHorizontal
	 *            <code>true</code> if this guide is to be placed on a vertical
	 *            ruler
	 */
	public void setHorizontal(boolean isHorizontal) {
		horizontal = isHorizontal;
	}

	/**
	 * Sets the location of the guide
	 * 
	 * @param offset
	 *            The location of the guide (in pixels)
	 */
	public void setPosition(int offset) {
		if (position != offset) {
			int oldValue = position;
			position = offset;
			listeners.firePropertyChange(PROPERTY_POSITION, Integer.valueOf(
					oldValue), Integer.valueOf(position));
		}
	}

	public void showToolTip(int pos) {
		Display display = Display.getDefault();
		if (tip == null || tip.isDisposed()) {
			tip = new Shell(SWT.ON_TOP | SWT.TOOL);
		}
		tip.setLayout(new FillLayout());
		if (label == null || label.isDisposed()) {
			label = new Label(tip, SWT.NONE);
		}
		label.setForeground(display.getSystemColor(SWT.COLOR_INFO_FOREGROUND));
		label.setBackground(display.getSystemColor(SWT.COLOR_INFO_BACKGROUND));
		if (horizontal) {
			int formPos, minY = 0;
			
			if(frame instanceof Form)
				minY = ((Form) frame).getMinY();
	        else if(frame instanceof Popup)
	            minY = ((Popup) frame).getMinY();
			
			formPos = pos - frameRect.y - FormFrameFigure.BORDER
					- minY;
			if(formPos < 0 || formPos > frameRect.height - FormFrameFigure.BORDER
					- minY)
				label.setText(Integer.toString(pos));
			else
				label.setText(pos + "(" + Integer.toString(pos - frameRect.y
					- FormFrameFigure.BORDER - minY) + ")");
		} else {
			int formPos = pos - frameRect.x - FormFrameFigure.BORDER;
			if(formPos < 0 || formPos > frameRect.width)
				label.setText(Integer.toString(pos));
			else
				label.setText(pos + "(" + Integer.toString(pos - frameRect.x
					- FormFrameFigure.BORDER) + ")");
		}
		Point size = tip.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		Point pt = display.getCursorLocation();
		tip.setBounds(pt.x, pt.y - size.y - 5, size.x, size.y);
		tip.setVisible(true);
		Timer close = new Timer();
		Date curDate = new Date();
		curDate.setTime(curDate.getTime() + 2000);
		close.schedule(new ToolTipClose(close, display), curDate);
	}

	public void closeToolTip() {
		if (tip != null && !tip.isDisposed()) {
			tip.dispose();
		}
		if (label != null && !label.isDisposed()) {
			label.dispose();
			label = null;
		}
	}

	public void setFrameRect(Rectangle rect) {
		frameRect = rect;
	}

	public void setFrame(FrameNode frame) {
		this.frame = frame;
	}

    /**
     * @return
     */
    public Rectangle getFrameRect() {
        return frameRect;
    }
}
