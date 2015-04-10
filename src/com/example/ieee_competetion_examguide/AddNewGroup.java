package com.example.ieee_competetion_examguide;

import java.io.ByteArrayOutputStream;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewGroup extends Activity {
	/** Called when the activity is first created. */
	
	private static final int SELECT_PHOTO = 1;
	String picturePath,fileName;
	
	//ArrayAdapter<String> myAdapter;
	
	EditText pagesNo,daysNo,groupname,groupdesc;
	int pagesNos,daysNos;
	TextView pagesperdays;
	Button create;
	ImageView groupphoto;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnewgroup);	
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        pagesperdays = (TextView) findViewById(R.id.textView3);
       
        groupname = (EditText) findViewById(R.id.editText1);
        groupname.setTypeface(font);
        groupdesc = (EditText) findViewById(R.id.editText2);
        groupdesc.setTypeface(font);
        groupphoto = (ImageView) findViewById(R.id.imageView1);
        
        pagesNo = (EditText) findViewById(R.id.editText3);
        pagesNo.setHint("0");
        daysNo = (EditText) findViewById(R.id.EditText01);
        daysNo.setHint("0");
        
        create = (Button) findViewById(R.id.button1);
        
        groupphoto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),SELECT_PHOTO);
			}
		});
        
        
        pagesNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            	if((count>0)&&(!((daysNo.getText().toString()).equals(""))))
            		pagesperdays.setText(Integer.parseInt(s.toString())/
            				Integer.parseInt(daysNo.getText().toString())+" "+"");
            	else if((daysNo.getText().toString()).equals(""))
            		daysNo.setText("1");
            	//else{
            		pagesNo.setHint("0");
            	//}

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                
            }

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(arg0.length()>0)
	            	pagesperdays.setText(Integer.parseInt(arg0.toString())/
	            			Integer.parseInt(daysNo.getText().toString())+" "+"");
				else if((daysNo.getText().toString()).equals(""))
            		daysNo.setText("1");
            	
	            	else{
	            		pagesNo.setHint("0");
	            	}
			}
        });
        
        daysNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
            	if((count>0)&&(!((pagesNo.getText().toString()).equals(""))))
            		pagesperdays.setText(Integer.parseInt(pagesNo.getText().toString())/
            				Integer.parseInt(s.toString())+" "+"");
            	else if((pagesNo.getText().toString()).equals(""))
            		pagesNo.setText("0");
            	else{
            		daysNo.setHint("0");
            	}

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                    int after) {
                
            }

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if(arg0.length()>0)
	            	pagesperdays.setText(Integer.parseInt(pagesNo.getText().toString())/
	            			Integer.parseInt(arg0.toString())+" "+"");
				else if((pagesNo.getText().toString()).equals(""))
					pagesNo.setText("0");
            	
	            	else{
	            		daysNo.setHint("0");
	            	}
			}
        });
        
        create.setTypeface(font);
        create.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
				
				ParseObject studygroup = new ParseObject("StudyGroup");
				studygroup.put("group_name", groupname.getText().toString());
				studygroup.put("group_desc", groupdesc.getText().toString());
				studygroup.put("page_no", pagesNo.getText().toString());
				studygroup.put("days_no", Integer.parseInt(daysNo.getText().toString()));
				studygroup.put("pages_per_day", pagesperdays.getText().toString());
				studygroup.put("user_open", ParseUser.getCurrentUser());
				
				Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
				Bitmap prsImgScaled = Bitmap.createScaledBitmap(bitmap, 110, 110* bitmap.getHeight() / bitmap.getWidth(), false);
		        Matrix matrix = new Matrix();
		        Bitmap prsImgScaledRotated = Bitmap.createBitmap(prsImgScaled, 0,0, prsImgScaled.getWidth(), prsImgScaled.getHeight(),matrix, true);
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
				prsImgScaledRotated.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				byte[] scaledData = bos.toByteArray();
				ParseFile prsFile = new ParseFile(groupname.getText().toString()+".jpg", scaledData);
				studygroup.put("group_pic", prsFile);
				
				
				studygroup.saveInBackground();
			}
		});
        
}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
	         Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaStore.Images.Media.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         picturePath = cursor.getString(columnIndex);
	         cursor.close();
	         Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
	         groupphoto.setImageBitmap(bitmap); // for pevieeeewwww
	        
	     }
	}

	

}
