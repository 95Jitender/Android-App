package com.example.slidertabs;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.example.slidertabs.DisplayClass.uploadcontent;
import com.example.slidertabs.DisplayClass.uploadinfo;
import com.example.slidertabs.DisplayClass.uploadmarksinfo;
import com.example.slidertabs.DisplayClass.uploadmarkstable;
import com.example.slidertabs.DisplayClass.uploadtable;

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
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayClassGrid extends Activity {
	GridView gv;  
	String classname;
	String name,roll,cls,con,teachername,subject ,updatedate ,teachercontact;
	int attendance,totallect , flag , semister;
	int batchyear,totalcol,n=5;
	String info="",colnames="",classname1,serverurl;
	SharedPreferences spref;
	Button btnback;
	TextView txt;
     public static String [] prgmNameList={"Mark Attendance","Class Chart","Student Details","View Attendance","Add Student","Update To Server","Enter Marks","View Marks","Marks Chart","Delete Class"};
	 int imgid[]={R.drawable.markattendance,R.drawable.classdetails,R.drawable.studentdetails,R.drawable.attendance2,R.drawable.adduser,R.drawable.server,R.drawable.entermarks,R.drawable.viewmarks,R.drawable.markschart,R.drawable.deletebuttonicon };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_class_grid);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));	
		try{Bundle b= getIntent().getExtras();
		classname=b.getString("classname");}
		catch(Exception e){
			Toast.makeText(DisplayClassGrid.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
	    flag = spref.getInt(classname,0);
	    serverurl=spref.getString("ServerURL","val").trim();
	    btnback=(Button)findViewById(R.id.button1);
		gv=(GridView)findViewById(R.id.gridView1);
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.hyperspace_in);
		GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
		gv.setLayoutAnimation(controller);
		txt=(TextView)findViewById(R.id.textView1);
		txt.setText(classname);
		try{gv.setAdapter(new GridCustomAdapter(DisplayClassGrid.this, prgmNameList,imgid,"IT1_2013"));}
		catch(Exception e){
	        Toast.makeText(DisplayClassGrid.this,"Error : " +e, Toast.LENGTH_LONG).show();
	        }
		try{
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Intent i;
				Toast.makeText(DisplayClassGrid.this,"Position: "+prgmNameList[pos],Toast.LENGTH_LONG).show();
				switch(pos){
				case 0: i = new Intent(DisplayClassGrid.this,MarkAttendance.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 1 : i = new Intent(DisplayClassGrid.this,ClassChart.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 2 :  i = new Intent(DisplayClassGrid.this,ViewStudentDetails.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 3 :  i = new Intent(DisplayClassGrid.this,ViewAttendance.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
					break;
					
				case 4 :  i = new Intent(DisplayClassGrid.this,AddRecord.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
					break;
				
				case 5 :  new AlertDialog.Builder(DisplayClassGrid.this)
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
					break;
				case 6 : i = new Intent(DisplayClassGrid.this,MarksDetails.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 7 : i = new Intent(DisplayClassGrid.this,ViewMarks.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 8 : i = new Intent(DisplayClassGrid.this,MarksChart.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				break;
				
				case 9 : new AlertDialog.Builder(DisplayClassGrid.this)
			    .setTitle("Delete...")
			    .setMessage("Are you sure you want to delete this Class?")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	 try{
							 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
						     mydatabase.execSQL("DROP TABLE IF EXISTS "+ classname);
						     mydatabase.execSQL("DELETE FROM classesnameslist WHERE classname ='"+classname+"'");
						     mydatabase.execSQL("DROP TABLE IF EXISTS "+ classname+"_markstable");
						     mydatabase.execSQL("DELETE FROM marksinfo WHERE classname ='"+classname+"'");
						     mydatabase.close();
						     Toast.makeText(DisplayClassGrid.this,"Class Deleted", Toast.LENGTH_LONG).show();
						     Intent i = new Intent(DisplayClassGrid.this,MainActivity.class);
							 startActivity(i);
						     finish();
						 }
						 catch(Exception e){
							 Toast.makeText(DisplayClassGrid.this,"Unable To Delete Class", Toast.LENGTH_LONG).show();
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
					break;
				}
			}
        });
        } catch(Exception e){
        Toast.makeText(DisplayClassGrid.this,"Error : " +e, Toast.LENGTH_LONG).show();
        }
	btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Intent i = new Intent(DisplayClassGrid.this,MainActivity.class);
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
		getMenuInflater().inflate(R.menu.menugridact, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{	
		switch(item.getItemId())
		{
		case R.id.item1 : 
			SharedPreferences.Editor spe = spref.edit();
			spe.putInt("DisplayClassActivity",0);
			spe.commit();
			Intent i = new Intent(DisplayClassGrid.this,MainActivity.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			break;
		case R.id.item2 : 
			Intent i2 = new Intent(DisplayClassGrid.this,ClassDetails.class );
			i2.putExtra("classname",classname);
			i2.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i2);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			break;
		case R.id.item3 : 
/*			Intent i3 = new Intent(DisplayClassGrid.this,Practice.class );
			i3.putExtra("classname",classname);
			i3.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i3);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
*/
			 new AlertDialog.Builder(DisplayClassGrid.this)
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
			 new AlertDialog.Builder(DisplayClassGrid.this)
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
			pb = new ProgressDialog(DisplayClassGrid.this);
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
			 }catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			connflag=1;
	    			System.out.println("Error conntimeout: "+e.toString());
	    		   }
			 catch(Exception e){}
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
				// msg = sb.toString();
				 
			 } catch(Exception e) {}
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
	    	Intent i = new Intent(DisplayClassGrid.this,MainActivity.class);
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
			pb = new ProgressDialog(DisplayClassGrid.this);
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
			pb = new ProgressDialog(DisplayClassGrid.this);
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
			pb = new ProgressDialog(DisplayClassGrid.this);
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
			pb = new ProgressDialog(DisplayClassGrid.this);
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
