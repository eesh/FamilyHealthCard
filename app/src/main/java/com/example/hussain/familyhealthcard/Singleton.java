package com.example.hussain.familyhealthcard;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Singleton extends Application {

    private static Singleton instance = null;
    private static Context appContext = null;
    private  RequestQueue requestQueue = null;

    private Singleton(Context context) {
        appContext = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Singleton getInstance(Context context) {

        if(instance == null) {
            instance = new Singleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(appContext);
        }
        return requestQueue;
    }


}
