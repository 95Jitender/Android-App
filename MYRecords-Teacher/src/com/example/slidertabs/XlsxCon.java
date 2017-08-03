package com.example.slidertabs;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

public class XlsxCon {
	String TAG = "DBAdapter";
	   /*public static final*/ String Tablename;
	   public static final String id = "_id";// 0 integer
	   public static final String NAME = "name";// 1 text(String)
	   public static final String ROLLNO = "rollno";// 2 integer
	   public static final String CONTACT = "contact";// 3 date(String)
       private SQLiteDatabase db;
	   private DBHelper dbHelper;
	   public XlsxCon(Context context,String tablename) {
	        dbHelper = new DBHelper(context);
	        Tablename = tablename;
	    }
	    public void open() {
	        if (null == db || !db.isOpen()) {
	            try {
	                db = dbHelper.getWritableDatabase();
	            } catch (SQLiteException sqLiteException) {
	            }
	        }
	    }
     public void close() {
	        if (db != null) {
	           db.close();
	        }
	    }
	    public int insert(String table, ContentValues values) {
	        return (int) db.insert(table, null, values);
	    }
	    public void delete() {
	        db.execSQL("delete from " + Tablename);
	    }
	    public Cursor getAllRow(String table) {
	        return db.query(table, null, null, null, null, null, id);
	    }

	    private class DBHelper extends SQLiteOpenHelper {
	       private static final int VERSION = 1;
	        private static final String DB_NAME = "Student.db";
	        public DBHelper(Context context) {
	            super(context,DB_NAME, null, VERSION);
	        }
	        @Override
	        public void onCreate(SQLiteDatabase db) {
	            String create_sql = "CREATE TABLE IF NOT EXISTS " + Tablename + "("
	                    + NAME + " TEXT ," + ROLLNO + " TEXT PRIMARY KEY NOT NULL,"
	                    + CONTACT + " TEXT , class TEXT , totalattendance INTEGER DEFAULT 0 )";
	            db.execSQL(create_sql);
	        }
	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	            db.execSQL("DROP TABLE IF EXISTS " + Tablename);
	        }
	 
	    }
	 
	    public ArrayList<HashMap<String, String>> getProducts(String tablename) {
	    	ArrayList<HashMap<String, String>> prolist;
	    	prolist = new ArrayList<HashMap<String, String>>();
	    	 try{
	        String selectQuery = "SELECT  * FROM " + tablename;
	        SQLiteDatabase database = dbHelper.getWritableDatabase();
	        Cursor cursor = database.rawQuery(selectQuery,null);
	        if (cursor.moveToFirst()) {
	            do {
	                HashMap<String, String> map = new HashMap<String, String>();
	                map.put(NAME, cursor.getString(0));
	                map.put(ROLLNO, cursor.getString(1));
	                map.put(CONTACT, cursor.getString(2));
	                prolist.add(map);
	            } while (cursor.moveToNext());
	        }
	    	}catch(Exception e){System.out.println("XlsxError:"+e);}
	    	return prolist;
	    	
	    }
}
