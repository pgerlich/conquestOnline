package conquest.online;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.support.v7.app.ActionBarActivity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MapActivity extends ActionBarActivity {
	
	private UserSession user;
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;

	//test
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user = new UserSession(getApplicationContext());
		
    	if ( servicesOK() ) {
        	setContentView(R.layout.activity_map);
    	} else {
    		//Display error message, close gracefully?
    	}
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}
	
	/**
	 * Go to the main menu and close the current activity
	 */
	public void goToMain(){
		finish();
    	Intent main = new Intent(this, MainActivity.class);
    	startActivity(main);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//Log user out - hope it didn't mess up.
		if (id == R.id.action_logout) {
			user.logOut();
			goToMain();
			return true;
		} else if (id == R.id.action_settings ) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
}
