package project.excelSpike;

/*
@author Jenny Wang
*/

import java.util.ArrayList;

public class Transcript {

    private ArrayList<Course> courselist;

    public Transcript() {
        courselist = new ArrayList<Course>();
    }
   
    public void addCourse(Course course) {
    	this.courselist.add(course);
    }
    
    public Transcript(ArrayList<Course> courseListIn) {
        courselist = courseListIn;
    }

    public ArrayList<Course> getCourses() {
        return courselist;
    }

    public ArrayList<Grade> getAllGrades(){
    	ArrayList<Grade> grades = new ArrayList<Grade>();
    	for(Course course : courselist) {
    		grades.add(course.getGrade());
    	}
    	return grades;
    }
    
    public ArrayList<Course> getCoursesByArea(String area){
    	ArrayList<Course> areaCourses = new ArrayList<Course>();
    	ArrayList<String> relevantCourses = AreaSchema.getAllCoursesInArea(area);
    	for (Course course : courselist) {
    		if (relevantCourses.contains(course.getCourseID())){
    			areaCourses.add(course);
    		}
    	}
    	return areaCourses;
    }
    
}