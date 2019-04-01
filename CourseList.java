package project.excelSpike;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CourseList {

	private Map<String, Course> passedCourses;
	private Map<String, Course> failedCourses;
	private Map<String, List<Course>> retakenCourses;
	
	public CourseList() {
		this.passedCourses = new HashMap<String, Course>();
		this.failedCourses = new HashMap<String, Course>();
		this.retakenCourses = new HashMap<String, List<Course>>();
	}

	public void addCourse(Course newCourse) {
		String courseName = newCourse.getCourseID();
		//Check if new mark is pass or fail
		if(GradeSchema.checkIfFailed(newCourse)) {
			//If failed, check if there has already been a failure
			Set<String> courseNamesInList = failedCourses.keySet();
			if (courseNamesInList.contains(courseName)) {
				addRetakenCourse(newCourse);
			}
			else {
				failedCourses.put(courseName, newCourse);
			}
			
		}
		else {
			//Else check if it was passed
			Set<String> courseNamesInList = passedCourses.keySet();
			if (courseNamesInList.contains(courseName)) {
				Course currentCourse = passedCourses.get(courseName);

				//If there is already a passing grade, compare them and keep the highest while adding the lowest to retaken courses
				Double newGrade = newCourse.getGrade().getNumberGrade();
				Double oldGrade = currentCourse.getGrade().getNumberGrade();
				
				if (newGrade < oldGrade) {
					addRetakenCourse(newCourse);
				}
				else {
					addRetakenCourse(currentCourse);
					passedCourses.put(courseName, newCourse);
				}
			}
			else {
				passedCourses.put(courseName, newCourse);
			}
		}
	}
	
	public void addRetakenCourse(Course course) {
		String courseName = course.getCourseID();
		Set<String> courseNamesInList = retakenCourses.keySet();
		if (courseNamesInList.contains(courseName)) {
			List<Course> retakenList = retakenCourses.get(courseName);
			retakenList.add(course);
			retakenCourses.put(courseName, retakenList);
		}
		else {
			List<Course> retakenList = new ArrayList<Course>();
			retakenList.add(course);
			retakenCourses.put(courseName, retakenList);
		}
	}

	public List<Course> getCourses(){
		List<Course> courses = new ArrayList<Course>();
		for(String courseName : passedCourses.keySet()) {
			courses.add(passedCourses.get(courseName));
		}
		for(String courseName : failedCourses.keySet()) {
			courses.add(passedCourses.get(courseName));
		}
		return courses;
	}
	
	public List<Course> getPassingCourses(){
		List<Course> courses = new ArrayList<Course>();
		for(String courseName : passedCourses.keySet()) {
			courses.add(passedCourses.get(courseName));
		}
		return courses;
	}
	
	public List<Course> getCoursesForName(String courseName){
		List<Course> courses = new ArrayList<Course>();
		courses.add(passedCourses.get(courseName));
		courses.add(failedCourses.get(courseName));
		return courses;
	}
	
	public List<Course> getRetakenCourses(){
		List<Course> courses = new ArrayList<Course>();
		for(String courseName : retakenCourses.keySet()) {
			courses.addAll(retakenCourses.get(courseName));
		}
		return courses;
	}
	
	public List<Course> getRetakenCoursesForName(String courseName){
		return retakenCourses.get(courseName);
	}
}
