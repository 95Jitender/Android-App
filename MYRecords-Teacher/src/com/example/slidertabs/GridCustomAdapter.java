package com.example.slidertabs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

class GridCustomAdapter extends BaseAdapter{
	String classname;
    String [] result;
    Context context;
 int [] imageId;
   private static LayoutInflater inflater=null;
    public GridCustomAdapter(Context context, String[] prgmNameList, int[] prgmImages,String classname) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context=context;
        imageId=prgmImages;
        this.classname=classname;
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

             rowView = inflater.inflate(R.layout.gridlayout, null);
             holder.tv=(TextView) rowView.findViewById(R.id.textView1);
             holder.img=(ImageView) rowView.findViewById(R.id.imageView1);

         holder.tv.setText(result[position]);
         holder.img.setImageResource(imageId[position]);
         
        // holder.img.setLayoutParams(new GridView.LayoutParams(300,300 ));
        // holder.img.setPadding(8,8,8,8);

         /*rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               // Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_LONG).show();
                switch(position){
                case 0 : Intent i = new Intent(context,AddRecord.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();break;
                case 1 : break;
                }
            }
        });*/

        return rowView;
    }

} 