package conquest.online;

import java.io.IOException;
import java.util.ArrayList;

import conquest.client.classes.AbstractStructure;
import conquest.online.client.MovementClient;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PersonalPropertyActivity extends Activity {
	
	//The double array of grid items
	public ImageView[][] grid;
	
	//Arraylist of structures
	public ArrayList<AbstractStructure> structs;
	
	public int width;
	public int height;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_property);
		UserSession user = new UserSession(getApplicationContext());
		
		width = height = 7;
		
		//Do we move this into the onPostExecute?
		populateGridsAndAddListeners();
		
		structs = new ArrayList<AbstractStructure>(10);
		
		//Grab structs w/ ASYNC task
		StructsRequest SR = new StructsRequest(user.getUser(), user.getToken(), -1);
		SR.execute();
	}
	
	/*
	 * Populates the grid array and adds listeners
	 */
	public void populateGridsAndAddListeners(){
		grid = new ImageView[width][height];
		
		grid[0][0] = (ImageView) findViewById(R.id.imageView00);
		grid[1][0] = (ImageView) findViewById(R.id.imageView10);
		grid[2][0] = (ImageView) findViewById(R.id.imageView20);
		grid[3][0] = (ImageView) findViewById(R.id.imageView30);
		grid[4][0] = (ImageView) findViewById(R.id.imageView40);
		grid[5][0] = (ImageView) findViewById(R.id.imageView50);
		grid[6][0] = (ImageView) findViewById(R.id.imageView60);
		grid[0][1] = (ImageView) findViewById(R.id.imageView01);
		grid[1][1] = (ImageView) findViewById(R.id.imageView11);
		grid[2][1] = (ImageView) findViewById(R.id.imageView21);
		grid[3][1] = (ImageView) findViewById(R.id.imageView31);
		grid[4][1] = (ImageView) findViewById(R.id.imageView41);
		grid[5][1] = (ImageView) findViewById(R.id.imageView51);
		grid[6][1] = (ImageView) findViewById(R.id.imageView61);
		grid[0][2] = (ImageView) findViewById(R.id.imageView02);
		grid[1][2] = (ImageView) findViewById(R.id.imageView12);
		grid[2][2] = (ImageView) findViewById(R.id.imageView22);
		grid[3][2] = (ImageView) findViewById(R.id.imageView32);
		grid[4][2] = (ImageView) findViewById(R.id.imageView42);
		grid[5][2] = (ImageView) findViewById(R.id.imageView52);
		grid[6][2] = (ImageView) findViewById(R.id.imageView62);
		grid[0][3] = (ImageView) findViewById(R.id.imageView03);
		grid[1][3] = (ImageView) findViewById(R.id.imageView13);
		grid[2][3] = (ImageView) findViewById(R.id.imageView23);
		grid[3][3] = (ImageView) findViewById(R.id.imageView33);
		grid[4][3] = (ImageView) findViewById(R.id.imageView43);
		grid[5][3] = (ImageView) findViewById(R.id.imageView53);
		grid[6][3] = (ImageView) findViewById(R.id.imageView63);
		grid[0][4] = (ImageView) findViewById(R.id.imageView04);
		grid[1][4] = (ImageView) findViewById(R.id.imageView14);
		grid[2][4] = (ImageView) findViewById(R.id.imageView24);
		grid[3][4] = (ImageView) findViewById(R.id.imageView34);
		grid[4][4] = (ImageView) findViewById(R.id.imageView44);
		grid[5][4] = (ImageView) findViewById(R.id.imageView54);
		grid[6][4] = (ImageView) findViewById(R.id.imageView64);
		grid[0][5] = (ImageView) findViewById(R.id.imageView05);
		grid[1][5] = (ImageView) findViewById(R.id.imageView15);
		grid[2][5] = (ImageView) findViewById(R.id.imageView25);
		grid[3][5] = (ImageView) findViewById(R.id.imageView35);
		grid[4][5] = (ImageView) findViewById(R.id.imageView45);
		grid[5][5] = (ImageView) findViewById(R.id.imageView55);
		grid[6][5] = (ImageView) findViewById(R.id.imageView65);
		grid[0][6] = (ImageView) findViewById(R.id.imageView05);
		grid[1][6] = (ImageView) findViewById(R.id.imageView16);
		grid[2][6] = (ImageView) findViewById(R.id.imageView26);
		grid[3][6] = (ImageView) findViewById(R.id.imageView36);
		grid[4][6] = (ImageView) findViewById(R.id.imageView46);
		grid[5][6] = (ImageView) findViewById(R.id.imageView56);
		grid[6][6] = (ImageView) findViewById(R.id.imageView66);

		for(int i = 0; i < height; i++ ) {
			for (int j = 0; j < width; j++) {
				addListenerToItem(grid[j][i], "(" + j + ", " + i + ")");
			}
		}
		
	}
	
	/**
	 * Adds a listener to the given item
	 * @param image
	 */
	public void addListenerToItem(ImageView image, final String location) {
		
		image.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				toast(location);
				return false;
			}
		});
		
	}
	
	/**
	 * Go to the settings
	 */
	public void goToSettings(){
    	Intent settings = new Intent(this, SettingsActivity.class);
    	startActivity(settings);
	}
		
	
	/**
	 * Go to the main menu and close the current activity
	 */
	public void goToMap(View view){
		finish();
    	Intent main = new Intent(this, MapActivity.class);
    	startActivity(main);
	}
	
	/**
	 * When user taps social button, this function is called and takes user to Social Screen
	 */
	public void toSocial(View view) {
		Intent soc = new Intent(this, NewSocialActivity.class);
		startActivity(soc);
	}
	
	public void shop(View view) {
		Intent soc = new Intent(this, PersonalPropertyActivity.class);
		startActivity(soc);
	}
	/**
	 * When user taps settings button this functino is called and takes user to the social screen
	 */
	public void settings(View view) {
		goToSettings();
	}
	
	/**
	 * When user taps character button the user is taken to the character screen
	 */
	public void myCharacter(View view) {
		Intent character = new Intent(this, CharacterActivity.class);
		startActivity(character);
	}
	
	
	/**
	 * Displays the message on the screen
	 * @param message
	 */
	public void toast(String message) {
		//Toast an error message if it exists. Will close and leave page if it doesn't
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class StructsRequest extends AsyncTask<Void, Void, Boolean> {

		private final String user;
		private final String token;
		private final int propertyID;

		public String message;
		public ArrayList<AbstractStructure> structs;
		
		StructsRequest(String user, String token, int propID) {
			this.user = user;
			this.token = token;
			this.propertyID = propID;
			structs = new ArrayList<AbstractStructure>(10);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			try {
				//Will throw I/O exception if it fails to connect
				MovementClient mc = new MovementClient();
				
				//Have to start this on a new thread so it stays open and listends for responses
				new Thread(mc).start();
				
				mc.requestStructs(user, token, propertyID);
				
				//Wait for a response from the server
				while ( mc.structsResponse == null ) {
					
				}

				//If we succeeded!
				if ( mc.structsResponse.success ) {
					structs = mc.structsResponse.structs;
					mc.close();
					return true;
				} else {
					message = mc.loginResponse.message;
					mc.close();
					return false;
				}
			} catch (IOException e) {
				message = "Couldn't connect to server.";
				return false;
			}
			
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if ( success ) {
				//Now populate your property
			} else {
				//nah
			}
		}
	}
}
