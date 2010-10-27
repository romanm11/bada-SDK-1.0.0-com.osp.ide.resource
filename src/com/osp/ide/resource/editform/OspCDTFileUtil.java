/**
 * 
 */
package com.osp.ide.resource.editform;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.model.CModelException;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.model.IBuffer;
import org.eclipse.cdt.core.model.ICElement;
import org.eclipse.cdt.core.model.IField;
import org.eclipse.cdt.core.model.IFunction;
import org.eclipse.cdt.core.model.IMethod;
import org.eclipse.cdt.core.model.IMethodDeclaration;
import org.eclipse.cdt.core.model.IStructure;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;

import com.osp.ide.resource.Activator;

/**
 * @author PD_CHJ
 *
 */
public class OspCDTFileUtil {
    private static final String HEADER_FOLDER = "inc";
    private static final String SOURCE_FOLDER = "src";
    private static final String SOURCE_EXT = "cpp"; //$NON-NLS-1$
    private static final String METHOD_INIT = "Initialize";

    /**
     * @param project
     * @param formId
     * @return
     */
    public static String getClassName(IProject project, String formId) {
        IFolder srcFolder = project.getFolder(SOURCE_FOLDER);
        if(srcFolder == null)
            return null;
        
        IResource[] resources = new IResource[0];
        try {
            resources = srcFolder.members();
        } catch (CoreException e) {
            Activator.setErrorMessage("OspCDTFileUtil.getClassName()", 
                e.getClass() + " - " + e.getMessage(), e);
            return null;
        }
        
        for(IResource resource : resources) {
            if(resource.getFileExtension() != null &&
                resource.getFileExtension().equals(SOURCE_EXT)) {
                ICElement element = CoreModel.getDefault().create(resource);
                ITranslationUnit translationUnit = null;
                if (element instanceof ITranslationUnit)
                    translationUnit = (ITranslationUnit) element;

                if(translationUnit == null)
                    continue;
                
                String className = getClassName(translationUnit, formId);
                if(className != null && !className.isEmpty())
                    return className;
            }
        }
        
        return null;
    }
    
    /**
     * @param cppUnit
     * @param formId
     * @return
     */
    public static String getClassName(ITranslationUnit cppUnit, String formId) {
        if(cppUnit == null || !cppUnit.isSourceUnit())
            return null;
        
        try {
            List<ICElement> methods = OspCDTFileUtil.getMethod(cppUnit);
        
            for(ICElement method : methods) {
                if(method instanceof IMethod) {
                    IMethod lMethod = (IMethod) method;
                    String name = lMethod.getElementName();
                    if(name.matches(".*" + METHOD_INIT)) {
                        String body = lMethod.getSource();
                        if(body.indexOf(formId) >= 0)
                            return name.split("::")[0];
                    }
                } else if(method instanceof IFunction) {
                    IFunction lFunction = (IFunction) method;
                    String name = lFunction.getElementName();
                    if(name.matches(".*" + METHOD_INIT)) {
                        String body = lFunction.getSource();
                        if(body.indexOf(formId) >= 0)
                            return name.split("::")[0];
                    }
                }
            }
        } catch (CModelException e) {
            Activator.setErrorMessage("OspCDTFileUtil.getClassName()", 
                e.getClass() + " - " + e.getMessage(), e);
        }
        
        return null;
    }

    /**
     * @param project
     * @param formId
     * @return
     */
    public static IPath getHeaderFile(IProject project, String formId) {
        ITranslationUnit headerUnit = getHeaderUnit(project, formId);
        
        if(headerUnit != null) {
            return headerUnit.getResource().getFullPath();
        }
        
        return null;
    }

    /**
     * @param project
     * @param formId
     * @return
     */
    public static IPath getHeaderFile(IProject project, String formId, ITranslationUnit cppUnit) {
        ITranslationUnit headerUnit = getHeaderUnit(project, formId, cppUnit);
        
        if(headerUnit != null) {
            return headerUnit.getResource().getFullPath();
        }
        
        return null;
    }

    /**
     * @param project
     * @param formId
     * @return
     */
    public static ITranslationUnit getHeaderUnit(IProject project, String formId) {
        ITranslationUnit cppUnit = getCppUnit(project, formId);
        if(cppUnit == null)
            return null;
        
        ITranslationUnit headerUnit = getHeaderUnit(project, formId, cppUnit);
        
        return headerUnit;
    }

