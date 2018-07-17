package co.andresmpuerto.popularapps.utils;

import com.android.volley.Request;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class Constants {
  
  public static String URL_SERVICE = "https://itunes.apple.com/us/rss/toppaidapplications/limit=20/json";
  public static int MAX_BYTES = 10485760;
  public static final int SPLASH_SCREEN_DELAY = 800;
  
  public static int POST = Request.Method.POST;
  public static int GET = Request.Method.GET;
  public static int PUT = Request.Method.PUT;
  
  public static String PREFERENCE_NAME = "top_apps";
  public static String PREFERENCE_ENTRIES = "entry";
  public static String PREFERENCE_CATEGORIES = "categories";
  public static String PREFERENCE_APPS = "apps";
  
}
