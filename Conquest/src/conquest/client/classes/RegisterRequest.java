package conquest.client.classes;

public class RegisterRequest {
	
	public String username;
	public String password;
	public String email;
	
	//The account type (Facebook/Google/local)
	public int accountType;
	
	//The character type (0=spy,1=engineer,2=hacker)
	public int accountTypeCharacter;
	

}
