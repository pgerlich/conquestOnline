package conquest.online.client;

import java.io.IOException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import conquest.client.classes.*;


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
	public String test;
	
	//Response from loggin in
	public LoginResponse loginResponse;
	public RegistrationResponse regResponse;
	
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
		this.host = "proj-309-R12.cs.iastate.edu";
		
		//Add all the classes
		@SuppressWarnings("rawtypes")
		Class[] classes = new Class[]{LoginRequest.class, LoginResponse.class, LogoutRequest.class, RegisterRequest.class, RegistrationResponse.class};
		
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
	        	  regResponse.message = "received";
	          }

	       }
	    });
		
	}
	
	/**
	 * Start up the client and bind it to the ports and server.
	 * @throws IOException 
	 */
	public void startClient() throws IOException{
		client.connect(5000, host, TCP, UDP);
		//System.out.println("Succesfully connected to server " + host);
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
	
	public void regster() {
		
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

	/**
	 * Tests for the logging in and out and registration
	 */
//	public static void main(String args[]) {
//		@SuppressWarnings("rawtypes")
//		Class[] classes = new Class[]{LoginRequest.class, RegisterRequest.class, LogoutRequest.class, LoginResponse.class, LogoutResponse.class};
//		ConquestClient client = new ConquestClient("test", "proj-309-R12.cs.iastate.edu", 54555, 54777, classes);
//
//		
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
//	}

}