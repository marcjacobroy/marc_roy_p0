package FlashCard.pojos;

import java.util.List;
import FlashCard.pojos.User.UserType;

public class Student extends User {
	
	private List<StudySet> sets;

	public Student(String userName) {
		super(userName, UserType.STUDENT);
	}

	public Student(String userName, List<Course> courses) {
		super(userName, UserType.STUDENT, courses);
	}

	public Student(String userName, List<Course> courses, List<StudySet> sets) {
		this(userName, courses);
		this.sets = sets;
	}
}
