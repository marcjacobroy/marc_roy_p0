package FlashCard.main;

import java.util.Scanner;


import org.apache.log4j.Logger;

import FlashCard.pojos.User;

public class UserRegistrationDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static boolean registerUser() {
		log.info("Starting user registration");
		User.UserType userType = getUserType();
		String userName = getName();
		return addUser(userType, userName);
	}
	
	private static User.UserType getUserType(){
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
				System.out.println("Au revoir!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
		} while (!"0".equals(userInputUserType));
		
		return userType;
	}
	private static String getName() {
		String userName;
		boolean nameTaken;
		
		do {
			System.out.println("What is your username?");
			userName = scan.nextLine();
			
			nameTaken = FlashCardDriver.studentsCache.containsStudentWithName(userName); 
			if (nameTaken) {
				System.out.println("Username already taken. Please choose a different one.");
			}
			
		} while (!"".equals(userName) && nameTaken);
		
		return userName;
	}
	
	private static boolean addUser(User.UserType userType, String userName) {
		if (userType == (User.UserType.STUDENT)){
			FlashCardDriver.studentsCache.createStudent(userName);
		} else if (userType == User.UserType.INSTRUCTOR) {
			FlashCardDriver.instructorsCache.createInstructor(userName);
		}
		return true; 
	}
}
