package com.example.slidertabs;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapterAttendanceRange extends BaseAdapter
{
   private ArrayList<String> listCountry;
   private Activity activity;
  // Context context;
   public GridViewAdapterAttendanceRange(Activity activity,ArrayList<String> listCountry) {
       super();
       this.listCountry = listCountry;
       this.activity = activity;
       
    //   this.context= context;
   }

   @Override
   public int getCount() {
       // TODO Auto-generated method stub
       return listCountry.size();
   }

   @Override
   public String getItem(int position) {
       // TODO Auto-generated method stub
       return listCountry.get(position);
   }

   @Override
   public long getItemId(int position) {
       // TODO Auto-generated method stub
       return position;
   }

   public static class ViewHolder
   {
      // public ImageView imgViewFlag;
       public TextView txtViewTitle;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
       // TODO Auto-generated method stub
       ViewHolder view;
       LayoutInflater inflator = activity.getLayoutInflater();
       Animation animation;
       animation = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
       if(convertView==null)
       {
           view = new ViewHolder();
           convertView = inflator.inflate(R.layout.grid_row_attrange, null);

           view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
         //  view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

           convertView.setTag(view);
       }
       else
       {
           view = (ViewHolder) convertView.getTag();
       }

       view.txtViewTitle.setText(listCountry.get(position));
       view.txtViewTitle.startAnimation(animation);
      // view.imgViewFlag.setImageResource(listFlag.get(position));

       return convertView;
   }
}