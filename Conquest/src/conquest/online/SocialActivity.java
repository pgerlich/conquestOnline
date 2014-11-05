package conquest.online;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
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
	private ArrayList<String> friends;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_social);
		
		toast("happened");
		
		//Create user session
		user = new UserSession(getApplicationContext());
		
		toast(user.getUser());
		
		//Retrieve friends w/ asynch task
		RetrieveFriends grab = new RetrieveFriends(user.getUser(), user.getToken());
		grab.execute((Void) null);
		friends = grab.friends;
		
		//Now load the friends up on the display
		listFriends();
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
		/*
		 * 
		 */
		LinearLayout friend = (LinearLayout) findViewById(R.id.friend_list);
		EditText usr = (EditText) findViewById(R.id.newFriend);
		String name = usr.getText().toString();
		
		
		
		TextView f = new TextView(this);
        f.setText("test");
        f.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));

        ((LinearLayout) friend).addView(f);
        
        toast("added: " + name);
	}
	
	/** 
	 * Display friends by iterating through the arraylist of friends
	 */
	public void listFriends() {
		LinearLayout showFriends = (LinearLayout) findViewById(R.id.friend_list);
		
		for (String person : friends) {
			TextView f = new TextView(this);
			f.setText(person);
			f.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			((LinearLayout) showFriends).addView(f);
		}
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class RetrieveFriends extends AsyncTask<Void, Void, Boolean> {

		private final String username;
		private final String token;
		public ArrayList<String> friends;
		public String message;

		RetrieveFriends(String username, String token) {
			this.username = username;
			this.token = token;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
//			Query the login script with their entered username/password
	        List<NameValuePair> postParams = new ArrayList<NameValuePair>(3);
	        postParams.add(new BasicNameValuePair("username", username));
	        postParams.add(new BasicNameValuePair("token", token));
			
			JSONObject requestFriends = JSONfunctions.getJSONfromURL("http://proj-309-R12.cs.iastate.edu/functions/social/requestFriends.php", postParams);					
			
			//Try and check if it succeeded
			try {
				String success = requestFriends.getString("success");
				
				//Return true on success
				if ( success.equals("1") ) {
					
					for (int i = 0; i < requestFriends.length() - 1; i++ ) {
						friends.add(requestFriends.getString("" + i));
					}
					
					message = "success";
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
			//Not really sure what there is to do here
		}

		@Override
		protected void onCancelled() {
			//Nothing
		}
	}

}
