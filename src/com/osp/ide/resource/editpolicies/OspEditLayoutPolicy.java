package com.osp.ide.resource.editpolicies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGuides;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.rulers.RulerProvider;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.dialogs.MessageDialog;

import com.osp.ide.resource.Activator;
import com.osp.ide.resource.actions.OspElementGuide;
import com.osp.ide.resource.commands.ButtonChangeLayoutCommand;
import com.osp.ide.resource.commands.ButtonCreateCommand;
import com.osp.ide.resource.commands.ChangeGuideCommand;
import com.osp.ide.resource.commands.CheckChangeLayoutCommand;
import com.osp.ide.resource.commands.CheckCreateCommand;
import com.osp.ide.resource.commands.ColorPickerChangeLayoutCommand;
import com.osp.ide.resource.commands.ColorPickerCreateCommand;
import com.osp.ide.resource.commands.CustomListChangeLayoutCommand;
import com.osp.ide.resource.commands.CustomListCreateCommand;
import com.osp.ide.resource.commands.DatePickerChangeLayoutCommand;
import com.osp.ide.resource.commands.DatePickerCreateCommand;
import com.osp.ide.resource.commands.EditAreaChangeLayoutCommand;
import com.osp.ide.resource.commands.EditAreaCreateCommand;
import com.osp.ide.resource.commands.EditFieldChangeLayoutCommand;
import com.osp.ide.resource.commands.EditFieldCreateCommand;
import com.osp.ide.resource.commands.ExpandableListChangeLayoutCommand;
import com.osp.ide.resource.commands.ExpandableListCreateCommand;
import com.osp.ide.resource.commands.FlashControlChangeLayoutCommand;
import com.osp.ide.resource.commands.FlashControlCreateCommand;
import com.osp.ide.resource.commands.GroupedListChangeLayoutCommand;
import com.osp.ide.resource.commands.GroupedListCreateCommand;
import com.osp.ide.resource.commands.IconListChangeLayoutCommand;
import com.osp.ide.resource.commands.IconListCreateCommand;
import com.osp.ide.resource.commands.LabelChangeLayoutCommand;
import com.osp.ide.resource.commands.LabelCreateCommand;
import com.osp.ide.resource.commands.ListChangeLayoutCommand;
import com.osp.ide.resource.commands.ListCreateCommand;
import com.osp.ide.resource.commands.OspAbstractLayoutCommand;
import com.osp.ide.resource.commands.OspAddCommand;
import com.osp.ide.resource.commands.OspDeleteCommand;
import com.osp.ide.resource.commands.OverlayPanelChangeLayoutCommand;
import com.osp.ide.resource.commands.OverlayPanelCreateCommand;
import com.osp.ide.resource.commands.PanelChangeLayoutCommand;
import com.osp.ide.resource.commands.PanelCreateCommand;
import com.osp.ide.resource.commands.ProgressChangeLayoutCommand;
import com.osp.ide.resource.commands.ProgressCreateCommand;
import com.osp.ide.resource.commands.ScrollPanelChangeLayoutCommand;
import com.osp.ide.resource.commands.ScrollPanelCreateCommand;
import com.osp.ide.resource.commands.SlidableGroupedListChangeLayoutCommand;
import com.osp.ide.resource.commands.SlidableGroupedListCreateCommand;
import com.osp.ide.resource.commands.SlidableListChangeLayoutCommand;
import com.osp.ide.resource.commands.SlidableListCreateCommand;
import com.osp.ide.resource.commands.SliderChangeLayoutCommand;
import com.osp.ide.resource.commands.SliderCreateCommand;
import com.osp.ide.resource.commands.TimePickerChangeLayoutCommand;
import com.osp.ide.resource.commands.TimePickerCreateCommand;
import com.osp.ide.resource.common.Tag_info;
import com.osp.ide.resource.model.Button;
import com.osp.ide.resource.model.Check;
import com.osp.ide.resource.model.ColorPicker;
import com.osp.ide.resource.model.CustomList;
import com.osp.ide.resource.model.EditDate;
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
import com.osp.ide.resource.model.PanelFrame;
import com.osp.ide.resource.model.Popup;
import com.osp.ide.resource.model.Progress;
import com.osp.ide.resource.model.ScrollPanel;
import com.osp.ide.resource.model.SlidableGroupedList;
import com.osp.ide.resource.model.SlidableList;
import com.osp.ide.resource.model.Slider;
import com.osp.ide.resource.model.EditTime;
import com.osp.ide.resource.part.ButtonPart;
import com.osp.ide.resource.part.CheckPart;
import com.osp.ide.resource.part.ColorPickerPart;
import com.osp.ide.resource.part.CustomListPart;
import com.osp.ide.resource.part.DatePickerPart;
import com.osp.ide.resource.part.EditAreaPart;
import com.osp.ide.resource.part.EditFieldPart;
import com.osp.ide.resource.part.ExpandableListPart;
import com.osp.ide.resource.part.FlashControlPart;
import com.osp.ide.resource.part.FormFramePart;
import com.osp.ide.resource.part.GroupedListPart;
import com.osp.ide.resource.part.IconListPart;
import com.osp.ide.resource.part.LabelPart;
import com.osp.ide.resource.part.ListPart;
import com.osp.ide.resource.part.OverlayPanelPart;
import com.osp.ide.resource.part.PanelFramePart;
import com.osp.ide.resource.part.PanelPart;
import com.osp.ide.resource.part.PopupFramePart;
import com.osp.ide.resource.part.ProgressPart;
import com.osp.ide.resource.part.ScrollPanelPart;
import com.osp.ide.resource.part.SlidableGroupedListPart;
import com.osp.ide.resource.part.SlidableListPart;
import com.osp.ide.resource.part.SliderPart;
import com.osp.ide.resource.part.TimePickerPart;
import com.osp.ide.resource.resinfo.FrameConst;
import com.osp.ide.resource.resinfo.Layout;
import com.osp.ide.resource.resourceexplorer.ResourceExplorer;

