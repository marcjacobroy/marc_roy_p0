package FlashCard.Dao;

import java.sql.Statement;

import FlashCard.pojos.Course;
import FlashCard.util.ConnectionUtil;

public class CourseDaoPostgres implements CourseDao {

	private Statement statement; 
	
	private ConnectionUtil connUtil = new ConnectionUtil();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}
	
	@Override
	public void createCourse(Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public String readCourse(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCourse(int courseId, Course course) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCourse(Course course) {
		// TODO Auto-generated method stub

	}

}
