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
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewStudentDetails extends Activity {
	ListView lv;
	sqlquery sq[];
	static String classname;
	static String time;
	Button btnback;
	TextView txt;
	CheckBox checkBox;
	ArrayList<String> al,al2;
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_student_details);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));

		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		}
		catch(Exception e){
			Toast.makeText(ViewStudentDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		SharedPreferences spref;
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
        flag = spref.getInt("DisplayClassActivity",0);
		 btnback=(Button)findViewById(R.id.button1);
		 lv = (ListView) findViewById(R.id.listView1);
		 txt=(TextView)findViewById(R.id.textView1);
		 txt.setText(classname);
		 al= new ArrayList<String>();
		 al2= new ArrayList<String>();
	     
		 final SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		  	 
		  	
		   
			Cursor resultSet = mydatabase.rawQuery("Select * from "+classname+" Order by rollno ",null);
			int row=resultSet.getCount();
			sq = new sqlquery[row];
			int i=0;
			while(resultSet.moveToNext())
			{
			sq[i]=new sqlquery(resultSet.getString(0),resultSet.getString(1));
			al.add(resultSet.getString(1));
			al2.add(resultSet.getString(0));
			i++;
			}
		
			ViewStudentAdapter adapter = new ViewStudentAdapter(this,sq,row);
    	    lv.setAdapter(adapter);
    	    lv.smoothScrollToPosition(adapter.getCount());
    	    lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) {
					// TODO Auto-generated method stub
					Toast.makeText(ViewStudentDetails.this,"You Clicked on : "+al2.get(pos), Toast.LENGTH_LONG).show();
					Intent i= new Intent(ViewStudentDetails.this,EachStudent.class);
					i.putExtra("studentname",al2.get(pos));
					i.putExtra("rollno",al.get(pos));
					i.putExtra("classname",classname);
					i.putExtra("comefrom","details");
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
				}
			});
    	    btnback.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				  if(flag==0){Intent i= new Intent(ViewStudentDetails.this,DisplayClass.class);
						i.putExtra("classname",classname);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						finish();
						}
						else if(flag==1){
							Intent i= new Intent(ViewStudentDetails.this,DisplayClassGrid.class);
							i.putExtra("classname",classname);
							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						finish();
						}
    			}
    		});
    	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_student_details2, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {  if(flag==0){Intent i= new Intent(ViewStudentDetails.this,DisplayClass.class);
		i.putExtra("classname",classname);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
		}
		else if(flag==1){
			Intent i= new Intent(ViewStudentDetails.this,DisplayClassGrid.class);
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
