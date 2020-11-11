package FlashCard.controller;

import FlashCard.pojos.Course;
import FlashCard.service.CourseService;
import FlashCard.service.CourseServiceFullStack;
import io.javalin.http.Context;

public class CourseController {
	
	CourseService courseService = new CourseServiceFullStack();
	
	public void createCourse(Context ctx) {
		System.out.println("Responding to create course request");
		
		String courseName = ctx.formParam("courseName");
		
		if (courseName.length() != 0) {
			Course course = new Course(ctx.formParam("courseName"));
			courseService.createCourse(course);
			ctx.html("Created course");
		} else {
			ctx.html("Course must have non empty name");
		}
		
	}
	
	public void readCourseStudySets(Context ctx) {
		System.out.println("Responding to read course study sets");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		try {
			ctx.html("Course study sets are: " + courseService.readCourseStudySets(courseId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
	
	public void readCourseName(Context ctx) {
		System.out.println("Responding to read course name request");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		try {
			ctx.html("Course name is: " + courseService.readCourseName(courseId));
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
		
	}
		
	public void renameCourse(Context ctx) {
		System.out.println("Responding to rename course request");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				courseService.renameCourse(courseId, newName);
				ctx.html("renamed course");
			} catch (Exception e) {
				ctx.html(String.valueOf(e));
			} 
		} else {
			ctx.html("New course name must be non empty");
		}
	}
	
	public void deleteCourse(Context ctx) {
		System.out.println("Responding to delete course request");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			courseService.deleteCourse(courseId);
			ctx.html("deleted course");
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
		
	}
	
	public void assignStudySetToCourse(Context ctx) {
		System.out.println("Responding to assign card to study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			courseService.assignStudySetToCourse(studySetId, courseId);
			ctx.html("assigned study set " + studySetId + " to course " + courseId);
		} catch (Exception e) {
			ctx.html(String.valueOf(e));
		}
	}
}
