/**
 * 
 */
package org.neontoolkit.oyster2.client.gui.dialogs.content;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.neontoolkit.oyster2.client.gui.GUIConstants;
import org.vafada.swtcalendar.SWTCalendar;
import org.vafada.swtcalendar.SWTCalendarEvent;
import org.vafada.swtcalendar.SWTCalendarListener;

/**
 * @author David Muñoz
 *
 */
public class CalendarComposite extends Composite {

	
	
	private SimpleDateFormat dateFormat = null;
	
	private SWTCalendar swtCalendar = null;
	
	private Text dateText = null;
	
	public CalendarComposite (Composite parent, int style,
			Date date) {
		super(parent,style);
		setLayout(new FormLayout());
		
		FormData fd = null;
		
		
		
		swtCalendar = new SWTCalendar(this);
		fd = new FormData();
		fd.top = new FormAttachment(0,5);
		fd.left = new FormAttachment(0,5);
		fd.right = new FormAttachment(100,-5);
		swtCalendar.setLayoutData(fd);
		
		dateFormat = new SimpleDateFormat(GUIConstants.DATE_FORMAT);
		
		
		makeListeners();
		
	}

	private void makeListeners() {
		SWTCalendarListener listener = new SWTCalendarListener() {
			public void dateChanged(SWTCalendarEvent event) {
				dateText.setText(dateFormat.format(event.getCalendar().getTime()));
			}
		};
		swtCalendar.addSWTCalendarListener(listener);
		
		
		ModifyListener textListener = new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				Text text = (Text)e.getSource();
				try {
					Date date = dateFormat.parse(text.getText());
					Calendar calendar = Calendar.getInstance();
			        calendar.setTime(date);
			        swtCalendar.setCalendar(calendar);
					
				} catch (ParseException e1) {
					// Not valid date, ignore
				}
				
			}
			
		};
		dateText.addModifyListener(textListener);
	}
	
	
	
	
	/**
	 * @return the dateText
	 */
	public final Text getDateText() {
		return dateText;
	}

	/**
	 * @param dateText the dateText to set
	 */
	public final void setDateText(Text dateText) {
		this.dateText = dateText;
	}

	public final String getDateString() {
		return dateText.getText();
	}
	public final void setDateString(String newDate) {
		dateText.setText(newDate);
	}
	
}
