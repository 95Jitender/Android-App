package com.example.slidertabs;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.example.slidertabs.AttendanceChart.CSVToExcelConverter;
import com.example.slidertabs.AttendanceChart.ExportDatabaseCSVTask;
import com.example.slidertabs.DisplayClassGrid.uploadinfo;
import com.example.slidertabs.DisplayClassGrid.uploadtable;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class ViewMarksChart extends Activity {
	TableLayout tb;
	String classname,colname;
	Button btnback,export;
	TextView txt;
	String csvfilename,excelfilename,time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_marks_chart);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		    colname=b.getString("colname");
		}
		catch(Exception e){
			Toast.makeText(ViewMarksChart.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
		txt=(TextView)findViewById(R.id.textView1);
		btnback=(Button)findViewById(R.id.button1);
		export=(Button)findViewById(R.id.button2);
		txt.setText(colname);
		
		try{BuildTable(); 
		} catch(Exception e){
			Toast.makeText(ViewMarksChart.this,"Error BuildTable() :"+e, Toast.LENGTH_LONG).show();
		}
		
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(ViewMarksChart.this,ViewAttendance.class);
				i.putExtra("classname",classname);
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				
			}
		});
      export.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				   new AlertDialog.Builder(ViewMarksChart.this)
				    .setTitle("Export...")
				    .setMessage("Do You Want To Export This Attendance To Excel Sheet?")
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	new ExportDatabaseCSVTask().execute();
						     new CSVToExcelConverter().execute();
				        }
				     })
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // do nothing
				        }
				     })
				    .setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				
			}
		});
		
	}
	 private void BuildTable() {
		 
		 SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
		 final Cursor resultSet = mydatabase.rawQuery("Select name,rollno,"+colname+" from "+classname+"_markstable Order by rollno",null);
		 final int cols = resultSet.getColumnCount();
		  tb=(TableLayout)findViewById(R.id.tableLayout1);		
		 // to Display Column Names
		 int x=1;
		while(x>0)
		{  TableRow rowcol = new TableRow(this);
		   rowcol.setLayoutParams(new  TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
		 for(int i=0;i<cols;i++)
		 {
			  
			   TextView tvcol = new TextView(this);
			   tvcol.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
			   tvcol.setBackgroundResource(R.drawable.cell_shape);
			   tvcol.setGravity(Gravity.CENTER);
			   tvcol.setTextSize(18);
			   tvcol.setPadding(10, 10, 10, 10);
			   tvcol.setText((resultSet.getColumnName(i)).toUpperCase());
			   tvcol.setTextColor(Color.parseColor("#ffffff"));
			   rowcol.addView(tvcol);
		 }
		 tb.addView(rowcol,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)); 
		x--;
		}
		
		//to display results
		
		  while(resultSet.moveToNext())
			{  
			  TableRow row = new TableRow(this);
			  row.setLayoutParams(new  TableRow.LayoutParams( TableRow.LayoutParams.MATCH_PARENT,
					  TableRow.LayoutParams.WRAP_CONTENT));
			  for (int j = 0; j < cols; j++)
			  {

				    TextView tv = new TextView(this);
				    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
				   tv.setBackgroundResource(R.drawable.cell_shape);
				   tv.setGravity(Gravity.CENTER);
				   tv.setTextSize(18);
				   tv.setPadding(10, 10, 10, 10);

				    tv.setText(resultSet.getString(j) + " ");
				    tv.setTextColor(Color.parseColor("#ffffff"));
				    row.addView(tv);

				   }
			  tb.addView(row,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
			  
			}
		  mydatabase.close();
	 }
	 
 public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>

	    	 {

	    	 private final ProgressDialog dialog = new ProgressDialog(ViewMarksChart.this);

	    	  @Override

	    	 protected void onPreExecute()

	    	 {

	    	     this.dialog.setMessage("Exporting MarksList...");

	    	     this.dialog.show();
	    	     this.dialog.setCancelable(false);

	    	 }



	    	 protected Boolean doInBackground(final String... args)

	    	 {


	    	     File dbFile=getDatabasePath("Student.db");
	    	     //AABDatabaseManager dbhelper = new AABDatabaseManager(getApplicationContext());
	    	     //AABDatabaseManager dbhelper = new AABDatabaseManager(AttendanceChart.this) ;
	    	     System.out.println(dbFile);  // displays the data base path in your logcat 

	    	     String folder_main = "MyRecords";
	    	     File exportDir = new File(Environment.getExternalStorageDirectory(), folder_main );        

	    	     if (!exportDir.exists()) 

	    	     {
	    	         exportDir.mkdirs();
	    	     }

	    			
	     		 String weekDay="",tms="";
	    				  Calendar c = Calendar.getInstance();
	    				     int day = c.get(Calendar.DATE);
	    				     int mon = c.get(Calendar.MONTH);
	    				     int year = c.get(Calendar.YEAR);
	    				     int min = c.get(Calendar.MINUTE);
	    				     int hr = c.get(Calendar.HOUR);
	    				     int tm = c.get(Calendar.HOUR_OF_DAY);
	    				     if(tm>=12&&tm<=24){tms="pm";}
	    				     else{tms="am";}
	    				 	
	    				   time=day+"_"+(mon+1)+"_"+year+"_"+hr+"_"+min+"_"+tms;
	    				   
	    	     File file = new File(exportDir, "MarksCSV_"+classname+"_Markslist"+time+".csv");
	    	     csvfilename = "MarksCSV_"+classname+"_Markslist"+time+".csv";

	    	     try

	    	     {

	    	         if (file.createNewFile()){
	    	             System.out.println("File is created!");
	    	             System.out.println("MarksCSV_"+classname+"_Markslist"+time+".csv"+file.getAbsolutePath());
	    	           }
	    	         else{
	    	             System.out.println("File already exists.");
	    	           }

	    	         CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
	    	       //SQLiteDatabase db = dbhelper.getWritableDatabase();
	    	         SQLiteDatabase db = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
	    	         Cursor curCSV= db.rawQuery("Select name , rollno , "+colname+" from "+classname+"_markstable Order by rollno" ,null);
	    	         int cols = curCSV.getColumnCount();
	    	         csvWrite.writeNext(curCSV.getColumnNames());
	    	         String[] arrStr = new String[cols];
	    	        /* StringBuilder arrStr = new StringBuilder(100);*/
	    	         
	    	         while(curCSV.moveToNext())

	    	         {
	    	        	 for(int i=0;i<cols;i++)
	    	             { arrStr[i]=curCSV.getString(i);
	    	               
	    	             }

	    	          /*curCSV.getString(3),curCSV.getString(4)};*/
	    	        	/* String arrStr[] ={curCSV.getString(0),curCSV.getString(1),curCSV.getString(2)};*/
	    	             csvWrite.writeNext(arrStr);
	    	             
	    	             


	    	         }

	    	         csvWrite.close();
	    	         curCSV.close();
	    	         /*String data="";
	    	         data=readSavedData();
	    	         data= data.replace(",", ";");
	    	         writeData(data);*/

	    	         return true;

	    	     }

	    	     catch(SQLException sqlEx)

	    	     {

	    	         Log.e("Markslist", sqlEx.getMessage(), sqlEx);

	    	         return false;

	    	     }

	    	     catch (IOException e)

	    	     {

	    	         Log.e("Markslist", e.getMessage(), e);

	    	         return false;

	    	     }

	    	 }

	    	 protected void onPostExecute(final Boolean success)

	    	 {

	    	     if (this.dialog.isShowing())

	    	     {

	    	         this.dialog.dismiss();

	    	     }

	    	     if (success)

	    	     {

	    	      /*   Toast.makeText(AttendanceChart.this, "Almost HalfWay", Toast.LENGTH_SHORT).show();*/

	    	     }

	    	     else

	    	     {

	    	         Toast.makeText(ViewMarksChart.this, "Unable To Build CSV File.", Toast.LENGTH_SHORT).show();

	    	     }
	    	 }}
	    	 
	    	   public class CSVToExcelConverter extends AsyncTask<String, Void, Boolean> {


	    		   private final ProgressDialog dialog = new ProgressDialog(ViewMarksChart.this);

	    		   @Override
	    		   protected void onPreExecute()
	    		   {this.dialog.setMessage("Exporting to Excel...");
	    		    this.dialog.show();
	    		    this.dialog.setCancelable(false);
	    		   }

	    		   @Override
	    		   protected Boolean doInBackground(String... params) {
	    		       ArrayList arList=null;
	    		       ArrayList al=null;

	    		       //File dbFile= new File(getDatabasePath("database_name").toString());
	    		       File dbFile=getDatabasePath("Student.db");
	    		       String yes= dbFile.getAbsolutePath();
	    		       String tms="";
	    				  Calendar c = Calendar.getInstance();
	    				     int day = c.get(Calendar.DATE);
	    				     int mon = c.get(Calendar.MONTH);
	    				     int year = c.get(Calendar.YEAR);
	    				     int min = c.get(Calendar.MINUTE);
	    				     int hr = c.get(Calendar.HOUR);
	    				     int tm = c.get(Calendar.HOUR_OF_DAY);
	    				     if(tm>=12&&tm<=24){tms="pm";}
	    				     else{tms="am";}
	    				 	
	    				   time=day+"_"+(mon+1)+"_"+year+"_"+hr+"_"+min+"_"+tms;
	    				   
	    	     
	    	           excelfilename = "Marks_"+colname+"_"+classname+"_"+time+".xls";
	    		       String inFilePath = Environment.getExternalStorageDirectory().toString()+"/MyRecords/"+csvfilename;
	    		       String outFilePath = Environment.getExternalStorageDirectory().toString()+"/MyRecords/"+excelfilename;
	    		       String thisLine;
	    		       int count=0;
	    		       System.out.println(Environment.getExternalStorageDirectory()+"/MyRecords/".toString()+"");
	    		       try {

	    		       FileInputStream fis = new FileInputStream(inFilePath);
	    		       DataInputStream myInput = new DataInputStream(fis);
	    		       int i=0;
	    		       arList = new ArrayList();
	    		       while ((thisLine = myInput.readLine()) != null)
	    		       {
	    		       al = new ArrayList();
	    		       String strar[] = thisLine.split(",");
	    		       for(int j=0;j<strar.length;j++)
	    		       {
	    		       al.add(strar[j]);
	    		       }
	    		       arList.add(al);
	    		       System.out.println();
	    		       i++;
	    		       }} catch (Exception e) {
	    		           System.out.println("Error"+e);
	    		       }

	    		       try
	    		       {
	    		       HSSFWorkbook hwb = new HSSFWorkbook();
	    		       HSSFSheet sheet = hwb.createSheet("new sheet");
	    		       for(int k=0;k<arList.size();k++)
	    		       {
	    		       ArrayList ardata = (ArrayList)arList.get(k);
	    		       HSSFRow row = sheet.createRow((short) 0+k);
	    		       for(int p=0;p<ardata.size();p++)
	    		       {
	    		       HSSFCell cell = row.createCell((short) p);
	    		       String data = ardata.get(p).toString();
	    		       if(data.startsWith("=")){
	    		       cell.setCellType(Cell.CELL_TYPE_STRING);
	    		       data=data.replaceAll("\"", "");
	    		       data=data.replaceAll("=", "");
	    		       cell.setCellValue(data);
	    		       }
	    		       else if(data.startsWith("\"")){
	    		       data=data.replaceAll("\"", "");
	    		       cell.setCellType(Cell.CELL_TYPE_STRING);
	    		       cell.setCellValue(data);
	    		       }
	    		       else{
	    		       data=data.replaceAll("\"", "");
	    		       cell.setCellType(Cell.CELL_TYPE_NUMERIC);
	    		       cell.setCellValue(data);
	    		       }
	    		       //*/
	    		       // cell.setCellValue(ardata.get(p).toString());
	    		       }
	    		       System.out.println();
	    		       }
	    		       FileOutputStream fileOut = new FileOutputStream(outFilePath);
	    		       hwb.write(fileOut);
	    		       fileOut.close();
	    		       System.out.println("Your excel file has been generated");
	    		       } catch ( Exception ex ) {
	    		       ex.printStackTrace();
	    		       } //main method ends
	    		       return true;
	    		   }

	    		   protected void onPostExecute(final Boolean success)

	    		   {

	    		       if (this.dialog.isShowing())

	    		       {

	    		           this.dialog.dismiss();   
	    		           String dir = Environment.getExternalStorageDirectory().toString()+"/MyRecords/";
	    		           File f0 = new File(dir,csvfilename);
	    		           f0.delete(); 
	    		          // txt.setText("DataBase Exported Successfully !! \nLocation : "+Environment.getExternalStorageDirectory().toString()+"/MyRecords/"+"\nFile Name : "+excelfilename);

	    		       }

	    		       if (success)

	    		       {

	    		           Toast.makeText(ViewMarksChart.this, " Exported Successful !! \nLocation : "+Environment.getExternalStorageDirectory().toString()+"/MyRecords/"+"\nFile Name : "+excelfilename, Toast.LENGTH_LONG).show();

	    		       }

	    		       else

	    		       {

	    		           Toast.makeText(ViewMarksChart.this, "Failed To Export Database To Excel", Toast.LENGTH_SHORT).show();

	    		       }

	    		   }


	    		   }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_marks_chart, menu);
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if(keyCode == KeyEvent.KEYCODE_BACK)
	    {Intent i= new Intent(ViewMarksChart.this,ViewMarks.class);
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
