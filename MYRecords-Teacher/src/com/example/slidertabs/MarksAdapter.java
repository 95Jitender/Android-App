package com.example.slidertabs;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;


public class MarksAdapter extends ArrayAdapter<sqlquery> {
	Context context;
	sqlquery[] sq = null;
	private final double[] mCheckedState;
    static double[] mCheckedvalue;
    static String[] mroll;
	 public MarksAdapter(Context context, sqlquery[] resource , int rowcount)
	 {
	 super(context,R.layout.marksrow,resource);
	 // TODO Auto-generated constructor stub
	 this.context = context;
	 this.sq = resource;
	 mCheckedState = new double[rowcount];
	 mCheckedvalue = new double[rowcount];
	 mroll= new String[rowcount];
	 }
	 @Override
	 public View getView(final int position, View convertView, ViewGroup parent) {
	 // TODO Auto-generated method stub
	 
	LayoutInflater inflater = ((Activity)context).getLayoutInflater();
	convertView = inflater.inflate(R.layout.marksrow, parent, false); 
	final TextView name = (TextView) convertView.findViewById(R.id.textView1);
	final EditText edt = (EditText) convertView.findViewById(R.id.editText1);
	name.setText(sq[position].getName()+"\n"+sq[position].getValue());
try{
	edt.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			//viewid.add(edt.getId()+"");
			//marksvalue.add(s.toString());
			if((edt.getText().toString()).equals("") || (edt.getText().toString()).equals(null)){
			mCheckedState[position]=edt.getId();
			mCheckedvalue[position]=0.0;
					//Double.parseDouble(edt.getText().toString());
			mroll[position]=sq[position].getValue();
	       }
			else{
				mCheckedState[position]=edt.getId();
				mCheckedvalue[position]=Double.parseDouble(edt.getText().toString());
				mroll[position]=sq[position].getValue();	
			}
			
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {// TODO Auto-generated method stub	
		}
		
		@Override
		public void afterTextChanged(Editable s) {// TODO Auto-generated method stub
		}
	});
	

		if(mCheckedState[position]==edt.getId())
		{
			
			edt.setText(mCheckedvalue[position]+"");
			//mCheckedvalue[position]=0.0;
		}
	}catch(Exception e){System.out.println(e);}
	Animation anim = AnimationUtils.loadAnimation(
            context,R.anim.push_left_in
        );
	anim.setDuration(500);
	convertView.startAnimation(anim);
	  return convertView;
	 }
	 
	static double[] getmarks(){ return mCheckedvalue;	}
	static String[] getroll(){ return mroll; }

}
