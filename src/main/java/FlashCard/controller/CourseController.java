
package FlashCard.controller;

import FlashCard.pojos.Course;
import org.apache.log4j.Logger;
import FlashCard.service.CourseService;
import FlashCard.service.CourseServiceFullStack;
import io.javalin.http.Context;

//Control interactions between parameters passed in by user and calls to FullStackService
public class CourseController {
	
	private static Logger log = Logger.getRootLogger();
	
	CourseService courseService = new CourseServiceFullStack();
	
	// ensure course name isn't empty, then create course 
	public void createCourse(Context ctx) {
		
		log.trace("Entering createCourse in CourseController");
		
		String courseName = ctx.formParam("courseName");
		
		if (courseName.length() != 0) {
			Course course = new Course(ctx.formParam("courseName"));
			courseService.createCourse(course);
			ctx.html("Created course");
		} else {
			log.warn("Non valid String entered for name");
			ctx.html("Course must have non empty name");
		}
		
	}
	
	// read the study sets of a course given its id 
	public void readCourseStudySets(Context ctx) {
		
		log.trace("Entering readCourseStudySets in CourseController");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		try {
			ctx.html("Course study sets are: " + courseService.readCourseStudySets(courseId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
	
	// read the name of a course given its id 
	public void readCourseName(Context ctx) {
		
		log.trace("Entering readCourseName in CourseController");
		
		int courseId = Integer.valueOf((ctx.formParam("courseId")));
		
		try {
			ctx.html("Course name is: " + courseService.readCourseName(courseId));
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
		
	}
	
	// after checking the new name is not empty, rename a course given its id 
	public void renameCourse(Context ctx) {
		
		log.trace("Entering renameCourse in CourseController");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		String newName = ctx.formParam("newName");
		
		if (newName.length() != 0) {
			try {
				courseService.renameCourse(courseId, newName);
				ctx.html("renamed course");
			} catch (Exception e) {
				ctx.html(String.valueOf(e));
				log.warn("Exception was thrown " + String.valueOf(e));
			} 
		} else {
			log.warn("Non valid string passed in for new Name");
			ctx.html("New course name must be non empty");
		}
	}
	
	// delete a course given its id, cascading
	public void deleteCourse(Context ctx) {
		
		log.trace("Entering deleteCourse in CourseController");
		
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			courseService.deleteCourse(courseId);
			ctx.html("deleted course");
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
		
	}
	
	// add a row to the AssignSSC table with study set id and course id 
	public void assignStudySetToCourse(Context ctx) {
		
		log.trace("Entering assignStudySetToCourse in CourseController");
		
		int studySetId = Integer.valueOf(ctx.formParam("studySetId"));
		int courseId = Integer.valueOf(ctx.formParam("courseId"));
		
		try {
			courseService.assignStudySetToCourse(studySetId, courseId);
			ctx.html("assigned study set " + studySetId + " to course " + courseId);
		} catch (Exception e) {
			log.warn("Exception was thrown " + String.valueOf(e));
			ctx.html(String.valueOf(e));
		}
	}
}
