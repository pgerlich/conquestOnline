package conquest.online;

import conquest.online.client.MovementClient;


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
	public void logIn(String username, String token){
		//Stores KEY - VALUE
		edit.putBoolean("loggedIn", true); // Stores that we're logged in
		edit.putString("user", username); // Stores the username
		edit.putString("token", token);
		edit.commit(); // commit changes
	}
	
	/**
	 * Returns the user name that is currently logged in
	 * @return
	 */
	public String getUser(){
		return pref.getString("user", null);
	}
	
	/**
	 * Returns if the user is logged in or not
	 * @return
	 */
	public boolean isLoggedIn(){
		return pref.getBoolean("loggedIn", false);
	}
	
	/**
	 * Returns the user's token
	 * @return
	 */
	public String getToken(){
		return pref.getString("token", null);
	}
	
	
	/**
	 * Logout the user
	 */
	public void logout() {

		UserLoginTask logout = new UserLoginTask(getUser(), getToken());
		logout.execute((Void) null);
		
		edit.putBoolean("loggedIn", false); // Logged out
		edit.putString("username", null); // null user
		edit.putString("token", null); // null token
		edit.commit(); // commit changes

	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;

		UserLoginTask(String username, String token) {
			this.username = username;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			try { 
				MovementClient mc = new MovementClient();
				
				//Have to start this on a new thread so it stays open and listends for responses
				new Thread(mc).start();
				
				//Attempt to log the user out
				mc.logout(username, token);
				
				mc.close();
			}
			catch (Exception e) {
				//Idk why this would happen - only happens if it couldn't connect to server.
			}
			
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			//Nothing needs to go here
		}

		@Override
		protected void onCancelled() {
			//Nothing needs to go here
		}
	}

	
}