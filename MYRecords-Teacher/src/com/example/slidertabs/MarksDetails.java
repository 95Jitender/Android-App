package com.example.slidertabs;

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
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MarksDetails extends Activity {
String classname,testname1,maxmarks1,colname;
TextView txt;
EditText testname,maxmarks;
Button btn;
int flag,totalcol,testflag=0;;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_marks_details);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));

		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
		    SharedPreferences spref;
		    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		    flag = spref.getInt("DisplayClassActivity",0);
		}
		catch(Exception e){
			Toast.makeText(MarksDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		txt=(TextView)findViewById(R.id.textView1);
		txt.setText(classname.toUpperCase());
		testname=(EditText)findViewById(R.id.editText1);
		maxmarks=(EditText)findViewById(R.id.editText2);
		btn=(Button)findViewById(R.id.button1);
		testname.setOnFocusChangeListener(new View.OnFocusChangeListener(){
			 int x=1;
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus){
		        if(x==1){testname.setHint("Test Name"); x=2;}
		        else{testname.setHint("");}
		        	}
		else
			testname.setHint("Test Name");
		      }
		    });
		maxmarks.setOnFocusChangeListener(new View.OnFocusChangeListener(){
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus)
		{ maxmarks.setHint("");}
		else
			maxmarks.setHint("Maximum Marks");
		      }
		    });
		maxmarks.setOnKeyListener(new View.OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	     if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) { 
	    	 testname1=testname.getText().toString();
				maxmarks1=maxmarks.getText().toString();
				if(testname.equals(null)||testname.equals("")||maxmarks1.equals(null)||maxmarks1.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Input All Details", Toast.LENGTH_SHORT).show();
				}
				else{
					InputMethodManager inputManager = (InputMethodManager)
		                      getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
					// SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
					 //mydatabase.execSQL( "ALTER TABLE "+ classname +"_markstable ADD COLUMN "+ testname1 +" TEXT DEFAULT '0' ; " );
		 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			// mydatabase.execSQL( "ALTER TABLE "+ classname +"_markstable ADD COLUMN "+ testname1 +" TEXT DEFAULT '0' ; " );
Cursor resultSet = mydatabase.rawQuery("Select * from " +classname+"_markstable " ,null);
totalcol=resultSet.getColumnCount();
for(int i=0;i<totalcol;i++)
{
	if(resultSet.getColumnName(i).equalsIgnoreCase(testname1)){
		testflag=1;
		break;
	}

}
			 if(testflag==0)
			 {Intent i = new Intent(MarksDetails.this,EnterMarks.class);
			 i.putExtra("classname",classname);
			 i.putExtra("colname", testname1);
			 i.putExtra("maxmarks", maxmarks1);
			 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			 startActivity(i);
			 overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			 finish();
			 }
			 else{
				 Toast.makeText(MarksDetails.this,"Test Marks Already Stored", Toast.LENGTH_LONG).show(); 
				 testflag=0;
			 }
				}
	    	 
	                return true;
	            }
	            return false;
	        }
	    });
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{testname1=testname.getText().toString();
				maxmarks1=maxmarks.getText().toString();
				if(testname.equals(null)||testname.equals("")||maxmarks1.equals(null)||maxmarks1.equals(""))
				{
					Toast.makeText(getApplicationContext(),"Input All Details", Toast.LENGTH_SHORT).show();
				}
				else{
					InputMethodManager inputManager = (InputMethodManager)
		                      getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
		 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
					// mydatabase.execSQL( "ALTER TABLE "+ classname +"_markstable ADD COLUMN "+ testname1 +" TEXT DEFAULT '0' ; " );
		Cursor resultSet = mydatabase.rawQuery("Select * from " +classname+"_markstable " ,null);
		totalcol=resultSet.getColumnCount();
		for(int i=0;i<totalcol;i++)
		{
			if(resultSet.getColumnName(i).equals(testname1)){
				testflag=1;
				break;
			}
		
		}
					 if(testflag==0)
					 {Intent i = new Intent(MarksDetails.this,EnterMarks.class);
					 i.putExtra("classname",classname);
					 i.putExtra("colname", testname1);
					 i.putExtra("maxmarks", maxmarks1);
					 i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					 startActivity(i);
					 overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					 finish();
					 }
					 else{
						 Toast.makeText(MarksDetails.this,"Test Marks Already Stored", Toast.LENGTH_LONG).show(); 
						 testflag=0;
					 }
				}
				
				}catch(Exception e)
				{
					System.out.print("Error : "+e);
				}
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.marks_details, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { if(flag==0){Intent i= new Intent(MarksDetails.this,DisplayClass.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			}
			else if(flag==1){
				Intent i= new Intent(MarksDetails.this,DisplayClassGrid.class);
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
