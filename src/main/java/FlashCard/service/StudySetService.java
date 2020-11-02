package FlashCard.service;

import java.util.List;

import FlashCard.pojos.StudySet;
import FlashCard.pojos.Card;
import java.util.PriorityQueue;

public interface StudySetService {
	
	public StudySet createStudySet(PriorityQueue<Card> cards);
	
	public List<StudySet> getAllStudySets();
	
	public boolean containsStudySet (StudySet studySet);
	
	public boolean containsStudySetWithId(int id);
	
	public StudySet getStudySetWithId (int id); 
}
