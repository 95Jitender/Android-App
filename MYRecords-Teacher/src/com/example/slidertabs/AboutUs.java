package com.example.slidertabs;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUs extends Activity {
TextView txt;
EditText sub,body;
Button btn,submit;
String subject,bodyinfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		txt = (TextView)findViewById(R.id.textView2);
		btn = (Button)findViewById(R.id.button1);
		submit = (Button)findViewById(R.id.button2);
		sub=(EditText)findViewById(R.id.editText1);
		body=(EditText)findViewById(R.id.editText2);
		txt.setText(" We here at 'OUR HOMES :P' are working hard to make Teachers / Professours or Anyone who is using our product to " +
				"handle their records Easily.\nOur Aim is to help people Handle Records Easily and Save their time in Writing Records to " +
				"Sheets. Moreover saving PAPER. \n\n This App Offer's Services Such as :- \n 1. Saving Class Details Along With Each Student Name And Contact . \n 2. " +
				"Search Student From Whole Database and And View Their Performance. \n 3. Adding, Deleting, Updating Records. \n 4. Mark Attendance And Add Marks." +
				" \n 5. Print Attendance / Class Details /Marks List. " +
				" \n 6. Import/Export To Excel Sheets  And Much More. " +
				"\n\nTo Report Bugs/ Suggestions : Email Us At - msmyrecords@gmail.com \nYour Suggestions Are Most Welcome!");
		/*btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(AboutUs.this, MainActivity.class );
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				
			}
		});*/
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				subject=sub.getText().toString();
				bodyinfo=body.getText().toString();
				if(subject.equals(null)||subject.equals("")||bodyinfo.equals(null)||bodyinfo.equals(""))
				{
					Toast.makeText(getApplicationContext(), "No Fields Can be Empty", Toast.LENGTH_SHORT).show();
				}else 
				{
					/* Create the Intent */
					final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

					/* Fill it with Data */
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"msmyrecords@gmail.com"});
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, bodyinfo);

					/* Send it off to the Activity-Chooser */
					emailIntent.setType("message/rfc822");  
					startActivity(Intent.createChooser(emailIntent, "Send mail..."));
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about_us,menu);
		return true;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		 if(keyCode == KeyEvent.KEYCODE_BACK)
		    { Intent i = new Intent(AboutUs.this, MainActivity.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			
		    return true;
	    }
	return false;
	}
}
