package com.example.ieee_competetion_examguide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class followinggroup extends Activity {
		
	ListView groupList;
	ArrayList<Integer> obList4;
	ArrayList<String> obList2,obList3,obList;
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_main);	
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		
		groupList = (ListView) findViewById(R.id.listview);
		
	}
		public void onResume(){
			super.onResume();{
				groupList.setAdapter(new groupfollowlistadapter(this));
				//groupList.setAdapter(new groupfollowlistadapter(this));
			}
		}
		
		// Create an Adapter Class extending the BaseAdapter
	    class groupfollowlistadapter extends BaseAdapter {

	        private LayoutInflater layoutInflater;
	        
	        public groupfollowlistadapter(followinggroup activity) {
	            // TODO Auto-generated constructor stub
	            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        	
	            obList = new ArrayList<String>();
	            obList2 = new ArrayList<String>();
	            obList3 = new ArrayList<String>();
	            obList4 = new ArrayList<Integer>();
	            try {
	            	ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Follow");
	        		query.whereEqualTo("from", ParseUser.getCurrentUser());
	        		List<ParseObject> ob = query.find();
	    			for(ParseObject m:ob){
	    				ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("StudyGroup");
        				query2.whereEqualTo("objectId", m.getParseObject("to").getObjectId().toString());
        				List<ParseObject> gg = query2.find();
	    				for(ParseObject c:gg){
	    					obList.add(c.getString("group_name"));
	    					obList2.add(c.getObjectId());
	    					obList3.add(c.getString("page_no"));
	    					obList4.add(c.getInt("days_no"));
        						}
	    			}
	        		
	    			} catch (ParseException e1) {
	    				// TODO Auto-generated catch block
	    				e1.printStackTrace();
	    			}
	 	
	        }
	        
	        @Override
			public int getCount() {
				// TODO Auto-generated method stub
				return obList.size();
			}
	        
	        @
	        Override
	        public Object getItem(int position) {
	            // TODO Auto-generated method stub
	            return position;
	        }

	        @
	        Override
	        public long getItemId(int position) {
	            // TODO Auto-generated method stub
	            return position;
	        }

	        @
	        Override
	        public View getView(int position, View convertView, ViewGroup parent) {

	            // Inflate the item layout and set the views
	            View listItem = convertView;
	            final int pos = position;
	            if (listItem == null) {
	                listItem = layoutInflater.inflate(R.layout.grouplist_result, null);   
	            }
	            
	            Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
	            
	            // Initialize the views in the layout
	            final TextView groupname = (TextView) listItem.findViewById(R.id.textView1);
	            groupname.setTypeface(font);
	            final TextView lastupdate = (TextView) listItem.findViewById(R.id.textView2);
	            lastupdate.setTypeface(font);
	            final TextView forday = (TextView) listItem.findViewById(R.id.textView3);
	            forday.setTypeface(font);

	            final ParseImageView picprof = (ParseImageView) listItem.findViewById(R.id.imageView1);
	            
	            groupname.setText("Name:"+obList.get(pos)+"");
				
	            try{
	            	
	            	ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("StudyGroup");
					query2.whereEqualTo("group_name", obList.get(pos));
					List<ParseObject> gg = query2.find();
					for(ParseObject c:gg){
		            ParseFile imageFile = c.getParseFile("group_pic");
					if (imageFile != null) {
						picprof.setParseFile(imageFile);
						picprof.loadInBackground();
					}
					else
					{
							Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.inspiration_6);
							picprof.setImageBitmap(bitmap); // for pevieeeewwww		
					}
					
					Date datecreate = c.getUpdatedAt();
			        Date cur = Calendar.getInstance().getTime();
			        long t = cur.getTime() - datecreate.getTime();
			        
			        int days = (int) (t / (1000*60*60*24));  
			        int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
			        int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

			        if(hours>23)
			        	lastupdate.setText("Last Update: "+days+"d"+hours+"h"+min+"m");
			        else
			        	lastupdate.setText("Last Update: "+hours+"h"+min+"m");
					
					}
	            }catch(ParseException e){
	            	
	            }
	            
	            forday.setText("Study "+obList3.get(pos)+" Pages in "+obList4.get(pos)+" Days");
	            
	            listItem.setOnClickListener(new View.OnClickListener() {
	    			@Override
	    			public void onClick(View v) {
	    				// TODO Auto-generated method stub
	    				
	    				//Toast.makeText(getContext(), "" +object.getObjectId()+"", Toast.LENGTH_SHORT).show();
	    				Intent intent = new Intent(getBaseContext(), OtherGroupsPage.class);
	    				intent.putExtra("GROUP_ID", obList2.get(pos));
	    				startActivity(intent);
	    				
	    			}
	    		});
	            return listItem; 
	            
	            
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
