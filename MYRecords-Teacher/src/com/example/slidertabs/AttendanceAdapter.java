package com.example.slidertabs;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;


public class AttendanceAdapter extends ArrayAdapter<sqlquery> {
	Context context;
	sqlquery[] sq = null;
	static ArrayList<String> selectedStrings = new ArrayList<String>();
	private final boolean[] mCheckedState;
	 public AttendanceAdapter(Context context, sqlquery[] resource , int rowcount)
	 {
	 super(context,R.layout.attendancerow,resource);
	 // TODO Auto-generated constructor stub
	 this.context = context;
	 this.sq = resource;
	 mCheckedState = new boolean[rowcount];
	 }
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	 // TODO Auto-generated method stub
	 
		 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	 convertView = inflater.inflate(R.layout.attendancerow, parent, false); 
	final TextView name = (TextView) convertView.findViewById(R.id.textView1);
	final CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
	
	 cb.setId(position);
	 cb.setText(sq[position].getValue());
	 name.setText(sq[position].getName());
	if(mCheckedState[cb.getId()]==true)
	{
		cb.setChecked(true);
	}
	cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if (isChecked) {
                 selectedStrings.add(cb.getText().toString());
                 mCheckedState[cb.getId()]=true;
             }else{
                 selectedStrings.remove(cb.getText().toString());
                 mCheckedState[cb.getId()]=false;
             }

         }
         		
     });
	Animation anim = AnimationUtils.loadAnimation(
            context,R.anim.push_left_in
        );
	anim.setDuration(500);
	convertView.startAnimation(anim);
	  return convertView;
	 }
	 
	static ArrayList<String> getSelectedString(){
	   	  return selectedStrings;
	   	}

}
