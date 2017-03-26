package com.logics.khaynay.campusapp2.database;

public class Reminder {

	private int id;
	private String remindertype;
	
	private String starttime;
	private String endtime;
	private String startdate;
	private String enddate;
	private String eventdetails;
	
	
	public Reminder(){}
	
	public Reminder(String remindertype,String starttime ,String endtime,String startdate, String enddate ,String eventdetails) {
		super();
		this.remindertype = remindertype;
		this.starttime =starttime ; 
		this.endtime = endtime;
		this.startdate = startdate;
		this.enddate = enddate;
		this.eventdetails= eventdetails;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
///// reminder type
	public String getReminderType() {
		return remindertype;
	}
	public void setReminderType(String ReminderType) {
		this.remindertype = ReminderType;
	}
	
	///////////// Start time
	
	
	public String getStartTime() {
		return starttime ;
	}
	public void setStartTime(String StartTime) {
		this.starttime = StartTime;
	}
	
	///// endtime 
	

	public String getEndTime() {
		return endtime ;
	}
	public void setEndTime(String EndTime) {
		this.endtime = EndTime;
	}
	
	
	/// start date 
	public String getStartDate() {
		return startdate ;
	}
	public void setStartDate(String StartDate) {
		this.startdate = StartDate;
	}
	
	
	
	/// end date
		public String getEndDate() {
			return enddate ;
		}
		public void setEndDate(String EndDate) {
			this.enddate= EndDate;
		}
	
	/// event details 
		 public String getEventDetails() {
		 return eventdetails;
	 }
	 public void setEventDetails(String EventDetails) {
	    this.eventdetails= EventDetails;
	  }	
		
////////////////
	 
	@Override
	public String toString() {
		return "Reminder [id=" + id + ", title=" + remindertype + ", starttime =" + starttime  + ", endtime=" + endtime + ", startdate=" + startdate
				 + ", enddate =" + enddate + ", eventdetails=" + eventdetails+ "]";
	}
	
	
	
}
