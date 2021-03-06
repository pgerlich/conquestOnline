package conquest.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import conquest.server.classes.*;
/**
 * This is a connection client. See the constructor for more details.
 * 
 * 
 * @author Paul
 * 
 */
public class ConquestServer {

	// Client to connect to
	public Server server;
	public Server movementServer;
	public boolean online;

	// TCP/UDP Port binds
	public int TCP;
	public int UDP;

	// Name/purpose -- Name of the client/connection -
	public String name;
	public String purpose;

	// Array of online users.
	public static ArrayList<User> usersConnected;

	public static MySqlConnection myCon;

	/**
	 * Creates a connection client that connects to the specified host and
	 * ports.
	 * 
	 * @param name
	 *            - The name of the client/connection
	 * @param purpose
	 *            - The purpose of the connection (Combat/Movement/ect.)
	 * @param host
	 *            - The host to connect to
	 * @param TCP
	 *            - TCP Port to bind to
	 * @param UDP
	 *            - UDP Port to bind to
	 */

	public ConquestServer(String name, int TCP, int UDP,
			@SuppressWarnings("rawtypes") Class[] classes) {

		System.out.println("Attemping to start server..");

		// Create our server
		server = new Server();

		// Set the ports and info
		this.TCP = TCP;
		this.UDP = UDP;
		this.name = name;
		usersConnected = new ArrayList<User>();

		// Bind Start up and bind ports
		if (startServer(server, TCP, UDP)) {
			myCon = new MySqlConnection();

			// See if the connection succeeded
			if (myCon.connected) {
				// Register classes
				registerClasses(classes);

				// Adds a listened to the client (for responses from the server)
				addListener();

				System.out.println("----- " + name + " : " + UDP + " -----");

				online = true;
			} else {
				online = false;
			}
		} else {
			online = false;
			System.out.println("----- Binding server failed -----");
		}

	}

	/**
	 * Start up the server and bind it to the ports and server.
	 */
	public boolean startServer(Server serv, int TCP, int UDP) {
		serv.start();

		System.out.println("Server " + name + " online");

		try {
			serv.bind(TCP, UDP);
			System.out.println("Binded to UDP:" + UDP + " and listening.");
			return true;
		} catch (IOException e) {
			System.out.println("Binding failed. Port already in use?");
			return false;
			// .printStackTrace();
		}
	}

	/**
	 * Bind the classes - IN THE SAME ORDER AS THE SERVER
	 */
	public void registerClasses(@SuppressWarnings("rawtypes") Class[] classes) {
		// This serializes classes (Breaks them down and reassembles??)
		Kryo kryo = server.getKryo();
		
		System.out.print("Registering classes: ");

		// Add each class
		for (int i = 0; i < classes.length; i++) {
			kryo.register(classes[i]);
			System.out.print(classes[i].getSimpleName() + ", ");
		}
		
		System.out.println(" ...");
	}

