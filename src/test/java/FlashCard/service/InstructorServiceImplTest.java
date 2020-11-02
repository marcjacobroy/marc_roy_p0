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

import FlashCard.pojos.Instructor;

// Test methods of instructor cache
@RunWith(MockitoJUnitRunner.class)
public class InstructorServiceImplTest {
	
	private InstructorServiceImpl instructorService;
	
	@Mock
	private CustomCacheServiceSimpleInMemory<Instructor> customCache; 
	
	List<Instructor> instructorList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		instructorList = new ArrayList<Instructor>();
		
		Instructor instructor1 = new Instructor("marc");
		Instructor instructor2 = new Instructor("mike");
		Instructor instructor3 = new Instructor("matt");
		Instructor instructor4 = new Instructor("ma");
		
		instructorList.add(instructor1);
		instructorList.add(instructor2);
		instructorList.add(instructor3);
		instructorList.add(instructor4);
		
		when((customCache.retrieveAllItems())).thenReturn(instructorList);
		instructorService = new InstructorServiceImpl(customCache);
	}

	@After
	public void tearDown() throws Exception {
		instructorList.clear();
	}

	@Test
	public void createInstructorTest() {
		Instructor testInstructor = instructorService.createInstructor("hi");
		assertEquals("Should create instructor object", "hi", testInstructor.getUserName());
		verify(customCache).addToCache(testInstructor);
	}
	
	@Test
	public void getInstructorListTest() {
		assertEquals("Should return full list of instructors", instructorList, instructorService.getAllInstructors());
	}
	

}
