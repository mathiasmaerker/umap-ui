package com.tts.umap.ui.preferences;

import java.io.IOException;
import java.net.MalformedURLException;

import org.eclipse.jface.preference.StringButtonFieldEditor;
import org.eclipse.swt.widgets.Composite;

import com.tts.umap.ui.Activator;
import com.tts.umap.ui.common.UmapRestClient;

public class URIStringButtonFieldEditor extends StringButtonFieldEditor {

	public URIStringButtonFieldEditor(String name, String labelText,
			Composite parent) {
		super(name, labelText, parent);
		setChangeButtonText("Test Service");
	}
	 
	
	@Override
	protected String changePressed() {
			getPage().setErrorMessage(null);
			try {
				UmapRestClient client = new UmapRestClient(getTextControl().getText());
				client.setUsername(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_USER_NAME).trim());
				client.setPassword(Activator.getDefault().getPreferenceStore().getString(PreferenceConstants.P_PWD).trim());
				showMessage(client.testService());
			} catch (MalformedURLException e) {
				setErrorMessage("Please enter a valid URL");
				showErrorMessage();
			} catch (IOException e) {
				setErrorMessage("Can't connect to server");
				showErrorMessage();
			}
			
		return null;
	}

}
