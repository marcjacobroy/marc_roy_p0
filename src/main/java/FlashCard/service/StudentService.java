package FlashCard.service;

import java.util.List;
import FlashCard.pojos.Student;

public interface StudentService {
	public Student createStudent(String userName);
	
	public List<Student> getAllStudents();
	
	public boolean containsStudent (Student student);
	
	public boolean containsStudentWithId(int id);
	
	public boolean containsStudentWithName(String userName);
	
	public Student getStudentWithId (int id); 
	
	public Student getStudentWithUserName (String userName);
}