package FlashCard.Dao;

import FlashCard.pojos.Course;

public interface CourseDao {

	public void createCourse(Course course);
	
	public String readCourseStudySets(int courseId);
	
	public String readCourseName(int courseId);
		
	public void renameCourse(int courseId, String newName);
	
	public void deleteCourse(int courseId);
	
	public void assignStudySetToCourse(int studySetId, int courseId);
}
