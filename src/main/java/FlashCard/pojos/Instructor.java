package FlashCard.pojos;

import java.util.List;
import FlashCard.pojos.User.UserType;

public class Instructor extends User {

	public Instructor(String userName, List<Course> courses) {
		super(userName, UserType.INSTRUCTOR, courses);
	}
	
	public Instructor(String userName) {
		super(userName, UserType.INSTRUCTOR);
	}
	
	public boolean isInstructor(Course course) {
		return this.getCourses().contains(course);
	}
}
