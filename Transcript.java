package project.excelSpike;

/*
@author Jenny Wang
*/

import java.util.List;
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
    
    public List<Course> getCoursesByArea(String area){
    	List<Course> areaCourses = new ArrayList<Course>();
    	List<String> relevantCourses = AreaSchema.getAllCoursesInArea(area);
    	for (Course course : courselist) {
    		if (relevantCourses.contains(course.getCourseID())){
    			areaCourses.add(course);
    		}
    	}
    	return areaCourses;
    }

    public double getAverageForArea(String area){
        double average = 0;
        double creditHours = 0;
        
        List<String> coursesInArea = AreaSchema.getAllCoursesInArea(area);
        for (Course course : courselist) {
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
    
}