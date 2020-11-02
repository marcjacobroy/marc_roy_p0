package FlashCard.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FlashCard.pojos.Course;
import FlashCard.pojos.Instructor;

// Test methods of course cache 
@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {
	
	private CourseServiceImpl courseService;
	
	@Mock
	private CustomCacheServiceSimpleInMemory<Course> customCache; 
	
	List<Course> courseList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		courseList = new ArrayList<Course>();
		
		Instructor instructor1 = new Instructor("marc");
		Instructor instructor2 = new Instructor("mike");
		
		Course course1 = new Course("german", instructor1);
		Course course2 = new Course("french", instructor1);
		Course course3 = new Course("russian", instructor2);
		Course course4 = new Course("portuguese", instructor2);
		
		courseList.add(course1);
		courseList.add(course2);
		courseList.add(course3);
		courseList.add(course4);
		
		when((customCache.retrieveAllItems())).thenReturn(courseList);
		courseService = new CourseServiceImpl(customCache);
	}

	@After
	public void tearDown() throws Exception {
		courseList.clear();
	}

	@Test
	public void createcourseTest() {
		Instructor instructor = new Instructor("hi");
		Course testCourse = courseService.createCourse("arabic", instructor);
		assertEquals("Should create course object", "arabic", testCourse.getCourseName());
		assertEquals("Should create course object", instructor, testCourse.getInstructor());
		verify(customCache).addToCache(testCourse);
	}
	
	@Test
	public void getcourseListTest() {
		assertEquals("Should return full list of courses", courseList, courseService.getAllCourses());
	}
	

}
