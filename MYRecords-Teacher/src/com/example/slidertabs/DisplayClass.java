package com.example.slidertabs;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.example.slidertabs.AttendanceChart.CSVToExcelConverter;
import com.example.slidertabs.AttendanceChart.ExportDatabaseCSVTask;
import com.example.slidertabs.Practice.uploadcontent;
import com.example.slidertabs.Practice.uploadinfo;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayClass extends Activity {

	TextView txt;
	Button btnclsdt,btnstu,btnadd,btndel,btnback,btnattendance,displayatt,upload,entermarks,viewmarks,markschart;
	String classname;
	String name,roll,cls,con,teachername,subject ,updatedate ,teachercontact;
	int batchyear,totalcol,n=5;
	String info="",colnames="",classname1,serverurl;
	int attendance,totallect , flag , semister;
	SharedPreferences spref;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_display_class);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));		 
			try{Bundle b= getIntent().getExtras();
			classname=b.getString("classname");}
			catch(Exception e){
				Toast.makeText(DisplayClass.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
			spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		    flag = spref.getInt(classname,0);
		    serverurl=spref.getString("ServerURL","val").trim();
			btnclsdt=(Button)findViewById(R.id.button1);
			btnstu=(Button)findViewById(R.id.button2);
			btnadd=(Button)findViewById(R.id.button3);
			btndel=(Button)findViewById(R.id.button4);
			btnback=(Button)findViewById(R.id.button5);
			btnattendance=(Button)findViewById(R.id.button6);
			displayatt=(Button)findViewById(R.id.button7);
			upload=(Button)findViewById(R.id.button8);
			entermarks=(Button)findViewById(R.id.button9);
			viewmarks=(Button)findViewById(R.id.button10);
			markschart=(Button)findViewById(R.id.button11);
			txt=(TextView)findViewById(R.id.textView1);
			txt.setText(classname);
			 Animation a = AnimationUtils.loadAnimation(this,R.anim.slide_in_top_slower);
			 a.reset();   
			 btnclsdt.startAnimation(a);
			 btnstu.startAnimation(a);
			 btnadd.startAnimation(a);
			 btndel.startAnimation(a);
			 btnback.startAnimation(a);
			 btnattendance.startAnimation(a);
			 displayatt.startAnimation(a);
			 upload.startAnimation(a);
			 entermarks.startAnimation(a);
			 viewmarks.startAnimation(a);
			 markschart.startAnimation(a);
			//txt.setText(classname);
			btnadd.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						Intent i = new Intent(DisplayClass.this,AddRecord.class);
						i.putExtra("classname",classname);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						finish();
				}
			});
			
			btnclsdt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClass.this,ClassChart.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
				}
			});
			btnstu.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClass.this,ViewStudentDetails.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
				}
			});
		btndel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 new AlertDialog.Builder(DisplayClass.this)
					    .setTitle("Delete...")
					    .setMessage("Are you sure you want to delete this Class?")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // continue with delete
					        	 try{
									 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
								     mydatabase.execSQL("DROP TABLE IF EXISTS "+ classname);
								     mydatabase.execSQL("DROP TABLE IF EXISTS "+ classname+"_markstable");
								     mydatabase.execSQL("DELETE FROM classesnameslist WHERE classname ='"+classname+"'");
								     mydatabase.execSQL("DELETE FROM marksinfo WHERE classname ='"+classname+"'");
								     mydatabase.close();
								     Toast.makeText(DisplayClass.this,"Class Deleted", Toast.LENGTH_LONG).show();
								     Intent i = new Intent(DisplayClass.this,MainActivity.class);
									 startActivity(i);
								     finish();
								 }
								 catch(Exception e){
									 Toast.makeText(DisplayClass.this,"Unable To Delete Class", Toast.LENGTH_LONG).show();
									 }
					        }
					     })
					    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // do nothing
					        }
					     })
					    .setIcon(android.R.drawable.ic_dialog_alert)
					     .show();
					
				}
			});
		
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClass.this,MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
			}
		});
		
		btnattendance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClass.this,MarkAttendance.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
			}
		});
	displayatt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClass.this,ViewAttendance.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					finish();
			}
		});

	upload.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			 new AlertDialog.Builder(DisplayClass.this)
			    .setTitle("Upload...")
			    .setMessage("Do You Want To Upload Details To Server.?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	if(isNetworkAvailable()==true){
			        		try{ teachercontact=spref.getString("contact","val").trim(); 
			    			 teachername=spref.getString("name","val").trim();
			    			 serverurl=spref.getString("ServerURL","val").trim();}
			    			catch(Exception e){System.out.println("Error in shared preferences");}
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
			    				updatedate=dayOfTheWeek+"-"+day+"/"+(mon+1)+"/"+year+"-"+hr+":"+min+""+tms;
			    			new uploadcontent().execute(null, null , null);
			    			
			    			}
			    			else { Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show(); }
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
		}
	});
	entermarks.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				Intent i = new Intent(DisplayClass.this,MarksDetails.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
		}
	});
	viewmarks.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				Intent i = new Intent(DisplayClass.this,ViewMarks.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
		}
	});
	markschart.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
				Intent i = new Intent(DisplayClass.this,MarksChart.class);
				i.putExtra("classname",classname);
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
		getMenuInflater().inflate(R.menu.menulistact, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		switch(item.getItemId())
		{
		case R.id.item1 : 
			SharedPreferences.Editor spe = spref.edit();
			spe.putInt("DisplayClassActivity",1);
			spe.commit();
			Intent i = new Intent(DisplayClass.this,MainActivity.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			break;
		case R.id.item2 : 
			Intent i2 = new Intent(DisplayClass.this,ClassDetails.class );
			i2.putExtra("classname",classname);
			i2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i2);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			break;
		case R.id.item3 : 
/*			Intent i3 = new Intent(DisplayClass.this,Practice.class );
			i3.putExtra("classname",classname);
			i3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i3);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();*/

			 new AlertDialog.Builder(DisplayClass.this)
			    .setTitle("Upload...")
			    .setMessage("Do You Want To Upload Details To Server.?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	if(isNetworkAvailable()==true){
			    			new uploadtable().execute(null, null , null);
			    			new uploadinfo().execute(null, null , null);
			    			try{ teachername=spref.getString("name","val").trim(); 
			    			teachername=teachername.replaceAll(" ", "_");}
			    			catch(Exception e){System.out.println("Error in shared preferences");}
			    			}
			    			else { Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show(); }
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			break;
		case R.id.item4 : 
			 new AlertDialog.Builder(DisplayClass.this)
			    .setTitle("Upload...")
			    .setMessage("Do You Want To Upload Marks To Server.?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	if(isNetworkAvailable()==true){
			    			new uploadmarkstable().execute(null, null , null);
			    			new uploadmarksinfo().execute(null, null , null);
			    			try{ teachername=spref.getString("name","val").trim(); 
			    			     teachername=teachername.replaceAll(" ", "_");}
			    			catch(Exception e){System.out.println("Error in shared preferences");}
			    			}
			    			else { Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show(); }
			        }
			     })
			    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // do nothing
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			break;
			
		}
		return super.onOptionsItemSelected(item);
		
	}
	class uploadcontent extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(DisplayClass.this);
			pb.setMessage("Uploading Content To Server");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//lv.setAdapter(adp);
			if(pb.isShowing()){pb.dismiss();}
			if(connflag==1){Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();}
			else {Toast.makeText(getApplicationContext(),"Data Updated Successfully.", Toast.LENGTH_LONG).show(); }
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			Cursor resultSet = mydatabase.rawQuery("Select * from "+ classname +" order by rollno ",null);
			Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
			resultSet2.moveToNext();
		    totallect=resultSet2.getInt(1);
		    subject=resultSet2.getString(2).trim();
		    semister=resultSet2.getInt(3);
		    batchyear=resultSet2.getInt(4);
		    int total =resultSet.getCount();
			while(total!=0)
			{    resultSet.moveToNext();
				 name=resultSet.getString(0).trim();
			     roll=resultSet.getString(1).trim();
				 con=resultSet.getString(2).trim();
				 cls=resultSet.getString(3).trim();
				 attendance=resultSet.getInt(4);
				if(connflag!=1){ deliver(); }
				else{break;}
				 total--;
			}
			if(flag==0){
				int update=1;
				SharedPreferences.Editor spe = spref.edit();
				spe.putInt(classname,update);
				spe.commit();	
			}
		//Toast.makeText(getApplicationContext(), "Successful" , Toast.LENGTH_LONG).show();
			
			mydatabase.close();
		} catch(Exception e){
			System.out.println("Not Completed");
		}
			return null;
		}
		void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				//message=message.replaceAll(" ", "+");
				// System.out.println(message);
    			HttpParams params = httpclient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
				HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/uploadcontent.php?name="+name.replaceAll(" ","+")+"&rollno="+roll+"&batch="+batchyear+"&contact="+con+"&class="+cls+"&attendance="+attendance+"&totallect="+totallect+"&flag="+flag+"&semister="+semister+"&subject="+subject.replaceAll(" ", "+")+"&teachername="+teachername.replaceAll(" ", "+")+"&teachercontact="+teachercontact+"&updatedate="+updatedate);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			 }
			 catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){System.out.println("Error e1: "+e.toString());}
			 try {
				 InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
				 BufferedReader reader =  new BufferedReader(isre,8);
				 StringBuilder sb = new StringBuilder();
				 String line =  null;
				 while((line = reader.readLine())!=null)
				 {
					sb.append(line+"\n") ;
				 }
				 isr.close();
				 System.out.println(line);
				// msg = sb.toString();
				 
			 } catch(Exception e) {System.out.println("Error e2 : "+e.toString());}
		 }
	}
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
	    	 {return true;}
	    else {return false;}
	} 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	Intent i = new Intent(DisplayClass.this,MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
		    return true;
	    }
	return false;
	}
	class uploadtable extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(DisplayClass.this);
			pb.setMessage("Creating Table");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//lv.setAdapter(adp);
			if(pb.isShowing()){pb.dismiss();}
			if(connflag==1){Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();}
			else {Toast.makeText(getApplicationContext(),"Table Created Successfully.", Toast.LENGTH_LONG).show(); }
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			
			try{
		    	teachername= spref.getString("name","val");
		    	teachername=teachername.replaceAll(" ", "_").trim();
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
				resultSet2.moveToNext();
				classname1=resultSet2.getString(0);
				totallect=resultSet2.getInt(1);
				System.out.println("TotalLect "+totallect);
			    subject=resultSet2.getString(2).trim();
			    semister=resultSet2.getInt(3);
			    batchyear=resultSet2.getInt(4);
				Cursor resultSet = mydatabase.rawQuery("Select * from " +classname ,null);
				totalcol=resultSet.getColumnCount();
				colnames="";
				for(int i=0;i<totalcol;i++)
				{colnames=colnames+"($)"+resultSet.getColumnName(i);}
				if(connflag!=1){ deliver(); }
				mydatabase.close();
			}
			catch(Exception e){System.out.println("Error : "+e);}
			return null;
		}
		void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				//message=message.replaceAll(" ", "+");
				// System.out.println(message);
    			HttpParams params = httpclient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
				HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/createnewtable.php?" +
						"colnames="+colnames.replaceAll(" ","+")+"&totalcol="+totalcol+"&classname="+classname1+"&semister="+semister+
						"&totallect="+totallect+"&batchyear="+batchyear+"&subject="+subject.replaceAll(" ", "+")+
						"&teachername="+teachername.replaceAll(" ", "+"));
				System.out.println(classname1+" "+totallect+" "+subject+" "+semister+" "+batchyear+" "+totalcol+" "+colnames+" "+teachername);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			 }
			 catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){System.out.println("Error e1: "+e.toString());}
			 try {
				 InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
				 BufferedReader reader =  new BufferedReader(isre,8);
				 StringBuilder sb = new StringBuilder();
				 String line =  null;
				 while((line = reader.readLine())!=null)
				 {
					sb.append(line+"\n") ;
				 }
				 isr.close();
				 System.out.println(line);
				// msg = sb.toString();
				 
			 } catch(Exception e) {System.out.println("Error e2 : "+e.toString());}
		 }
	}
	
	class uploadinfo extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(DisplayClass.this);
			pb.setMessage("Uploading Info To Server");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//lv.setAdapter(adp);
			if(pb.isShowing()){pb.dismiss();}
			if(connflag==1){Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();}
			else {Toast.makeText(getApplicationContext(),"Data Uploaded Successfully.", Toast.LENGTH_LONG).show(); }
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			
			try{
		    	teachername= spref.getString("name","val");
		    	teachername=teachername.replaceAll(" ", "_").trim();
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
				resultSet2.moveToNext();
				classname1=resultSet2.getString(0);
				totallect=resultSet2.getInt(1);
				System.out.println("TotalLect "+totallect);
			    subject=resultSet2.getString(2).trim();
			    semister=resultSet2.getInt(3);
			    batchyear=resultSet2.getInt(4);
				Cursor resultSet = mydatabase.rawQuery("Select * from " +classname ,null);
				totalcol=resultSet.getColumnCount();
				colnames="";
				for(int i=0;i<totalcol;i++)
				{colnames=colnames+"($)"+resultSet.getColumnName(i);}		
				System.out.println("Total Columns : "+totalcol);
				info="";
	        	while(resultSet.moveToNext()){
	        		System.out.println(resultSet.getString(0).replaceAll(" ", "+")+" "+resultSet.getString(1)+" "+resultSet.getString(2)+" "+
	        					resultSet.getString(3)+" "+resultSet.getInt(4)+" ");
	        		info=" ($)"+resultSet.getString(0).replaceAll(" ", "+")+"($)"+resultSet.getString(1)+"($)"+resultSet.getString(2)+"($)"+
	    					resultSet.getString(3)+"($)"+resultSet.getInt(4);
	        		n=5;
	        		while(n<totalcol)
	        		{
	        			System.out.println(resultSet.getString(n));
	        			info=info+"($)"+resultSet.getString(n);
	        			n++;
	        		}
					if(connflag!=1){ deliver(); }
	        		info="";
	        		System.out.println("****\n");
	        		
	            }				
				mydatabase.close();
			}
			catch(Exception e){System.out.println("Error : "+e);}
			return null;
		}
		
		void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				//message=message.replaceAll(" ", "+");
				// System.out.println(message);
   			HttpParams params = httpclient.getParams();
   			HttpConnectionParams.setConnectionTimeout(params,5000);
   			HttpConnectionParams.setSoTimeout(params,5000);
			HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/insertdatatotable.php?" +
						"colnames="+colnames.replaceAll(" ","+")+"&totalcol="+totalcol+"&classname="+classname1+"&semister="+semister+
						"&totallect="+totallect+"&batchyear="+batchyear+"&subject="+subject.replaceAll(" ", "+")+
						"&teachername="+teachername.replaceAll(" ", "+")+"&info="+info.replaceAll(" ", "+"));
				System.out.println(classname1+" "+totallect+" "+subject+" "+semister+" "+batchyear+" "+totalcol+" "+colnames+" "+teachername+"\n"+info);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			 }
			 catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){System.out.println("Error e1: "+e.toString());}
			 try {
				 InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
				 BufferedReader reader =  new BufferedReader(isre,8);
				 StringBuilder sb = new StringBuilder();
				 String line =  null;
				 while((line = reader.readLine())!=null)
				 {
					sb.append(line+"\n") ;
				 }
				 isr.close();
				 System.out.println(line);
				// msg = sb.toString();
				 
			 } catch(Exception e) {System.out.println("Error e2 : "+e.toString());}
		 }
	}
	
	/* Marks Table Upload AsyncTask */
	class uploadmarkstable extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(DisplayClass.this);
			pb.setMessage("Creating Table");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//lv.setAdapter(adp);
			if(pb.isShowing()){pb.dismiss();}
			if(connflag==1){Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();}
			else {Toast.makeText(getApplicationContext(),"Table Created Successfully.", Toast.LENGTH_LONG).show(); }
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			
			try{
		    	teachername= spref.getString("name","val");
		    	teachername=teachername.replaceAll(" ", "_").trim();
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
				resultSet2.moveToNext();
				classname1=resultSet2.getString(0);
				totallect=resultSet2.getInt(1);
				System.out.println("TotalLect "+totallect);
			    subject=resultSet2.getString(2).trim();
			    semister=resultSet2.getInt(3);
			    batchyear=resultSet2.getInt(4);
				Cursor resultSet = mydatabase.rawQuery("Select * from " +classname+"_markstable " ,null);
				totalcol=resultSet.getColumnCount();
				colnames="";
				for(int i=0;i<totalcol;i++)
				{colnames=colnames+"($)"+resultSet.getColumnName(i);}
				if(connflag!=1){ deliver(); }
				mydatabase.close();
			}
			catch(Exception e){System.out.println("Error : "+e);}
			return null;
		}
		void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				//message=message.replaceAll(" ", "+");
				// System.out.println(message);
    			HttpParams params = httpclient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
				HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/createnewmarkstable.php?" +
						"colnames="+colnames.replaceAll(" ","+")+"&totalcol="+totalcol+"&classname="+classname1+"&semister="+semister+
						"&batchyear="+batchyear+"&subject="+subject.replaceAll(" ", "+")+"&teachername="+teachername.replaceAll(" ", "+"));
				System.out.println(classname1+" "+subject+" "+semister+" "+batchyear+" "+totalcol+" "+colnames+" "+teachername);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			 }
			 catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){System.out.println("Error e1: "+e.toString());}
			 try {
				 InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
				 BufferedReader reader =  new BufferedReader(isre,8);
				 StringBuilder sb = new StringBuilder();
				 String line =  null;
				 while((line = reader.readLine())!=null)
				 {
					sb.append(line+"\n") ;
				 }
				 isr.close();
				 System.out.println(line);
				// msg = sb.toString();
				 
			 } catch(Exception e) {System.out.println("Error e2 : "+e.toString());}
		 }
	}
	/* Marks Info Upload AsyncTask */
	class uploadmarksinfo extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(DisplayClass.this);
			pb.setMessage("Uploading Info To Server");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				//lv.setAdapter(adp);
			if(pb.isShowing()){pb.dismiss();}
			if(connflag==1){Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();}
			else {Toast.makeText(getApplicationContext(),"Marks Uploaded Successfully.", Toast.LENGTH_LONG).show(); }
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			
			try{
		    	teachername= spref.getString("name","val");
		    	teachername=teachername.replaceAll(" ", "_").trim();
				SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
				Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
				resultSet2.moveToNext();
				classname1=resultSet2.getString(0);
				totallect=resultSet2.getInt(1);
				System.out.println("TotalLect "+totallect);
			    subject=resultSet2.getString(2).trim();
			    semister=resultSet2.getInt(3);
			    batchyear=resultSet2.getInt(4);
				Cursor resultSet = mydatabase.rawQuery("Select * from " +classname+"_markstable " ,null);
				totalcol=resultSet.getColumnCount();
				colnames="";
				for(int i=0;i<totalcol;i++)
				{colnames=colnames+"($)"+resultSet.getColumnName(i);}		
				System.out.println("Total Columns : "+totalcol);
				info="";
	        	while(resultSet.moveToNext()){
	        		System.out.println(resultSet.getString(0).replaceAll(" ", "+")+" "+resultSet.getString(1));
	        		info=" ($)"+resultSet.getString(0).replaceAll(" ", "+")+"($)"+resultSet.getString(1);
	        		n=2;
	        		while(n<totalcol)
	        		{
	        			System.out.println(resultSet.getString(n));
	        			info=info+"($)"+resultSet.getString(n);
	        			n++;
	        		}
					if(connflag!=1){ deliver(); }
	        		info="";
	        		System.out.println("****\n");
	        		
	            }				
				mydatabase.close();
			}
			catch(Exception e){System.out.println("Error : "+e);}
			return null;
		}
		
		void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				//message=message.replaceAll(" ", "+");
				// System.out.println(message);
   			HttpParams params = httpclient.getParams();
   			HttpConnectionParams.setConnectionTimeout(params,5000);
   			HttpConnectionParams.setSoTimeout(params,5000);
			HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/insertdatatomarkstable.php?" +
						"colnames="+colnames.replaceAll(" ","+")+"&totalcol="+totalcol+"&classname="+classname1+"&semister="+semister+
						"&batchyear="+batchyear+"&subject="+subject.replaceAll(" ", "+")+
						"&teachername="+teachername.replaceAll(" ", "+")+"&info="+info.replaceAll(" ", "+"));
				System.out.println(classname1+"  "+subject+" "+semister+" "+batchyear+" "+totalcol+" "+colnames+" "+teachername+"\n"+info);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				isr = entity.getContent();
			 }
			 catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){System.out.println("Error e1: "+e.toString());}
			 try {
				 InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
				 BufferedReader reader =  new BufferedReader(isre,8);
				 StringBuilder sb = new StringBuilder();
				 String line =  null;
				 while((line = reader.readLine())!=null)
				 {
					sb.append(line+"\n") ;
				 }
				 isr.close();
				 System.out.println(line);
				// msg = sb.toString();
				 
			 } catch(Exception e) {System.out.println("Error e2 : "+e.toString());}
		 }
	}
	//end of asynctask marks info
}
