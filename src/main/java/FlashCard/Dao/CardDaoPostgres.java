package FlashCard.Dao;

import FlashCard.pojos.Card;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;



import FlashCard.util.ConnectionUtil;

public class CardDaoPostgres implements CardDao {
	
	private Statement statement; 
	
	private ConnectionUtil connUtil = new ConnectionUtil();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createCard(Card card) {
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
		
		try (Connection conn = connUtil.createConnection()) {
			statement = conn.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public String readCardDef(int cardId){
		
		String sql = "select def from \"Card\" where card_id = " + Integer.toString(cardId);
			
		try (Connection conn = connUtil.createConnection()) {
			statement = conn.createStatement();
			ResultSet rsCardIdDef = statement.executeQuery(sql);
			rsCardIdDef.next();
			String def = rsCardIdDef.getString("def");
			return def;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@Override
	public String readCardTerm(int cardId) {
		String sql = "select term from \"Card\" where card_id = " + Integer.toString(cardId);
		
		try (Connection conn = connUtil.createConnection()) {
			statement = conn.createStatement();
			ResultSet rsCardIdTerm = statement.executeQuery(sql);
			rsCardIdTerm.next();
			String term = rsCardIdTerm.getString("term");
			return term;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Card readAllCards() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCard(int cardId, Card card) {
		
		String sqlUpdate = "update \"Card\" set term = '" + card.getTerm()
				+ "', def = '" + card.getDef()
				+ "', count_correct = " + 0
				+ ", count_wrong = " + 0 
				+ " where card_id = " + Integer.toString(cardId)
				+ ";";
		
		try (Connection conn = connUtil.createConnection()) {
			statement = conn.createStatement();
			statement.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void deleteCard(Card card) {
		String sqlUpdate = "delete from \"Card\" where term = '" 
				+ card.getTerm() + "' AND def = '" + card.getDef() 
				+ "';";

		try (Connection conn = connUtil.createConnection()) {
			statement = conn.createStatement();
			statement.executeUpdate(sqlUpdate);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
