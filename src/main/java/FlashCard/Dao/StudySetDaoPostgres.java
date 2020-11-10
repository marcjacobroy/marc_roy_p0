package FlashCard.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import FlashCard.pojos.StudySet;
import FlashCard.util.ConnectionUtil;

public class StudySetDaoPostgres implements StudySetDao {
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil;
	
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
	public String readStudySet(int studySetId) {
		String sql = "select term, def from \"Card\" where study_set_id = ?";
		String output = "";
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, studySetId);
			ResultSet rsCards = stmt.executeQuery();
			while(rsCards.next()) {
				String cardText = rsCards.getString("term") + " " + rsCards.getString("def") + "\n";
				output.concat(cardText);
			}
			return output;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	@Override
	public void renameStudySet(int studySetId, String newName) {
		String sql = "update \"StudySet\" set study_set_name = ? where study_set_id = ?";
		
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newName);
			stmt.setInt(2, studySetId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteStudySet(int studySetId) {
		String sql = "delete from \"StudySet\" where study_set_id = ?";

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, studySetId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
