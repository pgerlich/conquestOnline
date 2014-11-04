package conquest.online;

import android.app.Dialog;
import android.content.Context;
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

public class MapActivity extends ActionBarActivity {
	
	private UserSession user;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	//test wtf
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = new UserSession(getApplicationContext());
		//user.logout();
			
    	if ( servicesOK() ) {
        	setContentView(R.layout.activity_map);
    	} else {
    		//Display error message, close gracefully?
    	}
    	updateHealth();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//Log user out - hope it didn't mess up.
		if (id == R.id.action_logout) {
			user.logout();
			goToMain();
			return true;
		} else if (id == R.id.action_settings ) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * When user taps social button, this function is called and takes user to Social Screen
	 */
	public void toSocial(View view) {
		Intent soc = new Intent(this, SocialActivity.class);
		startActivity(soc);
	}
	
	/**
	 * When user taps settings button this functino is called and takes user to the social screen
	 */
	public void settings(View view) {
		
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
		TextView health = (TextView) findViewById(R.id.health_view);
		String currentHealth = "";
		String maxHealth = "";
		// NEED TO GET CURRENT AND MAX HEALTH INFO
		String display = "Health: " + currentHealth + "/" + maxHealth;
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
}
