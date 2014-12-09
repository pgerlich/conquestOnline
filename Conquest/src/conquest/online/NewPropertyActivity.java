package conquest.online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import conquest.online.client.MovementClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewPropertyActivity extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener
{
	GoogleMap mMap;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	static final int POLYGON_POINTS=4;
	Polygon shape= null;
	Marker mark;
	LocationClient mLocationClient;
	LatLng finalLL=null;
	UserSession user;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newproperty);
		user = new UserSession(getApplicationContext());
		if (initMap()) {
			// Toast.makeText(this, "RADYTOMAPPAP", Toast.LENGTH_SHORT).show();
			mLocationClient = new LocationClient(this, this, this);
			mLocationClient.connect();
		} else {
			Toast.makeText(this, "CANTMAPBITCH", Toast.LENGTH_SHORT).show();
		}

	}

	// method which finds the lat and lng of input text
	public void geoLocate(View v) throws IOException {

		EditText et = (EditText) findViewById(R.id.editText1);
		String location = et.getText().toString();
		if(location.length()== 0)
		{
			Toast.makeText(this, "PLEASE ENTER A VALID LOCATION", Toast.LENGTH_SHORT).show();
			return;
		}

		Geocoder gc = new Geocoder(this);
		List<Address> list = gc.getFromLocationName(location, 1);
		Address add = list.get(0);
		String locality = add.getLocality();
		//Toast.makeText(this, locality, Toast.LENGTH_SHORT).show();

		double lat = add.getLatitude();
		double lng = add.getLongitude();
		gotoLocation(lat, lng, 15);
		makeMark(locality, lat, lng);
		
	
	}

	// method which takes in lat lng and zoom factor to change the map camera
	// view
	private void gotoLocation(double lat, double lng, float zoom) {
		LatLng ll = new LatLng(lat, lng);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.animateCamera(update);
	}

	private boolean initMap() {
		if (mMap == null) {
			SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			mMap = mapFrag.getMap();
			if(mMap!=null)
			{
				mMap.setOnMapLongClickListener(new OnMapLongClickListener() {
					
					@Override
					public void onMapLongClick(LatLng ll) {
						Geocoder gc = new Geocoder(NewPropertyActivity.this);
						List<Address> list = null;
						try {
							list= gc.getFromLocation(ll.latitude, ll.longitude, 1);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return;
						}
						Address add= list.get(0);
						NewPropertyActivity.this.makeMark(add.getLocality(), ll.latitude, ll.longitude);
						
						GroundOverlayOptions blah = new GroundOverlayOptions()
				        .image(BitmapDescriptorFactory.fromResource(R.drawable.prop_grid))
				        .position(ll, 50f, 50f)
				        .transparency((float) 0.5);
						NewPropertyActivity.this.mMap.addGroundOverlay(blah);
						finalLL =ll;
						
					}
				});
				
			}
		}
		return (mMap != null);
	}

	public void useCurrent(View v) {
		
		Location currentLoc=mLocationClient.getLastLocation();
		if(currentLoc==null)
		{
			Toast.makeText(this, "can not find current location", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//Toast.makeText(this, "find current location", Toast.LENGTH_SHORT).show();
			float zoom = 18;
			LatLng ll= new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
			mMap.animateCamera(update);
			//makeMark("Property", ll.latitude, ll.longitude);
		}
		

	}
	
	//sends latlng coordinates to databse along with length and width of property
	public void confirmProp(View v){
		//finalLL shit
		toast("buttholes");
		PropertyPurchaseTask ppt= new PropertyPurchaseTask(user.getUser(), user.getToken(), finalLL.latitude, finalLL.longitude);
		ppt.execute();
	}
	
	
	
//next three methods are part of the googleplayservices interfaces that are implemented
	//they are in case the GPS conection fails, connects, or gets disconnected
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		Toast.makeText(this, "connected to location services", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	
	//used to write markers on the map
	private void makeMark(String identifier, double lat, double lng)
	{
		
		if(mark != null)
		{
			mark.remove();
		}
		if(markers.size() ==POLYGON_POINTS)
		{
			removeEverything();
		}
		
		
		MarkerOptions options = new MarkerOptions()
		.title(identifier)
		.snippet("info about property Stats")
		.position(new LatLng(lat, lng))
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_propicon));
	mark = mMap.addMarker(options);
	
	markers.add(mMap.addMarker(options.draggable(true)));
	
	if(markers.size()== POLYGON_POINTS)
	{
		drawPolygon();
	}
	
	}
	
	
	
	//method used to draw property
	private void drawPolygon()
	{
		PolygonOptions options = new PolygonOptions().fillColor(0x330000FF).strokeWidth(3).strokeColor(Color.BLUE);
		for(int i=0; i< POLYGON_POINTS;i++)
		{
			options.add(markers.get(i).getPosition());
		}
		shape = mMap.addPolygon(options);
	}
	
	private void removeEverything()
	{
		for(Marker marker : markers)
		{
			marker.remove();
		}
		markers.clear();
		
		shape.remove();
		shape=null;
	}
	
	//database shtuff

/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class PropertyPurchaseTask extends AsyncTask<Void, Void, Boolean> {


		private String mUsername;
		private String mToken;
		private double mLat;
		private double mLng;
		
		public String message;


		PropertyPurchaseTask(String username, String token, double lat, double lon) {
			mUsername = username;
			mToken = token;
			mLat = lat;
			mLng= lon;
			
		}


		@Override
		protected Boolean doInBackground(Void... params) {
			
			//String IP = IPGrabber.getIPAddress(false);
			
			
			try {
				//Will throw I/O exception if it fails to connect
				MovementClient mc = new MovementClient();
				
				//Have to start this on a new thread so it stays open and listens for responses
				new Thread(mc).start();
				
				mc.purchaseProp(mUsername, mToken, mLat, mLng);
				
				//Wait for a response from the server
				while ( mc.propResponse == null ) {
					
				}
						
				
				
				mc.close();
				message = mc.propResponse.message;
				return mc.propResponse.success;
				
			} catch (IOException e) {
				toast("Couldn't connect to server.");
				return false;
			}
	
			
		}


		@Override
		protected void onPostExecute(final Boolean success) {
			toast(message);
			
			if (success) {
				//TODO: Navigate to the maps page.
	        	finish();
		        goToMap();
			}
		}


		@Override
		protected void onCancelled() {

		}
	}
	
	
	public void toast(String message) {
		//Toast an error message if it exists. Will close and leave page if it doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	public void goToMap()
	{
		finish();
    	Intent map = new Intent(this, MapActivity.class);
    	startActivity(map);
	}
	
}