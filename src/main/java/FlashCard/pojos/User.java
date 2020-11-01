package FlashCard.pojos;

import java.util.List; 

public class User {
	
	public enum UserType{
		STUDENT,
		INSTRUCTOR
	}
	
	private static int userCount;
	
	private int userId;
	
	private String userName;
	
	private List<Course> courses;
	
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

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
}
