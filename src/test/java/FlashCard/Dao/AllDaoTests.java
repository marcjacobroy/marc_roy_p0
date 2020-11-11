package FlashCard.Dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CardDaoPostgresTest.class, CourseDaoPostgresTest.class, StudySetDaoPostgresTest.class,
		UserDaoPostgresTest.class })
public class AllDaoTests {

}
