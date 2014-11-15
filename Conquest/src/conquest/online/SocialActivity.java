package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SocialActivity extends ActionBarActivity {
	
	private UserSession user;
	public ArrayList<String> friends;
	public ArrayList<String> guilds;
	public ArrayList<String> enemies;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social);
		user = new UserSession(getApplicationContext());
		
		//Create user session and friends list
		friends = new ArrayList<String>(10);
		guilds = new ArrayList<String>(10);
		enemies = new ArrayList<String>(10);
		
		//Retrieve friends
		RetrievePeople grabFriends = new RetrievePeople(user.getUser(), user.getToken(), "friends");
		grabFriends.execute();
		
		//Retrieve guild members
		RetrievePeople grabGuild = new RetrievePeople(user.getUser(), user.getToken(), "guild");
		grabGuild.execute();
		
		//Retrieve enemies
		RetrievePeople grabEnemies = new RetrievePeople(user.getUser(), user.getToken(), "enemies");
		grabEnemies.execute();
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
	 * Go to the settings
	 */
	public void goToSettings(){
    	Intent settings = new Intent(this, SettingsActivity.class);
    	startActivity(settings);
	}
	
	
	public void toast(String message) {
		//Toast an error message if it exists. Will close and leave page if it doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	/**
	 * called when the user hits the add friend button
	 */
	public void addFriend(View view) {
		EditText usr = (EditText) findViewById(R.id.newFriend);
		String name = usr.getText().toString();
		
		
		addPlayer add = new addPlayer(user.getUser(), name, user.getToken());
		add.execute();
	}
	
	/** 
	 * Display friends by iterating through the arraylist of friends
	 */
	public void listPeople(String type) {
		ArrayList<String> persons = null;
		LinearLayout showPersons = null;
		
		//So this is a generic method called for friends, enemies, and guild members. This just specifies it
		if ( type.equals("friends") ) {
			persons = friends;
			showPersons = (LinearLayout) findViewById(R.id.friend_list);
		} else if ( type.equals("guild") ) {
			persons = guilds;
			showPersons = (LinearLayout) findViewById(R.id.guild_list);
		} else if ( type.equals("enemies") ) {
			persons = enemies;
			showPersons = (LinearLayout) findViewById(R.id.enemy_list);
		}
		
		((LinearLayout) showPersons).removeAllViews();
				
		for (String person : persons) {
			TextView f = new TextView(this);
			f.setText(person);
			f.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			((LinearLayout) showPersons).addView(f);
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RetrievePeople extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		private final String type;
		public String message;
		
		RetrievePeople(String username, String token, String type) {
			this.username = username;
			this.token = token;
			this.type = type;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
//			Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
	        postParams.add(new BasicNameValuePair("user", username));
	        postParams.add(new BasicNameValuePair("token", token));
	        postParams.add(new BasicNameValuePair("type", type));

			JSONObject requestPersons = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/social/requestPersons.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestPersons.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					int curGuildMember = 0;
					
					//Ignoring Message/Sucess - add the friends/enemies
					for (int i = 0; i < requestPersons.length() - 2; i++ ) {
						
						if ( type.equals("friends") ) {
							String person = requestPersons.getString("" + i);;
							friends.add(person);
						} else if (type.equals("enemies") ) {
							String person = requestPersons.getString("" + i);
							enemies.add(person);
						} else if (type.equals("guild") ) {
							if ( curGuildMember >= requestPersons.getInt("numMembers")) {
								break;
							}
							String person = requestPersons.getString("name" + curGuildMember);
							person += " - " + requestPersons.getString("rank" + curGuildMember);
							guilds.add(person);
							curGuildMember++;
						}
						
					}
					
					
					
					message = "success";
					return true;
					
				//Set error message and return false.
				} else {
					message = requestPersons.getString("message");
					return false;
				}
			
			//Off chance that some weird shit happens
			} catch (JSONException e) {
				//Something went wrong - typically JSON value doesn't exist (success).
				message = "An error occured. Please try again later.";
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if ( success ) {
				listPeople(type);
			} 
		}

		@Override
		protected void onCancelled() {
			//
		}
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
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class addPlayer extends AsyncTask<Void, Void, Boolean> {

		private final String myUser;
		private final String otherUser;
		private final String token;
		public String message;

		addPlayer(String myUser, String otherUser, String token) {
			this.myUser = myUser;
			this.otherUser = otherUser;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
//			Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
	        postParams.add(new BasicNameValuePair("thisUser", myUser));
	        postParams.add(new BasicNameValuePair("otherUser", otherUser));
	        postParams.add(new BasicNameValuePair("token", token));
	        
			JSONObject requestFriends = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/social/addPlayer.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestFriends.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					friends.add(otherUser);
					message = otherUser + " added successfully";
					return true;
					
				//Set error message and return false.
				} else {
					message = requestFriends.getString("message");
					return false;
				}
			
			//Off chance that some weird shit happens
			} catch (JSONException e) {
				//Something went wrong - typically JSON value doesn't exist (success).
				message = "An error occured. Please try again later.";
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if ( success ) {
				listPeople("friends");
				toast(message);
			} else {
				toast(message);
			}
		}

		@Override
		protected void onCancelled() {
			//Nothing
		}
	}

}
