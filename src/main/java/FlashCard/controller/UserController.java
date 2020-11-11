package FlashCard.controller;

import FlashCard.pojos.User;
import FlashCard.pojos.User.UserType;
import FlashCard.service.UserService;
import FlashCard.service.UserServiceFullStack;
import io.javalin.http.Context;

public class UserController {
	UserService userService = new UserServiceFullStack();
	
	public void createUser(Context ctx) {
		System.out.println("Responding to create user request");
		
		String userName = ctx.formParam("userName");
		UserType userType;
		String type = ctx.formParam("userType");
		type = type.toLowerCase().trim();
		try {
			switch(type) {
			case "instructor":
				userType = UserType.INSTRUCTOR;
				break;
			case "student":
				userType = UserType.STUDENT;
				break;
			default:
				throw new IllegalArgumentException("Please input either 'instructor' or 'student' for user type");
			}
			
			if (userName.length() != 0) {
				User user = new User(userName, userType);
				
				userService.createUser(user);
				
				ctx.html("Created user");
			} else {
				ctx.html("User name must be non empty");
			}
			
			
		} catch(IllegalArgumentException e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	
	public void readUserCourses(Context ctx) {
		System.out.println("Responding to read User study sets");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		try {
			ctx.html("User courses are: " + userService.readUserCourses(userId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void readUserName(Context ctx) {
		System.out.println("Responding to read user name request");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		try {
			ctx.html("User name is " + userService.readUserName(userId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
		
	}
		
	public void renameUser(Context ctx) {
		System.out.println("Responding to rename user request");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				userService.renameUser(userId, newName);
				ctx.html("renamed user");
			} catch (Exception e) {
				ctx.html(String.valueOf(e));
			} 
		} else {
			ctx.html("New name must be non empty");
		}
	}
	
	public void deleteUser(Context ctx) {
		System.out.println("Responding to delete user request");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		try {
			userService.deleteUser(userId);
			ctx.html("deleted user");
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void assignUserToCourse(Context ctx) {
		System.out.println("Responding to assign course to user request");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			userService.assignUserToCourse(userId, courseId);
			ctx.html("Assigned user " + userId + " to course " + courseId);
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
}
