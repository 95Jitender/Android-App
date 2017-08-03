package com.example.slidertabs;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class CreateClass extends Fragment {

	 public static final String ARG_PAGE = "ARG_PAGE";

	    private int mPage;
	    ViewPager viewPager;
	    public static CreateClass newInstance(int page) {
	        Bundle args = new Bundle();
	        args.putInt(ARG_PAGE, page);
	        CreateClass fragment = new CreateClass();
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
	        View view = inflater.inflate(R.layout.activity_create_class, container, false);
	       // TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
	       // tvTitle.setText("Fragment #" + mPage);
	      final  EditText edt,edt2,edt3,edt4;
	        Button btn;
	     
	       edt=(EditText)view.findViewById(R.id.editText1);
		   edt2=(EditText)view.findViewById(R.id.editText2);
		   edt3=(EditText)view.findViewById(R.id.editText3);
		   edt4=(EditText)view.findViewById(R.id.editText4);
		   btn=(Button)view.findViewById(R.id.button1);
		viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
		
		 edt.setOnFocusChangeListener(new View.OnFocusChangeListener(){
			 int x=1;
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus){
		        if(x==1){edt.setHint("Enter Class Name"); x=2;}
		        else{edt.setHint("");}
		        	}
		else
		        edt.setHint("Enter Class Name");
		      }
		    });
		 
		 edt2.setOnFocusChangeListener(new View.OnFocusChangeListener(){
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus)
		{ edt2.setHint("");}
		else
		        edt2.setHint("Enter Subject");
		      }
		    });
		 
		 edt3.setOnFocusChangeListener(new View.OnFocusChangeListener(){
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus)
		{ edt3.setHint("");}
		else
		        edt3.setHint("Enter Semester");
		      }
		    });
		 
		 edt4.setOnFocusChangeListener(new View.OnFocusChangeListener(){
		      public void onFocusChange(View v, boolean hasFocus){
		if(hasFocus)
		{ edt4.setHint("");}
		else
		        edt4.setHint("Batch Year");
		      }
		    });
		 
		 edt4.setOnKeyListener(new View.OnKeyListener() {
		        public boolean onKey(View v, int keyCode, KeyEvent event) {
		            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
		        // Toast.makeText(getActivity(),"CLICKED", Toast.LENGTH_SHORT).show();
		    	String tbname=edt.getText().toString();
				String subject=edt2.getText().toString();
			    String semister=edt3.getText().toString();
			    String batchyear=edt4.getText().toString();
						InputMethodManager inputManager = (InputMethodManager)
		                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 

		inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
		                           InputMethodManager.HIDE_NOT_ALWAYS);
						if(tbname.equals(null) || tbname.equals("")||subject.equals(null) 
								|| subject.equals("")||semister.equals(null) || 
								semister.equals("")||batchyear.equals(null) || batchyear.equals(""))
						{
							Toast.makeText(getActivity(),"All Fill Details First.",Toast.LENGTH_LONG).show();
						}
						
						else
						{
							try{
							    SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",android.content.Context.MODE_PRIVATE,null);
							    mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS "+ tbname +" ( name TEXT , rollno TEXT PRIMARY KEY NOT NULL , contact TEXT ,class TEXT ,totalattendance INTEGER DEFAULT 0 );");
							    String newtable=tbname+"_markstable";
							    mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS "+ newtable +" ( name TEXT , rollno TEXT PRIMARY KEY NOT NULL );");
							    mydatabase.execSQL("INSERT INTO classesnameslist VALUES('"+tbname+"','0','"+subject+"','"+semister+"','"+batchyear+"');");
								mydatabase.close();
							    Toast.makeText(getActivity(),"Class " + edt.getText().toString() + " Created. ",Toast.LENGTH_LONG).show();
								edt.setText("");
								/*Intent i = new Intent(getActivity(),PageFragment.class);
								i.putExtra("classname",tbname);
								i.putExtra("noofstu",noofstu);
								startActivity(i);*/
								SharedPreferences spref;
								spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(getActivity());
								SharedPreferences.Editor spe = spref.edit();
								spe.putInt(tbname,0);
								spe.commit();	
								 viewPager.setCurrentItem(0);
							}
								catch(Exception e){
								Toast.makeText(getActivity(),"Unable To Create Class "+e,Toast.LENGTH_LONG).show();
								}
								
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
					String tbname=edt.getText().toString();
				    String subject=edt2.getText().toString();
				    String semister=edt3.getText().toString();
				    String batchyear=edt4.getText().toString();
					InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
					inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
					if(tbname.equals(null) || tbname.equals("")||subject.equals(null) || subject.equals("")
							||semister.equals(null) || semister.equals("")||batchyear.equals(null) || batchyear.equals(""))
					{
						Toast.makeText(getActivity(),"All Fill Details First.",Toast.LENGTH_LONG).show();
					}
					
					else
					{
						try{
						    SQLiteDatabase mydatabase = getActivity().openOrCreateDatabase("Student.db",android.content.Context.MODE_PRIVATE,null);
						    mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS "+ tbname +" ( name TEXT , rollno TEXT PRIMARY KEY NOT NULL , contact TEXT ,class TEXT ,totalattendance INTEGER DEFAULT 0 );");
						    String newtable=tbname+"_markstable";
						    mydatabase.execSQL(" CREATE TABLE IF NOT EXISTS "+ newtable +" ( name TEXT , rollno TEXT PRIMARY KEY NOT NULL );");
						    mydatabase.execSQL("INSERT INTO classesnameslist VALUES('"+tbname+"','0','"+subject+"','"+semister+"','"+batchyear+"');");
							mydatabase.close();
						    Toast.makeText(getActivity(),"Class " + edt.getText().toString() + " Created. ",Toast.LENGTH_LONG).show();
							edt.setText("");
							/*Intent i = new Intent(getActivity(),PageFragment.class);
							i.putExtra("classname",tbname);
							i.putExtra("noofstu",noofstu);
							startActivity(i);*/
							SharedPreferences spref;
							spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(getActivity());
							SharedPreferences.Editor spe = spref.edit();
							spe.putInt(tbname,0);
							spe.commit();	
							 viewPager.setCurrentItem(0);
						}
							catch(Exception e){
							Toast.makeText(getActivity(),"Unable To Create Class "+e,Toast.LENGTH_LONG).show();
							}
							
					}
					
				}
			});
			
	        return view;
	    }
/*
		@Override
		public void onDestroyView() {
			// TODO Auto-generated method stub
			super.onDestroyView();
			InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); 
			inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
		}
	    */

}
