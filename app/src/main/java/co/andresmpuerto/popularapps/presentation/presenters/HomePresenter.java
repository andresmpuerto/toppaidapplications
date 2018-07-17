package co.andresmpuerto.popularapps.presentation.presenters;

import android.app.Activity;
import android.app.ProgressDialog;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.andresmpuerto.popularapps.database.Preferences;
import co.andresmpuerto.popularapps.http.VolleyManager;
import co.andresmpuerto.popularapps.models.App;
import co.andresmpuerto.popularapps.models.Category;
import co.andresmpuerto.popularapps.presentation.views.HomeActivity;
import co.andresmpuerto.popularapps.utils.Constants;
import co.andresmpuerto.popularapps.utils.Utils;

/**
 * @author andresmpuerto
 * @since 15/07/18.
 */
public class HomePresenter {
  
  private Activity activity;
  public static final String TAG = HomePresenter.class.getSimpleName();
  private ProgressDialog progressDialog;
  private int responseCode;
  private Response.Listener<JSONObject> jsonResponseListener;
  private Response.ErrorListener errorListener;
  private Map<String, String> params;
  private Map<String, String> headers;
  private JsonObjectRequest jsonObjectRequest;
  
  public HomePresenter(Activity activity) {
    this.activity = activity;
    progressDialog = new ProgressDialog(activity);
    params = new LinkedHashMap<>();
    headers = new HashMap<>();
  }
  
  private void addJSONHeaders() {
    addHeader("Accept", "application/json");
    addHeader("Content-type", "application/json");
  }
  
  public void addHeader(String name, String value)
  {
    headers.put(name, value);
  }
  
  public void getInfo(){
    progressDialog.setCancelable(false);
    dismissProgressDialog();
    progressDialog.setMessage("Loading...");
    progressDialog.show();
    
    if (Utils.isConnected(activity)) {
      addToRequestQueue(
          createJsonObjectRequest(Constants.GET, Constants.URL_SERVICE));
    }else{
      ((HomeActivity) activity).adviceToView(0);
    }
  }
  
  public void dismissProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }
  
  private <X> void addToRequestQueue(Request<X> req, String tag) {
    req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
    VolleyManager.getInstance(activity).getRequestQueue().add(req);
  }
  
  private <X> void addToRequestQueue(Request<X> req)
  {
    addToRequestQueue(req, "");
  }
  
  private void setListeners() {
    jsonResponseListener = new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        dismissProgressDialog();
        try {
          JSONObject data = response.getJSONObject("feed");
          JSONArray entries = data.getJSONArray("entry");
          
          List<Category> categories = new ArrayList<>();
          List<App> apps = new ArrayList<>();
          List<Integer> singleton = new ArrayList<>();
          for (int i = 0; i < entries.length(); i++) {
            App app = new App();
            Category category = new Category();
            app.setTitle(entries.getJSONObject(i).getJSONObject("title").getString("label"));
            
            app.setAuthor(entries.getJSONObject(i).getJSONObject("im:artist").getString("label"));
           
            app.setId_category(entries.getJSONObject(i)
                .getJSONObject("category").getJSONObject("attributes").getInt("im:id"));
            
            app.setCategory(entries.getJSONObject(i)
                .getJSONObject("category").getJSONObject("attributes").getString("label"));
            
            app.setPrice(entries.getJSONObject(i)
                .getJSONObject("im:price").getString("label"));
  
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-07:00");
            
            app.setDate_release(f.parse(entries.getJSONObject(i)
                .getJSONObject("im:releaseDate").getString("label")));
            
            app.setId(i);
            
            if (!entries.getJSONObject(i).
                getJSONArray("im:image").
                isNull(1)) {
              app.setUrl_image(entries.getJSONObject(i).
                  getJSONArray("im:image").
                  getJSONObject(1).
                  getString("label"));
            }else{
              app.setUrl_image(entries.getJSONObject(i).
                  getJSONArray("im:image").
                  getJSONObject(0).
                  getString("label"));
            }
            app.setSummary(entries.getJSONObject(i).getJSONObject("summary").getString("label"));
            
            int id = entries.getJSONObject(i)
                .getJSONObject("category").getJSONObject("attributes").getInt("im:id");
            
            if (singleton.size() == 0 || !singleton.contains(id)) {
              category.setId(id);
              category.setTitle(entries.getJSONObject(i)
                  .getJSONObject("category").getJSONObject("attributes").getString("label"));
              singleton.add(id);
              categories.add(category);
            }
            apps.add(app);
          }

          Gson gson = new Gson();
          String c = gson.toJson(categories);
          String a = gson.toJson(apps);
          
          Preferences.setApps(activity, a);
          
          ((HomeActivity) activity).setList(categories);
        } catch (JSONException e) {
          
          Log.e(TAG, e.getMessage());
        } catch (ParseException e) {
          e.printStackTrace();
        }
      }
    };
    
    errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        dismissProgressDialog();
        ((HomeActivity) activity).adviceToView(responseCode);
      }
    };
  }
  
  
  public JsonObjectRequest createJsonObjectRequest(int method,String url ) {
    addJSONHeaders();
    setListeners();
    jsonObjectRequest = new JsonObjectRequest(method, url, null, jsonResponseListener, errorListener){
      
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        return headers;
      }
      
      @Override
      protected Map<String, String> getParams() {
        return params;
      }
      
      @Override
      protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        responseCode = response.statusCode;
        return super.parseNetworkResponse(response);
      }
      
      @Override
      protected VolleyError parseNetworkError(VolleyError volleyError) {
        responseCode = volleyError.networkResponse.statusCode;
        return super.parseNetworkError(volleyError);
      }
    };
    
    return jsonObjectRequest;
  }
  
}
