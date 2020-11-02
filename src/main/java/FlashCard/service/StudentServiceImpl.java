package FlashCard.service;

import java.util.List;

import FlashCard.pojos.Student;

public class StudentServiceImpl implements StudentService {

	private CustomCacheService<Student> studentCache = new CustomCacheServiceSimpleInMemory<Student>();
	
	@Override
	public Student createStudent(String userName) {
		Student student = new Student(userName);
		studentCache.addToCache(student);
		return student;
	}

	@Override
	public List<Student> getAllStudents() {
		return studentCache.retrieveAllItems();
	}

	@Override
	public boolean containsStudent(Student student) {
		return studentCache.contains(student);
	}

	@Override
	public Student getStudentWithId(int id) {
		return studentCache.retrieveMatchingElt(p -> p.getUserId() == id);
	}

	@Override
	public boolean containsStudentWithId(int id) {
		return studentCache.containsMatchingElt(p -> p.getUserId() == id);
	}

	@Override
	public boolean containsStudentWithName(String userName) {
		return studentCache.containsMatchingElt(p -> p.getUserName() == userName);
	}

	@Override
	public Student getStudentWithUserName(String userName) {
		return studentCache.retrieveMatchingElt(p -> p.getUserName() == userName);
	}

}
