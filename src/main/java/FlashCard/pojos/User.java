package FlashCard.pojos;

import java.util.List; 
import java.util.ArrayList;

// A user, either a student or an instructor 
public class User {
	
	public enum UserType{
		STUDENT,
		INSTRUCTOR
	}
	
	private static int userCount;
	
	private int userId;
	
	private String userName;
	
	private List<Course> courses = new ArrayList<Course>();
	
	private UserType userType;

	public User(String userName, UserType userType) {
		super();
		this.userName = userName;
		this.userType = userType;
		this.userId = userCount;
		userCount++;
	}
	
	public User(String userName, UserType userType, List<Course> courses) {
		this(userName, userType);
		this.courses = courses;
	}
	
	public boolean isEnrolled(Course course) {
		return courses.contains(course);
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public void addCourse(Course course) {
		this.courses.add(course);
	}

	public String getUserType() {
		if (userType == UserType.INSTRUCTOR) {
			return "Instructor";
		} else {
			return "Student";
		}
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", courses=" + courses + ", userType=" + userType
				+ "]";
	}
	
	
}
