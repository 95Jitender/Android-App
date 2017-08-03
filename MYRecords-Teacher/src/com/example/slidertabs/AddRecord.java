package com.example.slidertabs;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecord extends Activity {
	TextView txt,txt2;
	Button btnadd,btnback,btnimport;
	EditText edtname,edtroll,edtcontact;
	String classname,noofstu;
	String total="0";
	int x=0,flag;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_add_record);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
			try{
				Bundle b= getIntent().getExtras();
			    classname=b.getString("classname");
			}
			catch(Exception e){
				Toast.makeText(AddRecord.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
		    SharedPreferences spref;
		    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		    flag = spref.getInt("DisplayClassActivity",0);
			edtname=(EditText)findViewById(R.id.editText1);
			edtroll=(EditText)findViewById(R.id.editText2);
			edtcontact=(EditText)findViewById(R.id.editText3);
			btnadd=(Button)findViewById(R.id.button1);
			btnback=(Button)findViewById(R.id.button2);
			btnimport=(Button)findViewById(R.id.button3);
		
			txt=(TextView)findViewById(R.id.textView1);
			txt.setText(classname);
			edtname.setOnFocusChangeListener(new View.OnFocusChangeListener(){
				 int x=1;
			      public void onFocusChange(View v, boolean hasFocus){
			if(hasFocus){
			        if(x==1){edtname.setHint("Enter Name"); x=2;}
			        else{edtname.setHint("");}
			        	}
			else
				edtname.setHint("Enter Name");
			      }
			    });
			edtroll.setOnFocusChangeListener(new View.OnFocusChangeListener(){
			      public void onFocusChange(View v, boolean hasFocus){
			if(hasFocus)
			{ edtroll.setHint("");}
			else
				edtroll.setHint("Enter Roll No.");
			      }
			    });
			edtcontact.setOnFocusChangeListener(new View.OnFocusChangeListener(){
			      public void onFocusChange(View v, boolean hasFocus){
			if(hasFocus)
			{ edtcontact.setHint("");}
			else
				edtcontact.setHint("Enter Contact");
			      }
			    });
			
			edtcontact.setOnKeyListener(new View.OnKeyListener() {
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
						String name=edtname.getText().toString();
						String roll=edtroll.getText().toString();
						String con=edtcontact.getText().toString();
						int rolllen=roll.length();
						InputMethodManager inputManager = (InputMethodManager)
			                      getSystemService(Context.INPUT_METHOD_SERVICE); 
			
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
			                           InputMethodManager.HIDE_NOT_ALWAYS);
					    try{ 
					   if(name.equals(null) || roll.equals(null) || con.equals(null) || name.equals("") || roll.equals("") || con.equals(""))
					    {
					    Toast.makeText(AddRecord.this,"Input All Details.",Toast.LENGTH_LONG).show();
					    edtname.requestFocus();
				
					    }
					   else if(rolllen!=11){
						
						 Toast.makeText(AddRecord.this,"Enter 11 Digits Roll No.",Toast.LENGTH_LONG).show(); 
						 edtroll.requestFocus();
					   }
					   else if(con.length()!=10){
							
						 Toast.makeText(AddRecord.this,"Enter 10 Digits Contact",Toast.LENGTH_LONG).show(); 
						 edtcontact.requestFocus();
						   }
					   else{
					 
					    	SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
					        mydatabase.execSQL("INSERT INTO " + classname + " (name,rollno,contact,class,totalattendance) VALUES('"+name+"','"+roll+"','"+con+"','"+classname+"','0');");
					        mydatabase.execSQL("INSERT INTO " + classname + "_markstable (name,rollno) VALUES('"+name+"','"+roll+"');");
					        Toast.makeText(AddRecord.this,"Record : Name : "+name+"\nRollNo : "+roll+"\nContact : "+con+"\nAdded Successfully To Class : "+classname,Toast.LENGTH_LONG).show();
					        edtname.setText("");
					        edtroll.setText("");
					        edtcontact.setText("");
					        edtname.requestFocus();
					        mydatabase.close();
					        }
					    }
					    catch(Exception e){Toast.makeText(AddRecord.this,"Unable to Update Records "+e,Toast.LENGTH_LONG).show();}
		                return true;
		            }
		            return false;
		        }
		    });
			btnadd.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String name=edtname.getText().toString();
					String roll=edtroll.getText().toString();
					String con=edtcontact.getText().toString();
					int rolllen=roll.length();
					InputMethodManager inputManager = (InputMethodManager)
		                      getSystemService(Context.INPUT_METHOD_SERVICE); 
		
		inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
				    try{
				   if(name.equals(null) || roll.equals(null) || con.equals(null) || name.equals("") || roll.equals("") || con.equals(""))
				    {
				    Toast.makeText(AddRecord.this,"Input All Details.",Toast.LENGTH_LONG).show();
				    edtname.requestFocus();
				
				    }
				   else if(rolllen!=11){
					
					 Toast.makeText(AddRecord.this,"Enter Valid 11 Digits Roll No.",Toast.LENGTH_LONG).show(); 
					 edtroll.requestFocus();
				   }
				   else if(con.length()!=10){
						
						 Toast.makeText(AddRecord.this,"Enter 10 Digits Contact",Toast.LENGTH_LONG).show(); 
						 edtcontact.requestFocus();
					   }
				    else{
				    	SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				        mydatabase.execSQL("INSERT INTO " + classname + " (name,rollno,contact,class,totalattendance) VALUES('"+name+"','"+roll+"','"+con+"','"+classname+"','0');");
				        mydatabase.execSQL("INSERT INTO " + classname + "_markstable (name,rollno) VALUES('"+name+"','"+roll+"');");
				        Toast.makeText(AddRecord.this,"Record : Name : "+name+"\nRollNo : "+roll+"\nContact : "+con+"\nAdded Successfully To Class : "+classname,Toast.LENGTH_LONG).show();
				        edtname.setText("");
				        edtroll.setText("");
				        edtcontact.setText("");
				        edtname.requestFocus();
				        mydatabase.close();
				        }
				    }
				    catch(Exception e){Toast.makeText(AddRecord.this,"Unable to Update Records "+e,Toast.LENGTH_LONG).show();}
				}
			});
			
		
		
		
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

	         
			  if(flag==0){Intent i= new Intent(AddRecord.this,DisplayClass.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				}
				else if(flag==1){
					Intent i= new Intent(AddRecord.this,DisplayClassGrid.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
				    overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				    finish();
				}
				}
			});
		/*btnimport.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(AddRecord.this,ExceltoDb.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
			}
		});*/
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_record, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{	Intent i;
		switch(item.getItemId())
		{
		case R.id.item1 : 
			i = new Intent(AddRecord.this,ExceltoDb.class );
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			break;
		}
		return super.onOptionsItemSelected(item);
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { if(flag==0){Intent i= new Intent(AddRecord.this,DisplayClass.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			}
			else if(flag==1){
				Intent i= new Intent(AddRecord.this,DisplayClassGrid.class);
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
