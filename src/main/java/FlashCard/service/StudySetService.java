package FlashCard.service;


import FlashCard.pojos.StudySet;

// structure for cache used for storing study sets 
public interface StudySetService {
	
	public void createStudySet(StudySet studySet);
	
	public String readStudySetCards(int studySetId);
	
	public String readStudySetName(int studySetId);
	
	public void renameStudySet(int studySetId, String newName);
	
	public void deleteStudySet(int studySetId);
	
	public void assignCardToStudySet(int cardId, int studySetId);
	
	public String getCardWithMinScoreFromStudySet(int studySetId);

}
