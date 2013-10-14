/**
 * 
 */
package com.tts.umap.ui.wizard;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

/**
 * @author MaerkerMa
 *
 */
public class RequestPage extends AbstractUmapWizardPage {
	private List transportRequests;
	private List developmentClass;
	private UmapExportModel model;

	/**
	 * This wizard page will fetch available Transportrequest and Development classes from the SAP System
	 * @param pageName
	 */
	public RequestPage(String pageName) {
		super(pageName);
		setTitle("UMAP Export to SAP");
		setDescription("Select Transport Request and Development Class");
		
	} 

	/**
	 * @param pageName
	 * @param title
	 * @param titleImage
	 */
	public RequestPage(String pageName, String title, ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
		setDescription("Select Transport Request and Development Class");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
	    // create the composite to hold the widgets
		GridData gd;
		Composite composite = new Composite(parent, SWT.NONE);

	    // create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 2;
		gl.numColumns = ncol;
		composite.setLayout(gl);

		Label transportLabel = new Label (composite, SWT.NONE);
		transportLabel.setText("Transport Requests:");
		
		transportRequests = new List(composite, SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		transportRequests.setLayoutData(gd);

		Label seperator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		seperator.setLayoutData(gd);
		
		Label devclassLabel = new Label (composite, SWT.NONE);
		devclassLabel.setText("Development Class:");
		//TODO Werte aus Model holen
		developmentClass = new List(composite, SWT.BORDER | SWT.READ_ONLY);
//		developmentClass.add("ZLSO_PACKAGE1");
//		developmentClass.add("ZLSO_PACKAGE2");
//		developmentClass.add("ZLSO_PACKAGE3");
//		System.out.println(model.getDevelopmentClass().length);
//		developmentClass.setItems(model.getDevelopmentClass());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = ncol;
		developmentClass.setLayoutData(gd);
		
		setControl(composite);
		setPageComplete(isPageComplete());
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		// TODO Prüfen ob werte ausgewählt wurden
		try {
			if (model.getSelectedPackage().equals(""))return false;
			if (model.getSelectedRequest().equals(""))return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	@Override
	protected
	void onEnterPage() {
//		// TODO Model abfragen
		UmapExportWizard wizard = (UmapExportWizard) getWizard();
		try {
			model = wizard.exportModel;
			model.queryPackageList();
			
//			   getContainer().run(true, true, new IRunnableWithProgress() {
//				
//				@Override
//				public void run(IProgressMonitor monitor) throws InvocationTargetException,
//						InterruptedException {
//			         int sum = 20;
//			         monitor.beginTask("Fetching Packages: ", sum);
//			         for (int i = 0; i < sum; i++) {
//			            monitor.subTask(Integer.toString(i));
//			            //sleep to simulate long running operation
//			            Thread.sleep(100);
//			            monitor.worked(1);
//			         }
//			         monitor.done();
//					
//				}
//			});
			   model.queryTransportRequests();
//			   getContainer().run(true, true, new IRunnableWithProgress() {
//					
//					@Override
//					public void run(IProgressMonitor monitor) throws InvocationTargetException,
//							InterruptedException {
//				         int sum = 20;
//				         monitor.beginTask("Fetching Requests: ", sum);
//				         for (int i = 0; i < sum; i++) {
//				            monitor.subTask(Integer.toString(i));
//				            //sleep to simulate long running operation
//				            Thread.sleep(100);
//				            monitor.worked(1);
//				         }
//				         monitor.done();
//						
//					}
//				});
			developmentClass.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
//					
					try {
						model.setSelectedPackage(developmentClass.getItem(developmentClass.getSelectionIndex()));
//						System.out.println(model.getSelectedPackage());
						setPageComplete(isPageComplete());
					} catch (IllegalArgumentException  e2) {
						e2.printStackTrace();
					}
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					//Nothing to do
				}
			});
			developmentClass.removeAll();
			developmentClass.setItems(model.getDevelopmentClass());
			
			transportRequests.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						model.setSelectedRequest(transportRequests.getItem(transportRequests.getSelectionIndex()));
						setPageComplete(isPageComplete());
					} catch (IllegalArgumentException e2) {
						e2.printStackTrace();
					}
					
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					//Not needed
				}
			});
			transportRequests.removeAll();
			transportRequests.setItems(model.getTransportRequest());
//			developmentClass.pack();
		} catch (Exception e) {

			e.printStackTrace();
		}
		System.out.println("class request onEnter");
		
	}

	@Override
	protected void saveDateToModel() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.WizardPage#getNextPage()
	 */
	@Override
	public IWizardPage getNextPage() {
		UmapExportWizard wizard = (UmapExportWizard) getWizard();
		return wizard.finishPage;
	}

	


}
