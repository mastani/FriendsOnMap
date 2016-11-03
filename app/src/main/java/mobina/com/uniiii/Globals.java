package mobina.com.uniiii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

public class Globals extends MultiDexApplication {
    public static SQLiteDatabase mydb;
    public static String DATABASE_NAME = "uniii";

    public void onCreate() {

        super.onCreate();

        mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

        //mydb.execSQL("CREATE  TABLE  IF NOT EXISTS final (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
        //        " username TEXT NOT NULL  UNIQUE , date TEXT, tool1 DOUBLE, arz1 DOUBLE, tool2 DOUBLE," +
        //        " arz2 DOUBLE, tool3 DOUBLE, arz3 DOUBLE, tool4 DOUBLE, arz4 DOUBLE, tool5 DOUBLE, arz5 DOUBLE)");


        mydb.execSQL("CREATE  TABLE  IF NOT EXISTS location (id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL  UNIQUE ," +
                " username TEXT NOT NULL , latitude TEXT NOT NULL, longitude TEXT NOT NULL, date TEXT)");

        //mydb.close();
    }
}



