package com.logics.khaynay.campusapp2.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.logics.khaynay.campusapp2.R;
import com.logics.khaynay.campusapp2.database.Book;
import com.logics.khaynay.campusapp2.database.JCGSQLiteHelper;



import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNote extends ListActivity implements OnItemClickListener {
	JCGSQLiteHelper db = new JCGSQLiteHelper(this);
	
	private EditText addNote;
	List<Book> list;
	ArrayAdapter<String> NotesAdapter;
	Book addNewNote;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.addnote);
		
		
		
		
		
		
		

        //// menu custom
        
     // Asking for the default ActionBar element that our platform supports.
     		ActionBar actionBar = getActionBar();
     		 
             // Screen handling while hiding ActionBar icon.
             actionBar.setDisplayShowHomeEnabled(true);
      
             // Screen handling while hiding Actionbar title.
             actionBar.setDisplayShowTitleEnabled(true);
             actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.event_color_03)));
             
		
		
		addNote = (EditText) findViewById(R.id.AddNoteEditText);
		// drop this database if already exists
		//db.onUpgrade(db.getWritableDatabase(), 1, 2);

		//db.createBook(new Book("WIUC", "wiuc"));
		
		
		// get all Sual Notes
		list = db.getAllBooks();
		List<String> listTitle = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			listTitle.add(i, list.get(i).getTitle());
			
		}
		
		
		NotesAdapter = new ArrayAdapter<String>(this, R.layout.note_row_layout, R.id.listNote, listTitle);
		getListView().setOnItemClickListener(this);
		setListAdapter(NotesAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// start BookActivity with extras the book id
		Intent intent = new Intent(this, BookActivity.class);
		intent.putExtra("book", list.get(arg2).getId());
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// get all books again, because something changed
		list = db.getAllBooks();

		List<String> listTitle = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			listTitle.add(i, list.get(i).getTitle());
		}

		NotesAdapter = new ArrayAdapter<String>(this, R.layout.note_row_layout, R.id.listNote, listTitle);
		getListView().setOnItemClickListener(this);
		setListAdapter(NotesAdapter);
	}
	
	
	//////////// all data
	
	
	
	/////////// add data 
	
	public void addData(View v) {
		  Intent i = new Intent(getApplicationContext(),BookActivity.class);
			 
		  startActivity(i);
		  finish();
	}
	
	/////////////// ADD NOTE 
	
	public void addNote(View v) {
		Toast.makeText(getApplicationContext(), "Note Added", Toast.LENGTH_SHORT).show();
	
		db.createBook(new Book(addNote.getText().toString(), "wiuc"));
		

		 Intent addEvent = new Intent(getApplicationContext(),ViewEventActivity.class);
			 
		      startActivity(addEvent);
		        finish();
		  }
	
	
}
