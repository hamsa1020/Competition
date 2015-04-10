package com.example.ieee_competetion_examguide;

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
import android.view.MenuItem;
import android.widget.TextView;

public class SummeryPage extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.summerypage);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		   
		final TextView writtenby = (TextView) findViewById(R.id.textView2);
		writtenby.setTypeface(font);
		final TextView openion = (TextView) findViewById(R.id.textView1);
		openion.setTypeface(font);
		final TextView summerydetail = (TextView) findViewById(R.id.textView3);
		summerydetail.setTypeface(font);
		final String m = getIntent().getExtras().getString("SUMMERY_ID");

		    
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Summery");
		query.getInBackground(m, new GetCallback<ParseObject>(){

			@Override
			public void done(final ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				summerydetail.setText(object.getString("summery_details")+"");
				
				ParseQuery<ParseUser> queryuser = ParseQuery.getUserQuery();
				queryuser.getInBackground(object.getParseUser("written_by").getObjectId().toString(), new GetCallback<ParseUser>(){

					@Override
					public void done(ParseUser objects, ParseException e) {
						// TODO Auto-generated method stub
						writtenby.setText("Written By: "+objects.getUsername()+"");
						openion.setText(objects.getUsername()+" specefied it as "+object.getString("part_openion")+" part to study");
						
					}
	            	
	            });
			}
		});
		
		
		
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        // Respond to the action bar's Up/Home button
        case android.R.id.home:
          //  NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
	 }
}
