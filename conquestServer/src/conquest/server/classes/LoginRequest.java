package conquest.server.classes;

public class LoginRequest {
	
	public String user;
	public String password;
	public boolean success;
	public String message;
	
	public LoginRequest(String username, String password) {
		this.user = username;
		this.password = password;
		this.success = false;
		this.message = "";
	}

	
}
