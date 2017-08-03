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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Practice extends Activity {
int flag,totallect,semister,batchyear,totalcol,n=5;
String classname,subject,teachername,info="",colnames="",classname1;
TextView txt1;
Button btn;
ArrayList<String> arraylist;
SharedPreferences spref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		
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
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				 new AlertDialog.Builder(Practice.this)
				    .setTitle("Upload...")
				    .setMessage("Do You Want To Upload Details To Server.?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	if(isNetworkAvailable()==true){
				    			new uploadcontent().execute(null, null , null);
				    			new uploadinfo().execute(null, null , null);
				    			try{ teachername=spref.getString("name","val").trim(); }
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
			
				
			}
		});
		/*try{
			SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
			resultSet2.moveToNext();
			totallect=resultSet2.getInt(1);
			System.out.println("TotalLect "+totallect);
		    subject=resultSet2.getString(2).trim();
		    semister=resultSet2.getInt(3);
		    batchyear=resultSet2.getInt(4);
		    arraylist= new ArrayList<String>();
			Cursor resultSet = mydatabase.rawQuery("Select * from " +classname ,null);
			totalcol=resultSet.getColumnCount();
			colnames="";
			for(int i=0;i<totalcol;i++)
			{colnames=colnames+"($)"+resultSet.getColumnName(i);}
			System.out.println("Total Columns : "+totalcol);
			holdvalue="";
        	while(resultSet.moveToNext()){
        		System.out.println(resultSet.getString(0)+" "+resultSet.getString(1)+" "+resultSet.getString(2)+" "+
        					resultSet.getString(3)+" "+resultSet.getInt(4)+" ");
        		holdvalue=resultSet.getString(0)+"($)"+resultSet.getString(1)+"($)"+resultSet.getString(2)+"($)"+
    					resultSet.getString(3)+"($)"+resultSet.getInt(4);
        		n=5;
        		while(n<totalcol)
        		{
        			System.out.println(resultSet.getString(n));
        			holdvalue=holdvalue+"($)"+resultSet.getString(n);
        			n++;
        			arraylist.add(resultSet.getString(0)+" "+resultSet.getString(1)+" "+resultSet.getString(2)+" "+
        					resultSet.getString(3)+" "+resultSet.getInt(4)+" ");
        		}
        		arraylist.add(holdvalue);
        		txt1.append(holdvalue+"\n\n");
        		holdvalue="";
        		System.out.println("****\n");
        		
            }
            for(int i=0;i<arraylist.size();i++)
            {System.out.println(arraylist.get(i)+"\n");}
	//		System.out.println("out4");
			mydatabase.close();
			}
			catch(Exception e) {
			Toast.makeText(Practice.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}*/
	}

	class uploadcontent extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	   int connflag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(Practice.this);
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
		    	teachername=teachername.replaceAll(" ", "_");
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
				HttpPost httppost =  new HttpPost("http://"+getString(R.string.link)+"/myrecords/createnewtable.php?" +
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
			pb = new ProgressDialog(Practice.this);
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
		    	teachername=teachername.replaceAll(" ", "_");
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
			HttpPost httppost =  new HttpPost("http://"+getString(R.string.link)+"/myrecords/insertdatatotable.php?" +
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practice, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { if(flag==0){Intent i= new Intent(Practice.this,DisplayClass.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			}
			else if(flag==1){
				Intent i= new Intent(Practice.this,DisplayClassGrid.class);
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
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
	    	 {return true;}
	    else {return false;}
	} 
}
