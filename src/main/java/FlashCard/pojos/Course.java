package FlashCard.pojos;

import java.util.List;

public class Course {
	private static int courseCount;
	
	private int courseId; 
	
	private String courseName; 
	
	private List<StudySet> studySets;
	
	
	
	
	public Course(String courseName) {
		super();
		this.courseName = courseName;
		this.courseId = courseCount;
		courseCount++;
	}

	public Course(String courseName, List<StudySet> studySets) {
		this(courseName);
		this.studySets = studySets;
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
