package FlashCard.service;

import java.util.List;

import FlashCard.pojos.Course;
import FlashCard.pojos.Instructor;

//Structure of cache for courses 
public interface CourseService {
	
	public Course createCourse(String courseName, Instructor instructor);
	
	public List<Course> getAllCourses();
	
	public boolean containsCourse (Course course);
	
	public boolean containsCourseWithId(int id);
	
	public boolean containsCourseWithCourseName(String courseName);
	
	public Course getCourseWithId (int id); 
	
	public Course getCourseWithCourseName (String CourseName); 

}
