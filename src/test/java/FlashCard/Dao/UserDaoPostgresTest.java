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
import FlashCard.pojos.User;
import FlashCard.pojos.User.UserType;


@RunWith(MockitoJUnitRunner.class)
public class UserDaoPostgresTest {
	
	public UserDaoPostgres userDao = new UserDaoPostgres();
	
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
	
	private static final String TEST_USER_NAME = "awertq2334rtqabdf";
	private int TEST_USER_ID;
	
	private static final String TEST_COURSE_NAME = "24rq23rq4%@##@$";
	private int TEST_COURSE_ID;
	
	private static final String TEST_RENAME_USER_NAME = "12312(*&@#)(*&$";
	private static final String TEST_CREATE_USER_NAME = "unliekly name asdf;lkj(*Us;lkdjD#";
	
	
	@Before
	public void setUp() throws Exception {
		realConnection = new ConnectionUtil().createConnection();
		
		//set up Dao to use the mocked object
		userDao.setConnUtil(connUtil);
		
		utilStmt = realConnection.prepareStatement("insert into \"User\" (user_name, user_type) values(?, ?)");
		utilStmt.setString(1, TEST_USER_NAME);
		utilStmt.setString(2, "Instructor");
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select user_id from \"User\" where user_name = ?");
		utilStmt.setString(1, TEST_USER_NAME);
		ResultSet rs = utilStmt.executeQuery();
		rs.next();
		TEST_USER_ID = rs.getInt("user_id");
		
		utilStmt = realConnection.prepareStatement("insert into \"Course\" (course_name) values(?)");
		utilStmt.setString(1, TEST_COURSE_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select course_id from \"Course\" where course_name = ?");
		utilStmt.setString(1, TEST_COURSE_NAME);
		rs = utilStmt.executeQuery();
		rs.next();
		TEST_COURSE_ID = rs.getInt("course_id");
		
		

	}

	@After
	public void tearDown() throws Exception {
		
		utilStmt = realConnection.prepareStatement("delete from \"User\" where user_name = ?");
		utilStmt.setString(1, TEST_USER_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"User\" where user_name = ?");
		utilStmt.setString(1, TEST_CREATE_USER_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"User\" where user_name = ?");
		utilStmt.setString(1, TEST_RENAME_USER_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Enrollment\" where course_id = ? or user_id = ?");
		utilStmt.setInt(1, TEST_COURSE_ID);
		utilStmt.setInt(2, TEST_USER_ID);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Course\" where course_id = ?");
		utilStmt.setInt(1, TEST_COURSE_ID);
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
	public void createUserTest() {
		
		User user = new User(TEST_CREATE_USER_NAME, UserType.STUDENT);
		try {
			 String sql = "insert into \"User\" (user_name, user_type) values(?, ?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		try {
			userDao.createUser(user);
			verify(spy).setString(1, user.getUserName());
			verify(spy).setString(2,  user.getUserType());
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
	
	@Test
	public void createUserTest2() {
		
		User user = new User(TEST_CREATE_USER_NAME, UserType.INSTRUCTOR);
		try {
			 String sql = "insert into \"User\" (user_name, user_type) values(?, ?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		try {
			userDao.createUser(user);
			verify(spy).setString(1, user.getUserName());
			verify(spy).setString(2,  user.getUserType());
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
	
	@Test
	public void renameUserTest() {
		
		try {
			String sql = "update \"User\" set user_name = ? where user_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		userDao.renameUser(TEST_USER_ID, TEST_RENAME_USER_NAME);
		
		try {
			verify(spy).setString(1, TEST_RENAME_USER_NAME);
			verify(spy).setInt(2, TEST_USER_ID);
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL Exception thrown: " + e);
		}
	}
	
	@Test
	public void deleteUserTest() {
		
		try {
			String sql = "delete from \"User\" where user_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			utilStmt = realConnection.prepareStatement("select user_id from \"User\" where user_name = ?");
			utilStmt.setString(1, TEST_USER_NAME);
			ResultSet rs = utilStmt.executeQuery();
			rs.next();
			int userId = rs.getInt("user_id");
			userDao.deleteUser(userId);
			verify(spy).setInt(1, userId);
			verify(spy).executeUpdate();
			
			utilStmt = realConnection.prepareStatement("select * from \"User\" where user_id = ?");
			utilStmt.setInt(1, userId);
			rs = utilStmt.executeQuery();
			
			assertFalse(rs.next());
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
	
	@Test
	public void readUserCoursesTest() {
		
		try {
			String sql = "select \"Course\".course_name, \"User\".user_name from \"Course\", \"User\", \"Enrollment\" where \n"
					+ "\"Course\".course_id = \"Enrollment\".course_id and \"User\".user_id = \n"
					+ "\"Enrollment\".user_id and \"User\".user_id = ?;";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			userDao.readUserCourses(TEST_USER_ID);
			verify(spy).setInt(1, TEST_USER_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
		
		@Test
		public void assignUserToCourseTest() {
			
			try {
				String sql = "insert into \"Enrollment\" (user_id, course_id) values(?, ?)";
				 initStmtHelper(sql);
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
			try {
				userDao.assignUserToCourse(TEST_USER_ID, TEST_COURSE_ID);
				verify(spy).setInt(1, TEST_USER_ID);
				verify(spy).setInt(2, TEST_COURSE_ID);
				verify(spy).executeUpdate();
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
		}
		
		@Test
		public void readUserNameTest() {
			
			try {
				String sql = "select user_name from \"User\" where user_id = ?";
				 initStmtHelper(sql);
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
			try {
				userDao.readUserName(TEST_USER_ID);
				verify(spy).setInt(1, TEST_USER_ID);
				verify(spy).executeQuery();
			} catch(SQLException e) {
				fail("SQL exception thrown: " + e);
			}
			
		}
}

