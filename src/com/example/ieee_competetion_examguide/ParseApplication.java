package com.example.ieee_competetion_examguide;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    //Parse.enableLocalDatastore(this);

    //ParseUser.enableRevocableSessionInBackground();
    // Add your initialization code here
    Parse.initialize(this,"C6OENgRu0nkHGdNBgofsLD0BtoTt7lwVAgka69h9","qxrl21oZkQsTgJptjXVY7xcj2umBdhwgDw6nFtol");


    ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
