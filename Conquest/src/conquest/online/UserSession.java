package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
public class UserSession {
	
	//The shared preference file
	SharedPreferences pref;
	
	Editor edit;
	
	//The context
	Context _context;

	//The name of the shared preference storing the data
	private static final String prefName = "userState";
	
	//This user is logged in
	public final String loggedIn = "loggedIn";
	
	//Username
	private static final String user = "username";
	
	/**
	 * Createa a new user session with the given context. Suppressed a warning that pref.edit
	 * isn't actually commiting any changes.
	 * @param context
	 */
	@SuppressLint("CommitPrefEdits") public  UserSession(Context context) {
		_context = context;
		pref = context.getSharedPreferences(prefName, 0); // 0 - for private mode
		edit = pref.edit();
	}
	
	/**
	 * Log the user in and store session variables
	 */
	public void logIn(String username){
		//Stores KEY - VALUE
		edit.putBoolean(loggedIn, true); // Stores that we're logged in
		edit.putString(user, username); // Stores the username
		edit.commit(); // commit changes
	}
	
	/**
	 * Log the user in and store session variables
	 */
	public String returnUser(){
		return pref.getString(user, null);
	}
	
	
	/**
	 * Logout the user
	 */
	public String logOut() {
		BackgroundProcess mAuthTask = null;
		
		// Show a progress spinner, and kick off a background task to
		// perform the user login attempt.
		mAuthTask = new BackgroundProcess(pref.getString(user, null));
		mAuthTask.execute((Void) null);
		
		edit.putBoolean("loggedIn", false); // Stores that we're logged in
		edit.putString("username", null); // Stores the username
		edit.commit(); // commit changes
		
		return mAuthTask.message;
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class BackgroundProcess extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		public String message;
		public String success;

		//Instantiate task
		BackgroundProcess(String username) {
			this.username = username;
			message = "";
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			
			//Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
	        postParams.add(new BasicNameValuePair("username", username));
	        //FIXME: Include the token we receive when logging in.
		        
	        //Actual logout feature
	        JSONObject logoutAttempt = JSONfunctions.getJSONfromURL("http://gerlichsoftwaresolutions.net/conquest/logout.php", postParams);
	        
			//Try/Catch to attempt logout message recovery.
			try {
				success = logoutAttempt.getString("success");
				message = logoutAttempt.getString("message");
				
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