public class OspEditLayoutPolicy extends XYLayoutEditPolicy implements
		FrameConst {

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {

		OspAbstractLayoutCommand command = null;
		String message = "";

		if (child instanceof LabelPart) {
			command = new LabelChangeLayoutCommand();
		} else if (child instanceof CheckPart) {
			command = new CheckChangeLayoutCommand();
		} else if (child instanceof SliderPart) {
			command = new SliderChangeLayoutCommand();
		} else if (child instanceof EditFieldPart) {
			command = new EditFieldChangeLayoutCommand();
		} else if (child instanceof EditAreaPart) {
			command = new EditAreaChangeLayoutCommand();
		} else if (child instanceof ProgressPart) {
			command = new ProgressChangeLayoutCommand();
		} else if (child instanceof ButtonPart) {
			command = new ButtonChangeLayoutCommand();
		} else if (child instanceof ListPart) {
			command = new ListChangeLayoutCommand();
		} else if (child instanceof IconList) {
			command = new IconListChangeLayoutCommand();
		} else if (child instanceof CustomList) {
			command = new CustomListChangeLayoutCommand();
		} else if (child instanceof ScrollPanel) {
			command = new ScrollPanelChangeLayoutCommand();
		} else if (child instanceof Flash) {
			command = new FlashControlChangeLayoutCommand();
		} else if (child instanceof ColorPicker) {
			command = new ColorPickerChangeLayoutCommand();
		} else if (child instanceof EditDate) {
			command = new DatePickerChangeLayoutCommand();
		} else if (child instanceof ExpandableList) {
			command = new ExpandableListChangeLayoutCommand();
		} else if (child instanceof GroupedList) {
			command = new GroupedListChangeLayoutCommand();
		} else if (child instanceof OverlayPanel) {
			command = new OverlayPanelChangeLayoutCommand();
		} else if (child instanceof Panel) {
			command = new PanelChangeLayoutCommand();
		} else if (child instanceof SlidableGroupedList) {
			command = new SlidableGroupedListChangeLayoutCommand();
		} else if (child instanceof SlidableList) {
			command = new SlidableListChangeLayoutCommand();
		} else if (child instanceof EditTime) {
			command = new TimePickerChangeLayoutCommand();
		}
		ResourceExplorer view = ResourceExplorer.getResourceView();
		if (view != null) {
			IStatusLineManager statusManager = view.getViewSite()
					.getActionBars().getStatusLineManager();
			statusManager.setMessage(message);
		}

		if(command != null) {
			command.setModel(child.getModel());
			command.setConstraint((Rectangle) constraint);
		}
		return command;
	}

	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		OspAbstractLayoutCommand command = null;
		String message = "";
		Rectangle rect = (Rectangle) constraint;

		int repointTop = 0;
		if(getHost().getModel() instanceof Form)
			repointTop = ((Form) getHost().getModel()).getMinY();
		else if (getHost().getModel() instanceof Popup) {
            repointTop = ((Popup) getHost().getModel()).getMinY();
		}

		if (child instanceof LabelPart) {
			command = new LabelChangeLayoutCommand();
			Label model = (Label) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof CheckPart) {
			command = new CheckChangeLayoutCommand();
			Check model = (Check) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof EditFieldPart) {
			command = new EditFieldChangeLayoutCommand();
			EditField model = (EditField) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof EditAreaPart) {
			command = new EditAreaChangeLayoutCommand();
			EditArea model = (EditArea) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ProgressPart) {
			command = new ProgressChangeLayoutCommand();
			Progress model = (Progress) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ButtonPart) {
			command = new ButtonChangeLayoutCommand();
			Button model = (Button) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ListPart) {
			command = new ListChangeLayoutCommand();
			ListControl model = (ListControl) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof IconListPart) {
			command = new IconListChangeLayoutCommand();
			IconList model = (IconList) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof CustomListPart) {
			command = new CustomListChangeLayoutCommand();
			CustomList model = (CustomList) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ScrollPanelPart) {
			command = new ScrollPanelChangeLayoutCommand();
			ScrollPanel model = (ScrollPanel) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof FlashControlPart) {
			command = new FlashControlChangeLayoutCommand();
			Flash model = (Flash) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ColorPickerPart) {
			 command = new ColorPickerChangeLayoutCommand();
			 ColorPicker model = (ColorPicker) child.getModel();
			 message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof DatePickerPart) {
			command = new DatePickerChangeLayoutCommand();
			EditDate model = (EditDate) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof ExpandableListPart) {
			command = new ExpandableListChangeLayoutCommand();
			ExpandableList model = (ExpandableList) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof GroupedListPart) {
			command = new GroupedListChangeLayoutCommand();
			GroupedList model = (GroupedList) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof OverlayPanelPart && 
				isResizeEnable(child, rect)) {
			command = new OverlayPanelChangeLayoutCommand();
			OverlayPanel model = (OverlayPanel) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof PanelPart && 
				isResizeEnable(child, rect)) {
			command = new PanelChangeLayoutCommand();
			Panel model = (Panel) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof SlidableGroupedListPart) {
			command = new SlidableGroupedListChangeLayoutCommand();
			SlidableGroupedList model = (SlidableGroupedList) child
					.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof SlidableListPart) {
			command = new SlidableListChangeLayoutCommand();
			SlidableList model = (SlidableList) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof SliderPart) {
			command = new SliderChangeLayoutCommand();
			Slider model = (Slider) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		} else if (child instanceof TimePickerPart) {
			command = new TimePickerChangeLayoutCommand();
			EditTime model = (EditTime) child.getModel();
			message = FrameConst.cszTag[model.getType()] + "(" + model.getName();
		}

		if (message != null && !message.isEmpty())
			message += ") -   X:" + rect.x + "  Y:" + (rect.y - repointTop)
					+ "  W:" + rect.width + "  H:" + rect.height;
		else
			message = "";

		Activator.setStatusMessage(message);

		if(command == null)
			return null;
		
		command.setModel(child.getModel());
		command.setConstraint((Rectangle) constraint);

		Command result = command;

		FrameNode element = (FrameNode) child.getModel();
		if (element.getHorizontalGuide() != null) {
			int alignment = element.getHorizontalGuide().getAlignment(element);
			int edgeBeingResized = 0;
			if ((request.getResizeDirection() & PositionConstants.NORTH) != 0)
				edgeBeingResized = -1;
			else
				edgeBeingResized = 1;
			if (alignment == edgeBeingResized)
				result = result.chain(new ChangeGuideCommand(element, true));
		}

		if ((request.getResizeDirection() & PositionConstants.EAST_WEST) != 0) {
			Integer guidePos = (Integer) request.getExtendedData().get(
					SnapToGuides.KEY_VERTICAL_GUIDE);
			if (guidePos != null) {
				result = chainGuideAttachmentCommand(request, element, result,
						false);
			} else if (element.getVerticalGuide() != null) {
				int alignment = element.getVerticalGuide()
						.getAlignment(element);
				int edgeBeingResized = 0;
				if ((request.getResizeDirection() & PositionConstants.WEST) != 0)
					edgeBeingResized = -1;
				else
					edgeBeingResized = 1;
				if (alignment == edgeBeingResized)
					result = result.chain(new ChangeGuideCommand(element,
							false));
			}
		}

		if (request.getType().equals(REQ_MOVE_CHILDREN)
				|| request.getType().equals(REQ_ALIGN_CHILDREN)) {
			result = chainGuideAttachmentCommand(request, element, result, true);
			result = chainGuideAttachmentCommand(request, element, result,
					false);
			result = chainGuideDetachmentCommand(request, element, result, true);
			result = chainGuideDetachmentCommand(request, element, result,
					false);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
		CompoundCommand resize = new CompoundCommand();
		Command c;
		GraphicalEditPart child;
		List children = request.getEditParts();

		for (int i = 0; i < children.size(); i++) {
			child = (GraphicalEditPart) children.get(i);

			if (request.getType().equals(REQ_RESIZE_CHILDREN)
					&& (child instanceof ColorPickerPart ||
							child instanceof DatePickerPart || 
							child instanceof TimePickerPart))
				continue;

			c = createChangeConstraintCommand(
					request,
					child,
					translateToModelConstraint(getConstraintFor(request, child)));
			resize.add(c);
		}
		return resize.unwrap();
	}

	private boolean isResizeEnable(EditPart part, Rectangle rect) {
		FrameNode model = (FrameNode) part.getModel();
		List<FrameNode> children = model.getChildrenArray();
		for(int i=0; i<children.size(); i++) {
			FrameNode child = children.get(i);
			Layout childLayout = child.getLayout();
			if(rect.width < childLayout.x + childLayout.width ||
					rect.height < childLayout.y + childLayout.height)
				return false;
		}
		return true;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (request.getType() == REQ_CREATE && (getHost() instanceof FormFramePart ||
				getHost() instanceof PanelFramePart || 
				getHost() instanceof PopupFramePart || 
				getHost() instanceof PanelPart ||
				getHost() instanceof OverlayPanelPart)) {
			Object childClass = request.getNewObjectType();
			FrameNode frame = (FrameNode) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);

			if(frame instanceof Form) {
				int minY = ((Form) frame).getMinY();
				if (constraint.y < minY)
					return null;
			} else if (getHost().getModel() instanceof Popup) {
			    int minY = ((Popup) getHost().getModel()).getMinY();
                if (constraint.y < minY)
                    return null;
			}

			Tag_info info;

			if (childClass == Label.class) {
				Label label;

				LabelCreateCommand labelcmd = new LabelCreateCommand();

				labelcmd.setFrame(frame);
				label = new Label(frame.getDocuments(), frame.getModeIndex());// (Label)
				label.setType(WINDOW_LABEL);
				labelcmd.setLabel(label);
				label.setName(label.MakeID(WINDOW_LABEL, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[label.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = 25;
				labelcmd.setLayout(constraint);

				return labelcmd;
			}
			if (childClass == Check.class) {
				Check check;
				CheckCreateCommand checkcmd = new CheckCreateCommand();

				checkcmd.setFrame(frame);
				check = new Check(frame.getDocuments(), frame.getModeIndex());
				check.setType(WINDOW_CHECKBUTTON);
				checkcmd.setCheck(check);
				check.setName(check.MakeID(
						WINDOW_CHECKBUTTON, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[check.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = Check.CHECK_DEFAULT_HEIGHT;
				checkcmd.setLayout(constraint);
				return checkcmd;
			}
			if (childClass == Slider.class) {
				Slider slide;
				SliderCreateCommand slidecmd = new SliderCreateCommand();

				slidecmd.setFrame(frame);
				slide = new Slider(frame.getDocuments(), frame.getModeIndex());
				slide.setType(WINDOW_SLIDER);
				slidecmd.setSlide(slide);
				slide.setName(slide.MakeID(WINDOW_SLIDER,
						""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[slide.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = Slide.SLIDE_DEFAULT_HEIGHT;

				slidecmd.setLayout(constraint);
				return slidecmd;
			}
			if (childClass == EditField.class) {
				EditField editfield;
				EditFieldCreateCommand editfieldcmd = new EditFieldCreateCommand();

				editfieldcmd.setFrame(frame);
				editfield = new EditField(frame.getDocuments(), frame.getModeIndex());
				editfield.setType(WINDOW_EDITFIELD);
				editfieldcmd.setEditField(editfield);
				editfield.setName(editfield.MakeID(
						WINDOW_EDITFIELD, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[editfield.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = EditField.EDITFIELD_DEFAULT_HEIGHT;
				editfieldcmd.setLayout(constraint);
				return editfieldcmd;
			}

			if (childClass == EditArea.class) {
				EditArea editarea;
				EditAreaCreateCommand editareacmd = new EditAreaCreateCommand();

				editareacmd.setFrame(frame);
				editarea = new EditArea(frame.getDocuments(), frame.getModeIndex());
				editarea.setType(WINDOW_EDITAREA);
				editareacmd.setEditArea(editarea);
				editarea.setName(editarea.MakeID(
						WINDOW_EDITAREA, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[editarea.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				editareacmd.setLayout(constraint);
				return editareacmd;
			}

			if (childClass == Progress.class) {
				Progress progress;
				ProgressCreateCommand progresscmd = new ProgressCreateCommand();

				progresscmd.setFrame(frame);
				progress = new Progress(frame.getDocuments(), frame.getModeIndex());
				progress.setType(WINDOW_PROGRESS);
				progresscmd.setProgress(progress);
				progress.setName(progress.MakeID(
						WINDOW_PROGRESS, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[progress.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = Progress.PROGRESS_DEFAULT_HEIGHT;
				progresscmd.setLayout(constraint);
				return progresscmd;
			}
			if (childClass == Button.class) {
				Button button;
				ButtonCreateCommand buttoncmd = new ButtonCreateCommand();

				buttoncmd.setFrame(frame);
				button = new Button(frame.getDocuments(), frame.getModeIndex());
				button.setType(WINDOW_BUTTON);
				buttoncmd.setButton(button);
				button.setName(button.MakeID(WINDOW_BUTTON,
						""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[button.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				buttoncmd.setLayout(constraint);
				return buttoncmd;
			}
			if (childClass == ListControl.class) {
				ListControl list;
				ListCreateCommand listcmd = new ListCreateCommand();

				listcmd.setFrame(frame);
				list = new ListControl(frame.getDocuments(), frame.getModeIndex());
				list.setType(WINDOW_LIST);
				listcmd.setListCtl(list);
				list.setName(list.MakeID(WINDOW_LIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[list.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				listcmd.setLayout(constraint);
				return listcmd;
			}
			if (childClass == IconList.class) {
				IconList iconlist;
				IconListCreateCommand iconlistcmd = new IconListCreateCommand();

				iconlistcmd.setFrame(frame);
				iconlist = new IconList(frame.getDocuments(), frame.getModeIndex());
				iconlist.setType(WINDOW_ICONLIST);
				iconlistcmd.setIconList(iconlist);
				iconlist.setName(iconlist.MakeID(
						WINDOW_ICONLIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[iconlist.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				iconlistcmd.setLayout(constraint);
				return iconlistcmd;
			}
			if (childClass == CustomList.class) {
				CustomList customlist;
				CustomListCreateCommand customcontrolcmd = new CustomListCreateCommand();

				customcontrolcmd.setFrame(frame);
				customlist = new CustomList(frame.getDocuments(), frame.getModeIndex());
				customlist.setType(WINDOW_CUSTOMLIST);
				customcontrolcmd.setCustromControl(customlist);
				customlist.setName(customlist.MakeID(
						WINDOW_CUSTOMLIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[customlist.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				customcontrolcmd.setLayout(constraint);
				return customcontrolcmd;
			}

			if (childClass == ScrollPanel.class) {
				ScrollPanel scrollPanel;
				ScrollPanelCreateCommand scrollpanelcmd = new ScrollPanelCreateCommand();

				scrollpanelcmd.setFrame(frame);
				scrollPanel = new ScrollPanel(frame.getDocuments(), frame
						.getModeIndex());
				scrollPanel.setType(WINDOW_SCROLLPANEL);
				scrollpanelcmd.setScrollPanel(scrollPanel);
				scrollPanel.setName(scrollPanel.MakeID(
						WINDOW_SCROLLPANEL, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[scrollPanel.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				scrollpanelcmd.setLayout(constraint);
				return scrollpanelcmd;
			}

			if (childClass == Flash.class) {
				Flash flashcontrol;
				FlashControlCreateCommand flashcontrolcmd = new FlashControlCreateCommand();

				flashcontrolcmd.setFrame(frame);
				flashcontrol = new Flash(frame.getDocuments(), frame
						.getModeIndex());
				flashcontrol.setType(WINDOW_FLASHCONTROL);
				flashcontrolcmd.setFlashControl(flashcontrol);
				flashcontrol.setName(flashcontrol.MakeID(
						WINDOW_FLASHCONTROL, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[flashcontrol.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				flashcontrolcmd.setLayout(constraint);
				return flashcontrolcmd;
			}
			if (childClass == ColorPicker.class) {
				ColorPicker colorpicker;
				ColorPickerCreateCommand colorpickercmd = new ColorPickerCreateCommand();

				colorpickercmd.setFrame(frame);
				colorpicker = new ColorPicker(frame.getDocuments(), frame.getModeIndex());
				colorpicker.setType(WINDOW_COLORPICKER);
				colorpickercmd.setCustromControl(colorpicker);
				colorpicker.setName(colorpicker.MakeID(WINDOW_COLORPICKER,
						""));

                info = ResourceExplorer.getResourceView().getImageInfo(
                    cszTag[colorpicker.getType()], frame.getScreen());
                constraint.x = 0;
                constraint.width = info.dftlSize.x;
                constraint.height = info.dftlSize.y;
                colorpickercmd.setLayout(constraint);
                return colorpickercmd;
			}
			if (childClass == EditDate.class) {
				EditDate datepicker;
				DatePickerCreateCommand datepickercmd = new DatePickerCreateCommand();

				datepickercmd.setFrame(frame);
				datepicker = new EditDate(frame.getDocuments(), frame.getModeIndex());
				datepicker.setType(WINDOW_EDITDATE);
				datepickercmd.setCustromControl(datepicker);
				datepicker.setName(datepicker.MakeID(
						WINDOW_EDITDATE, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[datepicker.getType()], frame.getScreen());
				constraint.x = 0;
                constraint.width = info.dftlSize.x;
				constraint.height = info.dftlSize.y;
				datepickercmd.setLayout(constraint);
				return datepickercmd;
			}
			if (childClass == ExpandableList.class) {
				ExpandableList expandablelist;
				ExpandableListCreateCommand expandablelistcmd = new ExpandableListCreateCommand();

				expandablelistcmd.setFrame(frame);
				expandablelist = new ExpandableList(frame.getDocuments(), frame
						.getModeIndex());
				expandablelist.setType(WINDOW_EXPANDABLELIST);
				expandablelistcmd.setCustromControl(expandablelist);
				expandablelist.setName(expandablelist
						.MakeID(WINDOW_EXPANDABLELIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[expandablelist.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				expandablelistcmd.setLayout(constraint);
				return expandablelistcmd;
			}
			if (childClass == GroupedList.class) {
				GroupedList groupedlist;
				GroupedListCreateCommand groupedlistcmd = new GroupedListCreateCommand();

				groupedlistcmd.setFrame(frame);
				groupedlist = new GroupedList(frame.getDocuments(), frame
						.getModeIndex());
				groupedlist.setType(WINDOW_GROUPEDLIST);
				groupedlistcmd.setCustromControl(groupedlist);
				groupedlist.setName(groupedlist.MakeID(
						WINDOW_GROUPEDLIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[groupedlist.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				groupedlistcmd.setLayout(constraint);
				return groupedlistcmd;
			}
			if (childClass == OverlayPanel.class) {
				OverlayPanel overlaypanel;
				OverlayPanelCreateCommand overlaypanelcmd = new OverlayPanelCreateCommand();

				overlaypanelcmd.setFrame(frame);
				overlaypanel = new OverlayPanel(frame.getDocuments(), frame
						.getModeIndex());
				overlaypanel.setType(WINDOW_OVERLAYPANEL);
				overlaypanelcmd.setCustromControl(overlaypanel);
				overlaypanel.setName(overlaypanel.MakeID(
						WINDOW_OVERLAYPANEL, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[overlaypanel.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				overlaypanelcmd.setLayout(constraint);
				return overlaypanelcmd;
			}
			if (childClass == Panel.class) {
				Panel panel;
				PanelCreateCommand panelcmd = new PanelCreateCommand();

				panelcmd.setFrame(frame);
				panel = new Panel(frame.getDocuments(), frame.getModeIndex());
				panel.setType(WINDOW_PANEL);
				panelcmd.setCustromControl(panel);
				panel.setName(panel.MakeID(
						WINDOW_PANEL, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[panel.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				panelcmd.setLayout(constraint);
				return panelcmd;
			}

			if (childClass == SlidableGroupedList.class) {
				SlidableGroupedList sgList;
				SlidableGroupedListCreateCommand sgListcmd = new SlidableGroupedListCreateCommand();

				sgListcmd.setFrame(frame);
				sgList = new SlidableGroupedList(frame.getDocuments(), frame
						.getModeIndex());
				sgList.setType(WINDOW_SLIDABLEGROUPEDLIST);
				sgListcmd.setListCtl(sgList);
				sgList.setName(sgList.MakeID(
						WINDOW_SLIDABLEGROUPEDLIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[sgList.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				sgListcmd.setLayout(constraint);
				return sgListcmd;
			}

			if (childClass == SlidableList.class) {
				SlidableList sList;
				SlidableListCreateCommand sListcmd = new SlidableListCreateCommand();

				sListcmd.setFrame(frame);
				sList = new SlidableList(frame.getDocuments(), frame.getModeIndex());
				sList.setType(WINDOW_SLIDABLELIST);
				sListcmd.setListCtl(sList);
				sList.setName(sList.MakeID(
						WINDOW_SLIDABLELIST, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[sList.getType()], frame.getScreen());
				constraint.x = (constraint.x < 0) ? 0 : constraint.x;
				constraint.y = (constraint.y < 0) ? 0 : constraint.y;
				constraint.width = (constraint.width <= 0) ? info.dftlSize.x
						: constraint.width;
				constraint.height = (constraint.height <= 0) ? info.dftlSize.y
						: constraint.height;
				// constraint.height = IconList.ICONLIST_DEFAULT_HEIGHT;
				sListcmd.setLayout(constraint);
				return sListcmd;
			}
			if (childClass == EditTime.class) {
				EditTime timepicker;
				TimePickerCreateCommand timepickercmd = new TimePickerCreateCommand();

				timepickercmd.setFrame(frame);
				timepicker = new EditTime(frame.getDocuments(), frame.getModeIndex());
				timepicker.setType(WINDOW_EDITTIME);
				timepickercmd.setModel(timepicker);
				timepicker.setName(timepicker.MakeID(
						WINDOW_EDITTIME, ""));

				info = ResourceExplorer.getResourceView().getImageInfo(
						cszTag[timepicker.getType()], frame.getScreen());
				constraint.x = 0;
                constraint.width = info.dftlSize.x;
				constraint.height = info.dftlSize.y;
				timepickercmd.setLayout(constraint);
				return timepickercmd;
			}

		}
		return null;
	}

	@Override
	protected Command createAddCommand(EditPart child, Object constraint) {
		if(!(constraint instanceof Rectangle) || !(child.getModel() instanceof FrameNode))
			return null;
		if(child instanceof OverlayPanelPart)
			return null;
		
		FrameNode model = (FrameNode) child.getModel();
		Object parent = getHost().getModel();
		if(!(parent instanceof FrameNode))
			return null;
		
		FrameNode parentModel = (FrameNode) parent;
//		Point size = new Point(model.getLayout().width, model.getLayout().height);
//		if(model instanceof ColorPicker ||
//				model instanceof DatePicker ||
//				model instanceof TimePicker) {
//			Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
//					FrameConst.cszTag[model.getType()], parentModel.getScreen());
//			size = info.minSize;
//		}
		Tag_info info = ResourceExplorer.getResourceView().getImageInfo(
				FrameConst.cszTag[model.getType()], parentModel.getScreen());
		if(parentModel.getLayout().width < info.minSize.x || parentModel.getLayout().height < info.minSize.y) {
			if(parentModel instanceof PanelFrame || parentModel instanceof Panel || parentModel instanceof OverlayPanel)
				MessageDialog.openError(null, "Add " + FrameConst.cszTag[model.getType()], 
						"This Control cannot be inserted to the Panel because it is larger than the Panel.");
			else if(parentModel instanceof Popup)
				MessageDialog.openError(null, "Add " + FrameConst.cszTag[model.getType()], 
				"This Control cannot be inserted to the Popup because it is larger than the Popup.");

			return null;
		}
		
		OspAddCommand command = new OspAddCommand(child, constraint);
		command.setParentModel(parentModel);
		return command;
	}

	@Override
	protected Command getOrphanChildrenCommand(Request generic) {
		// TODO Auto-generated method stub
		Object parent = getHost().getModel();
		
		GroupRequest request = (GroupRequest)generic;
		List<?> editParts = request.getEditParts();
		CompoundCommand command = new CompoundCommand();
		command.setDebugLabel("getOrphanChildrenCommand");//$NON-NLS-1$
		GraphicalEditPart childPart;

		for (int i = 0; i < editParts.size(); i++) {
			childPart = (GraphicalEditPart)editParts.get(i);

			OspDeleteCommand delCommand = new OspDeleteCommand();
			delCommand.setModel(childPart.getModel());
			delCommand.setParentModel(parent);
			command.add(delCommand);
		}
		return command.unwrap();
	}

	protected Command chainGuideAttachmentCommand(Request request,
			FrameNode element, Command cmd, boolean horizontal) {
		Command result = cmd;

		// Attach to guide, if one is given
		Integer guidePos = (Integer) request.getExtendedData().get(
				horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE
						: SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos != null) {
			int alignment = ((Integer) request.getExtendedData().get(
					horizontal ? SnapToGuides.KEY_HORIZONTAL_ANCHOR
							: SnapToGuides.KEY_VERTICAL_ANCHOR)).intValue();
			ChangeGuideCommand cgm = new ChangeGuideCommand(element,
					horizontal);
			cgm.setNewGuide(findGuideAt(guidePos.intValue(), horizontal),
					alignment);
			result = result.chain(cgm);
		}

		return result;
	}

	protected Command chainGuideDetachmentCommand(Request request,
			FrameNode element, Command cmd, boolean horizontal) {
		Command result = cmd;

		// Detach from guide, if none is given
		Integer guidePos = (Integer) request.getExtendedData().get(
				horizontal ? SnapToGuides.KEY_HORIZONTAL_GUIDE
						: SnapToGuides.KEY_VERTICAL_GUIDE);
		if (guidePos == null)
			result = result
					.chain(new ChangeGuideCommand(element, horizontal));

		return result;
	}

	protected OspElementGuide findGuideAt(int pos, boolean horizontal) {
		RulerProvider provider = ((RulerProvider) getHost().getViewer()
				.getProperty(
						horizontal ? RulerProvider.PROPERTY_VERTICAL_RULER
								: RulerProvider.PROPERTY_HORIZONTAL_RULER));
		return (OspElementGuide) provider.getGuideAt(pos);
	}
}
