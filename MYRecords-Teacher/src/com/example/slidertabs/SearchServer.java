package com.example.slidertabs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchServer extends Activity {
String result="",searchitem="",record1="",serverurl;
EditText edt1;
Button btn;
ListView lv;
TextView txt;
ArrayAdapter<String> adp;
SerachServerAdapter adapter;
ArrayList<String> nameal = new ArrayList<String>();
ArrayList<String> rollal= new ArrayList<String>();
ArrayList<String> classnameal= new ArrayList<String>();
ArrayList<String> subjectal= new ArrayList<String>();
ArrayList<String> semisteral= new ArrayList<String>();
ArrayList<String> totallectal= new ArrayList<String>();
ArrayList<String> totalattal= new ArrayList<String>();
ArrayList<String> remarksal= new ArrayList<String>();
ArrayList<String> prratingal= new ArrayList<String>();
ArrayList<String> brratingal= new ArrayList<String>();
ArrayList<String> teachernameal= new ArrayList<String>();
ArrayList<String> updatedateal= new ArrayList<String>();
int output;
SharedPreferences spref;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_server);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		edt1=(EditText)findViewById(R.id.editText1);
		btn=(Button)findViewById(R.id.button1);
		lv=(ListView)findViewById(R.id.listView1);
		txt=(TextView)findViewById(R.id.textView1);
		spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		serverurl=spref.getString("ServerURL","val").trim();
		/*SerachServerAdapter adapter = new SerachServerAdapter(this,nameal,  rollal, classnameal, subjectal, semisteral, totallectal, totalattal,
				remarksal , prratingal, brratingal, teachernameal, updatedateal);*/
		edt1.setOnKeyListener(new View.OnKeyListener() {
	        public boolean onKey(View v, int keyCode, KeyEvent event) {
	            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					
					if(isNetworkAvailable()==true)
					{searchitem=edt1.getText().toString().trim();
					if(searchitem.equals(null)||searchitem.equals("")){
						lv.setAdapter(null);
						txt.setText("Search Field Cannot Be Empty.");
						InputMethodManager inputManager = (InputMethodManager)
	                            getSystemService(Context.INPUT_METHOD_SERVICE); 

						inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	                               InputMethodManager.HIDE_NOT_ALWAYS);
					}
					else
					{lv.setAdapter(null);
					nameal.clear();  rollal.clear(); classnameal.clear(); subjectal.clear(); semisteral.clear(); totallectal.clear();
					totalattal.clear(); remarksal.clear(); prratingal.clear(); brratingal.clear(); teachernameal.clear(); updatedateal.clear();
					InputMethodManager inputManager = (InputMethodManager)
	                        getSystemService(Context.INPUT_METHOD_SERVICE); 

	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	                           InputMethodManager.HIDE_NOT_ALWAYS);
					new Searchserver().execute();
					//adp = new ArrayAdapter<String>(SearchServer.this, android.R.layout.simple_list_item_1);
					}
					}
					else{
						Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
						InputMethodManager inputManager = (InputMethodManager)
	                            getSystemService(Context.INPUT_METHOD_SERVICE); 

	inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
	                               InputMethodManager.HIDE_NOT_ALWAYS);
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
				
				if(isNetworkAvailable()==true)
				{searchitem=edt1.getText().toString().trim();
				if(searchitem.equals(null)||searchitem.equals("")){
					lv.setAdapter(null);
					txt.setText("Search Field Cannot Be Empty.");
					InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE); 

					inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                               InputMethodManager.HIDE_NOT_ALWAYS);
				}
				else
				{lv.setAdapter(null);
				nameal.clear();  rollal.clear(); classnameal.clear(); subjectal.clear(); semisteral.clear(); totallectal.clear();
				totalattal.clear(); remarksal.clear(); prratingal.clear(); brratingal.clear(); teachernameal.clear(); updatedateal.clear();
				InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
				serverurl=spref.getString("ServerURL","val").trim();
				new Searchserver().execute();
				//adp = new ArrayAdapter<String>(SearchServer.this, android.R.layout.simple_list_item_1);
				}
				}
				else{
					Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
					InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                               InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		});
		/*if(output==1){
			adapter = new SerachServerAdapter(this,nameal,  rollal, classnameal, subjectal, semisteral, totallectal, totalattal,
					remarksal , prratingal, brratingal, teachernameal, updatedateal);
			lv.setAdapter(adapter);
		    lv.smoothScrollToPosition(adapter.getCount());
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_server, menu);
		return true;
	}
	class Searchserver extends AsyncTask<Void, Void, Void>
	{
	ProgressDialog pb;
     int flag=0; 
      @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(SearchServer.this);
			pb.setMessage("Searching Server...");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			getData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(pb.isShowing()){pb.dismiss();}
			if(flag==1){ Toast.makeText(SearchServer.this, "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();
			//lv.setAdapter(adapter);	
			lv.setAdapter(null);
			}
			else if(flag==2) { //Toast.makeText(SearchServer.this, "Server Not Responding..", Toast.LENGTH_LONG).show();
			//lv.setAdapter(adapter);
			lv.setAdapter(null);
			txt.setText("No Results Found.");
			}
			else {
			//lv.setAdapter(adapter);
				
			txt.setText("");
			adapter = new SerachServerAdapter(SearchServer.this,nameal,  rollal, classnameal, subjectal, semisteral, totallectal, totalattal,
						remarksal , prratingal, brratingal, teachernameal, updatedateal);
			lv.setAdapter(adapter);
		    lv.smoothScrollToPosition(adapter.getCount());
				
			}
		}
    	public void getData(){
    		InputStream isr = null;
    		try{
    			HttpClient httpClient = new DefaultHttpClient();
    			HttpParams params = httpClient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
    			HttpPost httppost = new HttpPost("http://"+serverurl+"/myrecords/searchdata.php?search="+searchitem.replaceAll(" ","+"));
    			HttpResponse response = httpClient.execute(httppost);
    			if(response !=null)
    			{
    				System.out.println("Connection Created..!");
    				System.out.println(serverurl);
    			}
    			else{
    				System.out.println("Connection Not Created..!");
    			}
    			HttpEntity entity = response.getEntity();
    			isr = entity.getContent();
    			
    		} catch (ConnectTimeoutException e) {
    	        //Here Connection TimeOut excepion    
    		     // Toast.makeText(SearchServer.this, "Your connection timedout", Toast.LENGTH_LONG).show();
    			flag=1;
    		   }
    		catch(Exception e){
    			System.out.println("Error"+e);
    		}
    		try{
    			InputStreamReader isre = new InputStreamReader(isr,"iso-8859-1");
    			BufferedReader reader = new BufferedReader(isre,8);
    			StringBuffer sb = new StringBuffer();
    			String line = null;
    					while((line= reader.readLine())!=null)
    					{
    						sb.append(line);
    					}
    				isr.close();
    				result = sb.toString();
    				System.out.println("Success");
    				
    		
    		}catch(Exception e){
    			System.out.println("Error"+e);
    		}
    		try {
    			JSONArray j = new JSONArray(result);
    			for(int i=0;i<j.length();i++){
    				JSONObject json = j.getJSONObject(i);
    				record1 ="Name : "+json.getString("name")+"\nRoll No. : " + json.getString("rollno")+"\nClass : "+json.getString("class")+"\nRemarks : "+json.getString("remarks")+"\nPerformance : "+json.getString("performancerating")+"\n Behaviour : "+json.getString("behaviourrating");
    				 nameal.add(json.getString("name"));
    				 rollal.add(json.getString("rollno"));
    				 classnameal.add(json.getString("class"));
    			     subjectal.add(json.getString("subject"));
    				 semisteral.add(json.getString("semister"));
    				 totallectal.add(json.getString("totallect"));
    				 totalattal.add(json.getString("attendance"));
    				 remarksal.add(json.getString("remarks"));
    				 prratingal.add(json.getString("performancerating"));
    				 brratingal.add(json.getString("behaviourrating"));
    				 teachernameal.add(json.getString("teachername"));
    				 updatedateal.add(json.getString("updatedate"));
    				 System.out.println(record1);
    				//adp.add(record1);
    				//txt.append(record1+"\n");
    				//total = total + record1 + "\n";
    			}
    		} catch(JSONException e)
    		{
    			flag=2;
    		}
    		catch(Exception e){
    			System.out.println("Error"+e);
    		}
    	}
	}


	
	public boolean isNetworkAvailable() {
			ConnectivityManager connectivityManager  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
		    	 {return true;}
		    else {return false;}
		}
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { Intent i = new Intent(SearchServer.this, MainActivity.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			
		    return true;
	    }
	return false;
	}
}
