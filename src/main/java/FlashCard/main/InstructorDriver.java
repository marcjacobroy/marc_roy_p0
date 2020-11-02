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
				Course course = createCourse();
				System.out.println("Successfully created course!");
				break;
			case "2":
				StudySet studySet = createStudySet();
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
		
		Course course = new Course(courseName, this.instructor);
		instructor.addCourse(course);
		return course;
	}
	
	private static StudySet createStudySet(){
		
		StudySet studySet = new StudySet();
		
		String userInputStudySetName;
		do {
			System.out.println("What is the study set name?");
			userInputStudySetName = scan.nextLine();
		} while (!"".equals(userInputStudySetName));
		
		
		String userInputTermLanguage;
		Language termLanguage = null;
		do {
		
			System.out.println("What is the language of the terms?");
			System.out.println("[1] English");
			System.out.println("[2] German");
			System.out.println("[3] French");
			System.out.println("[4] Spanish");
			System.out.println("[5] Portuguese");
			System.out.println("[6] Italian");
			System.out.println("[7] Russian");
			System.out.println("[8] Arabic");
			
			userInputTermLanguage = scan.nextLine();
			
			switch(userInputTermLanguage) {
			case "1":
				termLanguage = Language.ENGLISH;
				break;
			case "2":
				termLanguage = Language.GERMAN;
				break;
			case "3": 
				termLanguage = Language.FRENCH;
				break;
			case "4": 
				termLanguage = Language.SPANISH;
				break;
			case "5":
				termLanguage = Language.PORTUGUESE;
				break;
			case "6":
				termLanguage = Language.ITALIAN;
				break;
			case "7": 
				termLanguage = Language.RUSSIAN;
				break;
			case "8": 
				termLanguage = Language.ARABIC;
				break;
			default:
				System.out.println("Invalid choice. Please choose a number from 1 to 8.");
			}
		} while (termLanguage == null);
		
		String userInputDefLanguage;
		Language defLanguage = null;
		do {
		
			System.out.println("What is the language of the definitions?");
			System.out.println("[1] English");
			System.out.println("[2] German");
			System.out.println("[3] French");
			System.out.println("[4] Spanish");
			System.out.println("[5] Portuguese");
			System.out.println("[6] Italian");
			System.out.println("[7] Russian");
			System.out.println("[8] Arabic");
			
			userInputDefLanguage = scan.nextLine();
			
			switch(userInputDefLanguage) {
			case "1":
				defLanguage = Language.ENGLISH;
				break;
			case "2":
				defLanguage = Language.GERMAN;
				break;
			case "3": 
				defLanguage = Language.FRENCH;
				break;
			case "4": 
				defLanguage = Language.SPANISH;
				break;
			case "5":
				defLanguage = Language.PORTUGUESE;
				break;
			case "6":
				defLanguage = Language.ITALIAN;
				break;
			case "7": 
				defLanguage = Language.RUSSIAN;
				break;
			case "8": 
				defLanguage = Language.ARABIC;
				break;
			default:
				System.out.println("Invalid choice. Please choose a number from 1 to 8.");
			}
		} while (defLanguage == null);
		
		String addCardUserInput;
		do {
			System.out.println("Please add at least one card to the new study set. Type [0] when done.");
			System.out.println("[1] Add new card.");
			System.out.println("[0] Done.");
			
			addCardUserInput = scan.nextLine();
			
			
			switch(addCardUserInput) {
			case "1":
				System.out.println("Please enter term:");
				String termText = scan.nextLine();
				Entry term = new Entry(termText, termLanguage);
				System.out.println("Please enter definition:");
				String defText = scan.nextLine();
				Entry def = new Entry(defText, defLanguage);
				Card card = new Card(term, def);
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
	
	private void assignStudySetToCourse() {
		int studySetId = -1;
		int courseId = -1;
		StudySet studySet = null;
		Course course = null;
		
		boolean courseExists = false;
		boolean isInstructor = false;
		boolean studySetExists = false;
		
		do {
			System.out.println("Please enter course Id");
			courseId = scan.nextInt();
			List<Course> courses = this.instructor.getCourses();
			courseExists = containsCourseId(courses, courseId);
			if (courseExists) {
				course = getCourseWithId(courses, courseId);
			} else {
				System.out.println("Course does not exist.");
			}
		} while(!courseExists);
	
		do {
			System.out.println("Please enter study set Id");
			studySetId = scan.nextInt();
			List<StudySet> studySets = FlashCardDriver.getStudySets();
			studySetExists = containsStudySetId(studySets, studySetId);
			if (studySetExists) {
				studySet = getStudySetWithId(studySets, studySetId);
			} else {
				System.out.println("Study set does not exist.");
			}
		} while(!studySetExists);
		
		course.addStudySet(studySet);
	}
	
	private void assignStudentToCourse() {
		int studentId = -1;
		int courseId = -1;
		User student = null;
		Course course = null;
		
		boolean courseExists = false;
		boolean studentExists = false;
		
		do {
			System.out.println("Please enter course Id");
			courseId = scan.nextInt();
			List<Course> courses = this.instructor.getCourses();
			courseExists = containsCourseId(courses, courseId);
			if (courseExists) {
				course = getCourseWithId(courses, courseId);
			} else {
				System.out.println("Course does not exist.");
			}
		} while(!courseExists);
	
		do {
			System.out.println("Please enter student Id");
			studentId = scan.nextInt();
			List<Student> students = FlashCardDriver.getStudents();
			studentExists = containsStudentId(students, studentId);
			if (studentExists) {
				student = getStudentWithId(students, studentId);
			} else {
				System.out.println("Student does not exist.");
			}
		} while(!studentExists);
		
		student.addCourse(course);
		
	}
	
	
	
	public static boolean containsCourseId(final List<Course> list, final int id) {
		return list.stream().filter(p -> p.getCourseId() == id).findFirst().isPresent();
	}
	
	public static boolean containsStudySetId(final List<StudySet> list, final int id) {
		return list.stream().filter(p -> p.getStudySetId() == id).findFirst().isPresent();
	}
	
	public static boolean containsUserId(List<User> list, int id) {
		return list.stream().filter(p -> p.getUserId() == id).findFirst().isPresent();
	}
	
	public static boolean containsStudentId(List<Student> list, int id) {
		return list.stream().filter(p -> p.getUserId() == id).findFirst().isPresent();
	}
	
	public static boolean isInstructor(final Instructor instructor, Course course) {
		return (course.getInstructor().getUserId() == instructor.getUserId());
	}
	
	public static Course getCourseWithId(final List<Course> list, final int id) {
		if (containsCourseId(list, id)) {
			return (list.stream().filter(p -> p.getCourseId() == id).collect(Collectors.toList()).get(0));
		} else {
			throw new IllegalArgumentException("Course with id is not present in list.");
		}
	}
	
	public static StudySet getStudySetWithId(final List<StudySet> list, final int id) {
		if (containsStudySetId(list, id)) {
			return (list.stream().filter(p -> p.getStudySetId() == id).collect(Collectors.toList()).get(0));
		} else {
			throw new IllegalArgumentException("Study Set with id is not present in list.");
		}
	}
	
	public static User getUserWithId(final List<User> list, final int id) {
		if (containsUserId(list, id)) {
			return (list.stream().filter(p -> p.getUserId() == id).collect(Collectors.toList()).get(0));
		} else {
			throw new IllegalArgumentException("User with id is not present in list.");
		}
	}
	
	public static Student getStudentWithId(final List<Student> list, final int id) {
		if (containsStudentId(list, id)) {
			return (list.stream().filter(p -> p.getUserId() == id).collect(Collectors.toList()).get(0));
		} else {
			throw new IllegalArgumentException("Student with id is not present in list.");
		}
	}
}
