package com.example.slidertabs;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Profile extends Activity {
	Button btngal, btnback ,btnsave;
    ImageView iv;
    Bitmap bmp;
    SharedPreferences spref;
    String teachercontact,teachername;
    EditText name,contact;
    String filepath;
    private static int RESULT_LOAD_IMAGE = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		ActionBar bar = getActionBar();
	    bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e62e00")));
	    spref=(SharedPreferences)PreferenceManager.getDefaultSharedPreferences(this);
	    filepath=spref.getString("picturepath",""); 
	    try{File imgFile = new  File(filepath);

			if(imgFile.exists()){
			    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			    iv = (ImageView) findViewById(R.id.imageView1);
			    iv.setImageBitmap(myBitmap);
			
			}
			else{
				String uri = "@drawable/logofinal";  // where myresource (without the extension) is the file

				int imageResource = getResources().getIdentifier(uri, null, getPackageName());

				iv= (ImageView)findViewById(R.id.imageView1);
				Drawable res = getResources().getDrawable(imageResource);
				iv.setImageDrawable(res);
			}
	    }catch(Exception e){}
	    btnback=(Button)findViewById(R.id.button1);
	    btngal=(Button)findViewById(R.id.button2);
	    btnsave=(Button)findViewById(R.id.button3);
	   
	    name=(EditText)findViewById(R.id.editText1);
	    name.setEnabled(false);
	    contact=(EditText)findViewById(R.id.editText2);
	    /*iv=(ImageView)findViewById(R.id.imageView1);*/
	   
	    teachercontact=spref.getString("contact","val").trim(); 
	    teachername=spref.getString("name","val").trim();
	    name.setText(teachername);
	    contact.setText(teachercontact);
	    btngal.setOnClickListener(new OnClickListener() 
        {            
            @Override
            public void onClick(View arg0) 
            {
                /*openGallery(); */ 
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        }); 
      btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Profile.this, MainActivity.class );
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				startActivity(i);
				overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
				finish();
				
			}
		});
     
      btnsave.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String newname=name.getText().toString().trim();
			String newcon=contact.getText().toString().trim();
			if(newname.equals(null)||newname.equals("")||newcon.equals(null)||newcon.equals("")){
				Toast.makeText(getApplicationContext(), "Fields Cannot Be Left Blank.", Toast.LENGTH_SHORT).show();
			}
			else {
				SharedPreferences.Editor spe = spref.edit();
				spe.putString("name",newname);
				spe.putString("contact",newcon);
				spe.commit();
				Toast.makeText(getApplicationContext(), "Detailes Saved!", Toast.LENGTH_SHORT).show();
			}
			
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	  /* private void openGallery()
	    {
	        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
	        photoPickerIntent.setType("image/*");
	        startActivityForResult(photoPickerIntent, 1);
	    }*/
	   /*@Override
	   protected void onActivityResult(int requestCode, int resultcode, Intent intent)
	   {
	       super.onActivityResult(requestCode, resultcode, intent);
	       
	       if (requestCode == 1) 
	       {
	           if (intent != null && resultcode == RESULT_OK) 
	           {              
	               
	                 Uri selectedImage = intent.getData();
	                 
	                 String[] filePathColumn = {MediaStore.Images.Media.DATA};
	                 Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	                 cursor.moveToFirst();
	                 int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	                 String filePath = cursor.getString(columnIndex);
	                 cursor.close();
	                 System.out.println(filepath);
	                 if(bmp != null && !bmp.isRecycled())
	                 {
	                     bmp = null;                
	                 }
	                                 
	                 bmp = BitmapFactory.decodeFile(filePath);
	                 iv.setBackgroundResource(0);
	                 iv.setImageBitmap(bmp);              
	           }
	           else 
	           {
	               Log.d("Status:", "Photopicker canceled");            
	           }
	       }
	   }*/
	   @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	            Uri selectedImage = data.getData();
	            String[] filePathColumn = { MediaStore.Images.Media.DATA };
	            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	            cursor.moveToFirst();
	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String picturePath = cursor.getString(columnIndex);
	            cursor.close();
	            SharedPreferences.Editor spe = spref.edit();
				spe.putString("picturepath",picturePath);
				spe.commit();
	            System.out.println(picturePath);
	            iv = (ImageView) findViewById(R.id.imageView1);
	            //iv.setImageBitmap(BitmapFactory.decodeFile(picturePath));
	            iv.setImageBitmap(getScaledBitmap(picturePath, 800, 800));
	        }
	      
	    }
	   private Bitmap getScaledBitmap(String picturePath, int width, int height) {
           BitmapFactory.Options sizeOptions = new BitmapFactory.Options();
           sizeOptions.inJustDecodeBounds = true;
           BitmapFactory.decodeFile(picturePath, sizeOptions);

           int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

           sizeOptions.inJustDecodeBounds = false;
           sizeOptions.inSampleSize = inSampleSize;

           return BitmapFactory.decodeFile(picturePath, sizeOptions);
       }

       private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
           // Raw height and width of image
           final int height = options.outHeight;
           final int width = options.outWidth;
           int inSampleSize = 1;

           if (height > reqHeight || width > reqWidth) {

               // Calculate ratios of height and width to requested height and
               // width
               final int heightRatio = Math.round((float) height / (float) reqHeight);
               final int widthRatio = Math.round((float) width / (float) reqWidth);

               // Choose the smallest ratio as inSampleSize value, this will
               // guarantee
               // a final image with both dimensions larger than or equal to the
               // requested height and width.
               inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
           }

           return inSampleSize;
       }
       @Override
   	public boolean onKeyDown(int keyCode, KeyEvent event) 
   	{
   	    if(keyCode == KeyEvent.KEYCODE_BACK)
   	    {Intent i = new Intent(Profile.this, MainActivity.class );
		i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(i);
		overridePendingTransition(R.anim.push_left_in,android.R.anim.fade_out);
		finish();
   		    return true;
   	    }
   	return false;
   	}
}
