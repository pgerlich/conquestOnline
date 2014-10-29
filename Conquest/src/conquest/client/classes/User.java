package conquest.client.classes;

import com.esotericsoftware.kryonet.Connection;

public class User {
	
	public Connection con;
	
	public String username;
	public String token;
	
	//The users location. -1 = not on map currently (in some other screen)
	public double latitude;
	public double longitude;
	
	/**
	 * Create a new user
	 * @param username
	 */
	public User(String username, String token) {
		this.username = username;
		this.token = token;
	}

}
