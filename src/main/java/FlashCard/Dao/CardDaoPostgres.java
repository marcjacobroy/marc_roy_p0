package FlashCard.Dao;

import FlashCard.pojos.Card;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



import FlashCard.util.ConnectionUtil;

public class CardDaoPostgres implements CardDao {
	
	private static final int DEFAULT_STUDYSET = 2;
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil;
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}

	@Override
	public void createCard(Card card) {
		String sql = "insert into \"Card\" (count_correct, count_wrong, term, def, study_set_id) values( ?, ?, ?, ?, ?)"; 
		
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			if (stmt == null) {
				System.out.println("null statement");
			}
			stmt.setInt(1, card.getCountCorrect());
			stmt.setInt(2,  card.getCountWrong());
			stmt.setString(3,  card.getTerm());
			stmt.setString(4,  card.getDef());
			stmt.setInt(5,  DEFAULT_STUDYSET);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String readCardDef(int cardId){
		
		String sql = "select def from \"Card\" where card_id = ?";
			
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cardId);
			ResultSet rsDef = stmt.executeQuery();
			rsDef.next();
			String def = rsDef.getString("def");
			return def;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
	@Override
	public String readCardTerm(int cardId){
		
		String sql = "select term from \"Card\" where card_id = ?";
			
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cardId);
			ResultSet rsTerm = stmt.executeQuery();
			rsTerm.next();
			String term = rsTerm.getString("term");
			return term;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateCard(int cardId, Card card) {
		
		String sql = "update \"Card\" set term = ?, def = ?, count_correct = ?, count_wrong = ? where card_id = ?";
		
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, card.getTerm());
			stmt.setString(2, card.getDef());
			stmt.setInt(3, card.getCountCorrect());
			stmt.setInt(4, card.getCountWrong());
			stmt.setInt(5, cardId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	@Override
	public void deleteCard(Card card) {
		String sql = "delete from \"Card\" where term = ? and def = ?";

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, card.getTerm());
			stmt.setString(2, card.getDef());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
