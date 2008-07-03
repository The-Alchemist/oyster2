package org.neontoolkit.changelogging.core.flogic;

import java.util.Calendar;

public class ChangeLogEntry {
	private String changeType;
	private String module;
	private String[] args;
	private String timeStamp;
	private String pred;
	
	public ChangeLogEntry(String type, String pred, String module, String[] args, String timeStamp){
		this.changeType = type;
		this.pred = pred;
		this.module = module;
		this.args = args;
		if(timeStamp == null || timeStamp.equals(""))
			this.timeStamp = Calendar.getInstance().getTime().toString();
		else
			this.timeStamp = timeStamp;
	}
	
	public String getType(){
		return changeType;
	}
	
	public String getModule(){
		return module;
	}
	
	public String getPredicate(){
		return pred;
	}
	
	public String[] getArguments(){
		return args;
	}
	
	public String getTimeStamp(){
		return timeStamp;
	}
}
