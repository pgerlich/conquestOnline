package conquest.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.sun.xml.internal.fastinfoset.util.CharArray;

import conquest.server.classes.KickAssert;
import conquest.server.classes.LoginRequest;
import conquest.server.classes.LoginResponse;
import conquest.server.classes.LogoutRequest;
import conquest.server.classes.MySqlConnection;
import conquest.server.classes.RegisterRequest;
import conquest.server.classes.RegistrationResponse;
import conquest.server.classes.User;

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
	public ArrayList<User> usersConnected;
	
	public MySqlConnection myCon;
	
	
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
		usersConnected = new ArrayList<User>();
		
		
		//Bind Start up and bind ports
		if ( startServer(TCP, UDP) ) {
			myCon = new MySqlConnection();
			
			//See if the connection succeded
			if ( myCon.connected ) {
				//Register classes
				registerClasses(classes);
				
				//Adds a listened to the client (for responses from the server)
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
	public boolean startServer(int TCP, int UDP){
		server.start();
		
		System.out.println("Server " + name + " online");
		
		try {
			server.bind(TCP, UDP);
			System.out.println("Binded to UDP:" + UDP + " and listening.");
			return true;
		} catch (IOException e) {
			System.out.println("Binding failed. Port already in use?");
			return false;
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
		    	   
		    	  //Login request
		    	  if (obj instanceof LoginRequest) {
		    		  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": ");
		    		  
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
		    	      
	    	      //Logout request
	    	      if (obj instanceof LogoutRequest) {
	    	    	  System.out.println("(" + con.getRemoteAddressUDP() + ")" + ": ");
	    	    	  LogoutRequest log = (LogoutRequest) obj;
	    	    	  System.out.println(myCon.processLogout(log));
	    	      }
		       }
		       
		    });
	}
	
	public User findUser(String username) {
		for(int i = 0; i < usersConnected.size(); i++) {
			if ( usersConnected.get(i).username.equals(username) ){
				return usersConnected.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * Kick the user from the server FIXME!!
	 * @param user
	 * @return
	 */
	public void kickFromServer(User user) {
		//Send them message saying they're disconnected and close connection
		KickAssert kick = new KickAssert();
		kick.message = "You have been kicked from the server.";
		user.con.sendUDP(kick);
		user.con.close();
		
		LogoutRequest log = new LogoutRequest();
		log.username = user.username;
		log.token = user.token;
		
		//Log them out as far as the database is concerned
		myCon.processLogout(log);
		
		//Remove them from the list of logged in users
		usersConnected.remove(user);
		
		System.out.println(user.username + " kicked from server.");
	}
		
	public void statistics(){
		System.out.println("# of users connected: " + usersConnected.size());
		for ( int i = 0; i < usersConnected.size(); i++ ) {
			System.out.println(usersConnected.get(i).toString());
		}
	}
	
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void main(String args[]) {
		Class[] classes = new Class[]{LoginRequest.class, LoginResponse.class, LogoutRequest.class, RegisterRequest.class, RegistrationResponse.class};
		ConquestServer test = new ConquestServer("ConquestTest", 54555, 54777, classes);
		
		try{
			BufferedReader br = 
	                      new BufferedReader(new InputStreamReader(System.in));
	 
			String input;
	 
			while((input=br.readLine())!=null){
				if ( input.toLowerCase().contains("kick") ) {
					char[] str = input.toCharArray();
					String user = "";
					for ( int i = 0; i < input.length(); i++ ) {
						if ( str[i] == ' ' ) {
							user = input.substring(i + 1);
						}
					}

	    		  //See if this user is logged in 
	    		  User alreadyOnline = test.findUser(user);
	    		  
	    		  //If this user is logged in, kick them
	    		  if ( alreadyOnline != null) {
	    			  test.kickFromServer(alreadyOnline);
	    		  } else {
	    			  System.out.println(user + " not currently online.");
	    		  }
		    		  
				} if ( input.toLowerCase().contains("help") ) {
					System.out.println("#### List of commands ####");
					System.out.println("## Kick user :: Kicks the user from the server ##");
					System.out.println("## Help :: Displays this menu of commands ##");
				} else {
					System.out.println("Command " + input + " not recognized.");
				}
			}
	 
		}catch(IOException io){
			System.out.println("Error occured reading from user input");
		}	
	}
}