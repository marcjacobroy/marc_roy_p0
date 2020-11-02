package FlashCard.main;


import java.util.InputMismatchException;
import java.util.Scanner;


import org.apache.log4j.Logger;

import FlashCard.pojos.Card;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;
import FlashCard.pojos.Course;
import FlashCard.pojos.StudySet;
import FlashCard.pojos.Entry;
import FlashCard.pojos.Entry.Language;

// Possible actions once logged in as instructor.

public class InstructorDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	private Instructor instructor;

	public InstructorDriver(Instructor instructor) {
		super();
		this.instructor = instructor;
	}
	
	// Instructor options
	public void instructorActions() {
		log.info("Selecting an instructor action.");
		String userInput;
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] Create a course.");
			System.out.println("[2] Create a study set.");
			System.out.println("[3] Add a study set to a course.");
			System.out.println("[4] Assign a student to a course.");
			System.out.println("[0] Logout.");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				if (createCourse()) {
					System.out.println("Successfully created course!");
				} else {
					System.out.println("Something went wrong. We're sorry.");
				}
				break;
			case "2":
				if (createStudySet()) {
					System.out.println("Successfully created study set!");
				} else {
					System.out.println("Something went wrong. We're sorry.");
				}
				break;
			case "3":
				if (assignStudySetToCourse()) {
					System.out.println("Successfully assigned study set to course!");
				} else {
					System.out.println("Something went wrong. We're sorry.");
				}
				break;
			case "4":
				if (assignStudentToCourse()) {
					System.out.println("Successfully assigned student to course!");
				} else {
					System.out.println("Something went wrong. We're sorry.");
				}
				break;
			case "0":
				log.info("Instructor is leaving.");
				System.out.println("Tchau!");
				break;
			default:
				System.out.println("string = " + userInput);
				System.out.println("Invalid choice. Please select either '1', '2', '3', '4' or '0'.");
				break;
			}
			
		} while (!"0".equals(userInput));
		
	}
	
	// Allow current instructor to create a course with specified name. 
	private boolean createCourse() {
		
		log.info("Instructor would like to create course.");
		String courseName;
		
		System.out.println("What is the course name?");
		
		do {
			courseName = scan.nextLine();
		} while ("".equals(courseName));
		
		Course course = FlashCardDriver.coursesCache.createCourse(courseName, this.instructor);
		instructor.addCourse(course);
		log.info("Created course.");
		System.out.println("Course id is " + course.getCourseId());
		return true;
	}
	
	// Choose language for set created by this instructor.
	private static Language pickLanguage() {
		
		log.info("Instructor is choosing a language");
		String userInputLanguage;
		do {
			System.out.println("Pick a language!");
			System.out.println("[1] English");
			System.out.println("[2] German");
			System.out.println("[3] French");
			System.out.println("[4] Spanish");
			System.out.println("[5] Portuguese");
			System.out.println("[6] Italian");
			System.out.println("[7] Russian");
			System.out.println("[8] Arabic");
			
			userInputLanguage = scan.nextLine();
			
			switch(userInputLanguage) {
			case "1":
				return Language.ENGLISH;
			case "2":
				return Language.GERMAN;
			case "3": 
				return Language.FRENCH;
			case "4": 
				return Language.SPANISH;
			case "5":
				return Language.PORTUGUESE;
			case "6":
				return Language.ITALIAN;
			case "7": 
				return Language.RUSSIAN;
			case "8": 
				return Language.ARABIC;
			default:
				System.out.println("Invalid choice. Please choose a number from 1 to 8.");
			}
	} while ("".equals(userInputLanguage));
		
	return null;
	}
	
	// This instructor creates a study set
	private boolean createStudySet(){
		
		log.info("Instructor would like to create study set.");
		
		String userInputStudySetName;
		System.out.println("What is the study set name?");
		do {
			userInputStudySetName = scan.nextLine();
		} while ("".equals(userInputStudySetName));
		
		StudySet studySet = FlashCardDriver.studySetsCache.createStudySet(userInputStudySetName);
		Language termLanguage;
		Language defLanguage;
		
		System.out.println("What is the language of the terms?");
		termLanguage = pickLanguage();
		System.out.println("What is the language of the definitions?");
		defLanguage = pickLanguage();
		boolean atLeastOneCard = false;
		
		String addCardUserInput;
		do {
			System.out.println("Please add at least one card to the new study set. Type [0] when done.");
			System.out.println("[1] Add new card.");
			System.out.println("[0] Done.");
			
			addCardUserInput = scan.nextLine();
			
			switch(addCardUserInput) {
			case "1":
				Card card = createCard(termLanguage, defLanguage);
				studySet.addCard(card);
				atLeastOneCard = true;
				break;
			case "0":
				if (atLeastOneCard) {
					System.out.println("Completing set...");
				} 
				break;
			default:
				System.out.println("Invalid choice. Please type [1] or [0].");
			}
		} while (!"0".equals(addCardUserInput) || !atLeastOneCard);
		System.out.println("Set id is " + studySet.getStudySetId());
		
		return true;
		
	}
	
	// Helper function allows instructor to create card to later be added to set. 
	private Card createCard(Language termLanguage, Language defLanguage) {
		
		log.info("Instructor is creating card.");
		System.out.println("Please enter term:");
		String termText = scan.nextLine();
		Entry term = new Entry(termText, termLanguage);
		System.out.println("Please enter definition:");
		String defText = scan.nextLine();
		Entry def = new Entry(defText, defLanguage);
		Card card = new Card(term, def);
		return card;
	}
	
	// Helper function allows instructor to specify course for assigning a study set or student. 
	private Course getCourse() {
		
		log.info("Instructor is choosing course.");
		boolean validCourse = false;
		Course course = null;
		int courseId = -1;
		do {
			do {
				System.out.println("Please enter course id.");
				try {
					courseId = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("Please type a valid int.");
					courseId = -1;
					log.error("Encountered InputMismatchException");
				} finally {
					scan.nextLine();
				}
			} while(courseId == -1);
			try {
				course = FlashCardDriver.coursesCache.getCourseWithId(courseId);
				validCourse = this.instructor.isInstructor(course);
				if(!validCourse) {
					System.out.println("Invalid course. Are you sure you're the instructor?");
				}
			} catch(IllegalArgumentException e) {
				System.out.println("This course doesn't exist.");
				log.error("Encountered IlllegalArgumentException");
			}
		} while(!validCourse);
		return course;
	}
	
	// Helper function allows instructor to select study set for assigning to a course. 
	private StudySet getStudySet() {
		
			StudySet studySet = null;
			boolean foundSet = false;
			int studySetId = -1;
			do {
				System.out.println("Please enter study set Id");
				do {
					try {
						studySetId = scan.nextInt();
					} catch(InputMismatchException e) {
						System.out.println("Please type a valid int.");
						studySetId = -1;
						log.error("Encountered InputMismatchException");
					} finally {
						scan.nextLine();
					}
				} while(studySetId == -1);
				try {
					studySet = FlashCardDriver.studySetsCache.getStudySetWithId(studySetId);
					foundSet = true;
				} catch(IllegalArgumentException e) {
					System.out.println("Study set doesn't exist.");
					log.error("Encountered IllegalArgumentException");
				}
			} while (!foundSet);
			return studySet;
	}
	
	// Helper function allows instructor to select student for assinging to course. 
	private Student getStudent() {
		
		Student student = null;
		boolean foundStudent = false;
		int studentId = -1;
		do {
			System.out.println("Please enter student Id");
			do {
				try {
					studentId = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("Please type a valid int.");
					studentId = -1;
					log.error("Encountered InputMismatchException");
				} finally {
					scan.nextLine();
				}
			} while(studentId == -1);
			try {
				student = FlashCardDriver.studentsCache.getStudentWithId(studentId);
				foundStudent = true;
			} catch(IllegalArgumentException e) {
				System.out.println("Student doesn't exist.");
				log.error("Encountered IllegalArgumentException");
			}
		} while (!foundStudent);
		return student;
	}

	private boolean assignStudySetToCourse() {
		
		Course course = getCourse();
		StudySet studySet = getStudySet();
		course.addStudySet(studySet);
		return true;
	}
	
	private boolean assignStudentToCourse() {
		
		Course course = getCourse();
		Student student = getStudent();
		student.addCourse(course);
		return true;
	}
}
