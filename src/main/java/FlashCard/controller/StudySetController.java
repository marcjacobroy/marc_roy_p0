package FlashCard.controller;

import FlashCard.pojos.StudySet;
import FlashCard.service.StudySetService;
import FlashCard.service.StudySetServiceFullStack;
import io.javalin.http.Context;

public class StudySetController {
	
	StudySetService studySetService = new StudySetServiceFullStack();
	
	public void createStudySet(Context ctx) {
		System.out.println("Responding to create study set request");
		
		StudySet studySet = new StudySet(ctx.formParam("studySetName"));
		
		studySetService.createStudySet(studySet);
		
		ctx.html("Created study set");
	}
	
	public void readStudySetCards(Context ctx) {
		System.out.println("Responding to read study set cards request");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		ctx.html(studySetService.readStudySetCards(studySetId));
		
		ctx.html("Read study set cards");
	}
	
	public void readStudySetName(Context ctx) {
		System.out.println("Responding to read study set name request");
		
		int studySetId = Integer.valueOf((ctx.formParam("studySetId")));
		
		ctx.html(studySetService.readStudySetName(studySetId));
		
		ctx.html("Read study set name");
	}
	
	public void renameStudySet(Context ctx) {
		System.out.println("Responding to rename study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		String newName = ctx.formParam("newName");
		
		studySetService.renameStudySet(studySetId, newName);
		ctx.html("renamed study set");
	}
	
	public void deleteStudySet(Context ctx) {
		System.out.println("Responding to delete study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		
		studySetService.deleteStudySet(studySetId);
		ctx.html("deleted study set");
	}
	
	public void assignCardToStudySet(Context ctx) {
		System.out.println("Responding to assign card to study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int cardId = Integer.valueOf(ctx.formParam("cardId"));
		
		studySetService.assignCardToStudySet(cardId, studySetId);
		ctx.html("assigned card to study set");
	}

}


