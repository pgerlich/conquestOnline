package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	
	/**
	 * When user is registering and they select the Spy, this sets their class type to Spy
	 */
	public void makeSpy(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Spy");
		
	}
	
	/**
	 * When the user is registering and they select the Engineer, this method is called and sets their class to Engineer
	 */
	public void makeEngineer(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Engineer");
	}
	
	/**
	 * When the user is registering and they select the Soldier, this method is called and sets their class to soldier
	 */
	public void makeSoldier(View view) {
		TextView t = (TextView) findViewById(R.id.classChosen);
		t.setText("Soldier");
	}
	
	/**
	 * When you click register, it takes all of the info you typed - compared passwords,
	 * and kicks the information off to an asyncronous background task to register you
	 * and log you in.
	 * @param view
	 */
	public void registerUser(View view){
		String message;
		EditText userEdit = (EditText) findViewById(R.id.username);
		EditText passEdit = (EditText) findViewById(R.id.password);
		EditText confirmEdit = (EditText) findViewById(R.id.confirm);
		EditText emailEdit = (EditText) findViewById(R.id.email);
		TextView cChosen = (TextView) findViewById(R.id.classChosen);
		
		String classChosen = cChosen.getText().toString();
		String username = userEdit.getText().toString();
		String password = passEdit.getText().toString();
		String confirmPass = confirmEdit.getText().toString();
		String email = emailEdit.getText().toString();
		
		
		//Check passwords match
		if (password.equals(confirmPass) ) {
			//Execute background process - try and register account
			RegistrationProcess register = new RegistrationProcess(username, password, email, classChosen);
			register.execute((Void) null);
			
			//Succeeded - login
			if ( register.success.equals("1") ) {
				
				//Log the user in
				UserSession User = new UserSession(getApplicationContext());
				//FIXME: User.logIn(username);
				
				//Transition to map screen
				finish();
				goToMap();
			//Failed - print message
			} else {
				message = register.message;
			}
			
		} else {
			//Passwords did not match - Display message
			message = "Passwords did not match";
		}
		
	}
	
    /** Called when login completes succesfully - loads map Activity */
    public void goToMap() {
    	Intent map = new Intent(this, MapActivity.class);
    	startActivity(map);	
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_register,
					container, false);
			return rootView;
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RegistrationProcess extends AsyncTask<Void, Void, Boolean> {

		private final String email;
		private final String username;
		private final String password;
		private final String classChosen;
		public String message;
		public String success;

		//Instantiate task
		RegistrationProcess(String username, String password, String email, String classChosen) {
			this.username = username;
			this.password= password;
			this.email = email;
			this.classChosen= classChosen;
			
			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			
			//Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
	        postParams.add(new BasicNameValuePair("username", username));
	        postParams.add(new BasicNameValuePair("password", password));
	        postParams.add(new BasicNameValuePair("email", email));
	        postParams.add(new BasicNameValuePair("characterType", classChosen));
	        //FIXME: Include the token we receive when logging in.
		        
	        //Actual logout feature
	        JSONObject registerAttempt = JSONfunctions.getJSONfromURL("http://gerlichsoftwaresolutions.net/conquest/register.php", postParams);
	        
			//Try/Catch to attempt register message recovery.
			try {
				success = registerAttempt.getString("success");
				message = registerAttempt.getString("message");
				
				//Succeeded in logging out
				if ( success.equals("1") ) {
					return true;
				} 
				
			//Failed
			} catch (JSONException e) {
				//Print out the error
				return false;
			}
			
			return false;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			
		}

		@Override
		protected void onCancelled() {
			//on cancel
		}
	}
}
