package FlashCard.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// Test suite for FlashCard.service package 
@RunWith(Suite.class)
@SuiteClasses({ CourseServiceImplTest.class, CustomCacheServiceSimpleInMemoryTest.class,
		InstructorServiceImplTest.class, StudentServiceImplTest.class, StudySetServiceImplTest.class })
public class AllServiceTests {

}
