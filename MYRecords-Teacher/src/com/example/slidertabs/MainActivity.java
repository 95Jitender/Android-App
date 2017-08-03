package com.example.slidertabs;

import com.astuetz.PagerSlidingTabStrip;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	int flag=0;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS classesnameslist ( classname TEXT PRIMARY KEY NOT NULL , totalLect INTEGER , subject TEXT , semister INTEGER, batchyear INTEGER);");
		mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS marksinfo ( classname TEXT , subject TEXT , semister INTEGER, batchyear INTEGER, testname TEXT ,maxmarks INTEGER DEFAULT 0);");
		mydatabase.close();

		  // Get the ViewPager and set it's PagerAdapter so that it can display items
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager(),this.getApplicationContext()));
       
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
               
        tabsStrip.setOnPageChangeListener(new OnPageChangeListener() {
        	
        	// This method will be invoked when a new page becomes selected.
        	@Override
        	public void onPageSelected(int position) {
        		
        		/*Toast toast = Toast.makeText(MainActivity.this, "Selected page position: " + position, Toast.LENGTH_LONG);
        		View view = toast.getView();
        		view.setBackgroundResource(R.drawable.toast);
           		TextView text = (TextView) view.findViewById(android.R.id.message);
        		here you can do anything with text
        		 toast.show();*/
/*				InputMethodManager inputManager = (InputMethodManager)
				                      getSystemService(Context.INPUT_METHOD_SERVICE); 
				
				inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
				                           InputMethodManager.HIDE_NOT_ALWAYS);*/
        		
        		Animation anim = AnimationUtils.loadAnimation(
     	                MainActivity.this,R.anim.fade_in
     	            );
     			anim.setDuration(500);
     			viewPager.startAnimation(anim);
        		//Toast.makeText(MainActivity.this,"Selected page position: " + position, Toast.LENGTH_SHORT).show();
        		/*Fragment fragment = ((SampleFragmentPagerAdapter)viewPager.getAdapter()).getFragment(position);
				
        		if (position ==1 && fragment != null)
        		{
        			fragment.onResume();
        		}*/

        	}
        	
        	// This method will be invoked when the current page is scrolled
        	@Override
        	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        		// Code goes here
        		//Toast.makeText(MainActivity.this,"Scrolling " + position, Toast.LENGTH_SHORT).show();
        		//System.out.println("PageScroll :"+position);
        	}
        	
        	// Called when the scroll state changes: 
        	// SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
        	@Override
        	public void onPageScrollStateChanged(int state) {
        		// Code goes here
        		//Toast.makeText(MainActivity.this," State Changed ", Toast.LENGTH_SHORT).show();
        		//System.out.println("PageScrollStateChanged :"+state);
        		
        	}
        	
        });
        
        
	}
	
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item)
	{	Intent i;
		switch(item.getItemId())
		{
		case R.id.item2 : 
			i = new Intent(MainActivity.this,AboutUs.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			break;
		case R.id.item1 : 
			 i = new Intent(MainActivity.this,Profile.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			break;
		case R.id.item3 : 
			 i = new Intent(MainActivity.this,SearchServer.class );
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			break;	
		case R.id.item4 : 
			 i = new Intent(MainActivity.this,ServerURL.class );
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
	    {
	    	if(flag==0){
	    		Toast.makeText(getApplicationContext(), "Press Again To Exit", Toast.LENGTH_SHORT).show();
	    		flag=flag+1;
	    	}
	    	else{
            finish();          
            moveTaskToBack(true);
		    return true;
	    	}
	    }
	return false;
	}

}
