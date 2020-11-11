package FlashCard.Dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import FlashCard.pojos.User;
import FlashCard.util.ConnectionUtil;

public class UserDaoPostgres implements UserDao {

	private PreparedStatement stmt; 
	
	private ConnectionUtil connUtil;
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createUser(User user) {
		String sql = "insert into \"User\" (user_name, user_type) values(?, ?)"; 
		
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getUserType());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String readUserCourses(int userId) {
		String sql = "select \"Course\".course_name, \"User\".user_name from \"Course\", \"User\", \"Enrollment\" where \n"
				+ "\"Course\".course_id = \"Enrollment\".course_id and \"User\".user_id = \n"
				+ "\"Enrollment\".user_id and \"User\".user_id = ?;";
		
		String output = "";
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rsCourses = stmt.executeQuery();
			while(rsCourses.next()) {
				String courseName = rsCourses.getString("course_name") + ", ";
				output.concat(courseName);
			}
			return output;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
		
	}
	
	@Override
	public String readUserName(int userId) {
		String sql = "select user_name from \"User\" where user_id = ?";
		
		String output = null;
		try {
			Connection connection = connUtil.createConnection();
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rsUserName = stmt.executeQuery();
			rsUserName.next();
			String userName = rsUserName.getString("user_name");
			output = userName;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return output;
		
	}
	@Override
	public void renameUser(int userId, String newName) {
		String sql = "update \"User\" set user_name = ? where user_id = ?";
		
		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newName);
			stmt.setInt(2, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void assignCourseToUser(int userId, int courseId) {
		String sql = "insert into \"Enrollment\" (user_id, course_id) values(?, ?)";

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.setInt(2, courseId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteUser(int userId) {
		String sql = "delete from \"User\" where user_id = ?";

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}