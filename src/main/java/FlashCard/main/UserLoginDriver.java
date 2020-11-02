package FlashCard.main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

import FlashCard.pojos.User;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;
import FlashCard.pojos.User.UserType;

public class UserLoginDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static boolean containsName(final List<User> list, final String name) {
		return list.stream().filter(p -> p.getUserName().equals(name)).findFirst().isPresent();
	}
	
	public static boolean containsStudentName(final List<Student> list, final String name) {
		return list.stream().filter(p -> p.getUserName().equals(name)).findFirst().isPresent();
	}
	
	public static boolean containsInstructorName(final List<Instructor> list, final String name) {
		return list.stream().filter(p -> p.getUserName().equals(name)).findFirst().isPresent();
	}
	
	public static User getUserWithName(final List<User> list, final String name) {
		if (containsName(list, name)) {
			return list.stream().filter(p -> p.getUserName().equals(name)).collect(Collectors.toList()).get(0);
		} else {
			throw new IllegalArgumentException("No such user exists.");
		}
		
	}
	
	public static Student getStudentWithName(final List<Student> list, final String name) {
		if (containsStudentName(list, name)) {
			return list.stream().filter(p -> p.getUserName().equals(name)).collect(Collectors.toList()).get(0);
		} else {
			throw new IllegalArgumentException("No such user exists.");
		}
		
	}
	
	public static Instructor getInstructorWithName(final List<Instructor> list, final String name) {
		if (containsInstructorName(list, name)) {
			return list.stream().filter(p -> p.getUserName().equals(name)).collect(Collectors.toList()).get(0);
		} else {
			throw new IllegalArgumentException("No such user exists.");
		}
		
	}
	
	public static int logInUser() {
		
		log.info("Starting user login.");
		
		UserType userType = null;
		String userInputUserType;
		
		do {
			System.out.println("Would you like to log in as a student or as an instructor?");
			System.out.println("[1] Log in as student.");
			System.out.println("[2] Log in as instructor.");
			
			userInputUserType = scan.nextLine();
			
			switch(userInputUserType) {
			case "1":
				userType = UserType.STUDENT;
				break;
			case "2":
				userType = UserType.INSTRUCTOR;
				break;
			default:
				System.out.println("Please type either [1] or [2].");
			}
		} while (userType == null);
		
		String userName;
		boolean userExists; 
		
		do {
			System.out.println("Please enter your username to log in.");
			userName = scan.nextLine();
			
			if (userType == UserType.INSTRUCTOR) {
				List<Instructor> instructorsList = FlashCardDriver.getInstructors();
				if (containsInstructorName(instructorsList, userName)) {
					Instructor instructor = getInstructorWithName(instructorsList, userName);
					return instructor.getUserId();
				} else {
					System.out.println("Username " + userName + " is not a registered instructor. Please try again.");
				}
			} else {
				List<Student> studentsList = FlashCardDriver.getStudents();
				if (containsStudentName(studentsList, userName)) {
					Student student = getStudentWithName(studentsList, userName);
					return student.getUserId();
				} else {
					System.out.println("Username " + userName + " is not a registered student. Please try again.");
				}
			}
		} while (!"".equals(userName));
	return -1;
	}
}
