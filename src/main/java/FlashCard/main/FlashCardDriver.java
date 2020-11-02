package FlashCard.main;

import java.util.Scanner;
import java.util.Arrays;
import java.util.List;


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
			System.out.println("[3] View all registered students");
			System.out.println("[4] View all registered instructors");
			System.out.println("[0] Exit");
			
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
					if (studentsCache.containsStudentWithId(userId)){
						StudentDriver studentDriver = new StudentDriver(studentsCache.getStudentWithId(userId));
						studentDriver.studentActions();
					} else if (instructorsCache.containsInstructorWithId(userId)){
						InstructorDriver instructorDriver = new InstructorDriver(instructorsCache.getInstructorWithId(userId));
						instructorDriver.instructorActions();
					} else {
						System.out.println("Something went wrong when trying to access your data. We're sorry.");
					}
				} else {
					System.out.println("Login failed.");
				}
				break;
			case "3":
				List<String> studentList = studentsCache.toStringList();
				System.out.println(Arrays.toString(studentList.toArray()));
				break;
			case "4":
				List<String> instructorList = instructorsCache.toStringList();
				System.out.println(Arrays.toString(instructorList.toArray()));
				break;
			case "0":
				System.out.println("Auf Wiedersehen");
				break;
			default:
				System.out.println("Invalid choice. Please select a number 0-4.");
				break;
			}
	
		} while (!"0".equals(userInput));
	}
}
