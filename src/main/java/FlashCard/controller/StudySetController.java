package FlashCard.controller;

import FlashCard.pojos.StudySet;
import FlashCard.service.StudySetService;
import FlashCard.service.StudySetServiceFullStack;
import io.javalin.http.Context;
import org.apache.log4j.Logger;

//Control interactions between parameters passed in by user and calls to FullStackService
public class StudySetController {
	
	private static Logger log = Logger.getRootLogger();
	
	StudySetService studySetService = new StudySetServiceFullStack();
	
	// Ensure no empty strings are passed in as entries, then create study set  
	public void createStudySet(Context ctx) {
		
		log.trace("Entering createStudySet in StudySetController");
		
		String studySetName = ctx.formParam("studySetName");
		if (studySetName.length() != 0) {
			StudySet studySet = new StudySet(ctx.formParam("studySetName"));
			studySetService.createStudySet(studySet);
			ctx.html("Created study set");
		} else {
			log.warn("Nonv valind String entered for study set name");
			ctx.html("Study set must have non empty name");
		}
	}
	
	// read all cards in study set with id 
	public void readStudySetCards(Context ctx) {
		
		log.trace("Entering readStudySetCards in StudySetController");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		try {
			ctx.html("Study set cards are: " + studySetService.readStudySetCards(studySetId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// read name of study set given its id 
	public void readStudySetName(Context ctx) {
		
		log.trace("Entering readStudySetName in StudySetController");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		try {
			ctx.html("Study set name is " + studySetService.readStudySetName(studySetId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// after checking that new name is not empty, change name of study set with id 
	public void renameStudySet(Context ctx) {
		
		log.trace("Entering renameStudySet in StudySetController");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				studySetService.renameStudySet(studySetId, newName);
				ctx.html("renamed study set");
			} catch (Exception e) {
				log.warn("Exception was thrown " + String.valueOf(e));
				ctx.html(String.valueOf(e));
			}
		} else {
			log.warn("Non valid string was entered for new name");
			ctx.html("New name must not be empty");
		}
	}
	
	// delete study set with id, cascading
	public void deleteStudySet(Context ctx) {
		
		log.trace("Entering deleteStudySet in StudySetController");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		try {
			studySetService.deleteStudySet(studySetId);
			ctx.html("deleted study set");
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// add entry to the AssignCSS table with card_id and study_set id 
	public void assignCardToStudySet(Context ctx) {
		
		log.trace("Entering assignCardToStudySet in StudySetController");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		try {
			studySetService.assignCardToStudySet(cardId, studySetId);
			ctx.html("Assigned card " + cardId + " to study set " + studySetId);
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// retrieve the card from study set with id that has the lowest score 
	public void getCardWithMinScoreFromStudySet(Context ctx) {
		
		log.trace("Entering getCardWithMinScoreFromStudySet in StudySetController");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		try {
			ctx.html(studySetService.getCardWithMinScoreFromStudySet(studySetId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
}


