package FlashCard.pojos;

import java.util.List;

public class Student {
	private static int studentCount;
	
	private int studentId;
	
	private String studentName;
	
	private List<Course> courses; 
	
	private List<StudySet> sets;

	public Student(String studentName, List<Course> courses, List<StudySet> sets) {
		super();
		this.studentId = studentCount;
		studentCount++;
		this.studentName = studentName;
		this.courses = courses;
		this.sets = sets;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<StudySet> getSets() {
		return sets;
	}

	public void setSets(List<StudySet> sets) {
		this.sets = sets;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", courses=" + courses + ", sets="
				+ sets + "]";
	}
	
	
	
}