    /**
     * @param project
     * @param formId
     * @param cppUnit
     * @return
     */
    public static ITranslationUnit getHeaderUnit(IProject project, String formId, ITranslationUnit cppUnit) {
        if(cppUnit == null || !cppUnit.isSourceUnit())
            cppUnit = getCppUnit(project, formId);
        if(cppUnit == null)
            return null;
        
        String className = getClassName(cppUnit, formId);
        if(className == null || className.isEmpty())
            return null;
        
        List<ICElement> includes;
        try {
            includes = cppUnit.getChildrenOfType(ICElement.C_INCLUDE);
        } catch (CModelException e) {
            Activator.setErrorMessage("OspCDTFileUtil.getHeaderUnit()", 
                e.getClass() + " - " + e.getMessage(), e);
            return null;
        }
        
        for(ICElement include : includes) {
            IResource resource = project.getFolder(HEADER_FOLDER).getFile(include.getElementName());
            ICElement element = CoreModel.getDefault().create(resource);
            ITranslationUnit translationUnit = null;
            if (element instanceof ITranslationUnit)
                translationUnit = (ITranslationUnit) element;

            if(translationUnit == null)
                continue;
            
            try {
                List<ICElement> classes = 
                    translationUnit.getChildrenOfType(ICElement.C_CLASS);
                
                for(ICElement lClass : classes) {
                    String name = lClass.getElementName();
                    if(name.equals(className)) {
                        return translationUnit;
                    }
                }
            } catch (CModelException e) {
                Activator.setErrorMessage("OspCDTFileUtil.getHeaderUnit()", 
                    e.getClass() + " - " + e.getMessage(), e);
                continue;
            }
        }
        
        return null;
    }

    /**
     * @param project
     * @param formId
     * @return
     */
    public static ITranslationUnit getCppUnit(IProject project, ITranslationUnit hUnit) {
        IFolder srcFolder = project.getFolder(SOURCE_FOLDER);
        if(srcFolder == null)
            return null;
        
        if(hUnit.isHeaderUnit()) {
        }
        
        return null;
    }
    
    /**
     * @param project
     * @param formId
     * @return
     */
    public static ITranslationUnit getCppUnit(IProject project, String formId) {
        IFolder srcFolder = project.getFolder(SOURCE_FOLDER);
        if(srcFolder == null)
            return null;
        
        IResource[] resources = new IResource[0];
        try {
            resources = srcFolder.members();
        } catch (CoreException e) {
            Activator.setErrorMessage("OspCDTFileUtil.getCppUnit()", 
                e.getClass() + " - " + e.getMessage(), e);
            return null;
        }
        
        for(IResource resource : resources) {
            if(resource.getFileExtension() != null &&
                resource.getFileExtension().equals(SOURCE_EXT)) {
                ICElement element = CoreModel.getDefault().create(resource);
                ITranslationUnit translationUnit = null;
                if (element instanceof ITranslationUnit)
                    translationUnit = (ITranslationUnit) element;

                if(translationUnit == null)
                    continue;
                
                try {
                    List<ICElement> methods = getMethod(translationUnit);
                
                    for(ICElement method : methods) {
                        if(method instanceof IMethod) {
                            IMethod lMethod = (IMethod) method;
                            String name = lMethod.getElementName();
                            if(name.matches(".*" + METHOD_INIT)) {
                                String body = lMethod.getSource();
                                if(body.indexOf(formId) >= 0)
                                    return translationUnit;
                            }
                        } else if(method instanceof IFunction) {
                            IFunction lFunction = (IFunction) method;
                            String name = lFunction.getElementName();
                            if(name.matches(".*" + METHOD_INIT)) {
                                String body = lFunction.getSource();
                                if(body.indexOf(formId) >= 0)
                                    return translationUnit;
                            }
                        }
                    }
                } catch (CModelException e) {
                    Activator.setErrorMessage("OspCDTFileUtil.getCppUnit()", 
                        e.getClass() + " - " + e.getMessage(), e);
                    continue;
                }
            }
        }
        
        return null;
    }
    
    /**
     * @param project
     * @param formId
     * @return
     */
    public static IPath getCppFile(IProject project, String formId) {
        ITranslationUnit cppUnit = getCppUnit(project, formId);
        
        if(cppUnit == null || !cppUnit.isSourceUnit())
            return null;
        
        return cppUnit.getResource().getFullPath();
    }

