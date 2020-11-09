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

import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.util.ConnectionUtil;

@RunWith(MockitoJUnitRunner.class)
public class CardDaoPostgresTest {

	public CardDaoPostgres cardDao = new CardDaoPostgres();
	
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
		cardDao.setConnUtil(connUtil);
		
		stmt.executeUpdate("insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) "
				+ "values("
				+ 0
				+ ", "
				+ 0
				+ ", '"
				+ "test"
				+ "', '"
				+ "pass"
				+ "', "
				+ 1
				+ ")");
	}

	@After
	public void tearDown() throws Exception {
		
		stmt.executeUpdate("delete from \"Card\" where term = 'test0' AND def = 'pass0'");
		stmt.executeUpdate("delete from \"Card\" where term = 'test' AND def = 'pass'");
		
		
		realConnection.close();
		
	}

	@Test
	public void createCardTest() throws SQLException {
		
		Entry term = new Entry("test0");
		
		Entry def = new Entry("pass0");
		
		Card card = new Card(term, def);
		
		cardDao.createCard(card);
		
		String sql = "insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) "
				+ "values("
				+ card.getCountCorrect()
				+ ", "
				+ card.getCountWrong()
				+ ", '"
				+ card.getTerm()
				+ "', '"
				+ card.getDef()
				+ "', "
				+ 1
				+ ")";
		
		verify(spy).executeUpdate(sql);
		
		ResultSet rs = stmt.executeQuery("select * from \"Card\" where term = 'test0' AND def = 'pass0'");
		
		assertTrue(rs.next());
		
	}
	
	@Test
	public void updateCardTest() throws SQLException {
		
		Entry term = new Entry("test0");
		
		Entry def = new Entry("pass0");
		
		Card card = new Card(term, def);
		
		ResultSet rsId = stmt.executeQuery("select card_id from \"Card\" where term = 'test' and def = 'pass'");
		
		rsId.next();
		
		int cardId = rsId.getInt("card_id");
		
		cardDao.updateCard(cardId, card);
		
		String sql = "update \"Card\" set term = 'test0', def = 'pass0', count_correct = 0, count_wrong = 0 where card_id = " + Integer.toString(cardId) + ";";
		
		verify(spy).executeUpdate(sql);
		
		ResultSet rs = stmt.executeQuery("select * from \"Card\" where term = 'test0' and def = 'pass0'");
		
		assertTrue(rs.next());
		
	}
	
	@Test
	public void readCardDefTest() throws SQLException {
		//TODO
		
		String expectedDef = "pass";
		
		ResultSet rsCardIdDef = stmt.executeQuery("select card_id from \"Card\" where term = 'test' and def = 'pass'");
		
		rsCardIdDef.next();
		
		int cardId = rsCardIdDef.getInt("card_id");
		
		String actualDef = cardDao.readCardDef(cardId);
		
		assertEquals(expectedDef, actualDef);
		
	}
	
	@Test
	public void readCardTermTest() throws SQLException {
		
		String expectedTerm = "test";
		
		ResultSet rsCardIdTerm = stmt.executeQuery("select card_id from \"Card\" where term = 'test' and def = 'pass'");
		
		rsCardIdTerm.next();
		
		int cardId = rsCardIdTerm.getInt("card_id");
		
		String actualTerm = cardDao.readCardTerm(cardId);
		
		assertEquals(expectedTerm, actualTerm);
	}
	
	@Test 
	public void deleteCardTest() throws SQLException {
		
		Entry term = new Entry("test");
		
		Entry def = new Entry("pass");
		
		Card card = new Card(term, def);
		
		cardDao.deleteCard(card);
		
		ResultSet rs = stmt.executeQuery("select card_id from \"Card\" where term = 'test' and def = 'pass'");
		
		assert(!rs.next());
	}
}