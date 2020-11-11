package FlashCard.service;

import FlashCard.pojos.StudySet;
import FlashCard.Dao.StudySetDao;
import FlashCard.Dao.StudySetDaoPostgres;

public class StudySetServiceFullStack implements StudySetService {

	StudySetDao studySetDao = new StudySetDaoPostgres();
	@Override
	public void createStudySet(StudySet studySet) {
		studySetDao.createStudySet(studySet);
	}

	@Override
	public String readStudySetCards(int studySetId) {
		return studySetDao.readStudySetCards(studySetId);
	}

	@Override
	public String readStudySetName(int studySetId) {
		return studySetDao.readStudySetName(studySetId);
	}

	@Override
	public void renameStudySet(int studySetId, String newName) {
		studySetDao.renameStudySet(studySetId, newName);
	}

	@Override
	public void deleteStudySet(int studySetId) {
		studySetDao.deleteStudySet(studySetId);
	}

	@Override
	public void assignCardToStudySet(int cardId, int studySetId) {
		studySetDao.assignCardToStudySet(cardId, studySetId);
	}

}
