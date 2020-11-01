package FlashCard.pojos;

import java.util.List;

public class Course {
	private static int courseCount;
	
	private int courseId; 
	
	private String courseName; 
	
	private List<StudySet> studySets;
	
	private Instructor instructor;
	
	
	public Course(String courseName, Instructor instructor) {
		super();
		this.instructor = instructor;
		this.courseName = courseName;
		this.courseId = courseCount;
		courseCount++;
	}
	
	
	public Instructor getInstructor() {
		return instructor;
	}


	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}


	public void addStudySet(StudySet studySet) {
		this.studySets.add(studySet);
	}

	public static int getCourseCount() {
		return courseCount;
	}

	public static void setCourseCount(int courseCount) {
		Course.courseCount = courseCount;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<StudySet> getStudySets() {
		return studySets;
	}

	public void setSets(List<StudySet> studySets) {
		this.studySets = studySets;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", studySets=" + studySets + "]";
	}
}
