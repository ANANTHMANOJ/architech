package in.major_team.project;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AmNayak on 16-03-2018.
 */

public class singleton {
    private static singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mctx;

    private singleton(Context context)
    {
        mctx = context;
        requestQueue=getRequestQueue();
    }

    public static synchronized singleton getmInstance(Context context)
    {
        if(mInstance==null)
        {
             mInstance=new singleton(context);
        }
        return mInstance;

    }


    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mctx);
        }
        return requestQueue;
    }

    public  <T>void  addToRequestQueue(Request<T> request)
    {
        requestQueue.add(request);
    }

}
