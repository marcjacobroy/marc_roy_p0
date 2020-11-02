package FlashCard.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import FlashCard.pojos.StudySet;

// test methods of study set cache 
@RunWith(MockitoJUnitRunner.class)
public class StudySetServiceImplTest {
	
	private StudySetServiceImpl studySetService;
	
	@Mock
	private CustomCacheServiceSimpleInMemory<StudySet> customCache; 
	
	List<StudySet> studySetList;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		studySetList = new ArrayList<StudySet>();
		
		StudySet studySet1 = new StudySet("1");
		StudySet studySet2 = new StudySet("2");
		StudySet studySet3 = new StudySet("3");
		StudySet studySet4 = new StudySet("4");
		
		studySetList.add(studySet1);
		studySetList.add(studySet2);
		studySetList.add(studySet3);
		studySetList.add(studySet4);
		
		when((customCache.retrieveAllItems())).thenReturn(studySetList);
		studySetService = new StudySetServiceImpl(customCache);
	}

	@After
	public void tearDown() throws Exception {
		studySetList.clear();
	}

	@Test
	public void createstudySetTest() {
		StudySet teststudySet = studySetService.createStudySet("test");
		assertEquals("Should create studySet object", 4, teststudySet.getStudySetId());
		verify(customCache).addToCache(teststudySet);
	}
	
	@Test
	public void getstudySetListTest() {
		assertEquals("Should return full list of studySets", studySetList, studySetService.getAllStudySets());
	}
	

}

