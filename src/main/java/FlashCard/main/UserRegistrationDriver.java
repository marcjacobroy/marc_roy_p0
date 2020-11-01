package FlashCard.main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import FlashCard.pojos.User;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;

public class UserRegistrationDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static boolean containsName(final List<User> list, final String name) {
		return list.stream().filter(p -> p.getUserName().equals(name)).findFirst().isPresent();
	}
	
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
		
		List<User> usersList = FlashCardDriver.getUsers();
		
		do {
			System.out.println("What is your username?");
			userName = scan.nextLine();
			
			nameTaken = containsName(usersList, userName); 
			if (nameTaken) {
				System.out.println("Username already taken. Please choose a different one.");
			}
			
		} while (!"".equals(userName) && nameTaken);
		
		return userName;
	}
	
	private static boolean addUser(User.UserType userType, String userName) {
		if (userType == (User.UserType.STUDENT)){
			Student student = new Student(userName);
			FlashCardDriver.addStudent(student);
		} else if (userType == User.UserType.INSTRUCTOR) {
			Instructor instructor = new Instructor(userName);
			FlashCardDriver.addInstructor(instructor);
		}
		return true; 
	}
}
