/* ====================================================================
 * 
 * The ObjectStyle Group Software License, Version 1.0 
 *
 * Copyright (c) 2002 The ObjectStyle Group 
 * and individual authors of the software.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        ObjectStyle Group (http://objectstyle.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "ObjectStyle Group" and "Cayenne" 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact andrus@objectstyle.org.
 *
 * 5. Products derived from this software may not be called "ObjectStyle"
 *    nor may "ObjectStyle" appear in their names without prior written
 *    permission of the ObjectStyle Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE OBJECTSTYLE GROUP OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the ObjectStyle Group.  For more
 * information on the ObjectStyle Group, please see
 * <http://objectstyle.org/>.
 *
 */
package org.objectstyle.wolips.wizards;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.objectstyle.wolips.plugin.WOLipsPlugin;
import org.objectstyle.wolips.project.ProjectHelper;
/**
 * @author mnolte
 * @author uli
 *
 */
public class WOApplicationCreationPage extends WOProjectCreationPage {
	private IResource elementToOpen;

	/**
	 * Constructor for WOApplicationCreationPage.
	 * @param pageName
	 */
	public WOApplicationCreationPage(String pageName) {
		super(pageName);
		setTitle(Messages.getString("WOApplicationCreationPage.title"));
		setDescription(
			Messages.getString("WOApplicationCreationPage.description"));
		setInitialProjectName(
			Messages.getString(
				"WOApplicationCreationPage.newProject.defaultName"));
	}
	/**
	 * Method getProjectTemplateID.
	 * @return String
	 */
	private String getProjectTemplateID() {
		String projectTemplateID =
			Messages.getString("webobjects.projectType.java.application");
		if (!useDefaults()
			&& (getLocationPath() != null)
			&& (getLocationPath().makeAbsolute().toFile().isDirectory())
			&& (getLocationPath().makeAbsolute().toFile().list().length > 0))
			projectTemplateID =
				Messages.getString(
					"webobjects.projectType.java.application.import");
		return projectTemplateID;
	}
	/**
	 * Method createProject.
	 * @return boolean
	 */
	public boolean createProject() {
		IProject newProject = getProjectHandle();
		String projectTemplateID = this.getProjectTemplateID();
		IRunnableWithProgress op =
			new WorkspaceModifyDelegatingOperation(
				new WOProjectCreator(
					newProject,
					projectTemplateID,
					getLocationPath(),
					getImportPath()));
		try {
			getContainer().run(false, false, op);
		} catch (InvocationTargetException e) {
			WOLipsPlugin.handleException(
				getShell(),
				e.getTargetException(),
				null);
			return false;
		} catch (InterruptedException e) {
			//WOLipsUtils.handleException(getShell(), e, null);
			return false;
		}
		IResource fileToOpen =
			ProjectHelper.getProjectSourceFolder(newProject).getFile(
				new Path("Application.java"));
		if (fileToOpen != null) {
			elementToOpen = fileToOpen;
		}
		return true;
	}
	/**
	 * Method getElementToOpen.
	 * @return resource to open on successful project creation
	 */
	public IResource getElementToOpen() {
		return elementToOpen;
	}

}
