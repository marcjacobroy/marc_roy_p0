package FlashCard.main;

import java.util.Scanner;

import org.apache.log4j.Logger;

import FlashCard.service.StudentServiceImpl;
import FlashCard.service.InstructorServiceImpl;
import FlashCard.service.CourseServiceImpl;
import FlashCard.service.StudySetServiceImpl;

public class FlashCardDriver {
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static StudentServiceImpl studentsCache = new StudentServiceImpl();
	
	public static InstructorServiceImpl instructorsCache = new InstructorServiceImpl();
	
	public static CourseServiceImpl coursesCache = new CourseServiceImpl();
	
	public static StudySetServiceImpl studySetsCache = new StudySetServiceImpl();

	public static void main(String args[]) {
		log.info("Program has started");
	
		String userInput;
	
		do {
			System.out.println("Welcome to FlashCard! Please choose one of the following options:");
			System.out.println("[1] Register as new user");
			System.out.println("[2] Log in");
			System.out.println("[0] exit");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
	
			case "1":
				if (UserRegistrationDriver.registerUser()) {
					System.out.println("Registration successful!");
				} else {
					System.out.println("Registration failed.");
				}
				break;
	
			case "2":
				int userId = UserLoginDriver.logInUser();
				if (userId != -1) {
					System.out.println("Login successful!");
				} else {
					System.out.println("Login failed.");
				}
				break;
			case "0":
				System.out.println("Auf Wiedersehen");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
	
		} while (!"0".equals(userInput));
	}
}
