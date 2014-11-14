package conquest.online;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class CharacterActivity extends Activity {
	private UserSession user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		user = new UserSession(getApplicationContext());
		
		loadCharacter();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.social, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_logout) {
			user.logout();
			goToMain();
			return true;
		} else if (id == R.id.action_settings ) {
			goToSettings();
			return true;
		}
		return super.onOptionsItemSelected(item);
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
	 * Go to the settings
	 */
	public void goToSettings(){
    	Intent settings = new Intent(this, SettingsActivity.class);
    	startActivity(settings);
	}
	
	/**
	 * perhaps load the different character information here, stats and such
	 * pass an objec maybe and read it all from there
	 * @param user
	 */
	public void loadCharacter() {
		//Set the users name
		TextView thisUser = new TextView(this); 
		thisUser = (TextView)findViewById(R.id.textView9); 
		thisUser.setText(user.getUser());
		
		toast("Health: "+user.getHealth());
		toast("Attack: "+user.getAttack());
		toast("Armor: "+user.getArmor());
		toast("Speed: "+user.getSpeed());
		toast("Stealth: "+user.getStealth());
		toast("Tech: "+user.getTech());
		
		//Load stats from user session object
	}
	
	/**
	 * Toasts the message as an alert box
	 * @param message
	 */
	public void toast(String message) {
		//Toast an error message if it exists. Will close and leave page if it doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
}
