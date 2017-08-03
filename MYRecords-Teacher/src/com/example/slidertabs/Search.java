package com.example.slidertabs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Search extends Fragment {

	 public static final String ARG_PAGE = "ARG_PAGE";

	    private int mPage;
	   
	    
	    public static Search newInstance(int page) {
	        Bundle args = new Bundle();
	        args.putInt(ARG_PAGE, page);
	        Search fragment = new Search();
	        fragment.setArguments(args);
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mPage = getArguments().getInt(ARG_PAGE);
	       
	    }


	    // Inflate the fragment layout we defined above for this fragment
	    // Set the associated text for the title
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	       View view = inflater.inflate(R.layout.activity_search, container, false);
	       final EditText edt1;
	       final  Button search;
	       final  ListView lv;
	       final TextView txt;	       
	       ArrayList<String> al,al1,al2,al3;
	       edt1=(EditText)view.findViewById(R.id.editText1);
	       search=(Button)view.findViewById(R.id.button1);
	       lv=(ListView)view.findViewById(R.id.listView1);
	       txt=(TextView)view.findViewById(R.id.textView1);
	       txt.setText("Nothing To Display Yet.\n Search Student By Name.");
	       edt1.setOnKeyListener(new View.OnKeyListener() {
	           public boolean onKey(View v, int keyCode, KeyEvent event) {
	               if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
	                   //Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
	   				try{	InputMethodManager inputManager = (InputMethodManager)
	                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 

	inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
	                           InputMethodManager.HIDE_NOT_ALWAYS);
						ArrayAdapter<String> adp;
						String classname;
						String search=edt1.getText().toString().trim();
						final ArrayList<String> al= new ArrayList<String>();
						final ArrayList<String> al1= new ArrayList<String>();
						final ArrayList<String> al2= new ArrayList<String>();
						final ArrayList<String> al3= new ArrayList<String>();
						if ( search.equals(null)||search.equals("") )
						{
							txt.setText("Search Field Cannnot Be Empty.");
							edt1.setText("");
							adp= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,al);
							lv.setAdapter(adp);	
							adp.notifyDataSetChanged();
							
						}
						else{
						
						SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",android.content.Context.MODE_PRIVATE,null);
						Cursor resultSet = mydatabase.rawQuery("Select * from classesnameslist ",null);
						while(resultSet.moveToNext())
						{int totallect= resultSet.getInt(1);
						classname=resultSet.getString(0);
						Cursor resultSet2 = mydatabase.rawQuery("Select * from "+classname+" Where name LIKE  '%"+search+"%' OR rollno LIKE  '%"+search+"%'" ,null);
						while(resultSet2.moveToNext())
						{
							al.add("Name : "+resultSet2.getString(0)+"\nRoll No. : "+resultSet2.getString(1)+"\nClass : "+resultSet2.getString(3)
									+"\nContact : "+resultSet2.getString(2)+"\nTotal Attendance : "+resultSet2.getString(4)+"\nTotal Lectures : "+totallect);
							al1.add(resultSet2.getString(0));
							al2.add(resultSet2.getString(1));
							al3.add(resultSet2.getString(3));
						}//end of inner while
						totallect=0;	
						}//end of outer while
						mydatabase.close();
						if(al.isEmpty()==true){
							txt.setText("No Results Found");
						}
						else {
							txt.setText("");
						}
						adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,al);
						Animation anim = AnimationUtils.loadAnimation(
				                getActivity(),R.anim.push_left_in
				            );
						anim.setDuration(500);
						lv.setAdapter(adp);	
					    lv.startAnimation(anim);
					    lv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent, View v, int pos,
									long id) {
								// TODO Auto-generated method stub
								Toast.makeText(getActivity(),""+ al1.get(pos), Toast.LENGTH_LONG).show();
								Intent i= new Intent(getActivity(),EachStudent.class);
								i.putExtra("studentname",al1.get(pos));
								i.putExtra("rollno",al2.get(pos));
								i.putExtra("classname",al3.get(pos));
								i.putExtra("comefrom","search");
								i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
								startActivity(i);
								getActivity().overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
								/*Animation anim = AnimationUtils.loadAnimation(
					     	                getActivity(),R.anim.fade_in
					     	            );
					     			anim.setDuration(500);
					     	    i.startAnimation(anim);*/
								
							}
					    
						});
					}
						//convertView.startAnimation(anim);
					}
					catch(Exception e){
						Toast.makeText(getActivity(),"Error : "+e, Toast.LENGTH_LONG).show();
					}
	                   return true;
	               }
	               return false;
	           }
	       });
	       search.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub				
				try{	InputMethodManager inputManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 

inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                           InputMethodManager.HIDE_NOT_ALWAYS);
					ArrayAdapter<String> adp;
					String classname;
					String search=edt1.getText().toString().trim();
					final ArrayList<String> al= new ArrayList<String>();
					final ArrayList<String> al1= new ArrayList<String>();
					final ArrayList<String> al2= new ArrayList<String>();
					final ArrayList<String> al3= new ArrayList<String>();
					if ( search.equals(null)||search.equals("") )
					{
						txt.setText("Search Field Cannnot Be Empty.");
						edt1.setText("");
						adp= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,al);
						lv.setAdapter(adp);	
						adp.notifyDataSetChanged();
						
					}
					else{
					
					SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",android.content.Context.MODE_PRIVATE,null);
					Cursor resultSet = mydatabase.rawQuery("Select * from classesnameslist ",null);
					while(resultSet.moveToNext())
					{int totallect= resultSet.getInt(1);
					classname=resultSet.getString(0);
					Cursor resultSet2 = mydatabase.rawQuery("Select * from "+classname+" Where name LIKE  '%"+search+"%'",null);
					while(resultSet2.moveToNext())
					{
						al.add("Name : "+resultSet2.getString(0)+"\nRoll No. : "+resultSet2.getString(1)+"\nClass : "+resultSet2.getString(3)
								+"\nContact : "+resultSet2.getString(2)+"\nTotal Attendance : "+resultSet2.getString(4)+"\nTotal Lectures : "+totallect);
						al1.add(resultSet2.getString(0));
						al2.add(resultSet2.getString(1));
						al3.add(resultSet2.getString(3));
					}//end of inner while
					totallect=0;	
					}//end of outer while
					mydatabase.close();
					if(al.isEmpty()==true){
						txt.setText("No Results Found");
					}
					else {
						txt.setText("");
					}
					adp = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,al);
					Animation anim = AnimationUtils.loadAnimation(
			                getActivity(),R.anim.push_left_in
			            );
					anim.setDuration(500);
					lv.setAdapter(adp);	
				    lv.startAnimation(anim);
				    lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent, View v, int pos,
								long id) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(),""+ al1.get(pos), Toast.LENGTH_LONG).show();
							Intent i= new Intent(getActivity(),EachStudent.class);
							i.putExtra("studentname",al1.get(pos));
							i.putExtra("rollno",al2.get(pos));
							i.putExtra("classname",al3.get(pos));
							i.putExtra("comefrom","search");
							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
							startActivity(i);
							getActivity().overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
							/*Animation anim = AnimationUtils.loadAnimation(
				     	                getActivity(),R.anim.fade_in
				     	            );
				     			anim.setDuration(500);
				     	    i.startAnimation(anim);*/
							
						}
				    
					});
				}
					//convertView.startAnimation(anim);
				}
				catch(Exception e){
					Toast.makeText(getActivity(),"Error : "+e, Toast.LENGTH_LONG).show();
				}
				
				}
				
			});

		
	        return view;
	        
	    }
/*		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
			inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		}*/

}
