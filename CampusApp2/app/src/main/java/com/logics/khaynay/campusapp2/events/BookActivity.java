package com.logics.khaynay.campusapp2.events;

import com.logics.khaynay.campusapp2.R;
import com.logics.khaynay.campusapp2.database.Book;
import com.logics.khaynay.campusapp2.database.JCGSQLiteHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends Activity {
	TextView NoteTitle;
	TextView Note;
	Book selectedBook;
	JCGSQLiteHelper db;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_note);

		Note = (TextView) findViewById(R.id.title);
		NoteTitle = (TextView) findViewById(R.id.titlename);
		
		
		

        //// menu custom
        
     // Asking for the default ActionBar element that our platform supports.
     		ActionBar actionBar = getActionBar();
     		 
             // Screen handling while hiding ActionBar icon.
             actionBar.setDisplayShowHomeEnabled(true);
      
             // Screen handling while hiding Actionbar title.
             actionBar.setDisplayShowTitleEnabled(true);
             actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
             

		// get the intent that we have passed from AndroidDatabaseExample
		Intent intent = getIntent();
		int id = intent.getIntExtra("book", -1);

		// open the database of the application context
		db = new JCGSQLiteHelper(getApplicationContext());

		// read the book with "id" from the database
		selectedBook = db.readBook(id);

		initializeViews();
	}

	public void initializeViews() {
		Note.setText(selectedBook.getTitle());
		NoteTitle.setText(selectedBook.getAuthor());
	}
	
	
	
	

	

	public void update(View v) {
		Toast.makeText(getApplicationContext(), "Note is updated.", Toast.LENGTH_SHORT).show();
		selectedBook.setTitle(((EditText) findViewById(R.id.titleEdit)).getText().toString());
		//selectedBook.setAuthor("WIUC");

		// update database with changes
		db.updateBook(selectedBook);
		finish();
	}

	public void delete(View v) {
		Toast.makeText(getApplicationContext(), "Note is deleted.", Toast.LENGTH_SHORT).show();

		// delete selected note
		db.deleteBook(selectedBook);
		finish();
	}
}
