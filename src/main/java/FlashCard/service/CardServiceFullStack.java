package FlashCard.service;

import FlashCard.pojos.Card;
import FlashCard.Dao.CardDao;
import FlashCard.Dao.CardDaoPostgres;

public class CardServiceFullStack implements CardService {
	
	CardDao cardDao = new CardDaoPostgres();
	
	@Override
	public void createCard(Card card) {
		cardDao.createCard(card);
	}

	@Override
	public String readCardDef(int cardId) {
		return cardDao.readCardDef(cardId);
	}

	@Override
	public String readCardTerm(int cardId) {
		return cardDao.readCardTerm(cardId);
	}

	@Override
	public void updateCard(int cardId, Card card) {
		cardDao.updateCard(cardId, card);
	}

	@Override
	public void deleteCard(int cardId) {
		cardDao.deleteCard(cardId);
	}
}
