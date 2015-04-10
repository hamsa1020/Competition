package com.example.ieee_competetion_examguide;

import java.util.Arrays;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class addSummery extends Activity {
	
	int daysnum;
	String [] num ;
	
	RadioGroup openion;
	
	RadioButton easy,between,defficult;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addsummery);
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		
		
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        
		
		final EditText summeryname = (EditText) findViewById(R.id.editText1);
		summeryname.setTypeface(font);
		final EditText summerydetails = (EditText) findViewById(R.id.editText2);
		summerydetails.setTypeface(font);
		final Button submit = (Button) findViewById(R.id.button1);
		submit.setTypeface(font);
		final String s = getIntent().getExtras().getString("GROUP_ID");
		final Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		openion = (RadioGroup) findViewById(R.id.radioOpenion);
		
		RadioButton r1 = (RadioButton) findViewById(R.id.radioButton1);
		r1.setTypeface(font);
		RadioButton r2 = (RadioButton) findViewById(R.id.radioButton2);
		r2.setTypeface(font);
		RadioButton r3 = (RadioButton) findViewById(R.id.radioButton3);
		r3.setTypeface(font);
		
		
		ParseQuery < ParseObject > query2 = ParseQuery.getQuery("StudyGroup");
		query2.whereEqualTo("objectId", s);
		query2.getFirstInBackground(new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				daysnum = object.getInt("days_no");
				num = new String[daysnum]; //create array
			    for (int i = 0; i < num.length; i++) { 
			        num[i] = String.valueOf(i+1); 
			    }
			 // Create an ArrayAdapter using the string array and a default spinner layout
				ArrayAdapter<String> adapter=new ArrayAdapter<String>(addSummery.this,android.R.layout.simple_list_item_1,num);
				// Specify the layout to use when the list of choices appears
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Apply the adapter to the spinner
				spinner.setAdapter(adapter);
				
				//Toast.makeText(getApplicationContext(), num.length+"", Toast.LENGTH_SHORT).show();
				submit.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						int selectedId = openion.getCheckedRadioButtonId();
						RadioButton chosenOpenion = (RadioButton) findViewById(selectedId);
						
						
						ParseObject summery = new ParseObject("Summery");
						summery.put("group_id", s);
						summery.put("day_num", (spinner.getSelectedItemPosition()+1));
						summery.put("written_by", ParseUser.getCurrentUser());
						summery.put("summery_name", summeryname.getText().toString());
						summery.put("summery_details", summerydetails.getText().toString());
						summery.put("part_openion", chosenOpenion.getText());
						summery.saveInBackground();
						
						finish();
					}
				});	
			}
			
		});
		
		
	}
	
	

}
