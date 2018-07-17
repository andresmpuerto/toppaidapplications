package co.andresmpuerto.popularapps.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class Utils {
  
  public static boolean isConnected(Context context) {
    ConnectivityManager connMgr = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo;
    if (connMgr != null) {
      activeNetworkInfo = connMgr.getActiveNetworkInfo();
      return (activeNetworkInfo != null &&
          activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable());
    }
    return false;
    
  }
}
