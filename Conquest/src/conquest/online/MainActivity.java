package conquest.online;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends FragmentActivity {

	//Key to define for activities to access previous activity info
	public final static String EXTRA_MESSAGE = "conquest.online.MESSAGE";
	private static final int GPS_ERRORDIALOG_REQUEST = 9001;
	
	
	
    @Override
    //This defines your first page to be started.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
      if(servicesOK())
      {
    	  Toast.makeText(this, "Ready to Map!!", Toast.LENGTH_SHORT).show();
    	  setContentView(R.layout.activity_map);
      }
      else{
    	  setContentView(R.layout.activity_main);
      }
        
       
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
    
    /** Called when the user clicks the send button on the main activity - THIS IS FOR TESTING*/
    public void goToLogin(View view) {
    	Intent login = new Intent(this, LoginActivity.class);
    	startActivity(login);
    }  
    
    
    
}