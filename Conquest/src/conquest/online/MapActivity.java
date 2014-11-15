package conquest.online;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class MapActivity extends ActionBarActivity {
	
	private UserSession user;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	//Will be used as the reference to the map dispayed
	GoogleMap mMap;
	
	//test wtf
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			user = new UserSession(getApplicationContext());
			
	    	if ( servicesOK() ) {
	        	setContentView(R.layout.activity_map);
	        	if(initMap())
	        	{
	        		//Toast.makeText(this, "RADYTOMAPPAP", Toast.LENGTH_SHORT).show();
	        		mMap.setMyLocationEnabled(true);
	        	}
	        	else{
	        		Toast.makeText(this, "CANTMAPBITCH", Toast.LENGTH_SHORT).show();
	        	}
	        	
	    	} else {
	    		//Display error message, close gracefully?
	    	}
	    	updateHealth();
	    	
		
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
		Intent soc = new Intent(this, SocialActivity.class);
		startActivity(soc);
	}
	
	public void shop(View view) {
		Intent soc = new Intent(this, ShopActivity.class);
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
		Intent property = new Intent(this, PropertyActivity.class);
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
		String display = "Health: " + currentHealth + "/" + maxHealth + " " + percentHealth;
		health.setText(display);
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
}