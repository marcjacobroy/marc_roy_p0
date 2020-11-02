package FlashCard.service;

import java.util.List;

import FlashCard.pojos.StudySet;

public class StudySetServiceImpl implements StudySetService {

	private CustomCacheService<StudySet> studySetCache = new CustomCacheServiceSimpleInMemory<StudySet>();

	@Override
	public StudySet createStudySet() {
		StudySet studySet = new StudySet();
		studySetCache.addToCache(studySet);
		return studySet;
	}

	@Override
	public List<StudySet> getAllStudySets() {
		return studySetCache.retrieveAllItems();
	}

	@Override
	public boolean containsStudySet(StudySet studySet) {
		return studySetCache.contains(studySet);
	}

	@Override
	public boolean containsStudySetWithId(int id) {
		return studySetCache.containsMatchingElt(p -> p.getStudySetId() == id);
	}

	@Override
	public StudySet getStudySetWithId(int id) {
		return studySetCache.retrieveMatchingElt(p -> p.getStudySetId() == id);
	}

}
