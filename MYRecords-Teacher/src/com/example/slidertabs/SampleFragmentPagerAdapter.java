package com.example.slidertabs;

import java.util.HashMap;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
	// final int PAGE_COUNT = 4;
	  
	    private String tabTitles[] = new String[] { "Home", "Create Class", "Search","Class List" };
	  
	    private Context mcontext;
	    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
	        super(fm);
	        mcontext = context;
	        
	    }

	    @Override
	    public int getCount() {
	       // return PAGE_COUNT;
	    	return tabTitles.length;
	    }

	    @Override
	    public Fragment getItem(int position) {
	        if(position==0){
	        	return HomePage.newInstance(position + 1);
	    
	        }
	        
	        else if(position==1) {
	        	 return CreateClass.newInstance(position + 1);
	        }
	        
	        else if(position==2) {
	        	return Search.newInstance(position + 1);
	        	
	        }
			
	        else {
	        	return ClassList.newInstance(position + 1);
	        	
	        }
			

	    }

	    @Override
	    public CharSequence getPageTitle(int position) {
	        // Generate title based on item position
	        return tabTitles[position];
	    }
	
}