    /**
     * @param cppUnit
     * @return
     */
    public static List<ICElement> getMethod(ITranslationUnit cppUnit) {
        if(cppUnit.isSourceUnit()) {
            try {
                List<ICElement> method = cppUnit.getChildrenOfType(ICElement.C_METHOD);
                List<ICElement> function = cppUnit.getChildrenOfType(ICElement.C_FUNCTION);
                
                for(ICElement element : function) {
                    if(!method.contains(element))
                        method.add(element);
                }
                return method;
            } catch (CModelException e) {
                Activator.setErrorMessage("OspCDTFileUtil.getMethodAndFunction()", 
                    e.getClass() + " - " + e.getMessage(), e);
            }
        }
      
        return new ArrayList<ICElement>();
    }

    /**
     * @param hUnit
     * @return
     */
    private static IStructure getCClass(ITranslationUnit hUnit) {
        if(hUnit.isHeaderUnit()) {
            try {
                List<ICElement> classes = hUnit.getChildrenOfType(ICElement.C_CLASS);
                
                if(classes.size() > 0 && classes.get(0) instanceof IStructure)
                    return (IStructure) classes.get(0);
            } catch (CModelException e) {
                Activator.setErrorMessage("OspCDTFileUtil.getMethodAndFunction()", 
                    e.getClass() + " - " + e.getMessage(), e);
            }
        }
      
        return null;
    }

    /**
     * @param hUnit
     * @return
     */
    public static IMethodDeclaration[] getMethodDecls(ITranslationUnit hUnit) {
        if(hUnit.isHeaderUnit()) {
            try {
                IStructure lClass = getCClass(hUnit);
                if(lClass != null) {
                    IMethodDeclaration[] methodDecl = lClass.getMethods();
                    
                    return methodDecl;
                }
            } catch (CModelException e) {
                Activator.setErrorMessage("OspCDTFileUtil.getMethodAndFunction()", 
                    e.getClass() + " - " + e.getMessage(), e);
            }
        }
      
        return new IMethodDeclaration[0];
    }

    /**
     * @param hUnit
     * @return
     */
    public static IField[] getFields(ITranslationUnit hUnit) {
        if(hUnit.isHeaderUnit()) {
            try {
                IStructure lClass = getCClass(hUnit);
                if(lClass != null) {
                    IField[] fields = lClass.getFields();
                    
                    return fields;
                }
            } catch (CModelException e) {
                Activator.setErrorMessage("OspCDTFileUtil.getMethodAndFunction()", 
                    e.getClass() + " - " + e.getMessage(), e);
            }
        }
      
        return new IField[0];
    }

    /**
     * @param hUnit
     * @return
     */
    public static String[] getSuperClasses(ITranslationUnit hUnit) {
        if(hUnit.isHeaderUnit()) {
            IStructure lClass = getCClass(hUnit);
            if(lClass != null) {
                String[] superClasses = lClass.getSuperClassesNames();
                
                return superClasses;
            }
        }
      
        return new String[0];
    }
    
    /**
     * @param unit
     * @param formId
     * @param method
     * @throws PartInitException 
     */
    public static void openCppFile(ITranslationUnit unit, String formId, String method) throws PartInitException {
    	if(unit == null)
    		return;
    	
    	if(method == null || method.isEmpty())
    		method = METHOD_INIT;
    	
		IWorkbenchPage page = PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage();
		if(page == null)
			return;
		
		IProject project = unit.getResource().getProject();
		if(project == null)
			return;
		
        String source;
        try {
            IBuffer buffer = unit.getBuffer();
            source = buffer.getContents();
        } catch (CModelException e) {
            e.printStackTrace();
            return;
        }
        IResource resource = unit.getResource();
        if(resource instanceof IFile) {
            IFile file = (IFile) resource;
            IEditorPart editor = IDE.openEditor(page, file, true);
            if(editor.isDirty()) {
                MessageDialog.openQuestion(page.getWorkbenchWindow().getShell(), 
                    "Save Resource", "'" + file.getName() + "' has been modified. Save Changes?");
            }
            
            if(editor instanceof TextEditor) {
                TextEditor tEditor = (TextEditor) editor;
                String offsetString = getClassName(unit, formId) + "::";
                offsetString += method;
                int offset  = source.indexOf(offsetString);
                tEditor.selectAndReveal(offset, offsetString.length());
            }
        }
    }
}
