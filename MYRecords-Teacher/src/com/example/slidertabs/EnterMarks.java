package com.example.slidertabs;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EnterMarks extends Activity {
String classname,colname,maxmarks,subject;
String[] rollnos;
sqlquery sq[];
TextView txt;
Button btn;
ListView lv;
double marks[]; int row,batchyear,semister;
SQLiteDatabase mydatabase;
int testnameflag=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_enter_marks);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		    colname=b.getString("colname");
		    maxmarks=b.getString("maxmarks");
		}
		catch(Exception e){
			Toast.makeText(EnterMarks.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		txt=(TextView)findViewById(R.id.textView1);
		txt.setText(classname);
		btn=(Button)findViewById(R.id.button1);
		lv = (ListView) findViewById(R.id.listView1);
		
		mydatabase  = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		Cursor resultSet2 = mydatabase.rawQuery("Select subject,semister,batchyear from classesnameslist where classname='"+classname+"' ",null);
		Cursor resultSet = mydatabase.rawQuery("Select * from "+classname+"_markstable Order by rollno ",null);
		row=resultSet.getCount();
		resultSet2.moveToNext();
		subject=resultSet2.getString(0);
		semister=resultSet2.getInt(1);
		batchyear=resultSet2.getInt(2);
		marks= new double[row];
		rollnos = new String[row];
		sq = new sqlquery[row];
		int i=0;
		while(resultSet.moveToNext())
			{
			sq[i]=new sqlquery(resultSet.getString(0),resultSet.getString(1));
			i++;
			}
		MarksAdapter adapter = new MarksAdapter(this,sq,row);
 	 lv.setAdapter(adapter);
 	 lv.smoothScrollToPosition(adapter.getCount());
 	 btn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try{
			System.out.println(colname+" "+maxmarks);
			Cursor resultSet = mydatabase.rawQuery("Select `testname` from marksinfo where classname='"+classname+"' ",null);
			while(resultSet.moveToNext())
			{
				if(resultSet.getString(0).equalsIgnoreCase(colname))  { testnameflag=1; break;}
				else {testnameflag=0;} 
			}
			if(testnameflag==1) {Toast.makeText(getApplicationContext(), "Marks of "+colname+ " already stored.", Toast.LENGTH_LONG).show();}
			else{
			mydatabase.execSQL("INSERT INTO marksinfo VALUES('"+classname+"','"+subject+"','"+semister+"','"+batchyear+"','"+colname+"','"+maxmarks+"');");
			mydatabase.execSQL( "ALTER TABLE "+ classname +"_markstable ADD COLUMN "+ colname +" REAL DEFAULT '0.0' ; " );
		 	 System.arraycopy(MarksAdapter.getmarks(), 0, marks, 0, row);
		 	 System.arraycopy(MarksAdapter.getroll(), 0, rollnos, 0, row);
		 	 for(int j=0;j<row;j++) {System.out.println(rollnos[j]+" -"+marks[j]);
		 	 mydatabase.execSQL("UPDATE "+classname+"_markstable SET "+colname+" ='"+marks[j]+"'  WHERE rollno='"+rollnos[j]+"'");
		 	 }
		 	Toast.makeText(getApplicationContext(), "Marks Stored..", Toast.LENGTH_LONG).show();
		 	Intent i= new Intent(EnterMarks.this,MarksDetails.class);
			i.putExtra("classname",classname);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(i);
			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
			finish();
			}
			}catch(Exception e) {Toast.makeText(getApplicationContext(), "Error : "+e, Toast.LENGTH_LONG).show();}
		}
	});

 	//selectedstrings=AttendanceAdapter.getSelectedString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_marks, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {Intent i= new Intent(EnterMarks.this,MarksDetails.class);
		i.putExtra("classname",classname);
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
		    return true;
	    }
	return false;
	}
}
