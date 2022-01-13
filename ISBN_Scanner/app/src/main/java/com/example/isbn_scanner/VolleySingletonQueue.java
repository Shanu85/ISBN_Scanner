package com.example.isbn_scanner;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingletonQueue {
    private RequestQueue requestQueue;
    private static VolleySingletonQueue new_instance;

    private VolleySingletonQueue(Context context)
    {
        requestQueue= Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleySingletonQueue getNew_instance(Context context)
    {
        if(new_instance==null) // if instance is not created we create new instance and return it
        {
            new_instance=new VolleySingletonQueue(context);
        }
        return new_instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
