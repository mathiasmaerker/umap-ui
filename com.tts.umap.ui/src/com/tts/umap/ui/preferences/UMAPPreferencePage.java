package com.tts.umap.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tts.umap.ui.Activator;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By 
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to 
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */

public class UMAPPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	
	private StringFieldEditor classAbreviation;
	private StringFieldEditor intfAbreviation;
	private StringFieldEditor excAbreviation;
	private StringFieldEditor projectAbbreviation;
	private BooleanFieldEditor validation;
	private Group group;
	private Group group2;
	
	public UMAPPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
//		setDescription(Messages.UMAPPreferencePage_0);
	}
	
	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	public void createFieldEditors() {		
		
//		addField(new DirectoryFieldEditor(PreferenceConstants.P_PATH, 
//				Messages.UMAPPreferencePage_1, getFieldEditorParent()));
		group = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
		group.setText(Messages.UMAPPreferencesHeader);
		group.setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		group.setLayoutData(gd);		
		
//		group2 = new Group(getFieldEditorParent(), SWT.SHADOW_ETCHED_IN);
//		group2.setText(Messages.UmapStringButtonFieldEditor_0);
//		gd.horizontalSpan = 2;
//		group2.setLayoutData(gd);
//		group2.setLayout(new GridLayout());
		
		
		validation = new BooleanFieldEditor( PreferenceConstants.P_BOOLEAN,	Messages.UMAPPreferencePage_2, group);
		addField( validation );

		classAbreviation = new StringFieldEditor(PreferenceConstants.P_CLASS_NAME, Messages.UMAPPreferencePage_3, group);
		classAbreviation.setEmptyStringAllowed(false);
		classAbreviation.setEnabled(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_BOOLEAN), group);
		
		intfAbreviation = new StringFieldEditor(PreferenceConstants.P_INTF_NAME, Messages.UMAPPreferencePage_4, group);
		intfAbreviation.setEmptyStringAllowed(false);
		intfAbreviation.setEnabled(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_BOOLEAN), group);
		
		excAbreviation = new StringFieldEditor(PreferenceConstants.P_EXCP_NAME, Messages.UMAPPreferencePage_5, group);
		excAbreviation.setEmptyStringAllowed(false);
		excAbreviation.setEnabled(Activator.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.P_BOOLEAN), group);
		
		
//		projectAbbreviation = new StringFieldEditor(PreferenceConstants.P_PROJ_NAME, Messages.UMAPPreferencePage_6, group);
//		projectAbbreviation.setEmptyStringAllowed(true);

//		StringFieldEditor name = new StringFieldEditor(PreferenceConstants.P_USER_NAME, Messages.UmapStringButtonFieldEditor_2, group2);
//		
//		StringFieldEditor pass = new StringFieldEditor(PreferenceConstants.P_PWD, Messages.UmapStringButtonFieldEditor_3, group2);
		
//		URIStringButtonFieldEditor button = new URIStringButtonFieldEditor(PreferenceConstants.P_URI, Messages.UmapStringButtonFieldEditor_1, group2);
		
//		addField(name);
//		addField(pass);
//		addField(button);
		
		addField( classAbreviation );
		addField( intfAbreviation  );
		addField( excAbreviation   );
//		addField( projectAbbreviation );		
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
//	 */
	public void init(IWorkbench workbench) {
	}


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
        if (event.getProperty().equals(BooleanFieldEditor.VALUE)) {
            
            classAbreviation.setEnabled(validation.getBooleanValue(), group);
            intfAbreviation.setEnabled(validation.getBooleanValue(), group);
            excAbreviation.setEnabled(validation.getBooleanValue(), group);
  }    
	}
	
}