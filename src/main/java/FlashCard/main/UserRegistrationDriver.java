package FlashCard.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import FlashCard.pojos.User;

// Handles registration requests from users 
public class UserRegistrationDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static boolean registerUser() {
		log.info("Starting user registration.");
		User.UserType userType = getUserType();
		if (userType == null) {
			return false;
		}
		String userName = getName();
		if (userName == null) {
			return false;
		}
		return addUser(userType, userName);
	}
	
	// Helper function to retrieve usertype from user attempting registration 
	private static User.UserType getUserType(){
		
		log.info("Requesting user type for registration.");
		String userInputUserType;
		User.UserType userType = null; 
		
		do {
			System.out.println("Would you like to register as a student or as an instructor?");
			System.out.println("[1] Register as student.");
			System.out.println("[2] Register as instructor.");
			System.out.println("[0] Exit.");
		
			userInputUserType = scan.nextLine();
			
			switch (userInputUserType) {
			case "1":
				userType = User.UserType.STUDENT;
				return userType;
			case "2":
				userType = User.UserType.INSTRUCTOR;
				return userType;
			case "0":
				return null;
			default:
				System.out.println("Invalid choice. Please select either '1' or '2' or '0'.");
				break;
			}
		} while (userType == null);
		
		return userType;
	}
	
	// Helper function to retrieve desired username from user attempting registration
	private static String getName() {
		log.info("Requesting username for registration.");
		String userName;
		boolean nameTaken;
		
		do {
			System.out.println("Please choose your username. Or type [0] to exit.");
			userName = scan.nextLine();
			if (userName.equals("0")) {
				return null;
			}
			nameTaken = FlashCardDriver.studentsCache.containsStudentWithName(userName); 
			if (nameTaken) {
				System.out.println("Username already taken. Please choose a different one.");
			}
			
		} while (!"".equals(userName) && nameTaken);
		
		return userName;
	}
	
	// Add the user to the appropriate cache in storage 
	private static boolean addUser(User.UserType userType, String userName) {
		log.info("Adding user info to cache.");
		if (userType == (User.UserType.STUDENT)){
			FlashCardDriver.studentsCache.createStudent(userName);
			return true; 
		} else if (userType == User.UserType.INSTRUCTOR) {
			FlashCardDriver.instructorsCache.createInstructor(userName);
			return true; 
		}
		return false;
	}
}
