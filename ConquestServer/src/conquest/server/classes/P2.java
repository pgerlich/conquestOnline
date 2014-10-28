//package p2;
//
////Importing the sql library.
//import java.sql.*;
//
//public class P2 {
//	
//	
//	public static void main(String[] args){
//		//Loading the driver.
//		try {
//				Class.forName("com.mysql.jdbc.Driver");
//		}
//		catch (Exception E){
//			System.err.println("Unable to laod driver");
//			E.printStackTrace();
//		}
//		
//		//Establishing a connection.
//		try {
//			Connection conn1;
//			String dbUrl = "jdbc:mysql://csdb.cs.iastate.edu:3306/pgerlichDB";
//			String user = "pgerlich";
//			String password = "pgerlich-89";
//			conn1 = DriverManager.getConnection(dbUrl, user, password);
//			System.out.println("*** Connected to Database ***");
//			
//			//Creating a statement
//			Statement stmt1 = conn1.createStatement();
//			Statement stmt2 = conn1.createStatement();
//			Statement stmt3 = conn1.createStatement();
//			Statement stmt4 = conn1.createStatement();
//			
//			//Some statements to execute
//			ResultSet rs1 = stmt1.executeQuery("select e.StudentID, e.Grade" + " " + "from Enrollment e");
//			ResultSet rs2 = stmt2.executeQuery("select s.StudentID, s.GPA, s.CreditHours, s.Classification" + " " + "from Student s" + " " + "order by s.GPA DESC");
//			PreparedStatement set = conn1.prepareStatement("Update Student" + " " + "set Classification = ? , CreditHours = ?, GPA = ?" + " " + "where StudentID = ?");
//			
//			//Process result set and update
//			int credits = 0, count = 0, nDisp = 1;
//			String sClass = "", SID = "", cGra = "",sName = "", mName ="", MID ="", PID = "";
//			float GPA = 0, cGPA;
//			//Go through all the students
//			while (rs2.next()){
//				SID = rs2.getString(1); //The current student ID
//				credits = rs2.getInt(3); // Number of credits before any updating.
//				GPA = (float) rs2.getDouble(2); //GPA
//				sClass = rs2.getString(4); //Current classification to begin with.
//				//Go through all the Current Classes
//				while(rs1.next()){
//					if (rs1.getString(1).equalsIgnoreCase(rs2.getString(1))){
//						cGra = rs1.getString(2);
//						cGPA = getGrade(cGra) * 3; //GPA adjustment for that class
//						GPA = GPA * credits;
//						credits += 3; // Update credits 
//						GPA = (GPA + cGPA) / credits; //Update GPA
//						GPA = (float) (Math.round(GPA*100)/100.0);
//						sClass = getClass(credits); // Update classification
//					}
//				}
//				set.setString(1, sClass); // Set Classification update
//				set.setInt(2, credits); // Set Credits update
//				set.setDouble(3, GPA); // Set GPA update
//				set.setString(4, SID); // Make sure it's the correct student ID
//				set.executeUpdate(); // Execute update query
//				System.out.println("--------Student " + count + "--------");
//				System.out.println("Student ID: " + SID + " GPA: " + GPA  + " Credits: " + credits + " Classification: " + sClass); //This should update
//				count++;
//			}
//			//Close connections
//			stmt1.close();
//			stmt2.close();
//			
//			//We have this down here so it grabs the most up-to-date information
//			ResultSet rs3 = stmt3.executeQuery("select s.StudentID, s.GPA, s.MentorID" + " " + "from Student s" + " " + "where s.Classification = 'Senior' " + " " + "order by s.GPA DESC");
//			ResultSet rs4 = stmt4.executeQuery("select p.ID, p.Name" + " " + "from Person p");
//			
//			//Now we will iterate through the seniors that are already ordered by highest to lowest GPA and print out 
//			//This checks each Senior
//			cGPA = 0;
//			count = 0;
//			while (rs3.next()){
//				count++;
//				if (nDisp < 6){
//					SID = rs3.getString(1);
//					MID = rs3.getString(3);
//					GPA = (float) rs3.getDouble(2);
//					GPA = (float) (Math.round(GPA*100)/100.0);
//					//Finds their mentor's name as well as their name from the person table
//					while(rs4.next()){
//						PID = rs4.getString(1);
//						if (PID.equalsIgnoreCase(SID)){
//							sName = rs4.getString(2);
//						}
//						if (PID.equalsIgnoreCase(MID)){
//							mName = rs4.getString(2);
//						}
//					}
//					rs4.first();
//					// Checks if we are the the last person to display. IF so, we check the next persons GPA, and if it is the same, we essentially will include them.
//					if (nDisp == 4)
//						cGPA = GPA;
//						if (cGPA == GPA && nDisp == 5){
//							nDisp --;
//						} else if (cGPA != GPA && nDisp == 5){
//							break;
//						} else {
//							nDisp ++;
//						}
//					System.out.println("--------Student " + count + "--------");
//					System.out.println("Student Name: " + sName);
//					System.out.println("Mentor Name: " + mName);
//					System.out.println("GPA: " + GPA);
//					
//				}
//			}
//			stmt3.close();
//			stmt4.close();
//			conn1.close();
//		}
//		catch (SQLException E){
//			System.out.println("SQLException: " + E.getMessage());
//			System.out.println("SQLState: " + E.getSQLState());
//			System.out.println("VendorError: " + E.getErrorCode());
//		}
//		}
//	
//	public static float getGrade(String cGra){
//		float cGPA = (float) 0.0;
//		cGra.trim();
//		
//		switch(cGra){
//		case "A": cGPA = (float) 4.0;
//			break;
//		case "A-": cGPA = (float) 3.66;
//		break;
//		case "B+": cGPA = (float) 3.33;
//		break;
//		case "B": cGPA = (float) 3.0;
//		break;
//		case "B-": cGPA = (float) 2.66;
//		break;
//		case "C+": cGPA = (float) 2.33;
//		break;
//		case "C": cGPA = (float) 2.0;
//		break;
//		case "C-": cGPA = (float) 1.66;
//		break;
//		case "D+": cGPA = (float) 1.33;
//		break;
//		case "D": cGPA = (float) 1.0;
//		break;
//		case "F": cGPA = (float) 0.0;
//		break;
//		}
//		return cGPA;
// 	}
//	
//	public static String getClass(int credits){
//		String sClass = "";
//		
//		if (credits >= 0 && credits < 30)
//			sClass = "Freshman";
//		if (credits >= 30 && credits < 60)
//			sClass = "Sophomore";
//		if (credits >= 60 && credits < 90)
//			sClass = "Junior";
//		if (credits >= 90)
//			sClass = "Senior";
//		
//		return sClass;
// 	}
//
//}
