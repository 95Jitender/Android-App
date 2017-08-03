package com.example.slidertabs;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ShowOnce extends Activity {
	SharedPreferences spref;
	EditText edt , edt2;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_once);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
		edt=(EditText)findViewById(R.id.editText1);
		edt2=(EditText)findViewById(R.id.editText2);
		btn=(Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name=edt.getText().toString();
				String contact=edt2.getText().toString();
				if(name.equals(null)||name.equals("")||contact.equals(null)||contact.equals(""))
				{
			     Toast.makeText(ShowOnce.this, "Fill Both Fields.", Toast.LENGTH_LONG).show();
				}
				else {
				int update=1;
				SharedPreferences.Editor spe = spref.edit();
				spe.putInt("login",update);
				spe.putString("name",name);
				spe.putString("contact",contact);
				spe.commit();
				Intent i=new Intent();
				i.setClass(ShowOnce.this, MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_once, menu);
		return true;
	}

}
