package FlashCard.service;

import FlashCard.pojos.Card;
import org.apache.log4j.Logger;
import FlashCard.Dao.CardDao;
import FlashCard.Dao.CardDaoPostgres;

public class CardServiceFullStack implements CardService {
	
	private static Logger log = Logger.getRootLogger();
	
	CardDao cardDao = new CardDaoPostgres();
	
	@Override
	public void createCard(Card card) {
		log.trace("Calling createCard in CardServiceFullStack on " + card.toString());
		cardDao.createCard(card);
	}

	@Override
	public String readCardDef(int cardId) {
		log.trace("Calling readCardDef in CardServiceFullStack on " + cardId);
		return cardDao.readCardDef(cardId);
	}

	@Override
	public String readCardTerm(int cardId) {
		log.trace("Calling readCardTerm in CardServiceFullStack on " + cardId);
		return cardDao.readCardTerm(cardId);
	}
	
	@Override
	public double readCardScore(int cardId) {
		log.trace("Calling readCardScore in CardServiceFullStack on " + cardId);
		return cardDao.readCardScore(cardId);
	}

	@Override
	public void updateCardEntries(int cardId, Card card) {
		log.trace("Calling updateCard in CardServiceFullStack on " + cardId + " " + card.toString());
		cardDao.updateCardEntries(cardId, card);
	}

	@Override
	public void deleteCard(int cardId) {
		log.trace("Calling deleteCard in CardServiceFullStack on " + cardId);
		cardDao.deleteCard(cardId);
	}

	@Override
	public void updateCardScore(int cardId, boolean res) {
		log.trace("Calling updateCardScore from CardServiceFullStack on " + cardId + " " + res);
		cardDao.updateCardScore(cardId, res);
		
	}

}
