package FlashCard.Dao;

import FlashCard.pojos.Card;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import FlashCard.util.ConnectionUtil;

public class CardDaoPostgres implements CardDao {
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil = new ConnectionUtil();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}

	@Override
	public void createCard(Card card) {
		String sql = "insert into \"Card\" (count_correct, count_wrong, term, def) values(?, ?, ?, ?)"; 
		
		if (card.getCountCorrect() < 0 || card.getCountWrong() < 0 || card.getTerm() == null || card.getDef() == null) {
			throw new IllegalArgumentException("Please enter valid data for the card.");
		}
		
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, card.getCountCorrect());
			stmt.setInt(2,  card.getCountWrong());
			stmt.setString(3,  card.getTerm());
			stmt.setString(4,  card.getDef());
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
			if (rsDef.next()) {
				String def = rsDef.getString("def");
				return def;
			} else {
				throw new IllegalArgumentException("Card with id " + cardId + " does not exist");
			}
			

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
			if (rsTerm.next()) {
				String term = rsTerm.getString("term");
				return term;
			} else {
				throw new IllegalArgumentException("Card with id " + cardId + " does not exist");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateCard(int cardId, Card card) {
		
		String sql = "update \"Card\" set term = ?, def = ?, count_correct = ?, count_wrong = ? where card_id = ?";
		
		if (cardExists(cardId)) {
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
		} else {
			throw new IllegalArgumentException("Card with id " + cardId + " does not exist");
		}
		
	}
		
	
	@Override
	public void deleteCard(int cardId) {
		String sql = "delete from \"Card\" where card_id = ?";
		
		if (cardExists(cardId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, cardId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			throw new IllegalArgumentException("Card with id " + cardId + " does not exist");
		}
	}
	
	public static boolean cardExists(int cardId) {
		PreparedStatement stmt; 
		ConnectionUtil connUtil = new ConnectionUtil();
		String verify = "select * from \"Card\" where card_id = ?";
		try (Connection conn = connUtil.createConnection()){
			stmt = conn.prepareStatement(verify);
			stmt.setInt(1, cardId);
			ResultSet rs = stmt.executeQuery();
			if (!rs.next()) {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}
