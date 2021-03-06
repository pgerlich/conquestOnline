package conquest.online;

import java.io.IOException;
import java.util.ArrayList;

import conquest.client.classes.AbstractStructure;
import conquest.client.classes.PropStructsResponse;
import conquest.online.client.MovementClient;
import conquest.online.gameAssets.Structures.GridItem;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PersonalPropertyActivity extends Activity {
	
	//The double array of grid items
	public GridItem[][] grid;
	//
	//Arraylist of structures
	public ArrayList<AbstractStructure> structs = new ArrayList<AbstractStructure>(10);
	public ArrayList<AbstractStructure> chestItems = new ArrayList<AbstractStructure>(10);
	
	public CharSequence[] structures;
	
	//Dimensions/info on property
	public int width;
	public int height;
	public int propID;
	
	//Structures and chest items
	public StructsRequest SR;
	public StructsRequest grabChestItems;
	
	//progress view
	public View progressView;
	
	//user session info
	public UserSession user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_property);
		user = new UserSession(getApplicationContext());
		
		//set width and height manually for now
		width = height = 7;
				
		//Populate grid w/ image views
 		populateGrids();
 		
 		//Animate progress bar
 		progressView.animate();
		
		//Now add the listeners
		addListeners();
		
		//Grab the chest items
		grabChestItems = new StructsRequest(user.getUser(), user.getToken(), "chest", -1);
		grabChestItems.execute();
		
		//Grab structs on property
		SR = new StructsRequest(user.getUser(), user.getToken(), "property", -1);
		SR.execute();
	}
	
	/**
	 * Refresh the property screen with items
	 */
	public void refreshProperty(){
		
		//Hide progress view
		progressView.setVisibility(View.INVISIBLE);
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				grid[j][i].image.setVisibility(ImageView.INVISIBLE);
			}
		}
		
		if ( structs != null ) {
			//Change image, set visible, and set struct
			for(int i = 0; i < structs.size(); i++){
				grid[structs.get(i).x][structs.get(i).y].struct = structs.get(i);
				grid[structs.get(i).x][structs.get(i).y].image.setImageResource(R.drawable.wall);
				grid[structs.get(i).x][structs.get(i).y].image.setVisibility(ImageView.VISIBLE);
			}
		}
		
		//Set blanks visible
		for(int i = 0; i < height; i++ ) {
			for (int j = 0; j < width; j++) {
				//If it's not visible (I.e, it's not a structure)
				if ( grid[j][i].image.getVisibility() != ImageView.VISIBLE ) {
					grid[j][i].image.setVisibility(ImageView.VISIBLE);
				}
			}
		}		
	}
	
	/**
	 * Add the listeners initially
	 */
	public void addListeners(){
		
		//Set blanks visible and add listeners
		for(int i = 0; i < height; i++ ) {
			for (int j = 0; j < width; j++) {
				grid[i][j].x = j;
				grid[i][j].y = i;
				addListenerToItem(grid[i][j]);
			}
		}
	}
	
	/*
	 * Populates the grid array and adds listeners
	 */
	public void populateGrids(){
		grid = new GridItem[width][height];
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				grid[j][i] = new GridItem(null,null);
			}
		}
		
		progressView = findViewById(R.id.propertyProgressBar);
		
		grid[0][0].image = (ImageView) findViewById(R.id.imageView00);
		grid[1][0].image = (ImageView) findViewById(R.id.imageView10);
		grid[2][0].image = (ImageView) findViewById(R.id.imageView20);
		grid[3][0].image = (ImageView) findViewById(R.id.imageView30);
		grid[4][0].image = (ImageView) findViewById(R.id.imageView40);
		grid[5][0].image = (ImageView) findViewById(R.id.imageView50);
		grid[6][0].image = (ImageView) findViewById(R.id.imageView60);
		grid[0][1].image = (ImageView) findViewById(R.id.imageView01);
		grid[1][1].image = (ImageView) findViewById(R.id.imageView11);
		grid[2][1].image = (ImageView) findViewById(R.id.imageView21);
		grid[3][1].image = (ImageView) findViewById(R.id.imageView31);
		grid[4][1].image = (ImageView) findViewById(R.id.imageView41);
		grid[5][1].image = (ImageView) findViewById(R.id.imageView51);
		grid[6][1].image = (ImageView) findViewById(R.id.imageView61);
		grid[0][2].image = (ImageView) findViewById(R.id.imageView02);
		grid[1][2].image = (ImageView) findViewById(R.id.imageView12);
		grid[2][2].image = (ImageView) findViewById(R.id.imageView22);
		grid[3][2].image = (ImageView) findViewById(R.id.imageView32);
		grid[4][2].image = (ImageView) findViewById(R.id.imageView42);
		grid[5][2].image = (ImageView) findViewById(R.id.imageView52);
		grid[6][2].image = (ImageView) findViewById(R.id.imageView62);
		grid[0][3].image = (ImageView) findViewById(R.id.imageView03);
		grid[1][3].image = (ImageView) findViewById(R.id.imageView13);
		grid[2][3].image = (ImageView) findViewById(R.id.imageView23);
		grid[3][3].image = (ImageView) findViewById(R.id.imageView33);
		grid[4][3].image = (ImageView) findViewById(R.id.imageView43);
		grid[5][3].image = (ImageView) findViewById(R.id.imageView53);
		grid[6][3].image = (ImageView) findViewById(R.id.imageView63);
		grid[0][4].image = (ImageView) findViewById(R.id.imageView04);
		grid[1][4].image = (ImageView) findViewById(R.id.imageView14);
		grid[2][4].image = (ImageView) findViewById(R.id.imageView24);
		grid[3][4].image = (ImageView) findViewById(R.id.imageView34);
		grid[4][4].image = (ImageView) findViewById(R.id.imageView44);
		grid[5][4].image = (ImageView) findViewById(R.id.imageView54);
		grid[6][4].image = (ImageView) findViewById(R.id.imageView64);
		grid[0][5].image = (ImageView) findViewById(R.id.imageView05);
		grid[1][5].image = (ImageView) findViewById(R.id.imageView15);
		grid[2][5].image = (ImageView) findViewById(R.id.imageView25);
		grid[3][5].image = (ImageView) findViewById(R.id.imageView35);
		grid[4][5].image = (ImageView) findViewById(R.id.imageView45);
		grid[5][5].image = (ImageView) findViewById(R.id.imageView55);
		grid[6][5].image = (ImageView) findViewById(R.id.imageView65);
		grid[0][6].image = (ImageView) findViewById(R.id.imageView06);
		grid[1][6].image = (ImageView) findViewById(R.id.imageView16);
		grid[2][6].image = (ImageView) findViewById(R.id.imageView26);
		grid[3][6].image = (ImageView) findViewById(R.id.imageView36);
		grid[4][6].image = (ImageView) findViewById(R.id.imageView46);
		grid[5][6].image = (ImageView) findViewById(R.id.imageView56);
		grid[6][6].image = (ImageView) findViewById(R.id.imageView66);
		
	}
	
	/**
	 * Adds a listener to the given item
	 * @param image
	 */
	public void addListenerToItem(final GridItem item) {
		
		item.image.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				//Set different listener/dialog for structure
				if ( item.struct == null ) {
					GridItemSelectBlank dialog = new GridItemSelectBlank(item);
				    dialog.show(getFragmentManager(), null);
				//Basic dialog for placing an item there
				} else {
					GridItemSelectOccupied dialog = new GridItemSelectOccupied();
					dialog.show(getFragmentManager(), null);
				}
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
		private final String location;
		private final int propertyID;

		public String message;
		public ArrayList<PropStructsResponse> structures = new ArrayList<PropStructsResponse>(10);
		
		StructsRequest(String user, String token, String location, int propID) {
			this.user = user;
			this.location = location;
			this.token = token;
			this.propertyID = propID;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected Boolean doInBackground(Void... params) {
			
			try {
				//Will throw I/O exception if it fails to connect
				MovementClient mc = new MovementClient();
				
				//Have to start this on a new thread so it stays open and listends for responses
				new Thread(mc).start();
				
				mc.requestStructs(user, token, propertyID, location);
				
				//Wait for a response from the server
				while ( mc.structsResponse.size() == 0 ) {
					try {
					    Thread.sleep(500); 
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				} 
				
				if ( location.equals("property") ) {
					propID = Integer.valueOf(mc.structsResponse.get(0).propertyID);
				}

				//If we succeeded!
				if ( mc.structsResponse.get(0).success ) {
					this.message = mc.structsResponse.get(0).message;
					
					structures = (ArrayList<PropStructsResponse>) mc.structsResponse.clone();
					
					mc.close();
					return true;
				} else {
					message = mc.structsResponse.get(0).message;
					
					if ( mc.structsResponse.get(0).message.equals("none") ){
						mc.close();
						return true;
					}
					
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
				if ( location.equals("property") ) {
					//Copy structs over from response
					structs = new ArrayList<AbstractStructure>(10);
					for(int i = 0; i < SR.structures.size(); i++) {
						structs.add(SR.structures.get(i).struct);
					}
					refreshProperty();
				} else if ( location.equals("chest") ) {
					//Copy structs over from response
					chestItems = new ArrayList<AbstractStructure>(10);
					for(int i = 0; i < structures.size();i ++) {
						chestItems.add(structures.get(i).struct);
					}
				}
				
			}
		}
	}
	
	
	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class PlaceStructRequest extends AsyncTask<Void, Void, Boolean> {

		private final String user;
		private final String token;
		private final int propertyID;
		private final AbstractStructure struct;

		public String message;
		
		PlaceStructRequest(String user, String token, int propertyID, AbstractStructure struct) {
			this.user = user;
			this.token = token;
			this.propertyID = propertyID;
			this.struct = struct;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			try {
				//Will throw I/O exception if it fails to connect
				MovementClient mc = new MovementClient();
				
				//Have to start this on a new thread so it stays open and listends for responses
				new Thread(mc).start();
				
				mc.placeOnProperty(user, token, propertyID, struct);
				
				//Wait for a response from the server
				while ( mc.placeResponse == null ) {
					try {
					    Thread.sleep(500); 
					} catch(InterruptedException ex) {
					    Thread.currentThread().interrupt();
					}
				} 

				//If we succeeded!
				if ( mc.placeResponse.success ) {
					message = mc.placeResponse.message;
					
					mc.close();
					return true;
				} else {
					message = mc.placeResponse.message;
					
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
				toast("yes");
			} else {
				toast("no");
			}
		}
	}
	
	public class GridItemSelectBlank extends DialogFragment {
		
		public GridItem i;
		
		public GridItemSelectBlank(GridItem i){
			this.i = i;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setItems(R.array.gridOptionsBlank, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   	switch(which) {
		            	   	case 0:
		            	   		StructureTypes sTypes = new StructureTypes(i);
		            	   		sTypes.show(getFragmentManager(), "sTypes");
		            	   		break;
		            		   
		            	   	case 1:
		            	   		//cancel
		            	   		break;
		            	   		
		            	   	}
		       					
		           }
		    });
		    return builder.create();
		}
	}
	
	public class StructureTypes extends DialogFragment {
		
		public GridItem i;
		
		public StructureTypes(GridItem i){
			this.i = i;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setItems(R.array.gridOptionsStructures, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   ArrayList<AbstractStructure> temp = new ArrayList<AbstractStructure>(10);
		            	   
		            	   	switch(which) {
		            	   	case 0:
		            	   		//offensive
			            	   for(int i = 0; i < chestItems.size(); i++) {
			            		   if ( chestItems.get(i).type.equals("offense") ) {
			            			   temp.add(chestItems.get(i));
			            		   }
			            	   }
		            	   		break;
		            		   
		            	   	case 1:
		            	   		//defensive
			            	   for(int i = 0; i < chestItems.size(); i++) {
			            		   if ( chestItems.get(i).type.equals("defense") ) {
			            			   temp.add(chestItems.get(i));
			            		   }
			            	   }
		            	   		break;
		            	   		
		            	   	case 2:
		            	   		//cancel
		            	   		break;
		            	   		
		            	   	}
		            	   	
		            	   	if ( temp.size() == 0 ){
		            	   		toast("No structures of this type available.");
		            	   	} else {
			            	    DisplayStructures structs = new DisplayStructures(temp, i);
		            	   		structs.show(getFragmentManager(), "chestItems");
		            	   	}

 		           }
		    });
		    return builder.create();
		}
	}
	
	public class DisplayStructures extends DialogFragment {
		
		public GridItem it;
		public ArrayList<AbstractStructure> structs;
		
		public DisplayStructures(ArrayList<AbstractStructure> structs, GridItem it){
			this.structs = structs;
			this.it = it;
	   		
	   		structures = new CharSequence[structs.size() + 1];
	   		
	   		//Copy from array list
	   		for(int i = 0; i < structs.size(); i++) {
	   			structures[i] = structs.get(i).name;
	   		}
	   		
	   		structures[structs.size()] = (CharSequence) "Cancel";
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    
		    builder.setItems(structures, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int which) {
		        	   AbstractStructure temp = structs.get(which);
		        	   
		        	   //Set position and health
		        	   temp.x = it.y;
		        	   temp.y = it.x;
		        	   temp.curHealth = temp.maxHealth;
		        	   
		        	   //Remove item from chest
		        	   chestItems.remove(temp);
		        	   
		        	   //Add to structs
		        	   structs.add(temp);
		        	   
		        	   //Place the structure in DB/do uddates
		        	   PlaceStructRequest PSR = new PlaceStructRequest(user.getUser(), user.getToken(), propID, temp);
		        	   PSR.execute();
		        	   
		        	   //Make invisible, set struct, make visible
		        	   it.image.setVisibility(ImageView.INVISIBLE);
		        	   it.struct = temp;
		        	   it.image.setImageResource(R.drawable.wall);
		        	   it.image.setVisibility(ImageView.VISIBLE);
		        	   
		               toast((String) structures[which] + " Placed");
		           }
		    });
		    
		    
		    return builder.create();
		}
	}
	
	/**
	 * Go to shop activity
	 * @param view
	 */
	public void toShop(View view) {
		Intent shop = new Intent(this, ShopActivity.class);
		startActivity(shop);
	}
	
	
	public class GridItemSelectOccupied extends DialogFragment {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    
		    builder.setItems(R.array.gridOptionsOccupied, new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int which) {
		            	   	switch(which) {
		            	   	case 0:
		            	   		//Upgrade
		            	   		break;
		            		   
		            	   	case 1:
		            	   		//Repair
		            	   		break;
		            	   		
		            	   	case 2:
		            	   		//View Stats
		            	   		break;
		            	   		
		            	   	case 3:
		            	   		//Store in chest
		            	   		break;
		            	   		
		            	   	case 4:
		            	   		//cancel
		            	   		break;
		            	   	}
		           }
		    });
		    return builder.create();
		}
	}

}
