package conquest.server.classes;

//Importing the sql library.
import java.sql.*;

public class MySqlConnection {
	
	//The DBURL/User/Pass for this database
	public static final String DBURL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309R12";
	public static final String DBUSER = "u309R12";
	public static final String DBPASS = "Lau9-T_Kk";

	private Connection con;
	
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
		
		
		this.connect();
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
	public void connect(){
		//Try and connect
		try {
			//Connect and print out successfully
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("*** Connected to Database ***");
		}
		
		//Some error occured.
		catch (SQLException E){
//			System.out.println("SQLException: " + E.getMessage());
//			System.out.println("SQLState: " + E.getSQLState());
//			System.out.println("VendorError: " + E.getErrorCode());
			System.out.println("*** Unable to Connect ***");
		}
	}
	
	public static void main(String args[]) {
		MySqlConnection con = new MySqlConnection();
		con.test();
	}
}
