package conquest.online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.android.gms.maps.model.LatLng;

import conquest.client.classes.LoginResponse;
import conquest.client.classes.RegistrationResponse;
import conquest.online.client.MovementClient;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
public class UserSession {
	
	//The shared preference file
	public SharedPreferences pref;
	
	public Editor edit;
	
	//The context
	public Context _context;
	
	public MovementClient mc;

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
//		try {
//			mc = new MovementClient();
//		} catch (IOException e) {
//			//
//		}
//		
//		new Thread(mc);
		
		//Need to have some way to check if we have gotten kicked, and close our screen and return to the main screen.
		//Currently when we get kicked, it just logs us out and resets our token to a new token - esentially making us unable
		//to interact with the server/database w/o relogging in. IT's effective, the user just doesn't have a way of being notified
		//They were kicked or of being actually legitimately kicked from the game.
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
		
		//Execute async task to grab stats of user
		RetrieveStats getStats = new RetrieveStats(getUser(), getToken());
		getStats.execute();
	}
	
	/**
	 * Set the follow base stats
	 * @param attack
	 * @param armor
	 * @param stealth
	 * @param speed
	 * @param tech
	 */
	public void setBaseStats(int attack, int armor, int stealth, int speed, int tech) {
		edit.putInt("attack", attack);
		edit.putInt("armor", armor);
		edit.putInt("stealth", stealth);
		edit.putInt("speed", speed);
		edit.putInt("tech", tech);
		edit.commit();
	}
	
	/**
	 * Adjusts the following stats
	 */
	public void adjStats(int health, int maxHealth, int attack) {
		edit.putInt("maxhealth", maxHealth);
		edit.putInt("health", health);
		edit.putInt("attack", attack);
		edit.commit();
	}
	
	/**
	 * Set the users max health
	 * @param health
	 */
	public void setMaxHealth(int health) {
		edit.putInt("maxHealth",  health);
		edit.commit();
	}
	
	/**
	 * Set the users current health
	 * @param health
	 */
	public void setCurHealth(int health) {
		edit.putInt("curHealth", health);
	}
	
	/**
	 * Set the users location.
	 * @param latitude
	 * @param longitude
	 */
	public void setLocation(LatLng location) {
		edit.putFloat("lat", (float) location.latitude);
		edit.putFloat("lon", (float) location.longitude);
		edit.commit();
	}
	
	/**
	 * Set the users level
	 * @param level
	 */
	public void setLevel(int level) {
		edit.putInt("level",  level);
		edit.commit();
	}
	
	/**
	 * Set the users exp
	 * @param exp
	 */
	public void setExp(int exp) {
		edit.putInt("exp", exp);
		edit.commit();
	}
	
	/**
	 * Creates and launches the async task to update all user's stats
	 */
	public void updateAllStats(){
		RetrieveStats stats = new RetrieveStats(getUser(), getToken());
		stats.execute();
	}
	
	/**
	 * Returns the location of the individual
	 * @return
	 */
	public LatLng getLocation(){
		LatLng location = new LatLng(pref.getFloat("lat", 0), pref.getFloat("lon", 0));
		return location;
	}

	/**
	 * Return the users level
	 * @return
	 */
	public int getLevel(){
		return pref.getInt("level", 0);
	}
	
	/**
	 * Return the users exp
	 * @return
	 */
	public int getExp(){
		return pref.getInt("exp", 0);
	}
	
	/**
	 * Return the users max health
	 * @return
	 */
	public int getMaxHealth(){
		return pref.getInt("maxHealth", 0);
	}
	
	/**
	 * Return the users current health
	 * @return
	 */
	public int getCurHealth(){
		return pref.getInt("curHealth", 0);
	}
	
	/**
	 * Returns the characters attack
	 * @return
	 */
	public int getAttack(){
		return pref.getInt("attack", 0);
	}
	
	/**
	 * Returns the users Money
	 */
	public int getMoney(){
		return pref.getInt("money", 0);
	}
	
	/**
	 * Return the users armor
	 */
	public int getArmor(){
		return pref.getInt("armor", 0);
	}
	
	/**
	 * Return the users stealth
	 * @return
	 */
	public int getStealth(){
		return pref.getInt("stealth", 0);
	}
	
	/**
	 * Return the users speed
	 * @return
	 */
	public int getSpeed(){
		return pref.getInt("speed", 0);
	}
	
	/**
	 * Return the users tech
	 * @return
	 */
	public int getTech(){
		return pref.getInt("tech", 0);
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

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RetrieveStats extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		public String message;
		
		public int level;
		public int exp;
		public int maxHealth;
		public int curHealth;
		
		public int attack;
		public int armor;
		public int speed;
		public int tech;
		public int stealth;
		
		public LatLng location;
		
		RetrieveStats(String username, String token) {
			this.username = username;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
//			Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(2);
	        postParams.add(new BasicNameValuePair("user", username));
	        postParams.add(new BasicNameValuePair("token", token));

			JSONObject stats = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/character/requestStats.php", postParams);					
			
			//Try and check if it succeeded
			try {
				int success = stats.getInt("success");
				
				//Return true on success
				if ( success == 1 ) {
					
					level = stats.getInt("level");
					exp = stats.getInt("exp");
					
					maxHealth = stats.getInt("maxHealth");
					curHealth = stats.getInt("curHealth");
					attack = stats.getInt("attack");
					armor = stats.getInt("armor");
					stealth = stats.getInt("stealth");
					speed = stats.getInt("speed");
					tech = stats.getInt("tech");
					
					
					location = new LatLng(stats.getDouble("lat"), stats.getDouble("lon"));
					
					message = "success";
					return true;
					
				//Set error message and return false.
				} else {
					message = stats.getString("message");
					return false;
				}
			
			//Off chance that some weird shit happens
			} catch (JSONException e) {
				//Something went wrong - typically JSON value doesn't exist.
				message = "An error occured. Please try again later.";
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if ( success ) {
				setLevel(level);
				setExp(level);
				setBaseStats(attack, armor, stealth, speed, tech);
				setCurHealth(curHealth);
				setMaxHealth(maxHealth);
				setLocation(location);
			} 
		}

		@Override
		protected void onCancelled() {
			//Action on cancle
		}
	}
}