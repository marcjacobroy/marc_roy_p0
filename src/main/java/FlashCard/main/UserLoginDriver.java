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
		String userName = getName(userType);
		switch (userType) {
		case STUDENT:
			return FlashCardDriver.studentsCache.getStudentWithUserName(userName).getUserId();
		case INSTRUCTOR:
			return FlashCardDriver.instructorsCache.getInstructorWithUserName(userName).getUserId();
		default:
			return -1;
		}
	}
	
	private static User.UserType getUserType(){
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
				userType = User.UserType.STUDENT;
				return userType;
			case "2":
				userType = User.UserType.INSTRUCTOR;
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
	private static String getName(User.UserType userType) {
		String userName;
		boolean nameExists = false;
		
		do {
			System.out.println("What is your username?");
			userName = scan.nextLine();
			
			if (userType == User.UserType.STUDENT) {
				nameExists = FlashCardDriver.studentsCache.containsStudentWithName(userName); 
			} else {
				nameExists = FlashCardDriver.instructorsCache.containsInstructorWithName(userName);
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
