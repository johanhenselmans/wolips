/*
 * Created on 15.02.2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
package org.objectstyle.wolips.wounit.prepare;

/**
 * @author uli
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */
public class PrepareWOUnit {
	private PrepareWOUnit() {
	}
	/*
		private static void buildWOUnit() throws Exception {
			URL relativeBuildFile = null;
			URL buildFile = null;
			IProgressMonitor monitor = null;
			try {
				relativeBuildFile =
					new URL(
						WOLipsPlugin.baseURL(),
						IWOLipsPluginConstants.build_user_home_properties);
				buildFile = Platform.asLocalURL(relativeBuildFile);
				monitor = new NullProgressMonitor();
				RunAnt runAnt = new RunAnt();
				runAnt.asAnt(buildFile.getFile().toString(), monitor, null);
			} finally {
				relativeBuildFile = null;
				buildFile = null;
				monitor = null;
			}
		}
	*/
}
