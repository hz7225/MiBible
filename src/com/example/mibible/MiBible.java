package com.example.mibible;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MiBible extends Activity implements OnItemClickListener {
	String TAG = "MiBible";
	
	ListView listViewOT;
	ListView listViewNT;
	ListView listViewChapter;
	ListView listViewVerse;
	
	SharedPreferences prefs;
	int prefsBook;
    int prefsChapter;
    int prefsVerse;
    
	MyDataAdapter adapterOT;
	MyDataAdapter adapterNT;
	MyDataAdapter adapterChapter;
	MyDataAdapter adapterVerse;
	
	List<String> chapterList = new ArrayList<String>();
	List<String> verseList = new ArrayList<String>();
	
	DataBaseHelper BibleDB = new DataBaseHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Log.d(TAG, "onCreate()");
		setContentView(R.layout.activity_main);
		
	    //Create  Android SQLite database file from downloaded file
	    DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    try {
	    	myDbHelper.createDataBase();
	    } catch (IOException ioe) {
	    	throw new Error("Unable to create database");
	    } 
				
	    // Get book names resources from XML
	    String[] ot = getResources().getStringArray(R.array.old_testament);
	    String[] nt = getResources().getStringArray(R.array.new_testament);
	    List<String> OTList = new ArrayList<String>();
	    List<String> NTList = new ArrayList<String>();	   
	    for (int i=0; i<ot.length; i++) {
	    	String book = ot[i].substring(0, ot[i].indexOf(","));
	    	OTList.add(book);
	    }
	    for (int i=0; i<nt.length; i++) {
	    	String book = nt[i].substring(0, nt[i].indexOf(","));
	    	NTList.add(book);
	    }
	    
	    // Get Listviews and set onClick listeners
		listViewOT = (ListView) findViewById(R.id.listViewOT);
		listViewNT = (ListView) findViewById(R.id.listViewNT);		
		listViewChapter = (ListView) findViewById(R.id.listViewChapter);
		listViewVerse = (ListView) findViewById(R.id.listViewVerse);		
		//Log.d(TAG, "listViewOT = " + listViewOT);	
	    listViewOT.setOnItemClickListener(this);
	    listViewNT.setOnItemClickListener(this);
	    listViewChapter.setOnItemClickListener(this);
	    listViewVerse.setOnItemClickListener(this);
	    
		// Instantiate Listview adapters
		adapterOT = new MyDataAdapter(this, OTList);
		adapterNT = new MyDataAdapter(this, NTList);	
	    adapterChapter = new MyDataAdapter(this, chapterList);
		adapterVerse = new MyDataAdapter(this, verseList);
		
		// Get saved preferences
	    prefs  = getSharedPreferences("BiblePreferences", MODE_PRIVATE);		    
	    prefsBook = prefs.getInt("BOOK", 1);
	    prefsChapter = prefs.getInt("CHAPTER", 1);
	    prefsVerse = prefs.getInt("VERSE", 1);
	    if (prefsBook < 40) {  //OT book
	    	adapterOT.selected_item = prefsBook - 1;
	    }
	    else {  //NT book
	    	adapterNT.selected_item = prefsBook - 40;
	    }
	    
	    populateListOfChaptersAndVerse();
	    
	    listViewOT.setAdapter(adapterOT);
		listViewNT.setAdapter(adapterNT);				
		listViewChapter.setAdapter(adapterChapter);
		listViewVerse.setAdapter(adapterVerse);	
		
		
		//Log.d(TAG, "adapterOT = " + adapterOT);
		//Log.d(TAG, "adapterNT = " + adapterNT);
		//Log.d(TAG, "adapterChapter = " + adapterChapter);
		//Log.d(TAG, "adapterVerse = " + adapterVerse);    
	}
	
	protected void onResume() {
		super.onResume();	
		Log.d(TAG, "onResume()");
	}
	
	private void populateListOfChaptersAndVerse() {	
		// Find the number of chapters from Bible database
		int c;
		int v;
		//DataBaseHelper BibleDB = new DataBaseHelper(this);
		Log.d(TAG, "prefsBook = " + String.valueOf(prefsBook) + " prefsChapter = " + String.valueOf(prefsChapter) + " prefsVerse = " + String.valueOf(prefsVerse));
		try {
			c = BibleDB.getNumOfChapters(prefsBook);
			v = BibleDB.getNumOfVerses(prefsBook, prefsChapter);
		} catch(SQLException sqle) {	    	
	    	throw sqle;
	    }
		
		chapterList.clear();
		for (int i = 1; i <= c; i++) {
			chapterList.add(String.valueOf(i));
		}
		verseList.clear();
		for (int i = 1; i <= v; i++) {
			verseList.add(String.valueOf(i));
		}
		
		adapterChapter.selected_item = prefsChapter - 1;
		adapterVerse.selected_item = prefsVerse - 1;
	}
	
	protected void onPause() {
		super.onPause();
		
		Log.d(TAG, "onPause()");
		Editor editor = prefs.edit();
		editor.putInt("BOOK", prefsBook);
		editor.putInt("CHAPTER", prefsChapter);
		editor.putInt("VERSE", prefsVerse);
		editor.commit();
	}	
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {			
		if (parent == (ListView) findViewById(R.id.listViewOT)) {
			adapterOT.selected_item = position; //Select OT book
			adapterNT.selected_item = -1; //Unselect NT book
			prefsBook = position + 1; //Set book preferences
			prefsChapter = 1;  //Reset chapter preferences to 1
			prefsVerse = 1;  //Reset verse preferences to 1
			Log.d(TAG, "position="+String.valueOf(position) + ", id=" + String.valueOf(id) + 
					   " prefsBook = " + String.valueOf(prefsBook));
		}
		if (parent == (ListView) findViewById(R.id.listViewNT)) {
			adapterOT.selected_item = -1;  //Unselect OT book
			adapterNT.selected_item = position;  //Select NT book
			prefsBook = position + 40;  //Set book preferences
			prefsChapter = 1; //Reset chapter preferences to 1
			prefsVerse = 1;  //Reset verse preferences to 1
			Log.d(TAG, "position="+String.valueOf(position) + ", id=" + String.valueOf(id) + 
					   " prefsBook = " + String.valueOf(prefsBook));
		}		
				
		if (parent == (ListView) findViewById(R.id.listViewChapter)) {
			adapterChapter.selected_item = position;
			prefsChapter = position + 1; 
			prefsVerse = 1; //Reset verse preference to 1
			Log.d(TAG, "position="+String.valueOf(position) + ", id=" + String.valueOf(id) + 
					   " prefsChapter = " + String.valueOf(prefsChapter));
		}
		
		if (parent == (ListView) findViewById(R.id.listViewVerse)) {
			adapterVerse.selected_item = position;
			prefsVerse = position + 1;
			Log.d(TAG, "position="+String.valueOf(position) + ", id=" + String.valueOf(id) + 
					   " prefsVerse = " + String.valueOf(prefsVerse));
			
			//String str = BibleDB.getVerse(prefsBook, prefsChapter, prefsVerse);
			//Log.d(TAG, str);
			
			//List<String> sl = BibleDB.getChapter(prefsBook, prefsChapter);
			//for (int i =0; i<sl.size(); i++) {
			//	Log.d(TAG, "["+String.valueOf(i+1)+"]" + sl.get(i));
			//}
			launchDisplayActivity(prefsBook, prefsChapter, prefsVerse);
		}
		
		populateListOfChaptersAndVerse();
		
		adapterOT.notifyDataSetChanged();
		adapterNT.notifyDataSetChanged();		
		adapterChapter.notifyDataSetChanged();
		adapterVerse.notifyDataSetChanged();
    }
	
	private void launchDisplayActivity(int book, int chapter, int verse) {
		Intent intent = new Intent(this, DisplayActivity.class);
		intent.putExtra("BOOK", book);
		intent.putExtra("CHAPTER", chapter);
		intent.putExtra("VERSE", verse);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mi_bible, menu);
		return true;
	}

}
