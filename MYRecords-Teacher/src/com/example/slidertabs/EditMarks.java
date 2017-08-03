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
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMarks extends Activity {
	int flag;
	String classname,studentname,colname,rollno,comefrom,marks,newmarks,subject,highestmarks;
	int totalstudents,semister,batchyear,maxmarks,highestmarks2,marks1;
	double totalmarks,highestmarks1;
	float perc;
	TextView txt1,txt2;
	EditText edt1;
	Button btn1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_marks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
			studentname=b.getString("studentname");
			rollno=b.getString("rollno");
			comefrom=b.getString("comefrom");
			colname=b.getString("colname");
		}
		catch(Exception e){
			Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		txt1=(TextView)findViewById(R.id.textView1);
		txt2=(TextView)findViewById(R.id.textView2);
		edt1=(EditText)findViewById(R.id.editText1);
		btn1=(Button)findViewById(R.id.button1);
		BarChart chart = (BarChart)findViewById(R.id.chart);
		txt1.setText(studentname+" - "+classname);
		txt2.setText(colname+" Marks - "+studentname);
        try{ 
       	SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
       	Cursor resultSet = mydatabase.rawQuery("Select "+colname+" from "+ classname+"_markstable where rollno='"+rollno+"'" ,null);
       	Cursor resultSet2 = mydatabase.rawQuery("Select * from marksinfo where testname='"+colname+"'" ,null);
       	resultSet2.moveToNext() ;
       	subject=resultSet2.getString(1);
       	semister=resultSet2.getInt(2);
     	batchyear=resultSet2.getInt(3);
     	maxmarks=resultSet2.getInt(5);
       	resultSet.moveToNext() ;
       	marks = resultSet.getString(0);
        marks1=(int)Double.parseDouble(marks);
        mydatabase.close();
        edt1.setText(marks);
        }
        catch(Exception e){
        	System.out.println("Error"+e.toString());
        }
        edt1.setOnKeyListener(new View.OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
	            	InputMethodManager inputManager = (InputMethodManager)
	            	getSystemService(Context.INPUT_METHOD_SERVICE);
	            	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
	            	newmarks=edt1.getText().toString();
					SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
					String strSQL2 = "UPDATE "+classname+"_markstable SET "+colname+" ='"+newmarks+"' WHERE rollno='"+rollno+"'";
					mydatabase.execSQL(strSQL2);
					Toast.makeText(EditMarks.this,"Marks Updated ",Toast.LENGTH_LONG).show();
					mydatabase.close();
					if(comefrom.equals("details"))
					{try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
					catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
					}
					else if(comefrom.equals("search")) 
					{
						try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
						catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
					}
	                return true;
	            }
	            return false;
	        }
	    });
        btn1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager inputManager = (InputMethodManager)
		        getSystemService(Context.INPUT_METHOD_SERVICE);
		      	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	            InputMethodManager.HIDE_NOT_ALWAYS);
				newmarks=edt1.getText().toString();
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				String strSQL2 = "UPDATE "+classname+"_markstable SET "+colname+" ='"+newmarks+"' WHERE rollno='"+rollno+"'";
				mydatabase.execSQL(strSQL2);
				Toast.makeText(EditMarks.this,"Marks Updated ",Toast.LENGTH_LONG).show();
				mydatabase.close();
				
				if(comefrom.equals("details"))
				{try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
				catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
				}
				else if(comefrom.equals("search")) 
				{
					try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
					catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
				}
				
			}
		});
        try{ 
        	SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
        	Cursor resultSet4 = mydatabase.rawQuery("Select * from "+ classname+"_markstable " ,null);
        	Cursor resultSet = mydatabase.rawQuery("Select "+colname+" from "+ classname+"_markstable " ,null);
        	Cursor resultSet3 = mydatabase.rawQuery("Select MAX("+colname+") from "+ classname+"_markstable " ,null);
        	resultSet3.moveToNext();
        	highestmarks=resultSet3.getString(0);
        	highestmarks1=Double.parseDouble(highestmarks);
        	highestmarks2= (int)highestmarks1;
            totalstudents = resultSet4.getCount();
        	while(resultSet.moveToNext()){
        		totalmarks=totalmarks+(int)Double.parseDouble(resultSet.getString(0));
            }
            mydatabase.close();
         }
         catch(Exception e){
         	System.out.println("Error"+e.toString());
         }
         //System.out.println(classname +" "+sub+" "+lect+" "+sem+" "+ totalstudents+" "+attendance);
        System.out.println("Total Marks : "+(int)totalmarks);
        System.out.println("Total Students : "+totalstudents+" Max Marks : "+maxmarks+" H : "+totalstudents*maxmarks);
        int avgatt=(((int)totalmarks)/(totalstudents));
         System.out.println(avgatt);
    
 		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
 		entries.add(new BarEntry(marks1, 0));
 		entries.add(new BarEntry(avgatt, 1));
 		entries.add(new BarEntry(highestmarks2, 2));
 		entries.add(new BarEntry(maxmarks, 3));
 		
 		BarDataSet dataset = new BarDataSet(entries, "Student,Average,Highest,Maximum Marks");
 		ArrayList<String> labels = new ArrayList<String>();
 		labels.add("Student"); 
 		labels.add("Avgerage"); 
 		labels.add("Highest"); 
 		labels.add("Maximum"); 
 		BarData data = new BarData(labels, dataset);
 		chart.setData(data);
 		chart.setDescription(studentname+" - Stats");
 		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
 	    chart.animateXY(2000, 2000);
 	    chart.invalidate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_marks, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(comefrom.equals("details"))
			{try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
			catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
			else if(comefrom.equals("search")) 
			{
				try{  Intent i = new Intent(EditMarks.this,StudentMarksList.class);
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
				catch(Exception e){Toast.makeText(EditMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
				return true;
	    }
	return false;
	}
}
