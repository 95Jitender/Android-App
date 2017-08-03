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
import android.widget.TextView;

public class ViewAttendanceAdapter  extends ArrayAdapter<String> {
	Context context;
	ArrayList<String> al;
	public ViewAttendanceAdapter(Context context, ArrayList<String> al)
	 {
	 super(context,R.layout.viewattendancerow,al);
	 // TODO Auto-generated constructor stub
	 this.context = context;
	 this.al = al;
	 }
	
	 public View getView(int position, View convertView, ViewGroup parent) {
		 // TODO Auto-generated method stub
		 
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	    convertView = inflater.inflate(R.layout.viewattendancerow, parent, false); 
		final TextView attendance = (TextView) convertView.findViewById(R.id.textView1);
		attendance.setText(al.get(position));
		 Animation anim = AnimationUtils.loadAnimation(
	            context,R.anim.push_left_in
	        );
		anim.setDuration(500);
		convertView.startAnimation(anim);
		  return convertView;
		 }
}
