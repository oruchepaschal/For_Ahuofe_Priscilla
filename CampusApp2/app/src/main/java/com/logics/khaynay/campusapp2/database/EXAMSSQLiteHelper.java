package com.logics.khaynay.campusapp2.database;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EXAMSSQLiteHelper extends SQLiteOpenHelper {

	// database version
	private static final int database_VERSION = 1;
	// database name
	private static final String database_NAME = "ReminderDB";
	private static final String table_BOOKS = "reminder";
	private static final String Reminder_ID = "id";
	private static final String Reminder_Type = "remindertype";
	private static final String  Start_Time = "starttime";
	private static final String End_Time = "endtime";
	private static final String Start_Date = "StartDate";
	private static final String End_Date = "EndDate";
	private static final String Event_Details = "EventDetails";
	

	private static final String[] COLUMNS = { Reminder_ID, Reminder_Type,Start_Time,End_Time,Start_Date,End_Date,Event_Details}; // book_TITLE, book_AUTHOR 

	public EXAMSSQLiteHelper(Context context) {
		super(context, database_NAME, null, database_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table    /books/
		String CREATE_BOOK_TABLE = "CREATE TABLE reminder ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "remindertype TEXT, " + "starttime TEXT, " + "endtime TEXT, " + "startdate TEXT, " + "enddate TEXT, "+ "eventdetails TEXT, "+ "title TEXT, " + "author TEXT )";
		db.execSQL(CREATE_BOOK_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// drop books table if already exists
		db.execSQL("DROP TABLE IF EXISTS reminder");
		this.onCreate(db);
	}

	public void createReminder(Reminder book) {
		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// make values to be inserted
		ContentValues values = new ContentValues();
		
		
		//////////////////
		//values.put( Reminder_ID,  book.getTitle());
		values.put (Reminder_Type, book.getReminderType());
		values.put( Start_Time, book.getStartTime());
		values.put( End_Time , book.getEndTime());
		values.put (Start_Date , book.getStartDate());
		values.put( End_Date , book.getEndDate());
		values.put( Event_Details, book.getEventDetails());
		///////////////////

		// insert 
		db.insert(table_BOOKS, null, values);

		// close database transaction
		db.close();
	}

	public Reminder readBook(int id) {
		// get reference of the DB database
		SQLiteDatabase db = this.getReadableDatabase();

		// get book query
		Cursor cursor = db.query(table_BOOKS, // a. table
				COLUMNS, " id = ?", new String[] { String.valueOf(id) }, null, null, null, null);

		// if results !=null, parse the first one
		if (cursor != null)
			cursor.moveToFirst();

		Reminder book = new Reminder();
		book.setId(Integer.parseInt(cursor.getString(0)));
		book.setReminderType(cursor.getString(1));
		book.setStartTime(cursor.getString(2));
		book.setEndTime(cursor.getString(3));
		book.setStartDate(cursor.getString(4));
		book.setEndDate(cursor.getString(5));
		book.setEventDetails(cursor.getString(6));
		

		return book;
	}
	
	
	
	/***
	 * 
	 * Get THE DETAILS 
	 * 
	 */
	
public List<Reminder> getAllReminder() {
		
		
		
		List<Reminder> books = new LinkedList<Reminder>();

		// select book query
		String query = "SELECT  * FROM " + table_BOOKS;

		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
         if (cursor!=null){
        	 
		// parse all results
		Reminder book = null;
		if (cursor.moveToFirst()) {
			do {
				book = new Reminder();
				book.setId(Integer.parseInt(cursor.getString(0)));
				book.setReminderType(cursor.getString(1));
				book.setStartTime(cursor.getString(2));
				book.setEndTime(cursor.getString(3));
				book.setStartDate(cursor.getString(4));
				book.setEndDate(cursor.getString(5));
				book.setEventDetails(cursor.getString(6));
				
				
				// Add book to books
				books.add(book);
			} while (cursor.moveToNext());
		}}
		return books;
         
	}
	
	


	public int updateBook(Reminder book) {

		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// make values to be inserted
		ContentValues values = new ContentValues();
		
		// update
		int i = db.update(table_BOOKS, values, Reminder_ID + " = ?", new String[] { String.valueOf(book.getId()) });

		db.close();
		return i;
	}

	// Deleting single book
	public void deleteBook(Reminder book) {

		// get reference of the BookDB database
		SQLiteDatabase db = this.getWritableDatabase();

		// delete book
		db.delete(table_BOOKS, Reminder_ID+ " = ?", new String[] { String.valueOf(book.getId()) });
		db.close();
	}
}
