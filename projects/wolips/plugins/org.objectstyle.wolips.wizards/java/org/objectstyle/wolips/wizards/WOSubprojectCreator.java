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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.objectstyle.wolips.core.plugin.IWOLipsPluginConstants;

/**
 * @author mnolte
 * @author uli
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WOSubprojectCreator extends WOProjectResourceCreator {

	private String subprojectName;
	
	/**
	 * Constructor for WOSubprojectCreator.
	 */
	public WOSubprojectCreator(
		IResource parentResource,
		String subprojectName) {
		super(parentResource);
		this.subprojectName =
			subprojectName + "." + IWOLipsPluginConstants.EXT_SUBPROJECT;
	}
	/**
	 * @see org.objectstyle.wolips.wizards.WOProjectResourceCreator#getType()
	 */
	protected int getType() {
		return SUBPROJECT_CREATOR;
	}
	/**
	 * @see WOProjectResourceCreator#run(IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor)
		throws InvocationTargetException, InterruptedException {
		super.run(monitor);
		try {
			createSubproject(monitor);
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		}
	}
	/**
	 * Method createSubproject.
	 * @param monitor
	 * @throws CoreException
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public void createSubproject(IProgressMonitor monitor)
		throws CoreException, InvocationTargetException, InterruptedException {
		IFolder subprojectFolder = null;

		switch (parentResource.getType()) {
			case IResource.PROJECT :
				subprojectFolder =
					((IProject) parentResource).getFolder(subprojectName);
				break;
			case IResource.FOLDER :
				subprojectFolder =
					((IFolder) parentResource).getFolder(subprojectName);

				break;
			default :
				throw new InvocationTargetException(
					new Exception("Wrong parent resource - check validation"));
		}

		createResourceFolderInProject(subprojectFolder, monitor);

		String projectTemplateID =
			Messages.getString("webobjects.projectType.java.subproject");

		// delegate subproject resource file creation to project creator
		WOProjectCreator projectCreator =
			new WOProjectCreator(subprojectFolder, projectTemplateID);
		projectCreator.run(monitor);
	}
}
