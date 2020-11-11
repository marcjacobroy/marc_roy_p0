package FlashCard.controller;

import FlashCard.pojos.User;
import FlashCard.pojos.User.UserType;
import FlashCard.service.UserService;
import FlashCard.service.UserServiceFullStack;
import io.javalin.http.Context;

public class UserController {
	UserService userService = new UserServiceFullStack();
	
	public void createStudent(Context ctx) {
		System.out.println("Responding to create student request");
		
		User user = new User(ctx.formParam("userName"), UserType.STUDENT);
		
		userService.createUser(user);
		
		ctx.html("Created student");
	}
	
	public void createInstructor(Context ctx) {
		System.out.println("Responding to create instructor request");
		
		User user = new User(ctx.formParam("userName"), UserType.INSTRUCTOR);
		
		userService.createUser(user);
		
		ctx.html("Created instructor");
	}
	
	
	public void readUserCourses(Context ctx) {
		System.out.println("Responding to read User study sets");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		ctx.html(userService.readUserCourses(userId));
		
		ctx.html("Read user courses");
	}
	
	public void readUserName(Context ctx) {
		System.out.println("Responding to read user name request");
		
		int userId = Integer.valueOf((ctx.formParam("userId")));
		
		ctx.html(userService.readUserName(userId));
		
		ctx.html("Read user name");
		
	}
		
	public void renameUser(Context ctx) {
		System.out.println("Responding to rename user request");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		String newName = ctx.formParam("newName");
		
		userService.renameUser(userId, newName);
		ctx.html("renamed user");
	}
	
	public void deleteUser(Context ctx) {
		System.out.println("Responding to delete user request");
		
		int userId = Integer.valueOf(ctx.formParam("userId"));
		
		userService.deleteUser(userId);
		ctx.html("deleted user");
	}
}
