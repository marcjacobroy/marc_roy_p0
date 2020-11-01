package FlashCard.main;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import FlashCard.pojos.Card;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;
import FlashCard.pojos.Course;
import FlashCard.pojos.StudySet;
import FlashCard.pojos.Entry;
import FlashCard.pojos.User;

public class FlashCardDriver {
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	private static List<StudySet> studySets = new ArrayList<StudySet>();
	
	private static List<Course> courses = new ArrayList<Course>();
	
	private static List<User> users = new ArrayList<User>();
	
	private static List<Student> students = new ArrayList<Student>();
	
	private static List<Instructor> instructors = new ArrayList<Instructor>();
	
	public static void addStudent(Student student) {
		students.add(student);
		User user = student;
		users.add(user);
	}
	
	public static void addInstructor(Instructor instructor) {
		instructors.add(instructor);
		User user = instructor;
		users.add(user);
	}
	
	public static List<Student> getStudents() {
		return students;
	}

	public static void setStudents(List<Student> students) {
		FlashCardDriver.students = students;
	}

	public static List<Instructor> getInstructors() {
		return instructors;
	}

	public static void setInstructors(List<Instructor> instructors) {
		FlashCardDriver.instructors = instructors;
	}

	public static List<Course> getCourses() {
		return courses;
	}

	public static void setCourses(List<Course> courses) {
		FlashCardDriver.courses = courses;
	}

	public static List<StudySet> getStudySets() {
		return studySets;
	}

	public static void setStudySets(List<StudySet> studySets) {
		FlashCardDriver.studySets = studySets;
	}

	public static List<User> getUsers() {
		return users;
	}

	public static void setUsers(List<User> users) {
		FlashCardDriver.users = users;
	}

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
				if (UserLoginDriver.logInUser()) {
					System.out.println("Login successful!");
				} else {
					System.out.println("Login failed");
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
