package FlashCard.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

@RunWith(MockitoJUnitRunner.class)
public class CardDaoPostgresPreparedStatementTest {

	public CardDaoPostgres cardDao = new CardDaoPostgres();
	
	@Mock
	private ConnectionUtil connUtil;
	
	@Mock
	private Connection connection;
	
	@Mock
	private PreparedStatement stmt;
	
	@Mock 
	private ResultSet rs;
	
	private Card c;
	
//	private Statement spy;
//	
//	private Connection realConnection;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		assertNotNull(connUtil);
		when(connection.prepareStatement(any(String.class))).thenReturn(stmt);
		when(connUtil.createConnection()).thenReturn(connection);
		
		c = new Card(new Entry("test"), new Entry("pass"));
		
		when(rs.first()).thenReturn(true);
		when(rs.getInt(1)).thenReturn(1);
		when(rs.getInt(2)).thenReturn(0);
		when(rs.getInt(3)).thenReturn(0);
		when(rs.getString(4)).thenReturn(c.getTerm());
		when(rs.getString(5)).thenReturn(c.getDef());
		when(rs.getInt(6)).thenReturn(1);
		when(stmt.executeQuery()).thenReturn(rs);
	}
	
	@Test(expected=NullPointerException.class)
	public void nullCreateThrowsException() {
		CardDaoPostgres cardDao = new CardDaoPostgres();
		cardDao.setConnUtil(connUtil);
		cardDao.createCard(null);
	}
	
	@Test
	public void createCardTest() {
		CardDaoPostgres cardDao = new CardDaoPostgres();
		cardDao.setConnUtil(connUtil);
		cardDao.createCard(c);
	}
	
}
