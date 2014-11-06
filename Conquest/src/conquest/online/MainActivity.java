package conquest.online;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends FragmentActivity {

	//Key to define for activities to access previous activity info
	public final static String EXTRA_MESSAGE = "conquest.online.MESSAGE";
	
    @Override
    //This defines your first page to be started.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);       
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
        
        if (id == R.id.action_close) {
        	finish();
            return true;
        }
        
        
        return super.onOptionsItemSelected(item);
    }
    
    /** Called when the user clicks the login button. */
    public void goToLogin(View view) {
    	finish();
    	Intent login = new Intent(this, LoginActivity.class);
    	startActivity(login);
    }  
    
    /** Called when the user clicks the register button.*/
    public void goToRegister(View view) {
    	/*
    	Intent register = new Intent(this, RegisterActivity.class);
    	startActivity(register);
    	*/
    	Intent map = new Intent(this, MapActivity.class);
    	startActivity(map);
    }  
    
}