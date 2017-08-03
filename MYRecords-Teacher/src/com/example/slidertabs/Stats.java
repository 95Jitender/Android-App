package com.example.slidertabs;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Stats extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    //private String[] mPlanetTitles;
    static ArrayList<String> al= new ArrayList<String>();
    static ArrayList<Integer> totallect= new ArrayList<Integer>();
    static ArrayList<String> subject= new ArrayList<String>();
    static ArrayList<Integer> semister= new ArrayList<Integer>();
    static int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
        mTitle = mDrawerTitle = getTitle();
      //  mPlanetTitles = getResources().getStringArray(R.array.planets_array);
       
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
      try{ 
    	 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
        mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS classesnameslist ( classname TEXT PRIMARY KEY NOT NULL , totalLect INTEGER , subject TEXT , semister INTEGER);");
        Cursor resultSet2 = mydatabase.rawQuery("Select * from classesnameslist ",null);
        flag=resultSet2.getCount();
        while(resultSet2.moveToNext()){
        	al.add(resultSet2.getString(0));
        	totallect.add(resultSet2.getInt(1));
        	subject.add(resultSet2.getString(2));
        	semister.add(resultSet2.getInt(3));
        }
        mydatabase.close();
        al.add("Compare All Classes");
        al.add("Go To Homepage");
     
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, al));
        
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
      }catch(Exception e){System.out.println("Error"+e.toString());}

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle( this,/* host Activity */ mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statsmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
       // menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
    	 if (mDrawerToggle.onOptionsItemSelected(item)) {
             return true;
         }
    	 Intent i;
 		switch(item.getItemId())
 		{
 		case R.id.item1 : 
 			 i = new Intent(Stats.this,MainActivity.class );
 			al.clear();
 			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
 			startActivity(i);
 			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
 			finish();
 			break;
 		}
            return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           if(position==al.size()-1){
        		Intent i = new Intent(Stats.this,MainActivity.class );
        		al.clear();
     			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
     			startActivity(i);
     			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
     			finish();
           }
           else{
        	selectItem(position);
           }
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(al.get(position));
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
       public static final String ARG_PLANET_NUMBER = "planet_number";
      // static ArrayList<Integer> totalstudents= new ArrayList<Integer>();
      // static ArrayList<Integer> totalattendance= new ArrayList<Integer>();

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.statsfragment, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            if(i==(al.size()-2)){
                /*  String sub = subject.get(i);
                  int sem = semister.get(i);*/
                 try{ 
                 /* int size=al.size();*/
                  //System.out.println(size);
                  int[] attendance=  new int[flag];
                  int[] lect = new int[flag];
                  int[] totalstudents = new int[flag];
                  String classnames="";
                 // int x=0;
                 
                  for(int x=0;x<flag;x++){
              try{ 
                    String classname = al.get(x);
                    classnames=classnames+classname+",";
                    lect[x]= totallect.get(x);
                 	SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
                 	Cursor resultSet = mydatabase.rawQuery("Select totalattendance from "+ classname ,null);
                 	totalstudents[x] = resultSet.getCount();
                 	attendance[x]=0;
                 	while(resultSet.moveToNext())
                 	{
                 		attendance[x]=attendance[x]+resultSet.getInt(0);
                 	}
                     mydatabase.close();
                     System.out.println(classname +"  "+lect[x]+"  "+ totalstudents[x]+" "+attendance[x]);
             
                  }
                  catch(Exception e){
                	  System.out.println("Error"+e.toString());
                  }
                     
                  }//end of while 1
                  
                  //System.out.println(classname +" "+sub+" "+lect+" "+sem+" "+ totalstudents+" "+attendance);
                  float[] avgatt= new float[(flag)];
                  for(int k=0;k<flag;k++){
                		avgatt[k]= (float)(((double)attendance[k])/(double)(totalstudents[k]*(double)lect[k]))*100;
                  }
                  getActivity().setTitle("Compare All Classes");
                  BarChart chart = (BarChart) rootView.findViewById(R.id.chart);
          		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
          		ArrayList<String> labels = new ArrayList<String>();
          		int l=0;
          		for(int k=0;k<flag;k++){
          		/*entries.add(new BarEntry(totalstudents[k], l));
          		l++;
          		entries.add(new BarEntry(lect[k], l));
          		l++;*/
          		entries.add(new BarEntry(avgatt[k], l));
          		labels.add(al.get(k)); 
          		l++;
          		/*labels.add("Total Students"); 
          		labels.add("Total Lectures"); */
          		
          		}
          		/*entries.add(new BarEntry(attendance, 3));
          		entries.add(new BarEntry(18f, 4));
          		entries.add(new BarEntry(9f, 5));
          		entries.add(new BarEntry(16f, 6));
          		entries.add(new BarEntry(3f, 7));
          		entries.add(new BarEntry(9f, 8));
          		entries.add(new BarEntry(14f, 9));
          		entries.add(new BarEntry(19f, 10));
          		entries.add(new BarEntry(10f, 11));*/
          		
          		BarDataSet dataset = new BarDataSet(entries, classnames );
          		
          	/*	
          		labels.add("Attendance"); 
          		labels.add("May");
          		labels.add("Jun");
          		labels.add("Jul");
          		labels.add("Aug");
          		labels.add("Sept");
          		labels.add("Oct");
          		labels.add("Nov");
          		labels.add("Dec");*/
          		
          		BarData data = new BarData(labels, dataset);
          		chart.setData(data);
          		chart.setDescription("Average Attendance Percentage.");
          		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
          		//chart.animateY(5000);
          	    chart.animateXY(2000, 2000);
          	    chart.invalidate();
          		/*LimitLine line = new LimitLine(10f);
          		data.addLimitLine(line);*/
                 }catch(Exception e)
                 {
                	 System.out.println("Error"+e.toString()); 
                 }
            }
            
            else{
            String classname = al.get(i);
            String sub = subject.get(i);
            int lect = totallect.get(i);
            int sem = semister.get(i);
            int totalstudents = 0;
            int attendance= 0;
            try{ 
           	 SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
           	Cursor resultSet = mydatabase.rawQuery("Select totalattendance from "+ classname ,null);
           	 totalstudents = resultSet.getCount();
           	while(resultSet.moveToNext()){
           		attendance=attendance+resultSet.getInt(0);
          
               }
               mydatabase.close();
            }
            catch(Exception e){
            	System.out.println("Error"+e.toString());
            }
            System.out.println(classname +" "+sub+" "+lect+" "+sem+" "+ totalstudents+" "+attendance);
            float avgatt=(float)(((double)attendance)/(double)(totalstudents*(double)lect))*100;

          /*  int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());*/
            /*((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);*/
            getActivity().setTitle(classname+" Semister-"+sem);
            BarChart chart = (BarChart) rootView.findViewById(R.id.chart);
    		ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    		entries.add(new BarEntry(totalstudents, 0));
    		entries.add(new BarEntry(lect, 1));
    		entries.add(new BarEntry(avgatt, 2));
    		/*entries.add(new BarEntry(attendance, 3));*/
    		/*entries.add(new BarEntry(18f, 4));
    		entries.add(new BarEntry(9f, 5));
    		entries.add(new BarEntry(16f, 6));
    		entries.add(new BarEntry(3f, 7));
    		entries.add(new BarEntry(9f, 8));
    		entries.add(new BarEntry(14f, 9));
    		entries.add(new BarEntry(19f, 10));
    		entries.add(new BarEntry(10f, 11));*/
    		
    		BarDataSet dataset = new BarDataSet(entries, "Total Students , Lectures , Avg Attendance");
    		ArrayList<String> labels = new ArrayList<String>();
    		labels.add("Students"); 
    		labels.add("Lectures"); 
    		labels.add("Avg. Attendance"); 
    		/*labels.add("Attendance"); */
    		/*labels.add("May");
    		labels.add("Jun");
    		labels.add("Jul");
    		labels.add("Aug");
    		labels.add("Sept");
    		labels.add("Oct");
    		labels.add("Nov");
    		labels.add("Dec");*/
    		
    		BarData data = new BarData(labels, dataset);
    		chart.setData(data);
    		chart.setDescription("Class "+classname+" Stats");
    		dataset.setColors(ColorTemplate.COLORFUL_COLORS);
    		//chart.animateY(5000);
    	    chart.animateXY(2000, 2000);
    	    chart.invalidate();
    		/*LimitLine line = new LimitLine(10f);
    		data.addLimitLine(line);*/
            }
            
           
            return rootView;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent  i = new Intent(Stats.this,MainActivity.class );
 			al.clear();
 			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
 			startActivity(i);
 			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
 			finish();
 			return true;
        }
    return false;
    }
}