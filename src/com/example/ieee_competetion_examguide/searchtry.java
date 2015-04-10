package com.example.ieee_competetion_examguide;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class searchtry extends Activity {
	/** Called when the activity is first created. */
	
	//ArrayAdapter<String> myAdapter;
	ListView groupList;
    int studygroupsize, foloowsize;
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trysearch);	
		
		//==========================
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e1c77c"))); // set your desired color
		createCutomActionBarTitle();
		
		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		TextView cg = (TextView) findViewById(R.id.textView1);
		cg.setTypeface(font);
		TextView logout = (TextView) findViewById(R.id.textView2);
		logout.setTypeface(font);
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ParseUser.logOut();
				finish();
			}
		});
		Button add = (Button) findViewById(R.id.button1);
		add.setTypeface(font);
		Button followinggroup = (Button) findViewById(R.id.button2);
		followinggroup.setTypeface(font);
		followinggroup.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(searchtry.this,followinggroup.class);
				startActivity(in);
			}
		});

		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in = new Intent(searchtry.this,AddNewGroup.class);
				startActivity(in);
			}
		});
		
		groupList = (ListView) findViewById(R.id.listview);
	
		
	    }

	public void onResume(){
		super.onResume();{
			groupList.setAdapter(new groupsListCustomAdapter(this));
			//groupList.setAdapter(new groupfollowlistadapter(this));
		}
	}

	
	public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable
	{
		private ArrayList<String> items;
		private ArrayList<String> suggestions;
		private ArrayList<String> itemsAll;
		//private ArrayList<String> id;
		private LayoutInflater layoutInflater;
	    private int viewResourceId;
	    
	    public AutoCompleteAdapter(Context context, int viewResourceId, ArrayList<String> items)
	    {
	    	
	    	super(context, viewResourceId, items);
	    	this.viewResourceId = viewResourceId;
	    	this.suggestions = new ArrayList<String>();
	    	this.items = items;
	    	//this.id = id;
	    	this.itemsAll = (ArrayList<String>) items.clone();
	    	
	    	
	    }
	    
	    @
        Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // Inflate the item layout and set the views
            View listItem = convertView;
            final int pos = position;
            if (listItem == null) {
            	layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            	listItem = layoutInflater.inflate(viewResourceId, null);
                //listItem = layoutInflater.inflate(R.layout.searchresult, null);   
            }
            
            final ParseImageView groupphotos = (ParseImageView)listItem.findViewById(R.id.imageView1);
            TextView groupname = (TextView) listItem.findViewById(R.id.textView1);
            
            groupname.setText(items.get(pos)+"");
            ParseQuery<ParseObject> y = ParseQuery.getQuery("StudyGroup");
        	y.whereEqualTo("group_name", items.get(pos));
        	y.getFirstInBackground(new GetCallback<ParseObject>(){
				@Override
				public void done(ParseObject object, ParseException e) {
					// TODO Auto-generated method stub
					ParseFile imageFile = object.getParseFile("group_pic");
					if (imageFile != null) {
						groupphotos.setParseFile(imageFile);
						groupphotos.loadInBackground();
					}
					else
					{
							Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
									R.drawable.inspiration_6);
							groupphotos.setImageBitmap(bitmap); // for pevieeeewwww
							
							
					}
				}
        	});
            
            
            
            return listItem;
        }
	    
	    @Override
	    public Filter getFilter() {
	        return nameFilter;
	    }

	    Filter nameFilter = new Filter() {
	    	
	    	public String convertResultToString(Object resultValue) {
	            String str = (String)resultValue; 
	            return str;
	        }
	    	
	        @Override
	        protected FilterResults performFiltering(CharSequence constraint) {
	            if(constraint != null) {
	                suggestions.clear();
	                for (String new_suggest : itemsAll) {
	                    if(new_suggest.toLowerCase().startsWith(constraint.toString().toLowerCase())){
	                        suggestions.add(new_suggest);
	                    }
	                }
	                FilterResults filterResults = new FilterResults();
	                filterResults.values = suggestions;
	                filterResults.count = suggestions.size();
	                return filterResults;
	            } else {
	                return new FilterResults();
	            }
	        }
	        
	        @SuppressWarnings("unchecked")
			@Override
	        protected void publishResults(CharSequence constraint, FilterResults results) {
	            ArrayList<String> filteredList = (ArrayList<String>) results.values;
	            if(results != null && results.count > 0) {
	                clear();
	                for (String c : filteredList) {
	                    add(c);
	                }
	                notifyDataSetChanged();
	            }
	        }
	    };

    }
	
	private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        final AutoCompleteAdapter myAdapter;
        final ArrayList<String> picList;
        final ImageView se;
		final ImageView cross;
        
		final TextView frag1=(TextView)v.findViewById(R.id.titleFragment1);
		final TextView frag2=(TextView)v.findViewById(R.id.titleFragment2);
        picList = new ArrayList<String>();
		
		
		try {
				ParseQuery<ParseObject> y = ParseQuery.getQuery("StudyGroup");
				y.whereNotEqualTo("user_open", ParseUser.getCurrentUser());
				List<ParseObject> gg = y.find();
				for(ParseObject c:gg){
					picList.add(c.getString("group_name"));
					//picList4.add(c.getObjectId());
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,androidBooks);
        final AutoCompleteTextView acTextView = (AutoCompleteTextView)v.findViewById(R.id.AndroidBooks);
        myAdapter = new AutoCompleteAdapter(this,R.layout.searchresult,picList);
        acTextView.setVisibility(View.GONE);
        se = (ImageView) v.findViewById(R.id.imageView1);
       
        cross = (ImageView) v.findViewById(R.id.imageView2);
        cross.setVisibility(View.GONE);
        
        se.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				acTextView.setVisibility(View.VISIBLE);
				cross.setVisibility(View.VISIBLE);
				frag1.setVisibility(View.GONE);
				frag2.setVisibility(View.GONE);
				
				cross.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						acTextView.setVisibility(View.GONE);
						cross.setVisibility(View.GONE);
						se.setVisibility(View.VISIBLE);
						frag1.setVisibility(View.VISIBLE);
						frag2.setVisibility(View.VISIBLE);
					}
				});
		        acTextView.setThreshold(2);
		        acTextView.setAdapter(myAdapter);

		        se.setVisibility(View.GONE);
		        
		        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
							long arg3) {
						// TODO Auto-generated method stub
						try{
						ParseQuery<ParseObject> y = ParseQuery.getQuery("StudyGroup");
						y.whereNotEqualTo("user_open", ParseUser.getCurrentUser());
			        	y.whereEqualTo("group_name", picList.get(arg2));
			        	List<ParseObject> gg = y.find();
						for(ParseObject c:gg){
			        	   // Toast.makeText(getApplicationContext(), "" +c.getObjectId() , Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(searchtry.this, OtherGroupsPage.class);
							//Toast.makeText(getApplicationContext(), "" +c.getObjectId() , Toast.LENGTH_SHORT).show();
							
							intent.putExtra("GROUP_ID", c.getObjectId());
							startActivity(intent);
						
					}
						}catch(ParseException ee){
							ee.printStackTrace();
						}
					}
		        });	
				
		        
			}
		});
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
    }
			
}