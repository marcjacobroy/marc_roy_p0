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
import FlashCard.pojos.StudySet;

@RunWith(MockitoJUnitRunner.class)
public class StudySetDaoPostgresTest {
	
	private int TEST_SET_ID;
	private final String TEST_SET_NAME = "aitn noboasdf;;o14o32iu ";
	private int TEST_CARD_ID;
	private final String TEST_CARD_TERM = "aoisud;asu n13roq32ijr ;";
	private final String TEST_CARD_DEF = "alsdj; @#P08ur23 ";
	
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
		
		utilStmt = realConnection.prepareStatement("insert into \"Card\" (count_correct, count_wrong, term, def) values(?, ?, ?, ?)");
		utilStmt.setInt(1, 0);
		utilStmt.setInt(2, 0);
		utilStmt.setString(3, TEST_CARD_TERM);
		utilStmt.setString(4, TEST_CARD_DEF);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, TEST_CARD_TERM);
		utilStmt.setString(2, TEST_CARD_DEF);
		ResultSet rs = utilStmt.executeQuery();
		rs.next();
		TEST_CARD_ID = rs.getInt("card_id");
		
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
		
		utilStmt = realConnection.prepareStatement("delete from \"Card\" where term = ? AND def = ?");
		utilStmt.setString(1, TEST_CARD_TERM);
		utilStmt.setString(2, TEST_CARD_DEF);
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
	public void readStudySetCardsTest() {
		
		try {
			String sql = "select \"StudySet\".study_set_name, \"AssignCSS\".card_id, "
					+ "\"Card\".term, \"Card\".def from \"StudySet\", \"AssignCSS\", \"Card\"where "
					+ "\"StudySet\".study_set_id = \"AssignCSS\".study_set_id and \"Card\".card_id = "
					+ "\"AssignCSS\".card_id and \"StudySet\".study_set_id = ?";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			studySetDao.readStudySetCards(TEST_SET_ID);
			verify(spy).setInt(1, TEST_SET_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
	
	@Test
	public void readStudySetNameTest() {
		
		try {
			String sql = "select study_set_name from \"StudySet\" where study_set_id = ?";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			studySetDao.readStudySetName(TEST_SET_ID);
			verify(spy).setInt(1, TEST_SET_ID);
			verify(spy).executeQuery();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
	}
	
	@Test
	public void assignCardToStudySetTest() {
		
		try {
			 String sql = "insert into \"AssignCSS\" (card_id, study_set_id) values(?, ?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		try {
			studySetDao.assignCardToStudySet(TEST_CARD_ID, TEST_SET_ID);
			verify(spy).setInt(1, TEST_CARD_ID);
			verify(spy).setInt(2, TEST_SET_ID);
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}
}
