package com.example.ieee_competetion_examguide;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class groupsListCustomAdapter extends ParseQueryAdapter<ParseObject> {
	//ParseUser fl =  new ParseUser();
	public groupsListCustomAdapter(final Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		// Todos marked as high-pri
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("StudyGroup");
	            query.whereEqualTo("user_open", ParseUser.getCurrentUser());
	            query.orderByDescending("updatedAt");
				return query;
			}
		});
	}

	
	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(final ParseObject object, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.grouplist_result, null);
		}

		
		super.getItemView(object, v, parent);
		
		Typeface font = Typeface.createFromAsset(getContext().getAssets(),"Fonts/Rosemary.ttf");
		
		
		final TextView groupname = (TextView) v.findViewById(R.id.textView1);
        final TextView lastupdate = (TextView) v.findViewById(R.id.textView2);
        final TextView fordays = (TextView) v.findViewById(R.id.textView3);
        ParseImageView picprof = (ParseImageView) v.findViewById(R.id.imageView1);
        
        ParseFile imageFile = object.getParseFile("group_pic");
		if (imageFile != null) {
			picprof.setParseFile(imageFile);
			picprof.loadInBackground();
		}
		else
		{
				Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.inspiration_6);
				picprof.setImageBitmap(bitmap); // for pevieeeewwww		
		}
		
		groupname.setTypeface(font);
		groupname.setText(object.getString("group_name")+"");
		groupname.setTextSize(16);
        Date datecreate = object.getUpdatedAt();
        Date cur = Calendar.getInstance().getTime();
        long t = cur.getTime() - datecreate.getTime();
        
        int days = (int) (t / (1000*60*60*24));  
        int hours = (int) ((t - (1000*60*60*24*days)) / (1000*60*60)); 
        int min = (int) (t - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

        lastupdate.setTypeface(font);
        lastupdate.setTextSize(16);
        if(hours>23)
        	lastupdate.setText("Last Update: "+days+"d"+hours+"h"+min+"m");
        else
        	lastupdate.setText("Last Update: "+hours+"h"+min+"m");
        
        fordays.setTypeface(font);
        fordays.setText("Study "+object.getString("page_no")+" Pages in "+object.getInt("days_no")+" Days");
       
        v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getContext(), "" +object.getObjectId()+"", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getContext(), MyGroupsPage.class);
				intent.putExtra("GROUP_ID", object.getObjectId());
				getContext().startActivity(intent);
				
			}
		});
        
		return v;
	}

}
