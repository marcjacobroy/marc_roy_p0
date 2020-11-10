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
import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.pojos.StudySet;

@RunWith(MockitoJUnitRunner.class)
public class StudySetDaoPostgresTest {
	
	private static final int DUMMY_SET_ID = 2;
	private static final String TEST_SET_NAME = "testSet";
	
	public StudySetDaoPostgres studySetDao = new StudySetDaoPostgres();
	
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

	@Before
	public void setUp() throws Exception {
		realConnection = new ConnectionUtil().createConnection();
		
		//set up Dao to use the mocked object
		studySetDao.setConnUtil(connUtil);
		
		utilStmt = realConnection.prepareStatement("insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) values(?, ?, ?, ?, ?)");
		utilStmt.setInt(1, 0);
		utilStmt.setInt(2, 0);
		utilStmt.setString(3, "test");
		utilStmt.setString(4, "pass");
		utilStmt.setInt(5, DUMMY_SET_ID);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("insert into \"StudySet\" (study_set_name) values(?)");
		utilStmt.setString(1, TEST_SET_NAME);
		utilStmt.executeUpdate();

	}

	@After
	public void tearDown() throws Exception {
		utilStmt = realConnection.prepareStatement("delete from \"Card\" where term = ? AND def = ?");
		utilStmt.setString(1, "test0");
		utilStmt.setString(2, "pass0");
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Card\" where term = ? AND def = ?");
		utilStmt.setString(1, "test");
		utilStmt.setString(2, "pass");
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"StudySet\" where study_set_name = ?");
		utilStmt.setString(1, TEST_SET_NAME);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"StudySet\" where study_set_name = ?");
		utilStmt.setString(1, "rename test");
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"StudySet\" where study_set_name = ?");
		utilStmt.setString(1, "test_study_set1");
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
	public void createStudySetTest() {
		
		StudySet studySet = new StudySet("test_study_set1");
		try {
			 String sql = "insert into \"StudySet\" (study_set_name) values(?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		try {
			studySetDao.createStudySet(studySet);
			verify(spy).setString(1, studySet.getStudySetName());
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
	
	@Test
	public void renameStudySetTest() {
		
		try {
			String sql = "update \"StudySet\" set study_set_name = ? where study_set_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		int studySetId = 0;
		String newName = "";
		
		try {
			utilStmt = realConnection.prepareStatement("select study_set_id from \"StudySet\" where study_set_name = ?");
			utilStmt.setString(1, TEST_SET_NAME);
			ResultSet rsStudySetId = utilStmt.executeQuery();
			rsStudySetId.next();
			studySetId = rsStudySetId.getInt("study_set_id");
			newName = "rename test";
			studySetDao.renameStudySet(studySetId, newName);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			verify(spy).setString(1, newName);
			verify(spy).setInt(2, studySetId);
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL Exception thrown: " + e);
		}
	}
	
	@Test
	public void deleteStudySetTest() {
		
		try {
			String sql = "delete from \"StudySet\" where study_set_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			utilStmt = realConnection.prepareStatement("select study_set_id from \"StudySet\" where study_set_name = ?");
			utilStmt.setString(1, TEST_SET_NAME);
			ResultSet rs = utilStmt.executeQuery();
			rs.next();
			int studySetId = rs.getInt("study_set_id");
			studySetDao.deleteStudySet(studySetId);
			verify(spy).setInt(1, studySetId);
			verify(spy).executeUpdate();
			
			utilStmt = realConnection.prepareStatement("select * from \"StudySet\" where study_set_id = ?");
			utilStmt.setInt(1, studySetId);
			rs = utilStmt.executeQuery();
			
			assertFalse(rs.next());
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
	
	@Test
	public void readStudySetTest() {
		
		try {
			 String sql = "select term, def from \"Card\" where study_set_id = ?";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			studySetDao.readStudySet(DUMMY_SET_ID);
			verify(spy).setInt(1, DUMMY_SET_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
}
