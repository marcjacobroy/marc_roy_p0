package FlashCard.pojos;

import java.util.List;

public class Instructor {
	private static int instructorCount;
	
	private int instructorId; 
	
	private List<Course> courses; 
	
	private String instructorName;
	
	public Instructor(List<Course> courses, String instructorName) {
		super();
		this.courses = courses;
		this.instructorName = instructorName;
	}

	public static int getInstructorCount() {
		return instructorCount;
	}

	public static void setInstructorCount(int instructorCount) {
		Instructor.instructorCount = instructorCount;
	}

	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	@Override
	public String toString() {
		return "Instructor [instructorId=" + instructorId + ", courses=" + courses + ", instructorName="
				+ instructorName + "]";
	}
	
	
	
}
