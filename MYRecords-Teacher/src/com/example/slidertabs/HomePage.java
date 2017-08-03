package com.example.slidertabs;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends Fragment {
	SharedPreferences spref;
	 public static final String ARG_PAGE = "ARG_PAGE";

	    private int mPage;
	    private String name;

	    public static HomePage newInstance(int page) {
	        Bundle args = new Bundle();
	        args.putInt(ARG_PAGE, page);
	        HomePage fragment = new HomePage();
	        fragment.setArguments(args);
	        return fragment;
	    }

	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mPage = getArguments().getInt(ARG_PAGE);
	    	spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(getActivity());
	    	name= spref.getString("name","val");
	       
	    }


	    // Inflate the fragment layout we defined above for this fragment
	    // Set the associated text for the title
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View view = inflater.inflate(R.layout.activity_home_page, container, false);
	        Button btnstats;
	        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
	        tvTitle.setText("Welcome \n" + name);
	        btnstats=(Button)view.findViewById(R.id.button1);
	       
	        btnstats.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i = new Intent(getActivity(),Stats.class );
					i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					startActivity(i);
					getActivity().overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
					getActivity().finish();
					
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
