package FlashCard.main;

import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

import org.apache.log4j.Logger;

import FlashCard.pojos.Student;
import FlashCard.pojos.Course;
import FlashCard.pojos.StudySet;

public class StudentDriver {
	private static Logger log = Logger.getRootLogger();

	private static Scanner scan = new Scanner(System.in);
	
	private Student student;

	public StudentDriver(Student student) {
		super();
		this.student = student;
	}

	public void studentActions() {
		log.info("Selecting student action.");
		String userInput; 
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] View all courses.");
			System.out.println("[2] View a specific course.");
			System.out.println("[0] Logout.");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				viewAllCourses();
				break;
			case "2":
				viewCourse();
				break;
			case "0":
				log.info("Student is leaving.");
				System.out.println("До встречи!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
		} while (!"0".equals(userInput));
		
	}
	
	private void viewAllCourses() {
		log.info("Student attempting to view courses.");
		List<Course> courses = this.student.getCourses();
		System.out.println("Enrolled courses:");
		for (Course course : courses) { 
			System.out.println(course.getCourseName() + " with id "+ course.getCourseId());
		}
	}
	
	private Course getCourse() {
		int courseId = -1; 
		Course course = null;
		boolean isEnrolled = false;
		do {
			System.out.println("Please enter the Id of the course you'd like to view. Or type [-2] to exit.");
			do {
				try {
					courseId = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("Please type a valid int.");
					courseId = -1;
				} finally {
					scan.nextLine();
				}
			} while(courseId == -1);
			
			if (courseId == -2) {
				return null;
			}
			try {
				course = FlashCardDriver.coursesCache.getCourseWithId(courseId);
				isEnrolled = this.student.isStudent(course);
				if (!isEnrolled) {
					System.out.println("You are not enrolled in this course.");
				}
			} catch(IllegalArgumentException e) {
				System.out.println("This course does not exist.");
			}
			
		} while(!isEnrolled);
		
		return course;
	}
	
	private void viewCourse() {
		log.info("Student attemping to view a course");
		Course course = getCourse();
		String userInput; 
		
		if (!(course == null)) {
			do {
				System.out.println("Selected course! What would you like to do now?");
				System.out.println("[1] View all study sets.");
				System.out.println("[2] View a specific study set.");
				System.out.println("[0] Exit.");
				
				userInput = scan.nextLine();
				
				switch(userInput) {
				case "1":
					viewAllStudySets(course);
					break;
				case "2":
					viewStudySet(course);
					break;
				case "0":
					System.out.println("До встречи!");
					break;
				default:
					System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
					break;
				}
			} while (!"0".equals(userInput));
		}
	}
	
	private void viewAllStudySets(Course course){
		List<StudySet> studySets = course.getStudySets();
		System.out.println("Study sets:");
		for (StudySet studySet: studySets) {
			System.out.println(studySet.getStudySetName() + "with id " + studySet.getStudySetId());
		}
	}
	
	private StudySet getStudySet(Course course) {
		int studySetId = -1;
		StudySet studySet = null;
		boolean isValidSet = false;
		
		do {
			System.out.println("Please enter the Id of the studySet you'd like to view.");
			do {
				try {
					studySetId = scan.nextInt();
				} catch(InputMismatchException e) {
					System.out.println("Please type a valid int.");
					studySetId = -1;
				} finally {
					scan.nextLine();
				}
			} while(studySetId == -1);
			try {
				studySet = FlashCardDriver.studySetsCache.getStudySetWithId(studySetId);
				isValidSet = course.studySetIsInCourse(studySet);
				if (!isValidSet) {
					System.out.println("This study set is not a part of the specified course.");
				}
			} catch(IllegalArgumentException e) {
				System.out.println("This study set does not exist.");
			}
			
		} while(!isValidSet);
		
		return studySet;
	}
	
	private void viewStudySet(Course course) {
		log.info("Attemping to view a studySet");
		StudySet studySet = getStudySet(course);
		String userInput; 
		do {
			System.out.println("Selected study set! What would you like to do now?");
			System.out.println("[1] Study.");
			System.out.println("[0] Exit.");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				StudyDriver studyDriver = new StudyDriver(studySet);
				studyDriver.Study();
				break;
			case "0":
				System.out.println("До встречи!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
		} while (!"0".equals(userInput));
		
	}
}
