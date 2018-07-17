package co.andresmpuerto.popularapps.database;

import android.content.Context;
import android.content.SharedPreferences;

import co.andresmpuerto.popularapps.utils.Constants;

public class Preferences {
  
  private static SharedPreferences getSharedPreferences(Context context) {
    return context.getSharedPreferences(Constants.PREFERENCE_APPS, Context.MODE_PRIVATE);
  }
  
  public static void setApps(Context context, String entry) {
    SharedPreferences.Editor editor = Preferences.getSharedPreferences(context).edit();
    if (entry != null)
      editor.putString(Constants.PREFERENCE_APPS, entry);
    else
      editor.remove(Constants.PREFERENCE_APPS);
    editor.apply();
  }
  
  public static String getApps(Context context) {
    SharedPreferences sharedPref = Preferences.getSharedPreferences(context);
    return sharedPref.getString(Constants.PREFERENCE_APPS, "");
  }
  
  public static void setCategories(Context context, String entry) {
    SharedPreferences.Editor editor = Preferences.getSharedPreferences(context).edit();
    if (entry != null)
      editor.putString(Constants.PREFERENCE_CATEGORIES, entry);
    else
      editor.remove(Constants.PREFERENCE_CATEGORIES);
    editor.apply();
  }
  
  public static String getCategories(Context context) {
    SharedPreferences sharedPref = Preferences.getSharedPreferences(context);
    return sharedPref.getString(Constants.PREFERENCE_CATEGORIES, "");
  }
  
  public static void clear (Context context){
    SharedPreferences.Editor editor = Preferences.getSharedPreferences(context).edit();
    editor.clear();
    editor.apply();
    
  }
}