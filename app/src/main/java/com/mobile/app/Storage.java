package com.mobile.app;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class Storage {
	private static SharedPreferences getSharedPreferences(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		return sharedPreferences;
	}
	
	public static void saveString(Context context,String key , String value){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().putString(key, value).commit();
		
	}
	
	public static String getString(Context context, String key){
		return getSharedPreferences(context).getString(key, "");
	}


	
	public static void removeString(Context context,String key){
		SharedPreferences sharedPreferences = getSharedPreferences(context);
		sharedPreferences.edit().remove(key).commit();
		
	}
	
	
	
}