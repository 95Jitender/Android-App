package com.example.slidertabs;

import android.app.ActionBar;
import android.app.ListActivity;

import android.content.ActivityNotFoundException;

import android.content.Intent;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

import android.widget.ListAdapter;

import android.widget.ListView;

import android.widget.SimpleAdapter;

import android.widget.TextView;

 

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

 

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.util.ArrayList;

import java.util.HashMap;

 

public class ExceltoDb extends ListActivity {

 

  TextView lbl;
  Button btnimport;
  ListView lv;
  public static final int requestcode = 1;
  //static String tableName;
  String classname;
  XlsxCon controller = new XlsxCon(this,classname);
  //public static final String Tablename = "MyTable1";
  //public static final String id = "_id";// 0 integer
  public static final String NAME = "name";// 1 text(String)
  public static final String ROLLNO = "rollno";// 2 integer
  public static final String CONTACT = "contact";// 3 date(String)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excelto_db);
        ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
		try{
			Bundle b= getIntent().getExtras();
		    classname=b.getString("classname");
		}
		catch(Exception e){
			Toast.makeText(ExceltoDb.this,"Error :"+e, Toast.LENGTH_LONG).show();
		}
        btnimport = (Button) findViewById(R.id.btnupload);
        lbl = (TextView) findViewById(R.id.txtresulttext);
        lv = getListView();
        //tableName = "info";
        btnimport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
              fileintent.setType("gagt/sdf");
              try {
                  startActivityForResult(fileintent, requestcode);
                } catch (ActivityNotFoundException e) {
                    lbl.setText("No activity can handle picking a file. Showing alternatives.");
                }
            }
        });
        
       /* System.out.println("control reacher arrayList");
        ArrayList<HashMap<String, String>> myList = controller.getProducts();
        if (myList.size() != 0) {
            lv = getListView();
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), myList,
                    R.layout.v, new String[]{Company, Product,
                    Price},
                    new int[]{R.id.txtproductcompany, R.id.txtproductname,
                            R.id.txtproductprice});
            setListAdapter(adapter);
        }*/
   } 
    //end of on create
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
           case requestcode:
                String FilePath = data.getData().getPath();
                System.out.println(FilePath);
                try {
                    if (resultCode == RESULT_OK) {
                        AssetManager am = this.getAssets();
                        InputStream inStream;
                        Workbook wb = null;
                        try {
                            inStream = new FileInputStream(FilePath);
                            wb = new HSSFWorkbook(inStream);
                            inStream.close();
                        }
                        catch (IOException e) {
                            lbl.setText("First "+e.getMessage().toString());
                            e.printStackTrace();
                        }
                        System.out.println("First TRY/CATCH Successfully Over");
 
                        XlsxCon dbAdapter = new XlsxCon(this,classname);
                        System.out.println("1");
                        Sheet sheet1 = wb.getSheetAt(0);
                        System.out.println("sheet(0)");
                      /*  Sheet sheet2 = wb.getSheetAt(1);
                        System.out.println("sheet(1)");*/
                        if (sheet1 == null) {
                        	 System.out.println("sheet1 null");
                           return;
                        }
                        System.out.println("sheet1 not null");
                       /* if (sheet2 == null) {
                        	System.out.println("sheet 2 null");
                            return;
                        }
                        System.out.println("sheet 2 not null");*/
 
                        dbAdapter.open();
                       Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet1,classname);
                       // dbAdapter.delete();
                        dbAdapter.close();
                        //dbAdapter.open();
                       
                     //   Excel2SQLiteHelper.insertExcelToSqlite(dbAdapter, sheet2);
                       // dbAdapter.close();
                      
 
                    }
                } catch (Exception ex) {
                    lbl.setText(ex.getMessage().toString() + "Second");

                }
                ArrayList<HashMap<String, String>> myList = controller
                     .getProducts(classname);
                if (myList.size() != 0) {
                    ListView lv = getListView();
                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), myList,
                            R.layout.v, new String[]{NAME, ROLLNO,
                            CONTACT},
                            new int[]{R.id.txtproductcompany, R.id.txtproductname,
                                    R.id.txtproductprice});
                    setListAdapter(adapter);
                    
                }
                String name,rollno;
                SQLiteDatabase mydatabase = openOrCreateDatabase("Student.db",MODE_PRIVATE,null);
                Cursor resultSet = mydatabase.rawQuery("Select name,rollno from "+ classname ,null);
                while(resultSet.moveToNext())
                {name=resultSet.getString(0);
    		    rollno=resultSet.getString(1);
    		    mydatabase.execSQL("INSERT INTO " + classname + "_markstable (name,rollno) VALUES('"+name+"','"+rollno+"');");	
                }
        } //end of switch
        
   } //end of on activity result
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent  i = new Intent(ExceltoDb.this,AddRecord.class );
            i.putExtra("classname",classname);
 			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
 			startActivity(i);
 			overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
 			finish();
 			return true;
        }
    return false;
    }
} //end of list activity
