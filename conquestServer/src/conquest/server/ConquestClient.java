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
		client.start();
		
		//Set the variables
		this.TCP = TCP;
		this.UDP = UDP;
		this.name = name;
		
		//Bind ports and such
		startClient(host, TCP, UDP);
		
		//Register classes
		registerClasses(classes);
		
		//Adds a listened to the client (for responses from the server)
		//addListener();
		
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
	 * Add a listener to the client to deal with server responses! :D
	 */
//	public void addListener(){
//		   client.addListener(new Listener() {
//		       public void received (Connection con, Object obj) {
//		          if (obj instanceof SomeResponse) {
//		             SomeResponse response = (SomeResponse)obj;
//		             System.out.println(response.text);
//		          }
//		       }
//		    });
//	}
	
	public static void main(String args[]) {
		Class[] classes = new Class[]{LoginRequest.class};
		ConquestClient client = new ConquestClient("test", "127.0.0.1", 54555, 54777, classes);
		
		LoginRequest test = new LoginRequest("test", "test");
		
		client.client.sendUDP(test);
	}
}
