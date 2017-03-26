package com.logics.khaynay.campusapp2.events;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.logics.khaynay.campusapp2.reminder.ReminderListActivity;
import com.logics.khaynay.campusapp2.weekview.DateTimeInterpreter;
import com.logics.khaynay.campusapp2.weekview.WeekView;
import com.logics.khaynay.campusapp2.weekview.WeekViewEvent;
import com.logics.khaynay.campusapp2.R;
import com.logics.khaynay.campusapp2.database.EXAMSSQLiteHelper;
import com.logics.khaynay.campusapp2.database.HOLIDAYSQLiteHelper;
import com.logics.khaynay.campusapp2.database.HomeWorkSQLiteHelper;
import com.logics.khaynay.campusapp2.database.JCGSQLiteHelper;
import com.logics.khaynay.campusapp2.database.LecturesSQLiteHelper;
import com.logics.khaynay.campusapp2.database.Reminder;
import com.logics.khaynay.campusapp2.database.ReminderSQLiteHelper;
import com.logics.khaynay.campusapp2.database.TODOSQLiteHelper;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;



@SuppressLint("NewApi") public class ViewEventActivity extends ActionBarActivity implements WeekView.MonthChangeListener,WeekView.EventClickListener, WeekView.EventLongPressListener,OnClickListener {

    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    
    private ImageView addEvent ;
    
    final Context context = this;
    
    /**
     * DATABASE STARTS
     * 
     */
    
   // ReminderSQLiteHelper db = new ReminderSQLiteHelper(this);
    LecturesSQLiteHelper lectures_db = new LecturesSQLiteHelper(this);
    HomeWorkSQLiteHelper homework_db = new HomeWorkSQLiteHelper(this);
    TODOSQLiteHelper todo_db  = new TODOSQLiteHelper(this);                     
    EXAMSSQLiteHelper exams_db = new EXAMSSQLiteHelper(this);
    HOLIDAYSQLiteHelper holiday_db= new HOLIDAYSQLiteHelper(this);
    
    
	List<Reminder> list;
	ArrayAdapter<String> myAdapter;
	//TextView DATATextView ;
	String log,getlectures,	getexams ,getholidays ,gettodo,gethomework;
    
      /**
       * DATABASE ENDS
       * 
       */
	
	
	/***
	 * 
	 * Date Starts DIALOG
	 */
	
	  /// group box
	private RadioGroup ReminderradioGroup;
	
	private RadioButton lectures,homework,exams,holidays,todo;
	

	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private EditText eventdetails;
	private TextView startDate,endDate ,startTimeView ,endTimeView ;
	private String reminderType ,setStartTime ,setEndTime ,events ,setStartDate ,setEndDate;
	int lecturesStarthour ,lecturesEndhour; 
	//////// Time
	SeekBar startTimeSeekBar,endTimeSeekBar;
	Date eventDate;
	
	//// set Time 
	
	private int endTimeSet,startTimeSet = 0 ;
	 /**
	  * 
	  * Date Ends
	  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewevent);
        
        
   
        /***
         * 
         * DATABASE STARTS
         * 
         */
      
      
		
    	
    	// drop this database if already exists
    			//db.onUpgrade(db.getWritableDatabase(), 1, 2);

    			//db.createBook(new Reminder("Sual Reminder","2016,01,11"));
    			
    			
    			
//////////////////////
    			
    		
       
        
       
        

	  // Reading all Reminder
  Log.d("Populating : ", "Reminder Data..");
  
   
  /////////// lectures 
  List<Reminder> lectureslist = lectures_db.getAllReminder();       
  for (Reminder cn : lectureslist) {
  	
  	getlectures= 
                 cn.getReminderType() 
      	       + cn.getStartTime() + "Startime"
                 + cn.getEndTime() + "End Time" 
                 +cn.getStartDate() + "Start Date"
                 +cn.getEndDate() + "End Date"
                 +cn.getEventDetails() + "Details";
  	
  	
  	
  }
  
  
  
  /////////////// all data
  
  
  /***
   * Format the calendar Starts
   * 
   */
 
