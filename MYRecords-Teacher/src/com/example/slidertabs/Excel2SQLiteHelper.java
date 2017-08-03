package com.example.slidertabs;
import android.content.ContentValues;
import android.util.Log;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;
public class Excel2SQLiteHelper {
	   /* public static final*/ String Tablename = "MyTable1";
	    public static final String id = "_id";// 0 integer
	    public static final String NAME = "name";// 1 text(String)
	    public static final String ROLLNO = "rollno";// 2 text(String)
	    public static final String CONTACT = "contact";// 3 text(String)
	    
	    public static void insertExcelToSqlite(XlsxCon dbAdapter, Sheet sheet,String tablename) {
		
	        for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext(); ) {	
	            Row row = rit.next();	
	            ContentValues contentValues = new ContentValues();
	            row.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
	            row.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
	            row.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
	           if(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue().length()!=11 ||  
	        		   row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue().length()!=10){
	        	  System.out.print("**Skipped**");
	        	   System.out.println(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		           System.out.println(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		           System.out.println(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		           System.out.print("****Skipped****");
	           }
	           else{
	            contentValues.put(NAME, row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	            contentValues.put(ROLLNO, row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	            contentValues.put(CONTACT, row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	            contentValues.put("class",tablename);
	            contentValues.put("totalattendance",0);
	            
	            System.out.println(row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	            System.out.println(row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	            System.out.println(row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
	           
	            try {
	                if (dbAdapter.insert(tablename, contentValues) < 0) {
                    return;
	                }
	            } catch (Exception ex) {
	                Log.d("Exception in importing", ex.getMessage().toString());
	            }
	           }
	        }
	    }
	}

