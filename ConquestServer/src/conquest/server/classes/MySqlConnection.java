package conquest.server.classes;

//Importing the sql library.
import java.sql.*;
import java.util.ArrayList;

public class MySqlConnection {

	// The DBURL/User/Pass for this database
	public static final String DBURL = "jdbc:mysql://mysql.cs.iastate.edu:3306/db309R12";
	public static final String DBUSER = "u309R12";
	public static final String DBPASS = "Lau9-T_Kk";

	private Connection con;
	public boolean connected;

	/**
	 * Create a MySql connection object
	 */
	public MySqlConnection() {

		// Try and load up the drive
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception E) {
			System.err.println("Unable to load driver");
			// E.printStackTrace();
		}

		connected = connect();
	}

	/**
	 * Just runs a test script
	 * 
	 * @throws SQLException
	 */
	public void test() {
		// Creating a statement
		Statement stmt1;
		try {
			stmt1 = con.createStatement();

			// Basic test statement
			ResultSet rs1 = stmt1
					.executeQuery("select u.username from users u");
			// PreparedStatement set =
			// conn1.prepareStatement("Update Student set Classification = ? , CreditHours = ?, GPA = ? where StudentID = ?");

			// Go through all the students
			while (rs1.next()) {
				System.out.println(rs1.getString(1));
			}

			// Close connections
			stmt1.close();
		} catch (SQLException e) {
			// System.out.println("Some error occured in test.");
			e.printStackTrace();
		}
	}

	/**
	 * Processe a login request and log the user in.
	 * 
	 * @param user
	 * @return The success/failure message
	 */
	public User processLogin(LoginRequest user) {
		// Creating a statement
		Statement stmt1;

		try {
			stmt1 = con.createStatement();

			// Set user and pass
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ user.user + "' and password = '" + user.password
							+ "'");

			// get result set
			// ResultSet isValid = validate.

			// If the credentials matched
			if (isValid.next()) {
				// Make a new user object upon success
				User thisUser = new User(user.user, generateToken());

				// Set the user to be logged in
				PreparedStatement st = con
						.prepareStatement("UPDATE users SET loggedIn = ?, token = ? WHERE username = ?");
				st.setInt(1, 1);
				st.setString(2, thisUser.token);
				st.setString(3, user.user);
				st.execute();

				// Output sucess message to server command line
				System.out.println(user.user + " Logged in");

				// Close connections
				stmt1.close();

				return thisUser;
			} else {
				// Close connection
				stmt1.close();
				return null;
			}
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()
					+ "  occured while attempting to login " + user.user);
			System.out.println(e.getMessage());
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * Log the given user out.
	 * 
	 * @param user
	 * @return The success/failure message
	 */
	public String processLogout(LogoutRequest logout) {
		// Creating a statement
		Statement stmt1;

		try {
			stmt1 = con.createStatement();

			// compare user and token
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ logout.username + "' and token = '"
							+ logout.token + "'");

			// If the credentials matched
			if (isValid.next()) {
				// Set the user to be logged out
				PreparedStatement st = con
						.prepareStatement("UPDATE users SET loggedIn = ?, token = ? WHERE username = ?");
				st.setInt(1, 0);
				st.setString(2, generateToken());
				st.setString(3, logout.username);
				st.execute();
			} else {
				// Close connection
				stmt1.close();
				return "Invalid token  or username. Request not processed.";
			}

			// Close connections
			stmt1.close();

			return logout.username + " logged out succesfully";
		} catch (SQLException e) {
			// System.out.println("Some error occured in test.");
			e.printStackTrace();
			return "Something went wrong.";
		}
	}

	/**
	 * Register the user to the system.
	 * 
	 * @param reggy
	 * @return
	 */
	public RegistrationResponse registerRequest(RegisterRequest reggy) {
		// Creating a statement
		Statement stmt1;

		RegistrationResponse response = new RegistrationResponse();

		try {
			stmt1 = con.createStatement();

			// compare user and token
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ reggy.username + "'");

			// If the credentials matched
			if (isValid.next()) {
				// Close connection
				stmt1.close();
				response.message = "Username " + reggy.username
						+ " already in use.";
				response.success = false;
				return response;
			} else {
				// Create the user account
				PreparedStatement st = con
						.prepareStatement("INSERT INTO users(username, password, accountType, email, accountTypeCharacter) VALUES(?, ?, ?, ?, ?)");
				st.setString(1, reggy.username);
				st.setString(2, reggy.password);
				st.setInt(3, reggy.accountType);
				st.setString(4, reggy.email);
				st.setString(5, reggy.accountTypeCharacter);
				st.execute();

				// Create the character
				PreparedStatement st1 = con
						.prepareStatement("INSERT INTO characters(username, type, maxHealth, attack, armor, speed, stealth, tech, level, exp, lat, lon, curHealth) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				st1.setString(1, reggy.username);
				st1.setString(2, reggy.accountTypeCharacter);
				st1.setInt(3, 100);
				st1.setInt(4, 100);
				st1.setInt(5, 100);
				st1.setInt(6, 100);
				st1.setInt(7, 100);
				st1.setInt(8, 100);
				st1.setInt(9, 1);
				st1.setInt(10, 0);
				st1.setDouble(11, 0);
				st1.setDouble(11, 0);
				st1.setInt(12, 0);
				st1.setInt(13, 100);
				st1.execute();
			}

			// Close connections
			stmt1.close();

			response.message = reggy.username + " registered succesfully";
			response.success = true;
			return response;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()
					+ " occured while trying to register " + reggy.username);
			// e.printStackTrace();
			response.message = e.getMessage();
			response.success = false;
			return response;
		}
	}
	
	/**
	 * return proprties structures
	 * 
	 * @param reggy
	 * @return
	 */
	public ArrayList<PropStructsResponse> requestStructuresOnProperty(PropStructsRequest psr) {
		// Creating a statement
		Statement stmt1;

		ArrayList<PropStructsResponse> response = new ArrayList<PropStructsResponse>(10);

		try {
			stmt1 = con.createStatement();

			// compare user and token
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ psr.user + "' AND token = '" + psr.token + "'");

			// If the credentials matched
			if (!isValid.next()) {
				// Close connection
				stmt1.close();
				PropStructsResponse nothing = new PropStructsResponse();
				nothing.message = "Invalid request";
				nothing.success = false;
				response.add(nothing);
				return response;
			} else {
				
				//Grab property ID
				ResultSet getPID = stmt1.executeQuery("select * from properties WHERE owner = '" + psr.user + "'");
				
				//If we own a property
				if ( getPID.next() ) {
					int id = getPID.getInt("propertyID");
					
					if ( psr.location.equals("property") ) {
					
						ResultSet structs = stmt1.executeQuery("select * from userStructures inner join structures on userStructures.structureID = structures.structureID where propertyID = '" + id + "'");
						
						while (structs.next()) {
							PropStructsResponse thisResponse = new PropStructsResponse();
							thisResponse.message = "Succesfully grabbed structures.";
							thisResponse.success = true;
							thisResponse.propertyID = structs.getString("structureID");
							
							AbstractStructure temp = new AbstractStructure();
							temp.name = structs.getString("name");
							temp.imageID = structs.getInt("imageID");
							temp.type = structs.getString("type");
							temp.x = structs.getInt("topX");
							temp.y = structs.getInt("topY");
							temp.level = structs.getInt("level");
							temp.cost = structs.getInt("price");
							temp.curHealth = structs.getInt("curHealth");
							temp.maxHealth = structs.getInt("maxHealth");
							temp.defense = structs.getInt("defense");
							temp.viewRadius = structs.getInt("viewRadius");
							temp.enabled = true;
							
							thisResponse.struct = temp;
							
							response.add(thisResponse);
						}
					} else if ( psr.location.equals("chest") ) {
						
						ResultSet structs = stmt1.executeQuery("select * from chests where propertyID = '" + id + "'");
						
						//If we have a chest..
						if (structs.next()) {
							ResultSet thisStruct;
							
							//For each item in our chest
							for(int i = 0; i < 10; i++ ) {
								i++;
								System.out.println("pre: " + i);
								//If we have an item in this chest slot
								if ( structs.getInt("struc" + i) != -1 ) {
									System.out.println("Comparing item " + i);
									//Create new response object
									PropStructsResponse thisResponse = new PropStructsResponse();
									thisResponse.message = "Succesfully grabbed structures.";
									thisResponse.success = true;
									thisResponse.propertyID = structs.getString("propertyID");
									
									//Grab the info on this structure
									thisStruct = stmt1.executeQuery("select * from structures where structureID = 'struc" + i + "'");
									
									if ( thisStruct.next() ) {
										System.out.println("added: " + thisStruct.getString("name"));
										AbstractStructure temp = new AbstractStructure();
										temp.name = thisStruct.getString("name");
										temp.imageID = thisStruct.getInt("imageID");
										temp.description = thisStruct.getString("description");
										temp.type = thisStruct.getString("type");
										temp.x = -1;
										temp.y = -1;
										temp.level = 1;
										temp.cost = thisStruct.getInt("price");
										temp.curHealth = -1;
										temp.maxHealth = thisStruct.getInt("maxHealth");
										temp.defense = thisStruct.getInt("defense");
										temp.viewRadius = thisStruct.getInt("viewRadius");
										temp.enabled = false;
										thisResponse.struct = temp;
									}
									
									System.out.println("finished" + i);
									response.add(thisResponse);
								}
								i--;
							}
						}
					}
				}
			}

			// Close connections
			stmt1.close();
			return response;
		} catch (SQLException e) {
			PropStructsResponse thisResponse = new PropStructsResponse();
			System.out.println(e.getMessage()
					+ " occured while trying to retrieve " + psr.user + "'s structs for property");
			// e.printStackTrace();
			thisResponse.message = e.getMessage();
			thisResponse.success = false;
			if (response.size() == 0 ) {
				response.add(thisResponse);	
			}
			return response;
		}
	}

	/**
	 * Register the user to the system.
	 * 
	 * @param reggy
	 * @return
	 */
	public PropertyPurchaseResponse propertyPurchase(
			PropertyPurchaseRequest prop) {
		// Creating a statement
		Statement stmt1;

		PropertyPurchaseResponse response = new PropertyPurchaseResponse();

		try {
			stmt1 = con.createStatement();

			// compare user and token
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ prop.username + "' AND token = '" + prop.token
							+ "'");

			// If the credentials matched
			if (!isValid.next()) {
				// Close connection
				stmt1.close();
				response.message = "Invalid Token. You are not logged in.";
				response.success = false;
				return response;
			} else {

				// Create the house
				PreparedStatement st = con
						.prepareStatement("INSERT INTO houses(topX, topY, currentHealth, maxHealth, computerLevel, computerCapacity, computerCurrent, safeLevel, safeCapacity, safeCurrent) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				st.setInt(1, 0);
				st.setInt(2, 0);
				st.setInt(3, 0);
				st.setInt(4, 100);
				st.setInt(5, 1);
				st.setInt(6, 100);
				st.setInt(7, 0);
				st.setInt(8, 1);
				st.setInt(9, 100);
				st.setInt(10, 0);
				st.execute();

				// NEED TO GET HOUSE ID
				// NEED TO CHANGE PROPERTY TO STORE AT ADDRESS

				// Create the property
				PreparedStatement st1 = con
						.prepareStatement("INSERT INTO properties(houseID, owner, numResidents, maxResidents, locLat, locLon, height, width) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
				st1.setInt(1, 0);
				st1.setString(2, prop.username);
				st1.setInt(3, 1);
				st1.setInt(4, 10);
				st1.setString(5, "" + prop.lat);
				st1.setString(6, "" + prop.lon);
				st1.setInt(7, 10);
				st1.setInt(8, 10);
				st1.execute();
			}

			// Close connections
			stmt1.close();

			response.message = "Property purchased succesfully";
			response.success = true;
			return response;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()
					+ " occured while trying to purchase property for "
					+ prop.username);
			// e.printStackTrace();
			response.message = e.getMessage();
			response.success = false;
			return response;
		}
	}

	/**
	 * Generate a random token when user logs in. Used to authenticate any
	 * future requests from that user with the server
	 * 
	 * @return The token generate when the user logs in
	 */
	public String generateToken() {
		String possible = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-=!@#$%^&*()_+~`/.,;[]<>?:{}";
		String token = "";

		int rand;

		for (int i = 0; i < 20; i++) {
			rand = (int) ((Math.random() * 10000) % possible.length());
			token += possible.charAt(rand);
		}

		return token;
	}

	/**
	 * Close our connection to the server
	 * 
	 * @throws SQLException
	 */
	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection.");
			// e.printStackTrace();
		}
	}

	/**
	 * Connect to the MySql server
	 */
	public boolean connect() {
		// Try and connect
		try {
			// Connect and print out successfully
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			System.out.println("*** Connected to Database ***");
			return true;
		}

		// Some error occured.
		catch (SQLException E) {
			// System.out.println("SQLException: " + E.getMessage());
			// System.out.println("SQLState: " + E.getSQLState());
			// System.out.println("VendorError: " + E.getErrorCode());
			System.out.println("*** Unable to Connect ***");
			return false;
		}
	}
	
	/**
	 * this is used to process putting items into the inventory
	 */
	public InventoryChangeResponse putInv(InventoryChangeRequest change) {
		Statement stmt1;
		InventoryChangeResponse response = new InventoryChangeResponse();
		
		try {
			stmt1 = con.createStatement();
			
			// compare user and token
						ResultSet isValid = stmt1
								.executeQuery("select * from users where username = '"
										+ change.user + "' AND token = '"
										+ change.token + "'");

						// If the credentials matched
						if (!isValid.next()) {
							// Close connection
							stmt1.close();
							response.message = "Invalid Token. You are not logged in.";
							response.success = false;
							return response;
						} else {
							//TODO
							PreparedStatement st = con.prepareStatement("UPDATE inventories SET struc? = ? WHERE username = '"
								+ change.user
								+ "' AND token = '"
								+ change.token + "'");
							st.setInt(1, change.id);
							st.execute();
						}

						// Close connections
						stmt1.close();

						response.message = "Chest placed succesfully";
						response.success = true;
						return response;
			
		} catch (SQLException e){
			System.out.println(e.getErrorCode()
					+ " occured while trying to purchase property for "
					+ change.user);
			// e.printStackTrace();
			response.message = e.getMessage();
			response.success = false;
			return response;
		}
	}
	
	
	/**
	 * This is used to process the change chest request through the server
	 */
	public ChestChangeResponse putChest(ChestChangeRequest change) {
		Statement stmt1;
		ChestChangeResponse response = new ChestChangeResponse();
		
		try {
			stmt1 = con.createStatement();
			
			// compare user and token
						ResultSet isValid = stmt1
								.executeQuery("select * from users where username = '"
										+ change.user + "' AND token = '"
										+ change.token + "'");

						// If the credentials matched
						if (!isValid.next()) {
							// Close connection
							stmt1.close();
							response.message = "Invalid Token. You are not logged in.";
							response.success = false;
							return response;
						} else {
							//TODO
							PreparedStatement st = con.prepareStatement("UPDATE chests SET struc? = ? WHERE username = '"
								+ change.user
								+ "' AND token = '"
								+ change.token + "'");
							st.setInt(1, change.id);
							st.execute();
						}

						// Close connections
						stmt1.close();

						response.message = "Chest placed succesfully";
						response.success = true;
						return response;
			
		} catch (SQLException e){
			System.out.println(e.getErrorCode()
					+ " occured while trying to purchase property for "
					+ change.user);
			// e.printStackTrace();
			response.message = e.getMessage();
			response.success = false;
			return response;
		}
	}

	/**
	 * This is used to process the update stats request through the server
	 */
	public UpdateStatsResponse updateStats(UpdateStatsRequest update) {
		Statement stmt1;

		UpdateStatsResponse response = new UpdateStatsResponse();

		try {
			stmt1 = con.createStatement();

			// compare user and token
			ResultSet isValid = stmt1
					.executeQuery("select * from users where username = '"
							+ update.username + "' AND token = '"
							+ update.token + "'");

			// If the credentials matched
			if (!isValid.next()) {
				// Close connection
				stmt1.close();
				response.message = "Invalid Token. You are not logged in.";
				response.success = false;
				return response;
			} else {

				// Create the house
				PreparedStatement st = con.prepareStatement("UPDATE characters SET maxHealth = ?, curHealth = ?, attack = ?, armor = ?, money = ?, armor0 = ?, armor1 = ?, armor2 = ?, armor3 = ?, weapon0 = ?, weapon1 = ?, weapon2 = ?, weapon3 = ?, wins = ?, loses = ?, kills = ?, deaths = ?, guild = ?, lat = ?, lon = ?, speed = ?, stealth = ?, tech = ?, level = ?, exp = ? WHERE username = '"
								+ update.username
								+ "' AND token = '"
								+ update.token + "'");
				st.setInt(0, update.maxHealth);
				st.setInt(1, update.curHealth);
				st.setInt(2, update.attack);
				st.setInt(3, update.armor);
				st.setInt(4, update.money);
				st.setInt(5, update.armor0);
				st.setInt(6, update.armor1);
				st.setInt(7, update.armor2);
				st.setInt(8, update.armor3);
				st.setInt(9, update.weapon0);
				st.setInt(10, update.weapon1);
				st.setInt(11, update.weapon2);
				st.setInt(12, update.weapon3);
				st.setInt(13, update.wins);
				st.setInt(14, update.loses);
				st.setInt(15, update.kills);
				st.setInt(16, update.deaths);
				st.setString(17, update.guild);
				st.setDouble(18, update.lat);
				st.setDouble(19, update.lon);
				st.setInt(20, update.speed);
				st.setInt(21, update.stealth);
				st.setInt(22, update.tech);
				st.setInt(23, update.level);
				st.setInt(24, update.exp);		
				
				st.execute();
			}

			// Close connections
			stmt1.close();

			response.message = "Property purchased succesfully";
			response.success = true;
			return response;
		} catch (SQLException e) {
			System.out.println(e.getErrorCode()
					+ " occured while trying to purchase property for "
					+ update.username);
			// e.printStackTrace();
			response.message = e.getMessage();
			response.success = false;
			return response;
		}

	}

}
