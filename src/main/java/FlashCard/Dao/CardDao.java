package FlashCard.Dao;

import java.sql.SQLException;

import FlashCard.pojos.Card;

public interface CardDao {
	
	public void createCard(Card card);
	
	public String readCardDef(int cardId);
	
	public String readCardTerm(int cardId);
	
	public Card readAllCards();
	
	public void updateCard(int cardId, Card card);
	
	public void deleteCard(Card card);
	
}
