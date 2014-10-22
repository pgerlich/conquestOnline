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
	
	public String processLogin(LoginRequest user) {
		//Creating a statement
		Statement stmt1;
		
		try {
			stmt1 = con.createStatement();
			
			//Set user and pass
			ResultSet isValid = stmt1.executeQuery("select * from users where username = 'pgerlich' and password = 'paulg1450'");
			
			//get result set
			//ResultSet isValid = validate.
			
			//If the credentials matched
			if ( isValid.next() ) {
				//Set the user to be logged in
				PreparedStatement st = con.prepareStatement("UPDATE users SET loggedIn = ? WHERE loggedIn = 0");
				st.setInt(1, 1);
				st.execute();
			} else {
				//Close connection
				stmt1.close();
				return "Invalid username or password";
			}
			
			
			//Close connections
			stmt1.close();
			
			return "Logged in succesfully";
		} catch (SQLException e) {
			//System.out.println("Some error occured in test.");
			e.printStackTrace();
			return "Something went wrong.";
		}
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
