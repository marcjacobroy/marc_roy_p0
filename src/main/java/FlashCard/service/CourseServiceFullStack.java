package FlashCard.service;

import FlashCard.pojos.Course;
import FlashCard.Dao.CourseDao;
import FlashCard.Dao.CourseDaoPostgres;

public class CourseServiceFullStack implements CourseService {
	
	CourseDao courseDao = new CourseDaoPostgres();
	
	@Override
	public void createCourse(Course course) {
		courseDao.createCourse(course);
	}

	@Override
	public String readCourseStudySets(int courseId) {
		return courseDao.readCourseStudySets(courseId);
	}

	@Override
	public String readCourseName(int courseId) {
		return courseDao.readCourseName(courseId);
	}

	@Override
	public void renameCourse(int courseId, String newName) {
		courseDao.renameCourse(courseId, newName);
	}

	@Override
	public void deleteCourse(int courseId) {
		courseDao.deleteCourse(courseId);
	}

	@Override
	public void assignStudySetToCourse(int studySetId, int courseId) {
		courseDao.assignStudySetToCourse(studySetId, courseId);
	}

}
