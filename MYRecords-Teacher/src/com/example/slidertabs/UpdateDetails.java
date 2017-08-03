package com.example.slidertabs;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateDetails extends Activity {

	EditText edt1,edt2,edt3;
	Button btn1,btn2;
	String classname,rollno,studentname,contact,comefrom;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_update_details);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
			try{
				Bundle b= getIntent().getExtras();
				classname=b.getString("classname");
				rollno=b.getString("rollno");
				studentname=b.getString("studentname");
				contact=b.getString("contact");
				comefrom=b.getString("comefrom");
			}
			catch(Exception e){
				Toast.makeText(UpdateDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
			
			edt1=(EditText)findViewById(R.id.editText1);
			edt2=(EditText)findViewById(R.id.editText2);
			edt3=(EditText)findViewById(R.id.editText3);
			btn1=(Button)findViewById(R.id.button1);
			btn2=(Button)findViewById(R.id.button2);
			edt1.setText(studentname);
			edt3.setText(rollno);
			edt2.setText(contact);
			edt2.setOnKeyListener(new View.OnKeyListener() {
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		            	InputMethodManager inputManager = (InputMethodManager)
		            	getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
		                return true;
		            }
		            return false;
		        }
		    });
			btn1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				try {
				String name=edt1.getText().toString();
				String contact=edt2.getText().toString();
				if(name.equals("")||name.equals(null)||contact.equals("")||contact.equals(null))
				{
				Toast.makeText(UpdateDetails.this,"Fill All Detils.",Toast.LENGTH_LONG).show();
				}
				else {
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				String strSQL = "UPDATE "+classname+" SET name ='"+name+"', contact='"+contact+"'  WHERE rollno='"+rollno+"'";
				String strSQL2 = "UPDATE "+classname+"_markstable SET name ='"+name+"'  WHERE rollno='"+rollno+"'";
				mydatabase.execSQL(strSQL);
				mydatabase.execSQL(strSQL2);
				Toast.makeText(UpdateDetails.this,"Details Updated ",Toast.LENGTH_LONG).show();
				mydatabase.close();
				Intent i=new Intent(UpdateDetails.this,MainActivity.class);
				startActivity(i);
				finish();
				}
				}
				catch(Exception e){
					Toast.makeText(UpdateDetails.this,"Error : "+e,Toast.LENGTH_LONG).show();
					}
					
				}
			});
			
			btn2.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(UpdateDetails.this,EachStudent.class);
					i.putExtra("classname",classname);
					i.putExtra("studentname",studentname);
					i.putExtra("rollno",rollno);
					i.putExtra("comefrom","details");
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				     finish();
					
				}
			});
		}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_details, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	if(comefrom.equals("details"))
			{
			try{  Intent i = new Intent(UpdateDetails.this,EachStudent.class);
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
			catch(Exception e){Toast.makeText(UpdateDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();}
	    }
		else if(comefrom.equals("search")) 
		{
			try{  Intent i = new Intent(UpdateDetails.this,EachStudent.class);
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
			catch(Exception e){Toast.makeText(UpdateDetails.this,"Error :"+e, Toast.LENGTH_LONG).show();}
		}
				return true;
	    }
	return false;
	}

}
