package FlashCard.controller;

import FlashCard.pojos.Card;
import FlashCard.pojos.Entry;
import FlashCard.service.CardService;
import FlashCard.service.CardServiceFullStack;
import io.javalin.http.Context;
import org.apache.log4j.Logger;

// Control interactions between parameters passed in by user and calls to FullStackService
public class CardController {
	
	private static Logger log = Logger.getRootLogger();
	
	CardService cardService = new CardServiceFullStack();
	
	
	// Ensure no empty strings are passed in as entries, then create card 
	public void createCard(Context ctx) {
		
		log.trace("Entering createCard in CardController");
		
		String term = ctx.formParam("term");
		
		String def = ctx.formParam("def");
		
		Entry term_side = new Entry(term);
		
		Entry def_side = new Entry(def);
		
		
		if (term.length() != 0 && def.length() != 0) {
			try {
				Card card = new Card(term_side, def_side);
				cardService.createCard(card);
				ctx.html("Created card");
			} catch (Exception e) {
				ctx.html(String.valueOf(e));
				log.warn("Exception was thrown " + String.valueOf(e));
			} 
		} else {
			log.warn("Non valid String passed in for Name");
			ctx.html("Term and def must be non empty");
		}
	}
	
	// read def of card given id 
	public void readCardDef(Context ctx) {
		
		log.trace("Entering readCardDef in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		try {
			ctx.html("Card def is " + cardService.readCardDef(cardId));
		} catch(Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// read term of card given id 
	public void readCardTerm(Context ctx) {
		
		log.trace("Entering readCardTerm in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		try {
			ctx.html("Card term is " + cardService.readCardTerm(cardId));
		} catch(Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// read score of card given id 
	public void readCardScore(Context ctx) {
		
		log.trace("Entering readCardScore in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		try {
			ctx.html("Card score is " + cardService.readCardScore(cardId));
		} catch(Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// update card term and def, making sure they are not empty 
	public void updateCardEntries(Context ctx) {
		
		log.trace("Entering updateCardEntries in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		String term = ctx.formParam("term");
		
		String def = ctx.formParam("def");
		
		Entry term_side = new Entry(term);
		
		Entry def_side = new Entry(def);
		
		Card card = new Card(term_side, def_side);
		
		if (term.length() != 0 && def.length() != 0) {
			try {
				cardService.updateCardEntries(cardId, card);
				ctx.html("Updated card");
			} catch (Exception e) {
				log.warn("Exception was thrown " + String.valueOf(e));
				ctx.html(String.valueOf(e));
			}
		} else {
			log.warn("Non valid string was enterd for term or def");
			ctx.html("New term and def must be non empty");
		}
		
	}
	
	// update one 'step' of a card score. 
	// ie, add one more correct or one more wrong 
	public void updateCardScore(Context ctx) {
		
		log.trace("Entering updateCardScore in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		String response = ctx.formParam("response");
		response = response.toLowerCase().trim();
		
		boolean res;
		
		if (response.equals("true") || response.equals("false")) {
			try {
				res = Boolean.parseBoolean(response);
				cardService.updateCardScore(cardId, res);
				ctx.html("Updated card score");
			} catch (Exception e) {
				log.warn("Exception was thrown " + String.valueOf(e));
				ctx.html(String.valueOf(e));
			}
		} else {
			log.warn("Non valid string was entered for response");
			ctx.html("Response must be 'true' or 'false' ");
		}
		
	}
	
	// delete a card given id (cascading)
	public void deleteCard(Context ctx) {
		
		log.trace("Entering deletCard in CardController");
		
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
	
		try {
			cardService.deleteCard(cardId);
			ctx.html("Deleted card " + cardId);
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
}
