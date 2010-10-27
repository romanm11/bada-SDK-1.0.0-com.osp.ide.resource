package com.osp.ide.resource.actions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.rulers.RulerChangeListener;
import org.eclipse.gef.rulers.RulerProvider;

import com.osp.ide.resource.commands.CreateGuideCommand;
import com.osp.ide.resource.commands.DeleteGuideCommand;
import com.osp.ide.resource.commands.MoveGuideCommand;
import com.osp.ide.resource.model.FrameNode;

public class OspElementRulerProvider extends RulerProvider {
	private OspElementRuler ruler;
	private PropertyChangeListener rulerListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(OspElementRuler.PROPERTY_CHILDREN)) {
				OspElementGuide guide = (OspElementGuide) evt.getNewValue();
				if (getGuides().contains(guide)) {
					guide.addPropertyChangeListener(guideListener);
				} else {
					guide.removePropertyChangeListener(guideListener);
				}
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyGuideReparented(guide);
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyUnitsChanged(ruler.getUnit());
				}
			}
		}
	};
	private PropertyChangeListener guideListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(OspElementGuide.PROPERTY_CHILDREN)) {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyPartAttachmentChanged(evt.getNewValue(), evt
									.getSource());
				}
			} else {
				for (int i = 0; i < listeners.size(); i++) {
					((RulerChangeListener) listeners.get(i))
							.notifyGuideMoved(evt.getSource());
				}
			}
		}
	};
	private Rectangle frameRect;
	private FrameNode frame;
	private CreateGuideCommand oldCreateCmd;

	public void setFrameRect(Rectangle frameRect) {
		this.frameRect = frameRect;
	}

	public Rectangle getFrameRect() {
		return frameRect;
	}

	public void setFrame(FrameNode frame) {
		this.frame = frame;
	}

	public FrameNode getFrame() {
		return frame;
	}

	@SuppressWarnings("unchecked")
	public OspElementRulerProvider(OspElementRuler ruler) {
		this.ruler = ruler;
		this.ruler.addPropertyChangeListener(rulerListener);
		List guides = getGuides();
		for (int i = 0; i < guides.size(); i++) {
			((OspElementGuide) guides.get(i))
					.addPropertyChangeListener(guideListener);
		}
	}

	@SuppressWarnings("unchecked")
	public List getAttachedModelObjects(Object guide) {
		return new ArrayList(((OspElementGuide) guide).getParts());
	}

	public Command getCreateGuideCommand(int position) {
		if(oldCreateCmd != null)
			oldCreateCmd.closeToolTip();
		oldCreateCmd = new CreateGuideCommand(frame, frameRect, ruler, position);
		return oldCreateCmd;
	}

	public Command getDeleteGuideCommand(Object guide) {
		return new DeleteGuideCommand((OspElementGuide) guide, ruler);
	}

	public Command getMoveGuideCommand(Object guide, int pDelta) {
	    if(!(guide instanceof OspElementGuide))
	        return null;
		int pos = ((OspElementGuide) guide).getPosition() + pDelta;
	    OspElementGuide castGuide = (OspElementGuide) guide;
        if(castGuide.isHorizontal()) {
            if(pos < 0 ||
                pos > castGuide.getFrameRect().y + 
                castGuide.getFrameRect().height + 50 )
                return null;
        } else {
            if(pos < 0 ||
                pos > castGuide.getFrameRect().x + 
                castGuide.getFrameRect().width + 50 )
                return null;

        }
		((OspElementGuide) guide).showToolTip(pos);
		return new MoveGuideCommand((OspElementGuide) guide, pDelta);
	}

	@SuppressWarnings("unchecked")
	public int[] getGuidePositions() {
		List guides = getGuides();
		int[] result = new int[guides.size()];
		for (int i = 0; i < guides.size(); i++) {
			result[i] = ((OspElementGuide) guides.get(i)).getPosition();
		}
		return result;
	}

	public Object getRuler() {
		return ruler;
	}

	public int getUnit() {
		return ruler.getUnit();
	}

	public void setUnit(int newUnit) {
		ruler.setUnit(newUnit);
	}

	public int getGuidePosition(Object guide) {
		return ((OspElementGuide) guide).getPosition();
	}
	
	public void removeAllGuides() {
		ruler.removeAllGuide();
	}

	@SuppressWarnings("unchecked")
	public List getGuides() {
		return ruler.getGuides();
	}
}
