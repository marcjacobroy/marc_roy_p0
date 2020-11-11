package FlashCard.service;

import FlashCard.pojos.Card;

public interface CardService {
	
	public void createCard(Card card);
	
	public String readCardDef(int cardId);
	
	public String readCardTerm(int cardId);
	
	public double readCardScore(int cardId);
	
	public void updateCardEntries(int cardId, Card card);
	
	public void updateCardScore(int cardId, boolean res);
	
	public void deleteCard(int cardId);
}
