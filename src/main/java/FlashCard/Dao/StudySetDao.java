package FlashCard.Dao;

import FlashCard.pojos.StudySet;
public interface StudySetDao {
	
	public void createStudySet(StudySet studySet);
	
	public String readStudySetCards(int studySetId);
	
	public String readStudySetName(int studySetId);
	
	public void renameStudySet(int studySetId, String newName);
	
	public void deleteStudySet(int studySetId);
	
	public void assignCardToStudySet(int cardId, int studySetId);
	
	public String getCardWithMinScoreFromStudySet(int studySetId);

}
