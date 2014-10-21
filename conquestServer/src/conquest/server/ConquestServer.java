package conquest.server;

import java.io.IOException;
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

	//Client to connect to
	public Server server;
	public boolean online;
	
	//TCP/UDP Port binds
	public int TCP;
	public int UDP;
	
	//Name/purpose -- Name of the client/connection - 
	public String name;
	public String purpose;
	
	//Array of online users.
	public ArrayList<Connection> usersConnected;
	public int numUsersConnected;
	
	// JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost/EMP";

    //  Database credentials
    static final String USER = "u309R12";
    static final String PASS = "Lau9-T_Kk";
	
	
	/**
	 * Creates a connection client that connects to the specified host and ports.
	 * 
	 * @param name - The name of the client/connection
	 * @param purpose - The purpose of the connection (Combat/Movement/ect.)
	 * @param host - The host to connect to
	 * @param TCP - TCP Port to bind to
	 * @param UDP - UDP Port to bind to
	 */
	
	public ConquestServer(String name, int TCP, int UDP, @SuppressWarnings("rawtypes") Class[] classes){
		
		System.out.println("Attemping to start server..");
		
		//Create our server
		server = new Server();
		
		//Set the ports and info
		this.TCP = TCP;
		this.UDP = UDP;
		this.name = name;
		usersConnected = new ArrayList<Connection>();
		
		
		//Bind Start up and bind ports
		startServer(TCP, UDP);
		
		//Register classes
		registerClasses(classes);
		
		//Adds a listened to the client (for responses from the server)
		addListener();
		
		System.out.println("------ " + name + ":" + UDP + " ------");
		
		online = true;
	}
	
	/**
	 * Start up the server and bind it to the ports and server.
	 */
	public void startServer(int TCP, int UDP){
		server.start();
		
		System.out.println("Server " + name + " online");
		
		try {
			server.bind(TCP, UDP);
			System.out.println("Binded to UDP:" + UDP + " and listening.");
		} catch (IOException e) {
			System.out.println("Binding failed. Port already in use?");
			//.printStackTrace();
		}
	}
	
	/**
	 * Bind the classes - IN THE SAME ORDER AS THE SERVER
	 */
	public void registerClasses(@SuppressWarnings("rawtypes") Class[] classes){
		//This serializes classes (Breaks them down and reassembles??)
	    Kryo kryo = server.getKryo();
	    
	    //Add each class
	    for(int i = 0; i < classes.length; i++) {
	    	kryo.register(classes[i]);
	    	System.out.println("Registered class: " + classes[i].getSimpleName());
	    }
	}
	
	/**
	 * Add a listener to the client to deal with server responses! :D
	 */
	public void addListener(){
		   server.addListener(new Listener() {
		       public void received (Connection con, Object obj) {
		    	  System.out.print("(" + con.getRemoteAddressUDP() + ")" + ": ");
		          
		    	  //Login request test
		    	  if (obj instanceof LoginRequest) {
		    		  
		    		  //If the connection is a new one
		    		  if ( !usersConnected.contains(con) ) {
		    			  LoginRequest user = (LoginRequest) obj;
		    			  con.setName(user.user);
		    			  
			    		  //Add user to online array List and increase number of clients
			    		  usersConnected.add(con);
			    		  numUsersConnected++;
			    		  
			    		  System.out.println(con.toString() + " Logged in.");
			    		  
			    	  //If the connection is not new - second log in attempt
		    		  } else {
		    			  //WTF happened - how is this person logging in twice?
		    		  }

		    		  //Print some basic connection stats.
		    		  statistics();
		    	  }
		       }
		       
		    });
	}
	
	public void loginUser(LoginRequest user) {
		
	}
	
	public void statistics(){
		System.out.println("# of users connected: " + numUsersConnected);
		for ( int i = 0; i < usersConnected.size(); i++ ) {
			System.out.println(usersConnected.get(i).toString());
		}
	}
	
	public static void main(String args[]) {
		Class[] classes = new Class[]{LoginRequest.class};
		ConquestServer test = new ConquestServer("ConquestTest", 54555, 54777, classes);
		
			  
	}
}
