package com.example.slidertabs;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StudentStats extends Activity {
String classname,sub,studentname,rollno,comefrom;
float perc;
int lect,totalstudents,attendance,sem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_stats);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
	    BarChart chart = (BarChart)findViewById(R.id.chart);
		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
			studentname=b.getString("studentname");
			rollno=b.getString("rollno");
			perc=b.getFloat("perc");
			sem=b.getInt("semister");
			sub=b.getString("subject");
			lect=b.getInt("totallect");
			comefrom=b.getString("comefrom");
			System.out.println(classname+" "+studentname+" "+rollno+" "+perc+" "+sem+" "+sub+" "+lect);
		}
		catch(Exception e){
			Toast.makeText(StudentStats.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}        
         try{ 
        	SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
        	Cursor resultSet = mydatabase.rawQuery("Select totalattendance from "+ classname ,null);
            totalstudents = resultSet.getCount();
        	while(resultSet.moveToNext()){
        		attendance=attendance+resultSet.getInt(0);
            }
            mydatabase.close();
         }
         catch(Exception e){
         	System.out.println("Error"+e.toString());
         }
         System.out.println(classname +" "+sub+" "+lect+" "+sem+" "+ totalstudents+" "+attendance);
         float avgatt=(float)(((double)attendance)/(double)(totalstudents*(double)lect))*100;
    
 		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
 		entries.add(new BarEntry(perc, 0));
 		entries.add(new BarEntry(avgatt, 1));
 		entries.add(new BarEntry(lect, 2));
 		
 		BarDataSet dataset = new BarDataSet(entries, "Student Avg Attendance , Class Avg Attendance , Lectures");
 		ArrayList<String> labels = new ArrayList<String>();
 		labels.add("Student Attendance"); 
 		labels.add("Avg. Attendance"); 
 		labels.add("Lectures"); 
 		BarData data = new BarData(labels, dataset);
 		chart.setData(data);
 		chart.setDescription(studentname+" - Stats");
 		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
 	    chart.animateXY(2000, 2000);
 	    chart.invalidate();
		}
		catch(Exception e){
			System.out.println("Error : "+e.toString());
		}
  }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.student_stats, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(comefrom.equals("details"))
			{try{  Intent i = new Intent(StudentStats.this,EachStudent.class);
			i.putExtra("classname",classname);
			i.putExtra("comefrom",comefrom);
			i.putExtra("studentname",studentname);
			i.putExtra("rollno",rollno);
			i.putExtra("comefrom","details");
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		     finish();
			}
			catch(Exception e){Toast.makeText(StudentStats.this,"Error :"+e, Toast.LENGTH_LONG).show();}
	    }
		else if(comefrom.equals("search")) 
		{
			try{  Intent i = new Intent(StudentStats.this,EachStudent.class);
			i.putExtra("classname",classname);
			i.putExtra("comefrom",comefrom);
			i.putExtra("studentname",studentname);
			i.putExtra("rollno",rollno);
			i.putExtra("comefrom","search");
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		     finish();
			}
			catch(Exception e){Toast.makeText(StudentStats.this,"Error :"+e, Toast.LENGTH_LONG).show();}
		}
				return true;
	    }
	return false;
	}

}
