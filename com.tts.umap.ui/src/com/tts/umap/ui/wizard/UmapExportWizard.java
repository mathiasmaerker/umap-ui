/**
 * 
 */
package com.tts.umap.ui.wizard;

import java.io.IOException;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.tts.umap.ui.common.UmapRestClient;

/**
 * @author MaerkerMa
 *
 */
public class UmapExportWizard extends Wizard implements INewWizard, Listener {
	
	private ISelection selection;
	protected RequestPage requestPage;
	protected PackageSelectPage packageSelectPage;
	protected FinishPage finishPage;
	
	protected UmapExportModel exportModel;

	/**
	 *  
	 */
	public UmapExportWizard() {
		super();
		setNeedsProgressMonitor(true);
		try {
			exportModel = new UmapExportModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**
	 * Instantiation of the three wizard pages. An inner pageChangeListener will call the 
	 * redefined onEnterPage method of each page to start initialisations
	 */
	public void addPages() {
		IWizardContainer wizardContainer = this.getContainer();
	    if ( wizardContainer instanceof IPageChangeProvider )
	    {
	      IPageChangeProvider pageChangeProvider = (IPageChangeProvider) wizardContainer;
	      pageChangeProvider.addPageChangedListener( new IPageChangedListener()
	      {
	        @Override
	        public void pageChanged( PageChangedEvent event )
	        {
	            ((AbstractUmapWizardPage)event.getSelectedPage()).onEnterPage();
	        }
	      } );
	    }
		packageSelectPage = new PackageSelectPage("PackageSelectPage");
		requestPage = new RequestPage("RequestPage");
		finishPage = new FinishPage("Finish");
		
		addPage(packageSelectPage);
		addPage(requestPage);
		addPage(finishPage);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	@Override
	public IWizardPage getNextPage(IWizardPage page) {
//		System.out.println("getNextPage in Wizard");
		((AbstractUmapWizardPage)page.getNextPage()).onEnterPage();
		return page.getNextPage();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		System.out.println("Wizard init");
		this.selection = selection;
		try {
			exportModel = new UmapExportModel();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// TODO Hier aufräumen und Fenster schliessen
		UmapRestClient client = new UmapRestClient();
		try {
			return client.publishModel(exportModel.getLocation());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
		 
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		return exportModel.isCanFinish();
	}

	@Override
	public void handleEvent(Event event) {
		System.out.println(event.toString());
		
	}

	/**
	 * @return the selection
	 */
	public ISelection getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

}
