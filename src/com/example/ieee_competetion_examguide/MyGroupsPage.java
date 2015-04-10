package com.example.ieee_competetion_examguide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyGroupsPage extends Activity {
	/** Called when the activity is first created. */
	

		int count;
		int pagesperday;
		TextView gname,gparticipantnumber,gdes;
		ParseImageView gpic;
		Button addsummery;
		ParseUser user;
		
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygrouppage);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		final String s = getIntent().getExtras().getString("GROUP_ID");
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
        
		
		gname = (TextView) findViewById(R.id.textView1);
		gname.setTypeface(font);
		gparticipantnumber = (TextView) findViewById(R.id.textView2);
		gparticipantnumber.setTypeface(font);
		gdes = (TextView) findViewById(R.id.textView3);
		gdes.setTypeface(font);
		gpic = (ParseImageView) findViewById(R.id.imageView1);
		
		
		
		addsummery = (Button) findViewById(R.id.button1);
		addsummery.setTypeface(font);
		addsummery.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(MyGroupsPage.this,addSummery.class);
				in.putExtra("GROUP_ID", s);
				startActivity(in);
			}
		});
		
		
		
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StudyGroup");
		query.getInBackground(s, new GetCallback<ParseObject>(){

			@Override
			public void done(ParseObject object, ParseException e) {
				// TODO Auto-generated method stub
				gname.setText(object.getString("group_name"));
				gdes.setText(object.getString("group_desc"));
				
				ParseFile imageFile = object.getParseFile("group_pic");
				if (imageFile != null) {
					gpic.setParseFile(imageFile);
					gpic.loadInBackground();
				}
				else
				{
						Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.inspiration_6);
						gpic.setImageBitmap(bitmap); // for pevieeeewwww		
				}
				
			}
			
		});
		
		
		
		
		
		
	}
		
		public void onResume(){
			super.onResume();{
				
				final String s = getIntent().getExtras().getString("GROUP_ID");
				ListView gg = (ListView) findViewById(R.id.listview);
				gg.setAdapter(new summeryListCustomAdapter(this));
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Follow");
				ParseObject obj = ParseObject.createWithoutData("_User",s);
				query.whereEqualTo("to", obj);
				query.findInBackground(new FindCallback<ParseObject>(){
					 
					@Override
					public void done(List<ParseObject> arg0, ParseException arg1) {
						// TODO Auto-generated method stub
						gparticipantnumber.setText("Followers: "+arg0.size());
						gparticipantnumber.setGravity(Gravity.CENTER);
					}
					
					
				});

			}
		}
	
	    public class summeryListCustomAdapter extends ParseQueryAdapter<ParseObject> {
	    	//ParseUser fl =  new ParseUser();
	    	public summeryListCustomAdapter(final Context context) {
	    		// Use the QueryFactory to construct a PQA that will only show
	    		// Todos marked as high-pri
	    		
	    		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
	    			public ParseQuery create() {
	    				final String s = getIntent().getExtras().getString("GROUP_ID");

	    				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Summery");
	    	            query.whereEqualTo("group_id", s);
	    	            query.orderByDescending("updatedAt");
	    				return query;
	    			}
	    		});
	    	}

	    	
	    	// Customize the layout by overriding getItemView
	    	@Override
	    	public View getItemView(final ParseObject object, View v, ViewGroup parent) {
	    		if (v == null) {
	    			v = View.inflate(getContext(), R.layout.summery_result, null);
	    		}

	    		
	    		super.getItemView(object, v, parent);
	    		
	    		Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
	    	       
	    		final TextView summeryname = (TextView) v.findViewById(R.id.textView1);
	    		summeryname.setTypeface(font);
	            final TextView forday = (TextView) v.findViewById(R.id.textView2);
	            forday.setTypeface(font);
	            final TextView writtenBy = (TextView) v.findViewById(R.id.textView3);
	            writtenBy.setTypeface(font);
	            final TextView lastupdate = (TextView) v.findViewById(R.id.textView4);
	            lastupdate.setTypeface(font);
	    		
	    		
	            summeryname.setText("Name: "+object.getString("summery_name")+"");
	    		
	            Date datecreate = object.getUpdatedAt();
	            Date cur = Calendar.getInstance().getTime();
	            long t = cur.getTime() - datecreate.getTime();
	            
	            int days = (int) (t / (1000*60*60*24));  
	            int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
	            int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

	            if((hours==0)&&(days>0)&&(min>0))
	            	lastupdate.setText(days+"d"+min+"m");
	            if((hours>0)&&(days>0)&&(min>0))
	            	lastupdate.setText(days+"d"+hours+"h"+min+"m");
	            if((hours>0)&&(days>0)&&(min==0))
	            	lastupdate.setText(days+"d"+hours+"h");
	            else
	            	lastupdate.setText(hours+"h"+min+"m");
	            
	            forday.setText("For Day: "+object.getInt("day_num")+"");
	            
	            ParseQuery<ParseUser> query = ParseQuery.getUserQuery();
	            query.getInBackground(object.getParseUser("written_by").getObjectId().toString(), new GetCallback<ParseUser>(){

					@Override
					public void done(ParseUser objects, ParseException e) {
						// TODO Auto-generated method stub
						writtenBy.setText("Written By: "+objects.getUsername()+"");
					}
	            	
	            });
	            
	           
	            //Toast.makeText(getApplicationContext(), object.getParseObject("written_by").toString()+"", Toast.LENGTH_SHORT).show();
	            //writtenBy.setText(obj.getString("username")+"");
	            
	            
	            
	            v.setOnClickListener(new View.OnClickListener() {
	    			
	    			@Override
	    			public void onClick(View v) {
	    				// TODO Auto-generated method stub
	    				
	    				//Toast.makeText(getContext(), "" +object.getObjectId()+"", Toast.LENGTH_SHORT).show();
	    				Intent intent = new Intent(getContext(), SummeryPage.class);
	    				intent.putExtra("SUMMERY_ID", object.getObjectId());
	    				getContext().startActivity(intent);
	    				
	    			}
	    		});
	            
	    		return v;
	    	}
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
