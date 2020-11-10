package FlashCard.Dao;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import FlashCard.pojos.StudySet;

@RunWith(MockitoJUnitRunner.class)
public class CourseDaoPostgresTest {
	
	public CourseDaoPostgres courseDao = new CourseDaoPostgres();
	
	@Mock
	private ConnectionUtil connUtil;
	
	@Mock
	private Connection connection;
	
	private PreparedStatement stmt;
	private PreparedStatement spy;
	
	private PreparedStatement utilStmt;
	
	private Connection realConnection;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
			}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private static final String TEST_COURSE_NAME = "testCourse";
	private static final int DUMMY_COURSE_ID = 2;
	
	@Before
	public void setUp() throws Exception {
		realConnection = new ConnectionUtil().createConnection();
		
		//set up Dao to use the mocked object
		courseDao.setConnUtil(connUtil);
		
		utilStmt = realConnection.prepareStatement("insert into \"Course\" (course_name) values(?)");
		utilStmt.setString(1, TEST_COURSE_NAME);
		utilStmt.executeUpdate();

	}

	@After
	public void tearDown() throws Exception {
		
		utilStmt = realConnection.prepareStatement("delete from \"Course\" where course_name = ?");
		utilStmt.setString(1, TEST_COURSE_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Course\" where course_name = ?");
		utilStmt.setString(1, "rename test");
		utilStmt.executeUpdate();
		
		if(stmt != null) {
			stmt.close();
		}
		
		realConnection.close();
	}
	
	// Credits to Conner Bosch for this helper function especially 
	
	private void initStmtHelper(String sql) throws SQLException{
		
		//creating a real stmt from a connection
		stmt = realConnection.prepareStatement(sql);
		
		//spying on that real stmt
		spy = Mockito.spy(stmt);
		
		//mock our connection and util, so we will only use the stmt we are spying on
		when(connUtil.createConnection()).thenReturn(connection);
		when(connection.prepareStatement(sql)).thenReturn(spy);
	}
	
	@Test
	public void createCourseTest() {
		
		Course course = new Course("test_course");
		try {
			 String sql = "insert into \"Course\" (course_name) values(?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		try {
			courseDao.createCourse(course);
			verify(spy).setString(1, course.getCourseName());
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
	
	@Test
	public void renameCourseTest() {
		
		try {
			String sql = "update \"Course\" set course_name = ? where course_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		int courseId = 0;
		String newName = "";
		
		try {
			utilStmt = realConnection.prepareStatement("select course_id from \"Course\" where course_name = ?");
			utilStmt.setString(1, TEST_COURSE_NAME);
			ResultSet rsCourseId = utilStmt.executeQuery();
			rsCourseId.next();
			courseId = rsCourseId.getInt("course_id");
			newName = "rename test";
			courseDao.renameCourse(courseId, newName);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			verify(spy).setString(1, newName);
			verify(spy).setInt(2, courseId);
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL Exception thrown: " + e);
		}
	}
	
	@Test
	public void deleteCourseTest() {
		
		try {
			String sql = "delete from \"Course\" where course_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			utilStmt = realConnection.prepareStatement("select course_id from \"Course\" where course_name = ?");
			utilStmt.setString(1, TEST_COURSE_NAME);
			ResultSet rs = utilStmt.executeQuery();
			rs.next();
			int courseId = rs.getInt("course_id");
			courseDao.deleteCourse(courseId);
			verify(spy).setInt(1, courseId);
			verify(spy).executeUpdate();
			
			utilStmt = realConnection.prepareStatement("select * from \"Course\" where course_id = ?");
			utilStmt.setInt(1, courseId);
			rs = utilStmt.executeQuery();
			
			assertFalse(rs.next());
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
	
	@Test
	public void readCourseTest() {
		
		try {
			 String sql = "select \"Course\".course_name, \"AssignSSC\".study_set_id, "
						+ "\"StudySet\".study_set_name from \"Course\", \"AssignSSC\", \"StudySet\"where "
						+ "\"Course\".course_id = \"AssignSSC\".course_id and \"StudySet\".study_set_id = "
						+ "\"AssignSSC\".study_set_id and \"Course\".course_id = ?";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			courseDao.readCourse(DUMMY_COURSE_ID);
			verify(spy).setInt(1, DUMMY_COURSE_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
}
