package FlashCard.service;

import java.util.List;
import java.util.PriorityQueue;

import FlashCard.pojos.Card;
import FlashCard.pojos.StudySet;

public class StudySetServiceImpl implements StudySetService {

	private CustomCacheService<StudySet> studySetCache = new CustomCacheServiceSimpleInMemory<StudySet>();

	@Override
	public StudySet createStudySet(PriorityQueue<Card> cards) {
		StudySet studySet = new StudySet(cards);
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
