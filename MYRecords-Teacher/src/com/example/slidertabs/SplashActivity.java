package com.example.slidertabs;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends Activity {
	public ProgressBar pb;
	public SharedPreferences spref;
	TextView txt1,txt2,txt3,txt4;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_splash);
	        pb=(ProgressBar)findViewById(R.id.progressBar1);
	        pb.setVisibility(View.INVISIBLE);
	        spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
	        txt1=(TextView)findViewById(R.id.textView1);
	        txt2=(TextView)findViewById(R.id.textView2);
	        txt3=(TextView)findViewById(R.id.textView3);
	        txt3.setVisibility(View.INVISIBLE);
	        txt4=(TextView)findViewById(R.id.textView4);
	        Animation a = AnimationUtils.loadAnimation(this,R.anim.slide_in_top);
	        Animation b = AnimationUtils.loadAnimation(this,R.anim.fade_in);
			  a.reset();   
			  txt1.startAnimation(a);
			  txt2.startAnimation(a);
			 // txt3.startAnimation(b);
			  Animation c = AnimationUtils.loadAnimation(this,R.anim.push_up_in);
		        txt4.startAnimation(c);
	        new Progress().execute();
	       
		}
		class Progress extends AsyncTask<Void,Integer,Void>
		{ 
		
			protected void onPostExecute(Void result)
			{
				super.onPostExecute(result);
				try{ int x= spref.getInt("login",0);
				if(x==0){Intent i=new Intent();
				i.setClass(SplashActivity.this, ShowOnce.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();} //end of if
					
					else{
						Intent i=new Intent();
						i.setClass(SplashActivity.this,MainActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    					startActivity(i);
    					overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
						finish();
						
					}
				}//end of try
				catch(Exception e){
					Toast.makeText(SplashActivity.this,"Error : "+e.toString(),Toast.LENGTH_LONG).show();
				}
			}
			
			protected void onProgressUpdate(Integer... values)
			{
				super.onProgressUpdate(values);
				pb.setProgress(values[0]);
				
			}
			protected Void doInBackground(Void... params){
				for(int i=0;i<=50;i++){
					try{
						Thread.sleep(10);
						publishProgress(i);
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}//end of for
				 }
				return null;
			}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
