package FlashCard.service;

import java.util.List;
import FlashCard.pojos.Instructor;
// implementation of cache storing instructors 
public class InstructorServiceImpl implements InstructorService {
	
	private CustomCacheService<Instructor> instructorCache = new CustomCacheServiceSimpleInMemory<Instructor>();
	
	public InstructorServiceImpl() {
		super();
	}
	public InstructorServiceImpl(CustomCacheService<Instructor> instructorCache) {
		this();
		this.instructorCache = instructorCache;
	}
	@Override
	public Instructor createInstructor(String userName) {
		Instructor instructor = new Instructor(userName);
		instructorCache.addToCache(instructor);
		return instructor;
	}

	@Override
	public List<Instructor> getAllInstructors() {
		return instructorCache.retrieveAllItems();
	}

	@Override
	public Instructor getInstructorWithId(int id) {
		return instructorCache.retrieveMatchingElt(p -> p.getUserId() == id);
	}

	@Override
	public boolean containsInstructorWithId(int id) {
		return instructorCache.containsMatchingElt(p -> p.getUserId() == id);
	}

	@Override
	public boolean containsInstructorWithName(String userName) {
		return instructorCache.containsMatchingElt(p -> p.getUserName().equals(userName));
	}

	@Override
	public Instructor getInstructorWithUserName(String userName) {
		return instructorCache.retrieveMatchingElt(p -> p.getUserName().equals(userName));
	}

	@Override
	public List<String> toStringList() {
		return instructorCache.toStringList();
	}

}
