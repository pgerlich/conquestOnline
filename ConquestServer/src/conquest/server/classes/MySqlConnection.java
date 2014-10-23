package conquest.server.classes;

//Importing the sql library.
import java.sql.*;

public class MySqlConnection {
	
	//The DBURL/User/Pass for this database
	public static final String DBURL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309R12";
	public static final String DBUSER = "u309R12";
	public static final String DBPASS = "Lau9-T_Kk";

	private Connection con;
	public boolean connected;
	
	/**
	 * Create a MySql connection object
	 */
	public MySqlConnection() {
		
		//Try and load up the drive
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception E){
			System.err.println("Unable to load driver");
			//E.printStackTrace();
		}	
		
		
		connected = connect();
	}
	
	/**
	 * Just runs a test script
	 * @throws SQLException
	 */
	public void test(){
		//Creating a statement
		Statement stmt1;
		try {
			stmt1 = con.createStatement();
		
			//Basic test statement
			ResultSet rs1 = stmt1.executeQuery("select u.username from users u");
			//PreparedStatement set = conn1.prepareStatement("Update Student set Classification = ? , CreditHours = ?, GPA = ? where StudentID = ?");
			
			//Go through all the students
			while (rs1.next()){
				System.out.println(rs1.getString(1));
			}
			
			//Close connections
			stmt1.close();
		} catch (SQLException e) {
			//System.out.println("Some error occured in test.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Processe a login request and log the user in.
	 * @param user
	 * @return The success/failure message
	 */
	public String processLogin(LoginRequest user, String token) {
		//Creating a statement
		Statement stmt1;
		
		try {
			stmt1 = con.createStatement();
			
			//Set user and pass
			ResultSet isValid = stmt1.executeQuery("select * from users where username = '" + user.user + "' and password = '" + user.password + "'");
			
			//get result set
			//ResultSet isValid = validate.
			
			//If the credentials matched
			if ( isValid.next() ) {
				//Set the user to be logged in
				PreparedStatement st = con.prepareStatement("UPDATE users SET loggedIn = ?, token = ? WHERE username = ?");
				st.setInt(1, 1);
				st.setString(2, token);
				st.setString(3, user.user);
				st.execute();
			} else {
				//Close connection
				stmt1.close();
				return "Invalid username or password";
			}
			
			
			//Close connections
			stmt1.close();
			
			return user.user + " logged in succesfully";
		} catch (SQLException e) {
			//System.out.println("Some error occured in test.");
			e.printStackTrace();
			return "Something went wrong.";
		}
	}
	
	/**
	 * Log the given user out.
	 * @param user
	 * @return The success/failure message
	 */
	public String processLogout(User user){
		//Creating a statement
		Statement stmt1;
		
		try {
			stmt1 = con.createStatement();
			
			//compare user and token
			ResultSet isValid = stmt1.executeQuery("select * from users where username = '" + user.username + "' and token = '" + user.token + "'");
			
			//If the credentials matched
			if ( isValid.next() ) {
				//Set the user to be logged out
				PreparedStatement st = con.prepareStatement("UPDATE users SET loggedIn = ?, token = ? WHERE username = ?");
				st.setInt(1, 0);
				st.setString(2, "");
				st.setString(3, user.username);
				st.execute();
			} else {
				//Close connection
				stmt1.close();
				return "Invalid token or username. Request not processed";
			}
			
			
			//Close connections
			stmt1.close();
			
			return user.username + " logged out succesfully";
		} catch (SQLException e) {
			//System.out.println("Some error occured in test.");
			e.printStackTrace();
			return "Something went wrong.";
		}
	}
	
	/**
	 * Register the user to the system.
	 * @param reggy
	 * @return
	 */
	public String registerRequest(RegisterRequest reggy) {
		//Creating a statement
		Statement stmt1;
		
		try {
			stmt1 = con.createStatement();
			
			//compare user and token
			ResultSet isValid = stmt1.executeQuery("select * from users where username = '" + reggy.username + "'");
			
			//If the credentials matched
			if ( isValid.next() ) {
				//Close connection
				stmt1.close();
				return "Username " + reggy.username + "  already in use.";
			} else {
				//Create the user account
				PreparedStatement st = con.prepareStatement("INSERT INTO users(username, password, accountType, email, accountTypeCharacter) VALUES(?, ?, ?, ?, ?)");
				st.setString(1, reggy.username);
				st.setString(2, reggy.password);
				st.setInt(3, reggy.accountType);
				st.setString(4, reggy.email);
				st.setInt(5, reggy.accountTypeCharacter);
				st.execute();
			}
			
			
			//Close connections
			stmt1.close();
			
			return reggy.username + " registered succesfully";
		} catch (SQLException e) {
			//System.out.println("Some error occured in test.");
			e.printStackTrace();
			return "Something went wrong.";
		}
	}
	
	/**
	 * Generate a random token when user logs in. Used to authenticate any future requests from that user with the server
	 * @return The token generate when the user logs in
	 */
	public String generateToken(){
		String possible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-=!@#$%^&*()_+~`/.,;[]<>?:{}";
		String token = "";
		
		int rand;
		
		for (int i = 0; i < 20; i++){
			rand = (int) (( Math.random() * 10000 ) % possible.length());
			token += possible.charAt(rand);
		}
		
		return token;
	}
	
	/**
	 * Close our connection to the server
	 * @throws SQLException
	 */
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection.");
			//e.printStackTrace();
		}
	}
	
	/**
	 * Connect to the MySql server
	 */
	public boolean connect(){
		//Try and connect
		try {
			//Connect and print out successfully
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("*** Connected to Database ***");
			return true;
		}
		
		//Some error occured.
		catch (SQLException E){
//			System.out.println("SQLException: " + E.getMessage());
//			System.out.println("SQLState: " + E.getSQLState());
//			System.out.println("VendorError: " + E.getErrorCode());
			System.out.println("*** Unable to Connect ***");
			return false;
		} 
	}
	
}
