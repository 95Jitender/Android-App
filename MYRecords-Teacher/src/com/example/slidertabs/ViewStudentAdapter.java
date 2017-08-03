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
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ViewStudentAdapter extends ArrayAdapter<sqlquery>{
	Context context;
	sqlquery[] sq = null;
	static ArrayList<String> selectedStrings = new ArrayList<String>();
	 public ViewStudentAdapter(Context context, sqlquery[] resource , int rowcount)
	 {
	 super(context,R.layout.studentnamerow,resource);
	 // TODO Auto-generated constructor stub
	 this.context = context;
	 this.sq = resource;
	 }
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	 // TODO Auto-generated method stub
	 
		 LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	 convertView = inflater.inflate(R.layout.studentnamerow, parent, false); 
	final TextView name = (TextView) convertView.findViewById(R.id.textView1);
	final TextView rollno = (TextView) convertView.findViewById(R.id.textView2);
	 rollno.setText(sq[position].getValue());
	 name.setText(sq[position].getName());
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
