package com.osp.ide.resource.part;

import org.eclipse.gef.requests.CreationFactory;

import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.EditDate;
import com.osp.ide.resource.model.EditArea;
import com.osp.ide.resource.model.EditField;
import com.osp.ide.resource.model.ExpandableList;
import com.osp.ide.resource.model.GroupedList;
import com.osp.ide.resource.model.IconList;
import com.osp.ide.resource.model.Label;
import com.osp.ide.resource.model.ListControl;
import com.osp.ide.resource.model.OverlayPanel;
import com.osp.ide.resource.model.Panel;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;

public class OspNodeCreationFactory implements CreationFactory {
	private Class<?> template;

	public OspNodeCreationFactory(Class<?> t) {
		this.template = t;
	}

	@Override
	public Object getNewObject() {
		if (template == null)
			return null;

		if (template == Button.class) {
			Button button = new Button();
			button.setName("Button");
			return button;
		} else if (template == Check.class) {
			Check check = new Check();
			check.setName("Check");
			return check;
		} else if (template == ColorPicker.class) {
			ColorPicker colorpicker = new ColorPicker();
			colorpicker.setName("ColorPicker");
			return colorpicker;
		} else if (template == EditDate.class) {
			EditDate datepicker = new EditDate();
			datepicker.setName("DatePicker");
			return datepicker;
		} else if (template == EditField.class) {
			EditField editfield = new EditField();
			editfield.setName("EditField");
			return editfield;
		} else if (template == EditArea.class) {
			EditArea editarea = new EditArea();
			editarea.setName("EditArea");
			return editarea;
		} else if (template == ExpandableList.class) {
			ExpandableList expandablelist = new ExpandableList();
			expandablelist.setName("ExpandableList");
			return expandablelist;
		} else if (template == GroupedList.class) {
			GroupedList groupedlist = new GroupedList();
			groupedlist.setName("GroupedList");
			return groupedlist;
		} else if (template == Label.class) {
			Label label = new Label();
			label.setName("Label");
			return label;
		} else if (template == Slider.class) {
			Slider slide = new Slider();
			slide.setName("slide");
			return slide;
		} else if (template == Progress.class) {
			Progress progress = new Progress();
			progress.setName("Progress");
			return progress;
		} else if (template == ListControl.class) {
			ListControl list = new ListControl();
			list.setName("List");
			return list;
		} else if (template == IconList.class) {
			IconList iconlist = new IconList();
			iconlist.setName("IconList");
			return iconlist;
		} else if (template == CustomList.class) {
			CustomList customlist = new CustomList();
			customlist.setName("CustomList");
			return customlist;
		} else if (template == OverlayPanel.class) {
			OverlayPanel overlaypanel = new OverlayPanel();
			overlaypanel.setName("OverlayPanel");
			return overlaypanel;
		} else if (template == Panel.class) {
			Panel panel = new Panel();
			panel.setName("Panel");
			return panel;
		} else if (template == SlidableGroupedList.class) {
			SlidableGroupedList customlist = new SlidableGroupedList();
			customlist.setName("SlidableGroupedList");
			return customlist;
		} else if (template == SlidableList.class) {
			SlidableList customlist = new SlidableList();
			customlist.setName("SlidableList");
			return customlist;
		} else if (template == EditTime.class) {
			EditTime timepcikr = new EditTime();
			timepcikr.setName("TimePicker");
			return timepcikr;
		}

		return null;
	}

	@Override
	public Object getObjectType() {
		return template;
	}
}
