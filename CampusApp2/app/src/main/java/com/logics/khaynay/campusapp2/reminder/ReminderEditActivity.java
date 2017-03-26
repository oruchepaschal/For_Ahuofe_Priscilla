
package com.logics.khaynay.campusapp2.reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.logics.khaynay.campusapp2.R;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ReminderEditActivity extends Activity {

	// 
	// Dialog Constants
	//
	private static final int DATE_PICKER_DIALOG = 0;
	private static final int TIME_PICKER_DIALOG = 1;
	
	// 
	// Date Format 
	//
	private static final String DATE_FORMAT = "yyyy-MM-dd"; 
	private static final String TIME_FORMAT = "kk:mm";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	
	
	private RadioGroup  SelectOptionsradioGroup;
	private RadioButton lectures, others; 
	private  LinearLayout viewlectureLinearLayout;
	private EditText mTitleText, mlecturenameText, mBodyText;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mConfirmButton;
    private Long mRowId;
    private RemindersDbAdapter mDbHelper;
    private Calendar mCalendar; 
    String title,body,dateForButton ,timeForButton,lecturename;

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mDbHelper = new RemindersDbAdapter(this);
        
        setContentView(R.layout.reminder_edit);
        
        
        
        
     // Asking for the default ActionBar element that our platform supports.
 		ActionBar actionBar = getActionBar();
 		 
         // Screen handling while hiding ActionBar icon.
         actionBar.setDisplayShowHomeEnabled(true);
  
         // Screen handling while hiding Actionbar title.
         actionBar.setDisplayShowTitleEnabled(true);
         actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
         
         
         //// options layout 
         
         viewlectureLinearLayout = (LinearLayout) findViewById(R.id.lectureLinearLayout);
         viewlectureLinearLayout.setVisibility(View.GONE);
     ///// radio button
         lectures = (RadioButton) findViewById(R.id.lecturesRadioButton);
 		 others= (RadioButton) findViewById(R.id.otherRadioButton);
         /// Group Radio BTN
         SelectOptionsradioGroup = (RadioGroup) findViewById(R.id.OptionsRadioGroup);
        
        mCalendar = Calendar.getInstance(); 
        mTitleText = (EditText) findViewById(R.id.title);
        mlecturenameText = (EditText) findViewById(R.id.lecturename);
        mBodyText = (EditText) findViewById(R.id.details);
        mDateButton = (Button) findViewById(R.id.reminder_date);
        mTimeButton = (Button) findViewById(R.id.reminder_time);
      
        mConfirmButton = (Button) findViewById(R.id.confirm);
       
        mRowId = savedInstanceState != null ? savedInstanceState.getLong(RemindersDbAdapter.KEY_ROWID) 
                							: null;
      
        registerButtonListenersAndSetDefaultText();
        
        
        
        
        
        
        //////////////// Group Radio Button
        
        SelectOptionsradioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.lecturesRadioButton) {
					
					viewlectureLinearLayout.setVisibility(View.VISIBLE);
				} else if(checkedId == R.id.otherRadioButton) {
				
					viewlectureLinearLayout.setVisibility(View.GONE);
				}
			}
			
		});
        
        
        
    }

	private void setRowIdFromIntent() {
		if (mRowId == null) {
			Bundle extras = getIntent().getExtras();            
			mRowId = extras != null ? extras.getLong(RemindersDbAdapter.KEY_ROWID) 
									: null;
			
		}
	}
    
    @Override
    protected void onPause() {
        super.onPause();
        mDbHelper.close(); 
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mDbHelper.open(); 
    	setRowIdFromIntent();
		populateFields();
    }
    
    @Override
    protected Dialog onCreateDialog(int id) {
    	switch(id) {
    		case DATE_PICKER_DIALOG: 
    			return showDatePicker();
    		case TIME_PICKER_DIALOG: 
    			return showTimePicker(); 
    	}
    	return super.onCreateDialog(id);
    }
    
 	private DatePickerDialog showDatePicker() {
		
		
		DatePickerDialog datePicker = new DatePickerDialog(ReminderEditActivity.this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				mCalendar.set(Calendar.YEAR, year);
				mCalendar.set(Calendar.MONTH, monthOfYear);
				mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateDateButtonText(); 
			}
		}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)); 
		return datePicker; 
	}

   private TimePickerDialog showTimePicker() {
		
    	TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute); 
				updateTimeButtonText(); 
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true); 
		
    	return timePicker; 
	}
 	
	private void registerButtonListenersAndSetDefaultText() {

		mDateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(DATE_PICKER_DIALOG);  
			}
		}); 
		
		
		mTimeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialog(TIME_PICKER_DIALOG); 
			}
		}); 
		
		mConfirmButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		saveState(); 
        		setResult(RESULT_OK);
        	    Toast.makeText(ReminderEditActivity.this, getString(R.string.task_saved_message), Toast.LENGTH_SHORT).show();
        	    finish(); 
        	}
          
        });
		
		  updateDateButtonText(); 
	      updateTimeButtonText();
	}
   
    private void populateFields()  {
    	
  	
    	
    	// Only populate the text boxes and change the calendar date
    	// if the row is not null from the database. 
        if (mRowId != null) {
            Cursor reminder = mDbHelper.fetchReminder(mRowId);
            startManagingCursor(reminder);
            mTitleText.setText(reminder.getString(
    	            reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_TITLE)));
            mBodyText.setText(reminder.getString(
                    reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_BODY)));
            

            // Get the date from the database and format it for our use. 
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = null;
			try {
				String dateString = reminder.getString(reminder.getColumnIndexOrThrow(RemindersDbAdapter.KEY_DATE_TIME)); 
				date = dateTimeFormat.parse(dateString);
	            mCalendar.setTime(date); 
			} catch (ParseException e) {
				Log.e("ReminderEditActivity", e.getMessage(), e); 
			} 
        } else {
        	// This is a new task - add defaults from preferences if set. 
        	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); 
        	String defaultTitleKey = getString(R.string.pref_task_title_key); 
        	String defaultTimeKey = getString(R.string.pref_default_time_from_now_key); 
        	
        	String defaultTitle = prefs.getString(defaultTitleKey, null);
        	String defaultTime = prefs.getString(defaultTimeKey, null); 
        	
        	if(defaultTitle != null)
        		mTitleText.setText(defaultTitle); 
        	
        	if(defaultTime != null)
        		mCalendar.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
        	
        }
        
        updateDateButtonText(); 
        updateTimeButtonText(); 
        	
    }

	private void updateTimeButtonText() {
		// Set the time button text based upon the value from the database
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT); 
        timeForButton = timeFormat.format(mCalendar.getTime()); 
        mTimeButton.setText(timeForButton);
	}

	private void updateDateButtonText() {
		// Set the date button text based upon the value from the database 
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT); 
        dateForButton = dateFormat.format(mCalendar.getTime()); 
        mDateButton.setText(dateForButton);
	}
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(RemindersDbAdapter.KEY_ROWID, mRowId);
    }
    

    
    private void saveState() {
    	
    	/////// Check for options selected
    	int selectedId = SelectOptionsradioGroup .getCheckedRadioButtonId();
		
		// find which radioButton is checked by id
		if(selectedId == lectures.getId ()) {
			
			
			
		    title = mTitleText.getText().toString() +"\t\t"+"lecuture : "+ mlecturenameText.getText().toString() +"\t\t"+dateForButton+" \t" +timeForButton;
		    body = mBodyText.getText().toString();
		    
		   
		    
			
		} else if(selectedId == others.getId()) {
			
			
			  title = mTitleText.getText().toString() +"\t\t"+dateForButton+" \t" +timeForButton;
		       body = mBodyText.getText().toString();

		} 

    	
    	///////////////////
    	
       
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT); 
    	String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());

        if (mRowId == null) {
        	
        	long id = mDbHelper.createReminder(title, body, reminderDateTime);
            if (id > 0) {
                mRowId = id;
            }
        } else {
            mDbHelper.updateReminder(mRowId, title, body, reminderDateTime);
        }
       
        new ReminderManager(this).setReminder(mRowId, mCalendar); 
    }
    
}
