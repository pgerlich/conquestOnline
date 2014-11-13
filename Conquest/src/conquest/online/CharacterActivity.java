package conquest.online;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;

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
		
		toast(""+user.getHealth());
		toast(""+user.getAttack());
		toast(""+user.getArmor());
		toast(""+user.getSpeed());
		toast(""+user.getStealth());
		toast(""+user.getTech());
		
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
