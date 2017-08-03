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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.slidertabs.AttendanceChart.CSVToExcelConverter;
import com.example.slidertabs.AttendanceChart.ExportDatabaseCSVTask;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class EachStudent extends Activity {
	Button btndetails,btnback,btndel , btncall , btnupdate , btnremarks ;
	TextView txt,text1,text2,text3,text5,text6,text7,text8;
	String classname,studentname,rollno,contact,comefrom;
	String result="",record1="" , total="";
	String name,roll,cls,con,teachername,subject ,updatedate ,teachercontact , message,serverurl;
	int totalatt,totallect , semister ;
	float perc;
	public static String [] prgmNameList={"Call","Upload To Server","Update Details","Remarks","Attendance Chart","Marks", "Delete Student"};
	int imgid[]={R.drawable.call,R.drawable.server,R.drawable.editdetails,R.drawable.remarks,R.drawable.stats,R.drawable.marksofstudent,R.drawable.deletebuttonicon };
	SharedPreferences spref;
	GridView gv;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_each_student);
			ActionBar bar = getActionBar();
			bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));

			try{
				Bundle b= getIntent().getExtras();
				classname=b.getString("classname");
				studentname=b.getString("studentname");
				rollno=b.getString("rollno");
				comefrom=b.getString("comefrom");
			}
			catch(Exception e){
				Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
			spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
			serverurl=spref.getString("ServerURL","val").trim();
			text1=(TextView)findViewById(R.id.textView2);
			text2=(TextView)findViewById(R.id.textView3);
			text3=(TextView)findViewById(R.id.textView4);
			text5=(TextView)findViewById(R.id.textView6);
			text6=(TextView)findViewById(R.id.textView7);
			text7=(TextView)findViewById(R.id.textView8);
			text8=(TextView)findViewById(R.id.textView9);
			btndetails=(Button)findViewById(R.id.button1);
			btnback=(Button)findViewById(R.id.button2);
			btncall=(Button)findViewById(R.id.button3);
			btndel=(Button)findViewById(R.id.button4);
			btnupdate=(Button)findViewById(R.id.button5);
			btnremarks=(Button)findViewById(R.id.button6);
			try{
			SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
			Cursor resultSet = mydatabase.rawQuery("Select * from "+ classname +" WHERE rollno ='" +rollno +"'",null);
			Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist WHERE classname ='" +classname +"'",null);
			resultSet.moveToNext();
			resultSet2.moveToNext();
			totallect=resultSet2.getInt(1);
		    subject=resultSet2.getString(2).trim();
		    semister=resultSet2.getInt(3);
			name=resultSet.getString(0);
		    roll=resultSet.getString(1);
			con=resultSet.getString(2);
		    cls=resultSet.getString(3);
		    totalatt=resultSet.getInt(4);
			int totallect=resultSet2.getInt(1);
			contact=con;
			text1.setText("Name : " +name);
			text2.setText(roll);
			text3.setText("Contact : " + con);
			text5.setText("Class : " +cls);
			text6.setText("Total Attendance : "+totalatt);
			text7.setText("Total Lectures : "+totallect);
			if(totallect<1){
				perc=((totalatt/1)*100);
				text8.setText("Attendance Percentage : "+perc);
			}
			else {
				perc=(float)(((double)totalatt/(double)totallect)*100);
				String percentage = String.format("%.1f", perc);
				text8.setText("Attendance Percentage : "+percentage);
			}
			mydatabase.close();
			}
			catch(Exception e) {
			Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
			}
			
		/*btndetails.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
					String roll= text2.getText().toString();
					Intent i = new Intent(EachStudent.this,UpdateDetails.class);
					i.putExtra("classname",classname);
					i.putExtra("rollno",roll);
					i.putExtra("studentname",studentname);
					i.putExtra("contact",contact);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				     finish();
					}
					catch(Exception e){
						Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
					}
				}
			});*/
		/*	btnback.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(comefrom.equals("details"))
					{
					try{  Intent i = new Intent(EachStudent.this,ViewStudentDetails.class);
					i.putExtra("classname",classname);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				     finish();
					}
					catch(Exception e){Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();}
					}
					else if(comefrom.equals("search")) 
					{
						try{  Intent i = new Intent(EachStudent.this,MainActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					     finish();
						}
						catch(Exception e){Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();}
					}
					
				}
			});*/
			btndel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub	
					new AlertDialog.Builder(EachStudent.this)
				    .setTitle("Delete...")
				    .setMessage("Are you sure you want to delete this Student Details?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	try{ 
								 String roll= text2.getText().toString();
								 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
							     mydatabase.execSQL("DELETE FROM "+ classname +" WHERE  rollno='"+roll+"';");
							     mydatabase.close();
							     Toast.makeText(EachStudent.this,"Student Deleted", Toast.LENGTH_LONG).show();
							     Intent i = new Intent(EachStudent.this,MainActivity.class);
								 startActivity(i);
							     finish();
							     }
								catch(Exception e){
									 Toast.makeText(EachStudent.this,"Unable To Delete", Toast.LENGTH_LONG).show();
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
			
	      /*  btncall.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 try {
		                    Intent i = new Intent(Intent.ACTION_CALL);
		                    i.setData(Uri.parse("tel:" + contact));
		                    startActivity(i);
		                }
		                catch(Exception e)
		                {
		                    Toast.makeText(EachStudent.this, e.toString(), Toast.LENGTH_LONG).show();
		                }	
				}
			
	        });	*/
	    	
	      /*  btnupdate.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(isNetworkAvailable()==true){
					try{ teachercontact=spref.getString("contact","val").trim(); 
					     teachername=spref.getString("name","val").trim();}
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
		                new updatestudentdata().execute();
		                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
				}
				else{Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();}
				}
	        });	*/
	        
	        
	    /* btnremarks.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try{
					Intent i = new Intent(EachStudent.this,EnterRemarks.class);
					i.putExtra("classname",classname);
					i.putExtra("rollno",roll);
					i.putExtra("studentname",studentname);
					i.putExtra("contact",contact);
					i.putExtra("semister",semister);
					i.putExtra("subject",subject);
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				     finish();
					}
					catch(Exception e){
						Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
					}
				}
			});*/
	     gv=(GridView)findViewById(R.id.gridView1);
			gv.setAdapter(new GridCustomAdapter(EachStudent.this, prgmNameList,imgid,"IT1_2013"));
	        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
						long arg3) {
					// TODO Auto-generated method stub
					Intent i;
					/*Toast.makeText(EachStudent.this,"Position: "+prgmNameList[pos],Toast.LENGTH_LONG).show();*/
					switch(pos){
					case 0 : try {
	                    i = new Intent(Intent.ACTION_CALL);
	                    i.setData(Uri.parse("tel:" + contact));
	                    startActivity(i);
	                }
	                catch(Exception e)
	                {
	                    Toast.makeText(EachStudent.this, e.toString(), Toast.LENGTH_LONG).show();
	                }	
					break;
					
					case 1 : 
					 new AlertDialog.Builder(EachStudent.this)
					    .setTitle("Upload...")
					    .setMessage("Do You Want To Update This Student Detail To Server?")
					    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
					        public void onClick(DialogInterface dialog, int which) { 
					            // continue with delete
					        	 if(isNetworkAvailable()==true){
										try{ teachercontact=spref.getString("contact","val").trim(); 
									     teachername=spref.getString("name","val").trim();
									     serverurl=spref.getString("ServerURL","val").trim();
									     }
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
						                new updatestudentdata().execute();
						                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
								}
								else{Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();}
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
					
					case 2 :  try{
						String roll= text2.getText().toString();
					    i = new Intent(EachStudent.this,UpdateDetails.class);
						i.putExtra("classname",classname);
						i.putExtra("rollno",roll);
						i.putExtra("studentname",studentname);
						i.putExtra("contact",contact);
						i.putExtra("comefrom",comefrom);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					     finish();
						}
						catch(Exception e){
							Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
						}
						break;
						
					case 3 : try{
					    i = new Intent(EachStudent.this,EnterRemarks.class);
						i.putExtra("classname",classname);
						i.putExtra("rollno",roll);
						i.putExtra("studentname",studentname);
						i.putExtra("contact",contact);
						i.putExtra("semister",semister);
						i.putExtra("subject",subject);
						i.putExtra("comefrom",comefrom);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					     finish();
						}
						catch(Exception e){
							Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
						}
						break;
					case 4 :
						try{
						    i = new Intent(EachStudent.this,StudentStats.class);
							i.putExtra("classname",classname);
							i.putExtra("rollno",roll);
							i.putExtra("studentname",studentname);
							i.putExtra("perc",perc);
							i.putExtra("semister",semister);
							i.putExtra("subject",subject);
							i.putExtra("totallect", totallect);
							i.putExtra("comefrom",comefrom);
							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(i);
							overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						     finish();
							}
							catch(Exception e){
								Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
							}
							break;
					case 5 : 
						try{
						    i = new Intent(EachStudent.this,StudentMarksList.class);
							i.putExtra("classname",classname);
							i.putExtra("rollno",roll);
							i.putExtra("studentname",studentname);
							i.putExtra("perc",perc);
							i.putExtra("semister",semister);
							i.putExtra("subject",subject);
							i.putExtra("totallect", totallect);
							i.putExtra("comefrom",comefrom);
							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(i);
							overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						     finish();
							}
							catch(Exception e){
								Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();
							}
						break;
					case 6 : new AlertDialog.Builder(EachStudent.this)
				    .setTitle("Delete...")
				    .setMessage("Are you sure you want to delete this Student Details?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	try{ 
								 String roll= text2.getText().toString();
								 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
							     mydatabase.execSQL("DELETE FROM "+ classname +" WHERE  rollno='"+roll+"';");
							     mydatabase.close();
							     Toast.makeText(EachStudent.this,"Student Deleted", Toast.LENGTH_LONG).show();
							     Intent i = new Intent(EachStudent.this,MainActivity.class);
								 startActivity(i);
							     finish();
							     }
								catch(Exception e){
									 Toast.makeText(EachStudent.this,"Unable To Delete", Toast.LENGTH_LONG).show();
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
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.each_student, menu);
		return true;
	}
	

	class updatestudentdata extends AsyncTask<Void, Void, Void>
	{ ProgressDialog pb;
      boolean x;
      int flag=0;
      @Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb = new ProgressDialog(EachStudent.this);
			pb.setMessage("Uploading Content To Server");
			//pb.setTitle("Please Wait...");
			pb.setCancelable(false);
			pb.show();
		}	
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 x =getData();
			if(x==false && flag!=2){  deliver();  }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			//lv.setAdapter(adp);
			
	    if(pb.isShowing()){pb.dismiss();}
		if(flag==1){
			Toast.makeText(getApplicationContext(), "Connection Timeout.. Try Again Later.", Toast.LENGTH_LONG).show();
		}
		else if(flag==2){
			Toast.makeText(getApplicationContext(), "Could Not Connect To Server..", Toast.LENGTH_LONG).show();
		}
		
		else{
			if(x==true){
				Toast.makeText(getApplicationContext(), "Student Already Exists.", Toast.LENGTH_LONG).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "Student Added.", Toast.LENGTH_LONG).show();
			}
		}
		}
    	public boolean getData(){
    		boolean verifyflag = true;
    		InputStream isr = null;
    		try{
    			HttpClient httpClient = new DefaultHttpClient();
    			HttpParams params = httpClient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
    			HttpPost httppost = new HttpPost("http://"+serverurl+"/myrecords/verifydata.php?name="+name.replaceAll(" ","+")+"&rollno="+roll+"&class="+cls+"&semister="+semister+"&subject="+subject.replaceAll(" ", "+")+"&teachername="+teachername.replaceAll(" ", "+"));
    			HttpResponse response = httpClient.execute(httppost);
    			HttpEntity entity = response.getEntity();
    			isr = entity.getContent();
    			
    		}catch (ConnectTimeoutException e) {
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
    				System.out.println(result);
    				System.out.println("Success");
    				if(result.contains("[null]"))
    				{
    					System.out.println("Not Exist");
    					verifyflag=false;
    				}
    				else if(result.contains("Could not connect"))
    				{
    					System.out.println("Not Exist");
    					flag=2;
    					verifyflag=true;
    				}
    				else {
    					System.out.println("Exist");
    					verifyflag=true;
    				}
    				
    		
    		}
    		catch(Exception e){
    			System.out.println("Error"+e);
    		}
    		
    		if(verifyflag==false) {  return false;  }
    		
    		else  {  return true;  }
    	}//end of getdata
    	
    	void deliver(){
			 InputStream isr = null;
			 try {
				HttpClient httpclient = new DefaultHttpClient();
				HttpParams params = httpclient.getParams();
    			HttpConnectionParams.setConnectionTimeout(params,5000);
    			HttpConnectionParams.setSoTimeout(params,5000);
				HttpPost httppost =  new HttpPost("http://"+serverurl+"/myrecords/uploadsinglestudent.php?name="+name.replaceAll(" ","+")+"&rollno="+roll+"&contact="+con+"&class="+cls+"&attendance="+totalatt+"&totallect="+totallect+"&semister="+semister+"&subject="+subject.replaceAll(" ", "+")+"&teachername="+teachername.replaceAll(" ", "+")+"&teachercontact="+teachercontact+"&updatedate="+updatedate);
				HttpResponse  response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
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
		try{  Intent i = new Intent(EachStudent.this,ViewStudentDetails.class);
		i.putExtra("classname",classname);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
	     finish();
		}
		catch(Exception e){Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();}
		}
		else if(comefrom.equals("search")) 
		{
			try{  Intent i = new Intent(EachStudent.this,MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		     finish();
			}
			catch(Exception e){Toast.makeText(EachStudent.this,"Error :"+e, Toast.LENGTH_LONG).show();}
		}
			return true;
    }
return false;
}
}