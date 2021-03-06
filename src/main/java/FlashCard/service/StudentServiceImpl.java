package FlashCard.service;

import java.util.List;
import FlashCard.pojos.Student;

// implementation of cache used for storing students 
public class StudentServiceImpl implements StudentService {

	private CustomCacheService<Student> studentCache = new CustomCacheServiceSimpleInMemory<Student>();
	
	
	public StudentServiceImpl() {
		super();
	}
	public StudentServiceImpl(CustomCacheService<Student> studentCache) {
		this();
		this.studentCache = studentCache;
	}
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
		return studentCache.containsMatchingElt(p -> p.getUserName().equals(userName));
	}

	@Override
	public Student getStudentWithUserName(String userName) {
		return studentCache.retrieveMatchingElt(p -> p.getUserName().equals(userName));
	}

	@Override
	public List<String> toStringList() {
		return studentCache.toStringList();
	}
	
}
