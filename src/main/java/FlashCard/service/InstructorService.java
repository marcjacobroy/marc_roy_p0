package FlashCard.service;

import java.util.List;
import FlashCard.pojos.Instructor;

public interface InstructorService {
	public Instructor createInstructor(String userName);
	
	public List<Instructor> getAllInstructors();
	
	public boolean containsInstructorWithId(int id);
	
	public boolean containsInstructorWithName(String userName);
	
	public Instructor getInstructorWithId (int id); 
	
	public Instructor getInstructorWithUserName (String userName);
	
	public List<String> toStringList();
}
