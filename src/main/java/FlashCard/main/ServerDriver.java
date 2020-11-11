package FlashCard.main;

import io.javalin.Javalin;

import org.apache.log4j.Logger;

import FlashCard.controller.CardController;
import FlashCard.controller.StudySetController;
import FlashCard.controller.CourseController;
import FlashCard.controller.UserController;

public class ServerDriver {
	
	private static Logger log = Logger.getRootLogger();
	
	private static CardController cardController = new CardController();
	private static StudySetController studySetController = new StudySetController();
	private static CourseController courseController = new CourseController();
	private static UserController userController = new UserController();
	
	private static final String CARD_PATH = "/card";
	private static final String ENTRIES_PATH = "/entries";
	private static final String SCORE_PATH = "/score";
	private static final String TERM_PATH = "/term";
	private static final String DEF_PATH = "/def";
	private static final String STUDY_SET_PATH = "/studyset";
	private static final String NAME_PATH = "/name";
	private static final String CARDS_PATH = "/cards";
	private static final String COURSE_PATH = "/course";
	private static final String STUDY_SETS_PATH = "/studysets";
	private static final String MIN_CARD_PATH = "/mincard";
	private static final String USER_PATH = "/user";
	private static final String COURSES_PATH = "/courses";
	
	public static void main(String[] args) {
		log.info("Program has started in Server Driver");
		Javalin app = Javalin.create().start(9096); //sets up and starts our server
		app.get("/hello", ctx -> ctx.html("Hello World"));
		app.post(CARD_PATH, ctx -> cardController.createCard(ctx));
		app.get(CARD_PATH + DEF_PATH, ctx -> cardController.readCardDef(ctx));
		app.get(CARD_PATH + TERM_PATH, ctx -> cardController.readCardTerm(ctx));
		app.get(CARD_PATH + SCORE_PATH, ctx -> cardController.readCardScore(ctx));
		app.patch(CARD_PATH + ENTRIES_PATH, ctx -> cardController.updateCardEntries(ctx));
		app.patch(CARD_PATH + SCORE_PATH, ctx -> cardController.updateCardScore(ctx));
		app.delete(CARD_PATH, ctx -> cardController.deleteCard(ctx));
		
		app.post(STUDY_SET_PATH, ctx -> studySetController.createStudySet(ctx));
		app.get(STUDY_SET_PATH + CARDS_PATH, ctx -> studySetController.readStudySetCards(ctx));
		app.get(STUDY_SET_PATH + NAME_PATH, ctx -> studySetController.readStudySetName(ctx));
		app.get(STUDY_SET_PATH + MIN_CARD_PATH, ctx -> studySetController.getCardWithMinScoreFromStudySet(ctx));
		app.patch(STUDY_SET_PATH, ctx -> studySetController.renameStudySet(ctx));
		app.delete(STUDY_SET_PATH, ctx -> studySetController.deleteStudySet(ctx));
		app.put(STUDY_SET_PATH, ctx -> studySetController.assignCardToStudySet(ctx));
		
		app.post(COURSE_PATH, ctx -> courseController.createCourse(ctx));
		app.get(COURSE_PATH + STUDY_SETS_PATH, ctx -> courseController.readCourseStudySets(ctx));
		app.get(COURSE_PATH + NAME_PATH, ctx -> courseController.readCourseName(ctx));
		app.patch(COURSE_PATH, ctx -> courseController.renameCourse(ctx));
		app.delete(COURSE_PATH, ctx -> courseController.deleteCourse(ctx));
		app.put(COURSE_PATH, ctx -> courseController.assignStudySetToCourse(ctx));
		
		app.post(USER_PATH, ctx -> userController.createUser(ctx));
		app.get(USER_PATH + COURSES_PATH, ctx -> userController.readUserCourses(ctx));
		app.get(USER_PATH + NAME_PATH, ctx -> userController.readUserName(ctx));
		app.patch(USER_PATH, ctx -> userController.renameUser(ctx));
		app.delete(USER_PATH, ctx -> userController.deleteUser(ctx));
		app.put(USER_PATH, ctx -> userController.assignUserToCourse(ctx));
	}
	
}
