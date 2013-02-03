package com.taskwar.android.util;

import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref.Scope;

@SharedPref(Scope.UNIQUE)
public interface SharedPrefs {
	
	int currentUserId();
	
	int currentHouseId();
	
	String currentHouseName();
	
	int currentHousemateId();
	
	boolean userLoggedIn();	
}
