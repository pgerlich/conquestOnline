package conquest.server;

import java.io.IOException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import conquest.server.classes.*;


/**
 * This is a connection client. See the constructor for more details. 
 * 
 * This client is used for login, logout, registration, and movement features
 * 
 * @author Paul
 *
 */
public class ConquestClient {

	//Client to connect to
	public Client client;
	
	//TCP/UDP Port binds
	public int TCP;
	public int UDP;
	
	//Name/purpose -- Name of the client/connection - 
	public String name;
	public String purpose;
	
	public User user;
	
	/**
	 * Creates a connection client that connects to the specified host and ports.
	 * 
	 * @param name - The name of the client/connection
	 * @param purpose - The purpose of the connection (Combat/Movement/ect.)
	 * @param host - The host to connect to
	 * @param TCP - TCP Port to bind to
	 * @param UDP - UDP Port to bind to
	 */
	public ConquestClient(String name, String host, int TCP, int UDP, @SuppressWarnings("rawtypes") Class[] classes){
		
		//Create and start our client
		client = new Client();
		
		//OK! So with the new release - this thread wilk be a daemon thread that shuts down when the child process shuts down (the main function call)
		//So, we start this is in a new thread.
		new Thread(client).start();
		
		//Set the variables
		this.TCP = TCP;
		this.UDP = UDP;
		this.name = name;
		
		//Bind ports and such
		startClient(host, TCP, UDP);
		
		//Register classes
		registerClasses(classes);
		
		//Adds a listened to the client (for responses from the server)
	   client.addListener(new Listener() {
	       public void received (Connection connection, Object object) {
	          if (object instanceof LoginResponse) {
	        	 LoginResponse login = (LoginResponse) object;
	             user = new User(login.username, login.message);
	             System.out.println(login.message);
	          }
	          
	          if (object instanceof LogoutResponse) {
	        	  LogoutResponse logout = (LogoutResponse) object;
	        	  System.out.println(logout.message);
	        	  
	        	  //If logged out successfully
	        	  user = null;
	          }
	          

	       }
	    });
		
	}
	
	/**
	 * Start up the client and bind it to the ports and server.
	 */
	public void startClient(String host, int TCP, int UDP){
		try {
			client.connect(5000, host, TCP, UDP);
			System.out.println("Succesfully connected to server " + host);
		} catch (IOException e) {
			System.out.println("Could not connect to server " + host);
			//.printStackTrace();
		}
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

	/**
	 * Tests for the logging in and out and registration
	 */
	public static void main(String args[]) {
		@SuppressWarnings("rawtypes")
		Class[] classes = new Class[]{LoginRequest.class, RegisterRequest.class, LogoutRequest.class, LoginResponse.class, LogoutResponse.class};
		ConquestClient client = new ConquestClient("test", "proj-309-R12.cs.iastate.edu", 54555, 54777, classes);

		
		LoginRequest login = new LoginRequest();
		login.user = "pgerlich";
		login.password = "paulg1450";
		
		client.client.sendTCP(login);
		
//		RegisterRequest reggy = new RegisterRequest();
//		reggy.username = "test1";
//		reggy.password = "test";
//		reggy.accountType = 0;
//		reggy.email = "test1@test.com";
//		reggy.accountTypeCharacter = 0;
//		
//		client.client.sendUDP(reggy);
//		
//		
//		LogoutRequest log = new LogoutRequest();
//		log.username = client.user.username;
//		log.token = client.user.token;
//		
//		
//		client.client.sendUDP(log);
		
		
	}

}