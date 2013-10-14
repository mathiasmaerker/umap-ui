/**
 * 
 */
package com.tts.umap.ui.wizard;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.tts.umap.ui.Activator;
import com.tts.umap.ui.common.UmapRestClient;
import com.tts.umap.ui.preferences.PreferenceConstants;

/**
 * @author MaerkerMa
 * 
 */
public class UmapExportModel {
	private String[] transportRequest;
	private String[] developmentClass;
	private String namespace;
	private boolean canFinish;
	private String selectedRequest;
	private String selectedPackage;
	private File location;

	public UmapExportModel() throws IOException  {
//			uri = new URL(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_URI));
			System.out.println(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_CLASS_NAME) + " URL of Preference Page");
//			connection = (HttpURLConnection) uri.openConnection(); +
		setCanFinish(false);
	}
	
	public void queryTransportRequests() throws MalformedURLException, IOException{
		UmapRestClient client = new UmapRestClient(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_URI));
		client.setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_NAME));
		client.setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PWD));
		setTransportRequest(client.queryTransportRequest());
	}
	
	public void queryPackageList() throws Exception{
		UmapRestClient client = new UmapRestClient(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_URI));
		client.setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_NAME));
		client.setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PWD));
		setDevelopmentClass(client.queryPackage(namespace));
	}
	/**
	 * @param transportRequest the transportRequest to set
	 */
	public void setTransportRequest(String[] transportRequest) {
		this.transportRequest = transportRequest;
	}

	/**
	 * @return the transportRequest
	 */
	public String[] getTransportRequest() {
		return transportRequest;
	}

	/**
	 * @param developmentClass the developmentClass to set
	 */
	public void setDevelopmentClass(String[] developmentClass) {
		this.developmentClass = developmentClass;
	}

	/**
	 * @return the developmentClass
	 */
	public String[] getDevelopmentClass() {
		return developmentClass;
	}
	/**
	 * @param namespace the namespace to set
	 */
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	/**
	 * @param canFinish the canFinish to set
	 */
	public void setCanFinish(boolean canFinish) {
		this.canFinish = canFinish;
	}

	/**
	 * @return the canFinish
	 */
	public boolean isCanFinish() {
		return canFinish;
	}

	/**
	 * @param selectedRequest the selectedRequest to set
	 */
	public void setSelectedRequest(String selectedRequest) {
		this.selectedRequest = selectedRequest;
	}

	/**
	 * @return the selectedRequest
	 */
	public String getSelectedRequest() {
		return selectedRequest;
	}

	/**
	 * @param selectedPackage the selectedPackage to set
	 */
	public void setSelectedPackage(String selectedPackage) {
		this.selectedPackage = selectedPackage;
	}

	/**
	 * @return the selectedPackage
	 */
	public String getSelectedPackage() {
		return selectedPackage;
	}
	/**
	 * @return the location
	 */
	public File getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(File location) {
		this.location = location;
	}
}
