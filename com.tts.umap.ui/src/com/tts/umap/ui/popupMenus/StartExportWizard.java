/**
 * 
 */
package com.tts.umap.ui.popupMenus;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.tts.umap.ui.wizard.UmapExportWizard;

/**
 * @author MaerkerMa
 *
 */
public class StartExportWizard implements IObjectActionDelegate {

	private Shell shell;
	private UmapExportWizard uwizard;
	@Override
	public void run(IAction action) {
		WizardDialog wd = new WizardDialog(shell, uwizard);
		wd.create();
		wd.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		uwizard = new UmapExportWizard();
		uwizard.setSelection(selection);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
		
	}



}
