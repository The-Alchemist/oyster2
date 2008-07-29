/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.neontoolkit.oyster2.client.gui.Activator;
import org.neontoolkit.oyster2.client.gui.dialogs.CalendarDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.DomainDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.InputDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.PartyDialog;
import org.neontoolkit.oyster2.client.gui.dialogs.SimpleListSelectionDialog;
import org.neontoolkit.oyster2.client.gui.validator.DateValidator;
import org.neontoolkit.oyster2.client.gui.validator.OntologyValidator;
import org.neontoolkit.oyster2.client.gui.validator.Validator;

/**
 * @author David Muñoz
 *
 */
public class CompositeFactory {
	public static final String LIST_DOMAIN_TYPE = "LIST_DOMAIN_TYPE"; //$NON-NLS-1$
	public static final String LIST_PARTY_TYPE = "LIST_PARTY_TYPE"; //$NON-NLS-1$
	public static final String LIST_AND_TEXT_TYPE = "LIST_AND_TEXT_TYPE"; //$NON-NLS-1$
	public static final String LIST_TYPE = "LIST_TYPE"; //$NON-NLS-1$
	public static final String COMBO_TYPE = "COMBO_TYPE"; //$NON-NLS-1$
	public static final String TEXT_TYPE = "TEXT_TYPE"; //$NON-NLS-1$
	public static final String LONG_TEXT_TYPE = "LONG_TEXT_TYPE"; //$NON-NLS-1$
	public static final String CALENDAR_TYPE = "CALENDAR_TYPE"; //$NON-NLS-1$
	public static final String NATURAL_LANGUAGE_TYPE = "NATURAL_LANGUAGE_TYPE"; //$NON-NLS-1$
	private static Map<String,Validator> validators = null;
	
	static {
		validators = new HashMap<String, Validator>();
		validators.put(OntologyValidator.validatorName,
				new OntologyValidator());
		validators.put(DateValidator.validatorName,
				new DateValidator());
	}
	
	
	public static InputComposite
		createComposite(String type,Composite parent, int style,
				String section, List<String> predefinedValues,String editable,
				String validatorName,String defaultValue, boolean isRequired) {
		Shell shell = Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
		String [] values = new String[0];
		
		if (predefinedValues != null) {
			if (predefinedValues.size() != 0)
				values = predefinedValues.toArray(values);
		}
		if (type.equals(LIST_AND_TEXT_TYPE)) {
			ListAndTextComposite composite =
				new ListAndTextComposite(parent,style,section,values);
			SimpleListSelectionDialog dialog =
				new SimpleListSelectionDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX,shell,values);
			composite.setNestedDialog(dialog);
			composite.setValidator(validators.get(validatorName));
			composite.setRequired(isRequired);
			return composite;
		}
		if (type.equals(LIST_TYPE)) {
			ListComposite composite =
				new ListComposite(parent,style,section,values);
			SimpleListSelectionDialog dialog =
				new SimpleListSelectionDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX,shell,values);
			composite.setNestedDialog(dialog);
			composite.setRequired(isRequired);
			composite.setValidator(validators.get(validatorName));
			return composite;
		}
		else if (type.equals(NATURAL_LANGUAGE_TYPE)) {
			NaturalLanguageComposite composite =
				new NaturalLanguageComposite(parent,style,section,values);
			SimpleListSelectionDialog dialog =
				new SimpleListSelectionDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX,shell,values);
			composite.setNestedDialog(dialog);
			composite.setRequired(isRequired);
			composite.setValidator(validators.get(validatorName));
			return composite;
		}
		else if (type.equals(COMBO_TYPE)) {
			InputComposite composite =
				new ComboComposite(parent,style,section,values,Boolean.valueOf(editable),
						defaultValue);
			composite.setValidator(validators.get(validatorName));
			composite.setRequired(isRequired);
			return composite;
		}
		else if (type.equals(CALENDAR_TYPE)) {
			ComboWithButtonComposite composite = 
				new ComboWithButtonComposite(parent,style,section,values,
						Boolean.valueOf(editable),defaultValue);
			CalendarDialog dialog = new CalendarDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX,shell);
			composite.setNestedDialog(dialog);
			composite.setValidator(validators.get(validatorName));
			composite.setRequired(isRequired);
			return composite;
		}
		else if (type.equals(LONG_TEXT_TYPE)) {
			TextAreaComposite composite = new TextAreaComposite(parent,style,section);
			composite.setRequired(isRequired);
			return composite;
		}
		else if (type.equals(LIST_PARTY_TYPE)) {
			PartyComposite composite =
				new PartyComposite(parent,style,section);
			PartyDialog dialog = new PartyDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX, shell);
			composite.setNestedDialog(dialog);
			composite.setValidator(validators.get(validatorName));
			composite.setRequired(isRequired);
			return composite;
		}
		else if (type.equals(LIST_DOMAIN_TYPE)) {
			DomainTreeComposite composite =
				new DomainTreeComposite(parent,style,section);
			//composite.setNestedDialog(new DomainDialog(section+InputDialog.DIALOG_SETTINGS_SUFFIX,shell));
			composite.setValidator(validators.get(validatorName));
			composite.setRequired(isRequired);
			return composite;
		}
		else {
		throw new RuntimeException (CompositeFactory.class.toString()+
				Messages.getString("CompositeFactory.unsupported.type") + type); //$NON-NLS-1$
		}
		
	}
	
}