	/**
	 * Add a listener to the client to deal with server responses! :D
	 */
	public void addListener(){
		   server.addListener(new Listener() {
		       public void received (Connection con, Object obj) {
		    	   
		    	   //If for some reason our connection died or timed out
		    	   if ( myCon.connected == false ) {
		    		   myCon.connected = myCon.connect();
		    	   }
		    	   
		    	  //Login request
		    	  if (obj instanceof LoginRequest) {
		    		  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Login Request");
		    		  
		    		  //A new login request cast from object
		    		  LoginRequest user = (LoginRequest) obj;
		    		  
		    		  //Returns a logged in user if credentials match, null otherwise
		    		  User thisUser = myCon.processLogin(user);
		    		  
		    		  //Login failed - let them know!
		    		  if ( thisUser == null ) {
			    		  LoginResponse response = new LoginResponse();
			    		  response.message = "Incorrect username or password.";
			    		  response.success = false;
			    		  
			    		  //Send back the user object w/ token
			    		  con.sendUDP(response);
		    		  } else { 
			    		  
			    		  //See if this user is logged in 
			    		  User alreadyOnline = findUser(user.user);
			    		  
			    		  //If this user is logged in, kick them
			    		  if ( alreadyOnline != null) {
			    			  kickFromServer(alreadyOnline);
			    		  }
			    		  
			    		  //Name connection (Ip/Port) after user. Connect it to this user
			    		  con.setName(user.user);
			    		  thisUser.con = con;
			    		  
			    		  //Add this user to logged in users
			    		  usersConnected.add(thisUser);
			    		  
			    		  //Set token to send
			    		  LoginResponse response = new LoginResponse();
			    		  response.token = thisUser.token;
			    		  response.username = user.user;
			    		  response.success = true;
			    		  response.message = "Logged in succesfully";
			    		  
			    		  System.out.println(user.user + " logged in succesfully");
			    		  
			    		  //Send back the user object w/ token
			    		  con.sendUDP(response);
			    		  }
		    	  }
		    	  
	    	      //Registration request
	    	      if (obj instanceof RegisterRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Attempting to register...");
	    	    	  RegisterRequest reggy = (RegisterRequest) obj;
	    	    	  RegistrationResponse response = myCon.registerRequest(reggy);
	    	    	  System.out.println(response.message);
	    	    	  con.sendUDP(response);
	    	      }
	    	      
	    	      if (obj instanceof PropertyPurchaseRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Property Purchase Request");
	    	    	  PropertyPurchaseRequest prop = (PropertyPurchaseRequest) obj;
	    	    	  PropertyPurchaseResponse response = myCon.propertyPurchase(prop);
	    	    	  System.out.println(response.message);
	    	    	  con.sendUDP(response);
	    	      }
		    	      
	    	      //Logout request
	    	      if (obj instanceof LogoutRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Logout Request");
	    	    	  LogoutRequest log = (LogoutRequest) obj;
	    	    	  //System.out.println(myCon.processLogout(log));
	    	    	  
	    	    	  //Remove from connected
	    	    	  User thisUser = findUser(log.username);
	    	    	  
	    	    	  //Kick them from servers
	    	    	  if ( thisUser != null ) {
	    	    		  kickFromServer(thisUser);
	    	    	  }
	    	    	  
	    	      }
	    	      
	    	      //Update stats request
	    	      if (obj instanceof UpdateStatsRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Update Stats");
	    	    	  UpdateStatsRequest update = (UpdateStatsRequest) obj;
	    	    	  UpdateStatsResponse response = myCon.updateStats(update);
	    	    	  System.out.println(response.message);
	    	    	  con.sendUDP(response);
	    	      }
	    	      
	    	      //Updates the inventory by adding latest item
	    	      if (obj instanceof InventoryChangeRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Inventory Change");
	    	    	  InventoryChangeRequest invChange = (InventoryChangeRequest) obj;
	    	    	  InventoryChangeResponse response = myCon.putInv(invChange);
	    	    	  System.out.println(response.message);
	    	    	  con.sendUDP(response);
	    	      }
	    	      
	    	      //Updates the chest when user buys structures with the latest item
	    	      if (obj instanceof ChestChangeRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Chest Change");
	    	    	  ChestChangeRequest chChange = (ChestChangeRequest) obj;
	    	    	  ChestChangeResponse response = myCon.putChest(chChange);
	    	    	  System.out.println(response.message);
	    	    	  con.sendUDP(response);	      
	    	      }
	    	      
	    	      //Grab structures for a given property
	    	      if (obj instanceof PropStructsRequest){
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Retrieve Property Structs (" + ((PropStructsRequest)obj).location + ")" );
	    	    	  PropStructsRequest psr = (PropStructsRequest) obj;
	    	    	  ArrayList<PropStructsResponse> psres = myCon.requestStructuresOnProperty(psr);
	    	    	  System.out.println(psres.get(0).message);
	    	    	  
	    	    	  
	    	    	  //Send each structure
	    	    	  for(int i = 0; i < psres.size(); i++) {
	    	    		  con.sendUDP(psres.get(i));
	    	    	  }
	    	    	  
	    	      }
	    	      
	    	      if (obj instanceof StructPlaceRequest){
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Struct Placement" );
	    	    	  StructPlaceRequest SPR = (StructPlaceRequest) obj;
	    	    	  StructPlaceResponse SPRes = myCon.placeStructure(SPR);
	    	    	  System.out.println(SPRes.message);
	    	    	  con.sendUDP(SPRes);
	    	      }
	    	      
	    	      if (obj instanceof UpdateLatLongRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": Update Lat/Long location" );
	    	    	  UpdateLatLongRequest ULLR = (UpdateLatLongRequest) obj;
	    	    	   
	    	    	  User thisUser = findUser(ULLR.username);
	    	    	  
	    	    	  if ( thisUser != null ) {
		    	    	  //Set the users current location
		    	    	  thisUser.latitude = ULLR.curLat;
		    	    	  thisUser.longitude = ULLR.curLng;
	    	    	  }
	    	    	  
	    	    	   myCon.updateLocation(ULLR);
	    	    	   
	    	    	   notifyNearbyUsers(ULLR);
	    	      }
	    	    
		       }
		       
		    });
	}
	
	/**
	 * Return user of the connection
	 * @param con
	 * @return
	 */
	public User findUser(Connection con){
		for (int i = 0; i < usersConnected.size(); i++) {
			if ( usersConnected.get(i).con.equals(con) ) {
				return usersConnected.get(i);
			}
		}
		
		return null;
	}

	/**
	 * Find user by name
	 * @param username
	 * @return
	 */
	public User findUser(String username) {
		for (int i = 0; i < usersConnected.size(); i++) {
			if (usersConnected.get(i).username.equals(username)) {
				return usersConnected.get(i);
			}
		}

		return null;
	}
	
	/**
	 * Sends out a notification to all nearby users of people near them's movement
	 * @param ULLR
	 */
	public void notifyNearbyUsers(UpdateLatLongRequest ULLR){
		for (User u :  usersConnected) {
			
			//Don't notify them of their own movement - for now.
			//if ( !u.username.equals(ULLR.username)) {
				if ( distance(u.latitude, u.longitude, ULLR.curLat, ULLR.curLng) <= 1 ) {
					
					//Set all information TODO: More efficient way of doing this?
					PersonNearYou p = myCon.getUserInfo(ULLR.username);
					p.user = ULLR.username;
					p.curLat = ULLR.curLat;
					p.curLng = ULLR.curLng;
					p.destLat = ULLR.destLat;
					p.destLng = ULLR.destLng;
					
					//send it off
					u.con.sendUDP(p);
					
					System.out.println("NOTIFIED: " + u.username + " of " + ULLR.username);
				}
			//}
		}
	}

	//Calculate distance between two lat/longs
	private double distance(double lat1, double lon1, double lat2, double lon2) {
	  double theta = lon1 - lon2;
	  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	  dist = Math.acos(dist);

	  dist = rad2deg(dist);

	  dist = dist * 60 * 1.1515;

	  return (dist);
	}

	//Degrees to radians
	private double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	}

	//Radians to degrees
	private double rad2deg(double rad) {
	  return (rad * 180 / Math.PI);
	}


	/**
	 * Kick the user from the server FIXME!!
	 * 
	 * @param user
	 * @return
	 */
	public static void kickFromServer(User user) {
		// Send them message saying they're disconnected and close connection
		// KickAssert kick = new KickAssert();
		// kick.message = "You have been kicked from the server.";
		// user.con.sendUDP(kick);
		// user.con.close();

		LogoutRequest log = new LogoutRequest();
		log.username = user.username;
		log.token = user.token;

		// Log them out as far as the database is concerned
		myCon.processLogout(log);

		for(int i = 0; i < usersConnected.size(); i++) {
			if ( usersConnected.get(i).username.equals(user.username) ) {
				usersConnected.remove(i);
			}
		}

		System.out.println(user.username + " kicked from server.");
	}

	public static void statistics() {
		System.out.println("# of users connected: " + usersConnected.size());
		for (int i = 0; i < usersConnected.size(); i++) {
			User thisUser = usersConnected.get(i);
			System.out.println("-- Username: " + thisUser.username + ", Lat: " + thisUser.latitude + ", Lon: " + thisUser.longitude + " ---");
		}
	}

	@SuppressWarnings({ "rawtypes", "static-access" })
	public static void main(String args[]) {
		Class[] classes = new Class[] { AbstractStructure.class,
				LoginRequest.class, LoginResponse.class, LogoutRequest.class,
				RegisterRequest.class, RegistrationResponse.class,
				PropertyPurchaseRequest.class, PropertyPurchaseResponse.class,
				UpdateStatsRequest.class, UpdateStatsResponse.class,
				PropStructsRequest.class, PropStructsResponse.class,
				InventoryChangeRequest.class, InventoryChangeResponse.class,
				ChestChangeRequest.class, ChestChangeResponse.class, StructPlaceRequest.class, 
				StructPlaceResponse.class, UpdateLatLongRequest.class, PersonNearYou.class };
		ConquestServer test = new ConquestServer("Conquest Main", 54555, 54777,
				classes);

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String input;

			while ((input = br.readLine()) != null) {
				if (input.toLowerCase().contains("kick")) {
					char[] str = input.toCharArray();
					String user = "";
					for (int i = 0; i < input.length(); i++) {
						if (str[i] == ' ') {
							user = input.substring(i + 1);
						}
					}

					// See if this user is logged in
					User alreadyOnline = test.findUser(user);

					// If this user is logged in, kick them
					if (alreadyOnline != null) {
						test.kickFromServer(alreadyOnline);
					} else {
						System.out.println(user + " not currently online.");
					}

				} else if (input.toLowerCase().contains("help")) {
					System.out.println("#### List of commands ####");
					System.out.println("## Kick user :: Kicks the user from the server ##");
					System.out.println("## Shutdown :: Shut down the server gracefully ##");
					System.out.println("## online :: Display current users online and info ##");
					System.out.println("## Help :: Displays this menu of commands ##");
				} else if (input.toLowerCase().contains("online")) {
					statistics();
				} else if (input.toLowerCase().contains("shutdown")) {
					for(int i = 0; i < usersConnected.size(); i++) {
						kickFromServer(usersConnected.get(i));
					}
					
					System.out.println("All users kicked..");
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						//
					}
					
					System.out.println("Shutting down...");
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						//
					}
					
					System.exit(0);
				} else {
					System.out.println("Command " + input + " not recognized.");
				}
			}

		} catch (IOException io) {
			System.out.println("Error occured reading from user input");
		}
	}
}