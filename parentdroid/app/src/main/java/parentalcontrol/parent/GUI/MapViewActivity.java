package parentalcontrol.parent.GUI;

import java.util.List;
import java.util.ArrayList;

import parentalcontrol.parent.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;

public class MapViewActivity extends Activity implements OnMapReadyCallback {
    //Debug
    private static boolean D = false;
    private static String TAG = "ParentDroid";
    private GoogleMap map;
    private LatLng currentLocation;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    // The minimum distance to change Updates in meters
    private static final long DISTANCE = 1;
    // The minimum time between updates in milliseconds
    private static final long TIME_INTERVAL = 1000 * 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapviewlayout);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        setLocationListener(locationManager);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.getUiSettings().setZoomControlsEnabled(true);
    }

    /**
     * Show the location on Map
     */
    private void showLocationOnMap(Location location) {
        if (map == null) return;
        
        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
        
        // Add a location marker
        map.clear();
        map.addMarker(new MarkerOptions()
                .position(currentLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
    }

    /**
     * Set a location listener
     */
    private void setLocationListener(LocationManager locationManager) {
        // getting GPS status
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                showLocationOnMap(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };
        // Register the listener to receive location updates
        if (isNetworkEnabled) {
            showLocationOnMap(locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    TIME_INTERVAL, DISTANCE, locationListener);
            if(D) Log.d(TAG, "Network provider : "+isNetworkEnabled);
        } else if (isGPSEnabled) {
            showLocationOnMap(locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER));
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    TIME_INTERVAL, DISTANCE, locationListener);
            if(D) Log.d(TAG, "GPS provider : "+isGPSEnabled);
        } else {
            Toast.makeText(getBaseContext(), "No Network Available.",
                    Toast.LENGTH_SHORT).show();
            if(D) Log.d(TAG, "Network provider : "+isNetworkEnabled + " GPS provider : "+isGPSEnabled);
        }
    }

    /**
     * Get KEYCODE
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (map == null) return super.onKeyDown(keyCode, event);
        
        switch (keyCode) {
            case KeyEvent.KEYCODE_3:
                map.animateCamera(CameraUpdateFactory.zoomIn());
                break;
            case KeyEvent.KEYCODE_1:
                map.animateCamera(CameraUpdateFactory.zoomOut());
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        // Remove the listener
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
        super.onDestroy();
    }
}