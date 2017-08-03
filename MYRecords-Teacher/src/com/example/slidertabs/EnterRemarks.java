package com.example.slidertabs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.graphics.PorterDuff;
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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.test.PerformanceTestCase;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class EnterRemarks extends Activity {
EditText edt;
RatingBar rbperformance,rbbehaviour;
String classname,studentname,rollno,teachername,subject ,updatedate ,teachercontact,contact,remarks,comefrom,serverurl;
float rating1,rating2;
Button btn,btnback;
int semister;
TextView txt1,txt2;
SharedPreferences spref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_remarks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));

		try{
			Bundle b= getIntent().getExtras();
			classname=b.getString("classname");
			studentname=b.getString("studentname");
			rollno=b.getString("rollno");
			contact=b.getString("contact");
			semister=b.getInt("semister");
			subject=b.getString("subject");
			comefrom=b.getString("comefrom");
		}
		catch(Exception e){
			Toast.makeText(EnterRemarks.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		serverurl=spref.getString("ServerURL","val").trim();
		edt=(EditText)findViewById(R.id.editText1);
		rbperformance=(RatingBar)findViewById(R.id.ratingBar1);
		rbbehaviour=(RatingBar)findViewById(R.id.ratingBar2);
		btn=(Button)findViewById(R.id.button1);
		btnback=(Button)findViewById(R.id.button2);
		txt1=(TextView)findViewById(R.id.textView4);
		txt2=(TextView)findViewById(R.id.textView5);
		txt1.setText("0.0/10.0");
		txt2.setText("0.0/10.0");
		edt.setOnKeyListener(new View.OnKeyListener() {
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
		rbperformance.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				rating1=rbperformance.getRating();
				txt1.setText(rating1+"/10.0");
				//System.out.println(""+rating1);
			}
		});
		rbbehaviour.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				rating2=rbbehaviour.getRating();
				txt2.setText(rating2+"/10.0");
				//System.out.println(""+rating2);
			}
		});
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isNetworkAvailable()==true)
				{
				remarks=edt.getText().toString();
				teachercontact=spref.getString("contact","val").trim(); 
			    teachername=spref.getString("name","val").trim();
			    serverurl=spref.getString("ServerURL","val").trim();
				
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
			   // System.out.println(""+rating2+classname+studentname+rollno+teachername+subject+updatedate +teachercontact+contact+ semister);
	                new uploadremarks().execute(null, null , null);
				}else {
					Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
				} 
			}
		});
		
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(EnterRemarks.this,EachStudent.class);
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
		getMenuInflater().inflate(R.menu.enter_remarks, menu);
		return true;
	}
	class uploadremarks extends AsyncTask<Void, Void, Void>
	{  ProgressDialog pb;
	int flag=0;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(EnterRemarks.this);
			pb.setMessage("Uploading Remarks To Server");
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
			if(flag==1){ Toast.makeText(EnterRemarks.this, "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();
			//lv.setAdapter(adapter);	
			
			}else {
		Toast.makeText(getApplicationContext(),"Data Updated Successfully.", Toast.LENGTH_LONG).show();
			}
				
			}
			
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				 deliver();		
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
				HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/uploadremarks.php?name="+studentname.replaceAll(" ","+")+"&rollno="+rollno+"&contact="+contact+"&class="+classname+"&semister="+semister+"&subject="+subject.replaceAll(" ", "+")+"&teachername="+teachername.replaceAll(" ", "+")+"&teachercontact="+teachercontact+"&updatedate="+updatedate+"&remarks="+remarks.replaceAll(" ","+").replaceAll("[\\t\\n\\r]","(eol)")+"&performance="+rating1+"&behaviour="+rating2);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				if(response!=null){System.out.println("connected");}
				isr = entity.getContent();
			 }catch (ConnectTimeoutException e) {
	    	        //Here Connection TimeOut excepion    
	    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
	    			flag=1;
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
	    	if(comefrom.equals("details"))
			{
			try{  Intent i = new Intent(EnterRemarks.this,EachStudent.class);
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
			catch(Exception e){Toast.makeText(EnterRemarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
			else if(comefrom.equals("search")) 
			{
				try{  Intent i = new Intent(EnterRemarks.this,EachStudent.class);
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
				catch(Exception e){Toast.makeText(EnterRemarks.this,"Error :"+e, Toast.LENGTH_LONG).show();}
			}
				return true;
	    }
	return false;
	}
}
