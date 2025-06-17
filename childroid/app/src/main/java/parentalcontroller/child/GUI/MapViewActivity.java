package parentalcontroller.child.GUI;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import parentalcontroller.child.R;

public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {
	private GoogleMap mMap;
	private static final LatLng DEFAULT_LOCATION = new LatLng(1.352566007, 103.78921587);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewlayout);

		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapView);
		mapFragment.getMapAsync(this);
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMap = googleMap;

		// Add a marker at the default location and move the camera
		mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).title("Marker"));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 13));

		// Enable satellite view
		mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

		// Set click listener
		mMap.setOnMapClickListener(latLng -> {
			Toast.makeText(
				getBaseContext(),
				"Location: " + latLng.latitude + "," + latLng.longitude,
				Toast.LENGTH_SHORT
			).show();
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_3:
			mMap.animateCamera(CameraUpdateFactory.zoomIn());
			break;
		case KeyEvent.KEYCODE_1:
			mMap.animateCamera(CameraUpdateFactory.zoomOut());
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}