package FlashCard.service;

import FlashCard.pojos.StudySet;
import org.apache.log4j.Logger;
import FlashCard.Dao.StudySetDao;
import FlashCard.Dao.StudySetDaoPostgres;

//currently, simply calls on Dao methods, intermediary between Controllers and Daos 
public class StudySetServiceFullStack implements StudySetService {
	
	private static Logger log = Logger.getRootLogger();

	StudySetDao studySetDao = new StudySetDaoPostgres();
	
	@Override
	public void createStudySet(StudySet studySet) {
		log.trace("Calling createStudySet in StudySetServiceFullStack on " + studySet.toString());
		studySetDao.createStudySet(studySet);
	}

	@Override
	public String readStudySetCards(int studySetId) {
		log.trace("Calling readStudySetCards in StudySetServiceFullStack on " + studySetId);
		return studySetDao.readStudySetCards(studySetId);
	}

	@Override
	public String readStudySetName(int studySetId) {
		log.trace("Calling readStudySetName in StudySetServiceFullStack on " + studySetId);
		return studySetDao.readStudySetName(studySetId);
	}

	@Override
	public void renameStudySet(int studySetId, String newName) {
		log.trace("Calling renameStudySet in StudySetServiceFullStack on " + studySetId + " " + newName);
		studySetDao.renameStudySet(studySetId, newName);
	}

	@Override
	public void deleteStudySet(int studySetId) {
		log.trace("Calling deleteStudySet in StudySetServiceFullStack on " + studySetId);
		studySetDao.deleteStudySet(studySetId);
	}

	@Override
	public void assignCardToStudySet(int cardId, int studySetId) {
		log.trace("Calling assignCardToStudySet in StudySetServiceFullStack on " + cardId + " "  + studySetId);
		studySetDao.assignCardToStudySet(cardId, studySetId);
	}

	@Override
	public String getCardWithMinScoreFromStudySet(int studySetId) {
		log.trace("Calling getCardWithMinScoreFromStudySet in StudySetServiceFullStack on " + studySetId);
		return studySetDao.getCardWithMinScoreFromStudySet(studySetId);
	}

}
