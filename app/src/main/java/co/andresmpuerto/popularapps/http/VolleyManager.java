package co.andresmpuerto.popularapps.http;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import co.andresmpuerto.popularapps.utils.Constants;


/**
 * The type Volley manager.
 *
 */
public final class VolleyManager {
    
    private static VolleyManager mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    private VolleyManager(Context context) {
        VolleyManager.mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static synchronized VolleyManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyManager(context);
        }
        return mInstance;
    }


    /**
     * Gets request queue.
     *
     * @return the request queue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), Constants.MAX_BYTES);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    /**
     * Add to request queue.
     *
     * @param req the req
     */
    public  void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }
}