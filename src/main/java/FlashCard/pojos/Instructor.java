package FlashCard.pojos;

// An instructor (type of user) 
import java.util.List;


public class Instructor extends User {

	public Instructor(String userName, List<Course> courses) {
		super(userName, UserType.INSTRUCTOR, courses);
	}
	
	public Instructor(String userName) {
		super(userName, UserType.INSTRUCTOR);
	}
	
	public boolean isInstructor(Course course) {
		return super.isEnrolled(course);
	}
}
