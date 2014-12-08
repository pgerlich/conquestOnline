package conquest.online;

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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import conquest.online.gameAssets.Property;

public class MapActivity extends ActionBarActivity implements
GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	
	private UserSession user;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	
	//Will be used as the reference to the map dispayed
	public GoogleMap mMap;
	public LocationClient mLocationClient;
	
	//test wtf
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			user = new UserSession(getApplicationContext());
			
	    	if ( servicesOK() ) {
	        	setContentView(R.layout.activity_map);
	        	if(initMap())
	        	{
	        		mMap.setMyLocationEnabled(true);
	        		mMap.setIndoorEnabled(false);
	        		mMap.setBuildingsEnabled(false);
	        		mLocationClient = new LocationClient(this, this, this);
	        		mLocationClient.connect();
	        		
	        	}
	        	else{
	        		Toast.makeText(this, "CANTMAPBITCH", Toast.LENGTH_SHORT).show();
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
		// NEED TO GET CURRENT AND MAX HEALTH INFO
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
	 * Represents an asynchronous task run on a different thread.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		//private final String mUsername;
		//private final String mPassword;

		UserLoginTask(String email, String password) {
			//mUsername = email;
			//mPassword = password;
			//Instantiate task
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			return null;

			//What to do asynchronously
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {

			//end of execution
			
		}

		@Override
		protected void onCancelled() {
			//on cancel
		}
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


		Location currentLoc = mMap.getMyLocation();
		
		if(currentLoc!=null)
		{
//			Toast.makeText(this, "find current location", Toast.LENGTH_SHORT).show();
			float zoom = 18;
			LatLng ll= new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
			CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
			mMap.animateCamera(update);
			//makeMark("Property", ll.latitude, ll.longitude);
		}
    }


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
	        
			JSONObject requestProps = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/properties/requestNearbyProperties.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestProps.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					
					int numPeople = requestProps.getInt("numProperties");
					
					for (int i = 0 ; i < numPeople; i++) {
						int propertyID = requestProps.getInt("property"+i+"id");
						double propertyLat = Double.parseDouble(requestProps.getString("property"+i+"lat"));
						double propertyLon = Double.parseDouble(requestProps.getString("property"+i+"id"));
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
			/*if ( success ) {
				
				for(int i=0;i<propertyList.size();i++)
				{
					//draw all properties
				}
				
			} else {
				//error msg
			}
			*/
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


	@Override
	public void onConnected(Bundle arg0) {
		Location currentLoc=mLocationClient.getLastLocation();
		
		float zoom = 18;
		LatLng ll= new LatLng(currentLoc.getLatitude(), currentLoc.getLongitude());
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
		mMap.animateCamera(update);
		//Draw(currentLoc);
		
    	
	}


	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
