package FlashCard.pojos;

import java.util.List;

// A student (type of user) 
public class Student extends User {

	public Student(String userName) {
		super(userName, UserType.STUDENT);
	}

	public Student(String userName, List<Course> courses) {
		super(userName, UserType.STUDENT, courses);
	}
	
	public boolean isStudent(Course course) {
		return super.isEnrolled(course);
	}
}
