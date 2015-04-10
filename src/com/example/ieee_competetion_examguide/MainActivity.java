package com.example.ieee_competetion_examguide;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface font = Typeface.createFromAsset(getAssets(),"Fonts/Rosemary.ttf");
		
		final EditText username = (EditText) findViewById(R.id.editText1);
		username.setTypeface(font);
		final EditText password = (EditText) findViewById(R.id.editText2);
		password.setTypeface(font);
		Button signin = (Button) findViewById(R.id.button1);
		signin.setTypeface(font);
		Button signup = (Button) findViewById(R.id.button2);
		signup.setTypeface(font);
		
		signup.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, SignUp.class);
				startActivity(intent);
			}
		});
		
		signin.setOnClickListener(new OnClickListener() {	 
			public void onClick(View arg0) {

			// Retrieve the text entered from the EditText
			String usernametxt = username.getText().toString();
			String passwordtxt = password.getText().toString();
			
			//Send data to Parse.com for verification
			ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
						public void done(ParseUser user, ParseException e) {
							
							// check if email is verified or not -------------------------------
							//user.getBoolean("emailVerified") == true
							if (user != null ) {
							
								// If user exist and authenticated, send user to Welcome.class
								Intent intent = new Intent(MainActivity.this, searchtry.class);
								startActivity(intent);
								Toast.makeText(getApplicationContext(),"Successfully Logged in",Toast.LENGTH_LONG).show();
								
								finish();
							} 
							
							else
							{
								Toast.makeText(
										getApplicationContext(),
										"No such user exist, please signup",
										Toast.LENGTH_LONG).show();
							}
						}

			});
			
			}
		});
	}

}
