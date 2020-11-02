package FlashCard.main;

import java.util.Scanner;
import java.util.List;

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
		log.info("Selecting student action");
		String userInput; 
		do {
			System.out.println("What would you like to do?");
			System.out.println("[1] View all courses.");
			System.out.println("[2] View a specific course.");
			System.out.println("[0] Exit.");
			
			userInput = scan.nextLine();
			
			switch (userInput) {
			case "1":
				viewAllCourses();
			case "2":
				viewCourse();
			case "0":
				System.out.println("До встречи!");
				break;
			default:
				System.out.println("Invalid choice. Please select either '1', '2' or '0'.");
				break;
			}
		} while (!"0".equals(userInput));
		
	}
	
	private List<Course> viewAllCourses() {
		return this.student.getCourses();
	}
	
	private Course getCourse() {
		int courseId; 
		Course course = null;
		boolean isEnrolled;
		do {
			System.out.println("Please enter the Id of the course you'd like to view.");
			courseId = scan.nextInt();
			course = FlashCardDriver.coursesCache.getCourseWithId(courseId);
			isEnrolled = this.student.isStudent(course);
		} while(!isEnrolled);
		
		return course;
	}
	
	private void viewCourse() {
		log.info("Attemping to view a course");
		Course course = getCourse();
		String userInput; 
		do {
			System.out.println("Selected course! What would you like to do now?");
			System.out.println("[1] View all study sets.");
			System.out.println("[2] View a specific study set.");
			System.out.println("[0] Exit.");
			
			userInput = scan.nextLine();
			
			switch(userInput) {
			case "1":
				viewAllStudySets(course);
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
	
	private List<StudySet> viewAllStudySets(Course course){
		return course.getStudySets();
	}
	
	private StudySet getStudySet(Course course) {
		if (course.getStudySets().size() == 0) {
			System.out.println("Course has no study sets. Please choose a different one.");
			return null;
		}
		int studySetId;
		StudySet studySet = null;
		boolean isValidSet;
		do {
			System.out.println("Please enter the Id of the study set you'd like to view.");
			studySetId = scan.nextInt();
			studySet = FlashCardDriver.studySetsCache.getStudySetWithId(studySetId);
			isValidSet = course.studySetIsInCourse(studySet);
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
