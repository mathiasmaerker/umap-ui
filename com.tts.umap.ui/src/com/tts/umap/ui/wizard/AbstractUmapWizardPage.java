package com.tts.umap.ui.wizard;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;

public abstract class AbstractUmapWizardPage extends WizardPage {
	protected UmapExportModel model;

	public AbstractUmapWizardPage(String pageName) {
		super(pageName);
//		model = ((UmapExportWizard)getWizard()).exportModel;
	}

	public AbstractUmapWizardPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}
	
	protected abstract void saveDateToModel();
	protected abstract void onEnterPage(); 
	
	protected void applyErrorMessage(IStatus status) {
		String message = status.getMessage();
		if (message.length() == 0)
			message = null;
		switch (status.getSeverity()) {
		case IStatus.OK:
			setErrorMessage(null);
			setMessage(message);
			break;
		case IStatus.WARNING:
			setErrorMessage(null);
			setMessage(message, WizardPage.WARNING);
			break;
		case IStatus.INFO:
			setErrorMessage(null);
			setMessage(message, WizardPage.INFORMATION);
			break;
		default:
			setErrorMessage(message);
			setMessage(null);
			break;
		}
	}

}
