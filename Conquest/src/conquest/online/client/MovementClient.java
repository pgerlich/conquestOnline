package conquest.online.client;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.google.android.gms.maps.model.LatLng;

import conquest.client.classes.*;
import conquest.client.classes.UpdateLatLongRequest;


/**
 * This is a connection client. See the constructor for more details. 
 * 
 * This client is used for login, logout, registration, and movement features
 * 
 * @author Paul
 *
 */
public class MovementClient implements Runnable {

	//Client to connect to
	public Client client;
	
	//TCP/UDP Port binds
	public int TCP;
	public int UDP;
	
	//Name/purpose -- Name of the client/connection - 
	public String name;
	public String host;
	public String test = " ";
	
	//server responses
	public LoginResponse loginResponse;
	public RegistrationResponse regResponse;
	public PropertyPurchaseResponse propResponse;
	public UpdateStatsResponse upStatResponse;
	public InventoryChangeResponse invChangeResponse;
	public ChestChangeResponse chChangeResponse;
	public ArrayList<PropStructsResponse> structsResponse = new ArrayList<PropStructsResponse>(10);
	public ArrayList<PersonNearYou> nearbyPeople = new ArrayList<PersonNearYou>(10);
	public StructPlaceResponse placeResponse;
	public UpdateLatLongRequest locRequest;
	
	/**
	 * Creates a connection client that connects to the specified host and ports.
	 * 
	 * @param name - The name of the client/connection
	 * @param purpose - The purpose of the connection (Combat/Movement/ect.)
	 * @param host - The host to connect to
	 * @param TCP - TCP Port to bind to
	 * @param UDP - UDP Port to bind to
	 * @throws IOException 
	 */
	public MovementClient() throws IOException{
		
		//Create and start our client
		client = new Client();
		
		//OK! So with the new release - this thread wilk be a daemon thread that shuts down when the child process shuts down (the main function call)
		//So, we start this is in a new thread.
		new Thread(client).start();
		
		//Set the variables
		this.TCP = 54555;
		this.UDP = 54777;
		this.name = "Conquest Online - Movement Client";
		this.host = "10.191.222.243";
		
		//Add all the classes
		@SuppressWarnings("rawtypes")
		Class[] classes = new Class[]{ AbstractStructure.class,
				LoginRequest.class, LoginResponse.class, LogoutRequest.class,
				RegisterRequest.class, RegistrationResponse.class,
				PropertyPurchaseRequest.class, PropertyPurchaseResponse.class,
				UpdateStatsRequest.class, UpdateStatsResponse.class,
				PropStructsRequest.class, PropStructsResponse.class,
				InventoryChangeRequest.class, InventoryChangeResponse.class,
				ChestChangeRequest.class, ChestChangeResponse.class, StructPlaceRequest.class,
				StructPlaceResponse.class, UpdateLatLongRequest.class, PersonNearYou.class };
		
		//Bind ports and start her up
		startClient();
		
		//Register classes
		registerClasses(classes);
		
		//Adds a listened to the client (for responses from the server)
	   client.addListener(new Listener() {
	       public void received (Connection connection, Object object) {
	          
	    	  if (object instanceof LoginResponse) {
	        	 loginResponse = (LoginResponse) object;
	          }
	          
	          if (object instanceof RegistrationResponse) {
	        	  regResponse = (RegistrationResponse) object;
	          }
	          
	          if (object instanceof PropertyPurchaseResponse){
	        	  propResponse = (PropertyPurchaseResponse) object;
	          }
	          
	          if (object instanceof UpdateStatsResponse){
	        	  upStatResponse = (UpdateStatsResponse) object;
	          }
	          
	          if (object instanceof PropStructsResponse){
	        	  structsResponse.add((PropStructsResponse) object);
	          }
	          
	          if (object instanceof InventoryChangeResponse){
	        	  invChangeResponse = (InventoryChangeResponse) object;
	          }
	          
	          if (object instanceof ChestChangeResponse) {
	        	  chChangeResponse = (ChestChangeResponse) object;
	          }
	          
	          if (object instanceof StructPlaceResponse){
	        	  placeResponse = (StructPlaceResponse) object;
	          }
	          
	          if (object instanceof PersonNearYou){
	        	  //remove/update info on person
	        	  removeUserByName((PersonNearYou) object);
	        	  nearbyPeople.add((PersonNearYou) object);  
	          }

	       }
	    });
		
	}
	
