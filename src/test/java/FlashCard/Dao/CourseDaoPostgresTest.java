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
	
	private static final String TEST_COURSE_NAME = "awertq2334rtqabdf";
	private int TEST_COURSE_ID;
	
	private static final String TEST_SET_NAME = "24rq23rq4%@##@$";
	private int TEST_SET_ID;
	
	private static final String TEST_RENAME_COURSE_NAME = "12312(*&@#)(*&$";
	private static final String TEST_CREATE_COURSE_NAME = "unliekly name asdf;lkj(*Us;lkdjD#";
	
	
	@Before
	public void setUp() throws Exception {
		realConnection = new ConnectionUtil().createConnection();
		
		//set up Dao to use the mocked object
		courseDao.setConnUtil(connUtil);
		
		utilStmt = realConnection.prepareStatement("insert into \"Course\" (course_name) values(?)");
		utilStmt.setString(1, TEST_COURSE_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select course_id from \"Course\" where course_name = ?");
		utilStmt.setString(1, TEST_COURSE_NAME);
		ResultSet rs = utilStmt.executeQuery();
		rs.next();
		TEST_COURSE_ID = rs.getInt("course_id");
		
		utilStmt = realConnection.prepareStatement("insert into \"StudySet\" (study_set_name) values(?)");
		utilStmt.setString(1, TEST_SET_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select study_set_id from \"StudySet\" where study_set_name = ?");
		utilStmt.setString(1, TEST_SET_NAME);
		rs = utilStmt.executeQuery();
		rs.next();
		TEST_SET_ID = rs.getInt("study_set_id");
		
		

	}

	@After
	public void tearDown() throws Exception {
		
		utilStmt = realConnection.prepareStatement("delete from \"Course\" where course_id = ?");
		utilStmt.setInt(1, TEST_COURSE_ID);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Course\" where course_name = ?");
		utilStmt.setString(1, TEST_CREATE_COURSE_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"AssignSSC\" where study_set_id = ? or course_id = ?");
		utilStmt.setInt(1, TEST_SET_ID);
		utilStmt.setInt(2, TEST_COURSE_ID);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"StudySet\" where study_set_id = ?");
		utilStmt.setInt(1, TEST_SET_ID);
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
		
		Course course = new Course(TEST_CREATE_COURSE_NAME);
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
		
		courseDao.renameCourse(TEST_COURSE_ID, TEST_RENAME_COURSE_NAME);
		
		try {
			verify(spy).setString(1, TEST_RENAME_COURSE_NAME);
			verify(spy).setInt(2, TEST_COURSE_ID);
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
	public void readCourseStudySetsTest() {
		
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
			courseDao.readCourseStudySets(TEST_COURSE_ID);
			verify(spy).setInt(1, TEST_COURSE_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
		
		@Test
		public void assignStudySetToCourseTest() {
			
			try {
				 String sql = "insert into \"AssignSSC\" (study_set_id, course_id) values(?, ?)";
				 initStmtHelper(sql);
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
			try {
				courseDao.assignStudySetToCourse(TEST_SET_ID, TEST_COURSE_ID);
				verify(spy).setInt(1, TEST_SET_ID);
				verify(spy).setInt(2, TEST_COURSE_ID);
				verify(spy).executeUpdate();
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
		}
		
		@Test
		public void readCourseNameTest() {
			
			try {
				String sql = "select course_name from \"Course\" where course_id = ?";
				 initStmtHelper(sql);
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
			try {
				courseDao.readCourseName(TEST_COURSE_ID);
				verify(spy).setInt(1, TEST_COURSE_ID);
				verify(spy).executeQuery();
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
		}
}
