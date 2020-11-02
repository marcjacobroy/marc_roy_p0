package FlashCard.main;


import java.util.Scanner;


import org.apache.log4j.Logger;

import FlashCard.pojos.Card;
import FlashCard.pojos.Student;
import FlashCard.pojos.Instructor;
import FlashCard.pojos.Course;
import FlashCard.pojos.StudySet;
import FlashCard.pojos.Entry;
import FlashCard.pojos.Entry.Language;

public class InstructorDriver {
	
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	private Instructor instructor;

	public InstructorDriver(Instructor instructor) {
		super();
		this.instructor = instructor;
	}

	public void instructorActions() {
		log.info("Selecting an instructor action");
		String userInput; 
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] Create a course.");
			System.out.println("[2] Create a study set.");
			System.out.println("[3] Add a study set to a course.");
			System.out.println("[4] Assign a student to a course.");
			System.out.println("[0] Exit.");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				createCourse();
				System.out.println("Successfully created course!");
				break;
			case "2":
				createStudySet();
				System.out.println("Successfully created study set!");
				break;
			case "3":
				assignStudySetToCourse();
				System.out.println("Successfully assigned study set to course!");
				break;
			case "4":
				assignStudentToCourse();
				System.out.println("Successfully assigned student to course!");
				break;
			case "0":
				System.out.println("Tchau!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
			
		} while (!"0".equals(userInput));
		
	}
	
	private Course createCourse() {
		String courseName;
		
		System.out.println("What is the course name?");
		
		do {
			courseName = scan.nextLine();
		} while (!"".equals(courseName));
		
		Course course = FlashCardDriver.coursesCache.createCourse(courseName, this.instructor);
		instructor.addCourse(course);
		return course;
	}
	
	private static Language pickLanguage() {
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
	
	private StudySet createStudySet(){
		
		StudySet studySet = new StudySet();
		
		String userInputStudySetName;
		do {
			System.out.println("What is the study set name?");
			userInputStudySetName = scan.nextLine();
		} while (!"".equals(userInputStudySetName));
		
		Language termLanguage;
		Language defLanguage;
		
		System.out.println("What is the language of the terms?");
		termLanguage = pickLanguage();
		System.out.println("What is the language of the definitions?");
		defLanguage = pickLanguage();
		
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
				break;
			case "0":
				System.out.println("Completing set...");
			default:
				System.out.println("Invalid choice. Please type [1] or [0].");
			}
		} while (!"0".equals(addCardUserInput));
		
		return studySet;
		
	}
	
	private Card createCard(Language termLanguage, Language defLanguage) {
		System.out.println("Please enter term:");
		String termText = scan.nextLine();
		Entry term = new Entry(termText, termLanguage);
		System.out.println("Please enter definition:");
		String defText = scan.nextLine();
		Entry def = new Entry(defText, defLanguage);
		Card card = new Card(term, def);
		return card;
	}
	
	private Course getCourse() {
		boolean validCourse = false;
		do {
			System.out.println("Please enter course Id.");
			int courseId = scan.nextInt();
			Course course = FlashCardDriver.coursesCache.getCourseWithId(courseId);
			if (this.instructor.isInstructor(course)) {
				validCourse = true;
				return course;
			} else {
				System.out.println("Invalid course. Are you sure you're the instructor?");
			}
		} while(!validCourse);
		return null;
	}
	
	private StudySet getStudySet() {
			System.out.println("Please enter study set Id");
			int studySetId = scan.nextInt();
			return FlashCardDriver.studySetsCache.getStudySetWithId(studySetId);
	}
	
	private Student getStudent() {
		System.out.println("Please enter student Id");
		int studentId = scan.nextInt();
		return FlashCardDriver.studentsCache.getStudentWithId(studentId);
	}

	private void assignStudySetToCourse() {
		Course course = getCourse();
		StudySet studySet = getStudySet();
		course.addStudySet(studySet);
	}
	
	private void assignStudentToCourse() {
		Course course = getCourse();
		Student student = getStudent();
		student.addCourse(course);
	}
}