	/**
	 * Checks if we already have this person registered
	 * @param person
	 * @return
	 */
	public boolean containsPerson(PersonNearYou person) {
		for(int i = 0; i < nearbyPeople.size(); i++) {
			PersonNearYou thisPerson = nearbyPeople.get(i);
			if ( thisPerson.user.equals(person.user) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Remove person by name
	 * @param person
	 * @return
	 */
	public void removeUserByName(PersonNearYou person) {
		for(int i = 0; i < nearbyPeople.size(); i++) {
			PersonNearYou thisPerson = nearbyPeople.get(i);
			if ( thisPerson.user.equals(person.user) ) {
				nearbyPeople.remove(i);
			}
		}
	}
	
	/**
	 * Start up the client and bind it to the ports and server.
	 * @throws IOException 
	 */
	public void startClient() throws IOException{
		client.connect(5000, host, TCP, UDP);
		//System.out.println("Successfully connected to server " + host);
	}
	
	public void close(){
		client.close();
	}
	
	/**
	 * Attempt to login the user
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		LoginRequest login = new LoginRequest();
		login.user = username;
		login.password = password;
		
		//Send login request
		this.client.sendUDP(login);
	}
	
	/**
	 * Attempt to logout the user
	 * @param username
	 * @param token
	 */
	public void logout(String username, String token) {
		LogoutRequest logout = new LogoutRequest();
		logout.username = username;
		logout.token = token;
		
		this.client.sendUDP(logout);
	}
	
	/**
	 * Update server of our current location and movement
	 * @param user
	 * @param token
	 * @param cur
	 * @param dest
	 */
	public void updateLocation(String user, String token, LatLng cur, LatLng dest){
		UpdateLatLongRequest ullr = new UpdateLatLongRequest();
		ullr.username = user;
		ullr.token = token;
		ullr.curLat = cur.latitude;
		ullr.curLng = cur.longitude;
		ullr.destLat = dest.latitude;
		ullr.destLng = dest.longitude;
		
		this.client.sendUDP(ullr);
	}
	
	/**
	 * Ask for the structures nearby
	 * @param username
	 * @param token
	 * @param propertyID
	 * @param location
	 */
	public void requestStructs(String username, String token, int propertyID, String location){
		PropStructsRequest psr = new PropStructsRequest();
		psr.user = username;
		psr.token = token;
		psr.propID = propertyID;
		psr.location = location;
		this.client.sendUDP(psr);
	}
	
	/**
	 * Purchase a property at location
	 * @param usernameS
	 * @param tokenS
	 * @param latS
	 * @param lonS
	 */
	public void purchaseProp(String usernameS, String tokenS, double latS, double lonS) {
		PropertyPurchaseRequest ppr = new PropertyPurchaseRequest();
		ppr.username = usernameS;
		ppr.token = tokenS;
		ppr.lat = latS;
		ppr.lon = lonS;
		this.client.sendUDP(ppr);
	}
	
	/**
	 * This is used to update the stats for the player and items for the player in the character table
	 * @param username
	 * @param token
	 * @param maxHealth
	 * @param curHealth
	 * @param attack
	 * @param armor
	 * @param money
	 * @param gpm 
	 */
	public void updateStats(String username, String token, int curHealth, int money, int gpm) {
		UpdateStatsRequest update = new UpdateStatsRequest();
		update.username = username;
		update.token = token;
		update.curHealth = curHealth;
		update.gpm = gpm;
		this.client.sendUDP(update);
	}
	
	/**
	 * This is called when a structure is being put into a chest
	 * @param username
	 * @param token
	 * @param id
	 * @param location 
	 */
	public void putChest(String username, String token, int id, int pId, String location) {
		ChestChangeRequest invChange = new ChestChangeRequest();
		invChange.user = username;
		invChange.token = token;
		invChange.pId = pId;
		invChange.id = id;
		invChange.location = location;
		this.client.sendUDP(invChange);
	}
	
	/**
	 * This is called when a user is putting an item into their inventory
	 * @param user
	 * @param token
	 * @param id
	 */
	public void putInv(String user, String token, int id, int pId, String location) {
		InventoryChangeRequest invChange = new InventoryChangeRequest();
		invChange.user = user;
		invChange.token = token;
		invChange.id = id;
		invChange.pId = pId;
		invChange.location = location;
		this.client.sendUDP(invChange);
	}
	
	/**
	 * Places an item from your chest on to your property
	 * @param user
	 * @param token
	 * @param propertyID
	 * @param strctID
	 */
	public void placeOnProperty(String user, String token, int propertyID, AbstractStructure struct) {
		StructPlaceRequest SPR = new StructPlaceRequest();
		SPR.username = user;
		SPR.token = token;
		SPR.propertyID = propertyID;
		SPR.struct = struct;
		this.client.sendUDP(SPR);
	}
	


	/**
	 * Bind the classes - IN THE SAME ORDER AS THE SERVER
	 */
	public void registerClasses(@SuppressWarnings("rawtypes") Class[] classes){
		//This serializes classes (Breaks them down and reassembles??)
	    Kryo kryo = client.getKryo();
	    
	    //Add each class
	    for(int i = 0; i < classes.length; i++) {
	    	kryo.register(classes[i]);
	    }
	}

	@Override
	public void run() {
		this.client.run();
	}
}