package FlashCard.Dao;

import FlashCard.pojos.Course;

public interface CourseDao {

	public void createCourse(Course course);
	
	public String readCourse(int courseId);
		
	public void updateCourse(int courseId, Course course);
	
	public void deleteCourse(Course course);
}
