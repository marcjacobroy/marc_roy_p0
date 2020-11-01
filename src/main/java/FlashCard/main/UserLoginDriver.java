package FlashCard.main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import FlashCard.pojos.User;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;

public class UserLoginDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	public static boolean containsName(final List<User> list, final String name) {
		return list.stream().filter(p -> p.getUserName().equals(name)).findFirst().isPresent();
	}
	
	public static boolean logInUser() {
		
		log.info("Starting user login.");
		
		String userName;
		boolean userExists; 
		
		do {
			System.out.println("Please enter your username to log in.");
			userName = scan.nextLine();
			List<User> usersList = FlashCardDriver.getUsers();
			userExists = containsName(usersList, userName);
			if (userExists) {
				return true;
			} else {
				System.out.println("Username " + userName + " is not a registered user. Please try again.");
			}
			
		} while (!"".equals(userName));
		
		return false; 
	}
}
