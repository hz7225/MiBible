package com.example.mibible;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class Gridview extends Activity {
	GridView gridView;
	String book;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        
        Intent intent = getIntent();
        int index = intent.getShortExtra("INDEX", (short) 0);
        book = intent.getStringExtra("BOOK");
        int number = intent.getShortExtra("NUMBER", (short) 0);
        
        Log.d("HZ", "Intent: " + "index " + index + " book " + book + " number " + String.valueOf(number));

        TextView tv = (TextView) findViewById(R.id.chapter);
        tv.setText(book);
        gridView = (GridView) findViewById(R.id.gridView1);
              
        List<Integer> list = new ArrayList<Integer>();
        for (int i=0; i<number;i++) {
        	list.add(i+1);
        }
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
						android.R.layout.simple_list_item_1, list);		
 
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
			   Toast.makeText(getApplicationContext(), book + " " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
			   
			}
		});

		
    }
		

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// Inflate the menu; this adds items to the action bar if it is present.
    	//getMenuInflater().inflate(R.menu.mi_bible, menu);
    	return true;
    }
}
