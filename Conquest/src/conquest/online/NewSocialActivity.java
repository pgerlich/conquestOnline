package conquest.online;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewSocialActivity extends Activity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	private static UserSession user;
	public static ArrayList<String> friends;
	public static ArrayList<String> guilds;
	public static ArrayList<String> enemies;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_social);
		user = new UserSession(getApplicationContext());
		
		//Create user session and friends list
		friends = new ArrayList<String>(10);
		guilds = new ArrayList<String>(10);
		enemies = new ArrayList<String>(10);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
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
		getMenuInflater().inflate(R.menu.new_social, menu);
		return true;
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
	public void addPlayer(View view, String type) {
		EditText usr = (EditText) findViewById(R.id.newPerson);
		String name = usr.getText().toString();
		
		addPlayer add = new addPlayer(user.getUser(), name, user.getToken(), type);
		add.execute();
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

			} 
		}

		@Override
		protected void onCancelled() {
			//
		}
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class addPlayer extends AsyncTask<Void, Void, Boolean> {

		private final String myUser;
		private final String otherUser;
		private final String token;
		@SuppressWarnings("unused")
		private final String type;
		public String message;

		addPlayer(String myUser, String otherUser, String token, String type) {
			this.myUser = myUser;
			this.otherUser = otherUser;
			this.token = token;
			this.type = type;
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

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";
		
		public int tabNumber;
		public String Title;
		
		//Some UI references
		public View socialTitle;
		public View socialDisplay;
		

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment(sectionNumber);
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment(int tabNumber) {
			this.tabNumber = tabNumber;
		}
		
		public void setTitle() {
			
			if ( tabNumber == 1 ) {
				Title = "Friends";
				((TextView) socialTitle).setTextColor(Color.GREEN);
			} else if ( tabNumber == 2 ) {
				Title = "Guild Members";
				((TextView) socialTitle).setTextColor(Color.BLUE);
			} else if ( tabNumber == 3 ) {
				Title = "Enemies";
				((TextView) socialTitle).setTextColor(Color.RED);
			}
			
			((TextView) socialTitle).setText(Title);
			
		}
		
		public void setPeople(){
			if ( tabNumber == 1 ) {
				listPeople("friends");
			} else if ( tabNumber == 2 ) {
				listPeople("guild");
			} else if ( tabNumber == 3 ) {
				listPeople("enemies");
			}
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_new_social, container,
					false);
			
	        //Set the title screen and load people
			socialTitle = rootView.findViewById(R.id.socialTitle);
			socialDisplay = rootView.findViewById(R.id.socialDisplay);
			
            setTitle();
            setPeople();
            
            //Show the addFriend part or whatever?
			return rootView;
		}
		
		/** 
		 * Display friends by iterating through the arraylist of friends
		 */
		@SuppressWarnings("deprecation")
		public void listPeople(String type) {
			ArrayList<String> persons = null;
			
			if ( socialDisplay != null ) {
			
				//So this is a generic method called for friends, enemies, and guild members. This just specifies it
				if ( type.equals("friends") ) {
					persons = friends;
				} else if ( type.equals("guild") ) {
					persons = guilds;
				} else if ( type.equals("enemies") ) {
					persons = enemies;
				}
				
				((LinearLayout) socialDisplay).removeAllViews();
	
				for (String person : persons) {
					TextView f = new TextView(getActivity());
					f.setText(person);
					f.setX(150);
					f.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
					((LinearLayout) socialDisplay).addView(f);
				}
			}
		}
		
		
		
		public void toast(String message) {
			//Toast an error message if it exists. Will close and leave page if it doesn't
			Context context = getActivity();
			CharSequence text = message;
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}

	}
}
