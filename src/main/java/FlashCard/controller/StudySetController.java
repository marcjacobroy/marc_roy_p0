package FlashCard.controller;

import FlashCard.pojos.StudySet;
import FlashCard.service.StudySetService;
import FlashCard.service.StudySetServiceFullStack;
import io.javalin.http.Context;

public class StudySetController {
	
	StudySetService studySetService = new StudySetServiceFullStack();
	
	public void createStudySet(Context ctx) {
		System.out.println("Responding to create study set request");
		
		String studySetName = ctx.formParam("studySetName");
		if (studySetName.length() != 0) {
			StudySet studySet = new StudySet(ctx.formParam("studySetName"));
			studySetService.createStudySet(studySet);
			ctx.html("Created study set");
		} else {
			ctx.html("Study set must have non empty name");
		}
	}
	
	public void readStudySetCards(Context ctx) {
		System.out.println("Responding to read study set cards request");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		try {
			ctx.html("Study set cards are: " + studySetService.readStudySetCards(studySetId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void readStudySetName(Context ctx) {
		System.out.println("Responding to read study set name request");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		try {
			ctx.html("Study set name is " + studySetService.readStudySetName(studySetId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void renameStudySet(Context ctx) {
		System.out.println("Responding to rename study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				studySetService.renameStudySet(studySetId, newName);
				ctx.html("renamed study set");
			} catch (Exception e) {
				ctx.html(String.valueOf(e));
			}
		} else {
			ctx.html("New name must not be empty");
		}
	}
	
	public void deleteStudySet(Context ctx) {
		System.out.println("Responding to delete study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		try {
			studySetService.deleteStudySet(studySetId);
			ctx.html("deleted study set");
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void assignCardToStudySet(Context ctx) {
		System.out.println("Responding to assign card to study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		try {
			studySetService.assignCardToStudySet(cardId, studySetId);
			ctx.html("Assigned card " + cardId + " to study set " + studySetId);
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	

}


