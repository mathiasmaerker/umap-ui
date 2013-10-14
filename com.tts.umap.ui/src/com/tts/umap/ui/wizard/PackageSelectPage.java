/**
 * 
 */
package com.tts.umap.ui.wizard;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * @author MaerkerMa
 * 
 */
public class PackageSelectPage extends AbstractUmapWizardPage implements
		Listener {

	private Text text;
	private IStatus packageStatus;

	public PackageSelectPage(String pageName, String title,
			ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setDescription("Specifiy Package");
		packageStatus = new Status(IStatus.OK, "not_used", 0, "", null);
	}

	public PackageSelectPage(String pageName) {
		super(pageName);
		setTitle("UMAP Export to SAP");
		setDescription("Specifiy Package");
		packageStatus = new Status(IStatus.OK, "not_used", 0, "", null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		GridData gd;
		Composite composite = new Composite(parent, SWT.NONE);

		// create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);

		Label transportLabel = new Label(composite, SWT.NONE);
		transportLabel
				.setText("Enter Package, allowed special characters * be awere that enter only * may result in a very long runtime");

		text = new Text(composite, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		text.setLayoutData(gd);
		text.addListener(SWT.KeyUp, this);

		setControl(composite);
		setPageComplete(isPageComplete());
		

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		Pattern pattern = Pattern.compile("[^a-zA-Z0-9._]");
		Matcher matcher = pattern.matcher(text.getText());
		if (matcher.find()) {
			packageStatus = new Status(IStatus.ERROR, "not_used", 0,
					"Only _ allowed as special character", null);
			super.applyErrorMessage(packageStatus);
			return false;
		}
		packageStatus = new Status(IStatus.OK, "not_used", 0, "", null);
		super.applyErrorMessage(packageStatus);
		if (!text.getText().trim().isEmpty()) {
			saveDateToModel();
			return true;
		}
		return false;

	}

	//
	// /* (non-Javadoc)
	// * @see org.eclipse.jface.wizard.WizardPage#canFlipToNextPage()
	// */
	@Override
	public void handleEvent(Event event) {
//		System.out.println(event.widget.toString());
		if (event.widget == text) {
			setPageComplete(isPageComplete());
		}

	}

	@Override
	protected
	void onEnterPage() {
		// TODO Model abfragen
//		System.out.println("class packegeselect onEnter");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
//		System.out.println("getNextPage in PackegePage");
		UmapExportWizard wizard = (UmapExportWizard) getWizard();
//		wizard.requestPage.onEnterPage();
		return wizard.requestPage;
	}

	@Override
	protected void saveDateToModel() {
		UmapExportWizard wizard = (UmapExportWizard) getWizard();
			wizard.exportModel.setNamespace(text.getText());

		
	}
	
}
