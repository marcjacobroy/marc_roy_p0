package FlashCard.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import FlashCard.pojos.Course;
import FlashCard.util.ConnectionUtil;

public class CourseDaoPostgres implements CourseDao {
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil;
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createCourse(Course course) {
		String sql = "insert into \"Course\" (course_name) values(?)"; 
		
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, course.getCourseName());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String readCourse(int courseId) {
		String sql = "select \"Course\".course_name, \"AssignSSC\".study_set_id, "
				+ "\"StudySet\".study_set_name from \"Course\", \"AssignSSC\", \"StudySet\"where "
				+ "\"Course\".course_id = \"AssignSSC\".course_id and \"StudySet\".study_set_id = "
				+ "\"AssignSSC\".study_set_id and \"Course\".course_id = ?";
		
		String output = "";
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, courseId);
			ResultSet rsStudySets = stmt.executeQuery();
			while(rsStudySets.next()) {
				String studySetName = rsStudySets.getString("study_set_name") + ", ";
				output.concat(studySetName);
			}
			return output;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
	}

	@Override
	public void renameCourse(int courseId, String newName) {
		String sql = "update \"Course\" set course_name = ? where course_id = ?";
		
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newName);
			stmt.setInt(2, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCourse(int courseId) {
		String sql = "delete from \"Course\" where course_id = ?";

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}