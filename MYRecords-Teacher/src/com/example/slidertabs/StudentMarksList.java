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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class StudentMarksList extends Activity {
	String classname;
	ListView lv;
	ArrayList<String> al,al2;
	TextView txt;
	Button btnback;
	int flag;
	String sub,studentname,rollno,comefrom;
	float perc;
	int lect,totalstudents,attendance,sem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_student_marks_list);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
			studentname=b.getString("studentname");
			rollno=b.getString("rollno");
/*			perc=b.getFloat("perc");
			sem=b.getInt("semister");
			sub=b.getString("subject");
			lect=b.getInt("totallect");*/
			comefrom=b.getString("comefrom");
		}
		catch(Exception e){
			Toast.makeText(StudentMarksList.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}     
		SharedPreferences spref;
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
         flag = spref.getInt("DisplayClassActivity",0);
		 lv=(ListView)findViewById(R.id.listView1);
		 txt=(TextView)findViewById(R.id.textView1);
		 txt.setText(studentname+" "+classname);
		 btnback=(Button)findViewById(R.id.button1);
		 al= new ArrayList<String>();
		 al2= new ArrayList<String>();
		 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		 Cursor resultSet = mydatabase.rawQuery("Select * from "+classname+"_markstable where rollno='"+rollno+"'",null);
		 final int cols = resultSet.getColumnCount();
		 resultSet.moveToNext();
		 try{for(int i=2;i<cols;i++)
		 {
			 al.add(resultSet.getColumnName(i)+"  - Marks :  "+resultSet.getString(i));
			 al2.add(resultSet.getColumnName(i));
			 //+" - "+resultSet.getString(i)
		 }
		 }catch(Exception e){System.out.print("Error - "+e);}
		ViewAttendanceAdapter adapter = new ViewAttendanceAdapter(this,al);
 	    lv.setAdapter(adapter);
 	    lv.smoothScrollToPosition(adapter.getCount());
		lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) {
					// TODO Auto-generated method stub
					Toast.makeText(StudentMarksList.this,"You Clicked on : "+ al2.get(pos), Toast.LENGTH_LONG).show();
					Intent i= new Intent(StudentMarksList.this,EditMarks.class);
					i.putExtra("classname",classname);
					i.putExtra("colname",al2.get(pos));
					i.putExtra("rollno", rollno);
					i.putExtra("comefrom", comefrom);
					i.putExtra("studentname", studentname);
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
		getMenuInflater().inflate(R.menu.student_marks_list, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(comefrom.equals("details"))
			{try{  Intent i = new Intent(StudentMarksList.this,EachStudent.class);
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
			catch(Exception e){Toast.makeText(StudentMarksList.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
			else if(comefrom.equals("search")) 
			{
				try{  Intent i = new Intent(StudentMarksList.this,EachStudent.class);
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
				catch(Exception e){Toast.makeText(StudentMarksList.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
				return true;
	    }
	return false;
	}
}
