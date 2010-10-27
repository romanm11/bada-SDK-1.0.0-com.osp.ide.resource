package com.osp.ide.resource.part.tree;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

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
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;

public class OspTreeEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart part = null;

		if (model instanceof DrawCanvas)
			part = new DrawCanvasTreeEditPart();
		else if (model instanceof Form)
			part = new FormFrameTreeEditPart();
		else if (model instanceof PanelFrame)
			part = new PanelFrameTreeEditPart();
		else if (model instanceof Popup)
			part = new PopupFrameTreeEditPart();
		else if (model instanceof Label)
			part = new LabelTreeEditPart();
		else if (model instanceof Check)
			part = new CheckTreeEditPart();
		else if (model instanceof Slider)
			part = new SliderTreeEditPart();
		else if (model instanceof EditField)
			part = new EditFieldTreeEditPart();
		else if (model instanceof EditArea)
			part = new EditAreaTreeEditPart();
		else if (model instanceof Progress)
			part = new ProgressTreeEditPart();
		else if (model instanceof Button)
			part = new ButtonTreeEditPart();
		else if (model instanceof ListControl)
			part = new ListTreeEditPart();
		else if (model instanceof IconList)
			part = new IconListTreeEditPart();
		else if (model instanceof CustomList)
			part = new CustomListTreeEditPart();
		else if (model instanceof ScrollPanel)
			part = new ScrollPanelTreeEditPart();
		else if (model instanceof Flash)
			part = new FlashControlTreeEditPart();
		else if (model instanceof ColorPicker)
			part = new ColorPickerTreeEditPart();
		else if (model instanceof EditDate)
			part = new DatePickerTreeEditPart();
		else if (model instanceof ExpandableList)
			part = new ExpandableListTreeEditPart();
		else if (model instanceof GroupedList)
			part = new GroupedListTreeEditPart();
		else if (model instanceof OverlayPanel)
			part = new OverlayPanelTreeEditPart();
		else if (model instanceof Panel)
			part = new PanelTreeEditPart();
		else if (model instanceof SlidableGroupedList)
			part = new SlidableGroupedListTreeEditPart();
		else if (model instanceof SlidableList)
			part = new SlidableListTreeEditPart();
		else if (model instanceof EditTime)
			part = new TimePickerTreeEditPart();

		if (part != null)
			part.setModel(model);

		return part;
	}

}
