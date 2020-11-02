package FlashCard.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import FlashCard.pojos.User;
import FlashCard.pojos.User.UserType;

public class UserLoginDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static int logInUser() {
		
		log.info("Starting user login.");
		
		UserType userType = getUserType();
		if (userType == null) {
			return -1;
		}
		String userName = getName(userType);
		if (userName == null){
			return -1;
		}
		switch (userType) {
		case STUDENT:
			return FlashCardDriver.studentsCache.getStudentWithUserName(userName).getUserId();
		case INSTRUCTOR:
			return FlashCardDriver.instructorsCache.getInstructorWithUserName(userName).getUserId();
		default:
			return -1;
		}
	}
	
	private static UserType getUserType(){
		log.info("Getting user type for login.");
		String userInputUserType;
		User.UserType userType = null; 
		
		do {
			System.out.println("Are you a student or an instructor?");
			System.out.println("[1] Student.");
			System.out.println("[2] Instructor.");
			System.out.println("[0] Exit.");
		
			userInputUserType = scan.nextLine();
			
			switch (userInputUserType) {
			case "1":
				userType = UserType.STUDENT;
				return userType;
			case "2":
				userType = UserType.INSTRUCTOR;
				return userType;
			case "0":
				System.out.println("ArrivederLa!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
		} while (!"0".equals(userInputUserType));
		
		return userType;
	}
	private static String getName(UserType userType) {
		log.info("Getting username for login.");
		String userName;
		boolean nameExists = false;
		
		do {
			System.out.println("What is your username? Or type [0] to exit.");
			userName = scan.nextLine();
			if (userName.equals("0")) {
				return null;
			}
			if (userType == UserType.STUDENT) {
				nameExists = FlashCardDriver.studentsCache.containsStudentWithName(userName); 
			} else if (userType == UserType.INSTRUCTOR){
				nameExists = FlashCardDriver.instructorsCache.containsInstructorWithName(userName);
			} else {
				System.out.println("Something went wrong when logging you in. Sorry!");
			}
			if (nameExists) {
				return userName;
			} else {
				System.out.println("Username " + userName + " is not a registered " + userType + ". Please try again.");
			}
			
		} while (!"".equals(userName) && !nameExists);
		
		return userName;
	}
}
