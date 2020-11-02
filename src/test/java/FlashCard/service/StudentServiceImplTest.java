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

import FlashCard.pojos.Student;

// test methods of student cache 
@RunWith(MockitoJUnitRunner.class)
public class StudentServiceImplTest {
	
	private StudentServiceImpl studentService;
	
	@Mock
	private CustomCacheServiceSimpleInMemory<Student> customCache; 
	
	List<Student> studentList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		studentList = new ArrayList<Student>();
		
		Student student1 = new Student("marc");
		Student student2 = new Student("mike");
		Student student3 = new Student("matt");
		Student student4 = new Student("ma");
		
		studentList.add(student1);
		studentList.add(student2);
		studentList.add(student3);
		studentList.add(student4);
		
		when((customCache.retrieveAllItems())).thenReturn(studentList);
		studentService = new StudentServiceImpl(customCache);
	}

	@After
	public void tearDown() throws Exception {
		studentList.clear();
	}

	@Test
	public void createstudentTest() {
		Student teststudent = studentService.createStudent("hi");
		assertEquals("Should create student object", "hi", teststudent.getUserName());
		verify(customCache).addToCache(teststudent);
	}
	
	@Test
	public void getstudentListTest() {
		assertEquals("Should return full list of students", studentList, studentService.getAllStudents());
	}
	

}

