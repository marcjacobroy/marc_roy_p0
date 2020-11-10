package FlashCard.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.util.ConnectionUtil;

// Credits to Conner Bosch for teaching us how to use Mockito with prepared statements
@RunWith(MockitoJUnitRunner.class)
public class CardDaoPostgresTest {

	public CardDaoPostgres cardDao = new CardDaoPostgres();
	
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
		cardDao.setConnUtil(connUtil);
		
		utilStmt = realConnection.prepareStatement("insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) values(?, ?, ?, ?, ?)");
		
		utilStmt.setInt(1, 0);
		utilStmt.setInt(2, 0);
		utilStmt.setString(3, "test");
		utilStmt.setString(4, "pass");
		utilStmt.setInt(5, 1);
		
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
	public void createCardTest() throws SQLException {
		
		Entry term = new Entry("test0");
		
		Entry def = new Entry("pass0");
		
		Card card = new Card(term, def);
		
		try {
			 String sql = "insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) values( ?, ?, ?, ?, ?)";
			 initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		try {
			cardDao.createCard(card);
			verify(spy).setInt(1, card.getCountCorrect());
			verify(spy).setInt(2, card.getCountWrong());
			verify(spy).setString(3, card.getTerm());
			verify(spy).setString(4, card.getDef());
			verify(spy).setInt(5, 1);
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}

	@Test
	public void updateCardTest() throws SQLException {
		
		Entry term = new Entry("test0");
		
		Entry def = new Entry("pass0");
		
		Card card = new Card(term, def);
		
		try {
			String sql = "update \"Card\" set term = ?, def = ?, count_correct = ?, count_wrong = ? where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, "test");
		utilStmt.setString(2, "pass");
		
		ResultSet rsCardId = utilStmt.executeQuery();
		rsCardId.next();
		
		int cardId = rsCardId.getInt("card_id");
		
		cardDao.updateCard(cardId, card);
		
		
		verify(spy).setString(1, card.getTerm());
		verify(spy).setString(2, card.getDef());
		verify(spy).setInt(3, card.getCountCorrect());
		verify(spy).setInt(4, card.getCountWrong());
		verify(spy).setInt(5, cardId);
		verify(spy).executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select * from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, "test0");
		utilStmt.setString(2, "pass0");
		ResultSet rs = utilStmt.executeQuery();
		
		assertTrue(rs.next());
	}
	
	
	@Test
	public void readCardDefTest() throws SQLException {
		
		String expectedDef = "pass";
		try {
			String sql = "select def from \"Card\" where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQLException " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ?");
		utilStmt.setString(1, "test");
		
		ResultSet rsCardId = utilStmt.executeQuery();
		rsCardId.next();
		
		int cardId = rsCardId.getInt("card_id");

		
		String actualDef = cardDao.readCardDef(cardId);
		assertEquals(expectedDef, actualDef);
	}
	
	@Test
	public void readCardTermTest() throws SQLException {
		
		String expectedTerm = "test";
		try {
			String sql = "select term from \"Card\" where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQLException " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ?");
		utilStmt.setString(1, "test");
		
		ResultSet rsCardId = utilStmt.executeQuery();
		rsCardId.next();
		
		int cardId = rsCardId.getInt("card_id");

		
		String actualTerm = cardDao.readCardTerm(cardId);
		assertEquals(expectedTerm, actualTerm);
	}


	@Test
	public void deleteCardTest() throws SQLException {
		
		Entry term = new Entry("test");
		
		Entry def = new Entry("pass");
		
		Card card = new Card(term, def);
		
		try {
			String sql = "delete from \"Card\" where term = ? and def = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		cardDao.deleteCard(card);
		
		
		verify(spy).setString(1, card.getTerm());
		verify(spy).setString(2, card.getDef());
		verify(spy).executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select * from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, "test");
		utilStmt.setString(2, "pass");
		ResultSet rs = utilStmt.executeQuery();
		
		assertFalse(rs.next());
	}
}