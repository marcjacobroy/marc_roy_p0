package FlashCard.service;

import FlashCard.pojos.Card;

public interface CardService {
	
	public void createCard(Card card);
	
	public String readCardDef(int cardId);
	
	public String readCardTerm(int cardId);
	
	public void updateCard(int cardId, Card card);
	
	public void deleteCard(int cardId);
}
