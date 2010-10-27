package com.osp.ide.resource.editpanel;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class OspPanelActionBarAdvisor extends ActionBarAdvisor
{
    public OspPanelActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected IWorkbenchAction makeAction(IWorkbenchWindow window, ActionFactory af) {
    	IWorkbenchAction action = af.create(window);
    	register(action);
    	return action;
    }
    
    protected void makeActions(IWorkbenchWindow window) {
        // Creates the actions and registers them.
        // Registering is needed to ensure that key bindings work.
        // The corresponding commands keybindings are defined in the plugin.xml file.
        // Registering also provides automatic disposal of the actions when
        // the window is closed.
    	
    	makeAction(window, ActionFactory.UNDO);
    	makeAction(window, ActionFactory.REDO);
    	makeAction(window, ActionFactory.CUT);
    	makeAction(window, ActionFactory.COPY);
    	makeAction(window, ActionFactory.PASTE);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
    }
}
