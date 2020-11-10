package FlashCard.service;

import java.util.List;

import FlashCard.pojos.Card;
import FlashCard.Dao.CardDao;
import FlashCard.Dao.CardDaoPostgres;

public class CardServiceFullStack implements CardService {
	
	CardDao cardDao  = new CardDaoPostgres();
	
	@Override
	public Card createCard(Card card) {
		cardDao.createCard(card);
		return card;
	}

	@Override
	public List<Card> getAllCardsByStudySetId(int studySetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> getAllCardsByCourseId(int courseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
