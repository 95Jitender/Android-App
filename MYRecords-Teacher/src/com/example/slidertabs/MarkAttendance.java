package com.example.slidertabs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MarkAttendance extends Activity {
	ListView lv;
	sqlquery sq[];
	static String classname;
	static String time;
	Button btnback,btnsubmit;
	TextView txt;
	CheckBox checkBox;
	ArrayList<String> selectedstrings;
	int flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_attendance);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		}
		catch(Exception e){
			Toast.makeText(MarkAttendance.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		SharedPreferences spref;
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
        flag = spref.getInt("DisplayClassActivity",0);
		 btnback=(Button)findViewById(R.id.button1);
		 btnsubmit=(Button)findViewById(R.id.button2);
		 lv = (ListView) findViewById(R.id.listView1);
		 txt=(TextView)findViewById(R.id.textView1);
		 txt.setText(classname);
	     
		 final SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			Cursor resultSet = mydatabase.rawQuery("Select * from "+classname+" Order by rollno ",null);
			int row=resultSet.getCount();
			sq = new sqlquery[row];
			int i=0;
			while(resultSet.moveToNext())
			{
			sq[i]=new sqlquery(resultSet.getString(0),resultSet.getString(1));
			i++;
			}
		AttendanceAdapter adapter = new AttendanceAdapter(this,sq,row);
    	 lv.setAdapter(adapter);
    	 lv.smoothScrollToPosition(adapter.getCount());
    	selectedstrings=AttendanceAdapter.getSelectedString();
    	 
    	 btnsubmit.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				try{
 		int x = selectedstrings.size(); int a=0;
 		String y="";
 		while(x!=0){
 			y=y+" "+selectedstrings.get(a);
 			a++;
 			x--;
 		}
 		
 		Toast.makeText(MarkAttendance.this,"Selected Items : " +y, Toast.LENGTH_LONG).show();
 		y="";
 				}
 				catch(Exception e){}
 				
 		 String weekDay="",tms="";
				  Calendar c = Calendar.getInstance();
				     int day = c.get(Calendar.DATE);
				     int mon = c.get(Calendar.MONTH);
				     int year = c.get(Calendar.YEAR);
				     int min = c.get(Calendar.MINUTE);
				     int hr = c.get(Calendar.HOUR);
				     int tm = c.get(Calendar.HOUR_OF_DAY);
				     if(tm>=12&&tm<=24){tms="pm";}
				     else{tms="am";}
				 	SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
					Date d = new Date();
					String dayOfTheWeek = sdf.format(d);
				   time=dayOfTheWeek+"_"+day+"_"+(mon+1)+"_"+year+"_"+hr+"_"+min+"_"+tms;
				   
					 
					try{
						 mydatabase.execSQL( "ALTER TABLE "+ classname +" ADD COLUMN "+ time +" TEXT DEFAULT 'ABSENT' ; " );
						 int x = selectedstrings.size();
						 int a=0;
						 String rollno="";
						 while(x!=0)
						 {
					    rollno=selectedstrings.get(a);
						SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
						Cursor resultSet = mydatabase.rawQuery("Select * from "+ classname +" WHERE rollno ='" +rollno +"'",null);
						resultSet.moveToNext();
						int totalattendance=0;
						totalattendance=resultSet.getInt(4);
						totalattendance=totalattendance+1;
						String strSQL = "UPDATE "+classname+" SET "+ time +" ='PRESENT'  WHERE rollno='"+rollno+"'";
						mydatabase.execSQL(strSQL);
						String strSQL2 = "UPDATE "+classname+" SET totalattendance ='"+totalattendance+"'  WHERE rollno='"+rollno+"'";
						mydatabase.execSQL(strSQL2);
						totalattendance=0;
						 x--;
						 a++;
						 }
						 
					    Cursor resultSet = mydatabase.rawQuery("Select * from classesnameslist WHERE classname='"+classname+"'",null);
						resultSet.moveToNext();
						int totallect=0;
						totallect=resultSet.getInt(1);
						totallect=totallect+1;
					    String strSQL1 = "UPDATE classesnameslist SET totalLect ='"+totallect+"'  WHERE classname='"+classname+"'";
						mydatabase.execSQL(strSQL1);
						totallect=0;
						mydatabase.close();
						Toast.makeText(MarkAttendance.this,"Attendance Updated", Toast.LENGTH_LONG).show();
						selectedstrings.clear();
						
					 }
					 catch(Exception e){
						 Toast.makeText(MarkAttendance.this,"Unable to Update Attendance ", Toast.LENGTH_LONG).show();
					 }

 	}
    	 
    	 });
    	 
	
    	btnback.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				    mydatabase.close();
    					selectedstrings.clear();
    					if(flag==0){Intent i= new Intent(MarkAttendance.this,DisplayClass.class);
  						i.putExtra("classname",classname);
  						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  						startActivity(i);
  						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
  						finish();
  						}
  						else if(flag==1){
  							Intent i= new Intent(MarkAttendance.this,DisplayClassGrid.class);
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
		getMenuInflater().inflate(R.menu.mark_attendance, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	 
				selectedstrings.clear();
				if(flag==0){Intent i= new Intent(MarkAttendance.this,DisplayClass.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				}
				else if(flag==1){
					Intent i= new Intent(MarkAttendance.this,DisplayClassGrid.class);
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
