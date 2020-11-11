package FlashCard.controller;

import FlashCard.pojos.Course;
import FlashCard.service.CourseService;
import FlashCard.service.CourseServiceFullStack;
import io.javalin.http.Context;

public class CourseController {
	
	CourseService courseService = new CourseServiceFullStack();
	
	public void createCourse(Context ctx) {
		System.out.println("Responding to create course request");
		
		Course course = new Course(ctx.formParam("courseName"));
		
		courseService.createCourse(course);
		
		ctx.html("Created course");
	}
	
	public void readCourseStudySets(Context ctx) {
		System.out.println("Responding to read course study sets");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		ctx.html(courseService.readCourseStudySets(courseId));
		
		ctx.html("Read course study sets");
	}
	
	public void readCourseName(Context ctx) {
		System.out.println("Responding to read course name request");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		ctx.html(courseService.readCourseName(courseId));
		
		ctx.html("Read course name");
		
	}
		
	public void renameCourse(Context ctx) {
		System.out.println("Responding to rename course request");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		String newName = ctx.formParam("newName");
		
		courseService.renameCourse(courseId, newName);
		ctx.html("renamed course");
	}
	
	public void deleteCourse(Context ctx) {
		System.out.println("Responding to delete course request");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		courseService.deleteCourse(courseId);
		ctx.html("deleted course");
		
	}
	
	public void assignStudySetToCourse(Context ctx) {
		System.out.println("Responding to assign card to study set request");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		courseService.assignStudySetToCourse(studySetId, courseId);
		ctx.html("assigned study set to course");
	}
}
