package conquest.online;

/**
 * Work with adjusting movement speeds
 * Clean up the code a bit
 * Get multiple clients showing up again with a new movement algorithm
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.c

import conquest.client.classes.PersonNearYou;
import conquest.online.client.MovementClient;
import conquest.online.gameAssets.Property;

public class MapActivity extends ActionBarActivity implements
GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	
	private UserSession user;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	
	//calculated so that user with speed 1 has a speed of 6.9 mph --just above the average running speed.
	private static final double SPEED_FACTOR = 1.0 / 360000.0;
	
	/**
	 * Character rendering shizz
	 */
	//used to represent the user's character *note we can set this to a bitmap see link below*
	//http://developer.android.com/reference/com/google/android/gms/maps/model/MarkerOptions.html
	private MarkerOptions characterMarkerOptions;
	private Marker characterMarker;
	
	private ArrayList<Marker> characters;
	
	public MoveCharacters currentMove;
	
	public CharacterTracker charTracker;
	
	/**
	 * End Character rendering shiz
	 */
	
	
	/**
	 * Google Maps Shizz
	 */
	//Will be used as the reference to the map dispayed
	public GoogleMap mMap;
	public LocationClient mLocationClient;
	
	/**
	 * End Google Maps shizz
	 */

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			user = new UserSession(getApplicationContext());
			
	    	if ( servicesOK() ) {
	        	setContentView(R.layout.activity_map);
	        	if (initMap()) {
	        		
	        		//Some modifications to the maps display
	        		mMap.setMyLocationEnabled(true);
	        		mMap.setIndoorEnabled(false);
	        		mMap.setBuildingsEnabled(false);
	 
	        		//Location services
	        		mLocationClient = new LocationClient(this, this, this);
	        		mLocationClient.connect();
	        		
	        		//Characters nearby
	        		characters = new ArrayList<Marker>();
	        		
	        		//For tracking all other users movement
	        		charTracker = new CharacterTracker();
	        		charTracker.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        		
	        		currentMove = new MoveCharacters(user.getUser(), user.getToken(), user.getLocation(), user.getLocation());

	                mMap.setOnMapClickListener(new OnMapClickListener() {
						@Override
						public void onMapClick(LatLng targetLocation) {
							
							if(currentMove.getStatus() == AsyncTask.Status.RUNNING ){
								currentMove.cancel(true);
							} 
							
							currentMove = new MoveCharacters(user.getUser(), user.getToken(), user.getLocation(), targetLocation);
							currentMove.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
						}
	                });
	        		
	        	}
	        	else{
	        		Toast.makeText(this, "Failed to initialize map. Restart and try again.", Toast.LENGTH_SHORT).show();
	        	}
	        	
	    	} else {
	    		//Display error message, close gracefully?
	    	}
	    	
	    	setView();
		}

	/**
	 * Go to the settings
	 */
	public void goToSettings(){
    	Intent settings = new Intent(this, SettingsActivity.class);
    	startActivity(settings);
	}	
	
	/**
	 * When user taps social button, this function is called and takes user to Social Screen
	 */
	public void toSocial(View view) {
		Intent soc = new Intent(this, NewSocialActivity.class);
		startActivity(soc);
	}
	
	public void shop(View view) {
		Intent soc = new Intent(this, PersonalPropertyActivity.class);
		startActivity(soc);
	}
	/**
	 * When user taps settings button this functino is called and takes user to the social screen
	 */
	public void settings(View view) {
		goToSettings();
	}
	
	/**
	 * Log the user out when they click the back button
	 * @param view
	 */
	public void logout(View view) {
		//Prompt the user
		user.logout();
		goToMain();
	}
	
	/**
	 * When user taps character button the user is taken to the character screen
	 */
	public void myCharacter(View view) {
		Intent character = new Intent(this, CharacterActivity.class);
		startActivity(character);
	}
	
	/**
	 * when user taps the home button they are taken to their property if they have one
	 */
	public void goHome(View view) {
		Intent property = new Intent(this, PersonalPropertyActivity.class);
		startActivity(property);
	}
	
	/**
	 * Used to adjust the health that is shown on the map screen
	 */
	public void updateHealth() {
		TextView health = (TextView) findViewById(R.id.playerHealthView);
		double currentHealth = user.getCurHealth();
		double maxHealth = user.getMaxHealth();
		double percentHealth = currentHealth / maxHealth;
		String display = "Health: " + currentHealth + "/" + maxHealth + " %" + percentHealth;
		health.setText(display);
	}
	
	/**
	 * When user taps the newPropbutton it takes you to the new property screen
	 */
	public void addNewProp(View view) {
		Intent newProp = new Intent(this, NewPropertyActivity.class);
		startActivity(newProp);
	}
	
	/**
	 * Go to the main menu and close the current activity
	 */
	public void goToMain(){
		finish();
    	Intent main = new Intent(this, MainActivity.class);
    	startActivity(main);
	}
	
	/**
	 * Used to manage all characters displayed on the screen
	 * @author Paul
	 *
	 */
	public class CharacterTracker extends AsyncTask<Void, Void, Boolean>{
		
		public boolean run;
		public MovementClient mc;
		private ArrayList<MarkerOptions> nearbyPlayers;
		
		CharacterTracker(){
			run = true;
			nearbyPlayers = new ArrayList<MarkerOptions>();
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			try {
				MovementClient mc = new MovementClient();
				
				MarkerOptions m;
				
				while (run) {
					
					if ( mc.nearbyPeople.size() != 0 ) {
					
						//Move everyone one unit vector in the direction they are going
						for ( PersonNearYou p : mc.nearbyPeople) {
							
							LatLng current = new LatLng(p.curLat, p.curLng);
							LatLng destination = new LatLng(p.destLat, p.destLng);
							
							LatLng moveVector = getUnitMovementVector(current, destination);
		
							if ( current != destination ) {
								//Update position
								p.curLat += moveVector.latitude;
								p.curLng += moveVector.longitude;
								
								//Update visual location
								m = new MarkerOptions();
								m.alpha((float)p.maxHealth / (float)p.curHealth);
								m.title(p.user);
								m.position(new LatLng(p.curLat, p.curLng));
								nearbyPlayers.add(m);
							}
						}
						
						//Update the map with their locations.
						update(nearbyPlayers);
					}
					
				}

				return true;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		
		@Override
	    protected void onCancelled() {
	        this.cancel(true);
	    }
		
		@Override
		protected void onPostExecute(final Boolean success) {
			//end of execution
		}
		
		/**
		 * takes in a start and destination LatLng and outputs a vector to be added to position every cycle.
		 * @param p1
		 * @param p2
		 * @return
		 */
		protected LatLng getUnitMovementVector(LatLng p1, LatLng p2) {
			double Lat = p2.latitude - p1.latitude;
			double Lon = p2.longitude - p1.longitude;
			double vectorMagnitude = Math.sqrt(Math.pow(Lat, 2) + Math.pow(Lon, 2));
			int speed = user.getSpeed();
			Lat = (Lat / vectorMagnitude) * speed * SPEED_FACTOR;//SPEED_FACTOR = 1.0 / 360000.0 
			Lon = (Lon / vectorMagnitude) * speed * SPEED_FACTOR;
			return new LatLng(Lat, Lon);
		}

	}

	public class MoveCharacters extends AsyncTask<Void, Void, Boolean>{
		
		private String username;
		private String token;
		private LatLng current;
		private LatLng destination;
		
		MoveCharacters(String user, String token, LatLng current, LatLng destination){
			this.username = user;
			this.token = token;
			this.current = current;
			this.destination = destination;
		}
		
		@Override
		protected Boolean doInBackground(Void... params) {
			// asynchronous Task
			MovementClient mc;
			
			try {
				mc = new MovementClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}
			
			//Update the server with our movement and current location as we begin moving
			mc.updateLocation(username, token, current, destination);
			
			int i=0;
			
			LatLng moveVector = getUnitMovementVector(current, destination);
			LatLng direction;

			do{
				i++;
				current = new LatLng(current.latitude + moveVector.latitude, current.longitude + moveVector.longitude);
				publishProgress();
				if(i % 10 == 0){
					user.setLocation(current);
				}
				
				direction = getMovementVector(current, destination);
				if (this.isCancelled()) break;
				
				try {
				    Thread.sleep(100);                 //10 times/second
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
			} while(moveVector.latitude * direction.latitude >= 0 && moveVector.longitude * direction.longitude >= 0);
			//^^ while movement vector and vector currently pointing to destination point in the same direction
			
			//Update the server with our location once we have arrived
			mc.updateLocation(username, token, current, new LatLng(0,0));
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Void... progress) {
			//every time there is a new location
	        user.setLocation(current);
	        characterMarker.setPosition(current);
	     }
		
		@Override
	    protected void onCancelled() {
	        this.cancel(true);
	    }
		
		@Override
		protected void onPostExecute(final Boolean success) {
			//end of execution
		}
		
	/**
	 * takes in a start and destination LatLng and outputs a vector to be added to position every cycle.
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected LatLng getUnitMovementVector(LatLng p1, LatLng p2) {
		double Lat = p2.latitude - p1.latitude;
		double Lon = p2.longitude - p1.longitude;
		double vectorMagnitude = Math.sqrt(Math.pow(Lat, 2) + Math.pow(Lon, 2));
		int speed = user.getSpeed();
		Lat = (Lat / vectorMagnitude) * speed * SPEED_FACTOR;//SPEED_FACTOR = 1.0 / 360000.0 
		Lon = (Lon / vectorMagnitude) * speed * SPEED_FACTOR;
		return new LatLng(Lat, Lon);
	}
	
	
	/**
	 * Used when only the direction of the vector is needed because it takes less processing power.
	 * @param p1
	 * @param p2
	 * @return
	 */
	protected LatLng getMovementVector(LatLng p1, LatLng p2){
		double Lat = p2.latitude - p1.latitude;
		double Lon = p2.longitude - p1.longitude;
		return new LatLng(Lat, Lon);
	}
		
	}
		
	/**
	 * This refreshes all of the markers on the map (besides ours??)
	 * @param newOptions
	 */
	public void update(ArrayList<MarkerOptions> newOptions){
		
		//Clear the current characters
		characters.clear();
		
		//Now fill this with our new positions
		for(int i = 0; i < newOptions.size(); i++){
			characters.add(mMap.addMarker(newOptions.get(i)));
		}
		
		Toast.makeText(getApplicationContext(), "Called Update!", Toast.LENGTH_SHORT).show();
	}
	
    //method used to check if device is connected to google play services
    public boolean servicesOK()
    {
    	int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    	
    	if(isAvailable == ConnectionResult.SUCCESS){
    		return true;
    	}
    	else if(GooglePlayServicesUtil.isUserRecoverableError(isAvailable)){
    		Dialog dialog = GooglePlayServicesUtil.getErrorDialog(isAvailable, this, GPS_ERRORDIALOG_REQUEST);
    		dialog.show();
    	}
    	else{
    		Toast.makeText(this, "Can't Connect to Google Play Services", Toast.LENGTH_SHORT).show();
    	}
    	
    	return false;
    }
    
    private boolean initMap() {
    	if (mMap == null){
    		SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    		mMap=mapFrag.getMap();

    	}
    	return (mMap!=null);
    }
    
    
    
    //draws all properties found in database
    public void Draw(Location currentLoc)
    {
    	
    	getNearbyProperties gnp = new getNearbyProperties(user.getUser(), user.getToken(),currentLoc.getLatitude()+"",currentLoc.getLongitude()+"");
    	gnp.execute();
    	
    	Toast.makeText(this, "NO WAY", Toast.LENGTH_SHORT).show();
    }
    
    private void setView(){

		LatLng playerLoc = user.getLocation();
		
		if(playerLoc != null)
		{
			float zoom = 18;
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(playerLoc, zoom);
			mMap.animateCamera(update);
			//makeMark("Property", ll.latitude, ll.longitude);
		}
    }
    
    
    //This should be deprecated once the new movement rendering is setup
//    /**
//     * Marks all characters nearby
//     * @author Paul
//     *
//     */
//	public class MarkCharacters extends AsyncTask<Void, ArrayList<MarkerOptions>, Boolean>{
//		
//		private LatLng loc;
//		private ArrayList<MarkerOptions> markerOptions;
    
//		
//		public MarkCharacters(LatLng l){
//			this.loc = l;
//		}
//		
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			// asynchronous Task
//			
//			PersonNearYou p;
//			MarkerOptions m;
//			markerOptions = new ArrayList<MarkerOptions>();
//			
//			try {
//				MovementClient mc; = new MovementClient();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				return false;
//			}
//				
//				for(int i = 0; i < mc.personResponse.size(); i++){
//					p = mc.personResponse.get(i);
//					m = new MarkerOptions();
//					m.alpha((float)p.maxHealth / (float)p.curHealth);
//					m.title(p.user);
//					m.position(new LatLng(p.x, p.y));
//					markerOptions.add(m);
//	        	}
//				
//			return true;
//		}
//		
//		@Override
//		protected void onProgressUpdate(ArrayList<MarkerOptions>... progress) {
//			//every time there is a new location
//			
//	     }
//		
//		@Override
//	    protected void onCancelled() {
//	        this.cancel(true);
//	    }
//		
//		@Override
//		protected void onPostExecute(final Boolean success) {
//			if(success){
//				update(markerOptions);
//			}
//		}
//
//	
//	}
    
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class getNearbyProperties extends AsyncTask<Void, Void, Boolean> {


		private final String myUser;
		private final String token;
		private final String lat;
		private final String lon;
		
		public String message;
		public ArrayList<Property> propertyList;


		getNearbyProperties(String myUser, String token, String lat, String lon) {
			this.myUser = myUser;
			this.token = token;
			this.lat = lat;
			this.lon = lon;
			propertyList = new ArrayList<Property>(10);
		}


		@Override
		protected Boolean doInBackground(Void... params) {
//			Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
	        postParams.add(new BasicNameValuePair("user", myUser));
	        postParams.add(new BasicNameValuePair("token", token));
	        postParams.add(new BasicNameValuePair("lat", lat));
	        postParams.add(new BasicNameValuePair("lon", lon));
	        
			JSONObject requestProps = JSONfunctions.getJSONfromURL("10.191.222.243/functions/properties/requestNearbyProperties.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestProps.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					
					int numPeople = requestProps.getInt("numProperties");
					
					for (int i = 0 ; i < numPeople; i++) {
						int propertyID = requestProps.getInt("property"+i+"id");
						double propertyLat = Double.parseDouble(requestProps.getString("property"+i+"lat"));
						double propertyLon = Double.parseDouble(requestProps.getString("property"+i+"lon"));
						LatLng location = new LatLng(propertyLat, propertyLon);
						Property p = new Property(propertyID, location);
						propertyList.add(p);

					}
					
					message = requestProps.getString("message");


					return true;
					
				//Set error message and return false.
				} else {
					message = requestProps.getString("message");
					return false;
				}
			
			//Off chance that some weird shit happens
			} catch (JSONException e) {
				//Something went wrong - typically JSON value doesn't exist (success).
				message = "An error occured. Please try again later.";
				return false;
			}
			
		}


		@Override
		protected void onPostExecute(final Boolean success) {
			if ( success ) {
				
				for(int i=0;i<propertyList.size();i++)
				{
					LatLng ll= new LatLng(propertyList.get(i).getLatitude(), propertyList.get(i).getLongitude());
					GroundOverlayOptions blah = new GroundOverlayOptions()
			        .image(BitmapDescriptorFactory.fromResource(R.drawable.prop_grid))
			        .position(ll, 50f, 50f)
			        .transparency((float) 0.5);
					MapActivity.this.mMap.addGroundOverlay(blah);
				}
				
			} else {
				//error msg
			}
			
		}


		@Override
		protected void onCancelled() {
			//Nothing
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method for setting our users location and such.
	 */
	@Override
	public void onConnected(Bundle arg0) {
		
		LatLng playerLoc = user.getLocation();
		
		//if the user's character is a new character set their position to user's
		if( playerLoc.latitude == 0 && playerLoc.longitude == 0 ){
			Location currentLoc = mLocationClient.getLastLocation();
			playerLoc = new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
			user.setLocation(playerLoc);
		}
		
		//Set camera zoom and such
		float zoom = 18;
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(playerLoc, zoom);
		mMap.animateCamera(update);
		

		//Draw(currentLoc);
		
		//Add users marker and such
		MarkerOptions m = new MarkerOptions();
		m.position(playerLoc);
		characterMarker = mMap.addMarker(m);
		
		//Now disable your location because you won't need it.
		mMap.setMyLocationEnabled(false);
	}

	

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
