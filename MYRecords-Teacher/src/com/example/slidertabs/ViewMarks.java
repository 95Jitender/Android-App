package com.example.slidertabs;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewMarks extends Activity {
	String classname;
	ListView lv;
	ArrayList<String> al;
	Button btnback;
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_marks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		}
		catch(Exception e){
			Toast.makeText(ViewMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}	
		SharedPreferences spref;
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
         flag = spref.getInt("DisplayClassActivity",0);
		 lv=(ListView)findViewById(R.id.listView1);
		 btnback=(Button)findViewById(R.id.button1);
		 al= new ArrayList<String>();
		 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		 Cursor resultSet = mydatabase.rawQuery("Select * from "+classname+"_markstable",null);
		 final int cols = resultSet.getColumnCount();
		 for(int i=2;i<cols;i++)
		 {
			 al.add(resultSet.getColumnName(i));
		 }
		ViewAttendanceAdapter adapter = new ViewAttendanceAdapter(this,al);
 	    lv.setAdapter(adapter);
 	    lv.smoothScrollToPosition(adapter.getCount());
		 
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) {
					// TODO Auto-generated method stub
					Toast.makeText(ViewMarks.this,"You Clicked on : "+ al.get(pos), Toast.LENGTH_LONG).show();
					Intent i= new Intent(ViewMarks.this,ViewMarksChart.class);
					i.putExtra("classname",classname);
					i.putExtra("colname",al.get(pos));
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
				}
			});
		mydatabase.close();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_marks, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    { if(flag==0){Intent i= new Intent(ViewMarks.this,DisplayClass.class);
		i.putExtra("classname",classname);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
		}
		else if(flag==1){
			Intent i= new Intent(ViewMarks.this,DisplayClassGrid.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
		}
		    return true;
	    }
	return false;
	}
}
