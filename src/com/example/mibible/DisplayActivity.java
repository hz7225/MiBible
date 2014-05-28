package com.example.mibible;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends Activity {
	String TAG = "DisplayActivity";
	
	DataBaseHelper BibleDB = new DataBaseHelper(this);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        
        Intent intent = getIntent();
        int book = intent.getIntExtra("BOOK", 1);
        int chapter = intent.getIntExtra("CHAPTER", 1);
        int verse = intent.getIntExtra("VERSE", 1);
        
        
        //Get the whole chapter of a book from database
        List<String> sl = BibleDB.getChapter(book, chapter);
        
        //Build the String and display it in TextView
        TextView tv = (TextView) findViewById(R.id.display_text);
        StringBuilder str = new StringBuilder();
		for (int i =0; i<sl.size(); i++) {
			Log.d(TAG, "["+String.valueOf(i+1)+"]" + sl.get(i));
			str.append("["+String.valueOf(i+1)+"]" + sl.get(i));
		}
        tv.setText(str);        
    
	}

}
