package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.TextView;

public class UserSession {
	
	//The shared preference file
	SharedPreferences pref;
	
	Editor edit;
	
	//The context
	Context _context;

	//The name of the shared preference storing the data
	private static final String prefName = "userState";
	
	//This user is logged in
	private static final String loggedIn = "loggedIn";
	
	//Username
	private static final String user = "username";
	
	/**
	 * Createa a new user session with the given context
	 * @param context
	 */
	public  UserSession(Context context) {
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
		String message = "";
	
		//Query the login script with their entered username/password
        List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
        postParams.add(new BasicNameValuePair("username", "pgerlich"));
        postParams.add(new BasicNameValuePair("bull", "shit"));
	        
        //Actual logout feature
        JSONObject logoutAttempt = JSONfunctions.getJSONfromURL("http://gerlichsoftwaresolutions.net/conquest/logout.php", postParams);
        
		//Try/Catch to attempt logout message recovery.
		try {
			message = logoutAttempt.getString("message");
		} catch (JSONException e) {
			//Failed for some reason..
		}
		
		edit.putBoolean("loggedIn", false); // Stores that we're logged in
		edit.putString("username", null); // Stores the username
		edit.commit(); // commit changes
		
		return message;
	}
	
}
