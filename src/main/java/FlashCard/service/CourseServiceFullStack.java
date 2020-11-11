package FlashCard.service;

import FlashCard.pojos.Course;
import org.apache.log4j.Logger;
import FlashCard.Dao.CourseDao;
import FlashCard.Dao.CourseDaoPostgres;

public class CourseServiceFullStack implements CourseService {
	
	private static Logger log = Logger.getRootLogger();
	
	CourseDao courseDao = new CourseDaoPostgres();
	
	@Override
	public void createCourse(Course course) {
		log.trace("Calling createCourse in CourseServiceFullStack on " + course.toString());
		courseDao.createCourse(course);
	}

	@Override
	public String readCourseStudySets(int courseId) {
		log.trace("Calling readCourseStudySets in CourseServiceFullStack on " + courseId);
		return courseDao.readCourseStudySets(courseId);
	}

	@Override
	public String readCourseName(int courseId) {
		log.trace("Calling readCourseName in CourseServiceFullStack on " + courseId);
		return courseDao.readCourseName(courseId);
	}

	@Override
	public void renameCourse(int courseId, String newName) {
		log.trace("Calling renameCourse in CourseServiceFullStack on " + courseId + " " + newName);
		courseDao.renameCourse(courseId, newName);
	}

	@Override
	public void deleteCourse(int courseId) {
		log.trace("Calling deleteCourse in CourseServiceFullStack on " + courseId);
		courseDao.deleteCourse(courseId);
	}

	@Override
	public void assignStudySetToCourse(int studySetId, int courseId) {
		log.trace("Calling assignStudySetToCourse in CourseServiceFullStack on " + studySetId + " " + courseId);
		courseDao.assignStudySetToCourse(studySetId, courseId);
	}

}
