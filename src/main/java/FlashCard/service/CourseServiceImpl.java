package FlashCard.service;

import java.util.List;

import FlashCard.pojos.Course;
import FlashCard.pojos.Instructor;

// Implementation of cache for courses 
public class CourseServiceImpl implements CourseService {

	private CustomCacheService<Course> courseCache = new CustomCacheServiceSimpleInMemory<Course>();
	
	public CourseServiceImpl() {
		super();
	}

	public CourseServiceImpl(CustomCacheService<Course> courseCache) {
		this();
		this.courseCache = courseCache;
	}

	@Override
	public Course createCourse(String courseName, Instructor instructor) {
		Course course = new Course(courseName, instructor);
		courseCache.addToCache(course);
		return course;
	}

	@Override
	public List<Course> getAllCourses() {
		return courseCache.retrieveAllItems();
	}

	@Override
	public boolean containsCourse(Course course) {
		return courseCache.contains(course);
	}

	@Override
	public boolean containsCourseWithId(int id) {
		return courseCache.containsMatchingElt(p -> p.getCourseId() == id);
	}

	@Override
	public boolean containsCourseWithCourseName(String courseName) {
		return courseCache.containsMatchingElt(p -> p.getCourseName().equals(courseName));

	}

	@Override
	public Course getCourseWithId(int id) {
		return courseCache.retrieveMatchingElt(p -> p.getCourseId() == id);
	}

	@Override
	public Course getCourseWithCourseName(String courseName) {
		return courseCache.retrieveMatchingElt(p -> p.getCourseName().equals(courseName));
	}

}
