package mobina.com.uniiii.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import mobina.com.uniiii.R;
import mobina.com.uniiii.abstracts.Group;
import mobina.com.uniiii.abstracts.User;

public class TrackGroupActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

        Bundle bundle = getIntent().getExtras();
        Group group = (Group) bundle.getSerializable("group");

        LatLng pos = new LatLng(0, 0);

        for (User user : group.getMembers()) {
            if (user.getLatitude().isEmpty() || user.getLongitude().isEmpty())
                continue;

            pos = new LatLng(Double.valueOf(user.getLatitude()), Double.valueOf(user.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(pos).title("مکان " + user.getName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        }
    }
}
