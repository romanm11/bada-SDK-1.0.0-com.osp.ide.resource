package com.osp.ide.resource.editform;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.gef.internal.GEFMessages;
import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.AlignmentRetargetAction;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.MatchHeightRetargetAction;
import org.eclipse.gef.ui.actions.MatchWidthRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.gef.ui.actions.ZoomComboContributionItem;
import org.eclipse.gef.ui.actions.ZoomInRetargetAction;
import org.eclipse.gef.ui.actions.ZoomOutRetargetAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.RetargetAction;

import com.osp.ide.resource.actions.ControlSnapAction;
import com.osp.ide.resource.common.GridComboContribution;
import com.osp.ide.resource.common.ModeComboContribution;

@SuppressWarnings("restriction")
public class OspFormEditorActionBarContributor extends ActionBarContributor {
	ICoolBarManager coolBarManager;
	private ModeComboContribution modeCombo;
	private GridComboContribution gridCombo;
	private ControlSnapAction controlSnap;

	@Override
	public void setActiveEditor(IEditorPart editor) {
		super.setActiveEditor(editor);
		if(modeCombo != null)
			modeCombo.setEditor(editor);
		if(gridCombo != null)
			gridCombo.setEditor(editor);
		if(controlSnap != null)
			controlSnap.setEditor(editor);
	}

	@Override
	protected void buildActions() {
		IWorkbenchWindow iww = getPage().getWorkbenchWindow();

		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());

		addRetargetAction((RetargetAction) ActionFactory.CUT.create(iww));
		addRetargetAction((RetargetAction) ActionFactory.COPY.create(iww));
		addRetargetAction((RetargetAction) ActionFactory.PASTE.create(iww));

		addRetargetAction(new ZoomInRetargetAction());
		addRetargetAction(new ZoomOutRetargetAction());

		addRetargetAction(new AlignmentRetargetAction(PositionConstants.LEFT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.CENTER));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.RIGHT));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.TOP));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.MIDDLE));
		addRetargetAction(new AlignmentRetargetAction(PositionConstants.BOTTOM));
		addRetargetAction(new MatchWidthRetargetAction());
		addRetargetAction(new MatchHeightRetargetAction());

		RetargetAction gridAction = new RetargetAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY, 
	            GEFMessages.ToggleGrid_Label, IAction.AS_CHECK_BOX);
	        
//	        ImageDescriptor newImage = AbstractUIPlugin.imageDescriptorFromPlugin(
//	            Activator.PLUGIN_ID, "icons/gridLine.png");
//	        gridAction.setImageDescriptor(newImage);
	        
			addRetargetAction(gridAction);

			RetargetAction rulerAction = new RetargetAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY, 
	            GEFMessages.ToggleRulerVisibility_Label, IAction.AS_CHECK_BOX);
			
//	        newImage = AbstractUIPlugin.imageDescriptorFromPlugin(
//	            Activator.PLUGIN_ID, "icons/ruler.png");
//	        rulerAction.setImageDescriptor(newImage);
	        
			addRetargetAction(rulerAction);

//			controlSnap = new ControlSnapAction("Control Snap", IAction.AS_CHECK_BOX);
//			addRetargetAction(controlSnap);
	}

	@Override
	protected void declareGlobalActionKeys() {
		// TODO Auto-generated method stub

	}

	@Override
	public void contributeToToolBar(IToolBarManager toolBarManager) {

		toolBarManager.add(getAction(ActionFactory.UNDO.getId()));
		toolBarManager.add(getAction(ActionFactory.REDO.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(ActionFactory.CUT.getId()));
		toolBarManager.add(getAction(ActionFactory.COPY.getId()));
		toolBarManager.add(getAction(ActionFactory.PASTE.getId()));
		toolBarManager.add(getAction(ActionFactory.DELETE.getId()));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_IN));
		toolBarManager.add(getAction(GEFActionConstants.ZOOM_OUT));
		toolBarManager.add(new ZoomComboContributionItem(getPage()));
		toolBarManager.add(new Separator());
		
		if(modeCombo != null) {
			modeCombo.dispose();
			modeCombo = null;
		}
		modeCombo = new ModeComboContribution();
		toolBarManager.add(modeCombo);
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_LEFT));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_CENTER));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_RIGHT));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_TOP));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_MIDDLE));
		toolBarManager.add(getAction(GEFActionConstants.ALIGN_BOTTOM));
		toolBarManager.add(new Separator());
		toolBarManager.add(getAction(GEFActionConstants.MATCH_WIDTH));
		toolBarManager.add(getAction(GEFActionConstants.MATCH_HEIGHT));
		toolBarManager.add(new Separator());
		toolBarManager
				.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		
		if(gridCombo != null) {
			gridCombo.dispose();
			gridCombo = null;
		}
		gridCombo = new GridComboContribution();
		toolBarManager.add(gridCombo);
		toolBarManager.add(new Separator());
		toolBarManager
				.add(getAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY));
		
		toolBarManager.add(new Separator());
        if(controlSnap != null)
            toolBarManager.add(controlSnap);
	}

	// @Override
	// public void contributeToCoolBar(ICoolBarManager coolBarManager) {
	//
	// super.contributeToCoolBar(coolBarManager);
	// this.coolBarManager = coolBarManager;
	// IToolBarManager toolBarManager = new ToolBarManager(
	// IAction.AS_DROP_DOWN_MENU);
	//
	// Action a = new Action("action", IAction.AS_DROP_DOWN_MENU) {
	// };
	//
	// toolBarManager.add(a);
	// coolBarManager.add(toolBarManager);
	// coolBarManager.update(true);
	//
	// if (coolBarManager instanceof SubCoolBarManager) {
	// coolBarManager = ((SubCoolBarManager) coolBarManager);
	// ((SubCoolBarManager) coolBarManager).setVisible(true);
	// }
	// // super.dispose();
	// // if(coolBarManager != null)
	// // ((SubCoolBarManager) coolBarManager).getParent().update(true);
	// }
	//
	@Override
	public void dispose() {
		if(gridCombo != null) {
			gridCombo.dispose();
			gridCombo = null;
		}
		
		if(modeCombo != null) {
			modeCombo.dispose();
			modeCombo = null;
		}
		super.dispose();
	}

	// @Override
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
		MenuManager viewMenu = new MenuManager("View");
		viewMenu.add(getAction(GEFActionConstants.TOGGLE_GRID_VISIBILITY));
		viewMenu.add(getAction(GEFActionConstants.TOGGLE_RULER_VISIBILITY));
	}

}
