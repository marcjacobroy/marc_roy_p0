package FlashCard.service;

import java.util.List;

import FlashCard.pojos.StudySet;


public interface StudySetService {
	
	public StudySet createStudySet(String studySetName);
	
	public List<StudySet> getAllStudySets();
	
	public boolean containsStudySet (StudySet studySet);
	
	public boolean containsStudySetWithId(int id);
	
	public StudySet getStudySetWithId (int id); 
}
