package com.example.slidertabs;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ClassDetails extends Activity {
	TextView txt1,txt2,txt3,txt4,txt5;
	private GridViewAdapterAttendanceRange mAdapter,mAdapter2,mAdapter3,mAdapter4;
	GridView gv1,gv2,gv3,gv4;
	String classname,totallectures,avgattendance,subject;
	int flag,totalatt,totallect , semister ,totalstu,attendance;
	double range1,range2,range3;
	ArrayList<String> al1,al2,al3,al4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_details);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		SharedPreferences spref;
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
	    flag = spref.getInt("DisplayClassActivity",0);
		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
		}
		catch(Exception e){
			Toast.makeText(getApplicationContext(),"Error :"+e, Toast.LENGTH_LONG).show();
		}
		txt1=(TextView)findViewById(R.id.textView1);
		txt2=(TextView)findViewById(R.id.textView2); txt2.setText(" Attendance \n 100%-76%");
		txt3=(TextView)findViewById(R.id.textView3); txt3.setText(" 75%-51%");
		txt4=(TextView)findViewById(R.id.textView4); txt4.setText(" 50%-26%");
		txt5=(TextView)findViewById(R.id.textView5); txt5.setText(" 25%-0%");
		gv1= (GridView) findViewById(R.id.gridView1);
		gv2= (GridView) findViewById(R.id.gridView2);
		gv3= (GridView) findViewById(R.id.gridView3);
		gv4= (GridView) findViewById(R.id.gridView4);
		try{
			SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
			resultSet2.moveToNext();
			totallect=resultSet2.getInt(1);
		    subject=resultSet2.getString(2).trim();
		    semister=resultSet2.getInt(3);
			Cursor resultSet = mydatabase.rawQuery("Select * from " +classname ,null);
			totalstu=resultSet.getCount();
			Cursor resultSet3 = mydatabase.rawQuery("Select totalattendance from "+ classname ,null);
        	while(resultSet3.moveToNext()){
        		attendance=attendance+resultSet3.getInt(0);
            }
            float avgatt=(float)(((double)attendance)/(double)(totalstu*(double)totallect))*100;
			txt1.setText(" Class Name : "+classname+"\n Subject : "+subject+"\n Semister : "+semister+"\n Total Lectures : "+
            totallect+"\n Total Students : "+totalstu+"\n Average Attendance : "+avgatt);
			range1 = Math.round( totallect * 75.0 ) / 100.0;
			range2 = Math.round( totallect * 50.0 ) / 100.0;
			range3 = Math.round( totallect * 25.0 ) / 100.0;
			/*range1=((totallect*75)/100);
			range2=((totallect*50)/100);
			range3=((totallect*25)/100);*/
			System.out.println(range1+" "+range2+" "+range3+"\n in1");
			Cursor resultrange1 = mydatabase.rawQuery("Select * from '"+ classname+"' WHERE totalattendance >="+range1 ,null);
			al1= new ArrayList<String>();
			while(resultrange1.moveToNext()){
        		al1.add(resultrange1.getString(0)+"\n"+resultrange1.getString(1));
        		System.out.println(resultrange1.getString(0)+" "+resultrange1.getString(1));
            }
	        // prepared arraylist and passed it to the Adapter class
			if(al1.size()==0) {al1.add("No\nStudent");}
	        mAdapter = new GridViewAdapterAttendanceRange(this,al1);
	        //gv1 = (GridView) findViewById(R.id.gridView1);
	        gv1.setAdapter(mAdapter);
			System.out.println("out1");
			Cursor resultrange2 = mydatabase.rawQuery("Select * from '"+ classname+"' WHERE totalattendance < '"+range1+"' AND totalattendance >= '"+range2+"'",null);
			al2= new ArrayList<String>();
			while(resultrange2.moveToNext()){
        		al2.add(resultrange2.getString(0)+"\n"+resultrange2.getString(1));
        		System.out.println(resultrange2.getString(0)+" "+resultrange2.getString(1));
            }
			if(al2.size()==0) {al2.add("No\nStudent");}
	        mAdapter2 = new GridViewAdapterAttendanceRange(this,al2);
	        //gv1 = (GridView) findViewById(R.id.gridView1);
	        gv2.setAdapter(mAdapter2);
			System.out.println("out2");
			Cursor resultrange3 = mydatabase.rawQuery("Select * from '"+ classname+"' WHERE totalattendance < "+range2+" AND totalattendance >="+range3 ,null);
			al3= new ArrayList<String>();
			while(resultrange3.moveToNext()){
        		al3.add(resultrange3.getString(0)+"\n"+resultrange3.getString(1));
        		System.out.println(resultrange3.getString(0)+" "+resultrange3.getString(1));
            }
			if(al3.size()==0) {al3.add("No\nStudent");}
	        mAdapter3 = new GridViewAdapterAttendanceRange(this,al3);
	        //gv1 = (GridView) findViewById(R.id.gridView1);
	        gv3.setAdapter(mAdapter3);
			System.out.println("out3");
			Cursor resultrange4 = mydatabase.rawQuery("Select * from '"+ classname+"' WHERE totalattendance <"+range3 ,null);
			al4= new ArrayList<String>();
			while(resultrange4.moveToNext()){
        		al4.add(resultrange4.getString(0)+"\n"+resultrange4.getString(1));
        		System.out.println(resultrange4.getString(0)+" "+resultrange4.getString(1));
            } 
			if(al4.size()==0) {al4.add("No\nStudent");}
	        mAdapter4 = new GridViewAdapterAttendanceRange(this,al4);
	        //gv1 = (GridView) findViewById(R.id.gridView1);
	        gv4.setAdapter(mAdapter4);
			System.out.println("out4");
			mydatabase.close();
			}
			catch(Exception e) {
			Toast.makeText(ClassDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class_details, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { if(flag==0){Intent i= new Intent(ClassDetails.this,DisplayClass.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			}
			else if(flag==1){
				Intent i= new Intent(ClassDetails.this,DisplayClassGrid.class);
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
