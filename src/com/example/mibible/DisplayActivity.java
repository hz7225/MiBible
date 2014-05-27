package com.example.mibible;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class DisplayActivity extends Activity {
	
	DataBaseHelper BibleDB = new DataBaseHelper(this);
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        
        Intent intent = getIntent();
        int book = intent.getIntExtra("BOOK", 1);
        int chapter = intent.getIntExtra("CHAPTER", 1);
        int verse = intent.getIntExtra("VERSE", 1);
        
        TextView tv = (TextView) findViewById(R.id.display_text);
        //tv.setText("Hellow the whole world!");
        
        //String str = BibleDB.getVerse(book, chapter, verse);
        
        List<String> sl = BibleDB.getChapter(book, chapter);
        StringBuilder str = new StringBuilder();
		for (int i =0; i<sl.size(); i++) {
		//	Log.d(TAG, "["+String.valueOf(i+1)+"]" + sl.get(i));
			str.append("["+String.valueOf(i+1)+"]" + sl.get(i));
		}
        
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(str);
        
        
    
	}

}
