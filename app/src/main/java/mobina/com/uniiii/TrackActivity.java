package mobina.com.uniiii;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;

import mobina.com.uniiii.Utility.ApplicationController;

public class TrackActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String t1, t2, t3, t4, t5;
    String ar1, ar2, ar3, ar4, ar5;

    ArrayList<LatLng> list = new ArrayList<LatLng>();
    ArrayList<String> datelist = new ArrayList<String>();
    private double arzz1, arzz2, arzz3, arzz4, arzz5, tt1, tt2, tt3, tt4, tt5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /////////////////////////////////////////////////
        Bundle bundle = getIntent().getExtras();
//Extract the data…
        String moh = bundle.getString("meghdar");
        Toast.makeText(TrackActivity.this, moh, Toast.LENGTH_SHORT).show();
        ApplicationController.getInstance().mydb = openOrCreateDatabase(ApplicationController.getInstance().DATABASE_NAME, Context.MODE_PRIVATE, null);

            Cursor tool1 = ApplicationController.getInstance().mydb.rawQuery("SELECT tool1 FROM final WHERE username = '" + moh + "'", null);
            Cursor tool2 = ApplicationController.getInstance().mydb.rawQuery("SELECT tool2 FROM final WHERE username = '" + moh + "'", null);
            Cursor tool3 = ApplicationController.getInstance().mydb.rawQuery("SELECT tool3 FROM final WHERE username = '" + moh + "'", null);
            Cursor tool4 = ApplicationController.getInstance().mydb.rawQuery("SELECT tool4 FROM final WHERE username = '" + moh + "'", null);
            Cursor tool5 = ApplicationController.getInstance().mydb.rawQuery("SELECT tool5 FROM final WHERE username = '" + moh + "'", null);
            tool1.moveToNext();
            tool2.moveToNext();
            tool3.moveToNext();
            tool4.moveToNext();
            tool5.moveToNext();

            t1 = tool1.getString(tool1.getColumnIndex("tool1"));
            t2 = tool2.getString(tool2.getColumnIndex("tool2"));
            t3 = tool3.getString(tool3.getColumnIndex("tool3"));
            t4 = tool4.getString(tool4.getColumnIndex("tool4"));
            t5 = tool5.getString(tool5.getColumnIndex("tool5"));


            /////////////////////////////////////// bazyabi arz ha
            Cursor arz1 = ApplicationController.getInstance().mydb.rawQuery("SELECT arz1 FROM final WHERE username = '" + moh + "'", null);
            Cursor arz2 = ApplicationController.getInstance().mydb.rawQuery("SELECT arz2 FROM final WHERE username = '" + moh + "'", null);
            Cursor arz3 = ApplicationController.getInstance().mydb.rawQuery("SELECT arz3 FROM final WHERE username = '" + moh + "'", null);
            Cursor arz4 = ApplicationController.getInstance().mydb.rawQuery("SELECT arz4 FROM final WHERE username = '" + moh + "'", null);
            Cursor arz5 = ApplicationController.getInstance().mydb.rawQuery("SELECT arz5 FROM final WHERE username = '" + moh + "'", null);
            arz1.moveToNext();
            arz2.moveToNext();
            arz3.moveToNext();
            arz4.moveToNext();
            arz5.moveToNext();

            ar1 = arz1.getString(arz1.getColumnIndex("arz1"));
            ar2 = arz2.getString(arz2.getColumnIndex("arz2"));
            ar3 = arz3.getString(arz3.getColumnIndex("arz3"));
            ar4 = arz4.getString(arz4.getColumnIndex("arz4"));
            ar5 = arz5.getString(arz5.getColumnIndex("arz5"));

///////////////////////////// tabdil format
    double tt1=Double.valueOf(t1).doubleValue();
        double arzz1 = Double.valueOf(ar1).doubleValue();
        double tt2 = Double.valueOf(t2).doubleValue();
        double arzz2 = Double.valueOf(ar2).doubleValue();
        double tt3 = Double.valueOf(t3).doubleValue();
        double arzz3 = Double.valueOf(ar3).doubleValue();
        double tt4 = Double.valueOf(t4).doubleValue();
        double arzz4 = Double.valueOf(ar4).doubleValue();
        double tt5 = Double.valueOf(t5).doubleValue();
        double arzz5 = Double.valueOf(ar5).doubleValue();





    Cursor date1 = ApplicationController.getInstance().mydb.rawQuery("SELECT date FROM final WHERE username = '" + moh + "'", null);
    date1.moveToNext();
    String date11 = date1.getString(date1.getColumnIndex("date"));
    datelist.add(date11);
    //  arz1.moveToNext();

    LatLng p1 = new LatLng(tt1, arzz1);
    LatLng p2 = new LatLng(tt2, arzz2);
    LatLng p3 = new LatLng(tt3, arzz3);
    LatLng p4 = new LatLng(tt4, arzz4);
    LatLng p5 = new LatLng(tt5, arzz5);
    list.addAll(Arrays.asList(p1,p2,p3,p4,p5));


    /////////////////////////////////////// bazyabi tarikh

    Toast.makeText(TrackActivity.this,"tool :"+tt1+" Arz : "+arzz1,Toast.LENGTH_LONG).

    show();

}




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

       try
       {Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4))
                .width(7)
                .color(Color.RED));
        mMap.addMarker(new MarkerOptions().position(list.get(0)).title("مکان اول").snippet(datelist.get(0)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(0)));
        mMap.addMarker(new MarkerOptions().position(list.get(1)).title("مکان دوم"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(1)));
        mMap.addMarker(new MarkerOptions().position(list.get(2)).title("مکان سوم"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(2)));
        mMap.addMarker(new MarkerOptions().position(list.get(3)).title("مکان چهارم"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(3)));
        mMap.addMarker(new MarkerOptions().position(list.get(4)).title("مکان پنجم"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(4)));}

       catch (Exception e)
       {
           Toast.makeText(TrackActivity.this, "موقعیت جغرافیایی کاربر در دسترس نیست", Toast.LENGTH_SHORT).show();

       }

    }

}