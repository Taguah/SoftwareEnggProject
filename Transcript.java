package project.excelSpike2;

/*
@author Jenny Wang, Ted Camp
*/

import java.util.List;
import java.util.ArrayList;

public class Transcript {

    private CourseList courseList;

    public Transcript() {
        this.courselist = new CourseList();
    }

    public void addCourse(Course course) {
    	this.courseList.add(course);
    }

    public Transcript(ArrayList<Course> courseListIn) {
    	this.courselist = new CourseList();
    	for (Course course : courseListIn) {
        	courseList.addCourse(course);
        }
    }

    public List<Course> getCourses() {
    	List<Course> courses = courseList.getCourses();
        return courses;
    }
    
    public List<Course> getPassingCourses() {
    	List<Course> courses = courseList.getPassingCourses();
        return courses;
    }

    public List<Course> getRetakenCourses() {
    	List<Course> courses = courseList.getRetakenCourses();
        return courses;
    }

    public List<Course> getCoursesByArea(String area){
    	List<Course> areaCourses = new ArrayList<Course>();
    	List<String> relevantCourses = AreaSchema.getAllCoursesInArea(area);
    	List<Course> courses = getCourses();
    	for (Course course : courses) {
    		String courseName = course.getCourseID();
    		courseName = EquivalencySchema.getEquivalent(courseName);
    		if (relevantCourses.contains(courseName)){
    			areaCourses.add(course);
    		}
    	}
    	return areaCourses;
    }
    
    public List<Course> getRetakenCoursesByArea(String area){
    	List<Course> areaCourses = new ArrayList<Course>();
    	List<String> relevantCourses = AreaSchema.getAllCoursesInArea(area);
    	List<Course> courses = getRetakenCourses();
    	for (Course course : courses) {
    		String courseName = course.getCourseID();
    		courseName = EquivalencySchema.getEquivalent(courseName);
    		if (relevantCourses.contains(courseName)){
    			areaCourses.add(course);
    		}
    	}
    	return areaCourses;
    }
    public double getAverageForArea(String area){
        double average = 0;
        double creditHours = 0;
        
        List<String> coursesInArea = AreaSchema.getAllCoursesInArea(area);
        List<Course> courses = getCourses();
        for (Course course : courses) {
        	if (coursesInArea.contains(course.getCourseID())) {
        		creditHours += course.getCreditHours();
        		average += course.getCreditHours() * ( course.getGrade().getNumberGrade() );
        	}
        }
        if (creditHours == 0) {
        	return -1;
        }
        else {
        	return (average/creditHours); 
        }
    }
    
    public String checkRank() {
    	double totalCreditHours = 0;
    	//Check retaken needs to be added
    	List<String> coursesTaken = new ArrayList<String>();
    	List<Course> courses = courseList.getPassingCourses();
    	for (Course course : courses) {
    		totalCreditHours = totalCreditHours + course.getCreditHours();
    	}
    	//Below needs rank schema
    	//String rank = RankSchema.checkRank(totalCreditHours);
    	String rank = "Boop";
    	return rank;
    }
}
