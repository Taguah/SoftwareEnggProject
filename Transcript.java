package project.excelSpike;

/*
@author Ted Camp, Jenny Wang
*/

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import project.excelSpike.Course;
import project.excelSpike.GradeSchema;

import java.util.ArrayList;

public class Transcript {

	private Map<String, Course> passedCourses;
	private Map<String, Course> failedCourses;
	private Map<String, List<Course>> retakenCourses;

    public Transcript() {
        passedCourses = new HashMap<String, Course>();
        failedCourses = new HashMap<String, Course>();
        retakenCourses = new HashMap<String, List<Course>>();
    }
   
    public Transcript(ArrayList<Course> courseListIn) {
    	passedCourses = new HashMap<String, Course>();
        failedCourses = new HashMap<String, Course>();
        retakenCourses = new HashMap<String, List<Course>>();
    	for (Course course : courseListIn) {
        	this.addCourse(course);
        };
    }

    public void addCourse(Course newCourse) {
    	//Treat courses by their equivalent names
		String courseName = EquivalencySchema.getEquivalent(newCourse.getCourseID());
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
    
	private void addRetakenCourse(Course course) {
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
    
	public Map<String, Course> getPassedCourseMap(){
		return passedCourses;
	}
	
	public Map<String, Course> getFailedCourseMap(){
		return failedCourses;
	}
	
	public Map<String, List<Course>> getRetakenCourseMap(){
		return retakenCourses;
	}

	public Map<String, String> getAverageGradesByArea(){
		Map<String, String> averageGrades = new HashMap<String, String>();
		List<String> areas = AreaSchema.getAllAreas();
		Set<String> passedCourseNames = passedCourses.keySet();
		Set<String> failedCourseNames = failedCourses.keySet();
		Set<String> retakenCourseNames = retakenCourses.keySet();
		for (String area : areas) {
			List<String> coursesInArea = AreaSchema.getAllCoursesInArea(area);
			List<Double> gradesToAverage = new ArrayList<Double>();
			for (String courseName : coursesInArea) {
				if (passedCourseNames.contains(courseName)) {
					if(GradeSchema.checkLevel(passedCourses.get(courseName).getGrade().getLetterGrade()) != "Other") {
						gradesToAverage.add(passedCourses.get(courseName).getGrade().getNumberGrade());
					}
				}
				if (failedCourseNames.contains(courseName)) {
					gradesToAverage.add(failedCourses.get(courseName).getGrade().getNumberGrade());
				}
				if (retakenCourseNames.contains(courseName)) {
					List<Course> retakenCoursesOfName = retakenCourses.get(courseName);
					for (Course retakenCourse : retakenCoursesOfName) {
						gradesToAverage.add(retakenCourse.getGrade().getNumberGrade());
					}
				}
			}
			if(gradesToAverage.size() > 0) {
				double total = 0;
				for(double numberGrade : gradesToAverage) {
					total += numberGrade;
				}
				Grade averageGrade = new Grade(total/gradesToAverage.size());
				String letterGrade = averageGrade.getLetterGrade();
				averageGrades.put(area, letterGrade);
			}
		}
		return averageGrades;
	}
	
	public String checkRank() {
    	double totalCreditHours = 0;
    	
    	for (Course course : passedCourses.values()) {
    		totalCreditHours = totalCreditHours + course.getCreditHours();
    	}
    	String rank = RankSchema.checkRank(totalCreditHours);
    	return rank;
    }
}
