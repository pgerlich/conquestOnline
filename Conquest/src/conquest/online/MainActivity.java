package conquest.online;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;



public class MainActivity extends FragmentActivity {

	//Key to define for activities to access previous activity info
	public final static String EXTRA_MESSAGE = "conquest.online.MESSAGE";
	
	public static Activity me;
	
    @Override
    //This defines your first page to be started.
    protected void onCreate(Bundle savedInstanceState) {
        me = this;
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); 
    }

    /** Called when the user clicks the login button. */
    public void goToLogin(View view) {
    	Intent login = new Intent(this, LoginActivity.class);
    	startActivity(login);
    }  
    
    /** Called when the user clicks the register button.*/
    public void goToRegister(View view) { 
    	Intent register = new Intent(this, RegisterActivity.class);
    	startActivity(register);
    }  
    
}