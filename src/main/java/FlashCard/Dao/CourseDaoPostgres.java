package FlashCard.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import FlashCard.pojos.Course;
import FlashCard.util.ConnectionUtil;

public class CourseDaoPostgres implements CourseDao {
	
	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil = new ConnectionUtil();
	
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
	public String readCourseStudySets(int courseId) {
		String sql = "select \"Course\".course_name, \"AssignSSC\".study_set_id, "
				+ "\"StudySet\".study_set_name from \"Course\", \"AssignSSC\", \"StudySet\"where "
				+ "\"Course\".course_id = \"AssignSSC\".course_id and \"StudySet\".study_set_id = "
				+ "\"AssignSSC\".study_set_id and \"Course\".course_id = ?";
		
		String output = "";
		if (courseExists(courseId)) {
			try {
				Connection connection = connUtil.createConnection();
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, courseId);
				ResultSet rsStudySets = stmt.executeQuery();
				while (rsStudySets.next()) {
					String studySetName = rsStudySets.getString("study_set_name") + ", ";
					output = output.concat(studySetName);
				}
				return output;

			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Course " + courseId + " does not exist");
		}
		return output;
	}

	@Override
	public void renameCourse(int courseId, String newName) {
		String sql = "update \"Course\" set course_name = ? where course_id = ?";
		
		if (courseExists(courseId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, newName);
				stmt.setInt(2, courseId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Course " + courseId + " does not exist");
		}
	}

	@Override
	public void deleteCourse(int courseId) {
		String sql = "delete from \"Course\" where course_id = ?";

		if (courseExists(courseId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, courseId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Course " + courseId + " does not exist");
		}
	}

	@Override
	public void assignStudySetToCourse(int studySetId, int courseId) {
		String sql = "insert into \"AssignSSC\" (study_set_id, course_id) values(?, ?)";

		if (courseExists(courseId) && StudySetDaoPostgres.studySetExists(studySetId)) {
			try (Connection conn = connUtil.createConnection()) {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, studySetId);
				stmt.setInt(2, courseId);
				stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} else {
			throw new IllegalArgumentException("Course " + courseId + " does not "
					+ "exist, and/or study set " + studySetId + " does not exist");
		}
	}

	@Override
	public String readCourseName(int courseId) {
		String sql = "select course_name from \"Course\" where course_id = ?";
		
		String output = null;
		
		if (courseExists(courseId)) {
			try {
				Connection connection = connUtil.createConnection();
				stmt = connection.prepareStatement(sql);
				stmt.setInt(1, courseId);
				ResultSet rsCourseName = stmt.executeQuery();
				rsCourseName.next();
				String courseName = rsCourseName.getString("course_name");
				output = courseName;

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return output;
		} else {
			throw new IllegalArgumentException("Course " + courseId + " does not exist");
		}
	}
	
	public static boolean courseExists(int courseId) {
		PreparedStatement stmt; 
		ConnectionUtil connUtil = new ConnectionUtil();
		String verify = "select * from \"Course\" where course_id = ?";
		try (Connection conn = connUtil.createConnection()){
			stmt = conn.prepareStatement(verify);
			stmt.setInt(1, courseId);
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