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
	
	private static final String TEST_TERM = "no one will ever use this 12r1 :SD:flabsd )#@#ub";
	private static final String TEST_DEF = "also nobody gon use thias 2;3rq;jn;lkSj08(@";
	private int TEST_CARD_ID;
	
	private static final String TEST_TERM2 = "really not likely 134;ln :LK3se G[W ";
	private static final String TEST_DEF2 = "also not nlieky likely 123r :LN3";
	
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
		
		
		utilStmt = realConnection.prepareStatement("insert into \"Card\" (count_correct, count_wrong, term, def) values(?, ?, ?, ?)");
		utilStmt.setInt(1, 0);
		utilStmt.setInt(2, 0);
		utilStmt.setString(3, TEST_TERM);
		utilStmt.setString(4, TEST_DEF);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, TEST_TERM);
		utilStmt.setString(2, TEST_DEF);
		ResultSet rs = utilStmt.executeQuery();
		rs.next();
		TEST_CARD_ID = rs.getInt("card_id");
		
		
	}

	@After
	public void tearDown() throws Exception {

		utilStmt = realConnection.prepareStatement("delete from \"Card\" where term = ? AND def = ?");
		utilStmt.setString(1, TEST_TERM2);
		utilStmt.setString(2, TEST_DEF2);
		utilStmt.executeUpdate();
		
		utilStmt = realConnection.prepareStatement("delete from \"Card\" where term = ? AND def = ?");
		utilStmt.setString(1, TEST_TERM);
		utilStmt.setString(2, TEST_DEF);
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
		
		Entry term = new Entry(TEST_TERM2);
		
		Entry def = new Entry(TEST_DEF2);
		
		Card card = new Card(term, def);
		
		try {
			 String sql = "insert into \"Card\" (count_correct, count_wrong, term, def) values(?, ?, ?, ?)";
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
			verify(spy).executeUpdate();
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
	}

	@Test
	public void updateCardTest() throws SQLException {
		
		Entry term = new Entry(TEST_TERM2);
		
		Entry def = new Entry(TEST_DEF2);
		
		Card card = new Card(term, def);
		
		try {
			String sql = "update \"Card\" set term = ?, def = ?, count_correct = ?, count_wrong = ? where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, TEST_TERM);
		utilStmt.setString(2, TEST_DEF);
		
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
		utilStmt.setString(1, TEST_TERM2);
		utilStmt.setString(2, TEST_DEF2);
		ResultSet rs = utilStmt.executeQuery();
		
		assertTrue(rs.next());
	}
	
	
	@Test
	public void readCardDefTest() throws SQLException {
		
		String expectedDef = TEST_DEF;
		try {
			String sql = "select def from \"Card\" where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQLException " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ?");
		utilStmt.setString(1, TEST_TERM);
		
		ResultSet rsCardId = utilStmt.executeQuery();
		rsCardId.next();
		
		int cardId = rsCardId.getInt("card_id");

		
		String actualDef = cardDao.readCardDef(cardId);
		assertEquals(expectedDef, actualDef);
	}
	
	@Test
	public void readCardTermTest() throws SQLException {
		
		String expectedTerm = TEST_TERM;
		try {
			String sql = "select term from \"Card\" where card_id = ?";
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQLException " + e);
		}
		
		utilStmt = realConnection.prepareStatement("select card_id from \"Card\" where term = ?");
		utilStmt.setString(1, TEST_TERM);
		
		ResultSet rsCardId = utilStmt.executeQuery();
		rsCardId.next();
		
		int cardId = rsCardId.getInt("card_id");

		
		String actualTerm = cardDao.readCardTerm(cardId);
		assertEquals(expectedTerm, actualTerm);
	}


	@Test
	public void deleteCardTest() throws SQLException {
		
		try {
			String sql = "delete from \"Card\" where card_id = ?";;
			initStmtHelper(sql);
		} catch(SQLException e) {
			fail("SQL exception thrown: " + e);
		}
		
		cardDao.deleteCard(TEST_CARD_ID);
		
		
		verify(spy).setInt(1, TEST_CARD_ID);
		verify(spy).executeUpdate();
		
		utilStmt = realConnection.prepareStatement("select * from \"Card\" where term = ? and def = ?");
		utilStmt.setString(1, TEST_TERM);
		utilStmt.setString(2, TEST_DEF);
		ResultSet rs = utilStmt.executeQuery();
		
		assertFalse(rs.next());
	}
}