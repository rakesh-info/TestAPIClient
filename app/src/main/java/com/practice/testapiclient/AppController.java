package com.practice.testapiclient;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

    private static Context mContext;


    private RequestQueue mRequestQueue;

    private static AppController mInstance;
    private static void setContext(Context context) {
        mContext = context;
    }
    public static Context getAppContext() {
        return mContext;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        setContext(getApplicationContext());
        //AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
