package FlashCard.controller;

import FlashCard.pojos.User;
import FlashCard.pojos.User.UserType;
import FlashCard.service.UserService;
import FlashCard.service.UserServiceFullStack;
import io.javalin.http.Context;
import org.apache.log4j.Logger;

public class UserController {
	
	private static Logger log = Logger.getRootLogger();
	
	UserService userService = new UserServiceFullStack();
	
	public void createUser(Context ctx) {
		
		log.trace("Entering createUser in UserController");
		
		String userName = ctx.formParam("userName");
		UserType userType;
		String type = ctx.formParam("userType");
		
		if (type != null) {
			type = type.toLowerCase().trim();
			try {
				switch (type) {
				case "instructor":
					userType = UserType.INSTRUCTOR;
					break;
				case "student":
					userType = UserType.STUDENT;
					break;
				default:
					log.warn("Input invalid userType into context");
					throw new IllegalArgumentException("Please input either 'instructor' or 'student' for user type");
				}

				if (userName.length() != 0) {
					User user = new User(userName, userType);

					userService.createUser(user);

					ctx.html("Created user");
				} else {
					log.warn("Entered non valid String for userName");
					ctx.html("User name must be non empty");
				}

			} catch (IllegalArgumentException e) {
				log.warn("Encountered IllegalArgumentException " + String.valueOf(e));
				ctx.html(String.valueOf(e));
			} 
		} else {
			ctx.html("Don't forget the user type!");
		}
	}
	
	
	public void readUserCourses(Context ctx) {
		
		log.trace("Entering readUserCourses in UserController");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		try {
			ctx.html("User courses are: " + userService.readUserCourses(userId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void readUserName(Context ctx) {
		
		log.trace("Entering readUserName in UserController");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		try {
			ctx.html("User name is " + userService.readUserName(userId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
		
	}
		
	public void renameUser(Context ctx) {
		
		log.trace("Entering renameUser in UserController");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				userService.renameUser(userId, newName);
				ctx.html("renamed user");
			} catch (Exception e) {
				log.warn("Exception was thrown " + String.valueOf(e));
				ctx.html(String.valueOf(e));
			} 
		} else {
			log.warn("Entered non valid String for user name");
			ctx.html("New name must be non empty");
		}
	}
	
	public void deleteUser(Context ctx) {
		
		log.trace("Entering deleteUser in UserController");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		try {
			userService.deleteUser(userId);
			ctx.html("deleted user");
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	public void assignUserToCourse(Context ctx) {
		
		log.trace("Entering assignUserToCourse in UserController");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			userService.assignUserToCourse(userId, courseId);
			ctx.html("Assigned user " + userId + " to course " + courseId);
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
}
