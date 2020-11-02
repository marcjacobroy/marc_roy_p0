package FlashCard.service;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FlashCard.pojos.Student;

public class CustomCacheServiceSimpleInMemoryTest {
	
	private CustomCacheServiceSimpleInMemory<Student> cacheService;
	
	static private Set<Student> testCache;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		testCache = new HashSet<>();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		Student student1 = new Student("marc");
		Student student2 = new Student("matt");
		Student student3 = new Student("mike");
		Student student4 = new Student("amy");
		
		testCache.add(student1);
		testCache.add(student2);
		testCache.add(student3);
		testCache.add(student4);
		
		cacheService = new CustomCacheServiceSimpleInMemory<Student>(testCache);

	}

	@After
	public void tearDown() throws Exception {
		
		testCache.clear();
		
	}

	@Test
	public void addToCacheSimpleGuestTest() {
		
		Student studentToAdd = new Student("Sally");
		
		cacheService.addToCache(studentToAdd);
		
		assertEquals("Guest " + studentToAdd + " should be in the cache", true, testCache.contains(studentToAdd));
		
	}

}