  DateFormat df = new SimpleDateFormat("yyy/M/dd");
  try {
		eventDate = df.parse("2016/01/12");
		
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  
  
    /**
     * 
     * Format the Calendar ends
     */
  
  /////////// home work
  List<Reminder> homeworks = homework_db.getAllReminder();       
  for (Reminder cn : homeworks) {
	  gethomework = "\t ( "+ cn.getReminderType()  +" ) \n"+
  			  "\t" + cn.getEventDetails()+ "\n\n"+
                
      	      "Between :" +"\n" + cn.getStartTime() +
               " - "  + cn.getEndTime() +" hrs"  +"\n\n"+
               "  "  +cn.getStartDate() + " \n"
                   +cn.getEndDate() + "\n";
  }
  /////  exams
  List<Reminder> exam = exams_db.getAllReminder();       

  for (Reminder cn : exam) {
  	
  	getexams =  "\t ( "+ cn.getReminderType()  +" ) \n"+
  			  "\t" + cn.getEventDetails()+ "\n\n"+
                
      	      "Between :" +"\n" + cn.getStartTime() +
               " - "  + cn.getEndTime() +" hrs"  +"\n\n"+
               "  "  +cn.getStartDate() + " \n"
                   +cn.getEndDate() + "\n";
  }
  
  ////////// todo 
  
  List<Reminder> todolist = todo_db.getAllReminder();    

  for (Reminder cn : todolist) {
  	gettodo=
  			 "\t ( "+ cn.getReminderType()  +" ) \n"+
  		  			  "\t" + cn.getEventDetails()+ "\n\n"+
  		                
  		      	      "Between :" +"\n" + cn.getStartTime() +
  		               " - "  + cn.getEndTime() +" hrs"  +"\n\n"+
  		               "  "  +cn.getStartDate() + " \n"
  		                   +cn.getEndDate() + "\n";
  			
  			/*"Id: "+cn.getId()+" id : " 
                 +cn.getReminderType()+ "ReminderType " 
      	       + cn.getStartTime() + "Startime"
                 + cn.getEndTime() + "End Time" 
                 +cn.getStartDate() + "Start Date"
                 +cn.getEndDate() + "End Date"
                 +cn.getEventDetails() + "Details";*/
  }
  
  
  List<Reminder> holiday = holiday_db.getAllReminder();    

  for (Reminder cn : holiday) {
  	getholidays= "\t ( "+ cn.getReminderType()  +" ) \n"+
  			  "\t" + cn.getEventDetails()+ "\n\n"+
                
      	      "Between :" +"\n" + cn.getStartTime() +
               " - "  + cn.getEndTime() +" hrs"  +"\n\n"+
               "  "  +cn.getStartDate() + " \n"
                   +cn.getEndDate() + "\n";
  	
  
  }
  
 
 
        
    //Log.d("Name: ", log);
   // DATATextView.setText(log);
	//////////////////////
    
    ///////////////////////////// ADD EVENT DIALOG STARTS
    
    
    
    //////////////////////// ADD EVENTS DIALOG ENDS

    
    
    
    			
        
        /**
         * 
         * DATABASE ENDS
         */
        
        
        //// menu custom
        
     // Asking for the default ActionBar element that our platform supports.
     		ActionBar actionBar = getActionBar();
     		 
             // Screen handling while hiding ActionBar icon.
             actionBar.setDisplayShowHomeEnabled(true);
      
             // Screen handling while hiding Actionbar title.
             actionBar.setDisplayShowTitleEnabled(true);
             actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.event_color_03)));
             
        
        

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        
        addEvent = (ImageView) findViewById(R.id.addNewEvent);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);
        
        
        
        
        
        
        
        
        
        /*****
         * 
         * 
         * DIALOG STARTS
         * 
         */
        
        ///THE DIALOG SMS
    	 
        addEvent.setOnClickListener(new OnClickListener() {

       	 
       	 
       	 
    		@Override
    		public void onClick(View arg0) {

    			// create a Dialog component
    			final Dialog dialog = new Dialog(context);
    			
    			dialog.setContentView(R.layout.add_event);
    			dialog.setTitle("  Schdule a Reminder !  ");
    			
    			
    			
    			
    			
    			

    	        /**
    	         * 
    	         * Date starts
    	         * 
    	         * 
    	         */
    	       
    	        
    	     // mDateButton = (Button) findViewById(R.id.date_button);
    	     	//	ib = (ImageButton) dialog.findViewById(R.id.cancel_button);
    	     		cal = Calendar.getInstance();
    	     		day = cal.get(Calendar.DAY_OF_MONTH);
    	     		month = cal.get(Calendar.MONTH);
    	     		year = cal.get(Calendar.YEAR);
    	     		
    	     		startDate = (TextView)dialog.findViewById(R.id.startDateTextView);
    	     		endDate = (TextView)dialog.findViewById(R.id.endDateTextView);
    	     		eventdetails = (EditText)dialog.findViewById(R.id.event_entry);
    	     		
    	     		
    	     		//// Group Radio Button
    	     		
    	     		ReminderradioGroup = (RadioGroup)dialog.findViewById(R.id.reminderTypeRadioGroup);
    	     		lectures = (RadioButton)dialog. findViewById(R.id.LecturesradioButton);
    	    		homework = (RadioButton)dialog. findViewById(R.id.HomeWorkradioButton);
    	    	    exams = (RadioButton)dialog. findViewById(R.id.ExamsradioButton);
    	    	    holidays = (RadioButton)dialog.findViewById(R.id.HolidayradioButton);
    	     	    todo = (RadioButton) dialog.findViewById(R.id.TodoradioButton);
    	    	
    	    	    
    	    	  //  button = (Button)findViewById(R.id.chooseBtn);
    	     		
    	     		
    	     		// Time
    	     		
    	     		///// Start time
    	     		
    	     		 
   				 startTimeSeekBar = (SeekBar)dialog.findViewById(R.id.StartTime);
   				startTimeView = (TextView)dialog.findViewById(R.id.StartTimeTextView);
   				
   				
   				//// ends
   				
   			 endTimeSeekBar = (SeekBar)dialog.findViewById(R.id.EndTime);
			 endTimeView = (TextView)dialog.findViewById(R.id.EndTimeTextView);
    	        
    	         /**
    	          * 
    	          * Date Ends
    	          */
    	     		
    	     		
                     ////////// Start Time 

    	     		 // Initialize time '0'.
   			     	startTimeView.setText( ""+  startTimeSeekBar.getProgress() );
    	     		 startTimeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    	     			// startTimeSet = 0;
    	     			  
    	     			  @Override
    	     			  public void onProgressChanged(SeekBar seekBar, int progresValue, boolean UserSelect) {
    	     				 startTimeSet = progresValue;
    	     				   }
    	     			
    	     			  @Override
    	     			  public void onStartTrackingTouch(SeekBar seekBar) {}
    	     			
    	     			  @Override
    	     			  public void onStopTrackingTouch(SeekBar seekBar) {
    	     				  
    	     				  /// back ends
    	     				  setStartTime = (startTimeSet+"");
    	     				  
    	     				  /// show to  text view
    	     				 startTimeView.setText("starts  on " + startTimeSet + "th hour" );
    	     				
    	     				
    	     				  }
    	     		   });
    	     		
    	     		 
    	     		   //// End Time 
    	     		 
    	     		// Initialize time '0'.
    			     	endTimeView.setText( ""+  endTimeSeekBar.getProgress() );
     	     		 endTimeSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
     	     			  //endTimeSet = 0;
     	     			  
     	     			  @Override
     	     			  public void onProgressChanged(SeekBar seekBar, int progresValue, boolean UserSelect) {
     	     				endTimeSet = progresValue;
     	     				   }
     	     			
     	     			  @Override
     	     			  public void onStartTrackingTouch(SeekBar seekBar) {}
     	     			
     	     			  @Override
     	     			  public void onStopTrackingTouch(SeekBar seekBar) {
     	     				  
     	     				  /// back ends
     	     				  setEndTime = (endTimeSet+"");
     	     				  
     	     				  /// show to  text view
     	     				 endTimeView.setText("end  on " + endTimeSet + "th hour" );
     	     			
     	     				  }
     	     		   });
     	     		
    	     		 
    	     		
    	     		   /**
    	     		    *  TIME ENDS
    	     		     *
    	     		     */
     	     		 
     	     		 
     	     		 
     	     		    /***
     	     		     * 
     	     		     * Group Box Starts
     	     		     * 
     	     		     */
     	     		 
     	     		ReminderradioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

     	   			@Override
     	   			public void onCheckedChanged(RadioGroup group, int checkedId) {
     	   				// find which notification reminder type is selected
     	   				
     	   			
     	   				if(checkedId == R.id.LecturesradioButton) {
     	   					///set Reminder type 
     	   				reminderType= "lectures";
     	   			Toast.makeText(getApplicationContext(), "choice: " + reminderType, 
	   							Toast.LENGTH_SHORT).show();
     	   					
     	   				} else if(checkedId == R.id.HomeWorkradioButton) {
     	   					
     	   				///set Reminder type 
         	   				reminderType= "home work";
         	   			Toast.makeText(getApplicationContext(), "choice: " + reminderType, 
	   							Toast.LENGTH_SHORT).show();
     	   				}
     	   				else if(checkedId == R.id.ExamsradioButton) {
 	   				
 	   				    ///set Reminder type 
     	   				reminderType= "exams";
     	   			Toast.makeText(getApplicationContext(), "choice: " + reminderType, 
   							Toast.LENGTH_SHORT).show();
     	   					
     	   				} 
     	   				else if(checkedId == R.id.HolidayradioButton) {
 	   				///set Reminder type 
     	   				reminderType= "holiday";
     	   			Toast.makeText(getApplicationContext(), "choice: " + reminderType, 
   							Toast.LENGTH_SHORT).show();
     	   					
     	   				}
     	   				else if(checkedId == R.id.TodoradioButton) {
 	   				///set Reminder type 
     	   				reminderType= "todo";
     	   			Toast.makeText(getApplicationContext(), "choice: " + reminderType, 
   							Toast.LENGTH_SHORT).show();
     	   					
     	   				} 
     	   				
     	   			}
     	   			
     	   		});
     	     		 
     	     		 
     	     		     /**
     	     		      * Group Box  Ends
     	     		      * 
     	     		      */
    			
    			
    			
    			///////////////////////// test 
    		
    			
    			
    	     		/////// Start DATE PICKER
    	     		Button startDateDialogButton = (Button) dialog.findViewById(R.id.sDate_button);
        			
    	     		startDateDialogButton.setOnClickListener(new OnClickListener() {
        				@Override
        				public void onClick(View v) {
        					//dialog.dismiss();
        					
        					showDialog(0);
        				}
        			});

        			dialog.show();
        			
        			
        			//// End Date Picker
        			
        		/////// Start DATE PICKER
    	     		Button endDateDialogButton = (Button) dialog.findViewById(R.id.endDate_button);
        			
    	     		endDateDialogButton.setOnClickListener(new OnClickListener() {
        				@Override
        				public void onClick(View v) {
        					//dialog.dismiss();
        					
        					showDialog(0);
        				}
        			});

        			dialog.show();
    	     		
    	     		
    			/// dismiss DIALOG
    			
    			Button dissmissDialogButton = (Button) dialog.findViewById(R.id.cancel_button);
    			
    			dissmissDialogButton.setOnClickListener(new OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					// dismiss dialog
    					dialog.dismiss();
    					
    					
    				}
    			});

    			dialog.show();
    			
    			
    			/// SENDING  THE SMS 
    			Button SendSMSButton = (Button) dialog.findViewById(R.id.submit_button);

    			SendSMSButton.setOnClickListener(new OnClickListener() {
    				 @Override
    				public void onClick(View view) {
    					
    					 
    					 
    					 //// Submit
    					 
    					 events = eventdetails.getText().toString();
    					 setStartDate = startDate.getText().toString();
    					setEndDate = endDate.getText().toString();	
    					//setEndTime = endTimeView.getText().toString();
 	     				//setStartTime  = startTimeView.getText().toString();
    					 
    					 if  (startDate.getText().toString().isEmpty())
    					 {
    						 Toast.makeText(ViewEventActivity.this, "Select Start date", Toast.LENGTH_SHORT).show();
    					 } else if(eventdetails.getText().toString().isEmpty())
    					 {
    						 Toast.makeText(ViewEventActivity.this, "Enter Note", Toast.LENGTH_SHORT).show();
    						 
    					 }
    					 else if(reminderType.length()==0){
    						 Toast.makeText(ViewEventActivity.this, "Select reminder type", Toast.LENGTH_SHORT).show();
    						  
    					 }
    					 else{
    						 
    						 
    						 
    						 
    						 /****
    						  * INSERT ON CONDITION STARTS
    						  * 
    						  * DATE : 12/01/2016
    						  * 
    						  */
    						 
    						 
    						 int selectedId = ReminderradioGroup.getCheckedRadioButtonId();
    					/////// convert 
								
								String StartTime = Integer.toString(startTimeSet); /// Start time
         					    String EndTime = Integer.toString(endTimeSet);		 // end time 
								
    							// find which radioButton is checked by id
    							if(selectedId ==  lectures .getId()) {
    								
    								
    								/// lectures 
    								 lectures_db.createReminder(new Reminder(
       	    							  reminderType, /// Reminder type
       	    							  StartTime,  /// Start time
       	    							  EndTime, // End Time
       	    							  setStartDate, /// start date
       	    							  setEndDate,  // end date
       	    							 events
       	    							 
       	    							 ));
    								
    							} else if(selectedId == homework.getId()) {

    								/// home work
    								
    								homework_db.createReminder(new Reminder(
      	    							  reminderType, /// Reminder type
      	    							  StartTime,  /// Start time
     	    							  EndTime, // End Time
      	    							  setStartDate, /// start date
      	    							  setEndDate,  // end date
      	    							 events
      	    							 
      	    							 ));
    							} 
    							
    					       else if(selectedId == exams.getId()) {
							
    						 // exams 
    						 
    					    	   
    					    	   exams_db.createReminder(new Reminder(
             							  reminderType, /// Reminder type
             							 StartTime,  /// Start time
    	    							  EndTime, // End Time
             							  setStartDate, /// start date
             							  setEndDate,  // end date
             							 events
             							 
             							 ));
						      	}
    							
    					       else if(selectedId == holidays.getId()){
    					    	   
    					    	   
    					    	   //// holiday
    					    	   
    					    	   holiday_db.createReminder(new Reminder(
             							  reminderType, /// Reminder type
             							 StartTime,  /// Start time
    	    							  EndTime, // End Time
             							  setStartDate, /// start date
             							  setEndDate,  // end date
             							 events
             							 
             							 ));
      							 
    					       }
    				             else if(selectedId == todo.getId()) {
    					 
    					   // todo 

                               	  todo_db.createReminder(new Reminder(
               							  reminderType, /// Reminder type
               							  StartTime,  /// Start time
   	    							      EndTime, // End Time
               							  setStartDate, /// start date
               							  setEndDate,  // end date
               							 events
               							 
               							 ));
						    }
    			
    						 //////////////////////////
    						 
    						 
    						 
    						  /**
    						   * 
    						   * INSERT ON CONDITION ENDS
    						   * 
    						   */
    						 
    						 
    						 
    						 
    		/*                     db.createReminder(new Reminder(
    							  reminderType, /// Reminder type
    							  setStartTime,  /// Start time
    							  setEndTime, // End Time
    							  setStartDate, /// start date
    							  setEndDate,  // end date
    							 events
    							 
    							 ));	 // events 
    				*/	
    					
    							
    							 /*****
    					          * 
    					          * Reminder Alert Starts
    					          * 
    					          */
    					        
    					       
    					           /**
    					            * Reminder Alert ENDS
    					            * 
    					            */
    							
     					dialog.dismiss();
     					
     					 Intent addEvent = new Intent(getApplicationContext(),AddNote.class);
     	       			 
     	 			      startActivity(addEvent);
     	 			        finish();
    					  }
    				 }
    			});
    			
    			
    		}
    		
    		
    		
    		
    		
    		
    	});
        
        
          /**
           * 
           * DIALOG ENDS
           */
        
        
        
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
                
                
                //// add note 
                
            case R.id.action_add_note :
            	
            	 Intent addEvent = new Intent(getApplicationContext(),AddNote.class);
       			 
 			      startActivity(addEvent);
 			        finish();
 			        
 		  return true;
 		  
 		   ///// Add reminder 
 		  
            case R.id.action_reminder:
            	 Intent addReminder = new Intent(getApplicationContext(), ReminderListActivity.class);
       			 
			      startActivity(addReminder);
			        finish();
			        
		  return true;
 		  
 		  //// lectures 
 		  
            case R.id.action_lectures:
            	
           	 //////////////////////////////////// holiday
            	
            	
            	List<Reminder> todolist = holiday_db.getAllReminder();    

            	  for (Reminder cn : todolist) {
            		  getlectures= "Id: "+cn.getId()+" FOLK FOLK!!!!!!: " 
            	                 +cn.getReminderType()+ "ReminderType " 
            	      	       + cn.getStartTime() + "Startime"
            	                 + cn.getEndTime() + "End Time" 
            	                 +cn.getStartDate() + "Start Date"
            	                 +cn.getEndDate() + "End Date"
            	                 +cn.getEventDetails() + "FOLK FOLK FOLD"+"";
            	  
            	  
            	
            	
            	
            	//////////////////////////////////
            	 
            	        // Populate the week view with some events.
            	        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
            	        
            	        
            	        

            	        /////////////////// date format : year.month,days
            	        
            	        DateFormat cal = new SimpleDateFormat("M/dd/yyy");
            	        
            	        Date date;
						try {
							date = cal.parse(cn.getStartDate());
						
            	        Calendar startTime =  Calendar.getInstance();
            	        startTime.setTime(date)  ;        	        
            	        int hour =  Integer.valueOf(cn.getStartTime());
            	        startTime.set(Calendar.HOUR_OF_DAY, hour);
            	        startTime.set(Calendar.MINUTE, 0);
            	        startTime.set(Calendar.MONTH, -1);
            	        startTime.set(Calendar.YEAR, 2016);
            	        Calendar endTime = (Calendar) startTime.clone();
            	        endTime.add(Calendar.HOUR, 1);
            	        endTime.set(Calendar.MONTH, -1);
            	        WeekViewEvent event = new WeekViewEvent(1, getEventLectures(startTime), startTime, endTime);
            	        event.setColor(getResources().getColor(R.color.event_color_01));
            	        events.add(event);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            	     

            	        
            	    
            	    
            	    /**
            	     * 
            	     * Get the Details START
            	     * 
            	     */
            	  
            	  ///////////////////////////
            	  }  
		  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                 //Format
                SimpleDateFormat format = new SimpleDateFormat(" M/dd/yyy ", Locale.getDefault());
                
                // the week day name. 
               
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        
        
        

        /////////////////// date format : year.month,days
        
        Calendar startTime = new GregorianCalendar(2016,1,13);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth-1);
        WeekViewEvent event = new WeekViewEvent(1, getEventLectures(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        
         ////// LECTURES 1
       
        startTime = Calendar.getInstance();
        startTime.setTime(eventDate);
        startTime.set(Calendar.HOUR_OF_DAY,  lecturesStarthour );
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, lecturesEndhour);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(10, getEventLectures(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);
        
        ///TODOLIST 2

        startTime = new GregorianCalendar(2016,1,18);
        startTime.set(Calendar.HOUR_OF_DAY, 4);
        startTime.set(Calendar.MINUTE, 20);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = new GregorianCalendar(2016,1,20);
        endTime.set(Calendar.HOUR_OF_DAY, 5);
        endTime.set(Calendar.MINUTE, 0);
        event = new WeekViewEvent(10, getEventTodo(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);
          
        
        
        /// Holidays 3
        
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 2);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(2, getEventHoliday(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);

        ///// Home Work 4
        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 5);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        startTime.add(Calendar.DATE, 1);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        endTime.set(Calendar.MONTH, newMonth - 1);
        event = new WeekViewEvent(3, getEventHomeWork(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_03));
        events.add(event);

        
         /// Exams 5
        
        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 15);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(4,  getEventExams(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_04));
        events.add(event);
        
        
        ///////////////

     /*   startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_01));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.DAY_OF_MONTH, startTime.getActualMaximum(Calendar.DAY_OF_MONTH));
        startTime.set(Calendar.HOUR_OF_DAY, 15);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR_OF_DAY, 3);
        event = new WeekViewEvent(5, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.event_color_02));
        events.add(event);*/

        return events;
    }
    
    /**
     * 
     * Get the Details START
     * 
     */

    ///// all data
    private String getEventTitl(Calendar time) {
    	 if(log == null){
         	log = "test";
         }
        return String.format(log);
    }
    
    
    //// lectures
    private String getEventLectures(Calendar time) {
   	 if(getlectures == null){
   		getlectures = "lecutures ";
        }
       return String.format(getlectures);
   }
    
    //////// todo
    private String getEventTodo(Calendar time) {
    	 if(gettodo == null){
    	   		gettodo = "todo";
    	 }
       return String.format(gettodo);
   }
    
    ///// holiday 
    private String getEventHoliday(Calendar time) {
   	 if(getholidays == null){
   		getholidays = "holidays";
        }
       return String.format(getholidays);
   }
    
    /// home work 
    private String getEventHomeWork(Calendar time) {
   	 if(gethomework == null){
   		gethomework = "home work";
        }
       return String.format(gethomework);
   }
    //// exams 
    private String getEventExams(Calendar time) {
   	 if(getexams == null){
   		getexams= "exams";
        }
       return String.format(getexams);
   }
    
  //////// 
     /**
      * 
      * Get Details ends
      * 
      */
    
    
    

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(ViewEventActivity.this, event.getName(), Toast.LENGTH_SHORT).show();
        
        
     // add button listener
     	

     			

     				// get prompts.xml view
     				LayoutInflater li = LayoutInflater.from(context);
     				View promptsView = li.inflate(R.layout.prompts, null);

     				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
     						context);

     				// set prompts.xml to alertdialog builder
     				alertDialogBuilder.setView(promptsView);

     				final EditText userInput = (EditText) promptsView
     						.findViewById(R.id.editTextEventDetails);
     				userInput.setText(event.getName());

     				// set dialog message
     				alertDialogBuilder
     						.setCancelable(false)
     						.setPositiveButton("Remove",
     								new DialogInterface.OnClickListener() {
     									public void onClick(DialogInterface dialog,
     											int id) {
     										
     									// delete selected event
         									/*	lectures_db.deleteBook(selectedEvent);
         										finish();
         										*/
         										
         										dialog.cancel();
     									}
     								})
     						.setNegativeButton("Cancel",
     								new DialogInterface.OnClickListener() {
     									public void onClick(DialogInterface dialog,
     											int id) {
     										dialog.cancel();
     									}
     								});

     				// create alert dialog
     				AlertDialog alertDialog = alertDialogBuilder.create();

     				// show it
     				alertDialog.show();

     			

     		
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(ViewEventActivity.this,  event.getName(), Toast.LENGTH_SHORT).show();
    }


    /**
     * 
     * Calendar Starts
     * 
     */
    
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			
			
			/// if start  date is null * Note : Set Starts date and End date
			
			if(startDate.getText().toString().isEmpty())
			{
			
			String startsCalendarEvent= (selectedYear + "/"+ (selectedMonth + 1) +"/" + selectedDay);
			startDate.setText("starts on "+ startsCalendarEvent );
			
			}else{
			
			String endCalendarEvent= (selectedYear + "/"+ (selectedMonth + 1) +"/" + selectedDay);
				endDate.setText("ends on " + endCalendarEvent);
				
			}
		}
	};
    
     /**
      * 
      * Calendar Ends
      * 
      */
	
	
	
	
	
	   /**
	    * 
	    * TIME STARTS 
	    * 
	    */
	
	
    
    
	@Override
	public void onClick(View v) {
		// Show Date
		
		//showDialog(0);
		
	}
    
	///////////////////////REMINDER
	
	

    
 
    
   
}  

