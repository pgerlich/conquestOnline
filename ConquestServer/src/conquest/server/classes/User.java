package conquest.server.classes;

import com.esotericsoftware.kryonet.Connection;

public class User {
	
	public Connection con;
	
	public String username;
	public String token;
	
	/**
	 * Create a new user
	 * @param username
	 */
	public User(String username, String token) {
		this.username = username;
		this.token = token;
	}

}
