package mobina.com.uniiii.Utility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class ApplicationController extends MultiDexApplication {

    public static SQLiteDatabase mydb;
    public static String DATABASE_NAME = "uniii";

    public static final String TAG = ApplicationController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static ApplicationController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        //mydb.execSQL("CREATE  TABLE  IF NOT EXISTS final (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
        //        " username TEXT NOT NULL  UNIQUE , date TEXT, tool1 DOUBLE, arz1 DOUBLE, tool2 DOUBLE," +
        //        " arz2 DOUBLE, tool3 DOUBLE, arz3 DOUBLE, tool4 DOUBLE, arz4 DOUBLE, tool5 DOUBLE, arz5 DOUBLE)");
        mydb.execSQL("CREATE  TABLE  IF NOT EXISTS location (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
                " username TEXT NOT NULL , latitude TEXT NOT NULL, longitude TEXT NOT NULL, date TEXT)");
        //mydb.close();
    }

    public static synchronized ApplicationController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
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
}