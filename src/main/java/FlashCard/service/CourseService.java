package FlashCard.service;


import FlashCard.pojos.Course;

//Structure of cache for courses 
public interface CourseService {
	
	public void createCourse(Course course);
	
	public String readCourseStudySets(int courseId);
	
	public String readCourseName(int courseId);
		
	public void renameCourse(int courseId, String newName);
	
	public void deleteCourse(int courseId);
	
	public void assignStudySetToCourse(int studySetId, int courseId);

}
