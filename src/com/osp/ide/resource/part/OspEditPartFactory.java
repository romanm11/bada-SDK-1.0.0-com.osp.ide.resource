package com.osp.ide.resource.part;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.DrawCanvas;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.Flash;
import com.osp.ide.resource.model.Form;
import com.osp.ide.resource.model.FrameNode;
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;

public class OspEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		AbstractGraphicalEditPart part = null;	
		
		if (model instanceof DrawCanvas) {
			part = new DrawCanvasPart();
		} else if (model instanceof Form) {
			part = new FormFramePart();
		} else if (model instanceof PanelFrame) {
			part = new PanelFramePart();
		} else if (model instanceof Popup) {
			part = new PopupFramePart();
		} else if (model instanceof Label) {
			part = new LabelPart();
		}else if (model instanceof Check) {
			part = new CheckPart();
		}else if (model instanceof Slider) {
			part = new SliderPart();
		}else if (model instanceof EditField) {
			part = new EditFieldPart();
		}else if (model instanceof EditArea) {
			part = new EditAreaPart();
		}else if (model instanceof Progress) {
			part = new ProgressPart();
		}else if (model instanceof Button) {
			part = new ButtonPart();
		}else if (model instanceof ListControl) {
			part = new ListPart();
		}else if (model instanceof IconList) {
			part = new IconListPart();
		}else if (model instanceof CustomList) {
			part = new CustomListPart();
		}else if (model instanceof ScrollPanel) {
			part = new ScrollPanelPart();
		}else if (model instanceof Flash) {
			part = new FlashControlPart();
		}else if (model instanceof ColorPicker) {
			part = new ColorPickerPart();
		}else if (model instanceof EditDate) {
			part = new DatePickerPart();
		}else if (model instanceof ExpandableList) {
			part = new ExpandableListPart();
		}else if (model instanceof GroupedList) {
			part = new GroupedListPart();
		}else if (model instanceof OverlayPanel) {
			part = new OverlayPanelPart();
		}else if (model instanceof Panel) {
			part = new PanelPart();
		}else if (model instanceof SlidableGroupedList) {
			part = new SlidableGroupedListPart();
		}else if (model instanceof SlidableList) {
			part = new SlidableListPart();
		}else if (model instanceof EditTime) {
			part = new TimePickerPart();
		}
		
		((FrameNode) model).setPart(part);
		if(part != null)
			part.setModel(model);
		return part;
	}
}
