package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MapActivity extends ActionBarActivity {
	
	private UserSession user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		user = new UserSession(getApplicationContext());
		
		TextView textView = (TextView) findViewById(R.id.username);
		textView.setText("Logged in as: " + user.returnUser());		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Log the user out
	public void logOut(View view) {
		//Gotta fix this.
		String message = user.logOut();
		
		
		TextView textView = (TextView) findViewById(R.id.username);
		textView.setText(message);
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mUsername;
		private final String mPassword;

		UserLoginTask(String email, String password) {
			mUsername = email;
			mPassword = password;
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
}
