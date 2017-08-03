package com.example.slidertabs;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;


import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ClassList extends Fragment {
int flag;
	 public static final String ARG_PAGE = "ARG_PAGE";
	    public static ClassList newInstance(int page) {
	        Bundle args = new Bundle();
	        args.putInt(ARG_PAGE, page);
	        ClassList fragment = new ClassList();
	        fragment.setArguments(args);
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	      
	       
	    }

	    // Inflate the fragment layout we defined above for this fragment
	    // Set the associated text for the title
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.activity_class_list, container, false);
	        final ListView lv;
	        final ArrayList<String> al,al2;
	        SharedPreferences spref;
	        spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(getActivity());
		    flag = spref.getInt("DisplayClassActivity",0);
	        lv=(ListView)view.findViewById(R.id.listView1);
			SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",android.content.Context.MODE_PRIVATE,null);
			Cursor resultSet = mydatabase.rawQuery("Select * from classesnameslist ",null);
			al= new ArrayList<String>();
			al2= new ArrayList<String>();
			while(resultSet.moveToNext())
			{	al.add(resultSet.getString(0));
				al2.add("Class : "+resultSet.getString(0)+"\nSubject : "+resultSet.getString(2)+"\nSemister : "+resultSet.getString(3));
			}
			ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,al2);
			lv.setAdapter(adp);
			mydatabase.close();
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,
						long id) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(),"You Clicked on : "+ al.get(pos), Toast.LENGTH_LONG).show();
					if(flag==0){Intent i= new Intent(getActivity(),DisplayClass.class);
					i.putExtra("classname",al.get(pos));
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					getActivity().overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					}
					else if(flag==1){
						Intent i= new Intent(getActivity(),DisplayClassGrid.class);
						i.putExtra("classname",al.get(pos));
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						startActivity(i);
						getActivity().overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					}
					/*Animation anim = AnimationUtils.loadAnimation(
		     	                getActivity(),R.anim.fade_in
		     	            );
		     			anim.setDuration(500);
		     	    i.startAnimation(anim);*/
					
				}
			});
			
	        return view;
	    }
	/*	public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
			inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		}  */
		
		
}
