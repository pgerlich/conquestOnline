package conquest.online;

//import conquest.online.R;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CharacterActivity extends Activity {
	private UserSession user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character);
		user = new UserSession(getApplicationContext());

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
		
		//Load stats from user session object
	}
}
