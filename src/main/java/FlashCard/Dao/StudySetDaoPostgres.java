package FlashCard.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import FlashCard.pojos.StudySet;
import FlashCard.util.ConnectionUtil;

public class StudySetDaoPostgres implements StudySetDao {
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil = new ConnectionUtil();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createStudySet(StudySet studySet) {
		String sql = "insert into \"StudySet\" (study_set_name) values(?)"; 
		
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, studySet.getStudySetName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String readStudySetCards(int studySetId) {
		String sql = "select \"StudySet\".study_set_name, \"AssignCSS\".card_id, "
				+ "\"Card\".term, \"Card\".def from \"StudySet\", \"AssignCSS\", \"Card\"where "
				+ "\"StudySet\".study_set_id = \"AssignCSS\".study_set_id and \"Card\".card_id = "
				+ "\"AssignCSS\".card_id and \"StudySet\".study_set_id = ?";
		
		String output = "";
		if (studySetExists(studySetId)) {
			try {
				Connection connection = connUtil.createConnection();
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, studySetId);
				ResultSet rsStudySets = stmt.executeQuery();
				while (rsStudySets.next()) {
					String cardInfo = rsStudySets.getString("term") + ": " + rsStudySets.getString("def") + ", ";
					output = output.concat(cardInfo);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Study set " + studySetId + " does not exist");
		}
		return output;
	}

	@Override
	public void renameStudySet(int studySetId, String newName) {
		String sql = "update \"StudySet\" set study_set_name = ? where study_set_id = ?";
		
		if (studySetExists(studySetId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, newName);
				stmt.setInt(2, studySetId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Study set " + studySetId + " does not exist");
		}
	}

	@Override
	public void deleteStudySet(int studySetId) {
		String sql = "delete from \"StudySet\" where study_set_id = ?";

		if (studySetExists(studySetId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, studySetId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Study set " + studySetId + " does not exist");
		}

	}
	
	@Override
	public void assignCardToStudySet(int cardId, int studySetId) {
		String sql = "insert into \"AssignCSS\" (card_id, study_set_id) values(?, ?)";

		if (studySetExists(studySetId) && CardDaoPostgres.cardExists(cardId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, cardId);
				stmt.setInt(2, studySetId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Study set " + studySetId + " does not exist "
					+ "and/or card " + cardId + " does not exist");
		}
	}

	@Override
	public String readStudySetName(int studySetId) {
		String sql = "select study_set_name from \"StudySet\" where study_set_id = ?";
		
		String output = null;
		if (studySetExists(studySetId)) {
			try {
				Connection connection = connUtil.createConnection();
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, studySetId);
				ResultSet rsStudySetName = stmt.executeQuery();
				rsStudySetName.next();
				String studySetName = rsStudySetName.getString("study_set_name");
				output = studySetName;

			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Study set " + studySetId + " does not exist");
		}
		return output;
	}
	
	public static boolean studySetExists(int studySetId) {
		PreparedStatement stmt; 
		ConnectionUtil connUtil = new ConnectionUtil();
		String verify = "select * from \"StudySet\" where study_set_id = ?";
		try (Connection conn = connUtil.createConnection()){
			stmt = conn.prepareStatement(verify);
			stmt.setInt(1, studySetId);
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
