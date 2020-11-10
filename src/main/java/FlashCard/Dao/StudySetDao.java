package FlashCard.Dao;

import FlashCard.pojos.StudySet;
import FlashCard.pojos.Card;
public interface StudySetDao {
	
	public void createStudySet(StudySet studySet);
	
	public String readStudySet(int studySetId);
	
	public void renameStudySet(int studySetId, String newName);
	
	public void deleteStudySet(int studySetId);
}
