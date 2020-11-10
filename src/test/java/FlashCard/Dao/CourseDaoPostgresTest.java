package FlashCard.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import FlashCard.util.ConnectionUtil;
import FlashCard.pojos.Course;
import FlashCard.pojos.Instructor;


@RunWith(MockitoJUnitRunner.class)
public class CourseDaoPostgresTest {
	
	public CourseDaoPostgres courseDao = new CourseDaoPostgres();
	
	@Mock
	private ConnectionUtil connUtil;
	
	@Mock
	private Connection connection;
	
	private Statement stmt;
	
	private Statement spy;
	
	private Connection realConnection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		realConnection = new ConnectionUtil().createConnection();
		
		//creating a real stmt from a connection
		stmt = realConnection.createStatement(); 
		
		//spying on that real stmt
		spy = Mockito.spy(stmt);
		
		//mock our connection and util, so we will only use the stmt we are spying on
		when(connection.createStatement()).thenReturn(spy);
		when(connUtil.createConnection()).thenReturn(connection);
		
		//set up Dao to use the mocked object
		courseDao.setConnUtil(connUtil);
	}

	@After
	public void tearDown() throws Exception {
		realConnection.close();
	}

	@Test
	public void createCourseTest() {
		String courseName = "TestCourse";
		
		Instructor instructor = new Instructor("TestInstructor");
		
		Course course = new Course(courseName, instructor);
		
		courseDao.createCourse(course);
	}
	
	@Test
	public void updateCourseTest() {
		//TODO
	}
	
	@Test
	public void deleteCourseTest() {
		//TODO
	}
	
	@Test 
	public void readCourseTest() {
	//TODO
	}

}
